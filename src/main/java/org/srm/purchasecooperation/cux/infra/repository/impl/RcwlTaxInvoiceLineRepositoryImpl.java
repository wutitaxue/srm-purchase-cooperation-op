package org.srm.purchasecooperation.cux.infra.repository.impl;

import org.hzero.mybatis.base.impl.BaseRepositoryImpl;
import org.springframework.stereotype.Component;
import org.srm.purchasecooperation.cux.domain.repository.RcwlTaxInvoiceLineRepository;
import org.srm.purchasecooperation.finance.domain.entity.InvoiceHeader;
import org.srm.purchasecooperation.finance.domain.entity.TaxInvoiceLine;
import org.srm.purchasecooperation.cux.infra.mapper.RcwlTaxInvoiceLineMapper;

import javax.annotation.Resource;

@Component
public class RcwlTaxInvoiceLineRepositoryImpl extends BaseRepositoryImpl<TaxInvoiceLine> implements RcwlTaxInvoiceLineRepository {
    @Resource
    private RcwlTaxInvoiceLineMapper rcwLTaxInvoiceLineMapper;

    @Override
    public InvoiceHeader selectOneInvoiceHeader(String InvoiceNum) {
        return rcwLTaxInvoiceLineMapper.selectOneInvoiceHeader(InvoiceNum);
    }

    @Override
    public TaxInvoiceLine selectOneInvoiceLine(Long invoiceHeaderId,String invoiceNumber) {
        return rcwLTaxInvoiceLineMapper.selectOneInvoiceLine(invoiceHeaderId,invoiceNumber);
    }
}
