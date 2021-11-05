package org.srm.purchasecooperation.cux.order.app.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import gxbpm.dto.RCWLGxBpmStartDataDTO;
import gxbpm.service.RCWLGxBpmInterfaceService;
import io.choerodon.core.convertor.ApplicationContextHelper;
import io.choerodon.core.domain.Page;
import io.choerodon.core.exception.CommonException;
import io.choerodon.core.oauth.CustomUserDetails;
import io.choerodon.core.oauth.DetailsHelper;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.hzero.boot.interfaces.sdk.dto.ResponsePayloadDTO;
import org.hzero.boot.platform.lov.adapter.LovAdapter;
import org.hzero.boot.platform.lov.dto.LovValueDTO;
import org.hzero.boot.platform.plugin.hr.EmployeeHelper;
import org.hzero.boot.platform.profile.ProfileClient;
import org.hzero.boot.workflow.WorkflowClient;
import org.hzero.boot.workflow.entity.ActivityInstanceHistory;
import org.hzero.boot.workflow.entity.ProcessInstanceHistory;
import org.hzero.core.base.BaseConstants;
import org.hzero.core.util.AssertUtils;
import org.hzero.mybatis.domian.Condition;
import org.hzero.mybatis.util.Sqls;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.srm.boot.adaptor.client.AdaptorTaskHelper;
import org.srm.boot.adaptor.client.result.TaskResultBox;
import org.srm.boot.event.service.sender.EventSender;
import org.srm.boot.platform.customizesetting.CustomizeSettingHelper;
import org.srm.boot.platform.message.MessageHelper;
import org.srm.boot.platform.message.entity.SpfmMessageSender;
import org.srm.common.TenantInfoHelper;
import org.srm.common.util.StringToNumberUtils;
import org.srm.purchasecooperation.common.app.MdmService;
import org.srm.purchasecooperation.common.utils.LogUtils;
import org.srm.purchasecooperation.cux.order.api.dto.PoToBpmDTO;
import org.srm.purchasecooperation.cux.order.api.dto.PoToBpmLineDTO;
import org.srm.purchasecooperation.cux.order.api.dto.RCWLPoLineDetailDTO;
import org.srm.purchasecooperation.cux.order.app.service.RcwlPoBudgetItfService;
import org.srm.purchasecooperation.cux.order.domain.repository.RcwlSpcmPcSubjectRepository;
import org.srm.purchasecooperation.cux.order.infra.mapper.RcwlMyCostMapper;
import org.srm.purchasecooperation.cux.order.util.TennantValue;
import org.srm.purchasecooperation.cux.pr.infra.mapper.RcwlPoToBpmMapper;
import org.srm.purchasecooperation.cux.pr.infra.mapper.RcwlPrToBpmMapper;
import org.srm.purchasecooperation.cux.pr.utils.DateTimeUtil;
import org.srm.purchasecooperation.cux.pr.utils.constant.PrConstant;
import org.srm.purchasecooperation.order.api.dto.*;
import org.srm.purchasecooperation.order.app.service.*;
import org.srm.purchasecooperation.order.app.service.impl.PoHeaderServiceImpl;
import org.srm.purchasecooperation.order.domain.entity.*;
import org.srm.purchasecooperation.order.domain.repository.*;
import org.srm.purchasecooperation.order.domain.service.*;
import org.srm.purchasecooperation.order.domain.vo.*;
import org.srm.purchasecooperation.order.infra.constant.MessageCode;
import org.srm.purchasecooperation.order.infra.constant.PoConstants;
import org.srm.purchasecooperation.order.infra.feign.HwfpRemoteService;
import org.srm.purchasecooperation.order.infra.feign.SmalRemoteService;
import org.srm.purchasecooperation.order.infra.mapper.PoHeaderMapper;
import org.srm.purchasecooperation.order.infra.mapper.PoLineMapper;
import org.srm.purchasecooperation.order.infra.utils.FieldUtils;
import org.srm.purchasecooperation.order.infra.utils.PoApproveRuleEnum;
import org.srm.purchasecooperation.order.infra.utils.ReceiverUtils;
import org.srm.purchasecooperation.pr.api.dto.PrHeaderChangeDto;
import org.srm.purchasecooperation.pr.api.dto.PrLineChangeDto;
import org.srm.purchasecooperation.pr.api.dto.ProductItemRefDTO;
import org.srm.purchasecooperation.pr.app.service.PrHeaderService;
import org.srm.purchasecooperation.pr.domain.entity.PrHeader;
import org.srm.purchasecooperation.pr.domain.entity.PrLine;
import org.srm.purchasecooperation.pr.domain.repository.PrHeaderRepository;
import org.srm.purchasecooperation.pr.domain.repository.PrLineRepository;
import org.srm.purchasecooperation.pr.infra.feign.ScecRemoteService;
import org.srm.purchasecooperation.pr.infra.mapper.PrLineMapper;
import org.srm.purchasecooperation.sinv.app.service.SinvRcvTrxHeaderService;
import org.srm.purchasecooperation.utils.annotation.EventSendTran;
import org.srm.purchasecooperation.utils.service.EventSendTranService;
import org.srm.web.annotation.Tenant;
import org.srm.purchasecooperation.order.infra.constant.PoConstants.autoTransferOrderFlag;
import org.srm.purchasecooperation.order.infra.constant.PoConstants.ConstantsOfBigDecimal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;


/**
 * description
 *
 * @author Zhouzy 2021/05/22 14:31
 */
@Slf4j
@Service
@Tenant(TennantValue.tenantV)
public class RcwlPoHeaderServiceImpl extends PoHeaderServiceImpl {

    @Autowired
    private PoheaderExtensionService poheaderExtensionService;
    @Autowired
    private PoHeaderRepository poHeaderRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PoLineRepository poLineRepository;
    @Autowired
    private OrderTypeService orderTypeService;
    @Autowired
    private PoHeaderMapper poHeaderMapper;
    @Autowired
    private ChangeHistoryRepository changeHistoryRepository;
    @Autowired
    @Lazy
    private PrHeaderService prHeaderService;
    @Autowired
    @Lazy
    private PoItfDomainService poItfDomainService;
    @Autowired
    private PoValidateDomainService poValidateDomainService;
    @Autowired
    private PoProcessActionService poProcessActionService;
    @Autowired
    private MdmService mdmService;
    @Autowired
    private PrLineMapper prLineMapper;
    @Autowired
    private PoPartnerRepository poPartnerRepository;
    @Autowired
    private PoHeaderDomainService poHeaderDomainService;
    @Autowired
    private PoLineLocationRepository poLineLocationRepository;
    @Autowired
    private PoConfigRuleService poConfigRuleService;
    @Autowired
    private PoHeaderSendApplyMqService poHeaderSendApplyMqService;
    @Autowired
    private RcwlSpcmPcSubjectRepository rcwlSpcmPcSubjectRepository;
    @Autowired
    private PoCreatingRepository poCreatingRepository;
    @Autowired
    private CustomizeSettingHelper customizeSettingHelper;
    @Autowired
    private GeneratorPoByPrDomainService generatorPoByPrDomainService;
    @Autowired
    private PoPriceLibDomainService poPriceLibDomainService;
    @Autowired
    private PoItemBomRepository poItemBomRepository;
    @Autowired
    private PrHeaderRepository prHeaderRepository;
    @Autowired
    private PrLineRepository prLineRepository;
    @Autowired
    private RcwlMyCostMapper rcwlMyCostMapper;
    @Autowired
    private RCWLGxBpmInterfaceService rcwlGxBpmInterfaceService;
    @Autowired
    private ProfileClient profileClient;
    @Autowired
    private RcwlPrToBpmMapper rcwlPrToBpmMapper;
    @Autowired
    private PoLineMapper poLineMapper;
    @Autowired
    private WorkflowClient workflowClient;
    @Autowired
    private HwfpRemoteService hwfpRemoteService;
    @Autowired
    private EventSender eventSender;
    @Autowired
    private PoStatusSyncMallRecordService poStatusSyncMallRecordService;
    @Autowired
    private MessageHelper messageHelper;
    @Autowired
    private SmalRemoteService smalRemoteService;
    @Autowired
    private ScecRemoteService scecRemoteService;
    @Autowired
    private EventSendTranService eventsendTranService;
    @Autowired
    private PoChangeRecordRepository poChangeRecordRepository;
    @Autowired
    private PoProcessActionRepository poProcessActionRepository;
    @Autowired
    private PoApproveRuleService poApproveRuleService;
    @Autowired
    @Lazy
    private SinvRcvTrxHeaderService sinvRcvTrxHeaderService;
    @Autowired
    private RcwlPoToBpmMapper rcwlPoToBpmMapper;
    @Autowired
    private RcwlPoBudgetItfService rcwlPoBudgetItfService;

    private static final Logger LOGGER = LoggerFactory.getLogger(RcwlPoHeaderServiceImpl.class);

