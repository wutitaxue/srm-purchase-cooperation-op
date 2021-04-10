//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.srm.purchasecooperation.cux.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import org.srm.common.mybatis.domain.ExpandDomain;

import java.math.BigDecimal;
import java.util.Date;


public class PlanHeaderImportVO extends ExpandDomain {


    @ApiModelProperty(value = "计划编号")
    private String planNum;
    @ApiModelProperty(value = "状态")
    private String state;
    @ApiModelProperty(value = "业态")
    private String format;
    @ApiModelProperty(value = "公司")
    private String companyName;
    @ApiModelProperty(value = "采购分类")
    private String prCategory;
    @ApiModelProperty(value = "采购方式")
    private String prWay;
    @ApiModelProperty(value = "评标方法")
    private String bidMethod;
    @ApiModelProperty(value = "需求人")
    private String demanders;
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
    @ApiModelProperty(value = "预算科目")
    private String budgetAccount;
    @ApiModelProperty(value = "招采模式")
    private String biddingMode;
    @ApiModelProperty(value = "经办人")
    private String agent;
    @ApiModelProperty(value = "立项金额（万元）")
    private BigDecimal projectAmount;
    @ApiModelProperty(value = "定标金额")
    private BigDecimal bidAmount;
    @ApiModelProperty(value = "合同金额")
    private BigDecimal contractAmount;
    @ApiModelProperty(value = "需求计划完成时间")
    private Date dePlanFinTime;
    @ApiModelProperty(value = "需求审批完成时间")
    private Date deApprFinTime;
    @ApiModelProperty(value = "计划完成时间（供方入围）")
    private Date planFinVenTime;
    @ApiModelProperty(value = "计划完成时间（立项审批）")
    private Date planFinApprTime;
    @ApiModelProperty(value = "计划完成时间（发标时间）")
    private Date planFinIssueTime;
    @ApiModelProperty(value = "计划完成时间（定标时间）")
    private Date planFinBidTime;
    @ApiModelProperty(value = "计划完成时间（合同完成时间）")
    private Date planFinConTime;
    @ApiModelProperty(value = "备注")
    private String remarks;
    @ApiModelProperty(value = "附件")
    private String attachment;
    @ApiModelProperty(value = "是否临时新增")
    private Integer addFlag;
    @ApiModelProperty(value = "公司id")
    private Long companyId;

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

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
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

    public String getPrWay() {
        return prWay;
    }

    public void setPrWay(String prWay) {
        this.prWay = prWay;
    }

    public String getBidMethod() {
        return bidMethod;
    }

    public void setBidMethod(String bidMethod) {
        this.bidMethod = bidMethod;
    }

    public String getDemanders() {
        return demanders;
    }

