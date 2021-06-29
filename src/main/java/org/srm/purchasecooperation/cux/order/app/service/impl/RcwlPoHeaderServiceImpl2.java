package org.srm.purchasecooperation.cux.order.app.service.impl;

import io.choerodon.core.exception.CommonException;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.hzero.boot.file.FileClient;
import org.hzero.boot.file.dto.FileParamsDTO;
import org.hzero.mybatis.domian.Condition;
import org.hzero.mybatis.util.Sqls;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.srm.purchasecooperation.order.api.dto.PoDTO;
import org.srm.purchasecooperation.order.app.service.OrderTypeService;
import org.srm.purchasecooperation.order.app.service.impl.PoHeaderServiceImpl;
import org.srm.purchasecooperation.order.domain.entity.PoHeader;
import org.srm.purchasecooperation.order.domain.entity.PoLine;
import org.srm.purchasecooperation.order.domain.entity.PoLineLocation;
import org.srm.purchasecooperation.order.domain.repository.PoHeaderRepository;
import org.srm.purchasecooperation.order.domain.repository.PoLineRepository;
import org.srm.purchasecooperation.order.domain.service.GeneratorPoByPrDomainService;
import org.srm.purchasecooperation.order.domain.service.PoHeaderSendApplyMqService;
import org.srm.purchasecooperation.order.infra.mapper.PoHeaderMapper;
import org.srm.purchasecooperation.pr.domain.entity.PrHeader;
import org.srm.purchasecooperation.pr.domain.entity.PrLine;
import org.srm.purchasecooperation.pr.domain.entity.PrType;
import org.srm.purchasecooperation.pr.domain.repository.PrHeaderRepository;
import org.srm.purchasecooperation.pr.domain.repository.PrLineRepository;
import org.srm.purchasecooperation.pr.domain.repository.PrTypeRepository;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;


/**
 * description
 *
 * @author Zhouzy 2021/05/22 14:31
 */
@Service
//@Tenant(TennantValue.tenantV)
public class RcwlPoHeaderServiceImpl2  {

    @Autowired
    private PoHeaderServiceImpl poHeaderServiceImpl;
    @Autowired
    private PoHeaderRepository poHeaderRepository;
    @Autowired
    private PoLineRepository poLineRepository;
    @Autowired
    private PrHeaderRepository prHeaderRepository;
    @Autowired
    private PrLineRepository prLineRepository;
    @Autowired
    private OrderTypeService orderTypeService;
    @Autowired
    private PoHeaderMapper poHeaderMapper;
    @Autowired
    private PrTypeRepository prTypeRepository;
    @Autowired
    private FileClient fileClient;
    @Autowired
    private PoHeaderSendApplyMqService poHeaderSendApplyMqService;
    @Autowired
    private GeneratorPoByPrDomainService generatorPoByPrDomainService;


    private static final Logger LOGGER = LoggerFactory.getLogger(RcwlPoHeaderServiceImpl2.class);

