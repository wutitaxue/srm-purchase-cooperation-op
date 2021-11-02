package org.srm.purchasecooperation.cux.order.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import org.hzero.core.base.BaseConstants;
import org.springframework.format.annotation.DateTimeFormat;
import org.srm.purchasecooperation.order.api.dto.PoLineDetailDTO;

import javax.persistence.Transient;
import java.util.Date;

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
    @ApiModelProperty("税率描述")
    @Transient
    private String taxDescription;
    @ApiModelProperty("库存组织名称")
    @Transient
    private String invOrganizationName;

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

    public String getTaxDescription() {
        return taxDescription;
    }

    public void setTaxDescription(String taxDescription) {
        this.taxDescription = taxDescription;
    }

    @Override
    public String getInvOrganizationName() {
        return invOrganizationName;
    }

    @Override
    public void setInvOrganizationName(String invOrganizationName) {
        this.invOrganizationName = invOrganizationName;
    }

    @Override
    public Date getAttributeDate1() {
        return attributeDate1;
    }

    @Override
    public void setAttributeDate1(Date attributeDate1) {
        this.attributeDate1 = attributeDate1;
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
