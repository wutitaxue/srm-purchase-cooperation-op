package org.srm.purchasecooperation.cux.pr.api.dto;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.List;

/**
 * @description:
 * @author:yuanping.zhang
 * @createTime:2021/4/19 20:53
 * @version:1.0
 */
public class PrToBpmDTO implements Serializable {
    @JSONField(name = "FSubject")
    private String subject;
    @JSONField(name = "TYPE")
    private String typeStr;
    @JSONField(name = "PRNUM")
    private String prNum;
    @JSONField(name = "TITLE")
    private String title;
    @JSONField(name = "CREATEBYNAME")
    private String employeeName;
    @JSONField(name = "REQUESTEDBY")
    private String prRequestedName;
    @JSONField(name = "COMPANYID")
    private String companyName;
    @JSONField(name = "PURCHASEORGID")
    private String purchaseOrgName;
    @JSONField(name = "PROJECT_STAGING")
    private String projectStaging;
    @JSONField(name = "AMOUNT")
    private String amount;
    @JSONField(name = "PRTYPEID")
    private String prTypeName;
    @JSONField(name = "FORMAT")
    private String format;
    @JSONField(name = "BIDDING_MODE")
    private String biddingMode;

    @JSONField(name = "CREATIONDATE")
    private String creationDate;
    @JSONField(name = "REMARK")
    private String remark;

    /**
     * 采购员
     */
    @JSONField(name = "PURCHASE_AGENT")
    private String purchaseAgent;
    /**
     * 当年预算总和
     */
    @JSONField(name = "PRESENTBUDGETSUM")
    private String presentBudgetSum;


    @JSONField(name = "URL_MX")
    private String zYunUrl;

    @JSONField(name = "XQXX")
    private List<PrToBpmLineDTO> prToBpmLineDTOList;
    @JSONField(name = "ATTACHMENTS1")
    private List<PrToBpmFileDTO> prToBpmFileDTOList;

    public String getPurchaseAgent() {
        return purchaseAgent;
    }

    public void setPurchaseAgent(String purchaseAgent) {
        this.purchaseAgent = purchaseAgent;
    }

    public String getPresentBudgetSum() {
        return presentBudgetSum;
    }

    public void setPresentBudgetSum(String presentBudgetSum) {
        this.presentBudgetSum = presentBudgetSum;
    }

    public String getzYunUrl() {
        return zYunUrl;
    }

    public void setzYunUrl(String zYunUrl) {
        this.zYunUrl = zYunUrl;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTypeStr() {
        return typeStr;
    }

    public void setTypeStr(String typeStr) {
        this.typeStr = typeStr;
    }

    public String getPrNum() {
        return prNum;
    }

    public void setPrNum(String prNum) {
        this.prNum = prNum;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getPrRequestedName() {
        return prRequestedName;
    }

    public void setPrRequestedName(String prRequestedName) {
        this.prRequestedName = prRequestedName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getPurchaseOrgName() {
        return purchaseOrgName;
    }

    public void setPurchaseOrgName(String purchaseOrgName) {
        this.purchaseOrgName = purchaseOrgName;
    }

    public String getProjectStaging() {
        return projectStaging;
    }

    public void setProjectStaging(String projectStaging) {
        this.projectStaging = projectStaging;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPrTypeName() {
        return prTypeName;
    }

    public void setPrTypeName(String prTypeName) {
        this.prTypeName = prTypeName;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getBiddingMode() {
        return biddingMode;
    }

    public void setBiddingMode(String biddingMode) {
        this.biddingMode = biddingMode;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<PrToBpmLineDTO> getPrToBpmLineDTOList() {
        return prToBpmLineDTOList;
    }

    public void setPrToBpmLineDTOList(List<PrToBpmLineDTO> prToBpmLineDTOList) {
        this.prToBpmLineDTOList = prToBpmLineDTOList;
    }

    public List<PrToBpmFileDTO> getPrToBpmFileDTOList() {
        return prToBpmFileDTOList;
    }

    public void setPrToBpmFileDTOList(List<PrToBpmFileDTO> prToBpmFileDTOList) {
        this.prToBpmFileDTOList = prToBpmFileDTOList;
    }
}
