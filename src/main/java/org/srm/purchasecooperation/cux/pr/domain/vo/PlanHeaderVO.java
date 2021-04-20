//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.srm.purchasecooperation.cux.pr.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import io.swagger.annotations.ApiModelProperty;
import org.hzero.boot.platform.lov.annotation.LovValue;
import org.srm.purchasecooperation.cux.pr.infra.constant.Constants;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@JsonInclude(Include.NON_NULL)


public class PlanHeaderVO  {
    @ApiModelProperty("表ID，主键，供其他表做外键")
    private Long planId;
    @ApiModelProperty(value = "租户id", required = true)
    @NotNull
    private Long tenantId;
    @ApiModelProperty(value = "计划编号")
    private String planNum;
    @LovValue(
            lovCode = "SCUX.RCWL.SCEC.JH_STATE",
            meaningField = "stateMeaning"
    )
    @ApiModelProperty(value = "状态code")
    private String state;
    @ApiModelProperty(value = "状态")
    private String stateMeaning;
    @LovValue(
            lovCode = "SCUX.RCWL.SCEC.JH_FORMAT",
            meaningField = "formatMeaning"
    )
    @ApiModelProperty(value = "业态code")
    private String format;

    @ApiModelProperty(value = "业态")
    private String formatMeaning;
//    @LovValue(
//            lovCode = "SPFM.USER_AUTH.COMPANY",
//            meaningField = "companyName"
//    )
    @ApiModelProperty(value = "公司id")
    private Long companyId;
    @ApiModelProperty(value = "公司")
    private String companyName;
    @LovValue(
            lovCode = "SCUX.RCWL.SCEC.JH_PROCUREMENT",
            meaningField = "prCategoryMeaning"
    )
    @ApiModelProperty(value = "采购分类code")
    private String prCategory;
    @ApiModelProperty(value = "采购分类")
    private String prCategoryMeaning;
    @LovValue(
            lovCode = "SCUX.RCWL.SCEC.JH_MODE",
            meaningField = "prWayMeaning"
    )
    @ApiModelProperty(value = "采购方式code")
    private String prWay;
    @ApiModelProperty(value = "采购方式")
    private String prWayMeaning;
    @LovValue(
            lovCode = "SSRC.RCWL.BID_EVAL_METHOD",
            meaningField = "bidMethodMeaning"
    )
    @ApiModelProperty(value = "评标方法code")
    private String bidMethod;
    @ApiModelProperty(value = "评标方法")
    private String bidMethodMeaning;
//    @LovValue(
//            lovCode = "SPCM.ACCEPT_USER",
//            meaningField = "demandersMeaning"
//    )
    @ApiModelProperty(value = "需求人code")
    private String demanders;
    @ApiModelProperty(value = "需求人")
    private String demandersMeaning;
    @ApiModelProperty(value = "创建时间从")
    private Date creationDateFrom;
    @ApiModelProperty(value = "创建时间至")
    private Date creationDateTo;
    @ApiModelProperty(value = "采购申请头id")
    private Long prHeaderId;
    @ApiModelProperty(value = "采购申请编号")
    private String prNum;
    @ApiModelProperty(value = "行号")
    private String lineNum;
    @ApiModelProperty(value = "采购申请行id")
    private Long prLineId;
//    @LovValue(
//            lovCode = "SMDM.BUDGET_ACCOUNT",
//            meaningField = "budgetAccountMeaning"
//    )
    @ApiModelProperty(value = "预算科目code")
    private String budgetAccount;
    @ApiModelProperty(value = "预算科目")
    private String budgetAccountMeaning;
    @LovValue(
            lovCode = "SCUX.RCWL.SCEC.JH_BIDDING",
            meaningField = "biddingModeMeaning"
    )
    @ApiModelProperty(value = "招采模式code")
    private String biddingMode;
    @ApiModelProperty(value = "招采模式")
    private String biddingModeMeaning;
//    @LovValue(
//            lovCode = "SPUC.PURCHASE_AGENT",
//            meaningField = "agentMeaning"
//    )
    @ApiModelProperty(value = "经办人code")
    private String agent;
    @ApiModelProperty(value = "经办人")
    private String agentMeaning;

