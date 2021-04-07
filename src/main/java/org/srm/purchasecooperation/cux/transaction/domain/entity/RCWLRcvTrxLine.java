//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.srm.purchasecooperation.cux.transaction.domain.entity;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.util.*;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.validator.constraints.Length;
import org.hzero.core.base.BaseConstants.Flag;
import org.hzero.starter.keyencrypt.core.Encrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.srm.purchasecooperation.order.infra.utils.OrderUtil;
import org.srm.purchasecooperation.transaction.api.dto.RcvTrxTypeDTO;
import org.srm.purchasecooperation.transaction.domain.entity.RcvTrxHeader;
import org.srm.purchasecooperation.transaction.domain.entity.RcvTrxLine;
import org.srm.purchasecooperation.transaction.domain.entity.RcvTrxLineEs;
import org.srm.purchasecooperation.transaction.infra.annotation.FieldIgnore;

import io.choerodon.mybatis.annotation.ModifyAudit;
import io.choerodon.mybatis.annotation.VersionAudit;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("采购事务行表")
@VersionAudit
@ModifyAudit
@Table(name = "sinv_rcv_trx_line")
public class RCWLRcvTrxLine extends RcvTrxLine {
    public static final String[] PERMIT_UPDATE_FIELD_NAME = permitUpdateFieldName();
    public static final String FIELD_RCV_TRX_LINE_ID = "rcvTrxLineId";
    public static final String FIELD_RCV_TRX_HEADER_ID = "rcvTrxHeaderId";
    public static final String FIELD_TENANT_ID = "tenantId";
    public static final String FIELD_SUPPLIER_TENANT_ID = "supplierTenantId";
    public static final String FIELD_TRX_LINE_NUM = "trxLineNum";
    public static final String FIELD_DISPLAY_TRX_LINE_NUM = "displayTrxLineNum";
    public static final String FIELD_TRX_DATE = "trxDate";
    public static final String FIELD_TRX_YEAR = "trxYear";
    public static final String FIELD_RCV_TRX_TYPE_ID = "rcvTrxTypeId";
    public static final String FIELD_MOVE_REASON_NAME = "moveReason";
    public static final String FIELD_QUANTITY = "quantity";
    public static final String FIELD_STOCK_TYPE = "stockType";
    public static final String FIELD_CURRENCY_CODE = "currencyCode";
    public static final String FIELD_PERIOD_NAME = "periodName";
    public static final String FIELD_NET_PRICE = "netPrice";
    public static final String FIELD_TAX_INCLUDED_PRICE = "taxIncludedPrice";
    public static final String FIELD_PO_UNIT_PRICE = "poUnitPrice";
    public static final String FIELD_NET_AMOUNT = "netAmount";
    public static final String FIELD_TAX_INCLUDED_AMOUNT = "taxIncludedAmount";
    public static final String FIELD_TAX_ID = "taxId";
    public static final String FIELD_TAX_RATE = "taxRate";
    public static final String FIELD_ITEM_ID = "itemId";
    public static final String FIELD_ITEM_CODE = "itemCode";
    public static final String FIELD_ITEM_NAME = "itemName";
    public static final String FIELD_CATEGORY_ID = "categoryId";
    public static final String FIELD_UOM_ID = "uomId";
    public static final String FIELD_COMPANY_ID = "companyId";
    public static final String FIELD_COMPANY_NUM = "companyName";
    public static final String FIELD_OU_ID = "ouId";
    public static final String FIELD_PUR_ORGANIZATION_ID = "purOrganizationId";
    public static final String FIELD_INV_ORGANIZATION_ID = "invOrganizationId";
    public static final String FIELD_INVENTORY_ID = "inventoryId";
    public static final String FIELD_LOCATOR_ID = "locatorId";
    public static final String FIELD_SUPPLIER_ID = "supplierId";
    public static final String FIELD_SUPPLIER_NUM = "supplierNum";
    public static final String FIELD_SUPPLIER_NAME = "supplierName";
    public static final String FIELD_SUPPLIER_SITE_ID = "supplierSiteId";
    public static final String FIELD_SUPPLIER_COMPANY_ID = "supplierCompanyId";
    public static final String FIELD_SUPPLIER_COMPANY_NAME = "supplierCompanyName";
    public static final String FIELD_SUPPLIER_OU_ID = "supplierOuId";
    public static final String FIELD_ENTERED_TRX_PRICE = "enteredTrxPrice";
    public static final String FIELD_ENTERED_TRX_AMOUNT = "enteredTrxAmount";
    public static final String FIELD_ENTERED_CURRENCY_CODE = "enteredCurrencyCode";
    public static final String FIELD_RATE_TYPE = "rateType";
    public static final String FIELD_EXCHANGE_RATE_DATE = "exchangeRateDate";
    public static final String FIELD_EXCHANGE_RATE = "exchangeRate";
    public static final String FIELD_LOT_NUM = "lotNum";
    public static final String FIELD_SERIAL_NUM = "serialNum";
    public static final String FIELD_PARENT_TRX_LINE_ID = "parentTrxLineId";
    public static final String FIELD_FROM_PO_HEADER_ID = "fromPoHeaderId";
    public static final String FIELD_FROM_PO_LINE_ID = "fromPoLineId";
    public static final String FIELD_FROM_PO_LINE_LOCATION_ID = "fromPoLineLocationId";
    public static final String FIELD_FROM_PO_DISTRIBUTION_ID = "fromPoDistributionId";
    public static final String FIELD_FROM_ASN_HEADER_ID = "fromAsnHeaderId";
    public static final String FIELD_FROM_ASN_LINE_ID = "fromAsnLineId";
    public static final String FIELD_TERMS_ID = "termsId";
    public static final String FIELD_REVERSE_FLAG = "reverseFlag";
    public static final String FIELD_REVERSE_TRX_LINE_ID = "reverseTrxLineId";
    public static final String FIELD_NEED_INVOICE_FLAG = "needInvoiceFlag";
    public static final String FIELD_INVOICE_CLOSED_FLAG = "invoiceClosedFlag";
    public static final String FIELD_INVOICE_MATCHED_STATUS = "invoiceMatchedStatus";
    public static final String FIELD_INVOICED_QUANTITY = "invoicedQuantity";
    public static final String FIELD_INVOICE_REVIEWED_STATUS = "invoiceReviewedStatus";
    public static final String FIELD_INVOICED_REVIEWED_QUANTITY = "invoicedReviewedQuantity";
    public static final String FIELD_BILL_MATCHED_FLAG = "billMatchedFlag";
    public static final String FIELD_MATCHED_BILL_DETAIL_ID = "matchedBillDetailId";
    public static final String FIELD_MATCHED_BILL_NUM = "matchedBillNum";
    public static final String FIELD_TRX_SOURCE_TYPE_ID = "trxSourceTypeId";
    public static final String FIELD_TRX_SOURCE_LINE_ID = "trxSourceLineId";
    public static final String FIELD_ERP_PARENT_CONVERT_FLAG = "erpParentConvertFlag";
    public static final String FIELD_ERP_PARENT_TRX_LINE_ID = "erpParentTrxLineId";
    public static final String FIELD_ERP_PARENT_TRX_NUM = "erpParentTrxNum";
    public static final String FIELD_ERP_PARENT_TRX_LINE_NUM = "erpParentTrxLineNum";
    public static final String FIELD_ERP_PARENT_TRX_YEAR = "erpParentTrxYear";
    public static final String FIELD_ERP_SOURCE_TRX_NUM = "erpSourceTrxNum";
    public static final String FIELD_ERP_SOURCE_TRX_LINE_ID = "erpSourceTrxLineId";
    public static final String FIELD_ERP_SOURCE_TRX_LINE_NUM = "erpSourceTrxLineNum";
    public static final String FIELD_ERP_SOURCE_TRX_YEAR = "erpSourceTrxYear";
    public static final String FIELD_ERP_CREATION_DATE = "erpCreationDate";
    public static final String FIELD_ERP_LAST_UPDATE_DATE = "erpLastUpdateDate";
    public static final String FIELD_REMARK = "remark";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";
    public static final String FIELD_START_DATE = "startDate";
    public static final String FIELD_END_DATE = "endDate";
    public static final String FIELD_ORDER_SEQ = "orderSeq";
    public static final String FIELD_OUTPUT_STOCK_FLAG = "outputStockFlag";
    public static final String FIELD_AGENT_ID = "agentId";
    public static final String FIELD_FULL_REVERSED_FLAG = "fullReversedFlag";
    public static final String FIELD_EXTERNAL_REVERSE_FLAG = "externalReverseFlag";
    public static final String FIELD_REALITY_RECEIVE_DATE = "realityReceiveDate";
    public static final String FIELD_FROM_ORDER_FLAG = "fromOrderFlag";
    public static final String FIELD_FROM_RCV_TRX_LINE_ID = "fromRcvTrxLineId";
    public static final String FIELD_OCCUPIED_QUANTITY = "occupiedQuantity";
    public static final String FIELD_REVERSED_QUANTITY = "reversedQuantity";
    public static final String FIELD_COMPLETE_FLAG = "completeFlag";
    public static final String FIELD_REVERSED_TAX_AMOUNT = "reversedTaxAmount";
    public static final String FIELD_OCCUPIED_TAX_AMOUNT = "occupiedTaxAmount";
    public static final String FIELD_SINV_LINE_ATTACHMENT_UUID = "sinvLineAttachmentUuid";
    public static final String FIELD_DELETE_FLAG = "deleteFlag";
    public static final String FIELD_ORG_QUANTITY = "orgQuantity";
    public static final String BILL_MATCHED_QUANTITY = "billMatchedQuantity";
    public static final String FIELD_PR_HEADER_ID = "prHeaderId";
    public static final String FIELD_PR_LINE_ID = "prLineId";
    public static final String FIELD_REVERSE_OCCUPIED_QUANTITY = "reverseOccupiedQuantity";
    public static final String FIELD_REVERSE_OCCUPIED_TAX_AMOUNT = "reverseOccupiedTaxAmount";
    public static final String FIELD_ATTRIBUTE_VARCHER1 = "attributeVarchar1";
    public static final String FIELD_ATTRIBUTE_VARCHER2 = "attributeVarchar2";
    public static final String FIELD_ATTRIBUTE_BIGINT1 = "attributeBigint1";
    public static final String FIELD_ACCP_METENTION_MONEY = "accp_metention_money";
    public static final Logger LOGGER = LoggerFactory.getLogger(RcvTrxLine.class);
    @ApiModelProperty("表ID，主键，供其他表做外键")
    @Id
    @GeneratedValue
    @FieldIgnore
    @Encrypt
    private Long rcvTrxLineId;
    @ApiModelProperty(value = "采购事务头ID", required = true)
    @NotNull
    @FieldIgnore
    @Encrypt
    private Long rcvTrxHeaderId;
    @ApiModelProperty(value = "租户ID,hpfm_tenant.tenant_id", required = true)
    @NotNull
    @FieldIgnore
    private Long tenantId;
    @ApiModelProperty("合作租户ID")
    @FieldIgnore
    @NotNull
    private Long supplierTenantId;
    @ApiModelProperty(value = "事务行号", required = true)
    @NotNull
    @FieldIgnore
    private Long trxLineNum;
    @ApiModelProperty(value = "事务展示行号", required = true)
    @NotBlank
    @FieldIgnore
    private String displayTrxLineNum;
    @ApiModelProperty("事务日期")
    @FieldIgnore
    private Date trxDate;
    @ApiModelProperty("事务年份")
    @FieldIgnore
    private Long trxYear;
    @ApiModelProperty(value = "接收事务类型ID", required = true)
    @NotNull
    @FieldIgnore
    private Long rcvTrxTypeId;
    @ApiModelProperty("移动原因说明")
    private String moveReason;
    @ApiModelProperty(value = "事务数量", required = true)
    @NotNull
    @FieldIgnore
    private BigDecimal quantity;
    @ApiModelProperty(value = "库存类型（寄售 CONSIGNMENT、自有 OWN_STOCK）sinv.rcv_trx_stock_type", required = true)
    @NotBlank
    @FieldIgnore
    private String stockType;
    @ApiModelProperty(value = "币种代码", required = true)
    @NotBlank
    private String currencyCode;
    @ApiModelProperty("期间名称")
    private String periodName;
    @ApiModelProperty("不含税单价")
    private BigDecimal netPrice;
    @ApiModelProperty("含税单价")
    private BigDecimal taxIncludedPrice;
    @ApiModelProperty("订单单价")
    private BigDecimal poUnitPrice;
    @ApiModelProperty("事务金额(不含税)")
    private BigDecimal netAmount;
    @ApiModelProperty("含税金额")
    private BigDecimal taxIncludedAmount;
    @ApiModelProperty("税率ID")
    private Long taxId;
    @ApiModelProperty("税率")
    private BigDecimal taxRate;
    @ApiModelProperty("物料ID")
    @FieldIgnore
    @Encrypt
    private Long itemId;
    @ApiModelProperty("物料代码")
    private String itemCode;
    @ApiModelProperty("物料说明")
    private String itemName;
    @ApiModelProperty("物料类别ID")
    @FieldIgnore
    private Long categoryId;
    @ApiModelProperty("单位ID")
    private Long uomId;
    @ApiModelProperty("所属公司ID")
    @NotNull
    @FieldIgnore
    @Encrypt
    private Long companyId;
    @ApiModelProperty("所属公司描述")
    private String companyName;
    @ApiModelProperty("所属业务实体ID")
    @FieldIgnore
    @Encrypt
    private Long ouId;
    @ApiModelProperty("采购组织ID")
    @FieldIgnore
    private Long purOrganizationId;
    @ApiModelProperty("收货组织ID")
    @FieldIgnore
    private Long invOrganizationId;
    @ApiModelProperty("收货库房ID")
    private Long inventoryId;
    @ApiModelProperty("收货库位ID")
    private Long locatorId;
    @ApiModelProperty("供应商ID")
    @NotNull
    @FieldIgnore
    @Encrypt
    private Long supplierId;
    @ApiModelProperty("供应商代码")
    private String supplierNum;
    @ApiModelProperty("供应商名称")
    private String supplierName;
    @ApiModelProperty("供应商地点ID")
    @FieldIgnore
    private Long supplierSiteId;
    @ApiModelProperty("供应商公司ID")
    @FieldIgnore
    @Encrypt
    private Long supplierCompanyId;
    @ApiModelProperty("供应商公司描述")
    private String supplierCompanyName;
    @ApiModelProperty("合作方业务实体ID")
    @FieldIgnore
    private Long supplierOuId;
    @ApiModelProperty("本币单价")
    private BigDecimal enteredTrxPrice;
    @ApiModelProperty("本币金额")
    private BigDecimal enteredTrxAmount;
    @ApiModelProperty("本币币种代码")
    private String enteredCurrencyCode;
    @ApiModelProperty("汇率类型")
    private String rateType;
    @ApiModelProperty("汇率日期")
    private Date exchangeRateDate;
    @ApiModelProperty("汇率")
    private BigDecimal exchangeRate;
    @ApiModelProperty("批次号")
    private String lotNum;
    @ApiModelProperty("序列号")
    private String serialNum;
    @ApiModelProperty("父事务行ID")
    @FieldIgnore
    private Long parentTrxLineId;
    @ApiModelProperty("来源采购订单头ID")
    @FieldIgnore
    private Long fromPoHeaderId;
    @ApiModelProperty("来源采购订单行ID")
    @FieldIgnore
    private Long fromPoLineId;
    @ApiModelProperty("来源采购订单发运行ID")
    @FieldIgnore
    private Long fromPoLineLocationId;
    @ApiModelProperty("来源采购订单分配行ID")
    @FieldIgnore
    private Long fromPoDistributionId;
    @ApiModelProperty("来源送货单头ID")
    @FieldIgnore
    private Long fromAsnHeaderId;
    @ApiModelProperty("来源送货单行ID")
    @FieldIgnore
    private Long fromAsnLineId;
    @ApiModelProperty("付款条款ID")
    @Encrypt
    private Long termsId;
    @ApiModelProperty(value = "反冲标识", required = true)
    @NotNull
    @FieldIgnore
    private Integer reverseFlag;
    @FieldIgnore
    @ApiModelProperty("反冲事务行ID")
    private Long reverseTrxLineId;
    @ApiModelProperty(value = "需要开票标识", required = true)
    @NotNull
    @FieldIgnore
    private Integer needInvoiceFlag;
    @ApiModelProperty(value = "发票关闭标识", required = true)
    @NotNull
    @FieldIgnore
    private Integer invoiceClosedFlag;
    @ApiModelProperty(value = "发票匹配状态 sinv.rcv_trx_invoice_matching_status(UNINVOICED,PART_INVOICED,INVOICE_COMPLETE)",
                    required = true)
    @NotBlank
    @FieldIgnore
    private String invoiceMatchedStatus;
    @ApiModelProperty(value = "已开发票数量", required = true)
    @NotNull
    @FieldIgnore
    private BigDecimal invoicedQuantity;
    @ApiModelProperty("发票复核状态，SINV.RCV_TRX_INVOICE_REVIEWED_STATUS(UNREVIEW|未复核,PART_REVIEWED|部分复核,REVIEWED|已复核)")
    @FieldIgnore
    private String invoiceReviewedStatus;
    @ApiModelProperty("发票复核数量")
    @FieldIgnore
    private BigDecimal invoicedReviewedQuantity;
    @ApiModelProperty(value = "匹配账单标识", required = true)
    @NotNull
    @FieldIgnore
    private Integer billMatchedFlag;
    @ApiModelProperty("匹配账单明细行ID")
    @FieldIgnore
    private Long matchedBillDetailId;
    @ApiModelProperty("匹配账单编号")
    @FieldIgnore
    private String matchedBillNum;
    @ApiModelProperty("事务来源单据类型")
    @FieldIgnore
    private Long trxSourceTypeId;
    @ApiModelProperty("事务来源行ID")
    @FieldIgnore
    private Long trxSourceLineId;
    @ApiModelProperty(value = "ERP父事务转换标识", required = true)
    @NotNull
    @FieldIgnore
    private Integer erpParentConvertFlag;
    @ApiModelProperty("ERP父事务行ID")
    @FieldIgnore
    private String erpParentTrxLineId;
    @ApiModelProperty("ERP父事务编号")
    @FieldIgnore
    private String erpParentTrxNum;
    @ApiModelProperty("ERP父事务行编号")
    @FieldIgnore
    private String erpParentTrxLineNum;
    @ApiModelProperty("ERP父事务年份")
    @FieldIgnore
    private Long erpParentTrxYear;
    @ApiModelProperty("erp源事务号")
    @FieldIgnore
    private String erpSourceTrxNum;
    @ApiModelProperty("erp源事务行ID")
    @FieldIgnore
    private String erpSourceTrxLineId;
    @ApiModelProperty("erp源事务行编号")
    @FieldIgnore
    private String erpSourceTrxLineNum;
    @ApiModelProperty("erp源事务年份")
    @FieldIgnore
    private Long erpSourceTrxYear;
    @ApiModelProperty("erp创建日期")
    @FieldIgnore
    private Date erpCreationDate;
    @ApiModelProperty("erp最后更新日期")
    private Date erpLastUpdateDate;
    @ApiModelProperty("备注说明")
    @FieldIgnore
    @Length(max = 480)
    private String remark;
    @ApiModelProperty("启用标识")
    @FieldIgnore
    private Integer enabledFlag;
    @ApiModelProperty("有效期从")
    @FieldIgnore
    private Date startDate;
    @ApiModelProperty("有效期至")
    @FieldIgnore
    private Date endDate;
    @ApiModelProperty("排序号")
    @FieldIgnore
    private Long orderSeq;
    @ApiModelProperty("备库出库标识")
    @FieldIgnore
    private Integer outputStockFlag;
    @ApiModelProperty("采购员Id")
    @FieldIgnore
    @Encrypt
    private Long agentId;
    @ApiModelProperty("全部冲销标记")
    @FieldIgnore
    private Integer fullReversedFlag;
    @FieldIgnore
    @ApiModelProperty("外部系统关闭标识")
    private Integer esCloseFlag;
    @ApiModelProperty("实际收货日期")
    private Date realityReceiveDate;
    @ApiModelProperty("来源事务行id")
    @FieldIgnore
    private Long fromRcvTrxLineId;
    @ApiModelProperty("行附件")
    private String sinvLineAttachmentUuid;
    @ApiModelProperty("占用金额(含税)")
    @FieldIgnore
    private BigDecimal occupiedTaxAmount;
    @ApiModelProperty("冲销金额(含税)")
    @FieldIgnore
    private BigDecimal reversedTaxAmount;
    @ApiModelProperty("是否执行完毕")
    @FieldIgnore
    private Integer completeFlag;
    @ApiModelProperty("占用数量")
    @FieldIgnore
    private BigDecimal occupiedQuantity;
    @ApiModelProperty("冲销数量")
    @FieldIgnore
    private BigDecimal reversedQuantity;
    @ApiModelProperty("删除标识")
    @FieldIgnore
    private Integer deleteFlag;
    @ApiModelProperty("事务数量")
    @FieldIgnore
    private BigDecimal orgQuantity;
    @ApiModelProperty("对账数量")
    @FieldIgnore
    private BigDecimal billMatchedQuantity;
    @ApiModelProperty("申请行id")
    private Long prHeaderId;
    @ApiModelProperty("申请头id")
    private Long prLineId;
    @ApiModelProperty("冲销占用数量")
    private BigDecimal reverseOccupiedQuantity;
    @ApiModelProperty("冲销占用金额")
    private BigDecimal reverseOccupiedTaxAmount;
    @Transient
    @FieldIgnore
    private String esRcvTrxLineId;
    @Transient
    @ApiModelProperty("是否来自订单标识")
    private Integer fromOrderFlag;
    @Transient
    @FieldIgnore
    private String externalSystemCode;
    @Transient
    @FieldIgnore
    private String sourceCode;
    @Transient
    @FieldIgnore
    private Integer canCreateBillFlag;
    @Transient
    @FieldIgnore
    private Integer receiveFlag;
    @Transient
    @FieldIgnore
    private Integer deliverFlag;
    @Transient
    @FieldIgnore
    private Integer returnToSupplierFlag;
    @Transient
    @FieldIgnore
    private Integer returnToReceivingFlag;
    @Transient
    @FieldIgnore
    private Integer poFlag;
    @Transient
    @FieldIgnore
    private Integer asnFlag;
    @Transient
    @FieldIgnore
    private Integer poUpdateFlag;
    @Transient
    @FieldIgnore
    private Integer asnUpdateFlag;
    @Transient
    @FieldIgnore
    private Integer positive;
    @Transient
    @FieldIgnore
    private Boolean poReturnFlag;
    @Transient
    @ApiModelProperty("外部反冲标识")
    private Integer externalReverseFlag;
    @Transient
    @ApiModelProperty("标记：是否需要进行金额转换")
    private boolean needChangeAmountEnable = true;
    @Transient
    @ApiModelProperty("外部系统事务编码")
    private String externalRcvTypeCode;
    @Transient
    @ApiModelProperty("外部系统接收事务代码")
    private String esTrxTypeCode;
    @Transient
    @ApiModelProperty("事务行es表映射类型")
    private String sourceTypeCode;
    @Transient
    @ApiModelProperty("原金额:含税")
    private BigDecimal orgTaxAmount;
    @Transient
    @ApiModelProperty("原金额:不含税")
    private BigDecimal orgNetAmount;
    @ApiModelProperty("入库数量")
    private Long attributeBigint1;
    @ApiModelProperty("入库单号")
    private String attributeVarchar1;
    @ApiModelProperty("资产系统采购订单号")
    private String attributeVarchar2;
    @ApiModelProperty("质保金额")
    private BigDecimal accpMetentionMoney;