    public PoDTO referWholePrHeaderAuto(Long tenantId, Long prHeaderId) {
        PrHeader prHeader = (PrHeader)this.prHeaderRepository.selectByPrimaryKey(prHeaderId);
        Assert.notNull(prHeader, "error.pr.not.exists");
        prHeader.validECRefer();
        List<PrLine> prLines = this.prLineRepository.selectByCondition(Condition.builder(PrLine.class).andWhere(Sqls.custom().andEqualTo("tenantId", tenantId).andEqualTo("prHeaderId", prHeaderId)).build());
        Assert.notEmpty(prLines, "error.pr.line.not.exists");
        PrLine firstPrLine = (PrLine)prLines.get(0);
        List<PoLine> poLines = new ArrayList(prLines.size());
        PoDTO poDTO = this.buildPoHeaderByPrHeader(tenantId, prHeader, firstPrLine);
        List<PoDTO> poDTOS = new ArrayList();
        List<PrHeader> prHeaders = new ArrayList();
        int i = 0;
        Iterator var11 = prLines.iterator();

        while(var11.hasNext()) {
            PrLine prLine = (PrLine)var11.next();
            Assert.isTrue(prLine.getSupplierId() != null || prLine.getSupplierCompanyId() != null, "error.pr.line.supplier.id.not.null");
            if (prLine != firstPrLine) {
                prLine.referValidate(firstPrLine);
            }

            prLine.referInitFeild(prHeader);
            PoLine poLine = this.referWholeToPoLineAndLocation(prLine);
            poLine.setDepartmentId(prHeader.getUnitId());
            poLines.add(poLine);
            if (i == 0) {
                poDTO.setCreatedBy(prLine.getCreatedBy());
            }

            poLine.setCreatedBy(prLine.getCreatedBy());
            ++i;
            String mappingCode = this.generatorPoByPrDomainService.queryPrToPoMappingConfig(prLine);
            if (StringUtils.isNotBlank(mappingCode)) {
                prLine.setMappingCode(mappingCode);
                PoDTO poDTONew = new PoDTO();
                BeanUtils.copyProperties(poDTO, poDTONew);
                poDTONew.setPoLineList((List)null);
                poDTONew.setPoLine(poLine);
                poLine.setPoLineLocation((PoLineLocation)poLine.getPoLineLocationList().get(0));
                poDTOS.add(poDTONew);
                prHeader.setPrLine(prLine);
                prHeaders.add(prHeader);
            }
        }

        poDTO.setPoLineList(poLines);

        try {
            if (CollectionUtils.isNotEmpty(poDTOS)) {
                Map<Long, List<PrLine>> map = (Map)prLines.stream().collect(Collectors.groupingBy(PrLine::getPrHeaderId));
                this.generatorPoByPrDomainService.initTemplatePoDTO(poDTO, poDTOS, prHeaders, map.size() == 1);
            }
        } catch (Exception var16) {
            LOGGER.error("=====referWholePrHeaderAuto mapping error is {}", ExceptionUtils.getMessage(var16));
        }

        poDTO = poHeaderServiceImpl.poCreate(poDTO);
        poHeaderServiceImpl.initPoCreatedBy(poDTO);
        PoHeader poHeader = (PoHeader)this.poHeaderRepository.selectByPrimaryKey(poDTO.getPoHeaderId());
        //再次查询
        prHeader = (PrHeader)this.prHeaderRepository.selectByPrimaryKey(prHeaderId);
        this.prHeaderRepository.updateByPrimaryKeySelective(new PrHeader(prHeaderId, prHeader.getObjectVersionNumber(), "CLOSED", poHeader.getPoHeaderId(), poHeader.getPoNum(), poHeader.getCreatedBy(), poHeader.getCreationDate(), "PO"));
        LOGGER.info("25140==1===poDTO:{}", poDTO.toString());
        List<PrLine> prLineList = this.covPoToPrEchoInfo(poDTO.getPoLineList(), prLines, new PoDTO(), true);
        LOGGER.info("25140==1===prLineList:{}", prLineList.toString());
        this.prLineRepository.batchUpdateByPrimaryKeySelective(prLineList);
        LOGGER.error("25140==2===prLineList:{}", prLineList.toString());
        this.poHeaderSendApplyMqService.sendApplyMq(poDTO.getPoHeaderId(), tenantId, "OCCUPY");
        return poDTO;
    }

