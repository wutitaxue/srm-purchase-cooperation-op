package org.srm.purchasecooperation.cux.order.infra.mapper;

import org.apache.ibatis.annotations.Param;


public interface RcwlMyCostMapper {
    Long selectCostId(@Param("costCode") String costCode,@Param("tenantId") Long tenantId);

}