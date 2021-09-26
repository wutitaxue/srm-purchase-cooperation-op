//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.srm.purchasecooperation.pr.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import io.choerodon.core.exception.CommonException;
import io.choerodon.core.oauth.CustomUserDetails;
import io.choerodon.core.oauth.DetailsHelper;
import io.choerodon.mybatis.annotation.ModifyAudit;
import io.choerodon.mybatis.annotation.VersionAudit;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.hzero.boot.platform.lov.annotation.LovValue;
import org.hzero.core.base.BaseConstants.Flag;
import org.hzero.starter.keyencrypt.core.Encrypt;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.Assert;
import org.srm.common.mybatis.domain.ExpandDomain;
import org.srm.purchasecooperation.common.utils.comparator.UniqueKey;
import org.srm.purchasecooperation.order.infra.annotation.ChangeField;
import org.srm.purchasecooperation.pr.domain.repository.PrLineAssignRepository;
import org.srm.purchasecooperation.pr.domain.vo.PrLineVO;

@ApiModel("采购申请行")
@VersionAudit
@ModifyAudit
@Table(name = "sprm_pr_line")
@JsonInclude(Include.NON_NULL)
public class PrLine extends ExpandDomain implements UniqueKey {
    public static final String FIELD_PR_LINE_ID = "prLineId";
    public static final String FIELD_PR_HEADER_ID = "prHeaderId";
    public static final String FIELD_TENANT_ID = "tenantId";
    public static final String FIELD_LINE_NUM = "lineNum";
    public static final String FIELD_DISPLAY_LINE_NUM = "displayLineNum";
    public static final String FIELD_COMPANY_ID = "companyId";
    public static final String FIELD_INV_ORGANIZATION_ID = "invOrganizationId";
    public static final String FIELD_INVENTORY_ID = "inventoryId";
    public static final String FIELD_ITEM_ID = "itemId";
    public static final String FIELD_ITEM_CODE = "itemCode";
    public static final String FIELD_ITEM_NAME = "itemName";
    public static final String FIELD_PRODUCT_ID = "productId";
    public static final String FIELD_PRODUCT_NUM = "productNum";
    public static final String FIELD_PRODUCT_NAME = "productName";
    public static final String FIELD_CATALOG_ID = "catalogId";
    public static final String FIELD_CATALOG_NAME = "catalogName";
    public static final String FIELD_CATEGORY_ID = "categoryId";
    public static final String FIELD_UOM_ID = "uomId";
    public static final String FIELD_QUANTITY = "quantity";
    public static final String FIELD_TAX_ID = "taxId";
    public static final String FIELD_TAX_RATE = "taxRate";
    public static final String FIELD_CURRENCY_CODE = "currencyCode";
    public static final String FIELD_TAX_INCLUDED_UNIT_PRICE = "taxIncludedUnitPrice";
    public static final String FIELD_UNIT_PRICE = "unitPrice";
    public static final String FIELD_LINE_AMOUNT = "lineAmount";
    public static final String FIELD_TAX_INCLUDED_LINE_AMOUNT = "taxIncludedLineAmount";
    public static final String FIELD_NEEDED_DATE = "neededDate";
    public static final String FIELD_SUPPLIER_TENANT_ID = "supplierTenantId";
    public static final String FIELD_SUPPLIER_ID = "supplierId";
    public static final String FIELD_SUPPLIER_CODE = "supplierCode";
    public static final String FIELD_SUPPLIER_NAME = "supplierName";
    public static final String FIELD_SUPPLIER_COMPANY_ID = "supplierCompanyId";
    public static final String FIELD_SUPPLIER_COMPANY_NAME = "supplierCompanyName";
    public static final String FIELD_EXECUTION_STATUS_CODE = "executionStatusCode";
    public static final String FIELD_EXECUTED_DATE = "executedDate";
    public static final String FIELD_EXECUTED_BY = "executedBy";
    public static final String FIELD_EXECUTION_BILL_ID = "executionBillId";
    public static final String FIELD_EXECUTION_BILL_NUM = "executionBillNum";
    public static final String FIELD_EXECUTION_BILL_DATA = "executionBillData";
    public static final String FIELD_ASSIGNED_FLAG = "assignedFlag";
    public static final String FIELD_ASSIGNED_DATE = "assignedDate";
    public static final String FIELD_ASSIGNED_BY = "assignedBy";
    public static final String FIELD_ASSIGNED_REMARK = "assignedRemark";
    public static final String FIELD_CANCELLED_FLAG = "cancelledFlag";
    public static final String FIELD_CANCELLED_DATE = "cancelledDate";
    public static final String FIELD_CANCELLED_BY = "cancelledBy";
    public static final String FIELD_CANCELLED_REMARK = "cancelledRemark";
    public static final String FIELD_CLOSED_FLAG = "closedFlag";
    public static final String FIELD_CLOSED_DATE = "closedDate";
    public static final String FIELD_CLOSED_BY = "closedBy";
    public static final String FIELD_CLOSED_REMARK = "closedRemark";
    public static final String FIELD_SUSPEND_FLAG = "suspendFlag";
    public static final String FIELD_SUSPEND_DATE = "suspendDate";
    public static final String FIELD_SUSPEND_REMARK = "suspendRemark";
    public static final String FIELD_PR_REQUESTED_NAME = "prRequestedName";
    public static final String FIELD_INCORRECT_FLAG = "incorrectFlag";
    public static final String FIELD_INCORRECT_DATE = "incorrectDate";
    public static final String FIELD_INCORRECT_MSG = "incorrectMsg";
    public static final String FIELD_ATTACHMENT_UUID = "attachmentUuid";
    public static final String FIELD_REMARK = "remark";
    public static final String FIELD_LINE_FREIGHT = "lineFreight";
    public static final String FIELD_EXECUTION_HEADER_BILL_ID = "executionHeaderBillId";
    public static final String FIELD_EXECUTION_HEADER_BILL_NUM = "executionHeaderBillNum";
    public static final String FIELD_INV_ORGANIZATION_NAME = "invOrganizationName";
    public static final String FIELD_CATEGORY_NAME = "categoryName";
    public static final String FIELD_UOM_NAME = "uomName";
    public static final String FIELD_TAX_CODE = "taxCode";
    public static final String FIELD_CURRENCY_NAME = "currencyName";
    public static final String FIELD_EXTERNAL_SYSTEM_CODE = "externalSystemCode";
    public static final String FIELD_DATA_VERSION = "dataVersion";
    public static final String FIELD_REQUESTED_BY = "requestedBy";
    public static final String FIELD_REQUEST_DATE = "requestDate";
    public static final String FIELD_OLD_CANCELLED_FLAG = "oldCancelledFlag";
    public static final String FIELD_PURCHASE_ORG_ID = "purchaseOrgId";
    public static final String FIELD_PURCHASE_AGENT_ID = "purchaseAgentId";
    public static final String FIELD_OU_ID = "ouId";
    public static final String FIELD_ITEM_ABC_CLASS = "itemAbcClass";
    public static final String FIELD_DRAWING_NUM = "drawingNum";
    public static final String FIELD_PROJECT_NUM = "projectNum";
    public static final String FIELD_PROJECT_NAME = "projectName";
    public static final String FIELD_CRANE_NUM = "craneNum";
    public static final String FIELD_URGENT_FLAG = "urgentFlag";
    public static final String FIELD_URGENT_DATE = "urgentDate";
    public static final String FIELD_COST_ID = "costId";
    public static final String FIELD_COST_CODE = "costCode";
    public static final String FIELD_ACCOUNT_SUBJECT_ID = "accountSubjectId";
    public static final String FIELD_ACCOUNT_SUBJECT_NUM = "accountSubjectNum";
    public static final String FIELD_WBS = "wbs";
    public static final String FIELD_WBS_CODE = "wbsCode";
    public static final String FIELD_JD_PRICE = "jdPrice";
    public static final String FIELD_PO_LINE_ID = "poLineId";
    public static final String FIELD_LAST_PURCHASE_PRICE = "lastPurchasePrice";
    public static final String FIELD_ERP_EDIT_STATUS = "erpEditStatus";
    public static final String FIELD_UNIT_PRICE_BATCH = "unitPriceBatch";
    public static final String FIELD_PROJECT_CATEGORY = "projectCategory";
    public static final String FIELD_ASSETS = "assets";
    public static final String FIELD_ASSET_CHILD_NUM = "assetChildNum";
    public static final String FIELD_QUALITY_STANDARD = "qualityStandard";
    public static final String FIELD_TAX_INCLUDED_BUDGET_UNIT_PRICE = "taxIncludedBudgetUnitPrice";
    public static final String FIELD_BUDGET_IO_FLAG = "budgetIoFlag";
    public static final String FIELD_DOCUMENT_ID = "documentId";
    public static final String FIELD_OCCUPIED_QUANTITY = "occupiedQuantity";
    public static final String FIELD_REST_PO_QUANTITY = "restPoQuantity";
    public static final String FIELD_KEEPER_USER_ID = "keeperUserId";
    public static final String FIELD_EXP_BEAR_DEP_ID = "expBearDepId";
    public static final String FIELD_SUPPLIER_ITEM_NAME = "supplierItemName";
    public static final String FIELD_INNER_PO_NUM = "innerPoNum";
    public static final String FIELD_ITEM_MODEL = "itemModel";
    public static final String FIELD_ITEM_SPECS = "itemSpecs";
    public static final String FIELD_ITEM_PROPERTIES = "itemProperties";
    public static final String FIELD_ACCEPTER_USER_ID = "accepterUserId";
    public static final String FIELD_ADDRESS = "address";
    public static final String FIELD_TAX_WITHOUT_FREIGHT_PRICE = "taxWithoutFreightPrice";
    public static final String FIELD_ACCOUNT_ASSIGN_TYPE_ID = "accountAssignTypeId";
    public static final String FIELD_FREIGHT_LINE_FLAG = "freightLineFlag";
    public static final String FIELD_EXECUTION_STRATEGY_CODE = "executionStrategyCode";
    public static final String FIELD_BUDGET_ACCOUNT_ID = "budgetAccountId";
    public static final String FIELD_BUDGET_ACCOUNT_NUM = "budgetAccountNum";
    public static final String FIELD_RECEIVER_INFORMATION = "receiverInformation";
    public static final String FIELD_RECEIVE_ADDRESS = "receiveAddress";
    public static final String FIELD_RECEIVE_CONTACT_NAME = "receiveContactName";
    public static final String FIELD_RECEIVE_TEL_NUM = "receiveTelNum";
    public static final String EXECUTION_STRATEGY_CODE = "executionStrategyCode";
    public static final String FIELD_BUSINESS_CARD_FLAG = "businessCardFlag";
    public static final String FIELD_CHANGE_ORDER_CODE = "changeOrderCode";
    public static final String FIELD_CHANGE_ORDER_MESSAGE = "changeOrderMessage";
    public static final String EXCHANGE_RATE = "exchangeRate";
    public static final String EXCHANGE_RATE_DATE = "exchangeRateDate";
    public static final String LOCAL_CURRENCY_TAX_UNIT = "localCurrencyTaxUnit";
    public static final String LOCAL_CURRENCY_NO_TAX_UNIT = "localCurrencyNoTaxUnit";
    public static final String LOCAL_CURRENCY_TAX_SUM = "localCurrencyTaxSum";
    public static final String LOCAL_CURRENCY_NO_TAX_SUM = "localCurrencyNoTaxSum";
    public static final String FIELD_CAN_VAT_FLAG = "canVatFlag";
    public static final String FIELD_AGENT_ID = "agentId";
    public static final String FIELD_COST_PAYER_USER_ID = "costPayerUserId";
    public static final String FIELD_PURCHASE_ORG_GROUP_NAME = "purchaseOrgGroupName";
    public static final String FIELD_COMPANY_ORG_NAME = "companyOrgName";
    public static final String FIELD_COST_ANCH_DEP_DESC = "costAnchDepDesc";
    public static final String FIELD_EXP_BEAR_DEP = "expBearDep";
    public static final String FIELD_SOURCE_PLATFORM_CODE = "sourcePlatformCode";
    public static final String FIELD_SURFACE_TREAT_FLAG = "surfaceTreatFlag";
    public static final String FIELD_REQ_TYPE_CODE = "reqTypeCode";
    public static final String FIELD_ACCEPTER_USER_NAME = "accepterUserName";
    public static final String FIELD_AGENT_NAME = "agentName";
    public static final String FIELD_KEEPER_USER_NAME = "keeperUserName";
    public static final String FIELD_BATCH_NO = "batchNo";
    public static final String FIELD_COMPANY_ORG_ID = "companyOrgId";
    public static final String FIELD_COST_ANCH_DEP_ID = "costAnchDepId";
    public static final String FIELD_OVERSEAS_PROCUREMENT = "overseasProcurement";
    public static final String FIELD_DRAWING_VERSION = "drawingVersion";
    public static final String FIELD_SUPPLIER_ITEM_CODE = "supplierItemCode";
    public static final String FIELD_FRAME_AGREEMENT_NUM = "frameAgreementNum";
    public static final String FIELD_BENCHMARK_PRICE = "benchmarkPrice";
    public static final String FIELD_CHANGE_PERCENT = "changePercent";
    public static final String FIELD_PURCHASE_LINE_TYPE_ID = "purchaseLineTypeId";
    public static final String FIELD_CART_USER_ID = "cartUserId";
    public static final String FIELD_CART_USER_TYPE = "cartUserType";
    public static final String FIELD_BUDGET_ACCOUNT_DEPTNO = "budgetAccountDeptno";
    public static final String FIELD_BUDGET_ACCOUNT_PRICE = "budgetAccountPrice";
    public static final String[] LINE_IGNORE_LIST = new String[]{"incorrectFlag", "externalSystemCode", "incorrectMsg", "incorrectDate", "cancelledFlag", "cancelledDate", "cancelledBy", "cancelledRemark", "suspendFlag", "suspendDate", "suspendRemark", "assignedFlag", "assignedDate", "assignedBy", "assignedRemark", "closedFlag", "closedDate", "closedBy", "closedRemark", "executionStatusCode", "executedDate", "executedBy", "executionBillId", "executionBillNum", "executionBillData", "taxIncludedLineAmount", "lineNum", "prHeaderId", "prLineId", "dataVersion", "requestedBy", "lineFreight", "oldCancelledFlag", "localCurrencyNoTaxSum", "localCurrencyTaxSum", "localCurrencyNoTaxUnit", "localCurrencyTaxUnit", "exchangeRate"};
    public static final String[] ERP_UPDATE_FIELD_LIST = new String[]{"erpEditStatus", "companyId", "invOrganizationId", "inventoryId", "purchaseOrgId", "purchaseAgentId", "ouId", "companyId", "itemId", "itemCode", "itemName", "itemModel", "itemSpecs", "productId", "productNum", "productName", "catalogId", "catalogName", "categoryId", "uomId", "taxId", "quantity", "taxRate", "currencyCode", "taxIncludedUnitPrice", "lineAmount", "taxIncludedLineAmount", "neededDate", "supplierTenantId", "supplierId", "supplierCode", "supplierName", "supplierCompanyId", "supplierCompanyName", "remark", "invOrganizationName", "categoryName", "uomName", "taxCode", "currencyName", "requestedBy", "requestDate", "prRequestedName", "incorrectFlag", "incorrectDate", "incorrectMsg", "assignedFlag", "assignedDate", "assignedBy", "assignedRemark", "closedFlag", "closedDate", "closedBy", "closedRemark", "suspendFlag", "suspendDate", "suspendRemark", "cancelledFlag", "cancelledDate", "cancelledBy", "cancelledRemark", "executionStatusCode", "executedDate", "executedBy", "executionBillId", "executionBillNum", "executionBillData", "executionHeaderBillId", "executionHeaderBillNum", "jdPrice", "projectCategory", "localCurrencyNoTaxSum", "localCurrencyTaxSum", "localCurrencyNoTaxUnit", "localCurrencyTaxUnit", "exchangeRate"};
    public static final String[] SRM_COPY_FIELD_LIST = new String[]{"tenantId", "lineNum", "displayLineNum", "companyId", "purchaseOrgId", "purchaseAgentId", "requestDate", "requestedBy", "prRequestedName", "invOrganizationId", "inventoryId", "itemId", "itemCode", "itemName", "itemAbcClass", "drawingNum", "projectNum", "projectName", "craneNum", "productId", "productNum", "productName", "catalogId", "catalogName", "categoryId", "uomId", "quantity", "taxId", "taxRate", "currencyCode", "unitPrice", "taxIncludedUnitPrice", "lineAmount", "taxIncludedLineAmount", "neededDate", "supplierTenantId", "supplierId", "supplierCode", "supplierName", "supplierCompanyId", "supplierCompanyName", "canVatFlag", "erpEditStatus", "attachmentUuid", "remark", "lineFreight", "urgentFlag", "urgentDate", "costId", "costCode", "accountSubjectId", "accountSubjectNum", "wbs", "lastPurchasePrice", "itemModel", "itemSpecs", "itemProperties", "agentId", "keeperUserId", "accepterUserId", "costPayerUserId", "address", "innerPoNum", "jdPrice", "purchaseOrgGroupName", "companyOrgName", "costAnchDepDesc", "expBearDep", "sourcePlatformCode", "surfaceTreatFlag", "reqTypeCode", "accepterUserName", "agentName", "keeperUserName", "batchNo", "taxCode", "companyOrgId", "costAnchDepId", "expBearDepId", "overseasProcurement", "drawingVersion", "supplierItemCode", "supplierItemName", "frameAgreementNum", "wbsCode", "taxWithoutFreightPrice", "benchmarkPrice", "changePercent", "unitPriceBatch", "projectCategory", "assets", "assetChildNum", "accountAssignTypeId", "purchaseLineTypeId", "budgetIoFlag", "taxIncludedBudgetUnitPrice", "qualityStandard", "freightLineFlag", "budgetAccountId", "budgetAccountNum", "receiverInformation", "businessCardFlag", "receiveAddress", "receiveContactName", "receiveTelNum", "cartUserId", "cartUserType", "budgetAccountDeptno", "budgetAccountPrice"};
    public static final Map<String, String> FIELD_REMARK_MAP = new HashMap(69);
    private static final int MAX_PER = 10000;
    @ApiModelProperty("表ID，主键，供其他表做外键")
    @Id
    @GeneratedValue
    @Encrypt
    private Long prLineId;
    @ApiModelProperty(
            value = "采购申请头ID",
            required = true
    )
    @NotNull
    @Encrypt
    private Long prHeaderId;
    @ApiModelProperty(
            value = "租户ID",
            required = true
    )
    @NotNull
    private Long tenantId;
    @ApiModelProperty("行号")
    private Long lineNum;
    @ApiModelProperty(
            value = "展示行号",
            required = true
    )
    @NotBlank
    private String displayLineNum;
    @ApiModelProperty(
            value = "公司ID",
            required = true
    )
    @NotNull
    @Encrypt
    private Long companyId;
    @ApiModelProperty(
            value = "库存组织ID",
            required = true
    )
    @NotNull
    @ChangeField(
            name = "inv_organization_id",
            table = "hpfm_inv_organization",
            field = "organization_name",
            quaryField = "organization_id"
    )
    @Encrypt
    private Long invOrganizationId;
    @ApiModelProperty("库房ID")
    @ChangeField(
            name = "inventory_id",
            table = "hpfm_inventory",
            field = "inventory_name",
            quaryField = "inventory_id"
    )
    @Encrypt
    private Long inventoryId;
    @ApiModelProperty("物料ID")
    @ChangeField(
            name = "item_id",
            table = "smdm_item",
            field = "item_code",
            quaryField = "item_id"
    )
    @Encrypt
    private Long itemId;
    @ApiModelProperty("物料编码")
    private String itemCode;
    @ApiModelProperty(
            value = "物料名称",
            required = true
    )
    @NotBlank
    private String itemName;
    @ApiModelProperty("物料ABC属性")
    @Length(
            max = 60
    )
    private String itemAbcClass;
    @ApiModelProperty("图号")
    @Length(
            max = 60
    )
    private String drawingNum;
    @ApiModelProperty("项目号")
    @Length(
            max = 60
    )
    private String projectNum;
    @ApiModelProperty("项目名称")
    @Length(
            max = 60
    )
    private String projectName;
    @ApiModelProperty("项目号车号")
    @Length(
            max = 60
    )
    private String craneNum;
    @ApiModelProperty("商品ID")
    @Encrypt
    private Long productId;
    @ApiModelProperty("商品编码")
    private String productNum;
    @ApiModelProperty("商品名称")
    private String productName;
    @ApiModelProperty("商品目录ID")
    @Encrypt
    private Long catalogId;
    @ApiModelProperty("商品目录名")
    private String catalogName;
    @ApiModelProperty("自主品类ID")
    @ChangeField(
            name = "category_id",
            table = "smdm_item_category",
            field = "category_name",
            quaryField = "category_id"
    )
    @Encrypt
    private Long categoryId;
    @ApiModelProperty(
            value = "单位ID",
            required = true
    )
    @NotNull
    @ChangeField(
            name = "uom_id",
            table = "smdm_uom",
            field = "uom_name",
            quaryField = "uom_id"
    )
    @Encrypt
    private Long uomId;
    @ApiModelProperty("数量")
    private BigDecimal quantity;
    @ApiModelProperty("税率ID")
    @ChangeField(
            name = "tax_id",
            table = "smdm_tax",
            field = "tax_code",
            quaryField = "tax_id"
    )
    @Encrypt
    private Long taxId;
    @ApiModelProperty("税率")
    private BigDecimal taxRate;
    @ApiModelProperty("币种")
    private String currencyCode;
    @ApiModelProperty("不含税单价")
    private BigDecimal unitPrice;
    @ApiModelProperty("含税单价")
    private BigDecimal taxIncludedUnitPrice;
    @ApiModelProperty("不含税行金额")
    private BigDecimal lineAmount;
    @ApiModelProperty("含税行金额")
    private BigDecimal taxIncludedLineAmount;
    @DateTimeFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @ApiModelProperty("需求日期")
    private Date neededDate;
    @ApiModelProperty("供应商租户ID")
    private Long supplierTenantId;
    @ApiModelProperty(
            value = "供应商ID",
            required = true
    )
    @Encrypt
    private Long supplierId;
    @ApiModelProperty("供应商编码")
    private String supplierCode;
    @ApiModelProperty("供应商名称")
    private String supplierName;
    @ApiModelProperty("供应商公司ID")
    @ChangeField(
            name = "supplier_company_id",
            table = "hpfm_company",
            field = "company_name",
            quaryField = "company_id"
    )
    @Encrypt
    private Long supplierCompanyId;
    @ApiModelProperty("供应商公司描述")
    private String supplierCompanyName;
    @LovValue(
            lovCode = "SPRM.PR_EXECUTION_STATUS",
            meaningField = "executionStatusMeaning"
    )
    @ApiModelProperty("执行状态代码")
    private String executionStatusCode;
    @ApiModelProperty("执行时间")
    private Date executedDate;
    @ApiModelProperty("执行人ID")
    private Long executedBy;
    @ApiModelProperty("目标单据ID")
    private Long executionBillId;
    @ApiModelProperty("目标单据编码")
    private String executionBillNum;
    @ApiModelProperty("目标单据附加数据")
    private String executionBillData;
    @ApiModelProperty(
            value = "分配标识",
            required = true
    )
    @NotNull
    private Integer assignedFlag;
    @ApiModelProperty("分配时间")
    private Date assignedDate;
    @ApiModelProperty("分配人ID")
    private Long assignedBy;
    @ApiModelProperty("分配原因")
    private String assignedRemark;
    @ApiModelProperty(
            value = "取消标识",
            required = true
    )
    @NotNull
    private Integer cancelledFlag;
    @ApiModelProperty("取消时间")
    private Date cancelledDate;
    @ApiModelProperty("取消人ID")
    private Long cancelledBy;
    @ApiModelProperty("取消原因")
    private String cancelledRemark;
    @ApiModelProperty(
            value = "关闭标识",
            required = true
    )
    @NotNull
    private Integer closedFlag;
    @ApiModelProperty("关闭时间")
    private Date closedDate;
    @ApiModelProperty("关闭人ID")
    private Long closedBy;
    @ApiModelProperty("关闭原因")
    private String closedRemark;
    @ApiModelProperty(
            value = "暂挂标志",
            required = true
    )
    @NotNull
    private Integer suspendFlag;
    @ApiModelProperty("暂挂日期")
    private Date suspendDate;
    @ApiModelProperty("暂挂说明")
    private String suspendRemark;
    @ApiModelProperty(
            value = "异常标志",
            required = true
    )
    @NotNull
    private Integer incorrectFlag;
    @ApiModelProperty("异常发生时间")
    private Date incorrectDate;
    @ApiModelProperty("异常信息")
    private String incorrectMsg;
    @ApiModelProperty("附件UUID,功能: sprm-pr")
    private String attachmentUuid;
    @ApiModelProperty("行备注")
    private String remark;
    @ApiModelProperty("可开专票标识")
    @Range(
            max = 1L
    )
    private Integer canVatFlag;
    @ApiModelProperty("行运费")
    private BigDecimal lineFreight;
    @ApiModelProperty("目标头单据ID")
    private Long executionHeaderBillId;
    @ApiModelProperty("目标头单据编码")
    private String executionHeaderBillNum;
    @ApiModelProperty("申请日期")
    private Date requestDate;
    @ApiModelProperty("申请人ID")
    @ChangeField(
            name = "requested_by",
            table = "iam_user",
            field = "real_name",
            quaryField = "id"
    )
    private Long requestedBy;
    @ApiModelProperty("申请人")
    private String prRequestedName;
    @ApiModelProperty("业务实体ID")
    @Encrypt
    private Long ouId;
    @ApiModelProperty("采购组织ID")
    @Encrypt
    private Long purchaseOrgId;
    @ApiModelProperty("采购员ID")
    @ChangeField(
            name = "purchase_agent_id",
            table = "hpfm_purchase_agent",
            field = "purchase_agent_name",
            quaryField = "purchase_agent_id"
    )
    @Encrypt
    private Long purchaseAgentId;
    @ApiModelProperty("ERP编辑状态")
    private String erpEditStatus;
    @ApiModelProperty("版本号")
    private Long objectVersionNumber;
    @ApiModelProperty("加急标识")
    private Integer urgentFlag;
    @ApiModelProperty("加急日期")
    private Date urgentDate;
    @ApiModelProperty("成本中心主键")
    @ChangeField(
            name = "cost_id",
            table = "smdm_cost_center",
            field = "cost_name",
            quaryField = "cost_id"
    )
    @Encrypt
    private Long costId;
    @ApiModelProperty("成本中心编码")
    private String costCode;
    @ApiModelProperty("总账主键")
    @ChangeField(
            name = "account_subject_id",
            table = "smdm_account_subject",
            field = "account_subject_name",
            quaryField = "account_subject_id"
    )
    @Encrypt
    private Long accountSubjectId;
    @ApiModelProperty("外部总账编码")
    private String accountSubjectNum;
    @ApiModelProperty("工作分解结构")
    private String wbs;
    @ApiModelProperty("划线价")
    private BigDecimal jdPrice;
    @ApiModelProperty("采购协议头ID")
    @Encrypt
    private Long pcHeaderId;
    @ApiModelProperty("已占用数量（已创建订单数量）")
    private BigDecimal occupiedQuantity;
    @ApiModelProperty("采购申请行ID")
    @Encrypt
    private Long poLineId;
    @ApiModelProperty("上次采购价格")
    private BigDecimal lastPurchasePrice;
    @ApiModelProperty("型号")
    private String itemModel;
    @ApiModelProperty("规格")
    private String itemSpecs;
    @ApiModelProperty("属性")
    private String itemProperties;
    @ApiModelProperty("采购员ID")
    @Encrypt
    private Long agentId;
    @ApiModelProperty("保管人用户ID")
    @ChangeField(
            name = "keeper_user_id",
            table = "iam_user",
            field = "real_name",
            quaryField = "id"
    )
    private Long keeperUserId;
    @ApiModelProperty("验收人用户ID")
    @ChangeField(
            name = "accepter_user_id",
            table = "iam_user",
            field = "real_name",
            quaryField = "id"
    )
    private Long accepterUserId;
    @ApiModelProperty("费用承担人用户ID")
    private Long costPayerUserId;
    @ApiModelProperty("地点，值集SCUX.HYTE.SPRM.PR_LINE.ADDRESS")
    private String address;
    @ApiModelProperty("内部订单号")
    private String innerPoNum;
    @ApiModelProperty("图纸版本")
    private String drawingVersion;
    @ApiModelProperty("供应商料号")
    private String supplierItemCode;
    @ApiModelProperty("批次号")
    private String batchNo;
    @ApiModelProperty("费用承担部门id")
    @ChangeField(
            name = "exp_bear_dep_id",
            table = "hpfm_unit",
            field = "unit_name",
            quaryField = "unit_id"
    )
    @Encrypt
    private String expBearDepId;
    @ApiModelProperty("供应商料号描述")
    private String supplierItemName;
    private Integer surfaceTreatFlag;
    @ApiModelProperty("基准价")
    private BigDecimal benchmarkPrice;
    @ApiModelProperty("涨跌幅")
    private BigDecimal changePercent;
    @ApiModelProperty("价格批量(每)")
    private BigDecimal unitPriceBatch;
    @ApiModelProperty("项目类别")
    @LovValue(
            lovCode = "SPUC.PR_LINE_PROJECT_CATEHORY",
            meaningField = "projectCategoryMeaning"
    )
    private String projectCategory;
    @ApiModelProperty("资产")
    private String assets;
    @ApiModelProperty("资产子编号")
    private String assetChildNum;
    @ApiModelProperty("含税单价(不含运费)")
    private BigDecimal taxWithoutFreightPrice;
    @ApiModelProperty("框架协议编号")
    private String frameAgreementNum;
    @ApiModelProperty("wbs编码")
    private String wbsCode;
    @ApiModelProperty("账户分配类别ID")
    @Encrypt
    private Long accountAssignTypeId;
    @ApiModelProperty("新电商运费行标识")
    private Integer freightLineFlag;
    @ApiModelProperty("质量标准")
    private String qualityStandard;
    @ApiModelProperty("预算内外标识")
    private Integer budgetIoFlag;
    @ApiModelProperty("预算单价(含税)")
    private BigDecimal taxIncludedBudgetUnitPrice;
    @ApiModelProperty("执行策略")
    @LovValue(
            lovCode = "SPRM.EXECUTION_STRATEGY",
            meaningField = "executionStrategyMeaning"
    )
    private String executionStrategyCode;
    @ApiModelProperty("预算科目ID")
    @Encrypt
    private Long budgetAccountId;
    @ApiModelProperty("预算科目编码")
    private String budgetAccountNum;
    @ApiModelProperty("收货信息")
    private String receiverInformation;
    @ApiModelProperty("收货地址")
    private String receiveAddress;
    @ApiModelProperty("收货联系人")
    private String receiveContactName;
    @ApiModelProperty("收货联系电话")
    private String receiveTelNum;
    @ApiModelProperty("特殊商品标识")
    private Integer businessCardFlag;
    @ApiModelProperty("卡片用户ID")
    private Long cartUserId;
    @ApiModelProperty("卡片类型 GROUP/EMPLOYEE")
    private String cartUserType;
    @ApiModelProperty("自动生成订单成功失败标识")
    private String changeOrderCode;
    @ApiModelProperty("订单生成失败信息")
    private String changeOrderMessage;
    @ApiModelProperty("预算单据id")
    private String documentId;
    @ApiModelProperty("预算部门编号")
    private String budgetAccountDeptno;
    @ApiModelProperty("预算金额")
    private BigDecimal budgetAccountPrice;
    @ApiModelProperty("本币不含税金额")
    private BigDecimal localCurrencyNoTaxSum;
    @ApiModelProperty("本币含税金额")
    private BigDecimal localCurrencyTaxSum;
    @ApiModelProperty("本币不含税单价")
    private BigDecimal localCurrencyNoTaxUnit;
    @ApiModelProperty("本币含税单价")
    private BigDecimal localCurrencyTaxUnit;
    @ApiModelProperty("汇率")
    private BigDecimal exchangeRate;
    @ApiModelProperty("汇率日期")
    private Date exchangeRateDate;
    @ApiModelProperty("品类是否推资产标识")
    private Integer isPushAssetsFlag;


