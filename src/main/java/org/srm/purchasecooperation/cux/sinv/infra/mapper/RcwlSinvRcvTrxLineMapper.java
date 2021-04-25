package org.srm.purchasecooperation.cux.sinv.infra.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import org.srm.purchasecooperation.sinv.api.dto.SinvRcvTrxLineDTO;
import org.srm.purchasecooperation.sinv.domain.entity.SinvRcvTrxLine;
import org.srm.purchasecooperation.sinv.infra.mapper.SinvRcvTrxLineMapper;
import org.srm.web.annotation.Tenant;
import org.srm.web.dynamic.ExtendMapper;

import java.math.BigDecimal;
import java.util.List;

/**
 * @description:
 * @author: bin.zhang
 * @createDate: 2021/4/22 17:11
 */
@Component
@Tenant("SRM-RCWL")
public interface RcwlSinvRcvTrxLineMapper extends SinvRcvTrxLineMapper, ExtendMapper<SinvRcvTrxLine> {
    /**
     * 获取质保金比例
     * @param fromPoHeaderId
     * @param fromPoLineId
     * @param tenantId
     * @return
     */
    BigDecimal selectRententionMoneyPercent(@Param("fromPoHeaderId") Long fromPoHeaderId,@Param("fromPoLineId") Long fromPoLineId,@Param("tenantId") Long tenantId);

    /**
     * 将质保金和收货人插表
     * @param rcvTrxLineId
     * @param retentionMoney
     * @param attributeBigint2
     * @param tenantId
     */
    void insertRetentionMoneyAndReceiver(@Param("rcvTrxLineId")Long rcvTrxLineId, @Param("retentionMoney")BigDecimal retentionMoney, @Param("attributeBigint2")Long attributeBigint2, @Param("tenantId")Long tenantId);

    /**
     * 收货事务行明细查询
     * @param tenantId
     * @param rcvTrxHeaderId
     * @return
     */
    @Override
    List<SinvRcvTrxLineDTO> listRcvTrxLineDetail(@Param("tenantId") Long tenantId, @Param("rcvTrxHeaderId") Long rcvTrxHeaderId);

}
