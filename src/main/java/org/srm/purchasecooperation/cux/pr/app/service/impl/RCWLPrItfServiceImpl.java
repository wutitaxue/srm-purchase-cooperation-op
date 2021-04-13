package org.srm.purchasecooperation.cux.pr.app.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.choerodon.core.exception.CommonException;
import org.hzero.boot.interfaces.sdk.dto.RequestPayloadDTO;
import org.hzero.boot.interfaces.sdk.dto.ResponsePayloadDTO;
import org.hzero.boot.interfaces.sdk.invoke.InterfaceInvokeSdk;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.srm.purchasecooperation.cux.pr.api.dto.*;
import org.srm.purchasecooperation.cux.pr.app.service.RCWLPrItfService;
import org.srm.purchasecooperation.cux.pr.infra.constant.RCWLConstants;
import org.srm.purchasecooperation.pr.domain.entity.PrHeader;
import org.srm.purchasecooperation.pr.domain.entity.PrLine;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:接口
 * @author: bin.zhang
 * @createDate: 2021/4/10 13:57
 */
@Service
public class RCWLPrItfServiceImpl implements RCWLPrItfService {
    @Autowired
    private RCWLPrItfService rcwlPrItfService;
    @Autowired
    private InterfaceInvokeSdk interfaceInvokeSdk;
    private static final Logger logger= LoggerFactory.getLogger(RCWLPrItfServiceImpl.class);
    /**
     * 预算占用释放接口
     * @param prHeader
     * @param tenantId
     */
    @Override
    public void invokeBudget(PrHeader prHeader, Long tenantId) {
        //接口请求数据获取
        RCWLItfPrHeaderDTO rcwlItfPrHeaderDTO = rcwlPrItfService.getBudgetAccountItfData(prHeader,tenantId);

        ResponsePayloadDTO responsePayloadDTO = new ResponsePayloadDTO();
        RequestPayloadDTO requestPayloadDTO = new RequestPayloadDTO();
        requestPayloadDTO.setPayload(JSON.toJSONString(rcwlItfPrHeaderDTO));
        logger.info("请求数据:"+requestPayloadDTO.toString());

    }

    /**
     * 初始化头数据
     * @return
     */
    @Override
    public RCWLItfPrHeaderDTO initOccupyHeader() {
        RCWLItfPrHeaderDTO rcwlItfPrHeaderDTO = new RCWLItfPrHeaderDTO();
        String token =  rcwlPrItfService.getToken();
        rcwlItfPrHeaderDTO.setToken(token);
        rcwlItfPrHeaderDTO.setType(RCWLConstants.InterfaceInitValue.TYPE);
        rcwlItfPrHeaderDTO.setDefinecode(RCWLConstants.InterfaceInitValue.DEFINE_CODE);
        return rcwlItfPrHeaderDTO;
    }

    /**
     * 获取接口数据
     *
     * @param prHeader
     * @param tenantId
     * @return
     */
    @Override
    public RCWLItfPrHeaderDTO getBudgetAccountItfData(PrHeader prHeader, Long tenantId) {
        //获取接口所需数据
        RCWLItfPrLineDTO rcwlItfPrLineDTO = RCWLItfPrLineDTO.initOccupy(prHeader,tenantId);
        List<PrLine> lineDetailList = prHeader.getPrLineList();

        List<RCWLItfPrLineDetailDTO> rcwlItfPrLineDetailDTOS = new ArrayList<>();
        lineDetailList.forEach(prDetailLine -> {
            RCWLItfPrLineDetailDTO rcwlItfPrLineDetailDTO =RCWLItfPrLineDetailDTO.initOccupyDetail(prDetailLine,tenantId);
            rcwlItfPrLineDetailDTOS.add(rcwlItfPrLineDetailDTO);
        });

        RCWLItfPrHeaderDTO rcwlItfPrHeaderDTO = new RCWLItfPrHeaderDTO();
        RCWLItfPrDataDTO rcwlItfPrDataDTO = new RCWLItfPrDataDTO();

        rcwlItfPrDataDTO.setYszy(rcwlItfPrLineDTO);
        rcwlItfPrDataDTO.setYszyzb(rcwlItfPrLineDetailDTOS);

        rcwlItfPrHeaderDTO = rcwlPrItfService.initOccupyHeader();
        rcwlItfPrHeaderDTO.setData(rcwlItfPrDataDTO);
        return rcwlItfPrHeaderDTO;
    }

    /**
     * 获取token
     *
     * @return
     */
    @Override
    public String getToken() {
        RCWLTokenGetRequestDTO requestDTO = new RCWLTokenGetRequestDTO();
        requestDTO.setOpenid(RCWLConstants.InterfaceInitValue.OPEN_ID);

        ResponsePayloadDTO responsePayloadDTO = new ResponsePayloadDTO();
        RequestPayloadDTO requestPayloadDTO = new RequestPayloadDTO();
        requestPayloadDTO.setPayload(JSON.toJSONString(requestDTO));
        logger.info("token请求数据："+requestPayloadDTO.toString());

        //调用接口
        try {
            //接口调用
//            responsePayloadDTO = interfaceInvokeSdk.invoke(
//                    requestPayloadDTO);
            logger.info("调用接口成功responsePayloadDTO：" + JSON.toJSONString(responsePayloadDTO));
            RCWLTokenGetResponseDTO responseDTO = JSONObject.parseObject(responsePayloadDTO.getPayload(), RCWLTokenGetResponseDTO.class);
            logger.info("RCWLAcpInvoiceElephantResponseDto接口返回信息：" + JSON.toJSONString(responseDTO));
        } catch (Exception e) {
            logger.info("调用接口失败!" + e.getMessage());
            throw new CommonException("调用接口失败!" + e.getMessage());
        }




        return null;
    }
}
