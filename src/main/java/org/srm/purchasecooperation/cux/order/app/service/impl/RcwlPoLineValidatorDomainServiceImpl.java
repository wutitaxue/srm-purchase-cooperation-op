package org.srm.purchasecooperation.cux.order.app.service.impl;

import io.choerodon.core.oauth.DetailsHelper;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import org.apache.commons.collections.CollectionUtils;
import org.hzero.boot.imported.app.service.ValidatorHandler;
import org.hzero.boot.imported.domain.entity.ImportData;
import org.hzero.core.message.MessageAccessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.srm.boot.platform.configcenter.CnfHelper;
import org.srm.purchasecooperation.cux.order.util.TennantValue;
import org.srm.purchasecooperation.order.api.dto.ItemDTO;
import org.srm.purchasecooperation.order.api.dto.OrgnizationDTO;
import org.srm.purchasecooperation.order.api.dto.PoImportDTO;
import org.srm.purchasecooperation.order.api.dto.PoLineDetailDTO;
import org.srm.purchasecooperation.order.domain.repository.PoLineRepository;
import org.srm.purchasecooperation.order.domain.service.PoLineValidatorDomainService;
import org.srm.purchasecooperation.order.domain.service.impl.PoLineValidatorDomainServiceImpl;
import org.srm.purchasecooperation.order.infra.mapper.PoImportMapper;
import org.srm.purchasecooperation.pr.domain.vo.PrLineImportVO;
import org.srm.web.annotation.Tenant;

@Component
@Tenant(TennantValue.tenantV)
public class RcwlPoLineValidatorDomainServiceImpl extends PoLineValidatorDomainServiceImpl {
    private static final Logger LOGGER = LoggerFactory.getLogger(RcwlPoLineValidatorDomainServiceImpl.class);

    @Autowired
    private PoLineRepository poLineRepository;

    @Autowired
    private PoImportMapper poImportMapper;

