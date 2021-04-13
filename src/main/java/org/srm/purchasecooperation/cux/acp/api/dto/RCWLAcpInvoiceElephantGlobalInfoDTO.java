package org.srm.purchasecooperation.cux.acp.api.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author lu.cheng01@hand-china.com
 * @description 大象慧云 globalInfo 信息
 * @date 2021/4/9 13:45
 * @version:1.0
 */
@JsonInclude
@Data
@ToString
public class RCWLAcpInvoiceElephantGlobalInfoDTO {

    @ApiModelProperty("授权ID")
    @JSONField(ordinal = 1)
    private String appId;

    @ApiModelProperty("版本号")
    @JSONField(ordinal = 2)
    private String version;

    @ApiModelProperty("接口编码：INVOICE_LIST_QUERY；获取发票数据\n" +
            "REIMBURSE_UPDATE；报销状态更新\n" +
            "GET_IMAGE；获取图片\n" +
            "APPROVAL_STATE_UPDATE；审批状态更新接口")
    @JSONField(ordinal = 3)
    private String interfaceCode;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getInterfaceCode() {
        return interfaceCode;
    }

    public void setInterfaceCode(String interfaceCode) {
        this.interfaceCode = interfaceCode;
    }
}
