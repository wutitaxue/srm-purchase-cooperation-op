package org.srm.purchasecooperation.order.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.choerodon.core.exception.CommonException;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import io.choerodon.mybatis.pagehelper.domain.Sort;
import io.choerodon.mybatis.pagehelper.domain.Sort.Direction;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Transient;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;
import org.hzero.boot.platform.lov.annotation.LovValue;
import org.hzero.core.base.BaseConstants;
import org.hzero.core.base.BaseConstants.Flag;
import org.hzero.core.jackson.annotation.IgnoreTimeZone;
import org.hzero.core.util.FieldNameUtils;
import org.hzero.mybatis.domian.SecurityToken;
import org.hzero.starter.keyencrypt.core.Encrypt;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.Assert;
import org.srm.common.mybatis.domain.ExpandDomain;
import org.srm.purchasecooperation.order.domain.entity.PoItemBom;
import org.srm.purchasecooperation.order.domain.entity.PoLineLocation;
import org.srm.purchasecooperation.order.domain.vo.PoPriceLibReturnVO;
import org.srm.purchasecooperation.order.infra.utils.OrderUtil;
import org.srm.purchasecooperation.order.infra.utils.SortUtils;
import org.srm.purchasecooperation.pr.domain.entity.PrLine;
import org.srm.purchasecooperation.pr.domain.repository.PrLineRepository;

