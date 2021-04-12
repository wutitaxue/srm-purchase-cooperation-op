package org.srm.purchasecooperation.cux.acp.app.service.impl;

import io.choerodon.core.exception.CommonException;
import io.choerodon.core.oauth.DetailsHelper;
import javassist.Loader;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.servicecomb.pack.omega.context.annotations.SagaStart;
import org.hzero.core.base.BaseConstants;
import org.hzero.core.helper.LanguageHelper;
import org.hzero.core.message.MessageAccessor;
import org.hzero.mybatis.domian.Condition;
import org.hzero.mybatis.util.Sqls;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.srm.boot.event.service.sender.EventSender;
import org.srm.boot.platform.customizesetting.CustomizeSettingHelper;
import org.srm.boot.platform.group.dto.ProcessStartDTO;
import org.srm.boot.platform.group.feign.HwfpRemoteService;
import org.srm.boot.platform.message.MessageHelper;
import org.srm.boot.platform.message.entity.SpfmMessageSender;
import org.srm.boot.saga.utils.SagaClient;
import org.srm.purchasecooperation.cux.acp.app.service.RCWLAcpInvoceElephantInterfaceService;
import org.srm.purchasecooperation.cux.act.infra.utils.rcwlActConstant;
import org.srm.purchasecooperation.finance.api.dto.EcInvoiceLinesDTO;
import org.srm.purchasecooperation.finance.api.dto.EcInvoiceReqDTO;
import org.srm.purchasecooperation.finance.api.dto.MessageDTO;
import org.srm.purchasecooperation.finance.app.service.InvoiceActionService;
import org.srm.purchasecooperation.finance.app.service.InvoiceHeaderService;
import org.srm.purchasecooperation.finance.app.service.TaxInvoiceLineService;
import org.srm.purchasecooperation.finance.app.service.impl.InvoiceHeaderServiceImpl;
import org.srm.purchasecooperation.finance.domain.entity.*;
import org.srm.purchasecooperation.finance.domain.repository.*;
import org.srm.purchasecooperation.finance.domain.service.InvoiceDisposeService;
import org.srm.purchasecooperation.finance.domain.vo.BaseResultVO;
import org.srm.purchasecooperation.finance.infra.constant.FinanceConstants;
import org.srm.purchasecooperation.finance.infra.feign.SrmRemoteService;
import org.srm.purchasecooperation.finance.infra.utils.BusinessRulesUtil;
import org.srm.purchasecooperation.finance.infra.utils.DateUtil;
import org.srm.web.annotation.Tenant;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author lu.cheng01@hand-china.com
 * @description 融创重写
 * @date 2021/4/10 17:22
 * @version:1.0
 */
@Service
@Tenant(rcwlActConstant.TENANT_NUMBER)
public class RCWLAcpInvoiceHeaderServiceImpl extends InvoiceHeaderServiceImpl {
    private static final Logger LOGGER = LoggerFactory.getLogger(Loader.class);
    @Autowired
    private InvoiceHeaderRepository invoiceHeaderRepository;
    @Autowired
    private InvoiceDisposeService invoiceDisposeService;
    @Autowired
    private TaxInvoiceLineService taxInvoiceLineService;
    @Autowired
    private TaxInvoiceLineRepository taxInvoiceLineRepository;
    @Autowired
    private CustomizeSettingHelper customizeSettingHelper;
    @Autowired
    private InvoiceUpdateRulesRepository invoiceUpdateRulesRepository;
    @Autowired
    private EventSender eventSender;
    @Autowired
    private RCWLAcpInvoceElephantInterfaceService rcwlAcpInvoceElephantInterfaceService;
    @Autowired
    private SupplierDeductionRepository supplierDeductionRepository;
    @Autowired
    private ToleranceRulesRepository toleranceRulesRepository;
    @Autowired
    private InvoiceLineRepository invoiceLineRepository;
    @Autowired
    private InvoiceActionService invoiceActionService;
    @Autowired
    private SrmRemoteService srmRemoteService;
    @Autowired
    private MessageHelper messageHelper;
    @Autowired
    private HwfpRemoteService hwfpRemoteService;
    @Value("${service.purchase-approval-invoice-url}")
    private String invoiceUrl;

    @Override
    @Transactional(
            rollbackFor = {Exception.class}
    )

