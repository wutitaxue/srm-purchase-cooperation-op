package org.srm.purchasecooperation.cux.order.app.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.choerodon.core.convertor.ApplicationContextHelper;
import io.choerodon.core.exception.CommonException;
import io.choerodon.core.oauth.DetailsHelper;
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
import org.srm.boot.adaptor.client.result.TaskResultBox;
import org.srm.boot.platform.customizesetting.CustomizeSettingHelper;
import org.srm.common.TenantInfoHelper;
import org.srm.common.util.StringToNumberUtils;
import org.srm.purchasecooperation.common.app.MdmService;
import org.srm.purchasecooperation.cux.order.app.service.RcwlPoHeaderCreateService;
import org.srm.purchasecooperation.cux.order.domain.repository.RcwlSpcmPcSubjectRepository;
import org.srm.purchasecooperation.cux.order.domain.vo.PcHeaderVO;
import org.srm.purchasecooperation.cux.order.infra.mapper.RcwlSpcmPcSubjectMapper;
import org.srm.purchasecooperation.order.api.dto.ContractResultDTO;
import org.srm.purchasecooperation.order.api.dto.PoDTO;
import org.srm.purchasecooperation.order.api.dto.PoLineDetailDTO;
import org.srm.purchasecooperation.order.app.service.OrderTypeService;
import org.srm.purchasecooperation.order.app.service.PoConfigRuleService;
import org.srm.purchasecooperation.order.app.service.PoProcessActionService;
import org.srm.purchasecooperation.order.app.service.PoheaderExtensionService;
import org.srm.purchasecooperation.order.domain.entity.ChangeHistory;
import org.srm.purchasecooperation.order.domain.entity.PoHeader;
import org.srm.purchasecooperation.order.domain.entity.PoLine;
import org.srm.purchasecooperation.order.domain.entity.PoLineLocation;
import org.srm.purchasecooperation.order.domain.entity.User;
import org.srm.purchasecooperation.order.domain.repository.ChangeHistoryRepository;
import org.srm.purchasecooperation.order.domain.repository.PoHeaderRepository;
import org.srm.purchasecooperation.order.domain.repository.PoItemBomRepository;
import org.srm.purchasecooperation.order.domain.repository.PoLineLocationRepository;
import org.srm.purchasecooperation.order.domain.repository.PoLineRepository;
import org.srm.purchasecooperation.order.domain.repository.PoPartnerRepository;
import org.srm.purchasecooperation.order.domain.repository.UserRepository;
import org.srm.purchasecooperation.order.domain.service.PoHeaderDomainService;
import org.srm.purchasecooperation.order.domain.service.PoHeaderSendApplyMqService;
import org.srm.purchasecooperation.order.domain.service.PoItfDomainService;
import org.srm.purchasecooperation.order.domain.service.PoValidateDomainService;
import org.srm.purchasecooperation.order.domain.vo.SupplierLifeCycleStageVO;
import org.srm.purchasecooperation.order.infra.constant.PoConstants;
import org.srm.purchasecooperation.order.infra.mapper.PoHeaderMapper;
import org.srm.purchasecooperation.order.infra.utils.FieldUtils;
import org.srm.purchasecooperation.pr.domain.entity.PrHeader;
import org.srm.purchasecooperation.pr.domain.entity.PrLine;
import org.srm.purchasecooperation.pr.domain.repository.PrHeaderRepository;
import org.srm.purchasecooperation.pr.domain.repository.PrLineRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author zpx
 * @desc ...
 * @date 2021-10-26 20:17:31
 */
@Service
public class RcwlPoHeaderCreateServiceImpl implements RcwlPoHeaderCreateService {

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
    private PoItfDomainService poItfDomainService;
    @Autowired
    private PoValidateDomainService poValidateDomainService;
    @Autowired
    private PoProcessActionService poProcessActionService;
    @Autowired
    private MdmService mdmService;
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
    private CustomizeSettingHelper customizeSettingHelper;
    @Autowired
    private PoItemBomRepository poItemBomRepository;
    @Autowired
    private PrHeaderRepository prHeaderRepository;
    @Autowired
    private PrLineRepository prLineRepository;
    @Autowired
    private RcwlPoHeaderServiceImpl poHeaderService;
    @Autowired
    private RcwlSpcmPcSubjectMapper rcwlSpcmPcSubjectMapper;

