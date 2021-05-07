package org.srm.purchasecooperation.cux.act.api.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author lu.cheng01@hand-china.com
 * @description
 * @date 2021/5/6 21:20
 * @version:1.0
 */
public class RcwlBpmUrlDto {
    @ApiModelProperty("bpm地址")
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl( String url ) {
        this.url = url;
    }
}
