package org.srm.purchasecooperation.cux.pr.infra.mapper;

import org.apache.ibatis.annotations.Param;
import org.srm.purchasecooperation.cux.order.domain.vo.RCWLItemInfoVO;
import org.srm.purchasecooperation.order.domain.entity.PoLine;

public interface RcwlCheckPoLineMapper {
    PoLine checkPoItem(@Param("productNum") String productNum, @Param("tenantId") Long tenantId);
}