    public RCWLRcvTrxLine() {}

    private static final String[] permitUpdateFieldName() {
        Class<RcvTrxLine> ownerClass = RcvTrxLine.class;
        List<String> list = new ArrayList();
        Field[] fields = ownerClass.getDeclaredFields();
        Field[] var3 = fields;
        int var4 = fields.length;

        for (int var5 = 0; var5 < var4; ++var5) {
            Field f = var3[var5];
            if (!Modifier.isFinal(f.getModifiers()) && !f.isAnnotationPresent(FieldIgnore.class)) {
                list.add(f.getName());
            }
        }

        return (String[]) list.toArray(new String[0]);
    }

    public BigDecimal getAccpMetentionMoney() {
        return accpMetentionMoney;
    }

    public void setAccpMetentionMoney(BigDecimal accpMetentionMoney) {
        this.accpMetentionMoney = accpMetentionMoney;
    }

    public Long getAttributeBigint1() {
        return attributeBigint1;
    }

    public void setAttributeBigint1(Long attributeBigint1) {
        this.attributeBigint1 = attributeBigint1;
    }

    public String getAttributeVarchar1() {
        return attributeVarchar1;
    }

    public void setAttributeVarchar1(String attributeVarchar1) {
        this.attributeVarchar1 = attributeVarchar1;
    }

