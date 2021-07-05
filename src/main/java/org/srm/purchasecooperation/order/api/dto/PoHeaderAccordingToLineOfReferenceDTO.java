//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.srm.purchasecooperation.order.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.choerodon.core.oauth.CustomUserDetails;
import io.choerodon.core.oauth.DetailsHelper;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;
import org.hzero.boot.platform.lov.annotation.LovValue;
import org.hzero.core.base.BaseConstants.Flag;
import org.hzero.starter.keyencrypt.core.Encrypt;
import org.springframework.format.annotation.DateTimeFormat;
import org.srm.boot.platform.configcenter.CnfHelper;
import org.srm.boot.platform.customizesetting.CustomizeSettingHelper;
import org.srm.common.mybatis.domain.ExpandDomain;

public class PoHeaderAccordingToLineOfReferenceDTO extends ExpandDomain {
    @ApiModelProperty("租户ID")
    private Long tenantId;
    @ApiModelProperty("是否根据需求执行人过滤")
    private Integer assignFlag;
    @ApiModelProperty("是否根据执行策略过滤")
    private Integer executionStrategyFlag;
    @ApiModelProperty("用户ID")
    private Long userId;
    @ApiModelProperty("申请编号")
    private String prNum;
    @ApiModelProperty("申请行号")
    private String lineNum;
    @ApiModelProperty("申请日期从")
    @JsonFormat(
            pattern = "yyyy-MM-dd"
    )
    @DateTimeFormat(
            pattern = "yyyy-MM-dd"
    )
    private Date requestDateFrom;
    @ApiModelProperty("申请日期至")
    @JsonFormat(
            pattern = "yyyy-MM-dd"
    )
    @DateTimeFormat(
            pattern = "yyyy-MM-dd"
    )
    private Date requestDateTo;
    @ApiModelProperty("采购组织ID")
    @Encrypt
    private Long purchaseOrgId;
    @ApiModelProperty("业务实体ID")
    @Encrypt
    private Long ouId;
    @ApiModelProperty("公司ID")
    @Encrypt
    private Long companyId;
    @ApiModelProperty("采购员ID")
    private Long purchaseAgentId;
    @ApiModelProperty("创建人")
    private String createdName;
    private Long createdBy;
    @ApiModelProperty("需求部门名称")
    private String unitName;
    @ApiModelProperty("需求部门Id")
    private String unitId;
    @ApiModelProperty("单据来源")
    private String prSourcePlatform;
    @ApiModelProperty("供应商ID")
    @Encrypt
    private Long supplierId;
    @ApiModelProperty("供应商租户ID")
    private Long supplierTenantId;
    @ApiModelProperty("供应商公司ID")
    @Encrypt
    private Long supplierCompanyId;
    @ApiModelProperty("商品ID")
    private String productId;
    @ApiModelProperty("商品编码")
    private String productNum;
    @ApiModelProperty("物料编码")
    private String itemCode;
    @ApiModelProperty("物料编码集合")
    private List<String> itemCodes;
    @ApiModelProperty("物料分类id")
    private Long categoryId;
    @ApiModelProperty("需求日期从")
    @JsonFormat(
            pattern = "yyyy-MM-dd"
    )
    @DateTimeFormat(
            pattern = "yyyy-MM-dd"
    )
    private Date neededDateFrom;
    @ApiModelProperty("需求日期至")
    @JsonFormat(
            pattern = "yyyy-MM-dd"
    )
    @DateTimeFormat(
            pattern = "yyyy-MM-dd"
    )
    private Date neededDateTo;
    @ApiModelProperty("收货方地址")
    private String receiverAddress;
    @ApiModelProperty("业务实体code")
    private String ouCode;
    @ApiModelProperty("公司code")
    private String companyCode;
    @ApiModelProperty("加急标识")
    private Integer urgentFlag;
    @ApiModelProperty("加急日期")
    private Date urgentDate;
    @ApiModelProperty("收货方地址")
    private String receiveAddress;
    @ApiModelProperty("申请类型id")
    private Long prTypeId;
    @ApiModelProperty("申请类型")
    private String prTypeCode;
    @ApiModelProperty("申请类型值")
    private String prTypeName;
    private String reqUserName;
    private String itemName;
    private Date currentDate;
    private String purchaseOrgCode;
    @ApiModelProperty("请求来源为寻源大厅标志")
    private Integer erpControlFlag;
    private Long prHeaderId;
    @ApiModelProperty("执行人")
    private String executorName;
    @ApiModelProperty("需求执行人id")
    private Long executedByName;
    private Integer sapFlag;
    @ApiModelProperty("电商商城父订单号")
    private String mallParentOrderNum;
    @ApiModelProperty(
            value = "申请状态",
            required = true
    )
    @LovValue(
            lovCode = "SPRM.PR_STATUS",
            meaningField = "prStatusCodeMeaning"
    )
    @NotBlank
    private String prStatusCode;
    @ApiModelProperty("前驱申请状态")
    private String previousPrStatusCode;
    @ApiModelProperty("标题")
    private String title;
    @ApiModelProperty(
            value = "申请日期",
            required = true
    )
    @DateTimeFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    private Date requestDate;
    @ApiModelProperty(
            value = "申请总额",
            required = true
    )
    private BigDecimal amount;
    @ApiModelProperty(
            value = "申请人ID",
            required = true
    )
    private Long prRequestedById;
    @ApiModelProperty("申请人")
    private String prRequestedName;
    @ApiModelProperty("联系电话")
    private String contactTelNum;
    @ApiModelProperty("采购组织名称")
    private String purchaseUnitName;
    @ApiModelProperty(
            value = "批次号",
            required = true
    )
    @NotBlank
    private String lotNum;
    @ApiModelProperty("收货地址ID")
    @Encrypt
    private Long receiverAddressId;
    @ApiModelProperty("收货地区ID")
    @Encrypt
    private Long receiverRegionId;
    @ApiModelProperty("收货联系人")
    private String receiverContactName;
    @ApiModelProperty("收货联系电话")
    private String receiverTelNum;
    @ApiModelProperty("收单地址ID")
    @Encrypt
    private Long invoiceAddressId;
    @ApiModelProperty("收单地区ID")
    @Encrypt
    private Long invoiceRegionId;
    @ApiModelProperty("收单联系人")
    private String invoiceContactName;
    @ApiModelProperty("收单联系电话")
    private String invoiceTelNum;
    @ApiModelProperty("收单邮箱")
    @Length(
            max = 100
    )
    private String receiverEmailAddress;
    @ApiModelProperty("发票抬头")
    private String invoiceTitle;
    @ApiModelProperty("税务登记号")
    private String taxRegisterNum;
    @ApiModelProperty("税务登记地址")
    private String taxRegisterAddress;
    @ApiModelProperty("税务登记公司电话")
    private String taxRegisterTel;
    @ApiModelProperty("税务登记开户行")
    private String taxRegisterBank;
    @ApiModelProperty("税务登记开户行账号")
    private String taxRegisterBankAccount;
    @ApiModelProperty("开票方式")
    private String invoiceMethodCode;
    @ApiModelProperty("开票类型")
    @LovValue(
            lovCode = "SPRM.PR_INVOICE_TYPE"
    )
    private String invoiceTypeCode;
    @Transient
    private String invoiceTypeMeaning;
    @ApiModelProperty("开票抬头类型")
    private String invoiceTitleTypeCode;
    @ApiModelProperty("开票明细类型")
    private String invoiceDetailTypeCode;
    @ApiModelProperty("关闭状态")
    private String closeStatusCode;
    @ApiModelProperty("取消状态")
    private String cancelStatusCode;
    @ApiModelProperty(
            value = "电商校验错误标志",
            required = true
    )
    private Integer ecErrorFlag;
    @ApiModelProperty("电商校验反馈")
    private String ecCheckResponseMsg;
    @ApiModelProperty("附件UUID")
    private String attachmentUuid;
    @ApiModelProperty("备注")
    private String remark;
    @ApiModelProperty("审批时间")
    private Date approvedDate;
    @ApiModelProperty("审批人ID")
    private Long approvedBy;
    @ApiModelProperty("审批意见")
    private String approvedRemark;
    @ApiModelProperty(
            name = "执行状态代码"
    )
    private String executionStatusCode;
    @ApiModelProperty(
            name = "执行时间"
    )
    private Date executedDate;
    @ApiModelProperty(
            name = "执行人ID"
    )
    private Long executedBy;
    @ApiModelProperty(
            name = "目标单据ID"
    )
    private Long executionBillId;
    @ApiModelProperty(
            name = "目标单据编码"
    )
    private String executionBillNum;
    @ApiModelProperty("目标单据附加数据")
    private String executionBillData;
    @ApiModelProperty(
            name = "同步ERP状态"
    )
    private String syncStatus;
    @ApiModelProperty(
            name = "同步用户ID"
    )
    private Long syncBy;
    @ApiModelProperty(
            name = "同步日期"
    )
    private Date syncDate;
    @ApiModelProperty(
            name = "同步说明"
    )
    private String syncRemark;
    @ApiModelProperty(
            name = "同步ERP反馈信息"
    )
    private String syncResponseMsg;
    @ApiModelProperty("总运费")
    private BigDecimal freight;
    @ApiModelProperty("电商预占订单号")
    private String ecPreorderNum;
    @ApiModelProperty("SRM商城订单号")
    private String mallOrderNum;
    @ApiModelProperty("支付方式")
    private String paymentMethodCode;
    @ApiModelProperty("支付方式名称")
    private String paymentMethodName;
    @ApiModelProperty("收单地址")
    private String invoiceAddress;
    @ApiModelProperty("电商平台code")
    private String platformCode;
    @ApiModelProperty("开票方式描述")
    private String invoiceMethodName;
    @ApiModelProperty("发票形式描述")
    private String invoiceTypeName;
    @ApiModelProperty("发票类型描述")
    private String invoiceTitleTypeName;
    @ApiModelProperty("发票明细描述")
    private String invoiceDetailTypeName;
    @ApiModelProperty("是否已评价 0：未评价  1：已评价")
    private Integer evaluateFlag;
    @ApiModelProperty("评价满意度 1：非常不满意 2：不满意 3：一般满意 4：比较满意 5：非常满意")
    private Integer satisfactionDegreeCode;
    @ApiModelProperty("评价备注")
    private String evaluateRemark;
    @ApiModelProperty("评价用户ID")
    private Long evaluateBy;
    @ApiModelProperty("评价时间")
    private Date evaluateDate;
    @ApiModelProperty("新商城标识")
    private Integer newMallFlag;
    @ApiModelProperty("是否已变更")
    private Integer changedFlag;
    @ApiModelProperty("是否外部系统审批中")
    private Integer externalApprovingFlag;
    @ApiModelProperty("本币币种")
    private String localCurrency;
    @ApiModelProperty("本币含税金额")
    private BigDecimal localCurrencyTaxSum;
    @ApiModelProperty("本币不含税金额")
    private BigDecimal localCurrencyNoTaxSum;

