package org.srm.purchasecooperation.cux.sinv.infra.mapper;

import org.srm.purchasecooperation.cux.sinv.domain.vo.SinvRcvTrxToKpiAutoPOLineVO;
import org.srm.purchasecooperation.sinv.api.dto.SinvRcvTrxHeaderDTO;

public interface RcwlSinvRcvTrxHeaderMapper {

    SinvRcvTrxToKpiAutoPOLineVO countTrxHeaderByClosedFlag (SinvRcvTrxHeaderDTO sinvRcvTrxHeaderDTO);
}
