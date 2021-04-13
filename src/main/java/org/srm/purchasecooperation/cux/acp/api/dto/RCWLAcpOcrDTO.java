package org.srm.purchasecooperation.cux.acp.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author lu.cheng01@hand-china.com
 * @description 大慧云加密URL链接 base64加密后的字符串
 * @date 2021/4/8 15:25
 * @version:1.0
 */
@JsonInclude
public class RCWLAcpOcrDTO {
    @ApiModelProperty("大慧云加密URL")
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
