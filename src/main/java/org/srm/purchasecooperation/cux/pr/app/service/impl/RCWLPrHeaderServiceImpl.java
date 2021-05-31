package org.srm.purchasecooperation.cux.pr.app.service.impl;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.choerodon.core.exception.CommonException;
import io.choerodon.core.oauth.DetailsHelper;
import org.activiti.rest.service.api.engine.variable.RestVariable;
import org.activiti.rest.service.api.runtime.process.ProcessInstanceCreateRequest;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.servicecomb.pack.omega.context.annotations.SagaStart;
import org.hzero.boot.customize.service.CustomizeClient;
import org.hzero.boot.platform.plugin.hr.EmployeeHelper;
import org.hzero.boot.workflow.WorkflowClient;
import org.hzero.core.base.BaseConstants;
import org.hzero.core.redis.RedisHelper;
import org.hzero.core.util.ResponseUtils;
import org.hzero.mybatis.domian.Condition;
import org.hzero.mybatis.util.Sqls;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.srm.boot.adaptor.client.AdaptorTaskHelper;
import org.srm.boot.adaptor.client.exception.TaskNotExistException;
import org.srm.boot.adaptor.client.result.TaskResultBox;
import org.srm.boot.common.cache.impl.AbstractKeyGenerator;
import org.srm.boot.platform.customizesetting.CustomizeSettingHelper;
import org.srm.boot.platform.group.GroupApproveHelper;
import org.srm.boot.platform.group.dto.ProcessApproveDTO;
import org.srm.boot.saga.utils.SagaClient;
import org.srm.common.TenantInfoHelper;
import org.srm.purchasecooperation.asn.infra.utils.CopyUtils;
import org.srm.purchasecooperation.cux.acp.infra.constant.RCWLAcpConstant;
import org.srm.purchasecooperation.cux.pr.app.service.RCWLPrItfService;
import org.srm.purchasecooperation.cux.pr.app.service.RcwlPrheaderService;
import org.srm.purchasecooperation.cux.pr.domain.repository.RCWLItfPrDataRespository;
import org.srm.purchasecooperation.cux.pr.infra.constant.RCWLConstants;
import org.srm.purchasecooperation.cux.pr.utils.constant.PrConstant;
import org.srm.purchasecooperation.order.api.dto.ItemListDTO;
import org.srm.purchasecooperation.order.infra.constant.MessageCode;
import org.srm.purchasecooperation.pr.app.service.PrActionService;
import org.srm.purchasecooperation.pr.app.service.PrHeaderService;
import org.srm.purchasecooperation.pr.app.service.PrLineService;
import org.srm.purchasecooperation.pr.app.service.impl.PrHeaderServiceImpl;
import org.srm.purchasecooperation.pr.domain.entity.PrAction;
import org.srm.purchasecooperation.pr.domain.entity.PrChangeConfig;
import org.srm.purchasecooperation.pr.domain.entity.PrHeader;
import org.srm.purchasecooperation.pr.domain.entity.PrLine;
import org.srm.purchasecooperation.pr.domain.repository.PrActionRepository;
import org.srm.purchasecooperation.pr.domain.repository.PrChangeConfigRepository;
import org.srm.purchasecooperation.pr.domain.repository.PrHeaderRepository;
import org.srm.purchasecooperation.pr.domain.repository.PrLineRepository;
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
    private CustomizeClient customizeClient;
    @Autowired
    private RCWLItfPrDataRespository rcwlItfPrDataRespository;
    @Autowired
    private GroupApproveHelper groupApproveHelper;
    @Autowired
    private WorkflowClient workflowClient;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ScecRemoteService scecRemoteService;
    @Autowired
    private RedisHelper redisHelper;


    private static final String LOG_MSG_USER = " updatePrHeader ====用户信息:{},采购申请=:{}";
    private static final String LOG_MSG_SPUC_PR_HEADER_UPDATE_AMOUNT = "============SPUC_PR_HEADER_UPDATE_AMOUNT-TaskNotExistException=============={}";
    private static final String PR_TASK_CODE = "SPUC_PR_HEADER_UPDATE";
    private static final String[] HEADER_UPDATE_OPTIONAL = new String[]{"purchaseAgentId", "title", "contactTelNum", "approvedRemark",
            "invoiceAddressId", "invoiceContactName", "invoiceTelNum", "receiverEmailAddress", "invoiceMethodCode",
            "remark", "invoiceTypeCode", "invoiceTitleTypeCode", "invoiceDetailTypeCode", "ouId", "purchaseOrgId", "paymentMethodCode", "paymentMethodName",
            "invoiceAddress", "unitId", "unitName", "invoiceTypeName", "invoiceMethodName", "invoiceTitleTypeName", "invoiceTitle", "invoiceDetailTypeName",
            "expenseUnitId", "parentUnitId", "expenseUnitName", "invoiceCompanyId", "accepterUserId", "inventoryId", "categoryId", "techGuidanceFlag", "techDirectorUserId",
            "prTypeId", "requestedBy", "prRequestedName", "previousPrStatusCode", "localCurrencyNoTaxSum", "localCurrencyTaxSum", "localCurrency", "originalCurrency"};

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
            Integer count = this.rcwlItfPrDataRespository.validateInvokeItf(prHeader.getPrHeaderId(),tenantId);
            if(RCWLConstants.Common.IS.equals(count)){
            //保存完之后触发接口
            try {
                this.rcwlPrItfService.invokeBudgetOccupy(prHeader, tenantId);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            } }
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
            this.submit(tenantId, prHeader);
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
        Assert.isTrue("APPROVED".equals(prHeader.getPrStatusCode()) || "REJECTED".equals(prHeader.getPrStatusCode()), "error.change.header.status.not.approve");
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
        if(CollectionUtils.isNotEmpty(prLineList)){
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
}
