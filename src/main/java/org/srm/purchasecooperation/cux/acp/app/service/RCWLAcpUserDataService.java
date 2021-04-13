package org.srm.purchasecooperation.cux.acp.app.service;

import org.srm.purchasecooperation.cux.acp.api.dto.RCWLAcpUserDataDTO;
import org.srm.purchasecooperation.cux.acp.domain.entity.RCWLAcpInvoiceData;

/**
 * @author lu.cheng01@hand-china.com
 * @description 获取发票以及人员信息
 * @date 2021/4/8 15:38
 * @version:1.0
 */
public interface RCWLAcpUserDataService {
    public RCWLAcpUserDataDTO acpGetData(RCWLAcpInvoiceData rcwlAcpInvoiceData);
}
