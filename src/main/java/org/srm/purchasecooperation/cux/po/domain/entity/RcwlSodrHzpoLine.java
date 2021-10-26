package org.srm.purchasecooperation.cux.po.domain.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.validation.constraints.NotBlank;

import io.choerodon.mybatis.domain.AuditDomain;

import java.math.BigDecimal;

import io.choerodon.mybatis.annotation.ModifyAudit;
import io.choerodon.mybatis.annotation.VersionAudit;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 华住订单行
 *
 * @author jie.wang05@hand-china.com 2021-10-25 16:36:06
 */
@ApiModel("华住订单行")
@VersionAudit
@ModifyAudit
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@Table(name = "scux_rcwl_sodr_hzpo_line")
public class RcwlSodrHzpoLine extends AuditDomain {

    public static final String FIELD_PO_LINE_ID = "poLineId";
    public static final String FIELD_TENANT_ID = "tenantId";
    public static final String FIELD_PO_HEADER_ID = "poHeaderId";
    public static final String FIELD_LINE_STATUS = "lineStatus";
    public static final String FIELD_LINE_NUM = "lineNum";
    public static final String FIELD_SKU_CATEGORY_CODE = "skuCategoryCode";
    public static final String FIELD_SKU_NO = "skuNo";
    public static final String FIELD_SKU_NAME = "skuName";
    public static final String FIELD_UNIT_PRICE = "unitPrice";
    public static final String FIELD_QUANTITY = "quantity";
    public static final String FIELD_LINE_AMOUNT = "lineAmount";
    public static final String FIELD_CONTRACT_LINE_AMOUNT = "contractLineAmount";
    public static final String FIELD_TAX_RATE = "taxRate";

    //
    // 业务方法(按public protected private顺序排列)
    // ------------------------------------------------------------------------------

    //
    // 数据库字段
    // ------------------------------------------------------------------------------


    @ApiModelProperty("行ID，主键")
    @Id
    @GeneratedValue
    private Long poLineId;
    @ApiModelProperty(value = "租户ID")
    private Long tenantId;
    @ApiModelProperty(value = "头ID")
    private Long poHeaderId;
    @ApiModelProperty(value = "行状态")
    private String lineStatus;
    @ApiModelProperty(value = "行号")
    private String lineNum;
    @ApiModelProperty(value = "商品分类编号")
    private String skuCategoryCode;
    @ApiModelProperty(value = "商品编号")
    private String skuNo;
    @ApiModelProperty(value = "商品名称")
    private String skuName;
    @ApiModelProperty(value = "销售单价")
    private BigDecimal unitPrice;
    @ApiModelProperty(value = "购买数量")
    private BigDecimal quantity;
    @ApiModelProperty(value = "小计")
    private BigDecimal lineAmount;
    @ApiModelProperty(value = "协议价")
    private BigDecimal contractLineAmount;
    @ApiModelProperty(value = "税率")
    private Long taxRate;

    //
    // 非数据库字段
    // ------------------------------------------------------------------------------

    //
    // getter/setter
    // ------------------------------------------------------------------------------

    /**
     * @return
     */
    public Long getPoLineId() {
        return poLineId;
    }

    public void setPoLineId(Long poLineId) {
        this.poLineId = poLineId;
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
     * @return
     */
    public Long getPoHeaderId() {
        return poHeaderId;
    }

    public void setPoHeaderId(Long poHeaderId) {
        this.poHeaderId = poHeaderId;
    }

    /**
     * @return 行状态
     */
    public String getLineStatus() {
        return lineStatus;
    }

    public void setLineStatus(String lineStatus) {
        this.lineStatus = lineStatus;
    }

    /**
     * @return 行号
     */
    public String getLineNum() {
        return lineNum;
    }

    public void setLineNum(String lineNum) {
        this.lineNum = lineNum;
    }

    /**
     * @return 商品分类编号
     */
    public String getSkuCategoryCode() {
        return skuCategoryCode;
    }

    public void setSkuCategoryCode(String skuCategoryCode) {
        this.skuCategoryCode = skuCategoryCode;
    }

    /**
     * @return 商品编号
     */
    public String getSkuNo() {
        return skuNo;
    }

    public void setSkuNo(String skuNo) {
        this.skuNo = skuNo;
    }

    /**
     * @return 商品名称
     */
    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    /**
     * @return 销售单价
     */
    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    /**
     * @return 购买数量
     */
    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    /**
     * @return 小计
     */
    public BigDecimal getLineAmount() {
        return lineAmount;
    }

    public void setLineAmount(BigDecimal lineAmount) {
        this.lineAmount = lineAmount;
    }

    /**
     * @return 协议价
     */
    public BigDecimal getContractLineAmount() {
        return contractLineAmount;
    }

    public void setContractLineAmount(BigDecimal contractLineAmount) {
        this.contractLineAmount = contractLineAmount;
    }

    /**
     * @return 税率
     */
    public Long getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(Long taxRate) {
        this.taxRate = taxRate;
    }

}