    public boolean importValidator(PoLineDetailDTO poLineDetailDTO, ImportData importData) {
        if (Objects.isNull(poLineDetailDTO.getItemCode()) || "".equals(poLineDetailDTO.getItemCode())) {
            if (Objects.isNull(poLineDetailDTO.getItemName()) || "".equals(poLineDetailDTO.getItemName())) {
                importData.addErrorMsg(MessageAccessor.getMessage("error.po.line.import_item_code_name_error").desc());
                return false;
            }
            if (Objects.isNull(poLineDetailDTO.getUomCode()) || "".equals(poLineDetailDTO.getUomCode())) {
                importData.addErrorMsg(MessageAccessor.getMessage("error.po.line.import_uom_error").desc());
                return false;
            }
            PoImportDTO poImportDTO = new PoImportDTO();
            poImportDTO.setTenantId(poLineDetailDTO.getTenantId());
            poImportDTO.setUomCode(poLineDetailDTO.getUomCode());
            List<PoImportDTO> poImportDTOList = this.poImportMapper.queryUomInfo(poImportDTO);
            if (CollectionUtils.isEmpty(poImportDTOList) || Objects.isNull(poImportDTOList.get(0))) {
                importData.addErrorMsg(MessageAccessor.getMessage("error.po.line.import_uom_error").desc());
                return false;
            }
        } else {
            ItemDTO itemDTO = this.poLineRepository.queryItemByCode(poLineDetailDTO.getTenantId(), poLineDetailDTO.getItemCode());
            if (itemDTO == null) {
                importData.addErrorMsg(MessageAccessor.getMessage("error.po.line.import_item_code_error").desc());
                return false;
            }
            List<String> list = new ArrayList<>();
            list.add("是");
            list.add("否");
            if (poLineDetailDTO.getSurfaceFlag() != null && !list.contains(poLineDetailDTO.getSurfaceFlag())) {
                importData.addErrorMsg(MessageAccessor.getMessage("error.po.line.import_surface_error").desc());
                return false;
            }
            poLineDetailDTO.setBrand(itemDTO.getBrand());
            poLineDetailDTO.setSpecifications(itemDTO.getSpecifications());
            poLineDetailDTO.setModel(itemDTO.getModel());
            if (poLineDetailDTO.getItemName() == null)
                poLineDetailDTO.setItemName(itemDTO.getItemName());
            poLineDetailDTO.setItemName(itemDTO.getItemName());
            List<ItemDTO> itemList = this.poLineRepository.listCategoryAssign(itemDTO);
            if (itemList != null && itemList.size() != 0) {
                if (itemList.size() == 1) {
                    if (poLineDetailDTO.getCategoryCode() != null && !poLineDetailDTO.getCategoryCode().equals(((ItemDTO)itemList.get(0)).getCategoryCode())) {
                        importData.addErrorMsg(MessageAccessor.getMessage("error.po.line.import_category_error").desc());
                        return false;
                    }
                } else {
                    int categoryFlag = 0;
                    for (ItemDTO itemDTO1 : itemList) {
                        if (itemDTO1.getCategoryCode().equals(poLineDetailDTO.getCategoryCode()))
                            categoryFlag = 1;
                    }
                    if (poLineDetailDTO.getCategoryCode() == null)
                        categoryFlag = 1;
                    if (categoryFlag == 0) {
                        importData.addErrorMsg(MessageAccessor.getMessage("error.po.line.import_category_error").desc());
                        return false;
                    }
                }
            } else if (poLineDetailDTO.getCategoryCode() != null) {
                Long catCount = this.poLineRepository.listCategory(poLineDetailDTO.getTenantId(), poLineDetailDTO.getCategoryCode());
                if (catCount == null) {
                    importData.addErrorMsg(MessageAccessor.getMessage("error.po.line.import_category_error").desc());
                    return false;
                }
            }
            if (itemDTO.getUomCode() == null) {
                if (poLineDetailDTO.getUomCode() != null) {
                    poLineDetailDTO.setUomCode(poLineDetailDTO.getUomCode());
                } else {
                    importData.addErrorMsg(MessageAccessor.getMessage("error.po.line.import_uom_error").desc());
                    return false;
                }
            } else {
                if (poLineDetailDTO.getUomCode() == null) {
                    poLineDetailDTO.setUomCode(itemDTO.getUomCode());
                    poLineDetailDTO.setUomId(itemDTO.getUomId());
                }
                if (!poLineDetailDTO.getUomCode().equals(itemDTO.getUomCode())) {
                    importData.addErrorMsg(MessageAccessor.getMessage("error.po.line.import_uom_error").desc());
                    return false;
                }
            }
        }
        int countCurrency = 0;
        countCurrency = this.poLineRepository.queryCurrency(poLineDetailDTO.getTenantId(), poLineDetailDTO.getCurrencyCode()).intValue();
        if (countCurrency == 0) {
            importData.addErrorMsg(MessageAccessor.getMessage("error.po.line.import_currency_error").desc());
            return false;
        }
        int countTax = 0;
        countTax = this.poLineRepository.queryTax(poLineDetailDTO.getTenantId(), poLineDetailDTO.getTaxCode()).intValue();
        if (countTax == 0) {
            importData.addErrorMsg(MessageAccessor.getMessage("error.po.line.import_tax_error").desc());
            return false;
        }
        if (poLineDetailDTO.getUnitCode() != null) {
            Long countUnit = this.poLineRepository.queryUnit(poLineDetailDTO.getTenantId(), poLineDetailDTO.getUnitCode());
            if (countUnit == null) {
                importData.addErrorMsg(MessageAccessor.getMessage("error.po.line.import_unit_error").desc());
                return false;
            }
        }
        if (poLineDetailDTO.getClearOrganizationCode() != null) {
            Long orgnization1 = this.poLineRepository.queryClearOrg(poLineDetailDTO.getTenantId(), poLineDetailDTO.getClearOrganizationCode());
            if (orgnization1 == null) {
                importData.addErrorMsg(MessageAccessor.getMessage("error.po.line.import_clear_error").desc());
                return false;
            }
        }
        if (poLineDetailDTO.getCopeOrganizationCode() != null) {
            Long orgnization2 = this.poLineRepository.queryClearOrg(poLineDetailDTO.getTenantId(), poLineDetailDTO.getCopeOrganizationCode());
            if (orgnization2 == null) {
                importData.addErrorMsg(MessageAccessor.getMessage("error.po.line.import_cope_error").desc());
                return false;
            }
        }
        OrgnizationDTO orgnizationDTO3 = new OrgnizationDTO();
        orgnizationDTO3.setTenantId(poLineDetailDTO.getTenantId());
        orgnizationDTO3.setOrganizationCode(poLineDetailDTO.getInvOrganizationCode());
        List<OrgnizationDTO> orgnization3 = this.poLineRepository.queryOrg(orgnizationDTO3);
        if (orgnization3 == null || orgnization3.size() == 0) {
            importData.addErrorMsg(MessageAccessor.getMessage("error.po.line.import_org_error").desc());
            return false;
        }
        poLineDetailDTO.setInvOrganizationId(((OrgnizationDTO)orgnization3.get(0)).getOrganizationId());
        int invent = 0;
        OrgnizationDTO orgnizationDTO4 = new OrgnizationDTO();
        orgnizationDTO4.setTenantId(poLineDetailDTO.getTenantId());
        orgnizationDTO4.setOrganizationId(poLineDetailDTO.getInvOrganizationId());
        List<OrgnizationDTO> orgnization4 = this.poLineRepository.queryInvent(orgnizationDTO4);
        for (OrgnizationDTO org : orgnization4) {
            if (org.getInventoryCode().equals(poLineDetailDTO.getInvInventoryCode())) {
                invent = 1;
                poLineDetailDTO.setInvInventoryId(org.getInventoryId());
            }
        }
        if (poLineDetailDTO.getInvInventoryCode() == null)
            invent = 1;
        if (invent == 0) {
            importData.addErrorMsg(MessageAccessor.getMessage("error.po.line.import_item_invent_error").desc());
            return false;
        }
        if (poLineDetailDTO.getInvInventoryCode() == null && poLineDetailDTO.getInvLocationCode() != null) {
            importData.addErrorMsg(MessageAccessor.getMessage("error.po.line.import_location_invent_error").desc());
            return false;
        }
        int location = 0;
        OrgnizationDTO orgnizationDTO5 = new OrgnizationDTO();
        orgnizationDTO5.setTenantId(poLineDetailDTO.getTenantId());
        orgnizationDTO5.setOrganizationId(((OrgnizationDTO)orgnization3.get(0)).getOrganizationId());
        orgnizationDTO5.setInventoryId(poLineDetailDTO.getInvInventoryId());
        List<OrgnizationDTO> orgnization5 = this.poLineRepository.queryLocation(orgnizationDTO5);
        for (OrgnizationDTO org : orgnization5) {
            if (org.getLocationCode().equals(poLineDetailDTO.getInvLocationCode())) {
                location = 1;
                poLineDetailDTO.setLocationId(org.getLocationId());
            }
        }
        if (poLineDetailDTO.getInvLocationCode() == null)
            location = 1;
        if (location == 0) {
            importData.addErrorMsg(MessageAccessor.getMessage("error.po.line.import_location_error").desc());
            return false;
        }
        if (poLineDetailDTO.getNeededDate() == null){
            return false;
        }
        if (poLineDetailDTO.getNeedStartDate() == null){
            return false;
        }else {
            PrLineImportVO prLineImportVO = new PrLineImportVO();
            poLineDetailDTO.setAttributeDate1(prLineImportVO.localDate2Date(poLineDetailDTO.getNeedStartDate()));
        }
//        if (poLineDetailDTO.getNeededDate().isBefore(LocalDate.now())) {
//            importData.addErrorMsg(MessageAccessor.getMessage("error.po.line.import_data_error").desc());
//            return false;
//        }
        if (poLineDetailDTO.getNeededDate().isBefore(poLineDetailDTO.getNeedStartDate())) {
            importData.addErrorMsg(MessageAccessor.getMessage("error.po.line.import_po_data_error").desc());
            return false;
        }
        if (poLineDetailDTO.getQuantity() == null)
            return false;
        if (poLineDetailDTO.getQuantity().compareTo(new BigDecimal(0)) <= 0) {
            importData.addErrorMsg(MessageAccessor.getMessage("error.po.line.import_quality_nub_error").desc());
            return false;
        }
        if (poLineDetailDTO.getEnteredTaxIncludedPrice() != null && poLineDetailDTO.getEnteredTaxIncludedPrice().compareTo(new BigDecimal(0)) <= 0) {
            importData.addErrorMsg(MessageAccessor.getMessage("error.po.line.import_quality_error").desc());
            return false;
        }
        try {
            String cnfResults = (String)CnfHelper.select(DetailsHelper.getUserDetails().getTenantId(), "SITE.SSRC.QUOTATION_SET", String.class).invokeWithParameter(Collections.emptyMap());
            LOGGER.debug("22875-Cnf conter returns: {}", cnfResults.toString());
            LOGGER.debug("24497_PoLineValidatorDomainServiceImpl_importValidator_poLineDetailDTO is:{}", poLineDetailDTO);
            if ("TAX_INCLUDED_PRICE".equals(cnfResults) && poLineDetailDTO.getEnteredTaxIncludedPrice() == null) {
                LOGGER.debug("24497_PoLineValidatorDomainServiceImpl_importValidator_EnteredTaxIncludedPrice is null");
                importData.addErrorMsg(MessageAccessor.getMessage("error.po.line.import_tax_unit_price_error").desc());
                return false;
            }
            if ("NET_PRICE".equals(cnfResults) && poLineDetailDTO.getUnitPrice() == null) {
                LOGGER.debug("24497_PoLineValidatorDomainServiceImpl_importValidator_UnitPrice is null");
                importData.addErrorMsg(MessageAccessor.getMessage("error.po.line.import_unit_price_error").desc());
                return false;
            }
        } catch (Exception e) {
            LOGGER.error("22875-fail to query config-center for exception:{}", e);
            return false;
        }
        return true;
    }

    public boolean validate(String data) {
        return false;
    }
}
