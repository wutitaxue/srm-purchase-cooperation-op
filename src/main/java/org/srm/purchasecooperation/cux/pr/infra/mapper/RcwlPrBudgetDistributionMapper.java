package org.srm.purchasecooperation.cux.pr.infra.mapper;

import org.srm.purchasecooperation.cux.pr.api.dto.RcwlBudgetDistributionDTO;
import org.srm.purchasecooperation.cux.pr.domain.entity.RcwlBudgetDistribution;
import io.choerodon.mybatis.common.BaseMapper;

import java.util.List;

/**
 * 预算分配Mapper
 *
 * @author jie.wang05@hand-china.com 2021-10-27 14:49:26
 */
public interface RcwlPrBudgetDistributionMapper extends BaseMapper<RcwlBudgetDistribution> {
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
}
