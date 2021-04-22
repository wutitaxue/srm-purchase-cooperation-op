package org.srm.purchasecooperation.cux.sinv.domain.repository;

import java.math.BigDecimal;

/**
 * @description:
 * @author: bin.zhang
 * @createDate: 2021/4/22 17:05
 */
public interface RcwlSinvRcvTrxLineRepository {
    /**
     * 通过订单头行获取合同号，找合同上的质保金比例
     * @param fromPoHeaderId
     * @param fromPoLineId
     * @param tenantId
     * @return
     */
    BigDecimal selectRententionMoneyPercent(Long fromPoHeaderId, Long fromPoLineId, Long tenantId);
}
