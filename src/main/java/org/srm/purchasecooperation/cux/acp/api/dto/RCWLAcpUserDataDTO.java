package org.srm.purchasecooperation.cux.acp.api.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author lu.cheng01@hand-china.com
 * @description 发票人员数据
 * @date 2021/4/8 15:29
 * @version:1.0
 */
@JsonInclude
@Data
@ToString
public class RCWLAcpUserDataDTO {
    @ApiModelProperty("授权ID:大象提供")
    @JSONField(ordinal = 1)
    private String appSecId;

    @ApiModelProperty("员工号:用户唯一标识")
    @JSONField(ordinal = 2)
    private String userId;

    @ApiModelProperty("单据类型:01.供方形式发票，02.购方形式发票，03.付款申请单")
    @JSONField(ordinal = 3)
    private String documentType;

    @ApiModelProperty("业务单据号:01/02形式发票号，03付款申请单号")
    @JSONField(ordinal = 4)
    private String documentNumber;

    @ApiModelProperty("来源:CGXT_SUPPLIER：供应商;CGXT_SUNAC：融创内部员工")
    @JSONField(ordinal = 5)
    private String systemSource;

    public String getAppSecId() {
        return appSecId;
    }

    public void setAppSecId(String appSecId) {
        this.appSecId = appSecId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public String getSystemSource() {
        return systemSource;
    }

    public void setSystemSource(String systemSource) {
        this.systemSource = systemSource;
    }
}
