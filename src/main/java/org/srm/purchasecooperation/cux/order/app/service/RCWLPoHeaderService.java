package org.srm.purchasecooperation.cux.order.app.service;

import org.srm.purchasecooperation.order.api.dto.PoDTO;

public interface RCWLPoHeaderService {
    /**
     * 自动生成物料编码
     * @param poDTO
     * @param tenantId
     */
    void insertItemCode(PoDTO poDTO, Long tenantId);
}
