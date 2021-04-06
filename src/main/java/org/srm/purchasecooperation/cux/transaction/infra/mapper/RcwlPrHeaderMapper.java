package org.srm.purchasecooperation.cux.transaction.infra.mapper;

import org.srm.purchasecooperation.sinv.app.service.dto.PcHeaderDetailDTO;

public interface RcwlPrHeaderMapper {
    PcHeaderDetailDTO selectPcHeaderDetailByPcNum(String pcNum);
}
