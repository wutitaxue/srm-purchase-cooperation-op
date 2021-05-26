package org.srm.purchasecooperation.cux.order.infra.mapper;

import org.apache.ibatis.annotations.Param;
import org.srm.purchasecooperation.order.api.dto.PoLineDetailDTO;

import java.util.Date;
import java.util.List;

/**
 * description
 *
 * @author Zhouzy 2021/05/26 17:35
 */
public interface RcwlPoLineMapper {

    List<PoLineDetailDTO> listLineDetail(@Param("tenantId") Long tenantId, @Param("poHeaderId") Long poHeaderId, @Param("nowDate") Date nowDate);

}
