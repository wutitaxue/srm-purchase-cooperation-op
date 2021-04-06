package org.srm.purchasecooperation.order.api.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author bin.zhang
 */
public class RCWLPoLineDetailDTO extends PoLineDetailDTO {
    @ApiModelProperty("需求物料描述")
    private String attributeVarchar10;

    @Override
    public String getAttributeVarchar10() {
        return attributeVarchar10;
    }

    @Override
    public void setAttributeVarchar10(String attributeVarchar10) {
        this.attributeVarchar10 = attributeVarchar10;
    }

    @Override
    public String toString() {
        return "RCWLPoLineDetailDTO{" +
                "attributeVarchar10='" + attributeVarchar10 + '\'' +
                '}';
    }
}
