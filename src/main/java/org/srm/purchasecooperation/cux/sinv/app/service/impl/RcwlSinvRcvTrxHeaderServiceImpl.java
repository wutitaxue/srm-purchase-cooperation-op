package org.srm.purchasecooperation.cux.sinv.app.service.impl;

import com.alibaba.fastjson.JSON;
import io.choerodon.core.exception.CommonException;
import org.apache.servicecomb.pack.omega.context.annotations.SagaStart;
import org.hzero.core.base.BaseConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.srm.boot.adaptor.client.AdaptorTaskHelper;
import org.srm.boot.adaptor.client.exception.TaskNotExistException;
import org.srm.common.TenantInfoHelper;
import org.srm.mq.service.producer.MessageProducer;
import org.srm.purchasecooperation.asn.app.service.SendMessageToPrService;
import org.srm.purchasecooperation.common.api.dto.SmdmCurrencyDTO;
import org.srm.purchasecooperation.common.app.MdmService;
import org.srm.purchasecooperation.common.app.impl.SpucCommonServiceImpl;
import org.srm.purchasecooperation.cux.sinv.domain.repository.RcwlSinvRcvTrxLineRepository;
import org.srm.purchasecooperation.order.domain.repository.PoHeaderRepository;
import org.srm.purchasecooperation.order.domain.repository.PoLineLocationRepository;
import org.srm.purchasecooperation.order.domain.repository.PoLineRepository;
import org.srm.purchasecooperation.order.domain.service.PoHeaderSendApplyMqService;
import org.srm.purchasecooperation.sinv.api.dto.SinvRcvTrxHeaderDTO;
import org.srm.purchasecooperation.sinv.api.dto.SinvRcvTrxLineDTO;
import org.srm.purchasecooperation.sinv.app.service.impl.SinvRcvTrxHeaderServiceImpl;
import org.srm.purchasecooperation.sinv.domain.entity.RcvStrategyLine;
import org.srm.purchasecooperation.sinv.domain.entity.SinvRcvTrxHeader;
import org.srm.purchasecooperation.sinv.domain.entity.SinvRcvTrxLine;
import org.srm.purchasecooperation.sinv.domain.repository.*;
import org.srm.purchasecooperation.sinv.domain.service.*;
import org.srm.web.annotation.Tenant;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

/**
 * @description:
 * @author: bin.zhang
 * @createDate: 2021/4/22 16:36
 */
@Service
@Tenant("SRM-RCWL")
public class RcwlSinvRcvTrxHeaderServiceImpl extends SinvRcvTrxHeaderServiceImpl {
    private static final Logger LOGGER = LoggerFactory.getLogger(RcwlSinvRcvTrxHeaderServiceImpl.class);
    @Autowired
    private SinvRcvTrxHeaderRepository sinvRcvTrxHeaderRepository;
    @Autowired
    private PoHeaderRepository poHeaderRepository;
    @Autowired
    private PoLineRepository poLineRepository;
    @Autowired
    private PoLineLocationRepository poLineLocationRepository;
    @Autowired
    private SinvTrxNodeExectorDomainService sinvRcvTrxDomainService;
    @Autowired
    private RcvStrategyLineRepository strategyLineRepository;
    @Autowired
    private RcvStrategyLineDomainService rcvStrategyLineDomainService;
    @Autowired
    private SinvRcvTrxLineRepository sinvRcvTrxLineRepository;
    @Autowired
    private SinvRcvRecordStrategyMappingRepository sinvRcvRecordStrategyMappingRepository;
    @Autowired
    private SinvRcvTrxOrderLinkRepository sinvRcvTrxOrderLinkRepository;
    @Autowired
    private RcvChangeRecordRepository rcvChangeRecordRepository;
    @Autowired
    private RcvStrategyLineRepository rcvStrategyLineRepository;
    @Autowired
    private MdmService mdmService;
    @Autowired
    private SinvRcvTrxHeaderDomainService sinvRcvTrxHeaderDomainService;
    @Autowired
    private SpucCommonServiceImpl spucCommonService;
    @Autowired
    private SinvTrxExportDomainService sinvTrxExportDomainService;
    @Autowired
    private SinvTrxPcDomainService sinvRcvPcDomainService;
    @Autowired
    private SinvTrxMinuQuantityDomainService sinvTrxMinuQuantityDomainService;

