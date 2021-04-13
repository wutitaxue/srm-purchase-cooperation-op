package org.srm.purchasecooperation.cux.acp.app.service;

import org.hzero.boot.interfaces.sdk.dto.ResponsePayloadDTO;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * @author lu.cheng01@hand-china.com
 * @description 调用大象慧云接口
 * @date 2021/4/10 16:10
 * @version:1.0
 */
public interface RCWLAcpInvoceElephantInterfaceService {

    //调用大象接口
    public void rcwlAcpInvoceElephantInterface(Long InvoiceHeaderId) throws NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException;
}
