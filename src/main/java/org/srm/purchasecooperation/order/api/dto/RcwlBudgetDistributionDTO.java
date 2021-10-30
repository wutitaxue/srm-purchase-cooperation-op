package org.srm.purchasecooperation.order.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.srm.purchasecooperation.cux.pr.infra.constant.Constants;
import org.srm.purchasecooperation.order.domain.entity.RcwlBudgetDistribution;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author @author pengxu.zhi@hand-china.com 2021-10-29 16:56:18
 * @description 跨年预算分摊DTO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RcwlBudgetDistributionDTO extends RcwlBudgetDistribution {

    @ApiModelProperty(value = "物料编码")
    private String itemCode;
    @ApiModelProperty(value = "物料名称")
    private String itemName;
    @ApiModelProperty(value = "申请行总金额")
    private BigDecimal lineAmount;
    @JsonFormat(pattern = Constants.Pattern.DATE)
    @ApiModelProperty(value = "需求开始日期")
    private LocalDate attributeDate1;
    @ApiModelProperty(value = "需求结束日期")
    private LocalDate needByDate;

    @ApiModelProperty(value = "预算占用金额（系统计算值）")
    private BigDecimal budgetDisAmountCal;
    @ApiModelProperty(value = "需求开始日期所在年份")
    private Long attributeDate1Year;
    @ApiModelProperty(value = "需求开始日期所在月份")
    private Long attributeDate1Month;
    @ApiModelProperty(value = "需求结束日期所在年份")
    private Long needByDateYear;
    @ApiModelProperty(value = "需求结束日期所在月份")
    private Long needByDateMonth;
}