    @Override
    @Transactional(
            rollbackFor = {Exception.class}
    )
    public PoDTO createAnOrderBasedOnContract(Long tenantId, List<ContractResultDTO> contractResultDTOList) {
        if (CollectionUtils.isEmpty(contractResultDTOList)) {
            throw new CommonException("spuc.order.subject_can_not_be_null", new Object[0]);
        } else {
            Boolean autoTransferFlag = false;
            String autoPoStatus = null;
            if (autoTransferOrderFlag.YES.equals(((ContractResultDTO)contractResultDTOList.get(0)).getAutoTransferOrderFlag())) {
                autoTransferFlag = true;
                autoPoStatus = this.checkContractData(contractResultDTOList, autoPoStatus);
            }

            Assert.isTrue(CollectionUtils.isNotEmpty(contractResultDTOList), "error.pr.line.list.not.null");
            String language = DetailsHelper.getUserDetails().getLanguage();
            List<PoLineDetailDTO> poLineDetailDTOList = new ArrayList(contractResultDTOList.size());
            List<PoLine> poLineList = new ArrayList();
            Iterator var8 = contractResultDTOList.iterator();

            while(var8.hasNext()) {
                ContractResultDTO contractResultDTO = (ContractResultDTO)var8.next();
                if (contractResultDTO.getReceiptsOrderQuantity().compareTo(contractResultDTO.getResidueOrderQuantity()) == 1 && BaseConstants.Flag.YES.equals(contractResultDTO.getOrderQuantityFlag())) {
                    throw new CommonException("error.The.quantity.of.this.order.shall.not.be.greater.than.the.remaining.quantity", new Object[0]);
                }

                if (Objects.nonNull(contractResultDTO.getSupplierCompanyId())) {
                    SupplierLifeCycleStageVO stageVO = new SupplierLifeCycleStageVO(tenantId, contractResultDTO.getSupplierCompanyId(), contractResultDTO.getCompanyId(), contractResultDTO.getSupplierTenantId());
                    this.poValidateDomainService.validateSupplierLifeCycleAllowOrder(stageVO);
                }
            }

            this.contractDealWithPoList(contractResultDTOList, poLineList, poLineDetailDTOList, (Long)null);
            ContractResultDTO contractResultDTO = (ContractResultDTO)contractResultDTOList.get(0);
            PoDTO poDTO = new PoDTO();
            String domesticCurrencyCode;
            if (Objects.nonNull(tenantId)) {
                domesticCurrencyCode = TenantInfoHelper.selectByTenantId(tenantId).getTenantNum();

                try {
                    TaskResultBox resultBox = AdaptorTaskHelper.executeAdaptorTask("SPUC_CONTRACT_TO_ORDER_TERM", domesticCurrencyCode, contractResultDTO);
                    PoDTO poDTOs = (PoDTO)resultBox.get(0, PoDTO.class);
                    poDTO = (PoDTO)Optional.ofNullable(poDTOs).orElse(poDTO);
                } catch (Exception var21) {
                    LOGGER.error("spuc.contract.to.order.term.adaptor.execute.error", var21.getMessage());
                }
            }

            BeanUtils.copyProperties(contractResultDTO, poDTO, FieldUtils.getExpandFields());
            domesticCurrencyCode = this.poHeaderMapper.queryCurrencyByCompanyId(contractResultDTO.getCompanyId());
            if (domesticCurrencyCode != null) {
                poDTO.setDomesticCurrencyCode(domesticCurrencyCode);
            }

            String finalDomesticCurrencyCode = domesticCurrencyCode;
            poLineList.forEach((poLinex) -> {
                poLinex.modifyDomesticInfoByExchangeRate(this.mdmService, finalDomesticCurrencyCode);
            });
            poDTO.modifyDomesticAmountAndTaxIncludeAmount(poLineList, this.mdmService);
            poDTO.setSupplierTenantId(contractResultDTO.getSupplierTenantId());
            poDTO.setSupplierId(contractResultDTO.getSupplierId());
            poDTO.setObjectVersionNumber((Long)null);
            poDTO.setStatusCode("PENDING");
            poDTO.setSourceCode("SRM");
            poDTO.setExternalSystemCode("SRM");
            poDTO.setPoSourcePlatform("SRM");
            poDTO.setTaxIncludeAmount((BigDecimal)contractResultDTOList.stream().filter((d) -> {
                return null != d.getTaxIncludedLineAmount();
            }).map(ContractResultDTO::getTaxIncludedLineAmount).reduce(BigDecimal.ZERO, BigDecimal::add));
            poDTO.setSourceBillTypeCode("CONTRACT_ORDER");
            poDTO.setPoLineList(poLineList);
            Long defaultPoTypeId = this.orderTypeService.queryDefaultOrderType(tenantId).getOrderTypeId();
            List<PoLine> poLineList1;
            if (BaseConstants.Flag.YES.equals(contractResultDTO.getAutoTransferOrderFlag())) {
                poDTO.setPoTypeId(Objects.isNull(contractResultDTO.getOrderTypeId()) ? defaultPoTypeId : contractResultDTO.getOrderTypeId());
            } else {
                poLineList1 = (List)contractResultDTOList.stream().map(ContractResultDTO::getOrderTypeId).filter(Objects::nonNull).distinct().collect(Collectors.toList());
                if (0 != poLineList1.size() && poLineList1.size() <= 1) {
                    poDTO.setPoTypeId(contractResultDTO.getOrderTypeId());
                } else {
                    poDTO.setPoTypeId(defaultPoTypeId);
                }
            }

            poDTO.setRemark((String)null);
            poDTO.cancelPrInfo();
            poDTO.setVersionNum(1);
            poDTO.setIfAccoringToPrLine(true);
            poDTO.setAgentId(contractResultDTO.getAgentId());
            PoHeader.processPurchaseAgent(tenantId, poDTO, this.poHeaderRepository);
            poDTO = this.transferPcToPo(poDTO, tenantId, contractResultDTOList);
            if ("SUBMITTED".equals(autoPoStatus)) {
                poDTO = this.adaptorPo(poDTO);
                poLineList1 = poDTO.getPoLineList();
                LOGGER.info("21698_createAnOrderBasedOnContract_poLineList1:{}", JSON.toJSONString(poLineList1));
                poLineList1.forEach((poLinex) -> {
                    if (Objects.isNull(poLinex.getInvOrganizationId())) {
                        LOGGER.error("spuc.pc.invorganization.can.not.be.null.error:{}", JSON.toJSONString(contractResultDTOList));
                        throw new CommonException("spuc.pc.invorganization.can.not.be.null.error", new Object[0]);
                    }
                });
            }

            if (autoTransferFlag) {
                poDTO.setPoNum("PO_" + contractResultDTO.getPcNum());
            }

            this.checkMergeRuleByPc(contractResultDTOList, tenantId);
            PoHeader poHeader = new PoHeader();
            poHeader.setTenantId(contractResultDTO.getTenantId());
            poHeader.setSupplierCompanyId(contractResultDTO.getSupplierCompanyId());
            poHeader.setCompanyId(contractResultDTO.getCompanyId());
            if (!this.judgeSupplierFrozen(poHeader)) {
                throw new CommonException("error.order.supplier_have_benn_frozen", new Object[0]);
            } else {
                PoDTO res = this.poCreate(poDTO);
                List<ChangeHistory> changeHistoryList = new ArrayList();
                Iterator var15 = res.getPoLineList().iterator();

                while(var15.hasNext()) {
                    PoLine poLine = (PoLine)var15.next();
                    ChangeHistory changeHistory = new ChangeHistory();
                    changeHistory.setTenantId(tenantId);
                    changeHistory.setPcHeaderId(poLine.getPcHeaderId());
                    changeHistory.setPcSubjectId(poLine.getPcSubjectId());
                    changeHistory.setExecutingState("CONTRACT_CHANGE_ORDER");
                    changeHistory.setExecuteBillType("ORDER");
                    changeHistory.setExecuteBillHeaderId(res.getPoHeaderId());
                    changeHistory.setExecuteBillLineId(poLine.getPoLineId());
                    changeHistory.setExecuteQuantity(poLine.getQuantity());
                    Long userId = DetailsHelper.getUserDetails().getUserId();
                    User user = (User)this.userRepository.selectByPrimaryKey(userId);
                    changeHistory.setExecuteBy(user.getRealName());
                    changeHistory.setExecuteDate(new Date());
                    changeHistoryList.add(changeHistory);
                }

                contractResultDTOList.forEach((cr) -> {
                    ContractResultDTO contractResultDto = this.poLineRepository.selectContractSubject(tenantId, cr.getPcSubjectId());
                    contractResultDto.setPcSubjectId(cr.getPcSubjectId());
                    contractResultDto.setChanageOrderQuantity(contractResultDto.getChanageOrderQuantity().add(cr.getReceiptsOrderQuantity()));
                    this.poLineRepository.updateSubjce(contractResultDto);
                });
                this.changeHistoryRepository.batchInsertSelective(changeHistoryList);
                if (autoTransferFlag && "SUBMITTED".equals(autoPoStatus)) {
                    try {
                        this.submitPlatformDispatch(poDTO);
                    } catch (Exception var20) {
                        LOGGER.error("35189=== order submit faild ： {}", JSON.toJSONString(poDTO));
                        LOGGER.error(var20.getMessage(), var20);
                        throw new CommonException("spuc.pc.auto.order.submit.error", new Object[0]);
                    }
                }

                this.poheaderExtensionService.contractDealWithPoList(changeHistoryList, poLineList, tenantId);
                this.poHeaderSendApplyMqService.sendApplyMq(poDTO.getPoHeaderId(), tenantId, "OCCUPY");
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("协议转订单更新行sodr_po_line.pr_header_id 和 pr_line_id");
                }

                this.poHeaderDomainService.initPoMessage(res);
                return res;
            }
        }
    }

    private void checkMergeRuleByPc(List<ContractResultDTO> contractResultDTOList, Long tenantId) {
        LOGGER.debug("24514-checkMergeRule-start:" + JSON.toJSONString(contractResultDTOList));
        ContractResultDTO contractResultDTO = (ContractResultDTO)contractResultDTOList.get(0);
        Map<String, String> paramsMap = new HashMap();
        paramsMap.put("company", String.valueOf(contractResultDTO.getCompanyId()));
        Set<String> mergeRule = this.poConfigRuleService.getMergeRulePcOrSource(paramsMap, tenantId, "SITE.SPUC.PC_CHANGE_ORDER_MERGE_RULE");
        LovAdapter lovAdapter = (LovAdapter) ApplicationContextHelper.getContext().getBean(LovAdapter.class);
        List<LovValueDTO> lovValueDTOS = lovAdapter.queryLovValue("SPUC.PC_CHANGE_PO_MERGE_RULE", tenantId);
        lovValueDTOS.add(LovValueDTO.build("companyId", "公司", (String)null, (String)null, (String)null, (Integer)null));
        lovValueDTOS.add(LovValueDTO.build("supplierCompanyId", "供应商", (String)null, (String)null, (String)null, (Integer)null));
        contractResultDTOList.forEach((contractResultTemp) -> {
            Iterator var4 = mergeRule.iterator();

            while(var4.hasNext()) {
                String mergeRuleField = (String)var4.next();
                Object value1 = null;

                try {
                    value1 = FieldUtils.getValueByPropNameByPc(mergeRuleField, contractResultDTO, ContractResultDTO.class);
                } catch (Exception var10) {
                    LOGGER.info("24514-getPcResultPropError : {}, trace : {}", var10.getMessage(), var10.getStackTrace());
                }

                Object value2 = null;

                try {
                    value2 = FieldUtils.getValueByPropNameByPc(mergeRuleField, contractResultTemp, ContractResultDTO.class);
                } catch (Exception var9) {
                    LOGGER.info("24514-getPcResultPropError : {}, trace : {}", var9.getMessage(), var9.getStackTrace());
                }

                if (org.apache.commons.lang3.ObjectUtils.notEqual(value1, value2)) {
                    lovValueDTOS.stream().filter((item) -> {
                        return item.getValue().equals(mergeRuleField);
                    }).map(LovValueDTO::getMeaning).findFirst().ifPresent((item) -> {
                        throw new CommonException("spuc.pc.change_po_merge_rule_error", new Object[]{contractResultTemp.getPcNum(), contractResultTemp.getLineNum(), item});
                    });
                    throw new CommonException("spuc.pc.change_po_merge_rule_error", new Object[]{contractResultTemp.getPcNum(), contractResultTemp.getLineNum(), mergeRuleField});
                }
            }

        });
    }

    @Override
    public void contractDealWithPoList(List<ContractResultDTO> contractResultLine, List<PoLine> poLineList, List<PoLineDetailDTO> poLineDetailDTOList, Long poHeaderId) {
        Assert.isTrue(CollectionUtils.isNotEmpty(contractResultLine), "error.pr.line.list.not.null");
        AtomicLong lineNum = new AtomicLong(1L);
        if (poHeaderId != null) {
            List<PoLine> poLines = this.poLineRepository.selectByCondition(Condition.builder(PoLine.class).andWhere(Sqls.custom().andEqualTo("poHeaderId", poHeaderId)).build());
            if (CollectionUtils.isNotEmpty(poLines)) {
                poLines.stream().map(PoLine::getLineNum).max(Comparator.naturalOrder()).ifPresent(lineNum::addAndGet);
            }
        }

        Iterator var15 = contractResultLine.iterator();

        while(var15.hasNext()) {
            ContractResultDTO contractResultDTO = (ContractResultDTO)var15.next();
            PoLineDetailDTO poLineDetailDTO = new PoLineDetailDTO();
            BeanUtils.copyProperties(contractResultDTO, poLineDetailDTO);
            poLineDetailDTO.setSourcePlatformCode("CONTRACT_ORDER");
            poLineDetailDTOList.add(poLineDetailDTO);
            PoLineLocation poLineLocation = new PoLineLocation();
            List<PoLineLocation> poLineLocationList = new ArrayList(1);
            BeanUtils.copyProperties(contractResultDTO, poLineLocation, FieldUtils.getExpandFields());
            poLineLocation.setShipToOrganizationId(contractResultDTO.getInvOrganizationId());
            poLineLocation.setNeedByDate(contractResultDTO.getDeliverDate());
            poLineLocation.setQuantity(contractResultDTO.getReceiptsOrderQuantity());
            List<PrLine> prLineList = this.prLineMapper.selectPrLineByPc(contractResultDTO);
            if (CollectionUtils.isNotEmpty(prLineList) && prLineList.get(0) != null && ("SRM".equals(((PrLine)prLineList.get(0)).getPrSourcePlatform()) || "SHOP".equals(((PrLine)prLineList.get(0)).getPrSourcePlatform()) || "CATALOGUE".equals(((PrLine)prLineList.get(0)).getPrSourcePlatform()) || "ERP".equals(((PrLine)prLineList.get(0)).getPrSourcePlatform())) && ((PrLine)prLineList.get(0)).getReceiveAddress() != null) {
                poLineLocation.setShipToThirdPartyAddress(((PrLine)prLineList.get(0)).getReceiveAddress());
            }

            PrHeaderChangeDto prHeaderChangeDto = this.prHeaderService.selectPrHeaderByPcNumAndLineNum(contractResultDTO.getPcNum(), contractResultDTO.getLineNum(), contractResultDTO.getTenantId());
            if (prHeaderChangeDto != null) {
                List<PrLineChangeDto> prLineDtoList = prHeaderChangeDto.getPrLineList();
                if (CollectionUtils.isNotEmpty(prLineDtoList) && Objects.nonNull(prLineDtoList.get(0))) {
                    poLineLocation.setInvOrganizationId(((PrLineChangeDto)prLineDtoList.get(0)).getInvOrganizationId());
                    poLineLocation.setShipToThirdPartyAddress(((PrLineChangeDto)prLineDtoList.get(0)).getReceiveAddress());
                }
            }

            poLineLocation.setLineLocationNum(1L);
            poLineLocation.setDisplayLineLocationNum(String.valueOf(poLineLocation.getLineLocationNum()));
            PoLine poLine = new PoLine();
            BeanUtils.copyProperties(contractResultDTO, poLine, FieldUtils.getExpandFields());
            if (Objects.nonNull(prHeaderChangeDto) && CollectionUtils.isNotEmpty(prHeaderChangeDto.getPrLineList()) && Objects.nonNull(prHeaderChangeDto.getPrLineList().get(0))) {
                poLine.setPrRequestedBy(((PrLineChangeDto)prHeaderChangeDto.getPrLineList().get(0)).getRequestedBy());
                poLine.setPrRequestedName(((PrLineChangeDto)prHeaderChangeDto.getPrLineList().get(0)).getPrRequestedName());
            }

            //opd-26 更新订单行表的AttributeVarchar21、CostId、CostCode、Wbs、WbsCode
            List<Map<String,String>>  listMap = rcwlSpcmPcSubjectRepository.querySubjectByKey(contractResultDTO.getPcSubjectId());
            if(listMap.size()>0){
                Long costId = rcwlMyCostMapper.selectCostId(String.valueOf(listMap.get(0).get("attribute_varchar22")),poLine.getTenantId());
                String wbs = rcwlMyCostMapper.selectWbs(String.valueOf(listMap.get(0).get("attribute_varchar23")), poLine.getTenantId());
                poLine.setAttributeVarchar21(String.valueOf(listMap.get(0).get("attribute_varchar21")));
                poLine.setCostId(costId);
                poLine.setCostCode(String.valueOf(listMap.get(0).get("attribute_varchar22")));
                poLine.setWbs(wbs);
                poLine.setWbsCode(String.valueOf(listMap.get(0).get("attribute_varchar23")));
            }else{
                // throw new CommonException("error.po.sprm_pr_line_not_null", "");
            }
            poLine.setVersionNum(1L);
            poLineLocationList.add(poLineLocation);
            poLine.setPoLineLocationList(poLineLocationList);
            poLine.setLineNum(lineNum.get());
            poLine.setDisplayLineNum(lineNum.toString());
            lineNum.addAndGet(1L);
            poLine.setPurchaseBatch(BigDecimal.ONE);
            BigDecimal taxNotIncludePrice = contractResultDTO.getEnteredTaxIncludedPrice().divide(BigDecimal.ONE.add(contractResultDTO.getTaxRate().divide(new BigDecimal("100"))), ConstantsOfBigDecimal.SCALE_OF_TEN, RoundingMode.HALF_UP);
            poLine.setUnitPrice(taxNotIncludePrice);
            poLine.setLineAmount(taxNotIncludePrice.multiply(contractResultDTO.getReceiptsOrderQuantity()));
            poLine.setQuantity(contractResultDTO.getReceiptsOrderQuantity());
            poLine.setTaxIncludedLineAmount(contractResultDTO.getEnteredTaxIncludedPrice().multiply(contractResultDTO.getReceiptsOrderQuantity()));
            poLine.setUnitPriceBatch((BigDecimal)Optional.ofNullable(contractResultDTO.getUnitPriceBatch()).orElse(BigDecimal.ONE));
            poLine.setItemCode(contractResultDTO.getItemCode());
            poLine.setChartVersion(contractResultDTO.getChartVersion());
            poLine.setLineAmount(contractResultDTO.getTaxIncludedLineAmount().divide(BigDecimal.valueOf(1L).add(contractResultDTO.getTaxRate().divide(BigDecimal.valueOf(100L))), 2, 4));
            poLine.setExchangeRate(contractResultDTO.getExchangeRate());
            poLineList.add(poLine);
        }

    }

