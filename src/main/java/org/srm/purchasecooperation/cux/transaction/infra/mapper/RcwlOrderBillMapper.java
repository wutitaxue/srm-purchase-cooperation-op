package org.srm.purchasecooperation.cux.transaction.infra.mapper;


import org.apache.ibatis.annotations.Param;
import org.srm.purchasecooperation.cux.transaction.api.dto.RcwlOrderBillDTO;

public interface RcwlOrderBillMapper{

    //查询接收送货单
    RcwlOrderBillDTO selectSendAsn(@Param("tenantId")Long tenantId,@Param("id") Long id);
    //查询验收单
    RcwlOrderBillDTO selectSendAccept(@Param("tenantId")Long tenantId,@Param("id")Long id);
    //更新物料表
    void updateItem(@Param("tenantId")Long tenantId,@Param("itemCode")String itemCode);
}
