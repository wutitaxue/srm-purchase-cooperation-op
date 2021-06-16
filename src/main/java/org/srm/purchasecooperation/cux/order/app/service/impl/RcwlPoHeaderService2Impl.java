package org.srm.purchasecooperation.cux.order.app.service.impl;

import io.choerodon.core.exception.CommonException;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
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
import org.srm.web.annotation.Tenant;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author bin.zhang
 */
@Service
@Tenant("SRM-RCWL")
public class RcwlPoHeaderService2Impl extends PoHeaderServiceImpl {
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
    private GeneratorPoByPrDomainService generatorPoByPrDomainService;
    @Autowired
    private PrTypeRepository prTypeRepository;
    @Autowired
    private PoHeaderSendApplyMqService poHeaderSendApplyMqService;
    private static final Logger LOGGER = LoggerFactory.getLogger(RcwlPoHeaderService2Impl.class);
    @Override
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

        poDTO = this.poCreate(poDTO);
        this.initPoCreatedBy(poDTO);
        PoHeader poHeader = (PoHeader)this.poHeaderRepository.selectByPrimaryKey(poDTO.getPoHeaderId());
        //再次查询
        prHeader = (PrHeader)this.prHeaderRepository.selectByPrimaryKey(prHeaderId);
        this.prHeaderRepository.updateByPrimaryKeySelective(new PrHeader(prHeaderId, prHeader.getObjectVersionNumber(), "CLOSED", poHeader.getPoHeaderId(), poHeader.getPoNum(), poHeader.getCreatedBy(), poHeader.getCreationDate(), "PO"));
        List<PrLine> prLineList = this.covPoToPrEchoInfo(poDTO.getPoLineList(), prLines, new PoDTO(), true);
        this.prLineRepository.batchUpdateByPrimaryKeySelective(prLineList);
        this.poHeaderSendApplyMqService.sendApplyMq(poDTO.getPoHeaderId(), tenantId, "OCCUPY");
        return poDTO;
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
    private List<PrLine> covPoToPrEchoInfo(List<PoLine> poLines, List<PrLine> prLines, PoDTO poDTO, boolean enableReferWhole) {
        Long poHeaderId = poDTO.getPoHeaderId();
        String poNum = poDTO.getPoNum();
        String poLineIds = StringUtils.join(poLines.stream().map(PoLine::getPoLineId).toArray(), ",");
        List<PoLine> poLines1 = this.poLineRepository.selectByIds(poLineIds);
        return (List)prLines.stream().map((d) -> {
            PoLine poLine = (PoLine)poLines1.stream().filter((l) -> {
                return l.getPrLineId().equals(d.getPrLineId());
            }).findFirst().orElseThrow(() -> {
                return new CommonException("error.pr.line.list.not.null", new Object[0]);
            });
            return enableReferWhole ? new PrLine(d.getPrLineId(), 1, d.getObjectVersionNumber(), poLine.getPoLineId(), poLine.getLineNum().toString(), poLine.getCreatedBy(), poLine.getCreationDate(), "PO", poHeaderId, poNum, poLine.getQuantity()) : new PrLine(d.getPrLineId(), 1, d.getObjectVersionNumber(), poLine.getPoLineId(), poLine.getLineNum().toString(), poLine.getCreatedBy(), poLine.getCreationDate(), "PO", poHeaderId, poNum);
        }).collect(Collectors.toList());
    }
}
