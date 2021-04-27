package org.srm.purchasecooperation.cux.pr.app.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import gxbpm.dto.RCWLGxBpmStartDataDTO;
import gxbpm.service.RCWLGxBpmInterfaceService;
import io.choerodon.core.oauth.DetailsHelper;
import org.hzero.boot.interfaces.sdk.dto.ResponsePayloadDTO;
import org.hzero.boot.platform.profile.ProfileClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.srm.purchasecooperation.cux.pr.app.service.RCWLPlanHeaderDataToBpmService;
import org.srm.purchasecooperation.cux.pr.domain.entity.PlanHeader;

import java.io.IOException;
import java.util.List;

/**
 * @description:
 * @author: bin.zhang
 * @createDate: 2021/4/27 16:46
 */
@Component
public class RCWLPlanHeaderDataToBpmServiceImpl implements RCWLPlanHeaderDataToBpmService {
    @Autowired
    private RCWLGxBpmInterfaceService rcwlGxBpmInterfaceService;
    @Autowired
    private ProfileClient profileClient;

    /**
     * 传输数据给bpm
     *
     * @param list
     * @param organizationId
     */
    @Override
    public void sendDataToBpm(List<PlanHeader> list, Long organizationId) throws IOException {
        String reSrcSys = profileClient.getProfileValueByOptions(DetailsHelper.getUserDetails().getTenantId(), DetailsHelper.getUserDetails().getUserId(), DetailsHelper.getUserDetails().getRoleId(), "RCWL_BPM_REQSRCSYS");
        String reqTarSys = profileClient.getProfileValueByOptions(DetailsHelper.getUserDetails().getTenantId(), DetailsHelper.getUserDetails().getUserId(), DetailsHelper.getUserDetails().getRoleId(), "RCWL_BPM_REQTARSYS");


        ResponsePayloadDTO responsePayloadDTO = new ResponsePayloadDTO();

        RCWLGxBpmStartDataDTO rcwlGxBpmStartDataDTO = new RCWLGxBpmStartDataDTO();

        String userName = DetailsHelper.getUserDetails().getUsername();
        rcwlGxBpmStartDataDTO.setReSrcSys(reSrcSys);
        rcwlGxBpmStartDataDTO.setReqTarSys(reqTarSys);
        rcwlGxBpmStartDataDTO.setUserId(userName);
        rcwlGxBpmStartDataDTO.setBtid("RCWLSRMCGJH");
        rcwlGxBpmStartDataDTO.setBoid(list.get(0).getProcessNum());
        rcwlGxBpmStartDataDTO.setProcinstId("0");

//        ObjectMapper mapper = new ObjectMapper();
//        payload.setPayload(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(rcwlItfPrHeaderDTO));

        rcwlGxBpmStartDataDTO.setData("");

            //调用bpm接口
         responsePayloadDTO = rcwlGxBpmInterfaceService.RcwlGxBpmInterfaceRequestData(rcwlGxBpmStartDataDTO);
    }
}
