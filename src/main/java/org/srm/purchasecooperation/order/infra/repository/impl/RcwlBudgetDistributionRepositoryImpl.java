package org.srm.purchasecooperation.order.infra.repository.impl;

import org.hzero.mybatis.base.impl.BaseRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.srm.purchasecooperation.order.api.dto.RcwlBudgetDistributionDTO;
import org.srm.purchasecooperation.order.domain.entity.RcwlBudgetDistribution;
import org.srm.purchasecooperation.order.domain.repository.RcwlBudgetDistributionRepository;
import org.srm.purchasecooperation.order.infra.mapper.RcwlBudgetDistributionMapper;

import java.util.List;

/**
 * 预算分配 资源库实现
 *
 * @author pengxu.zhi@hand-china.com 2021-10-29 16:56:18
 */
@Component
public class RcwlBudgetDistributionRepositoryImpl extends BaseRepositoryImpl<RcwlBudgetDistribution> implements RcwlBudgetDistributionRepository {
    @Autowired
    private RcwlBudgetDistributionMapper rcwlBudgetDistributionMapper;

    @Override
    public List<RcwlBudgetDistributionDTO> selectBudgetDistributionByPrLine(Long tenantId, RcwlBudgetDistributionDTO rcwlBudgetDistributionDTO) {
        rcwlBudgetDistributionDTO.setTenantId(tenantId);
        return rcwlBudgetDistributionMapper.selectBudgetDistributionByPrLine(rcwlBudgetDistributionDTO);
    }

    @Override
    public List<RcwlBudgetDistributionDTO> selectBudgetDistribution(Long tenantId,
                                                                    RcwlBudgetDistributionDTO rcwlBudgetDistributionDTO) {
        rcwlBudgetDistributionDTO.setTenantId(tenantId);
        return rcwlBudgetDistributionMapper.selectBudgetDistribution(rcwlBudgetDistributionDTO);
    }

    @Override
    public List<RcwlBudgetDistribution> selectBudgetDistributionNotAcrossYear(Long tenantId, RcwlBudgetDistributionDTO rcwlBudgetDistributionDTO) {
        rcwlBudgetDistributionDTO.setTenantId(tenantId);
        return rcwlBudgetDistributionMapper.selectBudgetDistributionNotAcrossYear(rcwlBudgetDistributionDTO);
    }

    @Override
    public void deleteBudgetDistributionNotAcrossYear(Long tenantId, RcwlBudgetDistributionDTO rcwlBudgetDistributionDTO) {
        rcwlBudgetDistributionDTO.setTenantId(tenantId);
        rcwlBudgetDistributionMapper.deleteBudgetDistributionNotAcrossYear(rcwlBudgetDistributionDTO);
    }
}
