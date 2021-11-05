package org.srm.purchasecooperation.order.domain.entity;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.choerodon.core.convertor.ApplicationContextHelper;
import io.choerodon.core.exception.CommonException;
import io.choerodon.core.oauth.DetailsHelper;
import io.choerodon.mybatis.annotation.ModifyAudit;
import io.choerodon.mybatis.annotation.VersionAudit;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.hibernate.validator.constraints.Length;
import org.hzero.boot.customize.util.CustomizeHelper;
import org.hzero.boot.platform.lov.annotation.LovValue;
import org.hzero.core.base.BaseConstants.Flag;
import org.hzero.mybatis.domian.Condition;
import org.hzero.mybatis.util.Sqls;
import org.hzero.starter.keyencrypt.core.Encrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.Assert;
import org.srm.boot.adaptor.client.AdaptorTaskHelper;
import org.srm.boot.adaptor.client.result.TaskResultBox;
import org.srm.boot.platform.customizesetting.CustomizeSettingHelper;
import org.srm.common.mybatis.domain.ExpandDomain;
import org.srm.purchasecooperation.common.api.dto.SmdmCurrencyDTO;
import org.srm.purchasecooperation.common.app.MdmService;
import org.srm.purchasecooperation.order.api.dto.DeliveryFeedBackDTO;
import org.srm.purchasecooperation.order.api.dto.PoDTO;
import org.srm.purchasecooperation.order.api.dto.PoExpFeedbackDTO;
import org.srm.purchasecooperation.order.api.dto.PoLineDetailDTO;
import org.srm.purchasecooperation.order.api.dto.PoOrderSaveDTO;
import org.srm.purchasecooperation.order.api.dto.ResponseDTO;
import org.srm.purchasecooperation.order.app.service.PoProcessActionService;
import org.srm.purchasecooperation.order.domain.repository.PoChangeConfigRepository;
import org.srm.purchasecooperation.order.domain.repository.PoHeaderEsRepository;
import org.srm.purchasecooperation.order.domain.repository.PoHeaderRepository;
import org.srm.purchasecooperation.order.domain.repository.PoLineEsRepository;
import org.srm.purchasecooperation.order.domain.repository.PoLineLocationEsRepository;
import org.srm.purchasecooperation.order.domain.repository.PoLineLocationRepository;
import org.srm.purchasecooperation.order.domain.repository.PoLineRepository;
import org.srm.purchasecooperation.order.domain.vo.PoLineExportVo;
import org.srm.purchasecooperation.order.infra.constant.PoConstants;
import org.srm.purchasecooperation.order.infra.constant.PoConstants.ConstantsOfBigDecimal;
import org.srm.purchasecooperation.order.infra.mapper.PoHeaderMapper;
import org.srm.purchasecooperation.pr.domain.entity.PrHeader;
import org.srm.purchasecooperation.pr.domain.vo.PurchaseAgentVO;

