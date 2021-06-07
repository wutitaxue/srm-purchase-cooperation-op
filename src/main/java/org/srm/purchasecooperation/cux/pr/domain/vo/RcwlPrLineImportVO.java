package org.srm.purchasecooperation.cux.pr.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import org.srm.purchasecooperation.pr.domain.vo.PrLineImportVO;

/**
 * @description:
 * @author: bin.zhang
 * @createDate: 2021/6/7 14:17
 */
public class RcwlPrLineImportVO extends PrLineImportVO {
    @ApiModelProperty("业务用途编码")
    private String budgetAccountNum;

    public String getBudgetAccountNum() {
        return budgetAccountNum;
    }

    public void setBudgetAccountNum(String budgetAccountNum) {
        this.budgetAccountNum = budgetAccountNum;
    }
}
