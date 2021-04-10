package org.srm.purchasecooperation.cux.api.dto;


import io.swagger.annotations.ApiModelProperty;

import java.util.Date;


/**
 * @author bin.zhang
 */
public class HeaderQueryDTO {
    @ApiModelProperty(value = "采购计划id")
    private Long planId;
    @ApiModelProperty(value = "租户id")
    private Long tenantId;
    @ApiModelProperty(value = "计划编号")
    private String planNum;
    @ApiModelProperty(value = "状态")
    private String state;
    @ApiModelProperty(value = "业态")
    private String format;
    @ApiModelProperty(value = "公司")
    private String companyName;
    @ApiModelProperty(value = "采购类别")
    private String prCategory;
    @ApiModelProperty(value = "采购方式")
    private String prWay;
    @ApiModelProperty(value = "招采模式")
    private String bidMethod;
    @ApiModelProperty(value = "需求人")
    private String demanders;
    @ApiModelProperty(value = "创建日期从")
    private Date creationDateFrom;
    @ApiModelProperty(value = "创建日期至")
    private Date creationDateTo;
    @ApiModelProperty(value = "公司id")
    private Long companyId;
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

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    @Override
    public String toString() {
        return "HeaderQueryDTO{" +
                "planId=" + planId +
                ", tenantId=" + tenantId +
                ", planNum='" + planNum + '\'' +
                ", state='" + state + '\'' +
                ", format='" + format + '\'' +
                ", companyName='" + companyName + '\'' +
                ", prCategory='" + prCategory + '\'' +
                ", prWay='" + prWay + '\'' +
                ", bidMethod='" + bidMethod + '\'' +
                ", demanders='" + demanders + '\'' +
                ", creationDateFrom=" + creationDateFrom +
                ", creationDateTo=" + creationDateTo +
                ", companyId=" + companyId +
                '}';
    }
}