package org.srm.purchasecooperation.cux.pr.infra.mapper;

import org.apache.ibatis.annotations.Param;
import org.srm.purchasecooperation.cux.order.domain.vo.RCWLItemInfoVO;

public interface RcwlCheckPoLineMapper {
    RCWLItemInfoVO checkPoItem(@Param("productNum") String productNum,@Param("tenantId") Long tenantId);
}
