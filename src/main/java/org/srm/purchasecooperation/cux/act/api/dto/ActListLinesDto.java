package org.srm.purchasecooperation.cux.act.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import org.hzero.boot.platform.lov.annotation.LovValue;
import org.srm.purchasecooperation.cux.act.infra.utils.rcwlActConstant;

import javax.validation.constraints.NotBlank;

/**
 * @author lu.cheng01@hand-china.com
 * @description 验收单bpm接口查询行信息
 * @date 2021/4/6 9:50
 * @version:1.0
 */
@JsonInclude
public class ActListLinesDto {
    /*订单号*/
    @JsonProperty("POHEADERNUM")
    private String poHeaderNum;
    /*订单行号*/
    @JsonProperty("POLINENUM")
    private String poLineNum;
    /*物料名称*/
    @JsonProperty("ITEMNAME")
    private String itemName;
    /*总数量*/
    @JsonProperty("QUANTITY")
    private String quantity;
    /*已验收数量*/
    @JsonProperty("ACCEPTEDQUANTITY")
    private String acceptedQuantity;
    /*未验收数量*/
    @JsonProperty("CANACCEPTQUANTITY")
    private String canAcceptQuantity;
    /*本次验收数量*/
    @JsonProperty("ACCEPTQUANTITY")
    private String acceptQuantity;


    /*验收意见*/
    @JsonProperty("LINEACCEPTDESCRIPTION")
    private String lineAcceptDescription;
    /*物料品类*/
    @JsonProperty("ITEMCATEGORYNAME")
    private String itemCategoryName;
    /*单位*/
    @JsonProperty("UOMNAME")
    private String uomName;
    /*单价*/
    @JsonProperty("POUNITPRICE")
    private String poUnitPrice;
    /*金额*/
    @JsonProperty("AMOUNT")
    private String amount;
    /*需求到货日期*/
    @JsonProperty("NEEDEDDATE")
    private String neededDate;
    /*实际到货日期*/
    @JsonProperty("DELIVERDATE")
    private String deliverDate;
    /*业务事项*/
    @JsonProperty("BUDGETACCOUNTID")
    private String budgetAccountId;
    /*成本中心*/
    @JsonProperty("COSTID")
    private String costId;

    public String getPoHeaderNum() {
        return poHeaderNum;
    }

    public void setPoHeaderNum(String poHeaderNum) {
        this.poHeaderNum = poHeaderNum;
    }

    public String getPoLineNum() {
        return poLineNum;
    }

    public void setPoLineNum(String poLineNum) {
        this.poLineNum = poLineNum;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getAcceptedQuantity() {
        return acceptedQuantity;
    }

    public void setAcceptedQuantity(String acceptedQuantity) {
        this.acceptedQuantity = acceptedQuantity;
    }

    public String getCanAcceptQuantity() {
        return canAcceptQuantity;
    }

    public void setCanAcceptQuantity(String canAcceptQuantity) {
        this.canAcceptQuantity = canAcceptQuantity;
    }

    public String getAcceptQuantity() {
        return acceptQuantity;
    }

    public void setAcceptQuantity(String acceptQuantity) {
        this.acceptQuantity = acceptQuantity;
    }

    public String getLineAcceptDescription() {
        return lineAcceptDescription;
    }

    public void setLineAcceptDescription(String lineAcceptDescription) {
        this.lineAcceptDescription = lineAcceptDescription;
    }

    public String getItemCategoryName() {
        return itemCategoryName;
    }

    public void setItemCategoryName(String itemCategoryName) {
        this.itemCategoryName = itemCategoryName;
    }

    public String getUomName() {
        return uomName;
    }

    public void setUomName(String uomName) {
        this.uomName = uomName;
    }

    public String getPoUnitPrice() {
        return poUnitPrice;
    }

    public void setPoUnitPrice(String poUnitPrice) {
        this.poUnitPrice = poUnitPrice;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getNeededDate() {
        return neededDate;
    }

    public void setNeededDate(String neededDate) {
        this.neededDate = neededDate;
    }

    public String getDeliverDate() {
        return deliverDate;
    }

    public void setDeliverDate(String deliverDate) {
        this.deliverDate = deliverDate;
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

//    public String getAcceptOpinionCode() {
//        return acceptOpinionCode;
//    }
//
//    public void setAcceptOpinionCode(String acceptOpinionCode) {
//        this.acceptOpinionCode = acceptOpinionCode;
//    }
}
