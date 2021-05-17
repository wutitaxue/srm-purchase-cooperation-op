package org.srm.purchasecooperation.cux.gxbpm.app.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import javassist.Loader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.srm.purchasecooperation.cux.gxbpm.api.dto.*;
import org.srm.purchasecooperation.cux.gxbpm.app.service.RCWLGxBpmInterfaceService;

/**
 * @author lu.cheng01@hand-china.com
 * @description
 * @date 2021/4/12 22:14
 * @version:1.0
 */
@Service("BpmInterfaceService")
public class RCWLGxBpmInterfaceServiceImpl implements RCWLGxBpmInterfaceService {
    private static final Logger logger = LoggerFactory.getLogger(Loader.class);

    @Override
    public RCWLGxBpmRequestDataDTO RcwlGxBpmInterfaceRequestData(RCWLGxBpmStartDataDTO rcwlGxBpmStartDataDTO) {
        RCWLGxBpmBSXMLDTO rcwlGxBpmBSXMLDTO = new RCWLGxBpmBSXMLDTO();
        RCWLGxBpmReqItemDTO rcwlGxBpmReqItemDTO = new RCWLGxBpmReqItemDTO();
        RCWLGxBpmMessageDTO rcwlGxBpmMessageDTO = new RCWLGxBpmMessageDTO();
        RCWLGxBpmReqBaseInfoDTO rcwlGxBpmReqBaseInfoDTO = new RCWLGxBpmReqBaseInfoDTO();
        RCWLGxBpmIRequestDTO rcwlGxBpmIRequestDTO = new RCWLGxBpmIRequestDTO();
        RCWLGxBpmRequestDataDTO rcwlGxBpmRequestDataDTO = new RCWLGxBpmRequestDataDTO();

        JSONObject jsonObject = JSON.parseObject(rcwlGxBpmStartDataDTO.getData());
        rcwlGxBpmBSXMLDTO.setDATA(jsonObject);

        rcwlGxBpmReqItemDTO.setBSXML(rcwlGxBpmBSXMLDTO);
        rcwlGxBpmMessageDTO.setReqItem(rcwlGxBpmReqItemDTO);
        rcwlGxBpmIRequestDTO.setReqBaseInfo(rcwlGxBpmReqBaseInfoDTO);
        rcwlGxBpmIRequestDTO.setiMessage(rcwlGxBpmMessageDTO);
        rcwlGxBpmRequestDataDTO.setRcwlGxBpmIRequestDTO(rcwlGxBpmIRequestDTO);



//        logger.info("RCWLGxBpmBSXMLDTO:"+JSON.toJSONString(rcwlGxBpmBSXMLDTO));
//        logger.info("rcwlGxBpmReqItemDTO:"+JSON.toJSONString(rcwlGxBpmReqItemDTO));
//        logger.info("rcwlGxBpmMessageDTO:"+JSON.toJSONString(rcwlGxBpmMessageDTO));
//        logger.info("rcwlGxBpmIRequestDTO:"+JSON.toJSONString(rcwlGxBpmIRequestDTO));
//        logger.info("rcwlGxBpmRequestDataDTO:"+JSON.toJSONString(rcwlGxBpmRequestDataDTO));

        logger.info("###:" + JSON.toJSONString(rcwlGxBpmRequestDataDTO));

        return rcwlGxBpmRequestDataDTO;
    }
}
