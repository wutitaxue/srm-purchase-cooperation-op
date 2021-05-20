package org.srm.purchasecooperation.cux.sinv.infra.feign.fallback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.srm.purchasecooperation.cux.sinv.domain.vo.SinvRcvTrxToKpiAutoPOLineVO;
import org.srm.purchasecooperation.cux.sinv.infra.feign.RcwlSinvRcvTrxSslmRemoteService;

@Service
public class RcwlSinvRcvTrxSslmRemoteServiceImpl implements RcwlSinvRcvTrxSslmRemoteService {
    private static final Logger LOGGER = LoggerFactory.getLogger(RcwlSinvRcvTrxSslmRemoteServiceImpl.class);
    public RcwlSinvRcvTrxSslmRemoteServiceImpl() {
    }
    public SinvRcvTrxToKpiAutoPOLineVO rcwlORAutoEval(Long tenantId, SinvRcvTrxToKpiAutoPOLineVO sinvRcvTrxToKpiAutoPOLineVO) {
        LOGGER.error("25140-Get file failed where sinvRcvTrxToKpiAutoPOLineVO = {} and tenantId = {}", sinvRcvTrxToKpiAutoPOLineVO, tenantId);
        return null;
    }
}
