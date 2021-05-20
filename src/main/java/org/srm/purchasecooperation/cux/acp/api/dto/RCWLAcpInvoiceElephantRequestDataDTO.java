package org.srm.purchasecooperation.cux.acp.api.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.ToString;

/**
 * @author lu.cheng01@hand-china.com
 * @description 发票修改状态大象的请求数据封装
 * @date 2021/4/9 14:05
 * @version:1.0
 */
@JsonInclude
@Data
@ToString
public class RCWLAcpInvoiceElephantRequestDataDTO {

    @JSONField(ordinal = 1)
    private RCWLAcpInvoiceElephantAuthorizeDTO authorize;

    @JSONField(ordinal = 2)
    private RCWLAcpInvoiceElephantGlobalInfoDTO globalInfo;

    @JSONField(ordinal = 3)
    private RCWLAcpInvoiceElephantDataDTO data;

    public RCWLAcpInvoiceElephantAuthorizeDTO getAuthorize() {
        return authorize;
    }

    public void setAuthorize(RCWLAcpInvoiceElephantAuthorizeDTO authorize) {
        this.authorize = authorize;
    }

    public RCWLAcpInvoiceElephantGlobalInfoDTO getGlobalInfo() {
        return globalInfo;
    }

    public void setGlobalInfo(RCWLAcpInvoiceElephantGlobalInfoDTO globalInfo) {
        this.globalInfo = globalInfo;
    }

    public RCWLAcpInvoiceElephantDataDTO getData() {
        return data;
    }

    public void setData(RCWLAcpInvoiceElephantDataDTO data) {
        this.data = data;
    }
}
