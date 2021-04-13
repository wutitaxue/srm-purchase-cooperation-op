package org.srm.purchasecooperation.cux.acp.app.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.choerodon.core.exception.CommonException;
import javassist.Loader;
import org.hzero.boot.interfaces.sdk.dto.RequestPayloadDTO;
import org.hzero.boot.interfaces.sdk.dto.ResponsePayloadDTO;
import org.hzero.boot.interfaces.sdk.invoke.InterfaceInvokeSdk;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.srm.purchasecooperation.cux.acp.api.dto.RCWLAcpInvoiceElephantRequestDataDTO;
import org.srm.purchasecooperation.cux.acp.api.dto.RCWLAcpInvoiceElephantResponseDto;
import org.srm.purchasecooperation.cux.acp.app.service.RCWLAcpInvoceElephantInterfaceService;
import org.srm.purchasecooperation.cux.acp.app.service.RCWLAcpOcrService;
import org.srm.purchasecooperation.cux.acp.infra.constant.RCWLAcpConstant;
import org.srm.purchasecooperation.cux.acp.infra.constant.RCWLAcpConstant;
import org.srm.web.annotation.Tenant;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * @author lu.cheng01@hand-china.com
 * @description
 * @date 2021/4/10 17:38
 * @version:1.0
 */
@Service
@Tenant(RCWLAcpConstant.TENANT_NUMBER)
public class RCWLAcpInvoceElephantInterfaceServiceImpl implements RCWLAcpInvoceElephantInterfaceService {
    private static final Logger logger = LoggerFactory.getLogger(Loader.class);


    @Autowired
    private RCWLAcpOcrService rcwlAcpOcrService;
    @Autowired
    private InterfaceInvokeSdk interfaceInvokeSdk;


    @Override
    public void rcwlAcpInvoceElephantInterface(Long InvoiceHeaderId) throws NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException {
        RCWLAcpInvoiceElephantRequestDataDTO rcwlAcpInvoiceElephantRequestDataDTO = rcwlAcpOcrService.acpInvoiceELephantGRequestData(InvoiceHeaderId);
        ResponsePayloadDTO responsePayloadDTO = new ResponsePayloadDTO();
        RequestPayloadDTO requestPayloadDTO = new RequestPayloadDTO();
        requestPayloadDTO.setPayload(JSON.toJSONString(rcwlAcpInvoiceElephantRequestDataDTO));
        logger.info("rcwlAcpInvoiceElephantRequestDataDTO:" + JSON.toJSONString(rcwlAcpInvoiceElephantRequestDataDTO));

        logger.info(JSON.toJSONString("requestPayloadDTO:" + requestPayloadDTO));

//        调用接口
        try {
            //接口调用
            responsePayloadDTO = interfaceInvokeSdk.invoke(RCWLAcpConstant.AcpInvoiceElephantInterfaceInfo.NAMESPACE,
                    RCWLAcpConstant.AcpInvoiceElephantInterfaceInfo.SERVERCODE,
                    RCWLAcpConstant.AcpInvoiceElephantInterfaceInfo.INSERFACECODE,
                    requestPayloadDTO);
            logger.info("调用接口成功responsePayloadDTO：" + JSON.toJSONString(responsePayloadDTO));
            RCWLAcpInvoiceElephantResponseDto rcwlAcpInvoiceElephantResponseDto = JSONObject.parseObject(responsePayloadDTO.getPayload(), RCWLAcpInvoiceElephantResponseDto.class);
            logger.info("RCWLAcpInvoiceElephantResponseDto接口返回信息：" + JSON.toJSONString(rcwlAcpInvoiceElephantResponseDto));
        } catch (Exception e) {
            logger.info("调用接口失败!" + e.getMessage());
            throw new CommonException("调用接口失败!" + e.getMessage());
        }
    }
}
