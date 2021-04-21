package org.srm.purchasecooperation.cux.sinv.infra.mapper;

import org.apache.ibatis.annotations.Param;
import org.srm.purchasecooperation.cux.sinv.domain.vo.SinvRcvTrxToKpiAutoPOLineVO;
import org.srm.purchasecooperation.sinv.api.dto.SinvRcvTrxHeaderDTO;

public interface RcwlSinvRcvTrxHeaderMapper {

    SinvRcvTrxToKpiAutoPOLineVO countTrxHeaderByClosedFlag (@Param("sinvRcvTrxHeaderDTO") SinvRcvTrxHeaderDTO sinvRcvTrxHeaderDTO);
}