    private String titleLike;

    public PoHeaderAccordingToLineOfReferenceDTO() {
    }

    public void assignFilter(CustomizeSettingHelper customizeSettingHelper) {
        CustomUserDetails userDetails = DetailsHelper.getUserDetails();
        if (userDetails != null) {
            Integer assignFlagConfig = (Integer)customizeSettingHelper.queryBySettingCodeAndParse(userDetails.getTenantId(), "010908", Integer::parseInt);
            if (Integer.valueOf(1).equals(assignFlagConfig)) {
                this.assignFlag = assignFlagConfig;
                this.userId = userDetails.getUserId();
            }
        }

    }

    public void executionStrategyFilter(CustomizeSettingHelper customizeSettingHelper) {
        Integer assignFlagConfig = Flag.NO;

        try {
            assignFlagConfig = (Integer)CnfHelper.select(this.getTenantId(), "SITE.SPUC.PR.EXECUTION_STRATEGY", Integer.class).invokeWithParameter(Collections.emptyMap());
        } catch (Exception var4) {
        }

        if (Flag.YES.equals(assignFlagConfig)) {
            this.executionStrategyFlag = Flag.YES;
        }

    }

    public Long getExecutedByName() {
        return this.executedByName;
    }

    public void setExecutedByName(Long executedByName) {
        this.executedByName = executedByName;
    }

