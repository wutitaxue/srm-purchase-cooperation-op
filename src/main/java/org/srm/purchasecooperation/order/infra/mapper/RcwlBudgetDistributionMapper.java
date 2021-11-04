package org.srm.purchasecooperation.order.infra.mapper;

import io.choerodon.mybatis.common.BaseMapper;
import org.srm.purchasecooperation.order.api.dto.RcwlBudgetDistributionDTO;
import org.srm.purchasecooperation.order.domain.entity.RcwlBudgetDistribution;

import java.util.List;

/**
 * 预算分配Mapper
 *
 * @author pengxu.zhi@hand-china.com 2021-10-29 16:56:18
 */
public interface RcwlBudgetDistributionMapper extends BaseMapper<RcwlBudgetDistribution> {
    /**
     * 通过采购申请行生成跨年预算分摊逻辑
     *
     * @param rcwlBudgetDistributionDTO 跨年预算分摊查询参数
     * @return 跨年预算分摊集合
     */
    List<RcwlBudgetDistributionDTO> selectBudgetDistributionByPrLine (RcwlBudgetDistributionDTO rcwlBudgetDistributionDTO);

    /**
     * 查询采购申请跨年预算分摊
     *
     * @param rcwlBudgetDistributionDTO 跨年预算分摊查询参数
     * @return 跨年预算分摊集合
     */
    List<RcwlBudgetDistributionDTO> selectBudgetDistribution (RcwlBudgetDistributionDTO rcwlBudgetDistributionDTO);

    /**
     * 查询采购申请中未跨年的预算分摊
     *
     * @param rcwlBudgetDistributionDTO
     * @return 采购申请中未跨年的预算分摊
     */
    List<RcwlBudgetDistribution> selectBudgetDistributionNotAcrossYear(RcwlBudgetDistributionDTO rcwlBudgetDistributionDTO);

    /**
     * 查询订单号行号
     *
     * @param
     * @return 订单号行号拼接
     */
    String selectPoAndPoLineNum(Long tenantId, Long poHeaderId, Long poLineId);
}
