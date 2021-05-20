package org.srm.purchasecooperation.cux.acp.api.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author lu.cheng01@hand-china.com
 * @description 大象的 authorize 信息
 * @date 2021/4/9 13:39
 * @version:1.0
 */
@JsonInclude
@Data
@ToString
public class RCWLAcpInvoiceElephantDataDTO {

   @ApiModelProperty("业务单据号")
   @JSONField(ordinal = 1)
   private String documentNumber;

   @ApiModelProperty("审批状态")
   @JSONField(ordinal = 2)
   private String status;

   public String getDocumentNumber() {
      return documentNumber;
   }

   public void setDocumentNumber(String documentNumber) {
      this.documentNumber = documentNumber;
   }

   public String getStatus() {
      return status;
   }

   public void setStatus(String status) {
      this.status = status;
   }
}