    private List<PrLine> covPoToPrEchoInfo(List<PoLine> poLines, List<PrLine> prLines, PoDTO poDTO, boolean enableReferWhole) {
        LOGGER.info("25140==2===poLines:{}", poLines.toString());
        Long poHeaderId = poDTO.getPoHeaderId();
        String poNum = poDTO.getPoNum();
        String poLineIds = StringUtils.join(poLines.stream().map(PoLine::getPoLineId).toArray(), ",");
        List<PoLine> poLines1 = this.poLineRepository.selectByIds(poLineIds);
        LOGGER.info("25140==2===poLines1:{}", poLines1.toString());
        return (List)poLines1.stream().map((d) -> {
            PoLine poLine = (PoLine)poLines.stream().filter((l) -> {
                return l.getPrLineId().equals(d.getPrLineId());
            }).findFirst().orElseThrow(() -> {
                return new CommonException("error.pr.line.list.not.null", new Object[0]);
            });
            return enableReferWhole ? new PrLine(d.getPrLineId(), 1, d.getObjectVersionNumber(), poLine.getPoLineId(), poLine.getLineNum().toString(), poLine.getCreatedBy(), poLine.getCreationDate(), "PO", poHeaderId, poNum, poLine.getQuantity()) : new PrLine(d.getPrLineId(), 1, d.getObjectVersionNumber(), poLine.getPoLineId(), poLine.getLineNum().toString(), poLine.getCreatedBy(), poLine.getCreationDate(), "PO", poHeaderId, poNum);
        }).collect(Collectors.toList());
    }

