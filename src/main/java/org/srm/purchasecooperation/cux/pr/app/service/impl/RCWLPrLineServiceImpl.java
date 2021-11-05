package org.srm.purchasecooperation.cux.pr.app.service.impl;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import com.alibaba.fastjson.JSON;
import io.choerodon.core.domain.Page;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.hzero.boot.platform.lov.annotation.ProcessLovValue;
import org.hzero.core.base.BaseConstants;
import org.hzero.mybatis.domian.Condition;
import org.hzero.mybatis.util.Sqls;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.srm.boot.platform.configcenter.CnfHelper;
import org.srm.purchasecooperation.cux.acp.infra.constant.RCWLAcpConstant;
import org.srm.purchasecooperation.cux.pr.app.service.RCWLPrLineService;
import org.srm.purchasecooperation.order.api.dto.RcwlBudgetDistributionDTO;
import org.srm.purchasecooperation.order.domain.repository.RcwlBudgetDistributionRepository;
import org.srm.purchasecooperation.pr.api.dto.PrLineAssignDTO;
import org.srm.purchasecooperation.pr.app.service.impl.PrLineServiceImpl;
import org.srm.purchasecooperation.pr.domain.entity.PrLine;
import org.srm.purchasecooperation.pr.domain.entity.PrLineSupplier;
import org.srm.purchasecooperation.pr.domain.repository.PrLineAssignRepository;
import org.srm.purchasecooperation.pr.domain.repository.PrLineRepository;
import org.srm.purchasecooperation.pr.domain.repository.PrLineSupplierRepository;
import org.srm.purchasecooperation.pr.domain.vo.PrLineExecutorVO;
import org.srm.purchasecooperation.pr.domain.vo.PrLineVO;
import org.srm.purchasecooperation.pr.infra.utils.ListValueUtils;
import org.srm.web.annotation.Tenant;

/**
 * 采购申请行应用服务默认实现
 *
 * @author bin.zhang06@hand-china.com 2021-03-16 15:49:15
 */
@Tenant(RCWLAcpConstant.TENANT_NUMBER)
@Service
public class RCWLPrLineServiceImpl extends PrLineServiceImpl implements RCWLPrLineService {
    private static final Logger LOGGER = LoggerFactory.getLogger(RCWLPrLineServiceImpl.class);

    @Autowired
    private PrLineRepository prLineRepository;
    @Autowired
    private PrLineAssignRepository prLineAssignRepository;
    @Autowired
    private PrLineSupplierRepository prLineSupplierRepository;
    @Autowired
    private RcwlBudgetDistributionRepository rcwlBudgetDistributionRepository;
//

    @Override
    @ProcessLovValue
    public Page<PrLineVO> pagePrLines(PageRequest pageRequest, Long tenantId, Long prHeaderId, String hideFlag) {
        Page<PrLineVO> prLineVOs = this.prLineRepository.pagePrLines(pageRequest, tenantId, prHeaderId);
        this.jointExecutor(tenantId, prLineVOs);
        this.jointSupplierList(tenantId, prLineVOs);
        if (!"0".equals(hideFlag)) {
            this.hideSupplierHandler(tenantId, prLineVOs.getContent());
        }

        prLineVOs.forEach((prLineVO) -> {
            if("SOURCE_RFX".equals(prLineVO.getExecutionStatusCode())||"SOURCE_BID".equals(prLineVO.getExecutionStatusCode())) {
                prLineVO.setOccupyFlag(BaseConstants.Flag.NO);
            }else{
                if (prLineVO.getOccupiedQuantity() == null) {
                    prLineVO.setOccupyFlag(BaseConstants.Flag.NO);
                } else {
                    if (prLineVO.getOccupiedQuantity().compareTo(BigDecimal.ZERO) > 0) {
                        prLineVO.setOccupyFlag(BaseConstants.Flag.YES);
                    } else {
                        prLineVO.setOccupyFlag(BaseConstants.Flag.NO);
                    }

                }
            }
        });
        this.changePercentHandler(prLineVOs.getContent());
        return prLineVOs;
    }

