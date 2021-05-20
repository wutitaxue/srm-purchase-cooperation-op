package org.srm.purchasecooperation.cux.transaction.api.dto;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

public class RcwlOrderBillDTO {

    @ApiModelProperty("采购平台单据类型  01（收货单）/02（验收单）")
    @NotBlank
    @JSONField(name="FSRMBillType")
    private String fsrmBillType;

    @ApiModelProperty("采购共享系统采购订单单号")
    @JSONField(name="FSRMOrderBillNo")
    private String fsrmOrderBillNo;

    @ApiModelProperty("采购共享系统收货单/验收单号")
    @NotBlank
    @JSONField(name="FSRMStoreBillNo")
    private String fsrmStoreBillNo;

    @ApiModelProperty("采购共享系统收货单/验收单明细行号")
    @NotBlank
    @JSONField(name="FSRMStoreRowNo")
    private String fsrmStoreRowNo;

    @ApiModelProperty("采购组织编码")
    @NotBlank
    @JSONField(name="FPurchaseOrgId")
    private String fPurchaseOrgId;

    @ApiModelProperty("供应商编码")
    @NotBlank
    @JSONField(name="FSupplierId")
    private String fSupplierId;

    @ApiModelProperty("收货人姓名")
    @JSONField(name="FProviderContactName")
    private String fProviderContactName;

    @ApiModelProperty("物料编码")
    @NotBlank
    @JSONField(name="FMaterialId")
    private String fMaterialId;

    @ApiModelProperty("物料名称")
    @NotBlank
    @JSONField(name="FMaterialName")
    private String fMaterialName;

    @ApiModelProperty("物料分组编码")
    @NotBlank
    @JSONField(name="FMaterialGroup")
    private String fMaterialGroup;

    @ApiModelProperty("规格型号")
    @JSONField(name="FModel")
    private String fModel;

    @ApiModelProperty("物料是否需要新增int")
    @NotBlank
    @JSONField(name="FIsNewInt")
    private String fIsNewInt;

    @ApiModelProperty("物料是否需要新增")
    @NotBlank
    @JSONField(name="FIsNew")
    private Boolean fIsNew;

    @ApiModelProperty("结算币别编码")
    @NotBlank
    @JSONField(name="FSettleCurrId")
    private String fSettleCurrId;

    @ApiModelProperty("采购单位")
    @NotBlank
    @JSONField(name="FUnitId")
    private String fUnitId;

    @ApiModelProperty("采购数量")
    @NotBlank
    @JSONField(name="FQty")
    private Double fQty;

    @ApiModelProperty("交货日期")
    @NotBlank
    @JSONField(name="FDeliveryDate")
    private String fDeliveryDate;

    @ApiModelProperty("不含税单价")
    @NotBlank
    @JSONField(name="FPrice")
    private Double fPrice;

    @ApiModelProperty("不含税金额")
    @NotBlank
    @JSONField(name="FAmount")
    private Double fAmount;

    @ApiModelProperty("含税单价")
    @NotBlank
    @JSONField(name="FTaxPrice")
    private Double fTaxPrice;

    @ApiModelProperty("价税合计")
    @NotBlank
    @JSONField(name="FAllAmount")
    private Double fallAmount;

    @ApiModelProperty("税率")
    @NotBlank
    @JSONField(name="FTaxRate")
    private Double fTaxRate;

    @ApiModelProperty("税额")
    @NotBlank
    @JSONField(name="FTaxAmount")
    private Double fTaxAmount;

    @ApiModelProperty("备注")
    @JSONField(name="FNote")
    private String fNote;

    @ApiModelProperty("仓库")
    @NotBlank
    @JSONField(name="FStockId")
    private String fStockId;

    @ApiModelProperty("成本中心编码")
    @NotBlank
    @JSONField(name="FCostCenterNo")
    private String fCostCenterNo;

    @ApiModelProperty("是否专票")
    @NotBlank
    @JSONField(name="FIsVatinvoice")
    private String fIsVatinvoice;


    public String getfIsNewInt() {
        return fIsNewInt;
    }

    public void setfIsNewInt(String fIsNewInt) {
        this.fIsNewInt = fIsNewInt;
    }

    @JSONField(name="FIsVatinvoice")
    public String getfIsVatinvoice() {
        return fIsVatinvoice;
    }

    @JSONField(name="FIsVatinvoice")
    public void setfIsVatinvoice(String fIsVatinvoice) {
        this.fIsVatinvoice = fIsVatinvoice;
    }

    @JSONField(name="FSRMBillType")
    public String getFsrmBillType() {
        return fsrmBillType;
    }

    @JSONField(name="FSRMBillType")
    public void setFsrmBillType(String fsrmBillType) {
        this.fsrmBillType = fsrmBillType;
    }

    @JSONField(name="FSRMOrderBillNo")
    public String getFsrmOrderBillNo() {
        return fsrmOrderBillNo;
    }

    @JSONField(name="FSRMOrderBillNo")
    public void setFsrmOrderBillNo(String fsrmOrderBillNo) {
        this.fsrmOrderBillNo = fsrmOrderBillNo;
    }

    @JSONField(name="FSRMStoreBillNo")
    public String getFsrmStoreBillNo() {
        return fsrmStoreBillNo;
    }

    @JSONField(name="FSRMStoreBillNo")
    public void setFsrmStoreBillNo(String fsrmStoreBillNo) {
        this.fsrmStoreBillNo = fsrmStoreBillNo;
    }

    @JSONField(name="FSRMStoreRowNo")
    public String getFsrmStoreRowNo() {
        return fsrmStoreRowNo;
    }