    public String getExecutorName() {
        return this.executorName;
    }

    public void setExecutorName(String executorName) {
        this.executorName = executorName;
    }

    public Long getPrHeaderId() {
        return this.prHeaderId;
    }

    public void setPrHeaderId(Long prHeaderId) {
        this.prHeaderId = prHeaderId;
    }

    public Date getCurrentDate() {
        return this.currentDate;
    }

    public void setCurrentDate(Date currentDate) {
        this.currentDate = currentDate;
    }

    public Long getPrTypeId() {
        return this.prTypeId;
    }

    public void setPrTypeId(Long prTypeId) {
        this.prTypeId = prTypeId;
    }

    public String getPrTypeCode() {
        return this.prTypeCode;
    }

    public void setPrTypeCode(String prTypeCode) {
        this.prTypeCode = prTypeCode;
    }

    public String getPrTypeName() {
        return this.prTypeName;
    }

    public void setPrTypeName(String prTypeName) {
        this.prTypeName = prTypeName;
    }

    public String getProductId() {
        return this.productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Integer getUrgentFlag() {
        return this.urgentFlag;
    }

    public void setUrgentFlag(Integer urgentFlag) {
        this.urgentFlag = urgentFlag;
    }

    public Date getUrgentDate() {
        return this.urgentDate;
    }

    public void setUrgentDate(Date urgentDate) {
        this.urgentDate = urgentDate;
    }

    public String getReceiveAddress() {
        return this.receiveAddress;
    }

    public void setReceiveAddress(String receiveAddress) {
        this.receiveAddress = receiveAddress;
    }

    public String getUnitId() {
        return this.unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public Long getSupplierTenantId() {
        return this.supplierTenantId;
    }

    public void setSupplierTenantId(Long supplierTenantId) {
        this.supplierTenantId = supplierTenantId;
    }

    public Long getSupplierCompanyId() {
        return this.supplierCompanyId;
    }

    public void setSupplierCompanyId(Long supplierCompanyId) {
        this.supplierCompanyId = supplierCompanyId;
    }

    public Long getTenantId() {
        return this.tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public String getPrNum() {
        return this.prNum;
    }

    public void setPrNum(String prNum) {
        this.prNum = prNum;
    }

    public String getLineNum() {
        return this.lineNum;
    }

    public void setLineNum(String lineNum) {
        this.lineNum = lineNum;
    }

    public Date getRequestDateFrom() {
        return this.requestDateFrom;
    }

    public void setRequestDateFrom(Date requestDateFrom) {
        this.requestDateFrom = requestDateFrom;
    }

    public Date getRequestDateTo() {
        return this.requestDateTo;
    }

    public void setRequestDateTo(Date requestDateTo) {
        this.requestDateTo = requestDateTo;
    }

    public Integer getAssignFlag() {
        return this.assignFlag;
    }

    public void setAssignFlag(Integer assignFlag) {
        this.assignFlag = assignFlag;
    }

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getPurchaseOrgId() {
        return this.purchaseOrgId;
    }

    public void setPurchaseOrgId(Long purchaseOrgId) {
        this.purchaseOrgId = purchaseOrgId;
    }

    public Long getOuId() {
        return this.ouId;
    }

    public void setOuId(Long ouId) {
        this.ouId = ouId;
    }

    public Long getCompanyId() {
        return this.companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getPurchaseAgentId() {
        return this.purchaseAgentId;
    }

    public void setPurchaseAgentId(Long purchaseAgentId) {
        this.purchaseAgentId = purchaseAgentId;
    }

    public String getCreatedName() {
        return this.createdName;
    }

    public void setCreatedName(String createdName) {
        this.createdName = createdName;
    }

    public Long getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public String getPrSourcePlatform() {
        return this.prSourcePlatform;
    }

    public void setPrSourcePlatform(String prSourcePlatform) {
        this.prSourcePlatform = prSourcePlatform;
    }

    public String getOuCode() {
        return this.ouCode;
    }

    public void setOuCode(String ouCode) {
        this.ouCode = ouCode;
    }

    public String getCompanyCode() {
        return this.companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public Long getSupplierId() {
        return this.supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public String getProductNum() {
        return this.productNum;
    }

    public void setProductNum(String productNum) {
        this.productNum = productNum;
    }

    public String getItemCode() {
        return this.itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public Date getNeededDateFrom() {
        return this.neededDateFrom;
    }

    public void setNeededDateFrom(Date neededDateFrom) {
        this.neededDateFrom = neededDateFrom;
    }

    public Date getNeededDateTo() {
        return this.neededDateTo;
    }

    public void setNeededDateTo(Date neededDateTo) {
        this.neededDateTo = neededDateTo;
    }

    public String getReceiverAddress() {
        return this.receiverAddress;
    }

    public void setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress;
    }

    public String getUnitName() {
        return this.unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getReqUserName() {
        return this.reqUserName;
    }

    public void setReqUserName(String reqUserName) {
        this.reqUserName = reqUserName;
    }

    public String getItemName() {
        return this.itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Integer getErpControlFlag() {
        return this.erpControlFlag;
    }

    public void setErpControlFlag(Integer erpControlFlag) {
        this.erpControlFlag = erpControlFlag;
    }

    public Integer getExecutionStrategyFlag() {
        return this.executionStrategyFlag;
    }

    public void setExecutionStrategyFlag(Integer executionStrategyFlag) {
        this.executionStrategyFlag = executionStrategyFlag;
    }

    public String getPurchaseOrgCode() {
        return this.purchaseOrgCode;
    }

    public void setPurchaseOrgCode(String purchaseOrgCode) {
        this.purchaseOrgCode = purchaseOrgCode;
    }

    public String getMallParentOrderNum() {
        return this.mallParentOrderNum;
    }

    public void setMallParentOrderNum(String mallParentOrderNum) {
        this.mallParentOrderNum = mallParentOrderNum;
    }

    public String getPrStatusCode() {
        return this.prStatusCode;
    }

    public void setPrStatusCode(String prStatusCode) {
        this.prStatusCode = prStatusCode;
    }

    public String getPreviousPrStatusCode() {
        return this.previousPrStatusCode;
    }

    public void setPreviousPrStatusCode(String previousPrStatusCode) {
        this.previousPrStatusCode = previousPrStatusCode;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getRequestDate() {
        return this.requestDate;
    }

    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }

    public BigDecimal getAmount() {
        return this.amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Long getPrRequestedById() {
        return this.prRequestedById;
    }

    public void setPrRequestedById(Long prRequestedById) {
        this.prRequestedById = prRequestedById;
    }

    public String getPrRequestedName() {
        return this.prRequestedName;
    }

    public void setPrRequestedName(String prRequestedName) {
        this.prRequestedName = prRequestedName;
    }

    public String getContactTelNum() {
        return this.contactTelNum;
    }

    public void setContactTelNum(String contactTelNum) {
        this.contactTelNum = contactTelNum;
    }

    public String getPurchaseUnitName() {
        return this.purchaseUnitName;
    }

    public void setPurchaseUnitName(String purchaseUnitName) {
        this.purchaseUnitName = purchaseUnitName;
    }

    public String getLotNum() {
        return this.lotNum;
    }

    public void setLotNum(String lotNum) {
        this.lotNum = lotNum;
    }

    public Long getReceiverAddressId() {
        return this.receiverAddressId;
    }

    public void setReceiverAddressId(Long receiverAddressId) {
        this.receiverAddressId = receiverAddressId;
    }

    public Long getReceiverRegionId() {
        return this.receiverRegionId;
    }

    public void setReceiverRegionId(Long receiverRegionId) {
        this.receiverRegionId = receiverRegionId;
    }

    public String getReceiverContactName() {
        return this.receiverContactName;
    }

    public void setReceiverContactName(String receiverContactName) {
        this.receiverContactName = receiverContactName;
    }

    public String getReceiverTelNum() {
        return this.receiverTelNum;
    }

    public void setReceiverTelNum(String receiverTelNum) {
        this.receiverTelNum = receiverTelNum;
    }

    public Long getInvoiceAddressId() {
        return this.invoiceAddressId;
    }

    public void setInvoiceAddressId(Long invoiceAddressId) {
        this.invoiceAddressId = invoiceAddressId;
    }

    public Long getInvoiceRegionId() {
        return this.invoiceRegionId;
    }

    public void setInvoiceRegionId(Long invoiceRegionId) {
        this.invoiceRegionId = invoiceRegionId;
    }

    public String getInvoiceContactName() {
        return this.invoiceContactName;
    }

    public void setInvoiceContactName(String invoiceContactName) {
        this.invoiceContactName = invoiceContactName;
    }

    public String getInvoiceTelNum() {
        return this.invoiceTelNum;
    }

    public void setInvoiceTelNum(String invoiceTelNum) {
        this.invoiceTelNum = invoiceTelNum;
    }

    public String getReceiverEmailAddress() {
        return this.receiverEmailAddress;
    }

    public void setReceiverEmailAddress(String receiverEmailAddress) {
        this.receiverEmailAddress = receiverEmailAddress;
    }

    public String getInvoiceTitle() {
        return this.invoiceTitle;
    }

    public void setInvoiceTitle(String invoiceTitle) {
        this.invoiceTitle = invoiceTitle;
    }

    public String getTaxRegisterNum() {
        return this.taxRegisterNum;
    }

    public void setTaxRegisterNum(String taxRegisterNum) {
        this.taxRegisterNum = taxRegisterNum;
    }

    public String getTaxRegisterAddress() {
        return this.taxRegisterAddress;
    }

    public void setTaxRegisterAddress(String taxRegisterAddress) {
        this.taxRegisterAddress = taxRegisterAddress;
    }

    public String getTaxRegisterTel() {
        return this.taxRegisterTel;
    }

    public void setTaxRegisterTel(String taxRegisterTel) {
        this.taxRegisterTel = taxRegisterTel;
    }

    public String getTaxRegisterBank() {
        return this.taxRegisterBank;
    }

    public void setTaxRegisterBank(String taxRegisterBank) {
        this.taxRegisterBank = taxRegisterBank;
    }

    public String getTaxRegisterBankAccount() {
        return this.taxRegisterBankAccount;
    }

    public void setTaxRegisterBankAccount(String taxRegisterBankAccount) {
        this.taxRegisterBankAccount = taxRegisterBankAccount;
    }

    public String getInvoiceMethodCode() {
        return this.invoiceMethodCode;
    }

    public void setInvoiceMethodCode(String invoiceMethodCode) {
        this.invoiceMethodCode = invoiceMethodCode;
    }

    public String getInvoiceTypeCode() {
        return this.invoiceTypeCode;
    }

    public void setInvoiceTypeCode(String invoiceTypeCode) {
        this.invoiceTypeCode = invoiceTypeCode;
    }

    public String getInvoiceTypeMeaning() {
        return this.invoiceTypeMeaning;
    }

    public void setInvoiceTypeMeaning(String invoiceTypeMeaning) {
        this.invoiceTypeMeaning = invoiceTypeMeaning;
    }

    public String getInvoiceTitleTypeCode() {
        return this.invoiceTitleTypeCode;
    }

    public void setInvoiceTitleTypeCode(String invoiceTitleTypeCode) {
        this.invoiceTitleTypeCode = invoiceTitleTypeCode;
    }

    public String getInvoiceDetailTypeCode() {
        return this.invoiceDetailTypeCode;
    }

    public void setInvoiceDetailTypeCode(String invoiceDetailTypeCode) {
        this.invoiceDetailTypeCode = invoiceDetailTypeCode;
    }

    public String getCloseStatusCode() {
        return this.closeStatusCode;
    }

    public void setCloseStatusCode(String closeStatusCode) {
        this.closeStatusCode = closeStatusCode;
    }

    public String getCancelStatusCode() {
        return this.cancelStatusCode;
    }

    public void setCancelStatusCode(String cancelStatusCode) {
        this.cancelStatusCode = cancelStatusCode;
    }

    public Integer getEcErrorFlag() {
        return this.ecErrorFlag;
    }

    public void setEcErrorFlag(Integer ecErrorFlag) {
        this.ecErrorFlag = ecErrorFlag;
    }

    public String getEcCheckResponseMsg() {
        return this.ecCheckResponseMsg;
    }

    public void setEcCheckResponseMsg(String ecCheckResponseMsg) {
        this.ecCheckResponseMsg = ecCheckResponseMsg;
    }

    public String getAttachmentUuid() {
        return this.attachmentUuid;
    }

    public void setAttachmentUuid(String attachmentUuid) {
        this.attachmentUuid = attachmentUuid;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getApprovedDate() {
        return this.approvedDate;
    }

    public void setApprovedDate(Date approvedDate) {
        this.approvedDate = approvedDate;
    }

    public Long getApprovedBy() {
        return this.approvedBy;
    }

    public void setApprovedBy(Long approvedBy) {
        this.approvedBy = approvedBy;
    }

    public String getApprovedRemark() {
        return this.approvedRemark;
    }

    public void setApprovedRemark(String approvedRemark) {
        this.approvedRemark = approvedRemark;
    }

    public String getExecutionStatusCode() {
        return this.executionStatusCode;
    }

    public void setExecutionStatusCode(String executionStatusCode) {
        this.executionStatusCode = executionStatusCode;
    }

    public Date getExecutedDate() {
        return this.executedDate;
    }

    public void setExecutedDate(Date executedDate) {
        this.executedDate = executedDate;
    }

    public Long getExecutedBy() {
        return this.executedBy;
    }

    public void setExecutedBy(Long executedBy) {
        this.executedBy = executedBy;
    }

    public Long getExecutionBillId() {
        return this.executionBillId;
    }

    public void setExecutionBillId(Long executionBillId) {
        this.executionBillId = executionBillId;
    }

    public String getExecutionBillNum() {
        return this.executionBillNum;
    }

    public void setExecutionBillNum(String executionBillNum) {
        this.executionBillNum = executionBillNum;
    }

    public String getExecutionBillData() {
        return this.executionBillData;
    }

    public void setExecutionBillData(String executionBillData) {
        this.executionBillData = executionBillData;
    }

    public String getSyncStatus() {
        return this.syncStatus;
    }

    public void setSyncStatus(String syncStatus) {
        this.syncStatus = syncStatus;
    }

    public Long getSyncBy() {
        return this.syncBy;
    }

    public void setSyncBy(Long syncBy) {
        this.syncBy = syncBy;
    }

    public Date getSyncDate() {
        return this.syncDate;
    }

    public void setSyncDate(Date syncDate) {
        this.syncDate = syncDate;
    }

    public String getSyncRemark() {
        return this.syncRemark;
    }

    public void setSyncRemark(String syncRemark) {
        this.syncRemark = syncRemark;
    }

    public String getSyncResponseMsg() {
        return this.syncResponseMsg;
    }

    public void setSyncResponseMsg(String syncResponseMsg) {
        this.syncResponseMsg = syncResponseMsg;
    }

    public BigDecimal getFreight() {
        return this.freight;
    }

    public void setFreight(BigDecimal freight) {
        this.freight = freight;
    }

    public String getEcPreorderNum() {
        return this.ecPreorderNum;
    }

    public void setEcPreorderNum(String ecPreorderNum) {
        this.ecPreorderNum = ecPreorderNum;
    }

    public String getMallOrderNum() {
        return this.mallOrderNum;
    }

    public void setMallOrderNum(String mallOrderNum) {
        this.mallOrderNum = mallOrderNum;
    }

    public String getPaymentMethodCode() {
        return this.paymentMethodCode;
    }

    public void setPaymentMethodCode(String paymentMethodCode) {
        this.paymentMethodCode = paymentMethodCode;
    }

    public String getPaymentMethodName() {
        return this.paymentMethodName;
    }

    public void setPaymentMethodName(String paymentMethodName) {
        this.paymentMethodName = paymentMethodName;
    }

    public String getInvoiceAddress() {
        return this.invoiceAddress;
    }

    public void setInvoiceAddress(String invoiceAddress) {
        this.invoiceAddress = invoiceAddress;
    }

    public String getPlatformCode() {
        return this.platformCode;
    }

    public void setPlatformCode(String platformCode) {
        this.platformCode = platformCode;
    }

    public String getInvoiceMethodName() {
        return this.invoiceMethodName;
    }

    public void setInvoiceMethodName(String invoiceMethodName) {
        this.invoiceMethodName = invoiceMethodName;
    }

    public String getInvoiceTypeName() {
        return this.invoiceTypeName;
    }

    public void setInvoiceTypeName(String invoiceTypeName) {
        this.invoiceTypeName = invoiceTypeName;
    }

    public String getInvoiceTitleTypeName() {
        return this.invoiceTitleTypeName;
    }

    public void setInvoiceTitleTypeName(String invoiceTitleTypeName) {
        this.invoiceTitleTypeName = invoiceTitleTypeName;
    }

    public String getInvoiceDetailTypeName() {
        return this.invoiceDetailTypeName;
    }

    public void setInvoiceDetailTypeName(String invoiceDetailTypeName) {
        this.invoiceDetailTypeName = invoiceDetailTypeName;
    }

    public Integer getEvaluateFlag() {
        return this.evaluateFlag;
    }

    public void setEvaluateFlag(Integer evaluateFlag) {
        this.evaluateFlag = evaluateFlag;
    }

    public Integer getSatisfactionDegreeCode() {
        return this.satisfactionDegreeCode;
    }

    public void setSatisfactionDegreeCode(Integer satisfactionDegreeCode) {
        this.satisfactionDegreeCode = satisfactionDegreeCode;
    }

    public String getEvaluateRemark() {
        return this.evaluateRemark;
    }

    public void setEvaluateRemark(String evaluateRemark) {
        this.evaluateRemark = evaluateRemark;
    }

    public Long getEvaluateBy() {
        return this.evaluateBy;
    }

    public void setEvaluateBy(Long evaluateBy) {
        this.evaluateBy = evaluateBy;
    }

    public Date getEvaluateDate() {
        return this.evaluateDate;
    }

    public void setEvaluateDate(Date evaluateDate) {
        this.evaluateDate = evaluateDate;
    }

    public Integer getNewMallFlag() {
        return this.newMallFlag;
    }

    public void setNewMallFlag(Integer newMallFlag) {
        this.newMallFlag = newMallFlag;
    }

    public Integer getChangedFlag() {
        return this.changedFlag;
    }

    public void setChangedFlag(Integer changedFlag) {
        this.changedFlag = changedFlag;
    }

    public Integer getExternalApprovingFlag() {
        return this.externalApprovingFlag;
    }

    public void setExternalApprovingFlag(Integer externalApprovingFlag) {
        this.externalApprovingFlag = externalApprovingFlag;
    }

    public String getLocalCurrency() {
        return this.localCurrency;
    }

    public void setLocalCurrency(String localCurrency) {
        this.localCurrency = localCurrency;
    }

    public BigDecimal getLocalCurrencyTaxSum() {
        return this.localCurrencyTaxSum;
    }

    public void setLocalCurrencyTaxSum(BigDecimal localCurrencyTaxSum) {
        this.localCurrencyTaxSum = localCurrencyTaxSum;
    }

    public BigDecimal getLocalCurrencyNoTaxSum() {
        return this.localCurrencyNoTaxSum;
    }

    public void setLocalCurrencyNoTaxSum(BigDecimal localCurrencyNoTaxSum) {
        this.localCurrencyNoTaxSum = localCurrencyNoTaxSum;
    }

    public Integer getSapFlag() {
        return this.sapFlag;
    }

    public void setSapFlag(Integer sapFlag) {
        this.sapFlag = sapFlag;
    }

    public List<String> getItemCodes() {
        return this.itemCodes;
    }

    public void setItemCodes(List<String> itemCodes) {
        this.itemCodes = itemCodes;
    }

    public Long getCategoryId() {
        return this.categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getTitleLike() {
        return titleLike;
    }

    public void setTitleLike(String titleLike) {
        this.titleLike = titleLike;
    }
}
