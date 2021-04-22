package org.srm.purchasecooperation.cux.sinv.infra.repository.impl;

import org.springframework.stereotype.Component;
import org.srm.purchasecooperation.cux.sinv.domain.repository.RcwlSinvRcvTrxLineRepository;
import org.srm.purchasecooperation.cux.sinv.infra.mapper.RcwlSinvRcvTrxLineMapper;

import java.math.BigDecimal;

/**
 * @description:
 * @author: bin.zhang
 * @createDate: 2021/4/22 17:09
 */
@Component
public class RcwlSinvRcvTrxLineRepositoryImpl implements RcwlSinvRcvTrxLineRepository {
    private RcwlSinvRcvTrxLineMapper rcwlSinvRcvTrxLineMapper;
    /**
     * 通过订单头行获取合同号，找合同上的质保金比例
     *  @param fromPoHeaderId
     * @param fromPoLineId
     * @param tenantId
     * @return
     */
    @Override
    public BigDecimal selectRententionMoneyPercent(Long fromPoHeaderId, Long fromPoLineId, Long tenantId) {

        return this.rcwlSinvRcvTrxLineMapper.selectRententionMoneyPercent(fromPoHeaderId,fromPoLineId,tenantId);
    }
}
