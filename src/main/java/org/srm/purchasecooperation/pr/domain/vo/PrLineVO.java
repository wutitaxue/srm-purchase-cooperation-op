package org.srm.purchasecooperation.pr.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;
import javax.persistence.Transient;
import org.hzero.boot.platform.lov.annotation.LovValue;
import org.hzero.core.base.BaseConstants.Flag;
import org.hzero.core.cache.CacheValue;
import org.hzero.core.cache.Cacheable;
import org.hzero.core.cache.CacheValue.DataStructure;
import org.hzero.mybatis.domian.SecurityToken;
import org.hzero.starter.keyencrypt.core.Encrypt;
import org.springframework.format.annotation.DateTimeFormat;
import org.srm.common.mybatis.domain.ExpandDomain;
import org.srm.purchasecooperation.pr.domain.entity.PrHeader;
import org.srm.purchasecooperation.pr.domain.entity.PrLine;
import org.srm.purchasecooperation.pr.domain.entity.PrLineSupplier;

@JsonInclude(Include.NON_NULL)
public class PrLineVO extends ExpandDomain implements Cacheable {
    @ApiModelProperty("表ID，主键，供其他表做外键")
    @Encrypt
    private Long prLineId;
    @ApiModelProperty("采购申请头ID")
    @Encrypt
    private Long prHeaderId;
    @ApiModelProperty("租户ID")
    private Long tenantId;
    @ApiModelProperty("行号")
    private Long lineNum;
    @ApiModelProperty("展示行号")
    private String displayLineNum;
    @ApiModelProperty("公司ID")
    @Encrypt
    private Long companyId;
    @ApiModelProperty("库存组织ID")
    @Encrypt
    private Long invOrganizationId;
    @ApiModelProperty("物料ID")
    @Encrypt
    private Long itemId;
    @ApiModelProperty("物料编码")
    private String itemCode;
    @ApiModelProperty("物料名称")
    private String itemName;
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
    @Encrypt
    private Long categoryId;
    @ApiModelProperty("单位ID")
    @Encrypt
    private Long uomId;
    @ApiModelProperty("数量")
    private BigDecimal quantity;
    @ApiModelProperty("税率ID")
    @Encrypt
    private Long taxId;
    @ApiModelProperty("税率")
    private BigDecimal taxRate;
    @ApiModelProperty("币种")
    private String currencyCode;
    @ApiModelProperty("含税单价")
    private BigDecimal taxIncludedUnitPrice;
    @ApiModelProperty("不含税单价")
    private BigDecimal unitPrice;
    @ApiModelProperty("行金额")
    private BigDecimal lineAmount;
    @ApiModelProperty("含税行金额")
    private BigDecimal taxIncludedLineAmount;
    @DateTimeFormat(
            pattern = "yyyy-MM-dd"
    )
    @JsonFormat(
            pattern = "yyyy-MM-dd"
    )
    @ApiModelProperty("需求日期")
    private Date neededDate;
    @ApiModelProperty("供应商租户ID")
    private Long supplierTenantId;
    @ApiModelProperty("供应商ID")
    @Encrypt
    private Long supplierId;
    @ApiModelProperty("供应商编码")
    private String supplierCode;
    @ApiModelProperty("供应商名称")
    private String supplierName;
    @ApiModelProperty("来源平台")
    @LovValue("SPRM.SRC_PLATFORM")
    private String prSourcePlatform;
    @ApiModelProperty("供应商公司ID")
    @Encrypt
    private Long supplierCompanyId;
    @ApiModelProperty("供应商公司描述")
    private String supplierCompanyName;
    @ApiModelProperty("执行状态代码")
    @LovValue(
            lovCode = "SPRM.PR_EXECUTION_STATUS",
            meaningField = "executionStatusMeaning"
    )
    private String executionStatusCode;
    @DateTimeFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @ApiModelProperty("执行时间")
    private Date executedDate;
    @ApiModelProperty("执行人ID")
    @Encrypt
    private Long executedBy;
    @ApiModelProperty("目标单据ID")
    private Long executionBillId;
    @ApiModelProperty("目标单据编码")
    private String executionBillNum;
    @ApiModelProperty("分配标识")
    @LovValue(
            lovCode = "HPFM.FLAG",
            meaningField = "assignedFlagMeaning"
    )
    private Integer assignedFlag;
    @DateTimeFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @ApiModelProperty("分配时间")
    private Date assignedDate;
    @ApiModelProperty("分配人ID")
    private Long assignedBy;
    @ApiModelProperty("分配原因")
    private String assignedRemark;
    @LovValue("SPRM.LINE_CANCEL_STATUS")
    @ApiModelProperty("取消标识")
    private Integer cancelledFlag;
    @ApiModelProperty("行运费")
    private BigDecimal lineFreight;
    @DateTimeFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @ApiModelProperty("取消时间")
    private Date cancelledDate;
    @ApiModelProperty("取消人ID")
    private Long cancelledBy;
    @ApiModelProperty("取消原因")
    private String cancelledRemark;
    @LovValue("SPRM.LINE_CLOSE_STATUS")
    @ApiModelProperty("关闭标识")
    private Integer closedFlag;
    @DateTimeFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @ApiModelProperty("关闭时间")
    private Date closedDate;
    @ApiModelProperty("关闭人ID")
    private Long closedBy;
    @ApiModelProperty("关闭原因")
    private String closedRemark;
    @ApiModelProperty("附件UUID,功能: sprm-pr")
    private String attachmentUuid;
    @ApiModelProperty("行备注")
    private String remark;
    @ApiModelProperty("头备注")
    private String headerRemark;
    @ApiModelProperty("可开专票标识")
    private Integer canVatFlag;
    @ApiModelProperty("是否启用单位控制")
    private Integer unitFlag;
    @ApiModelProperty("目标头单据ID")
    private Long executionHeaderBillId;
    @ApiModelProperty("目标头单据编码")
    private String executionHeaderBillNum;
    @ApiModelProperty("库房名称")
    private String inventoryName;
    @ApiModelProperty("暂挂说明")
    private String suspendRemark;
    @ApiModelProperty("暂挂标识")
    private Integer suspendFlag;
    @ApiModelProperty("行状态")
    @LovValue(
            lovCode = "SPRM.PR_STATUS",
            meaningField = "prLineStatusCodeMeaning"
    )
    private String prLineStatusCode;
    private String prLineStatusCodeMeaning;
    @ApiModelProperty("异常标志")
    private Integer incorrectFlag;
    @ApiModelProperty("异常发生时间")
    private Date incorrectDate;
    @ApiModelProperty("异常信息")
    private String incorrectMsg;
    @ApiModelProperty("ERP编辑状态")
    private String erpEditStatus;
    @ApiModelProperty("加急标识")
    private Integer urgentFlag;
    @ApiModelProperty("加急日期")
    private Date urgentDate;
    @ApiModelProperty("成本中心主键")
    @Encrypt
    private Long costId;
    @ApiModelProperty("成本中心编码")
    private String costCode;
    @ApiModelProperty("总账主键")
    @Encrypt
    private Long accountSubjectId;
    @ApiModelProperty("wbs编码")
    private String wbsCode;
    @ApiModelProperty("外部总账编码")
    private String accountSubjectNum;
    @ApiModelProperty("工作分解结构")
    private String wbs;
    @ApiModelProperty("订单行ID")
    @Encrypt
    private Long PoLineId;
    @ApiModelProperty("上次采购价格")
    private BigDecimal lastPurchasePrice;
    @ApiModelProperty("含税单价(不含运费)")
    private BigDecimal taxWithoutFreightPrice;
    @ApiModelProperty("占用数量")
    private BigDecimal occupiedQuantity;
    @ApiModelProperty("账户分配类别ID")
    @Encrypt
    private Long accountAssignTypeId;
    @ApiModelProperty("价格批量(每)")
    private BigDecimal unitPriceBatch;
    @LovValue(
            lovCode = "SPUC.PR_LINE_PROJECT_CATEHORY",
            meaningField = "projectCategoryMeaning"
    )
    @ApiModelProperty("项目类别")
    private String projectCategory;
    @ApiModelProperty("资产")
    private String assets;
    @ApiModelProperty("资产子编号")
    private String assetChildNum;
    @ApiModelProperty("新电商运费行标识")
    private Integer freightLineFlag;
    @ApiModelProperty("质量标准")
    private String qualityStandard;
    @ApiModelProperty("预算内外标识")
    private Integer budgetIoFlag;
    @ApiModelProperty("预算单价(含税)")
    private BigDecimal taxIncludedBudgetUnitPrice;
    @ApiModelProperty("预算科目ID")
    @Encrypt
    private Long budgetAccountId;
    @ApiModelProperty("收货地址")
    private String receiveAddress;
    @ApiModelProperty("收货联系人")
    private String receiveContactName;
    @ApiModelProperty("收货联系电话")
    private String receiveTelNum;
    @ApiModelProperty("预算部门编号")
    private String budgetAccountDeptno;
    @ApiModelProperty("预算金额")
    private BigDecimal budgetAccountPrice;
    @ApiModelProperty("预算科目编码")
    private String budgetAccountNum;
    @ApiModelProperty("预算科目名称")
    private String budgetAccountName;
    @ApiModelProperty("预算部门Id")
    private Long budgetAccountDeptId;
    @ApiModelProperty("预算部门名称")
    private String budgetAccountDeptName;
    @ApiModelProperty("项目类别含义")
    private String projectCategoryMeaning;
    @ApiModelProperty("采购申请编号")
    private String prNum;
    @ApiModelProperty("采购申请编号")
    private String displayPrNum;
    @ApiModelProperty(" 库存组织名称")
    private String invOrganizationName;
    @ApiModelProperty("自主品类名称")
    private String categoryName;
    @ApiModelProperty("单位代码")
    private String uomCode;
    @ApiModelProperty("单位名称")
    private String uomName;
    @ApiModelProperty("公司名称")
    private String companyName;
    @ApiModelProperty("业务实体名称")
    private String ouName;
    @ApiModelProperty("税率代码")
    private String taxCode;
    @ApiModelProperty("币种名称")
    private String currencyName;
    @ApiModelProperty("执行人")
    private String executorName;
    @ApiModelProperty("创建人id")
    private Long createdBy;
    @CacheValue(
            db = 1,
            key = "hiam:user",
            primaryKey = "createdBy",
            searchKey = "realName",
            structure = DataStructure.MAP_OBJECT
    )
    @ApiModelProperty("创建人")
    private String creatorName;
    @ApiModelProperty("申请人编码")
    private String prRequestedNum;
    @ApiModelProperty("申请人名称")
    private String prRequestedName;
    @ApiModelProperty("采购组织名称")
    private String purchaseOrgName;
    @ApiModelProperty("采购员名称")
    private String purchaseAgentName;
    @ApiModelProperty("ERP状态")
    private String erpStatus;
    @ApiModelProperty("采购申请头目标单据ID")
    private Long headerExecutionBillId;
    @ApiModelProperty("采购申请头目标单据编码")
    private String headerExecutionBillNum;
    @ApiModelProperty("采购申请头同步ERP状态代码")
    @LovValue("SPRM.PR_SYNC_ERP_STATUS")
    private String headerSyncStatus;
    @ApiModelProperty("采购申请头同步ERP反馈信息")
    private String headerSyncResponseMsg;
    @ApiModelProperty("划线价")
    private BigDecimal jdPrice;
    @ApiModelProperty("表面处理字段")
    private String surfaceTreatFlag;
    @ApiModelProperty("同步ERP返回信息")
    private String syncResponseMsg;
    @ApiModelProperty("是否已变更")
    private Integer changedFlag;
    @ApiModelProperty("账户分配类别编码")
    private String accountAssignTypeCode;
    @ApiModelProperty("发票抬头")
    private String invoiceTitle;
    @ApiModelProperty("开票主体id(开票公司)")
    @Encrypt
    private Long invoiceCompanyId;
    @ApiModelProperty("开票主体名称(开票公司)")
    private String invoiceCompanyName;
    @ApiModelProperty("自动创建订单状态")
    private String changeOrderCode;
    @ApiModelProperty("自动创建订单返回信息")
    private String changeOrderMessage;
    @Transient
    @ApiModelProperty("精度(单价)")
    private Integer defaultPrecision;
    @Transient
    @ApiModelProperty("财务精度")
    private Integer financialPrecision;
    @ApiModelProperty("行状态含义")
    private String prLineStatusMeaning;
    @ApiModelProperty("来源平台含义")
    private String prSourcePlatformMeaning;
    @ApiModelProperty("执行状态含义")
    private String executionStatusMeaning;
    @ApiModelProperty("取消标识含义")
    private String cancelledFlagMeaning;
    @ApiModelProperty("关闭标识含义")
    private String closedFlagMeaning;
    @ApiModelProperty("分配标识含义")
    private String assignedFlagMeaning;
    @ApiModelProperty("头同步ERP状态含义")
    private String headerSyncStatusMeaning;
    @ApiModelProperty("采购申请头状态含义")
    private String prHeaderStatusMeaning;
    @ApiModelProperty("申请日期")
    private Date requestDate;
    @ApiModelProperty("申请人ID")
    private Long requestedBy;
    @ApiModelProperty("业务实体ID")
    @Encrypt
    private Long ouId;
    @ApiModelProperty("采购组织ID")
    @Encrypt
    private Long purchaseOrgId;
    @ApiModelProperty("采购员ID")
    @Encrypt
    private Long purchaseAgentId;
    @ApiModelProperty("物料ABC属性")
    private String itemAbcClass;
    @ApiModelProperty("图号")
    private String drawingNum;
    @ApiModelProperty("项目号")
    private String projectNum;
    @ApiModelProperty("项目名称")
    private String projectName;
    @ApiModelProperty("项目号车号")
    private String craneNum;
    @ApiModelProperty("所属部门ID")
    @Encrypt
    private Long unitId;
    @ApiModelProperty("所属部门名称")
    private String unitName;
    @ApiModelProperty("成本中心名称")
    private String costName;
    @ApiModelProperty("总账科目名称")
    private String accountSubjectName;
    @ApiModelProperty("采购协议头ID")
    @Encrypt
    private Long pcHeaderId;
    @ApiModelProperty("型号")
    private String itemModel;
    @ApiModelProperty("规格")
    private String itemSpecs;
    @ApiModelProperty("属性")
    @LovValue(
            lovCode = "SPUC.PR_LINE_ITEM_PROPERTIE",
            meaningField = "itemPropertiesMeaning"
    )
    private String itemProperties;
    private String itemPropertiesMeaning;
    @ApiModelProperty("采购员ID")
    @Encrypt
    private Long agentId;
    @ApiModelProperty("保管人用户ID")
    private Long keeperUserId;
    @ApiModelProperty("验收人用户ID")
    private Long accepterUserId;
    @ApiModelProperty("验收人用户ID")
    private Long costPayerUserId;
    @LovValue(
            lovCode = "SCUX.SPRM.PR_LINE.ADDRESS",
            meaningField = "addressMeaning"
    )
    private String address;
    private String addressMeaning;
    @ApiModelProperty("内部订单号")
    private String innerPoNum;
    @Encrypt
    private Long prTypeId;
    private String prTypeCode;
    private String prTypeName;
    @Encrypt
    private Long headerInventoryId;
    private String headerInventoryName;
    @Encrypt
    private String parentUnitId;
    @Encrypt
    private Long headerCategoryId;
    private String headerCategoryName;
    private String pcNum;
    private String parentUnitName;
    private String keeperUserName;
    private String accepterUserName;
    private String costPayerUserName;
    private String agentName;
    @Encrypt
    private Long inventoryId;
    @ApiModelProperty("基准价")
    private BigDecimal benchmarkPrice;
    @ApiModelProperty("涨跌幅")
    private BigDecimal changePercent;
    private String changePercentMeaning;
    @ApiModelProperty("图纸版本")
    private String drawingVersion;
    @ApiModelProperty("供应商料号")
    private String supplierItemCode;
    @ApiModelProperty("费用承担部门id")
    @Encrypt
    private Long expBearDepId;
    @ApiModelProperty("费用承担部门")
    private String expBearDep;
    @ApiModelProperty("费用承担部门编码")
    private String expBearDepCode;
    @Encrypt
    private Long projectId;
    @ApiModelProperty("费用挂靠部门")
    private String expenseUnitName;
    @ApiModelProperty("供应商料号描述")
    private String supplierItemName;
    @ApiModelProperty("标题")
    private String title;
    @ApiModelProperty("账户分配类别的必填字段名集合")
    private Set<String> assignTypeRequiredFieldNames;
    @ApiModelProperty("已经分配的申请头")
    private List<PrHeader> prHeaderAssignList;
    @ApiModelProperty("采购订单号集合")
    @Transient
    private List<String> poNumList;
    @ApiModelProperty("送货单Id集合")
    @Transient
    @Encrypt
    private List<Long> asnLineIdList;
    @ApiModelProperty("订单导入erp成功标识，1/0 成功/失败")
    private Integer poSyncSuccessFlag;
    @ApiModelProperty("订单导入erp反馈信息")
    private String poSyncMessage;
    @ApiModelProperty("执行策略")
    @LovValue(
            lovCode = "SPRM.EXECUTION_STRATEGY",
            meaningField = "executionStrategyMeaning"
    )
    private String executionStrategyCode;
    @ApiModelProperty("执行策略")
    private String executionStrategyMeaning;
    @ApiModelProperty("是否可显示参考价格")
    private Integer referencePriceDisplayFlag;
    @ApiModelProperty("是否已转")
    private Integer transferFlag;
    @ApiModelProperty("是否占用标识 1 已转单")
    private Integer occupyFlag;
    private String headAttachmentUuid;
    @ApiModelProperty("业务实体编码")
    private String ouCode;
    @ApiModelProperty("公司编码")
    private String companyNum;
    @ApiModelProperty("外部需求执行人 分配优先级最高")
    @Transient
    private List<Long> assignUserIds;
    @ApiModelProperty("采购供应商集合")
    private List<PrLineSupplier> supplierList;
    @ApiModelProperty("询报价执行状态")
    @LovValue(
            lovCode = "SPRM.PR_LINE_RFX_EXEC_STA",
            meaningField = "rfxStatusMeaning"
    )
    private String rfxStatus;
    @ApiModelProperty("询报价执行状态含义")
    private String rfxStatusMeaning;
    @ApiModelProperty("询报价执行数量")
    private BigDecimal rfxQuantity;
    @ApiModelProperty("招投标执行状态")
    @LovValue(
            lovCode = "SPRM.PR_LINE_BID_EXEC_STA",
            meaningField = "bidStatusMeaning"
    )
    private String bidStatus;
    @ApiModelProperty("招投标执行状态含义")
    private String bidStatusMeaning;
    @ApiModelProperty("招投标执行数量")
    private BigDecimal bidQuantity;
    @ApiModelProperty("立项执行状态")
    @LovValue(
            lovCode = "SPRM.PR_LINE_PROJECT_EXEC_STA",
            meaningField = "projectStatusMeaning"
    )
    private String projectStatus;
    @ApiModelProperty("立项执行状态含义")
    private String projectStatusMeaning;
    @ApiModelProperty("立项执行数量")
    private BigDecimal projectQuantity;
    @ApiModelProperty("协议执行状态")
    @LovValue(
            lovCode = "SPRM.PR_LINE_CONTRACT_EXEC_STA",
            meaningField = "contractStatusMeaning"
    )
    private String contractStatus;
    @ApiModelProperty("协议执行状态含义")
    private String contractStatusMeaning;
    @ApiModelProperty("协议执行数量")
    private BigDecimal contractQuantity;
    @ApiModelProperty("订单执行状态")
    @LovValue(
            lovCode = "SPRM.PR_LINE_ORDER_EXEC_STA",
            meaningField = "orderStatusMeaning"
    )
    private String orderStatus;
    @ApiModelProperty("订单执行状态含义")
    private String orderStatusMeaning;
    @ApiModelProperty("订单执行数量")
    private BigDecimal orderQuantity;
    @ApiModelProperty("送货执行状态")
    @LovValue(
            lovCode = "SPRM.PR_LINE_DELIVERY_EXEC_STA",
            meaningField = "deliveryStatusMeaning"
    )
    private String deliveryStatus;
    @ApiModelProperty("送货执行状态含义")
    private String deliveryStatusMeaning;
    @ApiModelProperty("送货执行数量")
    private BigDecimal deliveryQuantity;
    @ApiModelProperty("收货执行状态")
    @LovValue(
            lovCode = "SPRM.PR_LINE_RECEIPT_EXEC_STA",
            meaningField = "receiptStatusMeaning"
    )
    private String receiptStatus;
    @ApiModelProperty("收货执行状态含义")
    private String receiptStatusMeaning;
    @ApiModelProperty("收货执行数量")
    private BigDecimal receiptQuantity;
    @ApiModelProperty("开票执行状态")
    @LovValue(
            lovCode = "SPRM.PR_LINE_BILL_EXEC_STA",
            meaningField = "billStatusMeaning"
    )
    private String billStatus;
    @ApiModelProperty("开票执行状态含义")
    private String billStatusMeaning;
    @ApiModelProperty("开票执行数量")
    private BigDecimal billQuantity;
    @ApiModelProperty("申请头-申请人名称")
    private String headerPrRequestedName;
    @ApiModelProperty("申请头-采购员名称")
    private String headerPurchaseAgentName;
    @ApiModelProperty("字符扩展字段11,行品类是否推送资产标识")
    private String attributeVarchar11;

