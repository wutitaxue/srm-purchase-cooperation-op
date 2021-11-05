package org.srm.purchasecooperation.cux.pr.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import org.srm.purchasecooperation.order.domain.entity.PoLine;

import java.math.BigDecimal;

/**
 * @author:pengxu.zhi@hand-china.com
 * @createTime:2021/11/05 17:36
 */
public class PoBudgetOccupyLineVO extends PoLine {

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
    @ApiModelProperty(value = "预算占用年份")
    private Integer budgetDisYear;
    @ApiModelProperty(value = "预算占用金额（四舍五入）")
    private BigDecimal budgetDisAmount;
    @ApiModelProperty(value = "预算总时长（月）")
    private Long budgetDisGap;
    @ApiModelProperty(value = "租户ID")
    private Long tenantId;


    public Long getBudgetLineId() {
        return budgetLineId;
    }

    public void setBudgetLineId(Long budgetLineId) {
        this.budgetLineId = budgetLineId;
    }

    @Override
    public Long getPoHeaderId() {
        return poHeaderId;
    }

    @Override
    public void setPoHeaderId(Long poHeaderId) {
        this.poHeaderId = poHeaderId;
    }

    @Override
    public Long getPoLineId() {
        return poLineId;
    }

    @Override
    public void setPoLineId(Long poLineId) {
        this.poLineId = poLineId;
    }

    @Override
    public Long getPrHeaderId() {
        return prHeaderId;
    }

    @Override
    public void setPrHeaderId(Long prHeaderId) {
        this.prHeaderId = prHeaderId;
    }

    @Override
    public Long getPrLineId() {
        return prLineId;
    }

    @Override
    public void setPrLineId(Long prLineId) {
        this.prLineId = prLineId;
    }

    public Integer getBudgetDisYear() {
        return budgetDisYear;
    }

    public void setBudgetDisYear(Integer budgetDisYear) {
        this.budgetDisYear = budgetDisYear;
    }

    public BigDecimal getBudgetDisAmount() {
        return budgetDisAmount;
    }

    public void setBudgetDisAmount(BigDecimal budgetDisAmount) {
        this.budgetDisAmount = budgetDisAmount;
    }

    public Long getBudgetDisGap() {
        return budgetDisGap;
    }

    public void setBudgetDisGap(Long budgetDisGap) {
        this.budgetDisGap = budgetDisGap;
    }

    @Override
    public Long getTenantId() {
        return tenantId;
    }

    @Override
    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }
}