public class PoLineDetailDTO extends ExpandDomain {
    public static final String FIELD_DATE_EQUALLY_FLAG = "dateEquallyFlag";
    public static final String FIELD_TAX_WITHOUT_FREIGHT_PRICE = "taxWithoutFreightPrice";
    @ApiModelProperty("租户ID")
    private Long tenantId;
    @Encrypt
    private Long poHeaderId;
    @ApiModelProperty("供应商ID")
    @Encrypt
    private Long supplierId;
    @ApiModelProperty("公司ID")
    @Encrypt
    private Long companyId;
    @ApiModelProperty("业务实体ID")
    @Encrypt
    private Long ouId;
    @ApiModelProperty("数量")
    @DecimalMin(
            groups = {PoLineDetailDTO.UpdateCheck.class},
            value = "0"
    )
    private BigDecimal quantity;
    @ApiModelProperty("数量是否可编辑")
    private Integer quantityEditFlag;
    @ApiModelProperty("数量是否必输")
    private Integer quantityEnableFlag;
    @ApiModelProperty("原需求数量")
    private BigDecimal originalQuantity;
    @ApiModelProperty("单位ID")
    @NotNull(
            groups = {PoLineDetailDTO.UpdateCheck.class}
    )
    private Long uomId;
    @ApiModelProperty("单位代码")
    private String uomCode;
    @ApiModelProperty("单位名称")
    private String uomName;
    @ApiModelProperty("单价")
    @DecimalMin(
            groups = {PoLineDetailDTO.UpdateCheck.class},
            value = "0"
    )
    private BigDecimal unitPrice;
    @ApiModelProperty("原币含税单价")
    @DecimalMin(
            groups = {PoLineDetailDTO.UpdateCheck.class},
            value = "0"
    )
    private BigDecimal enteredTaxIncludedPrice;
    @ApiModelProperty("不含税行金额")
    private BigDecimal lineAmount;
    @ApiModelProperty("含税行金额")
    private BigDecimal taxIncludedLineAmount;
    @ApiModelProperty("币种")
    private String currencyName;
    @ApiModelProperty("币种代码")
    private String currencyCode;
    @ApiModelProperty("价格库合同标识")
    private Integer priceContractFlag;
    @ApiModelProperty("品牌，物料属性")
    private String brand;
    @ApiModelProperty("规格，物料属性")
    private String specifications;
    @ApiModelProperty("型号，物料属性")
    private String model;
    @ApiModelProperty("制造商名称")
    private String manufacturerName;
    @ApiModelProperty("收货组织")
    private String organizationName;
    @ApiModelProperty("收货组织")
    private String invOrganizationName;
    @ApiModelProperty("税率ID")
    @NotNull(
            groups = {PoLineDetailDTO.UpdateCheck.class}
    )
    @Encrypt
    private Long taxId;
    @ApiModelProperty("税率")
    @NotNull(
            groups = {PoLineDetailDTO.UpdateCheck.class}
    )
    private BigDecimal taxRate;
    @ApiModelProperty("单位价格批量")
    private BigDecimal unitPriceBatch;
    @ApiModelProperty("采购方行备注")
    @Length(
            max = 480,
            groups = {PoLineDetailDTO.UpdateCheck.class}
    )
    private String remark;
    @ApiModelProperty("反馈信息")
    private String feedback;
    @ApiModelProperty("需求日期")
    @DateTimeFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    private Date needByDate;
    @ApiModelProperty("预计发货日期")
    @DateTimeFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    private Date expectShipDate;
    @ApiModelProperty("承诺交货日期")
    @DateTimeFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @IgnoreTimeZone
    private Date promiseDeliveryDate;
    @ApiModelProperty("承诺交货日期是否勾选可编辑")
    private Integer deliveryDateEditFlag;
    @ApiModelProperty("承诺交货日期是否必输")
    private Integer deliveryDateEnableFlag;
    @ApiModelProperty("收货库房")
    private String inventoryName;
    @ApiModelProperty("收货库位")
    private String locationName;
    @ApiModelProperty("交期审核通过标志")
    private Integer deliveryDateRejectFlag;
    @ApiModelProperty("承诺日期和期望日期是否一致标志 1 一致 0 不一致")
    private Integer dateEquallyFlag;
    private Long lineVersionNumber;
    private Long locationVersionNumber;
    private Long versionNum;
    @ApiModelProperty("价格屏蔽标志 1  屏蔽 0 不屏蔽")
    private Integer priceShieldFlag;
    @Encrypt
    private Long poLineId;
    @ApiModelProperty("行号")
    private Integer lineNum;
    @ApiModelProperty("展示行号")
    private String displayLineNum;
    @ApiModelProperty("物料编码")
    private String itemCode;
    @ApiModelProperty("物料名称")
    @NotBlank(
            groups = {PoLineDetailDTO.UpdateCheck.class}
    )
    private String itemName;
    @ApiModelProperty("旧物料编码")
    private String oldItemCode;
    @ApiModelProperty("物料类别名称")
    private String categoryName;
    @ApiModelProperty("汇率")
    private BigDecimal rate;
    @ApiModelProperty("是否寄售标志")
    private Integer consignedFlag;
    @ApiModelProperty("是否退回标志")
    private Integer returnedFlag;
    @ApiModelProperty("取消发布标识")
    private Integer publishCancelFlag;
    @ApiModelProperty("是否免费标志")
    private Integer freeFlag;
    @ApiModelProperty("直发标志")
    private Integer immedShippedFlag;
    @ApiModelProperty("价格单位转换分子")
    private BigDecimal unitConversionMolecule;
    @ApiModelProperty("价格单位转换分母")
    private BigDecimal unitConversionDenominator;
    @ApiModelProperty("价格单位名称")
    private String priceUomName;
    @ApiModelProperty("价格单位名称")
    private String priceUomCode;
    @ApiModelProperty("采购申请类型")
    private Long prTypeId;
    @ApiModelProperty("物料类别编码")
    private String categoryCode;
    @ApiModelProperty("部门编码")
    private String unitCode;
    @ApiModelProperty("结算财务组织编码")
    private String clearOrganizationCode;
    @ApiModelProperty("应付组织编码")
    private String copeOrganizationCode;
    @ApiModelProperty("收货组织编码")
    private String invOrganizationCode;
    @ApiModelProperty("收货库房编码")
    private String invInventoryCode;
    @ApiModelProperty("收货库位编码")
    private String invLocationCode;
    @ApiModelProperty("税种代码")
    private String taxCode;
    @ApiModelProperty("订单开始日期")
    @DateTimeFormat(pattern = BaseConstants.Pattern.DATE)
    @JsonFormat(pattern = BaseConstants.Pattern.DATE)
    private LocalDate needStartDate;
    @ApiModelProperty("需求日期")
    private LocalDate neededDate;
    @ApiModelProperty("表面处理(是/否)")
    private String surfaceFlag;
    @ApiModelProperty("行附件UUID")
    private String attachmentUuid;
    @ApiModelProperty("价格单位转换关系")
    private String priceUomConversion;
    @Encrypt
    private Long poLineLocationId;
    @ApiModelProperty("发运行行号")
    private String lineLocationNum;
    @ApiModelProperty("发运行展示行号")
    private String displayLineLocationNum;
    @ApiModelProperty("采购申请号")
    private String displayPrNum;
    @ApiModelProperty("采购申请行号")
    private String displayPrLineNum;
    @ApiModelProperty("申请人")
    private String prRequestedName;
    @ApiModelProperty("送达方编码")
    @Length(
            max = 150,
            groups = {PoLineDetailDTO.UpdateCheck.class}
    )
    private String shipToThirdPartyCode;
    @ApiModelProperty("送达方名称")
    @Length(
            max = 360,
            groups = {PoLineDetailDTO.UpdateCheck.class}
    )
    private String shipToThirdPartyName;
    @ApiModelProperty("送货地址")
    @Length(
            max = 360,
            groups = {PoLineDetailDTO.UpdateCheck.class}
    )
    private String shipToThirdPartyAddress;
    @ApiModelProperty("联系人信息")
    @Length(
            max = 360,
            groups = {PoLineDetailDTO.UpdateCheck.class}
    )
    private String shipToThirdPartyContact;
    @ApiModelProperty("供应商公司ID,HPFM_COMPANY.COMPANY_ID")
    @Encrypt
    private Long supplierCompanyId;
    @ApiModelProperty(
            value = "关闭状态",
            required = true
    )
    private Integer closedFlag;
    @ApiModelProperty(
            value = "取消状态",
            required = true
    )
    private Integer cancelledFlag;
    @ApiModelProperty(
            value = "冻结标识",
            required = true
    )
    private Integer frozenFlag;
    @ApiModelProperty(
            value = "加急状态",
            required = true
    )
    private Integer urgentFlag;
    @ApiModelProperty(
            value = "确定状态",
            required = true
    )
    private Integer confirmedFlag;
    @ApiModelProperty(
            value = "需求部门",
            required = true
    )
    private String unitName;
    @ApiModelProperty(
            value = "需求部门Id",
            required = true
    )
    private Long unitId;
    @ApiModelProperty("账户分配类别ID")
    private Long accountAssignTypeId;
    @ApiModelProperty("账户分配类别编码")
    private String accountAssignTypeCode;
    @ApiModelProperty("成本中心主键")
    private Long costId;
    @ApiModelProperty("成本中心编码")
    private String costCode;
    @ApiModelProperty("成本中心名称")
    private String costName;
    @ApiModelProperty("总账主键")
    private Long accountSubjectId;
    @ApiModelProperty("外部总账编码")
    private String accountSubjectNum;
    @ApiModelProperty("外部总账名称")
    private String accountSubjectName;
    @ApiModelProperty("工作分解结构")
    private String wbsCode;
    @ApiModelProperty("wbs编码")
    private String wbs;
    @ApiModelProperty("账户分配类别的必填字段名集合")
    private Set<String> assignTypeRequiredFieldNames;
    @ApiModelProperty("采购申请号/行号")
    private String displayPrNumAndDisplayPrLineNum;
    @ApiModelProperty("接收允差数量")
    private BigDecimal receiveToleranceQuantity;
    @ApiModelProperty("订单号")
    private String displayPoNum;
    @ApiModelProperty("供应商编码")
    private String supplierCode;
    @ApiModelProperty("供应商名称")
    private String supplierName;
    @ApiModelProperty("交期反馈时间")
    @DateTimeFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    private Date feedbackDate;
    @ApiModelProperty("订单类型")
    private String poTypeCode;
    @ApiModelProperty("订单类型Meaning")
    private String poTypeCodeMeaning;
    @ApiModelProperty("公司")
    private String companyName;
    @ApiModelProperty("业务实体")
    private String orgName;
    @ApiModelProperty("采购组织")
    private String purOrganizationName;
    @ApiModelProperty("采购员")
    private String agentName;
    @ApiModelProperty("行版本号")
    private Long objectVersionNumber;
    @Encrypt
    private Long priceLibraryId;
    private String sourceNumAndLine;
    private Integer ladderInquiryFlag;
    private String statusCode;
    @ApiModelProperty("订单状态，SODR.PO_STATUS")
    @LovValue(
            lovCode = "SODR.PO_STATUS",
            meaningField = "displayStatusMeaning"
    )
    private String displayStatusCode;
    @ApiModelProperty("订单状态meaning")
    @Transient
    private String displayStatusMeaning;
    @ApiModelProperty("来源平台")
    private String sourcePlatformCode;
    @ApiModelProperty("收货地址ID")
    private Long locationId;
    @ApiModelProperty("收货地址")
    private String receiverAddress;
    @ApiModelProperty("采购组织ID")
    @Encrypt
    private Long purchaseOrgId;
    @ApiModelProperty("商品ID")
    private Long productId;
    @ApiModelProperty("采购员ID")
    @Encrypt
    private Long agentId;
    @ApiModelProperty("物料ID")
    @Encrypt
    private Long itemId;
    @ApiModelProperty("物料BOM保存关联的itemID")
    private Long saveBomItemId;
    @ApiModelProperty("物料类别ID")
    private Long categoryId;
    @ApiModelProperty("收货组织ID/库存组织ID")
    @NotNull(
            groups = {PoLineDetailDTO.UpdateCheck.class}
    )
    private Long invOrganizationId;
    @ApiModelProperty("申请人ID")
    private Long prRequestedBy;
    @ApiModelProperty("申请行ID")
    @Encrypt
    private Long prLineId;
    @ApiModelProperty("申请头ID")
    @Encrypt
    private Long prHeaderId;
    @ApiModelProperty("商品编码")
    private String productNum;
    @ApiModelProperty("商品名称")
    private String productName;
    @ApiModelProperty("商品目录ID")
    private Long catalogId;
    @ApiModelProperty("商品目录")
    private String catalogName;
    @ApiModelProperty("收货库房ID")
    @Encrypt
    private Long invInventoryId;
    @ApiModelProperty("收货库位ID")
    private Long invLocationId;
    @ApiModelProperty("发运行删除标识")
    private Integer poLineLocationDeleteFlag;
    @ApiModelProperty("订单行接收状态")
    private Integer beyondQuantity;
    @ApiModelProperty("划线价")
    private BigDecimal jdPrice;
    @ApiModelProperty("协议头id(用作并单规则校验)")
    private Long pcHeaderId;
    @ApiModelProperty("协议单据状态")
    private String receiptsStatus;
    @ApiModelProperty("协议类型ID")
    private Long pcTypeId;
    @ApiModelProperty("协议单据编号")
    private String contractNum;
    @ApiModelProperty("寻源单据编号")
    private String sourceCodeNum;
    @ApiModelProperty("订单行价格修改标识")
    private Integer modifyPriceFlag;
    @ApiModelProperty("含税单价(不含运费)")
    private BigDecimal taxWithoutFreightPrice;
    private String title;
    @ApiModelProperty("最近一次采购价")
    private BigDecimal lastPurchasePrice;
    @ApiModelProperty("部门ID")
    private Long departmentId;
    @ApiModelProperty("部门名称")
    private String departmentName;
    @ApiModelProperty("结算财务组织ID")
    private Long clearOrganizationId;
    @ApiModelProperty("结算财务组织名称")
    private String clearOrganizationName;
    @ApiModelProperty("应付组织ID")
    private Long copeOrganizationId;
    @ApiModelProperty("应付组织名称")
    private String copeOrganizationName;
    @ApiModelProperty("图号")
    private String chartCode;
    @ApiModelProperty("图号")
    private String chartCodeNew;
    @ApiModelProperty("图纸版本")
    private String chartVersion;
    @ApiModelProperty("表面处理标识(1为是，0为否)")
    private Integer surfaceTreatFlag;
    @Length(
            max = 120,
            groups = {PoLineDetailDTO.UpdateCheck.class}
    )
    @ApiModelProperty("协议编号")
    private String pcNum;
    @ApiModelProperty("供应商料号")
    private String supplierItemNum;
    @ApiModelProperty("生产工单号")
    private String productionOrderNum;
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
    @ApiModelProperty("供应商料号描述")
    private String supplierItemDesc;
    @ApiModelProperty("基准价类型(NET/未税价|TAX/含税价")
    private String benchmarkPriceType;
    private String sourceBillTypeCode;
    @ApiModelProperty("占用采购协议头id")
    private Long holdPcHeaderId;
    @ApiModelProperty("占用采购协议行id")
    private Long holdPcLineId;
    @ApiModelProperty("占用采购协议号")
    private String holdPcNum;
    @ApiModelProperty("占用采购协议行号")
    private Long holdPcLineNum;
    @ApiModelProperty("协议行可占用数量")
    private BigDecimal canHoldPcQuantity;
    @ApiModelProperty("申请行可占用数量")
    private BigDecimal canHoldPrQuantity;
    @ApiModelProperty("申请行物料id(用于判断当前订单行物料是否可以修改)")
    @Encrypt
    private Long prLineItemId;
    @ApiModelProperty("价格库税率id(用于判断税率是否可以修改)")
    private Long priceTaxId;
    @Transient
    @ApiModelProperty("选择的供应商公司id(申请转订单使用)")
    private Long selectSupplierCompanyId;
    @ApiModelProperty("选择的供应商租户id(申请转订单使用)")
    @Transient
    private Long selectSupplierTenantId;
    @ApiModelProperty("选择的供应商公司编码(申请转订单使用)")
    @Transient
    private String selectSupplierCode;
    @ApiModelProperty("选择的供应商租户名称(申请转订单使用)")
    @Transient
    private String selectSupplierCompanyName;
    @Transient
    private Boolean newPriceFlag;
    @ApiModelProperty("阶梯价格库ID")
    private Long ladderPriceLibId;
    @ApiModelProperty("物料单位")
    private Long itemUomId;
    @ApiModelProperty("确认标识")
    private Integer confirmFlag;
    @ApiModelProperty("汇率")
    private BigDecimal exchangeRate;
    @ApiModelProperty("本币含税单价")
    private BigDecimal domesticTaxIncludedPrice;
    @ApiModelProperty("本币不含税单价")
    private BigDecimal domesticUnitPrice;
    @ApiModelProperty("本币含税金额")
    private BigDecimal domesticTaxIncludedLineAmount;
    @ApiModelProperty("本币不含税金额")
    private BigDecimal domesticLineAmount;
    @ApiModelProperty("保存/提交修改价格flag")
    private Integer updatePriceFlag;
    @Transient
    @ApiModelProperty("行原币币种财务精度")
    private Integer financialPrecision;
    @Transient
    @ApiModelProperty("行原币币种财务精度")
    private Integer domesticFinancialPrecision;
    @ApiModelProperty("阶梯价判断标识")
    private Integer ladderQuotationFlag;
    @ApiModelProperty("申请人")
    private String purReqAppliedName;
    @ApiModelProperty("原币含税单价是否可修改标识")
    private Integer enteredTaxIncludedPriceFlag;
    @ApiModelProperty("原币不含税单价是否可修改标识")
    private Integer unitPriceFlag;
    @ApiModelProperty("税率是否可修改标识")
    private Integer taxRateFlag;
    @ApiModelProperty("备注是否可修改标识")
    private Integer remarkFlag;
    @ApiModelProperty("外协bom集合")
    private List<PoItemBom> poItemBomList;
    @ApiModelProperty("是否含税")
    private Integer taxIncludedFlag;
    public BigDecimal originUnitPrice;

