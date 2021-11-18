package org.srm.purchasecooperation.cux.order.app.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.choerodon.core.convertor.ApplicationContextHelper;
import io.choerodon.core.domain.Page;
import io.choerodon.core.exception.CommonException;
import io.choerodon.core.oauth.DetailsHelper;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.hzero.boot.platform.lov.adapter.LovAdapter;
import org.hzero.boot.platform.lov.dto.LovValueDTO;
import org.hzero.core.base.BaseConstants;
import org.hzero.core.util.AssertUtils;
import org.hzero.mybatis.domian.Condition;
import org.hzero.mybatis.util.Sqls;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.srm.boot.adaptor.client.AdaptorTaskHelper;
import org.srm.boot.adaptor.client.exception.TaskNotExistException;
import org.srm.boot.adaptor.client.result.TaskResultBox;
import org.srm.boot.platform.customizesetting.CustomizeSettingHelper;
import org.srm.common.TenantInfoHelper;
import org.srm.common.util.StringToNumberUtils;
import org.srm.purchasecooperation.common.app.MdmService;
import org.srm.purchasecooperation.cux.order.domain.repository.RcwlSpcmPcSubjectRepository;
import org.srm.purchasecooperation.cux.order.infra.mapper.RcwlMyCostMapper;
import org.srm.purchasecooperation.cux.order.util.TennantValue;
import org.srm.purchasecooperation.order.api.dto.*;
import org.srm.purchasecooperation.order.app.service.*;
import org.srm.purchasecooperation.order.app.service.impl.PoHeaderServiceImpl;
import org.srm.purchasecooperation.order.domain.entity.*;
import org.srm.purchasecooperation.order.domain.repository.*;
import org.srm.purchasecooperation.order.domain.service.*;
import org.srm.purchasecooperation.order.domain.vo.*;
import org.srm.purchasecooperation.order.infra.constant.PoConstants;
import org.srm.purchasecooperation.order.infra.mapper.PoHeaderMapper;
import org.srm.purchasecooperation.order.infra.mapper.PoLineMapper;
import org.srm.purchasecooperation.order.infra.utils.FieldUtils;
import org.srm.purchasecooperation.pr.api.dto.PrHeaderChangeDto;
import org.srm.purchasecooperation.pr.api.dto.PrLineChangeDto;
import org.srm.purchasecooperation.pr.app.service.PrHeaderService;
import org.srm.purchasecooperation.pr.domain.entity.AccountAssignType;
import org.srm.purchasecooperation.pr.domain.entity.PrHeader;
import org.srm.purchasecooperation.pr.domain.entity.PrLine;
import org.srm.purchasecooperation.pr.domain.repository.AccountAssignTypeLineRepository;
import org.srm.purchasecooperation.pr.domain.repository.PrHeaderRepository;
import org.srm.purchasecooperation.pr.domain.repository.PrLineRepository;
import org.srm.purchasecooperation.pr.infra.mapper.PrLineMapper;
import org.srm.purchasecooperation.transaction.infra.constant.Constants;
import org.srm.purchasecooperation.utils.annotation.EventSendTran;
import org.srm.web.annotation.Tenant;
import org.srm.purchasecooperation.order.infra.constant.PoConstants.autoTransferOrderFlag;
import org.srm.purchasecooperation.order.infra.constant.PoConstants.ConstantsOfBigDecimal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.ZoneId;
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
    private PoLineMapper poLineMapper;
    @Autowired
    private RcwlBudgetDistributionRepository rcwlBudgetDistributionRepository;
    @Autowired
    private RcwlBudgetDistributionService rcwlBudgetDistributionService;
    @Autowired
    private GeneratorPoByPcDomainService generatorPoByPcDomainService;
    @Autowired
    private AccountAssignTypeLineRepository accountAssignTypeLineRepository;


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



    @Override
    @Transactional(rollbackFor = {Exception.class})
    public PoDTO updatePoWhenPr(PoOrderSaveDTO poOrderSaveDTO) {
        List<PoLine> poLineList = new ArrayList();
        if (poOrderSaveDTO.getPoLineDetailDTOs() != null) {
            Iterator var3 = poOrderSaveDTO.getPoLineDetailDTOs().iterator();

            while(var3.hasNext()) {
                PoLineDetailDTO poLineDetailDTO = (PoLineDetailDTO)var3.next();
                //零星申请、其他采购申请、总价合同订单无需占预算
                if (!"PURCHASE_REQUEST".equals(poLineDetailDTO.getSourceBillTypeCode()) && !"PURCHASE_REQUEST_LX".equals(poLineDetailDTO.getSourceBillTypeCode())
                && !"CONTRACT_ORDER".equals(poLineDetailDTO.getSourceBillTypeCode())){
                    //行金额、跨年预算校验
                    checkBudgetDistribution(poLineDetailDTO);
                }
                PoLine line = new PoLine();
                BeanUtils.copyProperties(poLineDetailDTO, line);
                poLineList.add(line);
            }
        }

        this.poValidateDomainService.validateSubmitStatus(poOrderSaveDTO.getPoHeaderDetailDTO().getPoHeaderId(), poLineList);
        PoHeaderDetailDTO poHeaderDetailDTO = poOrderSaveDTO.getPoHeaderDetailDTO();
        ConfigQueryVO configQueryVO = this.poHeaderMapper.selectConfigParam(poHeaderDetailDTO.getTenantId(), poHeaderDetailDTO.getPoHeaderId());
        configQueryVO.setConfigCode("SITE.SPUC.PO.REF_PRICE_LIB");
        String enablePriceLib = this.poPriceLibDomainService.queryEnablePriceLib(configQueryVO);
        configQueryVO.setConfigCode("SITE.SPUC.PO.PRICE_LIB_STRATEGY");
        String serviceCode = this.poPriceLibDomainService.queryEnablePriceLib(configQueryVO);
        String priceLibService = this.queryEnablePriceLibConfig(poHeaderDetailDTO.getCompanyId(), poHeaderDetailDTO.getOuId(), poHeaderDetailDTO.getPurchaseOrgId(), poHeaderDetailDTO.getTenantId());
        poOrderSaveDTO.setPoLineMysql(this.poLineMapper.selectByPoHeaderIdList(Arrays.asList(poHeaderDetailDTO.getPoHeaderId())));
        String tenantNum = TenantInfoHelper.selectByTenantId(poOrderSaveDTO.getPoHeaderDetailDTO().getTenantId()).getTenantNum();

        try {
            TaskResultBox taskResultBox = AdaptorTaskHelper.executeAdaptorTask("SPUC_ORDER_PRICE_SOURCE_TYPE", tenantNum, poOrderSaveDTO);
            LOGGER.info("updatePoWhenPr taskResultBox:{}", JSONObject.toJSONString(taskResultBox));
            poOrderSaveDTO = (PoOrderSaveDTO)taskResultBox.get(0, PoOrderSaveDTO.class);
            LOGGER.info("updatePoWhenPr taskResultBox:{}", JSONObject.toJSONString(poOrderSaveDTO));
        } catch (TaskNotExistException var15) {
            LOGGER.info("============ORDER_PRICE_SOURCE_TYPE--updatePoWhenPr-TaskNotExistException=============={}", new Object[]{tenantNum, var15.getMessage(), var15.getStackTrace()});
        }

        if (BaseConstants.Flag.YES.toString().equals(enablePriceLib)) {
            if (StringUtils.isNotEmpty(priceLibService) && !"NULL".equals(serviceCode)) {
                if (poOrderSaveDTO.getPoHeaderDetailDTO().getUnSaveEnable() != null && 0 == poOrderSaveDTO.getPoHeaderDetailDTO().getUnSaveEnable()) {
                    LOGGER.debug("22875====PoHeaderServiceImpl.updatePoHeaderAndPoLineSNewPrice in{}", JSONObject.toJSONString(poOrderSaveDTO));
                    return ((PoHeaderService)this.self()).updatePoHeaderAndPoLineSNewPrice(poOrderSaveDTO);
                } else {
                    List<PoLine> poLines = this.poLineRepository.selectByCondition(Condition.builder(PoLine.class).andWhere(Sqls.custom().andEqualTo("poHeaderId", poOrderSaveDTO.getPoHeaderDetailDTO().getPoHeaderId())).build());
                    List<PoLineDetailDTO> poLineDetailDTOS = new ArrayList();
                    poOrderSaveDTO.setPoLineDetailDTOs(poLineDetailDTOS);

                    PoLineDetailDTO poLineDetailDTO;
                    for(Iterator var11 = poLines.iterator(); var11.hasNext(); poLineDetailDTOS.add(poLineDetailDTO)) {
                        PoLine poLine = (PoLine)var11.next();
                        poLineDetailDTO = new PoLineDetailDTO();
                        BeanUtils.copyProperties(poLine, poLineDetailDTO);
                        poLineDetailDTO.setLineVersionNumber(poLine.getObjectVersionNumber());
                        List<PoLineLocation> poLineLocations = this.poLineLocationRepository.select("poLineId", poLine.getPoLineId());
                        if (poLineLocations.size() > 0) {
                            poLineDetailDTO.setLocationVersionNumber(((PoLineLocation)poLineLocations.get(0)).getObjectVersionNumber());
                            poLineDetailDTO.setPoLineLocationId(((PoLineLocation)poLineLocations.get(0)).getPoLineLocationId());
                        }
                    }

                    LOGGER.debug("22875====PoHeaderServiceImpl.savePoWhenPrFirstNewPrice in{}", JSONObject.toJSONString(poOrderSaveDTO));
                    return this.savePoWhenPrFirstNewPrice(poOrderSaveDTO);
                }
            } else {
                LOGGER.debug("22875====PoHeaderServiceImpl.savePo in{}", JSONObject.toJSONString(poOrderSaveDTO));
                return this.savePo(poOrderSaveDTO);
            }
        } else if (poOrderSaveDTO.getPoHeaderDetailDTO().getUnSaveEnable() != null && 0 != poOrderSaveDTO.getPoHeaderDetailDTO().getUnSaveEnable()) {
            LOGGER.debug("22875====PoHeaderServiceImpl.savePoWhenPrFirst in{}", JSONObject.toJSONString(poOrderSaveDTO));
            return this.generatorPoByPrDomainService.savePoWhenPrFirst(poOrderSaveDTO);
        } else {
            LOGGER.debug("22875====PoHeaderServiceImpl.updatePoHeaderAndPoLineS in{}", JSONObject.toJSONString(poOrderSaveDTO));
            return ((PoHeaderService)this.self()).updatePoHeaderAndPoLineS(poOrderSaveDTO);
        }
    }

    private void checkBudgetDistribution(PoLineDetailDTO poLineDetailDTO) {
        // 根据订单头行ID、获取预算分配数据
        List<RcwlBudgetDistribution> budgetDistributionsInDB = rcwlBudgetDistributionRepository.selectByCondition((Condition.builder(RcwlBudgetDistribution.class).
                andWhere(Sqls.custom().andEqualTo(RcwlBudgetDistribution.FIELD_PO_HEADER_ID, poLineDetailDTO.getPoHeaderId())
                        .andEqualTo(RcwlBudgetDistribution.FIELD_PO_LINE_ID, poLineDetailDTO.getPoLineId())
                        .andEqualTo(RcwlBudgetDistribution.FIELD_TENANT_ID, poLineDetailDTO.getTenantId())
                ).build()));

        if (CollectionUtils.isEmpty(budgetDistributionsInDB)){
            throw new CommonException("未维护预算分配数据，请维护后保存提交！");
        }
        //校验各年原预算值（手工）是否等于行金额
        BigDecimal totalBudgetDisAmount = budgetDistributionsInDB.stream().map(bd -> Optional.ofNullable(bd.getBudgetDisAmount()).orElse(BigDecimal.ZERO)).reduce(BigDecimal.ZERO, BigDecimal::add);
        log.info("订单行总金额：{},各年原预算值（手工）：{}",poLineDetailDTO.getLineAmount(),totalBudgetDisAmount);
        if (totalBudgetDisAmount.compareTo(Optional.ofNullable(poLineDetailDTO.getLineAmount()).orElse(BigDecimal.ZERO))!= 0){
            throw new CommonException("订单行号为【"+poLineDetailDTO.getLineNum()+"】的行金额与预算占用合计必须相等，请重新维护预算拆分！");
        }
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public PoDTO operateOrder(PoOrderSaveDTO poOrderSavaDTO) {
        if ("CONTRACT_ORDER_WJ".equals(poOrderSavaDTO.getPoHeaderDetailDTO().getSourceBillTypeCode())){
            //无价合同计算行金额
            if (CollectionUtils.isNotEmpty(poOrderSavaDTO.getPoLineDetailDTOs())){
                for (PoLineDetailDTO poLineDetail :poOrderSavaDTO.getPoLineDetailDTOs()){
//                    poLineDetail.setLineAmount(poLineDetail.getEnteredTaxIncludedPrice().multiply(poLineDetail.getQuantity()));
                    BigDecimal taxNotIncludePrice = poLineDetail.getEnteredTaxIncludedPrice().divide(BigDecimal.ONE.add(poLineDetail.getTaxRate().divide(new BigDecimal("100"))), 10, RoundingMode.HALF_UP);
                    poLineDetail.setLineAmount(taxNotIncludePrice.multiply(poLineDetail.getQuantity()));
                    log.info("无价合同计算行金额:{},{},{}",taxNotIncludePrice,poLineDetail.getQuantity(),poLineDetail.getLineAmount());
                }
            }
        }
        ConfigQueryVO configQueryVO = this.poHeaderMapper.selectConfigParam(poOrderSavaDTO.getPoHeaderDetailDTO().getTenantId(), poOrderSavaDTO.getPoHeaderDetailDTO().getPoHeaderId());
        configQueryVO.setConfigCode("SITE.SPUC.PO.REF_PRICE_LIB");
        String enablePriceLib = this.poPriceLibDomainService.queryEnablePriceLib(configQueryVO);
        configQueryVO.setConfigCode("SITE.SPUC.PO.PRICE_LIB_STRATEGY");
        String serviceCode = this.poPriceLibDomainService.queryEnablePriceLib(configQueryVO);
        PoHeaderDetailDTO poHeaderDetailDTO = poOrderSavaDTO.getPoHeaderDetailDTO();
        poOrderSavaDTO.setPoLineMysql(this.poLineMapper.selectByPoHeaderIdList(Arrays.asList(poHeaderDetailDTO.getPoHeaderId())));
        String tenantNum = TenantInfoHelper.selectByTenantId(poOrderSavaDTO.getPoHeaderDetailDTO().getTenantId()).getTenantNum();

        try {
            TaskResultBox taskResultBox = AdaptorTaskHelper.executeAdaptorTask("SPUC_ORDER_PRICE_SOURCE_TYPE", tenantNum, poOrderSavaDTO);
            LOGGER.info("operateOrder taskResultBox:{}", JSONObject.toJSONString(taskResultBox));
            poOrderSavaDTO = (PoOrderSaveDTO)taskResultBox.get(0, PoOrderSaveDTO.class);
            LOGGER.info("operateOrder taskResultBox:{}", JSONObject.toJSONString(poOrderSavaDTO));
        } catch (TaskNotExistException var8) {
            LOGGER.info("============ORDER_PRICE_SOURCE_TYPE--operateOrder-TaskNotExistException=============={}", new Object[]{tenantNum, var8.getMessage(), var8.getStackTrace()});
        }

        //来源系统是SRM、ERP或者、SHOP并且单据来源是其他采购申请
        PoDTO poDTO = (poHeaderDetailDTO.isByErpOrSrmPr() || "PURCHASE_ORDER".equals(poHeaderDetailDTO.getSourceBillTypeCode())) && BaseConstants.Flag.YES.toString().equals(enablePriceLib) && StringUtils.isNotEmpty(serviceCode) && !"NULL".equals(serviceCode) && poOrderSavaDTO.getPoLineDetailDTOs() != null && poOrderSavaDTO.getPoLineDetailDTOs().size() > 0 ? ((PoHeaderService)this.self()).updatePoHeaderAndPoLineSNewPrice(poOrderSavaDTO) : ((PoHeaderService)this.self()).updatePoHeaderAndPoLineS(poOrderSavaDTO);

        //更新完成后，自动计算跨年预算数据
        if (CollectionUtils.isNotEmpty(poOrderSavaDTO.getPoLineDetailDTOs())){

            //零星申请、其他采购申请、总价合同订单无需生成跨年预算数据
            List<PoLineDetailDTO> poLineDetailDTOList = poOrderSavaDTO.getPoLineDetailDTOs().stream().filter(line -> Objects.nonNull(line.getPoLineId())
            && !"PURCHASE_REQUEST".equals(line.getSourceBillTypeCode()) && !"PURCHASE_REQUEST_LX".equals(line.getSourceBillTypeCode())
                    && !"CONTRACT_ORDER".equals(line.getSourceBillTypeCode())).collect(Collectors.toList());

            if (CollectionUtils.isNotEmpty(poLineDetailDTOList)) {
                for (PoLineDetailDTO poLineDetail : poLineDetailDTOList) {
                    PoLine poLineQuery = new PoLine();
                    poLineQuery.setPoHeaderId(poLineDetail.getPoHeaderId());
                    poLineQuery.setPoLineId(poLineDetail.getPoLineId());
                    poLineQuery.setTenantId(poLineDetail.getTenantId());
                    PoLine poLine = poLineRepository.selectOne(poLineQuery);
                    log.info("数据库行金额:{}",poLine.getLineAmount());

                    RcwlBudgetDistributionDTO rcwlBudgetDistributionDTO = new RcwlBudgetDistributionDTO();
                    BeanUtils.copyProperties(poLineDetail, rcwlBudgetDistributionDTO);
                    rcwlBudgetDistributionDTO.setAttributeDate1(poLineDetail.getAttributeDate1().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                    rcwlBudgetDistributionDTO.setNeedByDate(poLineDetail.getNeedByDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                    rcwlBudgetDistributionDTO.setLineAmount(poLine.getLineAmount());
                    rcwlBudgetDistributionDTO.setPoHeaderId(poOrderSavaDTO.getPoHeaderDetailDTO().getPoHeaderId());
                    rcwlBudgetDistributionService.selectBudgetDistributionByPoLine(poOrderSavaDTO.getPoHeaderDetailDTO().getTenantId(), rcwlBudgetDistributionDTO);
                }
            }
        }
        return poDTO;
    }

    @Transactional(rollbackFor = {Exception.class})
    public void insertPoLineAndLocationLine(List<PoLine> poLineList, PoHeader poHeader) {
        if (!CollectionUtils.isEmpty(poLineList)) {
            AccountAssignType.validPoRequiredFields(poLineList, this.accountAssignTypeLineRepository);
            PoLine poLineOfHeader = new PoLine();
            poLineOfHeader.setPoHeaderId(poHeader.getPoHeaderId());
            poLineOfHeader.setTenantId(poHeader.getTenantId());
            Long lineNum = this.poLineRepository.queryMaxPoLineNum(poLineOfHeader);
            Iterator var5 = poLineList.iterator();

            while(var5.hasNext()) {
                PoLine poLine = (PoLine)var5.next();
                lineNum = lineNum + PoConstants.IdInCreaseSize.ONE;
                poLine.setTenantId(poHeader.getTenantId());
                poLine.setVersionNum(poHeader.getVersionNum().longValue());
                if (!poHeader.getSourceBillTypeCode().equals("PURCHASE_ORDER")) {
                    poLine.setCurrencyCode(poHeader.getCurrencyCode());
                }

                poLine.setPoHeaderId(poHeader.getPoHeaderId());
                poLine.setLineNum(lineNum);
                poLine.setDisplayLineNum(lineNum.toString());
                if (CollectionUtils.isNotEmpty(poLine.getPoLineLocationList())) {
                    PoLineLocation poLineLocation = (PoLineLocation)poLine.getPoLineLocationList().get(0);
                    poLineLocation.setTenantId(poHeader.getTenantId());
                    poLineLocation.setVersionNum(poHeader.getVersionNum());
                    poLineLocation.setPoHeaderId(poHeader.getPoHeaderId());
                    poLineLocation.setLineLocationNum(PoConstants.IdInCreaseSize.ONE);
                    poLineLocation.setDisplayLineLocationNum(PoConstants.IdInCreaseSize.ONE.toString());
                }
            }

            poLineList.forEach((poLinex) -> {
                poLinex.modifyPricePrecisionByCurrencyCode(this.mdmService, poHeader.getDomesticCurrencyCode());
            });
            this.setPoLineDomesticInfo(poLineList, poHeader);
            this.poLineRepository.batchInsertSelective(poLineList);

            List<PoLineLocation> poLineLocationList = new ArrayList();
            List<PoLineDetailDTO> poLineDetailDTOList = new ArrayList();
            poLineList.forEach((poLinex) -> {
                if (CollectionUtils.isNotEmpty(poLinex.getPoLineLocationList())) {
                    PoLineLocation poLineLocation = (PoLineLocation)poLinex.getPoLineLocationList().get(0);
                    poLineLocation.setPoLineId(poLinex.getPoLineId());
                    poLineLocationList.add(poLineLocation);
                    PoLineDetailDTO poLineDetailDTO = new PoLineDetailDTO();
                    poLineDetailDTO.setPoLineId(poLinex.getPoLineId());
                    poLineDetailDTO.setLineNum(Integer.valueOf(poLinex.getLineNum().toString()));
                    poLineDetailDTO.setHoldPcHeaderId(poLinex.getHoldPcHeaderId());
                    poLineDetailDTO.setHoldPcLineId(poLinex.getHoldPcLineId());
                    poLineDetailDTO.setQuantity(poLinex.getQuantity());
                    poLineDetailDTOList.add(poLineDetailDTO);
                }

            });
            this.poLineLocationRepository.batchInsertSelective(poLineLocationList);

            //零星申请、其他采购申请、总价合同订单不生成跨年预算数据
            log.info("订单单据来源:{}",poHeader.getSourceBillTypeCode());
            if (!"PURCHASE_REQUEST".equals(poHeader.getSourceBillTypeCode()) && !"PURCHASE_REQUEST_LX".equals(poHeader.getSourceBillTypeCode())
                    && !"CONTRACT_ORDER".equals(poHeader.getSourceBillTypeCode())){
                //生成每一行的跨年预算数据
                for (PoLineLocation poLineLocation:poLineLocationList){
                    PoLine poLineQuery = new PoLine();
                    poLineQuery.setPoHeaderId(poLineLocation.getPoHeaderId());
                    poLineQuery.setPoLineId(poLineLocation.getPoLineId());
                    poLineQuery.setTenantId(poLineLocation.getTenantId());
                    PoLine poLine = poLineRepository.selectOne(poLineQuery);
                    RcwlBudgetDistributionDTO rcwlBudgetDistributionDTO = new RcwlBudgetDistributionDTO();
                    rcwlBudgetDistributionDTO.setAttributeDate1(poLine.getAttributeDate1().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                    rcwlBudgetDistributionDTO.setNeedByDate(poLineLocation.getNeedByDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                    rcwlBudgetDistributionDTO.setLineAmount(poLine.getLineAmount());
                    rcwlBudgetDistributionDTO.setPoHeaderId(poLineLocation.getPoHeaderId());
                    rcwlBudgetDistributionDTO.setPoLineId(poLineLocation.getPoLineId());
                    rcwlBudgetDistributionService.selectBudgetDistributionByPoLine(poHeader.getTenantId(), rcwlBudgetDistributionDTO);
                }
            }

            if (poHeader.isByErpOrSrmPr()) {
                PoOrderSaveDTO poOrderSaveDTO = new PoOrderSaveDTO();
                poOrderSaveDTO.setPoLineDetailDTOs(poLineDetailDTOList);
                this.generatorPoByPcDomainService.holdPc(poLineList, poHeader.getTenantId());
                PoDTO poDTO = new PoDTO();
                poDTO.setPoHeaderId(poHeader.getPoHeaderId());
                poDTO.setPoNum(poHeader.getPoNum());
                this.generatorPoByPrDomainService.holdPr(poLineList, poDTO);
            } else {
                this.modifyPrLineAndPrHeaderOfPoLineList(poLineList);
            }

        }
    }

    @Transactional(rollbackFor = {Exception.class})
    @EventSendTran(rollbackFor = {Exception.class})
    public PoDTO submittedPo(PoOrderSaveDTO poOrderSavaDTO) {
        this.poValidateDomainService.validateSubmitStatus(poOrderSavaDTO.getPoHeaderDetailDTO().getPoHeaderId());
        if (Objects.nonNull(poOrderSavaDTO.getPoHeaderDetailDTO().getSupplierCompanyId())) {
            SupplierLifeCycleStageVO stageVO = new SupplierLifeCycleStageVO(poOrderSavaDTO.getPoHeaderDetailDTO().getTenantId(), poOrderSavaDTO.getPoHeaderDetailDTO().getSupplierCompanyId(), poOrderSavaDTO.getPoHeaderDetailDTO().getCompanyId(), poOrderSavaDTO.getPoHeaderDetailDTO().getSupplierTenantId());
            this.poValidateDomainService.validateSupplierLifeCycleAllowOrder(stageVO);
        }
        PoHeaderDetailDTO poHeaderDetailDTO = poOrderSavaDTO.getPoHeaderDetailDTO();
        ConfigQueryVO configQueryVO = this.poHeaderMapper.selectConfigParam(poHeaderDetailDTO.getTenantId(), poHeaderDetailDTO.getPoHeaderId());
        configQueryVO.setConfigCode("SITE.SPUC.PO.REF_PRICE_LIB");
        String enablePriceLib = this.poPriceLibDomainService.queryEnablePriceLib(configQueryVO);
        configQueryVO.setConfigCode("SITE.SPUC.PO.PRICE_LIB_STRATEGY");
        String serviceCode = this.poPriceLibDomainService.queryEnablePriceLib(configQueryVO);
        String priceLibService = queryEnablePriceLibConfig(poHeaderDetailDTO.getCompanyId(), poHeaderDetailDTO.getOuId(), poHeaderDetailDTO.getPurchaseOrgId(), poHeaderDetailDTO.getTenantId());
        poOrderSavaDTO.setPoLineMysql(this.poLineMapper.selectByPoHeaderIdList(Arrays.asList(new Long[] { poHeaderDetailDTO.getPoHeaderId() })));
        String tenantNum = TenantInfoHelper.selectByTenantId(poOrderSavaDTO.getPoHeaderDetailDTO().getTenantId()).getTenantNum();
        try {
            TaskResultBox taskResultBox = AdaptorTaskHelper.executeAdaptorTask("SPUC_ORDER_PRICE_SOURCE_TYPE", tenantNum, poOrderSavaDTO);
            LOGGER.info("submittedPo taskResultBox:{}", JSONObject.toJSONString(taskResultBox));
            poOrderSavaDTO = (PoOrderSaveDTO)taskResultBox.get(0, PoOrderSaveDTO.class);
            LOGGER.info("submittedPo taskResultBox:{}", JSONObject.toJSONString(poOrderSavaDTO));
        } catch (TaskNotExistException e) {
            LOGGER.info("============ORDER_PRICE_SOURCE_TYPE--submittedPo-TaskNotExistException=============={}", new Object[] { tenantNum, e.getMessage(), e.getStackTrace() });
        }
        PoDTO poDTO = null;
        if ((poHeaderDetailDTO.isByErpOrSrmPr() || "PURCHASE_ORDER".equals(poHeaderDetailDTO.getSourceBillTypeCode())) && BaseConstants.Flag.YES.toString().equals(enablePriceLib) && StringUtils.isNotEmpty(priceLibService) && !"NULL".equals(serviceCode)) {
//            poDTO = ((PoHeaderService)self()).updatePoHeaderAndPoLineSNewPrice(poOrderSavaDTO);
            poDTO = this.operateOrder(poOrderSavaDTO);
        } else {
//            poDTO = ((PoHeaderService)self()).updatePoHeaderAndPoLineS(poOrderSavaDTO);
            poDTO = this.operateOrder(poOrderSavaDTO);
            poDTO.setHaveNullFlag(Boolean.valueOf(false));
            poDTO.setTaxNullFlag(Boolean.valueOf(false));
        }
        if (poDTO.getTaxNullFlag() != null && poDTO.getTaxNullFlag().booleanValue())
            throw new CommonException("error.price_lib_null_tax", new Object[0]);
        if (poDTO.getHaveNullFlag() != null && poDTO.getHaveNullFlag().booleanValue())
            throw new CommonException("error.price_lib_price_change", new Object[0]);
        poDTO = generatePoDto(poDTO.getPoHeaderId());
        boolean availableUpdate = (("ERP".equals(poDTO.getPoSourcePlatform()) || "SRM".equals(poDTO.getPoSourcePlatform()) || "SHOP".equals(poDTO.getPoSourcePlatform())) && "PURCHASE_REQUEST".equals(poDTO.getSourceBillTypeCode()));
        if (availableUpdate)
            validationPoDTO(poDTO);
        PoHeader poHeader = new PoHeader();
        BeanUtils.copyProperties(poDTO, poHeader);
        if (!judgeSupplierFrozen(poHeader).booleanValue())
            throw new CommonException("error.order.supplier_have_benn_frozen", new Object[0]);
        poDTO.validPoItemBomNull(poDTO, this.poLineRepository, this.poItemBomRepository);
        return ((PoHeaderService)self()).submitPlatformDispatchForSaga(poDTO);
    }

}
