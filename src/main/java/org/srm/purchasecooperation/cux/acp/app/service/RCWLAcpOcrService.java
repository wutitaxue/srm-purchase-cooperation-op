package org.srm.purchasecooperation.cux.acp.app.service;

import org.srm.purchasecooperation.cux.acp.api.dto.RCWLAcpInvoiceElephantRequestDataDTO;
import org.srm.purchasecooperation.cux.acp.api.dto.RCWLAcpOcrDTO;
import org.srm.purchasecooperation.cux.acp.api.dto.RCWLAcpUserDataDTO;
import org.srm.purchasecooperation.cux.acp.domain.entity.RCWLAcpInvoiceData;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * @author lu.cheng01@hand-china.com
 * @description 获取发票以及人员信息
 * @date 2021/4/8 15:38
 * @version:1.0
 */
public interface RCWLAcpOcrService {
    public RCWLAcpOcrDTO acpGetData(RCWLAcpInvoiceData rcwlAcpInvoiceData);

    /**
     * 获取发票审批状态更新的加密数据
     * @param InvoiceHeaderId
     * @return
     */
    public RCWLAcpInvoiceElephantRequestDataDTO acpInvoiceELephantGRequestData(Long InvoiceHeaderId) throws NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException;
}
