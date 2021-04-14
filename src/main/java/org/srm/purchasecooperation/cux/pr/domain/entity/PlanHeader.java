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
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 采购计划表
 *
 * @author bin.zhang06@hand-china.com 2021-03-15 12:30:00
 */
@ApiModel("采购计划表")
@VersionAudit
@ModifyAudit
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@Table(name = "scux_rcwl_plan_header")
public class PlanHeader extends AuditDomain {

    public static final String FIELD_PLAN_ID = "planId";
    public static final String FIELD_TENANT_ID = "tenantId";
    public static final String FIELD_PLAN_NUM = "planNum";
    public static final String FIELD_STATE = "state";
    public static final String FIELD_FORMAT = "format";
    public static final String FIELD_COMPANY_NAME = "companyName";
    public static final String FIELD_PR_CATEGORY = "prCategory";
    public static final String FIELD_PR_WAY = "prWay";
    public static final String FIELD_BID_METHOD = "bidMethod";
    public static final String FIELD_DEMANDERS = "demanders";
    public static final String FIELD_CREATION_DATE_FROM = "creationDateFrom";
    public static final String FIELD_CREATION_DATE_TO = "creationDateTo";
    public static final String FIELD_PR_HEADER_ID = "prHeaderId";
    public static final String FIELD_PR_NUM = "prNum";
    public static final String FIELD_LINE_NUM = "lineNum";
    public static final String FIELD_PR_LINE_ID = "prLineId";
    public static final String FIELD_BUDGET_ACCOUNT = "budgetAccount";
    public static final String FIELD_BIDDING_MODE = "biddingMode";
    public static final String FIELD_AGENT = "agent";
    public static final String FIELD_PROJECT_AMOUNT = "projectAmount";
    public static final String FIELD_BID_AMOUNT = "bidAmount";
    public static final String FIELD_CONTRACT_AMOUNT = "contractAmount";
    public static final String FIELD_DE_PLAN_FIN_TIME = "dePlanFinTime";
    public static final String FIELD_DE_APPR_FIN_TIME = "deApprFinTime";
    public static final String FIELD_PLAN_FIN_VEN_TIME = "planFinVenTime";
    public static final String FIELD_PLAN_FIN_APPR_TIME = "planFinApprTime";
    public static final String FIELD_PLAN_FIN_ISSUE_TIME = "planFinIssueTime";
    public static final String FIELD_PLAN_FIN_BID_TIME = "planFinBidTime";
    public static final String FIELD_PLAN_FIN_CON_TIME = "planFinConTime";
    public static final String FIELD_REMARKS = "remarks";
    public static final String FIELD_ATTACHMENT = "attachment";
    public static final String FIELD_ADD_FLAG = "addFlag";
	public static final String FIELD_COMPANY_ID = "companyId";

    //
    // 业务方法(按public protected private顺序排列)
    // ------------------------------------------------------------------------------

    //
    // 数据库字段
    // ------------------------------------------------------------------------------


    @ApiModelProperty("表ID，主键，供其他表做外键")
    @Id
    @GeneratedValue
    private Long planId;
    @ApiModelProperty(value = "租户id",required = true)
    @NotNull
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


	//
    // 非数据库字段
    // ------------------------------------------------------------------------------

    //
    // getter/setter
    // ------------------------------------------------------------------------------

    /**
     * @return 表ID，主键，供其他表做外键
     */
	public Long getPlanId() {
		return planId;
	}

	public void setPlanId(Long planId) {
		this.planId = planId;
	}
    /**
     * @return 租户id
     */
	public Long getTenantId() {
		return tenantId;
	}

	public void setTenantId(Long tenantId) {
		this.tenantId = tenantId;
	}
    /**
     * @return 计划编号
     */
	public String getPlanNum() {
		return planNum;
	}

