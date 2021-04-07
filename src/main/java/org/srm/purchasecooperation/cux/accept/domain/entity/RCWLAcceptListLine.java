package org.srm.purchasecooperation.cux.accept.domain.entity;

import java.math.BigDecimal;
import java.util.Optional;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hzero.starter.keyencrypt.core.Encrypt;
import org.srm.purchasecooperation.accept.domain.entity.AcceptListLine;

import io.choerodon.mybatis.annotation.ModifyAudit;
import io.choerodon.mybatis.annotation.VersionAudit;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author junjun.lei@hand-china.com
 * @create 2019-11-20 18:39
 */
@ApiModel("验收单行")
@VersionAudit
@ModifyAudit
@Table(name = "spuc_accept_list_line")
public class RCWLAcceptListLine extends AcceptListLine {
    public static final String FIELD_ACCEPT_LIST_LINE_ID = "acceptListLineId";
    public static final String FIELD_ACCEPT_LIST_HEADER_ID = "acceptListHeaderId";
    public static final String FIELD_TENANT_ID = "tenantId";
    public static final String FIELD_ITEM_ID = "itemId";
    public static final String FIELD_ITEM_NAME = "itemName";
    public static final String FIELD_ITEM_CATEGORY_ID = "itemCategoryId";
    public static final String FIELD_UOM_ID = "uomId";
    public static final String FIELD_ACCEPT_QUANTITY = "acceptQuantity";
    public static final String FIELD_ACCEPT_OPINION_CODE = "acceptOpinionCode";
    public static final String FIELD_LINE_NUM = "lineNum";
    public static final String FIELD_ACCEPTED_QUANTITY = "acceptedQuantity";
    public static final String FIELD_ATTACHMENT_UUID = "attachmentUuid";
    public static final String FIELD_LINE_ACCEPT_DESCRIPTION = "lineAcceptDescription";
    public static final String FIELD_ACCEPT_STAGE_ID = "acceptStageId";
    public static final String FIELD_ACCEPT_AMOUNT = "acceptAmount";
    public static final String FIELD_NEED_INVOICE_FLAG = "needInvoiceFlag";
    public static final String FIELD_BILL_MATCH_FLAG = "billMatchFlag";
    public static final String FIELD_OCCUPIED_QUANTITY = "occupiedQuantity";
    public static final String FIELD_ATTRIBUTEVARCHAR1 = "attributeVarchar1";
    public static final String FIELD_ATTRIBUTEVARCHAR2 = "attributeVarchar2";

    //
    // 数据库字段
    // ------------------------------------------------------------------------------

    @ApiModelProperty("表ID，主键，供其他表做外键")
    @Id
    @GeneratedValue
    @Encrypt
    private Long acceptListLineId;

    @ApiModelProperty("验收单头id")
    @Encrypt
    private Long acceptListHeaderId;
    @ApiModelProperty("租户id")
    private Long tenantId;
    @ApiModelProperty("物料id")
    @Encrypt
    private Long itemId;
    @ApiModelProperty("物料名称")
    private String itemName;
    @ApiModelProperty("物料品类id")
    @Encrypt
    private Long itemCategoryId;
    @ApiModelProperty("单位id")
    @Encrypt
    private Long uomId;
    @ApiModelProperty("验收数量")
    private BigDecimal acceptQuantity;
    @ApiModelProperty("验收意见编码")
    private String acceptOpinionCode;
    @ApiModelProperty("行号")
    private Long lineNum;
    @ApiModelProperty("单据来源ID")
    @Encrypt
    private Long sourceId;
    @ApiModelProperty("单据来源行ID")
    private Long sourceLineId;
    @ApiModelProperty("已验收数量")
    private BigDecimal acceptedQuantity;
    @Transient
    @ApiModelProperty("可验收数量")
    private BigDecimal canAcceptQuantity;
    @ApiModelProperty("附件UUID")
    private String attachmentUuid;
    @ApiModelProperty("验收说明")
    private String lineAcceptDescription;
    @ApiModelProperty("协议阶段id")
    private Long acceptStageId;
    @ApiModelProperty("费用")
    private BigDecimal acceptAmount;
    @Transient
    @ApiModelProperty("协议阶段ID")
    private Long pcStageId;
    @ApiModelProperty("需要开票标识")
    private Integer needInvoiceFlag;
    @ApiModelProperty("占用数量")
    private BigDecimal occupiedQuantity;
    @ApiModelProperty("匹配账单标识")
    private Integer billMatchFlag;
    @ApiModelProperty("资产编号")
    private String attributeVarchar1;
    @ApiModelProperty("资产系统采购订单号")
    private String attributeVarchar2;

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

