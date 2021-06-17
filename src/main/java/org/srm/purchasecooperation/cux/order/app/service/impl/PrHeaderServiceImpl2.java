package org.srm.purchasecooperation.cux.order.app.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import io.choerodon.core.convertor.ApplicationContextHelper;
import io.choerodon.core.exception.CommonException;
import io.choerodon.core.oauth.CustomUserDetails;
import io.choerodon.core.oauth.DetailsHelper;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.hzero.boot.customize.util.CustomizeHelper;
import org.hzero.core.base.BaseConstants;
import org.hzero.mybatis.domian.Condition;
import org.hzero.mybatis.util.Sqls;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.srm.boot.platform.configcenter.CnfHelper;
import org.srm.purchasecooperation.common.api.dto.TenantDTO;
import org.srm.purchasecooperation.cux.pr.infra.mapper.RcwlPrFeignMapper;
import org.srm.purchasecooperation.order.api.dto.PoDTO;
import org.srm.purchasecooperation.order.app.service.PoHeaderService;
import org.srm.purchasecooperation.order.infra.mapper.PoHeaderMapper;
import org.srm.purchasecooperation.pr.app.service.PrHeaderService;
import org.srm.purchasecooperation.pr.app.service.impl.PrHeaderServiceImpl;
import org.srm.purchasecooperation.pr.domain.entity.PrHeader;
import org.srm.purchasecooperation.pr.domain.entity.PrLine;
import org.srm.purchasecooperation.pr.domain.repository.PrLineRepository;
import org.srm.purchasecooperation.utils.annotation.EventSendTran;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PrHeaderServiceImpl2 {

    @Autowired
    private RcwlPoHeaderServiceImpl2 rcwlPoHeaderServiceImpl2;
    @Autowired
    private PrHeaderServiceImpl prHeaderServiceImpl;
    @Autowired
    private PrLineRepository prLineRepository;
    @Autowired
    private PoHeaderService poHeaderService;
    @Autowired
    private PoHeaderMapper poHeaderMapper;
    @Autowired
    private RcwlPrFeignMapper rcwlPrFeignMapper;

    private static final Logger LOGGER = LoggerFactory.getLogger(PrHeaderServiceImpl.class);

    @Transactional(
            rollbackFor = {Exception.class}
    )
    @EventSendTran(
            rollbackFor = {Exception.class}
    )
    @Async
    public void afterPrApprove(Long tenantId, List<PrHeader> prHeaderList) {
        LOGGER.debug("需求自动转单异步开始");
        if (DetailsHelper.getUserDetails() == null) {
            LOGGER.debug("需求转单设置UserDetail： {}", ((PrHeader)prHeaderList.get(0)).getCustomUserDetails());
            if (((PrHeader)prHeaderList.get(0)).getCustomUserDetails() != null) {
                if (((PrHeader)prHeaderList.get(0)).getCustomUserDetails().getLanguage() == null) {
                    ((PrHeader)prHeaderList.get(0)).getCustomUserDetails().setLanguage("zh_CN");
                }

                if (((PrHeader)prHeaderList.get(0)).getCustomUserDetails().getOrganizationId() == null) {
                    ((PrHeader)prHeaderList.get(0)).getCustomUserDetails().setOrganizationId(tenantId);
                }

                if (((PrHeader)prHeaderList.get(0)).getCustomUserDetails().getUserId() == null) {
                    ((PrHeader)prHeaderList.get(0)).getCustomUserDetails().setUserId(0L);
                }

                DetailsHelper.setCustomUserDetails(((PrHeader)prHeaderList.get(0)).getCustomUserDetails());
            } else {
                CustomUserDetails customUserDetails = new CustomUserDetails("1", "1");
                customUserDetails.setTenantId(tenantId);
                customUserDetails.setLanguage("zh_CN");
                customUserDetails.setOrganizationId(tenantId);
                customUserDetails.setUserId(0L);
                TenantDTO tenantDTO = this.poHeaderMapper.selectTenantById(tenantId);
                customUserDetails.setTenantNum(tenantDTO.getTenantNum());
                DetailsHelper.setCustomUserDetails(customUserDetails);
                LOGGER.error("需求转单手动设置UserDetail： {}", customUserDetails.toString());
            }
        }

        String platform = ((PrHeader)prHeaderList.get(0)).getPrSourcePlatform();
        if (("E-COMMERCE".equals(platform) || "CATALOGUE".equals(platform)) && ((PrHeader)prHeaderList.get(0)).getCreatedBy() != null) {
            DetailsHelper.getUserDetails().setUserId(((PrHeader)prHeaderList.get(0)).getCreatedBy());
        }

        try {
            this.autoPrToPo(tenantId, prHeaderList);
        } catch (Exception var8) {
            LOGGER.error("需求转单失败： 数据长度是{}", JSON.toJSONString(prHeaderList.size()));
            LOGGER.error(var8.getMessage(), var8);
            throw new CommonException(var8, new Object[0]);
        } finally {
            //查询smpc_sku表的attribute_varchar1插入到订单行sodr_po_line的pc_num上 关联条件product_num
            List<Long> lineIdsAll = new ArrayList<>();
            prHeaderList.forEach(item->{
                List<Long> lineIds = rcwlPrFeignMapper.selectLineIdByHeadId(item.getPrHeaderId());
                lineIdsAll.addAll(lineIds);
            });
            LOGGER.info("25140============ lineIdsAll = {}",lineIdsAll);
            lineIdsAll.forEach(item -> {
                rcwlPrFeignMapper.updatePoLine(item);
                LOGGER.info("25140============ lineIdsAll = {}",item);
            });
            SecurityContextHolder.clearContext();
        }

    }
    @Transactional(
            rollbackFor = {Exception.class}
    )
    @EventSendTran(
            rollbackFor = {Exception.class}
    )
    public void autoPrToPo(Long tenantId, List<PrHeader> prHeaderList) {
        LOGGER.debug("24497====自动转单开始，数据是：{}", prHeaderList);
        if (!CollectionUtils.isEmpty(prHeaderList)) {
            List<PrHeader> approvedPrHeaderList = (List)prHeaderList.stream().filter((prHeader) -> {
                return "APPROVED".equals(prHeader.getPrStatusCode());
            }).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(approvedPrHeaderList)) {
                approvedPrHeaderList.stream().filter((prHeader) -> {
                    return "E-COMMERCE".equals(prHeader.getPrSourcePlatform());
                }).forEach((prHeader) -> {
                    Long prTypeId = prHeader.getPrTypeId();
                    String prSourcePlatform = prHeader.getPrSourcePlatform();
                    Long companyId = prHeader.getCompanyId();
                    Long purchaseOrgId = prHeader.getPurchaseOrgId();
                    HashMap<String, String> map = new HashMap();
                    map.put("prType", prTypeId + "");
                    map.put("sourceFrom", prSourcePlatform);
                    map.put("company", companyId + "");
                    map.put("purchaseOrganization", purchaseOrgId + "");
                    Map<String, String> t = new HashMap();
                    LOGGER.debug("PrHeaderServiceImpl.autoPrToPo====query auto pr to po configtenant id is :{} ,param is :{} ", prHeader.getTenantId(), map);

                    try {
                        t = this.autoPrToPoFlag(map, prHeader);
                    } catch (Exception var17) {
                        LOGGER.debug("24497====申请头id:{},查询到的自动转单报错，错误信息是是：{}", prHeader.getPrHeaderId(), var17);
                    }

                    LOGGER.debug("24497====申请头id:{},查询到商城的自动转单配置是：{}", prHeader.getPrHeaderId(), t);
                    if (t != null && ((Map)t).size() > 0) {
                        String result = (String)((Map)t).get("result");
                        String transFrequency = (String)((Map)t).get("transFrequency");
                        if (StringUtils.equals(BaseConstants.Flag.YES.toString(), result) && StringUtils.equals(transFrequency, "IMMEDIATELY")) {
                            List<PrLine> prLines = prHeader.getPrLineList();
                            if (prLines == null) {
                                prLines = this.prLineRepository.selectByCondition(Condition.builder(PrLine.class).andWhere(Sqls.custom().andEqualTo("prHeaderId", prHeader.getPrHeaderId())).build());
                            }

                            PoDTO poDTO;
                            try {
                                poDTO = (PoDTO) CustomizeHelper.ignore(() -> {
                                    return this.rcwlPoHeaderServiceImpl2.referWholePrHeaderAuto(tenantId, prHeader.getPrHeaderId());
                                });
                                prHeaderServiceImpl.processPrLineReturn(prLines, (String)null, "success");
                                LOGGER.info("=====订单创建成功");
                            } catch (Exception var16) {
                                String orderErroMessage = var16.getMessage();
                                LOGGER.error("====referWholePrHeaderAuto error is {}", orderErroMessage);
                                prHeaderServiceImpl.processPrLineReturn(prLines, orderErroMessage, "fail");
                                LOGGER.info("====订单创建失败");
                                return;
                            }

                            String poChangeStatus = prHeaderServiceImpl.queryAutoPoStatus(poDTO);
                            if ("SUBMITTED".equals(poChangeStatus)) {
                                try {
                                    this.poHeaderService.batchSubmittedPo(Arrays.asList(poDTO));
                                } catch (Exception var15) {
                                    LOGGER.error("订单自动提交失败： {}", JSON.toJSONString(Arrays.asList(poDTO)));
                                    LOGGER.error(var15.getMessage(), var15);
                                    throw new CommonException(var15, new Object[0]);
                                }
                            }
                        }
                    }

                });
                ((PrHeaderService) ApplicationContextHelper.getContext().getBean(PrHeaderService.class)).generatorPoByPrAuto(approvedPrHeaderList, tenantId);
            }
        }
    }
    protected Map<String, String> autoPrToPoFlag(HashMap<String, String> map, PrHeader prHeader) {
        return (Map) CnfHelper.select(prHeader.getTenantId(), "SITE.SPUC.PO.AUTO_PRTOPO_STRATEGY", Map.class).invokeWithParameter(map);
    }
}