    public PoLineDetailDTO() {
    }

    public void validPo() {
        if (Objects.isNull(this.quantity) || Objects.isNull(this.uomId) || Objects.isNull(this.currencyCode) || Objects.isNull(this.taxId) || Objects.isNull(this.enteredTaxIncludedPrice) || Objects.isNull(this.invOrganizationId)) {
            throw new CommonException("error.po.line.not.null.field.exists", new Object[]{this.displayLineNum});
        }
    }

    public void validPo(PrLineRepository prLineRepository) {
        PrLine prLine = (PrLine)prLineRepository.selectByPrimaryKey(this.prLineId);
        if (Objects.nonNull(prLine) && Flag.YES.equals(prLine.getFreightLineFlag())) {
            if (Objects.isNull(this.quantity) || Objects.isNull(this.uomId) || Objects.isNull(this.currencyCode) || Objects.isNull(this.enteredTaxIncludedPrice) || Objects.isNull(this.invOrganizationId)) {
                throw new CommonException("error.po.line.not.null.field.exists", new Object[]{this.displayLineNum});
            }
        } else if (Objects.isNull(this.quantity) || Objects.isNull(this.uomId) || Objects.isNull(this.currencyCode) || Objects.isNull(this.taxId) || Objects.isNull(this.enteredTaxIncludedPrice) || Objects.isNull(this.invOrganizationId)) {
            throw new CommonException("error.po.line.not.null.field.exists", new Object[]{this.displayLineNum});
        }

    }

    public void convertStatus() {
        String headerStatus = this.statusCode;
        this.displayStatusCode = headerStatus;
        if (Objects.equals(Flag.YES, this.closedFlag)) {
            this.displayStatusCode = "CLOSED";
        } else if (Objects.equals(Flag.YES, this.cancelledFlag)) {
            this.displayStatusCode = "CANCELED";
        } else if (Objects.equals(Flag.YES, this.confirmFlag)) {
            this.displayStatusCode = "CONFIRMED";
        } else if (Objects.equals(Flag.YES, this.publishCancelFlag)) {
            this.displayStatusCode = "PUBLISH_CANCEL";
        } else if (Objects.equals(Flag.YES, this.getDeliveryDateRejectFlag())) {
            this.setDisplayStatusCode("DELIVERY_DATE_REJECT");
        } else if (StringUtils.equals("PUBLISHED", headerStatus) && Objects.equals(Flag.YES, this.confirmedFlag)) {
            this.displayStatusCode = "CONFIRMED";
        } else {
            if (StringUtils.equals("PUBLISHED", headerStatus) && Objects.equals(Flag.NO, this.confirmedFlag)) {
                this.displayStatusCode = "PUBLISHED";
            }

        }
    }

    public void transSomeFields(boolean priceShieldFlag) {
        this.initPriceUomConversion();
        if (priceShieldFlag) {
            this.setPriceShieldFlag(Flag.YES);
            this.setUnitPrice((BigDecimal)null);
            this.setEnteredTaxIncludedPrice((BigDecimal)null);
            this.setLineAmount((BigDecimal)null);
            this.setTaxIncludedLineAmount((BigDecimal)null);
        } else {
            this.setPriceShieldFlag(Flag.NO);
        }

    }

    public Integer getModifyPriceFlag() {
        return this.modifyPriceFlag;
    }

    public void setModifyPriceFlag(Integer modifyPriceFlag) {
        this.modifyPriceFlag = modifyPriceFlag;
    }

    public Integer getConfirmFlag() {
        return this.confirmFlag;
    }

    public void setConfirmFlag(Integer confirmFlag) {
        this.confirmFlag = confirmFlag;
    }

    public Integer getLadderQuotationFlag() {
        return this.ladderQuotationFlag;
    }

    public void setLadderQuotationFlag(Integer ladderQuotationFlag) {
        this.ladderQuotationFlag = ladderQuotationFlag;
    }

    public List<PoItemBom> getPoItemBomList() {
        return this.poItemBomList;
    }

    public void setPoItemBomList(List<PoItemBom> poItemBomList) {
        this.poItemBomList = poItemBomList;
    }

    public Integer getUpdatePriceFlag() {
        return this.updatePriceFlag;
    }

    public void setUpdatePriceFlag(Integer updatePriceFlag) {
        this.updatePriceFlag = updatePriceFlag;
    }

    public BigDecimal getExchangeRate() {
        return this.exchangeRate;
    }

