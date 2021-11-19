package org.srm.purchasecooperation.cux.order.api.dto;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @Author: pengxu.zhi@hand-china.com
 * @Date: 2021-10-31 15:51
 */
public class PoToBpmLineDTO implements Serializable {


    @ApiModelProperty(value = "是否取消")
    @JSONField(name = "CANCELLEDFLAG")
    private String cancelledFlag;

    @ApiModelProperty(value = "行号")
    @JSONField(name = "DISPLAYLINENUM")
    private String displayLineNum;

    @ApiModelProperty(value = "物料编码")
    @JSONField(name = "ITEMID")
    private String itemId;

    @ApiModelProperty(value = "物料名称")
    @JSONField(name = "ITEMNAME")
    private String itemName;

    @ApiModelProperty(value = "规格")
    @JSONField(name = "SPECIFICATIONS")
    private String specifications;

    @ApiModelProperty(value = "型号")
    @JSONField(name = "MODEL")
    private String model;

    @ApiModelProperty(value = "数量")
    @JSONField(name = "QUANTITY")
    private String quantity;

    @ApiModelProperty(value = "单位")
    @JSONField(name = "UOMID")
    private String uomId;

    @ApiModelProperty(value = "物料品类")
    @JSONField(name = "CATEGORYNAME")
    private String categoryName;

    @ApiModelProperty(value = "订单日期从")
    @JSONField(name = "ATTRIBUTEDATE1")
    private String attributeDate1;

    @ApiModelProperty(value = "订单日期至")
    @JSONField(name = "NEEDBYDATE")
    private String needByDate;

    @ApiModelProperty(value = "不含税单价")
    @JSONField(name = "UNITPRICE")
    private String unitPrice;

    @ApiModelProperty(value = "税率")
    @JSONField(name = "TAX")
    private String tax;

    @ApiModelProperty(value = "含税单价")
    @JSONField(name = "ENTEREDTAXINCLUDEDPRICE")
    private String enteredTaxIncludedPrice;

    @ApiModelProperty(value = "含税行金额")
    @JSONField(name = "TAXINCLUDELINEAMOUNT")
    private String taxInclueLineAmount;

    @ApiModelProperty(value = "收货组织")
    @JSONField(name = "INVORGANIZATIONID")
    private String invOrganizationId;

    @ApiModelProperty(value = "收货库房")
    @JSONField(name = "INVINVENTORYID")
    private String invInventoryId;

    @ApiModelProperty(value = "成本中心")
    @JSONField(name = "COSTID")
    private String costId;

    @ApiModelProperty(value = "业务用途")
    @JSONField(name = "ATTRIBUTEVARCHAR21")
    private String attributeVarchar21;

    @ApiModelProperty(value = "产品类型")
    @JSONField(name = "WBS")
    private String wbs;

    @ApiModelProperty(value = "采购方行备注")
    @JSONField(name = "REMARK")
    private String remark;


    public String getCancelledFlag() {
        return cancelledFlag;
    }

    public void setCancelledFlag(String cancelledFlag) {
        this.cancelledFlag = cancelledFlag;
    }

    public String getDisplayLineNum() {
        return displayLineNum;
    }

    public void setDisplayLineNum(String displayLineNum) {
        this.displayLineNum = displayLineNum;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getSpecifications() {
        return specifications;
    }

    public void setSpecifications(String specifications) {
        this.specifications = specifications;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getUomId() {
        return uomId;
    }

    public void setUomId(String uomId) {
        this.uomId = uomId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getAttributeDate1() {
        return attributeDate1;
    }

    public void setAttributeDate1(String attributeDate1) {
        this.attributeDate1 = attributeDate1;
    }

    public String getNeedByDate() {
        return needByDate;
    }

    public void setNeedByDate(String needByDate) {
        this.needByDate = needByDate;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    public String getEnteredTaxIncludedPrice() {
        return enteredTaxIncludedPrice;
    }

    public void setEnteredTaxIncludedPrice(String enteredTaxIncludedPrice) {
        this.enteredTaxIncludedPrice = enteredTaxIncludedPrice;
    }

    public String getTaxInclueLineAmount() {
        return taxInclueLineAmount;
    }

    public void setTaxInclueLineAmount(String taxInclueLineAmount) {
        this.taxInclueLineAmount = taxInclueLineAmount;
    }

    public String getInvOrganizationId() {
        return invOrganizationId;
    }

    public void setInvOrganizationId(String invOrganizationId) {
        this.invOrganizationId = invOrganizationId;
    }

    public String getInvInventoryId() {
        return invInventoryId;
    }

    public void setInvInventoryId(String invInventoryId) {
        this.invInventoryId = invInventoryId;
    }

    public String getCostId() {
        return costId;
    }

    public void setCostId(String costId) {
        this.costId = costId;
    }

    public String getAttributeVarchar21() {
        return attributeVarchar21;
    }

    public void setAttributeVarchar21(String attributeVarchar21) {
        this.attributeVarchar21 = attributeVarchar21;
    }

    public String getWbs() {
        return wbs;
    }

    public void setWbs(String wbs) {
        this.wbs = wbs;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