@ApiModel("采购订单头,功能sodr-order")
@VersionAudit
@ModifyAudit
@Table(
        name = "sodr_po_header"
)
public class PoHeader extends ExpandDomain {
    private static final Logger LOGGER = LoggerFactory.getLogger(PoHeader.class);
    public static final String FIELD_PO_HEADER_ID = "poHeaderId";
    public static final String FIELD_TENANT_ID = "tenantId";
    public static final String FIELD_PO_NUM = "poNum";
    public static final String FIELD_VERSION_NUM = "versionNum";
    public static final String FIELD_VERSION_DATE = "versionDate";
    public static final String FIELD_PO_TYPE_ID = "poTypeId";
    public static final String FIELD_RELEASE_NUM = "releaseNum";
    public static final String FIELD_DISPLAY_PO_NUM = "displayPoNum";
    public static final String FIELD_DISPLAY_RELEASE_NUM = "displayReleaseNum";
    public static final String FIELD_ERP_CONTRACT_NUM = "erpContractNum";
    public static final String FIELD_ERP_SUPPLIER_PHONE = "erpSupplierPhone";
    public static final String FIELD_AGENT_ID = "agentId";
    public static final String FIELD_SUPPLIER_TENANT_ID = "supplierTenantId";
    public static final String FIELD_SUPPLIER_OU_ID = "supplierOuId";
    public static final String FIELD_SUPPLIER_ID = "supplierId";
    public static final String FIELD_SUPPLIER_CODE = "supplierCode";
    public static final String FIELD_SUPPLIER_NAME = "supplierName";
    public static final String FIELD_SUPPLIER_COMPANY_ID = "supplierCompanyId";
    public static final String FIELD_SUPPLIER_COMPANY_NAME = "supplierCompanyName";
    public static final String FIELD_SUPPLIER_SITE_ID = "supplierSiteId";
    public static final String FIELD_SUPPLIER_CONTACT_ID = "supplierContactId";
    public static final String FIELD_SHIP_TO_LOCATION_CODE = "shipToLocationCode";
    public static final String FIELD_BILL_TO_LOCATION_CODE = "billToLocationCode";
    public static final String FIELD_TERMS_ID = "termsId";
    public static final String FIELD_COMPANY_ID = "companyId";
    public static final String FIELD_COMPANY_NAME = "companyName";
    public static final String FIELD_OU_ID = "ouId";
    public static final String FIELD_PURCHASE_ORG_ID = "purchaseOrgId";
    public static final String FIELD_SUBMITTED_BY = "submittedBy";
    public static final String FIELD_SUBMITTED_DATE = "submittedDate";
    public static final String FIELD_RELEASED_FLAG = "releasedFlag";
    public static final String FIELD_RELEASE_TYPE_CODE = "releaseTypeCode";
    public static final String FIELD_RELEASED_BY = "releasedBy";
    public static final String FIELD_RELEASED_DATE = "releasedDate";
    public static final String FIELD_CONFIRMED_FLAG = "confirmedFlag";
    public static final String FIELD_CONFIRM_TYPE_CODE = "confirmTypeCode";
    public static final String FIELD_CONFIRMED_BY = "confirmedBy";
    public static final String FIELD_CONFIRMED_DATE = "confirmedDate";
    public static final String FIELD_URGENT_FLAG = "urgentFlag";
    public static final String FIELD_URGENT_DATE = "urgentDate";
    public static final String FIELD_FEEDBACK_DATE = "feedbackDate";
    public static final String FIELD_CLOSED_FLAG = "closedFlag";
    public static final String FIELD_DIRECT_CUSTOM_FLAG = "directCustomFlag";
    public static final String FIELD_CONFIRM_UPDATE_FLAG = "confirmUpdateFlag";
    public static final String FIELD_INIT_FLAG = "initFlag";
    public static final String FIELD_RECEIVED_STATUS = "receivedStatus";
    public static final String FIELD_APPROVED_BY = "approvedBy";
    public static final String FIELD_APPROVED_FLAG = "approvedFlag";
    public static final String FIELD_APPROVED_DATE = "approvedDate";
    public static final String FIELD_APPROVED_REMARK = "approvedRemark";
    public static final String FIELD_FROZEN_FLAG = "frozenFlag";
    public static final String FIELD_PUBLISH_CANCEL_FLAG = "publishCancelFlag";
    public static final String FIELD_CANCELLED_FLAG = "cancelledFlag";
    public static final String FIELD_ERP_APPROVAL_FLAG = "erpApprovalFlag";
    public static final String FIELD_ERP_STATUS = "erpStatus";
    public static final String FIELD_STATUS_CODE = "statusCode";
    public static final String FIELD_CURRENCY_CODE = "currencyCode";
    public static final String FIELD_AMOUNT = "amount";
    public static final String FIELD_TAX_INCLUDE_AMOUNT = "taxIncludeAmount";
    public static final String FIELD_PURCHASE_REMARK = "purchaseRemark";
    public static final String FIELD_SUPPLIER_REMARK = "supplierRemark";
    public static final String FIELD_REMARK = "remark";
    public static final String FIELD_ATTACHMENT_UUID = "attachmentUuid";
    public static final String FIELD_SUPPLIER_ATTACHMENT_UUID = "supplierAttachmentUuid";
    public static final String FIELD_PURCHASER_INNER_ATTACHMENT_UUID = "purchaserInnerAttachmentUuid";
    public static final String FIELD_ERP_CREATED_NAME = "erpCreatedName";
    public static final String FIELD_ERP_CREATION_DATE = "erpCreationDate";
    public static final String FIELD_ERP_LAST_UPDATE_DATE = "erpLastUpdateDate";
    public static final String FIELD_RELEASE_FLAG = "releaseFlag";
    public static final String FIELD_SNAPSHOT_FLAG = "snapshotFlag";
    public static final String FIELD_REF_PO_HEADER_ID = "refPoHeaderId";
    public static final String FIELD_EXTERNAL_SYSTEM_CODE = "externalSystemCode";
    public static final String FIELD_SOURCE_CODE = "sourceCode";
    public static final String FIELD_SHIP_TO_LOCATION_ID = "shipToLocationId";
    public static final String FIELD_SHIP_TO_LOCATION_ADDRESS = "shipToLocationAddress";
    public static final String FIELD_BILL_TO_LOCATION_ADDRESS = "billToLocationAddress";
    public static final String FIELD_BILL_TO_LOCATION_ID = "billToLocationId";
    public static final String FIELD_PR_HEADER_ID = "prHeaderId";
    public static final String FIELD_RECEIVER_EMAIL_ADDRESS = "receiverEmailAddress";
    public static final String FIELD_REFUSED_BY = "refusedBy";
    public static final String FIELD_REFUSED_FLAG = "refusedFlag";
    public static final String FIELD_REFUSED_DATE = "refusedDate";
    public static final String FIELD_REFUSED_REMARK = "refusedRemark";
    public static final String FIELD_SOURCE_BILL_TYPE_CODE = "sourceBillTypeCode";
    public static final String FIELD_INVOICE_TITLE = "invoiceTitle";
    public static final String FIELD_TAX_REGISTER_NUM = "taxRegisterNum";
    public static final String FIELD_TAX_REGISTER_ADDRESS = "taxRegisterAddress";
    public static final String FIELD_TAX_REGISTER_TEL = "taxRegisterTel";
    public static final String FIELD_TAX_REGISTER_BANK = "taxRegisterBank";
    public static final String FIELD_TAX_REGISTER_BANK_ACCOUNT = "taxRegisterBankAccount";
    public static final String FIELD_INVOICE_METHOD_CODE = "invoiceMethodCode";
    public static final String FIELD_INVOICE_TITLE_TYPE_CODE = "invoiceTitleTypeCode";
    public static final String FIELD_INVOICE_DETAIL_TYPE_CODE = "invoiceDetailTypeCode";
    public static final String FIELD_PO_SOURCE_PLATFORM = "poSourcePlatform";
    public static final String FIELD_INCORRECT_FLAG = "incorrectFlag";
    public static final String FIELD_INCORRECT_DATE = "incorrectDate";
    public static final String FIELD_INCORRECT_MSG = "incorrectMsg";
    public static final String FIELD_SHIP_TO_LOC_CONT_NAME = "shipToLocContName";
    public static final String FIELD_SHIP_TO_LOC_TEL_NUM = "shipToLocTelNum";
    public static final String FIELD_BILL_TO_LOC_CONT_NAME = "billToLocContName";
    public static final String FIELD_BILL_TO_LOC_TEL_NUM = "billToLocTelNum";
    public static final String FIELD_DISPLAY_PR_NUM = "displayPrNum";
    public static final String FIELD_INVOICE_TYPE_CODE = "invoiceTypeCode";
    public static final String FIELD_APPROVED_SYNC_STATUS = "approvedSyncStatus";
    public static final String FIELD_APPROVED_SYNC_RESPONSE_MSG = "approvedSyncResponseMsg";
    public static final String FIELD_APPROVED_SYNC_DATE = "approvedSyncDate";
    public static final String FIELD_INVOICE_TYPE_NAME = "invoiceTypeName";
    public static final String FIELD_INVOICE_METHOD_NAME = "invoiceMethodName";
    public static final String FIELD_INVOICE_TITLE_TYPE_NAME = "invoiceTitleTypeName";
    public static final String FIELD_INVOICE_DETAIL_TYPE_NAME = "invoiceDetailTypeName";
    public static final String FIELD_CREATE_SYNC_STATUS = "createSyncStatus";
    public static final String FIELD_CREATE_SYNC_RESPONSE_MSG = "createSyncResponseMsg";
    public static final String FIELD_CREATE_SYNC_DATE = "createSyncDate";
    public static final String FIELD_DELIVERY_SYNC_STATUS = "deliverySyncStatus";
    public static final String FIELD_DELIVERY_SYNC_RESPONSE_MSG = "deliverySyncResponseMsg";
    public static final String FIELD_DELIVERY_SYNC_DATE = "deliverySyncDate";
    public static final String FIELD_CHANGE_SYNC_STATUS = "changeSyncStatus";
    public static final String FIELD_ADVANCE_PAYMENT_AMOUNT = "advancePaymentAmount";
    public static final String FIELD_ACCEPT_METHOD = "acceptMethod";
    public static final String FIELD_STRATEGY_HEADER_ID = "strategyHeaderId";
    public static final String[] PR_SYNC_FIELDS = new String[]{"sourceBillTypeCode", "displayPrNum", "poSourcePlatform", "shipToLocContName", "shipToLocTelNum", "billToLocContName", "billToLocTelNum", "invoiceTitle", "taxRegisterNum", "taxRegisterAddress", "taxRegisterTel", "taxRegisterBank", "taxRegisterBankAccount", "invoiceMethodCode", "invoiceTypeCode", "invoiceTitleTypeCode", "invoiceDetailTypeCode", "shipToLocationAddress", "billToLocationAddress", "receiverEmailAddress", "invoiceTypeName", "invoiceMethodName", "invoiceTitleTypeName", "invoiceDetailTypeName"};
    public static final String FIELD_MODIFY_PRICE_FLAG = "modifyPriceFlag";
    public static final String FIELD_UN_SAVE_ENABLE = "unSaveEnable";
    public static final String AMOUNT = "amount";
    public static final String TAX_INCLUDE_AMOUNT = "tax_include_amount";
    public static final String FIELD_RECEIVE_ORDER_TYPE = "receiveOrderType";
    public static final String APPROVE_METHOD = "approveMethod";
    public static final String FIELD_PURCHASE_UNIT_NAME = "purchaseUnitName";
    public static final String DOMESTIC_CURRENCY_CODE = "domesticCurrencyCode";
    public static final String DOMESTIC_AMOUNT = "domesticAmount";
    public static final String DOMESTIC_TAX_INCLUDE_AMOUNT = "domesticTaxIncludeAmount";
    public static final String OLD_STATUS_CODE = "oldStatusCode";
    public static final String FIELD_ORIGINAL_PO_HEADER_ID = "originalPoHeaderId";
    public static final String SETTLE_SUPPLIER_ID = "settleSupplierId";
    public static final String SETTLE_SUPPLIER_CODE = "settleSupplierCode";
    public static final String SETTLE_SUPPLIER_NAME = "settleSupplierName";
    public static final String SETTLE_SUPPLIER_TENANT_ID = "settleSupplierTenantId";
    public static final String SETTLE_ERP_SUPPLIER_ID = "settleErpSupplierId";
    public static final String SETTLE_ERP_SUPPLIER_CODE = "settleErpSupplierCode";
    public static final String SETTLE_ERP_SUPPLIER_NAME = "settleErpSupplierName";
    public static final String FIELD_CHANGING_FLAG = "changingFlag";
    @ApiModelProperty("表ID，主键，供其他表做外键")
    @Id
    @GeneratedValue
    @Encrypt
    private Long poHeaderId;
    @ApiModelProperty("租户ID")
    @NotNull
    private Long tenantId;
    @ApiModelProperty("采购订单编码")
    @NotBlank
    private String poNum;
    @ApiModelProperty("修订版本号")
    @NotNull
    private Integer versionNum;
    @DateTimeFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @ApiModelProperty("修订日期")
    @NotNull
    private Date versionDate;
    @ApiModelProperty("采购订单类型ID")
    @NotNull
    @Encrypt
    private Long poTypeId;
    @ApiModelProperty("发放编号")
    private String releaseNum;
    @ApiModelProperty("展示订单编号")
    private String displayPoNum;
    @ApiModelProperty("展示采购订单发放号")
    private String displayReleaseNum;
    @ApiModelProperty("ERP合同号")
    private String erpContractNum;
    @ApiModelProperty("ERP供应商联系人电话")
    private String erpSupplierPhone;
    @ApiModelProperty("采购员ID")
    @Encrypt
    private Long agentId;
    @ApiModelProperty("供应商租户ID")
    @NotNull
    private Long supplierTenantId;
    @ApiModelProperty("供应商业务实体ID")
    private Long supplierOuId;
    @ApiModelProperty("供应商ID")
    @NotNull
    @Encrypt
    private Long supplierId;
    @ApiModelProperty("供应商编码")
    private String supplierCode;
    @ApiModelProperty("供应商名称")
    private String supplierName;
    @ApiModelProperty("供应商公司ID")
    @Encrypt
    private Long supplierCompanyId;
    @ApiModelProperty("供应商公司描述")
    private String supplierCompanyName;
    @ApiModelProperty("供应商地点ID")
    private Long supplierSiteId;
    @ApiModelProperty("供应商联系人ID")
    private Long supplierContactId;
    @ApiModelProperty("收货方编码")
    private String shipToLocationCode;
    @Length(
            max = 360
    )
    @ApiModelProperty("收货方地址")
    private String shipToLocationAddress;
    @ApiModelProperty("收单方编码")
    private String billToLocationCode;
    @ApiModelProperty("收单方地址")
    private String billToLocationAddress;
    @ApiModelProperty("付款条款ID")
    @Encrypt
    private Long termsId;
    @ApiModelProperty("公司ID")
    @NotNull
    @Encrypt
    private Long companyId;
    @ApiModelProperty("公司描述")
    private String companyName;
    @ApiModelProperty("业务实体ID")
    @Encrypt
    private Long ouId;
    @ApiModelProperty("采购组织ID")
    @Encrypt
    private Long purchaseOrgId;
    @ApiModelProperty("提交人")
    private Long submittedBy;
    @DateTimeFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @ApiModelProperty("提交日期")
    private Date submittedDate;
    @ApiModelProperty("发布状态")
    @NotNull
    private Integer releasedFlag;
    @ApiModelProperty("发布方式, SODR.ORDER.RELEASE_TYPE")
    private String releaseTypeCode;
    @ApiModelProperty("发布人")
    private Long releasedBy;
    @DateTimeFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @ApiModelProperty("发布日期")
    private Date releasedDate;
    @ApiModelProperty("确认状态")
    @NotNull
    private Integer confirmedFlag;
    @ApiModelProperty("确认方式,SODR.ORDER.COMFIRM_TYPE")
    private String confirmTypeCode;
    @ApiModelProperty("确认人")
    private Long confirmedBy;
    @DateTimeFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @ApiModelProperty("确认日期")
    private Date confirmedDate;
    @ApiModelProperty("加急状态")
    @NotNull
    private Integer urgentFlag;
    @ApiModelProperty("加急时间")
    private Date urgentDate;
    @DateTimeFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @ApiModelProperty("交期反馈日期")
    private Date feedbackDate;
    @ApiModelProperty("完全关闭标志")
    @NotNull
    private Integer closedFlag;
    @ApiModelProperty("直发客户标志")
    @NotNull
    private Integer directCustomFlag;
    @ApiModelProperty("确认更新标识")
    @NotNull
    private Integer confirmUpdateFlag;
    @ApiModelProperty("是否初始化数据标识")
    @NotNull
    private Integer initFlag;
    @ApiModelProperty("接收状态，SODR.PO_RECEIVED_STATUS")
    private String receivedStatus;
    @ApiModelProperty("最新审批人")
    private Long approvedBy;
    @ApiModelProperty("审批状态")
    @NotNull
    private Integer approvedFlag;
    @DateTimeFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @ApiModelProperty("最新审批日期")
    private Date approvedDate;
    @ApiModelProperty("审批意见")
    @Length(
            max = 480
    )
    private String approvedRemark;
    @ApiModelProperty("冻结标识")
    @NotNull
    private Integer frozenFlag;
    @ApiModelProperty("取消发布标识(暂挂)")
    @NotNull
    private Integer publishCancelFlag;
    @ApiModelProperty("ERP订单审批标识")
    @NotNull
    private Integer erpApprovalFlag;
    @ApiModelProperty("ERP状态")
    @LovValue(
            value = "SODR.ERP_STATUS",
            meaningField = "erpStatusMeaning"
    )
    private String erpStatus;
    @ApiModelProperty("订单状态，SODR.PO_STATUS")
    @LovValue(
            value = "SODR.PO_STATUS",
            meaningField = "statusCodeMeaning"
    )
    private String statusCode;
    @ApiModelProperty("数据来源，HPFM.DATA_SOURCE")
    @NotBlank
    @LovValue(
            value = "HPFM.DATA_SOURCE",
            meaningField = "sourceCodeMeaning"
    )
    private String sourceCode;
    @ApiModelProperty("来源平台代码,SPRM.SRC_PLATFORM")
    @Length(
            max = 30
    )
    @LovValue(
            value = "SPRM.SRC_PLATFORM",
            meaningField = "poSourcePlatformMeaning"
    )
    private String poSourcePlatform;
    @ApiModelProperty("外部来源系统编码")
    private String externalSystemCode;
    @ApiModelProperty("币种")
    private String currencyCode;
    @ApiModelProperty("不含税金额")
    private BigDecimal amount;
    @ApiModelProperty("含税金额")
    private BigDecimal taxIncludeAmount;
    @ApiModelProperty("采购员备注")
    private String purchaseRemark;
    @ApiModelProperty("供应商备注")
    private String supplierRemark;
    @ApiModelProperty("备注（订单摘要）")
    @Length(
            max = 480
    )
    private String remark;
    @ApiModelProperty("采购方附件")
    @Length(
            max = 50
    )
    private String attachmentUuid;
    @ApiModelProperty("供应方附件")
    @Length(
            max = 50
    )
    private String supplierAttachmentUuid;
    @ApiModelProperty("采购方内部附件")
    @Length(
            max = 50
    )
    private String purchaserInnerAttachmentUuid;
    @ApiModelProperty("创建人用户名")
    private String erpCreatedName;
    @ApiModelProperty("erp创建时间")
    @DateTimeFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    private Date erpCreationDate;
    @ApiModelProperty("erp更新时间")
    private Date erpLastUpdateDate;
    @ApiModelProperty("行版本号")
    private Long objectVersionNumber;
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @DateTimeFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @ApiModelProperty("创建日期")
    private Date creationDate;
    @ApiModelProperty("正式版本标识")
    @NotNull
    private Integer releaseFlag;
    @ApiModelProperty("快照标识")
    @NotNull
    private Integer snapshotFlag;
    @ApiModelProperty("关联正式订单ID")
    private Long refPoHeaderId;
    @ApiModelProperty("取消标识")
    private Integer cancelledFlag;
    @ApiModelProperty("收货方ID")
    private Long shipToLocationId;
    private Long billToLocationId;
    @ApiModelProperty("采购申请头ID")
    private Long prHeaderId;
    @ApiModelProperty("采购申请编号")
    @Length(
            max = 150
    )
    private String displayPrNum;
    @ApiModelProperty("收单邮箱")
    @Length(
            max = 100
    )
    private String receiverEmailAddress;
    @ApiModelProperty("拒绝人")
    private Long refusedBy;
    @ApiModelProperty("拒绝标志")
    @NotNull
    private Integer refusedFlag;
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @DateTimeFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @ApiModelProperty("拒绝时间")
    private Date refusedDate;
    @ApiModelProperty("拒绝原因")
    @Length(
            max = 480
    )
    private String refusedRemark;
    @ApiModelProperty("来源单据类别,SODR.DOC_SOURCE")
    @Length(
            max = 30
    )
    @LovValue(
            value = "SODR.DOC_SOURCE",
            meaningField = "sourceBillTypeCodeMeaning"
    )
    private String sourceBillTypeCode;
    @ApiModelProperty("发票抬头")
    @Length(
            max = 150
    )
    private String invoiceTitle;
    @ApiModelProperty("税务登记号")
    @Length(
            max = 30
    )
    private String taxRegisterNum;
    @ApiModelProperty("税务登记地址")
    @Length(
            max = 150
    )
    private String taxRegisterAddress;
    @ApiModelProperty("税务登记公司电话")
    @Length(
            max = 30
    )
    private String taxRegisterTel;
    @ApiModelProperty("税务登记开户行")
    @Length(
            max = 120
    )
    private String taxRegisterBank;
    @ApiModelProperty("税务登记开户行账号")
    @Length(
            max = 120
    )
    private String taxRegisterBankAccount;
    @ApiModelProperty("开票方式")
    @Length(
            max = 30
    )
    private String invoiceMethodCode;
    @ApiModelProperty("开票类型")
    @Length(
            max = 30
    )
    private String invoiceTypeCode;
    @ApiModelProperty("开票抬头类型")
    @Length(
            max = 30
    )
    private String invoiceTitleTypeCode;
    @ApiModelProperty("开票明细类型")
    @Length(
            max = 30
    )
    private String invoiceDetailTypeCode;
    @ApiModelProperty("异常标志")
    @NotNull
    private Integer incorrectFlag;
    @DateTimeFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @ApiModelProperty("异常发生时间")
    private Date incorrectDate;
    @ApiModelProperty("异常信息")
    private String incorrectMsg;
    @ApiModelProperty("收货联系人")
    @Length(
            max = 255
    )
    private String shipToLocContName;
    @ApiModelProperty("收货联系电话")
    @Length(
            max = 30
    )
    private String shipToLocTelNum;
    @ApiModelProperty("收单联系人")
    @Length(
            max = 255
    )
    private String billToLocContName;
    @Length(
            max = 30
    )
    @ApiModelProperty("收单联系电话")
    private String billToLocTelNum;
    @ApiModelProperty("审批导出ERP状态")
    @LovValue(
            value = "SODR.PO.APPROVED_SYNC_STATUS",
            meaningField = "approvedSyncStatusMeaning"
    )
    private String approvedSyncStatus;
    @ApiModelProperty("审批导出ERP反馈")
    private String approvedSyncResponseMsg;
    @ApiModelProperty("审批导出时间")
    private Date approvedSyncDate;
    @ApiModelProperty("开票方式描述")
    private String invoiceMethodName;
    @ApiModelProperty("发票形式描述")
    private String invoiceTypeName;
    @ApiModelProperty("发票类型描述")
    private String invoiceTitleTypeName;
    @ApiModelProperty("发票明细描述")
    private String invoiceDetailTypeName;
    @ApiModelProperty("价格修改标识")
    private Integer modifyPriceFlag;
    @ApiModelProperty("创建ERP导出状态")
    private String createSyncStatus;
    @ApiModelProperty("创建ERP导出反馈")
    private String createSyncResponseMsg;
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @DateTimeFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @ApiModelProperty("创建ERP导出时间")
    private Date createSyncDate;
    @ApiModelProperty("变更ERP导出状态")
    private String changeSyncStatus;
    @ApiModelProperty("预付款金额")
    private BigDecimal advancePaymentAmount;
    @ApiModelProperty("验收接收方式code")
    @LovValue(
            lovCode = "SPUC.ACCEPT_METHOD",
            meaningField = "acceptMethodMeaning"
    )
    private String acceptMethod;
    @ApiModelProperty("接收类型code")
    @LovValue(
            lovCode = "SINV.RECEIVE_ORDER_TYPE ",
            meaningField = "receiveOrderTypeMeaning"
    )
    private String receiveOrderType;
    @ApiModelProperty("订单更新升级重新确认标识")
    private Integer poUpgradeReConfirmFlag;
    @ApiModelProperty("原订单ID")
    private Long originalPoHeaderId;
    @ApiModelProperty("审批方式")
    private String approveMethod;
    @ApiModelProperty("采购组织名称")
    private String purchaseUnitName;
    @ApiModelProperty("本币币种")
    private String domesticCurrencyCode;
    @ApiModelProperty("本币不含税金额")
    private BigDecimal domesticAmount;
    @ApiModelProperty("本币含税金额")
    private BigDecimal domesticTaxIncludeAmount;
    @ApiModelProperty("结算供应商id")
    private Long settleSupplierId;
    @ApiModelProperty("结算供应商code")
    private String settleSupplierCode;
    @ApiModelProperty("结算供应商name")
    private String settleSupplierName;
    @ApiModelProperty("结算供应商租户id")
    private Long settleSupplierTenantId;
    @ApiModelProperty("结算供应商id")
    private Long settleErpSupplierId;
    @ApiModelProperty("结算供应商code")
    private String settleErpSupplierCode;
    @ApiModelProperty("结算供应商name")
    private String settleErpSupplierName;
    @ApiModelProperty("变更中标识")
    private Integer changingFlag;
    @ApiModelProperty("留言板未读数量")
    @Transient
    private Integer unreadCount;
    @ApiModelProperty("采购组织启用标识")
    @Transient
    private Integer purchaseOrgEnabledFlag;
    @ApiModelProperty("币种ID")
    @Transient
    private Long currencyId;
    @ApiModelProperty("币种名")
    @Transient
    private String currencyName;
    @ApiModelProperty("采购员启用标识")
    @Transient
    private Integer purchaseAgentEnabledFlag;
    @ApiModelProperty("币种启用标识")
    @Transient
    private Integer currencyEnabledFlag;
    @ApiModelProperty("付款条款代码")
    @Transient
    private String termCode;
    @ApiModelProperty("付款条款启用标识")
    @Transient
    private Integer termEnabledFlag;
    @Transient
    @ApiModelProperty("redis缓存key")
    private String cacheKey;
    @ApiModelProperty("订单历史状态，SODR.PO_STATUS")
    private String oldStatusCode;
    @Transient
    @ApiModelProperty("头原币币种财务精度")
    private Integer financialPrecision;
    @Transient
    @ApiModelProperty("头原币币种财务精度")
    private Integer domesticFinancialPrecision;
    @Transient
    @ApiModelProperty("供应商id组")
    private List<Long> supplierCompanyIds;
    @Transient
    @ApiModelProperty("ERP供应商id组")
    private List<Long> supplierIds;
    @Transient
    @ApiModelProperty("接收标识")
    private String receiptType;
    @ApiModelProperty("提交标识")
    @Transient
    private Integer autoSubmitFlag;
    @ApiModelProperty("验收接收方式")
    @Transient
    private String acceptMethodMeaning;
    @ApiModelProperty("商品编码")
    @Transient
    private String productNum;
    @ApiModelProperty("商品名称")
    @Length(
            max = 300
    )
    @Transient
    private String productName;
    @ApiModelProperty("提交日期至")
    @DateTimeFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @Transient
    private Date submitDateEnd;
    @ApiModelProperty("提交日期从")
    @Transient
    @DateTimeFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    private Date submitDateStart;
    @ApiModelProperty("发布日期从")
    @Transient
    @DateTimeFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    private Date releaseDateStart;
    @ApiModelProperty("发布日期至")
    @Transient
    @DateTimeFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    private Date releaseDateEnd;
    @ApiModelProperty("确认日期从")
    @Transient
    @DateTimeFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    private Date confirmDateStart;
    @ApiModelProperty("确认日期至")
    @DateTimeFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @Transient
    private Date confirmDateEnd;
    @ApiModelProperty("订单创建开始")
    @DateTimeFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @Transient
    private Date creationDateStart;
    @ApiModelProperty("订单创建至")
    @DateTimeFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @Transient
    private Date creationDateEnd;
    @Transient
    @DateTimeFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    private Date feedbackDateStart;
    @Transient
    @DateTimeFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    private Date feedbackDateEnd;
    @Transient
    @ApiModelProperty("条款名称")
    private String termName;
    @Transient
    @ApiModelProperty("虚拟订单状态集合")
    private Set<String> statusCodes;
    @ApiModelProperty("ERP状态meaning")
    @Transient
    private String erpStatusMeaning;
    @ApiModelProperty("订单状态meaning")
    @Transient
    private String statusCodeMeaning;
    @ApiModelProperty("数据来源meaning")
    @Transient
    private String sourceCodeMeaning;
    @ApiModelProperty("来源单据类别meaning")
    @Transient
    private String sourceBillTypeCodeMeaning;
    @ApiModelProperty("审批导出ERP状态meaning")
    @Transient
    private String approvedSyncStatusMeaning;
    @ApiModelProperty("业务实体名")
    @Transient
    private String ouName;
    @ApiModelProperty("采购订单类型")
    @Transient
    private String poTypeCode;
    @ApiModelProperty("采购订单类型值")
    @Transient
    private String poTypeCodeMeaning;
    @ApiModelProperty("用于承载计算状态结果字段")
    @Transient
    @LovValue(
            value = "SODR.PO_STATUS",
            meaningField = "displayStatusCodeMeaning"
    )
    private String displayStatusCode;
    @ApiModelProperty("用于翻译计算状态")
    @Transient
    private String displayStatusCodeMeaning;
    @ApiModelProperty("ERP 创建日期开始")
    @Transient
    @DateTimeFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    private Date erpCreationDateStart;
    @ApiModelProperty("ERP 创建日期至")
    @Transient
    @DateTimeFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    private Date erpCreationDateEnd;
    @ApiModelProperty("来源平台代码meaning")
    @Transient
    private String poSourcePlatformMeaning;
    @ApiModelProperty("整单取消查询meaning")
    @Transient
    private Long poCancelSelect;
    @ApiModelProperty("整单关闭查询meaning")
    @Transient
    private Long poCloseSelect;
    @ApiModelProperty("流程控制查询meaning")
    @Transient
    private Long poProcessSelect;
    @ApiModelProperty(
            value = "供应商公司编码",
            hidden = true
    )
    @Transient
    private String supplierCompanyCode;
    @Transient
    @ApiModelProperty(
            value = "订单头id集合(导出用)",
            hidden = true
    )
    @Encrypt
    private Set<Long> poHeaderIds;
    @ApiModelProperty("更新时间从")
    @Transient
    @DateTimeFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    private Date lastUpdateDateStart;
    @ApiModelProperty("更新日期至")
    @DateTimeFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @Transient
    private Date lastUpdateDateEnd;
    @ApiModelProperty("超期数量")
    @Transient
    private Integer beyondQuantity;
    @ApiModelProperty("当前时间")
    @Transient
    private Date nowDate;
    @ApiModelProperty("是否超期 0：未超期 1：超期")
    @Transient
    private Integer beyondFlag;
    @ApiModelProperty("评价标识")
    private Integer evaluationFlag;
    @ApiModelProperty("承诺日期回传ERP统计状态 0：全部同步成功 >0：部分同步失败")
    @Transient
    private Integer deliverySyncFlag;
    @ApiModelProperty("可操作类型，cancel为可关闭,close为可关闭")
    @Transient
    private String operateType;
    @ApiModelProperty("可操作类型，cancel为可关闭,close为可关闭")
    @Transient
    private String createSyncFlay;
    @ApiModelProperty("承诺日期导出ERP状态")
    private String deliverySyncStatus;
    @ApiModelProperty("承诺日期导出ERP反馈")
    private String deliverySyncResponseMsg;
    @ApiModelProperty("承诺日期导出时间")
    private Date deliverySyncDate;
    @ApiModelProperty("申请转订单是否未保存头")
    private Integer unSaveEnable;
    @ApiModelProperty("采购申请行号")
    @Transient
    private String displayPrLineNum;
    @ApiModelProperty("待审批单据查询标志位")
    @Transient
    private Integer queryApprovingFlag;
    @ApiModelProperty("订单行集合")
    @Transient
    private List<PoLine> poLineList;
    @Transient
    private String receiveOrderTypeMeaning;
    private Long strategyHeaderId;
    @ApiModelProperty("查询采购员Ids")
    @Transient
    private String agentIds;
    @ApiModelProperty("查询采购组织Ids")
    @Transient
    private String purchaseOrgIds;
    @Transient
    private BigDecimal taxPrice;
    @ApiModelProperty("创建人姓名")
    @Transient
    private String loginName;
    @Transient
    @ApiModelProperty("组织名称")
    private String orgName;
    @Transient
    @ApiModelProperty("采购员")
    private String agentName;
    @Transient
    @ApiModelProperty("采购员")
    private String agentCode;
    @Transient
    @ApiModelProperty("采购组织名称")
    private String purOrganizationName;
    @Transient
    @ApiModelProperty("采购组织名称")
    private String purOrganizationCode;
    @Transient
    @ApiModelProperty("供应商地点代码")
    private String supplierSiteCode;
    @Transient
    @ApiModelProperty("供应商地点名称")
    private String supplierSiteName;
    @Transient
    private Integer publishFlag;
    @Transient
    @ApiModelProperty("创建人")
    private String realName;
    @Transient
    @ApiModelProperty("用户id")
    private Long userId;
    @Transient
    private Date PromiseDeliveryDate;
    @ApiModelProperty("存修改前的数据")
    @Transient
    private Map<String, Object> cacheMap;
    @ApiModelProperty("未关闭的订单行数量")
    @Transient
    private Integer unCloseCount;
    @ApiModelProperty("供应商采购品类")
    @Transient
    private Long supplierCategoryId;
    @ApiModelProperty("供应商采购品类")
    @Transient
    private String supplierCategoryCode;
    @ApiModelProperty("供应商采购品类")
    @Transient
    private String supplierCategoryName;
    @ApiModelProperty("公司编码")
    @Transient
    private String companyCode;
    @ApiModelProperty("业务实体编码")
    @Transient
    private String ouCode;
    @ApiModelProperty("采购组织编码")
    @Transient
    private String purchaseOrgCode;
    @ApiModelProperty("原订单号")
    @Transient
    private String originalPoNum;
    @ApiModelProperty("退货类型标识")
    @Transient
    private Integer returnOrderFlag;
    @ApiModelProperty("注册公司ID")
    @Transient
    private Long registeredCountryId;
    @Transient
    private List<PoLineExportVo> poLineExportVoList;
    @ApiModelProperty("商城父订单号")
    @Transient
    private String mallParentOrderNum;

