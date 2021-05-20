package org.srm.purchasecooperation.cux.acp.api.dto;

/**
 * @author lu.cheng01@hand-china.com
 * @description 大象接口返回类封装
 * @date 2021/4/10 10:42
 * @version:1.0
 */
public class RCWLAcpInvoiceElephantResponseDto {
    private RCWLAcpInvoiceElephantReturnInfoDTO returnInfo;

    public RCWLAcpInvoiceElephantReturnInfoDTO getReturnInfo() {
        return returnInfo;
    }

    public void setReturnInfo(RCWLAcpInvoiceElephantReturnInfoDTO returnInfo) {
        this.returnInfo = returnInfo;
    }
}
