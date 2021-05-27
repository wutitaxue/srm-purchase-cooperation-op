package org.srm.purchasecooperation.cux.order.api.dto;

import io.swagger.annotations.ApiModelProperty;
import org.srm.purchasecooperation.order.api.dto.PoLineDetailDTO;

/**
 * @author bin.zhang
 */
public class RCWLPoLineDetailDTO extends PoLineDetailDTO {
    @ApiModelProperty("需求物料描述")
    private String attributeVarchar10;

    @ApiModelProperty("业务用途")
    private String attributeVarchar21;

    @Override
    public String getAttributeVarchar21() {
        return attributeVarchar21;
    }

    @Override
    public void setAttributeVarchar21(String attributeVarchar21) {
        this.attributeVarchar21 = attributeVarchar21;
    }

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
                ", attributeVarchar21='" + attributeVarchar21 + '\'' +
                '}';
    }
}