    @Autowired
    private SinvTrxCommonDomainService sinvTrxCommonDomainService;
    @Autowired
    @Lazy
    private SinvTrxSettleDomainService sinvTrxSettleDomainService;
    @Autowired
    private PoHeaderSendApplyMqService poHeaderSendApplyMqService;
    @Autowired
    private MessageProducer messageProducer;
    @Autowired
    private SendMessageToPrService sendMessageToPrService;
    @Autowired
    @Lazy
    private RcvNodeConfigCommonDomainService rcvNodeConfigCommonDomainService;
    @Autowired
    private RcwlSinvRcvTrxLineRepository rcwlSinvRcvTrxLineRepository;

    public RcwlSinvRcvTrxHeaderServiceImpl() {
    }


    @Override
    @Transactional(
            rollbackFor = {Exception.class}
    )
    public SinvRcvTrxHeaderDTO updateSinv(Long tenantId, SinvRcvTrxHeaderDTO sinvRcvTrxHeaderDTO) {
        LOGGER.info("srm-22587-SinvRcvTrxHeaderServiceImpl-updateSinv:begin");
        LOGGER.info("srm-22587-SinvRcvTrxHeaderServiceImpl-updateSinv:sinvRcvTrxHeaderDTO,{}", sinvRcvTrxHeaderDTO);
        SinvRcvTrxHeader sinvRcvTrxHeader = new SinvRcvTrxHeader();

        BeanUtils.copyProperties(sinvRcvTrxHeaderDTO, sinvRcvTrxHeader);
        List<SinvRcvTrxLineDTO> sinvRcvTrxLineDTOS = sinvRcvTrxHeaderDTO.getSinvRcvTrxLineDTOS();
        List<SinvRcvTrxLine> sinvRcvTrxLines = new ArrayList();
        org.srm.common.client.entity.Tenant tenant = TenantInfoHelper.selectByTenantId(tenantId);

        try {
            Map<String, Object> map = new HashMap();
            map.put("sinvRcvTrxWaitingDTOs", sinvRcvTrxLineDTOS);
            LOGGER.debug("SINV_CHECK_RCV_TRX_SAVE-updateSinv 适配器入参:{}", JSON.toJSON(map));
            AdaptorTaskHelper.executeAdaptorTask("SINV_CHECK_RCV_TRX_SAVE", tenant.getTenantNum(), JSON.toJSON(map));
        } catch (TaskNotExistException var8) {
            LOGGER.debug("============SINV_CHECK_RCV_TRX_SAVE-updateSinv-TaskNotExistException=============={}", new Object[]{tenant.getTenantNum(), var8.getMessage(), var8.getStackTrace()});
        }

        sinvRcvTrxLineDTOS.forEach((sinvRcvTrxLineDTO) -> {

            SinvRcvTrxLine sinvRcvTrxLine = new SinvRcvTrxLine();
            BeanUtils.copyProperties(sinvRcvTrxLineDTO, sinvRcvTrxLine);
            LOGGER.info("收获事务行标表24730:"+sinvRcvTrxLine.toString());
            sinvRcvTrxLine.setTenantId(tenantId);
            SmdmCurrencyDTO smdmCurrencyDTO = this.mdmService.selectSmdmCurrencyDto(tenantId, sinvRcvTrxLine.getCurrencyCode());
            int financialPrecision = smdmCurrencyDTO.getFinancialPrecision();
            BigDecimal taxIncludedAmount;
            BigDecimal netAmount;
            if ("AMOUNT".equals(sinvRcvTrxLineDTO.getSubjectType())) {
                if (Objects.isNull(sinvRcvTrxLine.getTaxIncludedAmount())) {
                    throw new CommonException("sinv.quantity.or.amount.null.error", new Object[0]);
                }

                if (sinvRcvTrxLineDTO.getTaxIncludedAmount().compareTo(sinvRcvTrxLineDTO.getLeftTaxAmount()) == BaseConstants.Flag.YES) {
                    throw new CommonException("sinv.quantity.or.amount.more.than.error", new Object[0]);
                }

                taxIncludedAmount = this.calcQuantity(sinvRcvTrxLine.getTaxIncludedAmount(), sinvRcvTrxLine.getUnitPriceBatch(), sinvRcvTrxLine.getTaxIncludedPrice(), 4, sinvRcvTrxHeaderDTO.getOrderTypeCode(), sinvRcvTrxLine.getPayRatio());
                LOGGER.info("srm-22587-SinvRcvTrxHeaderServiceImpl-updateSinv:quantity[" + taxIncludedAmount + "]");
                //质保金比例获取
                BigDecimal percent = this.rcwlSinvRcvTrxLineRepository.selectRententionMoneyPercent(sinvRcvTrxLine.getFromPoHeaderId(), sinvRcvTrxLine.getFromPoLineId(), tenantId);

                if (percent == null) {
                    percent = new BigDecimal(0);
                }
                //质保金金额=执行金额（含税）*质保金比例/100
                BigDecimal retentionMoney = taxIncludedAmount.multiply(percent).divide(new BigDecimal(100),4,RoundingMode.HALF_UP);
                LOGGER.info("质保金："+retentionMoney);
                //将质保金和收货人插入行表
                this.rcwlSinvRcvTrxLineRepository.insertRetentionMoneyAndReceiver(sinvRcvTrxLine.getRcvTrxLineId(),retentionMoney,sinvRcvTrxLine.getAttributeBigint2(),tenantId);

                sinvRcvTrxLine.setQuantity(taxIncludedAmount);
                sinvRcvTrxLineDTO.setQuantity(taxIncludedAmount);
                netAmount = (new BigDecimal(100)).add(sinvRcvTrxLine.getTaxRate()).divide(new BigDecimal(100));
                sinvRcvTrxLine.setNetAmount(sinvRcvTrxLine.getTaxIncludedAmount().divide(netAmount, financialPrecision, RoundingMode.HALF_UP));
                sinvRcvTrxLine.setOrgQuantity(sinvRcvTrxLine.getQuantity());
            } else if ("QUANTITY".equals(sinvRcvTrxLineDTO.getSubjectType())) {
                if (Objects.isNull(sinvRcvTrxLine.getQuantity())) {
                    throw new CommonException("sinv.quantity.or.amount.null.error", new Object[0]);
                }

                if (sinvRcvTrxLineDTO.getQuantity().compareTo(sinvRcvTrxLineDTO.getLeftQuantity()) == BaseConstants.Flag.YES) {
                    throw new CommonException("sinv.quantity.or.amount.more.than.error", new Object[0]);
                }

                taxIncludedAmount = this.calcTaxIncludedAmount(sinvRcvTrxLine.getQuantity(), sinvRcvTrxLine.getTaxIncludedPrice(), sinvRcvTrxLine.getUnitPriceBatch(), financialPrecision, sinvRcvTrxLine.getOrderTypeCode(), sinvRcvTrxLine.getPayRatio());

                //质保金比例获取
                BigDecimal percent = this.rcwlSinvRcvTrxLineRepository.selectRententionMoneyPercent(sinvRcvTrxLine.getFromPoHeaderId(), sinvRcvTrxLine.getFromPoLineId(), tenantId);
                if (percent == null) {
                    percent = new BigDecimal(0);
                }
                //质保金金额=执行金额（含税）*质保金比例/100
                 BigDecimal retentionMoney = taxIncludedAmount.multiply(percent).divide(new BigDecimal(100),4,RoundingMode.HALF_UP);
                LOGGER.info("质保金："+retentionMoney);
                LOGGER.info("srm-22587-SinvRcvTrxHeaderServiceImpl-updateSinv:taxIncludedAmount[" + taxIncludedAmount + "]");
                //将质保金和收货人插入行表
                this.rcwlSinvRcvTrxLineRepository.insertRetentionMoneyAndReceiver(sinvRcvTrxLine.getRcvTrxLineId(),retentionMoney,sinvRcvTrxLine.getAttributeBigint2(),tenantId);

                sinvRcvTrxLine.setTaxIncludedAmount(taxIncludedAmount);
                sinvRcvTrxLineDTO.setTaxIncludedAmount(taxIncludedAmount);
                netAmount = sinvRcvTrxLineDTO.getQuantity().multiply(sinvRcvTrxLineDTO.getNetPrice()).divide(sinvRcvTrxLine.getUnitPriceBatch(), 8, RoundingMode.HALF_UP).setScale(financialPrecision, RoundingMode.HALF_UP);
                sinvRcvTrxLineDTO.setNetAmount(netAmount);
                LOGGER.info("srm-22587-SinvRcvTrxHeaderServiceImpl-updateSinv:netAmount[" + netAmount + "]");
                sinvRcvTrxLine.setNetAmount(netAmount);
                sinvRcvTrxLine.setOrgQuantity(sinvRcvTrxLine.getQuantity());
            }

            sinvRcvTrxLines.add(sinvRcvTrxLine);
        });
        this.sinvRcvTrxHeaderRepository.updateByPrimaryKeySelective(sinvRcvTrxHeader);
        this.sinvRcvTrxLineRepository.batchUpdateByPrimaryKeySelective(sinvRcvTrxLines);
        this.sinvRcvTrxDomainService.plusQuantityOccupy(tenantId, sinvRcvTrxHeaderDTO);
        LOGGER.info("srm-22587-SinvRcvTrxHeaderServiceImpl-updateSinv:end");
        return sinvRcvTrxHeaderDTO;
    }

