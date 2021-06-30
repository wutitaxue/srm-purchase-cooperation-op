package org.srm.purchasecooperation.cux.app.service;



import org.srm.purchasecooperation.cux.domain.entity.InvoiceData;
import org.srm.purchasecooperation.cux.domain.entity.ResponseData;
import java.util.List;

public interface RcwlTaxInvoiceLineService {
    /**
     * 税务发票信息同步
     *
     * @param tenantId
     * @param invoiceDataList
     */
    ResponseData InvoiceSynchronization(Long tenantId, List<InvoiceData> invoiceDataList);

    int ResponseData(ResponseData responseData);
}
