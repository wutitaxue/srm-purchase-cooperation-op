package org.srm.purchasecooperation.cux.sinv.infra.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

/**
 * description
 *
 * @author Penguin 2021/04/30 17:16
 */
public interface SinvRcvTrxWaitingAsnStatusMapper {

    List<String> selectRcvTrxWaitingAsnStatus(@Param("asnNum") String asnNum,@Param("tenantId") Long tenantId);
}
