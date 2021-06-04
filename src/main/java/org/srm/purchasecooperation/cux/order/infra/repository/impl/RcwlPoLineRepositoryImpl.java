package org.srm.purchasecooperation.cux.order.infra.repository.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.choerodon.core.domain.Page;
import io.choerodon.mybatis.pagehelper.PageHelper;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.hzero.boot.customize.util.CustomizeHelper;
import org.hzero.boot.platform.lov.annotation.ProcessLovValue;
import org.hzero.core.base.BaseConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.srm.boot.platform.configcenter.ConfigCenterHelper;
import org.srm.boot.platform.customizesetting.CustomizeSettingHelper;
import org.srm.boot.platform.group.domain.CustomizeSetting;
import org.srm.purchasecooperation.asn.domain.vo.AsnLineQuantityVO;
import org.srm.purchasecooperation.asn.infra.mapper.AsnLineMapper;
import org.srm.purchasecooperation.common.api.dto.SmdmCurrencyDTO;
import org.srm.purchasecooperation.common.app.MdmService;
import org.srm.purchasecooperation.cux.order.api.dto.RCWLPoLineDetailDTO;
import org.srm.purchasecooperation.cux.order.infra.mapper.RcwlPoLineMapper;
import org.srm.purchasecooperation.order.api.dto.PoLineDetailDTO;
import org.srm.purchasecooperation.order.api.dto.TaxRateConfigFieldDTO;
import org.srm.purchasecooperation.order.app.service.PoApproveRuleService;
import org.srm.purchasecooperation.order.domain.entity.PoApproveRule;
import org.srm.purchasecooperation.order.domain.entity.PoHeader;
import org.srm.purchasecooperation.order.domain.repository.PoHeaderRepository;
import org.srm.purchasecooperation.order.domain.repository.PoLineRepository;
import org.srm.purchasecooperation.order.domain.service.PoDomainService;
import org.srm.purchasecooperation.order.infra.feign.SpfmRemoteService;
import org.srm.purchasecooperation.order.infra.repository.impl.PoLineRepositoryImpl;
import org.srm.purchasecooperation.pr.domain.repository.AccountAssignTypeLineRepository;
import org.srm.web.annotation.Tenant;
import org.srm.purchasecooperation.order.infra.constant.PoConstants.EditFlag;

import org.srm.purchasecooperation.order.infra.constant.PoConstants.PoEntryPoint;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;


@Component
@Tenant("SRM-RCWL")
public class RcwlPoLineRepositoryImpl extends PoLineRepositoryImpl {
    private static final Logger LOGGER = LoggerFactory.getLogger(RcwlPoLineRepositoryImpl.class);

    @Autowired
    private RcwlPoLineMapper rcwlPoLineMapper;
    @Autowired
    private PoHeaderRepository poHeaderRepository;
    @Autowired
    private PoLineRepository poLineRepository;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private SpfmRemoteService spfmRemoteService;
    @Autowired
    private AccountAssignTypeLineRepository accountAssignTypeLineRepository;
    @Autowired
    private CustomizeSettingHelper customizeSettingHelper;
    @Autowired
    private MdmService mdmService;
    @Autowired
    private PoApproveRuleService poApproveRuleService;
    @Autowired
    private AsnLineMapper asnLineMapper;
    @Autowired
    private PoDomainService poDomainService;

    @Override
    public Page<PoLineDetailDTO> pageLineDetail(PageRequest pageRequest, Long poHeaderId, Integer camp, Long tenantId) {
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDate localDate = LocalDate.now();
        ZonedDateTime zdt = localDate.atStartOfDay(zoneId);
        Date date = Date.from(zdt.toInstant());
        return PageHelper.doPageAndSort(pageRequest, () -> {

            //过滤 个性化页面查 追加mysql值字段
            List<RCWLPoLineDetailDTO>  detailDTO = CustomizeHelper.ignore(()-> this.rcwlPoLineMapper.listLineDetail1(tenantId, poHeaderId, date));
            /*
            detailDTO.forEach(x->{
                x.setAttributeVarchar21Meanings(rcwlPoLineMapper.queryBudgetAccount(x.getAttributeVarchar21()));//x.getAttributeVarchar21()
            });*/

            return detailDTO;
        });
    }



