package org.srm.purchasecooperation.cux.acp.domain.repository;

import org.srm.purchasecooperation.cux.acp.api.dto.RCWLAcpUserDataDTO;
import org.srm.purchasecooperation.cux.acp.domain.entity.RCWLAcpInvoiceHeaderData;

/**
 * @author lu.cheng01@hand-china.com
 * @description
 * @date 2021/4/8 15:47
 * @version:1.0
 */
public interface RCWLAcpInvoiceHeaderRepository {
    /**
     * 获取发票头信息
     * @param organizationId
     * @return
     */
    public RCWLAcpInvoiceHeaderData getAcpInvoiceHeaderData(Long organizationId, Long InvoiceHeaderId);
}