    public String getAttributeVarchar2() {
        return attributeVarchar2;
    }

    public void setAttributeVarchar2(String attributeVarchar2) {
        this.attributeVarchar2 = attributeVarchar2;
    }

    public void changeInvoiceStatus() {
        if (this.invoicedQuantity.compareTo(BigDecimal.ZERO) > 0
                        && this.invoicedQuantity.compareTo(this.getQuantity()) < 0) {
            this.setInvoiceMatchedStatus("PART_INVOICED");
        } else if (this.invoicedQuantity.compareTo(this.getQuantity()) == 0) {
            this.setInvoiceMatchedStatus("INVOICE_COMPLETE");
            this.setInvoiceClosedFlag(Flag.YES);
        } else if (this.invoicedQuantity != null && this.invoicedQuantity.compareTo(BigDecimal.ZERO) != 0) {
            if (this.invoicedQuantity.compareTo(BigDecimal.ZERO) < 0
                            && this.invoicedQuantity.compareTo(this.getQuantity()) > 0) {
                this.setInvoiceMatchedStatus("PART_INVOICED");
            }
        } else {
            this.setInvoiceMatchedStatus("UNINVOICED");
        }

    }

    public void changeInvoiceStatusForCancel() {
        if (this.invoicedQuantity.compareTo(BigDecimal.ZERO) > 0
                        && this.invoicedQuantity.compareTo(this.getQuantity()) < 0) {
            this.setInvoiceMatchedStatus("PART_INVOICED");
        } else if (this.invoicedQuantity != null && this.invoicedQuantity.compareTo(BigDecimal.ZERO) != 0) {
            if (this.invoicedQuantity.compareTo(this.getQuantity()) == 0) {
                this.setInvoiceMatchedStatus("INVOICE_COMPLETE");
                this.setInvoiceClosedFlag(Flag.YES);
            } else if (this.invoicedQuantity.compareTo(BigDecimal.ZERO) < 0
                            && this.invoicedQuantity.compareTo(this.getQuantity()) > 0) {
                this.setInvoiceMatchedStatus("PART_INVOICED");
            }
        } else {
            this.setInvoiceMatchedStatus("UNINVOICED");
        }

    }