// ---------------------------------------------------- ------------------------------- ------------------------------------------------
    @Override
    @Transactional(
            rollbackFor = {Exception.class}
    )
    public List<PoDTO> poCreateNew(Long tenantId, List<PrLine> prLineList, String poTypeCode, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        Assert.notEmpty(prLineList, "error.data_invalid");
        String sourceConfig = this.queryPoSourceConfig(tenantId, "PURCHASE_REQUEST");
        prLineList.forEach((prLine) -> {
            if (!"CATALOGUE".equals(prLine.getPrSourcePlatform())) {
                prLine.setSupplierCompanyId(prLine.getSelectSupplierCompanyId());
            }
        });
        if (Objects.nonNull(sourceConfig) && "1".equals(sourceConfig)) {
            List<PoDTO> poDTOS = new ArrayList();
            List<List<PrLine>> listPrs = this.prToPoMergeRule(prLineList);
            Iterator var9 = listPrs.iterator();

            while(var9.hasNext()) {
                List<PrLine> listPr = (List)var9.next();
                PoDTO poDTO = ((PoHeaderService)this.self()).prHeaderAccordingToLineOfReference(tenantId, listPr, poTypeCode);
                poDTOS.add(poDTO);
            }

            if (CollectionUtils.isNotEmpty(poDTOS)) {
                String cacheKey = this.poCreatingRepository.saveSessionInCache(poDTOS);
                poDTOS.stream().forEach((poDTOx) -> {
                    poDTOx.setCacheKey(cacheKey);
                });
                return poDTOS;
            } else {
                return Collections.emptyList();
            }
        } else {
            return Collections.singletonList(((PoHeaderService)this.self()).prHeaderAccordingToLineOfReference(tenantId, prLineList, poTypeCode));
        }
    }


    @Override
    public List<List<PrLine>> prToPoMergeRule(List<PrLine> prLineMerge) {
        Assert.notEmpty(prLineMerge, "error.data_invalid");
        prLineMerge.forEach((prLine) -> {
            if (Objects.nonNull(prLine.getAgentId()) && Objects.isNull(prLine.getPurchaseAgentId())) {
                prLine.setPurchaseAgentId(prLine.getAgentId());
            }

        });
        LOGGER.debug("PoHeaderServiceImpl.prToPoMergeRule:begin param is {}", JSONObject.toJSONString(prLineMerge));
        List<List<PrLine>> list = new ArrayList();
        Map<PrLineConfigVo, List<PrLine>> groupMerge = (Map)prLineMerge.stream().collect(Collectors.groupingBy(PrLineConfigVo::parseGroup));
        groupMerge.forEach((prLineConfigVo, prLineList) -> {
            Set<String> mergeRulePr = this.poConfigRuleService.getMergeRulePr(prLineConfigVo);
            List<PrLine> noSupplierPrLines = new ArrayList();
            List<PrLine> supplierPrLines = new ArrayList();
            List<PrLine> errorPrLines = new ArrayList();
            prLineList.forEach((prLine) -> {
                if ("CATALOGUE".equals(prLine.getPrSourcePlatform())) {
                    LOGGER.debug("PoHeaderServiceLmpl.prToPoMergeRule:prNum:{} ,lineNum:{} execute [TWO]", prLine.getPrNum(), prLine.getLineNum());
                    supplierPrLines.add(prLine);
                } else if (prLine.getItemId() == null && Boolean.FALSE.equals(prLine.getReferencePriceDisplayFlag())) {
                    LOGGER.debug("PoHeaderServiceLmpl.prToPoMergeRule:prNum:{} ,lineNum:{} execute [FOUR]", prLine.getPrNum(), prLine.getLineNum());
                    noSupplierPrLines.add(prLine);
                } else if (prLine.getSupplierCompanyId() != null && Boolean.TRUE.equals(prLine.getReferencePriceDisplayFlag()) && Objects.nonNull(prLine.getItemId())) {
                    LOGGER.debug("PoHeaderServiceLmpl.prToPoMergeRule:prNum:{} ,lineNum:{} execute [ONE]", prLine.getPrNum(), prLine.getLineNum());
                    supplierPrLines.add(prLine);
                } else {
                    PageRequest pageRequest = new PageRequest(0, 10);
                    ItemReferencePriceLibraryQueryDTO itemReferencePriceLibraryQueryDTO = new ItemReferencePriceLibraryQueryDTO();
                    BeanUtils.copyProperties(prLine, itemReferencePriceLibraryQueryDTO);
                    Page<ItemReferencePriceLibraryVO> itemReferencePriceLibraryList = this.getItemReferencePriceLibraryList(prLine.getTenantId(), itemReferencePriceLibraryQueryDTO, pageRequest);
                    if ((CollectionUtils.isEmpty(itemReferencePriceLibraryList) || CollectionUtils.isEmpty(itemReferencePriceLibraryList.getContent())) && Objects.nonNull(prLine.getItemId()) && Boolean.FALSE.equals(prLine.getReferencePriceDisplayFlag())) {
                        LOGGER.debug("PoHeaderServiceLmpl.prToPoMergeRule:prNum:{} ,lineNum:{} execute [THREE]", prLine.getPrNum(), prLine.getLineNum());
                        noSupplierPrLines.add(prLine);
                    } else {
                        LOGGER.debug("PoHeaderServiceLmpl.prToPoMergeRule:prNum:{} ,lineNum:{} execute [TWO]", prLine.getPrNum(), prLine.getLineNum());
                        errorPrLines.add(prLine);
                    }

                }
            });
            if (CollectionUtils.isNotEmpty(errorPrLines)) {
                StringBuffer stringBuffer = new StringBuffer("");
                AtomicInteger number = new AtomicInteger(1);
                errorPrLines.forEach((prLine) -> {
                    String displayPrNum = prLine.getPrNum();
                    String displayLineNum = prLine.getLineNum().toString();
                    if (number.get() == 1) {
                        stringBuffer.append(displayPrNum + "/" + displayLineNum);
                        number.getAndIncrement();
                    } else {
                        stringBuffer.append("," + displayPrNum + "/" + displayLineNum);
                    }

                });
                throw new CommonException("error.po_no_suupplier_error", new Object[]{stringBuffer});
            } else {
                org.srm.purchasecooperation.order.infra.utils.CollectionUtils collectionUtils = new org.srm.purchasecooperation.order.infra.utils.CollectionUtils();
                Map<List<Object>,List<PrLine>> mapNoSupplier;
                if (CollectionUtils.isNotEmpty(supplierPrLines)) {
                    mapNoSupplier = collectionUtils.dynamicGroupListByFiled(supplierPrLines, mergeRulePr);
                    mapNoSupplier.forEach((k, v) -> {
                        list.add(v);
                    });
                }

                if (CollectionUtils.isNotEmpty(noSupplierPrLines)) {
                    mapNoSupplier = collectionUtils.dynamicGroupListByFiled(noSupplierPrLines, mergeRulePr);
                    mapNoSupplier.forEach((poLineMergeVO, prLines) -> {
                        list.add(prLines);
                    });
                }

            }
        });
        LOGGER.debug("PoHeaderServiceImpl.prToPoMergeRule:groupMerge return is {}", JSONObject.toJSONString(list));
        return list;
    }


    @Override
    @Transactional(
            rollbackFor = {Exception.class}
    )
    public PoDTO prHeaderAccordingToLineOfReference(Long tenantId, List<PrLine> prLineList, String poTypeCode) {
        Assert.isTrue(CollectionUtils.isNotEmpty(prLineList), "error.pr.line.list.not.null");
        prLineList.forEach((prLine) -> {
            SupplierLifeCycleStageVO stageVO;
            if (!"ERP".equals(prLine.getPrSourcePlatform()) && !"SRM".equals(prLine.getPrSourcePlatform()) && !"SHOP".equals(prLine.getPrSourcePlatform())) {
                if (Objects.nonNull(prLine.getSupplierCompanyId())) {
                    stageVO = new SupplierLifeCycleStageVO(tenantId, prLine.getSupplierCompanyId(), prLine.getCompanyId(), prLine.getSupplierTenantId());
                    this.poValidateDomainService.validateSupplierLifeCycleAllowOrder(stageVO);
                }
            } else if (Objects.nonNull(prLine.getSelectSupplierCompanyId())) {
                stageVO = new SupplierLifeCycleStageVO(tenantId, prLine.getSelectSupplierCompanyId(), prLine.getCompanyId(), prLine.getSelectSupplierTenantId());
                this.poValidateDomainService.validateSupplierLifeCycleAllowOrder(stageVO);
            }

        });
        Long poTypeId = this.generatorPoByPrDomainService.getPoTypeIdByPrLines(tenantId, prLineList);
        PoDTO poDTO = this.generatorPoByPrDomainService.generatePoByPrLines(prLineList, tenantId, poTypeId);
        String enableMinPurchase = this.customizeSettingHelper.queryBySettingCode(tenantId, "011025");
        if ("CATALOGUE".equals(poDTO.getPoSourcePlatform()) && StringUtils.isNotBlank(enableMinPurchase) && enableMinPurchase.equals(BaseConstants.Flag.YES.toString())) {
            MinPurchaseConfigVO minPurchaseConfig = new MinPurchaseConfigVO();
            minPurchaseConfig.setTenantId(tenantId);
            minPurchaseConfig.setSupplierCompanyId(((PrLine)prLineList.get(0)).getSupplierCompanyId());
            minPurchaseConfig.setCurrencyCode(poDTO.getCurrencyCode());
            List<MinPurchaseConfigVO> configList = this.poHeaderRepository.queryByCondition(minPurchaseConfig);
            if (CollectionUtils.isNotEmpty(configList)) {
                LOGGER.info("enableMinPurchase prLineId:" + ((PrLine)prLineList.get(0)).getPrLineId());
                LOGGER.info("enableMinPurchase getTaxIncludedUnitPrice:" + ((PrLine)prLineList.get(0)).getTaxIncludedUnitPrice());
                LOGGER.info("enableMinPurchase thisOrderQuantity:" + ((PrLine)prLineList.get(0)).getThisOrderQuantity());
                prLineList.forEach((x) -> {
                    if (x.getUnitPrice() != null && x.getThisOrderQuantity() != null) {
                        BigDecimal multiply = x.getTaxIncludedUnitPrice().multiply(x.getThisOrderQuantity());
                        x.setDynamicLineAmount(multiply);
                    }

                });
                BigDecimal reduce = (BigDecimal)prLineList.stream().map(PrLine::getDynamicLineAmount).filter((x) -> {
                    return x != null;
                }).reduce(BigDecimal.ZERO, BigDecimal::add);
                Long minPurchaseAmount = ((MinPurchaseConfigVO)configList.get(0)).getMinPurchaseAmount();
                BigDecimal minAmount = new BigDecimal(minPurchaseAmount);
                LOGGER.info("enableMinPurchase minPurchaseAmount:" + minPurchaseAmount);
                LOGGER.info("enableMinPurchase reduce:" + reduce);
                LOGGER.info("enableMinPurchase compareto:" + reduce.compareTo(minAmount));
                if (reduce.compareTo(minAmount) == -1) {
                    throw new CommonException("error.pr.line.line.amount.count.not.allow", new Object[0]);
                }
            }
        }

        PoDTO res = this.poCreate(poDTO);
        this.generatorPoByPrDomainService.holdPr(res.getPoLineList(), res);
        ConfigQueryVO configQueryVO = this.poHeaderMapper.selectConfigParam(tenantId, res.getPoHeaderId());
        configQueryVO.setConfigCode("SITE.SPUC.PO.REF_PRICE_LIB");
        String enablePriceLib = this.poPriceLibDomainService.queryEnablePriceLib(configQueryVO);
        configQueryVO.setConfigCode("SITE.SPUC.PO.PRICE_LIB_STRATEGY");
        String serviceCode = this.poPriceLibDomainService.queryEnablePriceLib(configQueryVO);
        if ((res.isByErpOrSrmPr() || "PURCHASE_ORDER".equals(res.getSourceBillTypeCode())) && StringUtils.isNotEmpty(serviceCode) && BaseConstants.Flag.YES.toString().equals(enablePriceLib) && !"NULL".equals(serviceCode)) {
            LOGGER.debug("24514_pr to po first query price lib:" + JSON.toJSONString(res));
            PoOrderSaveDTO poOrderSaveDTO = new PoOrderSaveDTO();
            PoHeaderDetailDTO poHeaderDetailDTO = new PoHeaderDetailDTO();
            BeanUtils.copyProperties(res, poHeaderDetailDTO);
            poOrderSaveDTO.setPoHeaderDetailDTO(poHeaderDetailDTO);
            poOrderSaveDTO.setPoLineDetailDTOs(new ArrayList(res.getPoLineList().size()));
            Iterator var13 = res.getPoLineList().iterator();

            while(var13.hasNext()) {
                PoLine poLine = (PoLine)var13.next();
                PoLineDetailDTO poLineDetailDTO = new PoLineDetailDTO();
                BeanUtils.copyProperties(poLine, poLineDetailDTO);
                poOrderSaveDTO.getPoLineDetailDTOs().add(poLineDetailDTO);
            }

            if (Objects.nonNull(poHeaderDetailDTO) && Objects.nonNull(poHeaderDetailDTO.getSupplierCompanyId())) {
                List<PoPriceLibReturnVO> poPriceLibReturnVOList = this.poQueryBettlePrice(tenantId, poOrderSaveDTO, serviceCode, (String)null);
                LOGGER.debug("24514_pr to po first query price lib result:{}", JSON.toJSONString(poPriceLibReturnVOList));
            }
        }

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("采购申请转订单更新行sodr_po_line.pr_header_id 和 pr_line_id");
        }

        this.poHeaderDomainService.initPoMessage(res);
        if (null != res.getPoHeaderId()) {
            this.poHeaderSendApplyMqService.sendApplyMq(res.getPoHeaderId(), poDTO.getTenantId(), "OCCUPY");
        }

        return res;
    }


    @Override
    @Transactional(
            rollbackFor = {Exception.class}
    )
    public PoDTO poCreate(PoDTO poDto) {
        LOGGER.debug("PoheaderExtensionServiceImpl_poCreate_poDto is:{}", JSONObject.toJSONString(poDto));
        PoHeader poHeader = new PoHeader();
        BeanUtils.copyProperties(poDto, poHeader);
        LOGGER.debug("PoheaderExtensionServiceImpl_poCreate_1_poHeader is:{}", JSONObject.toJSONString(poHeader));
        AssertUtils.notNull(poDto.getTenantId(), "error.not_null");
        String orderVersionManageRuleCode;
        if (StringUtils.isEmpty(poDto.getPoNum())) {
            orderVersionManageRuleCode = this.poItfDomainService.getPoNum(poHeader);
            poHeader.setPoNum(orderVersionManageRuleCode);
        }

        if (poDto.getIfAccoringToPrLine()) {
            poHeader.setVersionDate(new Date());
            poHeader.setDisplayPoNum(poHeader.getPoNum());
        } else {
            orderVersionManageRuleCode = this.customizeSettingHelper.queryBySettingCode(poHeader.getTenantId(), "010209");
            poHeader.versionInit(orderVersionManageRuleCode);
        }

        poHeader.validUniqueIndex(this.poHeaderRepository);
        poHeader.setPoSourcePlatform((String)Optional.ofNullable(poHeader.getPoSourcePlatform()).orElseGet(() -> {
            return !StringUtils.equals(poHeader.getSourceCode(), "SRM") ? "ERP" : poHeader.getPoSourcePlatform();
        }));
        poHeader.supplierInfoSetting(this.poHeaderMapper);
        Date nowHeaderDate = new Date();
        if (StringUtils.isEmpty(poHeader.getErpCreatedName())) {
            poHeader.setErpCreatedName(DetailsHelper.getUserDetails().getUsername());
        }

        if (poHeader.getErpCreationDate() == null) {
            poHeader.setErpCreationDate(nowHeaderDate);
        }

        if (poHeader.getErpLastUpdateDate() == null) {
            poHeader.setErpLastUpdateDate(nowHeaderDate);
        }

        this.setPoLineDomesticInfo(poDto.getPoLineList(), poHeader);
        poHeader.modifyDomesticAmountAndTaxIncludeAmount(poDto.getPoLineList(), this.mdmService);
        LOGGER.debug("PoheaderExtensionServiceImpl_poCreate_2_poHeader is:{}", JSONObject.toJSONString(poHeader));
        this.poHeaderRepository.insertSelective(poHeader);
        boolean isSRM = StringUtils.equalsAny(poHeader.getPoSourcePlatform(), new CharSequence[]{"SRM", "CATALOGUE", "E-COMMERCE", "SHOP"});
        List<PoLine> poLineList = poDto.getPoLineList();
        if (CollectionUtils.isNotEmpty(poLineList)) {
            Iterator var6 = poLineList.iterator();

            while(var6.hasNext()) {
                PoLine poLine = (PoLine)var6.next();
                poLine.setTenantId(poHeader.getTenantId());
                poLine.setPoHeaderId(poHeader.getPoHeaderId());
                if (poHeader.getVersionNum() != null) {
                    poLine.setVersionNum(poHeader.getVersionNum().longValue());
                }

                if (poLine.getLineNum() == null) {
                    if (StringUtils.isNotEmpty(poLine.getDisplayLineNum())) {
                        poLine.setLineNum(StringToNumberUtils.stringToLong(poLine.getDisplayLineNum()));
                    } else {
                        Long lineNum = (Long)Optional.ofNullable(this.poLineRepository.queryMaxPoLineNum(poLine)).orElse(0L) + 1L;
                        poLine.setLineNum(lineNum);
                        poLine.setDisplayLineNum(String.valueOf(lineNum));
                    }
                }

                if (StringUtils.isEmpty(poLine.getCurrencyCode())) {
                    poLine.setCurrencyCode(poHeader.getCurrencyCode());
                }

                poLine.validUniqueIndex(this.poLineRepository);
                if (poLine.getEnteredTaxIncludedPrice() != null && poLine.getUnitPriceBatch() != null) {
                    poLine.setEnteredTaxIncludedPrice(poLine.getEnteredTaxIncludedPrice());
                }

                if (isSRM) {
                    poLine.modifyPricePrecisionByCurrencyCode(this.mdmService, poHeader.getDomesticCurrencyCode());
                }

                // opd-26
                if(null != poLine.getPrLineId()){
                    poLine.setAttributeVarchar21(String.valueOf(rcwlSpcmPcSubjectRepository.queryPrLineByKey(poLine.getPrLineId()).get(0).get("budget_account_num")));
                }
                   LOGGER.info("srm-22875-poLineRepository.insertSelective-dete{}", poLine);
                this.poLineRepository.insertSelective(poLine);
                if (CollectionUtils.isNotEmpty(poLine.getPoItemBomList())) {
                    poLine.getPoItemBomList().forEach((poItemBom) -> {
                        poItemBom.setTenantId(poHeader.getTenantId());
                        poItemBom.setPoHeaderId(poHeader.getPoHeaderId());
                        poItemBom.setPoLineId(poLine.getPoLineId());
                    });
                }

                this.poItemBomRepository.batchInsertSelective(poLine.getPoItemBomList());
                if (CollectionUtils.isNotEmpty(poLine.getPoLineLocationList())) {
                    poLine.getPoLineLocationList().forEach((poLineLocation) -> {
                        poLineLocation.setVersionNum(poHeader.getVersionNum());
                        poLineLocation.setPoHeaderId(poHeader.getPoHeaderId());
                        poLineLocation.setPoLineId(poLine.getPoLineId());
                        poLineLocation.setTenantId(poHeader.getTenantId());
                        if (poLineLocation.getLineLocationNum() == null) {
                            if (StringUtils.isNotEmpty(poLineLocation.getDisplayLineLocationNum())) {
                                poLineLocation.setLineLocationNum(StringToNumberUtils.stringToLong(poLineLocation.getDisplayLineLocationNum()));
                            } else {
                                Long lineLocationNum = (Long)Optional.ofNullable(this.poLineLocationRepository.queryMaxPoLineLocationNum(poLineLocation)).orElse(0L) + 1L;
                                poLineLocation.setLineLocationNum(lineLocationNum);
                                poLineLocation.setDisplayLineLocationNum(String.valueOf(lineLocationNum));
                            }
                        }

                    });
                    PoLineLocation.processAsnFlag(poLine.getPoLineLocationList(), poHeader.getConfirmedFlag(), poHeader.getPublishCancelFlag());
                    LOGGER.info("srm-22875-batchInsertSelective-dete{}", poLine.getPoLineLocationList());
                    this.poLineLocationRepository.batchInsertSelective(poLine.getPoLineLocationList());
                }
            }
        }

        PoLineLocation query = new PoLineLocation();
        query.setPoHeaderId(poHeader.getPoHeaderId());
        poHeader.stsHandleAfterPoUpdate(this.poLineLocationRepository.select(query));
        poHeader.handlePrice(this.poHeaderRepository);
        if (isSRM) {
            poHeader.modifyPricePrecisionByCurrencyCode(this.mdmService);
        }

        this.poHeaderRepository.updateOptional(poHeader, new String[]{"amount", "taxIncludeAmount", "statusCode", "cancelledFlag", "closedFlag", "oldStatusCode"});
        BeanUtils.copyProperties(poHeader, poDto);
        poDto.setPoPartnerList(this.poPartnerRepository.batchInsertOrUpdate(poDto.getPoPartnerList(), poDto.getPoHeaderId(), poDto.getTenantId()));
        this.poProcessActionService.insert(poDto.getPoHeaderId(), "NEW");
        this.processPr(poDto);
        poDto.setPoLineList(poLineList);
        return poDto;
    }

    private void processPr(PoDTO poDto) {
        PoHeader poHeader = new PoHeader();
        List<PoLine> poLineList = poDto.getPoLineList();
        BeanUtils.copyProperties(poDto, poHeader);
        PrHeader prHeader = (PrHeader)this.prHeaderRepository.selectByPrimaryKey(poDto.getPrHeaderId());
        if (prHeader != null) {
            poHeader.prSyncToPo(prHeader);
            this.poHeaderRepository.updateOptional(poHeader, PoHeader.PR_SYNC_FIELDS);
            BeanUtils.copyProperties(poHeader, poDto);
        }

        List<PrLine> prLineList = this.prLineRepository.selectByCondition(Condition.builder(PrLine.class).andWhere(Sqls.custom().andIn("prLineId", (Iterable)poLineList.stream().map((item) -> {
            return item.getPrLineId();
        }).collect(Collectors.toList()))).build());
        if (!CollectionUtils.isEmpty(prLineList) && !CollectionUtils.isEmpty(poLineList)) {
            Map<Long, PrHeader> prHeaderMap = (Map)this.prHeaderRepository.selectByCondition(Condition.builder(PrHeader.class).andWhere(Sqls.custom().andIn("prHeaderId", (Iterable)prLineList.stream().map((item) -> {
                return item.getPrHeaderId();
            }).distinct().collect(Collectors.toList()))).build()).stream().collect(Collectors.toMap((item) -> {
                return item.getPrHeaderId();
            }, (item) -> {
                return item;
            }));
            Map<Long, PrLine> prLineMap = (Map)prLineList.stream().collect(Collectors.toMap((item) -> {
                return item.getPrLineId();
            }, (item) -> {
                return item;
            }));
            Iterator var8 = poLineList.iterator();

            while(var8.hasNext()) {
                PoLine poLine = (PoLine)var8.next();
                if (prHeaderMap.get(poLine.getPrHeaderId()) != null) {
                    prHeader = (PrHeader)prHeaderMap.get(poLine.getPrHeaderId());
                }

                if (prLineMap.get(poLine.getPrLineId()) != null) {
                    poLine.prSyncToPo(prHeader, (PrLine)prLineMap.get(poLine.getPrLineId()));
                }
            }
            this.poLineRepository.batchUpdateOptional(poLineList, PoLine.PR_SYNC_FIELDS);
            poDto.setPoLineList(poLineList);
        }
    }


    private void sendMessage(Set<String> loginNameSet, Long tenantId, Long poHeaderId) {
        if (!loginNameSet.isEmpty()) {
            List<User> users = this.userRepository.selectByCondition(Condition.builder(User.class).andWhere(Sqls.custom().andIn("loginName", loginNameSet)).build());
            HashMap<String, String> templateMap = new HashMap();
            PoHeader poHeader = (PoHeader)this.poHeaderRepository.selectByPrimaryKey(poHeaderId);
            templateMap.put("DISPLAYPONUM", String.valueOf(poHeader.getDisplayPoNum()));
            Iterator var7 = users.iterator();

            while(var7.hasNext()) {
                User user = (User)var7.next();
                SpfmMessageSender spfmMessageSender = new SpfmMessageSender(tenantId, MessageCode.TERMINATION.getMessageCode(), ReceiverUtils.buildAppointUser(user.getId(), templateMap));
                this.messageHelper.sendMessage(spfmMessageSender);
            }
        }
    }

    void itemMapping(Long tenantId, List<PoHeader> poHeaderList) {
        if (!CollectionUtils.isEmpty(poHeaderList)) {
            poHeaderList.stream().filter((x) -> {
                return "CATALOGUE".equals(x.getPoSourcePlatform()) || "E-COMMERCE".equals(x.getPoSourcePlatform());
            }).forEach((poHeader) -> {
                if (!Objects.nonNull(poHeader.getOriginalPoHeaderId())) {
                    Long poHeaderId = poHeader.getPoHeaderId();
                    PoLine poLine = new PoLine();
                    poLine.setPoHeaderId(poHeaderId);
                    List<PoLine> poLines = this.poLineRepository.select(poLine);
                    List<org.srm.purchasecooperation.pr.api.dto.ProductItemRefDTO> productItemRefDTOS = new ArrayList();
                    poLines.stream().forEach((poL) -> {
                        org.srm.purchasecooperation.pr.api.dto.ProductItemRefDTO productItemRefDTO = new ProductItemRefDTO();
                        productItemRefDTO.setProductItemId(poL.getProductId());
                        productItemRefDTO.setItemId(poL.getItemId());
                        productItemRefDTO.setCompanyId(poHeader.getCompanyId());
                        productItemRefDTO.setInvOrganizationId(poL.getInvOrganizationId());
                        productItemRefDTO.setPrSourcePlatformCode(poHeader.getPoSourcePlatform());
                        productItemRefDTO.setRemark(poL.getRemark());
                        productItemRefDTO.setCategoryId(poL.getCategoryId());
                        if (null != productItemRefDTO.getProductItemId()) {
                            productItemRefDTOS.add(productItemRefDTO);
                        }

                    });
                    productItemRefDTOS.forEach((productItemRefDTO) -> {
                        productItemRefDTO.setFlag(1);
                    });
                    if (poHeader.getPrHeaderId() != null) {
                        PrHeader prHeader = (PrHeader)this.prHeaderRepository.selectByPrimaryKey(poHeader.getPrHeaderId());
                        if (prHeader != null && BaseConstants.Flag.YES.equals(prHeader.getNewMallFlag())) {
                            this.smalRemoteService.productItemRefs(productItemRefDTOS, poHeader.getTenantId(), "PO");
                        } else {
                            this.scecRemoteService.productItemRefs(productItemRefDTOS, poHeader.getTenantId(), "PO");
                        }
                    } else {
                        List<PoLine> poLineList = this.poLineRepository.select("poHeaderId", poHeader.getPoHeaderId());
                        if (poLineList != null && !poLineList.isEmpty()) {
                            Long prHeaderId = (Long)poLineList.stream().filter((x) -> {
                                return x.getPrHeaderId() != null;
                            }).map(PoLine::getPrHeaderId).distinct().findFirst().get();
                            if (prHeaderId != null) {
                                PrHeader prHeaderx = (PrHeader)this.prHeaderRepository.selectByPrimaryKey(prHeaderId);
                                if (prHeaderx != null && BaseConstants.Flag.YES.equals(prHeaderx.getNewMallFlag())) {
                                    this.smalRemoteService.productItemRefs(productItemRefDTOS, poHeader.getTenantId(), "PO");
                                } else {
                                    this.scecRemoteService.productItemRefs(productItemRefDTOS, poHeader.getTenantId(), "PO");
                                }
                            }
                        }
                    }

                }
            });
        }
    }

    private void sendMessageByType(CustomUserDetails userDetail, Long poHeaderId, String operateType) {
        LOGGER.debug("24497====[sendMessageByType] poHeaderId is:{}", poHeaderId);
        LOGGER.debug("24497====[sendMessageByType] userDetails is:{}", userDetail.toString());
        if (userDetail.getTenantId() == null || userDetail.getTenantId() == 0L) {
            PoHeader poHeader = (PoHeader)this.poHeaderRepository.selectByPrimaryKey(poHeaderId);
            userDetail.setTenantId(poHeader.getTenantId());
        }

        List<PoMessageDetailDTO> poMessageDetailDTOS = new ArrayList();
        LOGGER.debug("===>sendMessageByType operateType : {}", operateType);
        PoMessageDetailDTO poMessageDetailDTO;
        if ("CONFIRMED".equals(operateType)) {
            poMessageDetailDTO = new PoMessageDetailDTO(poHeaderId, userDetail.getUserId(), userDetail.getTenantId(), userDetail.getOrganizationId(), "CONFIRMED", "ORDER");
            poMessageDetailDTOS.add(poMessageDetailDTO);

            try {
                this.eventsendTranService.fireEvent("PO", "SODR_PO_MESSAGE", PoConstants.DEFAULT_TENANT_ID, "CONFIRMED", poMessageDetailDTOS);
            } catch (Exception var9) {
                LOGGER.error("send poMessageDetailDTOS has failed: ", var9);
            }
        } else if ("PUBLISH".equals(operateType)) {
            poMessageDetailDTO = new PoMessageDetailDTO(poHeaderId, userDetail.getUserId(), userDetail.getTenantId(), userDetail.getOrganizationId(), "PUBLISH", "ORDER");
            poMessageDetailDTOS.add(poMessageDetailDTO);

            try {
                this.eventsendTranService.fireEvent("PO", "SODR_PO_MESSAGE", PoConstants.DEFAULT_TENANT_ID, "PUBLISHED", poMessageDetailDTOS);
            } catch (Exception var8) {
                LOGGER.error("send poMessageDetailDTOS has failed: ", var8);
            }
        } else if ("DELIVERY_DATE_SUBMIT".equals(operateType)) {
            poMessageDetailDTO = new PoMessageDetailDTO(poHeaderId, userDetail.getUserId(), userDetail.getTenantId(), userDetail.getOrganizationId(), "DELIVERY_DATE_SUBMIT", "ORDER");
            poMessageDetailDTOS.add(poMessageDetailDTO);

            try {
                this.eventSender.fireEvent("PO", "SODR_PO_MESSAGE", PoConstants.DEFAULT_TENANT_ID, "DELIVERY_DATE_SUBMIT", poMessageDetailDTOS);
            } catch (Exception var7) {
                LOGGER.error("send poMessageDetailDTOS has failed: ", var7);
            }
        }
    }

    private PoHeader publishOrderForECommerce(PoHeader poHeader) {
        poHeader.setReleasedDate(new Date());
        poHeader.setReleasedFlag(BaseConstants.Flag.YES);
        poHeader.setStatusCode("PUBLISHED");
        if ("E-COMMERCE".equals(poHeader.getPoSourcePlatform())) {
            this.sinvRcvTrxHeaderService.checkEcPoExistAsnStrategy(poHeader.getTenantId(), poHeader.getPoHeaderId());
        }

        this.poProcessActionService.insert(poHeader.getPoHeaderId(), "PUBLISH");
        this.poHeaderRepository.updateOptional(poHeader, new String[]{"statusCode", "releasedDate", "releasedFlag"});
        if ("CATALOGUE".equals(poHeader.getPoSourcePlatform())) {
            this.poStatusSyncMallRecordService.catalogueOrderStatusCallBack(new PoStatusSyncMallRecord("PO", poHeader.getPoHeaderId(), "PUBLISHED", poHeader.getTenantId()));
        }

        CustomUserDetails userDetail = DetailsHelper.getUserDetails();
        this.sendMessageByType(userDetail, poHeader.getPoHeaderId(), "PUBLISH");
        return poHeader;
    }

    @SneakyThrows
    @Transactional(rollbackFor = {Exception.class})
    @EventSendTran(rollbackFor = {Exception.class})
    public PoDTO submittedProcessForECommerceAndCatalogueNoSaga(PoDTO poDTO) {
        PoHeader poHeader = new PoHeader();
        BeanUtils.copyProperties(poDTO, poHeader);
        poHeader = (PoHeader)this.poHeaderRepository.selectByPrimaryKey(poHeader);
        LOGGER.debug("===>submittedProcessForECommerceAndCatalogueNoSaga poHeader : {}", poHeader);
        String approveCode = this.poHeaderRepository.getPoConfigCodeValue(poDTO.getTenantId(), poDTO.getPoHeaderId(), "SITE.SPUC.PO.APPROVING_METHOD");
        boolean enableSrmReview = Objects.equals("FUNCTIONAL", approveCode);
        if (StringUtils.isEmpty(poHeader.getStatusCode()) || "PENDING".equals(poHeader.getStatusCode())) {
            if (enableSrmReview && "FUNCTIONAL".equals(approveCode) && "E-COMMERCE".equals(poHeader.getPoSourcePlatform())) {
                poHeader.setStatusCode("SUBMITTED");
                this.poHeaderRepository.updateOptional(poHeader, new String[]{"statusCode"});

                try {
                    if ("E-COMMERCE".equals(poHeader.getPoSourcePlatform())) {
                        PoDocVO poDocVO = this.poHeaderRepository.selectPoDocVO(poDTO.getPoHeaderId(), "SUBMITTED");
                        this.eventSender.fireEvent("PO", "SORD_PO", PoConstants.DEFAULT_TENANT_ID, "SUBMITTED", Collections.singletonList(poDocVO));
                    }
                } catch (Exception var8) {
                    LOGGER.error("send poDocVO has failed: ", var8);
                }

                return poDTO;
            }

            if ("WORKFLOW".equals(approveCode)) {
                poHeader.setStatusCode("SUBMITTED_WFL");
                ((PoHeaderService)this.self()).orderWorkflowApproveStartUp(poDTO);
                this.poHeaderRepository.updateOptional(poHeader, new String[]{"statusCode"});
                return poDTO;
            }

            if ("EXTERNAL_SYSTEM".equals(approveCode)) {
//                ResponseDTO poToOaResponse = ((PoHeaderService)this.self()).poApproveByExternalSystem(Arrays.asList(poDTO));
//                if ("FAIL".equals(poToOaResponse.getResponseStatus())) {
//                    throw new CommonException("error.order.external.oa_approve_error", new Object[]{poToOaResponse.getResponseMessage()});
//                }

                //调用占预算接口，占用标识（01占用，02释放）,当前释放逻辑：占用金额固定为0，清空占用金额
                rcwlPoBudgetItfService.invokeBudgetOccupy(poDTO, poDTO.getTenantId(), "01");
                //预算占用成功，推送数据到bpm
                String dataToBpmUrl = this.poDataToBpm(poDTO);
                poDTO.setAttributeVarchar37(dataToBpmUrl);
//                poHeader.setStatusCode("SUBMITTED");
                poHeader.setApproveMethod("EXTERNAL_SYSTEM");
                this.poHeaderRepository.updateOptional(poHeader, new String[]{"statusCode", "approveMethod"});
            }

            poHeader.setStatusCode("CATALOGUE".equals(poHeader.getPoSourcePlatform()) ? "SUBMITTED" : "APPROVED");
            if (poHeader.getStatusCode().equals("APPROVED")) {
                List<PoHeader> poHeaderList = new ArrayList();
                poHeaderList.add(poHeader);
                this.itemMapping(poHeader.getTenantId(), poHeaderList);
            }

            this.poHeaderRepository.updateOptional(poHeader, new String[]{"statusCode"});
        }

        List<PoLineLocation> lineLocationList = this.poLineLocationRepository.select("poHeaderId", poHeader.getPoHeaderId());
        if (!"SRM".equals(poHeader.getSourceCode())) {
            boolean prToPoCheckPass = this.checkChangePoWithPr(poHeader, lineLocationList);
            if (!prToPoCheckPass) {
                BeanUtils.copyProperties(poHeader, poDTO);
                return "CATALOGUE".equals(poHeader.getPoSourcePlatform()) ? this.submittedProcess(poDTO) : poDTO;
            }
        }

        this.clearPoIncorrect(poHeader);
        poDTO.setObjectVersionNumber(poHeader.getObjectVersionNumber());
        if (!"PUBLISHED".equals(poHeader.getStatusCode())) {
            if ("CATALOGUE".equals(poHeader.getPoSourcePlatform())) {
                return this.submittedProcess(poDTO);
            }

            this.publishOrderForECommerce(poHeader);
            String promiseDeliveryDateNotNullFlag = String.valueOf(this.poApproveRuleService.selectPoApproveRule(DetailsHelper.getUserDetails().getTenantId(), PoApproveRuleEnum.COMMITTED_DELIVERY_DATE.name()).isPromiseDeliveryDateNotNull());
            if (String.valueOf(BaseConstants.Flag.YES).equals(promiseDeliveryDateNotNullFlag)) {
                lineLocationList.forEach((lineLocation) -> {
                    lineLocation.setPromiseDeliveryDate(lineLocation.getNeedByDate());
                });
            }

            try {
                if ("E-COMMERCE".equals(poHeader.getPoSourcePlatform())) {
                    PoDocVO poDocVO = this.poHeaderRepository.selectPoDocVO(poDTO.getPoHeaderId(), "PUBLISHED");
                    this.eventSender.fireEvent("PO", "SORD_PO", PoConstants.DEFAULT_TENANT_ID, "PUBLISHED", Collections.singletonList(poDocVO));
                }
            } catch (Exception var9) {
                LOGGER.error("send poDocVO has failed: ", var9);
            }

            poHeader = this.confirmOrderForECommerce(poHeader, lineLocationList);
        }

        BeanUtils.copyProperties(poHeader, poDTO);
        this.poHeaderSendApplyMqService.sendApplyMq(poDTO.getPoHeaderId(), poDTO.getTenantId(), "UPDATE");
        return poDTO;
    }

    @SneakyThrows
    @Transactional(rollbackFor = {Exception.class})
    @EventSendTran(rollbackFor = {Exception.class})
    public PoDTO submittedProcess(PoDTO poDTO) {
        processOriginalQuantity(Collections.singletonList(poDTO.getPoHeaderId()));
        String code = this.poHeaderRepository.getPoConfigCodeValue(poDTO.getTenantId(), poDTO.getPoHeaderId(), "SITE.SPUC.PO.APPROVING_METHOD");
        boolean enableSrmReview = Objects.equals("FUNCTIONAL", code);
        boolean enableWfleview = Objects.equals("WORKFLOW", code);
        boolean enableEsReview = Objects.equals("EXTERNAL_SYSTEM", code);
        if (enableSrmReview || enableWfleview) {
            this.poProcessActionService.insert(poDTO.getPoHeaderId(), "SUBMIT");
            poDTO.setStatusCode("SUBMITTED");
            poDTO.setSubmittedDate(new Date());
            poDTO.setSubmittedBy(DetailsHelper.getUserDetails().getUserId());
            if ("WORKFLOW".equals(code)) {
                poDTO.setStatusCode("SUBMITTED_WFL");
                String businessKey = generatePoApprovePoBusinessKey(poDTO.getTenantId(), poDTO.getPoHeaderId());
                ResponseEntity<List<ProcessInstanceHistory>> listResponseEntity = this.workflowClient.listTaskHistory(poDTO.getTenantId().longValue(), businessKey, null);
                List<ProcessInstanceHistory> body = (List<ProcessInstanceHistory>)listResponseEntity.getBody();
                if (body != null && body.size() != 0) {
                    ProcessInstanceHistory processInstanceHistory = body.get(body.size() - 1);
                    List<ActivityInstanceHistory> activityInstanceList = processInstanceHistory.getActivityInstanceList();
                    try {
                        this.hwfpRemoteService.endProc(poDTO.getTenantId(), processInstanceHistory.getProcessInstanceId());
                    } catch (Exception e) {
                        LOGGER.info("End processInstance failed, or processInstance has been terminated. tenantId = {}, procId = {}", poDTO
                                .getTenantId(), processInstanceHistory.getProcessInstanceId());
                    }
                    Set<String> names = new HashSet<>();
                    for (ActivityInstanceHistory activityInstanceHistory : activityInstanceList) {
                        LOGGER.info("审批人是:" + activityInstanceHistory.getAssigneeName() + "(" + activityInstanceHistory.getAssignee() + ")——" + activityInstanceHistory.getActivityName());
                        if (activityInstanceHistory.getAssignee() != null) {
                            names.add(activityInstanceHistory.getAssignee());
                        }
                    }
                    LOGGER.info("要发消息的人是：" + names.toString());
                    sendMessage(names, poDTO.getTenantId(), poDTO.getPoHeaderId());
                }
                Long userId = DetailsHelper.getUserDetails().getUserId();
                String employeeNum = EmployeeHelper.getEmployeeNum(userId.longValue(), poDTO.getTenantId().longValue());
                poDTO.setEmployeeNumber(employeeNum);
                ((PoHeaderService)self()).orderWorkflowApproveStartUp(poDTO);
            }
            PoHeader poHeader = new PoHeader();
            BeanUtils.copyProperties(poDTO, poHeader);
            this.poHeaderRepository.updateOptional(poHeader, new String[] { "statusCode", "submittedDate", "submittedBy" });
            BeanUtils.copyProperties(poHeader, poDTO);
            try {
                if (!"WORKFLOW".equals(code)) {
                    PoDocVO poDocVO = this.poHeaderRepository.selectPoDocVO(poDTO.getPoHeaderId(), "SUBMITTED");
                    LogUtils.debug(LOGGER, "Submit PoDOC: {}", new Object[] { poDocVO });
                    this.eventSender.fireEvent("PO", "SORD_PO", PoConstants.DEFAULT_TENANT_ID, "SUBMITTED", Collections.singletonList(poDocVO));
                }
            } catch (Exception e) {
                LOGGER.error("send poDocVO has failed: ", e);
            }
        } else if (enableEsReview) {
//            ResponseDTO poToOaResponse = ((PoHeaderService)self()).poApproveByExternalSystem(Arrays.asList(new PoDTO[] { poDTO }));
//            if ("FAIL".equals(poToOaResponse.getResponseStatus())){
//                throw new CommonException("error.order.external.oa_approve_error", new Object[] { poToOaResponse.getResponseMessage() });
//            }

            //调用占预算接口，占用标识（01占用，02释放）,当前释放逻辑：占用金额固定为0，清空占用金额
            rcwlPoBudgetItfService.invokeBudgetOccupy(poDTO, poDTO.getTenantId(), "01");
            //预算占用成功，推送数据到bpm
            String dataToBpmUrl = this.poDataToBpm(poDTO);
            poDTO.setAttributeVarchar37(dataToBpmUrl);
            this.poProcessActionService.insert(poDTO.getPoHeaderId(), "SUBMIT");
//            poDTO.setStatusCode("SUBMITTED");
            poDTO.setSubmittedDate(new Date());
            poDTO.setSubmittedBy(DetailsHelper.getUserDetails().getUserId());
            poDTO.setApproveMethod("EXTERNAL_SYSTEM");
            PoHeader poHeader = new PoHeader();
            BeanUtils.copyProperties(poDTO, poHeader);
            this.poHeaderRepository.updateOptional(poHeader, new String[] { "statusCode", "submittedDate", "submittedBy", "approveMethod" });
            BeanUtils.copyProperties(poHeader, poDTO);
        } else {
            poDTO.setStatusCode("APPROVED");
            PoHeader poHeaderInDB = (PoHeader)this.poHeaderRepository.selectByPrimaryKey(poDTO.getPoHeaderId());
            if (Objects.nonNull(poHeaderInDB) && "CATALOGUE".equals(poHeaderInDB.getPoSourcePlatform())) {
                itemMapping(poHeaderInDB.getTenantId(), Arrays.asList(new PoHeader[] { poHeaderInDB }));
            }
            PoHeader poHeader1 = (PoHeader)this.poHeaderRepository.selectByPrimaryKey(poDTO.getPoHeaderId());
            PoHeaderDetailDTO poHeaderDetailDTO = new PoHeaderDetailDTO();
            BeanUtils.copyProperties(poHeader1, poHeaderDetailDTO);
            String syncTime = querySyncConfirm(poHeaderDetailDTO, poDTO.getTenantId());
            if ("ORDER_APPROVE".equals(syncTime)) {
                posyncERP(poHeaderDetailDTO, poDTO.getTenantId());
                PoHeader poHeaderNew = (PoHeader)this.poHeaderRepository.selectByPrimaryKey(poDTO.getPoHeaderId());
                poDTO.setObjectVersionNumber(poHeaderNew.getObjectVersionNumber());
            }
            try {
                PoDocVO poDocVO = this.poHeaderRepository.selectPoDocVO(poDTO.getPoHeaderId(), "APPROVED");
                this.eventSender.fireEvent("PO", "SORD_PO", PoConstants.DEFAULT_TENANT_ID, "APPROVED", Collections.singletonList(poDocVO));
            } catch (Exception e) {
                LOGGER.error("send poDocVO has failed: ", e);
            }
            boolean manualPublish = Objects.equals(String.valueOf(BaseConstants.Flag.YES), this.poHeaderRepository.getPoConfigCodeValue(poDTO.getTenantId(), poDTO.getPoHeaderId(), "SITE.SPUC.PO.MANUAL_PUBLISH"));
            String approvalStatusReturn = this.customizeSettingHelper.queryBySettingCode(poDTO.getTenantId(), "010212");
            LOGGER.info("25855 manualPublish:" + manualPublish);
            LOGGER.info("25855 approvalStatusReturn:" + approvalStatusReturn);
            if ("SRM".equals(poDTO.getSourceCode()) || !String.valueOf(BaseConstants.Flag.YES).equals(approvalStatusReturn) || BaseConstants.Flag.YES.equals(poDTO.getUpdateFlag())) {
                LOGGER.info("25855 manualPublish:" + poDTO.toString());
                if (poDTO.canAutoPublish(manualPublish)) {
                    poDTO.autoPublish(manualPublish, this.poProcessActionService, this.poHeaderRepository);
                    LOGGER.debug("prod-bug-5973-PoHeaderServiceImpl-submittedProcess-step2-syncTime-" + syncTime);
                    LOGGER.debug("prod-bug-5973-PoHeaderServiceImpl-submittedProcess-manualPublish-" + poDTO.canAutoPublish(manualPublish));
                    if ("ORDER_PUBLISH".equals(syncTime)) {
                        PoHeader poHeader = (PoHeader)this.poHeaderRepository.selectByPrimaryKey(poHeaderDetailDTO.getPoHeaderId());
                        poHeaderDetailDTO.setObjectVersionNumber(poHeader.getObjectVersionNumber());
                        posyncERP(poHeaderDetailDTO, poDTO.getTenantId());
                        PoHeader poHeaderNew = (PoHeader)this.poHeaderRepository.selectByPrimaryKey(poHeaderDetailDTO.getPoHeaderId());
                        poDTO.setObjectVersionNumber(poHeaderNew.getObjectVersionNumber());
                    }
                }
                LOGGER.info("25855 manualPublish:" + poDTO.toString());
                if ("PUBLISHED".equals(poDTO.getStatusCode())) {
                    CustomUserDetails userDetails = DetailsHelper.getUserDetails();
                    sendMessageByType(userDetails, poDTO.getPoHeaderId(), "PUBLISH");
                }
                String autoConfirmFlag = this.poHeaderRepository.getPoConfigCodeValue(poDTO.getTenantId(), poDTO.getPoHeaderId(), "SITE.SPUC.PO.AUTO_CONFIRM_STRATEGY");
                List<PoHeader> poHeaderList = new ArrayList<>();
                PoHeader po = (PoHeader)this.poHeaderRepository.selectByPrimaryKey(poDTO.getPoHeaderId());
                if (po.getStatusCode().equals("PUBLISHED")) {
                    poHeaderList.add(po);
                    if ("CATALOGUE".equals(po.getPoSourcePlatform())) {
                        this.poStatusSyncMallRecordService.catalogueOrderStatusCallBack(new PoStatusSyncMallRecord("PO", po.getPoHeaderId(), "PUBLISHED", po.getTenantId()));
                        cataloguePoSendMQ(po.getTenantId(), Collections.singletonList(po.getPoHeaderId()), "PUBLISHED");
                    }
                    if ("SHOP".equals(po.getPoSourcePlatform())) {
                        shopPoSendMQ(po.getTenantId(), Collections.singletonList(po.getPoHeaderId()), "PUBLISHED");
                    }
                    if ("0".equals(autoConfirmFlag)) {
                        autoConfirmOrder(poHeaderList, poDTO.getTenantId());
                        poDTO.setObjectVersionNumber(((PoHeader)this.poHeaderRepository.selectByPrimaryKey(poDTO.getPoHeaderId())).getObjectVersionNumber());
                    }
                }
                try {
                    PoDocVO poDocVO = this.poHeaderRepository.selectPoDocVO(poDTO.getPoHeaderId(), "PUBLISHED");
                    this.eventSender.fireEvent("PO", "SORD_PO", PoConstants.DEFAULT_TENANT_ID, "PUBLISHED", Collections.singletonList(poDocVO));
                } catch (Exception e) {
                    LOGGER.error("send poDocVO has failed: ", e);
                }
            }
            if (poDTO.canAutoPublish(manualPublish)) {
                PoHeader poHeader = PoDTO.mergeSnapShot(poDTO.getPoHeaderId(), this.poHeaderRepository, this.poLineRepository, this.poLineLocationRepository, this.poChangeRecordRepository, this.poProcessActionRepository, this.poItemBomRepository, poDTO.getStatusCode());
                BeanUtils.copyProperties(poHeader, poDTO);
            } else {
                PoHeader poHeader = new PoHeader();
                BeanUtils.copyProperties(poDTO, poHeader);
                PoHeader poHeaderOld = (PoHeader)this.poHeaderRepository.selectByPrimaryKey(poHeader.getPoHeaderId());
                poHeader.setObjectVersionNumber(poHeaderOld.getObjectVersionNumber());
                this.poHeaderRepository.updateOptional(poHeader, new String[] { "statusCode" });
                poDTO.setObjectVersionNumber(poHeader.getObjectVersionNumber());
            }
        }
        this.poHeaderSendApplyMqService.sendApplyMq(poDTO.getPoHeaderId(), poDTO.getTenantId(), "UPDATE");
        return poDTO;
    }

    /**
     * @param poDTO
     * @return
     */
    public String poDataToBpm(PoDTO poDTO) {
        log.info("采购订单数据：{}",poDTO);
        CustomUserDetails userDetails = DetailsHelper.getUserDetails();
        String reSrcSys = this.profileClient.getProfileValueByOptions(userDetails.getTenantId(), userDetails.getUserId(), userDetails.getRoleId(), PrConstant.BpmCodes.REQ_SRC_SYS_CODE);
        String reqTarSys = this.profileClient.getProfileValueByOptions(userDetails.getTenantId(), userDetails.getUserId(), userDetails.getRoleId(), PrConstant.BpmCodes.REQ_TAR_CODE);
        String reqIp = this.profileClient.getProfileValueByOptions(userDetails.getTenantId(), userDetails.getUserId(), userDetails.getRoleId(), PrConstant.BpmCodes.REQ_URL_CODE);
        String zYunUrl = this.profileClient.getProfileValueByOptions(userDetails.getTenantId(), userDetails.getUserId(), userDetails.getRoleId(), "RCWL_PO_TO_BPM_URL");

        //采购订单行数据
//        List<PoLine> poLineList = this.poLineRepository.select(new PoLine(poDTO.getPoHeaderId()));
        List<RCWLPoLineDetailDTO> poLineList = rcwlPoToBpmMapper.selectPoTobpmline(poDTO.getTenantId(), poDTO.getPoHeaderId());
        List<PoToBpmLineDTO> poToBpmLineDTOS = new ArrayList<>();
        poLineList.forEach(line -> {
            PoToBpmLineDTO poToBpmLineDTO = this.setPoLineDataMap(line);
            poToBpmLineDTOS.add(poToBpmLineDTO);
        });

        //采购订单头数据
        PoToBpmDTO poToBpmDTO = this.setPoHeaderDataMap(poDTO, poLineList);
        poToBpmDTO.setzYunUrl(zYunUrl + poDTO.getPoHeaderId());
        poToBpmDTO.setPoToBpmLineDTOList(poToBpmLineDTOS);
        String data = JSONObject.toJSON(poToBpmDTO).toString();
        log.info("=========================json data===========================>" + data);

        RCWLGxBpmStartDataDTO rcwlGxBpmStartDataDTO = new RCWLGxBpmStartDataDTO();

        //设置传输值
        rcwlGxBpmStartDataDTO.setReSrcSys(reSrcSys);
        rcwlGxBpmStartDataDTO.setReqTarSys(reqTarSys);
        rcwlGxBpmStartDataDTO.setUserId(userDetails.getUsername());
        rcwlGxBpmStartDataDTO.setBtid("RCWLSRMCGDD");
        rcwlGxBpmStartDataDTO.setBoid(poDTO.getPoNum());
        //流程实例id
        String procinstId = poDTO.getAttributeVarchar36();
        rcwlGxBpmStartDataDTO.setProcinstId(StringUtils.isNotBlank(procinstId) ? procinstId : "0");
        rcwlGxBpmStartDataDTO.setData(data);
        String bpmUrl = "http://" + reqIp + "/Workflow/MTStart2.aspx?BSID=WLCGGXPT&BTID=RCWLSRMCGDD&BOID=" + poDTO.getPoNum();
        log.info("=========================bpmUrl ===========================>" + bpmUrl);

        // 调用bpm接口
        try {
            ResponsePayloadDTO responsePayloadDTO = rcwlGxBpmInterfaceService.RcwlGxBpmInterfaceRequestData(rcwlGxBpmStartDataDTO);
            log.info("=========================return ===========================>" + responsePayloadDTO.toString());
        } catch (Exception e) {
            throw new CommonException("bpm.interface.error");
        }
        return bpmUrl;
    }

    private PoToBpmLineDTO setPoLineDataMap(RCWLPoLineDetailDTO line) {
        PoToBpmLineDTO poToBpmLineDTO = new PoToBpmLineDTO();
        String costName = this.rcwlPrToBpmMapper.selectCost(line.getTenantId(), line.getCostId());
        String wbsName = this.rcwlPrToBpmMapper.selectWbs(line.getTenantId(), line.getWbsCode());
        LovAdapter lovAdapter = (LovAdapter) ApplicationContextHelper.getContext().getBean(LovAdapter.class);
        String cancelledFlagMeaning = lovAdapter.queryLovMeaning("SPUC.CANCEL_FLAG", line.getTenantId(), line.getCancelledFlag().toString());

        poToBpmLineDTO.setCancelledFlag(cancelledFlagMeaning);
        poToBpmLineDTO.setDisplayLineNum(line.getDisplayLineNum());
        poToBpmLineDTO.setItemId(line.getItemCode());
        poToBpmLineDTO.setItemName(line.getItemName());
        poToBpmLineDTO.setSpecifications(line.getSpecifications());
        poToBpmLineDTO.setModel(line.getModel());
        poToBpmLineDTO.setQuantity(String.valueOf(line.getQuantity().setScale(2, BigDecimal.ROUND_HALF_UP)));
        poToBpmLineDTO.setUomId(this.rcwlPrToBpmMapper.selectUomName(line.getTenantId(), line.getUomId()));
        poToBpmLineDTO.setCategoryName(this.rcwlPrToBpmMapper.selectCategoryName(line.getTenantId(), line.getCategoryId()));
        //订单开始日期
        poToBpmLineDTO.setAttributeDate1(new SimpleDateFormat(DateTimeUtil.PATTERN_DAY).format(line.getAttributeDate1()));
        //订单发运行结束日期
        poToBpmLineDTO.setNeedByDate(new SimpleDateFormat(DateTimeUtil.PATTERN_DAY).format(line.getNeedByDate()));
        poToBpmLineDTO.setUnitPrice(String.valueOf(line.getUnitPrice().setScale(2, BigDecimal.ROUND_HALF_UP)));
        poToBpmLineDTO.setTax(line.getTaxDescription());
        poToBpmLineDTO.setEnteredTaxIncludedPrice(String.valueOf(line.getEnteredTaxIncludedPrice().setScale(2, BigDecimal.ROUND_HALF_UP)));
        poToBpmLineDTO.setTaxInclueLineAmount(String.valueOf(line.getTaxIncludedLineAmount().setScale(2, BigDecimal.ROUND_HALF_UP)));
        poToBpmLineDTO.setInvOrganizationId(line.getInvOrganizationName());
        poToBpmLineDTO.setAttributeVarchar21(line.getBudgetAccountName());
        poToBpmLineDTO.setCostId(costName);
        poToBpmLineDTO.setWbs(wbsName);
        poToBpmLineDTO.setRemark(line.getRemark());
        return poToBpmLineDTO;
    }

    private PoToBpmDTO setPoHeaderDataMap(PoDTO poDTO, List<RCWLPoLineDetailDTO> poLineList) {
        PoToBpmDTO poToBpmDTO = new PoToBpmDTO();
        poToBpmDTO.setDisplayPoNum(poDTO.getDisplayPoNum());
        poToBpmDTO.setErpContractNum(poLineList.get(0).getPcNum());
        poToBpmDTO.setErpContractName(poLineList.get(0).getPcName());
        poToBpmDTO.setAmount(String.valueOf(poDTO.getAmount().setScale(2, BigDecimal.ROUND_HALF_UP)));
        poToBpmDTO.setTaxIncludeAmount(String.valueOf(poDTO.getTaxIncludeAmount().setScale(2, BigDecimal.ROUND_HALF_UP)));
        poToBpmDTO.setCurrencyCode(poDTO.getCurrencyCode());
        poToBpmDTO.setCompanyId(poDTO.getCompanyName());
        poToBpmDTO.setTempKey(poDTO.getSupplierName());
        poToBpmDTO.setEsPurchaseOrgId(this.rcwlPoToBpmMapper.selectEsPurchaseOrgName(poDTO.getTenantId(),poDTO.getPurchaseOrgId()));
        poToBpmDTO.setPoTypeId(this.rcwlPoToBpmMapper.selectOrderTypeName(poDTO.getTenantId(),poDTO.getPoTypeId()));
        poToBpmDTO.setAttributeVarchar1(poDTO.getAttributeVarchar1());
        poToBpmDTO.setEsAgentName(StringUtils.isNotBlank(poDTO.getAgentName())?poDTO.getAgentName():this.rcwlPoToBpmMapper.selectAgentName(poDTO.getTenantId(),poDTO.getAgentId()));
        poToBpmDTO.setPoDate(new SimpleDateFormat(DateTimeUtil.PATTERN_SECOND).format(poDTO.getCreationDate()));
        poToBpmDTO.setRemark(poDTO.getRemark());
        poToBpmDTO.setfSubject("采购订单" + poDTO.getDisplayPoNum());
        return poToBpmDTO;
    }

}
