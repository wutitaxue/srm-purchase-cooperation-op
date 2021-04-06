//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.srm.purchasecooperation.cux.accept.domain.vo;

import java.math.BigDecimal;
import java.util.Date;

import org.hzero.boot.platform.lov.annotation.LovValue;
import org.hzero.export.annotation.ExcelColumn;
import org.hzero.export.annotation.ExcelSheet;
import org.hzero.starter.keyencrypt.core.Encrypt;
import org.srm.purchasecooperation.accept.domain.vo.AcceptListLineVO;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModelProperty;

@ExcelSheet(promptKey = "sinv.acceptanceSheetCreate", promptCode = "sinv.acceptanceSheetCreate.title.acceptanceDetail")
public class RCWLAcceptListLineVO extends AcceptListLineVO {
    @ApiModelProperty("表ID，主键，供其他表做外键")
    @Encrypt
    private Long acceptListLineId;
    @ApiModelProperty("验收单头id")
    @Encrypt
    private Long acceptListHeaderId;
    @ExcelColumn(promptKey = "sinv.common", promptCode = "sinv.common.model.common.acceptListNum")
    @ApiModelProperty("验收单号")
    private String acceptListNum;
    @ExcelColumn(promptKey = "sprm.common", promptCode = "sprm.common.model.common.companyName")
    @ApiModelProperty("公司")
    private String companyName;
    @ExcelColumn(promptKey = "sinv.common", promptCode = "sinv.common.model.common.supplierCompanyName")
    @ApiModelProperty("供应商")
    private String supplierCompanyName;
    @ExcelColumn(promptKey = "spcm.common", promptCode = "spcm.common.model.common.lineNum")
    @ApiModelProperty("序号")
    private Long lineNum;
    @ExcelColumn(promptKey = "spuc.mdm", promptCode = "spuc.mdm.model.mdm.itemCode")
    @ApiModelProperty("物料编码")
    private String itemCode;
    @ExcelColumn(promptKey = "spuc.mdm", promptCode = "spuc.mdm.model.mdm.itemName")
    @ApiModelProperty("物料名称")
    private String itemName;
    @ExcelColumn(promptKey = "sinv.common", promptCode = "sinv.common.model.common.itemCategoryName")
    @ApiModelProperty("物料品类")
    private String itemCategoryName;
    @ExcelColumn(promptKey = "sprm.common", promptCode = "sprm.common.model.common.uomName")
    @ApiModelProperty("单位")
    private String uomName;
    @ExcelColumn(promptKey = "sinv.common", promptCode = "sinv.common.model.common.acceptQuantity")
    @ApiModelProperty("本次验收数量")
    private BigDecimal acceptedQuantity;
    @ApiModelProperty("租户id")
    private Long tenantId;
    @ApiModelProperty("物料id")
    @Encrypt
    private Long itemId;
    @ApiModelProperty("物料品类id")
    @Encrypt
    private Long itemCategoryId;
    @ApiModelProperty("单位id")
    @Encrypt
    private Long uomId;
    @ApiModelProperty("验收数量")
    private BigDecimal acceptQuantity;
    @LovValue(value = "SPUC.ACCEPT_OPINION", meaningField = "acceptOpinionCodeMeaning")
    @ApiModelProperty("验收意见编码")
    private String acceptOpinionCode;
    @ExcelColumn(promptKey = "sinv.common", promptCode = "sinv.common.model.common.acceptOpinionCodeMeaning")
    @ApiModelProperty("验收意见")
    private String acceptOpinionCodeMeaning;
    @ApiModelProperty("数量")
    private BigDecimal quantity;
    @ApiModelProperty("可验收数量")
    private BigDecimal canAcceptQuantity;
    @ApiModelProperty("规格")
    private String specifications;
    @ApiModelProperty("型号")
    private String model;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("交付日期")
    private Date deliverDate;
    @ApiModelProperty("备注")
    private String remark;
    @ApiModelProperty("来源单据编号")
    private String pcSourceCode;
    @ApiModelProperty("来源单据行号")
    private Long pcSourceLineNum;
    @ExcelColumn(promptKey = "sodr.common", promptCode = "sodr.common.model.common.pcNum")
    @ApiModelProperty("协议编号")
    private String pcNum;
    @ExcelColumn(promptKey = "sodr.common", promptCode = "sodr.common.model.common.pcName")
    @ApiModelProperty("协议名称")
    private String pcName;
    @ApiModelProperty("协议类型")
    private String pcTypeCode;
    private Long sourceId;
    private Long sourceLineId;
    private String pcTypeName;
    private Long acceptStageId;
    @ApiModelProperty("阶段")
    private String stageName;
    @ApiModelProperty("验收说明")
    private String lineAcceptDescription;
    @ApiModelProperty("费用")
    private BigDecimal acceptAmount;
    @ApiModelProperty("附件UUID")
    private String attachmentUuid;
    @ApiModelProperty("阶段费用")
    private BigDecimal costQuantity;
    @ApiModelProperty("协议总额")
    private BigDecimal taxIncludeAmount;
    @ApiModelProperty("标的原币含税单价")
    private BigDecimal enteredTaxIncludedPrice;
    @ApiModelProperty("协议阶段ID")
    private Long pcStageId;
    @ApiModelProperty("本币含税单价")
    private BigDecimal purchaseTaxIncludedPrice;
    @ApiModelProperty("本币费用")
    private BigDecimal purchaseCostQuantity;
    @ExcelColumn(promptKey = "spuc.common", promptCode = "spuc.common.mode.common.poHeaderNum")
    @ApiModelProperty("订单号")
    private String poHeaderNum;
    @ExcelColumn(promptKey = "spuc.common", promptCode = "spuc.common.model.common.poLineNum")
    @ApiModelProperty("订单行号")
    private Long poLineNum;
    @LovValue(lovCode = "SPUC.ACCEPT_METHOD", meaningField = "acceptMethodMeaning")
    private String acceptMethod;
    @ApiModelProperty("订单验收类型（接收方式）")
    private String acceptMethodMeaning;
    @ApiModelProperty("订单类型")
    private String orderTypeName;
    @ApiModelProperty("订单行单价")
    private BigDecimal poUnitPrice;
    @ApiModelProperty("订单行金额")
    private BigDecimal amount;
    @ApiModelProperty("公司ID")
    @Encrypt
    private Long companyId;
    @ApiModelProperty("供应商公司ID")
    @Encrypt
    private Long supplierCompanyId;
    private String sourceCode;
    private Long sourceLineNum;
    @ApiModelProperty("单位code")
    private String uomCode;
    @ApiModelProperty("单位精度")
    private Integer uomPrecision;
    @ApiModelProperty("币种精度")
    private Integer financialPrecision;
    @ApiModelProperty("资产编号")
    private String attributeVarchar1;
    @ApiModelProperty("资产系统采购订单号")
    private String attributeVarchar2;

