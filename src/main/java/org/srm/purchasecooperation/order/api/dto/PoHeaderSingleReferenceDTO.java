package org.srm.purchasecooperation.order.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.choerodon.mybatis.domain.AuditDomain;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import org.hzero.starter.keyencrypt.core.Encrypt;
import org.springframework.format.annotation.DateTimeFormat;

public class PoHeaderSingleReferenceDTO extends AuditDomain {
    @ApiModelProperty("申请编号")
    private String prNum;
    @ApiModelProperty("标题")
    private String title;
    @ApiModelProperty("申请日期从")
    @JsonFormat(
            pattern = "yyyy-MM-dd"
    )
    @DateTimeFormat(
            pattern = "yyyy-MM-dd"
    )
    private Date requestDateFrom;
    @ApiModelProperty("申请日期至")
    @JsonFormat(
            pattern = "yyyy-MM-dd"
    )
    @DateTimeFormat(
            pattern = "yyyy-MM-dd"
    )
    private Date requestDateTo;
    @ApiModelProperty("业务实体ID")
    @Encrypt
    private Long ouId;
    @ApiModelProperty("采购组织ID")
    @Encrypt
    private Long purchaseOrgId;
    @ApiModelProperty("采购员ID")
    private Long purchaseAgentId;
    @ApiModelProperty("公司ID")
    @Encrypt
    private Long companyId;
    @ApiModelProperty("申请人ID")
    private Long requestedBy;
    @ApiModelProperty("供应商ID")
    @Encrypt
    private Long supplierId;
    @ApiModelProperty("供应商公司ID")
    @Encrypt
    private Long supplierCompanyId;
    @ApiModelProperty("单据来源")
    private String sourceCode;
    @ApiModelProperty("加急标识")
    private Integer urgentFlag;
    @ApiModelProperty("采购申请类型ID")
    private Long prTypeId;

    public PoHeaderSingleReferenceDTO() {
    }

    public Long getPrTypeId() {
        return prTypeId;
    }

    public void setPrTypeId(Long prTypeId) {
        this.prTypeId = prTypeId;
    }

    public Long getSupplierCompanyId() {
        return this.supplierCompanyId;
    }

    public void setSupplierCompanyId(Long supplierCompanyId) {
        this.supplierCompanyId = supplierCompanyId;
    }

    public String getPrNum() {
        return this.prNum;
    }

    public void setPrNum(String prNum) {
        this.prNum = prNum;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getRequestDateFrom() {
        return this.requestDateFrom;
    }

    public void setRequestDateFrom(Date requestDateFrom) {
        this.requestDateFrom = requestDateFrom;
    }

    public Date getRequestDateTo() {
        return this.requestDateTo;
    }

    public void setRequestDateTo(Date requestDateTo) {
        this.requestDateTo = requestDateTo;
    }

    public Long getOuId() {
        return this.ouId;
    }

    public void setOuId(Long ouId) {
        this.ouId = ouId;
    }

    public Long getPurchaseOrgId() {
        return this.purchaseOrgId;
    }

    public void setPurchaseOrgId(Long purchaseOrgId) {
        this.purchaseOrgId = purchaseOrgId;
    }

    public Long getPurchaseAgentId() {
        return this.purchaseAgentId;
    }

    public void setPurchaseAgentId(Long purchaseAgentId) {
        this.purchaseAgentId = purchaseAgentId;
    }

    public Long getCompanyId() {
        return this.companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getRequestedBy() {
        return this.requestedBy;
    }

    public void setRequestedBy(Long requestedBy) {
        this.requestedBy = requestedBy;
    }

    public Long getSupplierId() {
        return this.supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public String getSourceCode() {
        return this.sourceCode;
    }

    public void setSourceCode(String sourceCode) {
        this.sourceCode = sourceCode;
    }

    public Integer getUrgentFlag() {
        return this.urgentFlag;
    }

    public void setUrgentFlag(Integer urgentFlag) {
        this.urgentFlag = urgentFlag;
    }

    public String toString() {
        return "PoHeaderSingleReferenceDTO{prNum='" + this.prNum + '\'' + ", title='" + this.title + '\'' + ", requestDateFrom=" + this.requestDateFrom + ", requestDateTo=" + this.requestDateTo + ", ouId=" + this.ouId + ", purchaseOrgId=" + this.purchaseOrgId + ", purchaseAgentId=" + this.purchaseAgentId + ", companyId=" + this.companyId + ", requestedBy=" + this.requestedBy + ", supplierId=" + this.supplierId + ", sourceCode='" + this.sourceCode + '\'' + '}';
    }
}