    /**
     * 重写发票审核退回功能，增加大象接口传输
     */

    public List<InvoiceHeader> invoiceApproveReturn(Long tenantId, List<InvoiceHeader> invoiceHeaderList) {
        if (CollectionUtils.isEmpty(invoiceHeaderList)) {
            return invoiceHeaderList;
        } else {
            try {
                this.checkInvoiceApprove(tenantId, invoiceHeaderList);
            } catch (Exception var8) {
                var8.printStackTrace();
                LOGGER.info("【发票审批状态错误】" + var8.getMessage());
                return invoiceHeaderList;
            }

            String invoiceHeaderIdsStr = (String) invoiceHeaderList.stream().map(InvoiceHeader::getInvoiceHeaderId).map(String::valueOf).collect(Collectors.joining(","));
            boolean existInvalidInvoiceHeaderInDb = this.invoiceHeaderRepository.selectByIds(invoiceHeaderIdsStr).stream().anyMatch((invoiceHeader) -> {
                return "RETURN_TO_VENDOR".equals(invoiceHeader.getInvoiceStatus());
            });
            if (existInvalidInvoiceHeaderInDb) {
                throw new CommonException("invoice.status.return", new Object[0]);
            } else {
                List invoiceHeaders;
                try {
                    invoiceHeaders = this.invoiceDisposeService.invoiceStatusChange(tenantId, invoiceHeaderList, "RETURN_TO_VENDOR", "APPROVE");
                } catch (Exception var7) {
                    throw new CommonException("invoice.status.return", var7);
                }

                this.sendMessageToWorkbench(invoiceHeaders, (String) null);
                this.invoiceDisposeService.invoiceSendMessage(tenantId, invoiceHeaders, "SFIN.INVOICE_APV_REJECTED");

                //调用大象接口
                LOGGER.info("退回开始调用大象接口：");
                for (InvoiceHeader list : invoiceHeaderList
                ) {
                    try {
                        rcwlAcpInvoceElephantInterfaceService.rcwlAcpInvoceElephantInterface(list.getInvoiceHeaderId());
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    } catch (InvalidKeyException e) {
                        e.printStackTrace();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    } finally {
                        LOGGER.info("退回调用接口结束：");
                        return invoiceHeaderList;
                    }
                }
                LOGGER.info("退回调用接口结束：");
                return invoiceHeaderList;
            }
        }
    }

    /**
     * 重写发票更新方法，调用大象接口
     *
     * @param invoiceHeader
     * @return
     */