    /*非数据库字段*/
    @Transient
    private Boolean referencePriceDisplayFlag;
    @ApiModelProperty("旧取消状态")
    @Transient
    private Integer oldCancelledFlag;
    @ApiModelProperty("是否启用单位控制")
    @Transient
    private Integer unitFlag;
    @ApiModelProperty("执行状态含义")
    @Transient
    private String executionStatusMeaning;
    @ApiModelProperty("基本计量单位")
    @Transient
    private String primaryUomName;
    @ApiModelProperty("采购单位")
    @Transient
    private String orderUomName;
    @Transient
    @ApiModelProperty("原币含税单价")
    private BigDecimal enteredTaxIncludedPrice;
    @Transient
    @ApiModelProperty("申请总额")
    private BigDecimal amount;
    @Transient
    @ApiModelProperty("公司名称")
    private String companyName;
    @Transient
    @ApiModelProperty("库存组织名称")
    private String invOrganizationName;
    @ApiModelProperty("每")
    @Transient
    private Integer per;
    @ApiModelProperty("通过每转换后的单价")
    @Transient
    private BigDecimal convertUnitPrice;
    @Transient
    private Long hObjectVersionNumber;
    @Transient
    private String prNum;
    @Transient
    private String displayPrNum;
    @Transient
    private String displayPrLineNum;
    @Transient
    private String prSourcePlatform;
    @ApiModelProperty(value = "外部系统代码", required = true)
    @Transient
    private String externalSystemCode;
    @ApiModelProperty("erp采购申请头id")
    @Transient
    @Encrypt
    private String esPrHeaderId;
    @ApiModelProperty("erp采购申请编号")
    @Transient
    private String esPrNumber;
    @ApiModelProperty("erp采购申请行id")
    @Transient
    @Encrypt
    private String esPrLineId;
    @ApiModelProperty("erp采购申请行编号")
    @Transient
    private String esPrLineNum;
    @ApiModelProperty("erp数据版本号")
    @Transient
    private Long dataVersion;
    @ApiModelProperty("订单类型id")
    @Transient
    @Encrypt
    private Long orderTypeId;
    @ApiModelProperty("采购订单号集合")
    @Transient
    private List<String> poNumList;
    @ApiModelProperty("采购供应商集合")
    @Transient
    private List<PrLineSupplier> supplierList;
    @ApiModelProperty("送货单Id集合")
    @Transient
    @Encrypt
    private List<Long> asnLineIdList;
    @ApiModelProperty("动态申请行金额")
    @Transient
    private BigDecimal dynamicLineAmount;
    @ApiModelProperty("执行策略")
    @Transient
    private String executionStrategyMeaning;
    @ApiModelProperty("库房名称")
    @Transient
    private String inventoryName;
    @ApiModelProperty("品类名称")
    @Transient
    private String categoryName;
    @ApiModelProperty("单位名称")
    @Transient
    private String uomName;
    @ApiModelProperty("成本中心名称")
    @Transient
    private String costName;
    @ApiModelProperty("总账科目名称")
    @Transient
    private String accountSubjectName;
    @ApiModelProperty("费用承担部门编码")
    @Transient
    private String expBearDepCode;
    @ApiModelProperty("费用承担部门名称")
    @Transient
    private String expBearDep;
    @ApiModelProperty("占用状态")
    @Transient
    private Integer occupyFlag;
    @ApiModelProperty("旧需求行数量")
    @Transient
    private BigDecimal oldQuantity;
    @ApiModelProperty("旧需求行Id")
    @Transient
    @Encrypt
    private String oldPrLineId;
    @ApiModelProperty("是否需要提交")
    @Transient
    private Integer changeSubmitFlag;
    @ApiModelProperty("头上采购组织")
    @Transient
    @Encrypt
    private Integer purchaseHerderOrgId;
    @ApiModelProperty("采购员ID")
    @Transient
    @Encrypt
    private Long purchaseHerderAgentId;
    @ApiModelProperty("业务实体ID")
    @Transient
    private Long ouHeaderId;
    @ApiModelProperty("已经分配的申请头")
    @Transient
    private List<PrHeader> prHeaderAssignList;
    @Transient
    private String mappingCode;
    @ApiModelProperty("基准价类型(NET/未税价|TAX/含税价")
    @Transient
    private String benchmarkPriceType;
    @ApiModelProperty("合同价头ID")
    @Transient
    private Long holdPcHeaderId;
    @ApiModelProperty("合同价行ID")
    @Transient
    private Long holdPcLineId;
    @ApiModelProperty("合同价协议号")
    @Transient
    private String contractNum;
    @ApiModelProperty("阶梯价判断标识")
    @Transient
    private Integer ladderQuotationFlag;
    @ApiModelProperty("自动转单标识")
    @Transient
    private Integer prToPoAutoFlag;
    @ApiModelProperty("外部需求执行人 分配优先级最高")
    @Transient
    private List<Long> assignUserIds;
    @ApiModelProperty("需求部门id")
    @Transient
    private Long unitId;
    @ApiModelProperty("需求部门")
    @Transient
    private String unitName;
    @ApiModelProperty("收货地址id")
    @Transient
    private Long receiverAddressId;
    @ApiModelProperty("收单方描述")
    @Transient
    private String receiverAddress;
    @ApiModelProperty("收货方地址")
    @Transient
    private String invoiceAddress;
    @ApiModelProperty("剩余可创建订单数量")
    @Transient
    private BigDecimal restPoQuantity;
    @ApiModelProperty("本次下单数量（默认等于剩余可创建订单数量）")
    @Transient
    private BigDecimal thisOrderQuantity;
    @ApiModelProperty("项目类别含义")
    @Transient
    private String projectCategoryMeaning;
    @Transient
    @ApiModelProperty("选择的供应商公司id(申请转订单使用)")
    private Long selectSupplierCompanyId;
    @ApiModelProperty("选择的供应商租户id(申请转订单使用)")
    @Transient
    private Long selectSupplierTenantId;
    @ApiModelProperty("选择的供应商公司编码(申请转订单使用)")
    @Transient
    private String selectSupplierCode;
    @ApiModelProperty("选择的供应商租户名称(申请转订单使用)")
    @Transient
    private String selectSupplierCompanyName;
    @ApiModelProperty("申请类型id(申请转订单判断并单规则使用)")
    @Transient
    private Long prTypeId;
    @ApiModelProperty("价格库ID")
    @Transient
    @Encrypt
    private Long priceLibraryId;
    @ApiModelProperty("采购员（个性化映射）")
    @Transient
    private String agentName;
    @ApiModelProperty("复制的采购申请行ID")
    @Transient
    private Long copyPrLineId;
    @ApiModelProperty("行品类是否推资产标识临时字段")
    @Transient
    private Integer attributeVarchar11;