    public RCWLAcceptListLineVO() {}

    public String getAttributeVarchar1() {
        return attributeVarchar1;
    }

    public void setAttributeVarchar1(String attributeVarchar1) {
        this.attributeVarchar1 = attributeVarchar1;
    }

    public String getAttributeVarchar2() {
        return attributeVarchar2;
    }

    public void setAttributeVarchar2(String attributeVarchar2) {
        this.attributeVarchar2 = attributeVarchar2;
    }

    public BigDecimal getPurchaseCostQuantity() {
        return this.purchaseCostQuantity;
    }

    public void setPurchaseCostQuantity(BigDecimal purchaseCostQuantity) {
        this.purchaseCostQuantity = purchaseCostQuantity;
    }

    public BigDecimal getPurchaseTaxIncludedPrice() {
        return this.purchaseTaxIncludedPrice;
    }

    public void setPurchaseTaxIncludedPrice(BigDecimal purchaseTaxIncludedPrice) {
        this.purchaseTaxIncludedPrice = purchaseTaxIncludedPrice;
    }

    public String getAttachmentUuid() {
        return this.attachmentUuid;
    }

    public void setAttachmentUuid(String attachmentUuid) {
        this.attachmentUuid = attachmentUuid;
    }

    public BigDecimal getCostQuantity() {
        return this.costQuantity;
    }

    public void setCostQuantity(BigDecimal costQuantity) {
        this.costQuantity = costQuantity;
    }

    public BigDecimal getTaxIncludeAmount() {
        return this.taxIncludeAmount;
    }

