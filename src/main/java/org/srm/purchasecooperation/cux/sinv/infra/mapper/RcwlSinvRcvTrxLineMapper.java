package org.srm.purchasecooperation.cux.sinv.infra.mapper;

import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

/**
 * @description:
 * @author: bin.zhang
 * @createDate: 2021/4/22 17:11
 */
public interface RcwlSinvRcvTrxLineMapper {
    /**
     * 获取质保金比例
     * @param fromPoHeaderId
     * @param fromPoLineId
     * @param tenantId
     * @return
     */
    BigDecimal selectRententionMoneyPercent(@Param("fromPoHeaderId") Long fromPoHeaderId,@Param("fromPoLineId") Long fromPoLineId,@Param("tenantId") Long tenantId);
}
