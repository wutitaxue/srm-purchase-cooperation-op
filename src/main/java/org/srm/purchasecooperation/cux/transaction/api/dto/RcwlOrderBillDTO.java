package org.srm.purchasecooperation.cux.transaction.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;
import org.srm.purchasecooperation.finance.api.dto.common.ItfBaseBO;

public class RcwlOrderBillDTO extends ItfBaseBO {

    @ApiModelProperty("采购平台单据类型  01（收货单）/02（验收单）")
    @NotBlank
    @JsonFormat(pattern = "FSRMBillType")
    private String fsrmBillType;

    @ApiModelProperty("采购共享系统采购订单单号")
    @JsonFormat(pattern = "FSRMOrderBillNo")
    private String fsrmOrderBillNo;

    @ApiModelProperty("采购共享系统收货单/验收单号")
    @NotBlank
    @JsonFormat(pattern = "FSRMStoreBillNo")
    private String fsrmStoreBillNo;

    @ApiModelProperty("采购共享系统收货单/验收单明细行号")
    @NotBlank
    @JsonFormat(pattern = "FSRMStoreRowNo")
    private String fsrmStoreRowNo;

    @ApiModelProperty("采购组织编码")
    @NotBlank
    @JsonFormat(pattern = "FPurchaseOrgId")
    private String fPurchaseOrgId;

    @ApiModelProperty("供应商编码")
    @NotBlank
    @JsonFormat(pattern = "FSupplierId")
    private String fSupplierId;

    @ApiModelProperty("收货人姓名")
    @JsonFormat(pattern = "FProviderContactName")
    private String fProviderContactName;

    @ApiModelProperty("物料编码")
    @NotBlank
    @JsonFormat(pattern = "FMaterialId")
    private String fMaterialId;

    @ApiModelProperty("物料名称")
    @NotBlank
    @JsonFormat(pattern = "FMaterialName")
    private String fMaterialName;

    @ApiModelProperty("物料分组编码")
    @NotBlank
    @JsonFormat(pattern = "FMaterialGroup")
    private String fMaterialGroup;

    @ApiModelProperty("规格型号")
    @JsonFormat(pattern = "FModel")
    private String fModel;

    @ApiModelProperty("物料是否需要新增")
    @NotBlank
    @JsonFormat(pattern = "FIsNew")
    private Boolean fIsNew;

    @ApiModelProperty("结算币别编码")
    @NotBlank
    @JsonFormat(pattern = "FSettleCurrId")
    private String fSettleCurrId;

    @ApiModelProperty("汇率")
    @NotBlank
    @JsonFormat(pattern = "FExchangeRate")
    private Double fExchangeRate;

    @ApiModelProperty("采购单位")
    @NotBlank
    @JsonFormat(pattern = "FUnitId")
    private String fUnitId;

    @ApiModelProperty("采购数量")
    @NotBlank
    @JsonFormat(pattern = "FQty")
    private Double fQty;

    @ApiModelProperty("交货日期")
    @NotBlank
    @JsonFormat(pattern = "FDeliveryDate")
    private String fDeliveryDate;

    @ApiModelProperty("不含税单价")
    @NotBlank
    @JsonFormat(pattern = "FPrice")
    private Double fPrice;

    @ApiModelProperty("不含税金额")
    @NotBlank
    @JsonFormat(pattern = "FAmount")
    private Double fAmount;

    @ApiModelProperty("含税单价")
    @NotBlank
    @JsonFormat(pattern = "FTaxPrice")
    private Double fTaxPrice;

    @ApiModelProperty("价税合计")
    @NotBlank
    @JsonFormat(pattern = "FAllAmount")
    private Double fallAmount;

    @ApiModelProperty("税率")
    @NotBlank
    @JsonFormat(pattern = "FTaxRate")
    private Double fTaxRate;

    @ApiModelProperty("税额")
    @NotBlank
    @JsonFormat(pattern = "FTaxAmount")
    private Double fTaxAmount;

    @ApiModelProperty("备注")
    @JsonFormat(pattern = "FNote")
    private String fNote;

    @ApiModelProperty("仓库")
    @NotBlank
    @JsonFormat(pattern = "FStockId")
    private String fStockId;

    @ApiModelProperty("成本中心编码")
    @NotBlank
    @JsonFormat(pattern = "FCostCenterNo")
    private String fCostCenterNo;

    public String getFsrmBillType() {
        return fsrmBillType;
    }

    public void setFsrmBillType(String fsrmBillType) {
        this.fsrmBillType = fsrmBillType;
    }

