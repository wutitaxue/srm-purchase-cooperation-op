package org.srm.purchasecooperation.cux.gxbpm.api.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author lu.cheng01@hand-china.com
 * @description
 * @date 2021/4/12 21:20
 * @version:1.0
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@ToString
public class RCWLGxBpmIRequestDTO {

    @ApiModelProperty("")
    @JsonProperty("REQ_BASEINFO")
    private RCWLGxBpmReqBaseInfoDTO reqBaseInfo;

    @ApiModelProperty("")
    @JsonProperty("REQ_BASEINFO")
    private RCWLGxBpmMessageDTO message;

    @JsonProperty("REQ_BASEINFO")
    public RCWLGxBpmReqBaseInfoDTO getReqBaseInfo() {
        return reqBaseInfo;
    }

    public void setReqBaseInfo(RCWLGxBpmReqBaseInfoDTO reqBaseInfo) {
        this.reqBaseInfo = reqBaseInfo;
    }

    @JsonProperty("MESSAGE")
    public RCWLGxBpmMessageDTO getiRequest() {
        return message;
    }

    public void setiMessage(RCWLGxBpmMessageDTO iRequest) {
        this.message = iRequest;
    }
}
