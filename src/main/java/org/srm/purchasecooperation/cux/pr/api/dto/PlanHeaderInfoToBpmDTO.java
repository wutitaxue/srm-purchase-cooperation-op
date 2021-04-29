package org.srm.purchasecooperation.cux.pr.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

/**
 * @description:
 * @author: bin.zhang
 * @createDate: 2021/4/27 19:04
 */
public class PlanHeaderInfoToBpmDTO {
    @ApiModelProperty(value = "采购分类")
    @JsonProperty("PRCATEGORY")
    private  String prCategory;

    @ApiModelProperty(value = "业态")
    @JsonProperty("FORMAT")
    private  String format;

    @ApiModelProperty(value = "预算科目")
    @JsonProperty("BUDGETACCOUNT")
    private  String budgetAccount;

    @ApiModelProperty(value = "招采模式")
    @JsonProperty("BIDDINGMODE")
    private  String biddingMode;

    @ApiModelProperty(value = "采购方式")
    @JsonProperty("PRWAY")
    private  String prWay;

    @ApiModelProperty(value = "需求人")
    @JsonProperty("DEMANDERS")
    private  String demanders;

    @ApiModelProperty(value = "经办人")
    @JsonProperty("AGENT")
    private  String agent;

    @ApiModelProperty(value = "立项金额")
    @JsonProperty("PROJECTAMOUNT")
    private  String projectAmount;

    @ApiModelProperty(value = "评标方法")
    @JsonProperty("BIDMETHOD")
    private  String bidMethod;

    @ApiModelProperty(value = "需求计划完成时间")
    @JsonProperty("DEPLANFINTIME")
    private  String dePlanFinTime;

    @ApiModelProperty(value = "供方入围计划完成时间")
    @JsonProperty("PLANFINVENTIME")
    private  String planFinVenTime;

    @ApiModelProperty(value = "立项审批计划完成时间")
    @JsonProperty("PLANFINAPPRTIME")
    private  String planFinApprTime;

    @ApiModelProperty(value = "发标时间计划完成时间")
    @JsonProperty("PLANFINISSUETIME")
    private  String planFinIssueTime;

    @ApiModelProperty(value = "定标时间计划完成时间")
    @JsonProperty("PLANFINBIDTIME")
    private  String planFinBidTime;

    @ApiModelProperty(value = "合同时间计划完成时间")
    @JsonProperty("PLANFINCONTIME")
    private  String planFinConTime;

    @ApiModelProperty(value = "采购申请编号")
    @JsonProperty("PRNUM")
    private  String prNum;

    @ApiModelProperty(value = "行号")
    @JsonProperty("LINENUM")
    private  String lineNum;

    @ApiModelProperty(value = "备注")
    @JsonProperty("REMARKS")
    private  String remarks;

    public String getPrCategory() {
        return prCategory;
    }

    public void setPrCategory(String prCategory) {
        this.prCategory = prCategory;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getBudgetAccount() {
        return budgetAccount;
    }

    public void setBudgetAccount(String budgetAccount) {
        this.budgetAccount = budgetAccount;
    }

    public String getBiddingMode() {
        return biddingMode;
    }

    public void setBiddingMode(String biddingMode) {
        this.biddingMode = biddingMode;
    }

    public String getPrWay() {
        return prWay;
    }

    public void setPrWay(String prWay) {
        this.prWay = prWay;
    }

    public String getDemanders() {
        return demanders;
    }

    public void setDemanders(String demanders) {
        this.demanders = demanders;
    }

    public String getAgent() {
        return agent;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }

    public String getProjectAmount() {
        return projectAmount;
    }

    public void setProjectAmount(String projectAmount) {
        this.projectAmount = projectAmount;
    }

    public String getBidMethod() {
        return bidMethod;
    }

    public void setBidMethod(String bidMethod) {
        this.bidMethod = bidMethod;
    }

    public String getDePlanFinTime() {
        return dePlanFinTime;
    }

    public void setDePlanFinTime(String dePlanFinTime) {
        this.dePlanFinTime = dePlanFinTime;
    }

    public String getPlanFinVenTime() {
        return planFinVenTime;
    }

    public void setPlanFinVenTime(String planFinVenTime) {
        this.planFinVenTime = planFinVenTime;
    }

    public String getPlanFinApprTime() {
        return planFinApprTime;
    }

    public void setPlanFinApprTime(String planFinApprTime) {
        this.planFinApprTime = planFinApprTime;
    }

    public String getPlanFinIssueTime() {
        return planFinIssueTime;
    }

    public void setPlanFinIssueTime(String planFinIssueTime) {
        this.planFinIssueTime = planFinIssueTime;
    }

    public String getPlanFinBidTime() {
        return planFinBidTime;
    }

    public void setPlanFinBidTime(String planFinBidTime) {
        this.planFinBidTime = planFinBidTime;
    }

    public String getPlanFinConTime() {
        return planFinConTime;
    }

    public void setPlanFinConTime(String planFinConTime) {
        this.planFinConTime = planFinConTime;
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

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Override
    public String toString() {
        return "PlanHeaderInfoToBpmDTO{" +
                "prCategory='" + prCategory + '\'' +
                ", format='" + format + '\'' +
                ", budgetAccount='" + budgetAccount + '\'' +
                ", biddingMode='" + biddingMode + '\'' +
                ", prWay='" + prWay + '\'' +
                ", demanders='" + demanders + '\'' +
                ", agent='" + agent + '\'' +
                ", projectAmount='" + projectAmount + '\'' +
                ", bidMethod='" + bidMethod + '\'' +
                ", dePlanFinTime='" + dePlanFinTime + '\'' +
                ", planFinVenTime='" + planFinVenTime + '\'' +
                ", planFinApprTime='" + planFinApprTime + '\'' +
                ", planFinIssueTime='" + planFinIssueTime + '\'' +
                ", planFinBidTime='" + planFinBidTime + '\'' +
                ", planFinConTime='" + planFinConTime + '\'' +
                ", prNum='" + prNum + '\'' +
                ", lineNum='" + lineNum + '\'' +
                ", remarks='" + remarks + '\'' +
                '}';
    }
}
