package org.srm.purchasecooperation.cux.pr.domain.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.choerodon.mybatis.annotation.ModifyAudit;
import io.choerodon.mybatis.annotation.VersionAudit;
import io.choerodon.mybatis.domain.AuditDomain;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 采购申请行
 *
 * @author bin.zhang06@hand-china.com 2021-03-16 15:49:15
 */
@ApiModel("采购申请行")
@VersionAudit
@ModifyAudit
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@Table(name = "sprm_pr_line")
public class PrLine extends AuditDomain {

    public static final String FIELD_PR_LINE_ID = "prLineId";
    public static final String FIELD_PR_HEADER_ID = "prHeaderId";
    public static final String FIELD_TENANT_ID = "tenantId";
    public static final String FIELD_LINE_NUM = "lineNum";
    public static final String FIELD_DISPLAY_LINE_NUM = "displayLineNum";
    public static final String FIELD_COMPANY_ID = "companyId";
    public static final String FIELD_OU_ID = "ouId";
    public static final String FIELD_PURCHASE_ORG_ID = "purchaseOrgId";
    public static final String FIELD_PURCHASE_AGENT_ID = "purchaseAgentId";
    public static final String FIELD_REQUEST_DATE = "requestDate";
    public static final String FIELD_REQUESTED_BY = "requestedBy";
    public static final String FIELD_PR_REQUESTED_NAME = "prRequestedName";
    public static final String FIELD_INV_ORGANIZATION_ID = "invOrganizationId";
    public static final String FIELD_INVENTORY_ID = "inventoryId";
    public static final String FIELD_ITEM_ID = "itemId";
    public static final String FIELD_ITEM_CODE = "itemCode";
    public static final String FIELD_ITEM_NAME = "itemName";
    public static final String FIELD_ITEM_ABC_CLASS = "itemAbcClass";
    public static final String FIELD_DRAWING_NUM = "drawingNum";
    public static final String FIELD_PROJECT_NUM = "projectNum";
    public static final String FIELD_PROJECT_NAME = "projectName";
    public static final String FIELD_CRANE_NUM = "craneNum";
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
    public static final String FIELD_UNIT_PRICE = "unitPrice";
    public static final String FIELD_TAX_INCLUDED_UNIT_PRICE = "taxIncludedUnitPrice";
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
    public static final String FIELD_INCORRECT_FLAG = "incorrectFlag";
    public static final String FIELD_INCORRECT_DATE = "incorrectDate";
    public static final String FIELD_INCORRECT_MSG = "incorrectMsg";
    public static final String FIELD_CAN_VAT_FLAG = "canVatFlag";
    public static final String FIELD_ERP_EDIT_STATUS = "erpEditStatus";
    public static final String FIELD_ATTACHMENT_UUID = "attachmentUuid";
    public static final String FIELD_REMARK = "remark";
    public static final String FIELD_LINE_FREIGHT = "lineFreight";
    public static final String FIELD_EXECUTION_HEADER_BILL_ID = "executionHeaderBillId";
    public static final String FIELD_EXECUTION_HEADER_BILL_NUM = "executionHeaderBillNum";
    public static final String FIELD_URGENT_FLAG = "urgentFlag";
    public static final String FIELD_URGENT_DATE = "urgentDate";
    public static final String FIELD_COST_ID = "costId";
    public static final String FIELD_COST_CODE = "costCode";
    public static final String FIELD_ACCOUNT_SUBJECT_ID = "accountSubjectId";
    public static final String FIELD_ACCOUNT_SUBJECT_NUM = "accountSubjectNum";
    public static final String FIELD_WBS = "wbs";
    public static final String FIELD_LAST_PURCHASE_PRICE = "lastPurchasePrice";
    public static final String FIELD_PC_HEADER_ID = "pcHeaderId";
    public static final String FIELD_ITEM_MODEL = "itemModel";
    public static final String FIELD_ITEM_SPECS = "itemSpecs";
    public static final String FIELD_ITEM_PROPERTIES = "itemProperties";
    public static final String FIELD_AGENT_ID = "agentId";
    public static final String FIELD_KEEPER_USER_ID = "keeperUserId";
    public static final String FIELD_ACCEPTER_USER_ID = "accepterUserId";
    public static final String FIELD_COST_PAYER_USER_ID = "costPayerUserId";
    public static final String FIELD_ADDRESS = "address";
    public static final String FIELD_INNER_PO_NUM = "innerPoNum";
    public static final String FIELD_JD_PRICE = "jdPrice";
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
    public static final String FIELD_TAX_CODE = "taxCode";
    public static final String FIELD_COMPANY_ORG_ID = "companyOrgId";
    public static final String FIELD_COST_ANCH_DEP_ID = "costAnchDepId";
    public static final String FIELD_EXP_BEAR_DEP_ID = "expBearDepId";
    public static final String FIELD_OVERSEAS_PROCUREMENT = "overseasProcurement";
    public static final String FIELD_PO_LINE_ID = "poLineId";
    public static final String FIELD_DRAWING_VERSION = "drawingVersion";
    public static final String FIELD_SUPPLIER_ITEM_CODE = "supplierItemCode";
    public static final String FIELD_OCCUPIED_QUANTITY = "occupiedQuantity";
    public static final String FIELD_SUPPLIER_ITEM_NAME = "supplierItemName";
    public static final String FIELD_FRAME_AGREEMENT_NUM = "frameAgreementNum";
    public static final String FIELD_WBS_CODE = "wbsCode";
    public static final String FIELD_TAX_WITHOUT_FREIGHT_PRICE = "taxWithoutFreightPrice";
    public static final String FIELD_BENCHMARK_PRICE = "benchmarkPrice";
    public static final String FIELD_CHANGE_PERCENT = "changePercent";
    public static final String FIELD_UNIT_PRICE_BATCH = "unitPriceBatch";
    public static final String FIELD_PROJECT_CATEGORY = "projectCategory";
    public static final String FIELD_ASSETS = "assets";
    public static final String FIELD_ASSET_CHILD_NUM = "assetChildNum";
    public static final String FIELD_ACCOUNT_ASSIGN_TYPE_ID = "accountAssignTypeId";
    public static final String FIELD_PURCHASE_LINE_TYPE_ID = "purchaseLineTypeId";
    public static final String FIELD_BUDGET_IO_FLAG = "budgetIoFlag";
    public static final String FIELD_TAX_INCLUDED_BUDGET_UNIT_PRICE = "taxIncludedBudgetUnitPrice";
    public static final String FIELD_QUALITY_STANDARD = "qualityStandard";
    public static final String FIELD_FREIGHT_LINE_FLAG = "freightLineFlag";
    public static final String FIELD_ATTRIBUTE_VARCHAR1 = "attributeVarchar1";
    public static final String FIELD_ATTRIBUTE_VARCHAR2 = "attributeVarchar2";
    public static final String FIELD_ATTRIBUTE_VARCHAR3 = "attributeVarchar3";
    public static final String FIELD_ATTRIBUTE_VARCHAR4 = "attributeVarchar4";
    public static final String FIELD_ATTRIBUTE_VARCHAR5 = "attributeVarchar5";
    public static final String FIELD_ATTRIBUTE_VARCHAR6 = "attributeVarchar6";
    public static final String FIELD_ATTRIBUTE_VARCHAR7 = "attributeVarchar7";
    public static final String FIELD_ATTRIBUTE_VARCHAR8 = "attributeVarchar8";
    public static final String FIELD_ATTRIBUTE_VARCHAR9 = "attributeVarchar9";
    public static final String FIELD_ATTRIBUTE_VARCHAR10 = "attributeVarchar10";
    public static final String FIELD_ATTRIBUTE_VARCHAR11 = "attributeVarchar11";
    public static final String FIELD_ATTRIBUTE_VARCHAR12 = "attributeVarchar12";
    public static final String FIELD_ATTRIBUTE_VARCHAR13 = "attributeVarchar13";
    public static final String FIELD_ATTRIBUTE_VARCHAR14 = "attributeVarchar14";
    public static final String FIELD_ATTRIBUTE_VARCHAR15 = "attributeVarchar15";
    public static final String FIELD_ATTRIBUTE_VARCHAR16 = "attributeVarchar16";
    public static final String FIELD_ATTRIBUTE_VARCHAR17 = "attributeVarchar17";
    public static final String FIELD_ATTRIBUTE_VARCHAR18 = "attributeVarchar18";
    public static final String FIELD_ATTRIBUTE_VARCHAR19 = "attributeVarchar19";
    public static final String FIELD_ATTRIBUTE_VARCHAR20 = "attributeVarchar20";
    public static final String FIELD_ATTRIBUTE_VARCHAR21 = "attributeVarchar21";
    public static final String FIELD_ATTRIBUTE_VARCHAR22 = "attributeVarchar22";
    public static final String FIELD_ATTRIBUTE_VARCHAR23 = "attributeVarchar23";
    public static final String FIELD_ATTRIBUTE_VARCHAR24 = "attributeVarchar24";
    public static final String FIELD_ATTRIBUTE_VARCHAR25 = "attributeVarchar25";
    public static final String FIELD_ATTRIBUTE_VARCHAR26 = "attributeVarchar26";
    public static final String FIELD_ATTRIBUTE_VARCHAR27 = "attributeVarchar27";
    public static final String FIELD_ATTRIBUTE_VARCHAR28 = "attributeVarchar28";
    public static final String FIELD_ATTRIBUTE_VARCHAR29 = "attributeVarchar29";
    public static final String FIELD_ATTRIBUTE_VARCHAR30 = "attributeVarchar30";
    public static final String FIELD_ATTRIBUTE_VARCHAR31 = "attributeVarchar31";
    public static final String FIELD_ATTRIBUTE_VARCHAR32 = "attributeVarchar32";
    public static final String FIELD_ATTRIBUTE_VARCHAR33 = "attributeVarchar33";
    public static final String FIELD_ATTRIBUTE_VARCHAR34 = "attributeVarchar34";
    public static final String FIELD_ATTRIBUTE_VARCHAR35 = "attributeVarchar35";
    public static final String FIELD_ATTRIBUTE_VARCHAR36 = "attributeVarchar36";
    public static final String FIELD_ATTRIBUTE_VARCHAR37 = "attributeVarchar37";
    public static final String FIELD_ATTRIBUTE_VARCHAR38 = "attributeVarchar38";
    public static final String FIELD_ATTRIBUTE_VARCHAR39 = "attributeVarchar39";
    public static final String FIELD_ATTRIBUTE_VARCHAR40 = "attributeVarchar40";
    public static final String FIELD_ATTRIBUTE_LONGTEXT1 = "attributeLongtext1";
    public static final String FIELD_ATTRIBUTE_LONGTEXT2 = "attributeLongtext2";
    public static final String FIELD_ATTRIBUTE_LONGTEXT3 = "attributeLongtext3";
    public static final String FIELD_ATTRIBUTE_LONGTEXT4 = "attributeLongtext4";
    public static final String FIELD_ATTRIBUTE_LONGTEXT5 = "attributeLongtext5";
    public static final String FIELD_ATTRIBUTE_LONGTEXT6 = "attributeLongtext6";
    public static final String FIELD_ATTRIBUTE_LONGTEXT7 = "attributeLongtext7";
    public static final String FIELD_ATTRIBUTE_LONGTEXT8 = "attributeLongtext8";
    public static final String FIELD_ATTRIBUTE_LONGTEXT9 = "attributeLongtext9";
    public static final String FIELD_ATTRIBUTE_LONGTEXT10 = "attributeLongtext10";
    public static final String FIELD_ATTRIBUTE_BIGINT1 = "attributeBigint1";
    public static final String FIELD_ATTRIBUTE_BIGINT2 = "attributeBigint2";
    public static final String FIELD_ATTRIBUTE_BIGINT3 = "attributeBigint3";
    public static final String FIELD_ATTRIBUTE_BIGINT4 = "attributeBigint4";
    public static final String FIELD_ATTRIBUTE_BIGINT5 = "attributeBigint5";
    public static final String FIELD_ATTRIBUTE_BIGINT6 = "attributeBigint6";
    public static final String FIELD_ATTRIBUTE_BIGINT7 = "attributeBigint7";
    public static final String FIELD_ATTRIBUTE_BIGINT8 = "attributeBigint8";
    public static final String FIELD_ATTRIBUTE_BIGINT9 = "attributeBigint9";
    public static final String FIELD_ATTRIBUTE_BIGINT10 = "attributeBigint10";
    public static final String FIELD_ATTRIBUTE_BIGINT11 = "attributeBigint11";
    public static final String FIELD_ATTRIBUTE_BIGINT12 = "attributeBigint12";
    public static final String FIELD_ATTRIBUTE_BIGINT13 = "attributeBigint13";
    public static final String FIELD_ATTRIBUTE_BIGINT14 = "attributeBigint14";
    public static final String FIELD_ATTRIBUTE_BIGINT15 = "attributeBigint15";
    public static final String FIELD_ATTRIBUTE_BIGINT16 = "attributeBigint16";
    public static final String FIELD_ATTRIBUTE_BIGINT17 = "attributeBigint17";
    public static final String FIELD_ATTRIBUTE_BIGINT18 = "attributeBigint18";
    public static final String FIELD_ATTRIBUTE_BIGINT19 = "attributeBigint19";
    public static final String FIELD_ATTRIBUTE_BIGINT20 = "attributeBigint20";
    public static final String FIELD_ATTRIBUTE_BIGINT21 = "attributeBigint21";
    public static final String FIELD_ATTRIBUTE_BIGINT22 = "attributeBigint22";
    public static final String FIELD_ATTRIBUTE_BIGINT23 = "attributeBigint23";
    public static final String FIELD_ATTRIBUTE_BIGINT24 = "attributeBigint24";
    public static final String FIELD_ATTRIBUTE_BIGINT25 = "attributeBigint25";
    public static final String FIELD_ATTRIBUTE_BIGINT26 = "attributeBigint26";
    public static final String FIELD_ATTRIBUTE_BIGINT27 = "attributeBigint27";
    public static final String FIELD_ATTRIBUTE_BIGINT28 = "attributeBigint28";
    public static final String FIELD_ATTRIBUTE_BIGINT29 = "attributeBigint29";
    public static final String FIELD_ATTRIBUTE_BIGINT30 = "attributeBigint30";
    public static final String FIELD_ATTRIBUTE_TINYINT1 = "attributeTinyint1";
    public static final String FIELD_ATTRIBUTE_TINYINT2 = "attributeTinyint2";
    public static final String FIELD_ATTRIBUTE_TINYINT3 = "attributeTinyint3";
    public static final String FIELD_ATTRIBUTE_TINYINT4 = "attributeTinyint4";
    public static final String FIELD_ATTRIBUTE_TINYINT5 = "attributeTinyint5";
    public static final String FIELD_ATTRIBUTE_TINYINT6 = "attributeTinyint6";
    public static final String FIELD_ATTRIBUTE_TINYINT7 = "attributeTinyint7";
    public static final String FIELD_ATTRIBUTE_TINYINT8 = "attributeTinyint8";
    public static final String FIELD_ATTRIBUTE_TINYINT9 = "attributeTinyint9";
    public static final String FIELD_ATTRIBUTE_TINYINT10 = "attributeTinyint10";
    public static final String FIELD_ATTRIBUTE_TINYINT11 = "attributeTinyint11";
    public static final String FIELD_ATTRIBUTE_TINYINT12 = "attributeTinyint12";
    public static final String FIELD_ATTRIBUTE_TINYINT13 = "attributeTinyint13";
    public static final String FIELD_ATTRIBUTE_TINYINT14 = "attributeTinyint14";
    public static final String FIELD_ATTRIBUTE_TINYINT15 = "attributeTinyint15";
    public static final String FIELD_ATTRIBUTE_TINYINT16 = "attributeTinyint16";
    public static final String FIELD_ATTRIBUTE_TINYINT17 = "attributeTinyint17";
    public static final String FIELD_ATTRIBUTE_TINYINT18 = "attributeTinyint18";
    public static final String FIELD_ATTRIBUTE_TINYINT19 = "attributeTinyint19";
    public static final String FIELD_ATTRIBUTE_TINYINT20 = "attributeTinyint20";
    public static final String FIELD_ATTRIBUTE_DECIMAL1 = "attributeDecimal1";
    public static final String FIELD_ATTRIBUTE_DECIMAL2 = "attributeDecimal2";
    public static final String FIELD_ATTRIBUTE_DECIMAL3 = "attributeDecimal3";
    public static final String FIELD_ATTRIBUTE_DECIMAL4 = "attributeDecimal4";
    public static final String FIELD_ATTRIBUTE_DECIMAL5 = "attributeDecimal5";
    public static final String FIELD_ATTRIBUTE_DECIMAL6 = "attributeDecimal6";
    public static final String FIELD_ATTRIBUTE_DECIMAL7 = "attributeDecimal7";
    public static final String FIELD_ATTRIBUTE_DECIMAL8 = "attributeDecimal8";
    public static final String FIELD_ATTRIBUTE_DECIMAL9 = "attributeDecimal9";
    public static final String FIELD_ATTRIBUTE_DECIMAL10 = "attributeDecimal10";
    public static final String FIELD_ATTRIBUTE_DECIMAL11 = "attributeDecimal11";
    public static final String FIELD_ATTRIBUTE_DECIMAL12 = "attributeDecimal12";
    public static final String FIELD_ATTRIBUTE_DECIMAL13 = "attributeDecimal13";
    public static final String FIELD_ATTRIBUTE_DECIMAL14 = "attributeDecimal14";
    public static final String FIELD_ATTRIBUTE_DECIMAL15 = "attributeDecimal15";
    public static final String FIELD_ATTRIBUTE_DECIMAL16 = "attributeDecimal16";
    public static final String FIELD_ATTRIBUTE_DECIMAL17 = "attributeDecimal17";
    public static final String FIELD_ATTRIBUTE_DECIMAL18 = "attributeDecimal18";
    public static final String FIELD_ATTRIBUTE_DECIMAL19 = "attributeDecimal19";
    public static final String FIELD_ATTRIBUTE_DECIMAL20 = "attributeDecimal20";
    public static final String FIELD_ATTRIBUTE_DECIMAL21 = "attributeDecimal21";
    public static final String FIELD_ATTRIBUTE_DECIMAL22 = "attributeDecimal22";
    public static final String FIELD_ATTRIBUTE_DECIMAL23 = "attributeDecimal23";
    public static final String FIELD_ATTRIBUTE_DECIMAL24 = "attributeDecimal24";
    public static final String FIELD_ATTRIBUTE_DECIMAL25 = "attributeDecimal25";
    public static final String FIELD_ATTRIBUTE_DECIMAL26 = "attributeDecimal26";
    public static final String FIELD_ATTRIBUTE_DECIMAL27 = "attributeDecimal27";
    public static final String FIELD_ATTRIBUTE_DECIMAL28 = "attributeDecimal28";
    public static final String FIELD_ATTRIBUTE_DECIMAL29 = "attributeDecimal29";
    public static final String FIELD_ATTRIBUTE_DECIMAL30 = "attributeDecimal30";
    public static final String FIELD_ATTRIBUTE_DATETIME1 = "attributeDatetime1";
    public static final String FIELD_ATTRIBUTE_DATETIME2 = "attributeDatetime2";
    public static final String FIELD_ATTRIBUTE_DATETIME3 = "attributeDatetime3";
    public static final String FIELD_ATTRIBUTE_DATETIME4 = "attributeDatetime4";
    public static final String FIELD_ATTRIBUTE_DATETIME5 = "attributeDatetime5";
    public static final String FIELD_ATTRIBUTE_DATETIME6 = "attributeDatetime6";
    public static final String FIELD_ATTRIBUTE_DATETIME7 = "attributeDatetime7";
    public static final String FIELD_ATTRIBUTE_DATETIME8 = "attributeDatetime8";
    public static final String FIELD_ATTRIBUTE_DATETIME9 = "attributeDatetime9";
    public static final String FIELD_ATTRIBUTE_DATETIME10 = "attributeDatetime10";
    public static final String FIELD_ATTRIBUTE_DATETIME11 = "attributeDatetime11";
    public static final String FIELD_ATTRIBUTE_DATETIME12 = "attributeDatetime12";
    public static final String FIELD_ATTRIBUTE_DATETIME13 = "attributeDatetime13";
    public static final String FIELD_ATTRIBUTE_DATETIME14 = "attributeDatetime14";
    public static final String FIELD_ATTRIBUTE_DATETIME15 = "attributeDatetime15";
    public static final String FIELD_ATTRIBUTE_DATETIME16 = "attributeDatetime16";
    public static final String FIELD_ATTRIBUTE_DATETIME17 = "attributeDatetime17";
    public static final String FIELD_ATTRIBUTE_DATETIME18 = "attributeDatetime18";
    public static final String FIELD_ATTRIBUTE_DATETIME19 = "attributeDatetime19";
    public static final String FIELD_ATTRIBUTE_DATETIME20 = "attributeDatetime20";
    public static final String FIELD_ATTRIBUTE_DATE1 = "attributeDate1";
    public static final String FIELD_ATTRIBUTE_DATE2 = "attributeDate2";
    public static final String FIELD_ATTRIBUTE_DATE3 = "attributeDate3";
    public static final String FIELD_ATTRIBUTE_DATE4 = "attributeDate4";
    public static final String FIELD_ATTRIBUTE_DATE5 = "attributeDate5";
    public static final String FIELD_ATTRIBUTE_DATE6 = "attributeDate6";
    public static final String FIELD_ATTRIBUTE_DATE7 = "attributeDate7";
    public static final String FIELD_ATTRIBUTE_DATE8 = "attributeDate8";
    public static final String FIELD_ATTRIBUTE_DATE9 = "attributeDate9";
    public static final String FIELD_ATTRIBUTE_DATE10 = "attributeDate10";
    public static final String FIELD_ATTRIBUTE_DATE11 = "attributeDate11";
    public static final String FIELD_ATTRIBUTE_DATE12 = "attributeDate12";
    public static final String FIELD_ATTRIBUTE_DATE13 = "attributeDate13";
    public static final String FIELD_ATTRIBUTE_DATE14 = "attributeDate14";
    public static final String FIELD_ATTRIBUTE_DATE15 = "attributeDate15";
    public static final String FIELD_ATTRIBUTE_DATE16 = "attributeDate16";
    public static final String FIELD_ATTRIBUTE_DATE17 = "attributeDate17";
    public static final String FIELD_ATTRIBUTE_DATE18 = "attributeDate18";
    public static final String FIELD_ATTRIBUTE_DATE19 = "attributeDate19";
    public static final String FIELD_ATTRIBUTE_DATE20 = "attributeDate20";
    public static final String FIELD_EXECUTION_STRATEGY_CODE = "executionStrategyCode";
    public static final String FIELD_BUDGET_ACCOUNT_ID = "budgetAccountId";
    public static final String FIELD_RECEIVER_INFORMATION = "receiverInformation";
    public static final String FIELD_BUSINESS_CARD_FLAG = "businessCardFlag";
    public static final String FIELD_RECEIVE_ADDRESS = "receiveAddress";
    public static final String FIELD_RECEIVE_CONTACT_NAME = "receiveContactName";
    public static final String FIELD_RECEIVE_TEL_NUM = "receiveTelNum";
    public static final String FIELD_CART_USER_ID = "cartUserId";
    public static final String FIELD_CART_USER_TYPE = "cartUserType";
    public static final String FIELD_CHANGE_ORDER_CODE = "changeOrderCode";
    public static final String FIELD_CHANGE_ORDER_MESSAGE = "changeOrderMessage";
    public static final String FIELD_BUDGET_ACCOUNT_DEPTNO = "budgetAccountDeptno";
    public static final String FIELD_BUDGET_ACCOUNT_PRICE = "budgetAccountPrice";
    public static final String FIELD_EXCHANGE_RATE = "exchangeRate";
    public static final String FIELD_LOCAL_CURRENCY_TAX_UNIT = "localCurrencyTaxUnit";
    public static final String FIELD_LOCAL_CURRENCY_NO_TAX_UNIT = "localCurrencyNoTaxUnit";
    public static final String FIELD_LOCAL_CURRENCY_TAX_SUM = "localCurrencyTaxSum";
    public static final String FIELD_LOCAL_CURRENCY_NO_TAX_SUM = "localCurrencyNoTaxSum";
    public static final String FIELD_DOCUMENT_ID = "documentId";
    public static final String FIELD_EXCHANGE_RATE_DATE = "exchangeRateDate";
    public static final String FIELD_BUDGET_ACCOUNT_NUM = "budgetAccountNum";
    public static final String FIELD_BEFORE_SPLIT_PR_LINE_ID = "beforeSplitPrLineId";