    private static final Logger LOGGER = LoggerFactory.getLogger(RcwlPoHeaderCreateServiceImpl.class);

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public PoDTO createAnOrderBasedOnContract(Long tenantId, List<ContractResultDTO> contractResultDTOList) {
        if (CollectionUtils.isEmpty(contractResultDTOList)) {
            throw new CommonException("spuc.order.subject_can_not_be_null", new Object[0]);
        } else {
            //查询引用合同数据
            ContractResultDTO contractResult = contractResultDTOList.get(0);
            PcHeaderVO pcHeaderVO = rcwlSpcmPcSubjectMapper.selectSpcmPcHeader(contractResult.getPcHeaderId(), contractResult.getTenantId());
            Date pcStartDateActive = null;
            Date pcEndDateActive = null;
            if (Objects.nonNull(pcHeaderVO)){
                pcStartDateActive = pcHeaderVO.getStartDateActive();
                pcEndDateActive = pcHeaderVO.getEndDateActive();
            }
            Boolean autoTransferFlag = false;
            String autoPoStatus = null;
            if (PoConstants.autoTransferOrderFlag.YES.equals(((ContractResultDTO)contractResultDTOList.get(0)).getAutoTransferOrderFlag())) {
                autoTransferFlag = true;
                autoPoStatus = poHeaderService.checkContractData(contractResultDTOList, autoPoStatus);
            }

            Assert.isTrue(CollectionUtils.isNotEmpty(contractResultDTOList), "error.pr.line.list.not.null");
            String language = DetailsHelper.getUserDetails().getLanguage();
            List<PoLineDetailDTO> poLineDetailDTOList = new ArrayList(contractResultDTOList.size());
            List<PoLine> poLineList = new ArrayList();
            Iterator var8 = contractResultDTOList.iterator();

            while(var8.hasNext()) {
                ContractResultDTO contractResultDTO = (ContractResultDTO)var8.next();
                if (Objects.nonNull(contractResultDTO.getSupplierCompanyId())) {
                    SupplierLifeCycleStageVO stageVO = new SupplierLifeCycleStageVO(tenantId, contractResultDTO.getSupplierCompanyId(), contractResultDTO.getCompanyId(), contractResultDTO.getSupplierTenantId());
                    this.poValidateDomainService.validateSupplierLifeCycleAllowOrder(stageVO);
                }
            }

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
            //来源合同号，来源合同id
            poDTO.setAttributeVarchar2(contractResultDTO.getPcNum());
            poDTO.setAttributeBigint1(contractResultDTO.getPcHeaderId());
            domesticCurrencyCode = this.poHeaderMapper.queryCurrencyByCompanyId(contractResultDTO.getCompanyId());
            if (domesticCurrencyCode != null) {
                poDTO.setDomesticCurrencyCode(domesticCurrencyCode);
            }

            String finalDomesticCurrencyCode = domesticCurrencyCode;
            poLineList.forEach((poLinex) -> {
                poLinex.modifyDomesticInfoByExchangeRate(this.mdmService, finalDomesticCurrencyCode);
            });
            //合同起止日期
            poDTO.setAttributeDate2(pcStartDateActive);
            poDTO.setAttributeDate3(pcEndDateActive);
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

            poDTO.setSourceBillTypeCode("CONTRACT_ORDER_WJ");
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
            poDTO = poHeaderService.transferPcToPo(poDTO, tenantId, contractResultDTOList);
            if ("SUBMITTED".equals(autoPoStatus)) {
                poDTO = poHeaderService.adaptorPo(poDTO);
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
            if (!poHeaderService.judgeSupplierFrozen(poHeader)) {
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

//                contractResultDTOList.forEach((cr) -> {
//                    ContractResultDTO contractResultDto = this.poLineRepository.selectContractSubject(tenantId, cr.getPcSubjectId());
//                    contractResultDto.setPcSubjectId(cr.getPcSubjectId());
//                    contractResultDto.setChanageOrderQuantity(contractResultDto.getChanageOrderQuantity().add(cr.getReceiptsOrderQuantity()));
//                    this.poLineRepository.updateSubjce(contractResultDto);
//                });
                this.changeHistoryRepository.batchInsertSelective(changeHistoryList);
                if (autoTransferFlag && "SUBMITTED".equals(autoPoStatus)) {
                    try {
                        poHeaderService.submitPlatformDispatch(poDTO);
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


    @Transactional(rollbackFor = {Exception.class})
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

        poHeaderService.setPoLineDomesticInfo(poDto.getPoLineList(), poHeader);
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
//        poHeader.stsHandleAfterPoUpdate(this.poLineLocationRepository.select(query));
        poHeader.handlePrice(this.poHeaderRepository);
        if (isSRM) {
            poHeader.modifyPricePrecisionByCurrencyCode(this.mdmService);
        }

        this.poHeaderRepository.updateOptional(poHeader, new String[]{"amount", "taxIncludeAmount"});
        BeanUtils.copyProperties(poHeader, poDto);
        poDto.setPoPartnerList(this.poPartnerRepository.batchInsertOrUpdate(poDto.getPoPartnerList(), poDto.getPoHeaderId(), poDto.getTenantId()));
        this.poProcessActionService.insert(poDto.getPoHeaderId(), "NEW");
//        this.processPr(poDto);
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



}
