

package org.srm.purchasecooperation.order.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.Range;
import org.hzero.boot.platform.lov.annotation.LovValue;
import org.hzero.starter.keyencrypt.core.Encrypt;
import org.springframework.format.annotation.DateTimeFormat;
import org.srm.common.mybatis.domain.ExpandDomain;
import org.srm.purchasecooperation.order.infra.annotation.ChangeField;
import org.srm.purchasecooperation.pr.domain.entity.PrLineSupplier;

@JsonInclude(Include.NON_NULL)
public class PoHeaderAccordingToLineOfReferenceVO extends ExpandDomain {
    @Encrypt
    private Long prLineId;
    private Long priceLibraryId;
    @Encrypt
    private Long prHeaderId;
    @Encrypt
    private Long poHeaderId;
    private String prNum;

    private String title;

    private String lineNum;
    private BigDecimal quantity;
    @Encrypt
    private Long companyId;
    private String companyName;
    @ApiModelProperty("业务实体code")
    private String ouCode;
    @ApiModelProperty("公司code")
    private String companyCode;
    @Encrypt
    private Long ouId;
    private String ouName;
    @Encrypt
    private Long supplierId;
    private String supplierCode;
    private String supplierName;
    private String supplierCompanyCode;
    @Encrypt
    private Long supplierCompanyId;
    private String supplierCompanyName;
    private String itemCode;
    private String itemName;
    private String commonName;
    private String categoryName;
    private Long productId;
    private String productNum;
    private String productName;
    private Long catalogId;
    private String catalogName;
    private String currencyCode;
    private Long uomId;
    private String uomCode;
    private String uomName;
    private BigDecimal taxIncludedUnitPrice;
    private Long invOrganizationId;
    private String invOrganizationName;
    private Long inventoryId;
    private Long receiverAddressId;
    private String receiverAddress;
    private String invoiceAddress;
    private Long requestedBy;
    private String prRequestedName;
    private String contactTelNum;
    private String purchaseOrgCode;
    @JsonFormat(
            pattern = "yyyy-MM-dd"
    )
    @DateTimeFormat(
            pattern = "yyyy-MM-dd"
    )
    private Date neededDate;
    @LovValue(
            lovCode = "SPRM.SRC_PLATFORM",
            meaningField = "prSourcePlatformMeaning"
    )
    private String prSourcePlatform;
    private String prSourcePlatformMeaning;
    @Encrypt
    private Long purchaseOrgId;
    private String purchaseOrgName;
    private BigDecimal amount;
    private BigDecimal lineAmount;
    private BigDecimal taxIncludedLineAmount;
    private BigDecimal enteredTaxIncludedPrice;
    private BigDecimal unitPrice;
    private BigDecimal noUnitPrice;
    @Encrypt
    private Long agentId;
    private Long categoryId;
    private Long taxId;
    private BigDecimal taxRate;
    @JsonFormat(
            pattern = "yyyy-MM-dd"
    )
    @DateTimeFormat(
            pattern = "yyyy-MM-dd"
    )
    private Date needNyDate;
    private Long unitId;
    private String unitName;
    private Long hObjectVersionNumber;
    private Long supplierTenantId;
    @Encrypt
    private Long itemId;
    private Integer urgentFlag;
    private Date urgentDate;
    private Long prTypeId;
    private String prTypeCode;
    private String prTypeName;
    private String surfaceTreatFlag;
    private String pcNum;
    private String itemModel;
    private String itemSpecs;
    private BigDecimal lastPurchasePrice;
    private String pcHeaderId;
    private BigDecimal jdPrice;
    private String drawingNum;
    private String drawingVersion;
    private String remark;
    private String brand;
    private Long orderTypeId;
    private String orderTypeCode;
    private String orderTypeName;
    @LovValue(
            lovCode = "SPUC.PR_LINE_PROJECT_CATEHORY",
            meaningField = "projectCategoryMeaning"
    )
    private String projectCategory;
    private String attachmentUuid;
    private String projectCategoryMeaning;
    @ApiModelProperty("收货方地址")
    private String receiveAddress;
    private Integer sapFlag;
    private List<PoHeaderAccordingToLineOfReferenceVO> prLineList;
    private String benchmarkPriceType;
    private BigDecimal unitPriceBatch;
    private Boolean referencePriceDisplayFlag;
    private Long accountAssignTypeId;
    private String accountAssignTypeCode;
    private Long costId;
    private String costCode;
    private Long accountSubjectId;
    private String accountSubjectNum;
    private String wbsCode;
    private String wbs;
    private String createByUnitName;
    private String createByUnitId;
    Integer freightLineFlag;
    private String serviceCodeRefPrice;
    private Long selectSupplierCompanyId;
    private Long selectSupplierTenantId;
    private String selectSupplierCode;
    private String selectSupplierCompanyName;
    @ApiModelProperty("收货联系人")
    private String receiveContactName;
    @ApiModelProperty("收货联系电话")
    private String receiveTelNum;
    private String prToPoerrorMessage;
    @ApiModelProperty("本币不含税金额")
    private BigDecimal localCurrencyNoTaxSum;
    @ApiModelProperty("本币含税金额")
    private BigDecimal localCurrencyTaxSum;
    @ApiModelProperty("本币不含税单价")
    private BigDecimal localCurrencyNoTaxUnit;
    @ApiModelProperty("本币含税单价")
    private BigDecimal localCurrencyTaxUnit;
    @ApiModelProperty("汇率")
    private BigDecimal exchangeRate;
    @ApiModelProperty("汇率日期")
    private Date exchangeRateDate;
    @ApiModelProperty("阶梯价判断标识")
    private Integer ladderQuotationFlag;
    @ApiModelProperty("供应商集合")
    private List<PrLineSupplier> supplierList;
    private Long holdPcHeaderId;
    private Long holdPcLineId;
    private String contractNum;
    private Long ladderPriceLibId;
    @ApiModelProperty(
            value = "租户ID",
            required = true
    )
    private Long tenantId;
    @ApiModelProperty(
            value = "展示行号",
            required = true
    )
    @NotBlank
    private String displayLineNum;
    @ApiModelProperty("物料ABC属性")
    private String itemAbcClass;
    @ApiModelProperty("项目号")
    private String projectNum;
    @ApiModelProperty("项目名称")
    private String projectName;
    @ApiModelProperty("项目号车号")
    private String craneNum;
    @LovValue(
            lovCode = "SPRM.PR_EXECUTION_STATUS",
            meaningField = "executionStatusMeaning"
    )
    @ApiModelProperty("执行状态代码")
    private String executionStatusCode;
    @ApiModelProperty("执行时间")
    private Date executedDate;
    @ApiModelProperty("执行人ID")
    private Long executedBy;
    @ApiModelProperty("目标单据ID")
    private Long executionBillId;
    @ApiModelProperty("目标单据编码")
    private String executionBillNum;
    @ApiModelProperty("目标单据附加数据")
    private String executionBillData;
    @ApiModelProperty(
            value = "分配标识",
            required = true
    )
    @NotNull
    private Integer assignedFlag;
    @ApiModelProperty("分配时间")
    private Date assignedDate;
    @ApiModelProperty("分配人ID")
    private Long assignedBy;
    @ApiModelProperty("分配原因")
    private String assignedRemark;
    @ApiModelProperty(
            value = "取消标识",
            required = true
    )
    @NotNull
    private Integer cancelledFlag;
    @ApiModelProperty("取消时间")
    private Date cancelledDate;
    @ApiModelProperty("取消人ID")
    private Long cancelledBy;
    @ApiModelProperty("取消原因")
    private String cancelledRemark;
    @ApiModelProperty(
            value = "关闭标识",
            required = true
    )
    @NotNull
    private Integer closedFlag;
    @ApiModelProperty("关闭时间")
    private Date closedDate;
    @ApiModelProperty("关闭人ID")
    private Long closedBy;
    @ApiModelProperty("关闭原因")
    private String closedRemark;
    @ApiModelProperty(
            value = "暂挂标志",
            required = true
    )
    @NotNull
    private Integer suspendFlag;
    @ApiModelProperty("暂挂日期")
    private Date suspendDate;
    @ApiModelProperty("暂挂说明")
    private String suspendRemark;
    @ApiModelProperty(
            value = "异常标志",
            required = true
    )
    @NotNull
    private Integer incorrectFlag;
    @ApiModelProperty("异常发生时间")
    private Date incorrectDate;
    @ApiModelProperty("异常信息")
    private String incorrectMsg;
    @ApiModelProperty("可开专票标识")
    @Range(
            max = 1L
    )
    private Integer canVatFlag;
    @ApiModelProperty("行运费")
    private BigDecimal lineFreight;
    @ApiModelProperty("目标头单据ID")
    private Long executionHeaderBillId;
    @ApiModelProperty("目标头单据编码")
    private String executionHeaderBillNum;
    @ApiModelProperty("申请日期")
    private Date requestDate;
    @ApiModelProperty("采购员ID")
    @ChangeField(
            name = "采购员",
            table = "hpfm_purchase_agent",
            field = "purchase_agent_name",
            quaryField = "purchase_agent_id"
    )
    @Encrypt
    private Long purchaseAgentId;
    @ApiModelProperty("ERP编辑状态")
    private String erpEditStatus;
    @ApiModelProperty("版本号")
    private Long objectVersionNumber;
    @ApiModelProperty("采购申请行ID")
    @Encrypt
    private Long poLineId;
    @ApiModelProperty("属性")
    private String itemProperties;
    @ApiModelProperty("保管人用户ID")
    @ChangeField(
            name = "保管人",
            table = "iam_user",
            field = "real_name",
            quaryField = "id"
    )
    private Long keeperUserId;
    @ApiModelProperty("验收人用户ID")
    @ChangeField(
            name = "验收人",
            table = "iam_user",
            field = "real_name",
            quaryField = "id"
    )
    private Long accepterUserId;
    @ApiModelProperty("费用承担人用户ID")
    private Long costPayerUserId;
    @ApiModelProperty("地点，值集SCUX.HYTE.SPRM.PR_LINE.ADDRESS")
    private String address;
    @ApiModelProperty("内部订单号")
    private String innerPoNum;
    @ApiModelProperty("供应商料号")
    private String supplierItemCode;
    @ApiModelProperty("批次号")
    private String batchNo;
    @ApiModelProperty("费用承担部门id")
    @ChangeField(
            name = "费用承担部门",
            table = "hpfm_unit",
            field = "unit_name",
            quaryField = "unit_id"
    )
    @Encrypt
    private String expBearDepId;
    private String expBearDep;
    private String costAnchDepId;
    private String costAnchDepDesc;
    @ApiModelProperty("供应商料号描述")
    private String supplierItemName;
    @ApiModelProperty("基准价")
    private BigDecimal benchmarkPrice;
    @ApiModelProperty("涨跌幅")
    private BigDecimal changePercent;
    @ApiModelProperty("资产")
    private String assets;
    @ApiModelProperty("资产子编号")
    private String assetChildNum;
    @ApiModelProperty("含税单价(不含运费)")
    private BigDecimal taxWithoutFreightPrice;
    @ApiModelProperty("框架协议编号")
    private String frameAgreementNum;
    @ApiModelProperty("质量标准")
    private String qualityStandard;
    @ApiModelProperty("预算内外标识")
    private Integer budgetIoFlag;
    @ApiModelProperty("预算单价(含税)")
    private BigDecimal taxIncludedBudgetUnitPrice;
    @ApiModelProperty("执行策略")
    @LovValue(
            lovCode = "SPRM.EXECUTION_STRATEGY",
            meaningField = "executionStrategyMeaning"
    )
    private String executionStrategyCode;
    @ApiModelProperty("预算科目ID")
    @Encrypt
    private Long budgetAccountId;
    @ApiModelProperty("预算科目编码")
    private String budgetAccountNum;
    @ApiModelProperty("收货信息")
    private String receiverInformation;
    @ApiModelProperty("特殊商品标识")
    private Integer businessCardFlag;
    @ApiModelProperty("卡片用户ID")
    private Long cartUserId;
    @ApiModelProperty("卡片类型 GROUP/EMPLOYEE")
    private String cartUserType;
    @ApiModelProperty("自动生成订单成功失败标识")
    private String changeOrderCode;
    @ApiModelProperty("订单生成失败信息")
    private String changeOrderMessage;
    @ApiModelProperty("预算单据id")
    private String documentId;
    @ApiModelProperty("预算部门编号")
    private String budgetAccountDeptno;
    @ApiModelProperty("预算金额")
    private BigDecimal budgetAccountPrice;
    private BigDecimal occupiedQuantity;
    private BigDecimal restPoQuantity;
    private BigDecimal thisOrderQuantity;

