package org.srm.purchasecooperation.cux.order.infra.mapper;

import org.srm.purchasecooperation.order.api.dto.PoHeaderDetailDTO;
import org.srm.purchasecooperation.order.domain.entity.PoHeader;


public interface RcwlMyPoHeaderMapper {
    PoHeaderDetailDTO rcwlSelectHeaderdetail(Long tenantId, Long poHeaderId);

    String rcwlSelect (Long poHeaderId);

}
