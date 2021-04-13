package org.srm.purchasecooperation.cux.pr.api.dto;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @description:预算占用/释放接口头
 * @author: bin.zhang
 * @createDate: 2021/4/9 16:25
 */
public class RCWLItfPrHeaderDTO {
    @ApiModelProperty(value = "授权")
    private String token;
    @ApiModelProperty(value = "数据类型")
    private String type;
    @ApiModelProperty(value = "数据定义标识")
    private String definecode;
    @ApiModelProperty(value = "同步数据列表")
    RCWLItfPrDataDTO data;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDefinecode() {
        return definecode;
    }

    public void setDefinecode(String definecode) {
        this.definecode = definecode;
    }

    public RCWLItfPrDataDTO getData() {
        return data;
    }

    public void setData(RCWLItfPrDataDTO data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "RCWLItfPrHeaderDTO{" +
                "token='" + token + '\'' +
                ", type='" + type + '\'' +
                ", definecode='" + definecode + '\'' +
                ", data=" + data +
                '}';
    }
}
