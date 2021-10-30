package org.srm.purchasecooperation.order.app.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.hzero.mybatis.domian.Condition;
import org.hzero.mybatis.util.Sqls;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.srm.purchasecooperation.order.api.dto.RcwlBudgetDistributionDTO;
import org.srm.purchasecooperation.order.app.service.RcwlBudgetDistributionService;
import org.srm.purchasecooperation.order.domain.entity.RcwlBudgetDistribution;
import org.srm.purchasecooperation.order.domain.repository.RcwlBudgetDistributionRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * 预算分配应用服务默认实现
 *
 * @author pengxu.zhi@hand-china.com 2021-10-29 16:56:18
 */
@Slf4j
@Service
public class RcwlBudgetDistributionServiceImpl implements RcwlBudgetDistributionService {
    @Autowired
    private RcwlBudgetDistributionRepository rcwlBudgetDistributionRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<RcwlBudgetDistribution> selectBudgetDistributionByPoLine(Long tenantId, RcwlBudgetDistributionDTO rcwlBudgetDistributionDTO) {
        // 根据订单头行ID、获取预算分配数据
        List<RcwlBudgetDistribution> budgetDistributionsInDB = rcwlBudgetDistributionRepository.selectByCondition((Condition.builder(RcwlBudgetDistribution.class).
                andWhere(Sqls.custom().andEqualTo(RcwlBudgetDistribution.FIELD_PO_HEADER_ID, rcwlBudgetDistributionDTO.getPoHeaderId())
                        .andEqualTo(RcwlBudgetDistribution.FIELD_PO_LINE_ID, rcwlBudgetDistributionDTO.getPoLineId())
                        .andEqualTo(RcwlBudgetDistribution.FIELD_TENANT_ID, tenantId)
                ).orderByAsc(RcwlBudgetDistribution.FIELD_BUDGET_DIS_YEAR).build()));

        log.info("原预算分配数据：{}",budgetDistributionsInDB.size());

        //未匹配则直接创建，匹配到则赋值原预算占用（手工）重新创建
        if (CollectionUtils.isEmpty(budgetDistributionsInDB)){
            return createBudgetDistributionByPoLine(tenantId, rcwlBudgetDistributionDTO,null);
        }
        //校验各年原预算值（手工）是否等于行金额
        BigDecimal totalBudgetDisAmount = budgetDistributionsInDB.stream().map(RcwlBudgetDistribution::getBudgetDisAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
        log.info("订单行总金额：{},各年原预算值（手工）：{}",rcwlBudgetDistributionDTO.getLineAmount(),totalBudgetDisAmount);
        if (totalBudgetDisAmount.compareTo(Optional.ofNullable(rcwlBudgetDistributionDTO.getLineAmount()).orElse(BigDecimal.ZERO))!= 0){
            budgetDistributionsInDB = null;
        }

        //重新计算系统预算，获取原手工预算，重新创建并清空原数据
        List<RcwlBudgetDistribution> budgetDistributionCreateList = createBudgetDistributionByPoLine(tenantId, rcwlBudgetDistributionDTO, budgetDistributionsInDB);
        rcwlBudgetDistributionRepository.batchDelete(budgetDistributionsInDB);
        return budgetDistributionCreateList;
    }

    private List<RcwlBudgetDistribution> createBudgetDistributionByPoLine(Long tenantId, RcwlBudgetDistributionDTO rcwlBudgetDistributionDTO, List<RcwlBudgetDistribution> budgetDistributionsInDB) {
        List<RcwlBudgetDistribution> budgetDistributionCreateList = new ArrayList<>();
        for (Long i = rcwlBudgetDistributionDTO.getAttributeDate1Year(); i <= rcwlBudgetDistributionDTO.getNeedByDateYear(); i++) {
            RcwlBudgetDistribution budgetDistribution = new RcwlBudgetDistribution();
            budgetDistribution.setPoHeaderId(rcwlBudgetDistributionDTO.getPoHeaderId());
            budgetDistribution.setPoLineId(rcwlBudgetDistributionDTO.getPoLineId());
            budgetDistribution.setBudgetDisYear(i);
            //预算总时长(月) = A2年B2月- A1年B1月=12-B1+B2+（A2-A1-1）*12
            budgetDistribution.setBudgetDisGap(12 - rcwlBudgetDistributionDTO.getNeedByDateMonth() + rcwlBudgetDistributionDTO.getAttributeDate1Month()
                    + (rcwlBudgetDistributionDTO.getNeedByDateYear() - rcwlBudgetDistributionDTO.getAttributeDate1Year() - 1) * 12);
            budgetDistribution.setTenantId(tenantId);

            if (CollectionUtils.isNotEmpty(budgetDistributionsInDB)){
                Long finalI = i;
                RcwlBudgetDistribution rcwlBudgetDistribution = budgetDistributionsInDB.stream()
                        .filter(bd -> finalI.equals(bd.getBudgetDisYear())).findFirst().get();
                //预算占用(手工填写)
                log.info("预算年份：{},原年份预算值（手工）：{}",finalI,rcwlBudgetDistribution);
                budgetDistribution.setBudgetDisAmount(Objects.isNull(rcwlBudgetDistribution)?null:rcwlBudgetDistribution.getBudgetDisAmount());
            }

            //预算占用(系统计算值)
            if (i.equals(rcwlBudgetDistributionDTO.getAttributeDate1Year())) {
                // 当前行【占用年份】=A1，则为【申请行总金额】/【占用总时长（月）】*【12-B1】
                budgetDistribution.setBudgetDisAmountCal(rcwlBudgetDistributionDTO.getLineAmount().divide(new BigDecimal(rcwlBudgetDistributionDTO.getBudgetDisGap() * (12 - rcwlBudgetDistributionDTO.getAttributeDate1Month())), 6, RoundingMode.HALF_UP));
            } else if (i.equals(rcwlBudgetDistributionDTO.getNeedByDateYear())) {
                // 若当前行【占用年份】=A2,则为【申请行总金额】/【占用总时长（月）】*B2
                budgetDistribution.setBudgetDisAmountCal(rcwlBudgetDistributionDTO.getLineAmount().divide(new BigDecimal(rcwlBudgetDistributionDTO.getBudgetDisGap() * rcwlBudgetDistributionDTO.getNeedByDateMonth()), 6, RoundingMode.HALF_UP));
            } else {
                // 前两者不满足，则为【申请行总金额】/【占用总时长（月）】*12
                budgetDistribution.setBudgetDisAmountCal(rcwlBudgetDistributionDTO.getLineAmount().divide(new BigDecimal(rcwlBudgetDistributionDTO.getBudgetDisGap() * 12), 6, RoundingMode.HALF_UP));
            }
            budgetDistributionCreateList.add(budgetDistribution);
        }

        rcwlBudgetDistributionRepository.batchInsert(budgetDistributionCreateList);

        return budgetDistributionCreateList;
    }

}
