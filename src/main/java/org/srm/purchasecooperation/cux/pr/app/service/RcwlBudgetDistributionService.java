package org.srm.purchasecooperation.cux.pr.app.service;

import io.choerodon.core.domain.Page;
import org.srm.purchasecooperation.cux.pr.api.dto.RcwlBudgetDistributionDTO;
import org.srm.purchasecooperation.cux.pr.domain.entity.RcwlBudgetDistribution;

import java.util.List;

/**
 * 预算分配应用服务
 *
 * @author jie.wang05@hand-china.com 2021-10-27 14:49:26
 */
public interface RcwlBudgetDistributionService {
    /**
     * 通过采购申请行生成跨年预算分摊逻辑
     *
     * @param tenantId
     * @param rcwlBudgetDistributionDTO
     * @return
     */
    List<RcwlBudgetDistributionDTO> selectBudgetDistributionByPrLine (Long tenantId, RcwlBudgetDistributionDTO rcwlBudgetDistributionDTO);

    /**
     * 跨年预算分摊保存/更新
     *
     * @param tenantId
     * @param rcwlBudgetDistributionDTOS  跨年预算分摊集合
     */
    List<RcwlBudgetDistribution> createBudgetDistributions(Long tenantId, List<RcwlBudgetDistributionDTO> rcwlBudgetDistributionDTOS);
}
