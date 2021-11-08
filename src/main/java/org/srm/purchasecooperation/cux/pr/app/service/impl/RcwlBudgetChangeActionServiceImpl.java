package org.srm.purchasecooperation.cux.pr.app.service.impl;


import io.choerodon.core.domain.Page;
import io.choerodon.core.exception.CommonException;
import io.choerodon.mybatis.pagehelper.PageHelper;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import org.hzero.core.base.BaseConstants;
import org.hzero.mybatis.domian.Condition;
import org.hzero.mybatis.util.Sqls;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.srm.purchasecooperation.cux.pr.app.service.RcwlBudgetChangeActionService;
import org.srm.purchasecooperation.cux.pr.domain.entity.RcwlBudgetChangeAction;
import org.srm.purchasecooperation.order.domain.entity.RcwlBudgetDistribution;
import org.srm.purchasecooperation.cux.pr.domain.repository.RcwlBudgetChangeActionRepository;
import org.srm.purchasecooperation.order.domain.repository.RcwlBudgetDistributionRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
    private RcwlBudgetDistributionRepository rcwlBudgetDistributionRepository;

    @Override
    public Page<RcwlBudgetChangeAction> selectList(PageRequest pageRequest, RcwlBudgetChangeAction rcwlBudgetChangeAction) {
        return PageHelper.doPageAndSort(pageRequest, () -> rcwlBudgetChangeActionRepository.selectList(rcwlBudgetChangeAction));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createBudgetChangeAction(Long tenantId, List<RcwlBudgetChangeAction> rcwlBudgetChangeActions) {
        if (!CollectionUtils.isEmpty(rcwlBudgetChangeActions)) {
            // 增加一个逻辑,判断当前保存的预算变更数据,是否和原有的预算数据是否一致,一致则直接不保存
            List<RcwlBudgetDistribution> rcwlBudgetDistributions = rcwlBudgetDistributionRepository.selectByCondition(Condition.builder(RcwlBudgetDistribution.class).andWhere(Sqls.custom().andEqualTo(RcwlBudgetDistribution.FIELD_PR_HEADER_ID, rcwlBudgetChangeActions.get(0).getPrHeaderId())
                    .andEqualTo(RcwlBudgetDistribution.FIELD_PR_LINE_ID, rcwlBudgetChangeActions.get(0).getPrLineId()).andEqualTo(RcwlBudgetDistribution.FIELD_TENANT_ID, tenantId)).build());
            Map<Integer, RcwlBudgetDistribution> rcwlBudgetDistributionYearMap = rcwlBudgetDistributions.stream().collect(Collectors.toMap(RcwlBudgetDistribution::getBudgetDisYear, Function.identity()));
            // 如果变更的预算和当前已有的预算、并且年份一致的话,则跳过保存
            if (rcwlBudgetChangeActions.size() == rcwlBudgetDistributions.size()) {
                // 1、年份不相等算变更；2、占用预算不等于行金额算变更；3、找不到对应年的跨年预算算变更；4、对应年的跨年预算金额对应不上算变更
                long notConsistentCount = rcwlBudgetChangeActions.stream().filter(rcwlBudgetChangeAction -> !rcwlBudgetDistributionYearMap.containsKey(rcwlBudgetChangeAction.getBudgetDisYear())
                        || rcwlBudgetChangeAction.getLineAmount().compareTo(rcwlBudgetDistributions.stream().map(RcwlBudgetDistribution::getBudgetDisAmount).reduce(BigDecimal.ZERO, BigDecimal::add)) != 0
                        || ObjectUtils.isEmpty(rcwlBudgetDistributionYearMap.get(rcwlBudgetChangeAction.getBudgetDisYear()))
                        || rcwlBudgetChangeAction.getBudgetDisAmount().compareTo(rcwlBudgetDistributionYearMap.get(rcwlBudgetChangeAction.getBudgetDisYear()).getBudgetDisAmount())!=0).count();
                if (notConsistentCount <= 0) {
                    return;
                }
            }
            // 筛选采购申请行未提交的预算变更数据
            List<RcwlBudgetChangeAction> rcwlBudgetChangeActionsNotEnableds = rcwlBudgetChangeActionRepository.selectByCondition(Condition.builder(RcwlBudgetChangeAction.class).andWhere(Sqls.custom().andEqualTo(RcwlBudgetChangeAction.FIELD_PR_HEADER_ID, rcwlBudgetChangeActions.get(0).getPrHeaderId())
                    .andEqualTo(RcwlBudgetChangeAction.FIELD_PR_LINE_ID, rcwlBudgetChangeActions.get(0).getPrLineId()).andEqualTo(RcwlBudgetChangeAction.FIELD_TENANT_ID, tenantId).andEqualTo(RcwlBudgetChangeAction.FIELD_ENABLED_FLAG, BaseConstants.Flag.NO)).build());
            // 判断跨年分摊金额表中实际分摊金额总值和传入的跨年分摊金额的实际分摊金额总值是否相等,不相等报错
            if (rcwlBudgetChangeActions.get(0).getLineAmount().setScale(RcwlBudgetChangeAction.SIX, RoundingMode.HALF_UP).compareTo(rcwlBudgetChangeActions.stream().map(RcwlBudgetChangeAction::getBudgetDisAmount).reduce(BigDecimal.ZERO, BigDecimal::add)) != 0) {
                throw new CommonException("error.pr.line.amount.budget.error");
            }

            // 筛选budget_group为new的条数
            List<RcwlBudgetChangeAction> rcwlBudgetChangeActionsNew = rcwlBudgetChangeActionsNotEnableds.stream().filter(rcwlBudgetChangeAction -> RcwlBudgetChangeAction.NEW.equals(rcwlBudgetChangeAction.getBudgetGroup())).collect(Collectors.toList());
            // budget_group为new的数据，先全部删除，并将当前预算分摊界面的数据存至scux_rcwl_budget_change_action
            if (!CollectionUtils.isEmpty(rcwlBudgetChangeActionsNew)) {
                rcwlBudgetChangeActionRepository.batchDeleteByPrimaryKey(rcwlBudgetChangeActionsNew);
            }
            rcwlBudgetChangeActions.forEach(rcwlBudgetChangeAction -> {
                rcwlBudgetChangeAction.setBudgetGroup(RcwlBudgetChangeAction.NEW);
                if (CollectionUtils.isEmpty(rcwlBudgetChangeActionsNew) && ObjectUtils.isEmpty(rcwlBudgetChangeAction.getBudgetDisAmount())) {
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
