package org.srm.purchasecooperation.cux.order.app.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.choerodon.core.oauth.DetailsHelper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.srm.boot.platform.customizesetting.CustomizeSettingHelper;
import org.srm.purchasecooperation.cux.order.util.TennantValue;
import org.srm.purchasecooperation.order.api.dto.ItemDTO;
import org.srm.purchasecooperation.order.api.dto.OrgnizationDTO;
import org.srm.purchasecooperation.order.api.dto.PoHeaderDetailDTO;
import org.srm.purchasecooperation.order.api.dto.PoImportDTO;
import org.srm.purchasecooperation.order.api.dto.PoLineDetailDTO;
import org.srm.purchasecooperation.order.api.dto.PoOrderSaveDTO;
import org.srm.purchasecooperation.order.api.dto.PriceLibDTO;
import org.srm.purchasecooperation.order.app.service.PoHeaderService;
import org.srm.purchasecooperation.order.domain.entity.PoHeader;
import org.srm.purchasecooperation.order.domain.repository.PoHeaderRepository;
import org.srm.purchasecooperation.order.domain.repository.PoLineRepository;
import org.srm.purchasecooperation.order.domain.service.PoLineImportDomainService;
import org.srm.purchasecooperation.order.domain.service.impl.PoLineImportDomainServiceImpl;
import org.srm.purchasecooperation.order.infra.businessRules.OrderCnfHelper;
import org.srm.purchasecooperation.order.infra.mapper.PoImportMapper;
import org.srm.purchasecooperation.pr.domain.entity.PrLine;
import org.srm.purchasecooperation.pr.domain.vo.PrLineImportVO;
import org.srm.purchasecooperation.pr.infra.mapper.PrImportMapper;
import org.srm.web.annotation.Tenant;

@Component
@Tenant(TennantValue.tenantV)
public class RcwlPoLineImportDomainServiceImpl extends PoLineImportDomainServiceImpl {
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PrImportMapper prImportMapper;

    @Autowired
    private PoHeaderRepository poHeaderRepository;

    @Autowired
    private PoHeaderService poHeaderService;

    @Autowired
    private PoLineRepository poLineRepository;

    @Autowired
    private CustomizeSettingHelper customizeSettingHelper;

    @Autowired
    private PoImportMapper poImportMapper;

    @Autowired
    private OrderCnfHelper orderCnfHelper;

    public Boolean doImport(List<String> list, Long poHeaderId) {
        List<PoLineDetailDTO> poLineDetailDTOList;
        String voStr = "[" + StringUtils.join(list.toArray(), ",") + "]";
        try {
            poLineDetailDTOList = (List<PoLineDetailDTO>)this.objectMapper.readValue(voStr, this.objectMapper.getTypeFactory().constructParametricType(List.class, new Class[] { PoLineDetailDTO.class }));
        } catch (IOException e) {
            return Boolean.valueOf(false);
        }
        if (CollectionUtils.isEmpty(poLineDetailDTOList))
            return Boolean.valueOf(true);
        Long tenantId = DetailsHelper.getUserDetails().getTenantId();
        PoHeader poHeader = (PoHeader)this.poHeaderRepository.selectByPrimaryKey(poHeaderId);
        if (poHeader == null || !tenantId.equals(poHeader.getTenantId()))
            return Boolean.valueOf(false);
        PoOrderSaveDTO poOrderSave = convertPrLine(poHeader, poLineDetailDTOList);
        PoHeaderDetailDTO poHeaderDetailDTO = this.poHeaderRepository.selectHeaderdetail(poHeader.getTenantId(), poHeaderId);
        String benchPriceCnf = this.orderCnfHelper.getBenchPriceCnf(poHeaderDetailDTO);
        poOrderSave.getPoLineDetailDTOs().stream().forEach(poLineDetailDTO -> poLineDetailDTO.setBenchmarkPriceType(benchPriceCnf));
        this.poHeaderService.updatePoHeaderAndPoLineS(poOrderSave);
        return Boolean.valueOf(true);
    }