    public PrLine() {
    }

    public PrLine(Long prHeaderId, Long prLineId, Integer suspendFlag, Integer closedFlag, Integer cancelledFlag) {
        this.prHeaderId = prHeaderId;
        this.prLineId = prLineId;
        this.suspendFlag = suspendFlag;
        this.closedFlag = closedFlag;
        this.cancelledFlag = cancelledFlag;
    }

    public PrLine(Long prHeaderId) {
        this.prHeaderId = prHeaderId;
    }

    public PrLine(Long prHeaderId, String productNum) {
        this.prHeaderId = prHeaderId;
        this.productNum = productNum;
    }

    public PrLine(Long prLineId, int one, Long objectVersionNumber, Long executionBillId, String executionBillNum, Long executedBy, Date executedDate, String executionStatusCode, Long poHeaderId, String poNum) {
        this.prLineId = prLineId;
        this.closedFlag = one;
        this.setObjectVersionNumber(objectVersionNumber);
        this.executionBillId = executionBillId;
        this.executionBillNum = executionBillNum;
        this.executedBy = executedBy;
        this.executedDate = executedDate;
        this.executionStatusCode = executionStatusCode;
        this.executionHeaderBillId = poHeaderId;
        this.executionHeaderBillNum = poNum;
    }

    public PrLine(Long prLineId, int one, Long objectVersionNumber, Long executionBillId, String executionBillNum, Long executedBy, Date executedDate, String executionStatusCode, Long poHeaderId, String poNum, BigDecimal occupiedQuantity) {
        this.prLineId = prLineId;
        this.closedFlag = one;
        this.setObjectVersionNumber(objectVersionNumber);
        this.executionBillId = executionBillId;
        this.executionBillNum = executionBillNum;
        this.executedBy = executedBy;
        this.executedDate = executedDate;
        this.executionStatusCode = executionStatusCode;
        this.executionHeaderBillId = poHeaderId;
        this.executionHeaderBillNum = poNum;
        this.occupiedQuantity = occupiedQuantity;
    }

