package org.srm.purchasecooperation.cux.order.domain.service.impl;

import org.hzero.boot.file.FileClient;
import org.hzero.boot.file.dto.FileParamsDTO;
import org.hzero.core.base.BaseConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.srm.boot.platform.customizesetting.CustomizeSettingHelper;
import org.srm.boot.pr.app.service.PrManageDomainService;
import org.srm.purchasecooperation.common.app.MdmService;
import org.srm.purchasecooperation.order.app.service.OrderTypeService;
import org.srm.purchasecooperation.order.domain.entity.OrderType;
import org.srm.purchasecooperation.order.domain.entity.PoLine;
import org.srm.purchasecooperation.order.domain.entity.PoLineLocation;
import org.srm.purchasecooperation.order.domain.repository.ChangeHistoryRepository;
import org.srm.purchasecooperation.order.domain.repository.OrderTypeRepository;
import org.srm.purchasecooperation.order.domain.repository.PoHeaderRepository;
import org.srm.purchasecooperation.order.domain.repository.PoLineRepository;
import org.srm.purchasecooperation.order.domain.service.GeneratorPoByPcDomainService;
import org.srm.purchasecooperation.order.domain.service.PoDomainService;
import org.srm.purchasecooperation.order.domain.service.impl.GeneratorPoByPrDomainServiceImpl;
import org.srm.purchasecooperation.order.infra.constant.PoConstants;
import org.srm.purchasecooperation.order.infra.mapper.PoHeaderMapper;
import org.srm.purchasecooperation.order.infra.mapper.PoLineMapper;
import org.srm.purchasecooperation.pr.domain.entity.PrLine;
import org.srm.purchasecooperation.pr.domain.repository.PrHeaderRepository;
import org.srm.purchasecooperation.pr.domain.repository.PrLineRepository;
import org.srm.web.annotation.Tenant;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

