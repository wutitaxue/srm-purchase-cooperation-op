package org.srm.purchasecooperation.cux.pr.infra.mapper;

import org.springframework.stereotype.Component;
import org.srm.purchasecooperation.cux.order.api.dto.RCWLPoLineDetailDTO;

import java.util.List;

/**
 * @author:pengxu.zhi@hand-china.com
 * @createTime:2021/1/02 11:09
 */
@Component
public interface RcwlPoToBpmMapper {

    /**
     * 查询待推送bpm的订单行数据
     * @param tenantId
     * @param poHeaderId
     * @return
     */
    List<RCWLPoLineDetailDTO> selectPoTobpmline(Long tenantId, Long poHeaderId);

    /**
     * 获取订单类型名称
     * @param tenantId
     * @param orderTypeId
     * @return
     */
    String selectOrderTypeName(Long tenantId, Long orderTypeId);

    /**
     * 获取采购组织名称
     * @param tenantId
     * @param purchaseOrgId
     * @return
     */
    String selectEsPurchaseOrgName(Long tenantId, Long purchaseOrgId);
}
