package org.srm.purchasecooperation.cux.order.api.dto;

import com.netflix.ribbon.proxy.annotation.TemplateName;
import io.swagger.annotations.ApiModelProperty;
import org.hzero.boot.platform.lov.annotation.LovValue;
import org.srm.purchasecooperation.order.api.dto.PoLineDetailDTO;

import javax.persistence.Transient;

/**
 * @author bin.zhang
 */
public class RCWLPoLineDetailDTO extends PoLineDetailDTO {
    @ApiModelProperty("需求物料描述")
    private String attributeVarchar10;

    @ApiModelProperty("业务用途")
    @LovValue(
            lovCode = "SMDM.RCWL.BUDGET_ACCOUNT1",
            meaningField = "attributeVarchar21Meaning"
    )
    private String attributeVarchar21;
    @ApiModelProperty("业务用途meaning")
    @Transient
    private String attributeVarchar21Meaning;

    public String getAttributeVarchar21Meaning() {
        return attributeVarchar21Meaning;
    }

    public void setAttributeVarchar21Meaning(String attributeVarchar21Meaning) {
        this.attributeVarchar21Meaning = attributeVarchar21Meaning;
    }

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
                ", attributeVarchar21Meaning='" + attributeVarchar21Meaning + '\'' +
                '}';
    }
}