    public PrLine(Long prLineId, int one, Long objectVersionNumber) {
        this.prLineId = prLineId;
        this.closedFlag = one;
        this.setObjectVersionNumber(objectVersionNumber);
    }

    public PrLine(PrLineVO prLineVO, CustomUserDetails userDetails) {
        this.prLineId = prLineVO.getPrLineId();
        this.closedFlag = Flag.YES;
        this.closedBy = userDetails.getUserId();
        this.closedDate = new Date();
        this.closedRemark = prLineVO.getClosedRemark();
        this.objectVersionNumber = prLineVO.getObjectVersionNumber();
    }

    public void validate(PrHeader prHeader) {
        if (!prHeader.getPrSourcePlatform().equals("E-COMMERCE") && !prHeader.getPrSourcePlatform().equals("CATALOGUE")) {
            if (null == this.itemName || null == this.uomId || null == this.quantity || this.neededDate == null) {
                throw new CommonException("error.pr.line.not.null.field.exists", new Object[]{this.displayLineNum});
            }
        } else if (null == this.uomId && !Flag.YES.equals(this.freightLineFlag) || null == this.quantity || this.neededDate == null) {
            throw new CommonException("error.pr.line.not.null.field.exists", new Object[]{this.displayLineNum});
        }

        if ("CATALOGUE".equals(prHeader.getPrSourcePlatform()) && !Flag.YES.equals(this.freightLineFlag) && null == this.catalogName && null == this.productNum && null == this.productName) {
            throw new CommonException("error.pr.line.not.null.field.exists", new Object[]{this.displayLineNum});
        }
    }

    public void validateForErp() {
        if (null == this.itemName || null == this.uomId || null == this.quantity || this.neededDate == null || this.ouId == null) {
            throw new CommonException("error.pr.line.not.null.field.exists", new Object[]{this.displayLineNum});
        }
    }

    public void validateSubmit(PrHeader prHeader) {
        try {
            this.validate(prHeader);
        } catch (CommonException var3) {
            throw new CommonException("error.pr.submit.fields_null", new Object[]{prHeader.getDisplayPrNum()});
        }
    }

    public void updatePrLineExecute() {
        this.setExecutionStatusCode((String)null);
        this.setExecutedDate((Date)null);
        this.setExecutedBy((Long)null);
        this.setExecutionBillId((Long)null);
        this.setExecutionBillNum((String)null);
        this.setExecutionHeaderBillId((Long)null);
        this.setExecutionHeaderBillNum((String)null);
    }

    public void openAndCleanExecutionStatusForLine() {
        if (Flag.YES.equals(this.assignedFlag)) {
            this.setExecutionStatusCode("ASSIGNED");
        } else {
            this.setExecutionStatusCode((String)null);
        }

        this.setExecutedDate((Date)null);
        this.setExecutedBy((Long)null);
        this.setExecutionBillId((Long)null);
        this.setExecutionBillNum((String)null);
        this.setExecutionHeaderBillId((Long)null);
        this.setExecutionHeaderBillNum((String)null);
        this.setClosedFlag(Flag.NO);
    }

    public void initData(PrHeader prHeader) {
        this.prHeaderId = prHeader.getPrHeaderId();
        this.tenantId = prHeader.getTenantId();
        this.closedFlag = (Integer)Optional.ofNullable(this.closedFlag).orElse(Flag.NO);
        this.cancelledFlag = (Integer)Optional.ofNullable(this.cancelledFlag).orElse(Flag.NO);
        this.assignedFlag = (Integer)Optional.ofNullable(this.assignedFlag).orElse(Flag.NO);
        this.suspendFlag = (Integer)Optional.ofNullable(this.suspendFlag).orElse(Flag.NO);
    }

    public void countLineAmount() {
        this.countLineAmount(10);
    }

    public void countLineAmount(int decimalPlaces) {
        if (this.taxIncludedUnitPrice != null && this.quantity != null) {
            BigDecimal priceBranch = this.unitPriceBatch == null ? BigDecimal.ONE : this.unitPriceBatch;
            this.taxIncludedLineAmount = this.taxIncludedUnitPrice.divide(priceBranch, 10, RoundingMode.HALF_UP).multiply(this.quantity);
            this.taxWithoutFreightPrice = BigDecimal.ZERO.equals(this.quantity) ? this.taxIncludedUnitPrice : this.taxIncludedLineAmount.subtract((BigDecimal)Optional.ofNullable(this.lineFreight).orElse(BigDecimal.ZERO)).divide(this.quantity, 10, RoundingMode.HALF_UP);
            if (this.taxRate == null) {
                this.unitPrice = this.taxWithoutFreightPrice;
                this.lineAmount = this.taxIncludedLineAmount;
            } else {
                this.unitPrice = BigDecimal.ZERO.compareTo(this.taxIncludedUnitPrice) != 0 ? this.taxIncludedUnitPrice.divide(BigDecimal.ONE.add(this.taxRate.divide(new BigDecimal("100"))), decimalPlaces, RoundingMode.HALF_UP) : BigDecimal.ZERO;
                this.lineAmount = this.unitPrice.divide(priceBranch, 10, RoundingMode.HALF_UP).multiply(this.quantity);
            }
        } else {
            this.taxIncludedLineAmount = BigDecimal.ZERO;
            this.lineAmount = BigDecimal.ZERO;
        }
    }

    public void countLineAmount(int defaultPrecision, int financialPrecision) {
        if (this.taxIncludedUnitPrice != null && this.quantity != null) {
            BigDecimal priceBranch = this.unitPriceBatch == null ? BigDecimal.ONE : this.unitPriceBatch;
            this.taxIncludedUnitPrice = this.taxIncludedUnitPrice.setScale(defaultPrecision, RoundingMode.HALF_UP);
            this.taxIncludedLineAmount = this.taxIncludedUnitPrice.divide(priceBranch, defaultPrecision, RoundingMode.HALF_UP).multiply(this.quantity).setScale(financialPrecision, RoundingMode.HALF_UP);
            this.taxWithoutFreightPrice = BigDecimal.ZERO.equals(this.quantity) ? this.taxIncludedUnitPrice : this.taxIncludedLineAmount.subtract((BigDecimal)Optional.ofNullable(this.lineFreight).orElse(BigDecimal.ZERO)).divide(this.quantity, defaultPrecision, RoundingMode.HALF_UP);
            if (this.taxRate == null) {
                this.unitPrice = this.taxWithoutFreightPrice;
                this.lineAmount = this.taxIncludedLineAmount;
            } else {
                this.unitPrice = BigDecimal.ZERO.compareTo(this.taxIncludedUnitPrice) != 0 ? this.taxIncludedUnitPrice.divide(BigDecimal.ONE.add(this.taxRate.divide(new BigDecimal("100"))), defaultPrecision, RoundingMode.HALF_UP) : BigDecimal.ZERO;
                this.lineAmount = this.unitPrice.multiply(this.quantity).divide(priceBranch, financialPrecision, RoundingMode.HALF_UP);
            }
        } else {
            this.taxIncludedLineAmount = BigDecimal.ZERO;
            this.lineAmount = BigDecimal.ZERO;
        }
    }

    public void countLineAmountForSRM(int defaultPrecision, int financialPrecision) {
        if (this.taxIncludedUnitPrice != null && this.quantity != null) {
            BigDecimal priceBranch = this.unitPriceBatch == null ? BigDecimal.ONE : this.unitPriceBatch;
            this.taxIncludedUnitPrice = this.taxIncludedUnitPrice.setScale(defaultPrecision, RoundingMode.HALF_UP);
            this.taxIncludedLineAmount = this.taxIncludedUnitPrice.divide(priceBranch, 10, RoundingMode.HALF_UP).multiply(this.quantity).setScale(financialPrecision, RoundingMode.HALF_UP);
            this.taxWithoutFreightPrice = BigDecimal.ZERO.equals(this.quantity) ? this.taxIncludedUnitPrice : this.taxIncludedLineAmount.subtract((BigDecimal)Optional.ofNullable(this.lineFreight).orElse(BigDecimal.ZERO)).divide(this.quantity, defaultPrecision, RoundingMode.HALF_UP);
            if (this.taxRate == null) {
                this.unitPrice = this.taxWithoutFreightPrice;
                this.lineAmount = this.taxIncludedLineAmount;
            } else {
                this.unitPrice = BigDecimal.ZERO.compareTo(this.taxIncludedUnitPrice) != 0 ? this.taxIncludedUnitPrice.divide(BigDecimal.ONE.add(this.taxRate.divide(new BigDecimal("100"))), defaultPrecision, RoundingMode.HALF_UP) : BigDecimal.ZERO;
                this.lineAmount = this.unitPrice.multiply(this.quantity).divide(priceBranch, financialPrecision, RoundingMode.HALF_UP);
            }
        } else {
            this.taxIncludedLineAmount = BigDecimal.ZERO;
            this.lineAmount = BigDecimal.ZERO;
        }
    }

    public void calculateLocalMoney(int defaultPrecision, int financialPrecision) {
        if (this.exchangeRate != null && this.taxIncludedUnitPrice != null) {
            this.localCurrencyTaxUnit = this.taxIncludedUnitPrice.multiply(this.exchangeRate).setScale(defaultPrecision, RoundingMode.HALF_UP);
            this.localCurrencyNoTaxUnit = this.unitPrice.multiply(this.exchangeRate).setScale(defaultPrecision, RoundingMode.HALF_UP);
            this.localCurrencyTaxSum = this.taxIncludedLineAmount.multiply(this.exchangeRate).setScale(financialPrecision, RoundingMode.HALF_UP);
            this.localCurrencyNoTaxSum = this.lineAmount.multiply(this.exchangeRate).setScale(financialPrecision, RoundingMode.HALF_UP);
        } else {
            this.localCurrencyTaxUnit = BigDecimal.ZERO;
            this.localCurrencyNoTaxUnit = BigDecimal.ZERO;
            this.localCurrencyTaxSum = BigDecimal.ZERO;
            this.localCurrencyNoTaxSum = BigDecimal.ZERO;
        }
    }

    public void emptyFromPoLine() {
        this.executionStatusCode = null;
        this.executedDate = null;
        this.executedBy = null;
        this.executionBillId = null;
        this.executionBillNum = null;
        this.executionHeaderBillId = null;
        this.executionHeaderBillNum = null;
    }

    public void updateCancelInfo(String displayPrNum) {
        if (!this.cancellable()) {
            throw new CommonException("error.pr.not_cancelable_line_exists", new Object[]{displayPrNum, this.displayLineNum});
        } else {
            this.setCancelledFlag(Flag.YES);
            this.setCancelledDate(new Date());
            this.setCancelledBy(DetailsHelper.getUserDetails().getUserId());
        }
    }

    public void updateCancelInfoEc(String displayPrNum) {
        this.setCancelledFlag(Flag.YES);
        this.setCancelledDate(new Date());
        this.setCancelledBy(DetailsHelper.getUserDetails().getUserId());
    }

    public boolean cancellable() {
        return Flag.NO.equals(this.closedFlag) && Flag.NO.equals(this.cancelledFlag) && (null == this.executionStatusCode || "UNASSIGNED".equals(this.executionStatusCode) || "ASSIGNED".equals(this.executionStatusCode));
    }

    public void referValidate(PrLine validLine) {
        Assert.isTrue(Flag.NO.equals(this.cancelledFlag), "error.pr.line.canceld.flag.should.zero");
        Assert.isTrue(Flag.NO.equals(this.closedFlag), "error.pr.line.closed.flag.should.zero");
        Assert.isTrue(Objects.equals(this.supplierId, validLine.getSupplierId()) && Objects.equals(this.supplierCompanyId, validLine.getSupplierCompanyId()), "error.pr.line.supplierid.not.same");
    }

    public void referInitFeild(PrHeader prHeader) {
        this.displayPrLineNum = this.displayLineNum;
        this.prRequestedName = prHeader.getPrRequestedName();
        this.requestedBy = prHeader.getRequestedBy();
        this.displayPrNum = prHeader.getDisplayPrNum();
        this.unitPriceBatch = new BigDecimal(1);
    }

    public PrLine convertPriceByPer() {
        if (this.unitPrice == null) {
            return this;
        } else {
            this.convertUnitPrice = new BigDecimal(this.unitPrice.setScale(6, RoundingMode.DOWN).stripTrailingZeros().toPlainString());
            int scale = this.convertUnitPrice.scale();

            for(this.per = 1; scale > 2 && this.per < 10000; scale = this.convertUnitPrice.scale()) {
                this.per = this.per * 10;
                this.convertUnitPrice = new BigDecimal(this.convertUnitPrice.multiply(new BigDecimal(10)).stripTrailingZeros().toPlainString());
            }

            return this;
        }
    }

    public PrLine stripBigDecimalTrailingZeros() {
        if (this.quantity != null) {
            this.quantity = this.quantity.stripTrailingZeros();
        }

        if (this.taxRate != null) {
            this.taxRate = this.taxRate.stripTrailingZeros();
        }

        if (this.unitPrice != null) {
            this.unitPrice = this.unitPrice.stripTrailingZeros();
        }

        if (this.taxIncludedUnitPrice != null) {
            this.taxIncludedUnitPrice = this.taxIncludedUnitPrice.stripTrailingZeros();
        }

        if (this.lineAmount != null) {
            this.lineAmount = this.lineAmount.stripTrailingZeros();
        }

        if (this.taxIncludedLineAmount != null) {
            this.taxIncludedLineAmount = this.taxIncludedLineAmount.stripTrailingZeros();
        }

        if (this.enteredTaxIncludedPrice != null) {
            this.enteredTaxIncludedPrice = this.enteredTaxIncludedPrice.stripTrailingZeros();
        }

        if (this.amount != null) {
            this.amount = this.amount.stripTrailingZeros();
        }

        if (this.convertUnitPrice != null) {
            this.convertUnitPrice = this.convertUnitPrice.stripTrailingZeros();
        }

        if (this.unitPriceBatch != null) {
            this.unitPriceBatch = this.unitPriceBatch.stripTrailingZeros();
        }

        if (this.jdPrice != null) {
            this.jdPrice = this.unitPriceBatch.stripTrailingZeros();
        }

        return this;
    }

