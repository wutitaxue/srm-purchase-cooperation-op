package org.srm.purchasecooperation.cux.pr.domain.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.choerodon.mybatis.domain.AuditDomain;

import java.math.BigDecimal;

import io.choerodon.mybatis.annotation.ModifyAudit;
import io.choerodon.mybatis.annotation.VersionAudit;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

/**
 * 预算分配
 *
 * @author jie.wang05@hand-china.com 2021-10-27 14:49:26
 */
@ApiModel("预算分配")
@VersionAudit
@ModifyAudit
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@Table(name = "scux_rcwl_budget_distribution")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RcwlBudgetDistribution extends AuditDomain {

    public static final String FIELD_BUDGET_LINE_ID = "budgetLineId";
    public static final String FIELD_PO_HEADER_ID = "poHeaderId";
    public static final String FIELD_PO_LINE_ID = "poLineId";
    public static final String FIELD_PR_HEADER_ID = "prHeaderId";
    public static final String FIELD_PR_LINE_ID = "prLineId";
    public static final String FIELD_BUDGET_DIS_YEAR = "budgetDisYear";
    public static final String FIELD_BUDGET_DIS_AMOUNT = "budgetDisAmount";
    public static final String FIELD_BUDGET_DIS_GAP = "budgetDisGap";
    public static final String FIELD_TENANT_ID = "tenantId";

    //
    // 业务方法(按public protected private顺序排列)
    // ------------------------------------------------------------------------------

    //
    // 数据库字段
    // ------------------------------------------------------------------------------


    @ApiModelProperty("表ID，主键")
    @Id
    @GeneratedValue
    private Long budgetLineId;
    @ApiModelProperty(value = "订单头id,sodr_po_header.po_header_id")
    private Long poHeaderId;
    @ApiModelProperty(value = "订单行id,sodr_po_line,po_line_id")
    private Long poLineId;
    @ApiModelProperty(value = "申请头id,sprm_pr_header.pr_header_id")
    private Long prHeaderId;
    @ApiModelProperty(value = "申请行id,sprm_pr_line.pr_line_id")
    private Long prLineId;
    @ApiModelProperty(value = "预算占用年份", required = true)
    @NotNull
    private Integer budgetDisYear;
    @ApiModelProperty(value = "预算占用金额（四舍五入）", required = true)
    @NotNull
    private BigDecimal budgetDisAmount;
    @ApiModelProperty(value = "预算总时长（月）", required = true)
    @NotNull
    private Long budgetDisGap;
    @ApiModelProperty(value = "租户ID", required = true)
    @NotNull
    private Long tenantId;

    //
    // 非数据库字段
    // ------------------------------------------------------------------------------
    @Transient
    @ApiModelProperty(value = "预算占用金额（系统计算值）")
    private BigDecimal budgetDisAmountCal;
    @Transient
    @ApiModelProperty(value = "行金额")
    private BigDecimal lineAmount;
    //
    // getter/setter
    // ------------------------------------------------------------------------------

	public BigDecimal getBudgetDisAmountCal() {
		return budgetDisAmountCal;
	}

	public void setBudgetDisAmountCal(BigDecimal budgetDisAmountCal) {
		this.budgetDisAmountCal = budgetDisAmountCal;
	}

	public BigDecimal getLineAmount() {
		return lineAmount;
	}

	public void setLineAmount(BigDecimal lineAmount) {
		this.lineAmount = lineAmount;
	}

	public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    /**
     * @return 表ID，主键
     */
    public Long getBudgetLineId() {
        return budgetLineId;
    }

    public void setBudgetLineId(Long budgetLineId) {
        this.budgetLineId = budgetLineId;
    }

    /**
     * @return 订单头id, sodr_po_header.po_header_id
     */
    public Long getPoHeaderId() {
        return poHeaderId;
    }

    public void setPoHeaderId(Long poHeaderId) {
        this.poHeaderId = poHeaderId;
    }

    /**
     * @return 订单行id, sodr_po_line, po_line_id
     */
    public Long getPoLineId() {
        return poLineId;
    }

    public void setPoLineId(Long poLineId) {
        this.poLineId = poLineId;
    }

    /**
     * @return 申请头id, sprm_pr_header.pr_header_id
     */
    public Long getPrHeaderId() {
        return prHeaderId;
    }

    public void setPrHeaderId(Long prHeaderId) {
        this.prHeaderId = prHeaderId;
    }

    /**
     * @return 申请行id, sprm_pr_line.pr_line_id
     */
    public Long getPrLineId() {
        return prLineId;
    }

    public void setPrLineId(Long prLineId) {
        this.prLineId = prLineId;
    }

    /**
     * @return 预算占用年份
     */
    public Integer getBudgetDisYear() {
        return budgetDisYear;
    }

    public void setBudgetDisYear(Integer budgetDisYear) {
        this.budgetDisYear = budgetDisYear;
    }

    /**
     * @return 预算占用金额（四舍五入）
     */
    public BigDecimal getBudgetDisAmount() {
        return budgetDisAmount;
    }

    public void setBudgetDisAmount(BigDecimal budgetDisAmount) {
        this.budgetDisAmount = budgetDisAmount;
    }

    /**
     * @return 预算总时长（月）
     */
    public Long getBudgetDisGap() {
        return budgetDisGap;
    }

    public void setBudgetDisGap(Long budgetDisGap) {
        this.budgetDisGap = budgetDisGap;
    }

}
