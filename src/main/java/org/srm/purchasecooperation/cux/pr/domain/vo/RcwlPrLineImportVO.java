package org.srm.purchasecooperation.cux.pr.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import org.srm.purchasecooperation.pr.domain.vo.PrLineImportVO;

import java.time.LocalDate;
import java.util.Date;

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
    @ApiModelProperty("需求结束日期")
    private LocalDate neededDate;
    @ApiModelProperty("需求开始日期")
    private Date attributeDate1;

}
