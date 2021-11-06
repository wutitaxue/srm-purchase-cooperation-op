package org.srm.purchasecooperation.cux.pr.app.service.impl;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.CaseFormat;
import io.choerodon.core.convertor.ApplicationContextHelper;
import io.choerodon.core.exception.CommonException;
import io.choerodon.core.oauth.CustomUserDetails;
import io.choerodon.core.oauth.DetailsHelper;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.servicecomb.pack.omega.context.annotations.SagaStart;
import org.hzero.boot.customize.service.CustomizeClient;
import org.hzero.boot.customize.util.CustomizeHelper;
import org.hzero.boot.message.MessageClient;
import org.hzero.boot.message.entity.Attachment;
import org.hzero.boot.message.entity.Receiver;
import org.hzero.boot.platform.code.builder.CodeRuleBuilder;
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
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.srm.boot.adaptor.client.AdaptorTaskHelper;
import org.srm.boot.adaptor.client.exception.TaskNotExistException;
import org.srm.boot.adaptor.client.result.TaskResultBox;
import org.srm.boot.common.cache.impl.AbstractKeyGenerator;
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
import org.srm.purchasecooperation.common.api.dto.TenantDTO;
import org.srm.purchasecooperation.cux.acp.infra.constant.RCWLAcpConstant;
import org.srm.purchasecooperation.cux.order.app.service.impl.RcwlPoHeaderServiceImpl2;
import org.srm.purchasecooperation.cux.order.domain.repository.RcwlPoHeaderRepository;
import org.srm.purchasecooperation.cux.order.domain.vo.RCWLItemInfoVO;
import org.srm.purchasecooperation.cux.pr.app.service.RCWLPrItfService;
import org.srm.purchasecooperation.cux.pr.app.service.RcwlCompanyService;
import org.srm.purchasecooperation.cux.pr.app.service.RcwlPrToBpmService;
import org.srm.purchasecooperation.cux.pr.app.service.RcwlPrheaderService;
import org.srm.purchasecooperation.cux.pr.domain.entity.RcwlBudgetChangeAction;
import org.srm.purchasecooperation.cux.pr.domain.entity.RcwlPrLineHis;
import org.srm.purchasecooperation.cux.pr.domain.repository.RCWLItfPrDataRespository;
import org.srm.purchasecooperation.cux.pr.domain.repository.RcwlBudgetChangeActionRepository;
import org.srm.purchasecooperation.cux.pr.domain.repository.RcwlPrLineHisRepository;
import org.srm.purchasecooperation.cux.pr.infra.constant.RCWLConstants;
import org.srm.purchasecooperation.cux.pr.infra.mapper.RcwlCheckPoLineMapper;
import org.srm.purchasecooperation.cux.pr.infra.mapper.RcwlPrFeignMapper;
import org.srm.purchasecooperation.cux.pr.utils.constant.PrConstant;
import org.srm.purchasecooperation.order.api.dto.ItemListDTO;
import org.srm.purchasecooperation.order.api.dto.PoDTO;
import org.srm.purchasecooperation.order.api.dto.RcwlBudgetDistributionDTO;
import org.srm.purchasecooperation.order.app.service.PoHeaderService;
import org.srm.purchasecooperation.order.domain.entity.PoHeader;
import org.srm.purchasecooperation.order.domain.entity.PoLine;
import org.srm.purchasecooperation.order.domain.entity.RcwlBudgetDistribution;
import org.srm.purchasecooperation.order.domain.entity.User;
import org.srm.purchasecooperation.order.domain.repository.PoLineRepository;
import org.srm.purchasecooperation.order.domain.repository.RcwlBudgetDistributionRepository;
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
import org.srm.purchasecooperation.pr.infra.constant.PrConstants;
import org.srm.purchasecooperation.pr.infra.feign.ScecRemoteService;
import org.srm.purchasecooperation.pr.infra.mapper.PrLineMapper;
import org.srm.purchasecooperation.pr.infra.utils.OrderCenterUtils;
import org.srm.purchasecooperation.utils.annotation.EventSendTran;
import org.srm.web.annotation.Tenant;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.srm.purchasecooperation.cux.pr.infra.constant.Constants.PlanHeaderApprovalStatus.SUBMITTED;

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
    @Autowired
    private PoHeaderService poHeaderService;
    @Autowired
    private PoLineRepository poLineRepository;
    @Autowired
    private RcwlCheckPoLineMapper rcwlCheckPoLineMapper;
    @Autowired
    private RcwlPoHeaderRepository poHeaderRepository;
    @Autowired
    private CodeRuleBuilder codeRuleBuilder;
    @Autowired
    private ItemCategoryAssignRepository itemCategoryAssignRepository;
    @Value("${service.demand-query-detail}")
    private String demandQueryDetailUrl;
    @Autowired
    private MessageClient messageClient;
    @Autowired
    private MessageHelper messageHelper;
    @Autowired
    private RcwlPrToBpmService rcwlPrToBpmService;
    @Autowired
    private RedisHelper redisHelper;
    @Autowired
    private RcwlBudgetDistributionRepository rcwlBudgetDistributionRepository;
    @Autowired
    private RcwlPrLineHisRepository rcwlPrLineHisRepository;
    @Autowired
    private RcwlBudgetChangeActionRepository rcwlBudgetChangeActionRepository;


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
        // -------------------------add by wangjie 校验当前采购申请所有行的【需求开始日期】及【需求结束日期】不为空 begin--------------------------
        this.judgeDateNull(prHeader);
        // -------------------------add by wangjie 校验当前采购申请所有行的【需求开始日期】及【需求结束日期】不为空 end--------------------------
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
            //prHeader.setPrStatusCode(SUBMITTED);
            prHeader = this.updatePrHeader(prHeader);
            // ----------------add by wangjie 校验同一个采购申请行id下预算分摊总金额=采购申请行总金额;同一个采购申请行id下需求日期从及需求日期至的年份在scux_rcwl_budget_distribution中均存在，否则报错 begin-------
            judgeBudget(prHeader);
            // ----------------add by wangjie 校验同一个采购申请行id下预算分摊总金额=采购申请行总金额;同一个采购申请行id下需求日期从及需求日期至的年份在scux_rcwl_budget_distribution中均存在，否则报错 end-------
            // -------------------------add by wangjie 后面增加的逻辑:将未跨年的需求行的预算数据先删除,自动插入至scux_rcwl_budget_distribution表 begin--------------------------
            // 所有未跨年的预算
            List<RcwlBudgetDistribution> rcwlBudgetDistributions = rcwlBudgetDistributionRepository.selectBudgetDistributionNotAcrossYear(prHeader.getTenantId(), RcwlBudgetDistributionDTO.builder().prHeaderId(prHeader.getPrHeaderId()).build());
            // 删除原有的旧数据
            rcwlBudgetDistributionRepository.deleteBudgetDistributionNotAcrossYear(tenantId, RcwlBudgetDistributionDTO.builder().prHeaderId(prHeader.getPrHeaderId()).prLineIds(rcwlBudgetDistributions.stream().map(RcwlBudgetDistribution::getPrLineId).collect(Collectors.toList())).build());
            rcwlBudgetDistributionRepository.batchInsert(rcwlBudgetDistributions);
            // -------------------------add by wangjie 后面增加的逻辑:将未跨年的需求行的预算数据先删除,自动插入至scux_rcwl_budget_distribution表 end--------------------------
            //判断是否能触发接口
            Integer count = this.rcwlItfPrDataRespository.validateInvokeItf(prHeader.getPrHeaderId(), tenantId);
            if (RCWLConstants.Common.IS.equals(count)) {
                //add by 21420 融创二开，电商/目录化需求不触发占用预算接口
                String prSourcePlatform = prHeader.getPrSourcePlatform();
                if(!(prSourcePlatform.equals(PrConstant.PrSourcePlatform.CATALOGUE) || prSourcePlatform.equals(PrConstant.PrSourcePlatform.E_COMMERCE))) {
                    //保存完之后触发接口
                    try {
                        this.rcwlPrItfService.invokeBudgetOccupy(prHeader, tenantId, null);
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                }
            }
//            prHeader.validateSubmitForBatch(this.prHeaderRepository, this.prLineRepository, this.customizeSettingHelper, this.customizeClient);
//            return ((PrHeaderService) this).submit(tenantId, prHeader);
            //this.prActionService.recordPrAction(prHeader.getPrHeaderId(), "SUBMITTED", "提交至BPM");
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
        List<PrLine> prHeaderOriginLines = this.prLineRepository.selectByCondition(Condition.builder(PrLine.class).andWhere(Sqls.custom().andEqualTo("prHeaderId", prHeader.getPrHeaderId())).build());
        Map<Long, PrLine> beforePrLineMap = prHeaderOriginLines.stream().collect(Collectors.toMap(PrLine::getPrLineId, Function.identity()));        LOGGER.info("Purchase requisition " + beforePrLineMap + " change submit start -------------");
        List<PrLine> changePrlines = prHeader.getPrLineList();
        // ----------------add by wangjie 校验同一个采购申请行id下预算分摊总金额=采购申请行总金额;同一个采购申请行id下需求日期从及需求日期至的年份在scux_rcwl_budget_distribution中均存在，否则报错 begin-------
        // 筛选跟预算有关的字段有变更的申请行(目前有三个字段-行金额、需求开始日期、需求结束日期)
        prHeader.getPrLineList().forEach(PrLine::countLineAmount);
        List<PrLine> changedPrLines = prHeader.getPrLineList().stream().filter(prLine -> prLine.getLineAmount().compareTo(beforePrLineMap.get(prLine.getPrLineId()).getLineAmount()) != 0
                || !prLine.getAttributeDate1().equals(beforePrLineMap.get(prLine.getPrLineId()).getAttributeDate1())
                || !prLine.getNeededDate().equals(beforePrLineMap.get(prLine.getPrLineId()).getNeededDate())).collect(Collectors.toList());
        // 筛选采购申请行未提交的预算变更数据
        List<RcwlBudgetChangeAction> rcwlBudgetChangeActionsNotEnableds = rcwlBudgetChangeActionRepository.selectByCondition(Condition.builder(RcwlBudgetChangeAction.class).andWhere(Sqls.custom().andEqualTo(RcwlBudgetChangeAction.FIELD_PR_HEADER_ID, prHeader.getPrHeaderId())
                .andEqualTo(RcwlBudgetChangeAction.FIELD_TENANT_ID, tenantId).andEqualTo(RcwlBudgetChangeAction.FIELD_ENABLED_FLAG, BaseConstants.Flag.NO))
                .orderByAsc(RcwlBudgetChangeAction.FIELD_PR_HEADER_ID,RcwlBudgetChangeAction.FIELD_PR_LINE_ID,RcwlBudgetChangeAction.FIELD_BUDGET_DIS_YEAR).build());
        // 筛选(new)变更的预算
        List<RcwlBudgetChangeAction> rcwlNewBudgetChangeActionsNotEnableds = rcwlBudgetChangeActionsNotEnableds.stream().filter(rcwlBudgetChangeAction -> RcwlBudgetChangeAction.NEW.equals(rcwlBudgetChangeAction.getBudgetGroup())).collect(Collectors.toList());
        // ---- 做校验之前,先把未变更的变更预算删掉 begin --
        // 查询没有变更过的申请行id
        List<Long> notChangedPrLineIds = changePrlines.stream().map(PrLine::getPrLineId).filter(prLineId -> !changedPrLines.stream().map(PrLine::getPrLineId).collect(Collectors.toSet()).contains(prLineId)).collect(Collectors.toList());
        // 删变更的、未提交的变更预算数据
        rcwlBudgetDistributionRepository.deleteBudgetDistributionNotAcrossYear(tenantId, RcwlBudgetDistributionDTO.builder().prHeaderId(prHeader.getPrHeaderId()).prLineIds(notChangedPrLineIds).changeSubmit(BaseConstants.Flag.YES).enabledFlag(BaseConstants.Flag.NO).build());
        // ---- 做校验之前,先把未变更的变更预算删掉 end --
        judgeChangeBudget(tenantId, prHeader.getPrHeaderId(), changedPrLines, rcwlNewBudgetChangeActionsNotEnableds);
        // ----------------add by wangjie 校验同一个采购申请行id下预算分摊总金额=采购申请行总金额;同一个采购申请行id下需求日期从及需求日期至的年份在scux_rcwl_budget_distribution中均存在，否则报错 end---------
        // -------------------------add by wangjie 后面增加的逻辑:将未跨年的需求行的预算数据自动插入至scux_rcwl_budget_distribution表 begin--------------------------
        // 查询变更行的未跨年预算
        List<RcwlBudgetDistribution> rcwlBudgetDistributionsChanged = rcwlBudgetDistributionRepository.selectBudgetDistributionNotAcrossYear(prHeader.getTenantId(), RcwlBudgetDistributionDTO.builder().prHeaderId(prHeader.getPrHeaderId()).prLineIds(changedPrLines.stream().map(PrLine::getPrLineId).collect(Collectors.toList())).build());
        List<RcwlBudgetChangeAction> rcwlBudgetChangeActions = new ArrayList<>(rcwlBudgetDistributionsChanged.size());
        List<Long> prLineDeleteIds = new ArrayList<>(rcwlBudgetDistributionsChanged.size());
        rcwlBudgetDistributionsChanged.forEach(rcwlBudgetDistribution -> {
            prLineDeleteIds.add(rcwlBudgetDistribution.getPrLineId());
            RcwlBudgetChangeAction rcwlBudgetChangeAction = new RcwlBudgetChangeAction();
            BeanUtils.copyProperties(rcwlBudgetDistribution,rcwlBudgetChangeAction);
            rcwlBudgetChangeAction.setBudgetGroup(RcwlBudgetChangeAction.NEW);
            rcwlBudgetChangeActions.add(rcwlBudgetChangeAction);
        });
        // 删除旧数据
        rcwlBudgetDistributionRepository.deleteBudgetDistributionNotAcrossYear(tenantId, RcwlBudgetDistributionDTO.builder().prHeaderId(prHeader.getPrHeaderId()).prLineIds(prLineDeleteIds).changeSubmit(BaseConstants.Flag.YES).enabledFlag(BaseConstants.Flag.NO).build());
        // 把未跨年的预算变更加到变更数据里面
        rcwlNewBudgetChangeActionsNotEnableds.addAll(rcwlBudgetChangeActionRepository.batchInsertSelective(rcwlBudgetChangeActions));
        // -------------------------add by wangjie 后面增加的逻辑:将未跨年的需求行的预算数据自动插入至scux_rcwl_budget_distribution表 end--------------------------
        // -------------------------add by wangjie 将上个版本的预算数据插入为old值 begin--------------------------
        // 筛选budget_group为old的条数
        List<RcwlBudgetDistribution> rcwlBudgetDistributionsOLD = rcwlBudgetDistributionRepository.selectByCondition(Condition.builder(RcwlBudgetDistribution.class).andWhere(Sqls.custom().andEqualTo(RcwlBudgetDistribution.FIELD_PR_HEADER_ID, prHeader.getPrHeaderId())
                .andEqualTo(RcwlBudgetDistribution.FIELD_TENANT_ID, tenantId)).build());
        List<RcwlBudgetChangeAction> rcwlBudgetChangeActionsOld = new ArrayList<>(rcwlBudgetDistributionsOLD.size());
        rcwlBudgetDistributionsOLD.forEach(rcwlBudgetDistribution -> {
            RcwlBudgetChangeAction rcwlBudgetChangeAction = new RcwlBudgetChangeAction();
            BeanUtils.copyProperties(rcwlBudgetDistribution, rcwlBudgetChangeAction);
            rcwlBudgetChangeActionsOld.add(rcwlBudgetChangeAction);
        });
        rcwlBudgetChangeActionsOld.forEach(rcwlBudgetChangeAction -> rcwlBudgetChangeAction.setBudgetGroup(RcwlBudgetChangeAction.OLD));
        rcwlBudgetChangeActionRepository.batchInsertSelective(rcwlBudgetChangeActionsOld);
        // -------------------------add by wangjie 将上个版本的预算数据插入为old值 end--------------------------
        // ----------add by wangjie 在行处理之前先记录历史数据 begin ---------------------
        List<RcwlPrLineHis> prLineHistories = new ArrayList<>(prHeaderOriginLines.size());
        prHeaderOriginLines.forEach(prLine -> {
            RcwlPrLineHis rcwlPrLineHis = new RcwlPrLineHis();
            BeanUtils.copyProperties(prLine,rcwlPrLineHis);
            prLineHistories.add(rcwlPrLineHis);
        });
        // 查询历史版本号
        List<RcwlPrLineHis> rcwlPrLineHisByPrHeadId = rcwlPrLineHisRepository.selectByCondition(Condition.builder(RcwlPrLineHis.class).andWhere(Sqls.custom().andEqualTo(RcwlPrLineHis.FIELD_PR_HEADER_ID, prHeader.getPrHeaderId()).andEqualTo(RcwlPrLineHis.FIELD_TENANT_ID, tenantId)).build());
        Long version = CollectionUtils.isEmpty(rcwlPrLineHisByPrHeadId) ? 1 : rcwlPrLineHisByPrHeadId.get(0).getVersion();
        prLineHistories.forEach(prLineHistory -> prLineHistory.setVersion(version));
        rcwlPrLineHisRepository.batchInsertSelective(prLineHistories);
        // ----------add by wangjie 在行处理之前先记录历史数据 end ---------------------
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
        // -------------- add by wangjie 变更提交成功后，会插入sprm_pr_action表数据，若预算有改动，则需要增加一条类型为change_field置为budget_dis的记录 begin ------------------------------------
        CustomUserDetails customUserDetails = DetailsHelper.getUserDetails();
        List<PrAction> insertPrChangeActions = new ArrayList(rcwlNewBudgetChangeActionsNotEnableds.size());
        // 记录稍后需要更新的预算数据
        List<RcwlBudgetDistribution> rcwlBudgetDistributions = new ArrayList<>(rcwlNewBudgetChangeActionsNotEnableds.size());
        rcwlNewBudgetChangeActionsNotEnableds.forEach(rcwlBudgetChangeAction -> {
            PrAction prAction = new PrAction();
            prAction.setTenantId(tenantId);
            prAction.setPrHeaderId(prHeader.getPrHeaderId());
            prAction.setDisplayPrNum(prHeader.getDisplayPrNum());
            prAction.setDisplayLineNum(prHeader.getDisplayPrNum());
            prAction.setProcessTypeCode(PrConstants.PrOperationType.UPDATE);
            prAction.setProcessedDate(new Date());
            prAction.setPrLineId(rcwlBudgetChangeAction.getPrLineId());
            prAction.setDisplayLineNum(prHeader.getPrLineList().stream().filter(prLine -> rcwlBudgetChangeAction.getPrLineId().equals(prLine.getPrLineId())).findFirst().orElse(new PrLine()).getDisplayLineNum());
            prAction.setProcessUserId(customUserDetails.getUserId());
            prAction.setProcessUserName(customUserDetails.getRealName());
            prAction.setChangeField(RcwlBudgetChangeAction.FIELD_BUDGET_DIS);
            insertPrChangeActions.add(prAction);
            rcwlBudgetDistributions.add(RcwlBudgetDistribution.builder().prHeaderId(rcwlBudgetChangeAction.getPrHeaderId())
                    .prLineId(rcwlBudgetChangeAction.getPrLineId())
                    .budgetDisYear(rcwlBudgetChangeAction.getBudgetDisYear())
                    .budgetDisAmount(rcwlBudgetChangeAction.getBudgetDisAmount())
                    .budgetDisGap(rcwlBudgetChangeAction.getBudgetDisGap())
                    .tenantId(tenantId).build());
        });
        List<PrAction> prActionList = this.prActionRepository.batchInsertSelective(insertPrChangeActions);
        rcwlBudgetChangeActionsNotEnableds.forEach(rcwlBudgetChangeAction -> {
            Long actionId = prActionList.stream().filter(prAction -> rcwlBudgetChangeAction.getPrHeaderId().equals(prAction.getPrHeaderId()) && rcwlBudgetChangeAction.getPrLineId().equals(prAction.getPrLineId())).findFirst().orElse(new PrAction()).getActionId();
            rcwlBudgetChangeAction.setPrActionId(actionId);
            rcwlBudgetChangeAction.setEnabledFlag(Boolean.TRUE);
        });
        List<RcwlBudgetChangeAction> tempTest = rcwlBudgetChangeActionRepository.selectByCondition(Condition.builder(RcwlBudgetChangeAction.class).andWhere(Sqls.custom().andEqualTo(RcwlBudgetChangeAction.FIELD_PR_HEADER_ID, prHeader.getPrHeaderId())
                .andEqualTo(RcwlBudgetChangeAction.FIELD_TENANT_ID, tenantId).andEqualTo(RcwlBudgetChangeAction.FIELD_ENABLED_FLAG, BaseConstants.Flag.NO)).build());
        tempTest.forEach(temp->{
            LOGGER.error(temp.getBudgetChangeId().toString()+temp.getObjectVersionNumber()+temp.getPrHeaderId()+temp.getPrLineId());
        });
        rcwlBudgetChangeActionRepository.batchUpdateOptional(rcwlBudgetChangeActionsNotEnableds,RcwlBudgetChangeAction.FIELD_PR_ACTION_ID,RcwlBudgetChangeAction.FIELD_ENABLED_FLAG);
        // -------------- add by wangjie 变更提交成功后，会插入sprm_pr_action表数据，若预算有改动，则需要增加一条类型为change_field置为budget_dis的记录 end ------------------------------------
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
        // add by wangjie 根据pr_header_id+pr_line_id删除scux_rcwl_budget_distribution的数据，并将scux_rcwl_budget_change_action中budget_group为new的数据写入scux_rcwl_budget_distribution begin ------
        List<Long> prLineIds = rcwlBudgetChangeActionsNotEnableds.stream().map(RcwlBudgetChangeAction::getPrLineId).collect(Collectors.toList());
        List<RcwlBudgetDistributionDTO> rcwlBudgetDistributionDTOS = rcwlBudgetDistributionRepository.selectBudgetDistribution(tenantId, RcwlBudgetDistributionDTO.builder().prLineIds(prLineIds).prHeaderId(prHeader.getPrHeaderId()).build());
        List<RcwlBudgetDistribution> rcwlBudgetDistributionDeleteDatas = new ArrayList<>(rcwlBudgetDistributionDTOS.size());
        rcwlBudgetDistributionDTOS.forEach(rcwlBudgetDistributionDTO -> {
            rcwlBudgetDistributionDeleteDatas.add(RcwlBudgetDistribution.builder().budgetLineId(rcwlBudgetDistributionDTO.getBudgetLineId()).build());
        });
        rcwlBudgetDistributionRepository.batchDeleteByPrimaryKey(rcwlBudgetDistributionDeleteDatas);
        rcwlBudgetDistributionRepository.batchInsertSelective(rcwlBudgetDistributions);
        // add by wangjie 根据pr_header_id+pr_line_id删除scux_rcwl_budget_distribution的数据，并将scux_rcwl_budget_change_action中budget_group为new的数据写入scux_rcwl_budget_distribution end --------
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
            return (!BaseConstants.Flag.YES.equals(prLine.getClosedFlag()) || ("SOURCE_RFX".equals(prLine.getExecutionStatusCode())||"SOURCE_BID".equals(prLine.getExecutionStatusCode()))) && !BaseConstants.Flag.YES.equals(prLine.getCancelledFlag());
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
        List<Long> prHeaderIds = new ArrayList<>();
        totalPrHeaders.forEach(PrHeader -> {
            PrHeader prHeadertemp = new PrHeader();
            prHeadertemp.setObjectVersionNumber(PrHeader.getObjectVersionNumber());
            prHeadertemp.setPrHeaderId(PrHeader.getPrHeaderId());
            prHeadertemp.setAttributeVarchar38(rcwlCompanyService.selectCompanyRcwlUnitName(PrHeader.getCompanyId(), PrHeader.getTenantId()));
            PrHeaders.add(prHeadertemp);
            prHeaderIds.add(PrHeader.getPrHeaderId());
        });
        prHeaderRepository.batchUpdateByPrimaryKeySelective(PrHeaders);
        //更新头表attribute_varchar38字段 end
        //add by 21420 防止乐观锁，再查询一下
        totalPrHeaders = prHeaderRepository.selectByPrHeaderIds(prHeaderIds);

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

    @Override
    @Transactional(
            rollbackFor = {Exception.class}
    )
    public List<PrHeader> cancelWholePrNote(Long tenantId, List<PrHeader> prHeaders) {
        prHeaders.forEach((prHeader) -> {
            prHeader.setTenantId(tenantId);
            List<PrLine> prLineList = this.prLineRepository.select(new PrLine(prHeader.getPrHeaderId()));
            LOGGER.info("================>PrSourcePlatform is"+prHeader.getPrSourcePlatform()+"AttributeVarchar39 is prHeader.getAttributeVarchar39()"
            +"lines size is prLineList.size()");
            try {
                if ((!"E-COMMERCE".equals(prHeader.getPrSourcePlatform())&&"DXCG".equals(prHeader.getAttributeVarchar39()) && prLineList.size() > 0)
                ||"E-COMMERCE".equals(prHeader.getPrSourcePlatform())
                ) {
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

    @Override
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
                    // 删预算数据、变更预算数据
                    rcwlBudgetDistributionRepository.deleteBudgetDistributionNotAcrossYear(tenantId, RcwlBudgetDistributionDTO.builder().prHeaderId(prHeaderId).build());
                    rcwlBudgetDistributionRepository.deleteBudgetDistributionNotAcrossYear(tenantId, RcwlBudgetDistributionDTO.builder().prHeaderId(prHeaderId).changeSubmit(BaseConstants.Flag.YES).build());
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
            /**
             * 预算接口
             */
//            try {
//                this.rcwlPrItfService.invokeBudgetOccupyClose(d, tenantId, "create");
//            } catch (JsonProcessingException e) {
//                e.printStackTrace();
//            }
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
    @Override
    @Transactional(
            rollbackFor = {Exception.class}
    )
    @EventSendTran(
            rollbackFor = {Exception.class}
    )
    @Async
    public void afterPrApprove(Long tenantId, List<PrHeader> prHeaderList) {
        LOGGER.info("24730===================进来了111");
        LOGGER.debug("需求自动转单异步开始");
        if (DetailsHelper.getUserDetails() == null) {
            LOGGER.debug("需求转单设置UserDetail： {}", ((PrHeader)prHeaderList.get(0)).getCustomUserDetails());
            if (((PrHeader)prHeaderList.get(0)).getCustomUserDetails() != null) {
                if (((PrHeader)prHeaderList.get(0)).getCustomUserDetails().getLanguage() == null) {
                    ((PrHeader)prHeaderList.get(0)).getCustomUserDetails().setLanguage("zh_CN");
                }

                if (((PrHeader)prHeaderList.get(0)).getCustomUserDetails().getOrganizationId() == null) {
                    ((PrHeader)prHeaderList.get(0)).getCustomUserDetails().setOrganizationId(tenantId);
                }

                if (((PrHeader)prHeaderList.get(0)).getCustomUserDetails().getUserId() == null) {
                    ((PrHeader)prHeaderList.get(0)).getCustomUserDetails().setUserId(0L);
                }

                DetailsHelper.setCustomUserDetails(((PrHeader)prHeaderList.get(0)).getCustomUserDetails());
            } else {
                CustomUserDetails customUserDetails = new CustomUserDetails("1", "1");
                customUserDetails.setTenantId(tenantId);
                customUserDetails.setLanguage("zh_CN");
                customUserDetails.setOrganizationId(tenantId);
                customUserDetails.setUserId(0L);
                TenantDTO tenantDTO = this.poHeaderMapper.selectTenantById(tenantId);
                customUserDetails.setTenantNum(tenantDTO.getTenantNum());
                DetailsHelper.setCustomUserDetails(customUserDetails);
                LOGGER.error("需求转单手动设置UserDetail： {}", customUserDetails.toString());
            }
        }

        String platform = ((PrHeader)prHeaderList.get(0)).getPrSourcePlatform();
        if (("E-COMMERCE".equals(platform) || "CATALOGUE".equals(platform)) && ((PrHeader)prHeaderList.get(0)).getCreatedBy() != null) {
            DetailsHelper.getUserDetails().setUserId(((PrHeader)prHeaderList.get(0)).getCreatedBy());
        }

        try {
            this.autoPrToPo(tenantId, prHeaderList);
        } catch (Exception var8) {
            LOGGER.error("需求转单失败： 数据长度是{}", JSON.toJSONString(prHeaderList.size()));
            LOGGER.error(var8.getMessage(), var8);
            throw new CommonException(var8, new Object[0]);
        } finally {
            SecurityContextHolder.clearContext();
        }

    }
    @Override
    @Transactional(
            rollbackFor = {Exception.class}
    )
    @EventSendTran(
            rollbackFor = {Exception.class}
    )
    public void autoPrToPo(Long tenantId, List<PrHeader> prHeaderList) {
        LOGGER.info("24730===================进来了222");
        LOGGER.debug("24497====自动转单开始，数据是：{}", prHeaderList);
        if (!CollectionUtils.isEmpty(prHeaderList)) {
            List<PrHeader> approvedPrHeaderList = (List)prHeaderList.stream().filter((prHeader) -> {
                return "APPROVED".equals(prHeader.getPrStatusCode());
            }).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(approvedPrHeaderList)) {
                approvedPrHeaderList.stream().filter((prHeader) -> {
                    return "E-COMMERCE".equals(prHeader.getPrSourcePlatform());
                }).forEach((prHeader) -> {
                    Long prTypeId = prHeader.getPrTypeId();
                    String prSourcePlatform = prHeader.getPrSourcePlatform();
                    Long companyId = prHeader.getCompanyId();
                    Long purchaseOrgId = prHeader.getPurchaseOrgId();
                    HashMap<String, String> map = new HashMap();
                    map.put("prType", prTypeId + "");
                    map.put("sourceFrom", prSourcePlatform);
                    map.put("company", companyId + "");
                    map.put("purchaseOrganization", purchaseOrgId + "");
                    Map<String, String> t = new HashMap();
                    LOGGER.debug("PrHeaderServiceImpl.autoPrToPo====query auto pr to po configtenant id is :{} ,param is :{} ", prHeader.getTenantId(), map);

                    try {
                        t = this.autoPrToPoFlag(map, prHeader);
                    } catch (Exception var17) {
                        LOGGER.debug("24497====申请头id:{},查询到的自动转单报错，错误信息是是：{}", prHeader.getPrHeaderId(), var17);
                    }

                    LOGGER.debug("24497====申请头id:{},查询到商城的自动转单配置是：{}", prHeader.getPrHeaderId(), t);
                    if (t != null && ((Map)t).size() > 0) {
                        String result = (String)((Map)t).get("result");
                        String transFrequency = (String)((Map)t).get("transFrequency");
                        if (StringUtils.equals(BaseConstants.Flag.YES.toString(), result) && StringUtils.equals(transFrequency, "IMMEDIATELY")) {
                            List<PrLine> prLines = prHeader.getPrLineList();
                            if (prLines == null) {
                                prLines = this.prLineRepository.selectByCondition(Condition.builder(PrLine.class).andWhere(Sqls.custom().andEqualTo("prHeaderId", prHeader.getPrHeaderId())).build());
                            }

                            PoDTO poDTO;
                            try {
                                poDTO = (PoDTO)CustomizeHelper.ignore(() -> {
                                    return this.poHeaderService.referWholePrHeaderAuto(tenantId, prHeader.getPrHeaderId());
                                });
                                this.processPrLineReturn(prLines, (String)null, "success");
                                LOGGER.info("=====订单创建成功");
                            } catch (Exception var16) {
                                String orderErroMessage = var16.getMessage();
                                LOGGER.error("====referWholePrHeaderAuto error is {}", orderErroMessage);
                                this.processPrLineReturn(prLines, orderErroMessage, "fail");
                                LOGGER.info("====订单创建失败");
                                return;
                            }

                            String poChangeStatus = this.queryAutoPoStatus(poDTO);
                            if ("SUBMITTED".equals(poChangeStatus)) {
                                try {
                                    this.poHeaderService.batchSubmittedPo(Arrays.asList(poDTO));
                                } catch (Exception var15) {
                                    LOGGER.error("订单自动提交失败： {}", JSON.toJSONString(Arrays.asList(poDTO)));
                                    LOGGER.error(var15.getMessage(), var15);
                                    throw new CommonException(var15, new Object[0]);
                                }
                            }
                            //bugfix-0623-jyb start
                            //校验po行商品编号有无物料编码
                            PoLine poLine = new PoLine();
                            poLine.setPoHeaderId(poDTO.getPoHeaderId());
                            poLine.setTenantId(tenantId);
                            List<PoLine> PoLines = poLineRepository.select(poLine);
                            List<PoLine> poLineList = new ArrayList<>();
                            PoLines.forEach(itemLine-> {
                                PoLine rcwlItemInfoVO = rcwlCheckPoLineMapper.checkPoItem(itemLine.getProductNum(), itemLine.getTenantId());
                                LOGGER.info("24730===================rcwlItemInfoVO"+rcwlItemInfoVO);
                                //没有物料创建编码并插入订单
                                if (rcwlItemInfoVO == null) {
                                    //查询需要封装的item数据集合(排除存在物料id的数据)
                                    List listTemp = new ArrayList();
                                    listTemp.add(itemLine.getPoLineId());
                                    List<RCWLItemInfoVO> rcwlItemInfoVOList = poHeaderRepository.selectItemListByPoLineIdList(listTemp, tenantId);
                                    List<ItemCategoryAssign> itemCategoryAssignList = new ArrayList<>();
                                    // 生成对应条编码
                                    if (CollectionUtils.isNotEmpty(rcwlItemInfoVOList)) {
                                        //判断来自哪个电商平台
                                        String dsFlag = poHeaderRepository.selectShopMallSupplier(poDTO.getPoHeaderId(),tenantId);
                                        LOGGER.info("24730===================dsFlag"+dsFlag);
                                        rcwlItemInfoVOList.forEach(item -> {
                                            String itemCode = null;
                                            String categoryCode = item.getItemCode();
                                            //电商来源的物料编码为 品类编码+来源（00，01，02等）+电商编码
                                            if("CG".equals(dsFlag)){
                                                itemCode = categoryCode+"04"+item.getProductNum();
                                            }else if("ZKH".equals(dsFlag)){
                                                itemCode = categoryCode+"03"+item.getProductNum();
                                            }else if("JD".equals(dsFlag)){
                                                itemCode = categoryCode+"02"+item.getProductNum();
                                            }else{
                                                String str = this.codeRuleBuilder.generateCode("SODR.RCWL.ITEM_CODE",  (Map) null);
                                                itemCode = categoryCode+str;
                                            }

                                            //物料设值
                                            item.setTenantId(tenantId);
                                            //String categoryCode = item.getItemCode();
                                            // String str = this.codeRuleBuilder.generateCode(DetailsHelper.getUserDetails().getTenantId(), "SODR.RCWL.ITEM_CODE", "GLOBAL", "GLOBAL", (Map) null);
                                            String ruleCode = this.codeRuleBuilder.generateCode("SMDM.ITEM", (Map) null);
                                            item.setItemNumber(ruleCode);
                                            item.setItemCode(itemCode);
                                            item.setQueryItemCode(itemCode);
                                            item.setSpecifications(item.getSpecifications());
                                            item.setModel(item.getModel());
                                        });
                                        //批量插入物料表
                                        poHeaderRepository.batchInsertItem(rcwlItemInfoVOList);

                                        List<Long> lineIds = rcwlItemInfoVOList.stream().map(RCWLItemInfoVO::getPoLineId).distinct().collect(Collectors.toList());
                                        //查询出需要封装好的itemCategoryAssign
                                        List<RCWLItemInfoVO> itemCategoryList = poHeaderRepository.selectItemCategoryListByPoLineIdList(lineIds, tenantId);

                                        if (CollectionUtils.isNotEmpty(itemCategoryList)) {
                                            itemCategoryList.forEach(item -> {
                                                ItemCategoryAssign itemCategoryAssign = new ItemCategoryAssign();
                                                itemCategoryAssign.setItemId(item.getItemId());
                                                itemCategoryAssign.setTenantId(tenantId);
                                                itemCategoryAssign.setSourceCode(item.getSourceCode());
                                                itemCategoryAssign.setCategoryId(item.getCategoryId());
                                                itemCategoryAssignList.add(itemCategoryAssign);
                                            });
                                            //批量插入物料分配品类表
                                            itemCategoryAssignRepository.batchInsertSelective(itemCategoryAssignList);
                                        }
                                        //把item_id item_code回写到订单行
                                        List<RCWLItemInfoVO> poLineList2 = new ArrayList<>();
                                        List<PoLine> poLineList1 = new ArrayList<>();
                                        if (CollectionUtils.isNotEmpty(itemCategoryList)) {
                                            itemCategoryList.forEach(poLine2-> {
                                                RCWLItemInfoVO lineUpdateInfo = new RCWLItemInfoVO();
                                                PoLine poLine1 = this.poLineRepository.selectByPrimaryKey(poLine2.getPoLineId());
                                                lineUpdateInfo.setTenantId(tenantId);
                                                lineUpdateInfo.setItemId(poLine2.getItemId());
                                                lineUpdateInfo.setItemCode(poLine2.getItemCode());
                                                lineUpdateInfo.setPoLineId(poLine2.getPoLineId());
                                                lineUpdateInfo.setItemName(poLine2.getItemName());
                                                poLine1.setItemId(poLine2.getItemId());
                                                poLine1.setItemCode(poLine2.getItemCode());
                                                poLineList2.add(lineUpdateInfo);
                                                poLineList1.add(poLine1);
                                            });
                                            //批量更新订单物料id和code
                                            // poHeaderRepository.batchUpdatePoLine(poLineList);
                                            this.poLineRepository.batchUpdateByPrimaryKeySelective(poLineList1);
                                            //批量插入物料名称多语言表smdm_item_tl
                                            poHeaderRepository.batchInsertItemTl(poLineList2);}
                                    }
                                    //存在则只插入订单行物料编码和物料id物料名称
                                }else {
                                    rcwlItemInfoVO.setPoLineId(itemLine.getPoLineId());
                                    rcwlItemInfoVO.setObjectVersionNumber(itemLine.getObjectVersionNumber());
                                    poLineList.add(rcwlItemInfoVO);
                                }
                            });
                            if (poLineList.size() > 0) {
                                //批量更新订单物料id和code
                                poLineRepository.batchUpdateByPrimaryKeySelective(poLineList);
                            }
                            //bugfix-0623-jyb end
                        }
                    }

                });
                ((PrHeaderService) ApplicationContextHelper.getContext().getBean(PrHeaderService.class)).generatorPoByPrAuto(approvedPrHeaderList, tenantId);
            }
        }
    }

    @Override
    public PrHeader prApprove(PrHeader prHeader,Long tenantId) {

        //查询审批类型
//        String approveMethod = prApproveMethod(prHeader.getTenantId(), prHeader.getCompanyId(), prHeader.getPrSourcePlatform());
        String approveMethod = this.prApproveMethodNew(prHeader);
        // 审批方式为空时 默认为无需审批
        if(approveMethod == null){
            approveMethod = PrConstants.PrApprovalMethod.NONE;
        }

        LOGGER.info("approveMethod: 审批方式：{} " + approveMethod);
        // 新商城 查询租户OMS标识，true：开启，false：未开启
        boolean omsFlag = !this.isOldMall(tenantId) && OrderCenterUtils.enableOrderCenterFlag(tenantId);

        //以下下为标准逻辑复制
        //电商订单且状态为新建则 进行电商异步提交逻辑，未开启OMS标识走原有逻辑，开启OMS电商和目录化无需商城处理
        if ( !omsFlag && PrConstants.PrSrcPlatformCode.E_COMMERCE.equals(prHeader.getPrSourcePlatform()) && (PrConstants.PrStatusCode.PENDING.equals(prHeader.getPrStatusCode()) || PrConstants.PrStatusCode.REJECTED.equals(prHeader.getPrStatusCode()))) {
            this.ecSubmitSync(tenantId, prHeader);
        } else {
            prHeader.setEventSenderFlag(BaseConstants.Flag.NO);
            // 非电商
            LOGGER.info("approveMethod: 非电商 {} , prHeader {}" ,approveMethod, prHeader);
            switch (approveMethod) {
                // 功能审批
                case PrConstants.PrApprovalMethod.FUNCTIONAL:
                    this.submitFunctional(tenantId, prHeader);
                    break;
                // 外部系统审批
                case PrConstants.PrApprovalMethod.EXTERNAL_APPROVAL:
                    //外部系统审批改为BPM审批，将之前的BPM逻辑复制到此处
                    // prHeaderService.submitExternal(tenantId, prHeader);
                    String dataToBpmUrl = this.rcwlPrToBpmService.prDataToBpm(prHeader, "create");
                    this.prActionService.recordPrAction(prHeader.getPrHeaderId(), "SUBMITTED", "提交至BPM");
                    prHeader.setAttributeVarchar37(dataToBpmUrl);
                    break;
                // 工作流审批
                case PrConstants.PrApprovalMethod.WORKFLOW:
                    this.submitWorkflow(tenantId, prHeader);
                    break;
                // 无需审批
                case PrConstants.PrApprovalMethod.NONE:
                    // 其他 默认无需审批
                default:
                    this.submitNone(tenantId, prHeader);
                    break;
            }
        }
        return prHeader;
    }
    /**
     * 判断是否为老商城
     * @param tenantId
     * @return
     */
    private Boolean isOldMall(Long tenantId) {
        String key = AbstractKeyGenerator.getKey(PrConstants.CacheCode.SCEC_SERVICE_NAME, PrConstants.CacheCode.OLD_MALL_TENANT);
        return redisHelper.hshHasKey(key,tenantId.toString());
    }

    /**
     * 校验当前采购申请所有行的【需求开始日期】及【需求结束日期】不为空
     *
     * @param prHeader 采购申请头
     */
    private void judgeDateNull(PrHeader prHeader) {
        // -----------------add by 王杰 校验当前采购申请所有行的【需求开始日期】及【需求结束日期】不为空 begin-----------------------------
        String neededDateEmptyLineIds = prHeader.getPrLineList().stream().filter(prLine -> ObjectUtils.isEmpty(prLine.getNeededDate())).map(prLine -> String.valueOf(prLine.getPrLineId())).collect(Collectors.joining(BaseConstants.Symbol.COMMA));
        if (StringUtils.isNotEmpty(neededDateEmptyLineIds)) {
            throw new CommonException("error.pr.line.neededDate.can.not.null", prHeader.getPrHeaderId(), neededDateEmptyLineIds);
        }
        String attributeDate1EmptyLineIds = prHeader.getPrLineList().stream().filter(prLine -> ObjectUtils.isEmpty(prLine.getAttributeDate1())).map(prLine -> String.valueOf(prLine.getPrLineId())).collect(Collectors.joining(BaseConstants.Symbol.COMMA));
        if (StringUtils.isNotEmpty(attributeDate1EmptyLineIds)) {
            throw new CommonException("error.pr.line.attributeDate1.can.not.null", prHeader.getPrHeaderId(), attributeDate1EmptyLineIds);
        }
        // -----------------add by 王杰 校验当前采购申请所有行的【需求开始日期】及【需求结束日期】不为空 end---------------------------------
    }

    /**
     * 新建预算提交校验
     * 校验同一个采购申请行id下预算分摊总金额=采购申请行总金额报错;
     * 同一个采购申请行id下需求日期从及需求日期至的年份在scux_rcwl_budget_distribution中均存在，否则报错
     *
     * @param prHeader
     */
    private void judgeBudget(PrHeader prHeader) {
        // 获取采购申请跨年预算信息
        List<RcwlBudgetDistributionDTO> rcwlBudgetDistributionDTOS = rcwlBudgetDistributionRepository.selectBudgetDistribution(prHeader.getTenantId(), RcwlBudgetDistributionDTO.builder().prHeaderId(prHeader.getPrHeaderId()).build());
        // 获取采购申请各行的需求开始年和需求结束年
        List<RcwlBudgetDistributionDTO> rcwlBudgetDistributionPrLines = rcwlBudgetDistributionRepository.selectBudgetDistributionByPrLine(prHeader.getTenantId(), RcwlBudgetDistributionDTO.builder().prHeaderId(prHeader.getPrHeaderId()).build());
        if (CollectionUtils.isNotEmpty(rcwlBudgetDistributionPrLines)) {
            rcwlBudgetDistributionPrLines.forEach(rcwlBudgetDistribution -> {
                // 先判断是否跨年
                if (!rcwlBudgetDistribution.getAttributeDate1Year().equals(rcwlBudgetDistribution.getNeededDateYear())) {
                    if (CollectionUtils.isNotEmpty(rcwlBudgetDistributionDTOS)) {
                        // 申请行金额不为空且跨年预算行为空
                        long prLineBudgetCount = rcwlBudgetDistributionDTOS.stream().filter(rcwlBudgetDistributionDTO -> rcwlBudgetDistribution.getPrLineId().equals(rcwlBudgetDistributionDTO.getPrLineId())).count();
                        if (prLineBudgetCount == 0 && rcwlBudgetDistribution.getLineAmount().compareTo(new BigDecimal(0)) != 0) {
                            throw new CommonException("error.pr.line.num.amount.budget.error", rcwlBudgetDistribution.getLineNum());
                        }
                        boolean amountEqual = rcwlBudgetDistribution.getLineAmount().compareTo(rcwlBudgetDistributionDTOS.stream().filter(rcwlBudgetDistributionDTO -> rcwlBudgetDistribution.getPrLineId().equals(rcwlBudgetDistributionDTO.getPrLineId())).map(RcwlBudgetDistributionDTO::getBudgetDisAmount).reduce(BigDecimal.ZERO, BigDecimal::add)) != 0;
                        if (amountEqual) {
                            throw new CommonException("error.pr.line.num.amount.budget.error", rcwlBudgetDistribution.getLineNum());
                        }
                        List<RcwlBudgetDistributionDTO> budgetDistributionLineDTOS = rcwlBudgetDistributionDTOS.stream().filter(rcwlBudgetDistributionDTO -> rcwlBudgetDistribution.getPrLineId().equals(rcwlBudgetDistributionDTO.getPrLineId())).collect(Collectors.toList());
                        boolean prLineYear = budgetDistributionLineDTOS.get(0).getBudgetDisYear().compareTo(rcwlBudgetDistribution.getAttributeDate1Year())!=0|| budgetDistributionLineDTOS.get(budgetDistributionLineDTOS.size() - 1).getBudgetDisYear().compareTo(rcwlBudgetDistribution.getNeededDateYear())!=0;
                        if (prLineYear) {
                            throw new CommonException("error.pr.line.num.year.budget.error", rcwlBudgetDistribution.getLineNum());
                        }
                    } else {
                        throw new CommonException("error.pr.line.num.amount.budget.error", rcwlBudgetDistribution.getLineNum());
                    }
                }
            });
        }
    }

    /**
     * 变更预算提交校验
     * 校验同一个采购申请行id下预算分摊总金额=采购申请行总金额报错;
     * 同一个采购申请行id下需求日期从及需求日期至的年份在scux_rcwl_budget_distribution中均存在，否则报错
     *
     * @param tenantId
     * @param prHeaderId
     * @param changedPrLines 做过变更的采购申请行
     * @param rcwlBudgetChangeActionsNotEnableds 筛选采购申请行未提交的预算变更数据
     */
    private void judgeChangeBudget(Long tenantId, Long prHeaderId, List<PrLine> changedPrLines, List<RcwlBudgetChangeAction> rcwlBudgetChangeActionsNotEnableds) {
        if (CollectionUtils.isNotEmpty(changedPrLines)) {
            // 获取采购申请各行的需求开始年和需求结束年
            List<RcwlBudgetDistributionDTO> rcwlBudgetDistributionPrLines = rcwlBudgetDistributionRepository.selectBudgetDistributionByPrLine(tenantId, RcwlBudgetDistributionDTO.builder().prHeaderId(prHeaderId).prLineIds(changedPrLines.stream().map(PrLine::getPrLineId).collect(Collectors.toList())).changeSubmit(BaseConstants.Flag.YES).build());
            if (CollectionUtils.isNotEmpty(rcwlBudgetDistributionPrLines)) {
                changedPrLines.forEach(prLine -> {
                    // 获取计算好的采购申请行预算数据
                    RcwlBudgetDistributionDTO rcwlBudgetDistribution = rcwlBudgetDistributionPrLines.stream().filter(rcwlBudgetDistributionPrLine -> prLine.getPrLineId().equals(rcwlBudgetDistributionPrLine.getPrLineId())).findFirst().orElse(new RcwlBudgetDistributionDTO());
                    // 先判断是否跨年
                    if (!rcwlBudgetDistribution.getAttributeDate1Year().equals(rcwlBudgetDistribution.getNeededDateYear())) {
                        if (CollectionUtils.isNotEmpty(rcwlBudgetChangeActionsNotEnableds)) {
                            // 申请行金额不为空且跨年预算行为空
                            long prLineBudgetChangeActionCount = rcwlBudgetChangeActionsNotEnableds.stream().filter(rcwlBudgetDistributionDTO -> prLine.getPrLineId().equals(rcwlBudgetDistributionDTO.getPrLineId())).count();
                            if (prLineBudgetChangeActionCount == 0 && prLine.getLineAmount().compareTo(new BigDecimal(0)) > 0) {
                                throw new CommonException("error.pr.line.num.amount.budget.error", prLine.getLineNum());
                            }
                            boolean amountEqual = prLine.getLineAmount().compareTo(rcwlBudgetChangeActionsNotEnableds.stream().filter(rcwlBudgetDistributionDTO -> prLine.getPrLineId().equals(rcwlBudgetDistributionDTO.getPrLineId())).map(RcwlBudgetChangeAction::getBudgetDisAmount).reduce(BigDecimal.ZERO, BigDecimal::add)) != 0;
                            if (amountEqual) {
                                throw new CommonException("error.pr.line.num.amount.budget.error", prLine.getLineNum());
                            }
                            List<RcwlBudgetChangeAction> rcwlBudgetChangeLineActions = rcwlBudgetChangeActionsNotEnableds.stream().filter(rcwlBudgetDistributionDTO -> prLine.getPrLineId().equals(rcwlBudgetDistributionDTO.getPrLineId())).collect(Collectors.toList());
                            boolean prLineYear = rcwlBudgetChangeLineActions.get(0).getBudgetDisYear().equals(rcwlBudgetDistribution.getAttributeDate1Year()) && rcwlBudgetChangeLineActions.get(rcwlBudgetChangeLineActions.size() - 1).getBudgetDisYear().equals(rcwlBudgetDistribution.getNeededDateYear());
                            if (!prLineYear) {
                                throw new CommonException("error.pr.line.num.year.budget.error", prLine.getLineNum());
                            }
                        } else {
                            throw new CommonException("error.pr.line.num.amount.budget.error", prLine.getLineNum());
                        }
                    }
                });
            } else {
                LOGGER.debug("========================>没有需要跨年的采购申请变更预算<=================================");
            }
        }

    }
}
