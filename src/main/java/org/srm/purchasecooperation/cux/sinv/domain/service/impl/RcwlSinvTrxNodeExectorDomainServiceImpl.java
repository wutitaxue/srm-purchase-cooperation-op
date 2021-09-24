package org.srm.purchasecooperation.cux.sinv.domain.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.hzero.core.base.BaseConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.srm.purchasecooperation.order.infra.convertor.CommonConvertor;
import org.srm.purchasecooperation.sinv.api.dto.SinvRcvTrxHeaderDTO;
import org.srm.purchasecooperation.sinv.api.dto.SinvRcvTrxLineDTO;
import org.srm.purchasecooperation.sinv.domain.entity.RcvStrategyLine;
import org.srm.purchasecooperation.sinv.domain.entity.SinvRcvTrxLine;
import org.srm.purchasecooperation.sinv.domain.repository.SinvRcvTrxLineRepository;
import org.srm.purchasecooperation.sinv.domain.service.impl.SinvTrxNodeExectorDomainServiceImpl;
import org.srm.purchasecooperation.sinv.infra.mapper.SinvRcvTrxHeaderMapper;
import org.srm.web.annotation.Tenant;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

/**
 * @description:
 * @author: bin.zhang
 * @createDate: 2021/9/24 18:27
 */
@Component
@Tenant("SRM-RCWL")
public class RcwlSinvTrxNodeExectorDomainServiceImpl extends SinvTrxNodeExectorDomainServiceImpl {
    private static final Logger LOGGER = LoggerFactory.getLogger(RcwlSinvTrxNodeExectorDomainServiceImpl.class);
    @Autowired
    private SinvRcvTrxHeaderMapper sinvRcvTrxHeaderMapper;
    @Autowired
    private SinvRcvTrxLineRepository sinvRcvTrxLineRepository;

    @Override
    public SinvRcvTrxHeaderDTO plusQuantityOccupy(Long tenantId, SinvRcvTrxHeaderDTO sinvRcvTrxHeaderDTO) {
        LOGGER.info("srm-22587-SinvRcvTrxDomainServiceImpl-plusQuantityOccupy:begin");
        LOGGER.info("srm-22587-SinvRcvTrxDomainServiceImpl-plusQuantityOccupy:sinvRcvTrxHeaderDTO{}", sinvRcvTrxHeaderDTO);
        List<SinvRcvTrxLineDTO> sinvRcvTrxLineDTOList = sinvRcvTrxHeaderDTO.getSinvRcvTrxLineDTOS();
        List<SinvRcvTrxLine> sinvRcvTrxLines = new ArrayList();
        sinvRcvTrxLineDTOList.forEach((sinvRcvTrxLineDTO) -> {
            SinvRcvTrxLine sinvRcvTrxLine = (SinvRcvTrxLine) CommonConvertor.beanConvert(SinvRcvTrxLine.class, sinvRcvTrxLineDTO);
            sinvRcvTrxLines.add(sinvRcvTrxLine);
        });
        String fromRcvTrxLineIds = StringUtils.join(sinvRcvTrxLineDTOList.stream().map(SinvRcvTrxLineDTO::getFromRcvTrxLineId).toArray(), ",");
        LOGGER.info("srm-22587-SinvRcvTrxDomainServiceImpl-plusQuantityOccupy:fromRcvTrxLineIds{}", fromRcvTrxLineIds);
        List<SinvRcvTrxLine> fromSinvRcvTrxLines = this.sinvRcvTrxLineRepository.selectByIds(fromRcvTrxLineIds);
        sinvRcvTrxLines.forEach((sinvRcvTrxLine) -> {
            Iterator var4 = fromSinvRcvTrxLines.iterator();

            while(var4.hasNext()) {
                SinvRcvTrxLine fromSinvRcvTrxLine = (SinvRcvTrxLine)var4.next();
                if (sinvRcvTrxLine.getFromRcvTrxLineId().equals(fromSinvRcvTrxLine.getRcvTrxLineId())) {
                    this.plusQuantitySingleLineOccupy(tenantId, sinvRcvTrxLine, fromSinvRcvTrxLine);
                    break;
                }
            }

        });
        LOGGER.info("srm-22587-SinvRcvTrxDomainServiceImpl-plusQuantityOccupy:end");
        return sinvRcvTrxHeaderDTO;
    }