    public PrLineVO() {
    }

    public void calPrLineStatus() {
        if (Flag.YES.equals(this.assignedFlag)) {
            this.prLineStatusCode = "ASSIGNED";
        }

        if (Flag.YES.equals(this.suspendFlag)) {
            this.prLineStatusCode = "SUSPEND";
        }

        if (Flag.YES.equals(this.closedFlag)) {
            this.prLineStatusCode = "CLOSED";
        }

        if (Flag.YES.equals(this.cancelledFlag)) {
            this.prLineStatusCode = "CANCELLED";
        }

    }

    public void hideSupplier() {
        this.supplierCode = "***";
        this.supplierCompanyName = "***";
        this.supplierName = "***";
        this.supplierCompanyId = null;
        this.supplierId = null;
        this.supplierTenantId = null;
    }

    public String appendKey() {
        StringBuilder key = new StringBuilder(this.itemId.toString());
        key.append(":");
        key.append(this.invOrganizationId);
        return key.toString();
    }

    public boolean checkCloseStatus() {
        if ("APPROVED".equals(this.prLineStatusCode)) {
            return true;
        } else if ("ASSIGNED".equals(this.prLineStatusCode)) {
            return true;
        } else {
            return "SUSPEND".equals(this.prLineStatusCode);
        }
    }