    public Page<PoLineDetailDTO> pageLineDetail(PageRequest pageRequest, Long poHeaderId, Long tenantId, Integer camp, Integer sortType, PoEntryPoint poEntryPoint) {
        if (poEntryPoint == null) {
            poEntryPoint = PoEntryPoint.PURCHASE_PO_DETAIL;
        }

        PoHeader poHeader = (PoHeader)this.poHeaderRepository.selectByPrimaryKey(poHeaderId);
        Page<PoLineDetailDTO> poLineDetailDTOsPage = this.poLineRepository.pageLineDetail(pageRequest, poHeaderId, camp, tenantId);
        List<PoLineDetailDTO> poLineDetailDTOList = poLineDetailDTOsPage.getContent();

        poLineDetailDTOList.forEach(x->{
            LOGGER.info("------验证2--------- " + x.toString());
        });

        try {
            LOGGER.info("25855 chartCode is null?:" + this.objectMapper.writeValueAsString(poLineDetailDTOList));
        } catch (Exception var25) {
            LOGGER.info("write log error!");
        }

        if (!CollectionUtils.isEmpty(poLineDetailDTOList) && poEntryPoint != PoEntryPoint.PO_MAINTAIN_DETAIL) {
            this.processShield(tenantId, camp, poLineDetailDTOList);
        } else if (!CollectionUtils.isEmpty(poLineDetailDTOList) && poEntryPoint.equals(PoEntryPoint.PO_MAINTAIN_DETAIL)) {
            this.accountAssignTypeRequiredFieldHandler(poLineDetailDTOList);
        }

        TaxRateConfigFieldDTO taxRate = this.poLineRepository.selectDefaultTaxRate(tenantId);
        boolean defaultPromiseDeliveryDateFlag = "ORDER_DEMAND_DATE".equals(this.customizeSettingHelper.queryBySettingCode(tenantId, "010206"));
        if (PoEntryPoint.PURCHASE_PO_CONFIRM.equals(poEntryPoint) && defaultPromiseDeliveryDateFlag) {
            poLineDetailDTOList.forEach((item) -> {
                LOGGER.info("25855 chart code1:" + item.getChartCode());
                if (Objects.isNull(item.getPromiseDeliveryDate())) {
                    item.setPromiseDeliveryDate(item.getNeedByDate());
                }

            });
        }

        poLineDetailDTOList.forEach((poLineDetailDTOx) -> {
            LOGGER.info("25855 chart code:" + poLineDetailDTOx.getChartCode());
            if (poLineDetailDTOx.getSourceBillTypeCode() != null && poLineDetailDTOx.getSourceBillTypeCode().equals("PURCHASE_ORDER")) {
                if (poLineDetailDTOx.getEnteredTaxIncludedPrice() != null) {
                    poLineDetailDTOx.setEnteredTaxIncludedPrice(new BigDecimal(poLineDetailDTOx.getEnteredTaxIncludedPrice().stripTrailingZeros().toPlainString()));
                }

                if (poLineDetailDTOx.getUnitPrice() != null) {
                    poLineDetailDTOx.setUnitPrice(new BigDecimal(poLineDetailDTOx.getUnitPrice().stripTrailingZeros().toPlainString()));
                }
            }

            if (StringUtils.isNotBlank(poLineDetailDTOx.getSourceBillTypeCode()) && poLineDetailDTOx.getSourceBillTypeCode().equals("SOURCE") && Objects.nonNull(poLineDetailDTOx.getTaxIncludedFlag())) {
                String benchmarkType = poLineDetailDTOx.getTaxIncludedFlag() == 0 ? "NET_PRICE" : "TAX_INCLUDED_PRICE";
                LOGGER.debug("25583-引用寻源创建的订单基准价类型: benchmarkPriceType:{}", benchmarkType);
                poLineDetailDTOx.setBenchmarkPriceType(benchmarkType);
            }

            this.initFinancialPrecision(poLineDetailDTOx, poHeader);
            if (Objects.isNull(poLineDetailDTOx.getTaxId()) && !Objects.isNull(taxRate)) {
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("=======setDefaultTaxRate：{}", taxRate.toString());
                }

                poLineDetailDTOx.setTaxId(taxRate.getTaxId());
                poLineDetailDTOx.setTaxCode(taxRate.getTaxCode());
                poLineDetailDTOx.setTaxRate(taxRate.getTaxRate());
            }

        });
        this.operatePoLine(poLineDetailDTOList);
        if (CollectionUtils.isNotEmpty(poLineDetailDTOList)) {
            List<PoApproveRule> poApproveRules = this.poApproveRuleService.selectPoApproveRule(tenantId);
            if (CollectionUtils.isNotEmpty(poApproveRules)) {
                PoApproveRule date = (PoApproveRule)poApproveRules.stream().filter((o) -> {
                    return "COMMITTED_DELIVERY_DATE".equals(o.getFieldName());
                }).findAny().orElse(null);
                String config = "";

                try {
                    config = ConfigCenterHelper.select(tenantId, "SITE.SPUC.PO.CONFIRM_EDITOR_DELIVERY_DATE").invoke(new String[]{String.valueOf(tenantId), "SITE.SPUC.PO.CONFIRM_EDITOR_DELIVERY_DATE", null, null, null, null});
                } catch (Exception var24) {
                    LOGGER.debug("Tenant not configured with rules");
                }

                PoApproveRule nuber = (PoApproveRule)poApproveRules.stream().filter((o) -> {
                    return "QUANTITY".equals(o.getFieldName());
                }).findAny().orElse(null);
                poHeader.calculatePoSts();
                List<AsnLineQuantityVO> asnList = this.asnLineMapper.getAsnListByPoHeaderId(tenantId, poHeaderId);
                Iterator var17 = poLineDetailDTOList.iterator();

                label135:
                while(true) {
                    PoLineDetailDTO poLineDetailDTO;
                    BigDecimal receiveQuantity;
                    do {
                        do {
                            do {
                                if (!var17.hasNext()) {
                                    break label135;
                                }

                                poLineDetailDTO = (PoLineDetailDTO)var17.next();
                                LOGGER.info("25855 chart code2:" + poLineDetailDTO.getChartCode());
                                PoLineDetailDTO finalPoLineDetailDTO = poLineDetailDTO;
                                List collect = (List)asnList.stream().filter((o) -> {
                                    return finalPoLineDetailDTO.getPoLineId().equals(o.getPoLineId());
                                }).collect(Collectors.toList());
                                receiveQuantity = new BigDecimal(BigInteger.ZERO);
                                if (CollectionUtils.isNotEmpty(collect)) {
                                    Iterator var21 = collect.iterator();

                                    while(var21.hasNext()) {
                                        AsnLineQuantityVO vo = (AsnLineQuantityVO)var21.next();
                                        if (vo.getReceiveQuantity() != null) {
                                            receiveQuantity.add(vo.getReceiveQuantity());
                                        }
                                    }
                                }

                                if (date.getRequiredFlag() == null) {
                                    poLineDetailDTO.setDeliveryDateEnableFlag(EditFlag.ZERO);
                                } else {
                                    poLineDetailDTO.setDeliveryDateEnableFlag(date.getRequiredFlag());
                                }

                                if (nuber.getRequiredFlag() == null) {
                                    poLineDetailDTO.setQuantityEnableFlag(EditFlag.ZERO);
                                } else {
                                    poLineDetailDTO.setQuantityEnableFlag(nuber.getRequiredFlag());
                                }

                                poLineDetailDTO.setDeliveryDateEditFlag(EditFlag.ZERO);
                                poLineDetailDTO.setQuantityEditFlag(EditFlag.ZERO);
                            } while(!StringUtils.isNotEmpty(config));

                            if (config.contains("deliveryDate") && ("CONFIRMED".equals(poHeader.getStatusCode()) || BaseConstants.Flag.YES.equals(poLineDetailDTO.getConfirmFlag())) && EditFlag.ZERO.equals(poLineDetailDTO.getCancelledFlag()) && EditFlag.ZERO.equals(poLineDetailDTO.getClosedFlag()) && !EditFlag.ZERO.equals(poLineDetailDTO.getQuantity().compareTo(receiveQuantity))) {
                                poLineDetailDTO.setDeliveryDateEditFlag(EditFlag.ONE);
                            }
                        } while(!config.contains("quantity"));
                    } while(!"CONFIRMED".equals(poHeader.getStatusCode()) && !BaseConstants.Flag.YES.equals(poLineDetailDTO.getConfirmFlag()));

                    if (EditFlag.ZERO.equals(poLineDetailDTO.getCancelledFlag()) && EditFlag.ZERO.equals(poLineDetailDTO.getClosedFlag()) && !EditFlag.ZERO.equals(poLineDetailDTO.getQuantity().compareTo(receiveQuantity))) {
                        poLineDetailDTO.setQuantityEditFlag(EditFlag.ONE);
                    }
                }
            }
        }