    public void configTrxType(RcvTrxTypeDTO type, Integer asnUpdateFlag, Integer poUpdateFlag,
                    RcvTrxHeader rcvTrxHeader) {
        this.receiveFlag = type.getReceiveFlag();
        this.deliverFlag = type.getDeliverFlag();
        this.asnFlag = type.getAsnFlag();
        this.poFlag = type.getPoFlag();
        if (StringUtils.equals("SRM", rcvTrxHeader.getSourceCode())) {
            this.asnUpdateFlag = Flag.YES;
            this.poUpdateFlag = Flag.YES;
        } else {
            this.asnUpdateFlag = asnUpdateFlag;
            this.poUpdateFlag = poUpdateFlag;
        }

        this.returnToSupplierFlag = type.getReturnToSupplierFlag();
        this.returnToReceivingFlag = type.getReturnToReceivingFlag();
        this.reverseFlag = type.getReverseFlag();
    }

    public Integer getPositive() {
        return this.positive;
    }

    public void setPositive(Integer positive) {
        this.positive = positive;
    }

    public Integer getCanCreateBillFlag() {
        return this.canCreateBillFlag;
    }

    public void setCanCreateBillFlag(Integer canCreateBillFlag) {
        this.canCreateBillFlag = canCreateBillFlag;
    }