    public void setExchangeRate(BigDecimal exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public BigDecimal getDomesticTaxIncludedPrice() {
        return this.domesticTaxIncludedPrice;
    }

    public void setDomesticTaxIncludedPrice(BigDecimal domesticTaxIncludedPrice) {
        this.domesticTaxIncludedPrice = domesticTaxIncludedPrice;
    }

    public BigDecimal getDomesticUnitPrice() {
        return this.domesticUnitPrice;
    }

    public void setDomesticUnitPrice(BigDecimal domesticUnitPrice) {
        this.domesticUnitPrice = domesticUnitPrice;
    }

    public BigDecimal getDomesticTaxIncludedLineAmount() {
        return this.domesticTaxIncludedLineAmount;
    }

    public void setDomesticTaxIncludedLineAmount(BigDecimal domesticTaxIncludedLineAmount) {
        this.domesticTaxIncludedLineAmount = domesticTaxIncludedLineAmount;
    }

    public BigDecimal getDomesticLineAmount() {
        return this.domesticLineAmount;
    }

    public void setDomesticLineAmount(BigDecimal domesticLineAmount) {
        this.domesticLineAmount = domesticLineAmount;
    }

    public Integer getTaxIncludedFlag() {
        return this.taxIncludedFlag;
    }

    public void setTaxIncludedFlag(Integer taxIncludedFlag) {
        this.taxIncludedFlag = taxIncludedFlag;
    }

    public Date getExpectShipDate() {
        return this.expectShipDate;
    }

    public void setExpectShipDate(Date expectShipDate) {
        this.expectShipDate = expectShipDate;
    }

    public Long getItemUomId() {
        return this.itemUomId;
    }

    public void setItemUomId(Long itemUomId) {
        this.itemUomId = itemUomId;
    }

    public Long getLadderPriceLibId() {
        return this.ladderPriceLibId;
    }

    public void setLadderPriceLibId(Long ladderPriceLibId) {
        this.ladderPriceLibId = ladderPriceLibId;
    }

    public String getBenchmarkPriceType() {
        return this.benchmarkPriceType;
    }

    public void setBenchmarkPriceType(String benchmarkPriceType) {
        this.benchmarkPriceType = benchmarkPriceType;
    }

    public static String getFieldDateEquallyFlag() {
        return "dateEquallyFlag";
    }

    public static String getFieldTaxWithoutFreightPrice() {
        return "taxWithoutFreightPrice";
    }

    public Boolean getNewPriceFlag() {
        return this.newPriceFlag;
    }

    public void setNewPriceFlag(Boolean newPriceFlag) {
        this.newPriceFlag = newPriceFlag;
    }

    public String getDisplayPrNumAndDisplayPrLineNum() {
        return this.displayPrNumAndDisplayPrLineNum;
    }

    public void setDisplayPrNumAndDisplayPrLineNum(String displayPrNumAndDisplayPrLineNum) {
        this.displayPrNumAndDisplayPrLineNum = displayPrNumAndDisplayPrLineNum;
    }

    public String getProjectCategoryMeaning() {
        return this.projectCategoryMeaning;
    }

    public void setProjectCategoryMeaning(String projectCategoryMeaning) {
        this.projectCategoryMeaning = projectCategoryMeaning;
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

    public BigDecimal getCanHoldPcQuantity() {
        return this.canHoldPcQuantity;
    }

    public void setCanHoldPcQuantity(BigDecimal canHoldPcQuantity) {
        this.canHoldPcQuantity = canHoldPcQuantity;
    }

    public BigDecimal getCanHoldPrQuantity() {
        return this.canHoldPrQuantity;
    }

    public void setCanHoldPrQuantity(BigDecimal canHoldPrQuantity) {
        this.canHoldPrQuantity = canHoldPrQuantity;
    }

    public BigDecimal getTaxWithoutFreightPrice() {
        return this.taxWithoutFreightPrice;
    }

    public void setTaxWithoutFreightPrice(BigDecimal taxWithoutFreightPrice) {
        this.taxWithoutFreightPrice = taxWithoutFreightPrice;
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

    public Date getDeliverySyncDate() {
        return this.deliverySyncDate;
    }

    public void setDeliverySyncDate(Date deliverySyncDate) {
        this.deliverySyncDate = deliverySyncDate;
    }

    public BigDecimal getJdPrice() {
        return this.jdPrice;
    }

    public void setJdPrice(BigDecimal jdPrice) {
        this.jdPrice = jdPrice;
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

    public Integer getSurfaceTreatFlag() {
        return this.surfaceTreatFlag;
    }

    public void setSurfaceTreatFlag(Integer surfaceTreatFlag) {
        this.surfaceTreatFlag = surfaceTreatFlag;
    }

    public String getPcNum() {
        return this.pcNum;
    }

    public void setPcNum(String pcNum) {
        this.pcNum = pcNum;
    }

    public String getSupplierItemNum() {
        return this.supplierItemNum;
    }

    public void setSupplierItemNum(String supplierItemNum) {
        this.supplierItemNum = supplierItemNum;
    }

    public String getProductionOrderNum() {
        return this.productionOrderNum;
    }

    public void setProductionOrderNum(String productionOrderNum) {
        this.productionOrderNum = productionOrderNum;
    }

    public String getCommonName() {
        return this.commonName;
    }

    public void setCommonName(String commonName) {
        this.commonName = commonName;
    }

    public String getDepartmentName() {
        return this.departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getClearOrganizationName() {
        return this.clearOrganizationName;
    }

    public void setClearOrganizationName(String clearOrganizationName) {
        this.clearOrganizationName = clearOrganizationName;
    }

    public String getCopeOrganizationName() {
        return this.copeOrganizationName;
    }

    public void setCopeOrganizationName(String copeOrganizationName) {
        this.copeOrganizationName = copeOrganizationName;
    }

    public String getSupplierItemDesc() {
        return this.supplierItemDesc;
    }

    public void setSupplierItemDesc(String supplierItemDesc) {
        this.supplierItemDesc = supplierItemDesc;
    }

    public BigDecimal getLastPurchasePrice() {
        return this.lastPurchasePrice;
    }

    public void setLastPurchasePrice(BigDecimal lastPurchasePrice) {
        this.lastPurchasePrice = lastPurchasePrice;
    }

    public Long getDepartmentId() {
        return this.departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public Long getClearOrganizationId() {
        return this.clearOrganizationId;
    }

    public void setClearOrganizationId(Long clearOrganizationId) {
        this.clearOrganizationId = clearOrganizationId;
    }

    public Long getCopeOrganizationId() {
        return this.copeOrganizationId;
    }

    public void setCopeOrganizationId(Long copeOrganizationId) {
        this.copeOrganizationId = copeOrganizationId;
    }

    public String getChartCode() {
        return this.chartCode;
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

    public String getHoldPcNum() {
        return this.holdPcNum;
    }

    public void setHoldPcNum(String holdPcNum) {
        this.holdPcNum = holdPcNum;
    }

    public Long getHoldPcLineNum() {
        return this.holdPcLineNum;
    }

    public void setHoldPcLineNum(Long holdPcLineNum) {
        this.holdPcLineNum = holdPcLineNum;
    }

    public Long getPrLineItemId() {
        return this.prLineItemId;
    }

    public void setPrLineItemId(Long prLineItemId) {
        this.prLineItemId = prLineItemId;
    }

    public Long getPriceTaxId() {
        return this.priceTaxId;
    }

    public void setPriceTaxId(Long priceTaxId) {
        this.priceTaxId = priceTaxId;
    }

    public void removeInforForCopy() {
        this.lineNum = null;
        this.displayLineNum = null;
        this.lineLocationNum = null;
        this.displayLineLocationNum = null;
        this.sourceNumAndLine = null;
        this.prHeaderId = null;
        this.prLineId = null;
        this.displayPrNum = null;
        this.displayPrLineNum = null;
        this.prRequestedBy = null;
        this.prRequestedName = null;
        this.pcNum = null;
    }

    public void initpoLineDetailDTO(PoPriceLibReturnVO poPriceLibReturnVO) {
        if (poPriceLibReturnVO != null) {
            this.uomId = poPriceLibReturnVO.getUomId();
            this.uomCode = poPriceLibReturnVO.getUomCode();
            this.uomName = poPriceLibReturnVO.getUomName();
            this.taxId = poPriceLibReturnVO.getTaxId();
            this.taxCode = poPriceLibReturnVO.getTaxCode();
            this.taxRate = poPriceLibReturnVO.getTaxRate();
            this.currencyCode = poPriceLibReturnVO.getCurrencyCode();
            this.currencyName = poPriceLibReturnVO.getCurrencyName();
            this.unitPrice = poPriceLibReturnVO.getNetPrice();
            this.enteredTaxIncludedPrice = poPriceLibReturnVO.getTaxIncludedPrice();
            this.holdPcHeaderId = poPriceLibReturnVO.getHoldPcHeaderId();
            this.holdPcLineId = poPriceLibReturnVO.getHoldPcLineId();
            this.holdPcNum = poPriceLibReturnVO.getContractNum();
            this.unitPriceBatch = poPriceLibReturnVO.getUnitPriceBatch();
            this.priceLibraryId = poPriceLibReturnVO.getPriceLibId();
            this.unitPriceBatch = poPriceLibReturnVO.getUnitPriceBatch();
            this.priceTaxId = poPriceLibReturnVO.getTaxId();
            this.ladderQuotationFlag = poPriceLibReturnVO.getLadderQuotationFlag();
            if (StringUtils.isNotEmpty(poPriceLibReturnVO.getBenchmarkPriceType())) {
                if ("NET_PRICE".equals(poPriceLibReturnVO.getBenchmarkPriceType())) {
                    this.originUnitPrice = poPriceLibReturnVO.getNetPrice();
                    this.benchmarkPriceType = "NET_PRICE";
                } else {
                    this.originUnitPrice = poPriceLibReturnVO.getTaxIncludedPrice();
                    this.benchmarkPriceType = "TAX_INCLUDED_PRICE";
                }
            } else {
                this.originUnitPrice = poPriceLibReturnVO.getNetPrice();
                this.benchmarkPriceType = null;
            }
        } else {
            this.uomId = null;
            this.uomCode = null;
            this.uomName = null;
            this.taxId = null;
            this.taxCode = null;
            this.taxRate = null;
            this.currencyCode = null;
            this.currencyName = null;
            this.unitPrice = null;
            this.enteredTaxIncludedPrice = null;
            this.holdPcHeaderId = null;
            this.holdPcLineId = null;
            this.holdPcNum = null;
            this.unitPriceBatch = new BigDecimal(1);
            this.priceLibraryId = null;
            this.priceTaxId = null;
            this.originUnitPrice = null;
            this.benchmarkPriceType = null;
            this.ladderQuotationFlag = null;
        }

    }

    public void validPoNewPrice(PrLineRepository prLineRepository) {
        PrLine prLine = (PrLine)prLineRepository.selectByPrimaryKey(this.prLineId);
        if (Objects.nonNull(prLine) && Flag.YES.equals(prLine.getFreightLineFlag())) {
            if (Objects.isNull(this.quantity) || Objects.isNull(this.uomId) || Objects.isNull(this.currencyCode) || Objects.isNull(this.enteredTaxIncludedPrice) || Objects.isNull(this.invOrganizationId)) {
                throw new CommonException("error.po.line.not.null.field.exists", new Object[]{this.displayLineNum});
            }
        } else if (Objects.isNull(this.quantity) || Objects.isNull(this.invOrganizationId)) {
            throw new CommonException("error.po.line.not.null.field.exists", new Object[]{this.displayLineNum});
        }

    }

    public Integer getDeliveryDateEditFlag() {
        return this.deliveryDateEditFlag;
    }

    public void setDeliveryDateEditFlag(Integer deliveryDateEditFlag) {
        this.deliveryDateEditFlag = deliveryDateEditFlag;
    }

    public Integer getQuantityEditFlag() {
        return this.quantityEditFlag;
    }

    public void setQuantityEditFlag(Integer quantityEditFlag) {
        this.quantityEditFlag = quantityEditFlag;
    }

    public Integer getQuantityEnableFlag() {
        return this.quantityEnableFlag;
    }

    public void setQuantityEnableFlag(Integer quantityEnableFlag) {
        this.quantityEnableFlag = quantityEnableFlag;
    }

    public Integer getDeliveryDateEnableFlag() {
        return this.deliveryDateEnableFlag;
    }

    public void setDeliveryDateEnableFlag(Integer deliveryDateEnableFlag) {
        this.deliveryDateEnableFlag = deliveryDateEnableFlag;
    }

    public Class<? extends SecurityToken> associateEntityClass() {
        return PoLineLocation.class;
    }

    public void initPriceUomConversion() {
        if (null != this.unitConversionMolecule && null != this.uomCode && null != this.unitConversionDenominator && null != this.priceUomCode) {
            this.priceUomConversion = OrderUtil.filterZeroAndDecimal(String.valueOf(this.unitConversionMolecule)) + "/" + this.uomCode + "-" + OrderUtil.filterZeroAndDecimal(String.valueOf(this.unitConversionDenominator)) + "/" + this.priceUomCode;
        }

    }

    public BigDecimal getOriginUnitPrice() {
        return this.originUnitPrice = this.originUnitPrice;
    }

    public void setOriginUnitPrice(BigDecimal originUnitPrice) {
        this.originUnitPrice = originUnitPrice == null ? new BigDecimal(2147483647) : originUnitPrice;
    }

    public String getContractNum() {
        return this.contractNum;
    }

    public void setContractNum(String contractNum) {
        this.contractNum = contractNum;
    }

    public String getSourceCodeNum() {
        return this.sourceCodeNum;
    }

    public void setSourceCodeNum(String sourceCodeNum) {
        this.sourceCodeNum = sourceCodeNum;
    }

    public String getSourceNumAndLine() {
        return this.sourceNumAndLine;
    }

    public void setSourceNumAndLine(String sourceNumAndLine) {
        this.sourceNumAndLine = sourceNumAndLine;
    }

    public BigDecimal getOriginalQuantity() {
        return this.originalQuantity;
    }

    public void setOriginalQuantity(BigDecimal originalQuantity) {
        this.originalQuantity = originalQuantity;
    }

    public Long getVersionNum() {
        return this.versionNum;
    }

    public void setVersionNum(Long versionNum) {
        this.versionNum = versionNum;
    }

    public Integer getBeyondQuantity() {
        return this.beyondQuantity;
    }

    public void setBeyondQuantity(Integer beyondQuantity) {
        this.beyondQuantity = beyondQuantity;
    }

    public Long getTenantId() {
        return this.tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public Long getPrTypeId() {
        return this.prTypeId;
    }

    public void setPrTypeId(Long prTypeId) {
        this.prTypeId = prTypeId;
    }

    public String getCurrencyCode() {
        return this.currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getSourceBillTypeCode() {
        return this.sourceBillTypeCode;
    }

    public void setSourceBillTypeCode(String sourceBillTypeCode) {
        this.sourceBillTypeCode = sourceBillTypeCode;
    }

    public Integer getUrgentFlag() {
        return this.urgentFlag;
    }

    public void setUrgentFlag(Integer urgentFlag) {
        this.urgentFlag = urgentFlag;
    }

    public Integer getConfirmedFlag() {
        return this.confirmedFlag;
    }

    public void setConfirmedFlag(Integer confirmedFlag) {
        this.confirmedFlag = confirmedFlag;
    }

    public Long getPriceLibraryId() {
        return this.priceLibraryId;
    }

    public void setPriceLibraryId(Long priceLibraryId) {
        this.priceLibraryId = priceLibraryId;
    }

    public Integer getLadderInquiryFlag() {
        return this.ladderInquiryFlag;
    }

    public void setLadderInquiryFlag(Integer ladderInquiryFlag) {
        this.ladderInquiryFlag = ladderInquiryFlag;
    }

    public static void pageSortReset(PageRequest pageRequest, Integer sortType) {
        if (pageRequest.getSort() != null) {
            Map<String, String> map = new HashMap();
            map.put("displayLineNum", "spl.display_line_num");
            map.put("displayLineLocationNum", "spll.display_line_location_num");
            map.put("deliveryDateRejectFlag", "spll.delivery_date_reject_flag");
            pageRequest.resetOrder("sph", map);
        } else {
            PoLineDetailDTO.SortType sortType1 = PoLineDetailDTO.SortType.valueOf("value" + sortType);
            Assert.isTrue(sortType1 != null, "error.data_invalid");
            pageRequest.setSort(sortType1.getSort());
        }

    }

    public LocalDate getNeedStartDate() {
        return needStartDate;
    }

    public void setNeedStartDate(LocalDate needStartDate) {
        this.needStartDate = needStartDate;
    }

    public Long getPcHeaderId() {
        return this.pcHeaderId;
    }

    public void setPcHeaderId(Long pcHeaderId) {
        this.pcHeaderId = pcHeaderId;
    }

    public String getDisplayStatusCode() {
        return this.displayStatusCode;
    }

    public void setDisplayStatusCode(String displayStatusCode) {
        this.displayStatusCode = displayStatusCode;
    }

    public String getDisplayStatusMeaning() {
        return this.displayStatusMeaning;
    }

    public void setDisplayStatusMeaning(String displayStatusMeaning) {
        this.displayStatusMeaning = displayStatusMeaning;
    }

    public Long getPoHeaderId() {
        return this.poHeaderId;
    }

    public void setPoHeaderId(Long poHeaderId) {
        this.poHeaderId = poHeaderId;
    }

    public Long getSupplierId() {
        return this.supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public Long getPcTypeId() {
        return this.pcTypeId;
    }

    public void setPcTypeId(Long pcTypeId) {
        this.pcTypeId = pcTypeId;
    }

    public Long getCompanyId() {
        return this.companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getUnitId() {
        return this.unitId;
    }

    public void setUnitId(Long unitId) {
        this.unitId = unitId;
    }

    public String getUnitName() {
        return this.unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public BigDecimal getQuantity() {
        return this.quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public String getUomCode() {
        return this.uomCode;
    }

    public void setUomCode(String uomCode) {
        this.uomCode = uomCode;
    }

    public String getUomName() {
        return this.uomName;
    }

    public void setUomName(String uomName) {
        this.uomName = uomName;
    }

    public BigDecimal getUnitPrice() {
        return this.unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getLineAmount() {
        return this.lineAmount;
    }

    public void setLineAmount(BigDecimal lineAmount) {
        this.lineAmount = lineAmount;
    }

    public String getReceiptsStatus() {
        return this.receiptsStatus;
    }

    public void setReceiptsStatus(String receiptsStatus) {
        this.receiptsStatus = receiptsStatus;
    }

    public String getBrand() {
        return this.brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Long getSaveBomItemId() {
        return this.saveBomItemId;
    }

    public void setSaveBomItemId(Long saveBomItemId) {
        this.saveBomItemId = saveBomItemId;
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

    public BigDecimal getTaxRate() {
        return this.taxRate;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    public BigDecimal getUnitPriceBatch() {
        return this.unitPriceBatch;
    }

    public void setUnitPriceBatch(BigDecimal unitPriceBatch) {
        this.unitPriceBatch = unitPriceBatch;
    }

    public Date getNeedByDate() {
        return this.needByDate;
    }

    public void setNeedByDate(Date needByDate) {
        this.needByDate = needByDate;
    }

    public String getInvOrganizationName() {
        return this.invOrganizationName;
    }

    public void setInvOrganizationName(String invOrganizationName) {
        this.invOrganizationName = invOrganizationName;
    }

    public Integer getDateEquallyFlag() {
        return this.dateEquallyFlag;
    }

    public void setDateEquallyFlag(Integer dateEquallyFlag) {
        this.dateEquallyFlag = dateEquallyFlag;
    }

    public Long getLineVersionNumber() {
        return this.lineVersionNumber;
    }

    public void setLineVersionNumber(Long lineVersionNumber) {
        this.lineVersionNumber = lineVersionNumber;
    }

    public Long getLocationVersionNumber() {
        return this.locationVersionNumber;
    }

    public void setLocationVersionNumber(Long locationVersionNumber) {
        this.locationVersionNumber = locationVersionNumber;
    }

    public String getStatusCode() {
        return this.statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public Integer getPriceShieldFlag() {
        return this.priceShieldFlag;
    }

    public void setPriceShieldFlag(Integer priceShieldFlag) {
        this.priceShieldFlag = priceShieldFlag;
    }

    public Long getPoLineId() {
        return this.poLineId;
    }

    public void setPoLineId(Long poLineId) {
        this.poLineId = poLineId;
    }

    public Integer getLineNum() {
        return this.lineNum;
    }

    public void setLineNum(Integer lineNum) {
        this.lineNum = lineNum;
    }

    public String getItemCode() {
        return this.itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getOldItemCode() {
        return this.oldItemCode;
    }

    public void setOldItemCode(String oldItemCode) {
        this.oldItemCode = oldItemCode;
    }

    public String getCategoryName() {
        return this.categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public BigDecimal getRate() {
        return this.rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public Integer getPublishCancelFlag() {
        return this.publishCancelFlag;
    }

    public void setPublishCancelFlag(Integer publishCancelFlag) {
        this.publishCancelFlag = publishCancelFlag;
    }

    public String getPriceUomName() {
        return this.priceUomName;
    }

    public void setPriceUomName(String priceUomName) {
        this.priceUomName = priceUomName;
    }

    public String getPriceUomConversion() {
        return this.priceUomConversion;
    }

    public void setPriceUomConversion(String priceUomConversion) {
        this.priceUomConversion = priceUomConversion;
    }

    public Long getPoLineLocationId() {
        return this.poLineLocationId;
    }

    public void setPoLineLocationId(Long poLineLocationId) {
        this.poLineLocationId = poLineLocationId;
    }

    public String getReceiverAddress() {
        return this.receiverAddress;
    }

    public void setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress;
    }

    public String getShipToThirdPartyName() {
        return this.shipToThirdPartyName;
    }

    public void setShipToThirdPartyName(String shipToThirdPartyName) {
        this.shipToThirdPartyName = shipToThirdPartyName;
    }

    public Long getSupplierCompanyId() {
        return this.supplierCompanyId;
    }

    public void setSupplierCompanyId(Long supplierCompanyId) {
        this.supplierCompanyId = supplierCompanyId;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public BigDecimal getReceiveToleranceQuantity() {
        return this.receiveToleranceQuantity;
    }

    public void setReceiveToleranceQuantity(BigDecimal receiveToleranceQuantity) {
        this.receiveToleranceQuantity = receiveToleranceQuantity;
    }

    public Long getOuId() {
        return this.ouId;
    }

    public void setOuId(Long ouId) {
        this.ouId = ouId;
    }

    public String getLineLocationNum() {
        return this.lineLocationNum;
    }

    public void setLineLocationNum(String lineLocationNum) {
        this.lineLocationNum = lineLocationNum;
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

    public Integer getDeliveryDateRejectFlag() {
        return this.deliveryDateRejectFlag;
    }

    public void setDeliveryDateRejectFlag(Integer deliveryDateRejectFlag) {
        this.deliveryDateRejectFlag = deliveryDateRejectFlag;
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

    public String getOrganizationName() {
        return this.organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
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

    public String getItemName() {
        return this.itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public BigDecimal getEnteredTaxIncludedPrice() {
        return this.enteredTaxIncludedPrice;
    }

    public void setEnteredTaxIncludedPrice(BigDecimal enteredTaxIncludedPrice) {
        this.enteredTaxIncludedPrice = enteredTaxIncludedPrice;
    }

    public BigDecimal getTaxIncludedLineAmount() {
        return this.taxIncludedLineAmount;
    }

    public void setTaxIncludedLineAmount(BigDecimal taxIncludedLineAmount) {
        this.taxIncludedLineAmount = taxIncludedLineAmount;
    }

    public String getCurrencyName() {
        return this.currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public Integer getImmedShippedFlag() {
        return this.immedShippedFlag;
    }

    public void setImmedShippedFlag(Integer immedShippedFlag) {
        this.immedShippedFlag = immedShippedFlag;
    }

    public Integer getFreeFlag() {
        return this.freeFlag;
    }

    public void setFreeFlag(Integer freeFlag) {
        this.freeFlag = freeFlag;
    }

    public Integer getReturnedFlag() {
        return this.returnedFlag;
    }

    public void setReturnedFlag(Integer returnedFlag) {
        this.returnedFlag = returnedFlag;
    }

    public Integer getConsignedFlag() {
        return this.consignedFlag;
    }

    public void setConsignedFlag(Integer consignedFlag) {
        this.consignedFlag = consignedFlag;
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

    public String getPrRequestedName() {
        return this.prRequestedName;
    }

    public void setrRequestedName(String prRequestedName) {
        this.prRequestedName = prRequestedName;
    }

    public BigDecimal getUnitConversionMolecule() {
        return this.unitConversionMolecule;
    }

    public void setUnitConversionMolecule(BigDecimal unitConversionMolecule) {
        this.unitConversionMolecule = unitConversionMolecule;
    }

    public BigDecimal getUnitConversionDenominator() {
        return this.unitConversionDenominator;
    }

    public void setUnitConversionDenominator(BigDecimal unitConversionDenominator) {
        this.unitConversionDenominator = unitConversionDenominator;
    }

    public Date getPromiseDeliveryDate() {
        return this.promiseDeliveryDate;
    }

    public void setPromiseDeliveryDate(Date promiseDeliveryDate) {
        this.promiseDeliveryDate = promiseDeliveryDate;
    }

    public String getFeedback() {
        return this.feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
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

    public Integer getFrozenFlag() {
        return this.frozenFlag;
    }

    public void setFrozenFlag(Integer frozenFlag) {
        this.frozenFlag = frozenFlag;
    }

    public String getPriceUomCode() {
        return this.priceUomCode;
    }

    public void setPriceUomCode(String priceUomCode) {
        this.priceUomCode = priceUomCode;
    }

    public void setPrRequestedName(String prRequestedName) {
        this.prRequestedName = prRequestedName;
    }

    public String getSourcePlatformCode() {
        return this.sourcePlatformCode;
    }

    public void setSourcePlatformCode(String sourcePlatformCode) {
        this.sourcePlatformCode = sourcePlatformCode;
    }

    public Long getLocationId() {
        return this.locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public Long getPurchaseOrgId() {
        return this.purchaseOrgId;
    }

    public void setPurchaseOrgId(Long purchaseOrgId) {
        this.purchaseOrgId = purchaseOrgId;
    }

    public Long getProductId() {
        return this.productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getAgentId() {
        return this.agentId;
    }

    public void setAgentId(Long agentId) {
        this.agentId = agentId;
    }

    public Long getItemId() {
        return this.itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Long getCategoryId() {
        return this.categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getInvOrganizationId() {
        return this.invOrganizationId;
    }

    public void setInvOrganizationId(Long invOrganizationId) {
        this.invOrganizationId = invOrganizationId;
    }

    public Long getPrRequestedBy() {
        return this.prRequestedBy;
    }

    public void setPrRequestedBy(Long prRequestedBy) {
        this.prRequestedBy = prRequestedBy;
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

    public Long getUomId() {
        return this.uomId;
    }

    public void setUomId(Long uomId) {
        this.uomId = uomId;
    }

    public Long getTaxId() {
        return this.taxId;
    }

    public void setTaxId(Long taxId) {
        this.taxId = taxId;
    }

    public Long getInvInventoryId() {
        return this.invInventoryId;
    }

    public void setInvInventoryId(Long invInventoryId) {
        this.invInventoryId = invInventoryId;
    }

    public Long getInvLocationId() {
        return this.invLocationId;
    }

    public void setInvLocationId(Long invLocationId) {
        this.invLocationId = invLocationId;
    }

    public Integer getPoLineLocationDeleteFlag() {
        return this.poLineLocationDeleteFlag;
    }

    public void setPoLineLocationDeleteFlag(Integer poLineLocationDeleteFlag) {
        this.poLineLocationDeleteFlag = poLineLocationDeleteFlag;
    }

    public String getShipToThirdPartyCode() {
        return this.shipToThirdPartyCode;
    }

    public void setShipToThirdPartyCode(String shipToThirdPartyCode) {
        this.shipToThirdPartyCode = shipToThirdPartyCode;
    }

    public Long getCatalogId() {
        return this.catalogId;
    }

    public void setCatalogId(Long catalogId) {
        this.catalogId = catalogId;
    }

    public Long getPrLineId() {
        return this.prLineId;
    }

    public void setPrLineId(Long prLineId) {
        this.prLineId = prLineId;
    }

    public Long getPrHeaderId() {
        return this.prHeaderId;
    }

    public void setPrHeaderId(Long prHeaderId) {
        this.prHeaderId = prHeaderId;
    }

    public String getCategoryCode() {
        return this.categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    public String getChartCodeNew() {
        return this.chartCodeNew;
    }

    public void setChartCodeNew(String chartCodeNew) {
        this.chartCodeNew = chartCodeNew;
    }

    public String getUnitCode() {
        return this.unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public String getClearOrganizationCode() {
        return this.clearOrganizationCode;
    }

    public void setClearOrganizationCode(String clearOrganizationCode) {
        this.clearOrganizationCode = clearOrganizationCode;
    }

    public String getCopeOrganizationCode() {
        return this.copeOrganizationCode;
    }

    public void setCopeOrganizationCode(String copeOrganizationCode) {
        this.copeOrganizationCode = copeOrganizationCode;
    }

    public String getInvOrganizationCode() {
        return this.invOrganizationCode;
    }

    public void setInvOrganizationCode(String invOrganizationCode) {
        this.invOrganizationCode = invOrganizationCode;
    }

    public String getInvInventoryCode() {
        return this.invInventoryCode;
    }

    public void setInvInventoryCode(String invInventoryCode) {
        this.invInventoryCode = invInventoryCode;
    }

    public String getInvLocationCode() {
        return this.invLocationCode;
    }

    public void setInvLocationCode(String invLocationCode) {
        this.invLocationCode = invLocationCode;
    }

    public LocalDate getNeededDate() {
        return this.neededDate;
    }

    public void setNeededDate(LocalDate neededDate) {
        this.neededDate = neededDate;
    }

    public String getSurfaceFlag() {
        return this.surfaceFlag;
    }

    public void setSurfaceFlag(String surfaceFlag) {
        this.surfaceFlag = surfaceFlag;
    }

    public String getTaxCode() {
        return this.taxCode;
    }

    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode;
    }

    public String getDisplayPoNum() {
        return this.displayPoNum;
    }

    public void setDisplayPoNum(String displayPoNum) {
        this.displayPoNum = displayPoNum;
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

    public Date getFeedbackDate() {
        return this.feedbackDate;
    }

    public void setFeedbackDate(Date feedbackDate) {
        this.feedbackDate = feedbackDate;
    }

    public String getPoTypeCode() {
        return this.poTypeCode;
    }

    public void setPoTypeCode(String poTypeCode) {
        this.poTypeCode = poTypeCode;
    }

    public String getPoTypeCodeMeaning() {
        return this.poTypeCodeMeaning;
    }

    public void setPoTypeCodeMeaning(String poTypeCodeMeaning) {
        this.poTypeCodeMeaning = poTypeCodeMeaning;
    }

    public String getCompanyName() {
        return this.companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getOrgName() {
        return this.orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getPurOrganizationName() {
        return this.purOrganizationName;
    }

    public void setPurOrganizationName(String purOrganizationName) {
        this.purOrganizationName = purOrganizationName;
    }

    public String getAgentName() {
        return this.agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public Long getObjectVersionNumber() {
        return this.objectVersionNumber;
    }

    public void setObjectVersionNumber(Long objectVersionNumber) {
        this.objectVersionNumber = objectVersionNumber;
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

    public String getCostName() {
        return this.costName;
    }

    public void setCostName(String costName) {
        this.costName = costName;
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

    public String getAccountSubjectName() {
        return this.accountSubjectName;
    }

    public void setAccountSubjectName(String accountSubjectName) {
        this.accountSubjectName = accountSubjectName;
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

    public Set<String> getAssignTypeRequiredFieldNames() {
        return this.assignTypeRequiredFieldNames;
    }

    public void setAssignTypeRequiredFieldNames(Set<String> assignTypeRequiredFieldNames) {
        this.assignTypeRequiredFieldNames = assignTypeRequiredFieldNames;
    }

    public Integer getPriceContractFlag() {
        return this.priceContractFlag;
    }

    public void setPriceContractFlag(Integer priceContractFlag) {
        this.priceContractFlag = priceContractFlag;
    }

    public String getAttachmentUuid() {
        return this.attachmentUuid;
    }

    public void setAttachmentUuid(String attachmentUuid) {
        this.attachmentUuid = attachmentUuid;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getPurReqAppliedName() {
        return this.purReqAppliedName;
    }

    public void setPurReqAppliedName(String purReqAppliedName) {
        this.purReqAppliedName = purReqAppliedName;
    }

    public Integer getEnteredTaxIncludedPriceFlag() {
        return this.enteredTaxIncludedPriceFlag;
    }

    public void setEnteredTaxIncludedPriceFlag(Integer enteredTaxIncludedPriceFlag) {
        this.enteredTaxIncludedPriceFlag = enteredTaxIncludedPriceFlag;
    }

    public Integer getUnitPriceFlag() {
        return this.unitPriceFlag;
    }

    public void setUnitPriceFlag(Integer unitPriceFlag) {
        this.unitPriceFlag = unitPriceFlag;
    }

    public Integer getTaxRateFlag() {
        return this.taxRateFlag;
    }

    public void setTaxRateFlag(Integer taxRateFlag) {
        this.taxRateFlag = taxRateFlag;
    }

    public Integer getRemarkFlag() {
        return this.remarkFlag;
    }

    public void setRemarkFlag(Integer remarkFlag) {
        this.remarkFlag = remarkFlag;
    }

    public String toString() {
        return "PoLineDetailDTO{tenantId=" + this.tenantId + ", poHeaderId=" + this.poHeaderId + ", supplierId=" + this.supplierId + ", companyId=" + this.companyId + ", ouId=" + this.ouId + ", quantity=" + this.quantity + ", originalQuantity=" + this.originalQuantity + ", uomId=" + this.uomId + ", uomCode='" + this.uomCode + '\'' + ", uomName='" + this.uomName + '\'' + ", unitPrice=" + this.unitPrice + ", enteredTaxIncludedPrice=" + this.enteredTaxIncludedPrice + ", lineAmount=" + this.lineAmount + ", taxIncludedLineAmount=" + this.taxIncludedLineAmount + ", currencyName='" + this.currencyName + '\'' + ", currencyCode='" + this.currencyCode + '\'' + ", priceContractFlag=" + this.priceContractFlag + ", brand='" + this.brand + '\'' + ", specifications='" + this.specifications + '\'' + ", model='" + this.model + '\'' + ", manufacturerName='" + this.manufacturerName + '\'' + ", organizationName='" + this.organizationName + '\'' + ", invOrganizationName='" + this.invOrganizationName + '\'' + ", taxId=" + this.taxId + ", taxRate=" + this.taxRate + ", unitPriceBatch=" + this.unitPriceBatch + ", remark='" + this.remark + '\'' + ", feedback='" + this.feedback + '\'' + ", needByDate=" + this.needByDate + ", promiseDeliveryDate=" + this.promiseDeliveryDate + ", inventoryName='" + this.inventoryName + '\'' + ", locationName='" + this.locationName + '\'' + ", deliveryDateRejectFlag=" + this.deliveryDateRejectFlag + ", dateEquallyFlag=" + this.dateEquallyFlag + ", lineVersionNumber=" + this.lineVersionNumber + ", locationVersionNumber=" + this.locationVersionNumber + ", versionNum=" + this.versionNum + ", priceShieldFlag=" + this.priceShieldFlag + ", poLineId=" + this.poLineId + ", lineNum=" + this.lineNum + ", displayLineNum=" + this.displayLineNum + ", itemCode='" + this.itemCode + '\'' + ", itemName='" + this.itemName + '\'' + ", oldItemCode='" + this.oldItemCode + '\'' + ", categoryName='" + this.categoryName + '\'' + ", rate=" + this.rate + ", consignedFlag=" + this.consignedFlag + ", returnedFlag=" + this.returnedFlag + ", publishCancelFlag=" + this.publishCancelFlag + ", freeFlag=" + this.freeFlag + ", immedShippedFlag=" + this.immedShippedFlag + ", unitConversionMolecule=" + this.unitConversionMolecule + ", unitConversionDenominator=" + this.unitConversionDenominator + ", priceUomName='" + this.priceUomName + '\'' + ", priceUomCode='" + this.priceUomCode + '\'' + ", prTypeId=" + this.prTypeId + ", categoryCode='" + this.categoryCode + '\'' + ", unitCode='" + this.unitCode + '\'' + ", clearOrganizationCode='" + this.clearOrganizationCode + '\'' + ", copeOrganizationCode='" + this.copeOrganizationCode + '\'' + ", invOrganizationCode='" + this.invOrganizationCode + '\'' + ", invInventoryCode='" + this.invInventoryCode + '\'' + ", invLocationCode='" + this.invLocationCode + '\'' + ", taxCode='" + this.taxCode + '\'' + ", neededDate=" + this.neededDate + ", surfaceFlag='" + this.surfaceFlag + '\'' + ", attachmentUuid='" + this.attachmentUuid + '\'' + ", priceUomConversion='" + this.priceUomConversion + '\'' + ", poLineLocationId=" + this.poLineLocationId + ", lineLocationNum='" + this.lineLocationNum + '\'' + ", displayLineLocationNum='" + this.displayLineLocationNum + '\'' + ", displayPrNum='" + this.displayPrNum + '\'' + ", displayPrLineNum='" + this.displayPrLineNum + '\'' + ", prRequestedName='" + this.prRequestedName + '\'' + ", purReqAppliedName='" + this.purReqAppliedName + '\'' + ", shipToThirdPartyCode='" + this.shipToThirdPartyCode + '\'' + ", shipToThirdPartyName='" + this.shipToThirdPartyName + '\'' + ", shipToThirdPartyAddress='" + this.shipToThirdPartyAddress + '\'' + ", shipToThirdPartyContact='" + this.shipToThirdPartyContact + '\'' + ", supplierCompanyId=" + this.supplierCompanyId + ", closedFlag=" + this.closedFlag + ", cancelledFlag=" + this.cancelledFlag + ", frozenFlag=" + this.frozenFlag + ", urgentFlag=" + this.urgentFlag + ", confirmedFlag=" + this.confirmedFlag + ", unitName='" + this.unitName + '\'' + ", unitId=" + this.unitId + ", accountAssignTypeId=" + this.accountAssignTypeId + ", accountAssignTypeCode='" + this.accountAssignTypeCode + '\'' + ", costId=" + this.costId + ", costCode='" + this.costCode + '\'' + ", costName='" + this.costName + '\'' + ", accountSubjectId=" + this.accountSubjectId + ", accountSubjectNum='" + this.accountSubjectNum + '\'' + ", accountSubjectName='" + this.accountSubjectName + '\'' + ", wbsCode='" + this.wbsCode + '\'' + ", wbs='" + this.wbs + '\'' + ", assignTypeRequiredFieldNames=" + this.assignTypeRequiredFieldNames + ", displayPrNumAndDisplayPrLineNum='" + this.displayPrNumAndDisplayPrLineNum + '\'' + ", priceLibraryId=" + this.priceLibraryId + ", sourceNumAndLine='" + this.sourceNumAndLine + '\'' + ", ladderInquiryFlag=" + this.ladderInquiryFlag + ", statusCode='" + this.statusCode + '\'' + ", displayStatusCode='" + this.displayStatusCode + '\'' + ", displayStatusMeaning='" + this.displayStatusMeaning + '\'' + ", sourcePlatformCode='" + this.sourcePlatformCode + '\'' + ", locationId=" + this.locationId + ", receiverAddress='" + this.receiverAddress + '\'' + ", purchaseOrgId=" + this.purchaseOrgId + ", productId=" + this.productId + ", agentId=" + this.agentId + ", itemId=" + this.itemId + ", categoryId=" + this.categoryId + ", invOrganizationId=" + this.invOrganizationId + ", prRequestedBy=" + this.prRequestedBy + ", prLineId=" + this.prLineId + ", prHeaderId=" + this.prHeaderId + ", productNum='" + this.productNum + '\'' + ", productName='" + this.productName + '\'' + ", catalogId=" + this.catalogId + ", catalogName='" + this.catalogName + '\'' + ", invInventoryId=" + this.invInventoryId + ", invLocationId=" + this.invLocationId + ", poLineLocationDeleteFlag=" + this.poLineLocationDeleteFlag + ", beyondQuantity=" + this.beyondQuantity + ", jdPrice=" + this.jdPrice + ", pcHeaderId=" + this.pcHeaderId + ", receiptsStatus='" + this.receiptsStatus + '\'' + ", pcTypeId=" + this.pcTypeId + ", contractNum='" + this.contractNum + '\'' + ", sourceCodeNum='" + this.sourceCodeNum + '\'' + ", modifyPriceFlag=" + this.modifyPriceFlag + ", taxWithoutFreightPrice=" + this.taxWithoutFreightPrice + ", lastPurchasePrice=" + this.lastPurchasePrice + ", departmentId=" + this.departmentId + ", departmentName='" + this.departmentName + '\'' + ", clearOrganizationId=" + this.clearOrganizationId + ", clearOrganizationName='" + this.clearOrganizationName + '\'' + ", copeOrganizationId=" + this.copeOrganizationId + ", copeOrganizationName='" + this.copeOrganizationName + '\'' + ", chartCode='" + this.chartCode + '\'' + ", chartVersion='" + this.chartVersion + '\'' + ", surfaceTreatFlag=" + this.surfaceTreatFlag + ", pcNum='" + this.pcNum + '\'' + ", supplierItemNum='" + this.supplierItemNum + '\'' + ", productionOrderNum='" + this.productionOrderNum + '\'' + ", commonName='" + this.commonName + '\'' + ", deliverySyncStatus='" + this.deliverySyncStatus + '\'' + ", deliverySyncResponseMsg='" + this.deliverySyncResponseMsg + '\'' + ", deliverySyncDate=" + this.deliverySyncDate + ", projectCategory='" + this.projectCategory + '\'' + ", projectCategoryMeaning='" + this.projectCategoryMeaning + '\'' + ", supplierItemDesc='" + this.supplierItemDesc + '\'' + ", sourceBillTypeCode='" + this.sourceBillTypeCode + '\'' + ", holdPcHeaderId=" + this.holdPcHeaderId + ", holdPcLineId=" + this.holdPcLineId + ", holdPcNum='" + this.holdPcNum + '\'' + ", holdPcLineNum=" + this.holdPcLineNum + ", canHoldPcQuantity=" + this.canHoldPcQuantity + ", canHoldPrQuantity=" + this.canHoldPrQuantity + ", prLineItemId=" + this.prLineItemId + ", priceTaxId=" + this.priceTaxId + ", selectSupplierCompanyId=" + this.selectSupplierCompanyId + ", selectSupplierTenantId=" + this.selectSupplierTenantId + ", selectSupplierCode='" + this.selectSupplierCode + '\'' + ", selectSupplierCompanyName='" + this.selectSupplierCompanyName + '\'' + ", originUnitPrice=" + this.originUnitPrice + ", enteredTaxIncludedPriceFlag=" + this.enteredTaxIncludedPriceFlag + ", taxRateFlag=" + this.taxRateFlag + ", unitPriceFlag=" + this.unitPriceFlag + ", remarkFlag=" + this.remarkFlag + '}';
    }

    @FunctionalInterface
    public interface SortTypeCond {
        Sort getSort();
    }

    public static enum SortType {
        value0("我发出的订单", new PoLineDetailDTO.SortTypeCond() {
            public Sort getSort() {
                return SortUtils.getSortInstance(true, Direction.ASC, new String[]{"spll." + FieldNameUtils.camel2Underline("displayLineLocationNum", false)});
            }
        }),
        value1("订单审批", new PoLineDetailDTO.SortTypeCond() {
            public Sort getSort() {
                return SortUtils.getSortInstance(true, Direction.ASC, new String[]{"spll." + FieldNameUtils.camel2Underline("displayLineLocationNum", false)});
            }
        }),
        value2("订单发布", new PoLineDetailDTO.SortTypeCond() {
            public Sort getSort() {
                return SortUtils.getSortInstance(true, Direction.ASC, new String[]{"spl." + FieldNameUtils.camel2Underline("displayLineNum", false)});
            }
        }),
        value3("交期审核", new PoLineDetailDTO.SortTypeCond() {
            public Sort getSort() {
                Map<String, Direction> sortMap = new HashMap(4);
                sortMap.put("spll." + FieldNameUtils.camel2Underline("deliveryDateRejectFlag", false), Direction.DESC);
                sortMap.put(FieldNameUtils.camel2Underline("dateEquallyFlag", false), Direction.ASC);
                sortMap.put("spll." + FieldNameUtils.camel2Underline("displayLineLocationNum", false), Direction.ASC);
                return SortUtils.getSortInstance(true, sortMap);
            }
        }),
        value4("订单确认", new PoLineDetailDTO.SortTypeCond() {
            public Sort getSort() {
                Map<String, Direction> sortMap = new HashMap(4);
                sortMap.put("spll." + FieldNameUtils.camel2Underline("deliveryDateRejectFlag", false), Direction.DESC);
                sortMap.put("spll." + FieldNameUtils.camel2Underline("displayLineLocationNum", false), Direction.ASC);
                return SortUtils.getSortInstance(true, sortMap);
            }
        }),
        value5("我收到的订单", new PoLineDetailDTO.SortTypeCond() {
            public Sort getSort() {
                return SortUtils.getSortInstance(true, Direction.ASC, new String[]{"spl." + FieldNameUtils.camel2Underline("displayLineNum", false)});
            }
        });

        private final String desc;
        private final PoLineDetailDTO.SortTypeCond sortTypeCond;

        private SortType(String desc, PoLineDetailDTO.SortTypeCond sortTypeCond) {
            this.desc = desc;
            this.sortTypeCond = sortTypeCond;
        }

        public String desc() {
            return this.desc;
        }

        public Sort getSort() {
            return this.sortTypeCond.getSort();
        }
    }

    public interface UpdateCheck {
    }
}
