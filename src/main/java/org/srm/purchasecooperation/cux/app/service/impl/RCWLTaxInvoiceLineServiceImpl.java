package org.srm.purchasecooperation.cux.app.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.ObjectUtils;
import org.hzero.mybatis.domian.Condition;
import org.hzero.mybatis.util.Sqls;
import org.springframework.stereotype.Service;
import org.srm.purchasecooperation.cux.app.service.RCWLTaxInvoiceLineService;
import org.srm.purchasecooperation.cux.domain.entity.InvoiceData;
import org.srm.purchasecooperation.cux.domain.entity.ResponseData;
import org.srm.purchasecooperation.cux.domain.repository.RCWLTaxInvoiceLineRepository;
import org.srm.purchasecooperation.finance.app.service.TaxInvoiceLineService;
import org.srm.purchasecooperation.finance.domain.entity.InvoiceHeader;
import org.srm.purchasecooperation.finance.domain.entity.InvoiceLine;
import org.srm.purchasecooperation.finance.domain.entity.TaxInvoiceLine;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class RCWLTaxInvoiceLineServiceImpl implements RCWLTaxInvoiceLineService {
    @Resource
    private TaxInvoiceLineService taxInvoiceLineService;
    @Resource
    private RCWLTaxInvoiceLineRepository rcwlTaxInvoiceLineRepository;

    @Override
    public ResponseData InvoiceSynchronization(Long tenantId, List<InvoiceData> invoiceDataList) {
        ResponseData responseData =  new ResponseData();
        try {
            InvoiceHeader invoiceHeader = new InvoiceHeader();
            List<TaxInvoiceLine> taxInvoiceLineList = new ArrayList<>();
            for (InvoiceData invoiceLine : invoiceDataList) {
                invoiceHeader = rcwlTaxInvoiceLineRepository.selectOneInvoiceHeader(invoiceLine.getDocumentNumber());
                TaxInvoiceLine taxInvoiceLine = new TaxInvoiceLine();
                log.info("====================一========================="+invoiceLine.getDocumentNumber());
                taxInvoiceLine = rcwlTaxInvoiceLineRepository.selectOneInvoiceLine(invoiceHeader.getInvoiceHeaderId());


                List list = this.rcwlTaxInvoiceLineRepository.selectByCondition(
                        Condition.builder(InvoiceLine.class)
                                .andWhere(Sqls.custom()
                                        .andEqualTo(InvoiceLine.FIELD_INVOICE_HEADER_ID, invoiceHeader.getInvoiceHeaderId())
                                ).build());
                if(CollectionUtil.isEmpty(list)){
                    taxInvoiceLine.setInvoiceCode(invoiceLine.getInvoiceCode());
                    taxInvoiceLine.setInvoiceNumber(invoiceLine.getInvoiceNumber());
                    taxInvoiceLine.setInvoiceTypeCode(invoiceLine.getInvoiceTypeCode());
                    taxInvoiceLine.setBillingDate(invoiceLine.getBillingDate());
                    taxInvoiceLine.setTotalAmount(invoiceLine.getTotalAmount());
                    taxInvoiceLine.setTaxAmount(invoiceLine.getTaxAmount());
                    taxInvoiceLine.setTaxIncludedAmount(invoiceLine.getTaxIncludedAmount());
                    taxInvoiceLine.setTaxInvoiceStatusCode(invoiceLine.getTaxIncludedStatusCode());
                    taxInvoiceLine.setValidateStatusCode(invoiceLine.getValidateStatus());
                    taxInvoiceLineList.add(taxInvoiceLine);
                }
            }
            taxInvoiceLineService.batchAddOrUpdateTaxInvoiceLine(taxInvoiceLineList, tenantId);
            responseData.setState("1");
            responseData.setMessage("操作成功！");
            responseData.setCode("200");
            return responseData;
        } catch (Exception e) {
            e.printStackTrace();
        }
        responseData.setState("0");
        responseData.setMessage("操作失败！");
        responseData.setCode("201");
        return responseData;
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