    //
    // 业务方法(按public protected private顺序排列)
    // ------------------------------------------------------------------------------

    //
    // 数据库字段
    // ------------------------------------------------------------------------------


    @ApiModelProperty("")
    @Id
    @GeneratedValue
    private Long prLineId;
    @ApiModelProperty(value = "采购申请头ID",required = true)
    @NotNull
    private Long prHeaderId;
    @ApiModelProperty(value = "租户ID",required = true)
    @NotNull
    private Long tenantId;
   @ApiModelProperty(value = "行号")    
    private Long lineNum;
    @ApiModelProperty(value = "展示行号",required = true)
    @NotBlank
    private String displayLineNum;
    @ApiModelProperty(value = "公司ID",required = true)
    @NotNull
    private Long companyId;
   @ApiModelProperty(value = "业务实体ID")    
    private Long ouId;
   @ApiModelProperty(value = "采购组织ID")    
    private Long purchaseOrgId;
   @ApiModelProperty(value = "采购员ID")    
    private Long purchaseAgentId;
   @ApiModelProperty(value = "申请日期")    
    private Date requestDate;
   @ApiModelProperty(value = "申请人ID")    
    private Long requestedBy;
   @ApiModelProperty(value = "申请人")    
    private String prRequestedName;
   @ApiModelProperty(value = "库存组织ID")    
    private Long invOrganizationId;
   @ApiModelProperty(value = "库房ID")    
    private Long inventoryId;
   @ApiModelProperty(value = "物料ID")    
    private Long itemId;
   @ApiModelProperty(value = "物料编码")    
    private String itemCode;
   @ApiModelProperty(value = "物料名称")    
    private String itemName;
   @ApiModelProperty(value = "物料ABC属性")    
    private String itemAbcClass;
   @ApiModelProperty(value = "图号")    
    private String drawingNum;
   @ApiModelProperty(value = "项目号")    
    private String projectNum;
   @ApiModelProperty(value = "项目名称")    
    private String projectName;
   @ApiModelProperty(value = "项目号车号")    
    private String craneNum;
   @ApiModelProperty(value = "商品ID")    
    private Long productId;
   @ApiModelProperty(value = "商品编码")    
    private String productNum;
   @ApiModelProperty(value = "商品名称")    
    private String productName;
   @ApiModelProperty(value = "商品目录ID")    
    private Long catalogId;
   @ApiModelProperty(value = "商品目录名")    
    private String catalogName;
   @ApiModelProperty(value = "自主品类ID")    
    private Long categoryId;
   @ApiModelProperty(value = "单位ID")    
    private Long uomId;
   @ApiModelProperty(value = "数量")    
    private BigDecimal quantity;
   @ApiModelProperty(value = "税率ID")    
    private Long taxId;
   @ApiModelProperty(value = "税率")    
    private BigDecimal taxRate;
   @ApiModelProperty(value = "币种")    
    private String currencyCode;
   @ApiModelProperty(value = "不含税单价")    
    private BigDecimal unitPrice;
   @ApiModelProperty(value = "含税单价")    
    private BigDecimal taxIncludedUnitPrice;
   @ApiModelProperty(value = "不含税行金额")    
    private BigDecimal lineAmount;
   @ApiModelProperty(value = "含税行金额")    
    private BigDecimal taxIncludedLineAmount;
   @ApiModelProperty(value = "需求日期")    
    private Date neededDate;
   @ApiModelProperty(value = "供应商租户ID")    
    private Long supplierTenantId;
   @ApiModelProperty(value = "供应商ID")    
    private Long supplierId;
   @ApiModelProperty(value = "供应商编码")    
    private String supplierCode;
   @ApiModelProperty(value = "供应商名称")    
    private String supplierName;
   @ApiModelProperty(value = "供应商公司ID")    
    private Long supplierCompanyId;
   @ApiModelProperty(value = "供应商公司描述")    
    private String supplierCompanyName;
   @ApiModelProperty(value = "执行状态代码")    
    private String executionStatusCode;
   @ApiModelProperty(value = "执行时间")    
    private Date executedDate;
   @ApiModelProperty(value = "执行人ID")    
    private Long executedBy;
   @ApiModelProperty(value = "目标单据ID")    
    private Long executionBillId;
   @ApiModelProperty(value = "目标单据编码")    
    private String executionBillNum;
   @ApiModelProperty(value = "目标单据附加数据")    
    private String executionBillData;
    @ApiModelProperty(value = "分配标识",required = true)
    @NotNull
    private Integer assignedFlag;
   @ApiModelProperty(value = "分配时间")    
    private Date assignedDate;
   @ApiModelProperty(value = "分配人ID")    
    private Long assignedBy;
   @ApiModelProperty(value = "分配原因")    
    private String assignedRemark;
    @ApiModelProperty(value = "取消标识，0:未取消、1：已取消、2：取消中",required = true)
    @NotNull
    private Integer cancelledFlag;
   @ApiModelProperty(value = "取消时间")    
    private Date cancelledDate;
   @ApiModelProperty(value = "取消人ID")    
    private Long cancelledBy;
   @ApiModelProperty(value = "取消原因")    
    private String cancelledRemark;
    @ApiModelProperty(value = "关闭标识，0:未关闭、1：已关闭、2：关闭中",required = true)
    @NotNull
    private Integer closedFlag;
   @ApiModelProperty(value = "关闭时间")    
    private Date closedDate;
   @ApiModelProperty(value = "关闭人ID")    
    private Long closedBy;
   @ApiModelProperty(value = "关闭原因")    
    private String closedRemark;
   @ApiModelProperty(value = "暂挂标志")    
    private Integer suspendFlag;
   @ApiModelProperty(value = "暂挂日期")    
    private Date suspendDate;
   @ApiModelProperty(value = "暂挂说明")    
    private String suspendRemark;
   @ApiModelProperty(value = "异常标志")    
    private Integer incorrectFlag;
   @ApiModelProperty(value = "异常发生时间")    
    private Date incorrectDate;
   @ApiModelProperty(value = "异常信息")    
    private String incorrectMsg;
   @ApiModelProperty(value = "可开专票标识")    
    private Integer canVatFlag;
   @ApiModelProperty(value = "ERP编辑状态")    
    private String erpEditStatus;
   @ApiModelProperty(value = "附件UUID,功能: sprm-pr")    
    private String attachmentUuid;
   @ApiModelProperty(value = "行备注")    
    private String remark;
   @ApiModelProperty(value = "行运费")    
    private BigDecimal lineFreight;
   @ApiModelProperty(value = "目标头单据ID")    
    private Long executionHeaderBillId;
   @ApiModelProperty(value = "目标头单据编码")    
    private String executionHeaderBillNum;
   @ApiModelProperty(value = "加急标识")    
    private Integer urgentFlag;
   @ApiModelProperty(value = "加急日期")    
    private Date urgentDate;
   @ApiModelProperty(value = "成本中心主键")    
    private Long costId;
   @ApiModelProperty(value = "成本中心编码")    
    private String costCode;
   @ApiModelProperty(value = "总账主键")    
    private Long accountSubjectId;
   @ApiModelProperty(value = "外部总账编码")    
    private String accountSubjectNum;
   @ApiModelProperty(value = "工作分解结构")    
    private String wbs;
   @ApiModelProperty(value = "最近一次采购价")    
    private BigDecimal lastPurchasePrice;
   @ApiModelProperty(value = "采购协议头ID")    
    private Long pcHeaderId;
   @ApiModelProperty(value = "型号")    
    private String itemModel;
   @ApiModelProperty(value = "规格")    
    private String itemSpecs;
   @ApiModelProperty(value = "属性")    
    private String itemProperties;
   @ApiModelProperty(value = "采购员ID")    
    private Long agentId;
   @ApiModelProperty(value = "保管人用户ID")    
    private Long keeperUserId;
   @ApiModelProperty(value = "验收人用户ID")    
    private Long accepterUserId;
   @ApiModelProperty(value = "费用承担人用户ID")    
    private Long costPayerUserId;
   @ApiModelProperty(value = "地点，值集SPRM.PR_LINE.ADDRESS")    
    private String address;
   @ApiModelProperty(value = "内部订单号")    
    private String innerPoNum;
   @ApiModelProperty(value = "划线价")    
    private BigDecimal jdPrice;
   @ApiModelProperty(value = "采购组")    
    private String purchaseOrgGroupName;
   @ApiModelProperty(value = "公司组织")    
    private String companyOrgName;
   @ApiModelProperty(value = "费用挂靠部门")    
    private String costAnchDepDesc;
   @ApiModelProperty(value = "费用承担部门")    
    private String expBearDep;
   @ApiModelProperty(value = "来源平台")    
    private String sourcePlatformCode;
   @ApiModelProperty(value = "表面处理")    
    private Integer surfaceTreatFlag;
   @ApiModelProperty(value = "申请类型")    
    private String reqTypeCode;
   @ApiModelProperty(value = "验收人")    
    private String accepterUserName;
   @ApiModelProperty(value = "采购员")    
    private String agentName;
   @ApiModelProperty(value = "保管人")    
    private String keeperUserName;
   @ApiModelProperty(value = "批次号")    
    private String batchNo;
   @ApiModelProperty(value = "税种")    
    private Long taxCode;
   @ApiModelProperty(value = "公司组织id")    
    private Long companyOrgId;
   @ApiModelProperty(value = "费用挂靠部门id")    
    private Long costAnchDepId;
   @ApiModelProperty(value = "费用承担部门id")    
    private Long expBearDepId;
    @ApiModelProperty(value = "境外采购",required = true)
    @NotNull
    private Integer overseasProcurement;
   @ApiModelProperty(value = "订单行ID")    
    private Long poLineId;
   @ApiModelProperty(value = "图纸版本")    
    private String drawingVersion;
   @ApiModelProperty(value = "供应商料号")    
    private String supplierItemCode;
   @ApiModelProperty(value = "占用数量")    
    private BigDecimal occupiedQuantity;
   @ApiModelProperty(value = "供应商料号描述")    
    private String supplierItemName;
   @ApiModelProperty(value = "框架协议编码")    
    private String frameAgreementNum;
   @ApiModelProperty(value = "wbs编码")    
    private String wbsCode;
   @ApiModelProperty(value = "含税单价(不含运费)")    
    private BigDecimal taxWithoutFreightPrice;
   @ApiModelProperty(value = "基准价")    
    private BigDecimal benchmarkPrice;
   @ApiModelProperty(value = "涨跌幅")    
    private BigDecimal changePercent;
   @ApiModelProperty(value = "价格批量(每)")    
    private BigDecimal unitPriceBatch;
   @ApiModelProperty(value = "项目类别")    
    private String projectCategory;
   @ApiModelProperty(value = "资产")    
    private String assets;
   @ApiModelProperty(value = "资产子编号")    
    private String assetChildNum;
   @ApiModelProperty(value = "账户分配类别ID")    
    private Long accountAssignTypeId;
   @ApiModelProperty(value = "采购行类型ID")    
    private Long purchaseLineTypeId;
    @ApiModelProperty(value = "预算内外标识",required = true)
    @NotNull
    private Integer budgetIoFlag;
   @ApiModelProperty(value = "预算单价(含税)")    
    private BigDecimal taxIncludedBudgetUnitPrice;
   @ApiModelProperty(value = "质量标准")    
    private String qualityStandard;
   @ApiModelProperty(value = "新电商运费行标识")    
    private Integer freightLineFlag;
   @ApiModelProperty(value = "整体周期（天数）（融创二开）")    
    private String attributeVarchar1;
   @ApiModelProperty(value = "入围单号（融创）")    
    private String attributeVarchar2;
   @ApiModelProperty(value = "询报价单号（融创）")    
    private String attributeVarchar3;
   @ApiModelProperty(value = "招标单号（融创）")    
    private String attributeVarchar4;
   @ApiModelProperty(value = "采购合同号（融创）")    
    private String attributeVarchar5;
   @ApiModelProperty(value = "字符扩展字段6")    
    private String attributeVarchar6;
   @ApiModelProperty(value = "字符扩展字段7")    
    private String attributeVarchar7;
   @ApiModelProperty(value = "字符扩展字段8")    
    private String attributeVarchar8;
   @ApiModelProperty(value = "字符扩展字段9")    
    private String attributeVarchar9;
   @ApiModelProperty(value = "字符扩展字段10")    
    private String attributeVarchar10;
   @ApiModelProperty(value = "字符扩展字段11")    
    private String attributeVarchar11;
   @ApiModelProperty(value = "字符扩展字段12")    
    private String attributeVarchar12;
   @ApiModelProperty(value = "字符扩展字段13")    
    private String attributeVarchar13;
   @ApiModelProperty(value = "字符扩展字段14")    
    private String attributeVarchar14;
   @ApiModelProperty(value = "字符扩展字段15")    
    private String attributeVarchar15;
   @ApiModelProperty(value = "字符扩展字段16")    
    private String attributeVarchar16;
   @ApiModelProperty(value = "字符扩展字段17")    
    private String attributeVarchar17;
   @ApiModelProperty(value = "字符扩展字段18")    
    private String attributeVarchar18;
   @ApiModelProperty(value = "字符扩展字段19")    
    private String attributeVarchar19;
   @ApiModelProperty(value = "字符扩展字段20")    
    private String attributeVarchar20;
   @ApiModelProperty(value = "字符扩展字段21")    
    private String attributeVarchar21;
   @ApiModelProperty(value = "字符扩展字段22")    
    private String attributeVarchar22;
   @ApiModelProperty(value = "字符扩展字段23")    
    private String attributeVarchar23;
   @ApiModelProperty(value = "字符扩展字段24")    
    private String attributeVarchar24;
   @ApiModelProperty(value = "字符扩展字段25")    
    private String attributeVarchar25;
   @ApiModelProperty(value = "字符扩展字段26")    
    private String attributeVarchar26;
   @ApiModelProperty(value = "字符扩展字段27")    
    private String attributeVarchar27;
   @ApiModelProperty(value = "字符扩展字段28")    
    private String attributeVarchar28;
   @ApiModelProperty(value = "字符扩展字段29")    
    private String attributeVarchar29;
   @ApiModelProperty(value = "字符扩展字段30")    
    private String attributeVarchar30;
   @ApiModelProperty(value = "字符扩展字段31")    
    private String attributeVarchar31;
   @ApiModelProperty(value = "字符扩展字段32")    
    private String attributeVarchar32;
   @ApiModelProperty(value = "字符扩展字段33")    
    private String attributeVarchar33;
   @ApiModelProperty(value = "字符扩展字段34")    
    private String attributeVarchar34;
   @ApiModelProperty(value = "字符扩展字段35")    
    private String attributeVarchar35;
   @ApiModelProperty(value = "字符扩展字段36")    
    private String attributeVarchar36;
   @ApiModelProperty(value = "字符扩展字段37")    
    private String attributeVarchar37;
   @ApiModelProperty(value = "字符扩展字段38")    
    private String attributeVarchar38;
   @ApiModelProperty(value = "字符扩展字段39")    
    private String attributeVarchar39;
   @ApiModelProperty(value = "字符扩展字段40")    
    private String attributeVarchar40;
   @ApiModelProperty(value = "长字符扩展字段1")    
    private String attributeLongtext1;
   @ApiModelProperty(value = "长字符扩展字段2")    
    private String attributeLongtext2;
   @ApiModelProperty(value = "长字符扩展字段3")    
    private String attributeLongtext3;
   @ApiModelProperty(value = "长字符扩展字段4")    
    private String attributeLongtext4;
   @ApiModelProperty(value = "长字符扩展字段5")    
    private String attributeLongtext5;
   @ApiModelProperty(value = "长字符扩展字段6")    
    private String attributeLongtext6;
   @ApiModelProperty(value = "长字符扩展字段7")    
    private String attributeLongtext7;
   @ApiModelProperty(value = "长字符扩展字段8")    
    private String attributeLongtext8;
   @ApiModelProperty(value = "长字符扩展字段9")    
    private String attributeLongtext9;
   @ApiModelProperty(value = "长字符扩展字段10")    
    private String attributeLongtext10;
   @ApiModelProperty(value = "采购计划id（融创）")    
    private Long attributeBigint1;
   @ApiModelProperty(value = "整数扩展字段2")    
    private Long attributeBigint2;
   @ApiModelProperty(value = "整数扩展字段3")    
    private Long attributeBigint3;
   @ApiModelProperty(value = "整数扩展字段4")    
    private Long attributeBigint4;
   @ApiModelProperty(value = "整数扩展字段5")    
    private Long attributeBigint5;
   @ApiModelProperty(value = "整数扩展字段6")    
    private Long attributeBigint6;
   @ApiModelProperty(value = "整数扩展字段7")    
    private Long attributeBigint7;
   @ApiModelProperty(value = "整数扩展字段8")    
    private Long attributeBigint8;
   @ApiModelProperty(value = "整数扩展字段9")    
    private Long attributeBigint9;
   @ApiModelProperty(value = "整数扩展字段10")    
    private Long attributeBigint10;
   @ApiModelProperty(value = "整数扩展字段11")    
    private Long attributeBigint11;
   @ApiModelProperty(value = "整数扩展字段12")    
    private Long attributeBigint12;
   @ApiModelProperty(value = "整数扩展字段13")    
    private Long attributeBigint13;
   @ApiModelProperty(value = "整数扩展字段14")    
    private Long attributeBigint14;
   @ApiModelProperty(value = "整数扩展字段15")    
    private Long attributeBigint15;
   @ApiModelProperty(value = "整数扩展字段16")    
    private Long attributeBigint16;
   @ApiModelProperty(value = "整数扩展字段17")    
    private Long attributeBigint17;
   @ApiModelProperty(value = "整数扩展字段18")    
    private Long attributeBigint18;
   @ApiModelProperty(value = "整数扩展字段19")    
    private Long attributeBigint19;
   @ApiModelProperty(value = "整数扩展字段20")    
    private Long attributeBigint20;
   @ApiModelProperty(value = "整数扩展字段21")    
    private Long attributeBigint21;
   @ApiModelProperty(value = "整数扩展字段22")    
    private Long attributeBigint22;
   @ApiModelProperty(value = "整数扩展字段23")    
    private Long attributeBigint23;
   @ApiModelProperty(value = "整数扩展字段24")    
    private Long attributeBigint24;
   @ApiModelProperty(value = "整数扩展字段25")    
    private Long attributeBigint25;
   @ApiModelProperty(value = "整数扩展字段26")    
    private Long attributeBigint26;
   @ApiModelProperty(value = "整数扩展字段27")    
    private Long attributeBigint27;
   @ApiModelProperty(value = "整数扩展字段28")    
    private Long attributeBigint28;
   @ApiModelProperty(value = "整数扩展字段29")    
    private Long attributeBigint29;
   @ApiModelProperty(value = "整数扩展字段30")    
    private Long attributeBigint30;
   @ApiModelProperty(value = "小整数扩展字段1")    
    private Integer attributeTinyint1;
   @ApiModelProperty(value = "小整数扩展字段2")    
    private Integer attributeTinyint2;
   @ApiModelProperty(value = "小整数扩展字段3")    
    private Integer attributeTinyint3;
   @ApiModelProperty(value = "小整数扩展字段4")    
    private Integer attributeTinyint4;
   @ApiModelProperty(value = "小整数扩展字段5")    
    private Integer attributeTinyint5;
   @ApiModelProperty(value = "小整数扩展字段6")    
    private Integer attributeTinyint6;
   @ApiModelProperty(value = "小整数扩展字段7")    
    private Integer attributeTinyint7;
   @ApiModelProperty(value = "小整数扩展字段8")    
    private Integer attributeTinyint8;
   @ApiModelProperty(value = "小整数扩展字段9")    
    private Integer attributeTinyint9;
   @ApiModelProperty(value = "小整数扩展字段10")    
    private Integer attributeTinyint10;
   @ApiModelProperty(value = "小整数扩展字段11")    
    private Integer attributeTinyint11;
   @ApiModelProperty(value = "小整数扩展字段12")    
    private Integer attributeTinyint12;
   @ApiModelProperty(value = "小整数扩展字段13")    
    private Integer attributeTinyint13;
   @ApiModelProperty(value = "小整数扩展字段14")    
    private Integer attributeTinyint14;
   @ApiModelProperty(value = "小整数扩展字段15")    
    private Integer attributeTinyint15;
   @ApiModelProperty(value = "小整数扩展字段16")    
    private Integer attributeTinyint16;
   @ApiModelProperty(value = "小整数扩展字段17")    
    private Integer attributeTinyint17;
   @ApiModelProperty(value = "小整数扩展字段18")    
    private Integer attributeTinyint18;
   @ApiModelProperty(value = "小整数扩展字段19")    
    private Integer attributeTinyint19;
   @ApiModelProperty(value = "小整数扩展字段20")    
    private Integer attributeTinyint20;
   @ApiModelProperty(value = "小数扩展字段1")    
    private BigDecimal attributeDecimal1;
   @ApiModelProperty(value = "小数扩展字段2")    
    private BigDecimal attributeDecimal2;
   @ApiModelProperty(value = "小数扩展字段3")    
    private BigDecimal attributeDecimal3;
   @ApiModelProperty(value = "小数扩展字段4")    
    private BigDecimal attributeDecimal4;
   @ApiModelProperty(value = "小数扩展字段5")    
    private BigDecimal attributeDecimal5;
   @ApiModelProperty(value = "小数扩展字段6")    
    private BigDecimal attributeDecimal6;
   @ApiModelProperty(value = "小数扩展字段7")    
    private BigDecimal attributeDecimal7;
   @ApiModelProperty(value = "小数扩展字段8")    
    private BigDecimal attributeDecimal8;
   @ApiModelProperty(value = "小数扩展字段9")    
    private BigDecimal attributeDecimal9;
   @ApiModelProperty(value = "小数扩展字段10")    
    private BigDecimal attributeDecimal10;
   @ApiModelProperty(value = "小数扩展字段11")    
    private BigDecimal attributeDecimal11;
   @ApiModelProperty(value = "小数扩展字段12")    
    private BigDecimal attributeDecimal12;
   @ApiModelProperty(value = "小数扩展字段13")    
    private BigDecimal attributeDecimal13;
   @ApiModelProperty(value = "小数扩展字段14")    
    private BigDecimal attributeDecimal14;
   @ApiModelProperty(value = "小数扩展字段15")    
    private BigDecimal attributeDecimal15;
   @ApiModelProperty(value = "小数扩展字段16")    
    private BigDecimal attributeDecimal16;
   @ApiModelProperty(value = "小数扩展字段17")    
    private BigDecimal attributeDecimal17;
   @ApiModelProperty(value = "小数扩展字段18")    
    private BigDecimal attributeDecimal18;
   @ApiModelProperty(value = "小数扩展字段19")    
    private BigDecimal attributeDecimal19;
   @ApiModelProperty(value = "小数扩展字段20")    
    private BigDecimal attributeDecimal20;
   @ApiModelProperty(value = "小数扩展字段21")    
    private BigDecimal attributeDecimal21;
   @ApiModelProperty(value = "小数扩展字段22")    
    private BigDecimal attributeDecimal22;
   @ApiModelProperty(value = "小数扩展字段23")    
    private BigDecimal attributeDecimal23;
   @ApiModelProperty(value = "小数扩展字段24")    
    private BigDecimal attributeDecimal24;
   @ApiModelProperty(value = "小数扩展字段25")    
    private BigDecimal attributeDecimal25;
   @ApiModelProperty(value = "小数扩展字段26")    
    private BigDecimal attributeDecimal26;
   @ApiModelProperty(value = "小数扩展字段27")    
    private BigDecimal attributeDecimal27;
   @ApiModelProperty(value = "小数扩展字段28")    
    private BigDecimal attributeDecimal28;
   @ApiModelProperty(value = "小数扩展字段29")    
    private BigDecimal attributeDecimal29;
   @ApiModelProperty(value = "小数扩展字段30")    
    private BigDecimal attributeDecimal30;
   @ApiModelProperty(value = "日期时间扩展字段1")    
    private Date attributeDatetime1;
   @ApiModelProperty(value = "日期时间扩展字段2")    
    private Date attributeDatetime2;
   @ApiModelProperty(value = "日期时间扩展字段3")    
    private Date attributeDatetime3;
   @ApiModelProperty(value = "日期时间扩展字段4")    
    private Date attributeDatetime4;
   @ApiModelProperty(value = "日期时间扩展字段5")    
    private Date attributeDatetime5;
   @ApiModelProperty(value = "日期时间扩展字段6")    
    private Date attributeDatetime6;
   @ApiModelProperty(value = "日期时间扩展字段7")    
    private Date attributeDatetime7;
   @ApiModelProperty(value = "日期时间扩展字段8")    
    private Date attributeDatetime8;
   @ApiModelProperty(value = "日期时间扩展字段9")    
    private Date attributeDatetime9;
   @ApiModelProperty(value = "日期时间扩展字段10")    
    private Date attributeDatetime10;
   @ApiModelProperty(value = "日期时间扩展字段11")    
    private Date attributeDatetime11;
   @ApiModelProperty(value = "日期时间扩展字段12")    
    private Date attributeDatetime12;
   @ApiModelProperty(value = "日期时间扩展字段13")    
    private Date attributeDatetime13;
   @ApiModelProperty(value = "日期时间扩展字段14")    
    private Date attributeDatetime14;
   @ApiModelProperty(value = "日期时间扩展字段15")    
    private Date attributeDatetime15;
   @ApiModelProperty(value = "日期时间扩展字段16")    
    private Date attributeDatetime16;
   @ApiModelProperty(value = "日期时间扩展字段17")    
    private Date attributeDatetime17;
   @ApiModelProperty(value = "日期时间扩展字段18")    
    private Date attributeDatetime18;
   @ApiModelProperty(value = "日期时间扩展字段19")    
    private Date attributeDatetime19;
   @ApiModelProperty(value = "日期时间扩展字段20")    
    private Date attributeDatetime20;
   @ApiModelProperty(value = "实际完成时间（供方入围）（融创）")    
    private Date attributeDate1;
   @ApiModelProperty(value = "实际完成时间（立项审批）（融创）")    
    private Date attributeDate2;
   @ApiModelProperty(value = "实际完成时间（发标时间）（融创）")    
    private Date attributeDate3;
   @ApiModelProperty(value = "实际完成时间（定标时间）（融创）")    
    private Date attributeDate4;
   @ApiModelProperty(value = "实际完成时间（合同完成时间）（融创）")    
    private Date attributeDate5;
   @ApiModelProperty(value = "日期扩展字段6")    
    private Date attributeDate6;
   @ApiModelProperty(value = "日期扩展字段7")    
    private Date attributeDate7;
   @ApiModelProperty(value = "日期扩展字段8")    
    private Date attributeDate8;
   @ApiModelProperty(value = "日期扩展字段9")    
    private Date attributeDate9;
   @ApiModelProperty(value = "日期扩展字段10")    
    private Date attributeDate10;
   @ApiModelProperty(value = "日期扩展字段11")    
    private Date attributeDate11;
   @ApiModelProperty(value = "日期扩展字段12")    
    private Date attributeDate12;
   @ApiModelProperty(value = "日期扩展字段13")    
    private Date attributeDate13;
   @ApiModelProperty(value = "日期扩展字段14")    
    private Date attributeDate14;
   @ApiModelProperty(value = "日期扩展字段15")    
    private Date attributeDate15;
   @ApiModelProperty(value = "日期扩展字段16")    
    private Date attributeDate16;
   @ApiModelProperty(value = "日期扩展字段17")    
    private Date attributeDate17;
   @ApiModelProperty(value = "日期扩展字段18")    
    private Date attributeDate18;
   @ApiModelProperty(value = "日期扩展字段19")    
    private Date attributeDate19;
   @ApiModelProperty(value = "日期扩展字段20")    
    private Date attributeDate20;
   @ApiModelProperty(value = "执行策略")    
    private String executionStrategyCode;
   @ApiModelProperty(value = "预算科目ID")    
    private Long budgetAccountId;
   @ApiModelProperty(value = "收货信息")    
    private String receiverInformation;
   @ApiModelProperty(value = "")    
    private Integer businessCardFlag;
   @ApiModelProperty(value = "收货地址")    
    private String receiveAddress;
   @ApiModelProperty(value = "收货联系人")    
    private String receiveContactName;
   @ApiModelProperty(value = "收货联系电话")    
    private String receiveTelNum;
   @ApiModelProperty(value = "卡片用户ID")    
    private Long cartUserId;
   @ApiModelProperty(value = "卡片类型 GROUP/EMPLOYEE")    
    private String cartUserType;
   @ApiModelProperty(value = "自动生成订单成功还是失败")    
    private String changeOrderCode;
   @ApiModelProperty(value = "自动生成订单报错信息")    
    private String changeOrderMessage;
   @ApiModelProperty(value = "预算部门编号")    
    private String budgetAccountDeptno;
   @ApiModelProperty(value = "预算金额")    
    private BigDecimal budgetAccountPrice;
   @ApiModelProperty(value = "汇率")    
    private BigDecimal exchangeRate;
   @ApiModelProperty(value = "本币含税单价")    
    private BigDecimal localCurrencyTaxUnit;
   @ApiModelProperty(value = "本币不含税单价")    
    private BigDecimal localCurrencyNoTaxUnit;
   @ApiModelProperty(value = "本币含税金额")    
    private BigDecimal localCurrencyTaxSum;
   @ApiModelProperty(value = "本币不含税金额")    
    private BigDecimal localCurrencyNoTaxSum;
   @ApiModelProperty(value = "预算单据ID")    
    private String documentId;
   @ApiModelProperty(value = "汇率日期")    
    private Date exchangeRateDate;
   @ApiModelProperty(value = "预算科目编码")    
    private String budgetAccountNum;
   @ApiModelProperty(value = "申请取消时，拆分前的申请行ID")    
    private Long beforeSplitPrLineId;

