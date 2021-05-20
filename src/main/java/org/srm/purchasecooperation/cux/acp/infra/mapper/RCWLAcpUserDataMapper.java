package org.srm.purchasecooperation.cux.acp.infra.mapper;

import org.srm.purchasecooperation.cux.acp.api.dto.RCWLAcpUserDataDTO;

/**
 * @author lu.cheng01@hand-china.com
 * @description
 * @date 2021/4/8 15:50
 * @version:1.0
 */
public interface RCWLAcpUserDataMapper {
    /**
     * description: 获取发票供应商
     *
     * @param invoiceNum 发票单据
     * @return
     */
    public RCWLAcpUserDataDTO acpGetData(Long organizationId, String invoiceNum);
}
