package org.srm.purchasecooperation.cux.acp.domain.entity;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

/**
 * @author lu.cheng01@hand-china.com
 * @description 发票信息
 * @date 2021/4/8 20:01
 * @version:1.0
 */
public class RCWLAcpInvoiceHeaderData {

    @ApiModelProperty("业务单据号")
    private String documentNumber;

    @ApiModelProperty("状态")
    private String status;

    @ApiModelProperty("税务发票行数")
    private int taxCount;

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTaxCount() {
        return taxCount;
    }

    public void setTaxCount(int taxCount) {
        this.taxCount = taxCount;
    }
}