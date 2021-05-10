package org.srm.purchasecooperation.cux.act.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author lu.cheng01@hand-china.com
 * @description bpm验收单头字段
 * @date 2021/3/31 16:24
 * @version:1.0
 */
@JsonInclude
public class ActListHeaderDto {

    @ApiModelProperty("流程id")
    @JsonIgnore
    private String attributeVarchar19;
    /*验收单标题*/
    @JsonProperty("TITLE")
    private String trxNum;
    /*验收人*/
    @JsonProperty("ACCEPTORNAME")
    private String acceptOrName;
    /*合同编码*/
    @JsonProperty("PCNUM")
    private String pcNum;
    /*合同名称*/
    @JsonProperty("PCNAME")
    private String pcName;
    /*验收单编号*/

    /*费用承担公司*/
    @JsonProperty("COMPANYID")
    private String companyId;
    /*供应商名称 */
    @JsonProperty("SUPPLIERCOMPANYID")
    private String supplierCompanyId;
    /*验收类型*/
    @JsonProperty("ACCEPTLISTTYPE")
    private String acceptListType;
    /*验收日期*/
    @JsonProperty("ACCEPTDATE")
    private String acceptDate;
    /*验收进度*/
    @JsonProperty("SPEED")
    private String speed;
    /*验收详细情况*/
    @JsonProperty("ACCEPTDETAILS")
    private String acceptDetails;

    @ApiModelProperty("验收单+合同名称+验收人")
    @JsonProperty("FSubject")
    private String fSubject;

    @JsonProperty("URL_MX")
    @ApiModelProperty("甄云链接")
    private String urlMX;


    /**
     * 验收行数据
     */
    @JsonProperty("YSDDH")
    private List<ActListLinesDto> ysddh;

    /**
     * 附件链接
     */
    @JsonProperty("ATTACHMENTS1")
    private List<ActListFilesDto> url;

    public List<ActListLinesDto> getYSDDH() {
        return ysddh;
    }

    public String getTrxNum() {
        return trxNum;
    }

    public void setTrxNum(String trxNum) {
        this.trxNum = trxNum;
    }

    public String getfSubject() {
        return fSubject;
    }

    public void setfSubject(String fSubject) {
        this.fSubject = fSubject;
    }

    public String getUrlMX() {
        return urlMX;
    }

    public void setUrlMX( String urlMX ) {
        this.urlMX = urlMX;
    }

    public void setYSDDH( List<ActListLinesDto> ysddh ) {
        this.ysddh = ysddh;
    }

    public List<ActListFilesDto> getURL() {
        return url;
    }

    public void setURL( List<ActListFilesDto> url ) {
        this.url = url;
    }

    public String getAcceptOrName() {
        return acceptOrName;
    }

    public void setAcceptOrName( String acceptOrName ) {
        this.acceptOrName = acceptOrName;
    }

    public String getPcNum() {
        return pcNum;
    }

    public void setPcNum( String pcNum ) {
        this.pcNum = pcNum;
    }

    public String getPcName() {
        return pcName;
    }

    public void setPcName( String pcName ) {
        this.pcName = pcName;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId( String companyId ) {
        this.companyId = companyId;
    }

    public String getSupplierCompanyId() {
        return supplierCompanyId;
    }

    public void setSupplierCompanyId( String supplierCompanyId ) {
        this.supplierCompanyId = supplierCompanyId;
    }

    public String getAcceptListType() {
        return acceptListType;
    }

    public void setAcceptListType( String acceptListType ) {
        this.acceptListType = acceptListType;
    }

    public String getAcceptDate() {
        return acceptDate;
    }

    public void setAcceptDate( String acceptDate ) {
        this.acceptDate = acceptDate;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed( String speed ) {
        this.speed = speed;
    }

    public String getAcceptDetails() {
        return acceptDetails;
    }

    public void setAcceptDetails( String acceptDetails ) {
        this.acceptDetails = acceptDetails;
    }

    public String getAttributeVarchar19() {
        return attributeVarchar19;
    }

    public void setAttributeVarchar19( String attributeVarchar19 ) {
        this.attributeVarchar19 = attributeVarchar19;
    }
}
