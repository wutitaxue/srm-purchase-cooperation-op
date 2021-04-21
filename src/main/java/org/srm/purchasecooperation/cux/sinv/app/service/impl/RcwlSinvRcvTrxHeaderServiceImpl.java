package org.srm.purchasecooperation.cux.sinv.app.service.impl;

import io.choerodon.core.exception.CommonException;
import org.hzero.core.base.BaseConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.srm.purchasecooperation.cux.sinv.domain.vo.SinvRcvTrxToKpiAutoPOLineVO;
import org.srm.purchasecooperation.cux.sinv.infra.feign.RcwlSinvRcvTrxSslmRemoteService;
import org.srm.purchasecooperation.cux.sinv.infra.mapper.RcwlSinvRcvTrxHeaderMapper;
import org.srm.purchasecooperation.sinv.api.dto.SinvRcvTrxHeaderDTO;
import org.srm.purchasecooperation.sinv.app.service.impl.SinvRcvTrxHeaderServiceImpl;
import org.srm.purchasecooperation.sinv.domain.entity.RcvStrategyLine;
import org.srm.purchasecooperation.sinv.domain.service.SinvRcvTrxHeaderDomainService;

import java.util.Optional;

public class RcwlSinvRcvTrxHeaderServiceImpl extends SinvRcvTrxHeaderServiceImpl {

    @Autowired
    private SinvRcvTrxHeaderDomainService sinvRcvTrxHeaderDomainService;
    @Autowired
    private RcwlSinvRcvTrxHeaderMapper rcvRcvTrxHeaderMapper;
    @Autowired
    private RcwlSinvRcvTrxSslmRemoteService rcwlSinvRcvTrxSslmRemoteService;

    private static final Logger LOGGER = LoggerFactory.getLogger(SinvRcvTrxHeaderServiceImpl.class);
    @Override
    public SinvRcvTrxHeaderDTO submittedSinv(Long tenantId, SinvRcvTrxHeaderDTO sinvRcvTrxHeaderDTO) {
        LOGGER.info("srm-22587-SinvRcvTrxHeaderServiceImpl-submittedSinv:begin");
        LOGGER.info("srm-22587-SinvRcvTrxHeaderServiceImpl-submittedSinv:sinvRcvTrxHeaderDTO{}", sinvRcvTrxHeaderDTO);
        if (BaseConstants.Flag.YES.equals(sinvRcvTrxHeaderDTO.getExecuteUpdateFlag())) {
            this.updateSinv(tenantId, sinvRcvTrxHeaderDTO);
        }

        this.adaptorTaskCheckBeforeStatusUpdate(tenantId, "SUBMITTED", sinvRcvTrxHeaderDTO);
        RcvStrategyLine rcvStrategyLine = this.selectRcvNowNodeConfig(tenantId, sinvRcvTrxHeaderDTO.getRcvTrxHeaderId(), (Long)null);
        if ("WFL".equals(((RcvStrategyLine) Optional.ofNullable(rcvStrategyLine).orElse(new RcvStrategyLine())).getApproveRuleCode())) {
            LOGGER.debug("21424-submittedSinv-need-wfl");
            this.sinvRcvTrxHeaderDomainService.submittedSinvToWFL(tenantId, sinvRcvTrxHeaderDTO, rcvStrategyLine);
            return sinvRcvTrxHeaderDTO;
        } else {
            this.submittedSinvNone(tenantId, sinvRcvTrxHeaderDTO, rcvStrategyLine);
            //如果事务订单 sodr_po_header.closed_flag=1 则调用订单自动更新考评评分
            SinvRcvTrxToKpiAutoPOLineVO sinvRcvTrxToKpiAutoPOLineVO = rcvRcvTrxHeaderMapper.countTrxHeaderByClosedFlag(sinvRcvTrxHeaderDTO);
            if (sinvRcvTrxToKpiAutoPOLineVO != null) {
                //feign调用自动更新考评评分
                rcwlSinvRcvTrxSslmRemoteService.rcwlORAutoEval(sinvRcvTrxHeaderDTO.getTenantId(),sinvRcvTrxToKpiAutoPOLineVO);
            }
            return sinvRcvTrxHeaderDTO;
        }
    }
}
