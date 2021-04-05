package org.srm.purchasecooperation.cux.domain.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class InvoiceData {
    private String documentType;
    private String documentNumber;
    private String invoiceCode;
    private String invoiceNumber;
    private String invoiceTypeCode;
    private Date billingDate;
    private BigDecimal totalAmount;
    private BigDecimal taxAmount;
    private BigDecimal taxIncludedAmount;
    private String taxIncludedStatusCode;
    private String validateStatus;
}
