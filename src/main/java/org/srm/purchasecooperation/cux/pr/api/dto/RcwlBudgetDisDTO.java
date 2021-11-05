package org.srm.purchasecooperation.cux.pr.api.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author: longjunquan 21420
 * @Date: 2021/11/3 16:07
 * @Description:
 */
@Data
public class RcwlBudgetDisDTO {
    Long prLineId;
    //预算占用年份
    Integer budgetDisYear;
    //预算占用金额
    BigDecimal budgetDisAmount;
}
