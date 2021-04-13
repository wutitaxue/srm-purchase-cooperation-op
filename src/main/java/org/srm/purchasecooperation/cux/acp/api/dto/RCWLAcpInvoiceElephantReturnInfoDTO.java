package org.srm.purchasecooperation.cux.acp.api.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author lu.cheng01@hand-china.com
 * @description 发票大象接口返回信息
 * @date 2021/4/10 10:40
 * @version:1.0
 */
public class RCWLAcpInvoiceElephantReturnInfoDTO {

    @ApiModelProperty("返回编码")
    private String returnCode;

    @ApiModelProperty("返回信息")
    private String returnMessage;

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public String getReturnMessage() {
        return returnMessage;
    }

    public void setReturnMessage(String returnMessage) {
        this.returnMessage = returnMessage;
    }
}