    public Integer getReceiveFlag() {
        return this.receiveFlag;
    }

    public void setReceiveFlag(Integer receiveFlag) {
        this.receiveFlag = receiveFlag;
    }

    public Integer getDeliverFlag() {
        return this.deliverFlag;
    }

    public void setDeliverFlag(Integer deliverFlag) {
        this.deliverFlag = deliverFlag;
    }

    public Integer getReturnToSupplierFlag() {
        return this.returnToSupplierFlag;
    }

    public void setReturnToSupplierFlag(Integer returnToSupplierFlag) {
        this.returnToSupplierFlag = returnToSupplierFlag;
    }

    public Integer getReturnToReceivingFlag() {
        return this.returnToReceivingFlag;
    }

    public void setReturnToReceivingFlag(Integer returnToReceivingFlag) {
        this.returnToReceivingFlag = returnToReceivingFlag;
    }

    public Integer getPoFlag() {
        return this.poFlag;
    }

    public void setPoFlag(Integer poFlag) {
        this.poFlag = poFlag;
    }

    public Integer getAsnFlag() {
        return this.asnFlag;
    }

    public void setAsnFlag(Integer asnFlag) {
        this.asnFlag = asnFlag;
    }

    public Integer getPoUpdateFlag() {
        return this.poUpdateFlag;
    }

    public void setPoUpdateFlag(Integer poUpdateFlag) {
        this.poUpdateFlag = poUpdateFlag;
    }

    public Integer getAsnUpdateFlag() {
        return this.asnUpdateFlag;
    }

    public void setAsnUpdateFlag(Integer asnUpdateFlag) {
        this.asnUpdateFlag = asnUpdateFlag;
    }

    public String getSourceCode() {
        return this.sourceCode;
    }

    public void setSourceCode(String sourceCode) {
        this.sourceCode = sourceCode;
    }

    public String getRateType() {
        return this.rateType;
    }

    public void setRateType(String rateType) {
        this.rateType = rateType;
    }

    public String getExternalSystemCode() {
        return this.externalSystemCode;
    }

    public void setExternalSystemCode(String externalSystemCode) {
        this.externalSystemCode = externalSystemCode;
    }

    public String getEsRcvTrxLineId() {
        return this.esRcvTrxLineId;
    }

    public void setEsRcvTrxLineId(String esRcvTrxLineId) {
        this.esRcvTrxLineId = esRcvTrxLineId;
    }

