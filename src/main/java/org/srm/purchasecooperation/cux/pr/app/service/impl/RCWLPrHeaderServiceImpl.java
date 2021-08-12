package org.srm.purchasecooperation.cux.pr.app.service.impl;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.base.CaseFormat;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.CaseFormat;
import io.choerodon.core.exception.CommonException;
import io.choerodon.core.oauth.DetailsHelper;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.servicecomb.pack.omega.context.annotations.SagaStart;
import org.hzero.boot.customize.service.CustomizeClient;
import org.hzero.boot.customize.util.CustomizeHelper;
import org.hzero.boot.message.MessageClient;
import org.hzero.boot.message.entity.Attachment;
import org.hzero.boot.message.entity.Receiver;
import org.hzero.boot.workflow.WorkflowClient;
import org.hzero.core.base.BaseConstants;
import org.hzero.core.helper.LanguageHelper;
import org.hzero.core.message.MessageAccessor;
import org.hzero.core.redis.RedisHelper;
import org.hzero.mybatis.domian.Condition;
import org.hzero.mybatis.util.Sqls;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.srm.boot.adaptor.client.AdaptorTaskHelper;
import org.srm.boot.adaptor.client.exception.TaskNotExistException;
import org.srm.boot.adaptor.client.result.TaskResultBox;
import org.srm.boot.event.service.sender.EventSender;
import org.srm.boot.platform.configcenter.CnfHelper;
import org.srm.boot.platform.customizesetting.CustomizeSettingHelper;
import org.srm.boot.platform.group.GroupApproveHelper;
import org.srm.boot.platform.message.MessageHelper;
import org.srm.boot.platform.message.entity.SpfmMessageSender;
import org.srm.common.TenantInfoHelper;
import org.srm.purchasecooperation.asn.infra.constant.Constants;
import org.srm.purchasecooperation.asn.infra.utils.CopyUtils;
import org.srm.purchasecooperation.budget.app.service.BudgetService;
import org.srm.purchasecooperation.cux.acp.infra.constant.RCWLAcpConstant;
import org.srm.purchasecooperation.cux.pr.app.service.RCWLPrItfService;
import org.srm.purchasecooperation.cux.pr.app.service.RcwlCompanyService;
import org.srm.purchasecooperation.cux.pr.app.service.RcwlPrheaderService;
import org.srm.purchasecooperation.cux.pr.domain.repository.RCWLItfPrDataRespository;
import org.srm.purchasecooperation.cux.pr.infra.constant.RCWLConstants;
import org.srm.purchasecooperation.cux.pr.utils.constant.PrConstant;
import org.srm.purchasecooperation.order.api.dto.ItemListDTO;
import org.srm.purchasecooperation.order.domain.entity.PoHeader;
import org.srm.purchasecooperation.order.domain.entity.User;
import org.srm.purchasecooperation.order.domain.service.PoHeaderDomainService;
import org.srm.purchasecooperation.order.infra.constant.MessageCode;
import org.srm.purchasecooperation.order.infra.mapper.PoHeaderMapper;
import org.srm.purchasecooperation.order.infra.utils.ReceiverUtils;
import org.srm.purchasecooperation.pr.api.dto.PrHeaderCreateDTO;
import org.srm.purchasecooperation.pr.app.service.PrActionService;
import org.srm.purchasecooperation.pr.app.service.PrBudgetService;
import org.srm.purchasecooperation.pr.app.service.PrHeaderService;
import org.srm.purchasecooperation.pr.app.service.PrLineService;
import org.srm.purchasecooperation.pr.app.service.impl.PrHeaderServiceImpl;
import org.srm.purchasecooperation.pr.domain.entity.*;
import org.srm.purchasecooperation.pr.domain.repository.*;
import org.srm.purchasecooperation.pr.domain.vo.PrCopyFieldsVO;
import org.srm.purchasecooperation.pr.domain.vo.PrHeaderVO;
import org.srm.purchasecooperation.pr.infra.feign.ScecRemoteService;
import org.srm.purchasecooperation.pr.infra.mapper.PrLineMapper;
import org.srm.web.annotation.Tenant;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @description:
 * @author:yuanping.zhang
 * @createTime:2021/3/26 16:19
 * @version:1.0
 */
@Tenant(RCWLAcpConstant.TENANT_NUMBER)
@Service
public class RCWLPrHeaderServiceImpl extends PrHeaderServiceImpl implements RcwlPrheaderService {
    private static final Logger LOGGER = LoggerFactory.getLogger(RCWLPrHeaderServiceImpl.class);
    @Autowired
    private PrHeaderRepository prHeaderRepository;
    @Autowired
    private PrLineService prLineService;
    @Autowired
    private PrLineRepository prLineRepository;
    @Autowired
    private PrActionService prActionService;
    @Autowired
    private PrLineMapper prLineMapper;
    @Autowired
    private CustomizeSettingHelper customizeSettingHelper;
    @Autowired
    private PrChangeConfigRepository prChangeConfigRepository;
    @Autowired
    private PrActionRepository prActionRepository;
    @Autowired
    private RCWLPrItfService rcwlPrItfService;
    @Autowired
    private RCWLItfPrDataRespository rcwlItfPrDataRespository;
    @Autowired
    private RcwlCompanyService rcwlCompanyService;
    @Autowired
    private BudgetService budgetService;
    @Autowired
    private PrBudgetService prBudgetService;
    @Autowired
    private EventSender eventSender;
    @Autowired
    private PoHeaderMapper poHeaderMapper;
    @Autowired
    private PrLineSupplierRepository prLineSupplierRepository;
    @Autowired
    private PoHeaderDomainService poHeaderDomainService;

