package org.srm.purchasecooperation.cux.acp.domain.repository;

import org.srm.purchasecooperation.cux.acp.api.dto.RCWLAcpUserDataDTO;

/**
 * @author lu.cheng01@hand-china.com
 * @description
 * @date 2021/4/8 15:47
 * @version:1.0
 */
public interface RCWLAcpUserDataRepository {
    /**
     * 获取发票以及人员数据
     * @param organizationId
     * @return
     */
    public RCWLAcpUserDataDTO acpGetData(Long organizationId,String invoiceNum);
}