    @JSONField(name="FSRMStoreRowNo")
    public void setFsrmStoreRowNo(String fsrmStoreRowNo) {
        this.fsrmStoreRowNo = fsrmStoreRowNo;
    }

    @JSONField(name="FPurchaseOrgId")
    public String getfPurchaseOrgId() {
        return fPurchaseOrgId;
    }

    @JSONField(name="FPurchaseOrgId")
    public void setfPurchaseOrgId(String fPurchaseOrgId) {
        this.fPurchaseOrgId = fPurchaseOrgId;
    }

    @JSONField(name="FSupplierId")
    public String getfSupplierId() {
        return fSupplierId;
    }

    @JSONField(name="FSupplierId")
    public void setfSupplierId(String fSupplierId) {
        this.fSupplierId = fSupplierId;
    }

    @JSONField(name="FProviderContactName")
    public String getfProviderContactName() {
        return fProviderContactName;
    }

    @JSONField(name="FProviderContactName")
    public void setfProviderContactName(String fProviderContactName) {
        this.fProviderContactName = fProviderContactName;
    }

    @JSONField(name="FMaterialId")
    public String getfMaterialId() {
        return fMaterialId;
    }

    @JSONField(name="FMaterialId")
    public void setfMaterialId(String fMaterialId) {
        this.fMaterialId = fMaterialId;
    }

    @JSONField(name="FMaterialName")
    public String getfMaterialName() {
        return fMaterialName;
    }

    @JSONField(name="FMaterialName")
    public void setfMaterialName(String fMaterialName) {
        this.fMaterialName = fMaterialName;
    }

    @JSONField(name="FMaterialGroup")
    public String getfMaterialGroup() {
        return fMaterialGroup;
    }

    @JSONField(name="FMaterialGroup")
    public void setfMaterialGroup(String fMaterialGroup) {
        this.fMaterialGroup = fMaterialGroup;
    }

    @JSONField(name="FModel")
    public String getfModel() {
        return fModel;
    }

    @JSONField(name="FModel")
    public void setfModel(String fModel) {
        this.fModel = fModel;
    }

    @JSONField(name="FIsNew")
    public Boolean getfIsNew() {
        return fIsNew;
    }

    @JSONField(name="FIsNew")
    public void setfIsNew(Boolean fIsNew) {
        this.fIsNew = fIsNew;
    }

    @JSONField(name="FSettleCurrId")
    public String getfSettleCurrId() {
        return fSettleCurrId;
    }

    @JSONField(name="FSettleCurrId")
    public void setfSettleCurrId(String fSettleCurrId) {
        this.fSettleCurrId = fSettleCurrId;
    }

    @JSONField(name="FUnitId")
    public String getfUnitId() {
        return fUnitId;
    }

    @JSONField(name="FUnitId")
    public void setfUnitId(String fUnitId) {
        this.fUnitId = fUnitId;
    }

    @JSONField(name="FQty")
    public Double getfQty() {
        return fQty;
    }

    @JSONField(name="FQty")
    public void setfQty(Double fQty) {
        this.fQty = fQty;
    }

    @JSONField(name="FDeliveryDate")
    public String getfDeliveryDate() {
        return fDeliveryDate;
    }

    @JSONField(name="FDeliveryDate")
    public void setfDeliveryDate(String fDeliveryDate) {
        this.fDeliveryDate = fDeliveryDate;
    }

    @JSONField(name="FPrice")
    public Double getfPrice() {
        return fPrice;
    }

    @JSONField(name="FPrice")
    public void setfPrice(Double fPrice) {
        this.fPrice = fPrice;
    }

    @JSONField(name="FAmount")
    public Double getfAmount() {
        return fAmount;
    }

    @JSONField(name="FAmount")
    public void setfAmount(Double fAmount) {
        this.fAmount = fAmount;
    }

    @JSONField(name="FTaxPrice")
    public Double getfTaxPrice() {
        return fTaxPrice;
    }

    @JSONField(name="FTaxPrice")
    public void setfTaxPrice(Double fTaxPrice) {
        this.fTaxPrice = fTaxPrice;
    }

    @JSONField(name="FAllAmount")
    public Double getFallAmount() {
        return fallAmount;
    }

    @JSONField(name="FAllAmount")
    public void setFallAmount(Double fallAmount) {
        this.fallAmount = fallAmount;
    }

    @JSONField(name="FTaxRate")
    public Double getfTaxRate() {
        return fTaxRate;
    }

    @JSONField(name="FTaxRate")
    public void setfTaxRate(Double fTaxRate) {
        this.fTaxRate = fTaxRate;
    }

    @JSONField(name="FTaxAmount")
    public Double getfTaxAmount() {
        return fTaxAmount;
    }

    @JSONField(name="FTaxAmount")
    public void setfTaxAmount(Double fTaxAmount) {
        this.fTaxAmount = fTaxAmount;
    }

    @JSONField(name="FNote")
    public String getfNote() {
        return fNote;
    }

    @JSONField(name="FNote")
    public void setfNote(String fNote) {
        this.fNote = fNote;
    }

    @JSONField(name="FStockId")
    public String getfStockId() {
        return fStockId;
    }

    @JSONField(name="FStockId")
    public void setfStockId(String fStockId) {
        this.fStockId = fStockId;
    }

    @JSONField(name="FCostCenterNo")
    public String getfCostCenterNo() {
        return fCostCenterNo;
    }

    @JSONField(name="FCostCenterNo")
    public void setfCostCenterNo(String fCostCenterNo) {
        this.fCostCenterNo = fCostCenterNo;
    }
}