    private PoDTO buildPoHeaderByPrHeader(Long tenantId, PrHeader prHeader, PrLine firstPrLine) {
        PoDTO poDTO = new PoDTO();
        BeanUtils.copyProperties(prHeader, poDTO);
        poDTO.setAgentId(prHeader.getPurchaseAgentId());
        PoHeader.processPurchaseAgent(tenantId, poDTO, this.poHeaderRepository);
        poDTO.setStatusCode("PENDING");
        poDTO.setSupplierId(firstPrLine.getSupplierId());
        poDTO.setSupplierCode(firstPrLine.getSupplierCode());
        poDTO.setSupplierName(firstPrLine.getSupplierName());
        poDTO.setSupplierTenantId(firstPrLine.getSupplierTenantId());
        poDTO.setSupplierCompanyId(firstPrLine.getSupplierCompanyId());
        poDTO.setSupplierCompanyName(firstPrLine.getSupplierCompanyName());
        poDTO.setShipToLocationId(prHeader.getReceiverAddressId());
        poDTO.setShipToLocationAddress(prHeader.getReceiverAddress());
        poDTO.setBillToLocationId(prHeader.getInvoiceAddressId());
        poDTO.setBillToLocationAddress(prHeader.getInvoiceAddress());
        poDTO.setReceiverEmailAddress(prHeader.getReceiverEmailAddress());
        PrType prType = (PrType)this.prTypeRepository.selectByPrimaryKey(prHeader.getPrTypeId());
        poDTO.setPoTypeId(prType != null && prType.getOrderTypeId() != null && prType.getOrderTypeId() != 0L ? prType.getOrderTypeId() : this.orderTypeService.queryDefaultOrderType(tenantId).getOrderTypeId());
        poDTO.setDisplayPrNum(prHeader.getPrNum());
        poDTO.setVersionNum(1);
        poDTO.setIfAccoringToPrLine(true);
        poDTO.setPoSourcePlatform(prHeader.getPrSourcePlatform());
        poDTO.setCompanyName(this.poHeaderMapper.selectCompanyNameById(prHeader.getCompanyId()));
        if ("E-COMMERCE".equals(poDTO.getPoSourcePlatform()) || "CATALOGUE".equals(poDTO.getPoSourcePlatform())) {
            if ("E-COMMERCE".equals(poDTO.getPoSourcePlatform())) {
                poDTO.setDomesticCurrencyCode(prHeader.getLocalCurrency());
            } else {
                poDTO.setDomesticCurrencyCode(this.poHeaderMapper.queryCurrencyByCompanyId(prHeader.getCompanyId()));
            }

            poDTO.setDomesticAmount(prHeader.getLocalCurrencyNoTaxSum());
            poDTO.setDomesticTaxIncludeAmount(prHeader.getLocalCurrencyTaxSum());
        }

        if ("CATALOGUE".equals(poDTO.getPoSourcePlatform())) {
            if (!Objects.isNull(firstPrLine.getReceiveContactName())) {
                poDTO.setShipToLocContName(firstPrLine.getReceiveContactName());
            }

            if (!Objects.isNull(firstPrLine.getReceiveTelNum())) {
                poDTO.setShipToLocTelNum(firstPrLine.getReceiveTelNum());
            }
        }

        if ("CATALOGUE".equals(poDTO.getPoSourcePlatform()) || "E-COMMERCE".equals(poDTO.getPoSourcePlatform())) {
            PrHeader prDbRecord = (PrHeader)this.prHeaderRepository.selectByPrimaryKey(firstPrLine.getPrHeaderId());
            poDTO.setPurchaseUnitName(prDbRecord.getPurchaseUnitName());
        }

        return poDTO;
    }
    protected PoLine referWholeToPoLineAndLocation(PrLine prLine) {
        PoLine poLine = new PoLine();
        poLine.setPrLineId(prLine.getPrLineId());
        poLine.setPrHeaderId(prLine.getPrHeaderId());
        poLine.setTenantId(prLine.getTenantId());
        poLine.setLineNum(prLine.getLineNum());
        poLine.setDisplayLineNum(prLine.getDisplayLineNum());
        poLine.setInvOrganizationId(prLine.getInvOrganizationId());
        poLine.setItemId(prLine.getItemId());
        poLine.setItemCode(prLine.getItemCode());
        poLine.setItemName(prLine.getItemName());
        poLine.setProductId(prLine.getProductId());
        poLine.setProductNum(prLine.getProductNum());
        poLine.setProductName(prLine.getProductName());
        poLine.setCatalogId(prLine.getCatalogId());
        poLine.setCatalogName(prLine.getCatalogName());
        poLine.setCategoryId(prLine.getCategoryId());
        poLine.setUomId(prLine.getUomId());
        poLine.setQuantity(prLine.getQuantity());
        poLine.setTaxId(prLine.getTaxId());
        poLine.setTaxRate(prLine.getTaxRate());
        poLine.setCurrencyCode(prLine.getCurrencyCode());
        poLine.setUnitPrice(prLine.getUnitPrice());
        poLine.setLineAmount(prLine.getLineAmount());
        poLine.setTaxIncludedLineAmount(prLine.getTaxIncludedLineAmount());
        poLine.setRemark(prLine.getRemark());
        poLine.setPrRequestedName(prLine.getPrRequestedName());
        poLine.setCostId(prLine.getCostId());
        poLine.setCostCode(prLine.getCostCode());
        poLine.setAccountSubjectId(prLine.getAccountSubjectId());
        poLine.setAccountSubjectNum(prLine.getAccountSubjectNum());
        poLine.setWbs(prLine.getWbs());
        poLine.setWbsCode(prLine.getWbsCode());
        poLine.setJdPrice(prLine.getJdPrice());
        poLine.setPcHeaderId(prLine.getPcHeaderId());
        poLine.setLastPurchasePrice(prLine.getLastPurchasePrice());
        poLine.setSurfaceTreatFlag(prLine.getSurfaceTreatFlag());
        poLine.setUnitPriceBatch(prLine.getUnitPriceBatch());
        poLine.setProjectCategory(prLine.getProjectCategory());
        poLine.setTaxWithoutFreightPrice(prLine.getTaxWithoutFreightPrice());
        poLine.setAccountAssignTypeId(prLine.getAccountAssignTypeId());
        poLine.setFreightLineFlag(prLine.getFreightLineFlag());
        poLine.setEnteredTaxIncludedPrice(prLine.getEnteredTaxIncludedPrice());
        poLine.setDisplayPrNum(prLine.getDisplayPrNum());
        poLine.setDisplayLineNum(prLine.getDisplayLineNum());
        poLine.setThisOrderQuantity(prLine.getThisOrderQuantity());
        poLine.setPoLineId((Long)null);
        poLine.setPrRequestedBy(prLine.getRequestedBy());
        poLine.setEnteredTaxIncludedPrice(prLine.getTaxIncludedUnitPrice());
        poLine.setChartCode(prLine.getDrawingNum());
        poLine.setChartVersion(prLine.getDrawingVersion());
        poLine.setModel(prLine.getItemModel());
        poLine.setSpecifications(prLine.getItemSpecs());
        poLine.setPcNum(prLine.getFrameAgreementNum());
        PoLineLocation poLineLocation = new PoLineLocation();
        if (("SRM".equals(prLine.getPrSourcePlatform()) || "SHOP".equals(prLine.getPrSourcePlatform()) || "CATALOGUE".equals(prLine.getPrSourcePlatform()) || "ERP".equals(prLine.getPrSourcePlatform())) && prLine.getReceiveAddress() != null) {
            poLineLocation.setShipToThirdPartyAddress(prLine.getReceiveAddress());
        }

        poLineLocation.setTenantId(prLine.getTenantId());
        poLineLocation.setDisplayLineNum(prLine.getDisplayLineNum());
        poLineLocation.setCompanyId(prLine.getCompanyId());
        poLineLocation.setInvOrganizationId(prLine.getInvOrganizationId());
        poLineLocation.setItemId(prLine.getItemId());
        poLineLocation.setItemCode(prLine.getItemCode());
        poLineLocation.setItemName(prLine.getItemName());
        poLineLocation.setQuantity(prLine.getQuantity());
        poLineLocation.setSupplierTenantId(prLine.getSupplierTenantId());
        poLineLocation.setRemark(prLine.getRemark());
        poLineLocation.setOuId(prLine.getOuId());
        poLineLocation.setPurchaseOrgId(prLine.getPurchaseOrgId());
        poLineLocation.setUrgentDate(prLine.getUrgentDate());
        poLineLocation.setUrgentFlag(prLine.getUrgentFlag());
        poLineLocation.setOccupiedQuantity(prLine.getOccupiedQuantity());
        poLineLocation.setPoLineId(prLine.getPoLineId());
        poLineLocation.setAgentId(prLine.getAgentId());
        poLineLocation.setPrNum(prLine.getPrNum());
        poLineLocation.setExternalSystemCode(prLine.getExternalSystemCode());
        poLineLocation.setNeedByDate(prLine.getNeededDate());
        poLineLocation.setPoLineId((Long)null);
        poLineLocation.setInvInventoryId(prLine.getInventoryId());
        poLineLocation.setOccupiedQuantity((BigDecimal)null);
        poLine.setPoLineLocationList(Collections.singletonList(poLineLocation));
        poLine.setTaxWithoutFreightPrice(prLine.getTaxWithoutFreightPrice());
        if (Objects.nonNull(prLine.getAttachmentUuid())) {
            Map<String, String> fileMap = this.fileClient.copyFile(poLine.getTenantId(), (FileParamsDTO)null, Collections.singletonList(prLine.getAttachmentUuid()), "private-bucket");
            poLine.setAttachmentUuid((String)fileMap.get(prLine.getAttachmentUuid()));
        }

        if ("E-COMMERCE".equals(prLine.getPrSourcePlatform()) || "CATALOGUE".equals(prLine.getPrSourcePlatform())) {
            poLine.setDomesticUnitPrice(prLine.getLocalCurrencyNoTaxUnit());
            poLine.setDomesticTaxIncludedPrice(prLine.getLocalCurrencyTaxUnit());
            poLine.setDomesticLineAmount(prLine.getLocalCurrencyNoTaxSum());
            poLine.setDomesticTaxIncludedLineAmount(prLine.getLocalCurrencyTaxSum());
            poLine.setExchangeRate(prLine.getExchangeRate());
        }

        return poLine;
    }

}
