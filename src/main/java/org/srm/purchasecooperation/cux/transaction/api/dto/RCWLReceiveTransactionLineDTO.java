//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.srm.purchasecooperation.cux.transaction.api.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.commons.lang3.time.DateUtils;
import org.hzero.boot.platform.lov.annotation.LovValue;
import org.hzero.starter.keyencrypt.core.Encrypt;
import org.springframework.format.annotation.DateTimeFormat;
import org.srm.purchasecooperation.transaction.api.dto.ReceiveTransactionLineDTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class RCWLReceiveTransactionLineDTO extends ReceiveTransactionLineDTO {
    @Encrypt
    private Long rcvTrxHeaderId;
    @Encrypt
    private Long rcvTrxLineId;
    private Long tenantId;
    private Long supplierTenantId;
    @Encrypt
    private Long asnLineId;
    private String displayTrxNum;
    private String displayTrxLineNum;
    private String displayPoNum;
    private String agentName;
    @LovValue(lovCode = "SINV.ASN.LNS_RECEIVE_STATUS", meaningField = "receiveStatusMeaning")
    private String receiveStatus;
    private String receiveStatusMeaning;
    @Encrypt
    private Long agentId;
    private String displayLineNum;
    private String supplierNum;
    private String supplierName;
    @Encrypt
    private Long companyId;
    private String companyName;
    private Long invOrganizationId;
    private String ouName;
    private String itemCode;
    private String itemName;
    private String rcvTrxTypeName;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date trxDate;
    private BigDecimal quantity;
    private String uomName;
    private BigDecimal poUnitPrice;
    private String poUnitPriceMeaning;
    private BigDecimal netAmount;
    private String netAmountMeaning;
    @LovValue("SPUC.SINV.MOVE.REASON")
    private String moveReason;
    private String moveReasonMeaning;
    private String displayReleaseNum;
    private String displayLineLocationNum;
    private String supplierSiteName;
    private String organizationName;
    private String inventoryName;
    private String locationName;
    @LovValue("SINV.RCV_STOCK_TYPE")
    private String stockType;
    private String stockTypeMeaning;
    private String oldItemCode;
    private String remark;
    private String receiptPerson;
    private String asnNum;
    private String displayAsnLineNum;
    private String lotNum;
    private Date lotExpirationDate;
    private String serialNum;
    private BigDecimal netPrice;
    private String currencyCode;
    private String rateType;
    private BigDecimal exchangeRate;
    private BigDecimal unitPriceBatch;
    private String periodName;
    @LovValue("HPFM.FLAG")
    private Integer invoiceClosedFlag;
    private String invoiceClosedFlagMeaning;
    @LovValue("SINV.INVOICE_MATCHED_STATUS")
    private String invoiceMatchedStatus;
    private String invoiceMatchedStatusMeaning;
    private BigDecimal invoicedQuantity;
    @LovValue("HPFM.FLAG")
    private Integer billClosedFlag;
    private String billClosedFlagMeaning;
    @LovValue("SINV.BILL_MATCHED_STATUS")
    private Integer billMatchedFlag;
    private String billMatchedFlagMeaning;
    private BigDecimal billMatchedQuantity;
    @LovValue("HPFM.FLAG")
    private Integer reverseFlag;
    private String reverseFlagMeaning;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date trxDateFrom;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date trxDateTo;
    private String purchaseAgentName;
    @Encrypt
    private String supplierId;
    private Long asnHeaderId;
    @Encrypt
    private Long supplierCompanyId;
    private Long supplierSiteId;
    private Long purchaseAgentId;
    @Encrypt
    private Long purchaseOrgId;
    @Encrypt
    private Long ouId;
    private Long inventoryId;
    private Long rcvTrxTypeId;
    private String rcvTrxTypeCode;
    private Long locationId;
    private Long poLineId;
    private Long inspectionAssociateFlag;
    private String productNum;
    private String productName;
    private String catalogName;
    private String supplierItemNum;
    private String supplierItemDesc;
    private String productionOrderNum;
    private String receivedBy;
    @Encrypt
    private Long itemId;
    private Date realityReceiveDate;
    private String agentIds;
    private String purchaseOrgIds;
    private Long prLineId;
    private Set<Long> poLineLocationIds;
    private Set<Long> poLineIds;
    private Set<Long> rcvTrxLineIds;
    @LovValue(value = "SINV.RCV_TRX_INVOICE_REVIEWED_STATUS", meaningField = "invoiceReviewedStatusMeaning")
    private String invoiceReviewedStatus;
    private String invoiceReviewedStatusMeaning;
    private BigDecimal invoicedReviewedQuantity;
    @LovValue(value = "HPFM.FLAG", meaningField = "esCloseFlagMeaning")
    private Integer esCloseFlag;
    private String esCloseFlagMeaning;
    private List<Long> asnLineList;
    private Integer newSinvFlag;
    private Integer returnedFlag;
    private String fromPcNum;
    private String sourceHeaderNum;
    private String sourceLineNum;
    private String spfmRcvTrxTypeCode;
    private String createdName;
    private BigDecimal orgNetAmount;
    private Long attributeBigint1;
    private String attributeVarchar1;
    private String attributeVarchar2;
    private String accpMetentionMoney;

    public RCWLReceiveTransactionLineDTO() {}

    public String getAccpMetentionMoney() {
        return accpMetentionMoney;
    }

    public void setAccpMetentionMoney(String accpMetentionMoney) {
        this.accpMetentionMoney = accpMetentionMoney;
    }

    public Long getAttributeBigint1() {
        return attributeBigint1;
    }

    public void setAttributeBigint1(Long attributeBigint1) {
        this.attributeBigint1 = attributeBigint1;
    }

    @Override
    public String getAttributeVarchar1() {
        return attributeVarchar1;
    }

    @Override
    public void setAttributeVarchar1(String attributeVarchar1) {
        this.attributeVarchar1 = attributeVarchar1;
    }

    @Override
    public String getAttributeVarchar2() {
        return attributeVarchar2;
    }

    @Override
    public void setAttributeVarchar2(String attributeVarchar2) {
        this.attributeVarchar2 = attributeVarchar2;
    }

    public RCWLReceiveTransactionLineDTO initTrxDateFromForQuery() {
        if (this.trxDateFrom == null) {
            this.trxDateFrom = DateUtils.addMonths(new Date(), -6);
        }

        return this;
    }

    public RCWLReceiveTransactionLineDTO initTrxDateToForQuery() {
        if (this.trxDateTo != null) {
            this.trxDateTo = DateUtils.addDays(this.trxDateTo, 1);
        }

        return this;
    }

    public String getRcvTrxTypeCode() {
        return this.rcvTrxTypeCode;
    }

    public void setRcvTrxTypeCode(String rcvTrxTypeCode) {
        this.rcvTrxTypeCode = rcvTrxTypeCode;
    }

    public String getSpfmRcvTrxTypeCode() {
        return this.spfmRcvTrxTypeCode;
    }

    public void setSpfmRcvTrxTypeCode(String spfmRcvTrxTypeCode) {
        this.spfmRcvTrxTypeCode = spfmRcvTrxTypeCode;
    }

    public String getReceiveStatus() {
        return this.receiveStatus;
    }

    public void setReceiveStatus(String receiveStatus) {
        this.receiveStatus = receiveStatus;
    }

    public String getReceiveStatusMeaning() {
        return this.receiveStatusMeaning;
    }

    public void setReceiveStatusMeaning(String receiveStatusMeaning) {
        this.receiveStatusMeaning = receiveStatusMeaning;
    }

    public Long getInspectionAssociateFlag() {
        return this.inspectionAssociateFlag;
    }

    public void setInspectionAssociateFlag(Long inspectionAssociateFlag) {
        this.inspectionAssociateFlag = inspectionAssociateFlag;
    }

    public List<Long> getAsnLineList() {
        return this.asnLineList;
    }

    public void setAsnLineList(List<Long> asnLineList) {
        this.asnLineList = asnLineList;
    }

    public BigDecimal getUnitPriceBatch() {
        return this.unitPriceBatch;
    }

    public void setUnitPriceBatch(BigDecimal unitPriceBatch) {
        this.unitPriceBatch = unitPriceBatch;
    }

    public Set<Long> getRcvTrxLineIds() {
        return this.rcvTrxLineIds;
    }

    public void setRcvTrxLineIds(Set<Long> rcvTrxLineIds) {
        this.rcvTrxLineIds = rcvTrxLineIds;
    }

    public Set<Long> getPoLineIds() {
        return this.poLineIds;
    }

    public void setPoLineIds(Set<Long> poLineIds) {
        this.poLineIds = poLineIds;
    }

    public Long getAsnHeaderId() {
        return this.asnHeaderId;
    }

    public void setAsnHeaderId(Long asnHeaderId) {
        this.asnHeaderId = asnHeaderId;
    }

    public Long getPoLineId() {
        return this.poLineId;
    }

    public void setPoLineId(Long poLineId) {
        this.poLineId = poLineId;
    }

    public String getProductNum() {
        return this.productNum;
    }

    public void setProductNum(String productNum) {
        this.productNum = productNum;
    }

    public String getProductName() {
        return this.productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCatalogName() {
        return this.catalogName;
    }

    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
    }

    public Long getInvOrganizationId() {
        return this.invOrganizationId;
    }

    public void setInvOrganizationId(Long invOrganizationId) {
        this.invOrganizationId = invOrganizationId;
    }

    public String getInvoiceClosedFlagMeaning() {
        return this.invoiceClosedFlagMeaning;
    }

    public void setInvoiceClosedFlagMeaning(String invoiceClosedFlagMeaning) {
        this.invoiceClosedFlagMeaning = invoiceClosedFlagMeaning;
    }

    public String getSupplierItemDesc() {
        return this.supplierItemDesc;
    }

    public void setSupplierItemDesc(String supplierItemDesc) {
        this.supplierItemDesc = supplierItemDesc;
    }

    public String getSupplierItemNum() {
        return this.supplierItemNum;
    }

    public void setSupplierItemNum(String supplierItemNum) {
        this.supplierItemNum = supplierItemNum;
    }

    public String getProductionOrderNum() {
        return this.productionOrderNum;
    }

    public void setProductionOrderNum(String productionOrderNum) {
        this.productionOrderNum = productionOrderNum;
    }

    public Long getRcvTrxHeaderId() {
        return this.rcvTrxHeaderId;
    }

    public void setRcvTrxHeaderId(Long rcvTrxHeaderId) {
        this.rcvTrxHeaderId = rcvTrxHeaderId;
    }

    public Long getRcvTrxLineId() {
        return this.rcvTrxLineId;
    }

    public void setRcvTrxLineId(Long rcvTrxLineId) {
        this.rcvTrxLineId = rcvTrxLineId;
    }

    public Long getTenantId() {
        return this.tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public Long getSupplierTenantId() {
        return this.supplierTenantId;
    }

    public void setSupplierTenantId(Long supplierTenantId) {
        this.supplierTenantId = supplierTenantId;
    }

    public Long getAsnLineId() {
        return this.asnLineId;
    }

    public void setAsnLineId(Long asnLineId) {
        this.asnLineId = asnLineId;
    }

    public String getDisplayTrxNum() {
        return this.displayTrxNum;
    }

    public void setDisplayTrxNum(String displayTrxNum) {
        this.displayTrxNum = displayTrxNum;
    }

    public String getDisplayTrxLineNum() {
        return this.displayTrxLineNum;
    }

    public void setDisplayTrxLineNum(String displayTrxLineNum) {
        this.displayTrxLineNum = displayTrxLineNum;
    }

    public String getDisplayPoNum() {
        return this.displayPoNum;
    }

    public void setDisplayPoNum(String displayPoNum) {
        this.displayPoNum = displayPoNum;
    }

    public String getDisplayLineNum() {
        return this.displayLineNum;
    }

    public void setDisplayLineNum(String displayLineNum) {
        this.displayLineNum = displayLineNum;
    }

    public String getSupplierNum() {
        return this.supplierNum;
    }

    public void setSupplierNum(String supplierNum) {
        this.supplierNum = supplierNum;
    }

    public String getSupplierName() {
        return this.supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getItemCode() {
        return this.itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemName() {
        return this.itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getRcvTrxTypeName() {
        return this.rcvTrxTypeName;
    }

    public void setRcvTrxTypeName(String rcvTrxTypeName) {
        this.rcvTrxTypeName = rcvTrxTypeName;
    }

    public Date getTrxDate() {
        return this.trxDate;
    }

    public void setTrxDate(Date trxDate) {
        this.trxDate = trxDate;
    }

    public BigDecimal getQuantity() {
        return this.quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public String getUomName() {
        return this.uomName;
    }

    public void setUomName(String uomName) {
        this.uomName = uomName;
    }

    public BigDecimal getPoUnitPrice() {
        return this.poUnitPrice;
    }

    public void setPoUnitPrice(BigDecimal poUnitPrice) {
        this.poUnitPrice = poUnitPrice;
    }

    public BigDecimal getNetAmount() {
        return this.netAmount;
    }

    public void setNetAmount(BigDecimal netAmount) {
        this.netAmount = netAmount;
    }

    public String getMoveReason() {
        return this.moveReason;
    }

    public void setMoveReason(String moveReason) {
        this.moveReason = moveReason;
    }

    public String getMoveReasonMeaning() {
        return this.moveReasonMeaning;
    }

    public void setMoveReasonMeaning(final String moveReasonMeaning) {
        this.moveReasonMeaning = moveReasonMeaning;
    }

    public String getDisplayReleaseNum() {
        return this.displayReleaseNum;
    }

    public void setDisplayReleaseNum(String displayReleaseNum) {
        this.displayReleaseNum = displayReleaseNum;
    }

    public String getDisplayLineLocationNum() {
        return this.displayLineLocationNum;
    }

    public void setDisplayLineLocationNum(String displayLineLocationNum) {
        this.displayLineLocationNum = displayLineLocationNum;
    }

    public String getSupplierSiteName() {
        return this.supplierSiteName;
    }

    public void setSupplierSiteName(String supplierSiteName) {
        this.supplierSiteName = supplierSiteName;
    }

    public String getOrganizationName() {
        return this.organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getInventoryName() {
        return this.inventoryName;
    }

    public void setInventoryName(String inventoryName) {
        this.inventoryName = inventoryName;
    }

    public String getLocationName() {
        return this.locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getStockType() {
        return this.stockType;
    }

    public void setStockType(String stockType) {
        this.stockType = stockType;
    }

    public String getStockTypeMeaning() {
        return this.stockTypeMeaning;
    }

    public void setStockTypeMeaning(String stockTypeMeaning) {
        this.stockTypeMeaning = stockTypeMeaning;
    }

    public String getOldItemCode() {
        return this.oldItemCode;
    }

    public void setOldItemCode(String oldItemCode) {
        this.oldItemCode = oldItemCode;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getReceiptPerson() {
        return this.receiptPerson;
    }

    public void setReceiptPerson(String receiptPerson) {
        this.receiptPerson = receiptPerson;
    }

    public String getAsnNum() {
        return this.asnNum;
    }

    public void setAsnNum(String asnNum) {
        this.asnNum = asnNum;
    }

    public String getDisplayAsnLineNum() {
        return this.displayAsnLineNum;
    }

    public void setDisplayAsnLineNum(String displayAsnLineNum) {
        this.displayAsnLineNum = displayAsnLineNum;
    }

    public String getLotNum() {
        return this.lotNum;
    }

    public void setLotNum(String lotNum) {
        this.lotNum = lotNum;
    }

    public String getAgentName() {
        return this.agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public Date getLotExpirationDate() {
        return this.lotExpirationDate;
    }

    public void setLotExpirationDate(Date lotExpirationDate) {
        this.lotExpirationDate = lotExpirationDate;
    }

    public String getSerialNum() {
        return this.serialNum;
    }

    public void setSerialNum(String serialNum) {
        this.serialNum = serialNum;
    }

    public BigDecimal getNetPrice() {
        return this.netPrice;
    }

    public void setNetPrice(BigDecimal netPrice) {
        this.netPrice = netPrice;
    }

    public String getCurrencyCode() {
        return this.currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getRateType() {
        return this.rateType;
    }

    public void setRateType(String rateType) {
        this.rateType = rateType;
    }

    public BigDecimal getExchangeRate() {
        return this.exchangeRate;
    }

    public void setExchangeRate(BigDecimal exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public String getPeriodName() {
        return this.periodName;
    }

    public void setPeriodName(String periodName) {
        this.periodName = periodName;
    }

    public Integer getInvoiceClosedFlag() {
        return this.invoiceClosedFlag;
    }

    public void setInvoiceClosedFlag(Integer invoiceClosedFlag) {
        this.invoiceClosedFlag = invoiceClosedFlag;
    }

    public String getInvoiceMatchedStatus() {
        return this.invoiceMatchedStatus;
    }

    public void setInvoiceMatchedStatus(String invoiceMatchedStatus) {
        this.invoiceMatchedStatus = invoiceMatchedStatus;
    }

    public String getInvoiceMatchedStatusMeaning() {
        return this.invoiceMatchedStatusMeaning;
    }

    public void setInvoiceMatchedStatusMeaning(String invoiceMatchedStatusMeaning) {
        this.invoiceMatchedStatusMeaning = invoiceMatchedStatusMeaning;
    }

    public Long getAgentId() {
        return this.agentId;
    }

    public void setAgentId(Long agentId) {
        this.agentId = agentId;
    }

    public BigDecimal getInvoicedQuantity() {
        return this.invoicedQuantity;
    }

    public void setInvoicedQuantity(BigDecimal invoicedQuantity) {
        this.invoicedQuantity = invoicedQuantity;
    }

    public Integer getBillClosedFlag() {
        return this.billClosedFlag;
    }

    public void setBillClosedFlag(Integer billClosedFlag) {
        this.billClosedFlag = billClosedFlag;
    }

    public String getBillClosedFlagMeaning() {
        return this.billClosedFlagMeaning;
    }

    public void setBillClosedFlagMeaning(String billClosedFlagMeaning) {
        this.billClosedFlagMeaning = billClosedFlagMeaning;
    }

    public Integer getBillMatchedFlag() {
        return this.billMatchedFlag;
    }

    public void setBillMatchedFlag(Integer billMatchedFlag) {
        this.billMatchedFlag = billMatchedFlag;
    }

    public String getBillMatchedFlagMeaning() {
        return this.billMatchedFlagMeaning;
    }

    public void setBillMatchedFlagMeaning(String billMatchedFlagMeaning) {
        this.billMatchedFlagMeaning = billMatchedFlagMeaning;
    }

    public BigDecimal getBillMatchedQuantity() {
        return this.billMatchedQuantity;
    }

    public void setBillMatchedQuantity(BigDecimal billMatchedQuantity) {
        this.billMatchedQuantity = billMatchedQuantity;
    }

    public Integer getReverseFlag() {
        return this.reverseFlag;
    }

    public void setReverseFlag(Integer reverseFlag) {
        this.reverseFlag = reverseFlag;
    }

    public String getReverseFlagMeaning() {
        return this.reverseFlagMeaning;
    }

    public void setReverseFlagMeaning(String reverseFlagMeaning) {
        this.reverseFlagMeaning = reverseFlagMeaning;
    }

    public Date getTrxDateFrom() {
        return this.trxDateFrom;
    }

    public void setTrxDateFrom(Date trxDateFrom) {
        this.trxDateFrom = trxDateFrom;
    }

    public Date getTrxDateTo() {
        return this.trxDateTo;
    }

    public void setTrxDateTo(Date trxDateTo) {
        this.trxDateTo = trxDateTo;
    }

    public String getOuName() {
        return this.ouName;
    }

    public void setOuName(String ouName) {
        this.ouName = ouName;
    }

    public String getPurchaseAgentName() {
        return this.purchaseAgentName;
    }

    public void setPurchaseAgentName(String purchaseAgentName) {
        this.purchaseAgentName = purchaseAgentName;
    }

    public Long getSupplierCompanyId() {
        return this.supplierCompanyId;
    }

    public void setSupplierCompanyId(Long supplierCompanyId) {
        this.supplierCompanyId = supplierCompanyId;
    }

    public Long getSupplierSiteId() {
        return this.supplierSiteId;
    }

    public void setSupplierSiteId(Long supplierSiteId) {
        this.supplierSiteId = supplierSiteId;
    }

    public Long getPurchaseAgentId() {
        return this.purchaseAgentId;
    }

    public void setPurchaseAgentId(Long purchaseAgentId) {
        this.purchaseAgentId = purchaseAgentId;
    }

    public Long getPurchaseOrgId() {
        return this.purchaseOrgId;
    }

    public void setPurchaseOrgId(Long purchaseOrgId) {
        this.purchaseOrgId = purchaseOrgId;
    }

    public Long getOuId() {
        return this.ouId;
    }

    public void setOuId(Long ouId) {
        this.ouId = ouId;
    }

    public Long getInventoryId() {
        return this.inventoryId;
    }

    public void setInventoryId(Long inventoryId) {
        this.inventoryId = inventoryId;
    }

    public Long getRcvTrxTypeId() {
        return this.rcvTrxTypeId;
    }

    public void setRcvTrxTypeId(Long rcvTrxTypeId) {
        this.rcvTrxTypeId = rcvTrxTypeId;
    }

    public Long getLocationId() {
        return this.locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public Long getCompanyId() {
        return this.companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return this.companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getReceivedBy() {
        return this.receivedBy;
    }

    public void setReceivedBy(String receivedBy) {
        this.receivedBy = receivedBy;
    }

    public String getSupplierId() {
        return this.supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getPoUnitPriceMeaning() {
        return this.poUnitPriceMeaning;
    }

    public void setPoUnitPriceMeaning(String poUnitPriceMeaning) {
        this.poUnitPriceMeaning = poUnitPriceMeaning;
    }

    public String getNetAmountMeaning() {
        return this.netAmountMeaning;
    }

    public void setNetAmountMeaning(String netAmountMeaning) {
        this.netAmountMeaning = netAmountMeaning;
    }

    public Set<Long> getPoLineLocationIds() {
        return this.poLineLocationIds;
    }

    public void setPoLineLocationIds(Set<Long> poLineLocationIds) {
        this.poLineLocationIds = poLineLocationIds;
    }

    public Long getItemId() {
        return this.itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Long getPrLineId() {
        return this.prLineId;
    }

    public void setPrLineId(Long prLineId) {
        this.prLineId = prLineId;
    }

    public String getInvoiceReviewedStatus() {
        return this.invoiceReviewedStatus;
    }

    public void setInvoiceReviewedStatus(String invoiceReviewedStatus) {
        this.invoiceReviewedStatus = invoiceReviewedStatus;
    }

    public BigDecimal getInvoicedReviewedQuantity() {
        return this.invoicedReviewedQuantity;
    }

    public void setInvoicedReviewedQuantity(BigDecimal invoicedReviewedQuantity) {
        this.invoicedReviewedQuantity = invoicedReviewedQuantity;
    }

    public String getInvoiceReviewedStatusMeaning() {
        return this.invoiceReviewedStatusMeaning;
    }

    public void setInvoiceReviewedStatusMeaning(String invoiceReviewedStatusMeaning) {
        this.invoiceReviewedStatusMeaning = invoiceReviewedStatusMeaning;
    }

    public Integer getEsCloseFlag() {
        return this.esCloseFlag;
    }

    public void setEsCloseFlag(Integer esCloseFlag) {
        this.esCloseFlag = esCloseFlag;
    }

    public String getEsCloseFlagMeaning() {
        return this.esCloseFlagMeaning;
    }

    public void setEsCloseFlagMeaning(String esCloseFlagMeaning) {
        this.esCloseFlagMeaning = esCloseFlagMeaning;
    }

    public Date getRealityReceiveDate() {
        return this.realityReceiveDate;
    }

    public void setRealityReceiveDate(Date realityReceiveDate) {
        this.realityReceiveDate = realityReceiveDate;
    }

    public String getAgentIds() {
        return this.agentIds;
    }

    public void setAgentIds(String agentIds) {
        this.agentIds = agentIds;
    }

    public String getPurchaseOrgIds() {
        return this.purchaseOrgIds;
    }

    public void setPurchaseOrgIds(String purchaseOrgIds) {
        this.purchaseOrgIds = purchaseOrgIds;
    }

    public Integer getNewSinvFlag() {
        return this.newSinvFlag;
    }

    public void setNewSinvFlag(final Integer newSinvFlag) {
        this.newSinvFlag = newSinvFlag;
    }

    public Integer getReturnedFlag() {
        return this.returnedFlag;
    }

    public void setReturnedFlag(final Integer returnedFlag) {
        this.returnedFlag = returnedFlag;
    }

    public String getFromPcNum() {
        return this.fromPcNum;
    }

    public void setFromPcNum(final String fromPcNum) {
        this.fromPcNum = fromPcNum;
    }

    public String getCreatedName() {
        return this.createdName;
    }

    public void setCreatedName(String createdName) {
        this.createdName = createdName;
    }

    public String getSourceHeaderNum() {
        return this.sourceHeaderNum;
    }

    public void setSourceHeaderNum(final String sourceHeaderNum) {
        this.sourceHeaderNum = sourceHeaderNum;
    }

    public String getSourceLineNum() {
        return this.sourceLineNum;
    }

    public void setSourceLineNum(final String sourceLineNum) {
        this.sourceLineNum = sourceLineNum;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