    @Override
    @Transactional(
            rollbackFor = {Exception.class}
    )
    public InvoiceHeader updateInvoice(InvoiceHeader invoiceHeader) {
        if (invoiceHeader.getBusinessType().equals("EC")) {
            this.invoiceHeaderRepository.updateOptional(invoiceHeader, new String[]{"taxInvoiceDateIssued", "taxInvoiceCode", "taxInvoiceNum", "taxIncludedAmount", "taxAmount", "taxCategory", "taxType", "errorMessage", "errorCode", "remark", "attachmentUuid", "expectInvoiceDate", "regionId", "contactName", "address", "mobile", "validateStatusCode"});
        } else {
            this.taxInvoiceLineService.batchAddOrUpdateTaxInvoiceLine(invoiceHeader.getTaxInvoiceLines(), invoiceHeader.getTenantId());
            String validateStatusCode = this.checkValidateStatusCode(invoiceHeader);
            invoiceHeader.setValidateStatusCode(validateStatusCode);
            InvoiceHeader realInvoiceHeader = this.summaryAmount(invoiceHeader.getTenantId(), invoiceHeader.getInvoiceHeaderId());
            realInvoiceHeader.checkUpdate(DetailsHelper.getUserDetails().getUserId());
            String billBasePrice = this.customizeSettingHelper.queryBySettingCode(invoiceHeader.getTenantId(), "010505");
            InvoiceUpdateRules invoiceUpdateRules = (InvoiceUpdateRules) this.invoiceUpdateRulesRepository.selectByCondition(Condition.builder(InvoiceUpdateRules.class).andWhere(Sqls.custom().andEqualTo("tenantId", realInvoiceHeader.getTenantId()).andEqualTo("consignmentType", realInvoiceHeader.getBusinessType())).build()).get(0);
            this.updateRealInvoice(invoiceUpdateRules, invoiceHeader, realInvoiceHeader, billBasePrice);
            this.copyInvoiceHeaderPropertys(realInvoiceHeader, invoiceHeader);
            String tolerance = this.customizeSettingHelper.queryBySettingCode(invoiceHeader.getTenantId(), "010510");
            String invoiceAmountMethod = this.customizeSettingHelper.queryBySettingCode(invoiceHeader.getTenantId(), "010523");
            String invoiceDeductionAmount = this.customizeSettingHelper.queryBySettingCode(invoiceHeader.getTenantId(), "010522");
            List<SupplierDeduction> supplierDeductions = null;
            if ("DICKET_DISCOUNT".equals(invoiceAmountMethod) && (StringUtils.isEmpty(invoiceDeductionAmount) || 0 == Integer.parseInt(invoiceDeductionAmount))) {
                supplierDeductions = this.supplierDeductionRepository.selectInvoiceDeduction(invoiceHeader.getInvoiceHeaderId());
            }

            invoiceHeader.validateToleranceRule(this.toleranceRulesRepository, tolerance, invoiceAmountMethod, supplierDeductions);
            this.invoiceHeaderRepository.updateOptional(invoiceHeader, new String[]{"taxInvoiceDateIssued", "taxInvoiceCode", "taxInvoiceNum", "taxIncludedAmount", "taxAmount", "taxCategory", "taxType", "errorMessage", "errorCode", "remark", "attachmentUuid", "expectInvoiceDate", "regionId", "contactName", "address", "mobile", "validateStatusCode"});

            //调用大象接口
            LOGGER.info("保存开始调用大象接口：");
            try {
                rcwlAcpInvoceElephantInterfaceService.rcwlAcpInvoceElephantInterface(invoiceHeader.getInvoiceHeaderId());
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (InvalidKeyException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            LOGGER.info("保存调用接口结束：");
            if (!StringUtils.equals(invoiceHeader.getInvoiceStatus(), "NEW") || StringUtils.equals(invoiceHeader.getInvoiceStatus(), "RETURN_TO_VENDOR")) {
                return realInvoiceHeader;
            }
        }

        return invoiceHeader;
    }

    /**
     * 重写发票提交，增加大象接口
     *
     * @param invoiceHeader
     */
    @Override
    @Transactional(
            rollbackFor = {Exception.class}
    )
    @SagaStart
    public void submitInvoice(InvoiceHeader invoiceHeader) {
        InvoiceHeader invoiceHeaderByKey = (InvoiceHeader) this.invoiceHeaderRepository.selectByPrimaryKey(invoiceHeader.getInvoiceHeaderId());
        if (StringUtils.isEmpty(invoiceHeader.getInvoiceNum())) {
            invoiceHeader.setInvoiceNum(invoiceHeaderByKey.getInvoiceNum());
        }

        Boolean flag = BaseConstants.Flag.YES.equals(invoiceHeaderByKey.getPermitDirectInvoiceFlag());
        if (flag) {
            this.checkInvoiceIssueStatus(invoiceHeaderByKey);
        } else {
            Integer checkFlag = (Integer) this.customizeSettingHelper.queryBySettingCodeAndParse(invoiceHeader.getTenantId(), "010514", Integer::parseInt);
            Integer supplierInspection = (Integer) this.customizeSettingHelper.queryBySettingCodeAndParse(invoiceHeader.getTenantId(), "010515", Integer::parseInt);
            if (BaseConstants.Flag.YES.equals(checkFlag) && BaseConstants.Flag.YES.equals(supplierInspection)) {
            }
        }

        InvoiceHeader realInvoiceHeader = this.updateInvoice(invoiceHeader);
        String result = this.customizeSettingHelper.queryBySettingCode(invoiceHeader.getTenantId(), "010505");
        List<InvoiceLine> invoiceLines = this.invoiceLineRepository.queryEcInvoiceLine(invoiceHeader.getTenantId(), invoiceHeader.getInvoiceHeaderId());
        Iterator var7 = invoiceLines.iterator();

        while (var7.hasNext()) {
            InvoiceLine invoiceLine = (InvoiceLine) var7.next();
            invoiceLine.initPriceDifferenceInfo(result);
        }

        this.invoiceLineRepository.batchUpdateOptional(invoiceLines, new String[]{"priceDifferenceFlag", "priceDifferenceMsg"});
        this.initPriceDifferenceInfo(invoiceHeader, invoiceLines, realInvoiceHeader);
        invoiceHeader.setSubmittedDate(new Date());
        invoiceHeader.setSubmittedBy(DetailsHelper.getUserDetails().getUserId());
        if ("STANDARD".equals(invoiceHeader.getTaxCategory())) {
            invoiceHeader.setInvoiceStatus("SUBMITTED");
        }

        InvoiceAction.writeInvoiceAction(this.invoiceActionService, invoiceHeader.getTenantId(), invoiceHeader.getInvoiceHeaderId(), invoiceHeader.getInvoiceStatus(), invoiceHeader.getRemark());
        InvoiceHeader newHeader = new InvoiceHeader();
        newHeader.setInvoiceHeaderId(invoiceHeader.getInvoiceHeaderId());
        newHeader = (InvoiceHeader) this.invoiceHeaderRepository.selectByPrimaryKey(newHeader);
        invoiceHeader.setObjectVersionNumber(newHeader.getObjectVersionNumber());
        this.invoiceHeaderRepository.updateByPrimaryKeySelective(invoiceHeader);
        String approvalConfig = BusinessRulesUtil.BusinessRulesMap(invoiceHeader.getTenantId(), invoiceHeader.getCompanyId(), invoiceHeader.getSupplierCompanyId(), "SITE.SFIN.INVOICE_AR_APPROVAL_TYPE");
        if ("WORKFLOW".equals(approvalConfig) && BaseConstants.Flag.YES.equals(invoiceHeaderByKey.getSupplierCreateFlag())) {
            String status = invoiceHeader.getInvoiceStatus();
            Assert.isTrue(!"PUBLISH_WORKFLOW".equals(status), "error.bill.bill_can_not_start_workflow");
            invoiceHeader.setInvoiceStatus("PUBLISH_WORKFLOW");
            this.invoiceHeaderRepository.updateOptional(invoiceHeader, new String[]{"invoiceStatus"});
            invoiceHeader.setInvoiceStatus(status);
            ProcessStartDTO processStartDTO = this.getWorkflowProcessStartDTO(invoiceHeader);
            this.hwfpRemoteService.start(SagaClient.getSagaKey(), invoiceHeader.getTenantId(), processStartDTO);
        } else {
            this.handleEcInvocieStatus(invoiceHeader, invoiceLines);
            this.invoiceHeaderRepository.updateOptional(invoiceHeader, new String[]{"invoiceStatus"});
        }

        List<InvoiceHeader> invoiceHeaders = new ArrayList();
        invoiceHeader.setMessageCopyTime(invoiceHeader.getSubmittedDate());
        ((List) invoiceHeaders).add(invoiceHeader);
        if (BaseConstants.Flag.NO.equals(invoiceHeaderByKey.getSupplierCreateFlag()) && "STANDARD".equals(invoiceHeader.getTaxCategory())) {
            String invoiceApApprovalType = BusinessRulesUtil.BusinessRulesMap(invoiceHeader.getTenantId(), invoiceHeader.getCompanyId(), invoiceHeader.getSupplierCompanyId(), "SITE.SFIN.INVOICE_AP_APPROVAL_TYPE");
            if ("NONE".equals(invoiceApApprovalType)) {
                invoiceHeaders = this.invoiceDisposeService.invoiceStatusChange(invoiceHeader.getTenantId(), (List) invoiceHeaders, "APPROVED", "APPROVE");
                this.invoiceReviewConfirm(invoiceHeader.getTenantId(), (List) invoiceHeaders);
            }
        }

        //调用大象接口
        LOGGER.info("提交开始调用大象接口：");
        try {
            rcwlAcpInvoceElephantInterfaceService.rcwlAcpInvoceElephantInterface(invoiceHeader.getInvoiceHeaderId());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        LOGGER.info("提交调用接口结束：");
        this.sendMessageToWorkbench((List) invoiceHeaders, (String) null);
        this.sendSubmitMessage(invoiceHeader, approvalConfig);
    }


    /**
     * 重写发票审批通过，增加大象接口
     *
     * @param tenantId
     * @param invoiceHeaderList
     * @return
     */
    @Override
    @Transactional(
            rollbackFor = {Exception.class}
    )
    public List<InvoiceHeader> invoiceApproveConfirm(Long tenantId, List<InvoiceHeader> invoiceHeaderList) {
        if (CollectionUtils.isEmpty(invoiceHeaderList)) {
            return invoiceHeaderList;
        } else {
            try {
                this.checkInvoiceApprove(tenantId, invoiceHeaderList);
            } catch (Exception var9) {
                var9.printStackTrace();
                LOGGER.info("【发票退回状态错误】" + var9.getMessage());
                return invoiceHeaderList;
            }

            String invoiceHeaderIdsStr = (String) invoiceHeaderList.stream().map(InvoiceHeader::getInvoiceHeaderId).map(String::valueOf).collect(Collectors.joining(","));
            boolean existInvalidInvoiceHeaderInDb = this.invoiceHeaderRepository.selectByIds(invoiceHeaderIdsStr).stream().anyMatch((invoiceHeader) -> {
                return "APPROVED".equals(invoiceHeader.getInvoiceStatus());
            });
            if (existInvalidInvoiceHeaderInDb) {
                throw new CommonException("invoice.status.approved", new Object[0]);
            } else {
                Boolean isWorkflow = "PUBLISH_WORKFLOW".equals(((InvoiceHeader) invoiceHeaderList.get(0)).getInvoiceStatus());

                List<InvoiceHeader> invoiceHeaders;
                try {
                    invoiceHeaders = this.invoiceDisposeService.invoiceStatusChange(tenantId, invoiceHeaderList, "APPROVED", "APPROVE");
                } catch (Exception var8) {
                    throw new CommonException("invoice.status.approved", new Object[0]);
                }

                String value = this.customizeSettingHelper.queryBySettingCode(tenantId, "010511");
                //调用大象接口
                LOGGER.info("退回开始调用大象接口：");
                for (InvoiceHeader list : invoiceHeaderList
                ) {
                    try {
                        rcwlAcpInvoceElephantInterfaceService.rcwlAcpInvoceElephantInterface(list.getInvoiceHeaderId());
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    } catch (InvalidKeyException e) {
                        e.printStackTrace();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
                LOGGER.info("退回调用接口结束：");
                if ((!StringUtils.isNotBlank(value) || !BaseConstants.Flag.YES.toString().equals(value)) && !isWorkflow) {
                    invoiceHeaders.forEach((invoiceHeader) -> {
                        invoiceHeader.setMessageCopyTime(invoiceHeader.getApprovedDate());
                    });
                    this.sendMessageToWorkbench(invoiceHeaders, (String) null);
                    return invoiceHeaderList;
                } else {
                    return this.invoiceReviewConfirm(tenantId, invoiceHeaderList);
                }
            }
        }
    }

    private String checkValidateStatusCode(InvoiceHeader invoiceHeader) {
        if (invoiceHeader == null) {
            return null;
        } else {
            List<TaxInvoiceLine> taxInvoiceLineListInDB = this.taxInvoiceLineRepository.selectByCondition(Condition.builder(TaxInvoiceLine.class).andWhere(Sqls.custom().andEqualTo("invoiceHeaderId", invoiceHeader.getInvoiceHeaderId()).andEqualTo("tenantId", invoiceHeader.getTenantId())).build());
            if (CollectionUtils.isEmpty(taxInvoiceLineListInDB)) {
                return null;
            } else {
                Map<String, Long> validateInfo = (Map) taxInvoiceLineListInDB.stream().collect(Collectors.groupingBy(TaxInvoiceLine::getValidateStatusCode, Collectors.counting()));
                return this.taxInvoiceLineService.updateValidateStatusCodeByInvoiceLine(validateInfo, taxInvoiceLineListInDB.size());
            }
        }
    }

    private void checkInvoiceApprove(Long tenantId, List<InvoiceHeader> invoiceHeaderList) {
        if (!CollectionUtils.isEmpty(invoiceHeaderList)) {
            List<Long> invoiceHeaderIds = (List) invoiceHeaderList.stream().map(InvoiceHeader::getInvoiceHeaderId).collect(Collectors.toList());
            if (this.invoiceHeaderRepository.checkInviceApproce(tenantId, invoiceHeaderIds) > 0) {
                throw new CommonException("invoice.approve.error", new Object[0]);
            }
        }
    }

    private void sendMessageToWorkbench(List<InvoiceHeader> invoiceHeaders, String code) {
        if (!CollectionUtils.isEmpty(invoiceHeaders)) {
            List<MessageDTO> messageDTOS = (List) invoiceHeaders.stream().map((invoiceHeader) -> {
                MessageDTO messageDTO = this.copyToMessage(invoiceHeader);
                if (StringUtils.equals("SYNC", code)) {
                    messageDTO.setInvoiceStatus(invoiceHeader.getSyncStatus());
                }

                return messageDTO;
            }).collect(Collectors.toList());
            this.eventSender.fireEvent("FINANCIAL_DOCUMENTS", "SORD_INVOICE", FinanceConstants.MessageFiled.TENANT_ID, ((MessageDTO) messageDTOS.get(0)).getInvoiceStatus(), messageDTOS);
        }
    }

    private MessageDTO copyToMessage(InvoiceHeader invoiceHeader) {
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setDocCategory("FINANCIAL_DOCUMENTS");
        messageDTO.setCompanyId(invoiceHeader.getCompanyId());
        messageDTO.setCompanyName(invoiceHeader.getCompanyName());
        messageDTO.setSupplierTenantId(invoiceHeader.getSupplierTenantId());
        messageDTO.setInvoiceNum(invoiceHeader.getInvoiceNum());
        messageDTO.setInvoiceStatus(invoiceHeader.getInvoiceStatus());
        messageDTO.setTaxIncludedAmount(invoiceHeader.getTaxIncludedAmount());
        messageDTO.setSupplierSiteId(invoiceHeader.getSupplierSiteId());
        messageDTO.setPurOrganizationId(invoiceHeader.getPurOrganizationId());
        messageDTO.setTenantId(invoiceHeader.getTenantId());
        messageDTO.setInvOrganizationId(invoiceHeader.getInvOrganizationId());
        messageDTO.setMessageDateId(invoiceHeader.getInvoiceHeaderId());
        messageDTO.setOuId(invoiceHeader.getOuId());
        messageDTO.setSubmittedDate(invoiceHeader.getMessageCopyTime());
        messageDTO.setSupplierCompanyId(invoiceHeader.getSupplierCompanyId());
        messageDTO.setSupplierCompanyName(invoiceHeader.getSupplierCompanyName());
        messageDTO.setSubmitBy(invoiceHeader.getCreatedBy());
        messageDTO.setPurchaseAgentId(invoiceHeader.getPurchaseAgentId());
        messageDTO.setTaxAmount(invoiceHeader.getTaxAmount());
        return messageDTO;
    }

    private void initPriceDifferenceInfo(InvoiceHeader invoiceHeader, List<InvoiceLine> invoiceLines, InvoiceHeader realInvoiceHeader) {
        invoiceHeader.setPriceDifferenceFlag(BaseConstants.Flag.NO);
        Boolean flag = this.getFlag(invoiceLines);
        if (flag) {
            invoiceHeader.setPriceDifferenceFlag(BaseConstants.Flag.YES);
        }

        LOGGER.debug("realInvoiceHeader.getTaxAmountSystem()[" + realInvoiceHeader.getTaxAmountSystem() + "] invoiceHeader.getTaxAmount()[" + invoiceHeader.getTaxAmount() + "] realInvoiceHeader.getTaxIncludedAmountSystem()[" + realInvoiceHeader.getTaxIncludedAmountSystem() + "] invoiceHeader.getTaxIncludedAmount()" + invoiceHeader.getTaxIncludedAmount());
        if (realInvoiceHeader.getTaxAmountSystem().compareTo(invoiceHeader.getTaxAmount()) != 0 || realInvoiceHeader.getTaxIncludedAmountSystem().compareTo(invoiceHeader.getTaxIncludedAmount()) != 0) {
            invoiceHeader.setPriceDifferenceFlag(BaseConstants.Flag.YES);
        }

    }

    private Boolean getFlag(List<InvoiceLine> invoiceLines) {
        Boolean flag = false;
        if (CollectionUtils.isEmpty(invoiceLines)) {
            return false;
        } else {
            Iterator var3 = invoiceLines.iterator();

            while (var3.hasNext()) {
                InvoiceLine invoiceLine = (InvoiceLine) var3.next();
                if (BaseConstants.Flag.YES.equals(invoiceLine.getPriceDifferenceFlag())) {
                    flag = true;
                    break;
                }
            }

            return flag;
        }
    }

    private void handleEcInvocieStatus(InvoiceHeader invoiceHeader, List<InvoiceLine> invoiceLines) {
        if ("WITH_GOODS".equals(invoiceHeader.getTaxCategory())) {
            invoiceHeader.setInvoiceStatus("REVIEWED");
        } else if ("CENTRALIZED".equals(invoiceHeader.getTaxCategory())) {
            if (!"NEW".equals(invoiceHeader.getInvoiceStatus()) && !"EC_SERVICED".equals(invoiceHeader.getInvoiceStatus())) {
                throw new CommonException("invoice.status.error", new Object[0]);
            }

            invoiceHeader.setInvoiceStatus("EC_INVOICING");
            EcInvoiceReqDTO ecInvoiceReqDTO = this.initEcInvoiceReqDTO(invoiceHeader, invoiceLines);
            LOGGER.info("SagaClient.getSagaKey()::::::" + SagaClient.getSagaKey());
            LOGGER.info("ecInvoiceReqDTO::::::" + ecInvoiceReqDTO.toString());
            ResponseEntity<BaseResultVO<String>> booleanResponseEntity = this.srmRemoteService.invoiceReq(SagaClient.getSagaKey(), invoiceHeader.getTenantId(), ecInvoiceReqDTO);
            LOGGER.info("booleanResponseEntity::::::" + ((BaseResultVO) booleanResponseEntity.getBody()).toString());
            if (!StringUtils.equals("true", (CharSequence) ((BaseResultVO) booleanResponseEntity.getBody()).getResult()) || ((BaseResultVO) booleanResponseEntity.getBody()).getResult() == null) {
                throw new CommonException("error.order.billing_application_failed", new Object[0]);
            }
        }

    }

    EcInvoiceReqDTO initEcInvoiceReqDTO(InvoiceHeader invoiceHeader, List<InvoiceLine> invoiceLines) {
        EcInvoiceReqDTO ecInvoiceReqDTO = new EcInvoiceReqDTO();
        ecInvoiceReqDTO.setInvoiceHeaderId(invoiceHeader.getInvoiceHeaderId());
        ecInvoiceReqDTO.setCompanyId(invoiceHeader.getCompanyId());
        ecInvoiceReqDTO.setSrmInvoiceNum(invoiceHeader.getInvoiceNum());
        ecInvoiceReqDTO.setBillToAddress(invoiceHeader.getAddress());
        ecInvoiceReqDTO.setBillToContact(invoiceHeader.getMobile());
        ecInvoiceReqDTO.setBillToPerson(invoiceHeader.getContactName());
        ecInvoiceReqDTO.setRegionId(invoiceHeader.getRegionId());
        ecInvoiceReqDTO.setBillType("1");
        ecInvoiceReqDTO.setMergeFlag(1);
        if (!"GENERAL_VAT".equals(invoiceHeader.getTaxType()) && !"ELECTRONIC".equals(invoiceHeader.getTaxType())) {
            if ("SPECIAL_VAT".equals(invoiceHeader.getTaxType())) {
                ecInvoiceReqDTO.setInvoiceType("2");
            }
        } else {
            ecInvoiceReqDTO.setInvoiceType("1");
        }

        ecInvoiceReqDTO.setExpectInvoiceDate(invoiceHeader.getExpectInvoiceDate());
        if (!"CENTRALIZED".equals(invoiceHeader.getTaxCategory())) {
            ecInvoiceReqDTO.setBillType("2");
        }

        ecInvoiceReqDTO.setSrmInvoiceNum(invoiceHeader.getInvoiceNum());
        ecInvoiceReqDTO.setTaxIncludedAmount(invoiceHeader.getTaxIncludedAmount());
        List<EcInvoiceLinesDTO> ecInvoiceLinesDTOS = new ArrayList();
        Iterator var5 = invoiceLines.iterator();

        while (true) {
            InvoiceLine invoiceLine;
            do {
                if (!var5.hasNext()) {
                    ecInvoiceReqDTO.setEcInvoiceLinesDTOList(ecInvoiceLinesDTOS);
                    EcInvoiceReqDTO partEcInvoiceReqDTO = this.invoiceHeaderRepository.selectPartEcInvoiceFromPrHeader(invoiceHeader.getInvoiceHeaderId());
                    Assert.notNull(partEcInvoiceReqDTO, "invoice.content.not.found");
                    ecInvoiceReqDTO.setEcPlatform(partEcInvoiceReqDTO.getEcPlatform());
                    ecInvoiceReqDTO.setInvoiceContentDetail(partEcInvoiceReqDTO.getInvoiceContentDetail());
                    ecInvoiceReqDTO.setInvoiceContent(partEcInvoiceReqDTO.getInvoiceContent());
                    return ecInvoiceReqDTO;
                }

                invoiceLine = (InvoiceLine) var5.next();
            } while (Objects.nonNull(invoiceLine.getFreightLineFlag()) && BaseConstants.Flag.YES.equals(invoiceLine.getFreightLineFlag()));

            EcInvoiceLinesDTO ecInvoiceLinesDTO = new EcInvoiceLinesDTO();
            ecInvoiceLinesDTO.setEcPoLineId(invoiceLine.getEcPoLineId());
            ecInvoiceLinesDTO.setEcPoSubHeaderId(invoiceLine.getEcPoSubHeaderId());
            ecInvoiceLinesDTO.setInvoiceLineId(invoiceLine.getInvoiceLineId());
            ecInvoiceLinesDTO.setSrmAmount(invoiceLine.getTaxIncludedAmount());
            ecInvoiceLinesDTO.setSrmNetAmount(invoiceLine.getNetAmount());
            ecInvoiceLinesDTO.setSrmPrice(invoiceLine.getTaxIncludedPrice());
            ecInvoiceLinesDTO.setSrmTaxAmount(invoiceLine.getTaxAmount());
            ecInvoiceLinesDTO.setSrmTaxRate(invoiceLine.getTaxRate());
            ecInvoiceLinesDTO.setSrmQuantity(invoiceLine.getQuantity());
            ecInvoiceLinesDTOS.add(ecInvoiceLinesDTO);
        }
    }

    private void sendSubmitMessage(InvoiceHeader invoiceHeader, String approveMethod) {
        Map<String, String> args = new HashMap();
        args.put("organizationId", String.valueOf(invoiceHeader.getTenantId()));
        args.put("companyName", invoiceHeader.getSupplierCompanyName());
        args.put("invoiceNum", invoiceHeader.getInvoiceNum());
        if ("WORKFLOW".equals(approveMethod)) {
            args.put("invoiceNumUrl", "<a href=\"/app/hwfp/task/list\">" + invoiceHeader.getInvoiceNum() + "</a>");
        } else {
            args.put("invoiceNumUrl", "<a href=\"/app" + this.invoiceUrl + invoiceHeader.getInvoiceHeaderId() + "\">" + invoiceHeader.getInvoiceNum() + "</a>");
        }

        String clickHereMessage = MessageAccessor.getMessage("click.here", LanguageHelper.locale()).desc();
        args.put("SERVICE_URL", "<a href=\"/app" + this.invoiceUrl + invoiceHeader.getInvoiceHeaderId() + "\">" + clickHereMessage + "</a>");
        args.put("INIT_DATE", DateUtil.initDate());
        List<Long> agentIds = new ArrayList();
        if (Objects.isNull(invoiceHeader.getPurchaseAgentId())) {
            agentIds.addAll(this.invoiceHeaderRepository.selectPoAgentIdsByInvoiceHeaderId(invoiceHeader.getInvoiceHeaderId()));
        } else {
            agentIds.add(invoiceHeader.getPurchaseAgentId());
        }

        List<String> agents = (List) agentIds.stream().map((a) -> {
            return a.toString();
        }).collect(Collectors.toList());
        args.put("agentIds", String.join(",", agents));
        args.put("companyId", String.valueOf(invoiceHeader.getCompanyId()));
        LOGGER.info("invoiceHeaderServiceImpl.sendSubmitMessage.args: " + args.toString());

        try {
            this.messageHelper.sendMessage(new SpfmMessageSender(invoiceHeader.getTenantId(), "SPUC.INVOICE.SUPPLIER_SUBMIT", args));
        } catch (Exception var8) {
            LOGGER.error(var8.getMessage(), var8);
        }

    }

}
