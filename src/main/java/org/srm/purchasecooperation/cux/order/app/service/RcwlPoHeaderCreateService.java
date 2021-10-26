package org.srm.purchasecooperation.cux.order.app.service;

import org.srm.purchasecooperation.order.api.dto.ContractResultDTO;
import org.srm.purchasecooperation.order.api.dto.PoDTO;

import java.util.List;

public interface RcwlPoHeaderCreateService {
    /**
     * 根据无价格合同创建订单
     * @param tenantId
     * @param contractResultDTOList
     */
    PoDTO createAnOrderBasedOnContract(Long tenantId, List<ContractResultDTO> contractResultDTOList);
}
