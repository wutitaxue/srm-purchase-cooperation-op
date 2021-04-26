package org.srm.purchasecooperation.cux.order.infra.mapper;

import org.srm.purchasecooperation.order.api.dto.PoHeaderDetailDTO;

public interface RcwlMyPoHeaderMapper {
    PoHeaderDetailDTO rcwlSelectHeaderdetail(Long tenantId, Long poHeaderId);
}
