package org.srm.purchasecooperation.cux.app.service.impl;

import org.springframework.stereotype.Service;
import org.srm.purchasecooperation.cux.app.service.RCWLTaxInvoiceLineService;
import org.srm.purchasecooperation.cux.domain.entity.InvoiceData;
import org.srm.purchasecooperation.cux.domain.entity.ResponseData;
import org.srm.purchasecooperation.cux.domain.repository.RCWLTaxInvoiceLineRepository;
import org.srm.purchasecooperation.finance.app.service.TaxInvoiceLineService;
import org.srm.purchasecooperation.finance.domain.entity.InvoiceHeader;
import org.srm.purchasecooperation.finance.domain.entity.TaxInvoiceLine;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class RCWLTaxInvoiceLineServiceImpl implements RCWLTaxInvoiceLineService {
    @Resource
    private TaxInvoiceLineService taxInvoiceLineService;
    @Resource
    private RCWLTaxInvoiceLineRepository rcwlTaxInvoiceLineRepository;

    @Override
    public int InvoiceSynchronization(Long tenantId, List<InvoiceData> invoiceDataList) {
        try {
            InvoiceHeader invoiceHeader = new InvoiceHeader();
            TaxInvoiceLine taxInvoiceLine = new TaxInvoiceLine();
            List<TaxInvoiceLine> taxInvoiceLineList = new ArrayList<>();
            for (InvoiceData invoiceLine : invoiceDataList) {
                invoiceHeader = rcwlTaxInvoiceLineRepository.selectOneInvoiceHeader(invoiceLine.getDocumentNumber());
                taxInvoiceLine = rcwlTaxInvoiceLineRepository.selectOneInvoiceLine(invoiceHeader.getInvoiceHeaderId());
                taxInvoiceLine.setInvoiceCode(invoiceLine.getInvoiceCode());
                taxInvoiceLine.setInvoiceNumber(invoiceLine.getInvoiceNumber());
                taxInvoiceLine.setInvoiceTypeCode(invoiceLine.getInvoiceTypeCode());
                taxInvoiceLine.setBillingDate(invoiceLine.getBillingDate());
                taxInvoiceLine.setTotalAmount(invoiceLine.getTotalAmount());
                taxInvoiceLine.setTaxAmount(invoiceLine.getTaxAmount());
                taxInvoiceLine.setTaxIncludedAmount(invoiceLine.getTaxIncludedAmount());
                taxInvoiceLine.setTaxInvoiceStatusCode(invoiceLine.getTaxIncludedStatusCode());
                taxInvoiceLine.setValidateStatusCode(invoiceLine.getValidateStatus());
            }
            taxInvoiceLineService.batchAddOrUpdateTaxInvoiceLine(taxInvoiceLineList, tenantId);
            return 200;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int ResponseData(ResponseData responseData) {
        try {
            responseData.setCode("coming!!!!");
            responseData.setMessage("进来了");
            responseData.setState("1");
            return 200;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
