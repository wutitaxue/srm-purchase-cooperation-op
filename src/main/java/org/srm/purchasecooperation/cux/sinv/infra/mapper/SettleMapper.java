package org.srm.purchasecooperation.cux.sinv.infra.mapper;

import org.apache.ibatis.annotations.Param;

public interface SettleMapper {
    void updateSettle(@Param("tenantId")Long tenantId, @Param("num") String num,@Param("lineNum") Long lineNum);
}
