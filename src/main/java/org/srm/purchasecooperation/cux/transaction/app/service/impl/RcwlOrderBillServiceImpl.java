package org.srm.purchasecooperation.cux.transaction.app.service.impl;



import org.hzero.boot.interfaces.sdk.dto.RequestPayloadDTO;
import org.hzero.boot.interfaces.sdk.invoke.InterfaceInvokeSdk;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.srm.purchasecooperation.cux.transaction.api.dto.RcwlOrderBillDTO;
import org.srm.purchasecooperation.cux.transaction.app.service.RcwlOrderBillService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RcwlOrderBillServiceImpl implements RcwlOrderBillService {

    @Autowired
    private InterfaceInvokeSdk interfaceInvokeSdk;

    @Override
    public void sendOrderBillList(List<RcwlOrderBillDTO> list) {
        for(RcwlOrderBillDTO rcwlOrderBillDTO:list){
            this.sendOrderBillOne(rcwlOrderBillDTO);
        }
    }

    @Override
    public void sendOrderBillOne(RcwlOrderBillDTO rcwlOrderBillDTO) {
            Map<String, String> requestMap = new HashMap<>();
            BeanUtils.copyProperties(rcwlOrderBillDTO, requestMap);
            RequestPayloadDTO requestPayloadDTO = new RequestPayloadDTO();
            requestPayloadDTO.setRequestParamMap(requestMap);
            requestPayloadDTO.setMediaType("aplication/json");
            interfaceInvokeSdk.invoke("SRM","RCWL_SPUC_ORDERS","RCWL_SPUC_ORDERS",requestPayloadDTO);
    }
}
