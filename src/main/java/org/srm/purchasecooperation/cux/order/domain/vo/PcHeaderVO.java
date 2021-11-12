package org.srm.purchasecooperation.cux.order.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import javax.persistence.GeneratedValue;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.srm.common.mybatis.domain.ExpandDomain;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PcHeaderVO extends ExpandDomain {

    @ApiModelProperty("表ID，主键，供其他表做外键")
    @GeneratedValue
    private Long pcHeaderId;
    @ApiModelProperty(value = "租户id")
    private Long tenantId;
    @ApiModelProperty(value = "协议名称")
    private String pcName;
    @ApiModelProperty(value = "协议编号")
    private String pcNum;
    @ApiModelProperty(value = "采购协议状态")
    private String pcStatusCode;
    @ApiModelProperty(value = "公司id")
    private Long companyId;
    @ApiModelProperty("公司编码")
    private String companyNum;
    @ApiModelProperty("公司名称")
    private String companyName;
    @ApiModelProperty(value = "不含税总额")
    private BigDecimal amount;
    @ApiModelProperty(value = "含税总额")
    private BigDecimal taxIncludeAmount;
    @ApiModelProperty(value = "协议性质")
    private String pcKindCode;
    @ApiModelProperty(value = "协议类型头id")

    private Long pcTypeId;
    @ApiModelProperty("协议模板id")
    private Long pcTemplateId;
    @ApiModelProperty("供应商id")
    private Long supplierId;
    @ApiModelProperty("供应商编码")
    private String supplierNum;
    @ApiModelProperty("供应商名称")
    private String supplierName;
    @ApiModelProperty("供应商租户id")
    private Long supplierTenantId;
    @ApiModelProperty("供应商公司id")
    private Long supplierCompanyId;
    @ApiModelProperty("供应商公司编码")
    private String supplierCompanyNum;
    @ApiModelProperty("供应商公司名称")
    private String supplierCompanyName;

    @ApiModelProperty(value = "协议起始日期")
    private Date startDateActive;
    @ApiModelProperty(value = "协议终止日期")
    private Date endDateActive;

    @ApiModelProperty("主协议id")
    private Long mainContractId;
    @ApiModelProperty("归档码")
    private String archiveCode;
    @ApiModelProperty("备注")
    private String remark;
    @ApiModelProperty("合同文档URL")
    private String contractFileUrl;
    @ApiModelProperty("模板文件URL")
    private String templateFileUrl;
    @ApiModelProperty("附件uuid")
    private String attachmentUuid;
    @ApiModelProperty("供应商附件uuid")
    private String supplierAttachmentUuid;
    @ApiModelProperty("归档附件uuId")
    private String archiveAttachmentUuid;
    @ApiModelProperty("审批时间")
    private Date approvedDate;
    @ApiModelProperty("审批人ID")
    private Long approvedBy;
    @ApiModelProperty("审批意见")
    private String approvedRemark;
    @ApiModelProperty("确认状态")
    private Integer confirmedFlag;
    @ApiModelProperty("确认人")
    private Long confirmedBy;
    @ApiModelProperty("确认时间")
    private Date confirmedDate;
    @ApiModelProperty(value = "编辑阶段")
    
    private Long editStep;
    @ApiModelProperty("合同附件文本URL（附件合同）")
    private String contractAttachmentUrl;
    @ApiModelProperty("电签标识")
    private Integer electricSignFlag;
    @ApiModelProperty("电签状态")
    private String electricSignStatus;
    @ApiModelProperty("电签顺序")
    private String electricSignOrder;
    @ApiModelProperty("已付款金额")
    private BigDecimal paidAmount;
    @ApiModelProperty("审批顺序")
    private String approveSequence;
    @ApiModelProperty("协议变更标识")
    private Long alterationFlag;
    @ApiModelProperty("业务实体Id")
    private Long ouId;
    @ApiModelProperty("采购组织id")
    private Long purchaseOrgId;
    @ApiModelProperty("采购协议来源")
    private String pcSourceCode;
    @ApiModelProperty("费用挂靠部门id")
    private Long costAnchDepId;
    @ApiModelProperty("费用挂靠部门")
    private String costAnchDepDesc;
    @ApiModelProperty("公司组织")
    private Long companyOrgId;
    @ApiModelProperty("公司组织")
    private String companyOrgName;
    @ApiModelProperty("是否全局协议")
    private Integer globalFlag;
    @ApiModelProperty("境外采购")
    
    private Integer overseasProcurement;
    @ApiModelProperty("采购品类")
    private Long itemCategoryId;
    @ApiModelProperty("境外采购")
    private Integer messagedFlag;
    @ApiModelProperty("签署即生效标识")
    private Integer signEffectFlag;
    @ApiModelProperty("生效时长（天）")
    private Long effectiveTime;
    @ApiModelProperty("验收类型")
    private String acceptType;
    @ApiModelProperty("内部批注")
    private String internalPostil;
    @ApiModelProperty("签订日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate signDate;
    @ApiModelProperty("签订事由说明")
    private String signDescription;
    @ApiModelProperty("审批方式")
    private String approvalMethod;
    @ApiModelProperty("终止原因")
    private String terminationReason;
    @ApiModelProperty("协议用途")
    private String contractPurpose;
    @ApiModelProperty("纸质配送方式")
    private String paperDeliveryMethod;
    @ApiModelProperty("纸质配送信息")
    private String paperDeliveryInfo;
    @ApiModelProperty("采购方附件")
    private String purchaserAttachmentUuid;
    @ApiModelProperty("协议作废审批方式")
    private String invalidApproveMethod;
    @ApiModelProperty("上次合同附件文本URL（附件合同）")
    private String lastContractFileUrl;
    @ApiModelProperty("初次版本合同附件文本URL（附件合同）")
    private String firstContractFileUrl;
    @ApiModelProperty("协议确认审批方式")
    private String confirmApproveMethod;
    @ApiModelProperty("协议变更审批方式")
    private String changeApproveMethod;
    @ApiModelProperty("协议终止审批方式")
    private String terminationApproveMethod;
    @ApiModelProperty("发布时间")
    private Date releaseDate;
    @ApiModelProperty("签署地点")
    private String signAddress;
    @ApiModelProperty("接口同步状态")
    private String syncStatus;
    @ApiModelProperty("接口同步返回消息")
    private String syncResponseMsg;
    @ApiModelProperty("接口同步失败次数")
    private Long syncFailTimes;
    @ApiModelProperty("接口同步日期")
    private Date syncDate;
    @ApiModelProperty("终止前状态")
    private String terminateStatus;
    @ApiModelProperty("管控部门")
    private Long unitId;
    @ApiModelProperty("基准价")
    private String priceType;
    @ApiModelProperty("是否自动协议转订单")
    private Long autoTransferOrderFlag;
    @ApiModelProperty("临时预览链接")
    private String previewTemplateFileUrl;
    @ApiModelProperty("临时在线编辑链接")
    private String temporaryTemplateFileUrl;
    @ApiModelProperty("是否补充协议标志")
    private Integer supplementFlag;
    @ApiModelProperty("变更前的状态")
    private String alterStatus;
    @ApiModelProperty("当前审批节点")
    private String currentNode;
    @ApiModelProperty("策略头id")
    private Long strategyHeaderId;
    @ApiModelProperty("协议归档审批方式,值集SPCM.CONFIG.PC_APPROVAL_METHOD")
    private String archiveApproveMethod;
    @ApiModelProperty("法务合同编号")
    private String legalContractNum;

}
