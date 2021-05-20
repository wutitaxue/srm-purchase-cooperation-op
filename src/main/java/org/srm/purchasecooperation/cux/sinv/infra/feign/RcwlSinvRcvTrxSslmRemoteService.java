package org.srm.purchasecooperation.cux.sinv.infra.feign;

import org.hzero.starter.keyencrypt.core.Encrypt;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.srm.purchasecooperation.cux.sinv.domain.vo.SinvRcvTrxToKpiAutoPOLineVO;
import org.srm.purchasecooperation.cux.sinv.infra.feign.fallback.RcwlSinvRcvTrxSslmRemoteServiceImpl;

@FeignClient(
        value = "${srm.service.supplier.name:srm-supplier-lifecycle}",
        fallback = RcwlSinvRcvTrxSslmRemoteServiceImpl.class,
        path = "/v1"
)
public interface RcwlSinvRcvTrxSslmRemoteService {
    @PostMapping({"/{organizationId}/eval-headers/Oder-Auto-Eval"})
    SinvRcvTrxToKpiAutoPOLineVO rcwlORAutoEval(@PathVariable("organizationId") Long tenantId, @Encrypt @RequestBody SinvRcvTrxToKpiAutoPOLineVO sinvRcvTrxToKpiAutoPOLineVO);
}
