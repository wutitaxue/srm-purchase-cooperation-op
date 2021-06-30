package org.srm.purchasecooperation.cux.order.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import org.srm.common.mybatis.domain.ExpandDomain;

/**
 * @description:物料返回信息
 * @author: bin.zhang
 * @createDate: 2021/4/7 14:12
 */
public class RCWLItemInfoVO extends ExpandDomain {
    @ApiModelProperty(value = "租户id")
    private Long tenantId;
    @ApiModelProperty(value = "订单行id")
    private Long poLineId;
    @ApiModelProperty(value = "来源编码")
    private String sourceCode;
    @ApiModelProperty(value = "物料id")
    private Long itemId;
    @ApiModelProperty(value = "物料编码")
    private String itemCode;
    @ApiModelProperty(value = "物料名称")
    private  String itemName;
    @ApiModelProperty(value = "基本单位")
    private Long primaryUomId;
    @ApiModelProperty(value = "品类id")
    private Long categoryId;
    @ApiModelProperty(value = "物料编号")
    private String itemNumber;
    @ApiModelProperty(value = "查询物料编码")
    private String queryItemCode;
    @ApiModelProperty(value = "电商编码")
    private String productNum;
    @ApiModelProperty(value = "规格")
    private String specifications;
    @ApiModelProperty(value = "型号")
    private String model;
    public Long getPoLineId() {
        return poLineId;
    }

    public void setPoLineId(Long poLineId) {
        this.poLineId = poLineId;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public String getSourceCode() {
        return sourceCode;
    }

    public void setSourceCode(String sourceCode) {
        this.sourceCode = sourceCode;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Long getPrimaryUomId() {
        return primaryUomId;
    }

    public void setPrimaryUomId(Long primaryUomId) {
        this.primaryUomId = primaryUomId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getItemNumber() {
        return itemNumber;
    }

    public void setItemNumber(String itemNumber) {
        this.itemNumber = itemNumber;
    }

    public String getQueryItemCode() {
        return queryItemCode;
    }

    public void setQueryItemCode(String queryItemCode) {
        this.queryItemCode = queryItemCode;
    }

    public String getProductNum() {
        return productNum;
    }

    public void setProductNum(String productNum) {
        this.productNum = productNum;
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

    @Override
    public String toString() {
        return "RCWLItemInfoVO{" +
                "tenantId=" + tenantId +
                ", poLineId=" + poLineId +
                ", sourceCode='" + sourceCode + '\'' +
                ", itemId=" + itemId +
                ", itemCode='" + itemCode + '\'' +
                ", itemName='" + itemName + '\'' +
                ", primaryUomId=" + primaryUomId +
                ", categoryId=" + categoryId +
                ", itemNumber='" + itemNumber + '\'' +
                ", queryItemCode='" + queryItemCode + '\'' +
                ", productNum='" + productNum + '\'' +
                ", specifications='" + specifications + '\'' +
                ", model='" + model + '\'' +
                '}';
    }
}