    @Override
    public void plusQuantitySingleLineOccupy(Long tenantId, SinvRcvTrxLine sinvRcvTrxLine, SinvRcvTrxLine fromSinvRcvTrxLine) {
        LOGGER.info("24730 =================begin");
        LOGGER.info("srm-22587-SinvRcvTrxDomainServiceImpl-plusQuantitySingleLineOccupy:begin");
        LOGGER.info("srm-22587-SinvRcvTrxDomainServiceImpl-plusQuantitySingleLineOccupy:sinvRcvTrxLine{}", sinvRcvTrxLine);
        LOGGER.info("srm-22587-SinvRcvTrxDomainServiceImpl-plusQuantitySingleLineOccupy:sinvRcvTrxLine{}", fromSinvRcvTrxLine);
        RcvStrategyLine rcvStrategyLine = this.sinvRcvTrxHeaderMapper.selectSinvNowNodeConfig(tenantId, sinvRcvTrxLine.getRcvTrxLineId());
        String subjectType = rcvStrategyLine.getSubjectType();
        BigDecimal orgOccupiedQuantity = ((BigDecimal) Optional.ofNullable(fromSinvRcvTrxLine.getOccupiedQuantity()).orElse(BigDecimal.ZERO)).add((BigDecimal)Optional.ofNullable(sinvRcvTrxLine.getQuantity()).orElse(BigDecimal.ZERO)).subtract((BigDecimal)Optional.ofNullable(sinvRcvTrxLine.getUpdateQuantity()).orElse(BigDecimal.ZERO)).setScale(6, RoundingMode.HALF_UP);
        LOGGER.info("srm-22587-SinvRcvTrxDomainServiceImpl-plusQuantitySingleLineOccupy-orgOccupiedQuantity-" + orgOccupiedQuantity);
        BigDecimal orgOccupiedTaxAmount = ((BigDecimal)Optional.ofNullable(fromSinvRcvTrxLine.getOccupiedTaxAmount()).orElse(BigDecimal.ZERO)).add((BigDecimal)Optional.ofNullable(sinvRcvTrxLine.getTaxIncludedAmount()).orElse(BigDecimal.ZERO)).subtract((BigDecimal)Optional.ofNullable(sinvRcvTrxLine.getUpdateTaxAmount()).orElse(BigDecimal.ZERO)).setScale(2, RoundingMode.HALF_UP);
        LOGGER.info("srm-22587-SinvRcvTrxDomainServiceImpl-plusQuantitySingleLineOccupy-orgOccupiedTaxAmount-" + orgOccupiedTaxAmount);
        fromSinvRcvTrxLine.setOccupiedQuantity(orgOccupiedQuantity);
        fromSinvRcvTrxLine.setOccupiedTaxAmount(orgOccupiedTaxAmount);
        BigDecimal leftQuantity = fromSinvRcvTrxLine.getQuantity().subtract(fromSinvRcvTrxLine.getOccupiedQuantity()).add(fromSinvRcvTrxLine.getReversedQuantity()).setScale(4, RoundingMode.HALF_UP);
        LOGGER.debug("srm-22587-SinvRcvTrxDomainServiceImpl-plusQuantitySingleLineOccupy-leftQuantity-" + leftQuantity);
        BigDecimal leftTaxAmount = fromSinvRcvTrxLine.getTaxIncludedAmount().subtract(fromSinvRcvTrxLine.getOccupiedTaxAmount()).add(fromSinvRcvTrxLine.getReversedTaxAmount()).setScale(2, RoundingMode.HALF_UP);
        LOGGER.debug("srm-22587-SinvRcvTrxDomainServiceImpl-plusQuantitySingleLineOccupy-leftTaxAmount-" + leftTaxAmount);
        if ("QUANTITY".equals(subjectType)) {
            if (BigDecimal.ZERO.compareTo(leftQuantity) != -1) {
                fromSinvRcvTrxLine.setCompleteFlag(BaseConstants.Flag.YES);
            } else {
                fromSinvRcvTrxLine.setCompleteFlag(BaseConstants.Flag.NO);
            }
        }

        if ("AMOUNT".equals(subjectType)) {
            if (BigDecimal.ZERO.compareTo(leftTaxAmount) != -1) {
                fromSinvRcvTrxLine.setCompleteFlag(BaseConstants.Flag.YES);
            } else {
                fromSinvRcvTrxLine.setCompleteFlag(BaseConstants.Flag.NO);
            }
        }

        this.sinvRcvTrxLineRepository.updateOptional(fromSinvRcvTrxLine, new String[]{"occupiedQuantity", "occupiedTaxAmount", "completeFlag"});
        LOGGER.info("srm-22587-SinvRcvTrxDomainServiceImpl-plusQuantitySingleLineOccupy:end");
    }
}
