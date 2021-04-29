package org.srm.purchasecooperation.cux.sinv.app.service.impl;

import org.hzero.core.base.BaseConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.srm.purchasecooperation.cux.transaction.api.dto.RcwlOrderBillDTO;
import org.srm.purchasecooperation.cux.transaction.app.service.RcwlOrderBillService;
import org.srm.purchasecooperation.cux.transaction.infra.mapper.RcwlOrderBillMapper;
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
    private RcwlOrderBillService rcwlOrderBillService;
    @Autowired
    private SinvRcvRecordStrategyMappingRepository sinvRcvRecordStrategyMappingRepository;

    @Override
    @Transactional
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
}
