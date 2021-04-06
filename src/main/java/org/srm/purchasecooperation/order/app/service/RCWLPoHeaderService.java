package org.srm.purchasecooperation.order.app.service;

import org.srm.purchasecooperation.order.api.dto.PoDTO;

/**
 * @author bin.zhang
 */
public interface RCWLPoHeaderService {
    /**
     * 自动生成物料编码
     * @param poDTO
     * @param tenantId
     */
    void insertItemCode(PoDTO poDTO, Long tenantId);
}
