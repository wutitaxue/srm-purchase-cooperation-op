package org.srm.purchasecooperation.cux.order.infra.mapper;

import org.srm.purchasecooperation.order.api.dto.PoHeaderDetailDTO;
import org.srm.purchasecooperation.order.domain.entity.PoHeader;

import java.util.List;

public interface RcwlMyPoHeaderMapper {

    String rcwlSelect(Long poHeaderId);
}
