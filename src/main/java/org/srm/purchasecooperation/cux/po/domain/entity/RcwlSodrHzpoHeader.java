package org.srm.purchasecooperation.cux.po.domain.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.choerodon.mybatis.annotation.ModifyAudit;
import io.choerodon.mybatis.annotation.VersionAudit;
import io.choerodon.mybatis.domain.AuditDomain;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 华住订单头
 *
 * @author jie.wang05@hand-china.com 2021-10-25 16:36:06
 */
@ApiModel("华住订单头")
@VersionAudit
@ModifyAudit
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@Table(name = "scux_rcwl_sodr_hzpo_header")
public class RcwlSodrHzpoHeader extends AuditDomain {

    public static final String FIELD_PO_HEADER_ID = "poHeaderId";
    public static final String FIELD_TENANT_ID = "tenantId";
    public static final String FIELD_PO_NUM = "poNum";
    public static final String FIELD_STATUS_CODE = "statusCode";
    public static final String FIELD_PURCHASE_ORG_NUM = "purchaseOrgNum";
    public static final String FIELD_PURCHASE_ORG_NAME = "purchaseOrgName";
    public static final String FIELD_PO_TYPE = "poType";
    public static final String FIELD_ORDER_DATE = "orderDate";
    public static final String FIELD_CONFIRMED_DATE = "confirmedDate";
    public static final String FIELD_ESTIMATED_SHIPPING_DATE = "estimatedShippingDate";
    public static final String FIELD_FIRST_SHIPPING_DATE = "firstShippingDate";
    public static final String FIELD_CONFIRM_RECEIPT_DATE = "confirmReceiptDate";
    public static final String FIELD_UNIFIED_SOCIAL_CODE = "unifiedSocialCode";
    public static final String FIELD_PAYMENT_CHANNEL = "paymentChannel";
    public static final String FIELD_ORDERER = "orderer";
    public static final String FIELD_TAX_INCLUDE_AMOUNT = "taxIncludeAmount";
    public static final String FIELD_FREIGHT_FEE = "freightFee";
    public static final String FIELD_INSTALLATION_FEE = "installationFee";
    public static final String FIELD_DISCOUNTED_PRICE = "discountedPrice";
    public static final String FIELD_DISCOUNTED_REASON = "discountedReason";
    public static final String FIELD_SHIPPING_ADDRESS_PROVINCE = "shippingAddressProvince";
    public static final String FIELD_SHIPPING_ADDRESS_CITY = "shippingAddressCity";
    public static final String FIELD_HOTEL_BRAND = "hotelBrand";
    public static final String FIELD_HOTEL_OPERATING_STATUS = "hotelOperatingStatus";
    public static final String FIELD_BUYER_TYPE = "buyerType";
    public static final String FIELD_BUYER_CLASS = "buyerClass";
    public static final String FIELD_CONTRACT_PRICE = "contractPrice";
    public static final String FIELD_COMMISSION = "commission";
    public static final String FIELD_PAYMENT_TERMS = "paymentTerms";

    //
    // 业务方法(按public protected private顺序排列)
    // ------------------------------------------------------------------------------

    //
    // 数据库字段
    // ------------------------------------------------------------------------------


