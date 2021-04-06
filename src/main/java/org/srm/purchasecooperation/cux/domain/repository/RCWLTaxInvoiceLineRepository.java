package org.srm.purchasecooperation.cux.domain.repository;

import org.srm.purchasecooperation.finance.domain.entity.InvoiceHeader;
import org.srm.purchasecooperation.finance.domain.entity.TaxInvoiceLine;

public interface RCWLTaxInvoiceLineRepository {
    InvoiceHeader selectOneInvoiceHeader(String InvoiceNum);

    TaxInvoiceLine selectOneInvoiceLine(Long invoiceHeaderId);
}
