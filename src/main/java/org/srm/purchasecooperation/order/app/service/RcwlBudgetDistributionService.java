package org.srm.purchasecooperation.order.app.service;

import org.srm.purchasecooperation.order.api.dto.RcwlBudgetDistributionDTO;
import org.srm.purchasecooperation.order.domain.entity.RcwlBudgetDistribution;

import java.util.List;

/**
 * 预算分配应用服务
 *
 * @author pengxu.zhi@hand-china.com 2021-10-29 16:56:18
 */
public interface RcwlBudgetDistributionService {
    /**
     * 通过订单行生成跨年预算分摊逻辑
     *
     * @param tenantId
     * @param rcwlBudgetDistributionDTO
     * @return
     */
    List<RcwlBudgetDistribution> selectBudgetDistributionByPoLine(Long tenantId, RcwlBudgetDistributionDTO rcwlBudgetDistributionDTO);
}
