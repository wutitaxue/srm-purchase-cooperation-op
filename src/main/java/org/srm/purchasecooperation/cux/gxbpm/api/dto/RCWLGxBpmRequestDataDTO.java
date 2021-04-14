package org.srm.purchasecooperation.cux.gxbpm.api.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author lu.cheng01@hand-china.com
 * @description
 * @date 2021/4/12 21:24
 * @version:1.0
 */
@JsonInclude
@ToString
@Data
public class RCWLGxBpmRequestDataDTO {

    @JsonProperty("I_REQUEST")
    private RCWLGxBpmIRequestDTO rcwlGxBpmIRequestDTO;

    @JsonProperty("I_REQUEST")
    public RCWLGxBpmIRequestDTO getRcwlGxBpmIRequestDTO() {
        return rcwlGxBpmIRequestDTO;
    }

    public void setRcwlGxBpmIRequestDTO(RCWLGxBpmIRequestDTO rcwlGxBpmIRequestDTO) {
        this.rcwlGxBpmIRequestDTO = rcwlGxBpmIRequestDTO;
    }
}
