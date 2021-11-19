package org.srm.purchasecooperation.order.api.dto;

import io.choerodon.core.exception.CommonException;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.hzero.core.base.BaseConstants.Flag;
import org.hzero.starter.keyencrypt.core.Encrypt;
import org.springframework.beans.BeanUtils;
import org.srm.boot.platform.customizesetting.CustomizeSettingHelper;
import org.srm.common.mybatis.domain.ExpandDomain;
import org.srm.purchasecooperation.common.api.dto.SmdmCurrencyDTO;
import org.srm.purchasecooperation.common.app.MdmService;
import org.srm.purchasecooperation.order.domain.entity.PoHeader;
import org.srm.purchasecooperation.order.domain.entity.PoLine;
import org.srm.purchasecooperation.order.domain.entity.PoLineLocation;
import org.srm.purchasecooperation.order.domain.repository.PoHeaderRepository;
import org.srm.purchasecooperation.order.domain.repository.PoLineLocationRepository;
import org.srm.purchasecooperation.order.infra.constant.PoConstants.FreeFlag;
import org.srm.purchasecooperation.pr.domain.repository.PrLineRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class PoOrderSaveDTO extends ExpandDomain {
    @Encrypt
    private PoHeaderDetailDTO poHeaderDetailDTO;
    private List<PoLineBasicDetailDTO> poLineBasicDetailDTOs;
    private List<PoLineOtherDetailDTO> poLineOtherDetailDTOs;
    private List<PoLineDetailDTO> poLineDetailDTOs;
    private List<PoLine> poLineMysql;
    private String errorMsg;
    private Integer firstFlag;
    private Integer updatePriceLibFlag;
    private MdmService mdmService;
    private String viewConfig;
    @ApiModelProperty("结算供应商id")
    private Long settleSupplierId;
    @ApiModelProperty("结算供应商code")
    private String settleSupplierCode;
    @ApiModelProperty("结算供应商name")
    private String settleSupplierName;
    @ApiModelProperty("结算供应商租户id")
    private Long settleSupplierTenantId;
    @ApiModelProperty("结算供应商id")
    private Long settleErpSupplierId;
    @ApiModelProperty("结算供应商code")
    private String settleErpSupplierCode;
    @ApiModelProperty("结算供应商name")
    private String settleErpSupplierName;

    public PoOrderSaveDTO() {
    }

    public void initPoLineAndPoHeader(CustomizeSettingHelper customizeSettingHelper, PoHeaderRepository poHeaderRepository, PoHeader poHeader, List<PoLine> poUpdateLineList, List<PoLine> poInsertLineList, List<PoLineLocation> poUpdateLineLocationList, PrLineRepository prLineRepository) {
        BeanUtils.copyProperties(this.poHeaderDetailDTO, poHeader);
        SmdmCurrencyDTO currencyDTO = null;
        int defaultPrecision = 10;
        int financialPrecision = defaultPrecision;
        if (CollectionUtils.isNotEmpty(this.poLineDetailDTOs)) {
            Iterator var11 = this.poLineDetailDTOs.iterator();

            while(var11.hasNext()) {
                PoLineDetailDTO p = (PoLineDetailDTO)var11.next();
                if (Objects.nonNull(this.mdmService)) {
                    currencyDTO = Objects.nonNull(currencyDTO) && Objects.equals(currencyDTO.getCurrencyCode(), p.getCurrencyCode()) ? currencyDTO : this.mdmService.selectSmdmCurrencyDto(poHeader.getTenantId(), p.getCurrencyCode());
                    defaultPrecision = currencyDTO.getDefaultPrecision();
                    financialPrecision = currencyDTO.getFinancialPrecision();
                }

                p.setUnitPrice(Objects.isNull(p.getUnitPrice()) ? null : p.getUnitPrice().setScale(defaultPrecision, RoundingMode.HALF_UP));
                p.setEnteredTaxIncludedPrice(Objects.isNull(p.getEnteredTaxIncludedPrice()) ? null : p.getEnteredTaxIncludedPrice().setScale(defaultPrecision, RoundingMode.HALF_UP));
                boolean updateFlag = p.getPoLineId() != null && p.getPoLineId() != 0L;
                PoLine poLine = new PoLine();
                BeanUtils.copyProperties(p, poLine);
                poLine.setObjectVersionNumber(p.getObjectVersionNumber());
                poLine.setPoLineId(p.getPoLineId());
                poLine.setLineNum(Objects.isNull(p.getLineNum()) ? null : (long)p.getLineNum());
                poLine.setDisplayLineNum(p.getLineNum() == null ? null : String.valueOf(p.getLineNum()));
                poLine.setItemId(p.getItemId());
                poLine.setItemCode(p.getItemCode());
                poLine.setItemName(p.getItemName());
                poLine.setCategoryId(p.getCategoryId());
                poLine.setUomId(p.getUomId());
                poLine.setTaxId(p.getTaxId());
                poLine.setTaxRate(p.getTaxRate());
                poLine.setEnteredTaxIncludedPrice(p.getEnteredTaxIncludedPrice());
                poLine.setCurrencyCode(p.getCurrencyCode());
                poLine.setBrand(p.getBrand());
                poLine.setSpecifications(p.getSpecifications());
                poLine.setModel(p.getModel());
                poLine.setDisplayPrNum(p.getDisplayPrNum());
                poLine.setDisplayPrLineNum(p.getDisplayPrLineNum());
                poLine.setPrRequestedBy(p.getPrRequestedBy());
                poLine.setPrRequestedName(p.getPrRequestedName());
                poLine.setProductId(p.getProductId());
                poLine.setProductNum(p.getProductNum());
                poLine.setProductName(p.getProductName());
                poLine.setCatalogId(p.getCatalogId());
                poLine.setCatalogName(p.getCatalogName());
                poLine.setPriceLibraryId(p.getPriceLibraryId());
                poLine.setRemark(p.getRemark());
                poLine.setHoldPcLineId(p.getHoldPcLineId());
                poLine.setHoldPcHeaderId(p.getHoldPcHeaderId());
                poLine.setPriceTaxId(p.getPriceTaxId());
                poLine.setPriceContractFlag(p.getPriceContractFlag());
                poLine.setReceiveToleranceQuantity(p.getReceiveToleranceQuantity());
                BigDecimal taxNotIncludePrice;
                if ((p.getPriceTaxId() == null || p.getPriceTaxId() == 0L) && this.getPoHeaderDetailDTO().getModifyablePriceFlag() == 0 && p.getPriceLibraryId() != null) {
                    taxNotIncludePrice = p.getUnitPrice();
                    poLine.setUnitPrice(p.getUnitPrice());
                    p.setEnteredTaxIncludedPrice(taxNotIncludePrice.multiply(BigDecimal.ONE.add(p.getTaxRate().divide(new BigDecimal("100")))).setScale(defaultPrecision, RoundingMode.HALF_UP));
                } else if (StringUtils.equalsAny(poHeader.getSourceBillTypeCode(), new CharSequence[]{"PURCHASE_REQUEST", "PURCHASE_ORDER"})) {
                    if ("NET_PRICE".equals(poLine.getBenchmarkPriceType())) {
                        taxNotIncludePrice = p.getUnitPrice();
                        p.setEnteredTaxIncludedPrice(taxNotIncludePrice.multiply(BigDecimal.ONE.add(p.getTaxRate().divide(new BigDecimal("100")))).setScale(defaultPrecision, RoundingMode.HALF_UP));
                        poLine.setEnteredTaxIncludedPrice(p.getEnteredTaxIncludedPrice());
                        poLine.setBenchmarkPriceType("NET_PRICE");
                    } else {
                        taxNotIncludePrice = p.getEnteredTaxIncludedPrice().divide(BigDecimal.ONE.add(p.getTaxRate().divide(new BigDecimal("100"))), defaultPrecision, RoundingMode.HALF_UP);
                        poLine.setUnitPrice(taxNotIncludePrice);
                        poLine.setBenchmarkPriceType("TAX_INCLUDED_PRICE");
                    }
                } else {
                    taxNotIncludePrice = p.getEnteredTaxIncludedPrice().divide(BigDecimal.ONE.add(p.getTaxRate().divide(new BigDecimal("100"))), defaultPrecision, RoundingMode.HALF_UP);
                    poLine.setUnitPrice(taxNotIncludePrice);
                }

                poLine.setQuantity(p.getQuantity());
                poLine.setUnitPriceBatch((BigDecimal)Optional.ofNullable(BigDecimal.ZERO == p.getUnitPriceBatch() ? BigDecimal.ONE : p.getUnitPriceBatch()).orElse(BigDecimal.ONE));
                poLine.setTaxIncludedLineAmount(p.getEnteredTaxIncludedPrice().multiply(p.getQuantity()).divide((BigDecimal)Optional.ofNullable(BigDecimal.ZERO == p.getUnitPriceBatch() ? BigDecimal.ONE : p.getUnitPriceBatch()).orElse(BigDecimal.ONE), financialPrecision, RoundingMode.HALF_UP));
                poLine.setLineAmount(taxNotIncludePrice.multiply(p.getQuantity()).divide((BigDecimal)Optional.ofNullable(BigDecimal.ZERO == p.getUnitPriceBatch() ? BigDecimal.ONE : p.getUnitPriceBatch()).orElse(BigDecimal.ONE), financialPrecision, RoundingMode.HALF_UP));
                poLine.setPrLineId(p.getPrLineId());
                poLine.setPrHeaderId(p.getPrHeaderId());
                poLine.setDemandUnitId(p.getUnitId());
                poLine.setLadderInquiryFlag(p.getLadderInquiryFlag());
                this.handWorkTieredPricing(customizeSettingHelper, poHeader, poLine, p, poHeaderRepository);
                poLine.setLastPurchasePrice(p.getLastPurchasePrice());
                poLine.setDepartmentId(p.getDepartmentId());
                poLine.setClearOrganizationId(p.getClearOrganizationId());
                poLine.setCopeOrganizationId(p.getCopeOrganizationId());
                poLine.setChartCode(p.getChartCode());
                poLine.setChartVersion(p.getChartVersion());
                poLine.setSurfaceTreatFlag(p.getSurfaceTreatFlag());
                poLine.setPcNum(p.getPcNum());
                poLine.setPcHeaderId(p.getPcHeaderId());
                poLine.setJdPrice(p.getJdPrice());
                poLine.setSupplierItemNum(p.getSupplierItemNum());
                poLine.setProductionOrderNum(p.getProductionOrderNum());
                poLine.setAccountAssignTypeId(p.getAccountAssignTypeId());
                poLine.setCostId(p.getCostId());
                poLine.setCostCode(p.getCostCode());
                poLine.setAccountSubjectId(p.getAccountSubjectId());
                poLine.setAccountSubjectNum(p.getAccountSubjectNum());
                poLine.setWbsCode(p.getWbsCode());
                poLine.setWbs(p.getWbs());
                poLine.setProjectCategory(p.getProjectCategory());
                poLine.setAttachmentUuid(p.getAttachmentUuid());
                if (FreeFlag.YES.equals(poLine.getFreeFlag())) {
                    poLine.setTaxIncludedLineAmount(new BigDecimal("0"));
                    poLine.setLineAmount(new BigDecimal("0"));
                    poLine.setUnitPrice(new BigDecimal("0"));
                    poLine.setEnteredTaxIncludedPrice(new BigDecimal("0"));
                }

                if ("CNY".equals(p.getCurrencyCode())) {
                    poLine.setRate(new BigDecimal("1"));
                } else {
                    poLine.setRate(new BigDecimal("0"));
                }

                PoLineLocation poLineLocation = new PoLineLocation();
                poLineLocation.setPoLineLocationId(p.getPoLineLocationId());
                poLineLocation.setPoLineId(p.getPoLineId());
                poLineLocation.setQuantity(p.getQuantity());
                poLineLocation.setInvOrganizationId(p.getInvOrganizationId());
                poLineLocation.setInvInventoryId(p.getInvInventoryId());
                poLineLocation.setInvLocationId(p.getInvLocationId());
                poLineLocation.setShipToThirdPartyCode(p.getShipToThirdPartyCode());
                poLineLocation.setShipToThirdPartyName(p.getShipToThirdPartyName());
                poLineLocation.setShipToThirdPartyAddress(p.getShipToThirdPartyAddress());
                poLineLocation.setShipToThirdPartyContact(p.getShipToThirdPartyContact());
                poLineLocation.setRemark(p.getRemark());
                poLineLocation.setNeedByDate(p.getNeedByDate());
                poLineLocation.setDeleteFlag(p.getPoLineLocationDeleteFlag());
                if (updateFlag) {
                    p.validPo(prLineRepository);
                    poLine.setPoLineLocationList(Collections.singletonList(poLineLocation));
                    poLine.setObjectVersionNumber(p.getLineVersionNumber());
                    poUpdateLineList.add(poLine);
                    poLineLocation.setObjectVersionNumber(p.getLocationVersionNumber());
                    poUpdateLineLocationList.add(poLineLocation);
                } else if (!Flag.YES.equals(p.getPoLineLocationDeleteFlag())) {
                    p.validPo(prLineRepository);
                    poLine.setPrHeaderId(p.getPrHeaderId());
                    poLine.setPrLineId(p.getPrLineId());
                    poLine.setHoldPcHeaderId(p.getHoldPcHeaderId());
                    poLine.setHoldPcLineId(p.getHoldPcLineId());
                    poLine.setPoLineLocationList(Collections.singletonList(poLineLocation));
                    poLine.setInsertFlag(Flag.YES);
                    poLineLocation.setInsertFlag(Flag.YES);
                    poInsertLineList.add(poLine);
                }
            }
        }

        if (this.poLineDetailDTOs.size() > 0 && (((PoLineDetailDTO)this.poLineDetailDTOs.get(0)).getNewPriceFlag() == null || !((PoLineDetailDTO)this.poLineDetailDTOs.get(0)).getNewPriceFlag())) {
            this.initDataItemPriceLibrary(customizeSettingHelper, poHeaderRepository, this.poLineDetailDTOs, poHeader);
        }

    }

    public String getErrorMsg() {
        return this.errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getViewConfig() {
        return this.viewConfig;
    }

    public void setViewConfig(String viewConfig) {
        this.viewConfig = viewConfig;
    }

    private void handWorkTieredPricing(CustomizeSettingHelper customizeSettingHelper, PoHeader poHeader, PoLine poLine, PoLineDetailDTO poLineDetailDTO, PoHeaderRepository poHeaderRepository) {
        if ("PURCHASE_ORDER".equals(this.poHeaderDetailDTO.getSourceBillTypeCode())) {
            Integer tieredPricingSetting = (Integer)customizeSettingHelper.queryBySettingCodeAndParse(poHeader.getTenantId(), "010203", Integer::parseInt);
            if (Flag.YES.equals(tieredPricingSetting)) {
                poLine.setPriceLibraryId(poLineDetailDTO.getPriceLibraryId());
                if (null != poLineDetailDTO.getLadderInquiryFlag() && poLineDetailDTO.getLadderInquiryFlag().equals(Flag.YES)) {
                    ItemListDTO ladderPrice = poHeaderRepository.queryTieredPricing(poHeader.getTenantId(), poLineDetailDTO.getPriceLibraryId(), poLineDetailDTO.getQuantity());
                    if (!Objects.isNull(ladderPrice)) {
                        if (ladderPrice.getLadderPrice() != null) {
                            poLine.setUnitPriceBatch(ladderPrice.getPriceBatchQuantity());
                            poLine.setEnteredTaxIncludedPrice(this.calcNoTaxAmount(poLineDetailDTO.getTaxRate(), ladderPrice.getLadderPrice()));
                            poLine.setUnitPrice(ladderPrice.getLadderPrice());
                            poLine.setTaxIncludedLineAmount(poLine.getEnteredTaxIncludedPrice().multiply(poLine.getQuantity()).divide((BigDecimal)Optional.ofNullable(poLine.getUnitPriceBatch()).orElse(BigDecimal.ONE)));
                            poLine.setLineAmount(ladderPrice.getLadderPrice().multiply(poLine.getQuantity()).divide((BigDecimal)Optional.ofNullable(poLine.getUnitPriceBatch()).orElse(BigDecimal.ONE)));
                        } else {
                            poLine.setUnitPriceBatch(ladderPrice.getPriceBatchQuantity());
                            poLine.setEnteredTaxIncludedPrice(this.calcNoTaxAmount(poLineDetailDTO.getTaxRate(), ladderPrice.getUnitPrice()));
                            poLine.setUnitPrice(ladderPrice.getUnitPrice());
                            poLine.setTaxIncludedLineAmount(poLine.getEnteredTaxIncludedPrice().multiply(poLine.getQuantity()).divide((BigDecimal)Optional.ofNullable(poLine.getUnitPriceBatch()).orElse(BigDecimal.ONE)));
                            poLine.setLineAmount(ladderPrice.getUnitPrice().multiply(poLine.getQuantity()).divide((BigDecimal)Optional.ofNullable(poLine.getUnitPriceBatch()).orElse(BigDecimal.ONE)));
                        }
                    }
                }
            }
        }

    }

    private void initDataItemPriceLibrary(CustomizeSettingHelper customizeSettingHelper, PoHeaderRepository poHeaderRepository, List<PoLineDetailDTO> poLineDetailDTOs, PoHeader poHeader) {
        Integer tieredPricingSetting = (Integer)customizeSettingHelper.queryBySettingCodeAndParse(poHeader.getTenantId(), "010203", Integer::parseInt);
        if (Flag.YES.equals(tieredPricingSetting) && !"SRM".equals(poHeader.getPoSourcePlatform()) && !"CATALOGUE".equals(poHeader.getPoSourcePlatform()) && !"ERP".equals(poHeader.getPoSourcePlatform()) && !"E-COMMERCE".equals(poHeader.getPoSourcePlatform()) && !"SHOP".equals(poHeader.getPoSourcePlatform())) {
            List<Long> itemIds = new ArrayList();
            List<BigDecimal> quantitys = new ArrayList();
            poLineDetailDTOs.forEach((poLineDetailDTO) -> {
                itemIds.add(poLineDetailDTO.getItemId());
                quantitys.add(poLineDetailDTO.getQuantity());
            });
            List<ItemListDTO> itemListDTOS = poHeaderRepository.selectItemPriceLibrary(poHeader.getTenantId(), poHeader.getSupplierCompanyId(), poHeader.getOuId(), poHeader.getCompanyId(), itemIds, quantitys);
            if (itemListDTOS.isEmpty()) {
                throw new CommonException("error.Price.database.data.is.empty.please.maintain", new Object[0]);
            }

            this.initData(itemListDTOS, poLineDetailDTOs);
        }

    }

    public void initPoPriceLibraryWhenPr(CustomizeSettingHelper customizeSettingHelper, PoHeaderRepository poHeaderRepository, List<PoLineDetailDTO> poLineDetailDTOs, PoHeader poHeader) {
        Integer tieredPricingSetting = (Integer)customizeSettingHelper.queryBySettingCodeAndParse(poHeader.getTenantId(), "010203", Integer::parseInt);
        if (Flag.YES.equals(tieredPricingSetting) && poHeader.isByErpOrSrmPr()) {
            List<Long> itemIds = new ArrayList();
            List<BigDecimal> quantitys = new ArrayList();
            poLineDetailDTOs.stream().filter((x) -> {
                return x.getItemId() != null;
            }).forEach((poLineDetailDTO) -> {
                itemIds.add(poLineDetailDTO.getItemId());
                quantitys.add(poLineDetailDTO.getQuantity());
            });
            List<ItemListDTO> itemListDTOS = poHeaderRepository.selectItemPriceLibrary(poHeader.getTenantId(), poHeader.getSupplierCompanyId(), poHeader.getOuId(), poHeader.getCompanyId(), itemIds, quantitys);
            if (!itemListDTOS.isEmpty()) {
                this.initData(itemListDTOS, poLineDetailDTOs);
                poLineDetailDTOs.forEach((x) -> {
                    if (x.getPriceLibraryId() != null) {
                        x.setEnteredTaxIncludedPrice(this.calcNoTaxAmount(x.getTaxRate(), x.getUnitPrice()));
                        x.setLineAmount(((BigDecimal)Optional.of(x.getQuantity()).orElse(BigDecimal.ZERO)).multiply((BigDecimal)Optional.ofNullable(x.getUnitPrice()).orElse(BigDecimal.ZERO)));
                        x.setTaxIncludedLineAmount(x.getQuantity().multiply(x.getEnteredTaxIncludedPrice()));
                    }

                });
                poHeader.setAmount((BigDecimal)poLineDetailDTOs.stream().filter((x) -> {
                    return x.getLineAmount() != null;
                }).map(PoLineDetailDTO::getLineAmount).reduce(BigDecimal.ZERO, BigDecimal::add));
                poHeader.setTaxIncludeAmount((BigDecimal)poLineDetailDTOs.stream().filter((x) -> {
                    return x.getTaxIncludedLineAmount() != null;
                }).map(PoLineDetailDTO::getTaxIncludedLineAmount).reduce(BigDecimal.ZERO, BigDecimal::add));
            }
        }

    }

    private void initData(List<ItemListDTO> itemListDTOS, List<PoLineDetailDTO> poLineDetailDTOs) {
        poLineDetailDTOs.stream().filter((item) -> {
            return Objects.nonNull(item.getItemId());
        }).forEach((poLineDetailDTO) -> {
            itemListDTOS.forEach((itemListDTO) -> {
                if (poLineDetailDTO.getItemId().equals(itemListDTO.getItemId())) {
                    if (Flag.YES.equals(itemListDTO.getLadderInquiryFlag()) && itemListDTO.getLadderFrom() != null) {
                        if (itemListDTO.getLadderTo() == null) {
                            if (itemListDTO.getLadderFrom().compareTo(poLineDetailDTO.getQuantity()) == 1) {
                                poLineDetailDTO.setEnteredTaxIncludedPrice(this.calcNoTaxAmount(poLineDetailDTO.getTaxRate(), itemListDTO.getLadderPrice()));
                                poLineDetailDTO.setUnitPrice(itemListDTO.getLadderPrice());
                            }

                            poLineDetailDTO.setEnteredTaxIncludedPrice(this.calcNoTaxAmount(itemListDTO.getTaxRate(), itemListDTO.getUnitPrice()));
                        } else {
                            if ((itemListDTO.getLadderFrom().compareTo(poLineDetailDTO.getQuantity()) == -1 || itemListDTO.getLadderFrom().compareTo(poLineDetailDTO.getQuantity()) == 0) && itemListDTO.getLadderTo().compareTo(poLineDetailDTO.getQuantity()) == 1) {
                                poLineDetailDTO.setEnteredTaxIncludedPrice(this.calcNoTaxAmount(poLineDetailDTO.getTaxRate(), itemListDTO.getLadderPrice()));
                                poLineDetailDTO.setUnitPrice(itemListDTO.getLadderPrice());
                            }

                            poLineDetailDTO.setEnteredTaxIncludedPrice(this.calcNoTaxAmount(itemListDTO.getTaxRate(), itemListDTO.getUnitPrice()));
                        }
                    } else {
                        poLineDetailDTO.setEnteredTaxIncludedPrice(this.calcNoTaxAmount(itemListDTO.getTaxRate(), itemListDTO.getUnitPrice()));
                    }

                    poLineDetailDTO.setPriceLibraryId(itemListDTO.getPriceLibraryId());
                    poLineDetailDTO.setTaxRate(itemListDTO.getTaxRate());
                    poLineDetailDTO.setCurrencyName(itemListDTO.getCurrencyName());
                    poLineDetailDTO.setCurrencyCode(itemListDTO.getCurrencyCode());
                    poLineDetailDTO.setTaxId(itemListDTO.getTaxId());
                    poLineDetailDTO.setCategoryId(itemListDTO.getCategoryId());
                    poLineDetailDTO.setCategoryName(itemListDTO.getCategoryName());
                }

            });
        });
    }

    public BigDecimal calcNoTaxAmount(BigDecimal taxRate, BigDecimal taxAmount) {
        if (taxRate != null && taxAmount != null && !BigDecimal.ZERO.equals(taxRate) && !BigDecimal.ZERO.equals(taxAmount)) {
            taxRate = BigDecimal.ONE.add(taxRate.divide(BigDecimal.valueOf(100L), 2, RoundingMode.HALF_UP));
            return taxAmount.multiply(taxRate);
        } else {
            return taxAmount;
        }
    }

    public PoHeaderDetailDTO getPoHeaderDetailDTO() {
        return this.poHeaderDetailDTO;
    }

    public void setPoHeaderDetailDTO(PoHeaderDetailDTO poHeaderDetailDTO) {
        this.poHeaderDetailDTO = poHeaderDetailDTO;
    }

    public List<PoLineBasicDetailDTO> getPoLineBasicDetailDTOs() {
        return this.poLineBasicDetailDTOs;
    }

    public void setPoLineBasicDetailDTOs(List<PoLineBasicDetailDTO> poLineBasicDetailDTOs) {
        this.poLineBasicDetailDTOs = poLineBasicDetailDTOs;
    }

    public List<PoLineOtherDetailDTO> getPoLineOtherDetailDTOs() {
        return this.poLineOtherDetailDTOs;
    }

    public void setPoLineOtherDetailDTOs(List<PoLineOtherDetailDTO> poLineOtherDetailDTOs) {
        this.poLineOtherDetailDTOs = poLineOtherDetailDTOs;
    }

    public List<PoLineDetailDTO> getPoLineDetailDTOs() {
        return this.poLineDetailDTOs;
    }

    public void setPoLineDetailDTOs(List<PoLineDetailDTO> poLineDetailDTOs) {
        this.poLineDetailDTOs = poLineDetailDTOs;
    }

    public Integer getFirstFlag() {
        return this.firstFlag;
    }

    public void setFirstFlag(Integer firstFlag) {
        this.firstFlag = firstFlag;
    }

    public Integer getUpdatePriceLibFlag() {
        return this.updatePriceLibFlag;
    }

    public void setUpdatePriceLibFlag(Integer updatePriceLibFlag) {
        this.updatePriceLibFlag = updatePriceLibFlag;
    }

    public void setMdmService(MdmService mdmService) {
        this.mdmService = mdmService;
    }

    public List<PoLine> getPoLineMysql() {
        return this.poLineMysql;
    }

    public void setPoLineMysql(List<PoLine> poLineMysql) {
        this.poLineMysql = poLineMysql;
    }

    public String toString() {
        return "PoOrderSaveDTO{poHeaderDetailDTO=" + this.poHeaderDetailDTO + ", poLineBasicDetailDTOs=" + this.poLineBasicDetailDTOs + ", poLineOtherDetailDTOs=" + this.poLineOtherDetailDTOs + ", poLineDetailDTOs=" + this.poLineDetailDTOs + '}';
    }

    public void initPoLineAndPoHeaderNewPrice(CustomizeSettingHelper customizeSettingHelper, PoHeaderRepository poHeaderRepository, PoHeader poHeader, List<PoLine> poUpdateLineList, List<PoLine> poInsertLineList, List<PoLineLocation> poUpdateLineLocationList, PrLineRepository prLineRepository) {
        BeanUtils.copyProperties(this.poHeaderDetailDTO, poHeader);
        SmdmCurrencyDTO currencyDTO = null;
        int defaultPrecision = 10;
        int financialPrecision = defaultPrecision;
        if (CollectionUtils.isNotEmpty(this.poLineDetailDTOs)) {
            Iterator var11 = this.poLineDetailDTOs.iterator();

            while(var11.hasNext()) {
                PoLineDetailDTO p = (PoLineDetailDTO)var11.next();
                if (Objects.nonNull(this.mdmService)) {
                    currencyDTO = Objects.nonNull(currencyDTO) && Objects.equals(currencyDTO.getCurrencyCode(), p.getCurrencyCode()) ? currencyDTO : this.mdmService.selectSmdmCurrencyDto(poHeader.getTenantId(), p.getCurrencyCode());
                    defaultPrecision = currencyDTO.getDefaultPrecision();
                    financialPrecision = currencyDTO.getFinancialPrecision();
                }

                p.setUnitPrice(Objects.isNull(p.getUnitPrice()) ? null : p.getUnitPrice().setScale(defaultPrecision, RoundingMode.HALF_UP));
                p.setEnteredTaxIncludedPrice(Objects.isNull(p.getEnteredTaxIncludedPrice()) ? null : p.getEnteredTaxIncludedPrice().setScale(defaultPrecision, RoundingMode.HALF_UP));
                boolean updateFlag = p.getPoLineId() != null && p.getPoLineId() != 0L;
                PoLine poLine = new PoLine();
                BeanUtils.copyProperties(p, poLine);
                poLine.setObjectVersionNumber(p.getObjectVersionNumber());
                poLine.setPoLineId(p.getPoLineId());
                poLine.setLineNum(Objects.isNull(p.getLineNum()) ? null : (long)p.getLineNum());
                poLine.setDisplayLineNum(p.getLineNum() == null ? null : String.valueOf(p.getLineNum()));
                poLine.setItemId(p.getItemId());
                poLine.setItemCode(p.getItemCode());
                poLine.setItemName(p.getItemName());
                poLine.setCategoryId(p.getCategoryId());
                poLine.setUomId(p.getUomId());
                poLine.setTaxId(p.getTaxId());
                poLine.setTaxRate(p.getTaxRate());
                poLine.setEnteredTaxIncludedPrice(p.getEnteredTaxIncludedPrice());
                poLine.setUnitPrice(p.getUnitPrice());
                poLine.setCurrencyCode(p.getCurrencyCode());
                poLine.setBrand(p.getBrand());
                poLine.setSpecifications(p.getSpecifications());
                poLine.setModel(p.getModel());
                poLine.setDisplayPrNum(p.getDisplayPrNum());
                poLine.setDisplayPrLineNum(p.getDisplayPrLineNum());
                poLine.setPrRequestedBy(p.getPrRequestedBy());
                poLine.setPrRequestedName(p.getPrRequestedName());
                poLine.setProductId(p.getProductId());
                poLine.setProductNum(p.getProductNum());
                poLine.setProductName(p.getProductName());
                poLine.setCatalogId(p.getCatalogId());
                poLine.setCatalogName(p.getCatalogName());
                poLine.setPriceLibraryId(p.getPriceLibraryId());
                poLine.setRemark(p.getRemark());
                poLine.setHoldPcLineId(p.getHoldPcLineId());
                poLine.setHoldPcHeaderId(p.getHoldPcHeaderId());
                poLine.setPriceTaxId(p.getPriceTaxId());
                poLine.setPriceContractFlag(p.getPriceContractFlag());
                BigDecimal taxNotIncludePrice = p.getUnitPrice();
                if (p.getTaxId() != null) {
                    if ("NET_PRICE".equals(p.getBenchmarkPriceType())) {
                        taxNotIncludePrice = p.getUnitPrice();
                        taxNotIncludePrice = p.getUnitPrice();
                        p.setEnteredTaxIncludedPrice(taxNotIncludePrice.multiply(BigDecimal.ONE.add(p.getTaxRate().divide(new BigDecimal("100")))).setScale(defaultPrecision, RoundingMode.HALF_UP));
                        poLine.setEnteredTaxIncludedPrice(p.getEnteredTaxIncludedPrice());
                        poLine.setBenchmarkPriceType("NET_PRICE");
                    } else {
                        taxNotIncludePrice = p.getEnteredTaxIncludedPrice().divide(BigDecimal.ONE.add(p.getTaxRate().divide(new BigDecimal("100"))), defaultPrecision, RoundingMode.HALF_UP);
                        poLine.setUnitPrice(taxNotIncludePrice);
                        poLine.setBenchmarkPriceType("TAX_INCLUDED_PRICE");
                    }
                } else if ("NET_PRICE".equals(p.getBenchmarkPriceType())) {
                    taxNotIncludePrice = p.getUnitPrice();
                } else {
                    taxNotIncludePrice = p.getEnteredTaxIncludedPrice();
                }

                poLine.setQuantity(p.getQuantity());
                poLine.setUnitPriceBatch(p.getUnitPriceBatch());
                if (p.getEnteredTaxIncludedPrice() != null) {
                    poLine.setTaxIncludedLineAmount(p.getEnteredTaxIncludedPrice().multiply(p.getQuantity()).divide((BigDecimal)Optional.ofNullable(p.getUnitPriceBatch()).orElse(BigDecimal.ONE), financialPrecision, RoundingMode.HALF_UP));
                    poLine.setLineAmount(taxNotIncludePrice.multiply(p.getQuantity()).divide((BigDecimal)Optional.ofNullable(p.getUnitPriceBatch()).orElse(BigDecimal.ONE), financialPrecision, RoundingMode.HALF_UP));
                }

                poLine.setPrLineId(p.getPrLineId());
                poLine.setPrHeaderId(p.getPrHeaderId());
                poLine.setDemandUnitId(p.getUnitId());
                poLine.setLadderInquiryFlag(p.getLadderInquiryFlag());
                this.handWorkTieredPricing(customizeSettingHelper, poHeader, poLine, p, poHeaderRepository);
                poLine.setLastPurchasePrice(p.getLastPurchasePrice());
                poLine.setDepartmentId(p.getDepartmentId());
                poLine.setClearOrganizationId(p.getClearOrganizationId());
                poLine.setCopeOrganizationId(p.getCopeOrganizationId());
                poLine.setChartCode(p.getChartCode());
                poLine.setChartVersion(p.getChartVersion());
                poLine.setSurfaceTreatFlag(p.getSurfaceTreatFlag());
                poLine.setPcNum(p.getPcNum());
                poLine.setJdPrice(p.getJdPrice());
                poLine.setSupplierItemNum(p.getSupplierItemNum());
                poLine.setProductionOrderNum(p.getProductionOrderNum());
                poLine.setAccountAssignTypeId(p.getAccountAssignTypeId());
                poLine.setCostId(p.getCostId());
                poLine.setCostCode(p.getCostCode());
                poLine.setAccountSubjectId(p.getAccountSubjectId());
                poLine.setAccountSubjectNum(p.getAccountSubjectNum());
                poLine.setWbsCode(p.getWbsCode());
                poLine.setWbs(p.getWbs());
                poLine.setProjectCategory(p.getProjectCategory());
                poLine.setAttachmentUuid(p.getAttachmentUuid());
                if (FreeFlag.YES.equals(poLine.getFreeFlag())) {
                    poLine.setTaxIncludedLineAmount(new BigDecimal("0"));
                    poLine.setLineAmount(new BigDecimal("0"));
                    poLine.setUnitPrice(new BigDecimal("0"));
                    poLine.setEnteredTaxIncludedPrice(new BigDecimal("0"));
                }

                if ("CNY".equals(p.getCurrencyCode())) {
                    poLine.setRate(new BigDecimal("1"));
                } else {
                    poLine.setRate(new BigDecimal("0"));
                }

                PoLineLocation poLineLocation = new PoLineLocation();
                BeanUtils.copyProperties(p, poLineLocation);
                poLineLocation.setPoLineLocationId(p.getPoLineLocationId());
                poLineLocation.setPoLineId(p.getPoLineId());
                poLineLocation.setQuantity(p.getQuantity());
                poLineLocation.setInvOrganizationId(p.getInvOrganizationId());
                poLineLocation.setInvInventoryId(p.getInvInventoryId());
                poLineLocation.setInvLocationId(p.getInvLocationId());
                poLineLocation.setShipToThirdPartyCode(p.getShipToThirdPartyCode());
                poLineLocation.setShipToThirdPartyName(p.getShipToThirdPartyName());
                poLineLocation.setShipToThirdPartyAddress(p.getShipToThirdPartyAddress());
                poLineLocation.setShipToThirdPartyContact(p.getShipToThirdPartyContact());
                poLineLocation.setRemark(p.getRemark());
                poLineLocation.setNeedByDate(p.getNeedByDate());
                poLineLocation.setPromiseDeliveryDate(p.getPromiseDeliveryDate());
                poLineLocation.setDeleteFlag(p.getPoLineLocationDeleteFlag());
                if (updateFlag) {
                    p.validPoNewPrice(prLineRepository);
                    poLine.setPoLineLocationList(Collections.singletonList(poLineLocation));
                    poLine.setObjectVersionNumber(p.getLineVersionNumber());
                    poUpdateLineList.add(poLine);
                    poLineLocation.setObjectVersionNumber(p.getLocationVersionNumber());
                    poUpdateLineLocationList.add(poLineLocation);
                } else if (!Flag.YES.equals(p.getPoLineLocationDeleteFlag())) {
                    p.validPoNewPrice(prLineRepository);
                    poLine.setPrHeaderId(p.getPrHeaderId());
                    poLine.setPrLineId(p.getPrLineId());
                    poLine.setHoldPcHeaderId(p.getHoldPcHeaderId());
                    poLine.setHoldPcLineId(p.getHoldPcLineId());
                    poLine.setPoLineLocationList(Collections.singletonList(poLineLocation));
                    poLine.setInsertFlag(Flag.YES);
                    poLineLocation.setInsertFlag(Flag.YES);
                    poInsertLineList.add(poLine);
                }
            }
        }

    }

    public void validNeewByDate(PoLineLocationRepository poLineLocationRepository) {
        Iterator var2 = this.poLineDetailDTOs.iterator();

        PoLineDetailDTO poLineDetailDTO;
        do {
            if (!var2.hasNext()) {
                List<PoLineLocation> poLineLocationList = poLineLocationRepository.select("poHeaderId", this.getPoHeaderDetailDTO().getPoHeaderId());
                List<Long> poLineLocationIds = (List)this.poLineDetailDTOs.stream().map((n) -> {
                    return n.getPoLineLocationId();
                }).collect(Collectors.toList());
                List<PoLineLocation> otherPoLineLocationList = (List)poLineLocationList.stream().filter((n) -> {
                    return !poLineLocationIds.contains(n.getPoLineLocationId());
                }).collect(Collectors.toList());
                Iterator var5 = otherPoLineLocationList.iterator();

                PoLineLocation poLineLocation;
                do {
                    if (!var5.hasNext()) {
                        return;
                    }

                    poLineLocation = (PoLineLocation)var5.next();
                } while(!Objects.isNull(poLineLocation.getNeedByDate()));

                throw new CommonException("error.po.line.need-by-date.not.null", new Object[0]);
            }

            poLineDetailDTO = (PoLineDetailDTO)var2.next();
        } while(!Objects.isNull(poLineDetailDTO.getNeedByDate()));

        throw new CommonException("error.po.line.need-by-date.not.null", new Object[0]);
    }

    public Long getSettleSupplierId() {
        return this.settleSupplierId;
    }

    public void setSettleSupplierId(Long settleSupplierId) {
        this.settleSupplierId = settleSupplierId;
    }

    public String getSettleSupplierCode() {
        return this.settleSupplierCode;
    }

    public void setSettleSupplierCode(String settleSupplierCode) {
        this.settleSupplierCode = settleSupplierCode;
    }

    public String getSettleSupplierName() {
        return this.settleSupplierName;
    }

    public void setSettleSupplierName(String settleSupplierName) {
        this.settleSupplierName = settleSupplierName;
    }

    public Long getSettleSupplierTenantId() {
        return this.settleSupplierTenantId;
    }

    public void setSettleSupplierTenantId(Long settleSupplierTenantId) {
        this.settleSupplierTenantId = settleSupplierTenantId;
    }

    public Long getSettleErpSupplierId() {
        return this.settleErpSupplierId;
    }

    public void setSettleErpSupplierId(Long settleErpSupplierId) {
        this.settleErpSupplierId = settleErpSupplierId;
    }

    public String getSettleErpSupplierCode() {
        return this.settleErpSupplierCode;
    }

    public void setSettleErpSupplierCode(String settleErpSupplierCode) {
        this.settleErpSupplierCode = settleErpSupplierCode;
    }

    public String getSettleErpSupplierName() {
        return this.settleErpSupplierName;
    }

    public void setSettleErpSupplierName(String settleErpSupplierName) {
        this.settleErpSupplierName = settleErpSupplierName;
    }
}
