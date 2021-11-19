package org.srm.purchasecooperation.cux.pr.infra.mapper;

import io.choerodon.mybatis.common.BaseMapper;
import org.srm.purchasecooperation.cux.pr.domain.entity.RcwlBudgetChangeAction;

import java.util.List;

/**
 * 预算变更记录表(RcwlBudgetChangeAction)应用服务
 *
 * @author jie.wang05@hand-china.com
 * @since 2021-11-01 14:58:31
 */
public interface RcwlBudgetChangeActionMapper extends BaseMapper<RcwlBudgetChangeAction> {
    /**
     * 基础查询
     *
     * @param rcwlBudgetChangeAction 查询条件
     * @return 返回值
     */
    List<RcwlBudgetChangeAction> selectList(RcwlBudgetChangeAction rcwlBudgetChangeAction);

    /**
     * 选择申请的最大action_id
     *
     * @param rcwlBudgetChangeAction
     * @return
     */
    List<RcwlBudgetChangeAction> selectMaxPrActionData(RcwlBudgetChangeAction rcwlBudgetChangeAction);
}
