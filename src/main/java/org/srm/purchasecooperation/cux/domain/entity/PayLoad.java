package org.srm.purchasecooperation.cux.domain.entity;

import java.util.List;

public class PayLoad {
    private List<InvoiceData> invoiceDataList;

    public List<InvoiceData> getInvoiceDataList() {
        return invoiceDataList;
    }

    public void setInvoiceDataList(List<InvoiceData> invoiceDataList) {
        this.invoiceDataList = invoiceDataList;
    }
}
