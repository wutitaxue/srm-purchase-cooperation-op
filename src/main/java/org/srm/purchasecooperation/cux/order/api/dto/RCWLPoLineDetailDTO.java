package org.srm.purchasecooperation.cux.order.api.dto;

import com.netflix.ribbon.proxy.annotation.TemplateName;
import io.swagger.annotations.ApiModelProperty;
import org.srm.purchasecooperation.order.api.dto.PoLineDetailDTO;

import javax.persistence.Transient;

/**
 * @author bin.zhang
 */
public class RCWLPoLineDetailDTO extends PoLineDetailDTO {
    @ApiModelProperty("需求物料描述")
    private String attributeVarchar10;

    @ApiModelProperty("此值实际在21字段 业务用途")
    private String attributeVarchar40;

    @Override
    public String getAttributeVarchar40() {
        return attributeVarchar40;
    }

    @Override
    public void setAttributeVarchar40(String attributeVarchar40) {
        this.attributeVarchar40 = attributeVarchar40;
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
                ", attributeVarchar40='" + attributeVarchar40 + '\'' +
                '}';
    }
}