    private void jointExecutor(Long tenantId, List<PrLineVO> prLineVOs) {
        if (!CollectionUtils.isEmpty(prLineVOs)) {
            String autoAssign = "NONE";

            try {
                autoAssign = (String) CnfHelper.select(tenantId, "SITE.SPUC.PR.AUTO_ASSIGN", String.class).invokeWithParameter(Collections.emptyMap());
            } catch (Exception var21) {
                LOGGER.debug("12705 ====租户id:{},采购申请=:{},查询到的采购申请自动分配异常：{}", new Object[]{tenantId, JSON.toJSONString(prLineVOs), var21});
            }

            boolean autoAssignFlag = !StringUtils.equals(autoAssign, "NONE");
            List<PrLineVO> assignedPrLineVOs = (List)prLineVOs.stream().filter((prLineVOx) -> {
                return BaseConstants.Flag.YES.equals(prLineVOx.getAssignedFlag());
            }).collect(Collectors.toList());
            List<PrLineVO> notAssignedPrLineVOs = (List)prLineVOs.stream().filter((prLineVOx) -> {
                return BaseConstants.Flag.NO.equals(prLineVOx.getAssignedFlag());
            }).collect(Collectors.toList());
            String autoAssignType = null;

            try {
                autoAssignType = (String)CnfHelper.select(tenantId, "SITE.SPUC.PR.AUTO_ASSIGN", String.class).invokeWithParameter(Collections.emptyMap());
            } catch (Exception var20) {
                LOGGER.debug("12705 ====租户id:{},采购申请=:{},查询到的采购申请自动分配异常：{}", new Object[]{tenantId, JSON.toJSONString(prLineVOs), var20});
            }

            Map<String, List<PrLineExecutorVO>> itemExecutorMap = new HashMap();
            Map<Long, List<PrLineExecutorVO>> purExecutorMap = new HashMap();
            Map<Long, List<PrLineAssignDTO>> longListMap = new HashMap();
            List<PrLineExecutorVO> headerExecutors;
            Set itemIds;
            if (autoAssignFlag && "ITEM_CATEGORY".equals(autoAssignType)) {
                itemIds = (Set)notAssignedPrLineVOs.stream().map(PrLineVO::getCategoryId).collect(Collectors.toSet());
                headerExecutors = this.prLineRepository.selectCategoryExecutorByIds(tenantId, itemIds);
                if (CollectionUtils.isNotEmpty(headerExecutors)) {
                    purExecutorMap.putAll((Map)headerExecutors.stream().collect(Collectors.groupingBy((prLineExecutorVO) -> {
                        return prLineExecutorVO.getCategoryId();
                    })));
                }
            } else if (autoAssignFlag && "PURCHASER".equals(autoAssignType)) {
                itemIds = (Set)notAssignedPrLineVOs.stream().map(PrLineVO::getPurchaseAgentId).collect(Collectors.toSet());
                headerExecutors = this.prLineRepository.selectPurchaseExecutorByIds(tenantId, itemIds);
                if (CollectionUtils.isNotEmpty(headerExecutors)) {
                    purExecutorMap.putAll((Map)headerExecutors.stream().collect(Collectors.groupingBy((prLineExecutorVO) -> {
                        return prLineExecutorVO.getPurchaseAgentId();
                    })));
                }
            } else if (autoAssignFlag && "EXECUTOR".equals(autoAssignType)) {
                itemIds = (Set)notAssignedPrLineVOs.stream().map(PrLineVO::getItemId).filter((item) -> {
                    return Objects.nonNull(item);
                }).collect(Collectors.toSet());
                headerExecutors = this.prLineRepository.selectItemExecutorByIds(tenantId, itemIds);
                if (CollectionUtils.isNotEmpty(headerExecutors)) {
                    itemExecutorMap.putAll((Map)headerExecutors.stream().collect(Collectors.groupingBy((prLineExecutorVO) -> {
                        return prLineExecutorVO.getItemId().toString();
                    })));
                }

                List<PrLineExecutorVO> invItemExecutors = this.prLineRepository.selectItemOrgExecutorByIds(tenantId, itemIds);
                if (CollectionUtils.isNotEmpty(invItemExecutors)) {
                    StringBuilder str = new StringBuilder();
                    itemExecutorMap.putAll((Map)invItemExecutors.stream().collect(Collectors.groupingBy((prLineExecutorVO) -> {
                        str.setLength(0);
                        str.append(prLineExecutorVO.getItemId());
                        str.append(":");
                        str.append(prLineExecutorVO.getInvOrganizationId());
                        return str.toString();
                    })));
                }
            }

            List<Long> prLineIds = (List)assignedPrLineVOs.stream().map(PrLineVO::getPrLineId).collect(Collectors.toList());
            List<List<Long>> prLineIdList = (List) ListValueUtils.divideSubListStream(prLineIds, 1000).collect(Collectors.toList());
            List<PrLineAssignDTO> prLineAssignDTOs = this.prLineAssignRepository.selectPrLineUserNameList(prLineIdList);
            if (CollectionUtils.isNotEmpty(prLineAssignDTOs)) {
                longListMap = (Map)prLineAssignDTOs.stream().collect(Collectors.groupingBy(PrLineAssignDTO::getPrLineId));
            }

            Iterator var16 = prLineVOs.iterator();

            while(true) {
                while(var16.hasNext()) {
                    PrLineVO prLineVO = (PrLineVO)var16.next();
                    List<PrLineExecutorVO> executorVOS = new ArrayList<>();
                    if (autoAssignFlag && BaseConstants.Flag.NO.equals(prLineVO.getAssignedFlag())) {
                        if ("ITEM_CATEGORY".equals(autoAssignType)) {
                            if (prLineVO.getCategoryId() != null) {
                                executorVOS = (List)purExecutorMap.get(prLineVO.getCategoryId());
                                if (CollectionUtils.isNotEmpty(executorVOS)) {
                                    prLineVO.setExecutorName((String)executorVOS.stream().filter((executorVO) -> {
                                        return StringUtils.isNotEmpty(executorVO.getExecutorName());
                                    }).map(PrLineExecutorVO::getExecutorName).collect(Collectors.joining(",")));
                                }
                            }
                        } else if ("PURCHASER".equals(autoAssignType)) {
                            if (prLineVO.getPurchaseAgentId() != null) {
                                executorVOS = (List)purExecutorMap.get(prLineVO.getPurchaseAgentId());
                                if (CollectionUtils.isNotEmpty(executorVOS)) {
                                    prLineVO.setExecutorName((String)executorVOS.stream().filter((executorVO) -> {
                                        return StringUtils.isNotEmpty(executorVO.getExecutorName());
                                    }).map(PrLineExecutorVO::getExecutorName).collect(Collectors.joining(",")));
                                }
                            }
                        } else if ("EXECUTOR".equals(autoAssignType) && prLineVO.getItemId() != null) {
                            String key = prLineVO.appendKey();
                            List<PrLineExecutorVO> executorVOS2 = itemExecutorMap.containsKey(key) ? (List)itemExecutorMap.get(key) : (List)itemExecutorMap.get(prLineVO.getItemId().toString());
                            if (CollectionUtils.isNotEmpty(executorVOS2)) {
                                prLineVO.setExecutorName((String)executorVOS2.stream().filter((executorVO) -> {
                                    return StringUtils.isNotEmpty(executorVO.getExecutorName());
                                }).map(PrLineExecutorVO::getExecutorName).collect(Collectors.joining(",")));
                            }
                        }
                    } else if (BaseConstants.Flag.YES.equals(prLineVO.getAssignedFlag())) {
                        List<PrLineAssignDTO> executorVOS3 = new ArrayList<>();
                        executorVOS3 = (List)((Map)longListMap).get(prLineVO.getPrLineId());
                        if (CollectionUtils.isNotEmpty(executorVOS3)) {
                            prLineVO.setExecutorName((String)executorVOS3.stream().map(PrLineAssignDTO::getNeededExecutorName).collect(Collectors.joining(",")));
                        }
                    }
                }

                return;
            }
        }
    }

