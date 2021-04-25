package org.srm.purchasecooperation.cux.infra.mapper;

import org.srm.purchasecooperation.finance.domain.entity.InvoiceHeader;
import org.srm.purchasecooperation.finance.domain.entity.TaxInvoiceLine;

public interface RCWLTaxInvoiceLineMapper {
    InvoiceHeader selectOneInvoiceHeader(String InvoiceNum);

    TaxInvoiceLine selectOneInvoiceLine(Long invoiceHeaderId,String invoiceCode,String invoiceNumber);
}