    public void setDemanders(String demanders) {
        this.demanders = demanders;
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

    public String getBiddingMode() {
        return biddingMode;
    }

    public void setBiddingMode(String biddingMode) {
        this.biddingMode = biddingMode;
    }

    public String getAgent() {
        return agent;
    }

    public void setAgent(String agent) {
        this.agent = agent;
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

    public Date getDePlanFinTime() {
        return dePlanFinTime;
    }

    public void setDePlanFinTime(Date dePlanFinTime) {
        this.dePlanFinTime = dePlanFinTime;
    }

    public Date getDeApprFinTime() {
        return deApprFinTime;
    }

    public void setDeApprFinTime(Date deApprFinTime) {
        this.deApprFinTime = deApprFinTime;
    }

    public Date getPlanFinVenTime() {
        return planFinVenTime;
    }

    public void setPlanFinVenTime(Date planFinVenTime) {
        this.planFinVenTime = planFinVenTime;
    }

    public Date getPlanFinApprTime() {
        return planFinApprTime;
    }

    public void setPlanFinApprTime(Date planFinApprTime) {
        this.planFinApprTime = planFinApprTime;
    }

    public Date getPlanFinIssueTime() {
        return planFinIssueTime;
    }

    public void setPlanFinIssueTime(Date planFinIssueTime) {
        this.planFinIssueTime = planFinIssueTime;
    }

    public Date getPlanFinBidTime() {
        return planFinBidTime;
    }

    public void setPlanFinBidTime(Date planFinBidTime) {
        this.planFinBidTime = planFinBidTime;
    }

    public Date getPlanFinConTime() {
        return planFinConTime;
    }

    public void setPlanFinConTime(Date planFinConTime) {
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

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

//    //校验
//    public boolean checkData(ImportData context,Long tenantId) {
//        //校验采购申请头行是否存在重复
//        if (this.prNum != null && this.lineNum != null) {
//            boolean check = planHeaderRepository.checkPrNumRep(this.prNum, this.lineNum,tenantId);
//            if (!check) {
//                context.addErrorMsg(MessageAccessor.getMessage("error.ssrc.pr_header_and_line_repeat").desc());
//                return false;
//            }
//            return true;
//        }
//        else if (this.companyName != null){
//            boolean check = planHeaderRepository.checkCompanyExist(this.companyName);
//            if (!check) {
//                context.addErrorMsg(MessageAccessor.getMessage("error.ssrc.company_not_exist").desc());
//                return false;
//            }
//            return true;
//        }
//        else if (this.budgetAccount != null){
//            boolean check = planHeaderRepository.checkBudgetAccountExist(this.budgetAccount,tenantId);
//            if (!check) {
//                context.addErrorMsg(MessageAccessor.getMessage("error.ssrc.budget_account_not_exist").desc());
//                return false;
//            }
//            return true;
//        }
//        else if (this.demanders != null){
//            boolean check = planHeaderRepository.checkDemandersExist(this.demanders,tenantId);
//            if (!check) {
//                context.addErrorMsg(MessageAccessor.getMessage("error.ssrc.demanders_not_exist").desc());
//                return false;
//            }
//            return true;
//        }
//        else if (this.agent != null){
//            boolean check = planHeaderRepository.checkAgentExist(this.agent,tenantId);
//            if (!check) {
//                context.addErrorMsg(MessageAccessor.getMessage("error.ssrc.agent_not_exist").desc());
//                return false;
//            }
//            return true;
//        }
//
//        return true;
//    }

    @Override
    public String toString() {
        return "PlanHeaderImportVO{" +
                "planNum='" + planNum + '\'' +
                ", state='" + state + '\'' +
                ", format='" + format + '\'' +
                ", companyName='" + companyName + '\'' +
                ", prCategory='" + prCategory + '\'' +
                ", prWay='" + prWay + '\'' +
                ", bidMethod='" + bidMethod + '\'' +
                ", demanders='" + demanders + '\'' +
                ", creationDateFrom=" + creationDateFrom +
                ", creationDateTo=" + creationDateTo +
                ", prHeaderId=" + prHeaderId +
                ", prNum='" + prNum + '\'' +
                ", lineNum='" + lineNum + '\'' +
                ", prLineId=" + prLineId +
                ", budgetAccount='" + budgetAccount + '\'' +
                ", biddingMode='" + biddingMode + '\'' +
                ", agent='" + agent + '\'' +
                ", projectAmount=" + projectAmount +
                ", bidAmount=" + bidAmount +
                ", contractAmount=" + contractAmount +
                ", dePlanFinTime=" + dePlanFinTime +
                ", deApprFinTime=" + deApprFinTime +
                ", planFinVenTime=" + planFinVenTime +
                ", planFinApprTime=" + planFinApprTime +
                ", planFinIssueTime=" + planFinIssueTime +
                ", planFinBidTime=" + planFinBidTime +
                ", planFinConTime=" + planFinConTime +
                ", remarks='" + remarks + '\'' +
                ", attachment='" + attachment + '\'' +
                ", addFlag=" + addFlag +
                ", companyId=" + companyId +
                '}';
    }
}