    /**
     * 验收采购协议时：按阶段验收，获取同一个标的行和阶段行的key
     * 
     * @return
     */
    public String getSameStageAndSource() {
        return Optional.ofNullable(sourceLineId).orElse(0L) + ":" + Optional.ofNullable(acceptStageId).orElse(0L);
    }

    public Long getPcStageId() {
        return pcStageId;
    }

    public void setPcStageId(Long pcStageId) {
        this.pcStageId = pcStageId;
    }

    public String getAttachmentUuid() {
        return attachmentUuid;
    }

    public void setAttachmentUuid(String attachmentUuid) {
        this.attachmentUuid = attachmentUuid;
    }

    public String getLineAcceptDescription() {
        return lineAcceptDescription;
    }

    public void setLineAcceptDescription(String lineAcceptDescription) {
        this.lineAcceptDescription = lineAcceptDescription;
    }

    public Long getAcceptStageId() {
        return acceptStageId;
    }

    public void setAcceptStageId(Long acceptStageId) {
        this.acceptStageId = acceptStageId;
    }

    public BigDecimal getAcceptAmount() {
        return acceptAmount;
    }

    public void setAcceptAmount(BigDecimal acceptAmount) {
        this.acceptAmount = acceptAmount;
    }

    public Long getSourceId() {
        return sourceId;
    }

    public void setSourceId(Long sourceId) {
        this.sourceId = sourceId;
    }

    public Long getSourceLineId() {
        return sourceLineId;
    }

    public void setSourceLineId(Long sourceLineId) {
        this.sourceLineId = sourceLineId;
    }

    public BigDecimal getAcceptedQuantity() {
        return acceptedQuantity;
    }

    public void setAcceptedQuantity(BigDecimal acceptedQuantity) {
        this.acceptedQuantity = acceptedQuantity;
    }

    public BigDecimal getCanAcceptQuantity() {
        return canAcceptQuantity;
    }

    public void setCanAcceptQuantity(BigDecimal canAcceptQuantity) {
        this.canAcceptQuantity = canAcceptQuantity;
    }

    public Long getAcceptListLineId() {
        return acceptListLineId;
    }

    public void setAcceptListLineId(Long acceptListLineId) {
        this.acceptListLineId = acceptListLineId;
    }

    public Long getAcceptListHeaderId() {
        return acceptListHeaderId;
    }

    public void setAcceptListHeaderId(Long acceptListHeaderId) {
        this.acceptListHeaderId = acceptListHeaderId;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Long getItemCategoryId() {
        return itemCategoryId;
    }

    public void setItemCategoryId(Long itemCategoryId) {
        this.itemCategoryId = itemCategoryId;
    }

    public Long getUomId() {
        return uomId;
    }

    public void setUomId(Long uomId) {
        this.uomId = uomId;
    }

    public BigDecimal getAcceptQuantity() {
        return acceptQuantity;
    }

    public void setAcceptQuantity(BigDecimal acceptQuantity) {
        this.acceptQuantity = acceptQuantity;
    }

    public String getAcceptOpinionCode() {
        return acceptOpinionCode;
    }

    public void setAcceptOpinionCode(String acceptOpinionCode) {
        this.acceptOpinionCode = acceptOpinionCode;
    }

    public Long getLineNum() {
        return lineNum;
    }

    public void setLineNum(Long lineNum) {
        this.lineNum = lineNum;
    }

    public Integer getNeedInvoiceFlag() {
        return needInvoiceFlag;
    }

    public void setNeedInvoiceFlag(Integer needInvoiceFlag) {
        this.needInvoiceFlag = needInvoiceFlag;
    }

    public BigDecimal getOccupiedQuantity() {
        return occupiedQuantity;
    }

    public void setOccupiedQuantity(BigDecimal occupiedQuantity) {
        this.occupiedQuantity = occupiedQuantity;
    }

    public Integer getBillMatchFlag() {
        return billMatchFlag;
    }

    public void setBillMatchFlag(Integer billMatchFlag) {
        this.billMatchFlag = billMatchFlag;
    }
}