    @ApiModelProperty("头ID，主键")
    @Id
    @GeneratedValue
    private Long poHeaderId;
    @ApiModelProperty(value = "租户ID", required = true)
    @NotNull
    private Long tenantId;
    @ApiModelProperty(value = "订单编号", required = true)
    @NotBlank
    private String poNum;
    @ApiModelProperty(value = "订单状态,值集SCUX_RCWL_HZPO_STATUS", required = true)
    @NotBlank
    private String statusCode;
    @ApiModelProperty(value = "酒店编号", required = true)
    @NotBlank
    private String purchaseOrgNum;
    @ApiModelProperty(value = "酒店名称", required = true)
    @NotBlank
    private String purchaseOrgName;
    @ApiModelProperty(value = "订单类型", required = true)
    @NotBlank
    private String poType;
    @ApiModelProperty(value = "下单日期", required = true)
    @NotNull
    private Date orderDate;
    @ApiModelProperty(value = "供应商确认日期")
    private Date confirmedDate;
    @ApiModelProperty(value = "预计发货日期")
    private Date estimatedShippingDate;
    @ApiModelProperty(value = "首次发货日期")
    private Date firstShippingDate;
    @ApiModelProperty(value = "确认收货日期")
    private Date confirmReceiptDate;
    @ApiModelProperty(value = "供应商统一社会信用代码", required = true)
    @NotBlank
    private String unifiedSocialCode;
    @ApiModelProperty(value = "支付渠道")
    private String paymentChannel;
    @ApiModelProperty(value = "下单人", required = true)
    @NotBlank
    private String orderer;
    @ApiModelProperty(value = "订单含税总额", required = true)
    @NotNull
    private BigDecimal taxIncludeAmount;
    @ApiModelProperty(value = "运费", required = true)
    @NotNull
    private BigDecimal freightFee;
    @ApiModelProperty(value = "安装费", required = true)
    @NotNull
    private BigDecimal installationFee;
    @ApiModelProperty(value = "优惠金额", required = true)
    @NotNull
    private BigDecimal discountedPrice;
    @ApiModelProperty(value = "优惠原因", required = true)
    @NotBlank
    private String discountedReason;
    @ApiModelProperty(value = "收货地址（省）", required = true)
    @NotBlank
    private String shippingAddressProvince;
    @ApiModelProperty(value = "收货地址（市）", required = true)
    @NotBlank
    private String shippingAddressCity;
    @ApiModelProperty(value = "酒店品牌", required = true)
    @NotBlank
    private String hotelBrand;
    @ApiModelProperty(value = "酒店运营状态", required = true)
    @NotBlank
    private String hotelOperatingStatus;
    @ApiModelProperty(value = "采购方类型", required = true)
    @NotBlank
    private String buyerType;
    @ApiModelProperty(value = "等级", required = true)
    @NotBlank
    private String buyerClass;
    @ApiModelProperty(value = "订单协议价", required = true)
    @NotNull
    private BigDecimal contractPrice;
    @ApiModelProperty(value = "佣金", required = true)
    @NotNull
    private BigDecimal commission;
    @ApiModelProperty(value = "账期模式", required = true)
    @NotBlank
    private String paymentTerms;

    //
    // 非数据库字段
    // ------------------------------------------------------------------------------
    @ApiModelProperty(value = "供应商编号")
    @Transient
    private String supplierNum;
    @ApiModelProperty(value = "供应商名称")
    @Transient
    private String supplierName;

    //
    // getter/setter
    // ------------------------------------------------------------------------------

    public String getSupplierNum() {
        return supplierNum;
    }

