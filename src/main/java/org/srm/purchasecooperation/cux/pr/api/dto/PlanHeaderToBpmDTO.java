package org.srm.purchasecooperation.cux.pr.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

/**
 * @description:
 * @author: bin.zhang
 * @createDate: 2021/4/27 17:12
 */
public class PlanHeaderToBpmDTO {
   @JsonProperty("FSubject")
   @ApiModelProperty(value = "标题")
   private  String FSubject;
}
