package org.srm.purchasecooperation.cux.pr.domain.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.choerodon.mybatis.annotation.ModifyAudit;
import io.choerodon.mybatis.annotation.VersionAudit;
import io.choerodon.mybatis.domain.AuditDomain;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.math.BigDecimal;

/**
 * 预算变更记录表(RcwlBudgetChangeAction)实体类
 *
 * @author jie.wang05@hand-china.com
 * @since 2021-11-01 14:58:30
 */
@ApiModel("预算变更记录表")
@VersionAudit
@ModifyAudit
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@Table(name = "scux_rcwl_budget_change_action")
public class RcwlBudgetChangeAction extends AuditDomain {
    private static final long serialVersionUID = -99330930588354735L;

    public static final String FIELD_BUDGET_CHANGE_ID = "budgetChangeId";
    public static final String FIELD_TENANT_ID = "tenantId";
    public static final String FIELD_PR_HEADER_ID = "prHeaderId";
    public static final String FIELD_PR_LINE_ID = "prLineId";
    public static final String FIELD_PR_ACTION_ID = "prActionId";
    public static final String FIELD_BUDGET_DIS_YEAR = "budgetDisYear";
    public static final String FIELD_BUDGET_DIS_AMOUNT = "budgetDisAmount";
    public static final String FIELD_BUDGET_DIS_GAP = "budgetDisGap";
    public static final String FIELD_BUDGET_GROUP = "budgetGroup";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";

    public static final String FIELD_BUDGET_DIS="budget_dis";
    public static final  String OLD = "old";
    public static final  String NEW = "new";
    @ApiModelProperty("表ID，主键")
    @Id
    @GeneratedValue
    private Long budgetChangeId;

    @ApiModelProperty(value = "租户id")
    private Long tenantId;

    @ApiModelProperty(value = "申请头id，sprm_pr_header.pr_header_id")
    private Long prHeaderId;

    @ApiModelProperty(value = "申请行id,sprm_pr_line.pr_line_id")
    private Long prLineId;

    @ApiModelProperty(value = "变更id，sprm_pr_action.action_id")
    private Long prActionId;

    @ApiModelProperty(value = "预算占用年份")
    private Integer budgetDisYear;

    @ApiModelProperty(value = "预算占用金额")
    private BigDecimal budgetDisAmount;

    @ApiModelProperty(value = "预算总时长（月）")
    private Long budgetDisGap;

    @ApiModelProperty(value = "预算组别 old为旧数据，new为新数据")
    private String budgetGroup;

    @ApiModelProperty(value = "变更已提交标识，0为为提交，1为已提交")
    private Boolean enabledFlag;

    @Transient
    @ApiModelProperty(value = "自动计算预算占用金额（四舍五入）")
    private BigDecimal autoCalculateBudgetDisAmount;

    @Transient
    @ApiModelProperty(value = "申请行总金额")
    private BigDecimal lineAmount;

    public BigDecimal getLineAmount() {
        return lineAmount;
    }

    public void setLineAmount(BigDecimal lineAmount) {
        this.lineAmount = lineAmount;
    }

    public BigDecimal getAutoCalculateBudgetDisAmount() {
        return autoCalculateBudgetDisAmount;
    }

    public void setAutoCalculateBudgetDisAmount(BigDecimal autoCalculateBudgetDisAmount) {
        this.autoCalculateBudgetDisAmount = autoCalculateBudgetDisAmount;
    }

    public Long getBudgetDisGap() {
        return budgetDisGap;
    }

    public void setBudgetDisGap(Long budgetDisGap) {
        this.budgetDisGap = budgetDisGap;
    }

    public Long getBudgetChangeId() {
        return budgetChangeId;
    }

    public void setBudgetChangeId(Long budgetChangeId) {
        this.budgetChangeId = budgetChangeId;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public Long getPrHeaderId() {
        return prHeaderId;
    }

    public void setPrHeaderId(Long prHeaderId) {
        this.prHeaderId = prHeaderId;
    }

    public Long getPrLineId() {
        return prLineId;
    }

    public void setPrLineId(Long prLineId) {
        this.prLineId = prLineId;
    }

    public Long getPrActionId() {
        return prActionId;
    }

    public void setPrActionId(Long prActionId) {
        this.prActionId = prActionId;
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

    public String getBudgetGroup() {
        return budgetGroup;
    }

    public void setBudgetGroup(String budgetGroup) {
        this.budgetGroup = budgetGroup;
    }

    public Boolean getEnabledFlag() {
        return enabledFlag;
    }

    public void setEnabledFlag(Boolean enabledFlag) {
        this.enabledFlag = enabledFlag;
    }

}
