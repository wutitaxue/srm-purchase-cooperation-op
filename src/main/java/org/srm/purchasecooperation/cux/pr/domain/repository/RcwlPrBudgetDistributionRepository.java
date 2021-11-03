package org.srm.purchasecooperation.cux.pr.domain.repository;

import io.choerodon.core.domain.Page;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import org.hzero.mybatis.base.BaseRepository;
import org.srm.purchasecooperation.cux.pr.api.dto.RcwlBudgetDistributionDTO;
import org.srm.purchasecooperation.cux.pr.domain.entity.RcwlBudgetDistribution;

import java.util.List;

/**
 * 预算分配资源库
 *
 * @author jie.wang05@hand-china.com 2021-10-27 14:49:26
 */
public interface RcwlPrBudgetDistributionRepository extends BaseRepository<RcwlBudgetDistribution> {
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
}
