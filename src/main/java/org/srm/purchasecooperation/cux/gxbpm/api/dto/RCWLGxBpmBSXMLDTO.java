package org.srm.purchasecooperation.cux.gxbpm.api.dto;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import org.apache.poi.ss.formula.functions.T;

/**
 * @author lu.cheng01@hand-china.com
 * @description
 * @date 2021/4/12 20:49
 * @version:1.0
 */
@JsonInclude
@Data
@ToString
public class RCWLGxBpmBSXMLDTO {

    @ApiModelProperty("动态data")
    @JsonProperty("DATA")
    private JSONObject data;

    @JsonProperty("DATA")
    public JSONObject getDATA() {
        return data;
    }

    public void setDATA(JSONObject data) {
        this.data = data;
    }

}
