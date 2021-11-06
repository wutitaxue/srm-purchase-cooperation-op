package org.srm.purchasecooperation.cux.pr.infra.repository.impl;

import org.apache.commons.collections.CollectionUtils;
import org.hzero.mybatis.base.impl.BaseRepositoryImpl;
import org.springframework.stereotype.Component;
import org.srm.purchasecooperation.cux.pr.domain.entity.RcwlBudgetChangeAction;
import org.srm.purchasecooperation.cux.pr.domain.repository.RcwlBudgetChangeActionRepository;
import org.srm.purchasecooperation.cux.pr.infra.mapper.RcwlBudgetChangeActionMapper;

import javax.annotation.Resource;
import java.util.List;

/**
 * 预算变更记录表(RcwlBudgetChangeAction)资源库
 *
 * @author jie.wang05@hand-china.com
 * @since 2021-11-01 14:58:31
 */
@Component
public class RcwlBudgetChangeActionRepositoryImpl extends BaseRepositoryImpl<RcwlBudgetChangeAction> implements RcwlBudgetChangeActionRepository {
    @Resource
    private RcwlBudgetChangeActionMapper rcwlBudgetChangeActionMapper;

    @Override
    public List<RcwlBudgetChangeAction> selectList(RcwlBudgetChangeAction rcwlBudgetChangeAction) {
        return rcwlBudgetChangeActionMapper.selectList(rcwlBudgetChangeAction);
    }

    @Override
    public RcwlBudgetChangeAction selectByPrimary(Long budgetChangeId) {
        RcwlBudgetChangeAction rcwlBudgetChangeAction = new RcwlBudgetChangeAction();
        rcwlBudgetChangeAction.setBudgetChangeId(budgetChangeId);
        List<RcwlBudgetChangeAction> rcwlBudgetChangeActions = rcwlBudgetChangeActionMapper.selectList(rcwlBudgetChangeAction);
        if (CollectionUtils.isEmpty(rcwlBudgetChangeActions)) {
            return null;
        }
        return rcwlBudgetChangeActions.get(0);
    }

    @Override
    public List<RcwlBudgetChangeAction> selectMaxPrActionData(RcwlBudgetChangeAction rcwlBudgetChangeAction) {
        return rcwlBudgetChangeActionMapper.selectMaxPrActionData(rcwlBudgetChangeAction);
    }

}
