package org.srm.purchasecooperation.cux.order.infra.mapper;

import org.apache.ibatis.annotations.Param;
import org.srm.purchasecooperation.order.api.dto.PoHeaderDetailDTO;
import org.srm.purchasecooperation.order.api.dto.PoHeaderSingleReferenceDTO;
import org.srm.purchasecooperation.order.domain.entity.PoHeader;
import org.srm.purchasecooperation.order.domain.vo.PoHeaderSingleReferenceVO;

import java.util.List;


public interface RcwlMyPoHeaderMapper {

    PoHeaderDetailDTO rcwlSelectHeaderdetail(Long tenantId, Long poHeaderId);

    String rcwlSelect (Long poHeaderId);

    List<PoHeaderSingleReferenceVO> selectPrHeader(@Param("tenantId") Long tenantId, @Param("dto") PoHeaderSingleReferenceDTO referenceDTO);

    List<PoHeader> selectPoHeader(PoHeader poHeader);

}