    public PoOrderSaveDTO convertPrLine(PoHeader poHeader, List<PoLineDetailDTO> poLineDetailDTOList) {
        new ArrayList();
        Long tenantId = poHeader.getTenantId();
        PoOrderSaveDTO poOrderSaveDTO = new PoOrderSaveDTO();
        Iterator var6 = poLineDetailDTOList.iterator();

        while(var6.hasNext()) {
            PoLineDetailDTO poLineDetailDTO = (PoLineDetailDTO)var6.next();
            poLineDetailDTO.setPoHeaderId(poHeader.getPoHeaderId());
            poLineDetailDTO.setTenantId(tenantId);
            poLineDetailDTO.setSupplierCompanyId(poHeader.getSupplierCompanyId());
            PrLineImportVO importVO = new PrLineImportVO();
            importVO.setTaxCode(poLineDetailDTO.getTaxCode());
            importVO.setTenantId(poLineDetailDTO.getTenantId());
            PrLine prLine = this.prImportMapper.queryTaxInfo(importVO);
            poLineDetailDTO.setTaxRate(prLine.getTaxRate());
            poLineDetailDTO.setTaxId(prLine.getTaxId());
            OrgnizationDTO orgnization = new OrgnizationDTO();
            orgnization.setTenantId(poLineDetailDTO.getTenantId());
            orgnization.setOrganizationCode(poLineDetailDTO.getInvOrganizationCode());
            List<OrgnizationDTO> orgnization3 = this.poLineRepository.queryOrg(orgnization);
            poLineDetailDTO.setInvOrganizationId(((OrgnizationDTO)orgnization3.get(0)).getOrganizationId());
            List itemList;
            if (Objects.nonNull(poLineDetailDTO.getItemCode())) {
                ItemDTO itemDTO = this.poLineRepository.queryItemByCode(poLineDetailDTO.getTenantId(), poLineDetailDTO.getItemCode());
                poLineDetailDTO.setUomId(itemDTO.getUomId());
                poLineDetailDTO.setUomCode(itemDTO.getUomCode());
                poLineDetailDTO.setItemName(itemDTO.getItemName());
                poLineDetailDTO.setItemId(itemDTO.getItemId());
                itemList = this.poLineRepository.listCategoryAssign(itemDTO);
                if (itemList != null && itemList.size() != 0 && itemList.size() == 1 && poLineDetailDTO.getCategoryCode() == null) {
                    poLineDetailDTO.setCategoryCode(((ItemDTO)itemList.get(0)).getCategoryCode());
                }
            } else {
                PoImportDTO poImportDTO = new PoImportDTO();
                poImportDTO.setTenantId(tenantId);
                poImportDTO.setUomCode(poLineDetailDTO.getUomCode());
                itemList = this.poImportMapper.queryUomInfo(poImportDTO);
                poLineDetailDTO.setUomId(((PoImportDTO)itemList.get(0)).getUomId());
                poLineDetailDTO.setUomName(((PoImportDTO)itemList.get(0)).getUomName());
            }

            Long categoryId = this.poLineRepository.listCategory(poLineDetailDTO.getTenantId(), poLineDetailDTO.getCategoryCode());
            poLineDetailDTO.setCategoryId(categoryId);
            PrLineImportVO prLineImportVO = new PrLineImportVO();
            poLineDetailDTO.setNeedByDate(prLineImportVO.localDate2Date(poLineDetailDTO.getNeededDate()));
            poLineDetailDTO.setAttributeDate1(prLineImportVO.localDate2Date(poLineDetailDTO.getNeedStartDate()));
            if (poLineDetailDTO.getSurfaceFlag() != null && poLineDetailDTO.getSurfaceFlag().equals("是")) {
                poLineDetailDTO.setSurfaceTreatFlag(1);
            } else {
                poLineDetailDTO.setSurfaceTreatFlag(0);
            }

            boolean tieredPricingSetting = Objects.equals(1, this.customizeSettingHelper.queryBySettingCodeAndParse(poLineDetailDTO.getTenantId(), "010203", Integer::parseInt));
            List orgnization5;
            if (tieredPricingSetting) {
                PriceLibDTO priceLibDTO = new PriceLibDTO();
                priceLibDTO.setCompanyId(poLineDetailDTO.getCompanyId());
                priceLibDTO.setSupplierCompanyId(poLineDetailDTO.getSupplierCompanyId());
                priceLibDTO.setItemId(poLineDetailDTO.getItemId());
                priceLibDTO.setOuId(poLineDetailDTO.getOuId());
                priceLibDTO.setInvOrganizationId(poLineDetailDTO.getInvOrganizationId());
                orgnization5 = this.poLineRepository.queryPrice(priceLibDTO);
                if (orgnization5 != null && orgnization5.size() != 0) {
                    Iterator var17 = orgnization5.iterator();

                    label112:
                    while(true) {
                        while(true) {
                            while(true) {
                                if (!var17.hasNext()) {
                                    break label112;
                                }

                                PriceLibDTO pri = (PriceLibDTO)var17.next();
                                if (pri.getLadderInquiryFlag() == 1) {
                                    if (pri.getLadderTo() != null && poLineDetailDTO.getQuantity().compareTo(pri.getLadderFrom()) > 0 && poLineDetailDTO.getQuantity().compareTo(pri.getLadderTo()) < 0) {
                                        poLineDetailDTO.setEnteredTaxIncludedPrice(pri.getLadderPrice());
                                    } else if (pri.getLadderTo() == null && poLineDetailDTO.getQuantity().compareTo(pri.getLadderTo()) < 0) {
                                        poLineDetailDTO.setEnteredTaxIncludedPrice(pri.getLadderPrice());
                                    }
                                } else {
                                    poLineDetailDTO.setEnteredTaxIncludedPrice(pri.getUnitPrice());
                                }
                            }
                        }
                    }
                }
            }

            Long copeId;
            if (poLineDetailDTO.getUnitCode() != null) {
                copeId = this.poLineRepository.queryUnit(poLineDetailDTO.getTenantId(), poLineDetailDTO.getUnitCode());
                if (copeId != null) {
                    poLineDetailDTO.setDepartmentId(copeId);
                }
            }

            if (poLineDetailDTO.getClearOrganizationCode() != null) {
                copeId = this.poLineRepository.queryClearOrg(poLineDetailDTO.getTenantId(), poLineDetailDTO.getClearOrganizationCode());
                if (copeId != null) {
                    poLineDetailDTO.setClearOrganizationId(copeId);
                }
            }

            if (poLineDetailDTO.getCopeOrganizationCode() != null) {
                copeId = this.poLineRepository.queryClearOrg(poLineDetailDTO.getTenantId(), poLineDetailDTO.getCopeOrganizationCode());
                if (copeId != null) {
                    poLineDetailDTO.setCopeOrganizationId(copeId);
                }
            }

            OrgnizationDTO orgnizationDTO5;
            if (poLineDetailDTO.getInvInventoryCode() != null) {
                orgnizationDTO5 = new OrgnizationDTO();
                orgnizationDTO5.setTenantId(poLineDetailDTO.getTenantId());
                orgnizationDTO5.setInventoryCode(poLineDetailDTO.getInvInventoryCode());
                orgnization5 = this.poLineRepository.queryInvent(orgnizationDTO5);
                if (orgnization5 != null && !orgnization5.isEmpty() && orgnization5.get(0) != null && ((OrgnizationDTO)orgnization5.get(0)).getInventoryId() != null) {
                    poLineDetailDTO.setInvInventoryId(((OrgnizationDTO)orgnization5.get(0)).getInventoryId());
                }
            }

            if (poLineDetailDTO.getInvLocationCode() != null) {
                orgnizationDTO5 = new OrgnizationDTO();
                orgnizationDTO5.setTenantId(poLineDetailDTO.getTenantId());
                orgnizationDTO5.setLocationCode(poLineDetailDTO.getInvLocationCode());
                orgnization5 = this.poLineRepository.queryLocation(orgnizationDTO5);
                if (orgnization5 != null && !orgnization5.isEmpty() && orgnization5.get(0) != null && ((OrgnizationDTO)orgnization5.get(0)).getLocationId() != null) {
                    poLineDetailDTO.setInvLocationId(((OrgnizationDTO)orgnization5.get(0)).getLocationId());
                }
            }
        }

        PoHeaderDetailDTO poHeaderDetailDTO = new PoHeaderDetailDTO();
        poHeaderDetailDTO.setTenantId(poHeader.getTenantId());
        poHeaderDetailDTO.setPoNum(poHeader.getPoNum());
        poHeaderDetailDTO.setVersionNum(poHeader.getVersionNum());
        poHeaderDetailDTO.setObjectVersionNumber(poHeader.getObjectVersionNumber());
        poHeaderDetailDTO.setPoTypeId(poHeader.getPoTypeId());
        poHeaderDetailDTO.setCompanyId(poHeader.getCompanyId());
        poHeaderDetailDTO.setPoHeaderId(poHeader.getPoHeaderId());
        poHeaderDetailDTO.setSupplierCompanyId(poHeader.getSupplierCompanyId());
        poHeaderDetailDTO.setSupplierCompanyName(poHeader.getSupplierCompanyName());
        poHeaderDetailDTO.setSourceBillTypeCode(poHeader.getSourceBillTypeCode());
        poHeaderDetailDTO.setOuId(poHeader.getOuId());
        poHeaderDetailDTO.setPurchaseOrgId(poHeader.getPurchaseOrgId());
        poHeaderDetailDTO.setAgentId(poHeader.getAgentId());
        poHeaderDetailDTO.setSupplierTenantId(poHeader.getSupplierTenantId());
        poHeaderDetailDTO.setCurrencyCode(poHeader.getCurrencyCode());
        poHeaderDetailDTO.setSupplierId(poHeader.getSupplierId());
        poHeaderDetailDTO.setSupplierName(poHeader.getSupplierName());
        poHeaderDetailDTO.setRemark(poHeader.getRemark());
        poHeaderDetailDTO.setTermsId(poHeader.getTermsId());
        poOrderSaveDTO.setPoHeaderDetailDTO(poHeaderDetailDTO);
        poOrderSaveDTO.setPoLineDetailDTOs(poLineDetailDTOList);
        return poOrderSaveDTO;
    }

}