    private List<PrLineVO> jointSupplierList(Long tenantId, List<PrLineVO> prLineVOs) {
        if (CollectionUtils.isEmpty(prLineVOs)) {
            return Collections.emptyList();
        } else {
            List<Long> prLineIds = (List)prLineVOs.stream().map(PrLineVO::getPrLineId).collect(Collectors.toList());
            List<PrLineSupplier> prLineSuppliers = this.prLineSupplierRepository.selectByCondition(Condition.builder(PrLineSupplier.class).andWhere(Sqls.custom().andIn("prLineId", prLineIds)).build());
            if (CollectionUtils.isEmpty(prLineSuppliers)) {
                return Collections.emptyList();
            } else {
                prLineSuppliers.forEach(PrLineSupplier::concatTempKey);
                Map<Long, List<PrLineSupplier>> supplierMap = (Map)prLineSuppliers.stream().collect(Collectors.groupingBy(PrLineSupplier::getPrLineId));
                prLineVOs.forEach((v) -> {
                    List<PrLineSupplier> suppliers = (List)supplierMap.get(v.getPrLineId());
                    if (!CollectionUtils.isEmpty(suppliers)) {
                        suppliers.stream().forEach((prLineSupplier) -> {
                            prLineSupplier.setDisplaySupplierName(StringUtils.isBlank(prLineSupplier.getSupplierName()) ? prLineSupplier.getSupplierCompanyName() : prLineSupplier.getSupplierName());
                        });
                        v.setSupplierList(suppliers);
                    }
                });
                return prLineVOs;
            }
        }
    }

    private void changePercentHandler(List<PrLineVO> prLineVOList) {
        prLineVOList.forEach((prLineVO) -> {
            if (Objects.nonNull(prLineVO.getChangePercent())) {
                prLineVO.setChangePercentMeaning(prLineVO.getChangePercent().multiply(new BigDecimal("100")).setScale(2, 4).toString() + "%");
            }

        });
    }

    @Override
    public void deleteLines(Long prHeaderId, List<PrLine> prLines) {
        super.deleteLines(prHeaderId, prLines);
        // 行删除之后需要预算信息
        rcwlBudgetDistributionRepository.deleteBudgetDistributionNotAcrossYear(prLines.get(0).getTenantId(), RcwlBudgetDistributionDTO.builder().prHeaderId(prHeaderId).prLineIds(prLines.stream().map(PrLine::getPrLineId).collect(Collectors.toList())).build());
    }
}