    @ApiModelProperty(value = "立项金额（万元）")
    private BigDecimal projectAmount;
    @ApiModelProperty(value = "定标金额")
    private BigDecimal bidAmount;
    @ApiModelProperty(value = "合同金额")
    private BigDecimal contractAmount;

    @JsonFormat(pattern = Constants.Pattern.DATE)
    @ApiModelProperty(value = "需求计划完成时间")
    private LocalDate dePlanFinTime;

    @ApiModelProperty(value = "需求审批完成时间")
    @JsonFormat(pattern = Constants.Pattern.DATE)
    private LocalDate deApprFinTime;

    @ApiModelProperty(value = "计划完成时间（供方入围）")
    @JsonFormat(pattern = Constants.Pattern.DATE)
    private LocalDate planFinVenTime;

    @ApiModelProperty(value = "计划完成时间（立项审批）")
    @JsonFormat(pattern = Constants.Pattern.DATE)
    private LocalDate planFinApprTime;

    @ApiModelProperty(value = "计划完成时间（发标时间）")
    @JsonFormat(pattern = Constants.Pattern.DATE)
    private LocalDate planFinIssueTime;

    @ApiModelProperty(value = "计划完成时间（定标时间）")
    @JsonFormat(pattern = Constants.Pattern.DATE)
    private LocalDate planFinBidTime;

    @ApiModelProperty(value = "计划完成时间（合同完成时间）")
    @JsonFormat(pattern = Constants.Pattern.DATE)
    private LocalDate planFinConTime;

    @ApiModelProperty(value = "备注")
    private String remarks;
    @ApiModelProperty(value = "附件")
    private String attachment;
    @LovValue(
            lovCode = "HPFM.FLAG",
            meaningField = "addFlagMeaning"
    )
    @ApiModelProperty(value = "是否临时新增code")
    private Integer addFlag;
    @ApiModelProperty(value = "是否临时新增")
    private String addFlagMeaning;
    @ApiModelProperty(value = "整体周期（天数）")
    private String attributeVarchar1;
    @ApiModelProperty(value = "attribute varchar2")
    private String attributeVarchar2;
    @ApiModelProperty(value = "询报价单号")
    private String attributeVarchar3;
    @ApiModelProperty(value = "招标单号")
    private String attributeVarchar4;
    @ApiModelProperty(value = "采购合同号")
    private String attributeVarchar5;
    @JsonFormat(pattern = Constants.Pattern.DATE)
    @ApiModelProperty(value = "实际完成时间（供方入围）")
    private LocalDate attributeDate1;
    @ApiModelProperty(value = "实际完成时间（立项审批）")
    @JsonFormat(pattern = Constants.Pattern.DATE)
    private LocalDate attributeDate2;
    @ApiModelProperty(value = "实际完成时间（发标时间）")
    @JsonFormat(pattern = Constants.Pattern.DATE)
    private LocalDate attributeDate3;
    @ApiModelProperty(value = "实际完成时间（定标时间）")
    @JsonFormat(pattern = Constants.Pattern.DATE)
    private LocalDate attributeDate4;
    @ApiModelProperty(value = "实际完成时间（合同完成时间）")
    @JsonFormat(pattern = Constants.Pattern.DATE)
    private LocalDate attributeDate5;
    @ApiModelProperty(value = "创建人名称")
    private String createdByName ;
    @ApiModelProperty(value = "创建时间")
    private Date creationDate;
    @ApiModelProperty(value = "流程编号")
    private String processNum;
    @LovValue(
            lovCode = "SCUX.RCWL.SCEC.JH_BPM",
            meaningField = "approvalStatusMeaning"
    )
    @ApiModelProperty(value = "审批状态code")
    private String approvalStatus;
    @ApiModelProperty(value = "审批状态")
    private String approvalStatusMeaning;

    public PlanHeaderVO() {
    }

    public Long getPlanId() {
        return planId;
    }

