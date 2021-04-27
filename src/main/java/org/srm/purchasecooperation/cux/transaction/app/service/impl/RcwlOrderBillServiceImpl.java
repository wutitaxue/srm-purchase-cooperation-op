package org.srm.purchasecooperation.cux.transaction.app.service.impl;



import org.hzero.boot.interfaces.sdk.dto.RequestPayloadDTO;
import org.hzero.boot.interfaces.sdk.invoke.InterfaceInvokeSdk;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.srm.purchasecooperation.cux.transaction.api.dto.RcwlOrderBillDTO;
import org.srm.purchasecooperation.cux.transaction.app.service.RcwlOrderBillService;
import org.srm.purchasecooperation.cux.transaction.infra.constant.RcwlTrcConstants;
import org.srm.web.annotation.Tenant;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Tenant("SRM-RCWL")
public class RcwlOrderBillServiceImpl implements RcwlOrderBillService {

    @Autowired
    private InterfaceInvokeSdk interfaceInvokeSdk;

    /**
    * 批量调用资产接口
    * */
    @Override
    public void sendOrderBillList(List<RcwlOrderBillDTO> list) {
        for(RcwlOrderBillDTO rcwlOrderBillDTO:list){
            this.sendOrderBillOne(rcwlOrderBillDTO);
        }
    }

    /**
     * 调用资产接口
     * */
    @Override
    public void sendOrderBillOne(RcwlOrderBillDTO rcwlOrderBillDTO) {
            Map<String, String> requestMap = new HashMap<>();
            BeanUtils.copyProperties(rcwlOrderBillDTO, requestMap);
            RequestPayloadDTO requestPayloadDTO = new RequestPayloadDTO();
            requestPayloadDTO.setRequestParamMap(requestMap);
            requestPayloadDTO.setMediaType(RcwlTrcConstants.interfaceInvoke.MEDIA_TYPE);
            interfaceInvokeSdk.invoke(RcwlTrcConstants.interfaceInvoke.NAME_SPACE, RcwlTrcConstants.interfaceInvoke.SERVER_CODE, RcwlTrcConstants.interfaceInvoke.INTERFACE_CODE,requestPayloadDTO);
    }
}
