package org.srm.purchasecooperation.cux.sinv.app.service.impl;

import org.hzero.core.base.BaseConstants;
import org.hzero.mybatis.domian.Condition;
import org.hzero.mybatis.util.Sqls;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.srm.purchasecooperation.asn.app.service.SendMessageToPrService;
import org.srm.purchasecooperation.cux.transaction.api.dto.RcwlOrderBillDTO;
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

@Service
@Tenant("SRM-RCWL")
public class RcwlSinvRcvTrxHeaderServiceImpl extends SinvRcvTrxHeaderServiceImpl {

    @Autowired
    private SinvRcvTrxHeaderDomainService sinvRcvTrxHeaderDomainService;
    @Autowired
    private RcwlOrderBillService rcwlOrderBillService;
    @Autowired
    private SinvRcvRecordStrategyMappingRepository sinvRcvRecordStrategyMappingRepository;
    @Autowired
    private SinvTrxNodeExectorDomainService sinvRcvTrxDomainService;
    @Autowired
    private SendMessageToPrService sendMessageToPrService;
    @Autowired
    private SinvRcvTrxHeaderRepository sinvRcvTrxHeaderRepository;
    @Autowired
    private SinvRcvTrxLineRepository sinvRcvTrxLineRepository;
    @Autowired
    private PoHeaderRepository poHeaderRepository;
    @Autowired
    private RcvTrxLineRepository rcvTrxLineRepository;
    @Autowired
    private RcwlOrderBillMapper rcwlOrderBillMapper;

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
    public void submittedSinvNone(Long tenantId, SinvRcvTrxHeaderDTO sinvRcvTrxHeaderDTO, RcvStrategyLine rcvStrategyLine) {
        Assert.notNull(rcvStrategyLine, "error.data_exists");
        SinvRcvTrxHeader sinvRcvTrxHeader = (SinvRcvTrxHeader)this.sinvRcvTrxHeaderRepository.selectByPrimaryKey(sinvRcvTrxHeaderDTO.getRcvTrxHeaderId());
        sinvRcvTrxHeader.setRcvStatusCode("40_FINISHED");
        this.sinvRcvTrxHeaderRepository.updateOptional(sinvRcvTrxHeader, new String[]{"rcvStatusCode"});
        List<SinvAfterTrxNodeExectedVo> sinvAfterTrxNodeExectedVos = new ArrayList();
        SinvAfterTrxNodeExectedVo sinvAfterTrxNodeExectedVo = new SinvAfterTrxNodeExectedVo();
        List<SinvRcvTrxLine> sinvRcvTrxLines = this.sinvRcvTrxLineRepository.selectByCondition(Condition.builder(SinvRcvTrxLine.class).andWhere(Sqls.custom().andEqualTo("rcvTrxHeaderId", sinvRcvTrxHeaderDTO.getRcvTrxHeaderId()).andEqualTo("tenantId", tenantId)).build());
        SinvRcvRecordStrategyMapping sinvRcvRecordStrategyMapping = new SinvRcvRecordStrategyMapping();
        sinvRcvRecordStrategyMapping.setRcvTrxHeaderId(sinvRcvTrxHeaderDTO.getRcvTrxHeaderId());
        sinvRcvRecordStrategyMapping = (SinvRcvRecordStrategyMapping)this.sinvRcvRecordStrategyMappingRepository.selectOne(sinvRcvRecordStrategyMapping);
        PoHeader poHeader = (PoHeader)Optional.ofNullable(this.poHeaderRepository.selectByPrimaryKey(((SinvRcvTrxLine)sinvRcvTrxLines.get(0)).getFromPoHeaderId())).orElse(new PoHeader());
        if ("E-COMMERCE".equals(poHeader.getPoSourcePlatform())) {
            sinvRcvRecordStrategyMapping.setOrderTypeCode("ASN");
        }

        sinvAfterTrxNodeExectedVo.setSinvRcvTrxLineList(sinvRcvTrxLines);
        sinvAfterTrxNodeExectedVo.setStrategyLineId(rcvStrategyLine.getStrategyLineId());
        sinvAfterTrxNodeExectedVo.setOrderTypeCode(sinvRcvRecordStrategyMapping.getOrderTypeCode());
        sinvAfterTrxNodeExectedVo.setFromStrategyLineId(rcvStrategyLine.getStrategyLineId());
        sinvAfterTrxNodeExectedVo.setToStrategyLineId(rcvStrategyLine.getStrategyLineId());
        sinvAfterTrxNodeExectedVos.add(sinvAfterTrxNodeExectedVo);
        this.sinvRcvTrxDomainService.afterTrxNodeExected(sinvAfterTrxNodeExectedVos);
        this.sitfSinvOut(tenantId, sinvRcvTrxHeaderDTO);
        //循环行新增品类需要推送资产时不新增结算
        RcvTrxLine rcvTrxLine = new RcvTrxLine();
        rcvTrxLine.setTenantId(tenantId);
        rcvTrxLine.setRcvTrxHeaderId(sinvRcvTrxHeaderDTO.getRcvTrxHeaderId());
        List<RcvTrxLine> RcvTrxLines = rcvTrxLineRepository.select(rcvTrxLine);
        RcvTrxLines.forEach(item -> {
            String sendToEas = rcwlOrderBillMapper.selectCategory(tenantId, item.getRcvTrxLineId());
            if (sendToEas == "0") {
                List<Long> RcvTrxLineIdlist = new ArrayList<>();
                RcvTrxLineIdlist.add(item.getRcvTrxLineId());
                this.syncRcvTrxLineSettle(tenantId, null, RcvTrxLineIdlist, "NEW", false);
            }
        });
        this.sinvEcRcvTrxSendMQ(tenantId, sinvRcvTrxHeader);
        List<Long> lineIdList = (List)sinvRcvTrxLines.stream().map(SinvRcvTrxLine::getRcvTrxLineId).collect(Collectors.toList());
        this.sendMessageToPrService.sendTrxMessageToPr(tenantId, Collections.singletonList(sinvRcvTrxHeader.getRcvTrxHeaderId()), lineIdList, BaseConstants.Flag.NO);
    }
}