    public void setTaxIncludeAmount(BigDecimal taxIncludeAmount) {
        this.taxIncludeAmount = taxIncludeAmount;
    }

    public BigDecimal getEnteredTaxIncludedPrice() {
        return this.enteredTaxIncludedPrice;
    }

    public void setEnteredTaxIncludedPrice(BigDecimal enteredTaxIncludedPrice) {
        this.enteredTaxIncludedPrice = enteredTaxIncludedPrice;
    }

    public Long getPcStageId() {
        return this.pcStageId;
    }

    public void setPcStageId(Long pcStageId) {
        this.pcStageId = pcStageId;
    }

    public Long getAcceptStageId() {
        return this.acceptStageId;
    }

    public void setAcceptStageId(Long acceptStageId) {
        this.acceptStageId = acceptStageId;
    }

    public String getStageName() {
        return this.stageName;
    }

    public void setStageName(String stageName) {
        this.stageName = stageName;
    }

    public String getLineAcceptDescription() {
        return this.lineAcceptDescription;
    }

    public void setLineAcceptDescription(String lineAcceptDescription) {
        this.lineAcceptDescription = lineAcceptDescription;
    }

    public BigDecimal getAcceptAmount() {
        return this.acceptAmount;
    }

    public void setAcceptAmount(BigDecimal acceptAmount) {
        this.acceptAmount = acceptAmount;
    }

