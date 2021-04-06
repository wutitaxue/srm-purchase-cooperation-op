package org.srm.purchasecooperation.order.infra.mapper;

import org.apache.ibatis.annotations.Param;
import org.srm.purchasecooperation.order.api.dto.PoLineDetailDTO;
import org.srm.purchasecooperation.order.api.dto.RCWLPoLineDetailDTO;
import org.srm.web.annotation.Tenant;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
 * @author bin.zhang
 */

public interface RCWLPoLineMapper{
    /**
     * 新加字段需求物料描述查询
     * @param tenantId
     * @param poHeaderId
     * @param nowDate
     * @return
     */
    List<RCWLPoLineDetailDTO> listLineDetail1(@Param("tenantId") Long tenantId, @Param("poHeaderId") Long poHeaderId, @Param("nowDate") Date nowDate);

}
