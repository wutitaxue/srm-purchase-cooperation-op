package org.srm.purchasecooperation.cux.pr.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @description:
 * @author: bin.zhang
 * @createDate: 2021/4/27 17:12
 */
public class PlanHeaderToBpmDTO {

   @ApiModelProperty(value = "标题")
   @JsonProperty("FSubject")
   private  String FSubject;

   @ApiModelProperty(value = "管理公司")
   @JsonProperty("COMPANY")
   private  String company;

   @ApiModelProperty(value = "是否临时新增")
   @JsonProperty("ADDFLAG")
   private  String addFlag;

   @ApiModelProperty(value = "本月累计申请数量")
   @JsonProperty("THISMONTHNUMBER")
   private  String thisMonthNumber;

   @ApiModelProperty(value = "申请数量")
   @JsonProperty("NUMBER")
   private  String number;

   @ApiModelProperty(value = "申请金额")
   @JsonProperty("MONEY")
   private  String money;

   @ApiModelProperty(value = "本月完成数量")
   @JsonProperty("LASTMONTHNUMBER")
   private  String lastMonthNumber;

   @ApiModelProperty(value = "上月完成数量")
   @JsonProperty("LASTMONTHCOMPLETE")
   private  String lastMonthComplete;

   @ApiModelProperty(value = "采购计划信息")
   List CGJHXX;

}