    @Value("${service.demand-query-detail}")
    private String demandQueryDetailUrl;
    @Autowired
    private MessageClient messageClient;
    @Autowired
    private MessageHelper messageHelper;


    private static final String LOG_MSG_USER = " updatePrHeader ====用户信息:{},采购申请=:{}";
    private static final String LOG_MSG_SPUC_PR_HEADER_UPDATE_AMOUNT = "============SPUC_PR_HEADER_UPDATE_AMOUNT-TaskNotExistException=============={}";
    private static final String PR_TASK_CODE = "SPUC_PR_HEADER_UPDATE";
    private static final String[] HEADER_UPDATE_OPTIONAL = new String[]{"purchaseAgentId", "title", "contactTelNum", "approvedRemark",
            "invoiceAddressId", "invoiceContactName", "invoiceTelNum", "receiverEmailAddress", "invoiceMethodCode",
            "remark", "invoiceTypeCode", "invoiceTitleTypeCode", "invoiceDetailTypeCode", "ouId", "purchaseOrgId", "paymentMethodCode", "paymentMethodName",
            "invoiceAddress", "unitId", "unitName", "invoiceTypeName", "invoiceMethodName", "invoiceTitleTypeName", "invoiceTitle", "invoiceDetailTypeName",
            "expenseUnitId", "parentUnitId", "expenseUnitName", "invoiceCompanyId", "accepterUserId", "inventoryId", "categoryId", "techGuidanceFlag", "techDirectorUserId",
            "prTypeId", "requestedBy", "prRequestedName", "previousPrStatusCode", "localCurrencyNoTaxSum", "localCurrencyTaxSum", "localCurrency", "originalCurrency"};


    private static final String PR_CHANGE_STATUS = "CHANGE";

    /**
     * 融创采购申请更新二开接口
     *
     * @param prHeader
     * @return
     */
    @Transactional(
            rollbackFor = {Exception.class}
    )
    @Override
    public PrHeader updatePrHeader(PrHeader prHeader) {
        LOGGER.debug(LOG_MSG_USER, DetailsHelper.getUserDetails(), JSON.toJSONString(Arrays.asList(prHeader)));
        this.validatePrCancel(prHeader);
        this.checkUnit(prHeader);
        prHeader.validInvoiceDetail();
        prHeader.createValidateNonNull();
        prHeader.validUniqueIndex(this.prHeaderRepository);
        prHeader.setLocalCurrency(this.prHeaderRepository.selectPrLocalCurrencyCode(prHeader.getTenantId(), prHeader.getCompanyId()));
        prHeader.setPrLineList(this.prLineService.updatePrLines(prHeader));
        String tenantNum = TenantInfoHelper.selectByTenantId(prHeader.getTenantId()).getTenantNum();

        try {
            TaskResultBox taskResultBox = AdaptorTaskHelper.executeAdaptorTask(PR_TASK_CODE, tenantNum, prHeader);
            PrHeader header = (PrHeader) taskResultBox.get(0, PrHeader.class);
            BeanUtils.copyProperties(header, prHeader, CopyUtils.getNullPropertyNames(header));
        } catch (TaskNotExistException var5) {
            LOGGER.info(LOG_MSG_SPUC_PR_HEADER_UPDATE_AMOUNT, new Object[]{tenantNum, var5.getMessage(), var5.getStackTrace()});
        }
        //当申请类型为“计划申请” PLAN时 项目申请”PROJECT，预算总金额必填，申请行“预估单价（含税）”字段非必填，同时取消，行金额相加=申请总额的逻辑。
        if (!PrConstant.PrType.PR_TYPE_PLAN.equals(prHeader.getPrTypeCode()) &&
                !PrConstant.PrType.PR_TYPE_PROJECT.equals(prHeader.getPrTypeCode())
        ) {
            prHeader.batchMaintainDateAndCountAmount(this.prLineRepository);
        } else {
            //当申请类型为“计划申请” PLAN时 项目申请”PROJECT 成本中心 业务事项 产品类型 只能维护同一值
            // 保存时校验 如果不同报错：计划申请和项目申请只能维护同一成本中心、业务事项、产品类型
            this.checkLines(prHeader.getPrLineList());
        }

        PrHeaderVO oldPrHeaderVO;
        PrHeaderVO newPrHeaderVO;
        if (prHeader.sourcePlatformIsRule()) {
            oldPrHeaderVO = this.prHeaderRepository.selectPrHeaderDetail(prHeader.getTenantId(), prHeader.getPrHeaderId());
            prHeader.setPreviousPrStatusCode(oldPrHeaderVO.getPrStatusCode());
            if (prHeader.getRequestedBy() == null) {
                prHeader.setRequestedBy(oldPrHeaderVO.getRequestedBy());
            }

            this.prHeaderRepository.updateOptional(prHeader, HEADER_UPDATE_OPTIONAL);
            newPrHeaderVO = this.prHeaderRepository.selectPrHeaderDetail(prHeader.getTenantId(), prHeader.getPrHeaderId());
            this.prActionService.headerDataChangeDetection(oldPrHeaderVO, newPrHeaderVO);
        } else {
            oldPrHeaderVO = this.prHeaderRepository.selectPrHeaderDetail(prHeader.getTenantId(), prHeader.getPrHeaderId());
            prHeader.setPreviousPrStatusCode(oldPrHeaderVO.getPrStatusCode());
            this.prHeaderRepository.updateByPrimaryKeySelective(prHeader);
            this.prHeaderRepository.updateOptional(prHeader, PrHeader.SRM_UPDATE_FIELD_LIST);
            newPrHeaderVO = this.prHeaderRepository.selectPrHeaderDetail(prHeader.getTenantId(), prHeader.getPrHeaderId());
            this.prActionService.headerDataChangeDetection(oldPrHeaderVO, newPrHeaderVO);
        }

        return prHeader;
    }

