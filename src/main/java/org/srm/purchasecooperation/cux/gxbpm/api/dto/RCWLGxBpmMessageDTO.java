package org.srm.purchasecooperation.cux.gxbpm.api.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author lu.cheng01@hand-china.com
 * @description
 * @date 2021/4/12 20:57
 * @version:1.0
 */
@JsonInclude
@Data
@ToString
public class RCWLGxBpmMessageDTO {

    @JsonProperty("REQ_ITEM")
    private RCWLGxBpmReqItemDTO reqItem;

    @JsonProperty("REQ_ITEM")
    public RCWLGxBpmReqItemDTO getReqItem() {
        return reqItem;
    }

    public void setReqItem(RCWLGxBpmReqItemDTO reqItem) {
        this.reqItem = reqItem;
    }
}