    public PoHeaderAccordingToLineOfReferenceVO() {
        this.referencePriceDisplayFlag = Boolean.FALSE;
    }

    public String getBenchmarkPriceType() {
        return this.benchmarkPriceType;
    }

    public void setBenchmarkPriceType(String benchmarkPriceType) {
        this.benchmarkPriceType = benchmarkPriceType;
    }

    public BigDecimal getUnitPriceBatch() {
        return this.unitPriceBatch;
    }

    public void setUnitPriceBatch(BigDecimal unitPriceBatch) {
        this.unitPriceBatch = unitPriceBatch;
    }

    public Boolean getReferencePriceDisplayFlag() {
        return this.referencePriceDisplayFlag;
    }

    public void setReferencePriceDisplayFlag(Boolean referencePriceDisplayFlag) {
        this.referencePriceDisplayFlag = referencePriceDisplayFlag;
    }

    public String getReceiveAddress() {
        return this.receiveAddress;
    }

    public void setReceiveAddress(String receiveAddress) {
        this.receiveAddress = receiveAddress;
    }

    public String getDocumentId() {
        return this.documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public Long getTenantId() {
        return this.tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public Integer getLadderQuotationFlag() {
        return this.ladderQuotationFlag;
    }

    public void setLadderQuotationFlag(Integer ladderQuotationFlag) {
        this.ladderQuotationFlag = ladderQuotationFlag;
    }

    public Long getHoldPcHeaderId() {
        return this.holdPcHeaderId;
    }

    public void setHoldPcHeaderId(Long holdPcHeaderId) {
        this.holdPcHeaderId = holdPcHeaderId;
    }

    public Long getHoldPcLineId() {
        return this.holdPcLineId;
    }

    public void setHoldPcLineId(Long holdPcLineId) {
        this.holdPcLineId = holdPcLineId;
    }

    public String getContractNum() {
        return this.contractNum;
    }

    public void setContractNum(String contractNum) {
        this.contractNum = contractNum;
    }

    public Long getLadderPriceLibId() {
        return this.ladderPriceLibId;
    }

    public void setLadderPriceLibId(Long ladderPriceLibId) {
        this.ladderPriceLibId = ladderPriceLibId;
    }

    public BigDecimal getLocalCurrencyNoTaxSum() {
        return this.localCurrencyNoTaxSum;
    }

    public void setLocalCurrencyNoTaxSum(BigDecimal localCurrencyNoTaxSum) {
        this.localCurrencyNoTaxSum = localCurrencyNoTaxSum;
    }

    public BigDecimal getLocalCurrencyTaxSum() {
        return this.localCurrencyTaxSum;
    }

    public void setLocalCurrencyTaxSum(BigDecimal localCurrencyTaxSum) {
        this.localCurrencyTaxSum = localCurrencyTaxSum;
    }

    public BigDecimal getLocalCurrencyNoTaxUnit() {
        return this.localCurrencyNoTaxUnit;
    }

    public void setLocalCurrencyNoTaxUnit(BigDecimal localCurrencyNoTaxUnit) {
        this.localCurrencyNoTaxUnit = localCurrencyNoTaxUnit;
    }

    public BigDecimal getLocalCurrencyTaxUnit() {
        return this.localCurrencyTaxUnit;
    }

    public void setLocalCurrencyTaxUnit(BigDecimal localCurrencyTaxUnit) {
        this.localCurrencyTaxUnit = localCurrencyTaxUnit;
    }

    public BigDecimal getExchangeRate() {
        return this.exchangeRate;
    }

    public void setExchangeRate(BigDecimal exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public Date getExchangeRateDate() {
        return this.exchangeRateDate;
    }

    public void setExchangeRateDate(Date exchangeRateDate) {
        this.exchangeRateDate = exchangeRateDate;
    }

    public BigDecimal getNoUnitPrice() {
        return this.noUnitPrice;
    }

    public void setNoUnitPrice(BigDecimal noUnitPrice) {
        this.noUnitPrice = noUnitPrice;
    }

    public String getPrToPoerrorMessage() {
        return this.prToPoerrorMessage;
    }

    public void setPrToPoerrorMessage(String prToPoerrorMessage) {
        this.prToPoerrorMessage = prToPoerrorMessage;
    }

    public String getServiceCodeRefPrice() {
        return this.serviceCodeRefPrice;
    }

    public void setServiceCodeRefPrice(String serviceCodeRefPrice) {
        this.serviceCodeRefPrice = serviceCodeRefPrice;
    }

    public Long getSelectSupplierCompanyId() {
        return this.selectSupplierCompanyId;
    }

    public void setSelectSupplierCompanyId(Long selectSupplierCompanyId) {
        this.selectSupplierCompanyId = selectSupplierCompanyId;
    }

    public Long getSelectSupplierTenantId() {
        return this.selectSupplierTenantId;
    }

    public void setSelectSupplierTenantId(Long selectSupplierTenantId) {
        this.selectSupplierTenantId = selectSupplierTenantId;
    }

    public String getSelectSupplierCode() {
        return this.selectSupplierCode;
    }

    public void setSelectSupplierCode(String selectSupplierCode) {
        this.selectSupplierCode = selectSupplierCode;
    }

    public String getSelectSupplierCompanyName() {
        return this.selectSupplierCompanyName;
    }

    public void setSelectSupplierCompanyName(String selectSupplierCompanyName) {
        this.selectSupplierCompanyName = selectSupplierCompanyName;
    }

    public String getProjectCategory() {
        return this.projectCategory;
    }

    public String getCreateByUnitName() {
        return this.createByUnitName;
    }

    public void setCreateByUnitName(String createByUnitName) {
        this.createByUnitName = createByUnitName;
    }

    public String getCreateByUnitId() {
        return this.createByUnitId;
    }

    public void setCreateByUnitId(String createByUnitId) {
        this.createByUnitId = createByUnitId;
    }

    public void setProjectCategory(String projectCategory) {
        this.projectCategory = projectCategory;
    }

    public String getProjectCategoryMeaning() {
        return this.projectCategoryMeaning;
    }

    public void setProjectCategoryMeaning(String projectCategoryMeaning) {
        this.projectCategoryMeaning = projectCategoryMeaning;
    }

    public Long getOrderTypeId() {
        return this.orderTypeId;
    }

    public void setOrderTypeId(Long orderTypeId) {
        this.orderTypeId = orderTypeId;
    }

    public String getOrderTypeCode() {
        return this.orderTypeCode;
    }

    public void setOrderTypeCode(String orderTypeCode) {
        this.orderTypeCode = orderTypeCode;
    }

    public String getOrderTypeName() {
        return this.orderTypeName;
    }

    public void setOrderTypeName(String orderTypeName) {
        this.orderTypeName = orderTypeName;
    }

    public String getBrand() {
        return this.brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public String getCommonName() {
        return this.commonName;
    }

    public void setCommonName(String commonName) {
        this.commonName = commonName;
    }

    public BigDecimal getLastPurchasePrice() {
        return this.lastPurchasePrice;
    }

    public void setLastPurchasePrice(BigDecimal lastPurchasePrice) {
        this.lastPurchasePrice = lastPurchasePrice;
    }

    public String getPcHeaderId() {
        return this.pcHeaderId;
    }

    public void setPcHeaderId(String pcHeaderId) {
        this.pcHeaderId = pcHeaderId;
    }

    public BigDecimal getJdPrice() {
        return this.jdPrice;
    }

    public void setJdPrice(BigDecimal jdPrice) {
        this.jdPrice = jdPrice;
    }

    public String getDrawingNum() {
        return this.drawingNum;
    }

    public void setDrawingNum(String drawingNum) {
        this.drawingNum = drawingNum;
    }

    public String getDrawingVersion() {
        return this.drawingVersion;
    }

    public void setDrawingVersion(String drawingVersion) {
        this.drawingVersion = drawingVersion;
    }

    public String getSurfaceTreatFlag() {
        return this.surfaceTreatFlag;
    }

    public void setSurfaceTreatFlag(String surfaceTreatFlag) {
        this.surfaceTreatFlag = surfaceTreatFlag;
    }

    public String getPcNum() {
        return this.pcNum;
    }

    public void setPcNum(String pcNum) {
        this.pcNum = pcNum;
    }

    public String getItemModel() {
        return this.itemModel;
    }

    public void setItemModel(String itemModel) {
        this.itemModel = itemModel;
    }

    public String getItemSpecs() {
        return this.itemSpecs;
    }

    public void setItemSpecs(String itemSpecs) {
        this.itemSpecs = itemSpecs;
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

    public String getUnitName() {
        return this.unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public Long getUnitId() {
        return this.unitId;
    }

    public void setUnitId(Long unitId) {
        this.unitId = unitId;
    }

    public Long getItemId() {
        return this.itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Long getSupplierTenantId() {
        return this.supplierTenantId;
    }

    public void setSupplierTenantId(Long supplierTenantId) {
        this.supplierTenantId = supplierTenantId;
    }

    public String getInvoiceAddress() {
        return this.invoiceAddress;
    }

    public void setInvoiceAddress(String invoiceAddress) {
        this.invoiceAddress = invoiceAddress;
    }

    public Long gethObjectVersionNumber() {
        return this.hObjectVersionNumber;
    }

    public void sethObjectVersionNumber(Long hObjectVersionNumber) {
        this.hObjectVersionNumber = hObjectVersionNumber;
    }

    public Date getNeedNyDate() {
        return this.needNyDate;
    }

    public void setNeedNyDate(Date needNyDate) {
        this.needNyDate = needNyDate;
    }

    public Long getPurchaseOrgId() {
        return this.purchaseOrgId;
    }

    public void setPurchaseOrgId(Long purchaseOrgId) {
        this.purchaseOrgId = purchaseOrgId;
    }

    public String getPurchaseOrgName() {
        return this.purchaseOrgName;
    }

    public void setPurchaseOrgName(String purchaseOrgName) {
        this.purchaseOrgName = purchaseOrgName;
    }

    public BigDecimal getAmount() {
        return this.amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getLineAmount() {
        return this.lineAmount;
    }

    public void setLineAmount(BigDecimal lineAmount) {
        this.lineAmount = lineAmount;
    }

    public BigDecimal getTaxIncludedLineAmount() {
        return this.taxIncludedLineAmount;
    }

    public void setTaxIncludedLineAmount(BigDecimal taxIncludedLineAmount) {
        this.taxIncludedLineAmount = taxIncludedLineAmount;
    }

    public BigDecimal getEnteredTaxIncludedPrice() {
        return this.enteredTaxIncludedPrice;
    }

    public void setEnteredTaxIncludedPrice(BigDecimal enteredTaxIncludedPrice) {
        this.enteredTaxIncludedPrice = enteredTaxIncludedPrice;
    }

    public BigDecimal getUnitPrice() {
        return this.unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Long getAgentId() {
        return this.agentId;
    }

    public void setAgentId(Long agentId) {
        this.agentId = agentId;
    }

    public Long getCategoryId() {
        return this.categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getTaxId() {
        return this.taxId;
    }

    public void setTaxId(Long taxId) {
        this.taxId = taxId;
    }

    public BigDecimal getTaxRate() {
        return this.taxRate;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    public Long getPrLineId() {
        return this.prLineId;
    }

    public PoHeaderAccordingToLineOfReferenceVO setPrLineId(Long prLineId) {
        this.prLineId = prLineId;
        return this;
    }

    public Long getPrHeaderId() {
        return this.prHeaderId;
    }

    public PoHeaderAccordingToLineOfReferenceVO setPrHeaderId(Long prHeaderId) {
        this.prHeaderId = prHeaderId;
        return this;
    }

    public String getPrNum() {
        return this.prNum;
    }

    public PoHeaderAccordingToLineOfReferenceVO setPrNum(String prNum) {
        this.prNum = prNum;
        return this;
    }

    public String getLineNum() {
        return this.lineNum;
    }

    public PoHeaderAccordingToLineOfReferenceVO setLineNum(String lineNum) {
        this.lineNum = lineNum;
        return this;
    }

    public BigDecimal getQuantity() {
        return this.quantity;
    }

    public PoHeaderAccordingToLineOfReferenceVO setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
        return this;
    }

    public Long getCompanyId() {
        return this.companyId;
    }

    public PoHeaderAccordingToLineOfReferenceVO setCompanyId(Long companyId) {
        this.companyId = companyId;
        return this;
    }

    public String getPurchaseOrgCode() {
        return this.purchaseOrgCode;
    }

    public void setPurchaseOrgCode(String purchaseOrgCode) {
        this.purchaseOrgCode = purchaseOrgCode;
    }

    public String getCompanyName() {
        return this.companyName;
    }

    public PoHeaderAccordingToLineOfReferenceVO setCompanyName(String companyName) {
        this.companyName = companyName;
        return this;
    }

    public Long getOuId() {
        return this.ouId;
    }

    public PoHeaderAccordingToLineOfReferenceVO setOuId(Long ouId) {
        this.ouId = ouId;
        return this;
    }

    public Long getProductId() {
        return this.productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getOuName() {
        return this.ouName;
    }

    public PoHeaderAccordingToLineOfReferenceVO setOuName(String ouName) {
        this.ouName = ouName;
        return this;
    }

    public Long getSupplierId() {
        return this.supplierId;
    }

    public PoHeaderAccordingToLineOfReferenceVO setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
        return this;
    }

    public String getSupplierName() {
        return this.supplierName;
    }

    public PoHeaderAccordingToLineOfReferenceVO setSupplierName(String supplierName) {
        this.supplierName = supplierName;
        return this;
    }

    public String getItemCode() {
        return this.itemCode;
    }

    public PoHeaderAccordingToLineOfReferenceVO setItemCode(String itemCode) {
        this.itemCode = itemCode;
        return this;
    }

    public String getItemName() {
        return this.itemName;
    }

    public PoHeaderAccordingToLineOfReferenceVO setItemName(String itemName) {
        this.itemName = itemName;
        return this;
    }

    public String getCategoryName() {
        return this.categoryName;
    }

    public PoHeaderAccordingToLineOfReferenceVO setCategoryName(String categoryName) {
        this.categoryName = categoryName;
        return this;
    }

    public String getProductNum() {
        return this.productNum;
    }

    public PoHeaderAccordingToLineOfReferenceVO setProductNum(String productNum) {
        this.productNum = productNum;
        return this;
    }

    public String getProductName() {
        return this.productName;
    }

    public PoHeaderAccordingToLineOfReferenceVO setProductName(String productName) {
        this.productName = productName;
        return this;
    }

    public Long getCatalogId() {
        return this.catalogId;
    }

    public PoHeaderAccordingToLineOfReferenceVO setCatalogId(Long catalogId) {
        this.catalogId = catalogId;
        return this;
    }

    public String getCatalogName() {
        return this.catalogName;
    }

    public PoHeaderAccordingToLineOfReferenceVO setCatalogName(String catalogName) {
        this.catalogName = catalogName;
        return this;
    }

    public String getCurrencyCode() {
        return this.currencyCode;
    }

    public PoHeaderAccordingToLineOfReferenceVO setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
        return this;
    }

    public Long getUomId() {
        return this.uomId;
    }

    public PoHeaderAccordingToLineOfReferenceVO setUomId(Long uomId) {
        this.uomId = uomId;
        return this;
    }

    public String getUomCode() {
        return this.uomCode;
    }

    public PoHeaderAccordingToLineOfReferenceVO setUomCode(String uomCode) {
        this.uomCode = uomCode;
        return this;
    }

    public String getUomName() {
        return this.uomName;
    }

    public void setUomName(String uomName) {
        this.uomName = uomName;
    }

    public BigDecimal getTaxIncludedUnitPrice() {
        return this.taxIncludedUnitPrice;
    }

    public PoHeaderAccordingToLineOfReferenceVO setTaxIncludedUnitPrice(BigDecimal taxIncludedUnitPrice) {
        this.taxIncludedUnitPrice = taxIncludedUnitPrice;
        return this;
    }

    public Long getInvOrganizationId() {
        return this.invOrganizationId;
    }

    public PoHeaderAccordingToLineOfReferenceVO setInvOrganizationId(Long invOrganizationId) {
        this.invOrganizationId = invOrganizationId;
        return this;
    }

    public Long getInventoryId() {
        return this.inventoryId;
    }

    public void setInventoryId(Long inventoryId) {
        this.inventoryId = inventoryId;
    }

    public String getInvOrganizationName() {
        return this.invOrganizationName;
    }

    public PoHeaderAccordingToLineOfReferenceVO setInvOrganizationName(String invOrganizationName) {
        this.invOrganizationName = invOrganizationName;
        return this;
    }

    public Long getReceiverAddressId() {
        return this.receiverAddressId;
    }

    public PoHeaderAccordingToLineOfReferenceVO setReceiverAddressId(Long receiverAddressId) {
        this.receiverAddressId = receiverAddressId;
        return this;
    }

    public String getReceiverAddress() {
        return this.receiverAddress;
    }

    public PoHeaderAccordingToLineOfReferenceVO setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress;
        return this;
    }

    public Long getRequestedBy() {
        return this.requestedBy;
    }

    public PoHeaderAccordingToLineOfReferenceVO setRequestedBy(Long requestedBy) {
        this.requestedBy = requestedBy;
        return this;
    }

    public Long getPriceLibraryId() {
        return this.priceLibraryId;
    }

    public void setPriceLibraryId(Long priceLibraryId) {
        this.priceLibraryId = priceLibraryId;
    }

    public String getPrRequestedName() {
        return this.prRequestedName;
    }

    public PoHeaderAccordingToLineOfReferenceVO setPrRequestedName(String prRequestedName) {
        this.prRequestedName = prRequestedName;
        return this;
    }

    public String getContactTelNum() {
        return this.contactTelNum;
    }

    public PoHeaderAccordingToLineOfReferenceVO setContactTelNum(String contactTelNum) {
        this.contactTelNum = contactTelNum;
        return this;
    }

    public Date getNeededDate() {
        return this.neededDate;
    }

    public PoHeaderAccordingToLineOfReferenceVO setNeededDate(Date neededDate) {
        this.neededDate = neededDate;
        return this;
    }

    public String getPrSourcePlatform() {
        return this.prSourcePlatform;
    }

    public PoHeaderAccordingToLineOfReferenceVO setPrSourcePlatform(String prSourcePlatform) {
        this.prSourcePlatform = prSourcePlatform;
        return this;
    }

    public String getPrSourcePlatformMeaning() {
        return this.prSourcePlatformMeaning;
    }

    public PoHeaderAccordingToLineOfReferenceVO setPrSourcePlatformMeaning(String prSourcePlatformMeaning) {
        this.prSourcePlatformMeaning = prSourcePlatformMeaning;
        return this;
    }

    public Long getPoHeaderId() {
        return this.poHeaderId;
    }

    public PoHeaderAccordingToLineOfReferenceVO setPoHeaderId(Long poHeaderId) {
        this.poHeaderId = poHeaderId;
        return this;
    }

    public String getSupplierCode() {
        return this.supplierCode;
    }

    public PoHeaderAccordingToLineOfReferenceVO setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
        return this;
    }

    public Long getSupplierCompanyId() {
        return this.supplierCompanyId;
    }

    public PoHeaderAccordingToLineOfReferenceVO setSupplierCompanyId(Long supplierCompanyId) {
        this.supplierCompanyId = supplierCompanyId;
        return this;
    }

    public String getSupplierCompanyName() {
        return this.supplierCompanyName;
    }

    public PoHeaderAccordingToLineOfReferenceVO setSupplierCompanyName(String supplierCompanyName) {
        this.supplierCompanyName = supplierCompanyName;
        return this;
    }

    public BigDecimal getOccupiedQuantity() {
        return this.occupiedQuantity;
    }

    public void setOccupiedQuantity(BigDecimal occupiedQuantity) {
        this.occupiedQuantity = occupiedQuantity;
    }

    public BigDecimal getRestPoQuantity() {
        return this.restPoQuantity;
    }

    public void setRestPoQuantity(BigDecimal restPoQuantity) {
        this.restPoQuantity = restPoQuantity;
    }

    public BigDecimal getThisOrderQuantity() {
        return this.thisOrderQuantity;
    }

    public void setThisOrderQuantity(BigDecimal thisOrderQuantity) {
        this.thisOrderQuantity = thisOrderQuantity;
    }

    public Long getAccountAssignTypeId() {
        return this.accountAssignTypeId;
    }

    public void setAccountAssignTypeId(Long accountAssignTypeId) {
        this.accountAssignTypeId = accountAssignTypeId;
    }

    public String getAccountAssignTypeCode() {
        return this.accountAssignTypeCode;
    }

    public void setAccountAssignTypeCode(String accountAssignTypeCode) {
        this.accountAssignTypeCode = accountAssignTypeCode;
    }

    public Long getCostId() {
        return this.costId;
    }

    public void setCostId(Long costId) {
        this.costId = costId;
    }

    public String getCostCode() {
        return this.costCode;
    }

    public void setCostCode(String costCode) {
        this.costCode = costCode;
    }

    public Long getAccountSubjectId() {
        return this.accountSubjectId;
    }

    public void setAccountSubjectId(Long accountSubjectId) {
        this.accountSubjectId = accountSubjectId;
    }

    public String getAccountSubjectNum() {
        return this.accountSubjectNum;
    }

    public void setAccountSubjectNum(String accountSubjectNum) {
        this.accountSubjectNum = accountSubjectNum;
    }

    public String getWbsCode() {
        return this.wbsCode;
    }

    public void setWbsCode(String wbsCode) {
        this.wbsCode = wbsCode;
    }

    public String getWbs() {
        return this.wbs;
    }

    public void setWbs(String wbs) {
        this.wbs = wbs;
    }

    public String getAttachmentUuid() {
        return this.attachmentUuid;
    }

    public void setAttachmentUuid(String attachmentUuid) {
        this.attachmentUuid = attachmentUuid;
    }

    public Integer getFreightLineFlag() {
        return this.freightLineFlag;
    }

    public void setFreightLineFlag(Integer freightLineFlag) {
        this.freightLineFlag = freightLineFlag;
    }

    public String getReceiveContactName() {
        return this.receiveContactName;
    }

    public void setReceiveContactName(final String receiveContactName) {
        this.receiveContactName = receiveContactName;
    }

    public String getReceiveTelNum() {
        return this.receiveTelNum;
    }

    public void setReceiveTelNum(final String receiveTelNum) {
        this.receiveTelNum = receiveTelNum;
    }

    public String getDisplayLineNum() {
        return this.displayLineNum;
    }

    public void setDisplayLineNum(String displayLineNum) {
        this.displayLineNum = displayLineNum;
    }

    public String getItemAbcClass() {
        return this.itemAbcClass;
    }

    public void setItemAbcClass(String itemAbcClass) {
        this.itemAbcClass = itemAbcClass;
    }

    public String getProjectNum() {
        return this.projectNum;
    }

    public void setProjectNum(String projectNum) {
        this.projectNum = projectNum;
    }

    public String getProjectName() {
        return this.projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getCraneNum() {
        return this.craneNum;
    }

    public void setCraneNum(String craneNum) {
        this.craneNum = craneNum;
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

    public Integer getAssignedFlag() {
        return this.assignedFlag;
    }

    public void setAssignedFlag(Integer assignedFlag) {
        this.assignedFlag = assignedFlag;
    }

    public Date getAssignedDate() {
        return this.assignedDate;
    }

    public void setAssignedDate(Date assignedDate) {
        this.assignedDate = assignedDate;
    }

    public Long getAssignedBy() {
        return this.assignedBy;
    }

    public void setAssignedBy(Long assignedBy) {
        this.assignedBy = assignedBy;
    }

    public String getAssignedRemark() {
        return this.assignedRemark;
    }

    public void setAssignedRemark(String assignedRemark) {
        this.assignedRemark = assignedRemark;
    }

    public Integer getCancelledFlag() {
        return this.cancelledFlag;
    }

    public void setCancelledFlag(Integer cancelledFlag) {
        this.cancelledFlag = cancelledFlag;
    }

    public Date getCancelledDate() {
        return this.cancelledDate;
    }

    public void setCancelledDate(Date cancelledDate) {
        this.cancelledDate = cancelledDate;
    }

    public Long getCancelledBy() {
        return this.cancelledBy;
    }

    public void setCancelledBy(Long cancelledBy) {
        this.cancelledBy = cancelledBy;
    }

    public String getCancelledRemark() {
        return this.cancelledRemark;
    }

    public void setCancelledRemark(String cancelledRemark) {
        this.cancelledRemark = cancelledRemark;
    }

    public Integer getClosedFlag() {
        return this.closedFlag;
    }

    public void setClosedFlag(Integer closedFlag) {
        this.closedFlag = closedFlag;
    }

    public Date getClosedDate() {
        return this.closedDate;
    }

    public void setClosedDate(Date closedDate) {
        this.closedDate = closedDate;
    }

    public Long getClosedBy() {
        return this.closedBy;
    }

    public void setClosedBy(Long closedBy) {
        this.closedBy = closedBy;
    }

    public String getClosedRemark() {
        return this.closedRemark;
    }

    public void setClosedRemark(String closedRemark) {
        this.closedRemark = closedRemark;
    }

    public Integer getSuspendFlag() {
        return this.suspendFlag;
    }

    public void setSuspendFlag(Integer suspendFlag) {
        this.suspendFlag = suspendFlag;
    }

    public Date getSuspendDate() {
        return this.suspendDate;
    }

    public void setSuspendDate(Date suspendDate) {
        this.suspendDate = suspendDate;
    }

    public String getSuspendRemark() {
        return this.suspendRemark;
    }

    public void setSuspendRemark(String suspendRemark) {
        this.suspendRemark = suspendRemark;
    }

    public Integer getIncorrectFlag() {
        return this.incorrectFlag;
    }

    public void setIncorrectFlag(Integer incorrectFlag) {
        this.incorrectFlag = incorrectFlag;
    }

    public Date getIncorrectDate() {
        return this.incorrectDate;
    }

    public void setIncorrectDate(Date incorrectDate) {
        this.incorrectDate = incorrectDate;
    }

    public String getIncorrectMsg() {
        return this.incorrectMsg;
    }

    public void setIncorrectMsg(String incorrectMsg) {
        this.incorrectMsg = incorrectMsg;
    }

    public Integer getCanVatFlag() {
        return this.canVatFlag;
    }

    public void setCanVatFlag(Integer canVatFlag) {
        this.canVatFlag = canVatFlag;
    }

    public BigDecimal getLineFreight() {
        return this.lineFreight;
    }

    public void setLineFreight(BigDecimal lineFreight) {
        this.lineFreight = lineFreight;
    }

    public Long getExecutionHeaderBillId() {
        return this.executionHeaderBillId;
    }

    public void setExecutionHeaderBillId(Long executionHeaderBillId) {
        this.executionHeaderBillId = executionHeaderBillId;
    }

    public String getExecutionHeaderBillNum() {
        return this.executionHeaderBillNum;
    }

    public void setExecutionHeaderBillNum(String executionHeaderBillNum) {
        this.executionHeaderBillNum = executionHeaderBillNum;
    }

    public Date getRequestDate() {
        return this.requestDate;
    }

    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }

    public Long getPurchaseAgentId() {
        return this.purchaseAgentId;
    }

    public void setPurchaseAgentId(Long purchaseAgentId) {
        this.purchaseAgentId = purchaseAgentId;
    }

    public String getErpEditStatus() {
        return this.erpEditStatus;
    }

    public void setErpEditStatus(String erpEditStatus) {
        this.erpEditStatus = erpEditStatus;
    }

    public Long getObjectVersionNumber() {
        return this.objectVersionNumber;
    }

    public void setObjectVersionNumber(Long objectVersionNumber) {
        this.objectVersionNumber = objectVersionNumber;
    }

    public Long getPoLineId() {
        return this.poLineId;
    }

    public void setPoLineId(Long poLineId) {
        this.poLineId = poLineId;
    }

    public String getItemProperties() {
        return this.itemProperties;
    }

    public void setItemProperties(String itemProperties) {
        this.itemProperties = itemProperties;
    }

    public Long getKeeperUserId() {
        return this.keeperUserId;
    }

    public void setKeeperUserId(Long keeperUserId) {
        this.keeperUserId = keeperUserId;
    }

    public Long getAccepterUserId() {
        return this.accepterUserId;
    }

    public void setAccepterUserId(Long accepterUserId) {
        this.accepterUserId = accepterUserId;
    }

    public Long getCostPayerUserId() {
        return this.costPayerUserId;
    }

    public void setCostPayerUserId(Long costPayerUserId) {
        this.costPayerUserId = costPayerUserId;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getInnerPoNum() {
        return this.innerPoNum;
    }

    public void setInnerPoNum(String innerPoNum) {
        this.innerPoNum = innerPoNum;
    }

    public String getSupplierItemCode() {
        return this.supplierItemCode;
    }

    public void setSupplierItemCode(String supplierItemCode) {
        this.supplierItemCode = supplierItemCode;
    }

    public String getBatchNo() {
        return this.batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getExpBearDepId() {
        return this.expBearDepId;
    }

    public void setExpBearDepId(String expBearDepId) {
        this.expBearDepId = expBearDepId;
    }

    public String getExpBearDep() {
        return this.expBearDep;
    }

    public void setExpBearDep(String expBearDep) {
        this.expBearDep = expBearDep;
    }

    public String getCostAnchDepId() {
        return this.costAnchDepId;
    }

    public void setCostAnchDepId(String costAnchDepId) {
        this.costAnchDepId = costAnchDepId;
    }

    public String getCostAnchDepDesc() {
        return this.costAnchDepDesc;
    }

    public void setCostAnchDepDesc(String costAnchDepDesc) {
        this.costAnchDepDesc = costAnchDepDesc;
    }

    public String getSupplierItemName() {
        return this.supplierItemName;
    }

    public void setSupplierItemName(String supplierItemName) {
        this.supplierItemName = supplierItemName;
    }

    public BigDecimal getBenchmarkPrice() {
        return this.benchmarkPrice;
    }

    public void setBenchmarkPrice(BigDecimal benchmarkPrice) {
        this.benchmarkPrice = benchmarkPrice;
    }

    public BigDecimal getChangePercent() {
        return this.changePercent;
    }

    public void setChangePercent(BigDecimal changePercent) {
        this.changePercent = changePercent;
    }

    public String getAssets() {
        return this.assets;
    }

    public void setAssets(String assets) {
        this.assets = assets;
    }

    public String getAssetChildNum() {
        return this.assetChildNum;
    }

    public void setAssetChildNum(String assetChildNum) {
        this.assetChildNum = assetChildNum;
    }

    public BigDecimal getTaxWithoutFreightPrice() {
        return this.taxWithoutFreightPrice;
    }

    public void setTaxWithoutFreightPrice(BigDecimal taxWithoutFreightPrice) {
        this.taxWithoutFreightPrice = taxWithoutFreightPrice;
    }

    public String getFrameAgreementNum() {
        return this.frameAgreementNum;
    }

    public void setFrameAgreementNum(String frameAgreementNum) {
        this.frameAgreementNum = frameAgreementNum;
    }

    public String getQualityStandard() {
        return this.qualityStandard;
    }

    public void setQualityStandard(String qualityStandard) {
        this.qualityStandard = qualityStandard;
    }

    public Integer getBudgetIoFlag() {
        return this.budgetIoFlag;
    }

    public void setBudgetIoFlag(Integer budgetIoFlag) {
        this.budgetIoFlag = budgetIoFlag;
    }

    public BigDecimal getTaxIncludedBudgetUnitPrice() {
        return this.taxIncludedBudgetUnitPrice;
    }

    public void setTaxIncludedBudgetUnitPrice(BigDecimal taxIncludedBudgetUnitPrice) {
        this.taxIncludedBudgetUnitPrice = taxIncludedBudgetUnitPrice;
    }

    public String getExecutionStrategyCode() {
        return this.executionStrategyCode;
    }

    public void setExecutionStrategyCode(String executionStrategyCode) {
        this.executionStrategyCode = executionStrategyCode;
    }

    public Long getBudgetAccountId() {
        return this.budgetAccountId;
    }

    public void setBudgetAccountId(Long budgetAccountId) {
        this.budgetAccountId = budgetAccountId;
    }

    public String getBudgetAccountNum() {
        return this.budgetAccountNum;
    }

    public void setBudgetAccountNum(String budgetAccountNum) {
        this.budgetAccountNum = budgetAccountNum;
    }

    public String getReceiverInformation() {
        return this.receiverInformation;
    }

    public void setReceiverInformation(String receiverInformation) {
        this.receiverInformation = receiverInformation;
    }

    public Integer getBusinessCardFlag() {
        return this.businessCardFlag;
    }

    public void setBusinessCardFlag(Integer businessCardFlag) {
        this.businessCardFlag = businessCardFlag;
    }

    public Long getCartUserId() {
        return this.cartUserId;
    }

    public void setCartUserId(Long cartUserId) {
        this.cartUserId = cartUserId;
    }

    public String getCartUserType() {
        return this.cartUserType;
    }

    public void setCartUserType(String cartUserType) {
        this.cartUserType = cartUserType;
    }

    public String getChangeOrderCode() {
        return this.changeOrderCode;
    }

    public void setChangeOrderCode(String changeOrderCode) {
        this.changeOrderCode = changeOrderCode;
    }

    public String getChangeOrderMessage() {
        return this.changeOrderMessage;
    }

    public void setChangeOrderMessage(String changeOrderMessage) {
        this.changeOrderMessage = changeOrderMessage;
    }

    public String getBudgetAccountDeptno() {
        return this.budgetAccountDeptno;
    }

    public void setBudgetAccountDeptno(String budgetAccountDeptno) {
        this.budgetAccountDeptno = budgetAccountDeptno;
    }

    public BigDecimal getBudgetAccountPrice() {
        return this.budgetAccountPrice;
    }

    public void setBudgetAccountPrice(BigDecimal budgetAccountPrice) {
        this.budgetAccountPrice = budgetAccountPrice;
    }

    public String toString() {
        return (new ToStringBuilder(this)).append("prLineId", this.prLineId).append("prHeaderId", this.prHeaderId).append("poHeaderId", this.poHeaderId).append("prNum", this.prNum).append("lineNum", this.lineNum).append("quantity", this.quantity).append("companyId", this.companyId).append("companyName", this.companyName).append("ouId", this.ouId).append("ouName", this.ouName).append("supplierId", this.supplierId).append("supplierCode", this.supplierCode).append("supplierName", this.supplierName).append("supplierCompanyId", this.supplierCompanyId).append("supplierCompanyName", this.supplierCompanyName).append("itemCode", this.itemCode).append("itemName", this.itemName).append("categoryName", this.categoryName).append("productNum", this.productNum).append("productName", this.productName).append("catalogId", this.catalogId).append("catalogName", this.catalogName).append("currencyCode", this.currencyCode).append("uomId", this.uomId).append("uomCode", this.uomCode).append("taxIncludedUnitPrice", this.taxIncludedUnitPrice).append("invOrganizationId", this.invOrganizationId).append("invOrganizationName", this.invOrganizationName).append("receiverAddressId", this.receiverAddressId).append("receiverAddress", this.receiverAddress).append("requestedBy", this.requestedBy).append("prRequestedName", this.prRequestedName).append("contactTelNum", this.contactTelNum).append("neededDate", this.neededDate).append("prSourcePlatform", this.prSourcePlatform).append("prSourcePlatformMeaning", this.prSourcePlatformMeaning).append("prSourcePlatform", this.prSourcePlatform).append("createByUnitName", this.createByUnitName).append("createByUnitId", this.createByUnitId).append("attachmentUuid", this.attachmentUuid).toString();
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

    public String getSupplierCompanyCode() {
        return this.supplierCompanyCode;
    }

    public void setSupplierCompanyCode(String supplierCompanyCode) {
        this.supplierCompanyCode = supplierCompanyCode;
    }

    public List<PrLineSupplier> getSupplierList() {
        return this.supplierList;
    }

    public void setSupplierList(List<PrLineSupplier> supplierList) {
        this.supplierList = supplierList;
    }

    public Integer getSapFlag() {
        return this.sapFlag;
    }

    public void setSapFlag(Integer sapFlag) {
        this.sapFlag = sapFlag;
    }

    public List<PoHeaderAccordingToLineOfReferenceVO> getPrLineList() {
        return this.prLineList;
    }

    public void setPrLineList(List<PoHeaderAccordingToLineOfReferenceVO> prLineList) {
        this.prLineList = prLineList;
    }

    public static PoHeaderAccordingToLineOfReferenceVO.ParseQueryNewPrice parseQueryNewPrice(PoHeaderAccordingToLineOfReferenceVO configQueryVO) {
        return new PoHeaderAccordingToLineOfReferenceVO.ParseQueryNewPrice(configQueryVO);
    }

    public static PoHeaderAccordingToLineOfReferenceVO.ParseQueryEnableSupplierCompany parseQueryEnableSupplierCompany(PoHeaderAccordingToLineOfReferenceVO configQueryVO) {
        return new PoHeaderAccordingToLineOfReferenceVO.ParseQueryEnableSupplierCompany(configQueryVO);
    }

    public static final class ParseQueryEnableSupplierCompany {
        private Long tenantId;
        private String prTypeCode;
        private String sourceFrom;
        private String purchaseOrgCode;
        private String companyCode;

        public ParseQueryEnableSupplierCompany() {
        }

        public ParseQueryEnableSupplierCompany(PoHeaderAccordingToLineOfReferenceVO configQueryVO) {
            this.prTypeCode = configQueryVO.getPrTypeCode();
            this.companyCode = configQueryVO.getCompanyCode();
            this.tenantId = configQueryVO.getTenantId();
            this.sourceFrom = configQueryVO.getPrSourcePlatform();
            this.purchaseOrgCode = configQueryVO.getPurchaseOrgCode();
        }

        public Long getTenantId() {
            return this.tenantId;
        }

        public String getPrTypeCode() {
            return this.prTypeCode;
        }

        public String getSourceFrom() {
            return this.sourceFrom;
        }

        public String getPurchaseOrgCode() {
            return this.purchaseOrgCode;
        }

        public String getCompanyCode() {
            return this.companyCode;
        }

        public void setTenantId(Long tenantId) {
            this.tenantId = tenantId;
        }

        public void setPrTypeCode(String prTypeCode) {
            this.prTypeCode = prTypeCode;
        }

        public void setSourceFrom(String sourceFrom) {
            this.sourceFrom = sourceFrom;
        }

        public void setPurchaseOrgCode(String purchaseOrgCode) {
            this.purchaseOrgCode = purchaseOrgCode;
        }

        public void setCompanyCode(String companyCode) {
            this.companyCode = companyCode;
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            } else if (!(o instanceof PoHeaderAccordingToLineOfReferenceVO.ParseQueryEnableSupplierCompany)) {
                return false;
            } else {
                PoHeaderAccordingToLineOfReferenceVO.ParseQueryEnableSupplierCompany that = (PoHeaderAccordingToLineOfReferenceVO.ParseQueryEnableSupplierCompany)o;
                return Objects.equals(this.getTenantId(), that.getTenantId()) && Objects.equals(this.getPrTypeCode(), that.getPrTypeCode()) && Objects.equals(this.getSourceFrom(), that.getSourceFrom()) && Objects.equals(this.getPurchaseOrgCode(), that.getPurchaseOrgCode()) && Objects.equals(this.getCompanyCode(), that.getCompanyCode());
            }
        }

        public int hashCode() {
            return Objects.hash(new Object[]{this.getTenantId(), this.getPrTypeCode(), this.getSourceFrom(), this.getPurchaseOrgCode(), this.getCompanyCode()});
        }
    }

    public static final class ParseQueryNewPrice {
        private String ouCode;
        private String companyCode;
        private Long tenantId;
        private String orderTypeCode;

        public ParseQueryNewPrice() {
        }

        public ParseQueryNewPrice(PoHeaderAccordingToLineOfReferenceVO configQueryVO) {
            this.ouCode = configQueryVO.getOuCode();
            this.companyCode = configQueryVO.getCompanyCode();
            this.tenantId = configQueryVO.getTenantId();
        }

        public String getOuCode() {
            return this.ouCode;
        }

        public String getCompanyCode() {
            return this.companyCode;
        }

        public Long getTenantId() {
            return this.tenantId;
        }

        public String getOrderTypeCode() {
            return this.orderTypeCode;
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            } else if (!(o instanceof PoHeaderAccordingToLineOfReferenceVO.ParseQueryNewPrice)) {
                return false;
            } else {
                PoHeaderAccordingToLineOfReferenceVO.ParseQueryNewPrice that = (PoHeaderAccordingToLineOfReferenceVO.ParseQueryNewPrice)o;
                return Objects.equals(this.getOuCode(), that.getOuCode()) && Objects.equals(this.getCompanyCode(), that.getCompanyCode()) && Objects.equals(this.getTenantId(), that.getTenantId()) && Objects.equals(this.getOrderTypeCode(), that.getOrderTypeCode());
            }
        }

        public int hashCode() {
            return Objects.hash(new Object[]{this.getOuCode(), this.getCompanyCode(), this.getTenantId(), this.getOrderTypeCode()});
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
