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
   private  String title;

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
   @JsonProperty("CGJHXX")
   List<PlanHeaderInfoToBpmDTO> cgjhxx;

   @ApiModelProperty(value = "附件信息")
   @JsonProperty("ATTACHMENTS1")
   List<PlanHeaderAttachementToBpmDTO> attachments1;

   public String getTitle() {
      return title;
   }

   public void setTitle(String title) {
      this.title = title;
   }

   public String getCompany() {
      return company;
   }

   public void setCompany(String company) {
      this.company = company;
   }

   public String getAddFlag() {
      return addFlag;
   }

   public void setAddFlag(String addFlag) {
      this.addFlag = addFlag;
   }

   public String getThisMonthNumber() {
      return thisMonthNumber;
   }

   public void setThisMonthNumber(String thisMonthNumber) {
      this.thisMonthNumber = thisMonthNumber;
   }

   public String getNumber() {
      return number;
   }

   public void setNumber(String number) {
      this.number = number;
   }

   public String getMoney() {
      return money;
   }

   public void setMoney(String money) {
      this.money = money;
   }

   public String getLastMonthNumber() {
      return lastMonthNumber;
   }

   public void setLastMonthNumber(String lastMonthNumber) {
      this.lastMonthNumber = lastMonthNumber;
   }

   public String getLastMonthComplete() {
      return lastMonthComplete;
   }

   public void setLastMonthComplete(String lastMonthComplete) {
      this.lastMonthComplete = lastMonthComplete;
   }

   public List<PlanHeaderInfoToBpmDTO> getCgjhxx() {
      return cgjhxx;
   }

   public void setCgjhxx(List<PlanHeaderInfoToBpmDTO> cgjhxx) {
      this.cgjhxx = cgjhxx;
   }

   public List<PlanHeaderAttachementToBpmDTO> getAttachments1() {
      return attachments1;
   }

   public void setAttachments1(List<PlanHeaderAttachementToBpmDTO> attachments1) {
      this.attachments1 = attachments1;
   }

   @Override
   public String toString() {
      return "PlanHeaderToBpmDTO{" +
              "title='" + title + '\'' +
              ", company='" + company + '\'' +
              ", addFlag='" + addFlag + '\'' +
              ", thisMonthNumber='" + thisMonthNumber + '\'' +
              ", number='" + number + '\'' +
              ", money='" + money + '\'' +
              ", lastMonthNumber='" + lastMonthNumber + '\'' +
              ", lastMonthComplete='" + lastMonthComplete + '\'' +
              ", cgjhxx=" + cgjhxx +
              ", attachments1=" + attachments1 +
              '}';
   }
}