    public String getFsrmOrderBillNo() {
        return fsrmOrderBillNo;
    }

    public void setFsrmOrderBillNo(String fsrmOrderBillNo) {
        this.fsrmOrderBillNo = fsrmOrderBillNo;
    }

    public String getFsrmStoreBillNo() {
        return fsrmStoreBillNo;
    }

    public void setFsrmStoreBillNo(String fsrmStoreBillNo) {
        this.fsrmStoreBillNo = fsrmStoreBillNo;
    }

    public String getFsrmStoreRowNo() {
        return fsrmStoreRowNo;
    }

    public void setFsrmStoreRowNo(String fsrmStoreRowNo) {
        this.fsrmStoreRowNo = fsrmStoreRowNo;
    }

    public String getfPurchaseOrgId() {
        return fPurchaseOrgId;
    }

    public void setfPurchaseOrgId(String fPurchaseOrgId) {
        this.fPurchaseOrgId = fPurchaseOrgId;
    }

    public String getfSupplierId() {
        return fSupplierId;
    }

    public void setfSupplierId(String fSupplierId) {
        this.fSupplierId = fSupplierId;
    }

    public String getfProviderContactName() {
        return fProviderContactName;
    }

    public void setfProviderContactName(String fProviderContactName) {
        this.fProviderContactName = fProviderContactName;
    }

    public String getfMaterialId() {
        return fMaterialId;
    }

    public void setfMaterialId(String fMaterialId) {
        this.fMaterialId = fMaterialId;
    }

    public String getfMaterialName() {
        return fMaterialName;
    }

    public void setfMaterialName(String fMaterialName) {
        this.fMaterialName = fMaterialName;
    }

    public String getfMaterialGroup() {
        return fMaterialGroup;
    }

    public void setfMaterialGroup(String fMaterialGroup) {
        this.fMaterialGroup = fMaterialGroup;
    }

    public String getfModel() {
        return fModel;
    }

    public void setfModel(String fModel) {
        this.fModel = fModel;
    }

    public Boolean getfIsNew() {
        return fIsNew;
    }

    public void setfIsNew(Boolean fIsNew) {
        this.fIsNew = fIsNew;
    }

    public String getfSettleCurrId() {
        return fSettleCurrId;
    }

    public void setfSettleCurrId(String fSettleCurrId) {
        this.fSettleCurrId = fSettleCurrId;
    }

    public Double getfExchangeRate() {
        return fExchangeRate;
    }

    public void setfExchangeRate(Double fExchangeRate) {
        this.fExchangeRate = fExchangeRate;
    }

    public String getfUnitId() {
        return fUnitId;
    }

    public void setfUnitId(String fUnitId) {
        this.fUnitId = fUnitId;
    }

    public Double getfQty() {
        return fQty;
    }

    public void setfQty(Double fQty) {
        this.fQty = fQty;
    }

    public String getfDeliveryDate() {
        return fDeliveryDate;
    }

    public void setfDeliveryDate(String fDeliveryDate) {
        this.fDeliveryDate = fDeliveryDate;
    }

    public Double getfPrice() {
        return fPrice;
    }

    public void setfPrice(Double fPrice) {
        this.fPrice = fPrice;
    }

    public Double getfAmount() {
        return fAmount;
    }

    public void setfAmount(Double fAmount) {
        this.fAmount = fAmount;
    }

    public Double getfTaxPrice() {
        return fTaxPrice;
    }

    public void setfTaxPrice(Double fTaxPrice) {
        this.fTaxPrice = fTaxPrice;
    }

    public Double getFallAmount() {
        return fallAmount;
    }

    public void setFallAmount(Double fallAmount) {
        this.fallAmount = fallAmount;
    }

    public Double getfTaxRate() {
        return fTaxRate;
    }

    public void setfTaxRate(Double fTaxRate) {
        this.fTaxRate = fTaxRate;
    }

    public Double getfTaxAmount() {
        return fTaxAmount;
    }

    public void setfTaxAmount(Double fTaxAmount) {
        this.fTaxAmount = fTaxAmount;
    }

    public String getfNote() {
        return fNote;
    }

    public void setfNote(String fNote) {
        this.fNote = fNote;
    }

    public String getfStockId() {
        return fStockId;
    }

    public void setfStockId(String fStockId) {
        this.fStockId = fStockId;
    }

    public String getfCostCenterNo() {
        return fCostCenterNo;
    }

    public void setfCostCenterNo(String fCostCenterNo) {
        this.fCostCenterNo = fCostCenterNo;
    }
}
