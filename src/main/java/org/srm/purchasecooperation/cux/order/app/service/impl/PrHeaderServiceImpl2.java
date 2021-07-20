package org.srm.purchasecooperation.cux.order.app.service.impl;

import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSON;
import io.choerodon.core.convertor.ApplicationContextHelper;
import io.choerodon.core.exception.CommonException;
import io.choerodon.core.oauth.CustomUserDetails;
import io.choerodon.core.oauth.DetailsHelper;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.hzero.boot.customize.util.CustomizeHelper;
import org.hzero.boot.platform.code.builder.CodeRuleBuilder;
import org.hzero.core.base.BaseConstants;
import org.hzero.mybatis.domian.Condition;
import org.hzero.mybatis.util.Sqls;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.srm.boot.platform.configcenter.CnfHelper;
import org.srm.purchasecooperation.common.api.dto.TenantDTO;
import org.srm.purchasecooperation.cux.order.domain.repository.RcwlPoHeaderRepository;
import org.srm.purchasecooperation.cux.order.domain.vo.RCWLItemInfoVO;
import org.srm.purchasecooperation.cux.pr.infra.mapper.RcwlCheckPoLineMapper;
import org.srm.purchasecooperation.cux.pr.infra.mapper.RcwlPrFeignMapper;
import org.srm.purchasecooperation.order.api.dto.PoDTO;
import org.srm.purchasecooperation.order.app.service.PoHeaderService;
import org.srm.purchasecooperation.order.domain.entity.PoLine;
import org.srm.purchasecooperation.order.domain.repository.PoLineRepository;
import org.srm.purchasecooperation.order.infra.mapper.PoHeaderMapper;
import org.srm.purchasecooperation.pr.app.service.PrHeaderService;
import org.srm.purchasecooperation.pr.app.service.impl.PrHeaderServiceImpl;
import org.srm.purchasecooperation.pr.domain.entity.ItemCategoryAssign;
import org.srm.purchasecooperation.pr.domain.entity.PrHeader;
import org.srm.purchasecooperation.pr.domain.entity.PrLine;
import org.srm.purchasecooperation.pr.domain.repository.ItemCategoryAssignRepository;
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
    @Autowired
    private PoLineRepository poLineRepository;
    @Autowired
    private RcwlCheckPoLineMapper rcwlCheckPoLineMapper;
    @Autowired
    private RcwlPoHeaderRepository poHeaderRepository;
    @Autowired
    private CodeRuleBuilder codeRuleBuilder;
    @Autowired
    private ItemCategoryAssignRepository itemCategoryAssignRepository;

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
                rcwlPrFeignMapper.updatePoLineVar21(item);
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
                            //bugfix-0623-jyb start
                            //校验po行商品编号有无物料编码
                            PoLine poLine = new PoLine();
                            poLine.setPoHeaderId(poDTO.getPoHeaderId());
                            poLine.setTenantId(tenantId);
                            List<PoLine> PoLines = poLineRepository.select(poLine);
                            List<RCWLItemInfoVO> poLineList = new ArrayList<>();
                            PoLines.forEach(itemLine-> {
                                RCWLItemInfoVO rcwlItemInfoVO = rcwlCheckPoLineMapper.checkPoItem(itemLine.getProductNum(), itemLine.getTenantId());
                                //没有物料创建编码并插入订单
                                if (rcwlItemInfoVO == null) {
                                    //查询需要封装的item数据集合(排除存在物料id的数据)
                                    List listTemp = new ArrayList();
                                    listTemp.add(itemLine.getPoLineId());
                                    List<RCWLItemInfoVO> rcwlItemInfoVOList = poHeaderRepository.selectItemListByPoLineIdList(listTemp, tenantId);
                                    List<ItemCategoryAssign> itemCategoryAssignList = new ArrayList<>();
                                    // 生成对应条编码
                                    if (CollectionUtils.isNotEmpty(rcwlItemInfoVOList)) {
                                        //判断来自哪个电商平台
                                        String dsFlag = poHeaderRepository.selectShopMallSupplier(poDTO.getPoHeaderId(),tenantId);

                                        rcwlItemInfoVOList.forEach(item -> {
                                            String itemCode = null;
                                            String categoryCode = item.getItemCode();
                                            //电商来源的物料编码为 品类编码+来源（00，01，02等）+电商编码
                                            if("CG".equals(dsFlag)){
                                                itemCode = categoryCode+"04"+item.getProductNum();
                                            }else if("ZKH".equals(dsFlag)){
                                                itemCode = categoryCode+"03"+item.getProductNum();
                                            }else if("JD".equals(dsFlag)){
                                                itemCode = categoryCode+"02"+item.getProductNum();
                                            }else{
                                                String str = this.codeRuleBuilder.generateCode("SODR.RCWL.ITEM_CODE",  (Map) null);
                                                itemCode = categoryCode+str;
                                            }

                                            //物料设值
                                            item.setTenantId(tenantId);
                                            //String categoryCode = item.getItemCode();
                                            // String str = this.codeRuleBuilder.generateCode(DetailsHelper.getUserDetails().getTenantId(), "SODR.RCWL.ITEM_CODE", "GLOBAL", "GLOBAL", (Map) null);
                                            String ruleCode = this.codeRuleBuilder.generateCode("SMDM.ITEM", (Map) null);
                                            item.setItemNumber(ruleCode);
                                            item.setItemCode(itemCode);
                                            item.setQueryItemCode(itemCode);
                                            item.setSpecifications(item.getSpecifications());
                                            item.setModel(item.getModel());
                                        });
                                        //批量插入物料表
                                        poHeaderRepository.batchInsertItem(rcwlItemInfoVOList);

                                        List<Long> lineIds = rcwlItemInfoVOList.stream().map(RCWLItemInfoVO::getPoLineId).distinct().collect(Collectors.toList());
                                        //查询出需要封装好的itemCategoryAssign
                                        List<RCWLItemInfoVO> itemCategoryList = poHeaderRepository.selectItemCategoryListByPoLineIdList(lineIds, tenantId);

                                        if (CollectionUtils.isNotEmpty(itemCategoryList)) {
                                            itemCategoryList.forEach(item -> {
                                                ItemCategoryAssign itemCategoryAssign = new ItemCategoryAssign();
                                                itemCategoryAssign.setItemId(item.getItemId());
                                                itemCategoryAssign.setTenantId(tenantId);
                                                itemCategoryAssign.setSourceCode(item.getSourceCode());
                                                itemCategoryAssign.setCategoryId(item.getCategoryId());
                                                itemCategoryAssignList.add(itemCategoryAssign);
                                            });
                                            //批量插入物料分配品类表
                                            itemCategoryAssignRepository.batchInsertSelective(itemCategoryAssignList);
                                        }
//                                        //把item_id item_code回写到订单行
//                                        List<RCWLItemInfoVO> poLineList1 = new ArrayList<>();
//                                        List<PoLine> poLineList2 = new ArrayList<>();
//                                        if (CollectionUtils.isNotEmpty(itemCategoryList)) {
//                                            itemCategoryList.forEach(poLine1 -> {
//                                                RCWLItemInfoVO lineUpdateInfo = new RCWLItemInfoVO();
//                                                PoLine poLine2 = this.poLineRepository.selectByPrimaryKey(poLine.getPoLineId());
//                                                lineUpdateInfo.setTenantId(tenantId);
//                                                lineUpdateInfo.setItemId(poLine.getItemId());
//                                                lineUpdateInfo.setItemCode(poLine.getItemCode());
//                                                lineUpdateInfo.setPoLineId(poLine.getPoLineId());
//                                                lineUpdateInfo.setItemName(poLine.getItemName());
//
//
//                                                poLine2.setItemId(poLine.getItemId());
//                                                poLine2.setItemCode(poLine.getItemCode());
//
//                                                poLineList1.add(lineUpdateInfo);
//
//                                                poLineList2.add(poLine2);
//                                            });
//                                            //批量更新订单物料id和code
//                                            // poHeaderRepository.batchUpdatePoLine(poLineList);
//                                            this.poLineRepository.batchUpdateByPrimaryKeySelective(poLineList2);
//                                            //批量插入物料名称多语言表smdm_item_tl
//                                            poHeaderRepository.batchInsertItemTl(poLineList1);
//                                        }
                                        //把item_id item_code回写到订单行
                                        List<RCWLItemInfoVO> poLineList1 = new ArrayList<>();
                                        if (CollectionUtils.isNotEmpty(itemCategoryList)) {
                                            itemCategoryList.forEach(poLine1 -> {
                                                RCWLItemInfoVO lineUpdateInfo = new RCWLItemInfoVO();
                                                lineUpdateInfo.setTenantId(tenantId);
                                                lineUpdateInfo.setItemId(poLine1.getItemId());
                                                lineUpdateInfo.setItemCode(poLine1.getItemCode());
                                                lineUpdateInfo.setPoLineId(poLine1.getPoLineId());
                                                lineUpdateInfo.setItemName(poLine1.getItemName());
                                                poLineList1.add(lineUpdateInfo);
                                            });
                                            //批量更新订单物料id和code
                                            poHeaderRepository.batchUpdatePoLine(poLineList1);
                                            //批量插入物料名称多语言表smdm_item_tl
                                            poHeaderRepository.batchInsertItemTl(poLineList1);
                                        }
                                    }
                                    //存在则只插入订单行物料编码和物料id物料名称
                                }else {
                                    rcwlItemInfoVO.setPoLineId(itemLine.getPoLineId());
                                    poLineList.add(rcwlItemInfoVO);
                                }
                            });
                            if (poLineList.size() > 0) {
                                //批量更新订单物料id和code
                                poHeaderRepository.batchUpdatePoLine(poLineList);
                            }
                            //bugfix-0623-jyb end
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
