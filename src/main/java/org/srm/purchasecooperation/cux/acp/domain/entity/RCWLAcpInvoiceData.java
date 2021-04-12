package org.srm.purchasecooperation.cux.acp.domain.entity;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

/**
 * @author lu.cheng01@hand-china.com
 * @description 发票调用传参
 * @date 2021/4/8 20:01
 * @version:1.0
 */
public class RCWLAcpInvoiceData {
    @ApiModelProperty("单据类型:01.供方形式发票，02.购方形式发票，03.付款申请单")
    @NotNull
    private String documentType;

    @ApiModelProperty("业务单据号")
    @NotNull
    private String documentNumber;

    @ApiModelProperty("来源")
    @NotNull
    private String systemSource;

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
