package org.srm.purchasecooperation.cux.order.infra.mapper;

import org.apache.ibatis.annotations.Param;


public interface RcwlMyCostMapper {
    Long selectCostId(@Param("costCode") String costCode,@Param("tenantId") Long tenantId);

    String selectWbs(@Param("wbsCode") String wbsCode,@Param("tenantId") Long tenantId,@Param("poHeaderId") Long poHeaderId);

}
