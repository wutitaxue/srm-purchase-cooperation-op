package org.srm.purchasecooperation.order.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.choerodon.mybatis.domain.AuditDomain;
import java.util.Date;
import org.hzero.boot.platform.lov.annotation.LovValue;
import org.hzero.starter.keyencrypt.core.Encrypt;
import org.springframework.format.annotation.DateTimeFormat;

public class PoHeaderSingleReferenceVO extends AuditDomain {
    @Encrypt
    private Long prHeaderId;
    private String prNum;
    private String title;
    @JsonFormat(
            pattern = "yyyy-MM-dd"
    )
    @DateTimeFormat(
            pattern = "yyyy-MM-dd"
    )
    private Date requestDate;
    private String ouName;
    private String organizationName;
    private String purchaseAgentName;
    private String companyName;
    private String requestedName;
    @LovValue(
            value = "SPRM.SRC_PLATFORM",
            meaningField = "prSourcePlatformMeaning"
    )
    private String prSourcePlatform;
    private String prSourcePlatformMeaning;
    private String supplierName;
    private String supplierCompanyName;
    private Integer urgentFlag;
    private Date urgentDate;
    private Long prTypeId;

    public Long getPrTypeId() {
        return prTypeId;
    }

    public void setPrTypeId(Long prTypeId) {
        this.prTypeId = prTypeId;
    }

    public PoHeaderSingleReferenceVO() {
    }

    public Integer getUrgentFlag() {
        return this.urgentFlag;
    }

    public void setUrgentFlag(Integer urgentFlag) {
        this.urgentFlag = urgentFlag;
    }

    public Date getUrgentDate() {
        return this.urgentDate;
    }

    public void setUrgentDate(Date urgentDate) {
        this.urgentDate = urgentDate;
    }

    public String getSupplierName() {
        return this.supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getSupplierCompanyName() {
        return this.supplierCompanyName;
    }

    public void setSupplierCompanyName(String supplierCompanyName) {
        this.supplierCompanyName = supplierCompanyName;
    }

    public String getPrSourcePlatformMeaning() {
        return this.prSourcePlatformMeaning;
    }

    public void setPrSourcePlatformMeaning(String prSourcePlatformMeaning) {
        this.prSourcePlatformMeaning = prSourcePlatformMeaning;
    }

    public Long getPrHeaderId() {
        return this.prHeaderId;
    }

    public void setPrHeaderId(Long prHeaderId) {
        this.prHeaderId = prHeaderId;
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

    public Date getRequestDate() {
        return this.requestDate;
    }

    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }

    public String getOuName() {
        return this.ouName;
    }

    public void setOuName(String ouName) {
        this.ouName = ouName;
    }

    public String getPurchaseAgentName() {
        return this.purchaseAgentName;
    }

    public void setPurchaseAgentName(String purchaseAgentName) {
        this.purchaseAgentName = purchaseAgentName;
    }

    public String getCompanyName() {
        return this.companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getRequestedName() {
        return this.requestedName;
    }

    public void setRequestedName(String requestedName) {
        this.requestedName = requestedName;
    }

    public String getOrganizationName() {
        return this.organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getPrSourcePlatform() {
        return this.prSourcePlatform;
    }

    public void setPrSourcePlatform(String prSourcePlatform) {
        this.prSourcePlatform = prSourcePlatform;
    }
}
