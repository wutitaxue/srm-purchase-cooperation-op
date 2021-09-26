package org.srm.purchasecooperation.cux.sinv.app.service.impl;

import org.activiti.engine.impl.util.CollectionUtil;
import org.hzero.boot.platform.lov.annotation.ProcessLovValue;
import org.hzero.core.base.BaseConstants;
import org.hzero.mybatis.domian.Condition;
import org.hzero.mybatis.util.Sqls;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.srm.purchasecooperation.asn.app.service.SendMessageToPrService;
import org.srm.purchasecooperation.common.infra.mapper.TenantMapper;
import org.srm.purchasecooperation.cux.sinv.domain.vo.SinvRcvTrxToKpiAutoPOLineVO;
import org.srm.purchasecooperation.cux.sinv.infra.feign.RcwlSinvRcvTrxSslmRemoteService;
import org.srm.purchasecooperation.cux.sinv.infra.mapper.RcwlSinvRcvTrxHeaderMapper;
import org.srm.purchasecooperation.cux.sinv.infra.mapper.SettleMapper;
import org.srm.purchasecooperation.cux.transaction.app.service.RcwlOrderBillService;
import org.srm.purchasecooperation.cux.transaction.infra.mapper.RcwlOrderBillMapper;
import org.srm.purchasecooperation.order.api.dto.PoLineDetailDTO;
import org.srm.purchasecooperation.order.domain.entity.PoHeader;
import org.srm.purchasecooperation.order.domain.repository.PoHeaderRepository;
import org.srm.purchasecooperation.order.infra.convertor.CommonConvertor;
import org.srm.purchasecooperation.sinv.api.dto.*;
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
import org.srm.purchasecooperation.sinv.domain.vo.*;
import org.srm.purchasecooperation.sinv.infra.mapper.SinvRcvTrxLineMapper;
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
    @Autowired
    private SinvRcvTrxLineMapper sinvRcvTrxLineMapper;
    @Autowired
    private TenantMapper tenantMapper;
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

                taxIncludedAmount = this.calcQuantity(sinvRcvTrxLine.getTaxIncludedAmount(), sinvRcvTrxLine.getUnitPriceBatch(), sinvRcvTrxLine.getTaxIncludedPrice(), 6, sinvRcvTrxHeaderDTO.getOrderTypeCode(), sinvRcvTrxLine.getPayRatio());
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


    @Override
    @Transactional(
            rollbackFor = {Exception.class}
    )
    public SinvRcvTrxHeaderDTO deletedSinv(Long tenantId, SinvRcvTrxHeaderDTO sinvRcvTrxHeaderDTO) {
        LOGGER.info("srm-22587-SinvRcvTrxHeaderServiceImpl-deletedSinv:begin");
        LOGGER.info("srm-22587-SinvRcvTrxHeaderServiceImpl-deletedSinv:sinvRcvTrxHeaderDTO{}", sinvRcvTrxHeaderDTO);
        this.adaptorTaskCheckBeforeStatusUpdate(tenantId, "DELETED", sinvRcvTrxHeaderDTO);
        List<SinvRcvTrxLineDTO> sinvRcvTrxLineDTOS = this.sinvRcvTrxLineMapper.listRcvTrxLineDetail(tenantId, sinvRcvTrxHeaderDTO.getRcvTrxHeaderId());
        sinvRcvTrxHeaderDTO.setSinvRcvTrxLineDTOS(sinvRcvTrxLineDTOS);
        List<SinvRcvTrxLine> sinvRcvTrxLines1 = new ArrayList();
        sinvRcvTrxLineDTOS.forEach((sinvRcvTrxLineDTO) -> {
            SinvRcvTrxLine sinvRcvTrxLine = new SinvRcvTrxLine();
            BeanUtils.copyProperties(sinvRcvTrxLineDTO, sinvRcvTrxLine);
            sinvRcvTrxLine.setTenantId(tenantId);
            sinvRcvTrxLine.setQuantity(BigDecimal.ZERO);
            sinvRcvTrxLine.setUpdateQuantity(BigDecimal.ZERO.subtract(sinvRcvTrxLine.getUpdateQuantity()));
            sinvRcvTrxLine.setTaxIncludedAmount(BigDecimal.ZERO);
            sinvRcvTrxLine.setUpdateTaxAmount(BigDecimal.ZERO.subtract(sinvRcvTrxLine.getUpdateTaxAmount()));
            sinvRcvTrxLines1.add(sinvRcvTrxLine);
            sinvRcvTrxLineDTO.setUpdateQuantity(BigDecimal.ZERO.subtract(sinvRcvTrxLine.getUpdateQuantity()));
            sinvRcvTrxLineDTO.setQuantity(BigDecimal.ZERO);
            sinvRcvTrxLineDTO.setUpdateTaxAmount(BigDecimal.ZERO.subtract(sinvRcvTrxLine.getUpdateTaxAmount()));
            sinvRcvTrxLineDTO.setTaxIncludedAmount(BigDecimal.ZERO);
        });
        this.sinvRcvTrxLineRepository.batchUpdateByPrimaryKeySelective(sinvRcvTrxLines1);
        this.sinvRcvTrxDomainService.plusQuantityOccupy(tenantId, sinvRcvTrxHeaderDTO);
        List<SinvRcvTrxLine> sinvRcvTrxLines = this.sinvRcvTrxLineRepository.selectByCondition(Condition.builder(SinvRcvTrxLine.class).andWhere(Sqls.custom().andEqualTo("rcvTrxHeaderId", sinvRcvTrxHeaderDTO.getRcvTrxHeaderId()).andEqualTo("tenantId", tenantId)).build());
        List<SinvRcvRecordStrategyMapping> sinvRcvRecordStrategyMappings = this.sinvRcvRecordStrategyMappingRepository.selectByCondition(Condition.builder(SinvRcvRecordStrategyMapping.class).andWhere(Sqls.custom().andEqualTo("rcvTrxHeaderId", sinvRcvTrxHeaderDTO.getRcvTrxHeaderId())).build());
        this.sinvRcvRecordStrategyMappingRepository.batchDeleteByPrimaryKey(sinvRcvRecordStrategyMappings);
        this.sinvRcvTrxLineRepository.batchDeleteByPrimaryKey(sinvRcvTrxLines);
        this.sinvRcvTrxHeaderRepository.deleteByPrimaryKey(sinvRcvTrxHeaderDTO.getRcvTrxHeaderId());
        LOGGER.info("srm-22587-SinvRcvTrxHeaderServiceImpl-deletedSinv:end");
        return sinvRcvTrxHeaderDTO;
    }

    @Override
    public SinvRcvTrxHeaderDTO waitintToDoSinvRcv(Long tenantId, Long rcvTrxTypeId, List<SinvRcvTrxWaitingDTO> sinvRcvTrxWaitingDTOList) {
        LOGGER.info("srm-22587-SinvRcvTrxHeaderServiceImpl-waitintToDoSinvRcv:begin");
        LOGGER.info("srm-22587-SinvRcvTrxHeaderServiceImpl-waitintToDoSinvRcv:rcvTrxTypeId{}", rcvTrxTypeId);
        LOGGER.info("srm-22587-SinvRcvTrxHeaderServiceImpl-waitintToDoSinvRcv:sinvRcvTrxWaitingDTOList{}", sinvRcvTrxWaitingDTOList);
        Long strategyLineId = ((SinvRcvTrxWaitingDTO)sinvRcvTrxWaitingDTOList.get(0)).getStrategyLineId();
        List<InitSinvRcvTrxDataVO> initSinvRcvTrxDataVOList = new ArrayList();
        org.srm.common.client.entity.Tenant tenant = TenantInfoHelper.selectByTenantId(tenantId);

        try {
            Map<String, Object> map = new HashMap();
            map.put("sinvRcvTrxWaitingDTOs", sinvRcvTrxWaitingDTOList);
            LOGGER.debug("SINV_CHECK_RCV_TRX_SAVE 适配器入参:{}", JSON.toJSON(map));
            AdaptorTaskHelper.executeAdaptorTask("SINV_CHECK_RCV_TRX_SAVE", tenant.getTenantNum(), JSON.toJSON(map));
        } catch (TaskNotExistException var12) {
            LOGGER.debug("============SINV_CHECK_RCV_TRX_SAVE-TaskNotExistException=============={}", new Object[]{tenant.getTenantNum(), var12.getMessage(), var12.getStackTrace()});
        }

        sinvRcvTrxWaitingDTOList.forEach((sinvRcvTrxWaitingDTO) -> {
            SmdmCurrencyDTO smdmCurrencyDTO = this.mdmService.selectSmdmCurrencyDto(tenantId, sinvRcvTrxWaitingDTO.getCurrencyCode());
            int financialPrecision = smdmCurrencyDTO.getFinancialPrecision();
            if (!strategyLineId.equals(sinvRcvTrxWaitingDTO.getStrategyLineId())) {
                throw new CommonException("sinv.same.strategy.error", new Object[0]);
            } else {
                BigDecimal taxIncludedAmount;
                if ("AMOUNT".equals(sinvRcvTrxWaitingDTO.getSubjectType())) {
                    if (Objects.isNull(sinvRcvTrxWaitingDTO.getTaxIncludedAmount())) {
                        throw new CommonException("sinv.quantity.or.amount.null.error", new Object[0]);
                    }

                    if (sinvRcvTrxWaitingDTO.getTaxIncludedAmount().compareTo(sinvRcvTrxWaitingDTO.getLeftTaxAmount()) == BaseConstants.Flag.YES) {
                        throw new CommonException("sinv.quantity.or.amount.more.than.error", new Object[0]);
                    }

                    taxIncludedAmount = this.calcQuantity(sinvRcvTrxWaitingDTO.getTaxIncludedAmount(), sinvRcvTrxWaitingDTO.getUnitPriceBatch(), sinvRcvTrxWaitingDTO.getTaxIncludedPrice(), 6, sinvRcvTrxWaitingDTO.getSourceOrderType(), sinvRcvTrxWaitingDTO.getPayRatio());
                    LOGGER.info("srm-22587-SinvRcvTrxHeaderServiceImpl-waitintToDoSinvRcv:quantity[" + taxIncludedAmount + "]");
                    sinvRcvTrxWaitingDTO.setQuantity(taxIncludedAmount);
                    BigDecimal rate = (new BigDecimal(100)).add(sinvRcvTrxWaitingDTO.getTaxRate()).divide(new BigDecimal(100));
                    sinvRcvTrxWaitingDTO.setNetAmount(sinvRcvTrxWaitingDTO.getTaxIncludedAmount().divide(rate, financialPrecision, RoundingMode.HALF_UP));
                } else if ("QUANTITY".equals(sinvRcvTrxWaitingDTO.getSubjectType())) {
                    if (Objects.isNull(sinvRcvTrxWaitingDTO.getQuantity())) {
                        throw new CommonException("sinv.quantity.or.amount.null.error", new Object[0]);
                    }

                    if (sinvRcvTrxWaitingDTO.getQuantity().compareTo(sinvRcvTrxWaitingDTO.getLeftQuantity()) == BaseConstants.Flag.YES) {
                        throw new CommonException("sinv.quantity.or.amount.more.than.error", new Object[0]);
                    }

                    taxIncludedAmount = this.calcTaxIncludedAmount(sinvRcvTrxWaitingDTO.getQuantity(), sinvRcvTrxWaitingDTO.getTaxIncludedPrice(), sinvRcvTrxWaitingDTO.getUnitPriceBatch(), financialPrecision, sinvRcvTrxWaitingDTO.getSourceOrderType(), sinvRcvTrxWaitingDTO.getPayRatio());
                    LOGGER.info("srm-22587-SinvRcvTrxHeaderServiceImpl-waitintToDoSinvRcv:taxIncludedAmount[" + taxIncludedAmount + "]");
                    sinvRcvTrxWaitingDTO.setTaxIncludedAmount(taxIncludedAmount);
                    sinvRcvTrxWaitingDTO.setNetAmount(sinvRcvTrxWaitingDTO.getQuantity().multiply(sinvRcvTrxWaitingDTO.getNetPrice()).divide(sinvRcvTrxWaitingDTO.getUnitPriceBatch(), 8, RoundingMode.HALF_UP).setScale(financialPrecision, RoundingMode.HALF_UP));
                }

                PoLineDetailDTO poLineDetailDTO = this.sinvRcvTrxLineMapper.queryPoLineDetailByLineId(sinvRcvTrxWaitingDTO.getFromPoLineId());
                if (Objects.nonNull(poLineDetailDTO)) {
                    sinvRcvTrxWaitingDTO.setCostId(poLineDetailDTO.getCostId());
                }

                InitSinvRcvTrxDataVO initSinvRcvTrxDataVO = (InitSinvRcvTrxDataVO) CommonConvertor.beanConvert(InitSinvRcvTrxDataVO.class, sinvRcvTrxWaitingDTO);
                initSinvRcvTrxDataVO.setRcvTrxTypeId(rcvTrxTypeId);
                initSinvRcvTrxDataVOList.add(initSinvRcvTrxDataVO);
            }
        });
        String tenantNum = this.tenantMapper.queryTenantNumById(tenantId);

        try {
            AdaptorTaskHelper.executeAdaptorTask("SINV_WAITING_TO_DO_RCV_TRX", tenantNum, Collections.singletonMap("data", initSinvRcvTrxDataVOList));
        } catch (TaskNotExistException var11) {
            LOGGER.debug("============WAITING_TO_DO_RCV_TRX-TaskNotExistException=============={}", tenantNum);
        }

        RcvStrategyLine rcvStrategyLine = new RcvStrategyLine();
        LOGGER.info("srm-22587-SinvRcvTrxHeaderServiceImpl-waitintToDoSinvRcv:getNextStrategyLineId{}", ((SinvRcvTrxWaitingDTO)sinvRcvTrxWaitingDTOList.get(0)).getNextStrategyLineId());
        SinvStrategyConnectVo sinvStrategyConnectVo = this.rcvStrategyLineDomainService.getStrategyConnectByStrategyLine(((SinvRcvTrxWaitingDTO)sinvRcvTrxWaitingDTOList.get(0)).getNextStrategyLineId());
        if (sinvStrategyConnectVo == null) {
            throw new CommonException("sinv.have.no.flow.error", new Object[0]);
        } else {
            rcvStrategyLine.setStrategyLineId(sinvStrategyConnectVo.getNowStrategyLineId());
            rcvStrategyLine.setStrategyHeaderId(((SinvRcvTrxWaitingDTO)sinvRcvTrxWaitingDTOList.get(0)).getStrategyHeaderId());
            SinvRcvTrxHeaderDTO sinvRcvTrxHeaderDTO = this.sinvToRcvTrxOperation(tenantId, initSinvRcvTrxDataVOList, rcvStrategyLine);
            this.sinvRcvTrxDomainService.plusQuantityOccupy(tenantId, sinvRcvTrxHeaderDTO);
            LOGGER.info("srm-22587-SinvRcvTrxHeaderServiceImpl-waitintToDoSinvRcv:end");
            return sinvRcvTrxHeaderDTO;
        }
    }

    @Override
    public List<SinvRcvTrxHeaderDTO> sinvEntrence(Long tenantId, String sourceOrderType, Long strategyHeaderId, List<SinvRcvTrxOrderLinkVO> sinvRcvTrxOrderLinkVOS) {
        LOGGER.info("srm-22587-SinvRcvTrxHeaderServiceImpl-sinvEntrence:begin{}", strategyHeaderId);
        LOGGER.info("srm-22587-SinvRcvTrxHeaderServiceImpl-sinvEntrence:sinvRcvTrxOrderLinkVOS{}", sinvRcvTrxOrderLinkVOS);
        if (CollectionUtil.isEmpty(sinvRcvTrxOrderLinkVOS)) {
            throw new CommonException("sinv.data.source.vo.null.error", new Object[0]);
        } else {
            List<SinvRcvTrxHeaderDTO> sinvRcvTrxHeaderDTOList = new ArrayList();
            RcvStrategyLine rcvStrategyLine = this.strategyLineRepository.selectLinesBySourceOrderType(tenantId, strategyHeaderId);
            if ("ORDER".equals(sourceOrderType)) {
                sinvRcvTrxHeaderDTOList = this.poToRcvTrxOperation(tenantId, sourceOrderType, sinvRcvTrxOrderLinkVOS, rcvStrategyLine);
            } else if ("ASN".equals(sourceOrderType)) {
                sinvRcvTrxHeaderDTOList = this.asnToRcvTrxOperation(tenantId, sourceOrderType, sinvRcvTrxOrderLinkVOS, rcvStrategyLine);
            }

            LOGGER.info("srm-22587-SinvRcvTrxHeaderServiceImpl-sinvEntrence:end");
            return (List)sinvRcvTrxHeaderDTOList;
        }
    }

    @Override
    @ProcessLovValue
    protected List<SinvRcvTrxHeaderDTO> poToRcvTrxOperation(Long tenantId, String sourceOrderType, List<SinvRcvTrxOrderLinkVO> sinvRcvTrxOrderLinkVOS, RcvStrategyLine rcvStrategyLine) {
        LOGGER.info("srm-22587-SinvRcvTrxHeaderServiceImpl-PoToRcvTrxOperation:begin");
        LOGGER.info("srm-22587-SinvRcvTrxHeaderServiceImpl-PoToRcvTrxOperation:sinvRcvTrxOrderLinkVOS{}", sinvRcvTrxOrderLinkVOS);
        LOGGER.info("srm-22587-SinvRcvTrxHeaderServiceImpl-PoToRcvTrxOperation:sourceCode{}", sourceOrderType);
        List<SinvRcvTrxHeaderDTO> sinvRcvTrxHeaderDTOList = new ArrayList();
        String sitfOrSrmCode = (String)Optional.ofNullable(((SinvRcvTrxOrderLinkVO)sinvRcvTrxOrderLinkVOS.get(0)).getSitfOrSrmCode()).orElse("SRM");
        List<Long> poHeaderIds = (List)sinvRcvTrxOrderLinkVOS.stream().map(SinvRcvTrxOrderLinkVO::getFromPoHeaderId).distinct().collect(Collectors.toList());
        poHeaderIds.forEach((poHeaderId) -> {
            List<SinvRcvTrxOrderLinkVO> sinvRcvTrxOrderLinks1 = new ArrayList();
            sinvRcvTrxOrderLinkVOS.forEach((sinvRcvTrxOrderLink) -> {
                if (poHeaderId.equals(sinvRcvTrxOrderLink.getFromPoHeaderId())) {
                    sinvRcvTrxOrderLinks1.add(sinvRcvTrxOrderLink);
                }

            });
            Set<Long> poLocationLineIds = (Set)sinvRcvTrxOrderLinks1.stream().map(SinvRcvTrxOrderLinkVO::getFromPoLineLocationId).collect(Collectors.toSet());
            List<InitSinvRcvTrxDataVO> initSinvRcvTrxDataVOS = this.sinvRcvTrxHeaderRepository.toSinvData(tenantId, sourceOrderType, poLocationLineIds);
            LOGGER.info("srm-22587-SinvRcvTrxHeaderServiceImpl-PoToRcvTrxOperation:initSinvRcvTrxDataVOS{}", initSinvRcvTrxDataVOS);
            List<SinvOtherOperateOrderLinkVO> sinvOtherOperateOrderLinkVOList = new ArrayList();
            List<InitSinvRcvTrxDataVO> initSinvRcvTrxDataVOSAct = new ArrayList();
            initSinvRcvTrxDataVOS.stream().forEach((receiveRcvTrxDataVO) -> {
                SinvOtherOperateOrderLinkVO sinvOtherOperateOrderLinkVOTemp;
                if (!ObjectUtils.isEmpty(receiveRcvTrxDataVO.getCheckInitialNodeFlag()) && "SITF".equals(sitfOrSrmCode)) {
                    sinvOtherOperateOrderLinkVOTemp = (SinvOtherOperateOrderLinkVO)CommonConvertor.beanConvert(SinvOtherOperateOrderLinkVO.class, receiveRcvTrxDataVO);
                    sinvOtherOperateOrderLinkVOTemp.setUpdateQuantity(receiveRcvTrxDataVO.getQuantity());
                    sinvOtherOperateOrderLinkVOList.add(sinvOtherOperateOrderLinkVOTemp);
                } else if (!ObjectUtils.isEmpty(receiveRcvTrxDataVO.getSrmCheckFlag()) && "SRM".equals(sitfOrSrmCode)) {
                    sinvOtherOperateOrderLinkVOTemp = (SinvOtherOperateOrderLinkVO)CommonConvertor.beanConvert(SinvOtherOperateOrderLinkVO.class, receiveRcvTrxDataVO);
                    sinvOtherOperateOrderLinkVOTemp.setUpdateQuantity(receiveRcvTrxDataVO.getQuantity());
                    sinvOtherOperateOrderLinkVOList.add(sinvOtherOperateOrderLinkVOTemp);
                } else {
                    receiveRcvTrxDataVO.setAgentId(receiveRcvTrxDataVO.getPoAgentId());
                    receiveRcvTrxDataVO.setReceiveOrderType("ORDER");
                    SinvRcvTrxOrderLinkVO sinvRcvTrxOrderLinks2 = new SinvRcvTrxOrderLinkVO();
                    Iterator var8 = sinvRcvTrxOrderLinkVOS.iterator();

                    while(var8.hasNext()) {
                        SinvRcvTrxOrderLinkVO sinvRcvTrxOrderLink = (SinvRcvTrxOrderLinkVO)var8.next();
                        if (receiveRcvTrxDataVO.getPoLineLocationId().equals(sinvRcvTrxOrderLink.getFromPoLineLocationId())) {
                            sinvRcvTrxOrderLink.setFromPoHeaderId(receiveRcvTrxDataVO.getFromPoHeaderId());
                            sinvRcvTrxOrderLink.setFromPoLineId(receiveRcvTrxDataVO.getFromPoLineId());
                            sinvRcvTrxOrderLink.setFromPoLineLocationId(receiveRcvTrxDataVO.getFromPoLineLocationId());
                            sinvRcvTrxOrderLink.setFromDisplayPoNum(receiveRcvTrxDataVO.getFromDisplayPoNum());
                            sinvRcvTrxOrderLink.setFromDisplayPoLineNum(receiveRcvTrxDataVO.getFromDisplayPoLineNum());
                            sinvRcvTrxOrderLink.setFromDisplayPoLineLocationNum(receiveRcvTrxDataVO.getFromDisplayPoLineLocationNum());
                            sinvRcvTrxOrderLink.setSourceHeaderNum(receiveRcvTrxDataVO.getSourceHeaderNum());
                            sinvRcvTrxOrderLink.setSourceLineNum(receiveRcvTrxDataVO.getSourceLineNum());
                            sinvRcvTrxOrderLink.setStrategyLineId(rcvStrategyLine.getStrategyLineId());
                            sinvRcvTrxOrderLink.setStrategyHeaderId(rcvStrategyLine.getStrategyHeaderId());
                            sinvRcvTrxOrderLinks2 = sinvRcvTrxOrderLink;
                            //判断如果是订单来源且策略类型为AMOUNT 时 ，数量计算获得
                            LOGGER.info("24730============"+sinvRcvTrxOrderLink.toString());
                            LOGGER.info("24730============"+receiveRcvTrxDataVO.toString());
                            if("AMOUNT".equals(rcvStrategyLine.getSubjectType())){
                               if(ObjectUtils.isEmpty(sinvRcvTrxOrderLink.getTaxIncludedAmount())||ObjectUtils.isEmpty(sinvRcvTrxOrderLink.getTaxIncludedPrice())){
                                   BigDecimal quantity1 = receiveRcvTrxDataVO.getTaxIncludedAmount().divide(receiveRcvTrxDataVO.getTaxIncludedPrice()).setScale(6,RoundingMode.HALF_UP);
                                   receiveRcvTrxDataVO.setQuantity(quantity1);
                               }else {
                                   BigDecimal quantity = sinvRcvTrxOrderLink.getTaxIncludedAmount().divide(sinvRcvTrxOrderLink.getTaxIncludedPrice()).setScale(6,RoundingMode.HALF_UP);
                                   receiveRcvTrxDataVO.setQuantity(quantity);
                               }
                            }
                            {
                                receiveRcvTrxDataVO.setQuantity((BigDecimal) Optional.ofNullable(sinvRcvTrxOrderLink.getQuantity()).orElse(receiveRcvTrxDataVO.getQuantity()));
                            }
                            receiveRcvTrxDataVO.setNetPrice((BigDecimal)Optional.ofNullable(sinvRcvTrxOrderLink.getNetPrice()).orElse(receiveRcvTrxDataVO.getNetPrice()));
                            receiveRcvTrxDataVO.setTaxIncludedPrice((BigDecimal)Optional.ofNullable(sinvRcvTrxOrderLink.getTaxIncludedPrice()).orElse(receiveRcvTrxDataVO.getTaxIncludedPrice()));
                            receiveRcvTrxDataVO.setNetAmount((BigDecimal)Optional.ofNullable(sinvRcvTrxOrderLink.getNetAmount()).orElse(receiveRcvTrxDataVO.getNetAmount()));
                            receiveRcvTrxDataVO.setTaxIncludedAmount((BigDecimal)Optional.ofNullable(sinvRcvTrxOrderLink.getTaxIncludedAmount()).orElse(receiveRcvTrxDataVO.getTaxIncludedAmount()));
                            break;
                        }
                    }

                    receiveRcvTrxDataVO.setSinvRcvTrxOrderLinksVO(sinvRcvTrxOrderLinks2);
                    receiveRcvTrxDataVO.setSourceOrderType(sourceOrderType);
                    receiveRcvTrxDataVO.setStrategyLineId(rcvStrategyLine.getStrategyLineId());
                    receiveRcvTrxDataVO.setStrategyHeaderId(rcvStrategyLine.getStrategyHeaderId());
                    initSinvRcvTrxDataVOSAct.add(receiveRcvTrxDataVO);
                }

            });
            if (CollectionUtil.isNotEmpty(initSinvRcvTrxDataVOSAct)) {
                SinvRcvTrxHeaderDTO sinvRcvTrxHeaderDTO = this.sinvRcvTrxDomainService.initTrx(tenantId, initSinvRcvTrxDataVOSAct);
                sinvRcvTrxHeaderDTOList.add(sinvRcvTrxHeaderDTO);
            }

            if (CollectionUtil.isNotEmpty(sinvOtherOperateOrderLinkVOList)) {
                this.sinvDocuAdjustEntrence(tenantId, sourceOrderType, "UPDATE", sinvOtherOperateOrderLinkVOList);
            }

        });
        LOGGER.info("srm-22587-SinvRcvTrxHeaderServiceImpl-PoToRcvTrxOperation:end");
        return sinvRcvTrxHeaderDTOList;
    }
}
