package org.srm.purchasecooperation.order.domain.repository;

import org.hzero.mybatis.base.BaseRepository;
import org.srm.purchasecooperation.order.api.dto.RcwlBudgetDistributionDTO;
import org.srm.purchasecooperation.order.domain.entity.RcwlBudgetDistribution;


import java.util.List;

/**
 * 预算分配资源库
 *
 * @author pengxu.zhi@hand-china.com 2021-10-29 16:56:18
 */
public interface RcwlBudgetDistributionRepository extends BaseRepository<RcwlBudgetDistribution> {
    /**
     * 通过采购申请行生成跨年预算分摊逻辑
     *
     * @param tenantId
     * @param rcwlBudgetDistributionDTO
     * @return
     */
    List<RcwlBudgetDistributionDTO> selectBudgetDistributionByPrLine (Long tenantId, RcwlBudgetDistributionDTO rcwlBudgetDistributionDTO);

    /**
     * 分页查询采购申请跨年预算分摊
     *
     * @param rcwlBudgetDistributionDTO
     * @param tenantId
     * @return
     */
    List<RcwlBudgetDistributionDTO> selectBudgetDistribution (Long tenantId, RcwlBudgetDistributionDTO rcwlBudgetDistributionDTO);

    /**
     * 查询采购申请中未跨年的预算分摊
     *
     * @param tenantId
     * @param rcwlBudgetDistributionDTO
     * @return 采购申请中未跨年的预算分摊
     */
    List<RcwlBudgetDistribution> selectBudgetDistributionNotAcrossYear(Long tenantId, RcwlBudgetDistributionDTO rcwlBudgetDistributionDTO);

    /**
     * 删除采购申请中的预算分摊
     *
     * @param tenantId
     * @param rcwlBudgetDistributionDTO
     * @return 采购申请中未跨年的预算分摊
     */
    void deleteBudgetDistributionNotAcrossYear(Long tenantId, RcwlBudgetDistributionDTO rcwlBudgetDistributionDTO);
}
