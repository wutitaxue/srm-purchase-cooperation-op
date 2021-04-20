//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.srm.purchasecooperation.cux.pr.domain.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import io.swagger.annotations.ApiModelProperty;
import org.hzero.boot.platform.lov.annotation.LovValue;
import org.hzero.export.annotation.ExcelColumn;
import org.hzero.export.annotation.ExcelSheet;
import org.hzero.starter.keyencrypt.core.Encrypt;
import org.srm.purchasecooperation.cux.pr.infra.constant.Constants;

import java.math.BigDecimal;
import java.util.Date;

@JsonInclude(Include.NON_NULL)
@ExcelSheet(title = "采购计划")
public class PlanHeaderExportVO  {
    @Encrypt
    private Long planId;

    private Long tenantId;
    @ExcelColumn(title = "采购计划编号",order = 4)
    private String planNum;
    @LovValue(
            lovCode = "SCUX.RCWL.SCEC.JH_STATE",
            meaningField = "stateMeaning"
    )
    private String state;
    @ExcelColumn(title = "状态",order = 3)
    private String stateMeaning;
    @LovValue(
            lovCode = "SCUX.RCWL.SCEC.JH_FORMAT",
            meaningField = "formatMeaning"
    )
    private String format;
    @ExcelColumn(title = "业态",order = 9)
    private String formatMeaning;
    @ExcelColumn(title = "公司",order = 7)
    private String companyName;
    @LovValue(
            lovCode = "SCUX.RCWL.SCEC.JH_PROCUREMENT",
            meaningField = "prCategoryMeaning"
    )
    private String prCategory;
    @ExcelColumn(title = "采购分类",order = 8)
    private String prCategoryMeaning;

    @LovValue(
            lovCode = "SCUX.RCWL.SCEC.JH_MODE",
            meaningField = "prWayMeaning"
    )
    private String prWay;
    @ExcelColumn(title = "采购方式",order = 12)
    private String prWayMeaning;

    @LovValue(
            lovCode = "SSRC.RCWL.BID_EVAL_METHOD",
            meaningField = "bidMethodMeaning"
    )
    private String bidMethod;
    @ExcelColumn(title = "评标方法",order = 19)
    private String bidMethodMeaning;
    @LovValue(
            lovCode = "SPCM.ACCEPT_USER",
            meaningField = "demandersMeaning"
    )
    private String demanders;
    @ExcelColumn(title = "需求人",order = 13)
    private String demandersMeaning;
    @ExcelColumn(title = "创建日期从")
    private Date creationDateFrom;
    @ExcelColumn(title = "创建日期至")
    private Date creationDateTo;


