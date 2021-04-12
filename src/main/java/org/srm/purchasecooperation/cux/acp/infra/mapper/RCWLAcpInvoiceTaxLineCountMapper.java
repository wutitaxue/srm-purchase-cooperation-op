package org.srm.purchasecooperation.cux.acp.infra.mapper;

import org.srm.purchasecooperation.cux.acp.api.dto.RCWLAcpUserDataDTO;

/**
 * @author lu.cheng01@hand-china.com
 * @description
 * @date 2021/4/8 15:50
 * @version:1.0
 */
public interface RCWLAcpInvoiceTaxLineCountMapper {
    /**
     * description: 获取发票税务发票行数
     *
     * @param InvoiceHeaderId 发票头id
     * @return int
     */
    public int acpInvoiceLineCount(Long organizationId, Long InvoiceHeaderId);
}