    public Class<? extends SecurityToken> associateEntityClass() {
        return PrLine.class;
    }

    public String getBudgetAccountDeptno() {
        return this.budgetAccountDeptno;
    }

    public void setBudgetAccountDeptno(String budgetAccountDeptno) {
        this.budgetAccountDeptno = budgetAccountDeptno;
    }

    public BigDecimal getBudgetAccountPrice() {
        return this.budgetAccountPrice;
    }

    public void setBudgetAccountPrice(BigDecimal budgetAccountPrice) {
        this.budgetAccountPrice = budgetAccountPrice;
    }

    public String getHeadAttachmentUuid() {
        return this.headAttachmentUuid;
    }

    public void setHeadAttachmentUuid(String headAttachmentUuid) {
        this.headAttachmentUuid = headAttachmentUuid;
    }

    public List<String> getPoNumList() {
        return this.poNumList;
    }

    public void setPoNumList(List<String> poNumList) {
        this.poNumList = poNumList;
    }

    public List<Long> getAsnLineIdList() {
        return this.asnLineIdList;
    }

    public void setAsnLineIdList(List<Long> asnLineIdList) {
        this.asnLineIdList = asnLineIdList;
    }

    public Integer getPoSyncSuccessFlag() {
        return this.poSyncSuccessFlag;
    }

