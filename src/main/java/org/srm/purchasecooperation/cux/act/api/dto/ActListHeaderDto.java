package org.srm.purchasecooperation.cux.act.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

/**
 * @author lu.cheng01@hand-china.com
 * @description bpm验收单头字段
 * @date 2021/3/31 16:24
 * @version:1.0
 */
@JsonInclude
public class ActListHeaderDto {
    /*验收单标题*/
    private String acceptListNum;
    /*验收人*/
    private String acceptOrName;
    /*合同编码*/
    private String pcNum;
    /*合同名称*/
    private String pcName;
    /*验收单编号*/
    private String Title;
    /*费用承担公司*/
    private String companyId;
    /*供应商名称 */
    private String supplierCompanyId;
    /*验收类型*/
    private String acceptListType;

    /**
     * 验收行数据
     */
    private List<ActListLinesDto> YSDDH;

    /**
     * 附件链接
     */
    private List<ActListFilesDto> URL;

    public List<ActListLinesDto> getYSDDH() {
        return YSDDH;
    }

    public void setYSDDH(List<ActListLinesDto> YSDDH) {
        this.YSDDH = YSDDH;
    }

    public List<ActListFilesDto> getURL() {
        return URL;
    }

    public void setURL(List<ActListFilesDto> URL) {
        this.URL = URL;
    }

    public String getAcceptListNum() {
        return acceptListNum;
    }

    public void setAcceptListNum(String acceptListNum) {
        this.acceptListNum = acceptListNum;
    }

    public String getAcceptOrName() {
        return acceptOrName;
    }

    public void setAcceptOrName(String acceptOrName) {
        this.acceptOrName = acceptOrName;
    }

    public String getPcNum() {
        return pcNum;
    }

    public void setPcNum(String pcNum) {
        this.pcNum = pcNum;
    }

    public String getPcName() {
        return pcName;
    }

    public void setPcName(String pcName) {
        this.pcName = pcName;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getSupplierCompanyId() {
        return supplierCompanyId;
    }

    public void setSupplierCompanyId(String supplierCompanyId) {
        this.supplierCompanyId = supplierCompanyId;
    }

    public String getAcceptListType() {
        return acceptListType;
    }

    public void setAcceptListType(String acceptListType) {
        this.acceptListType = acceptListType;
    }

    public String getAcceptDate() {
        return acceptDate;
    }

    public void setAcceptDate(String acceptDate) {
        this.acceptDate = acceptDate;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public String getAcceptDetails() {
        return acceptDetails;
    }

    public void setAcceptDetails(String acceptDetails) {
        this.acceptDetails = acceptDetails;
    }

    /*验收日期*/
    private String acceptDate;
    /*验收进度*/
    private String speed;
    /*验收详细情况*/
    private String acceptDetails;

}