    @Override
    @Transactional(
            rollbackFor = {Exception.class}
    )
    @SagaStart
    public PrHeader singletonSubmit(Long tenantId, PrHeader prHeader) {
        this.checkUnit(prHeader);
        if (CollectionUtils.isEmpty(prHeader.getPrLineList())) {
            return prHeader;
        } else {
            prHeader = this.updatePrHeader(prHeader);
            //判断是否能触发接口
            Integer count = this.rcwlItfPrDataRespository.validateInvokeItf(prHeader.getPrHeaderId(), tenantId);
            if (RCWLConstants.Common.IS.equals(count)) {
                //保存完之后触发接口
                try {
                    this.rcwlPrItfService.invokeBudgetOccupy(prHeader, tenantId);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            }
//            prHeader.validateSubmitForBatch(this.prHeaderRepository, this.prLineRepository, this.customizeSettingHelper, this.customizeClient);
//            return ((PrHeaderService) this).submit(tenantId, prHeader);
            this.prActionService.recordPrAction(prHeader.getPrHeaderId(), "SUBMITTED", "提交至BPM");
            return prHeader;
        }
    }

    @Override
    @Transactional(
            rollbackFor = {Exception.class}
    )
    @SagaStart
    public PrHeader changeSubmit(Long tenantId, PrHeader prHeader, Set<String> approveSet) {
        LOGGER.info("Purchase requisition " + prHeader.getDisplayPrNum() + " change submit start -------------");
        this.validatePrCancel(prHeader);
        String flag = this.customizeSettingHelper.queryBySettingCode(tenantId, "010910");
        Assert.isTrue(StringUtils.isNotEmpty(flag) && String.valueOf(BaseConstants.Flag.YES).equals(flag), "error.change.tenant.cannot.change");
        Map<Long, PrLine> beforePrLineMap = (Map) this.prLineRepository.selectByCondition(Condition.builder(PrLine.class).andWhere(Sqls.custom().andEqualTo("prHeaderId", prHeader.getPrHeaderId())).build()).stream().collect(Collectors.toMap(PrLine::getPrLineId, Function.identity()));
        LOGGER.info("Purchase requisition " + beforePrLineMap + " change submit start -------------");
        List<PrLine> changePrlines = prHeader.getPrLineList();
        //项目申请的删除或新增
        //取消，行金额相加=申请总额的逻辑。
        if (!PrConstant.PrType.PR_TYPE_PROJECT.equals(prHeader.getPrTypeCode())
        ) {
            this.changeStatusCheck(prHeader, beforePrLineMap);
            LOGGER.info("Purchase requisition change save -------------");
            prHeader.validInvoiceDetail();
            prHeader.createValidateNonNull();
            prHeader.validUniqueIndex(this.prHeaderRepository);
            prHeader.setPrLineList(this.prLineService.updatePrLinesForChange(prHeader));
            prHeader.batchMaintainDateAndCountAmount(this.prLineRepository);
            prHeader.setChangedFlag(BaseConstants.Flag.YES);
            prHeader.setPrStatusCode(PR_CHANGE_STATUS);
            this.prHeaderRepository.updateByPrimaryKeySelective(prHeader);
        } else {
            this.deleteOrInsertLines(beforePrLineMap, prHeader);
            prHeader.setPrLineList(this.prLineService.updatePrLines(prHeader));
            this.checkLinesAmount(prHeader.getPrLineList(), prHeader.getAmount());
        }

        LOGGER.info("Purchase requisition change action -------------");
        List<PrAction> insertPrActions = new ArrayList();
        Map<Long, PrLine> afterPrLineMap = (Map) this.prLineRepository.selectByCondition(Condition.builder(PrLine.class).andWhere(Sqls.custom().andEqualTo("prHeaderId", prHeader.getPrHeaderId())).build()).stream().collect(Collectors.toMap(PrLine::getPrLineId, Function.identity()));
        List<PrChangeConfig> configs = this.prChangeConfigRepository.listPrChangeConfig(new PrChangeConfig(tenantId));
        List<PrChangeConfig> lineConfigs = (List) configs.stream().filter((config) -> {
            return "SPRM_PR_LINE".equals(config.getTableName());
        }).collect(Collectors.toList());
        Iterator var11 = changePrlines.iterator();

        while (var11.hasNext()) {
            PrLine prLine = (PrLine) var11.next();
            PrLine beforePrLine = beforePrLineMap.get(prLine.getPrLineId());
            if (!ObjectUtils.isEmpty(beforePrLine)) {
                PrLine afterPrLine = afterPrLineMap.get(prLine.getPrLineId());
                insertPrActions.addAll(this.prActionService.createChangeAction(beforePrLine, afterPrLine, lineConfigs, prHeader, approveSet));
            }
        }

        this.prActionRepository.batchInsertSelective(insertPrActions);
        long submitFlag = changePrlines.stream().filter((prLinex) -> {
            return BaseConstants.Flag.YES.equals(prLinex.getChangeSubmitFlag());
        }).count();
        if (submitFlag <= 0L && !"REJECTED".equals(prHeader.getPrStatusCode())) {
            approveSet.clear();
            LOGGER.info("No approval required for purchase requisition -------------");
        } else if (!CollectionUtils.isNotEmpty(approveSet) && !"REJECTED".equals(prHeader.getPrStatusCode())) {
            approveSet.clear();
        } else {
            LOGGER.info("Purchase requisition change submitting -------------");
            this.prActionService.recordPrAction(prHeader.getPrHeaderId(), "SUBMITTED", "变更提交至BPM");
//            this.submit(tenantId, prHeader);
        }

        LOGGER.info("Purchase requisition " + prHeader.getDisplayPrNum() + " change submit end -------------");
        return prHeader;
    }

    private void deleteOrInsertLines(Map<Long, PrLine> beforePrLineMap, PrHeader prHeader) {
        Set<Long> ids = new TreeSet<>();
        List<PrLine> prDeleteLines = new ArrayList<>();
        prHeader.getPrLineList().forEach(line -> {
            if (beforePrLineMap.keySet().contains(line.getPrLineId())) {
                ids.add(line.getPrLineId());
            }
        });
        beforePrLineMap.entrySet().forEach(e -> {
            if (!ids.contains(e.getValue().getPrLineId())) {
                prDeleteLines.add(e.getValue());
            }
        });
        this.prLineService.deleteLines(prHeader.getPrHeaderId(), prDeleteLines);
    }

    private void checkLinesAmount(List<PrLine> prLineList, BigDecimal amount) {
        BigDecimal lineAmount = BigDecimal.ZERO;
        for (PrLine line : prLineList) {
            lineAmount = lineAmount.add(line.getTaxIncludedLineAmount());
        }
        if (lineAmount.compareTo(amount) >= 1) {
            throw new CommonException("error.lineAmount.lessThan.amount");
        }

    }

    private void changeStatusCheck(PrHeader prHeader, Map<Long, PrLine> beforePrLineMap) {
        Assert.isTrue("APPROVED".equals(prHeader.getPrStatusCode()) || "REJECTED".equals(prHeader.getPrStatusCode()) || PR_CHANGE_STATUS.equals(prHeader.getPrStatusCode()), "error.change.header.status.not.approve");
        Assert.isTrue(!"CATALOGUE".equals(prHeader.getPrSourcePlatform()) && !"E-COMMERCE".equals(prHeader.getPrSourcePlatform()), "error.change.header.source.platform");
        List<PrLine> prLineList = (List) prHeader.getPrLineList().stream().filter((prLine) -> {
            return !BaseConstants.Flag.YES.equals(prLine.getClosedFlag()) && !BaseConstants.Flag.YES.equals(prLine.getCancelledFlag());
        }).map((prLine) -> {
            PrLine oldPrLine = (PrLine) beforePrLineMap.get(prLine.getPrLineId());
            if (oldPrLine.getOccupiedQuantity().compareTo(prLine.getQuantity()) > 0) {
                throw new CommonException("error.pr.change_quantity_error", new Object[0]);
            } else {
                if (oldPrLine.getOccupiedQuantity() != null && oldPrLine.getOccupiedQuantity().compareTo(BigDecimal.ZERO) > 0) {
                    prLine.setOccupyFlag(BaseConstants.Flag.YES);
                    prLine.setOldQuantity(oldPrLine.getQuantity());
                    prLine.setOccupiedQuantity(oldPrLine.getOccupiedQuantity());
                }

                return prLine;
            }
        }).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(prLineList)) {
            throw new CommonException("error.change.line.cancelled.or.closed", new Object[0]);
        } else {
            prHeader.setPrLineList(prLineList);
        }
    }