    public void setPlanId(Long planId) {
        this.planId = planId;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public String getPlanNum() {
        return planNum;
    }

    public void setPlanNum(String planNum) {
        this.planNum = planNum;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStateMeaning() {
        return stateMeaning;
    }

    public void setStateMeaning(String stateMeaning) {
        this.stateMeaning = stateMeaning;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getFormatMeaning() {
        return formatMeaning;
    }

    public void setFormatMeaning(String formatMeaning) {
        this.formatMeaning = formatMeaning;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getPrCategory() {
        return prCategory;
    }

    public void setPrCategory(String prCategory) {
        this.prCategory = prCategory;
    }

    public String getPrCategoryMeaning() {
        return prCategoryMeaning;
    }

    public void setPrCategoryMeaning(String prCategoryMeaning) {
        this.prCategoryMeaning = prCategoryMeaning;
    }

    public String getPrWay() {
        return prWay;
    }

    public void setPrWay(String prWay) {
        this.prWay = prWay;
    }

    public String getPrWayMeaning() {
        return prWayMeaning;
    }

    public void setPrWayMeaning(String prWayMeaning) {
        this.prWayMeaning = prWayMeaning;
    }

    public String getBidMethod() {
        return bidMethod;
    }

    public void setBidMethod(String bidMethod) {
        this.bidMethod = bidMethod;
    }

    public String getBidMethodMeaning() {
        return bidMethodMeaning;
    }

    public void setBidMethodMeaning(String bidMethodMeaning) {
        this.bidMethodMeaning = bidMethodMeaning;
    }

    public String getDemanders() {
        return demanders;
    }

    public void setDemanders(String demanders) {
        this.demanders = demanders;
    }

    public String getDemandersMeaning() {
        return demandersMeaning;
    }

    public void setDemandersMeaning(String demandersMeaning) {
        this.demandersMeaning = demandersMeaning;
    }

    public Date getCreationDateFrom() {
        return creationDateFrom;
    }

    public void setCreationDateFrom(Date creationDateFrom) {
        this.creationDateFrom = creationDateFrom;
    }

    public Date getCreationDateTo() {
        return creationDateTo;
    }

    public void setCreationDateTo(Date creationDateTo) {
        this.creationDateTo = creationDateTo;
    }

    public Long getPrHeaderId() {
        return prHeaderId;
    }

    public void setPrHeaderId(Long prHeaderId) {
        this.prHeaderId = prHeaderId;
    }

    public String getPrNum() {
        return prNum;
    }

    public void setPrNum(String prNum) {
        this.prNum = prNum;
    }

    public String getLineNum() {
        return lineNum;
    }

    public void setLineNum(String lineNum) {
        this.lineNum = lineNum;
    }

    public Long getPrLineId() {
        return prLineId;
    }

    public void setPrLineId(Long prLineId) {
        this.prLineId = prLineId;
    }

    public String getBudgetAccount() {
        return budgetAccount;
    }

    public void setBudgetAccount(String budgetAccount) {
        this.budgetAccount = budgetAccount;
    }

    public String getBudgetAccountMeaning() {
        return budgetAccountMeaning;
    }

    public void setBudgetAccountMeaning(String budgetAccountMeaning) {
        this.budgetAccountMeaning = budgetAccountMeaning;
    }

    public String getBiddingMode() {
        return biddingMode;
    }

    public void setBiddingMode(String biddingMode) {
        this.biddingMode = biddingMode;
    }

    public String getBiddingModeMeaning() {
        return biddingModeMeaning;
    }

    public void setBiddingModeMeaning(String biddingModeMeaning) {
        this.biddingModeMeaning = biddingModeMeaning;
    }

    public String getAgent() {
        return agent;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }

    public String getAgentMeaning() {
        return agentMeaning;
    }

    public void setAgentMeaning(String agentMeaning) {
        this.agentMeaning = agentMeaning;
    }

    public BigDecimal getProjectAmount() {
        return projectAmount;
    }

    public void setProjectAmount(BigDecimal projectAmount) {
        this.projectAmount = projectAmount;
    }

    public BigDecimal getBidAmount() {
        return bidAmount;
    }

    public void setBidAmount(BigDecimal bidAmount) {
        this.bidAmount = bidAmount;
    }

    public BigDecimal getContractAmount() {
        return contractAmount;
    }

    public void setContractAmount(BigDecimal contractAmount) {
        this.contractAmount = contractAmount;
    }

    public LocalDate getDePlanFinTime() {
        return dePlanFinTime;
    }

    public void setDePlanFinTime(LocalDate dePlanFinTime) {
        this.dePlanFinTime = dePlanFinTime;
    }

    public LocalDate getDeApprFinTime() {
        return deApprFinTime;
    }

    public void setDeApprFinTime(LocalDate deApprFinTime) {
        this.deApprFinTime = deApprFinTime;
    }

    public LocalDate getPlanFinVenTime() {
        return planFinVenTime;
    }

    public void setPlanFinVenTime(LocalDate planFinVenTime) {
        this.planFinVenTime = planFinVenTime;
    }

    public LocalDate getPlanFinApprTime() {
        return planFinApprTime;
    }

    public void setPlanFinApprTime(LocalDate planFinApprTime) {
        this.planFinApprTime = planFinApprTime;
    }

    public LocalDate getPlanFinIssueTime() {
        return planFinIssueTime;
    }

    public void setPlanFinIssueTime(LocalDate planFinIssueTime) {
        this.planFinIssueTime = planFinIssueTime;
    }

    public LocalDate getPlanFinBidTime() {
        return planFinBidTime;
    }

    public void setPlanFinBidTime(LocalDate planFinBidTime) {
        this.planFinBidTime = planFinBidTime;
    }

    public LocalDate getPlanFinConTime() {
        return planFinConTime;
    }

    public void setPlanFinConTime(LocalDate planFinConTime) {
        this.planFinConTime = planFinConTime;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public Integer getAddFlag() {
        return addFlag;
    }

    public void setAddFlag(Integer addFlag) {
        this.addFlag = addFlag;
    }

    public String getAddFlagMeaning() {
        return addFlagMeaning;
    }

    public void setAddFlagMeaning(String addFlagMeaning) {
        this.addFlagMeaning = addFlagMeaning;
    }

    public String getAttributeVarchar1() {
        return attributeVarchar1;
    }

    public void setAttributeVarchar1(String attributeVarchar1) {
        this.attributeVarchar1 = attributeVarchar1;
    }

    public String getAttributeVarchar2() {
        return attributeVarchar2;
    }

    public void setAttributeVarchar2(String attributeVarchar2) {
        this.attributeVarchar2 = attributeVarchar2;
    }

    public String getAttributeVarchar3() {
        return attributeVarchar3;
    }

    public void setAttributeVarchar3(String attributeVarchar3) {
        this.attributeVarchar3 = attributeVarchar3;
    }

    public String getAttributeVarchar4() {
        return attributeVarchar4;
    }

    public void setAttributeVarchar4(String attributeVarchar4) {
        this.attributeVarchar4 = attributeVarchar4;
    }

    public String getAttributeVarchar5() {
        return attributeVarchar5;
    }

    public void setAttributeVarchar5(String attributeVarchar5) {
        this.attributeVarchar5 = attributeVarchar5;
    }

    public LocalDate getAttributeDate1() {
        return attributeDate1;
    }

    public void setAttributeDate1(LocalDate attributeDate1) {
        this.attributeDate1 = attributeDate1;
    }

    public LocalDate getAttributeDate2() {
        return attributeDate2;
    }

    public void setAttributeDate2(LocalDate attributeDate2) {
        this.attributeDate2 = attributeDate2;
    }

    public LocalDate getAttributeDate3() {
        return attributeDate3;
    }

    public void setAttributeDate3(LocalDate attributeDate3) {
        this.attributeDate3 = attributeDate3;
    }

    public LocalDate getAttributeDate4() {
        return attributeDate4;
    }

    public void setAttributeDate4(LocalDate attributeDate4) {
        this.attributeDate4 = attributeDate4;
    }

    public LocalDate getAttributeDate5() {
        return attributeDate5;
    }

    public void setAttributeDate5(LocalDate attributeDate5) {
        this.attributeDate5 = attributeDate5;
    }

    public String getCreatedByName() {
        return createdByName;
    }

    public void setCreatedByName(String createdByName) {
        this.createdByName = createdByName;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getProcessNum() {
        return processNum;
    }

    public void setProcessNum(String processNum) {
        this.processNum = processNum;
    }

    public String getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public String getApprovalStatusMeaning() {
        return approvalStatusMeaning;
    }

    public void setApprovalStatusMeaning(String approvalStatusMeaning) {
        this.approvalStatusMeaning = approvalStatusMeaning;
    }

}