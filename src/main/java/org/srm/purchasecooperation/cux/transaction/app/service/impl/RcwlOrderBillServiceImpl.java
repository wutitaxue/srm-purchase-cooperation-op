package org.srm.purchasecooperation.cux.transaction.app.service.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.choerodon.core.exception.CommonException;
import org.hzero.boot.interfaces.sdk.dto.RequestPayloadDTO;
import org.hzero.boot.interfaces.sdk.dto.ResponsePayloadDTO;
import org.hzero.boot.interfaces.sdk.invoke.InterfaceInvokeSdk;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.srm.purchasecooperation.cux.transaction.api.dto.RcwlOrderBillDTO;
import org.srm.purchasecooperation.cux.transaction.app.service.RcwlOrderBillService;
import org.srm.purchasecooperation.cux.transaction.infra.constant.RcwlTrcConstants;
import org.srm.purchasecooperation.cux.transaction.infra.mapper.RcwlOrderBillMapper;
import org.srm.purchasecooperation.sinv.domain.entity.SinvRcvTrxLine;
import org.srm.purchasecooperation.sinv.domain.repository.SinvRcvTrxLineRepository;
import org.srm.web.annotation.Tenant;

import java.util.HashMap;
import java.util.Map;

@Service
@Tenant("SRM-RCWL")
public class RcwlOrderBillServiceImpl implements RcwlOrderBillService {

    @Autowired
    private InterfaceInvokeSdk interfaceInvokeSdk;
    @Autowired
    private RcwlOrderBillMapper rcwlOrderBillMapper;
    @Autowired
    private SinvRcvTrxLineRepository sinvRcvTrxLineRepository;

    /**
     * 调用资产接口
     */
    @Override
    public void sendOrderBillOne(Long tenantId,Long rcvTrxLineId,String type) {
        RcwlOrderBillDTO rcwlOrderBillDTO;
        //检查品类是否需要推送资产 1推 2不推
        String s = rcwlOrderBillMapper.selectCategory(tenantId, rcvTrxLineId);
        if(s == "0"){
            return;
        }
        //ASN为接收类型单据
        if ("ASN".equals(type)){
            //查询接收送货单
             rcwlOrderBillDTO = rcwlOrderBillMapper.selectSendAsn(tenantId,rcvTrxLineId);
            //IsNew字段转换
            if("1".equals(rcwlOrderBillDTO.getfIsNewInt())){
                rcwlOrderBillDTO.setfIsNew(true);
            }else if ("0".equals(rcwlOrderBillDTO.getfIsNewInt())){
                rcwlOrderBillDTO.setfIsNew(false);
            }
        //ORDER为接收类型单据
        }else if("ORDER".equals(type)){
            //查询验收单数据
            rcwlOrderBillDTO = rcwlOrderBillMapper.selectSendAccept(tenantId,rcvTrxLineId);
            //IsNew字段转换
            if("1".equals(rcwlOrderBillDTO.getfIsNewInt())){
                rcwlOrderBillDTO.setfIsNew(true);
            }else if ("0".equals(rcwlOrderBillDTO.getfIsNewInt())){
                rcwlOrderBillDTO.setfIsNew(false);
            }
        }else {
            throw new CommonException("输入单据类型错误");
        }
        //根据返回报文更新前端显示状态返回值
        RequestPayloadDTO requestPayloadDTO = new RequestPayloadDTO();
        Map<String, RcwlOrderBillDTO> map = new HashMap<>();
        map.put("data", rcwlOrderBillDTO);
        requestPayloadDTO.setPayload(JSON.toJSONString(map, SerializerFeature.WriteNullStringAsEmpty, SerializerFeature.WriteNullBooleanAsFalse));
        requestPayloadDTO.setMediaType(RcwlTrcConstants.interfaceInvoke.MEDIA_TYPE);
        ResponsePayloadDTO invoke = interfaceInvokeSdk.invoke(RcwlTrcConstants.interfaceInvoke.NAME_SPACE, RcwlTrcConstants.interfaceInvoke.SERVER_CODE, RcwlTrcConstants.interfaceInvoke.INTERFACE_CODE, requestPayloadDTO);
        String payload = invoke.getPayload();
        JsonObject asJsonObject = new JsonParser().parse(payload).getAsJsonObject();
        String code = asJsonObject.get("code").getAsString();
        String message = asJsonObject.get("message").getAsString();
        String codecg = "E";
        //0和102状态默认为是成功 其他为失败
        if ("0".equals(code)){
            codecg = "S";
        }
        if ("0".equals(code) || "102".equals(code)){
            //更新物料smdm_item物料表 attribute_varchar1字段改为false
            rcwlOrderBillMapper.updateItem(tenantId,rcwlOrderBillDTO.getfMaterialId());
        }
        //更新事务行表的回写数据
        SinvRcvTrxLine sinvRcvTrxLine = new SinvRcvTrxLine();
        sinvRcvTrxLine.setRcvTrxLineId(rcvTrxLineId);
        Long objectVersionNumber = sinvRcvTrxLineRepository.selectOne(sinvRcvTrxLine).getObjectVersionNumber();
        sinvRcvTrxLine.setAttributeVarchar4(code);
        sinvRcvTrxLine.setAttributeVarchar5(message);
        sinvRcvTrxLine.setAttributeVarchar6(codecg);
        sinvRcvTrxLine.setObjectVersionNumber(objectVersionNumber);
        sinvRcvTrxLineRepository.updateByPrimaryKeySelective(sinvRcvTrxLine);
    }
}