	//
    // 非数据库字段
    // ------------------------------------------------------------------------------

    //
    // getter/setter
    // ------------------------------------------------------------------------------

    /**
     * @return 
     */
	public Long getPrLineId() {
		return prLineId;
	}

	public void setPrLineId(Long prLineId) {
		this.prLineId = prLineId;
	}
    /**
     * @return 采购申请头ID
     */
	public Long getPrHeaderId() {
		return prHeaderId;
	}

	public void setPrHeaderId(Long prHeaderId) {
		this.prHeaderId = prHeaderId;
	}
    /**
     * @return 租户ID
     */
	public Long getTenantId() {
		return tenantId;
	}

	public void setTenantId(Long tenantId) {
		this.tenantId = tenantId;
	}
    /**
     * @return 行号
     */
	public Long getLineNum() {
		return lineNum;
	}

	public void setLineNum(Long lineNum) {
		this.lineNum = lineNum;
	}
    /**
     * @return 展示行号
     */
	public String getDisplayLineNum() {
		return displayLineNum;
	}

	public void setDisplayLineNum(String displayLineNum) {
		this.displayLineNum = displayLineNum;
	}
    /**
     * @return 公司ID
     */
	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
    /**
     * @return 业务实体ID
     */
	public Long getOuId() {
		return ouId;
	}