    private void checkLines(List<PrLine> prLineList) {
        if (CollectionUtils.isNotEmpty(prLineList)) {
            HashSet<Long> costIdSet = new HashSet<>();
            HashSet<String> wbsCodeSet = new HashSet<>();
            HashSet<Long> budgetAccountIds = new HashSet<>();
            prLineList.forEach(line -> {
                costIdSet.add(line.getCostId());
                wbsCodeSet.add(line.getWbsCode());
                budgetAccountIds.add(line.getBudgetAccountId());
            });
            if (costIdSet.size() > 1 ||
                    wbsCodeSet.size() > 1 || budgetAccountIds.size() > 1
            ) {
                throw new CommonException("error.cost.different");
            }
        }

    }

    private void checkUnit(PrHeader prHeader) {
        Iterator var2 = prHeader.getPrLineList().iterator();

        while (var2.hasNext()) {
            PrLine prLine = (PrLine) var2.next();
            Integer flag = prLine.getUnitFlag();
            if (prLine.getItemId() != null && BaseConstants.Flag.YES.equals(flag) && prHeader.getPrSourcePlatform().equals("SRM")) {
                List<ItemListDTO> content = this.prLineMapper.relatePurchasePriceItemList(prHeader.getTenantId(), prLine.getItemCode(), prLine.getItemName(), (String) null, (Long) null, (Long) null, prHeader.getCompanyId());
                if (content != null && content.size() > 0) {
                    if (((ItemListDTO) content.get(0)).getOrderUomName() != null) {
                        if (!((ItemListDTO) content.get(0)).getOrderUomId().equals(prLine.getUomId())) {
                            throw new CommonException("error.pr.item_unit_error", new Object[0]);
                        }
                    } else if (!((ItemListDTO) content.get(0)).getPrimaryUomId().equals(prLine.getUomId())) {
                        throw new CommonException("error.pr.item_unit_error", new Object[0]);
                    }
                }
            }
        }

    }

