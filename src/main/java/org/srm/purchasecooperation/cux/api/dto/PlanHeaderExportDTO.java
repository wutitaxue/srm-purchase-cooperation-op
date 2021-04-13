package org.srm.purchasecooperation.cux.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import org.hzero.core.base.BaseConstants;
import org.hzero.starter.keyencrypt.core.Encrypt;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * bin.zhang
 */
public class PlanHeaderExportDTO {
    @ApiModelProperty("主键列表，当填写此参数时，其他过滤条件不会生效")
    @Encrypt
    private List<Long> planIds;
    @ApiModelProperty(
            value = "租户Id",
            hidden = true
    )
    private Long tenantId;

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

    @DateTimeFormat(pattern = BaseConstants.Pattern.DATE)
    @JsonFormat(pattern = BaseConstants.Pattern.DATE)
    @ApiModelProperty(value = "创建日期从")
    private Date creationDateFrom;
    @DateTimeFormat(pattern = BaseConstants.Pattern.DATE)
    @JsonFormat(pattern = BaseConstants.Pattern.DATE)
    @ApiModelProperty(value = "创建日期至")
    private Date creationDateTo;

    @ApiModelProperty(value = "采购申请头id")
    private Long prHeaderId;

    @ApiModelProperty(value = "采购申请编号")
    private String prNum;

    @ApiModelProperty(value = "行号")
    private String lineNum;

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

    @DateTimeFormat(pattern = BaseConstants.Pattern.DATE)
    @JsonFormat(pattern = BaseConstants.Pattern.DATE)
    @ApiModelProperty(value = "需求计划完成时间")
    private Date dePlanFinTime;

    @DateTimeFormat(pattern = BaseConstants.Pattern.DATE)
    @JsonFormat(pattern = BaseConstants.Pattern.DATE)
    @ApiModelProperty(value = "需求审批完成时间")
    private Date deApprFinTime;

    @DateTimeFormat(pattern = BaseConstants.Pattern.DATE)
    @JsonFormat(pattern = BaseConstants.Pattern.DATE)
    @ApiModelProperty(value = "计划完成时间（供方入围）")
    private Date planFinVenTime;

    @DateTimeFormat(pattern = BaseConstants.Pattern.DATE)
    @JsonFormat(pattern = BaseConstants.Pattern.DATE)
    @ApiModelProperty(value = "计划完成时间（立项审批）")
    private Date planFinApprTime;

    @DateTimeFormat(pattern = BaseConstants.Pattern.DATE)
    @JsonFormat(pattern = BaseConstants.Pattern.DATE)
    @ApiModelProperty(value = "计划完成时间（发标时间）")
    private Date planFinIssueTime;

    @DateTimeFormat(pattern = BaseConstants.Pattern.DATE)
    @JsonFormat(pattern = BaseConstants.Pattern.DATE)
    @ApiModelProperty(value = "计划完成时间（定标时间）")
    private Date planFinBidTime;

    @DateTimeFormat(pattern = BaseConstants.Pattern.DATE)
    @JsonFormat(pattern = BaseConstants.Pattern.DATE)
    @ApiModelProperty(value = "计划完成时间（合同完成时间）")
    private Date planFinConTime;

    @ApiModelProperty(value = "备注")
    private String remarks;


    @ApiModelProperty(value = "是否临时新增")
    private Integer addFlag;


    @ApiModelProperty(value = "整体周期（天数）")
    private String attributeVarchar1;

    @ApiModelProperty(value = "入围单号")
    private String attributeVarchar2;

    @ApiModelProperty(value = "询报价单号")
    private String attributeVarchar3;

    @ApiModelProperty(value = "招标单号")
    private String attributeVarchar4;

    @ApiModelProperty(value = "采购合同号")
    private String attributeVarchar5;

    @DateTimeFormat(pattern = BaseConstants.Pattern.DATE)
    @JsonFormat(pattern = BaseConstants.Pattern.DATE)
    @ApiModelProperty(value = "实际完成时间（供方入围）")
    private Date attributeDate1;
    @DateTimeFormat(pattern = BaseConstants.Pattern.DATE)
    @JsonFormat(pattern = BaseConstants.Pattern.DATE)
    @ApiModelProperty(value = "实际完成时间（立项审批）")
    private Date attributeDate2;
    @DateTimeFormat(pattern = BaseConstants.Pattern.DATE)
    @JsonFormat(pattern = BaseConstants.Pattern.DATE)
    @ApiModelProperty(value = "实际完成时间（发标时间）")
    private Date attributeDate3;
    @DateTimeFormat(pattern = BaseConstants.Pattern.DATE)
    @JsonFormat(pattern = BaseConstants.Pattern.DATE)
    @ApiModelProperty(value = "实际完成时间（定标时间）")
    private Date attributeDate4;
    @DateTimeFormat(pattern = BaseConstants.Pattern.DATE)
    @JsonFormat(pattern = BaseConstants.Pattern.DATE)
    @ApiModelProperty(value = "实际完成时间（合同完成时间）")
    private Date attributeDate5;

    @ApiModelProperty(value = "公司id")
    private Long companyId;

    public List<Long> getPlanIds() {
        return planIds;
    }

    public void setPlanIds(List<Long> planIds) {
        this.planIds = planIds;
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

    public Integer getAddFlag() {
        return addFlag;
    }

    public void setAddFlag(Integer addFlag) {
        this.addFlag = addFlag;
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

    public Long getPrHeaderId() {
        return prHeaderId;
    }

    public void setPrHeaderId(Long prHeaderId) {
        this.prHeaderId = prHeaderId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }
}