    public PoHeader() {
    }

    public void modifyPricePrecisionByCurrencyCode(MdmService mdmService) {
        SmdmCurrencyDTO currencyDTO = null;
        if (Objects.nonNull(this.currencyCode)) {
            currencyDTO = mdmService.selectSmdmCurrencyDto(this.tenantId, this.currencyCode);
            this.taxIncludeAmount = Objects.isNull(this.taxIncludeAmount) ? null : this.taxIncludeAmount.setScale(currencyDTO.getFinancialPrecision(), RoundingMode.HALF_UP);
            this.amount = Objects.isNull(this.amount) ? null : this.amount.setScale(currencyDTO.getFinancialPrecision(), RoundingMode.HALF_UP);
        }

        if (Objects.nonNull(this.domesticCurrencyCode)) {
            SmdmCurrencyDTO domesticCurrencyDTO = this.domesticCurrencyCode.equals(this.currencyCode) ? currencyDTO : mdmService.selectSmdmCurrencyDto(this.tenantId, this.currencyCode);
            this.domesticTaxIncludeAmount = Objects.isNull(this.domesticTaxIncludeAmount) ? null : this.domesticTaxIncludeAmount.setScale(domesticCurrencyDTO.getFinancialPrecision(), RoundingMode.HALF_UP);
            this.domesticAmount = Objects.isNull(this.domesticAmount) ? null : this.domesticAmount.setScale(domesticCurrencyDTO.getFinancialPrecision(), RoundingMode.HALF_UP);
        }

    }

    public void clearCloseFlag() {
        if ("CLOSED".equals(this.statusCode)) {
            this.statusCode = this.oldStatusCode != null ? this.oldStatusCode : "CONFIRMED";
        }

    }

    public void modifyAmountAndTaxIncludeAmount(List<PoLine> poLineList) {
        this.amount = new BigDecimal("0");
        this.taxIncludeAmount = new BigDecimal("0");
        Iterator var2 = poLineList.iterator();

        while(var2.hasNext()) {
            PoLine pl = (PoLine)var2.next();
            if (pl.getLineAmount() != null) {
                this.amount = this.amount.add(pl.getLineAmount());
            }

            if (pl.getTaxIncludedLineAmount() != null) {
                this.taxIncludeAmount = this.taxIncludeAmount.add(pl.getTaxIncludedLineAmount());
            }
        }

    }

    public void modifyDomesticAmountAndTaxIncludeAmount(List<PoLine> poLineList, MdmService mdmService) {
        this.domesticAmount = new BigDecimal("0");
        this.domesticTaxIncludeAmount = new BigDecimal("0");
        Iterator var3 = poLineList.iterator();

        while(var3.hasNext()) {
            PoLine pl = (PoLine)var3.next();
            if (pl.getDomesticLineAmount() != null) {
                this.domesticAmount = this.domesticAmount.add(pl.getDomesticLineAmount());
            }

            if (pl.getDomesticTaxIncludedLineAmount() != null) {
                this.domesticTaxIncludeAmount = this.domesticTaxIncludeAmount.add(pl.getDomesticTaxIncludedLineAmount());
            }
        }

        this.modifyDomesticPrecision(mdmService);
    }

    public void modifyDomesticAmountAndTaxIncludeAmount(PoLine poLineBefore, PoLine poLineAfter, MdmService mdmService) {
        if (poLineBefore.getDomesticLineAmount() != null && poLineAfter.getDomesticLineAmount() != null && Objects.nonNull(this.domesticAmount)) {
            this.domesticAmount = this.domesticAmount.subtract(poLineBefore.getDomesticLineAmount()).add(poLineAfter.getDomesticLineAmount());
        }

        if (poLineBefore.getDomesticTaxIncludedLineAmount() != null && poLineAfter.getDomesticTaxIncludedLineAmount() != null && Objects.nonNull(this.domesticTaxIncludeAmount)) {
            this.domesticTaxIncludeAmount = this.domesticTaxIncludeAmount.subtract(poLineBefore.getDomesticTaxIncludedLineAmount()).add(poLineAfter.getDomesticTaxIncludedLineAmount());
        }

        this.modifyDomesticPrecision(mdmService);
    }

    private void modifyDomesticPrecision(MdmService mdmService) {
        if (!Objects.isNull(mdmService)) {
            SmdmCurrencyDTO currencyDTO = mdmService.selectSmdmCurrencyDto(this.tenantId, this.domesticCurrencyCode);
            this.domesticAmount = Objects.isNull(this.domesticAmount) ? null : this.domesticAmount.setScale(currencyDTO.getDefaultPrecision(), RoundingMode.HALF_UP);
            this.domesticTaxIncludeAmount = Objects.isNull(this.domesticTaxIncludeAmount) ? null : this.domesticTaxIncludeAmount.setScale(currencyDTO.getDefaultPrecision(), RoundingMode.HALF_UP);
        }
    }

    public boolean isByErpOrSrmPr() {
        return ("ERP".equals(this.getPoSourcePlatform()) || "SRM".equals(this.getPoSourcePlatform()) || "SHOP".equals(this.getPoSourcePlatform())) && "PURCHASE_REQUEST".equals(this.getSourceBillTypeCode());
    }

    public void confirmInfo() {
        this.confirmedBy = 1L;
        this.confirmedDate = new Date();
        this.confirmedFlag = Flag.YES;
    }

    public void refusedInfo(String refusedRemark) {
        this.refusedBy = 1L;
        this.refusedDate = new Date();
        this.refusedFlag = Flag.YES;
        this.refusedRemark = refusedRemark;
    }

    public PoHeader updateAdvancePaymentAmount(BigDecimal paymentAdvanceAmount, Boolean addFlag) {
        if (Objects.isNull(this.advancePaymentAmount)) {
            this.advancePaymentAmount = BigDecimal.ZERO;
        }

        if (addFlag) {
            this.advancePaymentAmount = this.advancePaymentAmount.add(paymentAdvanceAmount);
        } else {
            this.advancePaymentAmount = this.advancePaymentAmount.subtract(paymentAdvanceAmount);
        }

        return this;
    }

    public void clearInfo() {
        this.refusedBy = null;
        this.refusedDate = null;
        this.refusedFlag = Flag.NO;
        this.refusedRemark = null;
    }

    public void updateSyncStatus(String syncStatus, String syncResponseMsg, Date syncDate) {
        if (!"SRM".equals(this.sourceCode)) {
            this.approvedSyncStatus = syncStatus;
            this.approvedSyncResponseMsg = syncResponseMsg;
            this.approvedSyncDate = syncDate;
        }

    }

    public void resetStatus() {
        this.refusedBy = null;
        this.refusedDate = null;
        this.refusedFlag = Flag.NO;
        this.refusedRemark = null;
        this.confirmedBy = null;
        this.confirmedDate = null;
        this.confirmedFlag = Flag.NO;
        this.setStatusCode("PENDING");
    }

    public void checkPoHeaderS(List<PoHeader> poHeaderList) {
        if (CollectionUtils.isNotEmpty(poHeaderList)) {
            Iterator var2 = poHeaderList.iterator();

            while(var2.hasNext()) {
                PoHeader p = (PoHeader)var2.next();
                boolean flag = "SRM".equals(p.getSourceCode()) && ("PENDING".equals(p.getStatusCode()) || "REJECTED".equals(p.getStatusCode()));
                if (!flag) {
                    throw new CommonException("error.po.status.or.source.bill.type", new Object[0]);
                }

                if ("E-COMMERCE".equals(p.getPoSourcePlatform())) {
                    throw new CommonException("error.po.source.platform.not.e.commerce", new Object[0]);
                }
            }
        }

    }

    public void wholeCancel() {
        this.statusCode = "CANCELED";
        this.cancelledFlag = Flag.YES;
    }

    public PoDTO copyProperties() {
        PoDTO poDTO = new PoDTO();
        BeanUtils.copyProperties(this, poDTO);
        return poDTO;
    }

    public void initPoOrder(String poNum) {
        this.setPoSourcePlatform("SRM");
        this.setStatusCode("PENDING");
        this.setPoNum(poNum);
        this.setDisplayPoNum(poNum);
        this.setSourceBillTypeCode("PURCHASE_ORDER");
        this.setErpCreationDate(new Date());
    }

    public boolean notTriggerUpdate(boolean enableSrmReview) {
        return !enableSrmReview && !Flag.YES.equals(this.erpApprovalFlag);
    }

    public void onlyUpdateHeaderFlag(PoHeaderRepository poHeaderRepository) {
        this.publishCancelFlag = Flag.YES;
        if (!"PUBLISHED".equals(this.getStatusCode())) {
            this.statusCode = "APPROVED";
        }

        if (Objects.equals(Flag.YES, this.confirmedFlag)) {
            this.confirmUpdateFlag = Flag.YES;
        } else {
            this.confirmUpdateFlag = Flag.NO;
        }

        this.confirmedFlag = Flag.NO;
        poHeaderRepository.updateOptional(this, new String[]{"publishCancelFlag", "statusCode", "confirmedFlag", "confirmUpdateFlag"});
    }

    public void onlyUpdateHeaderFlag() {
        this.publishCancelFlag = Flag.YES;
        if (!"PUBLISHED".equals(this.getStatusCode())) {
            this.statusCode = "APPROVED";
        }

        if (Objects.equals(Flag.YES, this.confirmedFlag)) {
            this.confirmUpdateFlag = Flag.YES;
        } else {
            this.confirmUpdateFlag = Flag.NO;
        }

        this.confirmedFlag = Flag.NO;
    }

    public void calculatePoSts() {
        if (Flag.YES.equals(this.closedFlag)) {
            this.statusCode = "CLOSED";
        } else if (Flag.YES.equals(this.cancelledFlag)) {
            this.statusCode = "CANCELED";
        } else if (Flag.YES.equals(this.publishCancelFlag)) {
            this.statusCode = "PUBLISH_CANCEL";
        } else {
            if (StringUtils.equals(this.statusCode, "PUBLISHED") && Flag.YES.equals(this.confirmedFlag)) {
                this.statusCode = "CONFIRMED";
            }

        }
    }

    public void backCalculatePoSts(String statusCode) {
        if (StringUtils.equals(this.statusCode, "CONFIRMED") && Flag.YES.equals(this.confirmedFlag)) {
            this.statusCode = "PUBLISHED";
        }

    }

    public String getDisplaySupplierName() {
        return this.getSupplierName() != null ? this.getSupplierName() : this.getSupplierCompanyName();
    }

    public boolean needSnapShot(boolean enableSrmReview, boolean enableManualPublish) {
        boolean needSnapShotForSapPo = !enableSrmReview && Flag.YES.equals(this.erpApprovalFlag) && enableManualPublish;
        LOGGER.debug("calculating snap shot flag when po updating , flag for sap: [{}], flag for srm: [{}], po status: [{}]", new Object[]{needSnapShotForSapPo, enableSrmReview, this.statusCode});
        return (enableSrmReview || needSnapShotForSapPo) && "PUBLISHED".equals(this.statusCode);
    }

    public boolean needSnapShotNew(boolean enableSrmReview, boolean enableManualPublish) {
        boolean needSnapShotForSapPo = !enableSrmReview && Flag.YES.equals(this.erpApprovalFlag) && enableManualPublish;
        LOGGER.debug("calculating snap shot flag when po updating , flag for sap: [{}], flag for srm: [{}], po status: [{}]", new Object[]{needSnapShotForSapPo, enableSrmReview, this.statusCode});
        LOGGER.debug("PoHeader CloseFlag:" + this.closedFlag + " and CancelFlag:" + this.cancelledFlag);
        if ((Flag.YES.equals(this.closedFlag) || Flag.YES.equals(this.cancelledFlag)) && "PUBLISHED".equals(this.statusCode)) {
            return true;
        } else {
            return (enableSrmReview || needSnapShotForSapPo) && "PUBLISHED".equals(this.statusCode);
        }
    }

    public void poFeedback() {
        this.statusCode = "DELIVERY_DATE_REVIEW";
        this.feedbackDate = new Date();
    }

    public void poConfirm() {
        this.confirmedDate = new Date();
        this.confirmedFlag = Flag.YES;
        this.statusCode = "PUBLISHED";
    }

    public void poApprove(String status) {
        this.validApproveSts(status);
        this.approvedDate = new Date();
        this.approvedFlag = Flag.YES;
        this.statusCode = "APPROVED";
    }

    public void poReject(String status) {
        this.validApproveSts(status);
        this.statusCode = "REJECTED";
    }

    public void validApproveSts(String status) {
        if (!"REJECTED".equals(status) && !"SUBMITTED".equals(status) && !"SUBMITTED_WFL".equals(status) && !"PUBLISHED".equals(status)) {
            throw new CommonException("error.order.status_not_suit", new Object[0]);
        }
    }

    public void validPublishSts(String status) {
        if (!"PUBLISH_CANCEL".equals(status) && !"APPROVED".equals(status)) {
            throw new CommonException("error.order.published_sts_error", new Object[0]);
        }
    }

    public static void poPublishOrCancel(PoDTO poDTO, String statusCode, PoHeaderRepository poHeaderRepository, PoLineLocationRepository poLineLocationRepository) {
        PoHeader poHeaderEntity = (PoHeader)poHeaderRepository.selectByPrimaryKey(poDTO.getPoHeaderId());
        poDTO.setConfirmedFlag(poHeaderEntity.getConfirmedFlag());
        poDTO.setConfirmUpdateFlag(poHeaderEntity.getConfirmUpdateFlag());
        poDTO.updateConfirmedFlagHandle();
        PoHeader poHeader = new PoHeader();
        BeanUtils.copyProperties(poDTO, poHeader);
        PoHeader entity = (PoHeader)poHeaderRepository.selectByPrimaryKey(poHeader);
        poHeader.validPublishSts(entity.getStatusCode());
        poHeader.setStatusCode(statusCode);
        if ("PUBLISHED".equals(statusCode)) {
            poHeader.setReleasedDate(new Date());
            poHeader.setReleasedFlag(Flag.YES);
            poHeader.setConfirmedFlag(Flag.NO);
            poHeaderRepository.updateOptional(poHeader, new String[]{"statusCode", "remark", "releasedFlag", "releasedDate", "confirmedFlag", "confirmUpdateFlag"});
        } else {
            if (!"PUBLISH_CANCEL".equals(statusCode)) {
                throw new CommonException("error.data_invalid", new Object[0]);
            }

            poHeader.setPublishCancelFlag(Flag.YES);
            poHeader.setCancelledFlag(Flag.NO);
            poHeader.setConfirmedFlag(Flag.NO);
            poHeader.setClosedFlag(Flag.NO);
            poHeader.setReleasedFlag(Flag.NO);
            poHeaderRepository.updateOptional(poHeader, new String[]{"statusCode", "remark", "releasedFlag", "releasedDate", "confirmedFlag", "publishCancelFlag", "closedFlag", "confirmedFlag", "confirmUpdateFlag"});
        }

        List<PoLineLocation> poDTOPoLineLocationList = poDTO.getPoLineLocationList();
        if (CollectionUtils.isNotEmpty(poDTOPoLineLocationList)) {
            poDTOPoLineLocationList.forEach((item) -> {
                poLineLocationRepository.updateOptional(item, new String[]{"remark"});
            });
        }

        BeanUtils.copyProperties(poHeader, poDTO);
    }

    public void handlePrice(PoHeaderRepository poHeaderRepository) {
        PoHeader poHeader = poHeaderRepository.selectPrice(this.getPoHeaderId());
        if (poHeader != null) {
            this.setAmount(poHeader.getAmount());
            if (poHeader.getTaxIncludeAmount() == null) {
                poHeader.setTaxIncludeAmount(new BigDecimal(0L));
            }

            this.setTaxIncludeAmount(poHeader.getTaxIncludeAmount());
        } else {
            this.setAmount(new BigDecimal(0L));
            this.setTaxIncludeAmount(new BigDecimal(0L));
        }

    }

    public void handlePrice(List<PoLine> poLineList) {
        BigDecimal amount = BigDecimal.ZERO;
        BigDecimal taxIncludeAmount = BigDecimal.ZERO;

        PoLine poLine;
        for(Iterator var4 = ((List)poLineList.stream().filter((item) -> {
            return !Flag.YES.equals(item.getCancelledFlag()) && !Flag.YES.equals(item.getDeleteFlag());
        }).collect(Collectors.toList())).iterator(); var4.hasNext(); taxIncludeAmount = taxIncludeAmount.add(poLine.getTaxIncludedLineAmount())) {
            poLine = (PoLine)var4.next();
            amount = amount.add(poLine.getLineAmount());
        }

        this.setAmount(amount);
        this.setTaxIncludeAmount(taxIncludeAmount);
    }

