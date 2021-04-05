package org.srm.purchasecooperation.cux.app.service;



import org.srm.purchasecooperation.cux.domain.entity.InvoiceData;

import java.util.List;

public interface RCWLTaxInvoiceLineService {
    /**
     * 税务发票信息同步
     *
     * @param tenantId
     * @param invoiceDataList
     */
    int InvoiceSynchronization(Long tenantId, List<InvoiceData> invoiceDataList);
}
