package org.srm.purchasecooperation.cux.sinv.app.service.impl;

import org.hzero.core.base.BaseConstants;
import org.hzero.mybatis.domian.Condition;
import org.hzero.mybatis.util.Sqls;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.srm.purchasecooperation.asn.app.service.SendMessageToPrService;
import org.srm.purchasecooperation.cux.sinv.domain.vo.SinvRcvTrxToKpiAutoPOLineVO;
import org.srm.purchasecooperation.cux.sinv.infra.feign.RcwlSinvRcvTrxSslmRemoteService;
import org.srm.purchasecooperation.cux.sinv.infra.mapper.RcwlSinvRcvTrxHeaderMapper;
import org.srm.purchasecooperation.cux.sinv.infra.mapper.SettleMapper;
import org.srm.purchasecooperation.cux.transaction.app.service.RcwlOrderBillService;
import org.srm.purchasecooperation.cux.transaction.infra.mapper.RcwlOrderBillMapper;
import org.srm.purchasecooperation.order.domain.entity.PoHeader;
import org.srm.purchasecooperation.order.domain.repository.PoHeaderRepository;
import org.srm.purchasecooperation.sinv.api.dto.SinvRcvTrxHeaderDTO;
import org.srm.purchasecooperation.sinv.api.dto.SinvRcvTrxLineDTO;
import org.srm.purchasecooperation.sinv.app.service.impl.SinvRcvTrxHeaderServiceImpl;
import org.srm.purchasecooperation.sinv.domain.entity.RcvStrategyLine;
import org.srm.purchasecooperation.sinv.domain.entity.SinvRcvRecordStrategyMapping;
import org.srm.purchasecooperation.sinv.domain.entity.SinvRcvTrxHeader;
import org.srm.purchasecooperation.sinv.domain.entity.SinvRcvTrxLine;
import org.srm.purchasecooperation.sinv.domain.repository.SinvRcvRecordStrategyMappingRepository;
import org.srm.purchasecooperation.sinv.domain.repository.SinvRcvTrxHeaderRepository;
import org.srm.purchasecooperation.sinv.domain.repository.SinvRcvTrxLineRepository;
import org.srm.purchasecooperation.sinv.domain.service.SinvRcvTrxHeaderDomainService;
import org.srm.purchasecooperation.sinv.domain.service.SinvTrxNodeExectorDomainService;
import org.srm.purchasecooperation.sinv.domain.vo.SinvAfterTrxNodeExectedVo;
import org.srm.purchasecooperation.transaction.domain.entity.RcvTrxLine;
import org.srm.purchasecooperation.transaction.domain.repository.RcvTrxLineRepository;
import org.srm.web.annotation.Tenant;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
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
@Service
@Tenant("SRM-RCWL")
public class RcwlSinvRcvTrxHeaderServiceImpl extends SinvRcvTrxHeaderServiceImpl {
    private static final Logger LOGGER = LoggerFactory.getLogger(RcwlSinvRcvTrxHeaderServiceImpl.class);
    @Autowired
    private SinvRcvTrxHeaderDomainService sinvRcvTrxHeaderDomainService;
    @Autowired
    private RcwlSinvRcvTrxHeaderMapper rcvRcvTrxHeaderMapper;
    @Autowired
    private RcwlSinvRcvTrxSslmRemoteService rcwlSinvRcvTrxSslmRemoteService;
    @Autowired
    private RcwlOrderBillService rcwlOrderBillService;
    @Autowired
    private SinvRcvRecordStrategyMappingRepository sinvRcvRecordStrategyMappingRepository;
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
    private SinvRcvTrxOrderLinkRepository sinvRcvTrxOrderLinkRepository;
    @Autowired
    private RcvChangeRecordRepository rcvChangeRecordRepository;
    @Autowired
    private RcvStrategyLineRepository rcvStrategyLineRepository;
    @Autowired
    private MdmService mdmService;

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
    public SinvRcvTrxHeaderDTO submittedSinv(Long tenantId, SinvRcvTrxHeaderDTO sinvRcvTrxHeaderDTO) {
        if (BaseConstants.Flag.YES.equals(sinvRcvTrxHeaderDTO.getExecuteUpdateFlag())) {
            this.updateSinv(tenantId, sinvRcvTrxHeaderDTO);
        }
        this.adaptorTaskCheckBeforeStatusUpdate(tenantId, "SUBMITTED", sinvRcvTrxHeaderDTO);
        RcvStrategyLine rcvStrategyLine = this.selectRcvNowNodeConfig(tenantId, sinvRcvTrxHeaderDTO.getRcvTrxHeaderId(), (Long)null);
        if ("WFL".equals(((RcvStrategyLine) Optional.ofNullable(rcvStrategyLine).orElse(new RcvStrategyLine())).getApproveRuleCode())) {
            this.sinvRcvTrxHeaderDomainService.submittedSinvToWFL(tenantId, sinvRcvTrxHeaderDTO, rcvStrategyLine);
            return sinvRcvTrxHeaderDTO;
        } else {
            this.submittedSinvNone(tenantId, sinvRcvTrxHeaderDTO, rcvStrategyLine);
            //如果事务订单 sodr_po_line_location表的quantity=net_received_quantity 则调用订单自动更新考评评分
            sinvRcvTrxHeaderDTO.setTenantId(tenantId);
            SinvRcvTrxToKpiAutoPOLineVO sinvRcvTrxToKpiAutoPOLineVO = rcvRcvTrxHeaderMapper.countTrxHeaderByClosedFlag(sinvRcvTrxHeaderDTO);
            if (sinvRcvTrxToKpiAutoPOLineVO != null && (sinvRcvTrxToKpiAutoPOLineVO.getNetReceivedQuantity() == sinvRcvTrxToKpiAutoPOLineVO.getQuantity())) {
                //feign调用自动更新考评评分
                rcwlSinvRcvTrxSslmRemoteService.rcwlORAutoEval(sinvRcvTrxHeaderDTO.getTenantId(), sinvRcvTrxToKpiAutoPOLineVO);
            }
            //循环所有行数据调用接口
            //查询事务头对应的单据类型
            SinvRcvRecordStrategyMapping sinvRcvRecordStrategyMapping = new SinvRcvRecordStrategyMapping();
            sinvRcvRecordStrategyMapping.setRcvTrxHeaderId(sinvRcvTrxHeaderDTO.getRcvTrxHeaderId());
            sinvRcvRecordStrategyMapping = (SinvRcvRecordStrategyMapping)this.sinvRcvRecordStrategyMappingRepository.selectOne(sinvRcvRecordStrategyMapping);
            //送货单时
            if("ASN".equals(sinvRcvRecordStrategyMapping.getOrderTypeCode())){
                List<SinvRcvTrxLineDTO> sinvRcvTrxLineDTOS = sinvRcvTrxHeaderDTO.getSinvRcvTrxLineDTOS();
                for (SinvRcvTrxLineDTO i:sinvRcvTrxLineDTOS){
                    rcwlOrderBillService.sendOrderBillOne(i.getTenantId(),i.getRcvTrxLineId(),"ASN");
                }
            }
            return sinvRcvTrxHeaderDTO;
        }
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
//                BigDecimal percent = this.rcwlSinvRcvTrxLineRepository.selectRententionMoneyPercent(sinvRcvTrxLine.getFromPoHeaderId(), sinvRcvTrxLine.getFromPoLineId(), tenantId);
//
//                if (percent == null) {
//                    percent = new BigDecimal(0);
//                }
                //质保金金额=执行金额（含税）*质保金比例/100
//                BigDecimal retentionMoney = sinvRcvTrxLine.getTaxIncludedAmount().multiply(percent).divide(new BigDecimal(100),4,RoundingMode.HALF_UP);
//                LOGGER.info("质保金："+retentionMoney);
                //将质保金和收货人插入行表
//                this.rcwlSinvRcvTrxLineRepository.insertRetentionMoneyAndReceiver(sinvRcvTrxLine.getRcvTrxLineId(),retentionMoney,sinvRcvTrxLine.getAttributeBigint2(),tenantId);

//                sinvRcvTrxLine.setAttributeDecimal1(retentionMoney);
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
//                BigDecimal percent = this.rcwlSinvRcvTrxLineRepository.selectRententionMoneyPercent(sinvRcvTrxLine.getFromPoHeaderId(), sinvRcvTrxLine.getFromPoLineId(), tenantId);
//                if (percent == null) {
//                    percent = new BigDecimal(0);
//                }
                //质保金金额=执行金额（含税）*质保金比例/100
//                BigDecimal retentionMoney = taxIncludedAmount.multiply(percent).divide(new BigDecimal(100),4,RoundingMode.HALF_UP);
//                LOGGER.info("质保金："+retentionMoney);
//                LOGGER.info("srm-22587-SinvRcvTrxHeaderServiceImpl-updateSinv:taxIncludedAmount[" + taxIncludedAmount + "]");
                //将质保金和收货人插入行表
//                this.rcwlSinvRcvTrxLineRepository.insertRetentionMoneyAndReceiver(sinvRcvTrxLine.getRcvTrxLineId(),retentionMoney,sinvRcvTrxLine.getAttributeBigint2(),tenantId);
//                sinvRcvTrxLine.setAttributeDecimal1(retentionMoney);
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

        //因为保存的时候数据只能传输当前页数据，下一页行质保金可能未计算，现在改为保存完之后再重新计算质保金
        SinvRcvTrxLine sinvRcvTrxLine = new SinvRcvTrxLine();
        List<SinvRcvTrxLine> sinvRcvTrxLineList1 = new ArrayList<>();
        sinvRcvTrxLine.setRcvTrxHeaderId(sinvRcvTrxHeader.getRcvTrxHeaderId());
        List<SinvRcvTrxLine> sinvRcvTrxLineList = this.sinvRcvTrxLineRepository.select(sinvRcvTrxLine);
        sinvRcvTrxLineList.forEach(sinvRcvTrxLine1 -> {
            //质保金比例获取
            BigDecimal percent = this.rcwlSinvRcvTrxLineRepository.selectRententionMoneyPercent(sinvRcvTrxLine1.getFromPoHeaderId(), sinvRcvTrxLine1.getFromPoLineId(), tenantId);
            if (percent == null) {
                percent = new BigDecimal(0);
            }
            if(!ObjectUtils.isEmpty(sinvRcvTrxLine1.getTaxIncludedAmount())) {
                //质保金金额=执行金额（含税）*质保金比例/100
                BigDecimal retentionMoney = sinvRcvTrxLine1.getTaxIncludedAmount().multiply(percent).divide(new BigDecimal(100), 4, RoundingMode.HALF_UP);
                LOGGER.info("质保金：" + retentionMoney);
                sinvRcvTrxLine1.setAttributeDecimal1(retentionMoney);
            }
            sinvRcvTrxLineList1.add(sinvRcvTrxLine1);
        });
        this.sinvRcvTrxLineRepository.batchUpdateOptional(sinvRcvTrxLineList1,new String[]{"attributeDecimal1"});
        return sinvRcvTrxHeaderDTO;
    }

//    @Override
//    @Transactional(
//            rollbackFor = {Exception.class}
//    )
//    public SinvRcvTrxHeaderDTO deletedSinv(Long tenantId, SinvRcvTrxHeaderDTO sinvRcvTrxHeaderDTO) {
//        LOGGER.info("srm-22587-SinvRcvTrxHeaderServiceImpl-deletedSinv:begin");
//        LOGGER.info("srm-22587-SinvRcvTrxHeaderServiceImpl-deletedSinv:sinvRcvTrxHeaderDTO{}", sinvRcvTrxHeaderDTO);
//        this.adaptorTaskCheckBeforeStatusUpdate(tenantId, "DELETED", sinvRcvTrxHeaderDTO);
//        List<SinvRcvTrxLineDTO> sinvRcvTrxLineDTOS = sinvRcvTrxHeaderDTO.getSinvRcvTrxLineDTOS();
//
//        List<SinvRcvTrxLine> sinvRcvTrxLines1 = new ArrayList();
//        //释放
//        List<SinvRcvTrxLine> sinvRcvTrxLines = this.sinvRcvTrxLineRepository.selectByCondition(Condition.builder(SinvRcvTrxLine.class).andWhere(Sqls.custom().andEqualTo("rcvTrxHeaderId", sinvRcvTrxHeaderDTO.getRcvTrxHeaderId()).andEqualTo("tenantId", tenantId)).build());
//        List<SinvRcvTrxLineDTO> sinvRcvTrxLineDTOList = new ArrayList<>();
//        sinvRcvTrxLines.forEach(sinvRcvTrxLine -> {
//            SinvRcvTrxLineDTO sinvRcvTrxLineDTO = new SinvRcvTrxLineDTO();
//            BeanUtils.copyProperties(sinvRcvTrxLine,sinvRcvTrxLineDTO);
//            sinvRcvTrxLineDTOList.add(sinvRcvTrxLineDTO);
//        });
//        sinvRcvTrxHeaderDTO.setSinvRcvTrxLineDTOS(sinvRcvTrxLineDTOList);
//        this.sinvRcvTrxDomainService.plusQuantityOccupy(tenantId, sinvRcvTrxHeaderDTO);
//
//        sinvRcvTrxLineDTOS.forEach((sinvRcvTrxLineDTO) -> {
//            SinvRcvTrxLine sinvRcvTrxLine = new SinvRcvTrxLine();
//            BeanUtils.copyProperties(sinvRcvTrxLineDTO, sinvRcvTrxLine);
//            sinvRcvTrxLine.setTenantId(tenantId);
//            sinvRcvTrxLine.setQuantity(BigDecimal.ZERO);
//            sinvRcvTrxLine.setUpdateQuantity(BigDecimal.ZERO.subtract(sinvRcvTrxLine.getUpdateQuantity()));
//            sinvRcvTrxLine.setTaxIncludedAmount(BigDecimal.ZERO);
//            sinvRcvTrxLine.setUpdateTaxAmount(BigDecimal.ZERO.subtract(sinvRcvTrxLine.getUpdateTaxAmount()));
//            sinvRcvTrxLines1.add(sinvRcvTrxLine);
//            sinvRcvTrxLineDTO.setUpdateQuantity(BigDecimal.ZERO.subtract(sinvRcvTrxLine.getUpdateQuantity()));
//            sinvRcvTrxLineDTO.setQuantity(BigDecimal.ZERO);
//            sinvRcvTrxLineDTO.setUpdateTaxAmount(BigDecimal.ZERO.subtract(sinvRcvTrxLine.getUpdateTaxAmount()));
//            sinvRcvTrxLineDTO.setTaxIncludedAmount(BigDecimal.ZERO);
//        });
//        this.sinvRcvTrxLineRepository.batchUpdateByPrimaryKeySelective(sinvRcvTrxLines1);
//
//        List<SinvRcvRecordStrategyMapping> sinvRcvRecordStrategyMappings = this.sinvRcvRecordStrategyMappingRepository.selectByCondition(Condition.builder(SinvRcvRecordStrategyMapping.class).andWhere(Sqls.custom().andEqualTo("rcvTrxHeaderId", sinvRcvTrxHeaderDTO.getRcvTrxHeaderId())).build());
//        this.sinvRcvRecordStrategyMappingRepository.batchDeleteByPrimaryKey(sinvRcvRecordStrategyMappings);
//        this.sinvRcvTrxLineRepository.batchDeleteByPrimaryKey(sinvRcvTrxLines);
//        this.sinvRcvTrxHeaderRepository.deleteByPrimaryKey(sinvRcvTrxHeaderDTO.getRcvTrxHeaderId());
//        LOGGER.info("srm-22587-SinvRcvTrxHeaderServiceImpl-deletedSinv:end");
//        return sinvRcvTrxHeaderDTO;
//    }
}
