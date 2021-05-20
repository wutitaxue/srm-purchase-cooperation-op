package org.srm.purchasecooperation.cux.domain.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class InvoiceData {
    @JsonProperty("documentType")
    private String documentType;
    @JsonProperty("documentNumber")
    private String documentNumber;
    @JsonProperty("invoiceCode")
    private String invoiceCode;
    @JsonProperty("invoiceNumber")
    private String invoiceNumber;
    @JsonProperty("invoiceTypeCode")
    private String invoiceTypeCode;
    @DateTimeFormat(pattern = "yyyy-MM-dd hh24:mm:ss")
    @JsonProperty("billingDate")
    private Date billingDate;
    @JsonProperty("totalAmount")
    private BigDecimal totalAmount;
    @JsonProperty("taxAmount")
    private BigDecimal taxAmount;
    @JsonProperty("taxIncludedAmount")
    private BigDecimal taxIncludedAmount;
    @JsonProperty("taxInvoiceStatusCode")
    private String taxInvoiceStatusCode;
    @JsonProperty("validateStatus")
    private String validateStatus;
    @JsonProperty("checkCode")
    private String checkCode;
}
