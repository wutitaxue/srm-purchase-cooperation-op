package org.srm.purchasecooperation.order.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Transient;
import org.apache.commons.lang3.StringUtils;
import org.hzero.boot.platform.lov.annotation.LovValue;
import org.hzero.core.base.BaseConstants.Flag;
import org.hzero.export.annotation.ExcelColumn;
import org.hzero.export.annotation.ExcelSheet;
import org.hzero.mybatis.domian.SecurityToken;
import org.hzero.starter.keyencrypt.core.Encrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.format.annotation.DateTimeFormat;
import org.srm.common.mybatis.domain.ExpandDomain;
import org.srm.purchasecooperation.common.infra.renderer.RetainDecimalRender;
import org.srm.purchasecooperation.order.domain.entity.PoLineLocation;

@ExcelSheet(
        promptKey = "sodr.PoLineLocationVO",
        promptCode = "sodr.PoLineLocationVO.model.export.PoLineLocationVO"
)
@JsonInclude(Include.NON_NULL)
public class PoLineLocationVO extends ExpandDomain {
    public static final Logger LOGGER = LoggerFactory.getLogger(PoLineLocationVO.class);
    @Encrypt
    private Long poLineId;
    @Encrypt
    private Long poLineLocationId;
    @Encrypt
    private Long poHeaderId;
    private String statusCode;
    private String statusMeaning;
    @LovValue(
            lovCode = "SODR.PO_STATUS",
            meaningField = "displayStatusMeaning"
    )
    private String displayStatusCode;
    @ApiModelProperty("采购订单类型名称")
    private String orderTypeName;
    @ExcelColumn(
            promptKey = "sodr.common",
            promptCode = "sodr.common.model.common.planStatusMeaning"
    )
    private String displayStatusMeaning;
    @ApiModelProperty("订单号")
    @ExcelColumn(
            promptKey = "sodr.common",
            promptCode = "sodr.common.model.common.orderNum"
    )
    private String displayPoNum;
    @ExcelColumn(
            promptKey = "sodr.common",
            promptCode = "sodr.common.model.supplierCode"
    )
    private String supplierCode;
    @ExcelColumn(
            promptKey = "sodr.common",
            promptCode = "sodr.common.model.common.supplierCompanyName"
    )
    private String supplierName;
    @Encrypt
    private Long supplierId;
    @ExcelColumn(
            promptKey = "sodr.common",
            promptCode = "sodr.common.model.common.versionNum"
    )
    private Integer versionNum;
    private String releaseNum;
    @ExcelColumn(
            promptKey = "sodr.common",
            promptCode = "sodr.common.model.common.releaseNum"
    )
    private String displayReleaseNum;
    @ExcelColumn(
            promptKey = "sodr.common",
            promptCode = "sodr.common.model.common.lineNum"
    )
    private String displayLineNum;
    @ExcelColumn(
            promptKey = "sodr.common",
            promptCode = "sodr.common.model.common.shipmentNum"
    )
    private String displayLineLocationNum;
    @ExcelColumn(
            promptKey = "sodr.common",
            promptCode = "sodr.common.model.common.itemCode"
    )
    private String itemCode;
    @ExcelColumn(
            promptKey = "sodr.common",
            promptCode = "sodr.common.model.common.itemDescription"
    )
    private String itemName;
    @ExcelColumn(
            promptKey = "sodr.common",
            promptCode = "sodr.common.model.common.oldItemCode"
    )
    private String oldItemCode;
    @ExcelColumn(
            promptKey = "sodr.common",
            promptCode = "sodr.common.model.common.purLineCount"
    )
    private BigDecimal quantity;
    @ExcelColumn(
            promptKey = "sodr.common",
            promptCode = "sodr.common.model.common.netReceivedQuantity"
    )
    private BigDecimal netReceivedQuantity;
    @ExcelColumn(
            promptKey = "sodr.common",
            promptCode = "sodr.common.model.common.deliverQuantity"
    )
    private BigDecimal netDeliverQuantity;
    @ExcelColumn(
            promptKey = "sodr.common",
            promptCode = "sodr.common.model.common.NotInStorage"
    )
    private BigDecimal notDeliverQuantity;
    @ExcelColumn(
            promptKey = "sodr.common",
            promptCode = "sodr.common.model.common.shippedQuantity"
    )
    private BigDecimal shippedQuantity;
    @ExcelColumn(
            promptKey = "sodr.common",
            promptCode = "sodr.common.model.common.billedQuantity"
    )
    private BigDecimal invoicedQuantity;
    private BigDecimal receivedQuantity;
    @ExcelColumn(
            promptKey = "sodr.common",
            promptCode = "sodr.common.model.common.unitPrice",
            renderers = {RetainDecimalRender.class}
    )
    private BigDecimal unitPrice;
    private String displayUnitPrice;
    private String displayEnteredTaxIncludedPrice;
    @ExcelColumn(
            promptKey = "sodr.common",
            promptCode = "sodr.common.model.common.taxedEnteredUnitPrice",
            renderers = {RetainDecimalRender.class}
    )
    private BigDecimal enteredTaxIncludedPrice;
    @ExcelColumn(
            promptKey = "sodr.common",
            promptCode = "sodr.common.model.common.linePrice",
            renderers = {RetainDecimalRender.class}
    )
    private BigDecimal lineAmount;
    private String displayLineAmount;
    private String displayTaxIncludedLineAmount;
    @ExcelColumn(
            promptKey = "sodr.common",
            promptCode = "sodr.common.model.common.taxIncludedLinePrice",
            renderers = {RetainDecimalRender.class}
    )
    private BigDecimal taxIncludedLineAmount;
    @ExcelColumn(
            promptKey = "sodr.common",
            promptCode = "sodr.common.model.common.unitPriceBatch"
    )
    private BigDecimal unitPriceBatch;
    @ExcelColumn(
            promptKey = "sodr.common",
            promptCode = "sodr.common.model.unit"
    )
    private String uomName;
    @ExcelColumn(
            promptKey = "sodr.common",
            promptCode = "sodr.common.model.common.currencyName"
    )
    private String currencyCode;
    @ExcelColumn(
            promptKey = "sodr.common",
            promptCode = "sodr.common.model.common.taxRate"
    )
    private BigDecimal taxRate;
    @ExcelColumn(
            promptKey = "sodr.common",
            promptCode = "sodr.common.model.common.productionNumber"
    )
    private String productionOrderNum;
    @ExcelColumn(
            promptKey = "sodr.common",
            promptCode = "sodr.common.model.common.needByDate",
            pattern = "yyyy-MM-dd"
    )
    @JsonFormat(
            pattern = "yyyy-MM-dd"
    )
    @DateTimeFormat(
            pattern = "yyyy-MM-dd"
    )
    private Date needByDate;
    @ExcelColumn(
            promptKey = "sodr.common",
            promptCode = "sodr.common.model.common.promiseDate",
            pattern = "yyyy-MM-dd"
    )
    @JsonFormat(
            pattern = "yyyy-MM-dd"
    )
    @DateTimeFormat(
            pattern = "yyyy-MM-dd"
    )
    private Date promiseDeliveryDate;
    @ApiModelProperty("是否延期标识")
    @LovValue(
            lovCode = "HPFM.FLAG",
            meaningField = "delayFlagMeaning"
    )
    private String delayFlag;
    @ExcelColumn(
            promptKey = "sodr.common",
            promptCode = "sodr.common.model.common.delayFlag"
    )
    private String delayFlagMeaning;
    @ExcelColumn(
            promptKey = "sodr.common",
            promptCode = "sodr.common.model.common.itemSpecs"
    )
    private String specifications;
    @ExcelColumn(
            promptKey = "sodr.common",
            promptCode = "sodr.common.model.common.modelNumber"
    )
    private String model;
    @ExcelColumn(
            promptKey = "sodr.common",
            promptCode = "sodr.common.model.common.manufacturerName"
    )
    private String manufacturerName;
    @ExcelColumn(
            promptKey = "sodr.common",
            promptCode = "sodr.common.model.common.brand"
    )
    private String brand;
    @LovValue(
            lovCode = "SODR.ERP_STATUS",
            meaningField = "erpStatusMeaning"
    )
    private String erpStatus;
    @ExcelColumn(
            promptKey = "sodr.common",
            promptCode = "sodr.common.model.common.erpStatus"
    )
    private String erpStatusMeaning;
    @ExcelColumn(
            promptKey = "sodr.sendOrder",
            promptCode = "sodr.sendOrder.model.common.frozenStatus"
    )
    private String frozenFlagMeaning;
    @LovValue(
            lovCode = "HPFM.FLAG",
            meaningField = "frozenFlagMeaning"
    )
    private Integer frozenFlag;
    @ExcelColumn(
            promptKey = "sodr.common",
            promptCode = "sodr.common.model.common.closedFlag"
    )
    private String closedMeaning;
    @LovValue(
            lovCode = "HPFM.FLAG",
            meaningField = "closedMeaning"
    )
    private Integer closedFlag;
    @ExcelColumn(
            promptKey = "sodr.common",
            promptCode = "sodr.common.model.common.cancelledFlag"
    )
    private String cancelledMeaning;
    @LovValue(
            lovCode = "HPFM.FLAG",
            meaningField = "cancelledMeaning"
    )
    private Integer cancelledFlag;
    @LovValue(
            value = "SPRM.SRC_PLATFORM",
            meaningField = "poSourcePlatformMeaning"
    )
    private String poSourcePlatform;
    @ExcelColumn(
            promptKey = "sodr.common",
            promptCode = "sodr.common.model.common.consignedFlag"
    )
    private String consignedMeaning;
    @LovValue(
            lovCode = "HPFM.FLAG",
            meaningField = "consignedMeaning"
    )
    private Integer consignedFlag;
    @ExcelColumn(
            promptKey = "sodr.common",
            promptCode = "sodr.common.model.common.returnedFlag"
    )
    private String returnedMeaning;
    @LovValue(
            lovCode = "HPFM.FLAG",
            meaningField = "returnedMeaning"
    )
    private Integer returnedFlag;
    @ExcelColumn(
            promptKey = "sodr.common",
            promptCode = "sodr.common.model.common.freeFlag"
    )
    private String freeMeaning;
    @LovValue(
            lovCode = "HPFM.FLAG",
            meaningField = "freeMeaning"
    )
    private Integer freeFlag;
    @ExcelColumn(
            promptKey = "sodr.common",
            promptCode = "sodr.common.model.common.immedShippedFlag"
    )
    private String immedShippedMeaning;
    @LovValue(
            lovCode = "HPFM.FLAG",
            meaningField = "immedShippedMeaning"
    )
    private Integer immedShippedFlag;
    @ExcelColumn(
            promptKey = "sodr.common",
            promptCode = "sodr.common.model.common.purchaserRemark"
    )
    private String remark;
    @ExcelColumn(
            promptKey = "sodr.common",
            promptCode = "sodr.common.model.common.feedback"
    )
    private String feedback;
    @ExcelColumn(
            promptKey = "sodr.common",
            promptCode = "sodr.common.model.common.shipToThirdPartyName"
    )
    private String shipToThirdPartyName;
    @ExcelColumn(
            promptKey = "sodr.common",
            promptCode = "sodr.common.model.common.shipAddress"
    )
    private String shipToThirdPartyAddress;
    @ExcelColumn(
            promptKey = "sodr.common",
            promptCode = "sodr.common.model.common.shipToThirdPartyContact"
    )
    private String shipToThirdPartyContact;
    @ExcelColumn(
            promptKey = "hap",
            promptCode = "hap.taxinvoice.suppliersitename"
    )
    private String supplierSiteName;
    @ExcelColumn(
            promptKey = "sodr.common",
            promptCode = "sodr.common.model.common.companyName"
    )
    private String companyName;
    @ExcelColumn(
            promptKey = "sodr.common",
            promptCode = "sodr.common.model.common.ouName"
    )
    private String ouName;
    @Encrypt
    private Long ouId;
    @ExcelColumn(
            promptKey = "sodr.common",
            promptCode = "sodr.common.model.common.purchaseOrgName"
    )
    private String purOrganizationName;
    @ExcelColumn(
            promptKey = "sodr.common",
            promptCode = "sodr.common.model.common.purchaseAgentCode"
    )
    private String purchaseAgentName;
    @ExcelColumn(
            promptKey = "sodr.common",
            promptCode = "sodr.common.model.common.organizationName"
    )
    private String invOrganizationName;
    @ExcelColumn(
            promptKey = "sodr.common",
            promptCode = "sodr.common.model.common.inventoryName"
    )
    private String inventoryName;
    @ExcelColumn(
            promptKey = "sodr.common",
            promptCode = "sodr.common.model.common.locationName"
    )
    private String locationName;
    @ExcelColumn(
            promptKey = "sodr.sendOrder",
            promptCode = "sodr.sendOrder.model.common.billToLocationName"
    )
    private String billToLocationAddress;
    @ExcelColumn(
            promptKey = "sodr.common",
            promptCode = "sodr.common.model.common.chartNumber"
    )
    private String chartCode;
    @ApiModelProperty("图纸版本")
    private String chartVersion;
    @ExcelColumn(
            promptKey = "sodr.common",
            promptCode = "sodr.common.model.common.priceUnit"
    )
    private String priceUomName;
    private Date submittedDate;
    private Long submittedBy;
    private Date createDate;
    private Long createBy;
    @ExcelColumn(
            promptKey = "sodr.common",
            promptCode = "sodr.common.model.common.createDate",
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    private Date erpCreationDate;
    @ExcelColumn(
            promptKey = "sodr.common",
            promptCode = "sodr.common.model.common.createdName"
    )
    private String erpCreatedName;
    @ExcelColumn(
            promptKey = "sodr.common",
            promptCode = "sodr.common.model.common.releaseTime",
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    private Date releasedDate;
    @ExcelColumn(
            promptKey = "sodr.common",
            promptCode = "sodr.common.model.common.sureDate",
            pattern = "yyyy-MM-dd"
    )
    @JsonFormat(
            pattern = "yyyy-MM-dd"
    )
    @DateTimeFormat(
            pattern = "yyyy-MM-dd"
    )
    private Date confirmedDate;
    @ExcelColumn(
            promptKey = "sodr.common",
            promptCode = "sodr.common.model.common.urgentOrder"
    )
    private String urgentMeaning;
    @LovValue(
            lovCode = "HPFM.FLAG",
            meaningField = "urgentMeaning"
    )
    private Integer urgentFlag;
    @ExcelColumn(
            promptKey = "sodr.common",
            promptCode = "sodr.common.model.common.urgentDate",
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    private Date urgentDate;
    @ExcelColumn(
            promptKey = "sodr.common",
            promptCode = "sodr.common.model.common.erpContractNum"
    )
    @ApiModelProperty("ERP合同号")
    private String erpContractNum;
    private String prNum;
    @ExcelColumn(
            promptKey = "sodr.common",
            promptCode = "sodr.common.model.common.purReqNum"
    )
    private String displayPrNum;
    @ExcelColumn(
            promptKey = "sodr.common",
            promptCode = "sodr.common.model.common.displayPrLineNum"
    )
    private String displayPrLineNum;
    private String prLineNum;
    private String sourceCode;
    @ExcelColumn(
            promptKey = "sodr.common",
            promptCode = "sodr.common.model.common.commodityEncoding"
    )
    @ApiModelProperty("商品编码")
    private String productNum;
    @ExcelColumn(
            promptKey = "sodr.common",
            promptCode = "sodr.common.model.common.tradeName"
    )
    @ApiModelProperty("商品名称")
    private String productName;
    @ExcelColumn(
            promptKey = "sodr.common",
            promptCode = "sodr.common.model.common.catalogue"
    )
    @ApiModelProperty("商品目录")
    private String catalogName;
    @ApiModelProperty("来源平台代码meaning")
    @ExcelColumn(
            promptKey = "sodr.common",
            promptCode = "sodr.common.model.common.poSourcePlatform"
    )
    private String poSourcePlatformMeaning;
    private String externalSystemCode;
    private Long tenantId;
    @ApiModelProperty("交期审核退回标志，1表示交期审核退回")
    private Integer deliveryDateRejectFlag;
    @ApiModelProperty("价格屏蔽标志，1表示进行价格屏蔽")
    private Integer priceSensitiveFlag;
    @Encrypt
    private Long supplierCompanyId;
    @ApiModelProperty("确认标识")
    private Integer confirmedFlag;
    @ApiModelProperty("取消发布标识")
    private Integer publishCancelFlag;
    @ApiModelProperty("可创建发货单标识")
    private Integer canCreateAsnFlag;
    @ApiModelProperty("采购订单类型编码")
    private String orderTypeCode;
    @ApiModelProperty("订单来源")
    private String sourcePlatform;
    private String supplierCompanyName;
    private String supplierCompanyCode;
    private String shipToLocationAddress;
    @ApiModelProperty("新电商运费标识")
    private Integer freightLineFlag;
    private String incorrectFlag;
    private String incorrectMsg;
    private Integer beyondQuantity;
    @ApiModelProperty("ERP采购申请号")
    private String erpPrNum;
    @ApiModelProperty("ERP采购申请行号")
    private String erpPrLineNum;
    @ApiModelProperty("部门ID")
    private Integer departmentId;
    @ApiModelProperty("部门名称")
    private String departmentName;
    @ApiModelProperty("通用名")
    private String commonName;
    @ApiModelProperty("承诺日期导出ERP状态")
    private String deliverySyncStatus;
    @ApiModelProperty("承诺日期导出ERP反馈")
    private String deliverySyncResponseMsg;
    @ApiModelProperty("承诺日期导出时间")
    private Date deliverySyncDate;
    @ApiModelProperty("项目类别")
    @LovValue(
            lovCode = "SPUC.PR_LINE_PROJECT_CATEHORY",
            meaningField = "projectCategoryMeaning"
    )
    private String projectCategory;
    @ApiModelProperty("项目类别Meaning")
    private String projectCategoryMeaning;
    @ApiModelProperty("账户分配类别ID")
    private Long accountAssignTypeId;
    @ApiModelProperty("账户分配类别编码")
    private String accountAssignTypeCode;
    @ApiModelProperty("妥投时间")
    private Date deliverTime;
    @ApiModelProperty("原需求数量")
    private BigDecimal originalQuantity;
    @ApiModelProperty("标题")
    private String titleName;
    @ApiModelProperty("批次号")
    private String lotNum;
    @ApiModelProperty("发运行确认标识")
    private Integer confirmFlag;
    @Transient
    @ApiModelProperty("头本币币种")
    private String headerDomesticCurrencyCode;
    @Transient
    @ApiModelProperty("行原币币种财务精度")
    private Integer financialPrecision;
    @Transient
    @ApiModelProperty("行原币币种财务精度")
    private Integer domesticFinancialPrecision;

    @ApiModelProperty("采购申请ID")
    private Long prHeaderId;
    @ApiModelProperty("协议头id")
    private Long pcHeaderId;
    @ApiModelProperty("协议编号")
    private String pcNum;

    public PoLineLocationVO() {
    }

    public void convertStatus() {
        String headerStatus = this.getStatusCode();
        this.setDisplayStatusCode(headerStatus);
        LOGGER.info("25855-setDisplayStatusCode():" + this.getDisplayStatusCode());
        if (Objects.equals(Flag.YES, this.getClosedFlag())) {
            this.setDisplayStatusCode("CLOSED");
        } else if (Objects.equals(Flag.YES, this.getCancelledFlag())) {
            this.setDisplayStatusCode("CANCELED");
        } else if (Objects.equals(Flag.YES, this.getConfirmFlag())) {
            this.setDisplayStatusCode("CONFIRMED");
        } else if (Objects.equals(Flag.YES, this.getPublishCancelFlag())) {
            this.setDisplayStatusCode("PUBLISH_CANCEL");
        } else if (Objects.equals(Flag.YES, this.getDeliveryDateRejectFlag())) {
            this.setDisplayStatusCode("DELIVERY_DATE_REJECT");
        } else if (StringUtils.equals("PUBLISHED", headerStatus) && Objects.equals(Flag.YES, this.getConfirmedFlag())) {
            this.setDisplayStatusCode("CONFIRMED");
        } else {
            if (StringUtils.equals("PUBLISHED", headerStatus) && Objects.equals(Flag.NO, this.getConfirmedFlag())) {
                this.setDisplayStatusCode("PUBLISHED");
            }

        }
    }

    public String getLotNum() {
        return lotNum;
    }

    public Long getPrHeaderId() {
        return prHeaderId;
    }

    public void setPrHeaderId(Long prHeaderId) {
        this.prHeaderId = prHeaderId;
    }

    public Long getPcHeaderId() {
        return pcHeaderId;
    }

    public void setPcHeaderId(Long pcHeaderId) {
        this.pcHeaderId = pcHeaderId;
    }

    public String getPcNum() {
        return pcNum;
    }

    public void setPcNum(String pcNum) {
        this.pcNum = pcNum;
    }

    public Integer getConfirmFlag() {
        return this.confirmFlag;
    }

    public void setConfirmFlag(Integer confirmFlag) {
        this.confirmFlag = confirmFlag;
    }

    public Integer getFreightLineFlag() {
        return this.freightLineFlag;
    }

    public void setLotNum(String lotNum) {
        this.lotNum = lotNum;
    }

    public void setFreightLineFlag(Integer freightLineFlag) {
        this.freightLineFlag = freightLineFlag;
    }

    public String getDelayFlag() {
        return this.delayFlag;
    }

    public void setDelayFlag(String delayFlag) {
        this.delayFlag = delayFlag;
    }

    public String getDelayFlagMeaning() {
        return this.delayFlagMeaning;
    }

    public void setDelayFlagMeaning(String delayFlagMeaning) {
        this.delayFlagMeaning = delayFlagMeaning;
    }

    public Date getDeliverTime() {
        return this.deliverTime;
    }

    public void setDeliverTime(Date deliverTime) {
        this.deliverTime = deliverTime;
    }

    public BigDecimal getOriginalQuantity() {
        return this.originalQuantity;
    }

    public void setOriginalQuantity(BigDecimal originalQuantity) {
        this.originalQuantity = originalQuantity;
    }

    public String getProjectCategory() {
        return this.projectCategory;
    }

    public void setProjectCategory(String projectCategory) {
        this.projectCategory = projectCategory;
    }

    public String getDeliverySyncStatus() {
        return this.deliverySyncStatus;
    }

    public void setDeliverySyncStatus(String deliverySyncStatus) {
        this.deliverySyncStatus = deliverySyncStatus;
    }

    public String getDeliverySyncResponseMsg() {
        return this.deliverySyncResponseMsg;
    }

    public void setDeliverySyncResponseMsg(String deliverySyncResponseMsg) {
        this.deliverySyncResponseMsg = deliverySyncResponseMsg;
    }

    public String getProjectCategoryMeaning() {
        return this.projectCategoryMeaning;
    }

    public void setProjectCategoryMeaning(String projectCategoryMeaning) {
        this.projectCategoryMeaning = projectCategoryMeaning;
    }

    public Date getDeliverySyncDate() {
        return this.deliverySyncDate;
    }

    public void setDeliverySyncDate(Date deliverySyncDate) {
        this.deliverySyncDate = deliverySyncDate;
    }

    public String getDepartmentName() {
        return this.departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getCommonName() {
        return this.commonName;
    }

    public void setCommonName(String commonName) {
        this.commonName = commonName;
    }

    public Integer getDepartmentId() {
        return this.departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public BigDecimal getNotDeliverQuantity() {
        return this.notDeliverQuantity;
    }

    public void setNotDeliverQuantity(BigDecimal notDeliverQuantity) {
        this.notDeliverQuantity = notDeliverQuantity;
    }

    public Integer getBeyondQuantity() {
        return this.beyondQuantity;
    }

    public void setBeyondQuantity(Integer beyondQuantity) {
        this.beyondQuantity = beyondQuantity;
    }

    public String getIncorrectFlag() {
        return this.incorrectFlag;
    }

    public void setIncorrectFlag(String incorrectFlag) {
        this.incorrectFlag = incorrectFlag;
    }

    public String getIncorrectMsg() {
        return this.incorrectMsg;
    }

    public void setIncorrectMsg(String incorrectMsg) {
        this.incorrectMsg = incorrectMsg;
    }

    public String getSupplierCompanyName() {
        return this.supplierCompanyName;
    }

    public void setSupplierCompanyName(String supplierCompanyName) {
        this.supplierCompanyName = supplierCompanyName;
    }

    public String getErpContractNum() {
        return this.erpContractNum;
    }

    public void setErpContractNum(String erpContractNum) {
        this.erpContractNum = erpContractNum;
    }

    public String getSupplierCompanyCode() {
        return this.supplierCompanyCode;
    }

    public void setSupplierCompanyCode(String supplierCompanyCode) {
        this.supplierCompanyCode = supplierCompanyCode;
    }

    public String getDisplayPrNum() {
        return this.displayPrNum;
    }

    public void setDisplayPrNum(String displayPrNum) {
        this.displayPrNum = displayPrNum;
    }

    public String getDisplayPrLineNum() {
        return this.displayPrLineNum;
    }

    public void setDisplayPrLineNum(String displayPrLineNum) {
        this.displayPrLineNum = displayPrLineNum;
    }

    public String getSourcePlatform() {
        return this.sourcePlatform;
    }

    public void setSourcePlatform(String sourcePlatform) {
        this.sourcePlatform = sourcePlatform;
    }

    public String getOrderTypeName() {
        return this.orderTypeName;
    }

    public void setOrderTypeName(String orderTypeName) {
        this.orderTypeName = orderTypeName;
    }

    public String getOrderTypeCode() {
        return this.orderTypeCode;
    }

    public void setOrderTypeCode(String orderTypeCode) {
        this.orderTypeCode = orderTypeCode;
    }

    public String getClosedMeaning() {
        return this.closedMeaning;
    }

    public void setClosedMeaning(String closedMeaning) {
        this.closedMeaning = closedMeaning;
    }

    public String getCancelledMeaning() {
        return this.cancelledMeaning;
    }

    public void setCancelledMeaning(String cancelledMeaning) {
        this.cancelledMeaning = cancelledMeaning;
    }

    public String getConsignedMeaning() {
        return this.consignedMeaning;
    }

    public void setConsignedMeaning(String consignedMeaning) {
        this.consignedMeaning = consignedMeaning;
    }

    public String getReturnedMeaning() {
        return this.returnedMeaning;
    }

    public void setReturnedMeaning(String returnedMeaning) {
        this.returnedMeaning = returnedMeaning;
    }

    public String getFreeMeaning() {
        return this.freeMeaning;
    }

    public void setFreeMeaning(String freeMeaning) {
        this.freeMeaning = freeMeaning;
    }

    public String getImmedShippedMeaning() {
        return this.immedShippedMeaning;
    }

    public void setImmedShippedMeaning(String immedShippedMeaning) {
        this.immedShippedMeaning = immedShippedMeaning;
    }

    public String getUrgentMeaning() {
        return this.urgentMeaning;
    }

    public void setUrgentMeaning(String urgentMeaning) {
        this.urgentMeaning = urgentMeaning;
    }

    public BigDecimal getNetReceivedQuantity() {
        return this.netReceivedQuantity;
    }

    public void setNetReceivedQuantity(BigDecimal netReceivedQuantity) {
        this.netReceivedQuantity = netReceivedQuantity;
    }

    public BigDecimal getNetDeliverQuantity() {
        return this.netDeliverQuantity;
    }

    public void setNetDeliverQuantity(BigDecimal netDeliverQuantity) {
        this.netDeliverQuantity = netDeliverQuantity;
    }

    public BigDecimal getInvoicedQuantity() {
        return this.invoicedQuantity;
    }

    public void setInvoicedQuantity(BigDecimal invoicedQuantity) {
        this.invoicedQuantity = invoicedQuantity;
    }

    public String getExternalSystemCode() {
        return this.externalSystemCode;
    }

    public void setExternalSystemCode(String externalSystemCode) {
        this.externalSystemCode = externalSystemCode;
    }

    public String getDisplayUnitPrice() {
        return this.displayUnitPrice;
    }

    public void setDisplayUnitPrice(String displayUnitPrice) {
        this.displayUnitPrice = displayUnitPrice;
    }

    public String getDisplayEnteredTaxIncludedPrice() {
        return this.displayEnteredTaxIncludedPrice;
    }

    public void setDisplayEnteredTaxIncludedPrice(String displayEnteredTaxIncludedPrice) {
        this.displayEnteredTaxIncludedPrice = displayEnteredTaxIncludedPrice;
    }

    public String getDisplayLineAmount() {
        return this.displayLineAmount;
    }

    public void setDisplayLineAmount(String displayLineAmount) {
        this.displayLineAmount = displayLineAmount;
    }

    public String getDisplayTaxIncludedLineAmount() {
        return this.displayTaxIncludedLineAmount;
    }

    public void setDisplayTaxIncludedLineAmount(String displayTaxIncludedLineAmount) {
        this.displayTaxIncludedLineAmount = displayTaxIncludedLineAmount;
    }

    public String getDisplayReleaseNum() {
        return this.displayReleaseNum;
    }

    public void setDisplayReleaseNum(String displayReleaseNum) {
        this.displayReleaseNum = displayReleaseNum;
    }

    public Date getCreateDate() {
        return this.createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Long getCreateBy() {
        return this.createBy;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    public Date getErpCreationDate() {
        return this.erpCreationDate;
    }

    public void setErpCreationDate(Date erpCreationDate) {
        this.erpCreationDate = erpCreationDate;
    }

    public String getErpCreatedName() {
        return this.erpCreatedName;
    }

    public void setErpCreatedName(String erpCreatedName) {
        this.erpCreatedName = erpCreatedName;
    }

    public Integer getCanCreateAsnFlag() {
        return this.canCreateAsnFlag;
    }

    public void setCanCreateAsnFlag(Integer canCreateAsnFlag) {
        this.canCreateAsnFlag = canCreateAsnFlag;
    }

    public Integer getConfirmedFlag() {
        return this.confirmedFlag;
    }

    public void setConfirmedFlag(Integer confirmedFlag) {
        this.confirmedFlag = confirmedFlag;
    }

    public Integer getPublishCancelFlag() {
        return this.publishCancelFlag;
    }

    public void setPublishCancelFlag(Integer publishCancelFlag) {
        this.publishCancelFlag = publishCancelFlag;
    }

    public String getDisplayStatusCode() {
        return this.displayStatusCode;
    }

    public void setDisplayStatusCode(String displayStatusCode) {
        this.displayStatusCode = displayStatusCode;
    }

    public String getChartCode() {
        return this.chartCode;
    }

    public void setChartCode(String chartCode) {
        this.chartCode = chartCode;
    }

    public String getChartVersion() {
        return this.chartVersion;
    }

    public void setChartVersion(String chartVersion) {
        this.chartVersion = chartVersion;
    }

    public String getDisplayStatusMeaning() {
        return this.displayStatusMeaning;
    }

    public void setDisplayStatusMeaning(String displayStatusMeaning) {
        this.displayStatusMeaning = displayStatusMeaning;
    }

    public Class<? extends SecurityToken> associateEntityClass() {
        return PoLineLocation.class;
    }

    public String getPurchaseAgentName() {
        return this.purchaseAgentName;
    }

    public void setPurchaseAgentName(String purchaseAgentName) {
        this.purchaseAgentName = purchaseAgentName;
    }

    public Long getSupplierCompanyId() {
        return this.supplierCompanyId;
    }

    public void setSupplierCompanyId(Long supplierCompanyId) {
        this.supplierCompanyId = supplierCompanyId;
    }

    public Integer getPriceSensitiveFlag() {
        return this.priceSensitiveFlag;
    }

    public void setPriceSensitiveFlag(Integer priceSensitiveFlag) {
        this.priceSensitiveFlag = priceSensitiveFlag;
    }

    public Long getSupplierId() {
        return this.supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public Long getOuId() {
        return this.ouId;
    }

    public void setOuId(Long ouId) {
        this.ouId = ouId;
    }

    public String getDisplayPoNum() {
        return this.displayPoNum;
    }

    public void setDisplayPoNum(String displayPoNum) {
        this.displayPoNum = displayPoNum;
    }

    public Integer getDeliveryDateRejectFlag() {
        return this.deliveryDateRejectFlag;
    }

    public void setDeliveryDateRejectFlag(Integer deliveryDateRejectFlag) {
        this.deliveryDateRejectFlag = deliveryDateRejectFlag;
    }

    public String getDisplayLineNum() {
        return this.displayLineNum;
    }

    public void setDisplayLineNum(String displayLineNum) {
        this.displayLineNum = displayLineNum;
    }

    public String getDisplayLineLocationNum() {
        return this.displayLineLocationNum;
    }

    public void setDisplayLineLocationNum(String displayLineLocationNum) {
        this.displayLineLocationNum = displayLineLocationNum;
    }

    public void setSubmittedDate(Date submittedDate) {
        this.submittedDate = submittedDate;
    }

    public Date getReleasedDate() {
        return this.releasedDate;
    }

    public void setReleasedDate(Date releasedDate) {
        this.releasedDate = releasedDate;
    }

    public Date getConfirmedDate() {
        return this.confirmedDate;
    }

    public void setConfirmedDate(Date confirmedDate) {
        this.confirmedDate = confirmedDate;
    }

    public Long getTenantId() {
        return this.tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public String getStatusMeaning() {
        return this.statusMeaning;
    }

    public void setStatusMeaning(String statusMeaning) {
        this.statusMeaning = statusMeaning;
    }

    public String getErpStatusMeaning() {
        return this.erpStatusMeaning;
    }

    public void setErpStatusMeaning(String erpStatusMeaning) {
        this.erpStatusMeaning = erpStatusMeaning;
    }

    public Long getPoLineId() {
        return this.poLineId;
    }

    public void setPoLineId(Long poLineId) {
        this.poLineId = poLineId;
    }

    public Long getPoLineLocationId() {
        return this.poLineLocationId;
    }

    public void setPoLineLocationId(Long poLineLocationId) {
        this.poLineLocationId = poLineLocationId;
    }

    public Long getPoHeaderId() {
        return this.poHeaderId;
    }

    public void setPoHeaderId(Long poHeaderId) {
        this.poHeaderId = poHeaderId;
    }

    public String getStatusCode() {
        return this.statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getSupplierCode() {
        return this.supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    public String getSupplierName() {
        return this.supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public Integer getVersionNum() {
        return this.versionNum;
    }

    public void setVersionNum(Integer versionNum) {
        this.versionNum = versionNum;
    }

    public String getReleaseNum() {
        return this.releaseNum;
    }

    public void setReleaseNum(String releaseNum) {
        this.releaseNum = releaseNum;
    }

    public String getItemCode() {
        return this.itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemName() {
        return this.itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getOldItemCode() {
        return this.oldItemCode;
    }

    public void setOldItemCode(String oldItemCode) {
        this.oldItemCode = oldItemCode;
    }

    public BigDecimal getQuantity() {
        return this.quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getReceivedQuantity() {
        return this.receivedQuantity;
    }

    public void setReceivedQuantity(BigDecimal receivedQuantity) {
        this.receivedQuantity = receivedQuantity;
    }

    public BigDecimal getUnitPrice() {
        return this.unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getEnteredTaxIncludedPrice() {
        return this.enteredTaxIncludedPrice;
    }

    public void setEnteredTaxIncludedPrice(BigDecimal enteredTaxIncludedPrice) {
        this.enteredTaxIncludedPrice = enteredTaxIncludedPrice;
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

    public BigDecimal getUnitPriceBatch() {
        return this.unitPriceBatch;
    }

    public void setUnitPriceBatch(BigDecimal unitPriceBatch) {
        this.unitPriceBatch = unitPriceBatch;
    }

    public String getUomName() {
        return this.uomName;
    }

    public void setUomName(String uomName) {
        this.uomName = uomName;
    }

    public String getCurrencyCode() {
        return this.currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public Date getNeedByDate() {
        return this.needByDate;
    }

    public void setNeedByDate(Date needByDate) {
        this.needByDate = needByDate;
    }

    public Date getPromiseDeliveryDate() {
        return this.promiseDeliveryDate;
    }

    public void setPromiseDeliveryDate(Date promiseDeliveryDate) {
        this.promiseDeliveryDate = promiseDeliveryDate;
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

    public String getManufacturerName() {
        return this.manufacturerName;
    }

    public void setManufacturerName(String manufacturerName) {
        this.manufacturerName = manufacturerName;
    }

    public String getBrand() {
        return this.brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getErpStatus() {
        return this.erpStatus;
    }

    public void setErpStatus(String erpStatus) {
        this.erpStatus = erpStatus;
    }

    public Integer getClosedFlag() {
        return this.closedFlag;
    }

    public void setClosedFlag(Integer closedFlag) {
        this.closedFlag = closedFlag;
    }

    public Integer getCancelledFlag() {
        return this.cancelledFlag;
    }

    public void setCancelledFlag(Integer cancelledFlag) {
        this.cancelledFlag = cancelledFlag;
    }

    public Integer getConsignedFlag() {
        return this.consignedFlag;
    }

    public void setConsignedFlag(Integer consignedFlag) {
        this.consignedFlag = consignedFlag;
    }

    public Integer getReturnedFlag() {
        return this.returnedFlag;
    }

    public void setReturnedFlag(Integer returnedFlag) {
        this.returnedFlag = returnedFlag;
    }

    public Integer getFreeFlag() {
        return this.freeFlag;
    }

    public void setFreeFlag(Integer freeFlag) {
        this.freeFlag = freeFlag;
    }

    public Integer getImmedShippedFlag() {
        return this.immedShippedFlag;
    }

    public void setImmedShippedFlag(Integer immedShippedFlag) {
        this.immedShippedFlag = immedShippedFlag;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getFeedback() {
        return this.feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getShipToThirdPartyAddress() {
        return this.shipToThirdPartyAddress;
    }

    public void setShipToThirdPartyAddress(String shipToThirdPartyAddress) {
        this.shipToThirdPartyAddress = shipToThirdPartyAddress;
    }

    public String getShipToThirdPartyContact() {
        return this.shipToThirdPartyContact;
    }

    public void setShipToThirdPartyContact(String shipToThirdPartyContact) {
        this.shipToThirdPartyContact = shipToThirdPartyContact;
    }

    public String getSupplierSiteName() {
        return this.supplierSiteName;
    }

    public void setSupplierSiteName(String supplierSiteName) {
        this.supplierSiteName = supplierSiteName;
    }

    public String getCompanyName() {
        return this.companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getOuName() {
        return this.ouName;
    }

    public void setOuName(String ouName) {
        this.ouName = ouName;
    }

    public String getPurOrganizationName() {
        return this.purOrganizationName;
    }

    public void setPurOrganizationName(String purOrganizationName) {
        this.purOrganizationName = purOrganizationName;
    }

    public String getInvOrganizationName() {
        return this.invOrganizationName;
    }

    public void setInvOrganizationName(String invOrganizationName) {
        this.invOrganizationName = invOrganizationName;
    }

    public String getInventoryName() {
        return this.inventoryName;
    }

    public void setInventoryName(String inventoryName) {
        this.inventoryName = inventoryName;
    }

    public String getLocationName() {
        return this.locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getBillToLocationAddress() {
        return this.billToLocationAddress;
    }

    public void setBillToLocationAddress(String billToLocationAddress) {
        this.billToLocationAddress = billToLocationAddress;
    }

    public String getPriceUomName() {
        return this.priceUomName;
    }

    public void setPriceUomName(String priceUomName) {
        this.priceUomName = priceUomName;
    }

    public Long getSubmittedBy() {
        return this.submittedBy;
    }

    public void setSubmittedBy(Long submittedBy) {
        this.submittedBy = submittedBy;
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

    public String getPrNum() {
        return this.prNum;
    }

    public void setPrNum(String prNum) {
        this.prNum = prNum;
    }

    public String getPrLineNum() {
        return this.prLineNum;
    }

    public void setPrLineNum(String prLineNum) {
        this.prLineNum = prLineNum;
    }

    public String getSourceCode() {
        return this.sourceCode;
    }

    public void setSourceCode(String sourceCode) {
        this.sourceCode = sourceCode;
    }

    public Date getSubmittedDate() {
        return this.submittedDate;
    }

    public String getProductNum() {
        return this.productNum;
    }

    public void setProductNum(String productNum) {
        this.productNum = productNum;
    }

    public String getProductName() {
        return this.productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCatalogName() {
        return this.catalogName;
    }

    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
    }

    public String getFrozenFlagMeaning() {
        return this.frozenFlagMeaning;
    }

    public void setFrozenFlagMeaning(String frozenFlagMeaning) {
        this.frozenFlagMeaning = frozenFlagMeaning;
    }

    public Integer getFrozenFlag() {
        return this.frozenFlag;
    }

    public void setFrozenFlag(Integer frozenFlag) {
        this.frozenFlag = frozenFlag;
    }

    public String getShipToThirdPartyName() {
        return this.shipToThirdPartyName;
    }

    public void setShipToThirdPartyName(String shipToThirdPartyName) {
        this.shipToThirdPartyName = shipToThirdPartyName;
    }

    public String getShipToLocationAddress() {
        return this.shipToLocationAddress;
    }

    public void setShipToLocationAddress(String shipToLocationAddress) {
        this.shipToLocationAddress = shipToLocationAddress;
    }

    public String getPoSourcePlatform() {
        return this.poSourcePlatform;
    }

    public void setPoSourcePlatform(String poSourcePlatform) {
        this.poSourcePlatform = poSourcePlatform;
    }

    public String getPoSourcePlatformMeaning() {
        return this.poSourcePlatformMeaning;
    }

    public void setPoSourcePlatformMeaning(String poSourcePlatformMeaning) {
        this.poSourcePlatformMeaning = poSourcePlatformMeaning;
    }

    public String getErpPrNum() {
        return this.erpPrNum;
    }

    public void setErpPrNum(String erpPrNum) {
        this.erpPrNum = erpPrNum;
    }

    public String getErpPrLineNum() {
        return this.erpPrLineNum;
    }

    public void setErpPrLineNum(String erpPrLineNum) {
        this.erpPrLineNum = erpPrLineNum;
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

    public BigDecimal getShippedQuantity() {
        return this.shippedQuantity;
    }

    public void setShippedQuantity(BigDecimal shippedQuantity) {
        this.shippedQuantity = shippedQuantity;
    }

    public String toString() {
        return "PoLineLocationVO{poLineId=" + this.poLineId + ", poLineLocationId=" + this.poLineLocationId + ", poHeaderId=" + this.poHeaderId + ", statusCode='" + this.statusCode + '\'' + ", statusMeaning='" + this.statusMeaning + '\'' + ", poNum='" + this.displayPoNum + '\'' + ", supplierCode='" + this.supplierCode + '\'' + ", supplierName='" + this.supplierName + '\'' + ", versionNum='" + this.versionNum + '\'' + ", releaseNum='" + this.releaseNum + '\'' + ", displayLineNum='" + this.displayLineNum + '\'' + ", displayLineLocationNum='" + this.displayLineLocationNum + '\'' + ", itemCode='" + this.itemCode + '\'' + ", itemName='" + this.itemName + '\'' + ", oldItemCode='" + this.oldItemCode + '\'' + ", quantity=" + this.quantity + ", shippedQuantity=" + this.shippedQuantity + ", receivedQuantity=" + this.receivedQuantity + ", unitPrice=" + this.unitPrice + ", enteredTaxIncludedPrice=" + this.enteredTaxIncludedPrice + ", lineAmount=" + this.lineAmount + ", taxIncludedLineAmount=" + this.taxIncludedLineAmount + ", unitPriceBatch=" + this.unitPriceBatch + ", uomName='" + this.uomName + '\'' + ", currencyCode='" + this.currencyCode + '\'' + ", needByDate=" + this.needByDate + ", promiseDeliveryDate=" + this.promiseDeliveryDate + ", specifications='" + this.specifications + '\'' + ", model='" + this.model + '\'' + ", manufacturerName='" + this.manufacturerName + '\'' + ", brand='" + this.brand + '\'' + ", erpStatus='" + this.erpStatus + '\'' + ", erpStatusMeaning='" + this.erpStatusMeaning + '\'' + ", closedFlag=" + this.closedFlag + ", cancelledFlag=" + this.cancelledFlag + ", consignedFlag=" + this.consignedFlag + ", returnedFlag=" + this.returnedFlag + ", freeFlag=" + this.freeFlag + ", immedShippedFlag=" + this.immedShippedFlag + ", remark='" + this.remark + '\'' + ", feedback='" + this.feedback + '\'' + ", shipToThirdPartyAddress='" + this.shipToThirdPartyAddress + '\'' + ", shipToThirdPartyContact='" + this.shipToThirdPartyContact + '\'' + ", supplierSiteName='" + this.supplierSiteName + '\'' + ", companyName='" + this.companyName + '\'' + ", ouName='" + this.ouName + '\'' + ", purOrganizationName='" + this.purOrganizationName + '\'' + ", invOrganizationName='" + this.invOrganizationName + '\'' + ", inventoryName='" + this.inventoryName + '\'' + ", locationName='" + this.locationName + '\'' + ", billToLocationAddress='" + this.billToLocationAddress + '\'' + ", priceUomName='" + this.priceUomName + '\'' + ", submittedDate='" + this.submittedDate + '\'' + ", submittedBy='" + this.submittedBy + '\'' + ", urgentFlag=" + this.urgentFlag + ", urgentDate=" + this.urgentDate + ", prNum='" + this.prNum + '\'' + ", prLineNum='" + this.prLineNum + '\'' + ", sourceCode='" + this.sourceCode + '\'' + ", tenantId=" + this.tenantId + '}';
    }

    public PoLineLocationSupVO copyPropertiesSup() {
        PoLineLocationSupVO poLineLocationSupVO = new PoLineLocationSupVO();
        BeanUtils.copyProperties(this, poLineLocationSupVO);
        poLineLocationSupVO.setCreateBy(this.getCreatedBy());
        return poLineLocationSupVO;
    }

    public String getTitleName() {
        return this.titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }

    public String getProductionOrderNum() {
        return this.productionOrderNum;
    }

    public void setProductionOrderNum(String productionOrderNum) {
        this.productionOrderNum = productionOrderNum;
    }

    public BigDecimal getTaxRate() {
        return this.taxRate;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    public String getHeaderDomesticCurrencyCode() {
        return this.headerDomesticCurrencyCode;
    }

    public void setHeaderDomesticCurrencyCode(String headerDomesticCurrencyCode) {
        this.headerDomesticCurrencyCode = headerDomesticCurrencyCode;
    }

    public Integer getFinancialPrecision() {
        return this.financialPrecision;
    }

    public void setFinancialPrecision(Integer financialPrecision) {
        this.financialPrecision = financialPrecision;
    }

    public Integer getDomesticFinancialPrecision() {
        return this.domesticFinancialPrecision;
    }

    public void setDomesticFinancialPrecision(Integer domesticFinancialPrecision) {
        this.domesticFinancialPrecision = domesticFinancialPrecision;
    }
}
