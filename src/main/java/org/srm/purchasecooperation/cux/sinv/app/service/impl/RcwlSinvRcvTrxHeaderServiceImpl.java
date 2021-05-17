package org.srm.purchasecooperation.cux.sinv.app.service.impl;

import org.hzero.core.base.BaseConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.srm.purchasecooperation.cux.sinv.domain.vo.SinvRcvTrxToKpiAutoPOLineVO;
import org.srm.purchasecooperation.cux.sinv.infra.feign.RcwlSinvRcvTrxSslmRemoteService;
import org.srm.purchasecooperation.cux.sinv.infra.mapper.RcwlSinvRcvTrxHeaderMapper;
import org.srm.purchasecooperation.cux.transaction.app.service.RcwlOrderBillService;
import org.srm.purchasecooperation.sinv.api.dto.SinvRcvTrxHeaderDTO;
import org.srm.purchasecooperation.sinv.api.dto.SinvRcvTrxLineDTO;
import org.srm.purchasecooperation.sinv.app.service.impl.SinvRcvTrxHeaderServiceImpl;
import org.srm.purchasecooperation.sinv.domain.entity.RcvStrategyLine;
import org.srm.purchasecooperation.sinv.domain.entity.SinvRcvRecordStrategyMapping;
import org.srm.purchasecooperation.sinv.domain.repository.SinvRcvRecordStrategyMappingRepository;
import org.srm.purchasecooperation.sinv.domain.service.SinvRcvTrxHeaderDomainService;
import org.srm.web.annotation.Tenant;

import java.util.List;
import java.util.Optional;

@Service
@Tenant("SRM-RCWL")
public class RcwlSinvRcvTrxHeaderServiceImpl extends SinvRcvTrxHeaderServiceImpl {

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
}