	public void setOuId(Long ouId) {
		this.ouId = ouId;
	}
    /**
     * @return 采购组织ID
     */
	public Long getPurchaseOrgId() {
		return purchaseOrgId;
	}

	public void setPurchaseOrgId(Long purchaseOrgId) {
		this.purchaseOrgId = purchaseOrgId;
	}
    /**
     * @return 采购员ID
     */
	public Long getPurchaseAgentId() {
		return purchaseAgentId;
	}

	public void setPurchaseAgentId(Long purchaseAgentId) {
		this.purchaseAgentId = purchaseAgentId;
	}
    /**
     * @return 申请日期
     */
	public Date getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}
    /**
     * @return 申请人ID
     */
	public Long getRequestedBy() {
		return requestedBy;
	}

	public void setRequestedBy(Long requestedBy) {
		this.requestedBy = requestedBy;
	}
    /**
     * @return 申请人
     */
	public String getPrRequestedName() {
		return prRequestedName;
	}

	public void setPrRequestedName(String prRequestedName) {
		this.prRequestedName = prRequestedName;
	}
    /**
     * @return 库存组织ID
     */
	public Long getInvOrganizationId() {
		return invOrganizationId;
	}

	public void setInvOrganizationId(Long invOrganizationId) {
		this.invOrganizationId = invOrganizationId;
	}
    /**
     * @return 库房ID
     */
	public Long getInventoryId() {
		return inventoryId;
	}

	public void setInventoryId(Long inventoryId) {
		this.inventoryId = inventoryId;
	}
    /**
     * @return 物料ID
     */
	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}
    /**
     * @return 物料编码
     */
	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
    /**
     * @return 物料名称
     */
	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
    /**
     * @return 物料ABC属性
     */
	public String getItemAbcClass() {
		return itemAbcClass;
	}

	public void setItemAbcClass(String itemAbcClass) {
		this.itemAbcClass = itemAbcClass;
	}
    /**
     * @return 图号
     */
	public String getDrawingNum() {
		return drawingNum;
	}

	public void setDrawingNum(String drawingNum) {
		this.drawingNum = drawingNum;
	}
    /**
     * @return 项目号
     */
	public String getProjectNum() {
		return projectNum;
	}

	public void setProjectNum(String projectNum) {
		this.projectNum = projectNum;
	}
    /**
     * @return 项目名称
     */
	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
    /**
     * @return 项目号车号
     */
	public String getCraneNum() {
		return craneNum;
	}

	public void setCraneNum(String craneNum) {
		this.craneNum = craneNum;
	}
    /**
     * @return 商品ID
     */
	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}
    /**
     * @return 商品编码
     */
	public String getProductNum() {
		return productNum;
	}

	public void setProductNum(String productNum) {
		this.productNum = productNum;
	}
    /**
     * @return 商品名称
     */
	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}
    /**
     * @return 商品目录ID
     */
	public Long getCatalogId() {
		return catalogId;
	}

	public void setCatalogId(Long catalogId) {
		this.catalogId = catalogId;
	}
    /**
     * @return 商品目录名
     */
	public String getCatalogName() {
		return catalogName;
	}

	public void setCatalogName(String catalogName) {
		this.catalogName = catalogName;
	}
    /**
     * @return 自主品类ID
     */
	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}
    /**
     * @return 单位ID
     */
	public Long getUomId() {
		return uomId;
	}

	public void setUomId(Long uomId) {
		this.uomId = uomId;
	}
    /**
     * @return 数量
     */
	public BigDecimal getQuantity() {
		return quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}
    /**
     * @return 税率ID
     */
	public Long getTaxId() {
		return taxId;
	}

	public void setTaxId(Long taxId) {
		this.taxId = taxId;
	}
    /**
     * @return 税率
     */
	public BigDecimal getTaxRate() {
		return taxRate;
	}

	public void setTaxRate(BigDecimal taxRate) {
		this.taxRate = taxRate;
	}
    /**
     * @return 币种
     */
	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
    /**
     * @return 不含税单价
     */
	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}
    /**
     * @return 含税单价
     */
	public BigDecimal getTaxIncludedUnitPrice() {
		return taxIncludedUnitPrice;
	}

	public void setTaxIncludedUnitPrice(BigDecimal taxIncludedUnitPrice) {
		this.taxIncludedUnitPrice = taxIncludedUnitPrice;
	}
    /**
     * @return 不含税行金额
     */
	public BigDecimal getLineAmount() {
		return lineAmount;
	}

	public void setLineAmount(BigDecimal lineAmount) {
		this.lineAmount = lineAmount;
	}
    /**
     * @return 含税行金额
     */
	public BigDecimal getTaxIncludedLineAmount() {
		return taxIncludedLineAmount;
	}

	public void setTaxIncludedLineAmount(BigDecimal taxIncludedLineAmount) {
		this.taxIncludedLineAmount = taxIncludedLineAmount;
	}
    /**
     * @return 需求日期
     */
	public Date getNeededDate() {
		return neededDate;
	}

	public void setNeededDate(Date neededDate) {
		this.neededDate = neededDate;
	}
    /**
     * @return 供应商租户ID
     */
	public Long getSupplierTenantId() {
		return supplierTenantId;
	}

	public void setSupplierTenantId(Long supplierTenantId) {
		this.supplierTenantId = supplierTenantId;
	}
    /**
     * @return 供应商ID
     */
	public Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}
    /**
     * @return 供应商编码
     */
	public String getSupplierCode() {
		return supplierCode;
	}

	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}
    /**
     * @return 供应商名称
     */
	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
    /**
     * @return 供应商公司ID
     */
	public Long getSupplierCompanyId() {
		return supplierCompanyId;
	}

	public void setSupplierCompanyId(Long supplierCompanyId) {
		this.supplierCompanyId = supplierCompanyId;
	}
    /**
     * @return 供应商公司描述
     */
	public String getSupplierCompanyName() {
		return supplierCompanyName;
	}

	public void setSupplierCompanyName(String supplierCompanyName) {
		this.supplierCompanyName = supplierCompanyName;
	}
    /**
     * @return 执行状态代码
     */
	public String getExecutionStatusCode() {
		return executionStatusCode;
	}

	public void setExecutionStatusCode(String executionStatusCode) {
		this.executionStatusCode = executionStatusCode;
	}
    /**
     * @return 执行时间
     */
	public Date getExecutedDate() {
		return executedDate;
	}

	public void setExecutedDate(Date executedDate) {
		this.executedDate = executedDate;
	}
    /**
     * @return 执行人ID
     */
	public Long getExecutedBy() {
		return executedBy;
	}

	public void setExecutedBy(Long executedBy) {
		this.executedBy = executedBy;
	}
    /**
     * @return 目标单据ID
     */
	public Long getExecutionBillId() {
		return executionBillId;
	}

	public void setExecutionBillId(Long executionBillId) {
		this.executionBillId = executionBillId;
	}
    /**
     * @return 目标单据编码
     */
	public String getExecutionBillNum() {
		return executionBillNum;
	}

	public void setExecutionBillNum(String executionBillNum) {
		this.executionBillNum = executionBillNum;
	}
    /**
     * @return 目标单据附加数据
     */
	public String getExecutionBillData() {
		return executionBillData;
	}

	public void setExecutionBillData(String executionBillData) {
		this.executionBillData = executionBillData;
	}
    /**
     * @return 分配标识
     */
	public Integer getAssignedFlag() {
		return assignedFlag;
	}

	public void setAssignedFlag(Integer assignedFlag) {
		this.assignedFlag = assignedFlag;
	}
    /**
     * @return 分配时间
     */
	public Date getAssignedDate() {
		return assignedDate;
	}

	public void setAssignedDate(Date assignedDate) {
		this.assignedDate = assignedDate;
	}
    /**
     * @return 分配人ID
     */
	public Long getAssignedBy() {
		return assignedBy;
	}

	public void setAssignedBy(Long assignedBy) {
		this.assignedBy = assignedBy;
	}
    /**
     * @return 分配原因
     */
	public String getAssignedRemark() {
		return assignedRemark;
	}

	public void setAssignedRemark(String assignedRemark) {
		this.assignedRemark = assignedRemark;
	}
    /**
     * @return 取消标识，0:未取消、1：已取消、2：取消中
     */
	public Integer getCancelledFlag() {
		return cancelledFlag;
	}

	public void setCancelledFlag(Integer cancelledFlag) {
		this.cancelledFlag = cancelledFlag;
	}
    /**
     * @return 取消时间
     */
	public Date getCancelledDate() {
		return cancelledDate;
	}

	public void setCancelledDate(Date cancelledDate) {
		this.cancelledDate = cancelledDate;
	}
    /**
     * @return 取消人ID
     */
	public Long getCancelledBy() {
		return cancelledBy;
	}

	public void setCancelledBy(Long cancelledBy) {
		this.cancelledBy = cancelledBy;
	}
    /**
     * @return 取消原因
     */
	public String getCancelledRemark() {
		return cancelledRemark;
	}

	public void setCancelledRemark(String cancelledRemark) {
		this.cancelledRemark = cancelledRemark;
	}
    /**
     * @return 关闭标识，0:未关闭、1：已关闭、2：关闭中
     */
	public Integer getClosedFlag() {
		return closedFlag;
	}

	public void setClosedFlag(Integer closedFlag) {
		this.closedFlag = closedFlag;
	}
    /**
     * @return 关闭时间
     */
	public Date getClosedDate() {
		return closedDate;
	}

	public void setClosedDate(Date closedDate) {
		this.closedDate = closedDate;
	}
    /**
     * @return 关闭人ID
     */
	public Long getClosedBy() {
		return closedBy;
	}

	public void setClosedBy(Long closedBy) {
		this.closedBy = closedBy;
	}
    /**
     * @return 关闭原因
     */
	public String getClosedRemark() {
		return closedRemark;
	}

	public void setClosedRemark(String closedRemark) {
		this.closedRemark = closedRemark;
	}
    /**
     * @return 暂挂标志
     */
	public Integer getSuspendFlag() {
		return suspendFlag;
	}

	public void setSuspendFlag(Integer suspendFlag) {
		this.suspendFlag = suspendFlag;
	}
    /**
     * @return 暂挂日期
     */
	public Date getSuspendDate() {
		return suspendDate;
	}

	public void setSuspendDate(Date suspendDate) {
		this.suspendDate = suspendDate;
	}
    /**
     * @return 暂挂说明
     */
	public String getSuspendRemark() {
		return suspendRemark;
	}

	public void setSuspendRemark(String suspendRemark) {
		this.suspendRemark = suspendRemark;
	}
    /**
     * @return 异常标志
     */
	public Integer getIncorrectFlag() {
		return incorrectFlag;
	}

	public void setIncorrectFlag(Integer incorrectFlag) {
		this.incorrectFlag = incorrectFlag;
	}
    /**
     * @return 异常发生时间
     */
	public Date getIncorrectDate() {
		return incorrectDate;
	}

	public void setIncorrectDate(Date incorrectDate) {
		this.incorrectDate = incorrectDate;
	}
    /**
     * @return 异常信息
     */
	public String getIncorrectMsg() {
		return incorrectMsg;
	}

	public void setIncorrectMsg(String incorrectMsg) {
		this.incorrectMsg = incorrectMsg;
	}
    /**
     * @return 可开专票标识
     */
	public Integer getCanVatFlag() {
		return canVatFlag;
	}

	public void setCanVatFlag(Integer canVatFlag) {
		this.canVatFlag = canVatFlag;
	}
    /**
     * @return ERP编辑状态
     */
	public String getErpEditStatus() {
		return erpEditStatus;
	}

	public void setErpEditStatus(String erpEditStatus) {
		this.erpEditStatus = erpEditStatus;
	}
    /**
     * @return 附件UUID,功能: sprm-pr
     */
	public String getAttachmentUuid() {
		return attachmentUuid;
	}

	public void setAttachmentUuid(String attachmentUuid) {
		this.attachmentUuid = attachmentUuid;
	}
    /**
     * @return 行备注
     */
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
    /**
     * @return 行运费
     */
	public BigDecimal getLineFreight() {
		return lineFreight;
	}

	public void setLineFreight(BigDecimal lineFreight) {
		this.lineFreight = lineFreight;
	}
    /**
     * @return 目标头单据ID
     */
	public Long getExecutionHeaderBillId() {
		return executionHeaderBillId;
	}

	public void setExecutionHeaderBillId(Long executionHeaderBillId) {
		this.executionHeaderBillId = executionHeaderBillId;
	}
    /**
     * @return 目标头单据编码
     */
	public String getExecutionHeaderBillNum() {
		return executionHeaderBillNum;
	}

	public void setExecutionHeaderBillNum(String executionHeaderBillNum) {
		this.executionHeaderBillNum = executionHeaderBillNum;
	}
    /**
     * @return 加急标识
     */
	public Integer getUrgentFlag() {
		return urgentFlag;
	}

	public void setUrgentFlag(Integer urgentFlag) {
		this.urgentFlag = urgentFlag;
	}
    /**
     * @return 加急日期
     */
	public Date getUrgentDate() {
		return urgentDate;
	}

	public void setUrgentDate(Date urgentDate) {
		this.urgentDate = urgentDate;
	}
    /**
     * @return 成本中心主键
     */
	public Long getCostId() {
		return costId;
	}

	public void setCostId(Long costId) {
		this.costId = costId;
	}
    /**
     * @return 成本中心编码
     */
	public String getCostCode() {
		return costCode;
	}

	public void setCostCode(String costCode) {
		this.costCode = costCode;
	}
    /**
     * @return 总账主键
     */
	public Long getAccountSubjectId() {
		return accountSubjectId;
	}

	public void setAccountSubjectId(Long accountSubjectId) {
		this.accountSubjectId = accountSubjectId;
	}
    /**
     * @return 外部总账编码
     */
	public String getAccountSubjectNum() {
		return accountSubjectNum;
	}

	public void setAccountSubjectNum(String accountSubjectNum) {
		this.accountSubjectNum = accountSubjectNum;
	}
    /**
     * @return 工作分解结构
     */
	public String getWbs() {
		return wbs;
	}

	public void setWbs(String wbs) {
		this.wbs = wbs;
	}
    /**
     * @return 最近一次采购价
     */
	public BigDecimal getLastPurchasePrice() {
		return lastPurchasePrice;
	}

	public void setLastPurchasePrice(BigDecimal lastPurchasePrice) {
		this.lastPurchasePrice = lastPurchasePrice;
	}
    /**
     * @return 采购协议头ID
     */
	public Long getPcHeaderId() {
		return pcHeaderId;
	}

	public void setPcHeaderId(Long pcHeaderId) {
		this.pcHeaderId = pcHeaderId;
	}
    /**
     * @return 型号
     */
	public String getItemModel() {
		return itemModel;
	}

	public void setItemModel(String itemModel) {
		this.itemModel = itemModel;
	}
    /**
     * @return 规格
     */
	public String getItemSpecs() {
		return itemSpecs;
	}

	public void setItemSpecs(String itemSpecs) {
		this.itemSpecs = itemSpecs;
	}
    /**
     * @return 属性
     */
	public String getItemProperties() {
		return itemProperties;
	}

	public void setItemProperties(String itemProperties) {
		this.itemProperties = itemProperties;
	}
    /**
     * @return 采购员ID
     */
	public Long getAgentId() {
		return agentId;
	}

	public void setAgentId(Long agentId) {
		this.agentId = agentId;
	}
    /**
     * @return 保管人用户ID
     */
	public Long getKeeperUserId() {
		return keeperUserId;
	}

	public void setKeeperUserId(Long keeperUserId) {
		this.keeperUserId = keeperUserId;
	}
    /**
     * @return 验收人用户ID
     */
	public Long getAccepterUserId() {
		return accepterUserId;
	}

	public void setAccepterUserId(Long accepterUserId) {
		this.accepterUserId = accepterUserId;
	}
    /**
     * @return 费用承担人用户ID
     */
	public Long getCostPayerUserId() {
		return costPayerUserId;
	}

	public void setCostPayerUserId(Long costPayerUserId) {
		this.costPayerUserId = costPayerUserId;
	}
    /**
     * @return 地点，值集SPRM.PR_LINE.ADDRESS
     */
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
    /**
     * @return 内部订单号
     */
	public String getInnerPoNum() {
		return innerPoNum;
	}

	public void setInnerPoNum(String innerPoNum) {
		this.innerPoNum = innerPoNum;
	}
    /**
     * @return 划线价
     */
	public BigDecimal getJdPrice() {
		return jdPrice;
	}

	public void setJdPrice(BigDecimal jdPrice) {
		this.jdPrice = jdPrice;
	}
    /**
     * @return 采购组
     */
	public String getPurchaseOrgGroupName() {
		return purchaseOrgGroupName;
	}

	public void setPurchaseOrgGroupName(String purchaseOrgGroupName) {
		this.purchaseOrgGroupName = purchaseOrgGroupName;
	}
    /**
     * @return 公司组织
     */
	public String getCompanyOrgName() {
		return companyOrgName;
	}

	public void setCompanyOrgName(String companyOrgName) {
		this.companyOrgName = companyOrgName;
	}
    /**
     * @return 费用挂靠部门
     */
	public String getCostAnchDepDesc() {
		return costAnchDepDesc;
	}

	public void setCostAnchDepDesc(String costAnchDepDesc) {
		this.costAnchDepDesc = costAnchDepDesc;
	}
    /**
     * @return 费用承担部门
     */
	public String getExpBearDep() {
		return expBearDep;
	}

	public void setExpBearDep(String expBearDep) {
		this.expBearDep = expBearDep;
	}
    /**
     * @return 来源平台
     */
	public String getSourcePlatformCode() {
		return sourcePlatformCode;
	}

	public void setSourcePlatformCode(String sourcePlatformCode) {
		this.sourcePlatformCode = sourcePlatformCode;
	}
    /**
     * @return 表面处理
     */
	public Integer getSurfaceTreatFlag() {
		return surfaceTreatFlag;
	}

	public void setSurfaceTreatFlag(Integer surfaceTreatFlag) {
		this.surfaceTreatFlag = surfaceTreatFlag;
	}
    /**
     * @return 申请类型
     */
	public String getReqTypeCode() {
		return reqTypeCode;
	}

	public void setReqTypeCode(String reqTypeCode) {
		this.reqTypeCode = reqTypeCode;
	}
    /**
     * @return 验收人
     */
	public String getAccepterUserName() {
		return accepterUserName;
	}

	public void setAccepterUserName(String accepterUserName) {
		this.accepterUserName = accepterUserName;
	}
    /**
     * @return 采购员
     */
	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}
    /**
     * @return 保管人
     */
	public String getKeeperUserName() {
		return keeperUserName;
	}

	public void setKeeperUserName(String keeperUserName) {
		this.keeperUserName = keeperUserName;
	}
    /**
     * @return 批次号
     */
	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
    /**
     * @return 税种
     */
	public Long getTaxCode() {
		return taxCode;
	}

	public void setTaxCode(Long taxCode) {
		this.taxCode = taxCode;
	}
    /**
     * @return 公司组织id
     */
	public Long getCompanyOrgId() {
		return companyOrgId;
	}

	public void setCompanyOrgId(Long companyOrgId) {
		this.companyOrgId = companyOrgId;
	}
    /**
     * @return 费用挂靠部门id
     */
	public Long getCostAnchDepId() {
		return costAnchDepId;
	}

	public void setCostAnchDepId(Long costAnchDepId) {
		this.costAnchDepId = costAnchDepId;
	}
    /**
     * @return 费用承担部门id
     */
	public Long getExpBearDepId() {
		return expBearDepId;
	}

	public void setExpBearDepId(Long expBearDepId) {
		this.expBearDepId = expBearDepId;
	}
    /**
     * @return 境外采购
     */
	public Integer getOverseasProcurement() {
		return overseasProcurement;
	}

	public void setOverseasProcurement(Integer overseasProcurement) {
		this.overseasProcurement = overseasProcurement;
	}
    /**
     * @return 订单行ID
     */
	public Long getPoLineId() {
		return poLineId;
	}

	public void setPoLineId(Long poLineId) {
		this.poLineId = poLineId;
	}
    /**
     * @return 图纸版本
     */
	public String getDrawingVersion() {
		return drawingVersion;
	}

	public void setDrawingVersion(String drawingVersion) {
		this.drawingVersion = drawingVersion;
	}
    /**
     * @return 供应商料号
     */
	public String getSupplierItemCode() {
		return supplierItemCode;
	}

	public void setSupplierItemCode(String supplierItemCode) {
		this.supplierItemCode = supplierItemCode;
	}
    /**
     * @return 占用数量
     */
	public BigDecimal getOccupiedQuantity() {
		return occupiedQuantity;
	}

	public void setOccupiedQuantity(BigDecimal occupiedQuantity) {
		this.occupiedQuantity = occupiedQuantity;
	}
    /**
     * @return 供应商料号描述
     */
	public String getSupplierItemName() {
		return supplierItemName;
	}

	public void setSupplierItemName(String supplierItemName) {
		this.supplierItemName = supplierItemName;
	}
    /**
     * @return 框架协议编码
     */
	public String getFrameAgreementNum() {
		return frameAgreementNum;
	}

	public void setFrameAgreementNum(String frameAgreementNum) {
		this.frameAgreementNum = frameAgreementNum;
	}
    /**
     * @return wbs编码
     */
	public String getWbsCode() {
		return wbsCode;
	}

	public void setWbsCode(String wbsCode) {
		this.wbsCode = wbsCode;
	}
    /**
     * @return 含税单价(不含运费)
     */
	public BigDecimal getTaxWithoutFreightPrice() {
		return taxWithoutFreightPrice;
	}

	public void setTaxWithoutFreightPrice(BigDecimal taxWithoutFreightPrice) {
		this.taxWithoutFreightPrice = taxWithoutFreightPrice;
	}
    /**
     * @return 基准价
     */
	public BigDecimal getBenchmarkPrice() {
		return benchmarkPrice;
	}

	public void setBenchmarkPrice(BigDecimal benchmarkPrice) {
		this.benchmarkPrice = benchmarkPrice;
	}
    /**
     * @return 涨跌幅
     */
	public BigDecimal getChangePercent() {
		return changePercent;
	}

	public void setChangePercent(BigDecimal changePercent) {
		this.changePercent = changePercent;
	}
    /**
     * @return 价格批量(每)
     */
	public BigDecimal getUnitPriceBatch() {
		return unitPriceBatch;
	}

	public void setUnitPriceBatch(BigDecimal unitPriceBatch) {
		this.unitPriceBatch = unitPriceBatch;
	}
    /**
     * @return 项目类别
     */
	public String getProjectCategory() {
		return projectCategory;
	}

	public void setProjectCategory(String projectCategory) {
		this.projectCategory = projectCategory;
	}
    /**
     * @return 资产
     */
	public String getAssets() {
		return assets;
	}

	public void setAssets(String assets) {
		this.assets = assets;
	}
    /**
     * @return 资产子编号
     */
	public String getAssetChildNum() {
		return assetChildNum;
	}

	public void setAssetChildNum(String assetChildNum) {
		this.assetChildNum = assetChildNum;
	}
    /**
     * @return 账户分配类别ID
     */
	public Long getAccountAssignTypeId() {
		return accountAssignTypeId;
	}

	public void setAccountAssignTypeId(Long accountAssignTypeId) {
		this.accountAssignTypeId = accountAssignTypeId;
	}
    /**
     * @return 采购行类型ID
     */
	public Long getPurchaseLineTypeId() {
		return purchaseLineTypeId;
	}

	public void setPurchaseLineTypeId(Long purchaseLineTypeId) {
		this.purchaseLineTypeId = purchaseLineTypeId;
	}
    /**
     * @return 预算内外标识
     */
	public Integer getBudgetIoFlag() {
		return budgetIoFlag;
	}

	public void setBudgetIoFlag(Integer budgetIoFlag) {
		this.budgetIoFlag = budgetIoFlag;
	}
    /**
     * @return 预算单价(含税)
     */
	public BigDecimal getTaxIncludedBudgetUnitPrice() {
		return taxIncludedBudgetUnitPrice;
	}

	public void setTaxIncludedBudgetUnitPrice(BigDecimal taxIncludedBudgetUnitPrice) {
		this.taxIncludedBudgetUnitPrice = taxIncludedBudgetUnitPrice;
	}
    /**
     * @return 质量标准
     */
	public String getQualityStandard() {
		return qualityStandard;
	}

	public void setQualityStandard(String qualityStandard) {
		this.qualityStandard = qualityStandard;
	}
    /**
     * @return 新电商运费行标识
     */
	public Integer getFreightLineFlag() {
		return freightLineFlag;
	}

	public void setFreightLineFlag(Integer freightLineFlag) {
		this.freightLineFlag = freightLineFlag;
	}
    /**
     * @return 整体周期（天数）（融创二开）
     */
	public String getAttributeVarchar1() {
		return attributeVarchar1;
	}

	public void setAttributeVarchar1(String attributeVarchar1) {
		this.attributeVarchar1 = attributeVarchar1;
	}
    /**
     * @return 入围单号（融创）
     */
	public String getAttributeVarchar2() {
		return attributeVarchar2;
	}

	public void setAttributeVarchar2(String attributeVarchar2) {
		this.attributeVarchar2 = attributeVarchar2;
	}
    /**
     * @return 询报价单号（融创）
     */
	public String getAttributeVarchar3() {
		return attributeVarchar3;
	}

	public void setAttributeVarchar3(String attributeVarchar3) {
		this.attributeVarchar3 = attributeVarchar3;
	}
    /**
     * @return 招标单号（融创）
     */
	public String getAttributeVarchar4() {
		return attributeVarchar4;
	}

	public void setAttributeVarchar4(String attributeVarchar4) {
		this.attributeVarchar4 = attributeVarchar4;
	}
    /**
     * @return 采购合同号（融创）
     */
	public String getAttributeVarchar5() {
		return attributeVarchar5;
	}

	public void setAttributeVarchar5(String attributeVarchar5) {
		this.attributeVarchar5 = attributeVarchar5;
	}
    /**
     * @return 字符扩展字段6
     */
	public String getAttributeVarchar6() {
		return attributeVarchar6;
	}

	public void setAttributeVarchar6(String attributeVarchar6) {
		this.attributeVarchar6 = attributeVarchar6;
	}
    /**
     * @return 字符扩展字段7
     */
	public String getAttributeVarchar7() {
		return attributeVarchar7;
	}

	public void setAttributeVarchar7(String attributeVarchar7) {
		this.attributeVarchar7 = attributeVarchar7;
	}
    /**
     * @return 字符扩展字段8
     */
	public String getAttributeVarchar8() {
		return attributeVarchar8;
	}

	public void setAttributeVarchar8(String attributeVarchar8) {
		this.attributeVarchar8 = attributeVarchar8;
	}
    /**
     * @return 字符扩展字段9
     */
	public String getAttributeVarchar9() {
		return attributeVarchar9;
	}

	public void setAttributeVarchar9(String attributeVarchar9) {
		this.attributeVarchar9 = attributeVarchar9;
	}
    /**
     * @return 字符扩展字段10
     */
	public String getAttributeVarchar10() {
		return attributeVarchar10;
	}

	public void setAttributeVarchar10(String attributeVarchar10) {
		this.attributeVarchar10 = attributeVarchar10;
	}
    /**
     * @return 字符扩展字段11
     */
	public String getAttributeVarchar11() {
		return attributeVarchar11;
	}

	public void setAttributeVarchar11(String attributeVarchar11) {
		this.attributeVarchar11 = attributeVarchar11;
	}
    /**
     * @return 字符扩展字段12
     */
	public String getAttributeVarchar12() {
		return attributeVarchar12;
	}

	public void setAttributeVarchar12(String attributeVarchar12) {
		this.attributeVarchar12 = attributeVarchar12;
	}
    /**
     * @return 字符扩展字段13
     */
	public String getAttributeVarchar13() {
		return attributeVarchar13;
	}

	public void setAttributeVarchar13(String attributeVarchar13) {
		this.attributeVarchar13 = attributeVarchar13;
	}
    /**
     * @return 字符扩展字段14
     */
	public String getAttributeVarchar14() {
		return attributeVarchar14;
	}

	public void setAttributeVarchar14(String attributeVarchar14) {
		this.attributeVarchar14 = attributeVarchar14;
	}
    /**
     * @return 字符扩展字段15
     */
	public String getAttributeVarchar15() {
		return attributeVarchar15;
	}

	public void setAttributeVarchar15(String attributeVarchar15) {
		this.attributeVarchar15 = attributeVarchar15;
	}
    /**
     * @return 字符扩展字段16
     */
	public String getAttributeVarchar16() {
		return attributeVarchar16;
	}

	public void setAttributeVarchar16(String attributeVarchar16) {
		this.attributeVarchar16 = attributeVarchar16;
	}
    /**
     * @return 字符扩展字段17
     */
	public String getAttributeVarchar17() {
		return attributeVarchar17;
	}

	public void setAttributeVarchar17(String attributeVarchar17) {
		this.attributeVarchar17 = attributeVarchar17;
	}
    /**
     * @return 字符扩展字段18
     */
	public String getAttributeVarchar18() {
		return attributeVarchar18;
	}

	public void setAttributeVarchar18(String attributeVarchar18) {
		this.attributeVarchar18 = attributeVarchar18;
	}
    /**
     * @return 字符扩展字段19
     */
	public String getAttributeVarchar19() {
		return attributeVarchar19;
	}

	public void setAttributeVarchar19(String attributeVarchar19) {
		this.attributeVarchar19 = attributeVarchar19;
	}
    /**
     * @return 字符扩展字段20
     */
	public String getAttributeVarchar20() {
		return attributeVarchar20;
	}

	public void setAttributeVarchar20(String attributeVarchar20) {
		this.attributeVarchar20 = attributeVarchar20;
	}
    /**
     * @return 字符扩展字段21
     */
	public String getAttributeVarchar21() {
		return attributeVarchar21;
	}

	public void setAttributeVarchar21(String attributeVarchar21) {
		this.attributeVarchar21 = attributeVarchar21;
	}
    /**
     * @return 字符扩展字段22
     */
	public String getAttributeVarchar22() {
		return attributeVarchar22;
	}

	public void setAttributeVarchar22(String attributeVarchar22) {
		this.attributeVarchar22 = attributeVarchar22;
	}
    /**
     * @return 字符扩展字段23
     */
	public String getAttributeVarchar23() {
		return attributeVarchar23;
	}

	public void setAttributeVarchar23(String attributeVarchar23) {
		this.attributeVarchar23 = attributeVarchar23;
	}
    /**
     * @return 字符扩展字段24
     */
	public String getAttributeVarchar24() {
		return attributeVarchar24;
	}

	public void setAttributeVarchar24(String attributeVarchar24) {
		this.attributeVarchar24 = attributeVarchar24;
	}
    /**
     * @return 字符扩展字段25
     */
	public String getAttributeVarchar25() {
		return attributeVarchar25;
	}

	public void setAttributeVarchar25(String attributeVarchar25) {
		this.attributeVarchar25 = attributeVarchar25;
	}
    /**
     * @return 字符扩展字段26
     */
	public String getAttributeVarchar26() {
		return attributeVarchar26;
	}

	public void setAttributeVarchar26(String attributeVarchar26) {
		this.attributeVarchar26 = attributeVarchar26;
	}
    /**
     * @return 字符扩展字段27
     */
	public String getAttributeVarchar27() {
		return attributeVarchar27;
	}

	public void setAttributeVarchar27(String attributeVarchar27) {
		this.attributeVarchar27 = attributeVarchar27;
	}
    /**
     * @return 字符扩展字段28
     */
	public String getAttributeVarchar28() {
		return attributeVarchar28;
	}

	public void setAttributeVarchar28(String attributeVarchar28) {
		this.attributeVarchar28 = attributeVarchar28;
	}
    /**
     * @return 字符扩展字段29
     */
	public String getAttributeVarchar29() {
		return attributeVarchar29;
	}

	public void setAttributeVarchar29(String attributeVarchar29) {
		this.attributeVarchar29 = attributeVarchar29;
	}
    /**
     * @return 字符扩展字段30
     */
	public String getAttributeVarchar30() {
		return attributeVarchar30;
	}

	public void setAttributeVarchar30(String attributeVarchar30) {
		this.attributeVarchar30 = attributeVarchar30;
	}
    /**
     * @return 字符扩展字段31
     */
	public String getAttributeVarchar31() {
		return attributeVarchar31;
	}

	public void setAttributeVarchar31(String attributeVarchar31) {
		this.attributeVarchar31 = attributeVarchar31;
	}
    /**
     * @return 字符扩展字段32
     */
	public String getAttributeVarchar32() {
		return attributeVarchar32;
	}

	public void setAttributeVarchar32(String attributeVarchar32) {
		this.attributeVarchar32 = attributeVarchar32;
	}
    /**
     * @return 字符扩展字段33
     */
	public String getAttributeVarchar33() {
		return attributeVarchar33;
	}

	public void setAttributeVarchar33(String attributeVarchar33) {
		this.attributeVarchar33 = attributeVarchar33;
	}
    /**
     * @return 字符扩展字段34
     */
	public String getAttributeVarchar34() {
		return attributeVarchar34;
	}

	public void setAttributeVarchar34(String attributeVarchar34) {
		this.attributeVarchar34 = attributeVarchar34;
	}
    /**
     * @return 字符扩展字段35
     */
	public String getAttributeVarchar35() {
		return attributeVarchar35;
	}

	public void setAttributeVarchar35(String attributeVarchar35) {
		this.attributeVarchar35 = attributeVarchar35;
	}
    /**
     * @return 字符扩展字段36
     */
	public String getAttributeVarchar36() {
		return attributeVarchar36;
	}

	public void setAttributeVarchar36(String attributeVarchar36) {
		this.attributeVarchar36 = attributeVarchar36;
	}
    /**
     * @return 字符扩展字段37
     */
	public String getAttributeVarchar37() {
		return attributeVarchar37;
	}

	public void setAttributeVarchar37(String attributeVarchar37) {
		this.attributeVarchar37 = attributeVarchar37;
	}
    /**
     * @return 字符扩展字段38
     */
	public String getAttributeVarchar38() {
		return attributeVarchar38;
	}

	public void setAttributeVarchar38(String attributeVarchar38) {
		this.attributeVarchar38 = attributeVarchar38;
	}
    /**
     * @return 字符扩展字段39
     */
	public String getAttributeVarchar39() {
		return attributeVarchar39;
	}

	public void setAttributeVarchar39(String attributeVarchar39) {
		this.attributeVarchar39 = attributeVarchar39;
	}
    /**
     * @return 字符扩展字段40
     */
	public String getAttributeVarchar40() {
		return attributeVarchar40;
	}

	public void setAttributeVarchar40(String attributeVarchar40) {
		this.attributeVarchar40 = attributeVarchar40;
	}
    /**
     * @return 长字符扩展字段1
     */
	public String getAttributeLongtext1() {
		return attributeLongtext1;
	}

	public void setAttributeLongtext1(String attributeLongtext1) {
		this.attributeLongtext1 = attributeLongtext1;
	}
    /**
     * @return 长字符扩展字段2
     */
	public String getAttributeLongtext2() {
		return attributeLongtext2;
	}

	public void setAttributeLongtext2(String attributeLongtext2) {
		this.attributeLongtext2 = attributeLongtext2;
	}
    /**
     * @return 长字符扩展字段3
     */
	public String getAttributeLongtext3() {
		return attributeLongtext3;
	}

	public void setAttributeLongtext3(String attributeLongtext3) {
		this.attributeLongtext3 = attributeLongtext3;
	}
    /**
     * @return 长字符扩展字段4
     */
	public String getAttributeLongtext4() {
		return attributeLongtext4;
	}

	public void setAttributeLongtext4(String attributeLongtext4) {
		this.attributeLongtext4 = attributeLongtext4;
	}
    /**
     * @return 长字符扩展字段5
     */
	public String getAttributeLongtext5() {
		return attributeLongtext5;
	}

	public void setAttributeLongtext5(String attributeLongtext5) {
		this.attributeLongtext5 = attributeLongtext5;
	}
    /**
     * @return 长字符扩展字段6
     */
	public String getAttributeLongtext6() {
		return attributeLongtext6;
	}

	public void setAttributeLongtext6(String attributeLongtext6) {
		this.attributeLongtext6 = attributeLongtext6;
	}
    /**
     * @return 长字符扩展字段7
     */
	public String getAttributeLongtext7() {
		return attributeLongtext7;
	}

	public void setAttributeLongtext7(String attributeLongtext7) {
		this.attributeLongtext7 = attributeLongtext7;
	}
    /**
     * @return 长字符扩展字段8
     */
	public String getAttributeLongtext8() {
		return attributeLongtext8;
	}

	public void setAttributeLongtext8(String attributeLongtext8) {
		this.attributeLongtext8 = attributeLongtext8;
	}
    /**
     * @return 长字符扩展字段9
     */
	public String getAttributeLongtext9() {
		return attributeLongtext9;
	}

	public void setAttributeLongtext9(String attributeLongtext9) {
		this.attributeLongtext9 = attributeLongtext9;
	}
    /**
     * @return 长字符扩展字段10
     */
	public String getAttributeLongtext10() {
		return attributeLongtext10;
	}

	public void setAttributeLongtext10(String attributeLongtext10) {
		this.attributeLongtext10 = attributeLongtext10;
	}
    /**
     * @return 采购计划id（融创）
     */
	public Long getAttributeBigint1() {
		return attributeBigint1;
	}

	public void setAttributeBigint1(Long attributeBigint1) {
		this.attributeBigint1 = attributeBigint1;
	}
    /**
     * @return 整数扩展字段2
     */
	public Long getAttributeBigint2() {
		return attributeBigint2;
	}

	public void setAttributeBigint2(Long attributeBigint2) {
		this.attributeBigint2 = attributeBigint2;
	}
    /**
     * @return 整数扩展字段3
     */
	public Long getAttributeBigint3() {
		return attributeBigint3;
	}

	public void setAttributeBigint3(Long attributeBigint3) {
		this.attributeBigint3 = attributeBigint3;
	}
    /**
     * @return 整数扩展字段4
     */
	public Long getAttributeBigint4() {
		return attributeBigint4;
	}

	public void setAttributeBigint4(Long attributeBigint4) {
		this.attributeBigint4 = attributeBigint4;
	}
    /**
     * @return 整数扩展字段5
     */
	public Long getAttributeBigint5() {
		return attributeBigint5;
	}

	public void setAttributeBigint5(Long attributeBigint5) {
		this.attributeBigint5 = attributeBigint5;
	}
    /**
     * @return 整数扩展字段6
     */
	public Long getAttributeBigint6() {
		return attributeBigint6;
	}

	public void setAttributeBigint6(Long attributeBigint6) {
		this.attributeBigint6 = attributeBigint6;
	}
    /**
     * @return 整数扩展字段7
     */
	public Long getAttributeBigint7() {
		return attributeBigint7;
	}

	public void setAttributeBigint7(Long attributeBigint7) {
		this.attributeBigint7 = attributeBigint7;
	}
    /**
     * @return 整数扩展字段8
     */
	public Long getAttributeBigint8() {
		return attributeBigint8;
	}

	public void setAttributeBigint8(Long attributeBigint8) {
		this.attributeBigint8 = attributeBigint8;
	}
    /**
     * @return 整数扩展字段9
     */
	public Long getAttributeBigint9() {
		return attributeBigint9;
	}

	public void setAttributeBigint9(Long attributeBigint9) {
		this.attributeBigint9 = attributeBigint9;
	}
    /**
     * @return 整数扩展字段10
     */
	public Long getAttributeBigint10() {
		return attributeBigint10;
	}

	public void setAttributeBigint10(Long attributeBigint10) {
		this.attributeBigint10 = attributeBigint10;
	}
    /**
     * @return 整数扩展字段11
     */
	public Long getAttributeBigint11() {
		return attributeBigint11;
	}

	public void setAttributeBigint11(Long attributeBigint11) {
		this.attributeBigint11 = attributeBigint11;
	}
    /**
     * @return 整数扩展字段12
     */
	public Long getAttributeBigint12() {
		return attributeBigint12;
	}

	public void setAttributeBigint12(Long attributeBigint12) {
		this.attributeBigint12 = attributeBigint12;
	}
    /**
     * @return 整数扩展字段13
     */
	public Long getAttributeBigint13() {
		return attributeBigint13;
	}

	public void setAttributeBigint13(Long attributeBigint13) {
		this.attributeBigint13 = attributeBigint13;
	}
    /**
     * @return 整数扩展字段14
     */
	public Long getAttributeBigint14() {
		return attributeBigint14;
	}

	public void setAttributeBigint14(Long attributeBigint14) {
		this.attributeBigint14 = attributeBigint14;
	}
    /**
     * @return 整数扩展字段15
     */
	public Long getAttributeBigint15() {
		return attributeBigint15;
	}

	public void setAttributeBigint15(Long attributeBigint15) {
		this.attributeBigint15 = attributeBigint15;
	}
    /**
     * @return 整数扩展字段16
     */
	public Long getAttributeBigint16() {
		return attributeBigint16;
	}

	public void setAttributeBigint16(Long attributeBigint16) {
		this.attributeBigint16 = attributeBigint16;
	}
    /**
     * @return 整数扩展字段17
     */
	public Long getAttributeBigint17() {
		return attributeBigint17;
	}

	public void setAttributeBigint17(Long attributeBigint17) {
		this.attributeBigint17 = attributeBigint17;
	}
    /**
     * @return 整数扩展字段18
     */
	public Long getAttributeBigint18() {
		return attributeBigint18;
	}

	public void setAttributeBigint18(Long attributeBigint18) {
		this.attributeBigint18 = attributeBigint18;
	}
    /**
     * @return 整数扩展字段19
     */
	public Long getAttributeBigint19() {
		return attributeBigint19;
	}

	public void setAttributeBigint19(Long attributeBigint19) {
		this.attributeBigint19 = attributeBigint19;
	}
    /**
     * @return 整数扩展字段20
     */
	public Long getAttributeBigint20() {
		return attributeBigint20;
	}

	public void setAttributeBigint20(Long attributeBigint20) {
		this.attributeBigint20 = attributeBigint20;
	}
    /**
     * @return 整数扩展字段21
     */
	public Long getAttributeBigint21() {
		return attributeBigint21;
	}

	public void setAttributeBigint21(Long attributeBigint21) {
		this.attributeBigint21 = attributeBigint21;
	}
    /**
     * @return 整数扩展字段22
     */
	public Long getAttributeBigint22() {
		return attributeBigint22;
	}

	public void setAttributeBigint22(Long attributeBigint22) {
		this.attributeBigint22 = attributeBigint22;
	}
    /**
     * @return 整数扩展字段23
     */
	public Long getAttributeBigint23() {
		return attributeBigint23;
	}

	public void setAttributeBigint23(Long attributeBigint23) {
		this.attributeBigint23 = attributeBigint23;
	}
    /**
     * @return 整数扩展字段24
     */
	public Long getAttributeBigint24() {
		return attributeBigint24;
	}

	public void setAttributeBigint24(Long attributeBigint24) {
		this.attributeBigint24 = attributeBigint24;
	}
    /**
     * @return 整数扩展字段25
     */
	public Long getAttributeBigint25() {
		return attributeBigint25;
	}

	public void setAttributeBigint25(Long attributeBigint25) {
		this.attributeBigint25 = attributeBigint25;
	}
    /**
     * @return 整数扩展字段26
     */
	public Long getAttributeBigint26() {
		return attributeBigint26;
	}

	public void setAttributeBigint26(Long attributeBigint26) {
		this.attributeBigint26 = attributeBigint26;
	}
    /**
     * @return 整数扩展字段27
     */
	public Long getAttributeBigint27() {
		return attributeBigint27;
	}

	public void setAttributeBigint27(Long attributeBigint27) {
		this.attributeBigint27 = attributeBigint27;
	}
    /**
     * @return 整数扩展字段28
     */
	public Long getAttributeBigint28() {
		return attributeBigint28;
	}

	public void setAttributeBigint28(Long attributeBigint28) {
		this.attributeBigint28 = attributeBigint28;
	}
    /**
     * @return 整数扩展字段29
     */
	public Long getAttributeBigint29() {
		return attributeBigint29;
	}

	public void setAttributeBigint29(Long attributeBigint29) {
		this.attributeBigint29 = attributeBigint29;
	}
    /**
     * @return 整数扩展字段30
     */
	public Long getAttributeBigint30() {
		return attributeBigint30;
	}

	public void setAttributeBigint30(Long attributeBigint30) {
		this.attributeBigint30 = attributeBigint30;
	}
    /**
     * @return 小整数扩展字段1
     */
	public Integer getAttributeTinyint1() {
		return attributeTinyint1;
	}

	public void setAttributeTinyint1(Integer attributeTinyint1) {
		this.attributeTinyint1 = attributeTinyint1;
	}
    /**
     * @return 小整数扩展字段2
     */
	public Integer getAttributeTinyint2() {
		return attributeTinyint2;
	}

	public void setAttributeTinyint2(Integer attributeTinyint2) {
		this.attributeTinyint2 = attributeTinyint2;
	}
    /**
     * @return 小整数扩展字段3
     */
	public Integer getAttributeTinyint3() {
		return attributeTinyint3;
	}

	public void setAttributeTinyint3(Integer attributeTinyint3) {
		this.attributeTinyint3 = attributeTinyint3;
	}
    /**
     * @return 小整数扩展字段4
     */
	public Integer getAttributeTinyint4() {
		return attributeTinyint4;
	}

	public void setAttributeTinyint4(Integer attributeTinyint4) {
		this.attributeTinyint4 = attributeTinyint4;
	}
    /**
     * @return 小整数扩展字段5
     */
	public Integer getAttributeTinyint5() {
		return attributeTinyint5;
	}

	public void setAttributeTinyint5(Integer attributeTinyint5) {
		this.attributeTinyint5 = attributeTinyint5;
	}
    /**
     * @return 小整数扩展字段6
     */
	public Integer getAttributeTinyint6() {
		return attributeTinyint6;
	}

	public void setAttributeTinyint6(Integer attributeTinyint6) {
		this.attributeTinyint6 = attributeTinyint6;
	}
    /**
     * @return 小整数扩展字段7
     */
	public Integer getAttributeTinyint7() {
		return attributeTinyint7;
	}

	public void setAttributeTinyint7(Integer attributeTinyint7) {
		this.attributeTinyint7 = attributeTinyint7;
	}
    /**
     * @return 小整数扩展字段8
     */
	public Integer getAttributeTinyint8() {
		return attributeTinyint8;
	}

	public void setAttributeTinyint8(Integer attributeTinyint8) {
		this.attributeTinyint8 = attributeTinyint8;
	}
    /**
     * @return 小整数扩展字段9
     */
	public Integer getAttributeTinyint9() {
		return attributeTinyint9;
	}

	public void setAttributeTinyint9(Integer attributeTinyint9) {
		this.attributeTinyint9 = attributeTinyint9;
	}
    /**
     * @return 小整数扩展字段10
     */
	public Integer getAttributeTinyint10() {
		return attributeTinyint10;
	}

	public void setAttributeTinyint10(Integer attributeTinyint10) {
		this.attributeTinyint10 = attributeTinyint10;
	}
    /**
     * @return 小整数扩展字段11
     */
	public Integer getAttributeTinyint11() {
		return attributeTinyint11;
	}

	public void setAttributeTinyint11(Integer attributeTinyint11) {
		this.attributeTinyint11 = attributeTinyint11;
	}
    /**
     * @return 小整数扩展字段12
     */
	public Integer getAttributeTinyint12() {
		return attributeTinyint12;
	}

	public void setAttributeTinyint12(Integer attributeTinyint12) {
		this.attributeTinyint12 = attributeTinyint12;
	}
    /**
     * @return 小整数扩展字段13
     */
	public Integer getAttributeTinyint13() {
		return attributeTinyint13;
	}

	public void setAttributeTinyint13(Integer attributeTinyint13) {
		this.attributeTinyint13 = attributeTinyint13;
	}
    /**
     * @return 小整数扩展字段14
     */
	public Integer getAttributeTinyint14() {
		return attributeTinyint14;
	}

	public void setAttributeTinyint14(Integer attributeTinyint14) {
		this.attributeTinyint14 = attributeTinyint14;
	}
    /**
     * @return 小整数扩展字段15
     */
	public Integer getAttributeTinyint15() {
		return attributeTinyint15;
	}

	public void setAttributeTinyint15(Integer attributeTinyint15) {
		this.attributeTinyint15 = attributeTinyint15;
	}
    /**
     * @return 小整数扩展字段16
     */
	public Integer getAttributeTinyint16() {
		return attributeTinyint16;
	}

	public void setAttributeTinyint16(Integer attributeTinyint16) {
		this.attributeTinyint16 = attributeTinyint16;
	}
    /**
     * @return 小整数扩展字段17
     */
	public Integer getAttributeTinyint17() {
		return attributeTinyint17;
	}

	public void setAttributeTinyint17(Integer attributeTinyint17) {
		this.attributeTinyint17 = attributeTinyint17;
	}
    /**
     * @return 小整数扩展字段18
     */
	public Integer getAttributeTinyint18() {
		return attributeTinyint18;
	}

	public void setAttributeTinyint18(Integer attributeTinyint18) {
		this.attributeTinyint18 = attributeTinyint18;
	}
    /**
     * @return 小整数扩展字段19
     */
	public Integer getAttributeTinyint19() {
		return attributeTinyint19;
	}

	public void setAttributeTinyint19(Integer attributeTinyint19) {
		this.attributeTinyint19 = attributeTinyint19;
	}
    /**
     * @return 小整数扩展字段20
     */
	public Integer getAttributeTinyint20() {
		return attributeTinyint20;
	}

	public void setAttributeTinyint20(Integer attributeTinyint20) {
		this.attributeTinyint20 = attributeTinyint20;
	}
    /**
     * @return 小数扩展字段1
     */
	public BigDecimal getAttributeDecimal1() {
		return attributeDecimal1;
	}

	public void setAttributeDecimal1(BigDecimal attributeDecimal1) {
		this.attributeDecimal1 = attributeDecimal1;
	}
    /**
     * @return 小数扩展字段2
     */
	public BigDecimal getAttributeDecimal2() {
		return attributeDecimal2;
	}

	public void setAttributeDecimal2(BigDecimal attributeDecimal2) {
		this.attributeDecimal2 = attributeDecimal2;
	}
    /**
     * @return 小数扩展字段3
     */
	public BigDecimal getAttributeDecimal3() {
		return attributeDecimal3;
	}

	public void setAttributeDecimal3(BigDecimal attributeDecimal3) {
		this.attributeDecimal3 = attributeDecimal3;
	}
    /**
     * @return 小数扩展字段4
     */
	public BigDecimal getAttributeDecimal4() {
		return attributeDecimal4;
	}

	public void setAttributeDecimal4(BigDecimal attributeDecimal4) {
		this.attributeDecimal4 = attributeDecimal4;
	}
    /**
     * @return 小数扩展字段5
     */
	public BigDecimal getAttributeDecimal5() {
		return attributeDecimal5;
	}

	public void setAttributeDecimal5(BigDecimal attributeDecimal5) {
		this.attributeDecimal5 = attributeDecimal5;
	}
    /**
     * @return 小数扩展字段6
     */
	public BigDecimal getAttributeDecimal6() {
		return attributeDecimal6;
	}

	public void setAttributeDecimal6(BigDecimal attributeDecimal6) {
		this.attributeDecimal6 = attributeDecimal6;
	}
    /**
     * @return 小数扩展字段7
     */
	public BigDecimal getAttributeDecimal7() {
		return attributeDecimal7;
	}

	public void setAttributeDecimal7(BigDecimal attributeDecimal7) {
		this.attributeDecimal7 = attributeDecimal7;
	}
    /**
     * @return 小数扩展字段8
     */
	public BigDecimal getAttributeDecimal8() {
		return attributeDecimal8;
	}

	public void setAttributeDecimal8(BigDecimal attributeDecimal8) {
		this.attributeDecimal8 = attributeDecimal8;
	}
    /**
     * @return 小数扩展字段9
     */
	public BigDecimal getAttributeDecimal9() {
		return attributeDecimal9;
	}

	public void setAttributeDecimal9(BigDecimal attributeDecimal9) {
		this.attributeDecimal9 = attributeDecimal9;
	}
    /**
     * @return 小数扩展字段10
     */
	public BigDecimal getAttributeDecimal10() {
		return attributeDecimal10;
	}

	public void setAttributeDecimal10(BigDecimal attributeDecimal10) {
		this.attributeDecimal10 = attributeDecimal10;
	}
    /**
     * @return 小数扩展字段11
     */
	public BigDecimal getAttributeDecimal11() {
		return attributeDecimal11;
	}

	public void setAttributeDecimal11(BigDecimal attributeDecimal11) {
		this.attributeDecimal11 = attributeDecimal11;
	}
    /**
     * @return 小数扩展字段12
     */
	public BigDecimal getAttributeDecimal12() {
		return attributeDecimal12;
	}

	public void setAttributeDecimal12(BigDecimal attributeDecimal12) {
		this.attributeDecimal12 = attributeDecimal12;
	}
    /**
     * @return 小数扩展字段13
     */
	public BigDecimal getAttributeDecimal13() {
		return attributeDecimal13;
	}

	public void setAttributeDecimal13(BigDecimal attributeDecimal13) {
		this.attributeDecimal13 = attributeDecimal13;
	}
    /**
     * @return 小数扩展字段14
     */
	public BigDecimal getAttributeDecimal14() {
		return attributeDecimal14;
	}

	public void setAttributeDecimal14(BigDecimal attributeDecimal14) {
		this.attributeDecimal14 = attributeDecimal14;
	}
    /**
     * @return 小数扩展字段15
     */
	public BigDecimal getAttributeDecimal15() {
		return attributeDecimal15;
	}

	public void setAttributeDecimal15(BigDecimal attributeDecimal15) {
		this.attributeDecimal15 = attributeDecimal15;
	}
    /**
     * @return 小数扩展字段16
     */
	public BigDecimal getAttributeDecimal16() {
		return attributeDecimal16;
	}

	public void setAttributeDecimal16(BigDecimal attributeDecimal16) {
		this.attributeDecimal16 = attributeDecimal16;
	}
    /**
     * @return 小数扩展字段17
     */
	public BigDecimal getAttributeDecimal17() {
		return attributeDecimal17;
	}

	public void setAttributeDecimal17(BigDecimal attributeDecimal17) {
		this.attributeDecimal17 = attributeDecimal17;
	}
    /**
     * @return 小数扩展字段18
     */
	public BigDecimal getAttributeDecimal18() {
		return attributeDecimal18;
	}

	public void setAttributeDecimal18(BigDecimal attributeDecimal18) {
		this.attributeDecimal18 = attributeDecimal18;
	}
    /**
     * @return 小数扩展字段19
     */
	public BigDecimal getAttributeDecimal19() {
		return attributeDecimal19;
	}

	public void setAttributeDecimal19(BigDecimal attributeDecimal19) {
		this.attributeDecimal19 = attributeDecimal19;
	}
    /**
     * @return 小数扩展字段20
     */
	public BigDecimal getAttributeDecimal20() {
		return attributeDecimal20;
	}

	public void setAttributeDecimal20(BigDecimal attributeDecimal20) {
		this.attributeDecimal20 = attributeDecimal20;
	}
    /**
     * @return 小数扩展字段21
     */
	public BigDecimal getAttributeDecimal21() {
		return attributeDecimal21;
	}

	public void setAttributeDecimal21(BigDecimal attributeDecimal21) {
		this.attributeDecimal21 = attributeDecimal21;
	}
    /**
     * @return 小数扩展字段22
     */
	public BigDecimal getAttributeDecimal22() {
		return attributeDecimal22;
	}

	public void setAttributeDecimal22(BigDecimal attributeDecimal22) {
		this.attributeDecimal22 = attributeDecimal22;
	}
    /**
     * @return 小数扩展字段23
     */
	public BigDecimal getAttributeDecimal23() {
		return attributeDecimal23;
	}

	public void setAttributeDecimal23(BigDecimal attributeDecimal23) {
		this.attributeDecimal23 = attributeDecimal23;
	}
    /**
     * @return 小数扩展字段24
     */
	public BigDecimal getAttributeDecimal24() {
		return attributeDecimal24;
	}

	public void setAttributeDecimal24(BigDecimal attributeDecimal24) {
		this.attributeDecimal24 = attributeDecimal24;
	}
    /**
     * @return 小数扩展字段25
     */
	public BigDecimal getAttributeDecimal25() {
		return attributeDecimal25;
	}

	public void setAttributeDecimal25(BigDecimal attributeDecimal25) {
		this.attributeDecimal25 = attributeDecimal25;
	}
    /**
     * @return 小数扩展字段26
     */
	public BigDecimal getAttributeDecimal26() {
		return attributeDecimal26;
	}

	public void setAttributeDecimal26(BigDecimal attributeDecimal26) {
		this.attributeDecimal26 = attributeDecimal26;
	}
    /**
     * @return 小数扩展字段27
     */
	public BigDecimal getAttributeDecimal27() {
		return attributeDecimal27;
	}

	public void setAttributeDecimal27(BigDecimal attributeDecimal27) {
		this.attributeDecimal27 = attributeDecimal27;
	}
    /**
     * @return 小数扩展字段28
     */
	public BigDecimal getAttributeDecimal28() {
		return attributeDecimal28;
	}

	public void setAttributeDecimal28(BigDecimal attributeDecimal28) {
		this.attributeDecimal28 = attributeDecimal28;
	}
    /**
     * @return 小数扩展字段29
     */
	public BigDecimal getAttributeDecimal29() {
		return attributeDecimal29;
	}

	public void setAttributeDecimal29(BigDecimal attributeDecimal29) {
		this.attributeDecimal29 = attributeDecimal29;
	}
    /**
     * @return 小数扩展字段30
     */
	public BigDecimal getAttributeDecimal30() {
		return attributeDecimal30;
	}

	public void setAttributeDecimal30(BigDecimal attributeDecimal30) {
		this.attributeDecimal30 = attributeDecimal30;
	}
    /**
     * @return 日期时间扩展字段1
     */
	public Date getAttributeDatetime1() {
		return attributeDatetime1;
	}

	public void setAttributeDatetime1(Date attributeDatetime1) {
		this.attributeDatetime1 = attributeDatetime1;
	}
    /**
     * @return 日期时间扩展字段2
     */
	public Date getAttributeDatetime2() {
		return attributeDatetime2;
	}

	public void setAttributeDatetime2(Date attributeDatetime2) {
		this.attributeDatetime2 = attributeDatetime2;
	}
    /**
     * @return 日期时间扩展字段3
     */
	public Date getAttributeDatetime3() {
		return attributeDatetime3;
	}

	public void setAttributeDatetime3(Date attributeDatetime3) {
		this.attributeDatetime3 = attributeDatetime3;
	}
    /**
     * @return 日期时间扩展字段4
     */
	public Date getAttributeDatetime4() {
		return attributeDatetime4;
	}

	public void setAttributeDatetime4(Date attributeDatetime4) {
		this.attributeDatetime4 = attributeDatetime4;
	}
    /**
     * @return 日期时间扩展字段5
     */
	public Date getAttributeDatetime5() {
		return attributeDatetime5;
	}

	public void setAttributeDatetime5(Date attributeDatetime5) {
		this.attributeDatetime5 = attributeDatetime5;
	}
    /**
     * @return 日期时间扩展字段6
     */
	public Date getAttributeDatetime6() {
		return attributeDatetime6;
	}

	public void setAttributeDatetime6(Date attributeDatetime6) {
		this.attributeDatetime6 = attributeDatetime6;
	}
    /**
     * @return 日期时间扩展字段7
     */
	public Date getAttributeDatetime7() {
		return attributeDatetime7;
	}

	public void setAttributeDatetime7(Date attributeDatetime7) {
		this.attributeDatetime7 = attributeDatetime7;
	}
    /**
     * @return 日期时间扩展字段8
     */
	public Date getAttributeDatetime8() {
		return attributeDatetime8;
	}

	public void setAttributeDatetime8(Date attributeDatetime8) {
		this.attributeDatetime8 = attributeDatetime8;
	}
    /**
     * @return 日期时间扩展字段9
     */
	public Date getAttributeDatetime9() {
		return attributeDatetime9;
	}

	public void setAttributeDatetime9(Date attributeDatetime9) {
		this.attributeDatetime9 = attributeDatetime9;
	}
    /**
     * @return 日期时间扩展字段10
     */
	public Date getAttributeDatetime10() {
		return attributeDatetime10;
	}

	public void setAttributeDatetime10(Date attributeDatetime10) {
		this.attributeDatetime10 = attributeDatetime10;
	}
    /**
     * @return 日期时间扩展字段11
     */
	public Date getAttributeDatetime11() {
		return attributeDatetime11;
	}

	public void setAttributeDatetime11(Date attributeDatetime11) {
		this.attributeDatetime11 = attributeDatetime11;
	}
    /**
     * @return 日期时间扩展字段12
     */
	public Date getAttributeDatetime12() {
		return attributeDatetime12;
	}

	public void setAttributeDatetime12(Date attributeDatetime12) {
		this.attributeDatetime12 = attributeDatetime12;
	}
    /**
     * @return 日期时间扩展字段13
     */
	public Date getAttributeDatetime13() {
		return attributeDatetime13;
	}

	public void setAttributeDatetime13(Date attributeDatetime13) {
		this.attributeDatetime13 = attributeDatetime13;
	}
    /**
     * @return 日期时间扩展字段14
     */
	public Date getAttributeDatetime14() {
		return attributeDatetime14;
	}

	public void setAttributeDatetime14(Date attributeDatetime14) {
		this.attributeDatetime14 = attributeDatetime14;
	}
    /**
     * @return 日期时间扩展字段15
     */
	public Date getAttributeDatetime15() {
		return attributeDatetime15;
	}

	public void setAttributeDatetime15(Date attributeDatetime15) {
		this.attributeDatetime15 = attributeDatetime15;
	}
    /**
     * @return 日期时间扩展字段16
     */
	public Date getAttributeDatetime16() {
		return attributeDatetime16;
	}

	public void setAttributeDatetime16(Date attributeDatetime16) {
		this.attributeDatetime16 = attributeDatetime16;
	}
    /**
     * @return 日期时间扩展字段17
     */
	public Date getAttributeDatetime17() {
		return attributeDatetime17;
	}

	public void setAttributeDatetime17(Date attributeDatetime17) {
		this.attributeDatetime17 = attributeDatetime17;
	}
    /**
     * @return 日期时间扩展字段18
     */
	public Date getAttributeDatetime18() {
		return attributeDatetime18;
	}

	public void setAttributeDatetime18(Date attributeDatetime18) {
		this.attributeDatetime18 = attributeDatetime18;
	}
    /**
     * @return 日期时间扩展字段19
     */
	public Date getAttributeDatetime19() {
		return attributeDatetime19;
	}

	public void setAttributeDatetime19(Date attributeDatetime19) {
		this.attributeDatetime19 = attributeDatetime19;
	}
    /**
     * @return 日期时间扩展字段20
     */
	public Date getAttributeDatetime20() {
		return attributeDatetime20;
	}

	public void setAttributeDatetime20(Date attributeDatetime20) {
		this.attributeDatetime20 = attributeDatetime20;
	}
    /**
     * @return 实际完成时间（供方入围）（融创）
     */
	public Date getAttributeDate1() {
		return attributeDate1;
	}

	public void setAttributeDate1(Date attributeDate1) {
		this.attributeDate1 = attributeDate1;
	}
    /**
     * @return 实际完成时间（立项审批）（融创）
     */
	public Date getAttributeDate2() {
		return attributeDate2;
	}

	public void setAttributeDate2(Date attributeDate2) {
		this.attributeDate2 = attributeDate2;
	}
    /**
     * @return 实际完成时间（发标时间）（融创）
     */
	public Date getAttributeDate3() {
		return attributeDate3;
	}

	public void setAttributeDate3(Date attributeDate3) {
		this.attributeDate3 = attributeDate3;
	}
    /**
     * @return 实际完成时间（定标时间）（融创）
     */
	public Date getAttributeDate4() {
		return attributeDate4;
	}

	public void setAttributeDate4(Date attributeDate4) {
		this.attributeDate4 = attributeDate4;
	}
    /**
     * @return 实际完成时间（合同完成时间）（融创）
     */
	public Date getAttributeDate5() {
		return attributeDate5;
	}

	public void setAttributeDate5(Date attributeDate5) {
		this.attributeDate5 = attributeDate5;
	}
    /**
     * @return 日期扩展字段6
     */
	public Date getAttributeDate6() {
		return attributeDate6;
	}

	public void setAttributeDate6(Date attributeDate6) {
		this.attributeDate6 = attributeDate6;
	}
    /**
     * @return 日期扩展字段7
     */
	public Date getAttributeDate7() {
		return attributeDate7;
	}

	public void setAttributeDate7(Date attributeDate7) {
		this.attributeDate7 = attributeDate7;
	}
    /**
     * @return 日期扩展字段8
     */
	public Date getAttributeDate8() {
		return attributeDate8;
	}

	public void setAttributeDate8(Date attributeDate8) {
		this.attributeDate8 = attributeDate8;
	}
    /**
     * @return 日期扩展字段9
     */
	public Date getAttributeDate9() {
		return attributeDate9;
	}

	public void setAttributeDate9(Date attributeDate9) {
		this.attributeDate9 = attributeDate9;
	}
    /**
     * @return 日期扩展字段10
     */
	public Date getAttributeDate10() {
		return attributeDate10;
	}

	public void setAttributeDate10(Date attributeDate10) {
		this.attributeDate10 = attributeDate10;
	}
    /**
     * @return 日期扩展字段11
     */
	public Date getAttributeDate11() {
		return attributeDate11;
	}

	public void setAttributeDate11(Date attributeDate11) {
		this.attributeDate11 = attributeDate11;
	}
    /**
     * @return 日期扩展字段12
     */
	public Date getAttributeDate12() {
		return attributeDate12;
	}

	public void setAttributeDate12(Date attributeDate12) {
		this.attributeDate12 = attributeDate12;
	}
    /**
     * @return 日期扩展字段13
     */
	public Date getAttributeDate13() {
		return attributeDate13;
	}

	public void setAttributeDate13(Date attributeDate13) {
		this.attributeDate13 = attributeDate13;
	}
    /**
     * @return 日期扩展字段14
     */
	public Date getAttributeDate14() {
		return attributeDate14;
	}

	public void setAttributeDate14(Date attributeDate14) {
		this.attributeDate14 = attributeDate14;
	}
    /**
     * @return 日期扩展字段15
     */
	public Date getAttributeDate15() {
		return attributeDate15;
	}

	public void setAttributeDate15(Date attributeDate15) {
		this.attributeDate15 = attributeDate15;
	}
    /**
     * @return 日期扩展字段16
     */
	public Date getAttributeDate16() {
		return attributeDate16;
	}

	public void setAttributeDate16(Date attributeDate16) {
		this.attributeDate16 = attributeDate16;
	}
    /**
     * @return 日期扩展字段17
     */
	public Date getAttributeDate17() {
		return attributeDate17;
	}

	public void setAttributeDate17(Date attributeDate17) {
		this.attributeDate17 = attributeDate17;
	}
    /**
     * @return 日期扩展字段18
     */
	public Date getAttributeDate18() {
		return attributeDate18;
	}

	public void setAttributeDate18(Date attributeDate18) {
		this.attributeDate18 = attributeDate18;
	}
    /**
     * @return 日期扩展字段19
     */
	public Date getAttributeDate19() {
		return attributeDate19;
	}

	public void setAttributeDate19(Date attributeDate19) {
		this.attributeDate19 = attributeDate19;
	}
    /**
     * @return 日期扩展字段20
     */
	public Date getAttributeDate20() {
		return attributeDate20;
	}

	public void setAttributeDate20(Date attributeDate20) {
		this.attributeDate20 = attributeDate20;
	}
    /**
     * @return 执行策略
     */
	public String getExecutionStrategyCode() {
		return executionStrategyCode;
	}

	public void setExecutionStrategyCode(String executionStrategyCode) {
		this.executionStrategyCode = executionStrategyCode;
	}
    /**
     * @return 预算科目ID
     */
	public Long getBudgetAccountId() {
		return budgetAccountId;
	}

	public void setBudgetAccountId(Long budgetAccountId) {
		this.budgetAccountId = budgetAccountId;
	}
    /**
     * @return 收货信息
     */
	public String getReceiverInformation() {
		return receiverInformation;
	}

	public void setReceiverInformation(String receiverInformation) {
		this.receiverInformation = receiverInformation;
	}
    /**
     * @return 
     */
	public Integer getBusinessCardFlag() {
		return businessCardFlag;
	}

	public void setBusinessCardFlag(Integer businessCardFlag) {
		this.businessCardFlag = businessCardFlag;
	}
    /**
     * @return 收货地址
     */
	public String getReceiveAddress() {
		return receiveAddress;
	}

	public void setReceiveAddress(String receiveAddress) {
		this.receiveAddress = receiveAddress;
	}
    /**
     * @return 收货联系人
     */
	public String getReceiveContactName() {
		return receiveContactName;
	}

	public void setReceiveContactName(String receiveContactName) {
		this.receiveContactName = receiveContactName;
	}
    /**
     * @return 收货联系电话
     */
	public String getReceiveTelNum() {
		return receiveTelNum;
	}

	public void setReceiveTelNum(String receiveTelNum) {
		this.receiveTelNum = receiveTelNum;
	}
    /**
     * @return 卡片用户ID
     */
	public Long getCartUserId() {
		return cartUserId;
	}

	public void setCartUserId(Long cartUserId) {
		this.cartUserId = cartUserId;
	}
    /**
     * @return 卡片类型 GROUP/EMPLOYEE
     */
	public String getCartUserType() {
		return cartUserType;
	}

	public void setCartUserType(String cartUserType) {
		this.cartUserType = cartUserType;
	}
    /**
     * @return 自动生成订单成功还是失败
     */
	public String getChangeOrderCode() {
		return changeOrderCode;
	}

	public void setChangeOrderCode(String changeOrderCode) {
		this.changeOrderCode = changeOrderCode;
	}
    /**
     * @return 自动生成订单报错信息
     */
	public String getChangeOrderMessage() {
		return changeOrderMessage;
	}

	public void setChangeOrderMessage(String changeOrderMessage) {
		this.changeOrderMessage = changeOrderMessage;
	}
    /**
     * @return 预算部门编号
     */
	public String getBudgetAccountDeptno() {
		return budgetAccountDeptno;
	}

	public void setBudgetAccountDeptno(String budgetAccountDeptno) {
		this.budgetAccountDeptno = budgetAccountDeptno;
	}
    /**
     * @return 预算金额
     */
	public BigDecimal getBudgetAccountPrice() {
		return budgetAccountPrice;
	}

	public void setBudgetAccountPrice(BigDecimal budgetAccountPrice) {
		this.budgetAccountPrice = budgetAccountPrice;
	}
    /**
     * @return 汇率
     */
	public BigDecimal getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(BigDecimal exchangeRate) {
		this.exchangeRate = exchangeRate;
	}
    /**
     * @return 本币含税单价
     */
	public BigDecimal getLocalCurrencyTaxUnit() {
		return localCurrencyTaxUnit;
	}

	public void setLocalCurrencyTaxUnit(BigDecimal localCurrencyTaxUnit) {
		this.localCurrencyTaxUnit = localCurrencyTaxUnit;
	}
    /**
     * @return 本币不含税单价
     */
	public BigDecimal getLocalCurrencyNoTaxUnit() {
		return localCurrencyNoTaxUnit;
	}

	public void setLocalCurrencyNoTaxUnit(BigDecimal localCurrencyNoTaxUnit) {
		this.localCurrencyNoTaxUnit = localCurrencyNoTaxUnit;
	}
    /**
     * @return 本币含税金额
     */
	public BigDecimal getLocalCurrencyTaxSum() {
		return localCurrencyTaxSum;
	}

	public void setLocalCurrencyTaxSum(BigDecimal localCurrencyTaxSum) {
		this.localCurrencyTaxSum = localCurrencyTaxSum;
	}
    /**
     * @return 本币不含税金额
     */
	public BigDecimal getLocalCurrencyNoTaxSum() {
		return localCurrencyNoTaxSum;
	}

	public void setLocalCurrencyNoTaxSum(BigDecimal localCurrencyNoTaxSum) {
		this.localCurrencyNoTaxSum = localCurrencyNoTaxSum;
	}
    /**
     * @return 预算单据ID
     */
	public String getDocumentId() {
		return documentId;
	}

	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}
    /**
     * @return 汇率日期
     */
	public Date getExchangeRateDate() {
		return exchangeRateDate;
	}

	public void setExchangeRateDate(Date exchangeRateDate) {
		this.exchangeRateDate = exchangeRateDate;
	}
    /**
     * @return 预算科目编码
     */
	public String getBudgetAccountNum() {
		return budgetAccountNum;
	}

	public void setBudgetAccountNum(String budgetAccountNum) {
		this.budgetAccountNum = budgetAccountNum;
	}
    /**
     * @return 申请取消时，拆分前的申请行ID
     */
	public Long getBeforeSplitPrLineId() {
		return beforeSplitPrLineId;
	}

	public void setBeforeSplitPrLineId(Long beforeSplitPrLineId) {
		this.beforeSplitPrLineId = beforeSplitPrLineId;
	}

}
