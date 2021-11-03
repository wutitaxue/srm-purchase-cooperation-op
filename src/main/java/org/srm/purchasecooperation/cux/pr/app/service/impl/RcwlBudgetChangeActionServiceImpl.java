package org.srm.purchasecooperation.cux.pr.app.service.impl;


import io.choerodon.core.domain.Page;
import io.choerodon.core.exception.CommonException;
import io.choerodon.mybatis.pagehelper.PageHelper;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import org.hzero.core.base.BaseConstants;
import org.hzero.mybatis.domian.Condition;
import org.hzero.mybatis.util.Sqls;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.srm.purchasecooperation.cux.pr.app.service.RcwlBudgetChangeActionService;
import org.springframework.stereotype.Service;
import org.srm.purchasecooperation.cux.pr.domain.entity.RcwlBudgetChangeAction;
import org.srm.purchasecooperation.cux.pr.domain.entity.RcwlBudgetDistribution;
import org.srm.purchasecooperation.cux.pr.domain.repository.RcwlBudgetChangeActionRepository;
import org.srm.purchasecooperation.cux.pr.domain.repository.RcwlPrBudgetDistributionRepository;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 预算变更记录表(RcwlBudgetChangeAction)应用服务
 *
 * @author jie.wang05@hand-china.com
 * @since 2021-11-01 14:58:31
 */
@Service
public class RcwlBudgetChangeActionServiceImpl implements RcwlBudgetChangeActionService {
    @Autowired
    private RcwlBudgetChangeActionRepository rcwlBudgetChangeActionRepository;
    @Autowired
    private RcwlPrBudgetDistributionRepository rcwlPrBudgetDistributionRepository;

    @Override
    public Page<RcwlBudgetChangeAction> selectList(PageRequest pageRequest, RcwlBudgetChangeAction rcwlBudgetChangeAction) {
        return PageHelper.doPageAndSort(pageRequest, () -> rcwlBudgetChangeActionRepository.selectList(rcwlBudgetChangeAction));
    }

