package org.srm.purchasecooperation.cux.sinv.app.service.impl;

import org.hzero.core.base.BaseConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.srm.purchasecooperation.cux.transaction.app.service.RcwlOrderBillService;
import org.srm.purchasecooperation.sinv.api.dto.SinvRcvTrxHeaderDTO;
import org.srm.purchasecooperation.sinv.app.service.impl.SinvRcvTrxHeaderServiceImpl;
import org.srm.purchasecooperation.sinv.domain.entity.RcvStrategyLine;
import org.srm.purchasecooperation.sinv.domain.service.SinvRcvTrxHeaderDomainService;
import org.srm.web.annotation.Tenant;

import java.util.Optional;

@Service
@Tenant("SRM-RCWL")
public class RcwlSinvRcvTrxHeaderServiceImpl extends SinvRcvTrxHeaderServiceImpl {

    @Autowired
    private SinvRcvTrxHeaderDomainService sinvRcvTrxHeaderDomainService;
    @Autowired
    private RcwlOrderBillService rcwlOrderBillService;

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
            rcwlOrderBillService.sendOrderBillOne(rcwlOrderBillDTO);
            return sinvRcvTrxHeaderDTO;
        }
    }
}
