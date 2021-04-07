package org.srm.purchasecooperation.cux.order.infra.mapper;

import org.apache.ibatis.annotations.Param;
import org.srm.purchasecooperation.cux.order.api.dto.RCWLPoLineDetailDTO;

import java.util.Date;
import java.util.List;

/**
 * @author 15640
 */

public interface RCWLPoLineMapper{
    /**
     * 需求物料描述 二开字段查询
     * @param tenantId
     * @param poHeaderId
     * @param nowDate
     * @return
     */
    List<RCWLPoLineDetailDTO> listLineDetail1(@Param("tenantId") Long tenantId, @Param("poHeaderId") Long poHeaderId, @Param("nowDate") Date nowDate);

}
