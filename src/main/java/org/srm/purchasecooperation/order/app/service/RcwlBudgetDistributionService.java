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

    /**
     * 批量更新预算分配
     *
     * @param tenantId
     * @param rcwlBudgetDistributionList
     * @return
     */
    List<RcwlBudgetDistribution> batchUpdateBudgetDistributions(Long tenantId, List<RcwlBudgetDistribution> rcwlBudgetDistributionList);

    /**
     * 通过采购申请行生成跨年预算分摊逻辑
     *
     * @param tenantId
     * @param rcwlBudgetDistributionDTO
     * @param batchFlag  批量计算标识(用于保存计算,batchFlag为ture表示批量计算)
     * @return
     */
    List<RcwlBudgetDistributionDTO> selectBudgetDistributionByPrLine (Long tenantId, RcwlBudgetDistributionDTO rcwlBudgetDistributionDTO, Boolean batchFlag);

    /**
     * 跨年预算分摊保存/更新
     *
     * @param tenantId
     * @param rcwlBudgetDistributionDTOS  跨年预算分摊集合
     */
    List<RcwlBudgetDistribution> createBudgetDistributions(Long tenantId, List<RcwlBudgetDistributionDTO> rcwlBudgetDistributionDTOS);
}
