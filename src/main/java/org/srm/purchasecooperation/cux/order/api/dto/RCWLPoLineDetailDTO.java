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

    @ApiModelProperty("业务用途存储字段")
    private String attributeVarchar21;

    @ApiModelProperty("业务用途")
    private String budgetAccountNum;
    @ApiModelProperty("业务用途Meanings")
    @Transient
    private String budgetAccountName;

    @Override
    public String getAttributeVarchar21() {
        return attributeVarchar21;
    }

    @Override
    public void setAttributeVarchar21(String attributeVarchar21) {
        this.attributeVarchar21 = attributeVarchar21;
    }

    public String getBudgetAccountNum() {
        return budgetAccountNum;
    }

    public void setBudgetAccountNum(String budgetAccountNum) {
        this.budgetAccountNum = budgetAccountNum;
    }

    public String getBudgetAccountName() {
        return budgetAccountName;
    }

    public void setBudgetAccountName(String budgetAccountName) {
        this.budgetAccountName = budgetAccountName;
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
                ", budgetAccountNum='" + budgetAccountNum + '\'' +
                ", budgetAccountName='" + budgetAccountName + '\'' +
                '}';
    }
}
