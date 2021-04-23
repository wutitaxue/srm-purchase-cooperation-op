package org.srm.purchasecooperation.cux.domain.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class PayLoad {

    @JsonProperty("payload")
    private List<InvoiceData> invoiceDataList;

    public List<InvoiceData> getInvoiceDataList() {
        return invoiceDataList;
    }

    public void setInvoiceDataList(List<InvoiceData> invoiceDataList) {
        this.invoiceDataList = invoiceDataList;
    }
}
