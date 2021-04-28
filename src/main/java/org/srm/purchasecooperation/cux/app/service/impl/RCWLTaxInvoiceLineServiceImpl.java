package org.srm.purchasecooperation.cux.app.service.impl;

import io.choerodon.core.exception.CommonException;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.map.MultiKeyMap;
import org.apache.commons.lang3.ObjectUtils;
import org.hzero.mybatis.domian.Condition;
import org.hzero.mybatis.helper.SecurityTokenHelper;
import org.hzero.mybatis.util.Sqls;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.srm.boot.platform.customizesetting.CustomizeSettingHelper;
import org.srm.purchasecooperation.cux.app.service.RCWLTaxInvoiceLineService;
import org.srm.purchasecooperation.cux.domain.entity.InvoiceData;
import org.srm.purchasecooperation.cux.domain.entity.ResponseData;
import org.srm.purchasecooperation.cux.domain.repository.RCWLTaxInvoiceLineRepository;
import org.srm.purchasecooperation.finance.app.service.TaxInvoiceLineService;
import org.srm.purchasecooperation.finance.domain.entity.InvoiceHeader;
import org.srm.purchasecooperation.finance.domain.entity.TaxInvoiceLine;
import org.srm.purchasecooperation.finance.domain.repository.InvoiceHeaderRepository;
import org.srm.purchasecooperation.finance.domain.repository.TaxInvoiceLineRepository;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RCWLTaxInvoiceLineServiceImpl implements RCWLTaxInvoiceLineService {
    @Resource
    private TaxInvoiceLineService taxInvoiceLineService;
    @Resource
    private RCWLTaxInvoiceLineRepository rcwlTaxInvoiceLineRepository;
    @Autowired
    private InvoiceHeaderRepository invoiceHeaderRepository;
    @Autowired
    private TaxInvoiceLineRepository taxInvoiceLineRepository;
    @Autowired
    private CustomizeSettingHelper customizeSettingHelper;

    @Override
    public ResponseData InvoiceSynchronization(Long tenantId, List<InvoiceData> invoiceDataList) {
        ResponseData responseData =  new ResponseData();
        try {
            InvoiceHeader invoiceHeader = new InvoiceHeader();
            List<TaxInvoiceLine> taxInvoiceLineList = new ArrayList<>();
            for (InvoiceData invoiceLine : invoiceDataList) {
                Boolean checkValidate = CheckValidate(invoiceLine);
                if(!checkValidate){
                    responseData.setState("0");
                    responseData.setMessage("操作失败，参数获取失败，请校验参数格式及完整性！");
                    responseData.setCode("201");
                    return responseData;
                }
                invoiceHeader = rcwlTaxInvoiceLineRepository.selectOneInvoiceHeader(invoiceLine.getDocumentNumber());
                if(null == invoiceHeader){
                    responseData.setState("0");
                    responseData.setMessage("操作失败，此业务单据号数据不存在！");
                    responseData.setCode("201");
                    return responseData;
                }
                log.info("====================一========================="+invoiceLine.getDocumentNumber());
                TaxInvoiceLine t  = rcwlTaxInvoiceLineRepository.selectOneInvoiceLine(invoiceHeader.getInvoiceHeaderId(),invoiceLine.getInvoiceNumber());
                    TaxInvoiceLine taxInvoiceLine = new TaxInvoiceLine ();
                    if(null != t){
                        taxInvoiceLine.setTaxInvoiceLineId(t.getTaxInvoiceLineId());
                        taxInvoiceLine.setObjectVersionNumber(t.getObjectVersionNumber());
                    }
                    taxInvoiceLine.setInvoiceHeaderId(invoiceHeader.getInvoiceHeaderId());
                    taxInvoiceLine.setTenantId(tenantId);
                    if("".equals(invoiceLine.getCheckCode()) || null == invoiceLine.getCheckCode()){
                        taxInvoiceLine.setCheckCode("111111");
                    }else{
                        taxInvoiceLine.setCheckCode(invoiceLine.getCheckCode());
                    }
                    taxInvoiceLine.setInvoiceCode(invoiceLine.getInvoiceCode());
                    taxInvoiceLine.setInvoiceNumber(invoiceLine.getInvoiceNumber());
                    taxInvoiceLine.setInvoiceTypeCode(invoiceLine.getInvoiceTypeCode());
                    taxInvoiceLine.setBillingDate(invoiceLine.getBillingDate());
                    taxInvoiceLine.setTotalAmount(invoiceLine.getTotalAmount());
                    taxInvoiceLine.setTaxAmount(invoiceLine.getTaxAmount());
                    taxInvoiceLine.setTaxIncludedAmount(invoiceLine.getTaxIncludedAmount());
                    taxInvoiceLine.setTaxInvoiceStatusCode(invoiceLine.getTaxInvoiceStatusCode());
                    taxInvoiceLine.setValidateStatusCode(invoiceLine.getValidateStatus());
                    taxInvoiceLineList.add(taxInvoiceLine);
            }
            this.overwriteBatchAddOrUpdateTaxInvoiceLine(taxInvoiceLineList, tenantId);
            responseData.setState("1");
            responseData.setMessage("操作成功！");
            responseData.setCode("200");
            return responseData;
        } catch (Exception e) {
            e.printStackTrace();
        }
        responseData.setState("0");
        responseData.setMessage("操作失败，请联系管理员！");
        responseData.setCode("201");
        return responseData;
    }

    public Boolean CheckValidate(InvoiceData invoiceLine){
        Boolean b = false;
        if(null != invoiceLine.getBillingDate() &&
            null != invoiceLine.getInvoiceCode() &&
            null != invoiceLine.getInvoiceNumber() &&
            null != invoiceLine.getInvoiceTypeCode() &&
            null != invoiceLine.getTaxAmount() &&
            null != invoiceLine.getValidateStatus() &&
            null != invoiceLine.getDocumentNumber() &&
            null != invoiceLine.getDocumentType() &&
            null != invoiceLine.getTaxIncludedAmount() &&
            null != invoiceLine.getTotalAmount() &&
            null != invoiceLine.getTaxInvoiceStatusCode()){
            b = true;
        }
        return b;
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

    public List<TaxInvoiceLine> overwriteBatchAddOrUpdateTaxInvoiceLine(List<TaxInvoiceLine> taxInvoiceLines, Long tenantId) {
        if (CollectionUtils.isEmpty(taxInvoiceLines)) {
            log.info("batchAddOrUpdateTaxInvoiceLine() 不更新税务发票行");
            return Collections.emptyList();
        } else {
            List<TaxInvoiceLine> taxInvoiceLineList = (List)taxInvoiceLines.stream().filter((item) -> {
                return !"CHECK_SUCCESS".equals(item.getValidateStatusCode());
            }).collect(Collectors.toList());
            MultiKeyMap<String, String> multiKeyMap = new MultiKeyMap();
            List<TaxInvoiceLine> dataList = new ArrayList(taxInvoiceLines);
            List<Long> lineIds = (List)taxInvoiceLines.stream().filter((vo) -> {
                return vo.getTaxInvoiceLineId() != null;
            }).map(TaxInvoiceLine::getTaxInvoiceLineId).collect(Collectors.toList());
            Long invoiceHeaderId = ((TaxInvoiceLine)taxInvoiceLines.get(0)).getInvoiceHeaderId();
            List<InvoiceHeader> invoiceHeaders = invoiceHeaderRepository.selectByCondition(Condition.builder(InvoiceHeader.class).andWhere(Sqls.custom().andEqualTo("tenantId", tenantId).andEqualTo("invoiceHeaderId", invoiceHeaderId)).build());
            Assert.notEmpty(invoiceHeaders, "No data found by tenantId and invoiceHeaderId");
            dataList.addAll(taxInvoiceLineRepository.selectInvoiceLineNotInIds(lineIds, invoiceHeaderId, (Long)null, (Set)null));
            dataList.forEach((item) -> {
                if (!multiKeyMap.containsKey(item.getInvoiceCode(), item.getInvoiceNumber())) {
                    multiKeyMap.put(item.getInvoiceCode(), item.getInvoiceNumber(), item.getInvoiceNumber());
                } else {
                    throw new CommonException("error.invoice.duplication", new Object[]{item.getInvoiceCode(), item.getInvoiceNumber()});
                }
            });
            String isEnableCheck = customizeSettingHelper.queryBySettingCode(tenantId, "010514");
            taxInvoiceLineList.forEach((item) -> {
                item.validateNotNull();
                item.verificationInvoiceType(isEnableCheck);
                if (StringUtils.isEmpty(item.getInputTypeCode())) {
                    item.setInputTypeCode("MANUAL_INPUT");
                }

            });
            List<TaxInvoiceLine> taxInvoiceLinesInDbs = new ArrayList();
            Map<Boolean, List<TaxInvoiceLine>> taxInvoiceLineMap = (Map)taxInvoiceLineList.stream().collect(Collectors.groupingBy((item) -> {
                return item.getTaxInvoiceLineId() == null || item.getTaxInvoiceLineId().equals(0L);
            }));
            taxInvoiceLinesInDbs.addAll(taxInvoiceLineRepository.batchUpdateOptional(taxInvoiceLineMap.get(false) == null ? Collections.emptyList() : (List)taxInvoiceLineMap.get(false), new String[]{"invoiceCode", "invoiceNumber", "inputTypeCode", "invoiceTypeCode", "billingDate", "totalAmount", "checkCode", "inputTypeCode", "taxAmount", "invoiceDirection", "noDepositInvoiceTypeCode"}));
            taxInvoiceLinesInDbs.addAll(taxInvoiceLineRepository.batchInsertSelective(taxInvoiceLineMap.get(true) == null ? Collections.emptyList() : (List)taxInvoiceLineMap.get(true)));
            return taxInvoiceLinesInDbs;
        }
    }

}
