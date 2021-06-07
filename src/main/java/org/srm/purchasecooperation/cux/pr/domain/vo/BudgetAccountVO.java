package org.srm.purchasecooperation.cux.pr.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import org.hzero.starter.keyencrypt.core.Encrypt;

/**
 * @description:
 * @author: bin.zhang
 * @createDate: 2021/6/7 19:26
 */
public class BudgetAccountVO {
    @ApiModelProperty("预算科目ID")
    private Long budgetAccountId;
    @ApiModelProperty("预算科目编码")
    private String budgetAccountNum;
    @ApiModelProperty("预算科目名称")
    private String budgetAccountName;

    public Long getBudgetAccountId() {
        return budgetAccountId;
    }

    public void setBudgetAccountId(Long budgetAccountId) {
        this.budgetAccountId = budgetAccountId;
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
    public String toString() {
        return "BudgetAccountVO{" +
                "budgetAccountId=" + budgetAccountId +
                ", budgetAccountNum='" + budgetAccountNum + '\'' +
                ", budgetAccountName='" + budgetAccountName + '\'' +
                '}';
    }
}