    public String getCompanyName() {
        return this.companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Long getRcvTrxLineId() {
        return this.rcvTrxLineId;
    }

    public void setRcvTrxLineId(Long rcvTrxLineId) {
        this.rcvTrxLineId = rcvTrxLineId;
    }

    public Long getRcvTrxHeaderId() {
        return this.rcvTrxHeaderId;
    }

    public void setRcvTrxHeaderId(Long rcvTrxHeaderId) {
        this.rcvTrxHeaderId = rcvTrxHeaderId;
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

    public Long getTrxLineNum() {
        return this.trxLineNum;
    }

    public void setTrxLineNum(Long trxLineNum) {
        this.trxLineNum = trxLineNum;
    }

    public String getDisplayTrxLineNum() {
        return this.displayTrxLineNum;
    }

    public void setDisplayTrxLineNum(String displayTrxLineNum) {
        this.displayTrxLineNum = displayTrxLineNum;
    }

    public Date getTrxDate() {
        return this.trxDate;
    }

    public void setTrxDate(Date trxDate) {
        this.trxDate = trxDate;
    }

    public Long getTrxYear() {
        return this.trxYear;
    }

    public void setTrxYear(Long trxYear) {
        this.trxYear = trxYear;
    }

    public Long getRcvTrxTypeId() {
        return this.rcvTrxTypeId;
    }

    public void setRcvTrxTypeId(Long rcvTrxTypeId) {
        this.rcvTrxTypeId = rcvTrxTypeId;
    }

    public String getMoveReason() {
        return this.moveReason;
    }

    public void setMoveReason(String moveReason) {
        this.moveReason = moveReason;
    }

    public BigDecimal getQuantity() {
        return this.quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public String getStockType() {
        return this.stockType;
    }

    public void setStockType(String stockType) {
        this.stockType = stockType;
    }

    public String getCurrencyCode() {
        return this.currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getPeriodName() {
        return this.periodName;
    }

    public void setPeriodName(String periodName) {
        this.periodName = periodName;
    }

    public BigDecimal getNetPrice() {
        return this.netPrice;
    }

    public void setNetPrice(BigDecimal netPrice) {
        this.netPrice = netPrice;
    }

    public BigDecimal getTaxIncludedPrice() {
        return this.taxIncludedPrice;
    }

    public void setTaxIncludedPrice(BigDecimal taxIncludedPrice) {
        this.taxIncludedPrice = taxIncludedPrice;
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

    public BigDecimal getTaxIncludedAmount() {
        return this.taxIncludedAmount;
    }

    public void setTaxIncludedAmount(BigDecimal taxIncludedAmount) {
        this.taxIncludedAmount = taxIncludedAmount;
    }

    public Long getTaxId() {
        return this.taxId;
    }

    public void setTaxId(Long taxId) {
        this.taxId = taxId;
    }

    public String getSourceTypeCode() {
        return this.sourceTypeCode;
    }

    public void setSourceTypeCode(String sourceTypeCode) {
        this.sourceTypeCode = sourceTypeCode;
    }

    public BigDecimal getTaxRate() {
        return this.taxRate;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    public Long getItemId() {
        return this.itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
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

    public Long getCategoryId() {
        return this.categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getUomId() {
        return this.uomId;
    }

    public void setUomId(Long uomId) {
        this.uomId = uomId;
    }

    public Long getCompanyId() {
        return this.companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getOuId() {
        return this.ouId;
    }

    public void setOuId(Long ouId) {
        this.ouId = ouId;
    }

    public Long getPurOrganizationId() {
        return this.purOrganizationId;
    }

    public void setPurOrganizationId(Long purOrganizationId) {
        this.purOrganizationId = purOrganizationId;
    }

    public Long getInvOrganizationId() {
        return this.invOrganizationId;
    }

    public void setInvOrganizationId(Long invOrganizationId) {
        this.invOrganizationId = invOrganizationId;
    }

    public Long getInventoryId() {
        return this.inventoryId;
    }

    public void setInventoryId(Long inventoryId) {
        this.inventoryId = inventoryId;
    }

    public Long getLocatorId() {
        return this.locatorId;
    }

    public void setLocatorId(Long locatorId) {
        this.locatorId = locatorId;
    }

    public Long getSupplierId() {
        return this.supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
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

    public Long getSupplierSiteId() {
        return this.supplierSiteId;
    }

    public void setSupplierSiteId(Long supplierSiteId) {
        this.supplierSiteId = supplierSiteId;
    }

    public Long getSupplierCompanyId() {
        return this.supplierCompanyId;
    }

    public void setSupplierCompanyId(Long supplierCompanyId) {
        this.supplierCompanyId = supplierCompanyId;
    }

    public String getSupplierCompanyName() {
        return this.supplierCompanyName;
    }

    public void setSupplierCompanyName(String supplierCompanyName) {
        this.supplierCompanyName = supplierCompanyName;
    }

    public Long getSupplierOuId() {
        return this.supplierOuId;
    }

    public void setSupplierOuId(Long supplierOuId) {
        this.supplierOuId = supplierOuId;
    }

    public BigDecimal getEnteredTrxPrice() {
        return this.enteredTrxPrice;
    }

    public void setEnteredTrxPrice(BigDecimal enteredTrxPrice) {
        this.enteredTrxPrice = enteredTrxPrice;
    }

    public BigDecimal getEnteredTrxAmount() {
        return this.enteredTrxAmount;
    }

    public void setEnteredTrxAmount(BigDecimal enteredTrxAmount) {
        this.enteredTrxAmount = enteredTrxAmount;
    }

    public String getEnteredCurrencyCode() {
        return this.enteredCurrencyCode;
    }

    public void setEnteredCurrencyCode(String enteredCurrencyCode) {
        this.enteredCurrencyCode = enteredCurrencyCode;
    }

    public Date getExchangeRateDate() {
        return this.exchangeRateDate;
    }

    public void setExchangeRateDate(Date exchangeRateDate) {
        this.exchangeRateDate = exchangeRateDate;
    }

    public BigDecimal getExchangeRate() {
        return this.exchangeRate;
    }

    public void setExchangeRate(BigDecimal exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public String getLotNum() {
        return this.lotNum;
    }

    public void setLotNum(String lotNum) {
        this.lotNum = lotNum;
    }

    public String getSerialNum() {
        return this.serialNum;
    }

    public void setSerialNum(String serialNum) {
        this.serialNum = serialNum;
    }

    public Long getParentTrxLineId() {
        return this.parentTrxLineId;
    }

    public void setParentTrxLineId(Long parentTrxLineId) {
        this.parentTrxLineId = parentTrxLineId;
    }

    public Long getFromPoHeaderId() {
        return this.fromPoHeaderId;
    }

    public void setFromPoHeaderId(Long fromPoHeaderId) {
        this.fromPoHeaderId = fromPoHeaderId;
    }

    public Long getFromPoLineId() {
        return this.fromPoLineId;
    }

    public void setFromPoLineId(Long fromPoLineId) {
        this.fromPoLineId = fromPoLineId;
    }

    public Long getFromPoLineLocationId() {
        return this.fromPoLineLocationId;
    }

    public void setFromPoLineLocationId(Long fromPoLineLocationId) {
        this.fromPoLineLocationId = fromPoLineLocationId;
    }

    public Long getFromPoDistributionId() {
        return this.fromPoDistributionId;
    }

    public void setFromPoDistributionId(Long fromPoDistributionId) {
        this.fromPoDistributionId = fromPoDistributionId;
    }

    public Long getFromAsnHeaderId() {
        return this.fromAsnHeaderId;
    }

    public void setFromAsnHeaderId(Long fromAsnHeaderId) {
        this.fromAsnHeaderId = fromAsnHeaderId;
    }

    public Long getFromAsnLineId() {
        return this.fromAsnLineId;
    }

    public void setFromAsnLineId(Long fromAsnLineId) {
        this.fromAsnLineId = fromAsnLineId;
    }

    public Long getTermsId() {
        return this.termsId;
    }

    public void setTermsId(Long termsId) {
        this.termsId = termsId;
    }

    public Long getAgentId() {
        return this.agentId;
    }

    public void setAgentId(Long agentId) {
        this.agentId = agentId;
    }

    public Integer getReverseFlag() {
        return this.reverseFlag;
    }

    public void setReverseFlag(Integer reverseFlag) {
        this.reverseFlag = reverseFlag;
    }

    public Long getReverseTrxLineId() {
        return this.reverseTrxLineId;
    }

    public void setReverseTrxLineId(Long reverseTrxLineId) {
        this.reverseTrxLineId = reverseTrxLineId;
    }

    public Integer getNeedInvoiceFlag() {
        return this.needInvoiceFlag;
    }

    public void setNeedInvoiceFlag(Integer needInvoiceFlag) {
        this.needInvoiceFlag = needInvoiceFlag;
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

    public BigDecimal getInvoicedQuantity() {
        return this.invoicedQuantity;
    }

    public void setInvoicedQuantity(BigDecimal invoicedQuantity) {
        this.invoicedQuantity = invoicedQuantity;
    }

    public Integer getBillMatchedFlag() {
        return this.billMatchedFlag;
    }

    public void setBillMatchedFlag(Integer billMatchedFlag) {
        this.billMatchedFlag = billMatchedFlag;
    }

    public Long getMatchedBillDetailId() {
        return this.matchedBillDetailId;
    }

    public void setMatchedBillDetailId(Long matchedBillDetailId) {
        this.matchedBillDetailId = matchedBillDetailId;
    }

    public String getMatchedBillNum() {
        return this.matchedBillNum;
    }

    public void setMatchedBillNum(String matchedBillNum) {
        this.matchedBillNum = matchedBillNum;
    }

    public Long getTrxSourceTypeId() {
        return this.trxSourceTypeId;
    }

    public void setTrxSourceTypeId(Long trxSourceTypeId) {
        this.trxSourceTypeId = trxSourceTypeId;
    }

    public Long getTrxSourceLineId() {
        return this.trxSourceLineId;
    }

    public void setTrxSourceLineId(Long trxSourceLineId) {
        this.trxSourceLineId = trxSourceLineId;
    }

    public Integer getErpParentConvertFlag() {
        return this.erpParentConvertFlag;
    }

    public void setErpParentConvertFlag(Integer erpParentConvertFlag) {
        this.erpParentConvertFlag = erpParentConvertFlag;
    }

    public String getErpParentTrxLineId() {
        return this.erpParentTrxLineId;
    }

    public void setErpParentTrxLineId(String erpParentTrxLineId) {
        this.erpParentTrxLineId = erpParentTrxLineId;
    }

    public String getErpParentTrxNum() {
        return this.erpParentTrxNum;
    }

    public void setErpParentTrxNum(String erpParentTrxNum) {
        this.erpParentTrxNum = erpParentTrxNum;
    }

    public String getErpParentTrxLineNum() {
        return this.erpParentTrxLineNum;
    }

    public void setErpParentTrxLineNum(String erpParentTrxLineNum) {
        this.erpParentTrxLineNum = erpParentTrxLineNum;
    }

    public Long getErpParentTrxYear() {
        return this.erpParentTrxYear;
    }

    public void setErpParentTrxYear(Long erpParentTrxYear) {
        this.erpParentTrxYear = erpParentTrxYear;
    }

    public String getErpSourceTrxNum() {
        return this.erpSourceTrxNum;
    }

    public void setErpSourceTrxNum(String erpSourceTrxNum) {
        this.erpSourceTrxNum = erpSourceTrxNum;
    }

    public String getErpSourceTrxLineId() {
        return this.erpSourceTrxLineId;
    }

    public void setErpSourceTrxLineId(String erpSourceTrxLineId) {
        this.erpSourceTrxLineId = erpSourceTrxLineId;
    }

    public String getErpSourceTrxLineNum() {
        return this.erpSourceTrxLineNum;
    }

    public void setErpSourceTrxLineNum(String erpSourceTrxLineNum) {
        this.erpSourceTrxLineNum = erpSourceTrxLineNum;
    }

    public Long getErpSourceTrxYear() {
        return this.erpSourceTrxYear;
    }

    public void setErpSourceTrxYear(Long erpSourceTrxYear) {
        this.erpSourceTrxYear = erpSourceTrxYear;
    }

    public Date getErpCreationDate() {
        return this.erpCreationDate;
    }

    public void setErpCreationDate(Date erpCreationDate) {
        this.erpCreationDate = erpCreationDate;
    }

    public Date getErpLastUpdateDate() {
        return this.erpLastUpdateDate;
    }

    public void setErpLastUpdateDate(Date erpLastUpdateDate) {
        this.erpLastUpdateDate = erpLastUpdateDate;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getEnabledFlag() {
        return this.enabledFlag;
    }

    public void setEnabledFlag(Integer enabledFlag) {
        this.enabledFlag = enabledFlag;
    }

    public Date getStartDate() {
        return this.startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return this.endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Long getOrderSeq() {
        return this.orderSeq;
    }

    public void setOrderSeq(Long orderSeq) {
        this.orderSeq = orderSeq;
    }

    public Integer getOutputStockFlag() {
        return this.outputStockFlag;
    }

    public void setOutputStockFlag(Integer outputStockFlag) {
        this.outputStockFlag = outputStockFlag;
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

    public Integer getFullReversedFlag() {
        return this.fullReversedFlag;
    }

    public void setFullReversedFlag(Integer fullReversedFlag) {
        this.fullReversedFlag = fullReversedFlag;
    }

    public Integer getExternalReverseFlag() {
        return this.externalReverseFlag;
    }

    public void setExternalReverseFlag(Integer externalReverseFlag) {
        this.externalReverseFlag = externalReverseFlag;
    }

    public Date getRealityReceiveDate() {
        return this.realityReceiveDate;
    }

    public void setRealityReceiveDate(Date realityReceiveDate) {
        this.realityReceiveDate = realityReceiveDate;
    }

    public String getEsTrxTypeCode() {
        return this.esTrxTypeCode;
    }

    public void setEsTrxTypeCode(String esTrxTypeCode) {
        this.esTrxTypeCode = esTrxTypeCode;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof RcvTrxLineEs)) {
            return super.equals(obj);
        } else {
            RcvTrxLineEs rcvTrxLineEs = (RcvTrxLineEs) obj;
            if (OrderUtil.equals(rcvTrxLineEs.getExternalSystemCode(), this.externalSystemCode)
                            && (rcvTrxLineEs.getEsRcvTrxLineId() != null
                                            && rcvTrxLineEs.getEsRcvTrxLineId().equals(this.displayTrxLineNum)
                                            || rcvTrxLineEs.getEsTrxLineNum() != null && rcvTrxLineEs.getEsTrxLineNum()
                                                            .equals(this.displayTrxLineNum))
                            && (Integer) Optional.ofNullable(this.externalReverseFlag).orElse(0) == 0) {
                LOGGER.debug(" this.externalSystemCode:{},this.displayTrxLineNum:{},rcvTrxLineEs.getExternalSystemCode():{},rcvTrxLineEs.getEsTrxLineNum():{},rcvTrxLineEs.getEsRcvTrxLineId():{}",
                                new Object[] {this.externalSystemCode, this.displayTrxLineNum,
                                        rcvTrxLineEs.getExternalSystemCode(), rcvTrxLineEs.getEsRcvTrxLineId(),
                                        rcvTrxLineEs.getEsTrxLineNum()});
                return true;
            } else {
                return false;
            }
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(new Object[] {this.externalSystemCode, this.displayTrxLineNum});
    }

    public boolean checkAsnFIFORcvConfig() {
        return (Objects.equals(Flag.YES, this.receiveFlag) || Objects.equals(Flag.YES, this.returnToSupplierFlag)
                        || Objects.equals(Flag.YES, this.returnToReceivingFlag))
                        && Objects.equals(Flag.YES, this.asnFlag) && Objects.equals(Flag.YES, this.asnUpdateFlag);
    }

    public boolean checkAsnExactMatchRcvConfig() {
        return (Objects.equals(Flag.YES, this.receiveFlag) || Objects.equals(Flag.YES, this.returnToSupplierFlag))
                        && Objects.equals(Flag.YES, this.asnFlag) && Objects.equals(Flag.YES, this.asnUpdateFlag);
    }

    public boolean checkTrxForReturnToReceiving() {
        return Objects.equals(Flag.NO, this.receiveFlag) && Objects.equals(Flag.NO, this.returnToSupplierFlag)
                        && Objects.equals(Flag.YES, this.returnToReceivingFlag)
                        && Objects.equals(Flag.YES, this.asnFlag) && Objects.equals(Flag.YES, this.asnUpdateFlag);
    }

    public boolean chechFromPoHeaderEnable() {
        return this.getFromPoHeaderId() != null || this.getFromPoLineId() != null
                        || this.getFromPoLineLocationId() != null;
    }

    public Boolean getPoReturnFlag() {
        return this.poReturnFlag;
    }

    public void setPoReturnFlag(Boolean poReturnFlag) {
        this.poReturnFlag = poReturnFlag;
    }

    public Integer getEsCloseFlag() {
        return this.esCloseFlag;
    }

    public void setEsCloseFlag(Integer esCloseFlag) {
        this.esCloseFlag = esCloseFlag;
    }

    public boolean isNeedChangeAmountEnable() {
        return this.needChangeAmountEnable;
    }

    public void setNeedChangeAmountEnable(boolean needChangeAmountEnable) {
        this.needChangeAmountEnable = needChangeAmountEnable;
    }

    public Integer getFromOrderFlag() {
        return this.fromOrderFlag;
    }

    public void setFromOrderFlag(Integer fromOrderFlag) {
        this.fromOrderFlag = fromOrderFlag;
    }

    public Long getFromRcvTrxLineId() {
        return this.fromRcvTrxLineId;
    }

    public void setFromRcvTrxLineId(Long fromRcvTrxLineId) {
        this.fromRcvTrxLineId = fromRcvTrxLineId;
    }

    public String getExternalRcvTypeCode() {
        return this.externalRcvTypeCode;
    }

    public void setExternalRcvTypeCode(String externalRcvTypeCode) {
        this.externalRcvTypeCode = externalRcvTypeCode;
    }

    public String getSinvLineAttachmentUuid() {
        return this.sinvLineAttachmentUuid;
    }

    public void setSinvLineAttachmentUuid(final String sinvLineAttachmentUuid) {
        this.sinvLineAttachmentUuid = sinvLineAttachmentUuid;
    }

    public BigDecimal getOccupiedTaxAmount() {
        return this.occupiedTaxAmount;
    }

    public void setOccupiedTaxAmount(final BigDecimal occupiedTaxAmount) {
        this.occupiedTaxAmount = occupiedTaxAmount;
    }

    public BigDecimal getReversedTaxAmount() {
        return this.reversedTaxAmount;
    }

    public void setReversedTaxAmount(final BigDecimal reversedTaxAmount) {
        this.reversedTaxAmount = reversedTaxAmount;
    }

    public Integer getCompleteFlag() {
        return this.completeFlag;
    }

    public void setCompleteFlag(final Integer completeFlag) {
        this.completeFlag = completeFlag;
    }

    public BigDecimal getOccupiedQuantity() {
        return this.occupiedQuantity;
    }

    public void setOccupiedQuantity(final BigDecimal occupiedQuantity) {
        this.occupiedQuantity = occupiedQuantity;
    }

    public BigDecimal getReversedQuantity() {
        return this.reversedQuantity;
    }

    public void setReversedQuantity(final BigDecimal reversedQuantity) {
        this.reversedQuantity = reversedQuantity;
    }

    public Integer getDeleteFlag() {
        return this.deleteFlag;
    }

    public void setDeleteFlag(final Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public BigDecimal getOrgQuantity() {
        return this.orgQuantity;
    }

    public void setOrgQuantity(final BigDecimal orgQuantity) {
        this.orgQuantity = orgQuantity;
    }

    public BigDecimal getBillMatchedQuantity() {
        return this.billMatchedQuantity;
    }

    public void setBillMatchedQuantity(BigDecimal billMatchedQuantity) {
        this.billMatchedQuantity = billMatchedQuantity;
    }

    public Long getPrHeaderId() {
        return this.prHeaderId;
    }

    public void setPrHeaderId(Long prHeaderId) {
        this.prHeaderId = prHeaderId;
    }

    public Long getPrLineId() {
        return this.prLineId;
    }

    public void setPrLineId(Long prLineId) {
        this.prLineId = prLineId;
    }

    public BigDecimal getOrgTaxAmount() {
        return this.orgTaxAmount;
    }

    public void setOrgTaxAmount(final BigDecimal orgTaxAmount) {
        this.orgTaxAmount = orgTaxAmount;
    }

    public BigDecimal getOrgNetAmount() {
        return this.orgNetAmount;
    }

    public void setOrgNetAmount(final BigDecimal orgNetAmount) {
        this.orgNetAmount = orgNetAmount;
    }

    public BigDecimal getReverseOccupiedQuantity() {
        return this.reverseOccupiedQuantity;
    }

    public void setReverseOccupiedQuantity(BigDecimal reverseOccupiedQuantity) {
        this.reverseOccupiedQuantity = reverseOccupiedQuantity;
    }

    public BigDecimal getReverseOccupiedTaxAmount() {
        return this.reverseOccupiedTaxAmount;
    }

    public void setReverseOccupiedTaxAmount(BigDecimal reverseOccupiedTaxAmount) {
        this.reverseOccupiedTaxAmount = reverseOccupiedTaxAmount;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
