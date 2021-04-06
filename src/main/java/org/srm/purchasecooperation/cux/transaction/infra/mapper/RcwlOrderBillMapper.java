package org.srm.purchasecooperation.cux.transaction.infra.mapper;


import org.srm.purchasecooperation.cux.transaction.api.dto.RcwlOrderBillDTO;

public interface RcwlOrderBillMapper{

    //查询接收送货单
    RcwlOrderBillDTO selectSendAsn(Long id);
    //查询验收单
    RcwlOrderBillDTO selectSendAccept(Long id);
}