	public void setPlanNum(String planNum) {
		this.planNum = planNum;
	}
    /**
     * @return 状态
     */
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
    /**
     * @return 业态
     */
	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}
    /**
     * @return 公司
     */
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
    /**
     * @return 采购分类
     */
	public String getPrCategory() {
		return prCategory;
	}

	public void setPrCategory(String prCategory) {
		this.prCategory = prCategory;
	}
    /**
     * @return 采购方式
     */
	public String getPrWay() {
		return prWay;
	}

	public void setPrWay(String prWay) {
		this.prWay = prWay;
	}
    /**
     * @return 评标方法
     */
	public String getBidMethod() {
		return bidMethod;
	}

	public void setBidMethod(String bidMethod) {
		this.bidMethod = bidMethod;
	}
    /**
     * @return 需求人
     */
	public String getDemanders() {
		return demanders;
	}

	public void setDemanders(String demanders) {
		this.demanders = demanders;
	}
    /**
     * @return 创建时间从
     */
	public Date getCreationDateFrom() {
		return creationDateFrom;
	}

	public void setCreationDateFrom(Date creationDateFrom) {
		this.creationDateFrom = creationDateFrom;
	}
    /**
     * @return 创建时间至
     */
	public Date getCreationDateTo() {
		return creationDateTo;
	}

	public void setCreationDateTo(Date creationDateTo) {
		this.creationDateTo = creationDateTo;
	}
    /**
     * @return 采购申请头id
     */
	public Long getPrHeaderId() {
		return prHeaderId;
	}

	public void setPrHeaderId(Long prHeaderId) {
		this.prHeaderId = prHeaderId;
	}
    /**
     * @return 采购申请编号
     */
	public String getPrNum() {
		return prNum;
	}

	public void setPrNum(String prNum) {
		this.prNum = prNum;
	}
    /**
     * @return 行号
     */
	public String getLineNum() {
		return lineNum;
	}

	public void setLineNum(String lineNum) {
		this.lineNum = lineNum;
	}
    /**
     * @return 采购申请行id
     */
	public Long getPrLineId() {
		return prLineId;
	}

	public void setPrLineId(Long prLineId) {
		this.prLineId = prLineId;
	}
    /**
     * @return 预算科目
     */
	public String getBudgetAccount() {
		return budgetAccount;
	}

	public void setBudgetAccount(String budgetAccount) {
		this.budgetAccount = budgetAccount;
	}
    /**
     * @return 招采模式
     */
	public String getBiddingMode() {
		return biddingMode;
	}

	public void setBiddingMode(String biddingMode) {
		this.biddingMode = biddingMode;
	}
    /**
     * @return 经办人
     */
	public String getAgent() {
		return agent;
	}

	public void setAgent(String agent) {
		this.agent = agent;
	}
    /**
     * @return 立项金额（万元）
     */
	public BigDecimal getProjectAmount() {
		return projectAmount;
	}

	public void setProjectAmount(BigDecimal projectAmount) {
		this.projectAmount = projectAmount;
	}
    /**
     * @return 定标金额
     */
	public BigDecimal getBidAmount() {
		return bidAmount;
	}

	public void setBidAmount(BigDecimal bidAmount) {
		this.bidAmount = bidAmount;
	}
    /**
     * @return 合同金额
     */
	public BigDecimal getContractAmount() {
		return contractAmount;
	}

	public void setContractAmount(BigDecimal contractAmount) {
		this.contractAmount = contractAmount;
	}
    /**
     * @return 需求计划完成时间
     */
	public Date getDePlanFinTime() {
		return dePlanFinTime;
	}

	public void setDePlanFinTime(Date dePlanFinTime) {
		this.dePlanFinTime = dePlanFinTime;
	}
    /**
     * @return 需求审批完成时间
     */
	public Date getDeApprFinTime() {
		return deApprFinTime;
	}

	public void setDeApprFinTime(Date deApprFinTime) {
		this.deApprFinTime = deApprFinTime;
	}
    /**
     * @return 计划完成时间（供方入围）
     */
	public Date getPlanFinVenTime() {
		return planFinVenTime;
	}

	public void setPlanFinVenTime(Date planFinVenTime) {
		this.planFinVenTime = planFinVenTime;
	}
    /**
     * @return 计划完成时间（立项审批）
     */
	public Date getPlanFinApprTime() {
		return planFinApprTime;
	}

	public void setPlanFinApprTime(Date planFinApprTime) {
		this.planFinApprTime = planFinApprTime;
	}
    /**
     * @return 计划完成时间（发标时间）
     */
	public Date getPlanFinIssueTime() {
		return planFinIssueTime;
	}

	public void setPlanFinIssueTime(Date planFinIssueTime) {
		this.planFinIssueTime = planFinIssueTime;
	}
    /**
     * @return 计划完成时间（定标时间）
     */
	public Date getPlanFinBidTime() {
		return planFinBidTime;
	}

	public void setPlanFinBidTime(Date planFinBidTime) {
		this.planFinBidTime = planFinBidTime;
	}
    /**
     * @return 计划完成时间（合同完成时间）
     */
	public Date getPlanFinConTime() {
		return planFinConTime;
	}

	public void setPlanFinConTime(Date planFinConTime) {
		this.planFinConTime = planFinConTime;
	}
    /**
     * @return 备注
     */
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
    /**
     * @return 附件
     */
	public String getAttachment() {
		return attachment;
	}

	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}
    /**
     * @return 是否临时新增
     */
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
}
