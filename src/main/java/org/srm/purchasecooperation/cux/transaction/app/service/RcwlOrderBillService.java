package org.srm.purchasecooperation.cux.transaction.app.service;



import org.srm.purchasecooperation.cux.transaction.api.dto.RcwlOrderBillDTO;

import java.util.List;

public interface RcwlOrderBillService {

    void sendOrderBillList(List<RcwlOrderBillDTO> list);

    void sendOrderBillOne(RcwlOrderBillDTO rcwlOrderBillDTO);
}