        Iterator var26 = poLineDetailDTOList.iterator();

        while(var26.hasNext()) {
            PoLineDetailDTO poLineDetailDTO = (PoLineDetailDTO)var26.next();
            if (Objects.equals(poLineDetailDTO.getReturnedFlag(), BaseConstants.Flag.YES)) {
                if (Objects.nonNull(poLineDetailDTO.getDomesticLineAmount())) {
                    poLineDetailDTO.setDomesticLineAmount(BigDecimal.ZERO.subtract(poLineDetailDTO.getDomesticLineAmount()));
                }

                if (Objects.nonNull(poLineDetailDTO.getDomesticTaxIncludedLineAmount())) {
                    poLineDetailDTO.setDomesticTaxIncludedLineAmount(BigDecimal.ZERO.subtract(poLineDetailDTO.getDomesticTaxIncludedLineAmount()));
                }
            }
        }

        poLineDetailDTOsPage.setContent(poLineDetailDTOList);

        try {
            LOGGER.info("25855 chartCode is null?:" + this.objectMapper.writeValueAsString(poLineDetailDTOList));
        } catch (Exception var23) {
            LOGGER.info("write log error!");
        }

        return poLineDetailDTOsPage;
    }

    private void processShield(Long tenantId, Integer camp, List<PoLineDetailDTO> poLineDetailDTOS) {
        CustomizeSetting customizeSetting = this.spfmRemoteService.queryBySettingCode(tenantId, "010202");
        if (!Objects.isNull(customizeSetting)) {
            String result = customizeSetting.getSettingValue();
            if ("1".equals(result)) {
                Map<String, Boolean> shieldMap = new HashMap();
                poLineDetailDTOS.forEach((poLineDetailDTO) -> {
                    Long orgId = poLineDetailDTO.getOuId();
                    Long supplierCompanyId = poLineDetailDTO.getSupplierCompanyId();
                    if (Objects.isNull(shieldMap.get(StringUtils.join(new Number[]{tenantId, camp, orgId, supplierCompanyId})))) {
                        boolean isPriceShield = this.poDomainService.getIfShield(tenantId, camp, orgId, supplierCompanyId);
                        shieldMap.put(StringUtils.join(new Number[]{tenantId, camp, orgId, supplierCompanyId}), isPriceShield);
                    }

                    poLineDetailDTOS.forEach((item) -> {
                        item.transSomeFields((Boolean)shieldMap.get(StringUtils.join(new Number[]{tenantId, camp, orgId, supplierCompanyId})));
                    });
                });
            }
        }
    }
    private void accountAssignTypeRequiredFieldHandler(List<PoLineDetailDTO> poLineDetailDTOList) {
        List<Long> idList = (List)poLineDetailDTOList.stream().map(PoLineDetailDTO::getAccountAssignTypeId).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(idList)) {
            Map<Long, Set<String>> map = this.accountAssignTypeLineRepository.queryAccountAssignTypeRequiredField(idList, "PO_LINE");
            Iterator var4 = poLineDetailDTOList.iterator();

            while(var4.hasNext()) {
                PoLineDetailDTO poLineDetailDTO = (PoLineDetailDTO)var4.next();
                Long accountAssignTypeId = poLineDetailDTO.getAccountAssignTypeId();
                if (Objects.nonNull(accountAssignTypeId)) {
                    poLineDetailDTO.setAssignTypeRequiredFieldNames((Set)map.get(accountAssignTypeId));
                }
            }

        }
    }
    public void initFinancialPrecision(PoLineDetailDTO poLineDetailDTO, PoHeader poHeader) {
        SmdmCurrencyDTO domesticCurrencyDTO;
        if (Objects.nonNull(poLineDetailDTO.getCurrencyCode())) {
            domesticCurrencyDTO = this.mdmService.selectSmdmCurrencyDto(poLineDetailDTO.getTenantId(), poLineDetailDTO.getCurrencyCode());
            poLineDetailDTO.setFinancialPrecision(domesticCurrencyDTO.getFinancialPrecision());
        }

        if (Objects.nonNull(poHeader.getDomesticCurrencyCode())) {
            domesticCurrencyDTO = this.mdmService.selectSmdmCurrencyDto(poLineDetailDTO.getTenantId(), poHeader.getDomesticCurrencyCode());
            poLineDetailDTO.setDomesticFinancialPrecision(domesticCurrencyDTO.getFinancialPrecision());
        }
    }

    private void operatePoLine(List<PoLineDetailDTO> poLineDetailDTOList) {
        Iterator var2 = poLineDetailDTOList.iterator();
        while(var2.hasNext()) {
            PoLineDetailDTO poLineDetailDTO = (PoLineDetailDTO)var2.next();
            if (poLineDetailDTO.getReturnedFlag() != null && poLineDetailDTO.getReturnedFlag() == 1) {
                if (Objects.nonNull(poLineDetailDTO.getLineAmount())) {
                    poLineDetailDTO.setLineAmount(poLineDetailDTO.getLineAmount().multiply(new BigDecimal(-1)));
                } else {
                    poLineDetailDTO.setLineAmount(new BigDecimal(0));
                }

                if (Objects.nonNull(poLineDetailDTO.getTaxIncludedLineAmount())) {
                    poLineDetailDTO.setTaxIncludedLineAmount(poLineDetailDTO.getTaxIncludedLineAmount().multiply(new BigDecimal(-1)));
                } else {
                    poLineDetailDTO.setTaxIncludedLineAmount(new BigDecimal(0));
                }
            }
        }
    }








}