    public void handlePoHeaderStatus() {
        if (!CollectionUtils.isEmpty(this.getPoLineList())) {
            Set<Long> closeLines = new HashSet();
            Set<Long> cancelLines = new HashSet();
            Iterator var3 = this.getPoLineList().iterator();

            while(var3.hasNext()) {
                PoLine poLine = (PoLine)var3.next();
                if (Flag.YES.equals(poLine.getClosedFlag())) {
                    closeLines.add(poLine.getPoLineId());
                }

                if (Flag.YES.equals(poLine.getCancelledFlag())) {
                    cancelLines.add(poLine.getPoLineId());
                }
            }

            LOGGER.info("25855 testlog3" + this.getPoLineList().size() + "  close line size:" + closeLines.size());
            if (this.getPoLineList().size() == closeLines.size()) {
                LOGGER.info("25855 testlog4");
                this.closedFlag = Flag.YES;
                this.statusCode = "CLOSED";
                this.autoSubmitFlag = Flag.NO;
            } else if (this.getPoLineList().size() == cancelLines.size()) {
                LOGGER.info("25855 testlog5");
                this.cancelledFlag = Flag.YES;
                this.statusCode = "CANCELED";
                this.autoSubmitFlag = Flag.NO;
            } else if (cancelLines.size() + closeLines.size() == this.getPoLineList().size()) {
                LOGGER.info("25855 testlog6");
                this.closedFlag = Flag.YES;
                this.statusCode = "CLOSED";
                this.autoSubmitFlag = Flag.NO;
            }
        }
    }

    public void versionHandleBeforeUpdate(String orderVersionManageRuleCode) {
        if (this.versionNum == null) {
            this.versionInit(orderVersionManageRuleCode);
        }

    }

    public void statusHandleBeforeUpdate() {
        if (Flag.YES.equals(this.closedFlag)) {
            this.statusCode = "CLOSED";
        } else if (Flag.YES.equals(this.cancelledFlag)) {
            this.statusCode = "CANCELED";
        }
    }

    public void checkEcCancelStatus() {
        LOGGER.debug("PoHeader_checkEcCancelStatus_Po_status is:{},confirmedFlag is:{}", this.statusCode, this.confirmedFlag);
        if (!Objects.equals("PENDING", this.statusCode) && !Objects.equals("SUBMITTED", this.statusCode) && !Objects.equals("REJECTED", this.statusCode) && (!Objects.equals("PUBLISHED", this.statusCode) || !Objects.equals(Flag.NO, this.confirmedFlag))) {
            throw new CommonException("error.ec.order.status__can_not_cancel", new Object[0]);
        }
    }

    public void versionInit(String orderVersionManageRuleCode) {
        if (!StringUtils.isEmpty(orderVersionManageRuleCode) && !Objects.equals("SRM", orderVersionManageRuleCode)) {
            if (Objects.equals("ERP", orderVersionManageRuleCode)) {
                this.versionNum = PoConstants.PO_DEFAULT_REVERSION_ERP;
            }
        } else {
            this.versionNum = PoConstants.PO_DEFAULT_REVERSION;
        }

        this.versionDate = new Date();
    }

    public void statusHandleBeforeUpdate(PoHeaderRepository poHeaderRepository) {
        PoHeader poHeader = (PoHeader)poHeaderRepository.selectByPrimaryKey(this.poHeaderId);
        Assert.notNull(poHeader, "error.order.not.exsit");
        if ("PUBLISHED".equals(poHeader.getStatusCode()) && "APPROVED".equals(this.statusCode)) {
            this.statusCode = "PUBLISHED";
        }

    }

    public void stsHandleAfterPoUpdate(List<PoLineLocation> poLineLocations) {
        if (CollectionUtils.isEmpty(poLineLocations)) {
            this.cancelledFlag = Flag.YES;
            this.statusCode = "CANCELED";
            this.closedFlag = Flag.NO;
        } else {
            List<PoLineLocation> notCancelList = (List)poLineLocations.stream().filter((item) -> {
                return item.getCancelledFlag() == 0;
            }).collect(Collectors.toList());
            if (this.checkAllStatusIsCancelOrClosed(poLineLocations, true)) {
                this.cancelledFlag = Flag.YES;
                this.statusCode = "CANCELED";
                this.closedFlag = Flag.NO;
            } else if (this.checkAllStatusIsCancelOrClosed(notCancelList, false)) {
                this.closedFlag = Flag.YES;
                this.statusCode = "CLOSED";
                this.cancelledFlag = Flag.NO;
            } else {
                this.cancelledFlag = Flag.NO;
                this.closedFlag = Flag.NO;
                if (Objects.nonNull(this.oldStatusCode)) {
                    this.statusCode = this.oldStatusCode;
                }
            }

        }
    }

    public void locationFlagHandle(List<PoLineLocation> poLineLocations) {
        poLineLocations.forEach((poLineLocation) -> {
            if (Flag.YES.equals(this.confirmedFlag) && Flag.NO.equals(this.publishCancelFlag) && Flag.NO.equals(poLineLocation.getFrozenFlag()) && Flag.NO.equals(poLineLocation.getClosedFlag()) && Flag.NO.equals(poLineLocation.getClosedFlag())) {
                poLineLocation.setCanCreateAsnFlag(Flag.YES);
            } else {
                poLineLocation.setCanCreateAsnFlag(Flag.NO);
            }

        });
    }

    public boolean checkAllStatusIsCancelOrClosed(List<PoLineLocation> poLineLocationList, boolean cancel) {
        if (CollectionUtils.isEmpty(poLineLocationList)) {
            return false;
        } else {
            Iterator var3;
            PoLineLocation poLineLocation;
            if (cancel) {
                var3 = poLineLocationList.iterator();

                while(var3.hasNext()) {
                    poLineLocation = (PoLineLocation)var3.next();
                    if (!Flag.YES.equals(poLineLocation.getCancelledFlag())) {
                        return false;
                    }
                }
            } else {
                var3 = poLineLocationList.iterator();

                while(var3.hasNext()) {
                    poLineLocation = (PoLineLocation)var3.next();
                    if (!Flag.YES.equals(poLineLocation.getClosedFlag())) {
                        return false;
                    }
                }
            }

            return true;
        }
    }

    public void handlePoStatusForQuery(String... queryStatusArray) {
        if (ArrayUtils.isNotEmpty(queryStatusArray)) {
            if (this.statusCodes == null) {
                this.statusCodes = new HashSet(queryStatusArray.length);
            }

            this.statusCodes.addAll(Arrays.asList(queryStatusArray));
        }

        if (this.supplierTenantId == null) {
            this.snapshotFlag = Flag.YES;
        } else {
            this.releaseFlag = Flag.YES;
        }

    }

    public static List<PoDTO> convertSaveDetailToPoDtoList(List<PoOrderSaveDTO> poOrderSavaDTOList, Long tenantId) {
        if (CollectionUtils.isEmpty(poOrderSavaDTOList)) {
            return Collections.emptyList();
        } else {
            List<PoDTO> poDTOList = new ArrayList(poOrderSavaDTOList.size());
            poOrderSavaDTOList.forEach((item) -> {
                PoDTO poDTO = new PoDTO();
                BeanUtils.copyProperties(item.getPoHeaderDetailDTO(), poDTO);
                poDTO.setTenantId(tenantId);
                List<PoLineDetailDTO> poLineDetailDTOS = item.getPoLineDetailDTOs();
                if (CollectionUtils.isNotEmpty(poLineDetailDTOS)) {
                    List<PoLineLocation> poLineLocationList = new ArrayList(poLineDetailDTOS.size());
                    Iterator var6 = poLineDetailDTOS.iterator();

                    while(var6.hasNext()) {
                        PoLineDetailDTO poLineDetailDTO = (PoLineDetailDTO)var6.next();
                        PoLineLocation poLineLocation = new PoLineLocation();
                        BeanUtils.copyProperties(poLineDetailDTO, poLineLocation);
                        poLineLocation.setPoHeaderId(poDTO.getPoHeaderId());
                        poLineLocation.setObjectVersionNumber(poLineDetailDTO.getLocationVersionNumber());
                        poLineLocation.setTenantId(tenantId);
                        poLineLocationList.add(poLineLocation);
                    }

                    poDTO.setPoLineLocationList(poLineLocationList);
                }

                poDTOList.add(poDTO);
            });
            return poDTOList;
        }
    }

    public static List<PoHeader> updatePoLineLocation(Long tenantId, List<PoOrderSaveDTO> poOrderSavaDTOList, PoLineLocationRepository poLineLocationRepository, CustomizeSettingHelper customizeSettingHelper) {
        List<PoHeader> poHeaderList = new ArrayList();
        MdmService mdmService = (MdmService)ApplicationContextHelper.getContext().getBean(MdmService.class);
        poOrderSavaDTOList.forEach((poOrderSavaDTO) -> {
            PoHeader poHeader = new PoHeader();
            BeanUtils.copyProperties(poOrderSavaDTO.getPoHeaderDetailDTO(), poHeader);
            poHeader.setTenantId(tenantId);
            poHeaderList.add(poHeader);
            List<PoLineDetailDTO> poLineBasicDetailDTOs = poOrderSavaDTO.getPoLineDetailDTOs();
            if (CollectionUtils.isNotEmpty(poLineBasicDetailDTOs)) {
                List<PoLine> poLineList = new ArrayList();
                List<PoLineLocation> modifyList = new ArrayList();
                Iterator var10 = poLineBasicDetailDTOs.iterator();

                while(var10.hasNext()) {
                    PoLineDetailDTO poLineBasicDetailDTO = (PoLineDetailDTO)var10.next();
                    PoLineLocation poLineLocation = new PoLineLocation();
                    PoLine poLine = new PoLine();
                    BeanUtils.copyProperties(poLineBasicDetailDTO, poLineLocation);
                    poLineLocation.setObjectVersionNumber(poLineBasicDetailDTO.getLocationVersionNumber());
                    poLineLocation.handleDeliverCommittedDate(tenantId, customizeSettingHelper);
                    modifyList.add(poLineLocation);
                    BeanUtils.copyProperties(poLineBasicDetailDTO, poLine);
                    List<PoLine> samePoLine = (List)poLineList.stream().filter((poLineNew) -> {
                        return poLineNew.getPoLineId().equals(poLineBasicDetailDTO.getPoLineId());
                    }).collect(Collectors.toList());
                    LOGGER.debug("PoHeader-updatePoLineLocation-poLineList is:{}", poLineList);
                    LOGGER.debug("PoHeader-updatePoLineLocation-poLineBasicDetailDTO is:{}", poLineBasicDetailDTO);
                    if (CollectionUtils.isNotEmpty(samePoLine)) {
                        LOGGER.debug("PoHeader-updatePoLineLocation-poLineId :{},have same poLine", poLineBasicDetailDTO.getPoLineId());
                    } else {
                        poLine.setObjectVersionNumber(poLineBasicDetailDTO.getLineVersionNumber());
                        poLine.modifyDomesticInfoByExchangeRate(mdmService, poHeader.getDomesticCurrencyCode());
                        poLineList.add(poLine);
                    }
                }

                LOGGER.debug("PoHeader-updatePoLineLocation-poLineList is:{}", poLineList);
                ((PoLineRepository)ApplicationContextHelper.getContext().getBean(PoLineRepository.class)).batchUpdateOptional(poLineList, new String[]{"domesticTaxIncludedPrice", "domesticUnitPrice", "domesticTaxIncludedLineAmount", "domesticLineAmount"});
                poHeader.modifyDomesticAmountAndTaxIncludeAmount(poLineList, mdmService);
                CustomizeHelper.ignore(() -> {
                    poLineLocationRepository.batchAddProcessAndChangeRecord(modifyList, "UPDATE");
                    return null;
                });
                modifyList.forEach((poLineLocationx) -> {
                    poLineLocationx.setDeliveryDateRejectFlag(0);
                    if (Objects.nonNull(poLineLocationx.getQuantity())) {
                        poLineLocationRepository.updateOptional(poLineLocationx, new String[]{"feedback", "deliveryDateRejectFlag", "promiseDeliveryDate", "quantity"});
                    } else {
                        poLineLocationRepository.updateOptional(poLineLocationx, new String[]{"feedback", "deliveryDateRejectFlag", "promiseDeliveryDate"});
                    }

                });
            }

        });
        return poHeaderList;
    }

    public static boolean checkIsInfluenceUpGrade(PoHeaderRepository poHeaderRepository, PoLineRepository poLineRepository, PoLineLocationRepository poLineLocationRepository, PoChangeConfigRepository poChangeConfigRepository, PoDTO poDto) {
        PoChangeConfig poChangeConfig = new PoChangeConfig();
        poChangeConfig.setTenantId(poDto.getTenantId());
        List<PoChangeConfig> poChangeConfigs = poChangeConfigRepository.select(poChangeConfig);
        PoHeader poHeader = new PoHeader();
        BeanUtils.copyProperties(poDto, poHeader);
        PoHeader poHeaderRecord = (PoHeader)poHeaderRepository.selectByPrimaryKey(poHeader);
        List<PoLine> poLineList = poDto.getPoLineList();
        List<PoLineLocation> poLineLocationList = new ArrayList();
        if (CollectionUtils.isNotEmpty(poDto.getPoLineList())) {
            poDto.getPoLineList().forEach((item) -> {
                if (item != null && CollectionUtils.isNotEmpty(item.getPoLineLocationList())) {
                    poLineLocationList.addAll(item.getPoLineLocationList());
                }

            });
        }

        Iterator var11 = poChangeConfigs.iterator();

        while(var11.hasNext()) {
            PoChangeConfig config = (PoChangeConfig)var11.next();

            try {
                PoChangeRecord poChangeRecord = new PoChangeRecord();
                poChangeRecord.setPoHeaderId(poDto.getPoHeaderId());
                if ("SODR_PO_HEADER".equals(config.getTableName())) {
                    Assert.notNull(poHeaderRecord, "error.data_not_exists");
                    if (judgeBeanFieldIsInfluenceUpGrade(config.getFieldName(), poHeader, poChangeRecord, config)) {
                        return true;
                    }
                } else {
                    Iterator var14;
                    if ("SODR_PO_LINE".equals(config.getTableName())) {
                        var14 = poLineList.iterator();

                        while(var14.hasNext()) {
                            PoLine line = (PoLine)var14.next();
                            PoLine lineRecord = (PoLine)poLineRepository.selectByPrimaryKey(line);
                            Assert.notNull(lineRecord, "error.data_not_exists");
                            if (judgeBeanFieldIsInfluenceUpGrade(config.getFieldName(), line, lineRecord, config)) {
                                return true;
                            }
                        }
                    } else {
                        if (!"SODR_PO_LINE_LOCATION".equals(config.getTableName())) {
                            throw new CommonException("error.error", new Object[0]);
                        }

                        var14 = poLineLocationList.iterator();

                        while(var14.hasNext()) {
                            PoLineLocation lineLocation = (PoLineLocation)var14.next();
                            PoLineLocation lineLocationEntity = (PoLineLocation)poLineLocationRepository.selectByPrimaryKey(lineLocation);
                            Assert.notNull(lineLocationEntity, "error.data_not_exists");
                            poChangeRecord.setPoLineId(lineLocationEntity.getPoLineId());
                            poChangeRecord.setDisplayLineNum(lineLocationEntity.getDisplayLineNum());
                            poChangeRecord.setPoLineLocationId(lineLocationEntity.getPoLineLocationId());
                            poChangeRecord.setDisplayLineLocationNum(lineLocationEntity.getDisplayLineLocationNum());
                            if (judgeBeanFieldIsInfluenceUpGrade(config.getFieldName(), lineLocation, lineLocationEntity, config)) {
                                return true;
                            }
                        }
                    }
                }
            } catch (Exception var17) {
                throw new CommonException("error.error", new Object[0]);
            }
        }

        return false;
    }

    public void changeDeliveryExpErpStatus(ResponseDTO responseDTO, PoHeaderRepository poHeaderRepository) {
        if (responseDTO != null && "SUCCESS".equals(responseDTO.getResponseStatus())) {
            if (CollectionUtils.isNotEmpty(responseDTO.getItfBaseBOS())) {
                DeliveryFeedBackDTO deliveryFeedBackDTO = (DeliveryFeedBackDTO)responseDTO.getItfBaseBOS().get(0);
                if (!"SUCCESS".equals(deliveryFeedBackDTO.getFeedBackStatus()) && !Objects.isNull(deliveryFeedBackDTO.getFeedBackStatus())) {
                    this.deliverySyncStatus = "FAIL";
                } else {
                    this.deliverySyncStatus = "SUCCESS";
                }

                this.deliverySyncResponseMsg = !Objects.isNull(deliveryFeedBackDTO.getFeedBackMessage()) && deliveryFeedBackDTO.getFeedBackMessage().length() >= 480 ? deliveryFeedBackDTO.getFeedBackMessage().substring(0, 478) : deliveryFeedBackDTO.getFeedBackMessage();
            } else {
                this.deliverySyncStatus = "SUCCESS";
            }
        } else {
            this.deliverySyncStatus = "FAIL";
            this.deliverySyncResponseMsg = !Objects.isNull(responseDTO.getResponseMessage()) && responseDTO.getResponseMessage().length() >= 480 ? responseDTO.getResponseMessage().substring(0, 478) : responseDTO.getResponseMessage();
        }

        this.deliverySyncDate = new Date();
        this.objectVersionNumber = ((PoHeader)poHeaderRepository.selectByPrimaryKey(this.poHeaderId)).getObjectVersionNumber();
        poHeaderRepository.updateOptional(this, new String[]{"deliverySyncDate", "deliverySyncResponseMsg", "deliverySyncStatus"});
    }

    private static boolean judgeBeanFieldIsInfluenceUpGrade(String fieldName, Object currentObj, Object recordObj, PoChangeConfig config) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String[] field = fieldName.split("_");
        StringBuilder sb = new StringBuilder();
        String[] var6 = field;
        int var7 = field.length;

        for(int var8 = 0; var8 < var7; ++var8) {
            String s = var6[var8];
            s = s.substring(0, 1).toUpperCase() + s.substring(1);
            sb.append(s);
        }

        Method currentMethod = currentObj.getClass().getMethod("get" + sb.toString());
        Method recordMethod = recordObj.getClass().getMethod("get" + sb.toString());
        if (currentMethod != null && recordMethod != null) {
            Object currentValue = currentMethod.invoke(currentObj);
            Object recordValue = recordMethod.invoke(recordObj);
            if (!recordValue.equals(currentValue) && Flag.YES.equals(config.getUpgradeFlag())) {
                return true;
            }
        }

