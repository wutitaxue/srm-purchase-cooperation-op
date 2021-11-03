package org.srm.purchasecooperation.cux.pr.domain.repository;

import org.hzero.mybatis.base.BaseRepository;
import org.srm.purchasecooperation.cux.pr.domain.entity.RcwlBudgetChangeAction;

import java.util.List;

/**
 * 预算变更记录表(RcwlBudgetChangeAction)资源库
 *
 * @author jie.wang05@hand-china.com
 * @since 2021-11-01 14:58:31
 */
public interface RcwlBudgetChangeActionRepository extends BaseRepository<RcwlBudgetChangeAction> {
    /**
     * 查询
     *
     * @param rcwlBudgetChangeAction 查询条件
     * @return 返回值
     */
    List<RcwlBudgetChangeAction> selectList(RcwlBudgetChangeAction rcwlBudgetChangeAction);

    /**
     * 根据主键查询（可关联表）
     *
     * @param budgetChangeId 主键
     * @return 返回值
     */
    RcwlBudgetChangeAction selectByPrimary(Long budgetChangeId);
}