    public void cleanExecutionStatusLine() {
        this.closedFlag = Flag.NO;
        this.closedDate = null;
        this.closedBy = null;
        this.executionStatusCode = null;
        this.executedDate = null;
        this.executedBy = null;
        this.executionBillId = null;
        this.executionBillNum = null;
    }

    public void turnOffThePendingSign() {
        if (!Flag.NO.equals(this.suspendFlag)) {
            throw new CommonException("error.pr.line.suspend.flag.mistake", new Object[]{this.displayLineNum});
        } else if (!Flag.YES.equals(this.closedFlag)) {
            throw new CommonException("error.pr.line.closed.flag.mistake", new Object[]{this.displayLineNum});
        }
    }

    public void addHeaderField(PrHeader prHeader) {
        this.companyId = prHeader.getCompanyId();
        this.ouId = prHeader.getOuId();
        this.purchaseOrgId = prHeader.getPurchaseOrgId();
        if (null != prHeader.getPurchaseAgentId()) {
            this.purchaseAgentId = prHeader.getPurchaseAgentId();
        }

    }

    public String getDocumentId() {
        return this.documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public Boolean getReferencePriceDisplayFlag() {
        return this.referencePriceDisplayFlag;
    }

    public void setReferencePriceDisplayFlag(Boolean referencePriceDisplayFlag) {
        this.referencePriceDisplayFlag = referencePriceDisplayFlag;
    }

    public BigDecimal getLocalCurrencyNoTaxSum() {
        return this.localCurrencyNoTaxSum;
    }

    public void setLocalCurrencyNoTaxSum(BigDecimal localCurrencyNoTaxSum) {
        this.localCurrencyNoTaxSum = localCurrencyNoTaxSum;
    }

    public BigDecimal getLocalCurrencyTaxSum() {
        return this.localCurrencyTaxSum;
    }

    public void setLocalCurrencyTaxSum(BigDecimal localCurrencyTaxSum) {
        this.localCurrencyTaxSum = localCurrencyTaxSum;
    }

    public BigDecimal getLocalCurrencyNoTaxUnit() {
        return this.localCurrencyNoTaxUnit;
    }

    public void setLocalCurrencyNoTaxUnit(BigDecimal localCurrencyNoTaxUnit) {
        this.localCurrencyNoTaxUnit = localCurrencyNoTaxUnit;
    }

    public BigDecimal getLocalCurrencyTaxUnit() {
        return this.localCurrencyTaxUnit;
    }

    public void setLocalCurrencyTaxUnit(BigDecimal localCurrencyTaxUnit) {
        this.localCurrencyTaxUnit = localCurrencyTaxUnit;
    }

    public BigDecimal getExchangeRate() {
        return this.exchangeRate;
    }

    public void setExchangeRate(BigDecimal exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public Date getExchangeRateDate() {
        return this.exchangeRateDate;
    }

    public void setExchangeRateDate(Date exchangeRateDate) {
        this.exchangeRateDate = exchangeRateDate;
    }

    public BigDecimal getBudgetAccountPrice() {
        return this.budgetAccountPrice;
    }

    public void setBudgetAccountPrice(BigDecimal budgetAccountPrice) {
        this.budgetAccountPrice = budgetAccountPrice;
    }

    public static String getFieldPrHeaderId() {
        return "prHeaderId";
    }

    public String getBudgetAccountDeptno() {
        return this.budgetAccountDeptno;
    }

    public void setBudgetAccountDeptno(String budgetAccountDeptno) {
        this.budgetAccountDeptno = budgetAccountDeptno;
    }

    public String getChangeOrderCode() {
        return this.changeOrderCode;
    }

    public void setChangeOrderCode(String changeOrderCode) {
        this.changeOrderCode = changeOrderCode;
    }

    public String getChangeOrderMessage() {
        return this.changeOrderMessage;
    }

    public void setChangeOrderMessage(String changeOrderMessage) {
        this.changeOrderMessage = changeOrderMessage;
    }

    public String getBenchmarkPriceType() {
        return this.benchmarkPriceType;
    }

    public void setBenchmarkPriceType(String benchmarkPriceType) {
        this.benchmarkPriceType = benchmarkPriceType;
    }

    public Long getPurchaseHerderAgentId() {
        return this.purchaseHerderAgentId;
    }

    public void setPurchaseHerderAgentId(Long purchaseHerderAgentId) {
        this.purchaseHerderAgentId = purchaseHerderAgentId;
    }

    public Integer getLadderQuotationFlag() {
        return this.ladderQuotationFlag;
    }

    public void setLadderQuotationFlag(Integer ladderQuotationFlag) {
        this.ladderQuotationFlag = ladderQuotationFlag;
    }

    public Integer getPrToPoAutoFlag() {
        return this.prToPoAutoFlag;
    }

    public void setPrToPoAutoFlag(Integer prToPoAutoFlag) {
        this.prToPoAutoFlag = prToPoAutoFlag;
    }

    public Long getHoldPcHeaderId() {
        return this.holdPcHeaderId;
    }

    public void setHoldPcHeaderId(Long holdPcHeaderId) {
        this.holdPcHeaderId = holdPcHeaderId;
    }

    public Long getHoldPcLineId() {
        return this.holdPcLineId;
    }

    public void setHoldPcLineId(Long holdPcLineId) {
        this.holdPcLineId = holdPcLineId;
    }

    public String getContractNum() {
        return this.contractNum;
    }

    public void setContractNum(String contractNum) {
        this.contractNum = contractNum;
    }

    public List<Long> getAssignUserIds() {
        return this.assignUserIds;
    }

    public void setAssignUserIds(List<Long> assignUserIds) {
        this.assignUserIds = assignUserIds;
    }

    public String toString() {
        return "PrLine{prLineId=" + this.prLineId + ", prHeaderId=" + this.prHeaderId + ", tenantId=" + this.tenantId + ", lineNum=" + this.lineNum + ", displayLineNum='" + this.displayLineNum + '\'' + ", companyId=" + this.companyId + ", invOrganizationId=" + this.invOrganizationId + ", inventoryId=" + this.inventoryId + ", itemId=" + this.itemId + ", itemCode='" + this.itemCode + '\'' + ", itemName='" + this.itemName + '\'' + ", itemAbcClass='" + this.itemAbcClass + '\'' + ", drawingNum='" + this.drawingNum + '\'' + ", projectNum='" + this.projectNum + '\'' + ", projectName='" + this.projectName + '\'' + ", craneNum='" + this.craneNum + '\'' + ", productId=" + this.productId + ", productNum='" + this.productNum + '\'' + ", productName='" + this.productName + '\'' + ", catalogId=" + this.catalogId + ", catalogName='" + this.catalogName + '\'' + ", categoryId=" + this.categoryId + ", uomId=" + this.uomId + ", quantity=" + this.quantity + ", taxId=" + this.taxId + ", taxRate=" + this.taxRate + ", currencyCode='" + this.currencyCode + '\'' + ", unitPrice=" + this.unitPrice + ", taxIncludedUnitPrice=" + this.taxIncludedUnitPrice + ", lineAmount=" + this.lineAmount + ", taxIncludedLineAmount=" + this.taxIncludedLineAmount + ", neededDate=" + this.neededDate + ", supplierTenantId=" + this.supplierTenantId + ", supplierId=" + this.supplierId + ", supplierCode='" + this.supplierCode + '\'' + ", supplierName='" + this.supplierName + '\'' + ", supplierCompanyId=" + this.supplierCompanyId + ", supplierCompanyName='" + this.supplierCompanyName + '\'' + ", executionStatusCode='" + this.executionStatusCode + '\'' + ", executedDate=" + this.executedDate + ", executedBy=" + this.executedBy + ", executionBillId=" + this.executionBillId + ", executionBillNum='" + this.executionBillNum + '\'' + ", executionBillData='" + this.executionBillData + '\'' + ", assignedFlag=" + this.assignedFlag + ", assignedDate=" + this.assignedDate + ", assignedBy=" + this.assignedBy + ", assignedRemark='" + this.assignedRemark + '\'' + ", cancelledFlag=" + this.cancelledFlag + ", cancelledDate=" + this.cancelledDate + ", cancelledBy=" + this.cancelledBy + ", cancelledRemark='" + this.cancelledRemark + '\'' + ", closedFlag=" + this.closedFlag + ", closedDate=" + this.closedDate + ", closedBy=" + this.closedBy + ", closedRemark='" + this.closedRemark + '\'' + ", suspendFlag=" + this.suspendFlag + ", suspendDate=" + this.suspendDate + ", suspendRemark='" + this.suspendRemark + '\'' + ", incorrectFlag=" + this.incorrectFlag + ", incorrectDate=" + this.incorrectDate + ", incorrectMsg='" + this.incorrectMsg + '\'' + ", attachmentUuid='" + this.attachmentUuid + '\'' + ", remark='" + this.remark + '\'' + ", canVatFlag=" + this.canVatFlag + ", lineFreight=" + this.lineFreight + ", executionHeaderBillId=" + this.executionHeaderBillId + ", executionHeaderBillNum='" + this.executionHeaderBillNum + '\'' + ", requestDate=" + this.requestDate + ", requestedBy=" + this.requestedBy + ", prRequestedName='" + this.prRequestedName + '\'' + ", ouId=" + this.ouId + ", purchaseOrgId=" + this.purchaseOrgId + ", purchaseAgentId=" + this.purchaseAgentId + ", erpEditStatus='" + this.erpEditStatus + '\'' + ", objectVersionNumber=" + this.objectVersionNumber + ", urgentFlag=" + this.urgentFlag + ", urgentDate=" + this.urgentDate + ", costId=" + this.costId + ", costCode='" + this.costCode + '\'' + ", accountSubjectId=" + this.accountSubjectId + ", accountSubjectNum='" + this.accountSubjectNum + '\'' + ", wbs='" + this.wbs + '\'' + ", jdPrice=" + this.jdPrice + ", pcHeaderId=" + this.pcHeaderId + ", occupiedQuantity=" + this.occupiedQuantity + ", poLineId=" + this.poLineId + ", lastPurchasePrice=" + this.lastPurchasePrice + ", itemModel='" + this.itemModel + '\'' + ", itemSpecs='" + this.itemSpecs + '\'' + ", itemProperties='" + this.itemProperties + '\'' + ", agentId=" + this.agentId + ", keeperUserId=" + this.keeperUserId + ", accepterUserId=" + this.accepterUserId + ", costPayerUserId=" + this.costPayerUserId + ", address='" + this.address + '\'' + ", innerPoNum='" + this.innerPoNum + '\'' + ", drawingVersion='" + this.drawingVersion + '\'' + ", supplierItemCode='" + this.supplierItemCode + '\'' + ", batchNo='" + this.batchNo + '\'' + ", expBearDepId='" + this.expBearDepId + '\'' + ", supplierItemName='" + this.supplierItemName + '\'' + ", surfaceTreatFlag=" + this.surfaceTreatFlag + ", benchmarkPrice=" + this.benchmarkPrice + ", changePercent=" + this.changePercent + ", unitPriceBatch=" + this.unitPriceBatch + ", projectCategory='" + this.projectCategory + '\'' + ", assets='" + this.assets + '\'' + ", assetChildNum='" + this.assetChildNum + '\'' + ", taxWithoutFreightPrice=" + this.taxWithoutFreightPrice + ", frameAgreementNum='" + this.frameAgreementNum + '\'' + ", wbsCode='" + this.wbsCode + '\'' + ", accountAssignTypeId=" + this.accountAssignTypeId + ", freightLineFlag=" + this.freightLineFlag + ", qualityStandard='" + this.qualityStandard + '\'' + ", budgetIoFlag=" + this.budgetIoFlag + ", taxIncludedBudgetUnitPrice=" + this.taxIncludedBudgetUnitPrice + ", executionStrategyCode='" + this.executionStrategyCode + '\'' + ", budgetAccountId=" + this.budgetAccountId + ", receiverInformation='" + this.receiverInformation + '\'' + ", businessCardFlag=" + this.businessCardFlag + ", oldCancelledFlag=" + this.oldCancelledFlag + ", unitFlag=" + this.unitFlag + ", executionStatusMeaning='" + this.executionStatusMeaning + '\'' + ", primaryUomName='" + this.primaryUomName + '\'' + ", orderUomName='" + this.orderUomName + '\'' + ", enteredTaxIncludedPrice=" + this.enteredTaxIncludedPrice + ", amount=" + this.amount + ", companyName='" + this.companyName + '\'' + ", invOrganizationName='" + this.invOrganizationName + '\'' + ", per=" + this.per + ", convertUnitPrice=" + this.convertUnitPrice + ", hObjectVersionNumber=" + this.hObjectVersionNumber + ", prNum='" + this.prNum + '\'' + ", displayPrNum='" + this.displayPrNum + '\'' + ", displayPrLineNum='" + this.displayPrLineNum + '\'' + ", prSourcePlatform='" + this.prSourcePlatform + '\'' + ", externalSystemCode='" + this.externalSystemCode + '\'' + ", esPrHeaderId='" + this.esPrHeaderId + '\'' + ", esPrNumber='" + this.esPrNumber + '\'' + ", esPrLineId='" + this.esPrLineId + '\'' + ", esPrLineNum='" + this.esPrLineNum + '\'' + ", dataVersion=" + this.dataVersion + ", orderTypeId=" + this.orderTypeId + ", poNumList=" + this.poNumList + ", asnLineIdList=" + this.asnLineIdList + ", dynamicLineAmount=" + this.dynamicLineAmount + ", executionStrategyMeaning='" + this.executionStrategyMeaning + '\'' + ", inventoryName='" + this.inventoryName + '\'' + ", categoryName='" + this.categoryName + '\'' + ", uomName='" + this.uomName + '\'' + ", costName='" + this.costName + '\'' + ", accountSubjectName='" + this.accountSubjectName + '\'' + ", expBearDepCode='" + this.expBearDepCode + '\'' + ", expBearDep='" + this.expBearDep + '\'' + ", occupyFlag=" + this.occupyFlag + ", oldQuantity=" + this.oldQuantity + ", oldPrLineId='" + this.oldPrLineId + '\'' + ", changeSubmitFlag=" + this.changeSubmitFlag + ", unitId=" + this.unitId + ", unitName='" + this.unitName + '\'' + ", receiverAddressId=" + this.receiverAddressId + ", receiverAddress='" + this.receiverAddress + '\'' + ", invoiceAddress='" + this.invoiceAddress + '\'' + ", restPoQuantity=" + this.restPoQuantity + ", thisOrderQuantity=" + this.thisOrderQuantity + ", projectCategoryMeaning='" + this.projectCategoryMeaning + '\'' + ", selectSupplierCompanyId=" + this.selectSupplierCompanyId + ", selectSupplierTenantId=" + this.selectSupplierTenantId + ", selectSupplierCode='" + this.selectSupplierCode + '\'' + ", selectSupplierCompanyName='" + this.selectSupplierCompanyName + '\'' + ", prTypeId=" + this.prTypeId + ", changeOrderCode=" + this.changeOrderCode + ", changeOrderMessage=" + this.changeOrderMessage + ", budgetAccountDeptno=" + this.budgetAccountDeptno + ", budAccountPrice=" + this.budgetAccountPrice + ", exchangeRate=" + this.exchangeRate + ", localCurrencyTaxUnit=" + this.localCurrencyTaxUnit + ", localCurrencyNoTaxUnit=" + this.localCurrencyNoTaxUnit + ", localCurrencyTaxSum=" + this.localCurrencyTaxSum + ", localCurrencyNoTaxSum=" + this.localCurrencyNoTaxSum + ", exchangeRateDate=" + this.exchangeRateDate + '}';
    }

    public Long getPriceLibraryId() {
        return this.priceLibraryId;
    }

    public void setPriceLibraryId(Long priceLibraryId) {
        this.priceLibraryId = priceLibraryId;
    }

    public String getSelectSupplierCode() {
        return this.selectSupplierCode;
    }

    public void setSelectSupplierCode(String selectSupplierCode) {
        this.selectSupplierCode = selectSupplierCode;
    }

    public String getSelectSupplierCompanyName() {
        return this.selectSupplierCompanyName;
    }

    public void setSelectSupplierCompanyName(String selectSupplierCompanyName) {
        this.selectSupplierCompanyName = selectSupplierCompanyName;
    }

    public Long getSelectSupplierCompanyId() {
        return this.selectSupplierCompanyId;
    }

    public void setSelectSupplierCompanyId(Long selectSupplierCompanyId) {
        this.selectSupplierCompanyId = selectSupplierCompanyId;
    }

    public Long getSelectSupplierTenantId() {
        return this.selectSupplierTenantId;
    }

    public void setSelectSupplierTenantId(Long selectSupplierTenantId) {
        this.selectSupplierTenantId = selectSupplierTenantId;
    }

    public BigDecimal getDynamicLineAmount() {
        return this.dynamicLineAmount;
    }

    public void setDynamicLineAmount(BigDecimal dynamicLineAmount) {
        this.dynamicLineAmount = dynamicLineAmount;
    }

    public List<String> getPoNumList() {
        return this.poNumList;
    }

    public void setPoNumList(List<String> poNumList) {
        this.poNumList = poNumList;
    }

    public List<PrLineSupplier> getSupplierList() {
        return this.supplierList;
    }

    public void setSupplierList(List<PrLineSupplier> supplierList) {
        this.supplierList = supplierList;
    }

    public List<Long> getAsnLineIdList() {
        return this.asnLineIdList;
    }

    public void setAsnLineIdList(List<Long> asnLineIdList) {
        this.asnLineIdList = asnLineIdList;
    }

    public Long getOrderTypeId() {
        return this.orderTypeId;
    }

    public void setOrderTypeId(Long orderTypeId) {
        this.orderTypeId = orderTypeId;
    }

    public String getFrameAgreementNum() {
        return this.frameAgreementNum;
    }

    public void setFrameAgreementNum(String frameAgreementNum) {
        this.frameAgreementNum = frameAgreementNum;
    }

    public String getExpBearDepId() {
        return this.expBearDepId;
    }

    public void setExpBearDepId(String expBearDepId) {
        this.expBearDepId = expBearDepId;
    }

    public String getSupplierItemName() {
        return this.supplierItemName;
    }

    public void setSupplierItemName(String supplierItemName) {
        this.supplierItemName = supplierItemName;
    }

    public String getBatchNo() {
        return this.batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getDrawingVersion() {
        return this.drawingVersion;
    }

    public void setDrawingVersion(String drawingVersion) {
        this.drawingVersion = drawingVersion;
    }

    public String getSupplierItemCode() {
        return this.supplierItemCode;
    }

    public void setSupplierItemCode(String supplierItemCode) {
        this.supplierItemCode = supplierItemCode;
    }

    public String getItemModel() {
        return this.itemModel;
    }

    public void setItemModel(String itemModel) {
        this.itemModel = itemModel;
    }

    public String getItemSpecs() {
        return this.itemSpecs;
    }

    public void setItemSpecs(String itemSpecs) {
        this.itemSpecs = itemSpecs;
    }

    public String getItemProperties() {
        return this.itemProperties;
    }

    public void setItemProperties(String itemProperties) {
        this.itemProperties = itemProperties;
    }

    public Long getKeeperUserId() {
        return this.keeperUserId;
    }

    public void setKeeperUserId(Long keeperUserId) {
        this.keeperUserId = keeperUserId;
    }

    public Long getAccepterUserId() {
        return this.accepterUserId;
    }

    public void setAccepterUserId(Long accepterUserId) {
        this.accepterUserId = accepterUserId;
    }

    public Long getCostPayerUserId() {
        return this.costPayerUserId;
    }

    public void setCostPayerUserId(Long costPayerUserId) {
        this.costPayerUserId = costPayerUserId;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getInnerPoNum() {
        return this.innerPoNum;
    }

    public void setInnerPoNum(String innerPoNum) {
        this.innerPoNum = innerPoNum;
    }

    public Long getPcHeaderId() {
        return this.pcHeaderId;
    }

    public void setPcHeaderId(Long pcHeaderId) {
        this.pcHeaderId = pcHeaderId;
    }

    public Integer getSurfaceTreatFlag() {
        return this.surfaceTreatFlag;
    }

    public void setSurfaceTreatFlag(Integer surfaceTreatFlag) {
        this.surfaceTreatFlag = surfaceTreatFlag;
    }

    public BigDecimal getBenchmarkPrice() {
        return this.benchmarkPrice;
    }

    public void setBenchmarkPrice(BigDecimal benchmarkPrice) {
        this.benchmarkPrice = benchmarkPrice;
    }

    public BigDecimal getChangePercent() {
        return this.changePercent;
    }

    public void setChangePercent(BigDecimal changePercent) {
        this.changePercent = changePercent;
    }

    public String getProjectCategory() {
        return this.projectCategory;
    }

    public void setProjectCategory(String projectCategory) {
        this.projectCategory = projectCategory;
    }

    public String getAssets() {
        return this.assets;
    }

    public void setAssets(String assets) {
        this.assets = assets;
    }

    public String getAssetChildNum() {
        return this.assetChildNum;
    }

    public void setAssetChildNum(String assetChildNum) {
        this.assetChildNum = assetChildNum;
    }

    public String getProjectCategoryMeaning() {
        return this.projectCategoryMeaning;
    }

    public void setProjectCategoryMeaning(String projectCategoryMeaning) {
        this.projectCategoryMeaning = projectCategoryMeaning;
    }

    public Long getPoLineId() {
        return this.poLineId;
    }

    public void setPoLineId(Long poLineId) {
        this.poLineId = poLineId;
    }

    public BigDecimal getLastPurchasePrice() {
        return this.lastPurchasePrice;
    }

    public void setLastPurchasePrice(BigDecimal lastPurchasePrice) {
        this.lastPurchasePrice = lastPurchasePrice;
    }

    public static String getFieldPrLineId() {
        return "prLineId";
    }

    public Long getCostId() {
        return this.costId;
    }

    public void setCostId(Long costId) {
        this.costId = costId;
    }

    public String getCostCode() {
        return this.costCode;
    }

    public void setCostCode(String costCode) {
        this.costCode = costCode;
    }

    public Long getAccountSubjectId() {
        return this.accountSubjectId;
    }

    public void setAccountSubjectId(Long accountSubjectId) {
        this.accountSubjectId = accountSubjectId;
    }

    public String getAccountSubjectNum() {
        return this.accountSubjectNum;
    }

    public void setAccountSubjectNum(String accountSubjectNum) {
        this.accountSubjectNum = accountSubjectNum;
    }

    public String getWbs() {
        return this.wbs;
    }

    public void setWbs(String wbs) {
        this.wbs = wbs;
    }

    public String getWbsCode() {
        return this.wbsCode;
    }

    public void setWbsCode(String wbsCode) {
        this.wbsCode = wbsCode;
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

    public String getReceiverAddress() {
        return this.receiverAddress;
    }

    public void setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress;
    }

    public String getInvoiceAddress() {
        return this.invoiceAddress;
    }

    public void setInvoiceAddress(String invoiceAddress) {
        this.invoiceAddress = invoiceAddress;
    }

    public void setUrgentDate(Date urgentDate) {
        this.urgentDate = urgentDate;
    }

    public Long getReceiverAddressId() {
        return this.receiverAddressId;
    }

    public void setReceiverAddressId(Long receiverAddressId) {
        this.receiverAddressId = receiverAddressId;
    }

    public String getItemAbcClass() {
        return this.itemAbcClass;
    }

    public void setItemAbcClass(String itemAbcClass) {
        this.itemAbcClass = itemAbcClass;
    }

    public String getDrawingNum() {
        return this.drawingNum;
    }

    public void setDrawingNum(String drawingNum) {
        this.drawingNum = drawingNum;
    }

    public String getProjectNum() {
        return this.projectNum;
    }

    public void setProjectNum(String projectNum) {
        this.projectNum = projectNum;
    }

    public String getProjectName() {
        return this.projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getCraneNum() {
        return this.craneNum;
    }

    public void setCraneNum(String craneNum) {
        this.craneNum = craneNum;
    }

    public Integer getOldCancelledFlag() {
        return this.oldCancelledFlag;
    }

    public void setOldCancelledFlag(Integer oldCancelledFlag) {
        this.oldCancelledFlag = oldCancelledFlag;
    }

    public String getErpEditStatus() {
        return this.erpEditStatus;
    }

    public void setErpEditStatus(String erpEditStatus) {
        this.erpEditStatus = erpEditStatus;
    }

    public String getExecutionStatusMeaning() {
        return this.executionStatusMeaning;
    }

    public void setExecutionStatusMeaning(String executionStatusMeaning) {
        this.executionStatusMeaning = executionStatusMeaning;
    }

    public Date getRequestDate() {
        return this.requestDate;
    }

    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }

    public Long getPurchaseAgentId() {
        return this.purchaseAgentId;
    }

    public void setPurchaseAgentId(Long purchaseAgentId) {
        this.purchaseAgentId = purchaseAgentId;
    }

    public Long getInventoryId() {
        return this.inventoryId;
    }

    public void setInventoryId(Long inventoryId) {
        this.inventoryId = inventoryId;
    }

    public String getExternalSystemCode() {
        return this.externalSystemCode;
    }

    public void setExternalSystemCode(String externalSystemCode) {
        this.externalSystemCode = externalSystemCode;
    }

    public String getEsPrHeaderId() {
        return this.esPrHeaderId;
    }

    public void setEsPrHeaderId(String esPrHeaderId) {
        this.esPrHeaderId = esPrHeaderId;
    }

    public String getEsPrNumber() {
        return this.esPrNumber;
    }

    public void setEsPrNumber(String esPrNumber) {
        this.esPrNumber = esPrNumber;
    }

    public String getEsPrLineId() {
        return this.esPrLineId;
    }

    public void setEsPrLineId(String esPrLineId) {
        this.esPrLineId = esPrLineId;
    }

    public String getEsPrLineNum() {
        return this.esPrLineNum;
    }

    public void setEsPrLineNum(String esPrLineNum) {
        this.esPrLineNum = esPrLineNum;
    }

    public Long getDataVersion() {
        return this.dataVersion;
    }

    public void setDataVersion(Long dataVersion) {
        this.dataVersion = dataVersion;
    }

    public String getPrSourcePlatform() {
        return this.prSourcePlatform;
    }

    public void setPrSourcePlatform(String prSourcePlatform) {
        this.prSourcePlatform = prSourcePlatform;
    }

    public BigDecimal getLineFreight() {
        return this.lineFreight;
    }

    public void setLineFreight(BigDecimal lineFreight) {
        this.lineFreight = lineFreight;
    }

    public Long getExecutionHeaderBillId() {
        return this.executionHeaderBillId;
    }

    public void setExecutionHeaderBillId(Long executionHeaderBillId) {
        this.executionHeaderBillId = executionHeaderBillId;
    }

    public String getExecutionHeaderBillNum() {
        return this.executionHeaderBillNum;
    }

    public void setExecutionHeaderBillNum(String executionHeaderBillNum) {
        this.executionHeaderBillNum = executionHeaderBillNum;
    }

    public Long getUnitId() {
        return this.unitId;
    }

    public void setUnitId(Long unitId) {
        this.unitId = unitId;
    }

    public String getUnitName() {
        return this.unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public BigDecimal getUnitPriceBatch() {
        return this.unitPriceBatch;
    }

    public void setUnitPriceBatch(BigDecimal unitPriceBatch) {
        this.unitPriceBatch = unitPriceBatch;
    }

    public String getDisplayPrNum() {
        return this.displayPrNum;
    }

    public void setDisplayPrNum(String displayPrNum) {
        this.displayPrNum = displayPrNum;
    }

    public Integer getPurchaseHerderOrgId() {
        return this.purchaseHerderOrgId;
    }

    public void setPurchaseHerderOrgId(Integer purchaseHerderOrgId) {
        this.purchaseHerderOrgId = purchaseHerderOrgId;
    }

    public String getDisplayPrLineNum() {
        return this.displayPrLineNum;
    }

    public void setDisplayPrLineNum(String displayPrLineNum) {
        this.displayPrLineNum = displayPrLineNum;
    }

    public Long getRequestedBy() {
        return this.requestedBy;
    }

    public void setRequestedBy(Long requestedBy) {
        this.requestedBy = requestedBy;
    }

    public String getPrRequestedName() {
        return this.prRequestedName;
    }

    public void setPrRequestedName(String prRequestedName) {
        this.prRequestedName = prRequestedName;
    }

    public String getPrNum() {
        return this.prNum;
    }

    public void setPrNum(String prNum) {
        this.prNum = prNum;
    }

    public Long gethObjectVersionNumber() {
        return this.hObjectVersionNumber;
    }

    public void sethObjectVersionNumber(Long hObjectVersionNumber) {
        this.hObjectVersionNumber = hObjectVersionNumber;
    }

    public BigDecimal getEnteredTaxIncludedPrice() {
        return this.enteredTaxIncludedPrice;
    }

    public void setEnteredTaxIncludedPrice(BigDecimal enteredTaxIncludedPrice) {
        this.enteredTaxIncludedPrice = enteredTaxIncludedPrice;
    }

    public Long getPurchaseOrgId() {
        return this.purchaseOrgId;
    }

    public void setPurchaseOrgId(Long purchaseOrgId) {
        this.purchaseOrgId = purchaseOrgId;
    }

    public BigDecimal getAmount() {
        return this.amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Long getAgentId() {
        return this.agentId;
    }

    public void setAgentId(Long agentId) {
        this.agentId = agentId;
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

    public Long getPrLineId() {
        return this.prLineId;
    }

    public void setPrLineId(Long prLineId) {
        this.prLineId = prLineId;
    }

    public Long getPrHeaderId() {
        return this.prHeaderId;
    }

    public void setPrHeaderId(Long prHeaderId) {
        this.prHeaderId = prHeaderId;
    }

    public Long getTenantId() {
        return this.tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public Long getLineNum() {
        return this.lineNum;
    }

    public void setLineNum(Long lineNum) {
        this.lineNum = lineNum;
    }

    public String getDisplayLineNum() {
        return this.displayLineNum;
    }

    public void setDisplayLineNum(String displayLineNum) {
        this.displayLineNum = displayLineNum;
    }

    public Long getCompanyId() {
        return this.companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getInvOrganizationId() {
        return this.invOrganizationId;
    }

    public void setInvOrganizationId(Long invOrganizationId) {
        this.invOrganizationId = invOrganizationId;
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

    public Long getOuHeaderId() {
        return this.ouHeaderId;
    }

    public void setOuHeaderId(Long ouHeaderId) {
        this.ouHeaderId = ouHeaderId;
    }

    public List<PrHeader> getPrHeaderAssignList() {
        return this.prHeaderAssignList;
    }

    public void setPrHeaderAssignList(List<PrHeader> prHeaderAssignList) {
        this.prHeaderAssignList = prHeaderAssignList;
    }

    public String getItemName() {
        return this.itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Long getProductId() {
        return this.productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
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

    public Long getCatalogId() {
        return this.catalogId;
    }

    public void setCatalogId(Long catalogId) {
        this.catalogId = catalogId;
    }

    public String getCatalogName() {
        return this.catalogName;
    }

    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
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

    public BigDecimal getQuantity() {
        return this.quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public Long getTaxId() {
        return this.taxId;
    }

    public void setTaxId(Long taxId) {
        this.taxId = taxId;
    }

    public BigDecimal getTaxRate() {
        return this.taxRate;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    public String getCurrencyCode() {
        return this.currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public BigDecimal getTaxIncludedUnitPrice() {
        return this.taxIncludedUnitPrice;
    }

    public void setTaxIncludedUnitPrice(BigDecimal taxIncludedUnitPrice) {
        this.taxIncludedUnitPrice = taxIncludedUnitPrice;
    }

    public BigDecimal getLineAmount() {
        return this.lineAmount;
    }

    public void setLineAmount(BigDecimal lineAmount) {
        this.lineAmount = lineAmount;
    }

    public BigDecimal getTaxIncludedLineAmount() {
        return this.taxIncludedLineAmount;
    }

    public void setTaxIncludedLineAmount(BigDecimal taxIncludedLineAmount) {
        this.taxIncludedLineAmount = taxIncludedLineAmount;
    }

    public Date getNeededDate() {
        return this.neededDate;
    }

    public void setNeededDate(Date neededDate) {
        this.neededDate = neededDate;
    }

    public Long getSupplierTenantId() {
        return this.supplierTenantId;
    }

    public void setSupplierTenantId(Long supplierTenantId) {
        this.supplierTenantId = supplierTenantId;
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

    public String getExecutionStatusCode() {
        return this.executionStatusCode;
    }

    public void setExecutionStatusCode(String executionStatusCode) {
        this.executionStatusCode = executionStatusCode;
    }

    public Date getExecutedDate() {
        return this.executedDate;
    }

    public void setExecutedDate(Date executedDate) {
        this.executedDate = executedDate;
    }

    public Long getExecutedBy() {
        return this.executedBy;
    }

    public void setExecutedBy(Long executedBy) {
        this.executedBy = executedBy;
    }

    public Long getExecutionBillId() {
        return this.executionBillId;
    }

    public void setExecutionBillId(Long executionBillId) {
        this.executionBillId = executionBillId;
    }

    public String getExecutionBillNum() {
        return this.executionBillNum;
    }

    public void setExecutionBillNum(String executionBillNum) {
        this.executionBillNum = executionBillNum;
    }

    public String getExecutionBillData() {
        return this.executionBillData;
    }

    public void setExecutionBillData(String executionBillData) {
        this.executionBillData = executionBillData;
    }

    public String getMappingCode() {
        return this.mappingCode;
    }

    public void setMappingCode(String mappingCode) {
        this.mappingCode = mappingCode;
    }

    public Integer getAssignedFlag() {
        return this.assignedFlag;
    }

    public void setAssignedFlag(Integer assignedFlag) {
        this.assignedFlag = assignedFlag;
    }

    public Date getAssignedDate() {
        return this.assignedDate;
    }

    public void setAssignedDate(Date assignedDate) {
        this.assignedDate = assignedDate;
    }

    public Long getAssignedBy() {
        return this.assignedBy;
    }

    public void setAssignedBy(Long assignedBy) {
        this.assignedBy = assignedBy;
    }

    public String getAssignedRemark() {
        return this.assignedRemark;
    }

    public void setAssignedRemark(String assignedRemark) {
        this.assignedRemark = assignedRemark;
    }

    public Integer getCancelledFlag() {
        return this.cancelledFlag;
    }

    public void setCancelledFlag(Integer cancelledFlag) {
        this.cancelledFlag = cancelledFlag;
    }

    public Date getCancelledDate() {
        return this.cancelledDate;
    }

    public void setCancelledDate(Date cancelledDate) {
        this.cancelledDate = cancelledDate;
    }

    public Long getCancelledBy() {
        return this.cancelledBy;
    }

    public void setCancelledBy(Long cancelledBy) {
        this.cancelledBy = cancelledBy;
    }

    public String getCancelledRemark() {
        return this.cancelledRemark;
    }

    public void setCancelledRemark(String cancelledRemark) {
        this.cancelledRemark = cancelledRemark;
    }

    public Integer getClosedFlag() {
        return this.closedFlag;
    }

    public void setClosedFlag(Integer closedFlag) {
        this.closedFlag = closedFlag;
    }

    public Date getClosedDate() {
        return this.closedDate;
    }

    public void setClosedDate(Date closedDate) {
        this.closedDate = closedDate;
    }

    public Long getClosedBy() {
        return this.closedBy;
    }

    public void setClosedBy(Long closedBy) {
        this.closedBy = closedBy;
    }

    public String getClosedRemark() {
        return this.closedRemark;
    }

    public void setClosedRemark(String closedRemark) {
        this.closedRemark = closedRemark;
    }

    public Integer getSuspendFlag() {
        return this.suspendFlag;
    }

    public void setSuspendFlag(Integer suspendFlag) {
        this.suspendFlag = suspendFlag;
    }

    public Date getSuspendDate() {
        return this.suspendDate;
    }

    public void setSuspendDate(Date suspendDate) {
        this.suspendDate = suspendDate;
    }

    public String getSuspendRemark() {
        return this.suspendRemark;
    }

    public void setSuspendRemark(String suspendRemark) {
        this.suspendRemark = suspendRemark;
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

    public String getAttachmentUuid() {
        return this.attachmentUuid;
    }

    public void setAttachmentUuid(String attachmentUuid) {
        this.attachmentUuid = attachmentUuid;
    }

    public Long getObjectVersionNumber() {
        return this.objectVersionNumber;
    }

    public void setObjectVersionNumber(Long objectVersionNumber) {
        this.objectVersionNumber = objectVersionNumber;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setCanVatFlag(Integer canVatFlag) {
        this.canVatFlag = canVatFlag;
    }

    public Integer getCanVatFlag() {
        return this.canVatFlag;
    }

    public BigDecimal getUnitPrice() {
        return this.unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getInvOrganizationName() {
        return this.invOrganizationName;
    }

    public void setInvOrganizationName(String invOrganizationName) {
        this.invOrganizationName = invOrganizationName;
    }

    public Integer getPer() {
        return this.per;
    }

    public void setPer(Integer per) {
        this.per = per;
    }

    public BigDecimal getConvertUnitPrice() {
        return this.convertUnitPrice;
    }

    public void setConvertUnitPrice(BigDecimal convertUnitPrice) {
        this.convertUnitPrice = convertUnitPrice;
    }

    public BigDecimal getJdPrice() {
        return this.jdPrice;
    }

    public void setJdPrice(BigDecimal jdPrice) {
        this.jdPrice = jdPrice;
    }

    public BigDecimal getOccupiedQuantity() {
        return this.occupiedQuantity;
    }

    public void setOccupiedQuantity(BigDecimal occupiedQuantity) {
        this.occupiedQuantity = occupiedQuantity;
    }

    public BigDecimal getRestPoQuantity() {
        return this.restPoQuantity;
    }

    public void setRestPoQuantity(BigDecimal restPoQuantity) {
        this.restPoQuantity = restPoQuantity;
    }

    public BigDecimal getThisOrderQuantity() {
        return this.thisOrderQuantity;
    }

    public void setThisOrderQuantity(BigDecimal thisOrderQuantity) {
        this.thisOrderQuantity = thisOrderQuantity;
    }

    public BigDecimal getTaxWithoutFreightPrice() {
        return this.taxWithoutFreightPrice;
    }

    public void setTaxWithoutFreightPrice(BigDecimal taxWithoutFreightPrice) {
        this.taxWithoutFreightPrice = taxWithoutFreightPrice;
    }

    public Long getAccountAssignTypeId() {
        return this.accountAssignTypeId;
    }

    public void setAccountAssignTypeId(Long accountAssignTypeId) {
        this.accountAssignTypeId = accountAssignTypeId;
    }

    public String getPrimaryUomName() {
        return this.primaryUomName;
    }

    public void setPrimaryUomName(String primaryUomName) {
        this.primaryUomName = primaryUomName;
    }

    public String getOrderUomName() {
        return this.orderUomName;
    }

    public void setOrderUomName(String orderUomName) {
        this.orderUomName = orderUomName;
    }

    public String getQualityStandard() {
        return this.qualityStandard;
    }

    public void setQualityStandard(String qualityStandard) {
        this.qualityStandard = qualityStandard;
    }

    public Integer getBudgetIoFlag() {
        return this.budgetIoFlag;
    }

    public void setBudgetIoFlag(Integer budgetIoFlag) {
        this.budgetIoFlag = budgetIoFlag;
    }

    public BigDecimal getTaxIncludedBudgetUnitPrice() {
        return this.taxIncludedBudgetUnitPrice;
    }

    public void setTaxIncludedBudgetUnitPrice(BigDecimal taxIncludedBudgetUnitPrice) {
        this.taxIncludedBudgetUnitPrice = taxIncludedBudgetUnitPrice;
    }

    public String getInventoryName() {
        return this.inventoryName;
    }

    public void setInventoryName(String inventoryName) {
        this.inventoryName = inventoryName;
    }

    public String getCategoryName() {
        return this.categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getUomName() {
        return this.uomName;
    }

    public void setUomName(String uomName) {
        this.uomName = uomName;
    }

    public String getCostName() {
        return this.costName;
    }

    public void setCostName(String costName) {
        this.costName = costName;
    }

    public String getAccountSubjectName() {
        return this.accountSubjectName;
    }

    public void setAccountSubjectName(String accountSubjectName) {
        this.accountSubjectName = accountSubjectName;
    }

    public String getExpBearDepCode() {
        return this.expBearDepCode;
    }

    public void setExpBearDepCode(String expBearDepCode) {
        this.expBearDepCode = expBearDepCode;
    }

    public String getExpBearDep() {
        return this.expBearDep;
    }

    public void setExpBearDep(String expBearDep) {
        this.expBearDep = expBearDep;
    }

    public int getUniqueKey() {
        return ("displayLineNum" + this.displayLineNum).hashCode();
    }

    public int hashCode() {
        return ("displayLineNum" + this.displayLineNum).hashCode();
    }

    public void addCancelStatus(String cancelledRemark) {
        this.cancelledFlag = Flag.YES;
        this.cancelledBy = DetailsHelper.getUserDetails().getUserId();
        this.cancelledDate = new Date();
        this.cancelledRemark = cancelledRemark;
    }

    public void addSuspendStatus(String suspendRemark) {
        this.suspendFlag = Flag.YES;
        this.suspendDate = new Date();
        this.suspendRemark = suspendRemark;
    }

    public void clearCancelStatus() {
        this.cancelledFlag = Flag.NO;
        this.cancelledBy = null;
        this.cancelledDate = null;
        this.cancelledRemark = null;
    }

    public void clearSuspendStatus() {
        this.suspendFlag = Flag.NO;
        this.suspendDate = null;
        this.suspendRemark = null;
    }

    public void clearIncorrectStatus() {
        this.incorrectDate = null;
        this.incorrectFlag = Flag.NO;
        this.incorrectMsg = null;
    }

    public void addIncorrectStatus(String msg) {
        this.incorrectDate = new Date();
        this.incorrectFlag = Flag.YES;
        this.incorrectMsg = msg;
    }

    public void clearAssignedStatus(PrLineAssignRepository prLineAssignRepository) {
        this.assignedBy = null;
        this.assignedFlag = Flag.NO;
        this.assignedRemark = null;
        this.assignedDate = null;
        this.executionStatusCode = null;
        if (Objects.nonNull(this.prLineId) && Objects.nonNull(this.tenantId)) {
            PrLineAssign prLineAssign = new PrLineAssign();
            prLineAssign.setPrLineId(this.prLineId);
            prLineAssign.setTenantId(this.tenantId);
            prLineAssignRepository.delete(prLineAssign);
        }

    }

    public void clearClosedStatus() {
        this.closedBy = null;
        this.closedDate = null;
        this.closedRemark = null;
        this.closedFlag = Flag.NO;
    }

    public void clearExecutionStatus() {
        this.executionStatusCode = null;
        this.executedDate = null;
        this.executedBy = null;
        this.executionBillId = null;
        this.executionBillNum = null;
        this.executionBillData = null;
        this.executionHeaderBillId = null;
        this.executionHeaderBillNum = null;
        this.executionStrategyCode = null;
        this.occupiedQuantity = BigDecimal.ZERO;
    }

    public Integer getUnitFlag() {
        return this.unitFlag;
    }

    public void setUnitFlag(Integer unitFlag) {
        this.unitFlag = unitFlag;
    }

    public Integer getFreightLineFlag() {
        return this.freightLineFlag;
    }

    public void setFreightLineFlag(Integer freightLineFlag) {
        this.freightLineFlag = freightLineFlag;
    }

    public void addExecutionStatusByErp(PrLine prLineInDb) {
        if (!Objects.equals(prLineInDb.getExecutionStatusCode(), "ASSIGNED")) {
            this.executedBy = prLineInDb.executedBy;
            this.executedDate = prLineInDb.executedDate;
            this.executionBillData = prLineInDb.executionBillData;
            this.executionBillId = prLineInDb.executionBillId;
            this.executionBillNum = prLineInDb.executionBillNum;
            this.executionStatusCode = prLineInDb.executionStatusCode;
            this.executionHeaderBillId = prLineInDb.executionHeaderBillId;
            this.executionHeaderBillNum = prLineInDb.executionHeaderBillNum;
        }

    }

    public Long getPrTypeId() {
        return this.prTypeId;
    }

    public void setPrTypeId(Long prTypeId) {
        this.prTypeId = prTypeId;
    }

    public String getExecutionStrategyCode() {
        return this.executionStrategyCode;
    }

    public void setExecutionStrategyCode(String executionStrategyCode) {
        this.executionStrategyCode = executionStrategyCode;
    }

    public String getExecutionStrategyMeaning() {
        return this.executionStrategyMeaning;
    }

    public void setExecutionStrategyMeaning(String executionStrategyMeaning) {
        this.executionStrategyMeaning = executionStrategyMeaning;
    }

    public Long getBudgetAccountId() {
        return this.budgetAccountId;
    }

    public void setBudgetAccountId(Long budgetAccountId) {
        this.budgetAccountId = budgetAccountId;
    }

    public Integer getOccupyFlag() {
        return this.occupyFlag;
    }

    public void setOccupyFlag(Integer occupyFlag) {
        this.occupyFlag = occupyFlag;
    }

    public BigDecimal getOldQuantity() {
        return this.oldQuantity;
    }

    public void setOldQuantity(BigDecimal oldQuantity) {
        this.oldQuantity = oldQuantity;
    }

    public String getOldPrLineId() {
        return this.oldPrLineId;
    }

    public void setOldPrLineId(String oldPrLineId) {
        this.oldPrLineId = oldPrLineId;
    }

    public Integer getChangeSubmitFlag() {
        return this.changeSubmitFlag;
    }

    public void setChangeSubmitFlag(Integer changeSubmitFlag) {
        this.changeSubmitFlag = changeSubmitFlag;
    }

    public String getReceiverInformation() {
        return this.receiverInformation;
    }

    public void setReceiverInformation(String receiverInformation) {
        this.receiverInformation = receiverInformation;
    }

    public String getReceiveAddress() {
        return this.receiveAddress;
    }

    public void setReceiveAddress(String receiveAddress) {
        this.receiveAddress = receiveAddress;
    }

    public String getReceiveContactName() {
        return this.receiveContactName;
    }

    public void setReceiveContactName(String receiveContactName) {
        this.receiveContactName = receiveContactName;
    }

    public String getReceiveTelNum() {
        return this.receiveTelNum;
    }

    public void setReceiveTelNum(String receiveTelNum) {
        this.receiveTelNum = receiveTelNum;
    }

    public Integer getBusinessCardFlag() {
        return this.businessCardFlag;
    }

    public void setBusinessCardFlag(Integer businessCardFlag) {
        this.businessCardFlag = businessCardFlag;
    }

    public Long getCartUserId() {
        return this.cartUserId;
    }

    public void setCartUserId(Long cartUserId) {
        this.cartUserId = cartUserId;
    }

    public String getCartUserType() {
        return this.cartUserType;
    }

    public void setCartUserType(String cartUserType) {
        this.cartUserType = cartUserType;
    }

    public String getBudgetAccountNum() {
        return this.budgetAccountNum;
    }

    public void setBudgetAccountNum(String budgetAccountNum) {
        this.budgetAccountNum = budgetAccountNum;
    }

    public String getAgentName() {
        return this.agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public Long getCopyPrLineId() {
        return this.copyPrLineId;
    }

    public void setCopyPrLineId(Long copyPrLineId) {
        this.copyPrLineId = copyPrLineId;
    }

    public Integer getIsPushAssetsFlag() {
        return isPushAssetsFlag;
    }

    public void setIsPushAssetsFlag(Integer isPushAssetsFlag) {
        this.isPushAssetsFlag = isPushAssetsFlag;
    }

    @Override
    public Integer getAttributeVarchar11() {
        return attributeVarchar11;
    }

    public void setAttributeVarchar11(Integer attributeVarchar11) {
        this.attributeVarchar11 = attributeVarchar11;
    }

    static {
        FIELD_REMARK_MAP.put("prHeaderId", "采购申请头ID");
        FIELD_REMARK_MAP.put("tenantId", "租户ID");
        FIELD_REMARK_MAP.put("lineNum", "行号");
        FIELD_REMARK_MAP.put("displayLineNum", "展示行号");
        FIELD_REMARK_MAP.put("companyId", "公司ID");
        FIELD_REMARK_MAP.put("ouId", "业务实体ID");
        FIELD_REMARK_MAP.put("purchaseOrgId", "采购组织ID");
        FIELD_REMARK_MAP.put("purchaseAgentId", "采购员ID");
        FIELD_REMARK_MAP.put("requestDate", "申请日期");
        FIELD_REMARK_MAP.put("requestedBy", "申请人Id");
        FIELD_REMARK_MAP.put("prRequestedName", "申请人");
        FIELD_REMARK_MAP.put("invOrganizationId", "库存组织ID");
        FIELD_REMARK_MAP.put("inventoryId", "库房ID");
        FIELD_REMARK_MAP.put("itemId", "物料ID");
        FIELD_REMARK_MAP.put("itemCode", "物料编码");
        FIELD_REMARK_MAP.put("itemName", "物料名称");
        FIELD_REMARK_MAP.put("productId", "商品ID");
        FIELD_REMARK_MAP.put("productNum", "商品编码");
        FIELD_REMARK_MAP.put("productName", "商品名称");
        FIELD_REMARK_MAP.put("catalogId", "商品目录ID");
        FIELD_REMARK_MAP.put("catalogName", "商品目录名");
        FIELD_REMARK_MAP.put("categoryId", "自主品类ID");
        FIELD_REMARK_MAP.put("uomId", "单位ID");
        FIELD_REMARK_MAP.put("quantity", "数量");
        FIELD_REMARK_MAP.put("taxId", "税率ID");
        FIELD_REMARK_MAP.put("taxRate", "税率");
        FIELD_REMARK_MAP.put("currencyCode", "币种");
        FIELD_REMARK_MAP.put("unitPrice", "不含税单价");
        FIELD_REMARK_MAP.put("taxIncludedUnitPrice", "含税单价");
        FIELD_REMARK_MAP.put("lineAmount", "不含税行金额");
        FIELD_REMARK_MAP.put("taxIncludedLineAmount", "含税行金额");
        FIELD_REMARK_MAP.put("neededDate", "需求日期");
        FIELD_REMARK_MAP.put("supplierTenantId", "供应商租户ID");
        FIELD_REMARK_MAP.put("supplierId", "供应商ID");
        FIELD_REMARK_MAP.put("supplierCode", "供应商编码");
        FIELD_REMARK_MAP.put("supplierName", "供应商名称");
        FIELD_REMARK_MAP.put("supplierCompanyId", "供应商公司ID");
        FIELD_REMARK_MAP.put("supplierCompanyName", "供应商公司描述");
        FIELD_REMARK_MAP.put("executionStatusCode", "执行状态代码");
        FIELD_REMARK_MAP.put("executedDate", "执行时间");
        FIELD_REMARK_MAP.put("executedBy", "执行人ID");
        FIELD_REMARK_MAP.put("executionBillId", "目标单据ID");
        FIELD_REMARK_MAP.put("executionBillNum", "目标单据编码");
        FIELD_REMARK_MAP.put("executionBillData", "目标单据附加数据");
        FIELD_REMARK_MAP.put("assignedFlag", "分配标识");
        FIELD_REMARK_MAP.put("assignedDate", "分配时间");
        FIELD_REMARK_MAP.put("assignedBy", "分配人ID");
        FIELD_REMARK_MAP.put("assignedRemark", "分配原因");
        FIELD_REMARK_MAP.put("cancelledFlag", "取消标识");
        FIELD_REMARK_MAP.put("cancelledDate", "取消时间");
        FIELD_REMARK_MAP.put("cancelledBy", "取消人ID");
        FIELD_REMARK_MAP.put("cancelledRemark", "取消原因");
        FIELD_REMARK_MAP.put("closedFlag", "关闭标识");
        FIELD_REMARK_MAP.put("closedDate", "关闭时间");
        FIELD_REMARK_MAP.put("closedBy", "关闭人ID");
        FIELD_REMARK_MAP.put("closedRemark", "关闭原因");
        FIELD_REMARK_MAP.put("suspendFlag", "暂挂标志");
        FIELD_REMARK_MAP.put("suspendDate", "暂挂日期");
        FIELD_REMARK_MAP.put("suspendRemark", "暂挂说明");
        FIELD_REMARK_MAP.put("incorrectFlag", "异常标志");
        FIELD_REMARK_MAP.put("incorrectDate", "异常发生时间");
        FIELD_REMARK_MAP.put("incorrectMsg", "异常信息");
        FIELD_REMARK_MAP.put("canVatFlag", "可开专票标识");
        FIELD_REMARK_MAP.put("attachmentUuid", "附件UUID,功能: sprm-pr");
        FIELD_REMARK_MAP.put("remark", "行备注");
        FIELD_REMARK_MAP.put("lineFreight", "行运费");
        FIELD_REMARK_MAP.put("executionHeaderBillId", "目标头单据ID");
        FIELD_REMARK_MAP.put("executionHeaderBillNum", "目标头单据编码");
        FIELD_REMARK_MAP.put("per", "每");
        FIELD_REMARK_MAP.put("dataVersion", "erp数据版本号");
        FIELD_REMARK_MAP.put("erpEditStatus", "erp编辑状态");
        FIELD_REMARK_MAP.put("jdPrice", "划线价");
    }
}
