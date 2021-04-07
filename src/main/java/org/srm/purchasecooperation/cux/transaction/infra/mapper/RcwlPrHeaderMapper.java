package org.srm.purchasecooperation.cux.transaction.infra.mapper;

import org.srm.purchasecooperation.pr.infra.mapper.PrHeaderMapper;
import org.srm.purchasecooperation.sinv.app.service.dto.PcHeaderDetailDTO;

public interface RcwlPrHeaderMapper extends PrHeaderMapper {
    PcHeaderDetailDTO selectPcHeaderDetailByPcNum(String pcNum, Long tenantId);

}