    public BigDecimal getQuantity() {
        return this.quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getCanAcceptQuantity() {
        return this.canAcceptQuantity;
    }

    public void setCanAcceptQuantity(BigDecimal canAcceptQuantity) {
        this.canAcceptQuantity = canAcceptQuantity;
    }

    public BigDecimal getAcceptedQuantity() {
        return this.acceptedQuantity;
    }

    public void setAcceptedQuantity(BigDecimal acceptedQuantity) {
        this.acceptedQuantity = acceptedQuantity;
    }

    public String getSpecifications() {
        return this.specifications;
    }

    public void setSpecifications(String specifications) {
        this.specifications = specifications;
    }

    public String getModel() {
        return this.model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Date getDeliverDate() {
        return this.deliverDate;
    }

    public void setDeliverDate(Date deliverDate) {
        this.deliverDate = deliverDate;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPcSourceCode() {
        return this.pcSourceCode;
    }

    public void setPcSourceCode(String pcSourceCode) {
        this.pcSourceCode = pcSourceCode;
    }

    public Long getPcSourceLineNum() {
        return this.pcSourceLineNum;
    }

    public void setPcSourceLineNum(Long pcSourceLineNum) {
        this.pcSourceLineNum = pcSourceLineNum;
    }

    public String getPcNum() {
        return this.pcNum;
    }

    public void setPcNum(String pcNum) {
        this.pcNum = pcNum;
    }

    public String getPcName() {
        return this.pcName;
    }

    public void setPcName(String pcName) {
        this.pcName = pcName;
    }

    public String getPcTypeCode() {
        return this.pcTypeCode;
    }

    public void setPcTypeCode(String pcTypeCode) {
        this.pcTypeCode = pcTypeCode;
    }

    public Long getSourceId() {
        return this.sourceId;
    }

    public void setSourceId(Long sourceId) {
        this.sourceId = sourceId;
    }

    public Long getSourceLineId() {
        return this.sourceLineId;
    }

    public void setSourceLineId(Long sourceLineId) {
        this.sourceLineId = sourceLineId;
    }

    public String getPcTypeName() {
        return this.pcTypeName;
    }

    public void setPcTypeName(String pcTypeName) {
        this.pcTypeName = pcTypeName;
    }

    public String getAcceptOpinionCodeMeaning() {
        return this.acceptOpinionCodeMeaning;
    }

    public void setAcceptOpinionCodeMeaning(String acceptOpinionCodeMeaning) {
        this.acceptOpinionCodeMeaning = acceptOpinionCodeMeaning;
    }

    public Long getAcceptListLineId() {
        return this.acceptListLineId;
    }

    public void setAcceptListLineId(Long acceptListLineId) {
        this.acceptListLineId = acceptListLineId;
    }

    public Long getAcceptListHeaderId() {
        return this.acceptListHeaderId;
    }

    public void setAcceptListHeaderId(Long acceptListHeaderId) {
        this.acceptListHeaderId = acceptListHeaderId;
    }

    public Long getTenantId() {
        return this.tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public Long getItemId() {
        return this.itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return this.itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Long getItemCategoryId() {
        return this.itemCategoryId;
    }

    public void setItemCategoryId(Long itemCategoryId) {
        this.itemCategoryId = itemCategoryId;
    }

    public Long getUomId() {
        return this.uomId;
    }

    public void setUomId(Long uomId) {
        this.uomId = uomId;
    }

    public BigDecimal getAcceptQuantity() {
        return this.acceptQuantity;
    }

    public void setAcceptQuantity(BigDecimal acceptQuantity) {
        this.acceptQuantity = acceptQuantity;
    }

    public String getAcceptOpinionCode() {
        return this.acceptOpinionCode;
    }

    public void setAcceptOpinionCode(String acceptOpinionCode) {
        this.acceptOpinionCode = acceptOpinionCode;
    }

    public Long getLineNum() {
        return this.lineNum;
    }

    public void setLineNum(Long lineNum) {
        this.lineNum = lineNum;
    }

    public String getItemCode() {
        return this.itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemCategoryName() {
        return this.itemCategoryName;
    }

    public void setItemCategoryName(String itemCategoryName) {
        this.itemCategoryName = itemCategoryName;
    }

    public String getUomName() {
        return this.uomName;
    }

    public void setUomName(String uomName) {
        this.uomName = uomName;
    }

    public Long getPoLineNum() {
        return this.poLineNum;
    }

    public void setPoLineNum(Long poLineNum) {
        this.poLineNum = poLineNum;
    }

    public String getPoHeaderNum() {
        return this.poHeaderNum;
    }

    public void setPoHeaderNum(String poHeaderNum) {
        this.poHeaderNum = poHeaderNum;
    }

    public String getAcceptMethod() {
        return this.acceptMethod;
    }

    public void setAcceptMethod(String acceptMethod) {
        this.acceptMethod = acceptMethod;
    }

    public String getAcceptMethodMeaning() {
        return this.acceptMethodMeaning;
    }

    public void setAcceptMethodMeaning(String acceptMethodMeaning) {
        this.acceptMethodMeaning = acceptMethodMeaning;
    }

    public String getOrderTypeName() {
        return this.orderTypeName;
    }

    public void setOrderTypeName(String orderTypeName) {
        this.orderTypeName = orderTypeName;
    }

    public BigDecimal getPoUnitPrice() {
        return this.poUnitPrice;
    }

    public void setPoUnitPrice(BigDecimal poUnitPrice) {
        this.poUnitPrice = poUnitPrice;
    }

    public BigDecimal getAmount() {
        return this.amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getAcceptListNum() {
        return this.acceptListNum;
    }

    public void setAcceptListNum(String acceptListNum) {
        this.acceptListNum = acceptListNum;
    }

    public Long getCompanyId() {
        return this.companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getSupplierCompanyId() {
        return this.supplierCompanyId;
    }

    public void setSupplierCompanyId(Long supplierCompanyId) {
        this.supplierCompanyId = supplierCompanyId;
    }

    public String getCompanyName() {
        return this.companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getSupplierCompanyName() {
        return this.supplierCompanyName;
    }

    public void setSupplierCompanyName(String supplierCompanyName) {
        this.supplierCompanyName = supplierCompanyName;
    }

    public String getSourceCode() {
        return this.sourceCode;
    }

    public void setSourceCode(String sourceCode) {
        this.sourceCode = sourceCode;
    }

    public Long getSourceLineNum() {
        return this.sourceLineNum;
    }

    public void setSourceLineNum(Long sourceLineNum) {
        this.sourceLineNum = sourceLineNum;
    }

    public String getUomCode() {
        return this.uomCode;
    }

    public void setUomCode(String uomCode) {
        this.uomCode = uomCode;
    }

    public Integer getUomPrecision() {
        return this.uomPrecision;
    }

    public void setUomPrecision(Integer uomPrecision) {
        this.uomPrecision = uomPrecision;
    }

    public Integer getFinancialPrecision() {
        return this.financialPrecision;
    }

    public void setFinancialPrecision(Integer financialPrecision) {
        this.financialPrecision = financialPrecision;
    }
}
