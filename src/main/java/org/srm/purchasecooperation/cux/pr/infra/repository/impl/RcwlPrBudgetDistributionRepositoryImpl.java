package org.srm.purchasecooperation.cux.pr.infra.repository.impl;

import org.hzero.mybatis.base.impl.BaseRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.srm.purchasecooperation.cux.pr.api.dto.RcwlBudgetDistributionDTO;
import org.srm.purchasecooperation.cux.pr.domain.entity.RcwlBudgetDistribution;
import org.srm.purchasecooperation.cux.pr.domain.repository.RcwlPrBudgetDistributionRepository;
import org.springframework.stereotype.Component;
import org.srm.purchasecooperation.cux.pr.infra.mapper.RcwlPrBudgetDistributionMapper;

import java.util.List;

/**
 * 预算分配 资源库实现
 *
 * @author jie.wang05@hand-china.com 2021-10-27 14:49:26
 */
@Component
public class RcwlPrBudgetDistributionRepositoryImpl extends BaseRepositoryImpl<RcwlBudgetDistribution> implements RcwlPrBudgetDistributionRepository {

    @Autowired
    private RcwlPrBudgetDistributionMapper rcwlPrBudgetDistributionMapper;

    @Override
    public List<RcwlBudgetDistributionDTO> selectBudgetDistributionByPrLine(Long tenantId, RcwlBudgetDistributionDTO rcwlBudgetDistributionDTO) {
        rcwlBudgetDistributionDTO.setTenantId(tenantId);
        return rcwlPrBudgetDistributionMapper.selectBudgetDistributionByPrLine(rcwlBudgetDistributionDTO);
    }

    @Override
    public List<RcwlBudgetDistributionDTO> selectBudgetDistribution(Long tenantId,
                                                                 RcwlBudgetDistributionDTO rcwlBudgetDistributionDTO) {
        rcwlBudgetDistributionDTO.setTenantId(tenantId);
        return rcwlPrBudgetDistributionMapper.selectBudgetDistribution(rcwlBudgetDistributionDTO);
    }

    @Override
    public List<RcwlBudgetDistribution> selectBudgetDistributionNotAcrossYear(Long tenantId, RcwlBudgetDistributionDTO rcwlBudgetDistributionDTO) {
        rcwlBudgetDistributionDTO.setTenantId(tenantId);
        return rcwlPrBudgetDistributionMapper.selectBudgetDistributionNotAcrossYear(rcwlBudgetDistributionDTO);
    }
}
