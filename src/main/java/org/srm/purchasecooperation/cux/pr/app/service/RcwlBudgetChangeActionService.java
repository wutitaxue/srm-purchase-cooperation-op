package org.srm.purchasecooperation.cux.pr.app.service;

import io.choerodon.core.domain.Page;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import org.srm.purchasecooperation.cux.pr.domain.entity.RcwlBudgetChangeAction;

import java.util.List;

/**
 * 预算变更记录表(RcwlBudgetChangeAction)应用服务
 *
 * @author jie.wang05@hand-china.com
 * @since 2021-11-01 14:58:31
 */
public interface RcwlBudgetChangeActionService {

    /**
     * 查询数据
     *
     * @param pageRequest             分页参数
     * @param rcwlBudgetChangeActions 查询条件
     * @return 返回值
     */
    Page<RcwlBudgetChangeAction> selectList(PageRequest pageRequest, RcwlBudgetChangeAction rcwlBudgetChangeActions);

    /**
     * 保存数据
     *
     * @param rcwlBudgetChangeActions 数据
     */
    void createBudgetChangeAction(Long tenantId, List<RcwlBudgetChangeAction> rcwlBudgetChangeActions);

}