    public void setPoSyncSuccessFlag(Integer poSyncSuccessFlag) {
        this.poSyncSuccessFlag = poSyncSuccessFlag;
    }

    public String getPoSyncMessage() {
        return this.poSyncMessage;
    }

    public void setPoSyncMessage(String poSyncMessage) {
        this.poSyncMessage = poSyncMessage;
    }

    public String getSyncResponseMsg() {
        return this.syncResponseMsg;
    }

    public void setSyncResponseMsg(String syncResponseMsg) {
        this.syncResponseMsg = syncResponseMsg;
    }

    public BigDecimal getTaxWithoutFreightPrice() {
        return this.taxWithoutFreightPrice;
    }

    public void setTaxWithoutFreightPrice(BigDecimal taxWithoutFreightPrice) {
        this.taxWithoutFreightPrice = taxWithoutFreightPrice;
    }

    public String getHeaderRemark() {
        return this.headerRemark;
    }

    public void setHeaderRemark(String headerRemark) {
        this.headerRemark = headerRemark;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getExpenseUnitName() {
        return this.expenseUnitName;
    }

    public void setExpenseUnitName(String expenseUnitName) {
        this.expenseUnitName = expenseUnitName;
    }

    public String getItemPropertiesMeaning() {
        return this.itemPropertiesMeaning;
    }

    public void setItemPropertiesMeaning(String itemPropertiesMeaning) {
        this.itemPropertiesMeaning = itemPropertiesMeaning;
    }

    public String getAddressMeaning() {
        return this.addressMeaning;
    }

    public void setAddressMeaning(String addressMeaning) {
        this.addressMeaning = addressMeaning;
    }

    public Long getExpBearDepId() {
        return this.expBearDepId;
    }

    public String getExpBearDep() {
        return this.expBearDep;
    }

    public void setExpBearDepId(Long expBearDepId) {
        this.expBearDepId = expBearDepId;
    }

    public void setExpBearDep(String expBearDep) {
        this.expBearDep = expBearDep;
    }

    public Long getProjectId() {
        return this.projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
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

    public Long getInventoryId() {
        return this.inventoryId;
    }

    public void setInventoryId(Long inventoryId) {
        this.inventoryId = inventoryId;
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

    public String getChangePercentMeaning() {
        return this.changePercentMeaning;
    }

    public void setChangePercentMeaning(String changePercentMeaning) {
        this.changePercentMeaning = changePercentMeaning;
    }

    public Long getPcHeaderId() {
        return this.pcHeaderId;
    }

    public void setPcHeaderId(Long pcHeaderId) {
        this.pcHeaderId = pcHeaderId;
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

    public Long getAgentId() {
        return this.agentId;
    }

    public void setAgentId(Long agentId) {
        this.agentId = agentId;
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

    public Long getPrTypeId() {
        return this.prTypeId;
    }

    public void setPrTypeId(Long prTypeId) {
        this.prTypeId = prTypeId;
    }

    public String getPrTypeCode() {
        return this.prTypeCode;
    }

    public void setPrTypeCode(String prTypeCode) {
        this.prTypeCode = prTypeCode;
    }

    public String getPrTypeName() {
        return this.prTypeName;
    }

    public void setPrTypeName(String prTypeName) {
        this.prTypeName = prTypeName;
    }

    public Long getHeaderInventoryId() {
        return this.headerInventoryId;
    }

    public void setHeaderInventoryId(Long headerInventoryId) {
        this.headerInventoryId = headerInventoryId;
    }

    public String getHeaderInventoryName() {
        return this.headerInventoryName;
    }

    public void setHeaderInventoryName(String headerInventoryName) {
        this.headerInventoryName = headerInventoryName;
    }

    public String getParentUnitId() {
        return this.parentUnitId;
    }

    public void setParentUnitId(String parentUnitId) {
        this.parentUnitId = parentUnitId;
    }

    public Long getHeaderCategoryId() {
        return this.headerCategoryId;
    }

    public void setHeaderCategoryId(Long headerCategoryId) {
        this.headerCategoryId = headerCategoryId;
    }

    public String getHeaderCategoryName() {
        return this.headerCategoryName;
    }

    public void setHeaderCategoryName(String headerCategoryName) {
        this.headerCategoryName = headerCategoryName;
    }

    public String getPcNum() {
        return this.pcNum;
    }

    public void setPcNum(String pcNum) {
        this.pcNum = pcNum;
    }

    public String getParentUnitName() {
        return this.parentUnitName;
    }

    public void setParentUnitName(String parentUnitName) {
        this.parentUnitName = parentUnitName;
    }

    public String getKeeperUserName() {
        return this.keeperUserName;
    }

    public void setKeeperUserName(String keeperUserName) {
        this.keeperUserName = keeperUserName;
    }

    public String getAccepterUserName() {
        return this.accepterUserName;
    }

    public void setAccepterUserName(String accepterUserName) {
        this.accepterUserName = accepterUserName;
    }

    public String getCostPayerUserName() {
        return this.costPayerUserName;
    }

    public void setCostPayerUserName(String costPayerUserName) {
        this.costPayerUserName = costPayerUserName;
    }

    public String getAgentName() {
        return this.agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public Long getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public BigDecimal getLastPurchasePrice() {
        return this.lastPurchasePrice;
    }

    public void setLastPurchasePrice(BigDecimal lastPurchasePrice) {
        this.lastPurchasePrice = lastPurchasePrice;
    }

    public Long getPoLineId() {
        return this.PoLineId;
    }

    public void setPoLineId(Long poLineId) {
        this.PoLineId = poLineId;
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

    public String getErpEditStatus() {
        return this.erpEditStatus;
    }

    public void setErpEditStatus(String erpEditStatus) {
        this.erpEditStatus = erpEditStatus;
    }

    public String getPrLineStatusCodeMeaning() {
        return this.prLineStatusCodeMeaning;
    }

    public void setPrLineStatusCodeMeaning(String prLineStatusCodeMeaning) {
        this.prLineStatusCodeMeaning = prLineStatusCodeMeaning;
    }

    public Integer getSuspendFlag() {
        return this.suspendFlag;
    }

    public void setSuspendFlag(Integer suspendFlag) {
        this.suspendFlag = suspendFlag;
    }

    public Long getHeaderExecutionBillId() {
        return this.headerExecutionBillId;
    }

    public void setHeaderExecutionBillId(Long headerExecutionBillId) {
        this.headerExecutionBillId = headerExecutionBillId;
    }

    public String getPrLineStatusCode() {
        return this.prLineStatusCode;
    }

    public void setPrLineStatusCode(String prLineStatusCode) {
        this.prLineStatusCode = prLineStatusCode;
    }

    public String getInventoryName() {
        return this.inventoryName;
    }

    public void setInventoryName(String inventoryName) {
        this.inventoryName = inventoryName;
    }

    public String getSuspendRemark() {
        return this.suspendRemark;
    }

    public void setSuspendRemark(String suspendRemark) {
        this.suspendRemark = suspendRemark;
    }

    public String getDisplayPrNum() {
        return this.displayPrNum;
    }

    public void setDisplayPrNum(String displayPrNum) {
        this.displayPrNum = displayPrNum;
    }

    public String getHeaderExecutionBillNum() {
        return this.headerExecutionBillNum;
    }

    public void setHeaderExecutionBillNum(String headerExecutionBillNum) {
        this.headerExecutionBillNum = headerExecutionBillNum;
    }

    public String getHeaderSyncStatus() {
        return this.headerSyncStatus;
    }

    public void setHeaderSyncStatus(String headerSyncStatus) {
        this.headerSyncStatus = headerSyncStatus;
    }

    public String getHeaderSyncResponseMsg() {
        return this.headerSyncResponseMsg;
    }

    public void setHeaderSyncResponseMsg(String headerSyncResponseMsg) {
        this.headerSyncResponseMsg = headerSyncResponseMsg;
    }

    public String getPrSourcePlatformMeaning() {
        return this.prSourcePlatformMeaning;
    }

    public void setPrSourcePlatformMeaning(String prSourcePlatformMeaning) {
        this.prSourcePlatformMeaning = prSourcePlatformMeaning;
    }

    public String getPrNum() {
        return this.prNum;
    }

    public void setPrNum(String prNum) {
        this.prNum = prNum;
    }

    public String getPrHeaderStatusMeaning() {
        return this.prHeaderStatusMeaning;
    }

    public void setPrHeaderStatusMeaning(String prHeaderStatusMeaning) {
        this.prHeaderStatusMeaning = prHeaderStatusMeaning;
    }

    public Integer getCanVatFlag() {
        return this.canVatFlag;
    }

    public void setCanVatFlag(Integer canVatFlag) {
        this.canVatFlag = canVatFlag;
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

    public String getPrSourcePlatform() {
        return this.prSourcePlatform;
    }

    public void setPrSourcePlatform(String prSourcePlatform) {
        this.prSourcePlatform = prSourcePlatform;
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

    public String getCurrencyName() {
        return this.currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
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

    public String getAttachmentUuid() {
        return this.attachmentUuid;
    }

    public void setAttachmentUuid(String attachmentUuid) {
        this.attachmentUuid = attachmentUuid;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getInvOrganizationName() {
        return this.invOrganizationName;
    }

    public void setInvOrganizationName(String invOrganizationName) {
        this.invOrganizationName = invOrganizationName;
    }

    public String getCategoryName() {
        return this.categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getWbsCode() {
        return this.wbsCode;
    }

    public void setWbsCode(String wbsCode) {
        this.wbsCode = wbsCode;
    }

    public String getUomCode() {
        return this.uomCode;
    }

    public void setUomCode(String uomCode) {
        this.uomCode = uomCode;
    }

    public String getUomName() {
        return this.uomName;
    }

    public void setUomName(String uomName) {
        this.uomName = uomName;
    }

    public String getCompanyName() {
        return this.companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getOuName() {
        return this.ouName;
    }

    public void setOuName(String ouName) {
        this.ouName = ouName;
    }

    public String getTaxCode() {
        return this.taxCode;
    }

    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode;
    }

    public String getExecutorName() {
        return this.executorName;
    }

    public void setExecutorName(String executorName) {
        this.executorName = executorName;
    }

    public String getCreatorName() {
        return this.creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public String getPrRequestedNum() {
        return this.prRequestedNum;
    }

    public void setPrRequestedNum(String prRequestedNum) {
        this.prRequestedNum = prRequestedNum;
    }

    public String getPrRequestedName() {
        return this.prRequestedName;
    }

    public void setPrRequestedName(String prRequestedName) {
        this.prRequestedName = prRequestedName;
    }

    public String getPurchaseOrgName() {
        return this.purchaseOrgName;
    }

    public void setPurchaseOrgName(String purchaseOrgName) {
        this.purchaseOrgName = purchaseOrgName;
    }

    public String getPurchaseAgentName() {
        return this.purchaseAgentName;
    }

    public void setPurchaseAgentName(String purchaseAgentName) {
        this.purchaseAgentName = purchaseAgentName;
    }

    public String getErpStatus() {
        return this.erpStatus;
    }

    public void setErpStatus(String erpStatus) {
        this.erpStatus = erpStatus;
    }

    public String getPrLineStatusMeaning() {
        return this.prLineStatusMeaning;
    }

    public void setPrLineStatusMeaning(String prLineStatusMeaning) {
        this.prLineStatusMeaning = prLineStatusMeaning;
    }

    public String getExecutionStatusMeaning() {
        return this.executionStatusMeaning;
    }

    public void setExecutionStatusMeaning(String executionStatusMeaning) {
        this.executionStatusMeaning = executionStatusMeaning;
    }

    public String getCancelledFlagMeaning() {
        return this.cancelledFlagMeaning;
    }

    public void setCancelledFlagMeaning(String cancelledFlagMeaning) {
        this.cancelledFlagMeaning = cancelledFlagMeaning;
    }

    public String getClosedFlagMeaning() {
        return this.closedFlagMeaning;
    }

    public void setClosedFlagMeaning(String closedFlagMeaning) {
        this.closedFlagMeaning = closedFlagMeaning;
    }

    public String getAssignedFlagMeaning() {
        return this.assignedFlagMeaning;
    }

    public void setAssignedFlagMeaning(String assignedFlagMeaning) {
        this.assignedFlagMeaning = assignedFlagMeaning;
    }

    public String getHeaderSyncStatusMeaning() {
        return this.headerSyncStatusMeaning;
    }

    public void setHeaderSyncStatusMeaning(String headerSyncStatusMeaning) {
        this.headerSyncStatusMeaning = headerSyncStatusMeaning;
    }

    public BigDecimal getLineFreight() {
        return this.lineFreight;
    }

    public void setLineFreight(BigDecimal lineFreight) {
        this.lineFreight = lineFreight;
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

    public Date getRequestDate() {
        return this.requestDate;
    }

    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }

    public Long getRequestedBy() {
        return this.requestedBy;
    }

    public void setRequestedBy(Long requestedBy) {
        this.requestedBy = requestedBy;
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

    public Long getPurchaseAgentId() {
        return this.purchaseAgentId;
    }

    public void setPurchaseAgentId(Long purchaseAgentId) {
        this.purchaseAgentId = purchaseAgentId;
    }

    public BigDecimal getJdPrice() {
        return this.jdPrice;
    }

    public void setJdPrice(BigDecimal jdPrice) {
        this.jdPrice = jdPrice;
    }

    public Integer getUnitFlag() {
        return this.unitFlag;
    }

    public void setUnitFlag(Integer unitFlag) {
        this.unitFlag = unitFlag;
    }

    public String getSupplierItemName() {
        return this.supplierItemName;
    }

    public void setSupplierItemName(String supplierItemName) {
        this.supplierItemName = supplierItemName;
    }

    public BigDecimal getUnitPriceBatch() {
        return this.unitPriceBatch;
    }

    public void setUnitPriceBatch(BigDecimal unitPriceBatch) {
        this.unitPriceBatch = unitPriceBatch;
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

    public String getExpBearDepCode() {
        return this.expBearDepCode;
    }

    public void setExpBearDepCode(String expBearDepCode) {
        this.expBearDepCode = expBearDepCode;
    }

    public Long getBudgetAccountId() {
        return this.budgetAccountId;
    }

    public void setBudgetAccountId(Long budgetAccountId) {
        this.budgetAccountId = budgetAccountId;
    }

    public String getBudgetAccountNum() {
        return this.budgetAccountNum;
    }

    public void setBudgetAccountNum(String budgetAccountNum) {
        this.budgetAccountNum = budgetAccountNum;
    }

    public String getBudgetAccountName() {
        return this.budgetAccountName;
    }

    public void setBudgetAccountName(String budgetAccountName) {
        this.budgetAccountName = budgetAccountName;
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

    public String toString() {
        return "PrLineVO{prLineId=" + this.prLineId + ", prHeaderId=" + this.prHeaderId + ", tenantId=" + this.tenantId + ", lineNum=" + this.lineNum + ", displayLineNum='" + this.displayLineNum + '\'' + ", companyId=" + this.companyId + ", invOrganizationId=" + this.invOrganizationId + ", itemId=" + this.itemId + ", itemCode='" + this.itemCode + '\'' + ", itemName='" + this.itemName + '\'' + ", productId=" + this.productId + ", productNum='" + this.productNum + '\'' + ", productName='" + this.productName + '\'' + ", catalogId=" + this.catalogId + ", catalogName='" + this.catalogName + '\'' + ", categoryId=" + this.categoryId + ", uomId=" + this.uomId + ", quantity=" + this.quantity + ", taxId=" + this.taxId + ", taxRate=" + this.taxRate + ", currencyCode='" + this.currencyCode + '\'' + ", taxIncludedUnitPrice=" + this.taxIncludedUnitPrice + ", lineAmount=" + this.lineAmount + ", taxIncludedLineAmount=" + this.taxIncludedLineAmount + ", neededDate=" + this.neededDate + ", supplierTenantId=" + this.supplierTenantId + ", supplierId=" + this.supplierId + ", supplierCode='" + this.supplierCode + '\'' + ", supplierName='" + this.supplierName + '\'' + ", prSourcePlatform='" + this.prSourcePlatform + '\'' + ", supplierCompanyId=" + this.supplierCompanyId + ", supplierCompanyName='" + this.supplierCompanyName + '\'' + ", executionStatusCode='" + this.executionStatusCode + '\'' + ", executedDate=" + this.executedDate + ", executedBy=" + this.executedBy + ", executionBillId=" + this.executionBillId + ", executionBillNum='" + this.executionBillNum + '\'' + ", assignedFlag=" + this.assignedFlag + ", assignedDate=" + this.assignedDate + ", assignedBy=" + this.assignedBy + ", assignedRemark='" + this.assignedRemark + '\'' + ", cancelledFlag=" + this.cancelledFlag + ", lineFreight=" + this.lineFreight + ", cancelledDate=" + this.cancelledDate + ", cancelledBy=" + this.cancelledBy + ", cancelledRemark='" + this.cancelledRemark + '\'' + ", closedFlag=" + this.closedFlag + ", closedDate=" + this.closedDate + ", closedBy=" + this.closedBy + ", closedRemark='" + this.closedRemark + '\'' + ", attachmentUuid='" + this.attachmentUuid + '\'' + ", remark='" + this.remark + '\'' + ", canVatFlag=" + this.canVatFlag + ", executionHeaderBillId=" + this.executionHeaderBillId + ", executionHeaderBillNum='" + this.executionHeaderBillNum + '\'' + ", inventoryName='" + this.inventoryName + '\'' + ", suspendRemark='" + this.suspendRemark + '\'' + ", suspendFlag=" + this.suspendFlag + ", prLineStatusCode='" + this.prLineStatusCode + '\'' + ", prLineStatusCodeMeaning='" + this.prLineStatusCodeMeaning + '\'' + ", incorrectFlag=" + this.incorrectFlag + ", incorrectDate=" + this.incorrectDate + ", incorrectMsg='" + this.incorrectMsg + '\'' + ", erpEditStatus='" + this.erpEditStatus + '\'' + ", urgentFlag=" + this.urgentFlag + ", urgentDate=" + this.urgentDate + ", costId=" + this.costId + ", costCode='" + this.costCode + '\'' + ", accountSubjectId=" + this.accountSubjectId + ", accountSubjectNum='" + this.accountSubjectNum + '\'' + ", wbs='" + this.wbs + '\'' + ", prNum='" + this.prNum + '\'' + ", displayPrNum='" + this.displayPrNum + '\'' + ", invOrganizationName='" + this.invOrganizationName + '\'' + ", categoryName='" + this.categoryName + '\'' + ", uomCode='" + this.uomCode + '\'' + ", uomName='" + this.uomName + '\'' + ", companyName='" + this.companyName + '\'' + ", ouName='" + this.ouName + '\'' + ", taxCode='" + this.taxCode + '\'' + ", currencyName='" + this.currencyName + '\'' + ", executorName='" + this.executorName + '\'' + ", createdBy=" + this.createdBy + ", creatorName='" + this.creatorName + '\'' + ", prRequestedName='" + this.prRequestedName + '\'' + ", purchaseOrgName='" + this.purchaseOrgName + '\'' + ", purchaseAgentName='" + this.purchaseAgentName + '\'' + ", erpStatus='" + this.erpStatus + '\'' + ", headerExecutionBillId=" + this.headerExecutionBillId + ", headerExecutionBillNum='" + this.headerExecutionBillNum + '\'' + ", headerSyncStatus='" + this.headerSyncStatus + '\'' + ", headerSyncResponseMsg='" + this.headerSyncResponseMsg + '\'' + ", jdPrice=" + this.jdPrice + ", prLineStatusMeaning='" + this.prLineStatusMeaning + '\'' + ", prSourcePlatformMeaning='" + this.prSourcePlatformMeaning + '\'' + ", executionStatusMeaning='" + this.executionStatusMeaning + '\'' + ", cancelledFlagMeaning='" + this.cancelledFlagMeaning + '\'' + ", closedFlagMeaning='" + this.closedFlagMeaning + '\'' + ", assignedFlagMeaning='" + this.assignedFlagMeaning + '\'' + ", headerSyncStatusMeaning='" + this.headerSyncStatusMeaning + '\'' + ", prHeaderStatusMeaning='" + this.prHeaderStatusMeaning + '\'' + ", requestDate=" + this.requestDate + ", requestedBy=" + this.requestedBy + ", ouId=" + this.ouId + ", purchaseOrgId=" + this.purchaseOrgId + ", purchaseAgentId=" + this.purchaseAgentId + ", itemAbcClass='" + this.itemAbcClass + '\'' + ", drawingNum='" + this.drawingNum + '\'' + ", projectNum='" + this.projectNum + '\'' + ", projectName='" + this.projectName + '\'' + ", craneNum='" + this.craneNum + '\'' + ", unitId=" + this.unitId + ", unitName='" + this.unitName + '\'' + ", costName='" + this.costName + '\'' + ", accountSubjectName='" + this.accountSubjectName + '\'' + ", supplierItemName='" + this.supplierItemName + '\'' + '}';
    }

    public BigDecimal getOccupiedQuantity() {
        return this.occupiedQuantity;
    }

    public void setOccupiedQuantity(BigDecimal occupiedQuantity) {
        this.occupiedQuantity = occupiedQuantity;
    }

    public String getSurfaceTreatFlag() {
        return this.surfaceTreatFlag;
    }

    public void setSurfaceTreatFlag(String surfaceTreatFlag) {
        this.surfaceTreatFlag = surfaceTreatFlag;
    }

    public Integer getChangedFlag() {
        return this.changedFlag;
    }

    public void setChangedFlag(Integer changedFlag) {
        this.changedFlag = changedFlag;
    }

    public Long getAccountAssignTypeId() {
        return this.accountAssignTypeId;
    }

    public void setAccountAssignTypeId(Long accountAssignTypeId) {
        this.accountAssignTypeId = accountAssignTypeId;
    }

    public String getAccountAssignTypeCode() {
        return this.accountAssignTypeCode;
    }

    public void setAccountAssignTypeCode(String accountAssignTypeCode) {
        this.accountAssignTypeCode = accountAssignTypeCode;
    }

    public Set<String> getAssignTypeRequiredFieldNames() {
        return this.assignTypeRequiredFieldNames;
    }

    public void setAssignTypeRequiredFieldNames(Set<String> assignTypeRequiredFieldNames) {
        this.assignTypeRequiredFieldNames = assignTypeRequiredFieldNames;
    }

    public List<PrHeader> getPrHeaderAssignList() {
        return this.prHeaderAssignList;
    }

    public void setPrHeaderAssignList(List<PrHeader> prHeaderAssignList) {
        this.prHeaderAssignList = prHeaderAssignList;
    }

    public String getInvoiceTitle() {
        return this.invoiceTitle;
    }

    public void setInvoiceTitle(String invoiceTitle) {
        this.invoiceTitle = invoiceTitle;
    }

    public Integer getFreightLineFlag() {
        return this.freightLineFlag;
    }

    public void setFreightLineFlag(Integer freightLineFlag) {
        this.freightLineFlag = freightLineFlag;
    }

    public Long getInvoiceCompanyId() {
        return this.invoiceCompanyId;
    }

    public void setInvoiceCompanyId(Long invoiceCompanyId) {
        this.invoiceCompanyId = invoiceCompanyId;
    }

    public String getInvoiceCompanyName() {
        return this.invoiceCompanyName;
    }

    public void setInvoiceCompanyName(String invoiceCompanyName) {
        this.invoiceCompanyName = invoiceCompanyName;
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

    public Integer getReferencePriceDisplayFlag() {
        return this.referencePriceDisplayFlag;
    }

    public void setReferencePriceDisplayFlag(Integer referencePriceDisplayFlag) {
        this.referencePriceDisplayFlag = referencePriceDisplayFlag;
    }

    public Integer getTransferFlag() {
        return this.transferFlag;
    }

    public void setTransferFlag(Integer transferFlag) {
        this.transferFlag = transferFlag;
    }

    public Integer getOccupyFlag() {
        return this.occupyFlag;
    }

    public void setOccupyFlag(Integer occupyFlag) {
        this.occupyFlag = occupyFlag;
    }

    public String getOuCode() {
        return this.ouCode;
    }

    public void setOuCode(String ouCode) {
        this.ouCode = ouCode;
    }

    public String getCompanyNum() {
        return this.companyNum;
    }

    public void setCompanyNum(String companyNum) {
        this.companyNum = companyNum;
    }

    public Long getBudgetAccountDeptId() {
        return this.budgetAccountDeptId;
    }

    public void setBudgetAccountDeptId(Long budgetAccountDeptId) {
        this.budgetAccountDeptId = budgetAccountDeptId;
    }

    public String getBudgetAccountDeptName() {
        return this.budgetAccountDeptName;
    }

    public void setBudgetAccountDeptName(String budgetAccountDeptName) {
        this.budgetAccountDeptName = budgetAccountDeptName;
    }

    public BigDecimal getUnitPrice() {
        return this.unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public List<Long> getAssignUserIds() {
        return this.assignUserIds;
    }

    public void setAssignUserIds(List<Long> assignUserIds) {
        this.assignUserIds = assignUserIds;
    }

    public List<PrLineSupplier> getSupplierList() {
        return this.supplierList;
    }

    public void setSupplierList(List<PrLineSupplier> supplierList) {
        this.supplierList = supplierList;
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

    public String getBidStatus() {
        return this.bidStatus;
    }

    public void setBidStatus(String bidStatus) {
        this.bidStatus = bidStatus;
    }

    public String getBidStatusMeaning() {
        return this.bidStatusMeaning;
    }

    public void setBidStatusMeaning(String bidStatusMeaning) {
        this.bidStatusMeaning = bidStatusMeaning;
    }

    public BigDecimal getBidQuantity() {
        return this.bidQuantity;
    }

    public void setBidQuantity(BigDecimal bidQuantity) {
        this.bidQuantity = bidQuantity;
    }

    public String getRfxStatus() {
        return this.rfxStatus;
    }

    public void setRfxStatus(String rfxStatus) {
        this.rfxStatus = rfxStatus;
    }

    public String getRfxStatusMeaning() {
        return this.rfxStatusMeaning;
    }

    public void setRfxStatusMeaning(String rfxStatusMeaning) {
        this.rfxStatusMeaning = rfxStatusMeaning;
    }

    public BigDecimal getRfxQuantity() {
        return this.rfxQuantity;
    }

    public void setRfxQuantity(BigDecimal rfxQuantity) {
        this.rfxQuantity = rfxQuantity;
    }

    public String getProjectStatus() {
        return this.projectStatus;
    }

    public void setProjectStatus(String projectStatus) {
        this.projectStatus = projectStatus;
    }

    public BigDecimal getProjectQuantity() {
        return this.projectQuantity;
    }

    public void setProjectQuantity(BigDecimal projectQuantity) {
        this.projectQuantity = projectQuantity;
    }

    public String getContractStatus() {
        return this.contractStatus;
    }

    public void setContractStatus(String contractStatus) {
        this.contractStatus = contractStatus;
    }

    public BigDecimal getContractQuantity() {
        return this.contractQuantity;
    }

    public void setContractQuantity(BigDecimal contractQuantity) {
        this.contractQuantity = contractQuantity;
    }

    public String getOrderStatus() {
        return this.orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public BigDecimal getOrderQuantity() {
        return this.orderQuantity;
    }

    public void setOrderQuantity(BigDecimal orderQuantity) {
        this.orderQuantity = orderQuantity;
    }

    public String getDeliveryStatus() {
        return this.deliveryStatus;
    }

    public void setDeliveryStatus(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public BigDecimal getDeliveryQuantity() {
        return this.deliveryQuantity;
    }

    public void setDeliveryQuantity(BigDecimal deliveryQuantity) {
        this.deliveryQuantity = deliveryQuantity;
    }

    public String getReceiptStatus() {
        return this.receiptStatus;
    }

    public void setReceiptStatus(String receiptStatus) {
        this.receiptStatus = receiptStatus;
    }

    public BigDecimal getReceiptQuantity() {
        return this.receiptQuantity;
    }

    public void setReceiptQuantity(BigDecimal receiptQuantity) {
        this.receiptQuantity = receiptQuantity;
    }

    public String getBillStatus() {
        return this.billStatus;
    }

    public void setBillStatus(String billStatus) {
        this.billStatus = billStatus;
    }

    public BigDecimal getBillQuantity() {
        return this.billQuantity;
    }

    public void setBillQuantity(BigDecimal billQuantity) {
        this.billQuantity = billQuantity;
    }

    public String getProjectStatusMeaning() {
        return this.projectStatusMeaning;
    }

    public void setProjectStatusMeaning(String projectStatusMeaning) {
        this.projectStatusMeaning = projectStatusMeaning;
    }

    public String getContractStatusMeaning() {
        return this.contractStatusMeaning;
    }

    public void setContractStatusMeaning(String contractStatusMeaning) {
        this.contractStatusMeaning = contractStatusMeaning;
    }

    public String getOrderStatusMeaning() {
        return this.orderStatusMeaning;
    }

    public void setOrderStatusMeaning(String orderStatusMeaning) {
        this.orderStatusMeaning = orderStatusMeaning;
    }

    public String getDeliveryStatusMeaning() {
        return this.deliveryStatusMeaning;
    }

    public void setDeliveryStatusMeaning(String deliveryStatusMeaning) {
        this.deliveryStatusMeaning = deliveryStatusMeaning;
    }

    public String getReceiptStatusMeaning() {
        return this.receiptStatusMeaning;
    }

    public void setReceiptStatusMeaning(String receiptStatusMeaning) {
        this.receiptStatusMeaning = receiptStatusMeaning;
    }

    public String getBillStatusMeaning() {
        return this.billStatusMeaning;
    }

    public void setBillStatusMeaning(String billStatusMeaning) {
        this.billStatusMeaning = billStatusMeaning;
    }

    public String getHeaderPrRequestedName() {
        return this.headerPrRequestedName;
    }

    public void setHeaderPrRequestedName(String headerPrRequestedName) {
        this.headerPrRequestedName = headerPrRequestedName;
    }

    public String getHeaderPurchaseAgentName() {
        return this.headerPurchaseAgentName;
    }

    public void setHeaderPurchaseAgentName(String headerPurchaseAgentName) {
        this.headerPurchaseAgentName = headerPurchaseAgentName;
    }

    public Integer getDefaultPrecision() {
        return this.defaultPrecision;
    }

    public void setDefaultPrecision(Integer defaultPrecision) {
        this.defaultPrecision = defaultPrecision;
    }

    public Integer getFinancialPrecision() {
        return this.financialPrecision;
    }

    public void setFinancialPrecision(Integer financialPrecision) {
        this.financialPrecision = financialPrecision;
    }

    @Override
    public String getAttributeVarchar11() {
        return attributeVarchar11;
    }

    @Override
    public void setAttributeVarchar11(String attributeVarchar11) {
        this.attributeVarchar11 = attributeVarchar11;
    }
}
