package org.srm.purchasecooperation.order.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.srm.purchasecooperation.cux.pr.infra.constant.Constants;
import org.srm.purchasecooperation.order.domain.entity.RcwlBudgetDistribution;
import org.srm.purchasecooperation.pr.domain.entity.PrLine;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * @author @author pengxu.zhi@hand-china.com 2021-10-29 16:56:18
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
    @JsonFormat(pattern = Constants.Pattern.DATE)
    @ApiModelProperty(value = "PO需求结束日期")
    private LocalDate needByDate;
    @ApiModelProperty(value = "PR需求结束日期")
    private LocalDate neededDate;


    @ApiModelProperty(value = "预算占用金额（系统计算值）")
    private BigDecimal budgetDisAmountCal;
    @ApiModelProperty(value = "需求开始日期所在年份")
    private Integer attributeDate1Year;
    @ApiModelProperty(value = "需求开始日期所在月份")
    private Integer attributeDate1Month;
    @ApiModelProperty(value = "需求结束日期所在年份")
    private Integer neededDateYear;
    @ApiModelProperty(value = "需求结束日期所在月份")
    private Integer neededDateMonth;

    @ApiModelProperty(value = "订单开始日期所在年份")
    private Integer needStartDateYear;
    @ApiModelProperty(value = "订单开始日期所在月份")
    private Integer needStartDateMonth;
    @ApiModelProperty(value = "订单结束日期所在年份")
    private Integer needEndDateYear;
    @ApiModelProperty(value = "订单结束日期所在月份")
    private Integer needEndDateMonth;

    @ApiModelProperty(value = "自动计算预算占用金额（四舍五入）")
    private BigDecimal autoCalculateBudgetDisAmount;
    @ApiModelProperty(value = "预算占用年份集合")
    private List<Integer> budgetDisYears;
    @ApiModelProperty(value = "申请行id集合")
    private List<Long> prLineIds;
    @ApiModelProperty(value = "是否是变更提交")
    private Integer changeSubmit;

    @ApiModelProperty(value = "采购申请行")
    private PrLine prLine;

    @ApiModelProperty(value = "变更已提交标识，0为为提交，1为已提交")
    private Integer enabledFlag;


}
