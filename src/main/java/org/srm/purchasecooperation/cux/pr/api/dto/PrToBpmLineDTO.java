package org.srm.purchasecooperation.cux.pr.api.dto;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * @description:
 * @author:yuanping.zhang
 * @createTime:2021/4/19 20:54
 * @version:1.0
 */
public class PrToBpmLineDTO implements Serializable {
    @JSONField(name = "DISPLAYLINENUM")
    private String displayLineNum;
    @JSONField(name = "ITEMNAME")
    private String itemName;
    @JSONField(name = "CATEGORYID")
    private String categoryName;
    @JSONField(name = "UOMID")
    private String uomName;
    @JSONField(name = "NEEDEDDATE")
    private String neededDate;
    @JSONField(name = "QUANTITY")
    private String quantity;
    @JSONField(name = "TAXINCLUDEDUNITPRICE")
    private String taxIncludedUnitPrice;
    @JSONField(name = "TAXINCLUDEDLINEAMOUNT")
    private String taxIncludedLineAmount;
    @JSONField(name = "BUDGETACCOUNTID")
    private String budgetAccountId;
    @JSONField(name = "COSTID")
    private String costId;
    @JSONField(name = "WBSCODE")
    private String wbsCode;
    @JSONField(name = "REMARK")
    private String remark;

    public String getDisplayLineNum() {
        return displayLineNum;
    }

    public void setDisplayLineNum(String displayLineNum) {
        this.displayLineNum = displayLineNum;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getUomName() {
        return uomName;
    }

    public void setUomName(String uomName) {
        this.uomName = uomName;
    }

    public String getNeededDate() {
        return neededDate;
    }

    public void setNeededDate(String neededDate) {
        this.neededDate = neededDate;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getTaxIncludedUnitPrice() {
        return taxIncludedUnitPrice;
    }

    public void setTaxIncludedUnitPrice(String taxIncludedUnitPrice) {
        this.taxIncludedUnitPrice = taxIncludedUnitPrice;
    }

    public String getTaxIncludedLineAmount() {
        return taxIncludedLineAmount;
    }

    public void setTaxIncludedLineAmount(String taxIncludedLineAmount) {
        this.taxIncludedLineAmount = taxIncludedLineAmount;
    }

    public String getBudgetAccountId() {
        return budgetAccountId;
    }

    public void setBudgetAccountId(String budgetAccountId) {
        this.budgetAccountId = budgetAccountId;
    }

    public String getCostId() {
        return costId;
    }

    public void setCostId(String costId) {
        this.costId = costId;
    }

    public String getWbsCode() {
        return wbsCode;
    }

    public void setWbsCode(String wbsCode) {
        this.wbsCode = wbsCode;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
