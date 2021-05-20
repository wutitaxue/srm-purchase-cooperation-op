package org.srm.purchasecooperation.cux.acp.api.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.springframework.integration.annotation.Default;
import org.srm.purchasecooperation.cux.acp.infra.constant.RCWLAcpConstant;

/**
 * @author lu.cheng01@hand-china.com
 * @description 大象的 authorize 信息
 * @date 2021/4/9 13:39
 * @version:1.0
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@ToString
public class RCWLAcpInvoiceElephantAuthorizeDTO {

    @ApiModelProperty("签名数据")
    @JSONField(ordinal = 1)
    private String appSecId;

    @ApiModelProperty("签名数据")
    @JSONField(ordinal = 2)
    private String appSec;

    public String getAppSecId() {
        return appSecId;
    }

    public void setAppSecId(String appSecId) {
        this.appSecId = appSecId;
    }

    public String getAppSec() {
        return appSec;
    }

    public void setAppSec(String appSec) {
        this.appSec = appSec;
    }
}