    public void setSupplierNum(String supplierNum) {
        this.supplierNum = supplierNum;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    /**
     * @return
     */
    public Long getPoHeaderId() {
        return poHeaderId;
    }

    public void setPoHeaderId(Long poHeaderId) {
        this.poHeaderId = poHeaderId;
    }

    /**
     * @return
     */
    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    /**
     * @return 订单编号
     */
    public String getPoNum() {
        return poNum;
    }

    public void setPoNum(String poNum) {
        this.poNum = poNum;
    }

    /**
     * @return 订单状态, 值集SCUX_RCWL_HZPO_STATUS
     */
    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    /**
     * @return 酒店编号
     */
    public String getPurchaseOrgNum() {
        return purchaseOrgNum;
    }

    public void setPurchaseOrgNum(String purchaseOrgNum) {
        this.purchaseOrgNum = purchaseOrgNum;
    }

    /**
     * @return 酒店名称
     */
    public String getPurchaseOrgName() {
        return purchaseOrgName;
    }

    public void setPurchaseOrgName(String purchaseOrgName) {
        this.purchaseOrgName = purchaseOrgName;
    }

    /**
     * @return 订单类型
     */
    public String getPoType() {
        return poType;
    }

    public void setPoType(String poType) {
        this.poType = poType;
    }

    /**
     * @return 下单日期
     */
    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    /**
     * @return 供应商确认日期
     */
    public Date getConfirmedDate() {
        return confirmedDate;
    }

    public void setConfirmedDate(Date confirmedDate) {
        this.confirmedDate = confirmedDate;
    }

    /**
     * @return 预计发货日期
     */
    public Date getEstimatedShippingDate() {
        return estimatedShippingDate;
    }

    public void setEstimatedShippingDate(Date estimatedShippingDate) {
        this.estimatedShippingDate = estimatedShippingDate;
    }

    /**
     * @return 首次发货日期
     */
    public Date getFirstShippingDate() {
        return firstShippingDate;
    }

    public void setFirstShippingDate(Date firstShippingDate) {
        this.firstShippingDate = firstShippingDate;
    }

    /**
     * @return 确认收货日期
     */
    public Date getConfirmReceiptDate() {
        return confirmReceiptDate;
    }

    public void setConfirmReceiptDate(Date confirmReceiptDate) {
        this.confirmReceiptDate = confirmReceiptDate;
    }

    /**
     * @return 供应商统一社会信用代码
     */
    public String getUnifiedSocialCode() {
        return unifiedSocialCode;
    }

    public void setUnifiedSocialCode(String unifiedSocialCode) {
        this.unifiedSocialCode = unifiedSocialCode;
    }

    /**
     * @return 支付渠道
     */
    public String getPaymentChannel() {
        return paymentChannel;
    }

    public void setPaymentChannel(String paymentChannel) {
        this.paymentChannel = paymentChannel;
    }

    /**
     * @return 下单人
     */
    public String getOrderer() {
        return orderer;
    }

    public void setOrderer(String orderer) {
        this.orderer = orderer;
    }

    /**
     * @return 订单含税总额
     */
    public BigDecimal getTaxIncludeAmount() {
        return taxIncludeAmount;
    }

    public void setTaxIncludeAmount(BigDecimal taxIncludeAmount) {
        this.taxIncludeAmount = taxIncludeAmount;
    }

    /**
     * @return 运费
     */
    public BigDecimal getFreightFee() {
        return freightFee;
    }

    public void setFreightFee(BigDecimal freightFee) {
        this.freightFee = freightFee;
    }

    /**
     * @return 安装费
     */
    public BigDecimal getInstallationFee() {
        return installationFee;
    }

    public void setInstallationFee(BigDecimal installationFee) {
        this.installationFee = installationFee;
    }

    /**
     * @return 优惠金额
     */
    public BigDecimal getDiscountedPrice() {
        return discountedPrice;
    }

    public void setDiscountedPrice(BigDecimal discountedPrice) {
        this.discountedPrice = discountedPrice;
    }

    /**
     * @return 优惠原因
     */
    public String getDiscountedReason() {
        return discountedReason;
    }

    public void setDiscountedReason(String discountedReason) {
        this.discountedReason = discountedReason;
    }

    /**
     * @return 收货地址（省）
     */
    public String getShippingAddressProvince() {
        return shippingAddressProvince;
    }

    public void setShippingAddressProvince(String shippingAddressProvince) {
        this.shippingAddressProvince = shippingAddressProvince;
    }

    /**
     * @return 收货地址（市）
     */
    public String getShippingAddressCity() {
        return shippingAddressCity;
    }

    public void setShippingAddressCity(String shippingAddressCity) {
        this.shippingAddressCity = shippingAddressCity;
    }

    /**
     * @return 酒店品牌
     */
    public String getHotelBrand() {
        return hotelBrand;
    }

    public void setHotelBrand(String hotelBrand) {
        this.hotelBrand = hotelBrand;
    }

    /**
     * @return 酒店运营状态
     */
    public String getHotelOperatingStatus() {
        return hotelOperatingStatus;
    }

    public void setHotelOperatingStatus(String hotelOperatingStatus) {
        this.hotelOperatingStatus = hotelOperatingStatus;
    }

    /**
     * @return 采购方类型
     */
    public String getBuyerType() {
        return buyerType;
    }

    public void setBuyerType(String buyerType) {
        this.buyerType = buyerType;
    }

    /**
     * @return 等级
     */
    public String getBuyerClass() {
        return buyerClass;
    }

    public void setBuyerClass(String buyerClass) {
        this.buyerClass = buyerClass;
    }

    /**
     * @return 订单协议价
     */
    public BigDecimal getContractPrice() {
        return contractPrice;
    }

    public void setContractPrice(BigDecimal contractPrice) {
        this.contractPrice = contractPrice;
    }

    /**
     * @return 佣金
     */
    public BigDecimal getCommission() {
        return commission;
    }

    public void setCommission(BigDecimal commission) {
        this.commission = commission;
    }

    /**
     * @return 账期模式
     */
    public String getPaymentTerms() {
        return paymentTerms;
    }

    public void setPaymentTerms(String paymentTerms) {
        this.paymentTerms = paymentTerms;
    }

}