@Component
@Tenant("SRM-RCWL")
public class RcwlGeneratorPoByPrDomainServiceImpl extends GeneratorPoByPrDomainServiceImpl {
    @Autowired
    private OrderTypeService orderTypeService;
    @Autowired
    private PoHeaderMapper poHeaderMapper;
    @Autowired
    private PrManageDomainService prManageDomainService;
    @Autowired
    private PoHeaderRepository poHeaderRepository;
    @Autowired
    private CustomizeSettingHelper customizeSettingHelper;
    @Autowired
    private PoLineRepository poLineRepository;
    @Autowired
    private ChangeHistoryRepository changeHistoryRepository;
    @Autowired
    private PrLineRepository prLineRepository;
    @Autowired
    private MdmService mdmService;
    @Autowired
    private PoLineMapper poLineMapper;
    @Autowired
    private GeneratorPoByPcDomainService generatorPoByPcDomainService;
    @Autowired
    private FileClient fileClient;
    @Autowired
    private PrHeaderRepository prHeaderRepository;
    @Autowired
    private OrderTypeRepository orderTypeRepository;
    @Autowired
    private PoDomainService poDomainService;
    private static final Logger LOGGER = LoggerFactory.getLogger(GeneratorPoByPrDomainServiceImpl.class);
    @Override
    protected PoLine generatePoLineByPrLine(PrLine prLine, long lineNum) {
        PoLine poLine = new PoLine();
        poLine.setVersionNum(1L);
        poLine.setPrLineId(prLine.getPrLineId());
        poLine.setPrHeaderId(prLine.getPrHeaderId());
        poLine.setTenantId(prLine.getTenantId());
        poLine.setLineNum(lineNum);
        poLine.setDisplayLineNum(String.valueOf(lineNum));
        poLine.setItemCode(prLine.getItemCode());
        poLine.setItemId(prLine.getItemId());
        poLine.setItemName(prLine.getItemName());
        //融创新增 将物料名称插入需求物料描述
        poLine.setAttributeVarchar10(prLine.getItemName());
        //融创协议编号
        poLine.setPcNum(StringUtils.isEmpty(prLine.getAttributeVarchar40())?null:prLine.getAttributeVarchar40());
        poLine.setCategoryId(prLine.getCategoryId());
        poLine.setQuantity(prLine.getThisOrderQuantity());
        poLine.setPurchaseBatch(BigDecimal.ONE);
        poLine.setUnitPriceBatch(prLine.getUnitPriceBatch() == null ? BigDecimal.ONE : prLine.getUnitPriceBatch());
        poLine.setDisplayPrNum(prLine.getPrNum());
        poLine.setDisplayPrLineNum(prLine.getLineNum().toString());
        poLine.setPrRequestedBy(prLine.getRequestedBy());
        poLine.setPrRequestedName(prLine.getPrRequestedName());
        poLine.setProductId(prLine.getProductId());
        poLine.setProductNum(prLine.getProductNum());
        poLine.setProductName(prLine.getProductName());
        poLine.setCatalogId(prLine.getCatalogId());
        poLine.setCatalogName(prLine.getCatalogName());
        poLine.setInvOrganizationId(prLine.getInvOrganizationId());
        poLine.setDemandUnitId(prLine.getUnitId());
        poLine.setChartCode(prLine.getDrawingNum());
        poLine.setChartVersion(prLine.getDrawingVersion());
        poLine.setModel(prLine.getItemModel());
        poLine.setSpecifications(prLine.getItemSpecs());
        poLine.setUomId(prLine.getUomId());
        poLine.setCurrencyCode(prLine.getCurrencyCode());
        poLine.setTaxId(prLine.getTaxId());
        poLine.setTaxRate(prLine.getTaxRate());
        poLine.setLastPurchasePrice(prLine.getLastPurchasePrice());
        if (("SRM".equals(prLine.getPrSourcePlatform()) || "SHOP".equals(prLine.getPrSourcePlatform()) || "ERP".equals(prLine.getPrSourcePlatform())) && !BaseConstants.Flag.YES.equals(prLine.getPrToPoAutoFlag()) && Objects.isNull(prLine.getPriceLibraryId())) {
            poLine.setUnitPrice((BigDecimal)null);
            poLine.setEnteredTaxIncludedPrice((BigDecimal)null);
            poLine.setLineAmount((BigDecimal)null);
            poLine.setTaxIncludedLineAmount((BigDecimal)null);
        } else {
            poLine.setUnitPrice(prLine.getUnitPrice());
            poLine.setEnteredTaxIncludedPrice(prLine.getEnteredTaxIncludedPrice() == null ? prLine.getTaxIncludedUnitPrice() : prLine.getEnteredTaxIncludedPrice());
            if (prLine.getUnitPrice() != null) {
                poLine.setLineAmount(prLine.getThisOrderQuantity().multiply(prLine.getUnitPrice()));
            }

            if (prLine.getEnteredTaxIncludedPrice() != null) {
                poLine.setTaxIncludedLineAmount(prLine.getThisOrderQuantity().multiply(prLine.getEnteredTaxIncludedPrice()));
            }
        }

        poLine.setSurfaceTreatFlag(prLine.getSurfaceTreatFlag());
        poLine.setAccountAssignTypeId(prLine.getAccountAssignTypeId());
        poLine.setFreightLineFlag(prLine.getFreightLineFlag());
        poLine.setCostId(prLine.getCostId());
        poLine.setCostCode(prLine.getCostCode());
        poLine.setAccountSubjectId(prLine.getAccountSubjectId());
        poLine.setAccountSubjectNum(prLine.getAccountSubjectNum());
        poLine.setWbs(prLine.getWbs());
        poLine.setWbsCode(prLine.getWbsCode());
        PoLineLocation poLineLocation = new PoLineLocation();
        if (("SRM".equals(prLine.getPrSourcePlatform()) || "SHOP".equals(prLine.getPrSourcePlatform()) || "CATALOGUE".equals(prLine.getPrSourcePlatform()) || "ERP".equals(prLine.getPrSourcePlatform())) && prLine.getReceiverAddress() != null) {
            poLineLocation.setShipToThirdPartyAddress(prLine.getReceiverAddress());
            LOGGER.info("srm-22875-ShipToThirdPartyAddress-ReceiverAddress{}", prLine.getReceiverAddress());
        }

        poLineLocation.setInvInventoryId(prLine.getInventoryId());
        poLineLocation.setInvOrganizationId(prLine.getInvOrganizationId());
        poLineLocation.setNeedByDate(prLine.getNeededDate());
        poLineLocation.setQuantity(prLine.getThisOrderQuantity());
        poLineLocation.setLineLocationNum(1L);
        poLineLocation.setDisplayLineLocationNum(String.valueOf(poLineLocation.getLineLocationNum()));
        poLineLocation.setShipToOrganizationId(prLine.getInvOrganizationId());
        poLineLocation.setRemark(prLine.getRemark());
        if (BaseConstants.Flag.YES.equals(prLine.getUrgentFlag())) {
            poLineLocation.setUrgentFlag(prLine.getUrgentFlag());
            poLineLocation.setUrgentDate(new Date());
        }

        poLine.setPoLineLocationList(Collections.singletonList(poLineLocation));
        poLine.setRemark(prLine.getRemark());
        if (Objects.nonNull(prLine.getAttachmentUuid())) {
            Map<String, String> fileMap = this.fileClient.copyFile(poLine.getTenantId(), (FileParamsDTO)null, Collections.singletonList(prLine.getAttachmentUuid()), "private-bucket");
            poLine.setAttachmentUuid((String)fileMap.get(prLine.getAttachmentUuid()));
        }

        poLine.setPriceLibraryId(prLine.getPriceLibraryId());
        poLine.setBenchmarkPriceType(prLine.getBenchmarkPriceType());
        poLine.setLadderQuotationFlag(prLine.getLadderQuotationFlag());
        if (("SRM".equals(prLine.getPrSourcePlatform()) || "SHOP".equals(prLine.getPrSourcePlatform()) || "ERP".equals(prLine.getPrSourcePlatform())) && !BaseConstants.Flag.YES.equals(prLine.getPrToPoAutoFlag()) && Objects.isNull(prLine.getPriceLibraryId())) {
            poLine.setDomesticUnitPrice((BigDecimal)null);
            poLine.setDomesticTaxIncludedPrice((BigDecimal)null);
            poLine.setDomesticLineAmount((BigDecimal)null);
            poLine.setDomesticTaxIncludedLineAmount((BigDecimal)null);
        } else {
            poLine.setDomesticUnitPrice(prLine.getLocalCurrencyNoTaxUnit());
            poLine.setDomesticTaxIncludedPrice(prLine.getLocalCurrencyTaxUnit());
            poLine.setDomesticLineAmount(prLine.getLocalCurrencyNoTaxSum());
            poLine.setDomesticTaxIncludedLineAmount(prLine.getLocalCurrencyTaxSum());
            poLine.setExchangeRate(prLine.getExchangeRate());
        }

        Long orderTypeId = prLine.getOrderTypeId();
        OrderType orderType = (OrderType)this.orderTypeRepository.selectByPrimaryKey(orderTypeId);
        if (!ObjectUtils.isEmpty(orderType)) {
            poLine.setReturnedFlag(Objects.equals(orderType.getReturnOrderFlag(), PoConstants.FreeFlag.YES) ? 1 : 0);
        }

        if (ObjectUtils.isEmpty(poLine.getPriceLibraryId())) {
            return poLine;
        } else {
            if (Objects.equals(poLine.getBenchmarkPriceType(), "NET_PRICE")) {
                poLine.setOriginUnitPrice(poLine.getUnitPrice());
            } else {
                poLine.setOriginUnitPrice(poLine.getEnteredTaxIncludedPrice());
            }

            return poLine;
        }
    }
}
