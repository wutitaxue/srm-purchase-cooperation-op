package org.srm.purchasecooperation.cux.infra.repository.impl;

import org.hzero.mybatis.base.BaseRepository;
import org.hzero.mybatis.base.impl.BaseRepositoryImpl;
import org.springframework.stereotype.Component;
import org.srm.purchasecooperation.cux.domain.repository.RCWLTaxInvoiceLineRepository;
import org.srm.purchasecooperation.finance.domain.entity.InvoiceHeader;
import org.srm.purchasecooperation.finance.domain.entity.TaxInvoiceLine;
import org.srm.purchasecooperation.cux.infra.mapper.RCWLTaxInvoiceLineMapper;

import javax.annotation.Resource;

@Component
public class RCWLTaxInvoiceLineRepositoryImpl extends BaseRepositoryImpl<TaxInvoiceLine> implements RCWLTaxInvoiceLineRepository {
    @Resource
    private RCWLTaxInvoiceLineMapper rcwLTaxInvoiceLineMapper;

    @Override
    public InvoiceHeader selectOneInvoiceHeader(String InvoiceNum) {
        return rcwLTaxInvoiceLineMapper.selectOneInvoiceHeader(InvoiceNum);
    }

    @Override
    public int selectOneInvoiceLine(Long invoiceHeaderId) {
        return rcwLTaxInvoiceLineMapper.selectOneInvoiceLine(invoiceHeaderId) == null ? 0:1;
    }
}