    @Override
    public void createBudgetChangeAction(Long tenantId, List<RcwlBudgetChangeAction> rcwlBudgetChangeActions) {
        if (!CollectionUtils.isEmpty(rcwlBudgetChangeActions)) {
            // 增加一个逻辑,判断当前保存的预算变更数据,是否和原有的预算数据是否一致,一致则直接不保存
            List<RcwlBudgetDistribution> rcwlBudgetDistributions = rcwlPrBudgetDistributionRepository.selectByCondition(Condition.builder(RcwlBudgetDistribution.class).andWhere(Sqls.custom().andEqualTo(RcwlBudgetDistribution.FIELD_PR_HEADER_ID, rcwlBudgetChangeActions.get(0).getPrHeaderId())
                    .andEqualTo(RcwlBudgetDistribution.FIELD_PR_LINE_ID, rcwlBudgetChangeActions.get(0).getPrLineId()).andEqualTo(RcwlBudgetDistribution.FIELD_TENANT_ID, tenantId)).build());
            Map<Integer, RcwlBudgetDistribution> rcwlBudgetDistributionYearMap = rcwlBudgetDistributions.stream().collect(Collectors.toMap(RcwlBudgetDistribution::getBudgetDisYear, Function.identity()));
            if (rcwlBudgetChangeActions.size() == rcwlBudgetDistributions.size()) {
                long notConsistentCount = rcwlBudgetChangeActions.stream().filter(rcwlBudgetChangeAction -> !rcwlBudgetChangeAction.getBudgetDisAmount().equals(rcwlBudgetDistributionYearMap.get(rcwlBudgetChangeAction.getBudgetDisYear()).getBudgetDisAmount())
                        || !rcwlBudgetChangeAction.getBudgetDisGap().equals(rcwlBudgetDistributionYearMap.get(rcwlBudgetChangeAction.getBudgetDisGap()).getBudgetDisAmount())).count();
                if (notConsistentCount <= 0) {
                    return;
                }
            }
            // 筛选采购申请行未提交的预算变更数据
            List<RcwlBudgetChangeAction> rcwlBudgetChangeActionsNotEnableds = rcwlBudgetChangeActionRepository.selectByCondition(Condition.builder(RcwlBudgetChangeAction.class).andWhere(Sqls.custom().andEqualTo(RcwlBudgetChangeAction.FIELD_PR_HEADER_ID, rcwlBudgetChangeActions.get(0).getPrHeaderId())
                    .andEqualTo(RcwlBudgetChangeAction.FIELD_PR_LINE_ID, rcwlBudgetChangeActions.get(0).getPrLineId()).andEqualTo(RcwlBudgetChangeAction.FIELD_TENANT_ID, tenantId).andEqualTo(RcwlBudgetChangeAction.FIELD_ENABLED_FLAG, BaseConstants.Flag.NO)).build());
            // 判断系统分摊金额总值和实际分摊金额总值是否相等,不相等---可能是人为调整有问题,或者金额变化了
            if (!rcwlBudgetChangeActions.stream().map(RcwlBudgetChangeAction::getAutoCalculateBudgetDisAmount).reduce(BigDecimal.ZERO, BigDecimal::add).equals(rcwlBudgetChangeActions.stream().map(RcwlBudgetChangeAction::getBudgetDisAmount).reduce(BigDecimal.ZERO, BigDecimal::add))) {
                // 判断跨年分摊金额表中实际分摊金额总值和传入的跨年分摊金额的实际分摊金额总值是否相等,不相等说明人为调整有误,报错
                if (!rcwlBudgetChangeActions.stream().map(RcwlBudgetChangeAction::getBudgetDisAmount).reduce(BigDecimal.ZERO, BigDecimal::add).equals(rcwlBudgetChangeActionsNotEnableds.stream().map(RcwlBudgetChangeAction::getBudgetDisAmount).reduce(BigDecimal.ZERO, BigDecimal::add))) {
                    throw new CommonException("error.pr.line.amount.budget.error");
                }
            }
            // 筛选budget_group为old的条数
            long oldCount = rcwlBudgetChangeActionsNotEnableds.stream().filter(rcwlBudgetChangeAction -> RcwlBudgetChangeAction.OLD.equals(rcwlBudgetChangeAction.getBudgetGroup())).count();
            // budget_group为old的数据，若存在，则不操作，若不存在，则将scux_rcwl_budget_distribution表中的pr_header_id+pr_line_id的数据写入scux_rcwl_budget_change_action表，budget_group为old
            if (oldCount <= 0) {
                List<RcwlBudgetChangeAction> rcwlBudgetChangeActionsOld = Collections.singletonList((RcwlBudgetChangeAction) rcwlBudgetDistributions);
                rcwlBudgetChangeActionsOld.forEach(rcwlBudgetChangeAction -> rcwlBudgetChangeAction.setBudgetGroup(RcwlBudgetChangeAction.OLD));
                rcwlBudgetChangeActionRepository.batchInsertSelective(rcwlBudgetChangeActionsOld);
            }
            // 筛选budget_group为new的条数
            List<RcwlBudgetChangeAction> rcwlBudgetChangeActionsNew = rcwlBudgetChangeActionsNotEnableds.stream().filter(rcwlBudgetChangeAction -> RcwlBudgetChangeAction.NEW.equals(rcwlBudgetChangeAction.getBudgetGroup())).collect(Collectors.toList());
            // budget_group为new的数据，先全部删除，并将当前预算分摊界面的数据存至scux_rcwl_budget_change_action
            if (!CollectionUtils.isEmpty(rcwlBudgetChangeActionsNew)) {
                rcwlBudgetChangeActionRepository.batchDeleteByPrimaryKey(rcwlBudgetChangeActionsNew);
            }
            rcwlBudgetChangeActions.forEach(rcwlBudgetChangeAction -> {
                rcwlBudgetChangeAction.setBudgetGroup(RcwlBudgetChangeAction.NEW);
                if (CollectionUtils.isEmpty(rcwlBudgetChangeActionsNew)) {
                    // 之前没有变更预算数据,则实际分摊金额=系统分摊金额
                    rcwlBudgetChangeAction.setBudgetDisAmount(rcwlBudgetChangeAction.getAutoCalculateBudgetDisAmount());
                }
            });
            rcwlBudgetChangeActionRepository.batchInsertSelective(rcwlBudgetChangeActions);
        }
    }

    public void saveData(List<RcwlBudgetChangeAction> rcwlBudgetChangeActions) {
        rcwlBudgetChangeActions.forEach(item -> {
            if (item.getBudgetChangeId() == null) {
                rcwlBudgetChangeActionRepository.insertSelective(item);
            } else {
                rcwlBudgetChangeActionRepository.updateByPrimaryKeySelective(item);
            }
        });
    }
}
