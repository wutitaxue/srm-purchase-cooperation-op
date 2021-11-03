package org.srm.purchasecooperation.cux.pr.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.srm.purchasecooperation.cux.pr.infra.constant.Constants;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * @author jie.wang05@hand-china.com 2021/10/28 09:54
 * @description 跨年预算分摊DTO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RcwlBudgetDistributionDTO {
    @ApiModelProperty("表ID，主键")
    private Long budgetLineId;
    @ApiModelProperty(value = "订单头id,sodr_po_header.po_header_id")
    private Long poHeaderId;
    @ApiModelProperty(value = "订单行id,sodr_po_line,po_line_id")
    private Long poLineId;
    @ApiModelProperty(value = "申请头id,sprm_pr_header.pr_header_id")
    private Long prHeaderId;
    @ApiModelProperty(value = "申请行id,sprm_pr_line.pr_line_id")
    private Long prLineId;
    @ApiModelProperty(value = "行号")
    private Integer lineNum;
    @ApiModelProperty(value = "预算占用年份")
    private Integer budgetDisYear;
    @ApiModelProperty(value = "预算占用金额（四舍五入）")
    private BigDecimal budgetDisAmount;
    @ApiModelProperty(value = "预算总时长（月）")
    private Long budgetDisGap;
    @ApiModelProperty(value = "租户id")
    private Long tenantId;

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
    private LocalDate neededDate;

    @ApiModelProperty(value = "需求开始日期所在年份")
    private Integer attributeDate1Year;
    @ApiModelProperty(value = "需求开始日期所在月份")
    private Integer attributeDate1Month;
    @ApiModelProperty(value = "需求结束日期所在年份")
    private Integer neededDateYear;
    @ApiModelProperty(value = "需求结束日期所在月份")
    private Integer neededDateMonth;
    @ApiModelProperty(value = "自动计算预算占用金额（四舍五入）")
    private BigDecimal autoCalculateBudgetDisAmount;
    @ApiModelProperty(value = "预算占用年份集合")
    private List<Integer> budgetDisYears;
    @ApiModelProperty(value = "申请行id集合")
    private List<Long> prLineIds;
}