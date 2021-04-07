package org.srm.purchasecooperation.cux.infra.repository.impl;

import org.springframework.stereotype.Component;
import org.srm.purchasecooperation.cux.domain.repository.RCWLTaxInvoiceLineRepository;
import org.srm.purchasecooperation.finance.domain.entity.InvoiceHeader;
import org.srm.purchasecooperation.finance.domain.entity.TaxInvoiceLine;
import org.srm.purchasecooperation.cux.infra.mapper.RCWLTaxInvoiceLineMapper;

import javax.annotation.Resource;

@Component
public class RCWLTaxInvoiceLineRepositoryImpl implements RCWLTaxInvoiceLineRepository {
    @Resource
    private RCWLTaxInvoiceLineMapper rcwLTaxInvoiceLineMapper;

    @Override
    public InvoiceHeader selectOneInvoiceHeader(String InvoiceNum) {
        return rcwLTaxInvoiceLineMapper.selectOneInvoiceHeader(InvoiceNum);
    }

    @Override
    public TaxInvoiceLine selectOneInvoiceLine(Long invoiceHeaderId) {
        return rcwLTaxInvoiceLineMapper.selectOneInvoiceLine(invoiceHeaderId);
    }
}