    @Override
    @Transactional(
            rollbackFor = {Exception.class}
    )
    @SagaStart
    public SinvRcvTrxHeaderDTO submittedSinv(Long tenantId, SinvRcvTrxHeaderDTO sinvRcvTrxHeaderDTO) {
        LOGGER.info("srm-22587-SinvRcvTrxHeaderServiceImpl-submittedSinv:begin");
        LOGGER.info("srm-22587-SinvRcvTrxHeaderServiceImpl-submittedSinv:sinvRcvTrxHeaderDTO{}", sinvRcvTrxHeaderDTO);
        if (BaseConstants.Flag.YES.equals(sinvRcvTrxHeaderDTO.getExecuteUpdateFlag())) {
            this.updateSinv(tenantId, sinvRcvTrxHeaderDTO);
        }

        this.adaptorTaskCheckBeforeStatusUpdate(tenantId, "SUBMITTED", sinvRcvTrxHeaderDTO);
        RcvStrategyLine rcvStrategyLine = this.selectRcvNowNodeConfig(tenantId, sinvRcvTrxHeaderDTO.getRcvTrxHeaderId(), (Long)null);
        if ("WFL".equals(((RcvStrategyLine)Optional.ofNullable(rcvStrategyLine).orElse(new RcvStrategyLine())).getApproveRuleCode())) {
            LOGGER.debug("21424-submittedSinv-need-wfl");
            this.sinvRcvTrxHeaderDomainService.submittedSinvToWFL(tenantId, sinvRcvTrxHeaderDTO, rcvStrategyLine);
            return sinvRcvTrxHeaderDTO;
        } else {
            this.submittedSinvNone(tenantId, sinvRcvTrxHeaderDTO, rcvStrategyLine);
            return sinvRcvTrxHeaderDTO;
        }
    }


}
