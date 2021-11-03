package org.srm.purchasecooperation.cux.pr.app.service.impl;

import io.choerodon.core.domain.Page;
import io.choerodon.core.exception.CommonException;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import net.sf.cglib.beans.BeanCopier;
import org.hzero.core.base.BaseConstants;
import org.hzero.mybatis.domian.Condition;
import org.hzero.mybatis.util.Sqls;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.srm.purchasecooperation.cux.pr.api.dto.RcwlBudgetDistributionDTO;
import org.srm.purchasecooperation.cux.pr.app.service.RcwlBudgetDistributionService;
import org.springframework.stereotype.Service;
import org.srm.purchasecooperation.cux.pr.domain.entity.RcwlBudgetDistribution;
import org.srm.purchasecooperation.cux.pr.domain.repository.RCWLPrLineRepository;
import org.srm.purchasecooperation.cux.pr.domain.repository.RcwlBudgetDistributionRepository;
import org.srm.purchasecooperation.finance.domain.entity.InvoiceUpdateRules;
import org.srm.purchasecooperation.pr.domain.repository.PrLineRepository;
import org.srm.purchasecooperation.pr.domain.vo.PrLineVO;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Stream;

/**
 * 预算分配应用服务默认实现
 *
 * @author jie.wang05@hand-china.com 2021-10-27 14:49:26
 */
@Service
public class RcwlBudgetDistributionServiceImpl implements RcwlBudgetDistributionService {
    @Autowired
    private RcwlBudgetDistributionRepository rcwlBudgetDistributionRepository;