        return false;
    }

    public void allEndDateAddOne() {
        if (this.submitDateEnd != null) {
            this.submitDateEnd = this.getTomorrowDate(this.submitDateEnd);
        }

        if (this.releaseDateEnd != null) {
            this.releaseDateEnd = this.getTomorrowDate(this.releaseDateEnd);
        }

        if (this.confirmDateEnd != null) {
            this.confirmDateEnd = this.getTomorrowDate(this.confirmDateEnd);
        }

        if (this.creationDateEnd != null) {
            this.creationDateEnd = this.getTomorrowDate(this.creationDateEnd);
        }

        if (this.erpCreationDateEnd != null) {
            this.erpCreationDateEnd = this.getTomorrowDate(this.erpCreationDateEnd);
        }

        if (this.feedbackDateEnd != null) {
            this.feedbackDateEnd = this.getTomorrowDate(this.feedbackDateEnd);
        }

    }

    public void validUniqueIndex(PoHeaderRepository poHeaderRepository) {
        PoHeader poHeader = new PoHeader();
        poHeader.setTenantId(this.getTenantId());
        poHeader.setPoNum(this.getPoNum());
        poHeader.setPoTypeId(this.getPoTypeId());
        if (poHeaderRepository.selectOne(poHeader) != null) {
            throw new CommonException("error.order.unique_index_reapeat", new Object[0]);
        }
    }

    public void changeDeliveryExpErpStatus(ResponseDTO responseDTO, PoHeaderRepository poHeaderRepository, PoProcessActionService poProcessActionService, PoHeaderEsRepository poHeaderEsRepository) {
        if (responseDTO != null) {
            if ("SUCCESS".equals(responseDTO.getResponseStatus())) {
                new PoExpFeedbackDTO();
                List<PoExpFeedbackDTO> responseEntity = (List<PoExpFeedbackDTO>) responseDTO.getItfBaseBOS();
                LOGGER.debug(responseEntity.get(0) + "______________+++++++++++++++++++++++++++++______");
                responseEntity.forEach((poExpFeedbackDTO) -> {
                    if (!"ERROR".equals(poExpFeedbackDTO.getFeedBackStatus())) {
                        PoHeaderEs poHeaderEs = new PoHeaderEs();
                        BeanUtils.copyProperties(poExpFeedbackDTO, poHeaderEs);
                        poHeaderEs.setEsPoNumber(poExpFeedbackDTO.getErpPoNumber());
                        poHeaderEs.setDataVersion(0L);
                        List<PoHeaderEs> poHeaderEsList = poHeaderEsRepository.selectByCondition(Condition.builder(PoHeaderEs.class).andWhere(Sqls.custom().andEqualTo("esPoNumber", poExpFeedbackDTO.getErpPoNumber())).build());
                        if (CollectionUtils.isEmpty(poHeaderEsList)) {
                            poHeaderEsRepository.insertSelective(poHeaderEs);
                        }

                        this.setDisplayPoNum(((PoExpFeedbackDTO)responseEntity.get(0)).getErpPoNumber());
                        this.createSyncStatus = "SUCCESS";
                        poProcessActionService.insert(this.poHeaderId, "SYNC_SUCCESS");
                    } else {
                        this.createSyncStatus = "FAIL";
                        this.createSyncResponseMsg = "SRM订单回传ERP: " + poExpFeedbackDTO.getFeedBackMessage();
                        poProcessActionService.insert(this.poHeaderId, "SYNC_FALSE");
                    }

                });
            } else {
                this.createSyncStatus = "FAIL";
                poProcessActionService.insert(this.poHeaderId, "SYNC_FALSE");
                this.createSyncResponseMsg = "SRM订单回传ERP: " + responseDTO.getResponseMessage();
            }
        } else {
            this.createSyncStatus = "FAIL";
            poProcessActionService.insert(this.poHeaderId, "SYNC_FALSE");
            this.createSyncResponseMsg = "SRM订单回传ERP: " + responseDTO.getResponseMessage();
        }

        this.createSyncDate = new Date();
        if (StringUtils.isNotEmpty(this.createSyncResponseMsg)) {
            this.createSyncResponseMsg = this.createSyncResponseMsg.length() > ConstantsOfBigDecimal.MESSAGE_MAX_LENGTH ? this.createSyncResponseMsg.substring(0, ConstantsOfBigDecimal.MESSAGE_MAX_LENGTH) : this.createSyncResponseMsg;
        }

        poHeaderRepository.updateOptional(this, new String[]{"createSyncStatus", "createSyncResponseMsg", "createSyncDate", "displayPoNum"});
    }

    public void changeDeliveryExpErpStatus(ResponseDTO responseDTO, PoHeaderRepository poHeaderRepository, PoProcessActionService poProcessActionService, PoHeaderEsRepository poHeaderEsRepository, PoHeaderEsRepository poHeaderEsRepository1, PoLineEsRepository poLineEsRepository, PoLineLocationEsRepository poLineLocationEsRepository, PoLineRepository poLineRepository, PoLineLocationRepository poLineLocationRepository) {
        if (responseDTO != null) {
            if ("SUCCESS".equals(responseDTO.getResponseStatus())) {
                new PoExpFeedbackDTO();
                List<PoExpFeedbackDTO> responseEntity = (List<PoExpFeedbackDTO>) responseDTO.getItfBaseBOS();
                LOGGER.debug(responseEntity.get(0) + "___________________");
                responseEntity.forEach((poExpFeedbackDTO) -> {
                    if (!"ERROR".equals(poExpFeedbackDTO.getFeedBackStatus())) {
                        PoHeaderEs poHeaderEs = new PoHeaderEs();
                        BeanUtils.copyProperties(poExpFeedbackDTO, poHeaderEs);
                        poHeaderEs.setEsPoNumber(poExpFeedbackDTO.getErpPoNumber());
                        poHeaderEs.setPoHeaderId(poExpFeedbackDTO.getPoHeaderId());
                        poHeaderEs.setDataVersion(0L);
                        List<PoHeaderEs> poHeaderEsList = poHeaderEsRepository.selectByCondition(Condition.builder(PoHeaderEs.class).andWhere(Sqls.custom().andEqualTo("esPoNumber", poExpFeedbackDTO.getErpPoNumber()).andEqualTo("poHeaderId", poExpFeedbackDTO.getPoHeaderId())).build());
                        if (CollectionUtils.isEmpty(poHeaderEsList)) {
                            poHeaderEsRepository.insertSelective(poHeaderEs);
                            List<PoLine> poLines = poLineRepository.selectByCondition(Condition.builder(PoLine.class).andWhere(Sqls.custom().andEqualTo("poHeaderId", poExpFeedbackDTO.getPoHeaderId())).build());
                            if (CollectionUtils.isNotEmpty(poLines)) {
                                poLines.forEach((poLine) -> {
                                    PoLineEs poLineEs = new PoLineEs();
                                    poLineEs.setDataVersion(0L);

                                    try {
                                        poLineEs.setEsPoLineNum((String)Optional.ofNullable(poLine.getLineNum()).map((item) -> {
                                            return item.toString() + "0";
                                        }).orElse((String) null));
                                        TaskResultBox taskResultBox = AdaptorTaskHelper.executeAdaptorTask("SPUC_ORDER_ES_PO_LINE", DetailsHelper.getUserDetails().getTenantNum(), JSON.toJSON(this.initObject(Collections.singletonList(poLine))));
                                        if (null != taskResultBox) {
                                            Map result = (Map)taskResultBox.get(0, Map.class);
                                            if (!result.isEmpty()) {
                                                poLineEs.setEsPoLineNum(result.get("esLine").toString());
                                            }
                                        }
                                    } catch (Exception var10) {
                                        poLineEs.setEsPoLineNum((String)Optional.ofNullable(poLine.getLineNum()).map((item) -> {
                                            return item.toString() + "0";
                                        }).orElse((String) null));
                                    }

                                    poLineEs.setPoHeaderEsId(poHeaderEs.getPoHeaderEsId());
                                    poLineEs.setPoLineId(poLine.getPoLineId());
                                    poLineEs.setEsPoNumber(poExpFeedbackDTO.getErpPoNumber());
                                    poLineEs.setExternalSystemCode(poExpFeedbackDTO.getExternalSystemCode());
                                    poLineEsRepository.insertSelective(poLineEs);
                                    List<PoLineLocation> poLineLocations = poLineLocationRepository.selectByCondition(Condition.builder(PoLineLocation.class).andWhere(Sqls.custom().andEqualTo("poLineId", poLine.getPoLineId())).build());
                                    if (CollectionUtils.isNotEmpty(poLineLocations)) {
                                        poLineLocations.forEach((poLineLocation) -> {
                                            PoLineLocationEs poLineLocationEs = new PoLineLocationEs();
                                            poLineLocationEs.setDataVersion(0L);
                                            poLineLocationEs.setPoHeaderEsId(poHeaderEs.getPoHeaderEsId());
                                            poLineLocationEs.setPoLineEsId(poLineEs.getPoLineEsId());
                                            poLineLocationEs.setExternalSystemCode(poExpFeedbackDTO.getExternalSystemCode());
                                            poLineLocationEs.setPoLineLocationId(poLineLocation.getPoLineLocationId());
                                            poLineLocationEs.setEsPoNumber(poExpFeedbackDTO.getErpPoNumber());
                                            poLineLocationEs.setEsPoLineNum(poLineEs.getEsPoLineNum());
                                            poLineLocationEs.setEsPoLineLocationNum(poLineLocation.getDisplayLineLocationNum() + "0");
                                            poLineLocationEsRepository.insertSelective(poLineLocationEs);
                                        });
                                    }

                                });
                            }
                        }

                        this.setDisplayPoNum(((PoExpFeedbackDTO)responseEntity.get(0)).getErpPoNumber());
                        this.createSyncStatus = "SUCCESS";
                        poProcessActionService.insert(this.poHeaderId, "SYNC_SUCCESS");
                    } else {
                        this.createSyncStatus = "FAIL";
                        this.createSyncResponseMsg = "SRM订单回传ERP: " + poExpFeedbackDTO.getFeedBackMessage();
                        poProcessActionService.insert(this.poHeaderId, "SYNC_FALSE");
                    }

                });
            } else {
                this.createSyncStatus = "FAIL";
                poProcessActionService.insert(this.poHeaderId, "SYNC_FALSE");
                this.createSyncResponseMsg = "SRM订单回传ERP: " + responseDTO.getResponseMessage();
            }
        } else {
            this.createSyncStatus = "FAIL";
            poProcessActionService.insert(this.poHeaderId, "SYNC_FALSE");
            this.createSyncResponseMsg = "SRM订单回传ERP: " + responseDTO.getResponseMessage();
        }

        this.createSyncDate = new Date();
        if (StringUtils.isNotEmpty(this.createSyncResponseMsg)) {
            this.createSyncResponseMsg = this.createSyncResponseMsg.length() > ConstantsOfBigDecimal.MESSAGE_MAX_LENGTH ? this.createSyncResponseMsg.substring(0, ConstantsOfBigDecimal.MESSAGE_MAX_LENGTH) : this.createSyncResponseMsg;
        }

        poHeaderRepository.updateOptional(this, new String[]{"createSyncStatus", "createSyncResponseMsg", "createSyncDate", "displayPoNum"});
    }

    protected Object initObject(Object o) {
        if (o instanceof Iterable) {
            Map<String, Object> map = new HashMap();
            map.put("key", o);
            return map;
        } else {
            return o;
        }
    }

    public void updateChangeExpErpStatus(ResponseDTO responseDTO, PoHeaderRepository poHeaderRepository, PoProcessActionService poProcessActionService, PoHeaderEsRepository poHeaderEsRepository) {
        if (responseDTO != null) {
            LOGGER.debug("-----------------------------------responseDTO:" + responseDTO.toString());
            List<PoExpFeedbackDTO> responseEntity = (List<PoExpFeedbackDTO>) responseDTO.getItfBaseBOS();
            if (CollectionUtils.isEmpty(responseEntity)) {
                throw new CommonException(responseDTO.getResponseMessage(), new Object[0]);
            } else {
                responseEntity.forEach((poExpFeedbackDTO) -> {
                    if (!"ERROR".equals(poExpFeedbackDTO.getFeedBackStatus())) {
                        this.changeSyncStatus = "SUCCESS";
                        poProcessActionService.insert(this.poHeaderId, "SYNC_SUCCESS");
                    } else {
                        throw new CommonException(poExpFeedbackDTO.getFeedBackMessage() + responseDTO.getResponseMessage(), new Object[0]);
                    }
                });
                poHeaderRepository.updateOptional(this, new String[]{"changeSyncStatus"});
            }
        } else {
            throw new CommonException("error.order.change.sync.exception", new Object[0]);
        }
    }

    public static PoDTO processPurchaseAgent(Long tenantId, PoDTO poDTO, PoHeaderRepository poHeaderRepository) {
        if (poDTO.getAgentId() == null) {
            Long userId = DetailsHelper.getUserDetails().getUserId();
            List<PurchaseAgentVO> purchaseAgentVOS = poHeaderRepository.queryCurrentUserOnPurchaseAgent(tenantId, userId);
            if (purchaseAgentVOS != null && purchaseAgentVOS.size() == 1) {
                poDTO.setAgentId(((PurchaseAgentVO)purchaseAgentVOS.get(0)).getPurchaseAgentId());
                poDTO.setAgentName(((PurchaseAgentVO)purchaseAgentVOS.get(0)).getPurchaseAgentName());
                poDTO.setAgentCode(((PurchaseAgentVO)purchaseAgentVOS.get(0)).getPurchaseAgentCode());
            }
        }

        return poDTO;
    }

    private Date getTomorrowDate(Date date) {
        return DateUtils.addDays(DateUtils.truncate(date, 5), 1);
    }

    public void validateSubmitted(PoHeaderRepository poHeaderRepository) {
        PoHeader poHeader = new PoHeader();
        poHeader.setPoHeaderId(this.poHeaderId);
        poHeader.setObjectVersionNumber(this.objectVersionNumber);
        poHeader = (PoHeader)poHeaderRepository.selectOne(poHeader);
        Assert.notNull(poHeader, "error.order.not.exsit");
    }

    public void clearClosedStatus() {
        this.closedFlag = Flag.NO;
    }

    public void addclosedStatus() {
        this.closedFlag = Flag.YES;
        this.statusCode = "CLOSED";
    }

    public void handleConfirmedFlag() {
        if (!Flag.YES.equals(this.confirmedFlag) && !Flag.YES.equals(this.confirmUpdateFlag)) {
            this.setConfirmUpdateFlag(Flag.NO);
        } else {
            this.setConfirmUpdateFlag(Flag.YES);
        }

    }

    public void supplierInfoSetting(PoHeaderMapper poHeaderMapper) {
        PoHeader poHeader;
        if (Objects.nonNull(this.supplierId) && Objects.isNull(this.supplierCompanyId)) {
            poHeader = poHeaderMapper.querySupplierCompanyInfo(this.tenantId, this.supplierId);
            if (Objects.nonNull(poHeader)) {
                this.setSupplierCompanyId(poHeader.getSupplierCompanyId());
                this.setSupplierCompanyName(poHeader.getSupplierCompanyName());
                this.setSupplierTenantId(poHeader.getSupplierTenantId());
            }
        }

        if (Objects.isNull(this.supplierId) && Objects.nonNull(this.supplierCompanyId)) {
            List<PoHeader> poHeaderList = poHeaderMapper.querySupplierInfo(this.tenantId, this.supplierCompanyId);
            if (CollectionUtils.isNotEmpty(poHeaderList) && poHeaderList.size() == 1) {
                this.setSupplierId(((PoHeader)poHeaderList.get(0)).getSupplierId());
                this.setSupplierCode(((PoHeader)poHeaderList.get(0)).getSupplierCode());
                this.setSupplierName(((PoHeader)poHeaderList.get(0)).getSupplierName());
            }
        }

        if (Objects.nonNull(this.supplierId) && Objects.isNull(this.supplierCode)) {
            poHeader = poHeaderMapper.querySupplierOne(this.supplierId);
            if (Objects.nonNull(poHeader)) {
                this.setSupplierCode(poHeader.getSupplierCode());
                this.setSupplierName(poHeader.getSupplierName());
            }
        }

        if (Objects.nonNull(this.supplierCompanyId) && Objects.isNull(this.supplierCompanyName)) {
            this.setSupplierCompanyName(poHeaderMapper.selectCompanyNameById(this.supplierCompanyId));
        }

        if (Objects.nonNull(this.supplierCompanyId) && Objects.isNull(this.supplierTenantId)) {
            this.setSupplierTenantId(poHeaderMapper.selectSupplierTenantIdByCompanyId(this.supplierCompanyId));
        }

    }

    public Long getSettleErpSupplierId() {
        return this.settleErpSupplierId;
    }

    public void setSettleErpSupplierId(Long settleErpSupplierId) {
        this.settleErpSupplierId = settleErpSupplierId;
    }

    public String getSettleErpSupplierCode() {
        return this.settleErpSupplierCode;
    }

    public void setSettleErpSupplierCode(String settleErpSupplierCode) {
        this.settleErpSupplierCode = settleErpSupplierCode;
    }

    public String getSettleErpSupplierName() {
        return this.settleErpSupplierName;
    }

    public void setSettleErpSupplierName(String settleErpSupplierName) {
        this.settleErpSupplierName = settleErpSupplierName;
    }

    public Long getSettleSupplierTenantId() {
        return this.settleSupplierTenantId;
    }

    public void setSettleSupplierTenantId(Long settleSupplierTenantId) {
        this.settleSupplierTenantId = settleSupplierTenantId;
    }

    public String getOldStatusCode() {
        return this.oldStatusCode;
    }

    public void setOldStatusCode(String oldStatusCode) {
        this.oldStatusCode = oldStatusCode;
    }

    public String getCacheKey() {
        return this.cacheKey;
    }

    public void setCacheKey(String cacheKey) {
        this.cacheKey = cacheKey;
    }

    public String getPurchaseUnitName() {
        return this.purchaseUnitName;
    }

    public void setPurchaseUnitName(String purchaseUnitName) {
        this.purchaseUnitName = purchaseUnitName;
    }

    public String getTermCode() {
        return this.termCode;
    }

    public void setTermCode(String termCode) {
        this.termCode = termCode;
    }

    public Integer getTermEnabledFlag() {
        return this.termEnabledFlag;
    }

    public void setTermEnabledFlag(Integer termEnabledFlag) {
        this.termEnabledFlag = termEnabledFlag;
    }

    public Integer getCurrencyEnabledFlag() {
        return this.currencyEnabledFlag;
    }

    public void setCurrencyEnabledFlag(Integer currencyEnabledFlag) {
        this.currencyEnabledFlag = currencyEnabledFlag;
    }

    public Long getCurrencyId() {
        return this.currencyId;
    }

    public void setCurrencyId(Long currencyId) {
        this.currencyId = currencyId;
    }

    public String getCurrencyName() {
        return this.currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public Integer getPurchaseOrgEnabledFlag() {
        return this.purchaseOrgEnabledFlag;
    }

    public Integer getPurchaseAgentEnabledFlag() {
        return this.purchaseAgentEnabledFlag;
    }

    public void setPurchaseAgentEnabledFlag(Integer purchaseAgentEnabledFlag) {
        this.purchaseAgentEnabledFlag = purchaseAgentEnabledFlag;
    }

    public void setPurchaseOrgEnabledFlag(Integer purchaseOrgEnabledFlag) {
        this.purchaseOrgEnabledFlag = purchaseOrgEnabledFlag;
    }

    public Integer getUnreadCount() {
        return this.unreadCount;
    }

    public void setUnreadCount(Integer unreadCount) {
        this.unreadCount = unreadCount;
    }

    public Integer getModifyPriceFlag() {
        return this.modifyPriceFlag;
    }

    public void setModifyPriceFlag(Integer modifyPriceFlag) {
        this.modifyPriceFlag = modifyPriceFlag;
    }

    public Integer getChangingFlag() {
        return this.changingFlag;
    }

    public void setChangingFlag(Integer changingFlag) {
        this.changingFlag = changingFlag;
    }

    public String getChangeSyncStatus() {
        return this.changeSyncStatus;
    }

    public String getCreateSyncFlay() {
        return this.createSyncFlay;
    }

    public void setCreateSyncFlay(String createSyncFlay) {
        this.createSyncFlay = createSyncFlay;
    }

    public Map<String, Object> getCacheMap() {
        return this.cacheMap;
    }

    public void setCacheMap(Map<String, Object> cacheMap) {
        this.cacheMap = cacheMap;
    }

    public Integer getReturnOrderFlag() {
        return this.returnOrderFlag;
    }

    public void setReturnOrderFlag(Integer returnOrderFlag) {
        this.returnOrderFlag = returnOrderFlag;
    }

    public List<PoLineExportVo> getPoLineExportVoList() {
        return this.poLineExportVoList;
    }

    public void setPoLineExportVoList(List<PoLineExportVo> poLineExportVoList) {
        this.poLineExportVoList = poLineExportVoList;
    }

    public Long getOriginalPoHeaderId() {
        return this.originalPoHeaderId;
    }

    public BigDecimal getTaxPrice() {
        return this.taxPrice;
    }

    public void setTaxPrice(BigDecimal taxPrice) {
        this.taxPrice = taxPrice;
    }

    public String getPurOrganizationCode() {
        return this.purOrganizationCode;
    }

    public void setPurOrganizationCode(String purOrganizationCode) {
        this.purOrganizationCode = purOrganizationCode;
    }

    public void setOriginalPoHeaderId(Long originalPoHeaderId) {
        this.originalPoHeaderId = originalPoHeaderId;
    }

    public String getOriginalPoNum() {
        return this.originalPoNum;
    }

    public void setOriginalPoNum(String originalPoNum) {
        this.originalPoNum = originalPoNum;
    }

    public String getMallParentOrderNum() {
        return this.mallParentOrderNum;
    }

    public void setMallParentOrderNum(String mallParentOrderNum) {
        this.mallParentOrderNum = mallParentOrderNum;
    }

    public Long getSettleSupplierId() {
        return this.settleSupplierId;
    }

    public void setSettleSupplierId(Long settleSupplierId) {
        this.settleSupplierId = settleSupplierId;
    }

    public String getSettleSupplierCode() {
        return this.settleSupplierCode;
    }

    public void setSettleSupplierCode(String settleSupplierCode) {
        this.settleSupplierCode = settleSupplierCode;
    }

    public String getSettleSupplierName() {
        return this.settleSupplierName;
    }

    public void setSettleSupplierName(String settleSupplierName) {
        this.settleSupplierName = settleSupplierName;
    }

    public Integer getAutoSubmitFlag() {
        return this.autoSubmitFlag;
    }

    public void setAutoSubmitFlag(Integer autoSubmitFlag) {
        this.autoSubmitFlag = autoSubmitFlag;
    }

    public List<PoLine> getPoLineList() {
        return this.poLineList;
    }

    public void setPoLineList(List<PoLine> poLineList) {
        this.poLineList = poLineList;
    }

    public Integer getPoUpgradeReConfirmFlag() {
        return this.poUpgradeReConfirmFlag;
    }

    public void setPoUpgradeReConfirmFlag(Integer poUpgradeReConfirmFlag) {
        this.poUpgradeReConfirmFlag = poUpgradeReConfirmFlag;
    }

    public String getApproveMethod() {
        return this.approveMethod;
    }

    public void setApproveMethod(String approveMethod) {
        this.approveMethod = approveMethod;
    }

    public String getDisplayPrLineNum() {
        return this.displayPrLineNum;
    }

    public void setDisplayPrLineNum(String displayPrLineNum) {
        this.displayPrLineNum = displayPrLineNum;
    }

    public void setChangeSyncStatus(String changeSyncStatus) {
        this.changeSyncStatus = changeSyncStatus;
    }

    public String getDeliverySyncStatus() {
        return this.deliverySyncStatus;
    }

    public void setDeliverySyncStatus(String deliverySyncStatus) {
        this.deliverySyncStatus = deliverySyncStatus;
    }

    public String getDeliverySyncResponseMsg() {
        return this.deliverySyncResponseMsg;
    }

    public void setDeliverySyncResponseMsg(String deliverySyncResponseMsg) {
        this.deliverySyncResponseMsg = deliverySyncResponseMsg;
    }

    public Date getDeliverySyncDate() {
        return this.deliverySyncDate;
    }

    public void setDeliverySyncDate(Date deliverySyncDate) {
        this.deliverySyncDate = deliverySyncDate;
    }

    public Date getPromiseDeliveryDate() {
        return this.PromiseDeliveryDate;
    }

    public void setPromiseDeliveryDate(Date promiseDeliveryDate) {
        this.PromiseDeliveryDate = promiseDeliveryDate;
    }

    public BigDecimal getAdvancePaymentAmount() {
        return this.advancePaymentAmount;
    }

    public void setAdvancePaymentAmount(BigDecimal advancePaymentAmount) {
        this.advancePaymentAmount = advancePaymentAmount;
    }

    public String getOperateType() {
        return this.operateType;
    }

    public void setOperateType(String operateType) {
        this.operateType = operateType;
    }

    public Integer getDeliverySyncFlag() {
        return this.deliverySyncFlag;
    }

    public void setDeliverySyncFlag(Integer deliverySyncFlag) {
        this.deliverySyncFlag = deliverySyncFlag;
    }

    public Integer getBeyondFlag() {
        return this.beyondFlag;
    }

    public void setBeyondFlag(Integer beyondFlag) {
        this.beyondFlag = beyondFlag;
    }

    public Date getNowDate() {
        return this.nowDate;
    }

    public void setNowDate(Date nowDate) {
        this.nowDate = nowDate;
    }

    public Integer getBeyondQuantity() {
        return this.beyondQuantity;
    }

    public void setBeyondQuantity(Integer beyondQuantity) {
        this.beyondQuantity = beyondQuantity;
    }

    public String getApprovedSyncStatusMeaning() {
        return this.approvedSyncStatusMeaning;
    }

    public void setApprovedSyncStatusMeaning(String approvedSyncStatusMeaning) {
        this.approvedSyncStatusMeaning = approvedSyncStatusMeaning;
    }

    public String getApprovedSyncStatus() {
        return this.approvedSyncStatus;
    }

    public void setApprovedSyncStatus(String approvedSyncStatus) {
        this.approvedSyncStatus = approvedSyncStatus;
    }

    public String getApprovedSyncResponseMsg() {
        return this.approvedSyncResponseMsg;
    }

    public void setApprovedSyncResponseMsg(String approvedSyncResponseMsg) {
        this.approvedSyncResponseMsg = approvedSyncResponseMsg;
    }

    public Date getApprovedSyncDate() {
        return this.approvedSyncDate;
    }

    public void setApprovedSyncDate(Date approvedSyncDate) {
        this.approvedSyncDate = approvedSyncDate;
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

    public String getRealName() {
        return this.realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Set<Long> getPoHeaderIds() {
        return this.poHeaderIds;
    }

    public void setPoHeaderIds(Set<Long> poHeaderIds) {
        this.poHeaderIds = poHeaderIds;
    }

    public String getLoginName() {
        return this.loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public Date getFeedbackDateStart() {
        return this.feedbackDateStart;
    }

    public void setFeedbackDateStart(Date feedbackDateStart) {
        this.feedbackDateStart = feedbackDateStart;
    }

    public Date getFeedbackDateEnd() {
        return this.feedbackDateEnd;
    }

    public void setFeedbackDateEnd(Date feedbackDateEnd) {
        this.feedbackDateEnd = feedbackDateEnd;
    }

    public void prSyncToPo(PrHeader prHeader) {
        this.sourceBillTypeCode = "PURCHASE_REQUEST";
        this.poSourcePlatform = prHeader.getPrSourcePlatform();
        this.displayPrNum = prHeader.getDisplayPrNum();
        this.shipToLocContName = prHeader.getReceiverContactName();
        this.shipToLocTelNum = prHeader.getReceiverTelNum();
        this.billToLocContName = prHeader.getInvoiceContactName();
        this.billToLocTelNum = prHeader.getInvoiceTelNum();
        this.invoiceTitle = prHeader.getInvoiceTitle();
        this.taxRegisterNum = prHeader.getTaxRegisterNum();
        this.taxRegisterAddress = prHeader.getTaxRegisterAddress();
        this.taxRegisterTel = prHeader.getTaxRegisterTel();
        this.taxRegisterBank = prHeader.getTaxRegisterBank();
        this.taxRegisterBankAccount = prHeader.getTaxRegisterBankAccount();
        this.invoiceMethodCode = prHeader.getInvoiceMethodCode();
        this.invoiceTypeCode = prHeader.getInvoiceTypeCode();
        this.invoiceTitleTypeCode = prHeader.getInvoiceTitleTypeCode();
        this.invoiceDetailTypeCode = prHeader.getInvoiceDetailTypeCode();
        this.invoiceMethodName = prHeader.getInvoiceMethodName();
        this.invoiceDetailTypeName = prHeader.getInvoiceDetailTypeName();
        this.invoiceTitleTypeName = prHeader.getInvoiceTitleTypeName();
        this.invoiceTypeName = prHeader.getInvoiceTypeName();
        this.shipToLocationAddress = prHeader.getReceiverAddress();
        this.billToLocationAddress = prHeader.getInvoiceAddress();
        this.receiverEmailAddress = prHeader.getReceiverEmailAddress();
    }

    public String getInvoiceMethodName() {
        return this.invoiceMethodName;
    }

    public void setInvoiceMethodName(String invoiceMethodName) {
        this.invoiceMethodName = invoiceMethodName;
    }

    public String getInvoiceTypeName() {
        return this.invoiceTypeName;
    }

    public void setInvoiceTypeName(String invoiceTypeName) {
        this.invoiceTypeName = invoiceTypeName;
    }

    public String getInvoiceTitleTypeName() {
        return this.invoiceTitleTypeName;
    }

    public void setInvoiceTitleTypeName(String invoiceTitleTypeName) {
        this.invoiceTitleTypeName = invoiceTitleTypeName;
    }

    public String getInvoiceDetailTypeName() {
        return this.invoiceDetailTypeName;
    }

    public void setInvoiceDetailTypeName(String invoiceDetailTypeName) {
        this.invoiceDetailTypeName = invoiceDetailTypeName;
    }

    public String getSupplierCompanyCode() {
        return this.supplierCompanyCode;
    }

    public void setSupplierCompanyCode(String supplierCompanyCode) {
        this.supplierCompanyCode = supplierCompanyCode;
    }

    public Long getPoCancelSelect() {
        return this.poCancelSelect;
    }

    public void setPoCancelSelect(Long poCancelSelect) {
        this.poCancelSelect = poCancelSelect;
    }

    public Date getLastUpdateDateStart() {
        return this.lastUpdateDateStart;
    }

    public void setLastUpdateDateStart(Date lastUpdateDateStart) {
        this.lastUpdateDateStart = lastUpdateDateStart;
    }

    public Date getLastUpdateDateEnd() {
        return this.lastUpdateDateEnd;
    }

    public void setLastUpdateDateEnd(Date lastUpdateDateEnd) {
        this.lastUpdateDateEnd = lastUpdateDateEnd;
    }

    public Long getShipToLocationId() {
        return this.shipToLocationId;
    }

    public void setShipToLocationId(Long shipToLocationId) {
        this.shipToLocationId = shipToLocationId;
    }

    public Long getBillToLocationId() {
        return this.billToLocationId;
    }

    public void setBillToLocationId(Long billToLocationId) {
        this.billToLocationId = billToLocationId;
    }

    public Integer getPublishFlag() {
        return this.publishFlag;
    }

    public void setPublishFlag(Integer publishFlag) {
        this.publishFlag = publishFlag;
    }

    public Integer getCancelledFlag() {
        return this.cancelledFlag;
    }

    public void setCancelledFlag(Integer cancelledFlag) {
        this.cancelledFlag = cancelledFlag;
    }

    public Long getSubmittedBy() {
        return this.submittedBy;
    }

    public void setSubmittedBy(Long submittedBy) {
        this.submittedBy = submittedBy;
    }

    public Integer getPublishCancelFlag() {
        return this.publishCancelFlag;
    }

    public void setPublishCancelFlag(Integer publishCancelFlag) {
        this.publishCancelFlag = publishCancelFlag;
    }

    public Integer getReleaseFlag() {
        return this.releaseFlag;
    }

    public void setReleaseFlag(Integer releaseFlag) {
        this.releaseFlag = releaseFlag;
    }

    public Integer getSnapshotFlag() {
        return this.snapshotFlag;
    }

    public void setSnapshotFlag(Integer snapshotFlag) {
        this.snapshotFlag = snapshotFlag;
    }

    public Long getRefPoHeaderId() {
        return this.refPoHeaderId;
    }

    public void setRefPoHeaderId(Long refPoHeaderId) {
        this.refPoHeaderId = refPoHeaderId;
    }

    public String getDomesticCurrencyCode() {
        return this.domesticCurrencyCode;
    }

    public void setDomesticCurrencyCode(String domesticCurrencyCode) {
        this.domesticCurrencyCode = domesticCurrencyCode;
    }

    public BigDecimal getDomesticAmount() {
        return this.domesticAmount;
    }

    public void setDomesticAmount(BigDecimal domesticAmount) {
        this.domesticAmount = domesticAmount;
    }

    public BigDecimal getDomesticTaxIncludeAmount() {
        return this.domesticTaxIncludeAmount;
    }

    public void setDomesticTaxIncludeAmount(BigDecimal domesticTaxIncludeAmount) {
        this.domesticTaxIncludeAmount = domesticTaxIncludeAmount;
    }

    public Long getPoHeaderId() {
        return this.poHeaderId;
    }

    public void setPoHeaderId(Long poHeaderId) {
        this.poHeaderId = poHeaderId;
    }

    public Long getTenantId() {
        return this.tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public String getPoNum() {
        return this.poNum;
    }

    public void setPoNum(String poNum) {
        this.poNum = poNum;
    }

    public Integer getVersionNum() {
        return this.versionNum;
    }

    public void setVersionNum(Integer versionNum) {
        this.versionNum = versionNum;
    }

    public Date getVersionDate() {
        return this.versionDate;
    }

    public void setVersionDate(Date versionDate) {
        this.versionDate = versionDate;
    }

    public Long getPoTypeId() {
        return this.poTypeId;
    }

    public void setPoTypeId(Long poTypeId) {
        this.poTypeId = poTypeId;
    }

    public String getReleaseNum() {
        return this.releaseNum;
    }

    public void setReleaseNum(String releaseNum) {
        this.releaseNum = releaseNum;
    }

    public String getDisplayPoNum() {
        return this.displayPoNum;
    }

    public void setDisplayPoNum(String displayPoNum) {
        this.displayPoNum = displayPoNum;
    }

    public String getDisplayReleaseNum() {
        return this.displayReleaseNum;
    }

    public void setDisplayReleaseNum(String displayReleaseNum) {
        this.displayReleaseNum = displayReleaseNum;
    }

    public String getErpContractNum() {
        return this.erpContractNum;
    }

    public void setErpContractNum(String erpContractNum) {
        this.erpContractNum = erpContractNum;
    }

    public String getErpSupplierPhone() {
        return this.erpSupplierPhone;
    }

    public void setErpSupplierPhone(String erpSupplierPhone) {
        this.erpSupplierPhone = erpSupplierPhone;
    }

    public Long getAgentId() {
        return this.agentId;
    }

    public void setAgentId(Long agentId) {
        this.agentId = agentId;
    }

    public Long getSupplierTenantId() {
        return this.supplierTenantId;
    }

    public void setSupplierTenantId(Long supplierTenantId) {
        this.supplierTenantId = supplierTenantId;
    }

    public Long getSupplierOuId() {
        return this.supplierOuId;
    }

    public void setSupplierOuId(Long supplierOuId) {
        this.supplierOuId = supplierOuId;
    }

    public Long getSupplierId() {
        return this.supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierCode() {
        return this.supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    public String getSupplierName() {
        return this.supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
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

    public Long getSupplierSiteId() {
        return this.supplierSiteId;
    }

    public void setSupplierSiteId(Long supplierSiteId) {
        this.supplierSiteId = supplierSiteId;
    }

    public Long getSupplierContactId() {
        return this.supplierContactId;
    }

    public void setSupplierContactId(Long supplierContactId) {
        this.supplierContactId = supplierContactId;
    }

    public String getShipToLocationCode() {
        return this.shipToLocationCode;
    }

    public void setShipToLocationCode(String shipToLocationCode) {
        this.shipToLocationCode = shipToLocationCode;
    }

    public String getShipToLocationAddress() {
        return this.shipToLocationAddress;
    }

    public void setShipToLocationAddress(String shipToLocationAddress) {
        this.shipToLocationAddress = shipToLocationAddress;
    }

    public String getBillToLocationCode() {
        return this.billToLocationCode;
    }

    public void setBillToLocationCode(String billToLocationCode) {
        this.billToLocationCode = billToLocationCode;
    }

    public String getBillToLocationAddress() {
        return this.billToLocationAddress;
    }

    public void setBillToLocationAddress(String billToLocationAddress) {
        this.billToLocationAddress = billToLocationAddress;
    }

    public Long getTermsId() {
        return this.termsId;
    }

    public void setTermsId(Long termsId) {
        this.termsId = termsId;
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

    public Long getOuId() {
        return this.ouId;
    }

    public void setOuId(Long ouId) {
        this.ouId = ouId;
    }

    public Long getPurchaseOrgId() {
        return this.purchaseOrgId;
    }

    public void setPurchaseOrgId(Long purchaseOrgId) {
        this.purchaseOrgId = purchaseOrgId;
    }

    public Date getSubmittedDate() {
        return this.submittedDate;
    }

    public void setSubmittedDate(Date submittedDate) {
        this.submittedDate = submittedDate;
    }

    public Integer getReleasedFlag() {
        return this.releasedFlag;
    }

    public void setReleasedFlag(Integer releasedFlag) {
        this.releasedFlag = releasedFlag;
    }

    public String getReleaseTypeCode() {
        return this.releaseTypeCode;
    }

    public void setReleaseTypeCode(String releaseTypeCode) {
        this.releaseTypeCode = releaseTypeCode;
    }

    public Long getReleasedBy() {
        return this.releasedBy;
    }

    public void setReleasedBy(Long releasedBy) {
        this.releasedBy = releasedBy;
    }

    public Date getReleasedDate() {
        return this.releasedDate;
    }

    public void setReleasedDate(Date releasedDate) {
        this.releasedDate = releasedDate;
    }

    public Integer getConfirmedFlag() {
        return this.confirmedFlag;
    }

    public void setConfirmedFlag(Integer confirmedFlag) {
        this.confirmedFlag = confirmedFlag;
    }

    public String getConfirmTypeCode() {
        return this.confirmTypeCode;
    }

    public void setConfirmTypeCode(String confirmTypeCode) {
        this.confirmTypeCode = confirmTypeCode;
    }

    public Long getConfirmedBy() {
        return this.confirmedBy;
    }

    public void setConfirmedBy(Long confirmedBy) {
        this.confirmedBy = confirmedBy;
    }

    public Date getConfirmedDate() {
        return this.confirmedDate;
    }

    public void setConfirmedDate(Date confirmedDate) {
        this.confirmedDate = confirmedDate;
    }

    public Integer getUrgentFlag() {
        return this.urgentFlag;
    }

    public void setUrgentFlag(Integer urgentFlag) {
        this.urgentFlag = urgentFlag;
    }

    public Date getUrgentDate() {
        return this.urgentDate;
    }

    public void setUrgentDate(Date urgentDate) {
        this.urgentDate = urgentDate;
    }

    public Date getFeedbackDate() {
        return this.feedbackDate;
    }

    public void setFeedbackDate(Date feedbackDate) {
        this.feedbackDate = feedbackDate;
    }

    public Integer getClosedFlag() {
        return this.closedFlag;
    }

    public void setClosedFlag(Integer closedFlag) {
        this.closedFlag = closedFlag;
    }

    public Integer getDirectCustomFlag() {
        return this.directCustomFlag;
    }

    public void setDirectCustomFlag(Integer directCustomFlag) {
        this.directCustomFlag = directCustomFlag;
    }

    public Integer getConfirmUpdateFlag() {
        return this.confirmUpdateFlag;
    }

    public void setConfirmUpdateFlag(Integer confirmUpdateFlag) {
        this.confirmUpdateFlag = confirmUpdateFlag;
    }

    public Integer getInitFlag() {
        return this.initFlag;
    }

    public void setInitFlag(Integer initFlag) {
        this.initFlag = initFlag;
    }

    public String getReceivedStatus() {
        return this.receivedStatus;
    }

    public void setReceivedStatus(String receivedStatus) {
        this.receivedStatus = receivedStatus;
    }

    public Long getApprovedBy() {
        return this.approvedBy;
    }

    public void setApprovedBy(Long approvedBy) {
        this.approvedBy = approvedBy;
    }

    public Integer getApprovedFlag() {
        return this.approvedFlag;
    }

    public void setApprovedFlag(Integer approvedFlag) {
        this.approvedFlag = approvedFlag;
    }

    public Date getApprovedDate() {
        return this.approvedDate;
    }

    public void setApprovedDate(Date approvedDate) {
        this.approvedDate = approvedDate;
    }

    public String getApprovedRemark() {
        return this.approvedRemark;
    }

    public void setApprovedRemark(String approvedRemark) {
        this.approvedRemark = approvedRemark;
    }

    public Integer getFrozenFlag() {
        return this.frozenFlag;
    }

    public void setFrozenFlag(Integer frozenFlag) {
        this.frozenFlag = frozenFlag;
    }

    public Integer getErpApprovalFlag() {
        return this.erpApprovalFlag;
    }

    public void setErpApprovalFlag(Integer erpApprovalFlag) {
        this.erpApprovalFlag = erpApprovalFlag;
    }

    public String getErpStatus() {
        return this.erpStatus;
    }

    public void setErpStatus(String erpStatus) {
        this.erpStatus = erpStatus;
    }

    public String getStatusCode() {
        return this.statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getSourceCode() {
        return this.sourceCode;
    }

    public void setSourceCode(String sourceCode) {
        this.sourceCode = sourceCode;
    }

    public String getExternalSystemCode() {
        return this.externalSystemCode;
    }

    public void setExternalSystemCode(String externalSystemCode) {
        this.externalSystemCode = externalSystemCode;
    }

    public String getCurrencyCode() {
        return this.currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public BigDecimal getAmount() {
        return this.amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getTaxIncludeAmount() {
        return this.taxIncludeAmount;
    }

    public void setTaxIncludeAmount(BigDecimal taxIncludeAmount) {
        this.taxIncludeAmount = taxIncludeAmount;
    }

    public String getPurchaseRemark() {
        return this.purchaseRemark;
    }

    public void setPurchaseRemark(String purchaseRemark) {
        this.purchaseRemark = purchaseRemark;
    }

    public String getSupplierRemark() {
        return this.supplierRemark;
    }

    public void setSupplierRemark(String supplierRemark) {
        this.supplierRemark = supplierRemark;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getAttachmentUuid() {
        return this.attachmentUuid;
    }

    public void setAttachmentUuid(String attachmentUuid) {
        this.attachmentUuid = attachmentUuid;
    }

    public String getSupplierAttachmentUuid() {
        return this.supplierAttachmentUuid;
    }

    public void setSupplierAttachmentUuid(String supplierAttachmentUuid) {
        this.supplierAttachmentUuid = supplierAttachmentUuid;
    }

    public String getPurchaserInnerAttachmentUuid() {
        return this.purchaserInnerAttachmentUuid;
    }

    public void setPurchaserInnerAttachmentUuid(String purchaserInnerAttachmentUuid) {
        this.purchaserInnerAttachmentUuid = purchaserInnerAttachmentUuid;
    }

    public String getErpCreatedName() {
        return this.erpCreatedName;
    }

    public void setErpCreatedName(String erpCreatedName) {
        this.erpCreatedName = erpCreatedName;
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

    public Date getReleaseDateEnd() {
        return this.releaseDateEnd;
    }

    public void setReleaseDateEnd(Date releaseDateEnd) {
        this.releaseDateEnd = releaseDateEnd;
    }

    public Date getReleaseDateStart() {
        return this.releaseDateStart;
    }

    public void setReleaseDateStart(Date releaseDateStart) {
        this.releaseDateStart = releaseDateStart;
    }

    public Date getConfirmDateStart() {
        return this.confirmDateStart;
    }

    public void setConfirmDateStart(Date confirmDateStart) {
        this.confirmDateStart = confirmDateStart;
    }

    public Date getConfirmDateEnd() {
        return this.confirmDateEnd;
    }

    public void setConfirmDateEnd(Date confirmDateEnd) {
        this.confirmDateEnd = confirmDateEnd;
    }

    public Date getCreationDateStart() {
        return this.creationDateStart;
    }

    public void setCreationDateStart(Date creationDateStart) {
        this.creationDateStart = creationDateStart;
    }

    public Date getCreationDateEnd() {
        return this.creationDateEnd;
    }

    public void setCreationDateEnd(Date creationDateEnd) {
        this.creationDateEnd = creationDateEnd;
    }

    public String getTermName() {
        return this.termName;
    }

    public void setTermName(String termName) {
        this.termName = termName;
    }

    public Set<String> getStatusCodes() {
        return this.statusCodes;
    }

    public void setStatusCodes(Set<String> statusCodes) {
        this.statusCodes = statusCodes;
    }

    public String getErpStatusMeaning() {
        return this.erpStatusMeaning;
    }

    public void setErpStatusMeaning(String erpStatusMeaning) {
        this.erpStatusMeaning = erpStatusMeaning;
    }

    public String getStatusCodeMeaning() {
        return this.statusCodeMeaning;
    }

    public void setStatusCodeMeaning(String statusCodeMeaning) {
        this.statusCodeMeaning = statusCodeMeaning;
    }

    public String getOrgName() {
        return this.orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getAgentName() {
        return this.agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getPurOrganizationName() {
        return this.purOrganizationName;
    }

    public void setPurOrganizationName(String purOrganizationName) {
        this.purOrganizationName = purOrganizationName;
    }

    public String getSupplierSiteCode() {
        return this.supplierSiteCode;
    }

    public void setSupplierSiteCode(String supplierSiteCode) {
        this.supplierSiteCode = supplierSiteCode;
    }

    public String getSupplierSiteName() {
        return this.supplierSiteName;
    }

    public void setSupplierSiteName(String supplierSiteName) {
        this.supplierSiteName = supplierSiteName;
    }

    public Long getObjectVersionNumber() {
        return this.objectVersionNumber;
    }

    public void setObjectVersionNumber(Long objectVersionNumber) {
        this.objectVersionNumber = objectVersionNumber;
    }

    public Date getCreationDate() {
        return this.creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getSubmitDateEnd() {
        return this.submitDateEnd;
    }

    public void setSubmitDateEnd(Date submitDateEnd) {
        this.submitDateEnd = submitDateEnd;
    }

    public Date getSubmitDateStart() {
        return this.submitDateStart;
    }

    public void setSubmitDateStart(Date submitDateStart) {
        this.submitDateStart = submitDateStart;
    }

    public String getPoTypeCode() {
        return this.poTypeCode;
    }

    public void setPoTypeCode(String poTypeCode) {
        this.poTypeCode = poTypeCode;
    }

    public String getPoTypeCodeMeaning() {
        return this.poTypeCodeMeaning;
    }

    public void setPoTypeCodeMeaning(String poTypeCodeMeaning) {
        this.poTypeCodeMeaning = poTypeCodeMeaning;
    }

    public String getDisplayStatusCode() {
        return this.displayStatusCode;
    }

    public void setDisplayStatusCode(String displayStatusCode) {
        this.displayStatusCode = displayStatusCode;
    }

    public String getDisplayStatusCodeMeaning() {
        return this.displayStatusCodeMeaning;
    }

    public void setDisplayStatusCodeMeaning(String displayStatusCodeMeaning) {
        this.displayStatusCodeMeaning = displayStatusCodeMeaning;
    }

    public Date getErpCreationDateStart() {
        return this.erpCreationDateStart;
    }

    public void setErpCreationDateStart(Date erpCreationDateStart) {
        this.erpCreationDateStart = erpCreationDateStart;
    }

    public Date getErpCreationDateEnd() {
        return this.erpCreationDateEnd;
    }

    public void setErpCreationDateEnd(Date erpCreationDateEnd) {
        this.erpCreationDateEnd = erpCreationDateEnd;
    }

    public String getSourceCodeMeaning() {
        return this.sourceCodeMeaning;
    }

    public void setSourceCodeMeaning(String sourceCodeMeaning) {
        this.sourceCodeMeaning = sourceCodeMeaning;
    }

    public String getOuName() {
        return this.ouName;
    }

    public void setOuName(String ouName) {
        this.ouName = ouName;
    }

    public String getPoSourcePlatform() {
        return this.poSourcePlatform;
    }

    public void setPoSourcePlatform(String poSourcePlatform) {
        this.poSourcePlatform = poSourcePlatform;
    }

    public String getPoSourcePlatformMeaning() {
        return this.poSourcePlatformMeaning;
    }

    public void setPoSourcePlatformMeaning(String poSourcePlatformMeaning) {
        this.poSourcePlatformMeaning = poSourcePlatformMeaning;
    }

    public Long getPrHeaderId() {
        return this.prHeaderId;
    }

    public void setPrHeaderId(Long prHeaderId) {
        this.prHeaderId = prHeaderId;
    }

    public String getDisplayPrNum() {
        return this.displayPrNum;
    }

    public void setDisplayPrNum(String displayPrNum) {
        this.displayPrNum = displayPrNum;
    }

    public String getReceiverEmailAddress() {
        return this.receiverEmailAddress;
    }

    public void setReceiverEmailAddress(String receiverEmailAddress) {
        this.receiverEmailAddress = receiverEmailAddress;
    }

    public Long getRefusedBy() {
        return this.refusedBy;
    }

    public void setRefusedBy(Long refusedBy) {
        this.refusedBy = refusedBy;
    }

    public Integer getRefusedFlag() {
        return this.refusedFlag;
    }

    public void setRefusedFlag(Integer refusedFlag) {
        this.refusedFlag = refusedFlag;
    }

    public Date getRefusedDate() {
        return this.refusedDate;
    }

    public void setRefusedDate(Date refusedDate) {
        this.refusedDate = refusedDate;
    }

    public String getRefusedRemark() {
        return this.refusedRemark;
    }

    public void setRefusedRemark(String refusedRemark) {
        this.refusedRemark = refusedRemark;
    }

    public String getSourceBillTypeCode() {
        return this.sourceBillTypeCode;
    }

    public void setSourceBillTypeCode(String sourceBillTypeCode) {
        this.sourceBillTypeCode = sourceBillTypeCode;
    }

    public String getInvoiceTitle() {
        return this.invoiceTitle;
    }

    public void setInvoiceTitle(String invoiceTitle) {
        this.invoiceTitle = invoiceTitle;
    }

    public String getTaxRegisterNum() {
        return this.taxRegisterNum;
    }

    public void setTaxRegisterNum(String taxRegisterNum) {
        this.taxRegisterNum = taxRegisterNum;
    }

    public String getTaxRegisterAddress() {
        return this.taxRegisterAddress;
    }

    public void setTaxRegisterAddress(String taxRegisterAddress) {
        this.taxRegisterAddress = taxRegisterAddress;
    }

    public String getTaxRegisterTel() {
        return this.taxRegisterTel;
    }

    public void setTaxRegisterTel(String taxRegisterTel) {
        this.taxRegisterTel = taxRegisterTel;
    }

    public String getTaxRegisterBank() {
        return this.taxRegisterBank;
    }

    public void setTaxRegisterBank(String taxRegisterBank) {
        this.taxRegisterBank = taxRegisterBank;
    }

    public String getTaxRegisterBankAccount() {
        return this.taxRegisterBankAccount;
    }

    public void setTaxRegisterBankAccount(String taxRegisterBankAccount) {
        this.taxRegisterBankAccount = taxRegisterBankAccount;
    }

    public String getInvoiceMethodCode() {
        return this.invoiceMethodCode;
    }

    public void setInvoiceMethodCode(String invoiceMethodCode) {
        this.invoiceMethodCode = invoiceMethodCode;
    }

    public String getInvoiceTypeCode() {
        return this.invoiceTypeCode;
    }

    public void setInvoiceTypeCode(String invoiceTypeCode) {
        this.invoiceTypeCode = invoiceTypeCode;
    }

    public String getInvoiceTitleTypeCode() {
        return this.invoiceTitleTypeCode;
    }

    public void setInvoiceTitleTypeCode(String invoiceTitleTypeCode) {
        this.invoiceTitleTypeCode = invoiceTitleTypeCode;
    }

    public String getInvoiceDetailTypeCode() {
        return this.invoiceDetailTypeCode;
    }

    public void setInvoiceDetailTypeCode(String invoiceDetailTypeCode) {
        this.invoiceDetailTypeCode = invoiceDetailTypeCode;
    }

    public Integer getIncorrectFlag() {
        return this.incorrectFlag;
    }

    public void setIncorrectFlag(Integer incorrectFlag) {
        this.incorrectFlag = incorrectFlag;
    }

    public Date getIncorrectDate() {
        return this.incorrectDate;
    }

    public void setIncorrectDate(Date incorrectDate) {
        this.incorrectDate = incorrectDate;
    }

    public String getIncorrectMsg() {
        return this.incorrectMsg;
    }

    public void setIncorrectMsg(String incorrectMsg) {
        this.incorrectMsg = incorrectMsg;
    }

    public String getSourceBillTypeCodeMeaning() {
        return this.sourceBillTypeCodeMeaning;
    }

    public void setSourceBillTypeCodeMeaning(String sourceBillTypeCodeMeaning) {
        this.sourceBillTypeCodeMeaning = sourceBillTypeCodeMeaning;
    }

    public String getShipToLocContName() {
        return this.shipToLocContName;
    }

    public void setShipToLocContName(String shipToLocContName) {
        this.shipToLocContName = shipToLocContName;
    }

    public String getShipToLocTelNum() {
        return this.shipToLocTelNum;
    }

    public void setShipToLocTelNum(String shipToLocTelNum) {
        this.shipToLocTelNum = shipToLocTelNum;
    }

    public String getBillToLocContName() {
        return this.billToLocContName;
    }

    public void setBillToLocContName(String billToLocContName) {
        this.billToLocContName = billToLocContName;
    }

    public String getBillToLocTelNum() {
        return this.billToLocTelNum;
    }

    public void setBillToLocTelNum(String billToLocTelNum) {
        this.billToLocTelNum = billToLocTelNum;
    }

    public Integer getEvaluationFlag() {
        return this.evaluationFlag;
    }

    public void setEvaluationFlag(Integer evaluationFlag) {
        this.evaluationFlag = evaluationFlag;
    }

    public Long getPoCloseSelect() {
        return this.poCloseSelect;
    }

    public void setPoCloseSelect(Long poCloseSelect) {
        this.poCloseSelect = poCloseSelect;
    }

    public Long getPoProcessSelect() {
        return this.poProcessSelect;
    }

    public void setPoProcessSelect(Long poProcessSelect) {
        this.poProcessSelect = poProcessSelect;
    }

    public String getCreateSyncStatus() {
        return this.createSyncStatus;
    }

    public void setCreateSyncStatus(String createSyncStatus) {
        this.createSyncStatus = createSyncStatus;
    }

    public String getCreateSyncResponseMsg() {
        return this.createSyncResponseMsg;
    }

    public void setCreateSyncResponseMsg(String createSyncResponseMsg) {
        this.createSyncResponseMsg = createSyncResponseMsg;
    }

    public Date getCreateSyncDate() {
        return this.createSyncDate;
    }

    public void setCreateSyncDate(Date createSyncDate) {
        this.createSyncDate = createSyncDate;
    }

    public void decimalData() {
        if (this.getAmount() != null) {
            this.setAmount(this.getAmount().setScale(2, 4));
        }

        if (this.getTaxIncludeAmount() != null) {
            this.setTaxIncludeAmount(this.getTaxIncludeAmount().setScale(2, 4));
        }

    }

    public Integer getUnSaveEnable() {
        return this.unSaveEnable;
    }

    public void setUnSaveEnable(Integer unSaveEnable) {
        this.unSaveEnable = unSaveEnable;
    }

    public Integer getQueryApprovingFlag() {
        return this.queryApprovingFlag;
    }

    public void setQueryApprovingFlag(Integer queryApprovingFlag) {
        this.queryApprovingFlag = queryApprovingFlag;
    }

    public String getAcceptMethod() {
        return this.acceptMethod;
    }

    public void setAcceptMethod(String acceptMethod) {
        this.acceptMethod = acceptMethod;
    }

    public String getAcceptMethodMeaning() {
        return this.acceptMethodMeaning;
    }

    public void setAcceptMethodMeaning(String acceptMethodMeaning) {
        this.acceptMethodMeaning = acceptMethodMeaning;
    }

    public Integer getUnCloseCount() {
        return this.unCloseCount;
    }

    public void setUnCloseCount(Integer unCloseCount) {
        this.unCloseCount = unCloseCount;
    }

    public String getCompanyCode() {
        return this.companyCode;
    }

    public void setCompanyCode(final String companyCode) {
        this.companyCode = companyCode;
    }

    public String getOuCode() {
        return this.ouCode;
    }

    public void setOuCode(final String ouCode) {
        this.ouCode = ouCode;
    }

    public String getPurchaseOrgCode() {
        return this.purchaseOrgCode;
    }

    public void setPurchaseOrgCode(final String purchaseOrgCode) {
        this.purchaseOrgCode = purchaseOrgCode;
    }

    public Long getSupplierCategoryId() {
        return this.supplierCategoryId;
    }

    public void setSupplierCategoryId(final Long supplierCategoryId) {
        this.supplierCategoryId = supplierCategoryId;
    }

    public String getSupplierCategoryCode() {
        return this.supplierCategoryCode;
    }

    public void setSupplierCategoryCode(final String supplierCategoryCode) {
        this.supplierCategoryCode = supplierCategoryCode;
    }

    public String getSupplierCategoryName() {
        return this.supplierCategoryName;
    }

    public void setSupplierCategoryName(final String supplierCategoryName) {
        this.supplierCategoryName = supplierCategoryName;
    }

    public String getReceiveOrderType() {
        return this.receiveOrderType;
    }

    public void setReceiveOrderType(String receiveOrderType) {
        this.receiveOrderType = receiveOrderType;
    }

    public String getReceiveOrderTypeMeaning() {
        return this.receiveOrderTypeMeaning;
    }

    public void setReceiveOrderTypeMeaning(String receiveOrderTypeMeaning) {
        this.receiveOrderTypeMeaning = receiveOrderTypeMeaning;
    }

    public Long getStrategyHeaderId() {
        return this.strategyHeaderId;
    }

    public void setStrategyHeaderId(Long strategyHeaderId) {
        this.strategyHeaderId = strategyHeaderId;
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

    public Integer getFinancialPrecision() {
        return this.financialPrecision;
    }

    public void setFinancialPrecision(Integer financialPrecision) {
        this.financialPrecision = financialPrecision;
    }

    public Integer getDomesticFinancialPrecision() {
        return this.domesticFinancialPrecision;
    }

    public void setDomesticFinancialPrecision(Integer domesticFinancialPrecision) {
        this.domesticFinancialPrecision = domesticFinancialPrecision;
    }

    public List<Long> getSupplierCompanyIds() {
        return this.supplierCompanyIds;
    }

    public void setSupplierCompanyIds(List<Long> supplierCompanyIds) {
        this.supplierCompanyIds = supplierCompanyIds;
    }

    public List<Long> getSupplierIds() {
        return this.supplierIds;
    }

    public void setSupplierIds(List<Long> supplierIds) {
        this.supplierIds = supplierIds;
    }

    public Long getRegisteredCountryId() {
        return this.registeredCountryId;
    }

    public void setRegisteredCountryId(Long registeredCountryId) {
        this.registeredCountryId = registeredCountryId;
    }

    public String getAgentCode() {
        return this.agentCode;
    }

    public void setAgentCode(String agentCode) {
        this.agentCode = agentCode;
    }

    public String toString() {
        return "PoHeader{poHeaderId=" + this.poHeaderId + ", tenantId=" + this.tenantId + ", poNum='" + this.poNum + '\'' + ", versionNum=" + this.versionNum + ", versionDate=" + this.versionDate + ", poTypeId=" + this.poTypeId + ", releaseNum='" + this.releaseNum + '\'' + ", displayPoNum='" + this.displayPoNum + '\'' + ", displayReleaseNum='" + this.displayReleaseNum + '\'' + ", erpContractNum='" + this.erpContractNum + '\'' + ", erpSupplierPhone='" + this.erpSupplierPhone + '\'' + ", agentId=" + this.agentId + ", supplierTenantId=" + this.supplierTenantId + ", supplierOuId=" + this.supplierOuId + ", supplierId=" + this.supplierId + ", supplierCode='" + this.supplierCode + '\'' + ", supplierName='" + this.supplierName + '\'' + ", supplierCompanyId=" + this.supplierCompanyId + ", supplierCompanyName='" + this.supplierCompanyName + '\'' + ", supplierSiteId=" + this.supplierSiteId + ", supplierContactId=" + this.supplierContactId + ", shipToLocationCode='" + this.shipToLocationCode + '\'' + ", shipToLocationAddress='" + this.shipToLocationAddress + '\'' + ", billToLocationCode='" + this.billToLocationCode + '\'' + ", billToLocationAddress='" + this.billToLocationAddress + '\'' + ", termsId=" + this.termsId + ", companyId=" + this.companyId + ", companyName='" + this.companyName + '\'' + ", ouId=" + this.ouId + ", purchaseOrgId=" + this.purchaseOrgId + ", submittedBy=" + this.submittedBy + ", submittedDate=" + this.submittedDate + ", releasedFlag=" + this.releasedFlag + ", releaseTypeCode='" + this.releaseTypeCode + '\'' + ", releasedBy=" + this.releasedBy + ", releasedDate=" + this.releasedDate + ", confirmedFlag=" + this.confirmedFlag + ", confirmTypeCode='" + this.confirmTypeCode + '\'' + ", confirmedBy=" + this.confirmedBy + ", confirmedDate=" + this.confirmedDate + ", urgentFlag=" + this.urgentFlag + ", urgentDate=" + this.urgentDate + ", feedbackDate=" + this.feedbackDate + ", closedFlag=" + this.closedFlag + ", directCustomFlag=" + this.directCustomFlag + ", confirmUpdateFlag=" + this.confirmUpdateFlag + ", initFlag=" + this.initFlag + ", receivedStatus='" + this.receivedStatus + '\'' + ", approvedBy=" + this.approvedBy + ", approvedFlag=" + this.approvedFlag + ", approvedDate=" + this.approvedDate + ", approvedRemark='" + this.approvedRemark + '\'' + ", frozenFlag=" + this.frozenFlag + ", publishCancelFlag=" + this.publishCancelFlag + ", erpApprovalFlag=" + this.erpApprovalFlag + ", erpStatus='" + this.erpStatus + '\'' + ", statusCode='" + this.statusCode + '\'' + ", sourceCode='" + this.sourceCode + '\'' + ", poSourcePlatform='" + this.poSourcePlatform + '\'' + ", externalSystemCode='" + this.externalSystemCode + '\'' + ", currencyCode='" + this.currencyCode + '\'' + ", amount=" + this.amount + ", taxIncludeAmount=" + this.taxIncludeAmount + ", purchaseRemark='" + this.purchaseRemark + '\'' + ", supplierRemark='" + this.supplierRemark + '\'' + ", remark='" + this.remark + '\'' + ", attachmentUuid='" + this.attachmentUuid + '\'' + ", supplierAttachmentUuid='" + this.supplierAttachmentUuid + '\'' + ", purchaserInnerAttachmentUuid='" + this.purchaserInnerAttachmentUuid + '\'' + ", erpCreatedName='" + this.erpCreatedName + '\'' + ", erpCreationDate=" + this.erpCreationDate + ", erpLastUpdateDate=" + this.erpLastUpdateDate + ", objectVersionNumber=" + this.objectVersionNumber + ", creationDate=" + this.creationDate + ", releaseFlag=" + this.releaseFlag + ", snapshotFlag=" + this.snapshotFlag + ", refPoHeaderId=" + this.refPoHeaderId + ", cancelledFlag=" + this.cancelledFlag + ", shipToLocationId=" + this.shipToLocationId + ", billToLocationId=" + this.billToLocationId + ", prHeaderId=" + this.prHeaderId + ", displayPrNum='" + this.displayPrNum + '\'' + ", receiverEmailAddress='" + this.receiverEmailAddress + '\'' + ", refusedBy=" + this.refusedBy + ", refusedFlag=" + this.refusedFlag + ", refusedDate=" + this.refusedDate + ", refusedRemark='" + this.refusedRemark + '\'' + ", sourceBillTypeCode='" + this.sourceBillTypeCode + '\'' + ", invoiceTitle='" + this.invoiceTitle + '\'' + ", taxRegisterNum='" + this.taxRegisterNum + '\'' + ", taxRegisterAddress='" + this.taxRegisterAddress + '\'' + ", taxRegisterTel='" + this.taxRegisterTel + '\'' + ", taxRegisterBank='" + this.taxRegisterBank + '\'' + ", taxRegisterBankAccount='" + this.taxRegisterBankAccount + '\'' + ", invoiceMethodCode='" + this.invoiceMethodCode + '\'' + ", invoiceTypeCode='" + this.invoiceTypeCode + '\'' + ", invoiceTitleTypeCode='" + this.invoiceTitleTypeCode + '\'' + ", invoiceDetailTypeCode='" + this.invoiceDetailTypeCode + '\'' + ", incorrectFlag=" + this.incorrectFlag + ", incorrectDate=" + this.incorrectDate + ", incorrectMsg='" + this.incorrectMsg + '\'' + ", shipToLocContName='" + this.shipToLocContName + '\'' + ", shipToLocTelNum='" + this.shipToLocTelNum + '\'' + ", billToLocContName='" + this.billToLocContName + '\'' + ", billToLocTelNum='" + this.billToLocTelNum + '\'' + ", approvedSyncStatus='" + this.approvedSyncStatus + '\'' + ", approvedSyncResponseMsg='" + this.approvedSyncResponseMsg + '\'' + ", approvedSyncDate=" + this.approvedSyncDate + ", invoiceMethodName='" + this.invoiceMethodName + '\'' + ", invoiceTypeName='" + this.invoiceTypeName + '\'' + ", invoiceTitleTypeName='" + this.invoiceTitleTypeName + '\'' + ", invoiceDetailTypeName='" + this.invoiceDetailTypeName + '\'' + ", modifyPriceFlag=" + this.modifyPriceFlag + ", createSyncStatus='" + this.createSyncStatus + '\'' + ", createSyncResponseMsg='" + this.createSyncResponseMsg + '\'' + ", createSyncDate=" + this.createSyncDate + ", changeSyncStatus='" + this.changeSyncStatus + '\'' + ", advancePaymentAmount=" + this.advancePaymentAmount + ", acceptMethod='" + this.acceptMethod + '\'' + ", receiveOrderType='" + this.receiveOrderType + '\'' + ", poUpgradeReConfirmFlag=" + this.poUpgradeReConfirmFlag + ", originalPoHeaderId=" + this.originalPoHeaderId + ", approveMethod='" + this.approveMethod + '\'' + ", purchaseUnitName='" + this.purchaseUnitName + '\'' + ", domesticCurrencyCode='" + this.domesticCurrencyCode + '\'' + ", domesticAmount=" + this.domesticAmount + ", domesticTaxIncludeAmount=" + this.domesticTaxIncludeAmount + ", settleSupplierId=" + this.settleSupplierId + ", settleSupplierCode='" + this.settleSupplierCode + '\'' + ", settleSupplierName='" + this.settleSupplierName + '\'' + ", settleSupplierTenantId=" + this.settleSupplierTenantId + ", settleErpSupplierId=" + this.settleErpSupplierId + ", settleErpSupplierCode='" + this.settleErpSupplierCode + '\'' + ", settleErpSupplierName='" + this.settleErpSupplierName + '\'' + ", changingFlag=" + this.changingFlag + ", unreadCount=" + this.unreadCount + ", purchaseOrgEnabledFlag=" + this.purchaseOrgEnabledFlag + ", currencyId=" + this.currencyId + ", currencyName='" + this.currencyName + '\'' + ", purchaseAgentEnabledFlag=" + this.purchaseAgentEnabledFlag + ", currencyEnabledFlag=" + this.currencyEnabledFlag + ", termCode='" + this.termCode + '\'' + ", termEnabledFlag=" + this.termEnabledFlag + ", cacheKey='" + this.cacheKey + '\'' + ", oldStatusCode='" + this.oldStatusCode + '\'' + ", financialPrecision=" + this.financialPrecision + ", domesticFinancialPrecision=" + this.domesticFinancialPrecision + ", supplierCompanyIds=" + this.supplierCompanyIds + ", supplierIds=" + this.supplierIds + ", autoSubmitFlag=" + this.autoSubmitFlag + ", acceptMethodMeaning='" + this.acceptMethodMeaning + '\'' + ", productNum='" + this.productNum + '\'' + ", productName='" + this.productName + '\'' + ", submitDateEnd=" + this.submitDateEnd + ", submitDateStart=" + this.submitDateStart + ", releaseDateStart=" + this.releaseDateStart + ", releaseDateEnd=" + this.releaseDateEnd + ", confirmDateStart=" + this.confirmDateStart + ", confirmDateEnd=" + this.confirmDateEnd + ", creationDateStart=" + this.creationDateStart + ", creationDateEnd=" + this.creationDateEnd + ", feedbackDateStart=" + this.feedbackDateStart + ", feedbackDateEnd=" + this.feedbackDateEnd + ", termName='" + this.termName + '\'' + ", statusCodes=" + this.statusCodes + ", erpStatusMeaning='" + this.erpStatusMeaning + '\'' + ", statusCodeMeaning='" + this.statusCodeMeaning + '\'' + ", sourceCodeMeaning='" + this.sourceCodeMeaning + '\'' + ", sourceBillTypeCodeMeaning='" + this.sourceBillTypeCodeMeaning + '\'' + ", approvedSyncStatusMeaning='" + this.approvedSyncStatusMeaning + '\'' + ", ouName='" + this.ouName + '\'' + ", poTypeCode='" + this.poTypeCode + '\'' + ", poTypeCodeMeaning='" + this.poTypeCodeMeaning + '\'' + ", displayStatusCode='" + this.displayStatusCode + '\'' + ", displayStatusCodeMeaning='" + this.displayStatusCodeMeaning + '\'' + ", erpCreationDateStart=" + this.erpCreationDateStart + ", erpCreationDateEnd=" + this.erpCreationDateEnd + ", poSourcePlatformMeaning='" + this.poSourcePlatformMeaning + '\'' + ", poCancelSelect=" + this.poCancelSelect + ", poCloseSelect=" + this.poCloseSelect + ", poProcessSelect=" + this.poProcessSelect + ", supplierCompanyCode='" + this.supplierCompanyCode + '\'' + ", poHeaderIds=" + this.poHeaderIds + ", lastUpdateDateStart=" + this.lastUpdateDateStart + ", lastUpdateDateEnd=" + this.lastUpdateDateEnd + ", beyondQuantity=" + this.beyondQuantity + ", nowDate=" + this.nowDate + ", beyondFlag=" + this.beyondFlag + ", evaluationFlag=" + this.evaluationFlag + ", deliverySyncFlag=" + this.deliverySyncFlag + ", operateType='" + this.operateType + '\'' + ", createSyncFlay='" + this.createSyncFlay + '\'' + ", deliverySyncStatus='" + this.deliverySyncStatus + '\'' + ", deliverySyncResponseMsg='" + this.deliverySyncResponseMsg + '\'' + ", deliverySyncDate=" + this.deliverySyncDate + ", unSaveEnable=" + this.unSaveEnable + ", displayPrLineNum='" + this.displayPrLineNum + '\'' + ", queryApprovingFlag=" + this.queryApprovingFlag + ", poLineList=" + this.poLineList + ", receiveOrderTypeMeaning='" + this.receiveOrderTypeMeaning + '\'' + ", strategyHeaderId=" + this.strategyHeaderId + ", agentIds='" + this.agentIds + '\'' + ", purchaseOrgIds='" + this.purchaseOrgIds + '\'' + ", taxPrice=" + this.taxPrice + ", loginName='" + this.loginName + '\'' + ", orgName='" + this.orgName + '\'' + ", agentName='" + this.agentName + '\'' + ", agentCode='" + this.agentCode + '\'' + ", purOrganizationName='" + this.purOrganizationName + '\'' + ", purOrganizationCode='" + this.purOrganizationCode + '\'' + ", supplierSiteCode='" + this.supplierSiteCode + '\'' + ", supplierSiteName='" + this.supplierSiteName + '\'' + ", publishFlag=" + this.publishFlag + ", realName='" + this.realName + '\'' + ", userId=" + this.userId + ", PromiseDeliveryDate=" + this.PromiseDeliveryDate + ", cacheMap=" + this.cacheMap + ", unCloseCount=" + this.unCloseCount + ", supplierCategoryId=" + this.supplierCategoryId + ", supplierCategoryCode='" + this.supplierCategoryCode + '\'' + ", supplierCategoryName='" + this.supplierCategoryName + '\'' + ", companyCode='" + this.companyCode + '\'' + ", ouCode='" + this.ouCode + '\'' + ", purchaseOrgCode='" + this.purchaseOrgCode + '\'' + ", originalPoNum='" + this.originalPoNum + '\'' + ", returnOrderFlag=" + this.returnOrderFlag + ", registeredCountryId=" + this.registeredCountryId + ", poLineExportVoList=" + this.poLineExportVoList + ", mallParentOrderNum='" + this.mallParentOrderNum + '\'' + '}';
    }

    public String getReceiptType() {
        return this.receiptType;
    }

    public void setReceiptType(String receiptType) {
        this.receiptType = receiptType;
    }
}