    @ExcelColumn(title = "采购申请编号",order = 5)
    private String prNum;
    @ExcelColumn(title = "行号",order = 6)
    private String lineNum;

//    @LovValue(
//            lovCode = "SMDM.BUDGET_ACCOUNT",
//            meaningField = "budgetAccountMeaning"
//    )
    private String budgetAccount;
    @ExcelColumn(title = "预算科目",order = 10)
    private String budgetAccountMeaning;
    @LovValue(
            lovCode = "SCUX.RCWL.SCEC.JH_BIDDING",
            meaningField = "biddingModeMeaning"
    )
    private String biddingMode;
    @ExcelColumn(title = "招采模式",order = 11)
    private String biddingModeMeaning;
//    @LovValue(
//            lovCode = "SPUC.PURCHASE_AGENT",
//            meaningField = "agentMeaning"
//    )
    private String agent;
    @ExcelColumn(title = "经办人",order = 14)
    private String agentMeaning;
    @ExcelColumn(title = "立项金额（万元）",order = 15)
    private BigDecimal projectAmount;
    @ExcelColumn(title = "定标金额",order = 16)
    private BigDecimal bidAmount;
    @ExcelColumn(title = "合同金额",order = 17)
    private BigDecimal contractAmount;
    @ExcelColumn(title = "需求计划完成时间", pattern = Constants.Pattern.DATE,order = 20)
    private Date dePlanFinTime;
    @ExcelColumn(title = "需求审批完成时间", pattern = Constants.Pattern.DATE,order = 21)
    private Date deApprFinTime;
    @ExcelColumn(title = "计划完成时间（供方入围）", pattern = Constants.Pattern.DATE,order = 22)
    private Date planFinVenTime;
    @ExcelColumn(title = "计划完成时间（立项审批）", pattern = Constants.Pattern.DATE,order = 24)
    private Date planFinApprTime;
    @ExcelColumn(title = "计划完成时间（发标时间）", pattern = Constants.Pattern.DATE,order = 26)
    private Date planFinIssueTime;
    @ExcelColumn(title = "计划完成时间（定标时间）", pattern = Constants.Pattern.DATE,order = 28)
    private Date planFinBidTime;
    @ExcelColumn(title = "计划完成时间（合同完成时间）", pattern = Constants.Pattern.DATE,order = 30)
    private Date planFinConTime;
    @ExcelColumn(title = "备注",order = 32)
    private String remarks;
    @LovValue(
            lovCode = "HPFM.FLAG",
            meaningField = "addFlagMeaning"
    )
    private Integer addFlag;
    @ExcelColumn(title = "是否临时新增",order = 37)
    private String addFlagMeaning;
    @ExcelColumn(title = "整体周期（天数）",order = 18)
    private String attributeVarchar1;
    @ExcelColumn(title = "入围单号",order = 33)
    private String attributeVarchar2;
    @ExcelColumn(title = "询报价单号",order = 35)
    private String attributeVarchar3;
    @ExcelColumn(title = "招标单号",order = 34)
    private String attributeVarchar4;
    @ExcelColumn(title = "协议单号",order = 36)
    private String attributeVarchar5;
    @ExcelColumn(title = "实际完成时间（供方入围）", pattern = Constants.Pattern.DATE,order = 23)
    private Date attributeDate1;
    @ExcelColumn(title = "实际完成时间（立项审批）", pattern = Constants.Pattern.DATE,order = 25)
    private Date attributeDate2;
    @ExcelColumn(title = "实际完成时间（发标时间）", pattern = Constants.Pattern.DATE,order = 27)
    private Date attributeDate3;
    @ExcelColumn(title = "实际完成时间（定标时间）", pattern = Constants.Pattern.DATE,order = 29)
    private Date attributeDate4;
    @ExcelColumn(title = "实际完成时间（合同完成时间）", pattern = Constants.Pattern.DATE,order = 31)
    private Date attributeDate5;
//    @LovValue(
//            lovCode = "SPFM.USER_AUTH.COMPANY",
//            meaningField = "companyName"
//    )
    private Long companyId;
    @ExcelColumn(title = "创建人",order = 38)
    private String createdByName ;
    @ExcelColumn(title = "创建时间",order = 39)
    private Date creationDate;
    @ExcelColumn(title = "流程编号",order = 1)
    private String processNum;
    @LovValue(
            lovCode = "SCUX.RCWL.SCEC.JH_BPM",
            meaningField = "approvalStatusMeaning"
    )
    @ApiModelProperty(value = "审批状态code")
    private String approvalStatus;
    @ExcelColumn(title = "审批状态",order = 2)
    private String approvalStatusMeaning;

    public PlanHeaderExportVO() {
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
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

    public Date getAttributeDate1() {
        return attributeDate1;
    }

    public void setAttributeDate1(Date attributeDate1) {
        this.attributeDate1 = attributeDate1;
    }

    public Date getAttributeDate2() {
        return attributeDate2;
    }

    public void setAttributeDate2(Date attributeDate2) {
        this.attributeDate2 = attributeDate2;
    }

    public Date getAttributeDate3() {
        return attributeDate3;
    }

    public void setAttributeDate3(Date attributeDate3) {
        this.attributeDate3 = attributeDate3;
    }

    public Date getAttributeDate4() {
        return attributeDate4;
    }

    public void setAttributeDate4(Date attributeDate4) {
        this.attributeDate4 = attributeDate4;
    }

    public Date getAttributeDate5() {
        return attributeDate5;
    }

    public void setAttributeDate5(Date attributeDate5) {
        this.attributeDate5 = attributeDate5;
    }

    public String getCreatedByName() {
        return createdByName;
    }

    public void setCreatedByName(String createdByName) {
        this.createdByName = createdByName;
    }
}