    @Override
    public PrHeader copyPurchaseRequests(Long tenantId, PrHeader prHeader) {
        LOGGER.debug("12705 copyPurchaseRequests ====用户信息:{},采购申请=:{}", DetailsHelper.getUserDetails(), JSON.toJSONString(Arrays.asList(prHeader)));
        List<String> headCopyFields = new ArrayList(Arrays.asList(PrHeader.SRM_COPY_FIELD_LIST));
        List<String> lineCopyFields = new ArrayList(Arrays.asList(PrLine.SRM_COPY_FIELD_LIST));
        String tenantNum = TenantInfoHelper.selectByTenantId(tenantId).getTenantNum();

        try {
            PrCopyFieldsVO prCopyFieldsVO = new PrCopyFieldsVO();
            TaskResultBox taskResultBox = AdaptorTaskHelper.executeAdaptorTask("SPUC_PR_COPY_FIELDS", tenantNum, prCopyFieldsVO);
            prCopyFieldsVO = (PrCopyFieldsVO) taskResultBox.get(0, PrCopyFieldsVO.class);
            if (StringUtils.isNotEmpty(prCopyFieldsVO.getTenantHeadCopyFields())) {
                headCopyFields.addAll(new ArrayList(Arrays.asList(prCopyFieldsVO.getTenantHeadCopyFields().split(","))));
            }

            if (StringUtils.isNotEmpty(prCopyFieldsVO.getTenantLineCopyFields())) {
                lineCopyFields.addAll(new ArrayList(Arrays.asList(prCopyFieldsVO.getTenantLineCopyFields().split(","))));
            }
        } catch (TaskNotExistException var11) {
            LOGGER.info("============SPUC_PR_COPY_FIELDS-TaskNotExistException=============={}", new Object[]{tenantNum, var11.getMessage(), var11.getStackTrace()});
        }

        PrHeader copyPrHeader = this.prHeaderRepository.selectCopyPrHeaderFields((String) headCopyFields.stream().map((field) -> {
            return CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, field);
        }).collect(Collectors.joining(",")), prHeader.getPrHeaderId());
        List<PrLine> copyPrLineList = this.prLineRepository.selectCopyPrLineFields((String) lineCopyFields.stream().map((field) -> {
            return CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, field);
        }).collect(Collectors.joining(",")), tenantId, prHeader.getPrHeaderId());
        this.clearCopyPrHeaderFlagAndStatus(copyPrHeader);
        //选中采购申请，点击复制采购申请时，需要自动给表： sprm_pr_header
        //的字段：attribute_varchar17、attribute_varchar18自动赋值为0
        copyPrHeader.setAttributeVarchar17("0");
        copyPrHeader.setAttributeVarchar18("0");
        copyPrHeader.setAttributeVarchar38(prHeaderRepository.selectOne(prHeader).getAttributeVarchar38());
        copyPrHeader = this.addCopyPrHeader(copyPrHeader);
        Map<Long, List<PrLineSupplier>> supplierMap = new HashMap();
        if (CollectionUtils.isNotEmpty(copyPrLineList)) {
            List<PrLineSupplier> prLineSuppliers = this.prLineSupplierRepository.selectByCondition(Condition.builder(PrLineSupplier.class).andWhere(Sqls.custom().andEqualTo("prHeaderId", prHeader.getPrHeaderId())).build());
            if (CollectionUtils.isNotEmpty(prLineSuppliers)) {
                supplierMap.putAll((Map) prLineSuppliers.stream().collect(Collectors.groupingBy(PrLineSupplier::getPrLineId)));
            }
        }

        if (CollectionUtils.isNotEmpty(copyPrLineList)) {
            Iterator var14 = copyPrLineList.iterator();

            while (var14.hasNext()) {
                PrLine prLine = (PrLine) var14.next();
                prLine.setRequestDate(copyPrHeader.getRequestDate());
                prLine.setSupplierList((List) supplierMap.get(prLine.getCopyPrLineId()));
                this.prLineService.clearCopyPrLineFlagAndStatus(prLine);
            }
        }

        copyPrHeader.setIsCopy(BaseConstants.Flag.YES);
        copyPrHeader.setPrLineList(copyPrLineList);
        copyPrHeader.setPrLineList(this.prLineService.updatePrLines(copyPrHeader));

        return copyPrHeader;
    }

    @Override
    public List<PrHeader> batchCreatePrWholeOrder(List<PrHeaderCreateDTO> prHeaderCreateDTOList, String lotNum, Long tenantId) {
        List<PrHeader> totalPrHeaders = ((PrHeaderService) this.self()).batchCreatePrWholeOrderNotSubmit(prHeaderCreateDTOList, lotNum, tenantId);

        //更新头表attribute_varchar38字段 start
        List<PrHeader> PrHeaders = new ArrayList<>();
        totalPrHeaders.forEach(PrHeader -> {
            PrHeader prHeadertemp = new PrHeader();
            prHeadertemp.setObjectVersionNumber(PrHeader.getObjectVersionNumber());
            prHeadertemp.setPrHeaderId(PrHeader.getPrHeaderId());
            prHeadertemp.setAttributeVarchar38(rcwlCompanyService.selectCompanyRcwlUnitName(PrHeader.getCompanyId(), PrHeader.getTenantId()));
            PrHeaders.add(prHeadertemp);
        });
        prHeaderRepository.batchUpdateByPrimaryKeySelective(PrHeaders);
        //更新头表attribute_varchar38字段 end

        if (CollectionUtils.isEmpty(totalPrHeaders)) {
            return totalPrHeaders;
        } else {
            try {
                List<PrHeader> autoSubmitPrHeaders = new ArrayList();
                Map<String, List<PrHeader>> prHeadersMap = (Map) totalPrHeaders.stream().collect(Collectors.groupingBy((prHeader) -> {
                    return prHeader.getPrSourcePlatform() + prHeader.getCompanyId();
                }));
                Iterator var7 = prHeadersMap.entrySet().iterator();

                while (true) {
                    List prHeaderList;
                    String prSourcePlatform;
                    do {
                        Long result;
                        do {
                            if (!var7.hasNext()) {
                                if (CollectionUtils.isNotEmpty(autoSubmitPrHeaders)) {
                                    ((PrHeaderService) this.self()).autoSubmit(DetailsHelper.getUserDetails(), tenantId, autoSubmitPrHeaders);
                                }

                                return totalPrHeaders;
                            }

                            Map.Entry<String, List<PrHeader>> prHeaderMapEntry = (Map.Entry) var7.next();
                            prHeaderList = (List) prHeaderMapEntry.getValue();
                            HashMap<String, String> map = new HashMap();
                            map.put("companyId", ((PrHeader) prHeaderList.get(0)).getCompanyId().toString());
                            map.put("sourcePlatform", ((PrHeader) prHeaderList.get(0)).getPrSourcePlatform());
                            result = 0L;

                            try {
                                result = (Long) CnfHelper.select(tenantId, "SITE.SPUC.PR.AUTO_SUBMIT_AGENT", Long.class).invokeWithParameter(map);
                            } catch (Exception var13) {
                                LOGGER.debug("12705 ====租户id:{},采购申请=:{},查询到的采购申请自动提交异常：{}", new Object[]{tenantId, JSON.toJSONString(prHeaderList), var13});
                            }
                        } while (!this.batchCreateAutoSubmitFlag(result != 0L, prHeaderList));

                        prSourcePlatform = ((PrHeader) prHeaderList.get(0)).getPrSourcePlatform();
                    } while (!StringUtils.equals("CATALOGUE", prSourcePlatform) && !StringUtils.equals("E-COMMERCE", prSourcePlatform));

                    autoSubmitPrHeaders.addAll(prHeaderList);
                }
            } catch (Exception var14) {
                LOGGER.error("batchCreatePrWholeOrder autoSubmit error");
                return totalPrHeaders;
            }
        }
    }

    @Transactional(
            rollbackFor = {Exception.class}
    )
    public List<PrHeader> cancelWholePrNote(Long tenantId, List<PrHeader> prHeaders) {
        prHeaders.forEach((prHeader) -> {
            prHeader.setTenantId(tenantId);
            List<PrLine> prLineList = this.prLineRepository.select(new PrLine(prHeader.getPrHeaderId()));
            try {
                if ("DXCG".equals(prHeader.getAttributeVarchar39()) && prLineList.size() > 0) {
                    this.rcwlPrItfService.invokeBudgetOccupyClose(prHeader, tenantId, "create");
                }
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            prHeader.setPrLineList(prLineList);
            String sourcePlatform = prHeader.getPrSourcePlatform();
            Boolean occupyFlag = prLineList.stream().map(PrLine::getOccupiedQuantity).filter((occQuantity) -> {
                return occQuantity.compareTo(BigDecimal.ZERO) > 0;
            }).count() > 0L;
            String enableFlag = this.budgetService.getConfigCodeValue(tenantId, "SITE.SPUC.BUD.ENABLE_BUDGET_CONTROL");
            if (!BaseConstants.Flag.YES.equals(prHeader.getExpiredCancelFlag()) && !String.valueOf(0).equals(enableFlag)) {
                this.prBudgetService.prBudgetRelease(tenantId, (List) prLineList.stream().map(PrLine::getPrLineId).collect(Collectors.toList()));
            }

            ArrayList reOccupyPrLineList;
            if ("CATALOGUE".equals(sourcePlatform)) {
                if (occupyFlag) {
                    throw new CommonException("error.pr.catalogue_part_cancel_error", new Object[0]);
                }

                ((PrHeaderService) this.self()).cancelCataloguePR(prHeader);
            } else if ("E-COMMERCE".equals(sourcePlatform)) {
                if (occupyFlag) {
                    throw new CommonException("error.pr.e_commerce_part_cancel_error", new Object[0]);
                }

                ((PrHeaderService) this.self()).cancelEcOrder(prHeader);
            } else if ("SHOP".equals(sourcePlatform)) {
                if (occupyFlag) {
                    throw new CommonException("error.pr.e_commerce_part_cancel_error", new Object[0]);
                }

                ((PrHeaderService) this.self()).cancelShopPr(prHeader);
            } else {
                prHeader.checkAndCancel();
                reOccupyPrLineList = new ArrayList();
                Iterator var8 = prLineList.iterator();

                while (var8.hasNext()) {
                    PrLine prLine = (PrLine) var8.next();
                    if (prLine.getOccupiedQuantity().compareTo(BigDecimal.ZERO) > 0) {
                        Long maxNumber = (Long) CustomizeHelper.ignore(() -> {
                            return this.prLineRepository.queryMaxLineNumByPrHeaderId(prHeader.getPrHeaderId());
                        }) + 1L;
                        BigDecimal partQuantity = prLine.getQuantity().subtract(prLine.getOccupiedQuantity());
                        prLine.setClosedFlag(BaseConstants.Flag.YES);
                        prLine.setClosedBy(DetailsHelper.getUserDetails().getUserId());
                        prLine.setClosedDate(new Date());
                        prLine.setQuantity(prLine.getOccupiedQuantity());
                        this.prLineService.countPrLineAmount(prLine);
                        this.prLineRepository.updateOptional(prLine, new String[]{"closedFlag", "closedDate", "closedBy", "closedRemark", "quantity", "lineAmount", "taxIncludedLineAmount"});
                        reOccupyPrLineList.add(prLine);
                        if (partQuantity.compareTo(BigDecimal.ZERO) > 0) {
                            PrLine newPrLine = new PrLine();
                            BeanUtils.copyProperties(prLine, newPrLine);
                            newPrLine.setPrLineId((Long) null);
                            newPrLine.setExecutionBillNum((String) null);
                            newPrLine.setExecutionBillId((Long) null);
                            newPrLine.setExecutionHeaderBillNum((String) null);
                            newPrLine.setExecutionHeaderBillId((Long) null);
                            newPrLine.setExecutionStatusCode((String) null);
                            newPrLine.setLineNum(maxNumber);
                            newPrLine.setDisplayLineNum(maxNumber.toString());
                            newPrLine.setCancelledFlag(BaseConstants.Flag.YES);
                            newPrLine.setCancelledDate(new Date());
                            newPrLine.setCancelledBy(DetailsHelper.getUserDetails().getUserId());
                            newPrLine.setQuantity(partQuantity);
                            newPrLine.setOccupiedQuantity(BigDecimal.ZERO);
                            this.prLineService.countPrLineAmount(newPrLine);
                            this.prLineRepository.insertSelective(newPrLine);
                            Object[] args = new Object[]{prLine.getDisplayLineNum()};
                            String desc = MessageAccessor.getMessage("pr.info.part.cancelled", args).desc();
                            this.prActionService.recordPrAction(prHeader.getPrHeaderId(), newPrLine.getPrLineId(), "NEWLINE", desc, (String) null, (Object) null, (Object) null);
                        }
                    } else {
                        prLine.updateCancelInfo(prHeader.getDisplayPrNum());
                        this.prLineRepository.updateOptional(prLine, new String[]{"cancelledFlag", "cancelledDate", "cancelledBy", "cancelledRemark"});
                    }
                }

                prHeader.countAmount(this.prLineRepository);
                this.prHeaderRepository.updateOptional(prHeader, new String[]{"cancelStatusCode", "amount", "localCurrencyTaxSum", "localCurrencyNoTaxSum"});
                if (!String.valueOf(0).equals(enableFlag) && CollectionUtils.isNotEmpty(reOccupyPrLineList)) {
                    this.prBudgetService.prBudgetOccupy(tenantId, prHeader, reOccupyPrLineList);
                }
            }

            try {
                reOccupyPrLineList = new ArrayList();
                reOccupyPrLineList.add(prHeader);
                this.eventSender.fireEvent("PURCHASE_REQUISIT", "SPRM_PR_LINE", Constants.DEFAULT_TENANT_ID, "CANCELLED", reOccupyPrLineList);
            } catch (Exception var15) {
                LOGGER.error("send cancel Purchase Requisit has failed:========", var15);
            }

            this.prActionService.recordPrAction(prHeader.getPrHeaderId(), "CANCEL", (String) null);
        });
        return prHeaders;
    }

    @Transactional(
            rollbackFor = {Exception.class}
    )
    public void deleteWholePrNote(Long tenantId, List<PrHeader> prHeaderList) {
        prHeaderList.forEach(PrHeader::deletable);
        prHeaderList.forEach((prHeader) -> {
            List<PrLine> prLineList = this.prLineRepository.select(new PrLine(prHeader.getPrHeaderId()));
            try {
                if ("DXCG".equals(prHeader.getAttributeVarchar39()) && prLineList.size() > 0) {
                    this.rcwlPrItfService.invokeBudgetOccupyClose(prHeader, tenantId, "create");
                }
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            prHeader.setTenantId(tenantId);
            Long prHeaderId = prHeader.getPrHeaderId();
            if (null == prHeaderId) {
                throw new CommonException("error.pr.field_not_null", new Object[]{"prHeaderId"});
            } else {
                PrHeader query = new PrHeader();
                query.setTenantId(tenantId);
                query.setPrHeaderId(prHeaderId);
                PrHeader oldPrHeader = (PrHeader) this.prHeaderRepository.selectOne(query);
                if (Objects.isNull(oldPrHeader)) {
                    throw new CommonException("error.pr.not.exists", new Object[0]);
                } else if (!oldPrHeader.validPrSourcePlatformDelete()) {
                    throw new CommonException("sprm.pr_source_platform_pr_not_delete", new Object[0]);
                } else {
                    this.prLineRepository.delete(new PrLine(prHeaderId));
                    this.prHeaderRepository.deleteByPrimaryKey(prHeaderId);
                    this.prActionRepository.delete(new PrAction(prHeaderId));
                }
            }
        });
    }


    /**
     * 电商订单取消
     *
     * @param tenantId
     * @param prNums
     * @return
     */
    @Transactional(
            rollbackFor = {Exception.class}
    )
    public List<PrHeader> cancelElectricityPurchasingExpired(Long tenantId, Set<String> prNums) {
        List<PrHeader> prHeaders = this.prHeaderRepository.selectHeaderAndLine(tenantId, prNums);
        List<PrHeader> sendMsgPrHeaders = new ArrayList();
        sendMsgPrHeaders.addAll(prHeaders);
        Assert.isTrue(prHeaders.size() == prNums.size(), "error.pr.not.exists");
        prHeaders.forEach((d) -> {
            Assert.isTrue("UNCANCELLED".equals(d.getCancelStatusCode()), "error.pr.cancel.status.should.uncancelled");
            /**
             * 预算接口
             */
            try {
                this.rcwlPrItfService.invokeBudgetOccupyClose(d, tenantId, "create");
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            List poHeaders;
            if ("CLOSED".equals(d.getCloseStatusCode())) {
                poHeaders = d.getPrLineList();
                Map<String, List<PrLine>> executionStatusCodes = (Map) poHeaders.stream().collect(Collectors.groupingBy(PrLine::getExecutionStatusCode));
                Assert.isTrue(1 == executionStatusCodes.size(), d.getPrHeaderId() + "error.pr.line.execution.status.not.same");
                executionStatusCodes.forEach((k, v) -> {
                    Assert.isTrue("PO".equals(k), "error.pr.line.not.has.passed" + k);
                });
                Map<Long, List<PrLine>> executionBillIds = (Map) poHeaders.stream().collect(Collectors.groupingBy(PrLine::getExecutionBillId));
                Assert.isTrue(1 == executionBillIds.size(), d.getPrHeaderId() + "error.pr.line.execution.billid.not.same");
            }

            poHeaders = this.poHeaderMapper.selectRejectPoForCancel(tenantId, d.getPrHeaderId());
            if (CollectionUtils.isNotEmpty(poHeaders)) {
                this.poHeaderDomainService.cancelEcPoNew(((PoHeader) poHeaders.get(0)).getPoHeaderId());
                d = (PrHeader) this.prHeaderRepository.selectByPrimaryKey(d);
            }

            PrHeader prHeader = new PrHeader(d.getPrHeaderId(), d.getCloseStatusCode(), d.getCancelStatusCode(), d.getPrStatusCode(), d.getObjectVersionNumber());
            prHeader.setExpiredCancelFlag(BaseConstants.Flag.YES);
            this.cancelWholePrNote(tenantId, Collections.singletonList(prHeader));
            if ("WORKFLOW_APPROVAL".equals(d.getPrStatusCode())) {
                this.endPrHeaderWorkflow(tenantId, d, false);
                prHeader.setPrStatusCode(d.getPrStatusCode());
                this.prHeaderRepository.updateOptional(prHeader, new String[]{"prStatusCode"});
            }

        });
        String enableFlag = this.budgetService.getConfigCodeValue(tenantId, "SITE.SPUC.BUD.ENABLE_BUDGET_CONTROL");
        if (!String.valueOf(0).equals(enableFlag)) {
            try {
                List<PrLine> prLineList = this.prLineRepository.selectByCondition(Condition.builder(PrLine.class).andWhere(Sqls.custom().andIn("prHeaderId", (Iterable) prHeaders.stream().map(PrHeader::getPrHeaderId).collect(Collectors.toList())).andNotEqualTo("freightLineFlag", BaseConstants.Flag.YES)).build());
                this.prBudgetService.prBudgetRelease(tenantId, (List) prLineList.stream().map(PrLine::getPrLineId).collect(Collectors.toList()));
            } catch (Exception var7) {
                LOGGER.error("expired cancel pr error：", var7);
            }
        }

        this.prHeaderCancelExport(tenantId, prHeaders);
        this.sendMessageDemandExpired(sendMsgPrHeaders);
        return prHeaders;
    }


    private void sendMessageDemandExpired(List<PrHeader> sendMsgPrHeaders) {
        try {
            String lang = LanguageHelper.language();
            sendMsgPrHeaders.forEach((prHeader) -> {
                Map<String, String> templateMap = new HashMap(1);
                Map<String, String> args = new HashMap(1);
                String url = null;
                String target = this.demandQueryDetailUrl + prHeader.getPrHeaderId();
                url = "<a onClick=\"openTab({key:'" + target + "',path:'" + target + "',title:'电商商城需求明细'})\">" + prHeader.getPrNum() + "</a>";
                templateMap.put("PR_NUM", url);
                args.put("PR_NUM", prHeader.getPrNum());
                List<User> users = this.prHeaderRepository.selectUsersByPsAndAuTypeCode(prHeader.getTenantId(), "hzero.srm.requirement.prm.pr-approval.ps.default", "COMPANY", prHeader.getCompanyId());
                users = (List) users.stream().filter((user) -> {
                    return user.getEmail() != null;
                }).collect(Collectors.toList());
                List<Receiver> receiverListx = new ArrayList();
                Receiver receiver = new Receiver();
                receiver.setUserId(prHeader.getCreatedBy());
                receiver.setTargetUserTenantId((long) BaseConstants.Flag.NO);
                receiverListx.add(receiver);
                this.messageClient.sendWebMessage(prHeader.getTenantId(), MessageCode.SPUC_DEMAND_EXPIRED.getMessageCode(), lang, receiverListx, templateMap);
                if (prHeader.getPurchaseAgentId() != null) {
                    this.messageHelper.sendMessage(new SpfmMessageSender(prHeader.getTenantId(), MessageCode.SPUC_DEMAND_EXPIRED.getMessageCode(), ReceiverUtils.buildAppointAgent(prHeader.getPurchaseAgentId(), templateMap)));
                }

                if (CollectionUtils.isNotEmpty(users)) {
                    receiverListx.clear();
                    List<Receiver> receiverList = (List) users.stream().map((user) -> {
                        Receiver re = new Receiver();
                        re.setUserId(user.getId());
                        re.setEmail(user.getEmail());
                        re.setTargetUserTenantId((long) BaseConstants.Flag.NO);
                        return re;
                    }).collect(Collectors.toList());
                    Set<Receiver> receiverSet = new HashSet(receiverList);
                    receiverList.clear();
                    receiverList.addAll(receiverSet);
                    this.messageClient.sendEmail(prHeader.getTenantId(), "SRM", MessageCode.SPUC_DEMAND_EXPIRED.getMessageCode(), lang, receiverList, args, new Attachment[0]);
                }

            });
        } catch (Exception var3) {
            LOGGER.error("电商需求失效，消息发送失败", var3);
        }

    }
}