    @Override
    public List<RcwlBudgetDistributionDTO> selectBudgetDistributionByPrLine(Long tenantId, RcwlBudgetDistributionDTO rcwlBudgetDistributionDTO) {
        List<RcwlBudgetDistributionDTO> rcwlBudgetDistributionResults = new ArrayList<>();
        // 根据采购申请头、行id计算跨年预算的值
        List<RcwlBudgetDistributionDTO> rcwlBudgetDistributionDTOS = rcwlBudgetDistributionRepository.selectBudgetDistributionByPrLine(tenantId, rcwlBudgetDistributionDTO);
        rcwlBudgetDistributionDTOS.forEach(itemLine -> {
            List<Integer> yearPrLineYears = new ArrayList<>(Integer.parseInt(String.valueOf(itemLine.getNeededDateYear() - itemLine.getAttributeDate1Year() + 1)));
            for (Integer i = itemLine.getAttributeDate1Year(); i <= itemLine.getNeededDateYear(); i++) {
                yearPrLineYears.add(i);
            }
            rcwlBudgetDistributionDTO.setBudgetDisYears(yearPrLineYears);
        });
        // 根据采购申请头、行id和申请行的年份集合去查询跨年预算的值
        List<RcwlBudgetDistributionDTO> rcwlBudgetDistributionRealValues =
                rcwlBudgetDistributionRepository.selectBudgetDistribution(tenantId, rcwlBudgetDistributionDTO);
        rcwlBudgetDistributionDTOS.forEach(itemLine -> {
            for (Integer i = itemLine.getAttributeDate1Year(); i <= itemLine.getNeededDateYear(); i++) {
                RcwlBudgetDistributionDTO rcwlBudgetDistributionResult = new RcwlBudgetDistributionDTO();
                BeanCopier beanCopier = BeanCopier.create(RcwlBudgetDistributionDTO.class, RcwlBudgetDistributionDTO.class, false);
                beanCopier.copy(itemLine, rcwlBudgetDistributionResult, null);
                rcwlBudgetDistributionResult.setBudgetDisYear(i);
                if (i.equals(itemLine.getAttributeDate1Year())) {
                    // 当前行【占用年份】=A1，则为【申请行总金额】/【占用总时长（月）】*【12-B1】
                    rcwlBudgetDistributionResult.setAutoCalculateBudgetDisAmount(rcwlBudgetDistributionResult.getLineAmount().divide(new BigDecimal(rcwlBudgetDistributionResult.getBudgetDisGap() * (12 - rcwlBudgetDistributionResult.getAttributeDate1Month())), 6, RoundingMode.HALF_UP));
                } else if (i.equals(itemLine.getNeededDateYear())) {
                    // 若当前行【占用年份】=A2,则为【申请行总金额】/【占用总时长（月）】*B2
                    rcwlBudgetDistributionResult.setAutoCalculateBudgetDisAmount(rcwlBudgetDistributionResult.getLineAmount().divide(new BigDecimal(rcwlBudgetDistributionResult.getBudgetDisGap() * rcwlBudgetDistributionResult.getNeededDateMonth()), 6, RoundingMode.HALF_UP));
                } else {
                    // 前两者不满足，则为【申请行总金额】/【占用总时长（月）】*12
                    rcwlBudgetDistributionResult.setAutoCalculateBudgetDisAmount(rcwlBudgetDistributionResult.getLineAmount().divide(new BigDecimal(rcwlBudgetDistributionResult.getBudgetDisGap() * 12), 6, RoundingMode.HALF_UP));
                }
                // 根据采购申请头、行id和申请行的年份去查询跨年预算的值
                if (!CollectionUtils.isEmpty(rcwlBudgetDistributionRealValues)) {
                    Integer finalI = i;
                    BigDecimal budgetDisAmount = rcwlBudgetDistributionRealValues.stream().filter(rcwlBudgetDistributionRealValue -> rcwlBudgetDistributionDTO.getPrHeaderId().equals(rcwlBudgetDistributionRealValue.getPrHeaderId()) && rcwlBudgetDistributionDTO.getPrLineId().equals(rcwlBudgetDistributionRealValue.getPrLineId()) && finalI.equals(rcwlBudgetDistributionRealValue.getBudgetDisYear())).findFirst().orElse(new RcwlBudgetDistributionDTO()).getBudgetDisAmount();
                    rcwlBudgetDistributionResult.setBudgetDisAmount(budgetDisAmount);
                }
                rcwlBudgetDistributionResults.add(rcwlBudgetDistributionResult);
            }
        });
        return rcwlBudgetDistributionResults;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<RcwlBudgetDistribution> createBudgetDistributions(Long tenantId, List<RcwlBudgetDistributionDTO> rcwlBudgetDistributionDTOS) {
        if (!CollectionUtils.isEmpty(rcwlBudgetDistributionDTOS)) {
            // 查询已经存在的跨年预算数据
            List<RcwlBudgetDistribution> budgetDistributions =
                    rcwlBudgetDistributionRepository.selectByCondition(Condition.builder(RcwlBudgetDistribution.class).andWhere(Sqls.custom().andEqualTo(RcwlBudgetDistribution.FIELD_TENANT_ID, tenantId).andEqualTo(RcwlBudgetDistribution.FIELD_PR_HEADER_ID, rcwlBudgetDistributionDTOS.get(0).getPrHeaderId()).andEqualTo(RcwlBudgetDistribution.FIELD_PR_LINE_ID, rcwlBudgetDistributionDTOS.get(0).getPrLineId())).orderByAsc(RcwlBudgetDistribution.FIELD_BUDGET_DIS_YEAR).build());
            // 记录最后要插入/更新的跨年预算数据
            List<RcwlBudgetDistribution> changeBudgetDistributions = new ArrayList<>(rcwlBudgetDistributionDTOS.size());
            // 转换数据
            rcwlBudgetDistributionDTOS.forEach(rcwlBudgetDistributionDTO -> {
                // 跨年预算未保存过,占用金额去系统分摊金额;保存过,则直接取实际分摊金额
                changeBudgetDistributions.add(RcwlBudgetDistribution.builder().prHeaderId(rcwlBudgetDistributionDTO.getPrHeaderId()).prLineId(rcwlBudgetDistributionDTO.getPrLineId()).budgetDisGap(rcwlBudgetDistributionDTO.getBudgetDisGap()).budgetDisYear(rcwlBudgetDistributionDTO.getBudgetDisYear()).budgetDisAmount(CollectionUtils.isEmpty(budgetDistributions)?rcwlBudgetDistributionDTO.getAutoCalculateBudgetDisAmount():rcwlBudgetDistributionDTO.getBudgetDisAmount()).tenantId(tenantId).build());
            });
            // 总体逻辑:已有跨年预算,删除->重新插入.  对于校验的部分,提交那里会限制住
            if (!CollectionUtils.isEmpty(budgetDistributions)) {
                // 判断系统分摊金额总值和实际分摊金额总值是否相等,不相等---可能是人为调整有问题,或者金额变化了
                if (!rcwlBudgetDistributionDTOS.stream().map(RcwlBudgetDistributionDTO::getAutoCalculateBudgetDisAmount).reduce(BigDecimal.ZERO, BigDecimal::add).equals(rcwlBudgetDistributionDTOS.stream().map(RcwlBudgetDistributionDTO::getBudgetDisAmount).reduce(BigDecimal.ZERO, BigDecimal::add))) {
                    // 判断跨年分摊金额表中实际分摊金额总值和传入的跨年分摊金额的实际分摊金额总值是否相等,不相等说明人为调整有误,报错
                    if (!rcwlBudgetDistributionDTOS.stream().map(RcwlBudgetDistributionDTO::getBudgetDisAmount).reduce(BigDecimal.ZERO, BigDecimal::add).equals(budgetDistributions.stream().map(RcwlBudgetDistribution::getBudgetDisAmount).reduce(BigDecimal.ZERO, BigDecimal::add))) {
                        throw new CommonException("error.pr.line.amount.budget.error");
                    } else {
                        rcwlBudgetDistributionRepository.batchDeleteByPrimaryKey(budgetDistributions);
                    }
                }
                // 跨年分摊金额表中的数量和传入的跨年分摊金额参数数量不一致,或者两者第一年年份不一致,说明需求日期发生了变化
                if (budgetDistributions.size() != rcwlBudgetDistributionDTOS.size() || !budgetDistributions.get(0).getBudgetDisYear().equals(rcwlBudgetDistributionDTOS.get(0).getBudgetDisYear())) {
                    rcwlBudgetDistributionRepository.batchDeleteByPrimaryKey(budgetDistributions);
                }
            }
            rcwlBudgetDistributionRepository.batchInsert(changeBudgetDistributions);
            return changeBudgetDistributions;
        }
        return null;
    }
}
