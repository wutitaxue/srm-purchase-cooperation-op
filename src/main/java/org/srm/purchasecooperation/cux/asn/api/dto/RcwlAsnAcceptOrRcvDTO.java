package org.srm.purchasecooperation.cux.asn.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.srm.purchasecooperation.order.api.dto.ItfBaseBO;

import javax.persistence.Id;
import javax.validation.constraints.NotBlank;


@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class RcwlAsnAcceptOrRcvDTO extends ItfBaseBO{


//    /**
//     * 租户id
//     */
//    @NotBlank
//    private String TenantId;
    @Id
    private Long id;

    /**
     * 业务类型
     */
    @NotBlank
    private String businessType;

    /**
     * 采购平台单据类型
     */
    @NotBlank
    private String plfmDocumentType;


    /**
     * 接收验收单号
     */
    @NotBlank
    private String acceptanceNumber;
    /**
     * 行号
     */
    @NotBlank
    private Long lineNumber;

    /**
     * 物料编码
     */
    private String itemCode;


    /**
     * 回传单号
     */
    private String receiptNo;

    /**
     * 净入库数量
     */
    private Long inventoryQuantity;

    /**
     * 资产系统采购订单号
     */
    private String purchaseOrderNo;

    private String returnFlag;

    public RcwlAsnAcceptOrRcvDTO() {
    }

    public RcwlAsnAcceptOrRcvDTO(Long sitfPkId, Integer errorFlag, String errorMessage, String errorType, String returnFlag) {
        super(sitfPkId, errorFlag, errorMessage, errorType);
        this.returnFlag = returnFlag;
    }

    public String getReturnFlag() {
        return returnFlag;
    }

    public void setReturnFlag(String returnFlag) {
        this.returnFlag = returnFlag;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getPlfmDocumentType() {
        return plfmDocumentType;
    }

    public void setPlfmDocumentType(String plfmDocumentType) {
        this.plfmDocumentType = plfmDocumentType;
    }

    public String getAcceptanceNumber() {
        return acceptanceNumber;
    }

    public void setAcceptanceNumber(String acceptanceNumber) {
        this.acceptanceNumber = acceptanceNumber;
    }

    public Long getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(Long lineNumber) {
        this.lineNumber = lineNumber;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getReceiptNo() {
        return receiptNo;
    }

    public void setReceiptNo(String receiptNo) {
        this.receiptNo = receiptNo;
    }

    public Long getInventoryQuantity() {
        return inventoryQuantity;
    }

    public void setInventoryQuantity(Long inventoryQuantity) {
        this.inventoryQuantity = inventoryQuantity;
    }

    public String getPurchaseOrderNo() {
        return purchaseOrderNo;
    }

    public void setPurchaseOrderNo(String purchaseOrderNo) {
        this.purchaseOrderNo = purchaseOrderNo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
