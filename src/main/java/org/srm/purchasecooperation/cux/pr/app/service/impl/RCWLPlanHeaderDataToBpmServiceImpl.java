package org.srm.purchasecooperation.cux.pr.app.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import gxbpm.dto.RCWLGxBpmStartDataDTO;
import gxbpm.service.RCWLGxBpmInterfaceService;
import io.choerodon.core.oauth.DetailsHelper;
import org.apache.commons.collections4.CollectionUtils;
import org.hzero.boot.interfaces.sdk.dto.ResponsePayloadDTO;
import org.hzero.boot.platform.profile.ProfileClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.srm.purchasecooperation.cux.pr.api.dto.PlanHeaderAttachementToBpmDTO;
import org.srm.purchasecooperation.cux.pr.api.dto.PlanHeaderInfoToBpmDTO;
import org.srm.purchasecooperation.cux.pr.api.dto.PlanHeaderToBpmDTO;
import org.srm.purchasecooperation.cux.pr.app.service.RCWLPlanHeaderDataToBpmService;
import org.srm.purchasecooperation.cux.pr.domain.repository.RCWLPlanHeaderRepository;
import org.srm.purchasecooperation.cux.pr.domain.vo.PlanHeaderVO;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
    @Autowired
    private RCWLPlanHeaderRepository rcwlPlanHeaderRepository;

    /**
     * 传输数据给bpm
     *
     * @param list
     * @param organizationId
     */
    @Override
    public void sendDataToBpm(List<PlanHeaderVO> list, Long organizationId) throws IOException {
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

        ObjectMapper mapper = new ObjectMapper();

        //获取封装数据
        PlanHeaderToBpmDTO planHeaderToBpmDTO = this.initData(list, organizationId);
        rcwlGxBpmStartDataDTO.setData(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(planHeaderToBpmDTO));

        //调用bpm接口
        responsePayloadDTO = rcwlGxBpmInterfaceService.RcwlGxBpmInterfaceRequestData(rcwlGxBpmStartDataDTO);
    }

    private PlanHeaderToBpmDTO initData(List<PlanHeaderVO> list, Long organizationId) {
        PlanHeaderToBpmDTO bpmDTO = new PlanHeaderToBpmDTO();
        Long userId = DetailsHelper.getUserDetails().getUserId();

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        String cur_month = sdf.format(date);
        String fSubject = "采购计划-" + list.get(0).getCompanyName() + "-" + cur_month;
        bpmDTO.setFSubject(fSubject);
        bpmDTO.setCompany(list.get(0).getCompanyName());
        bpmDTO.setAddFlag(list.get(0).getAddFlagMeaning());
        bpmDTO.setNumber(String.valueOf(list.size()));

        Integer thisMonthNumber =  this.rcwlPlanHeaderRepository.calThisMonthNumber(userId,organizationId);
        Integer lastMonthNumber = this.rcwlPlanHeaderRepository.calLastMonthNumber(userId,organizationId);
        Integer lastMonthComplete = this.rcwlPlanHeaderRepository.calLastMonthComplete(userId,organizationId);
        bpmDTO.setThisMonthNumber(String.valueOf(thisMonthNumber));
        bpmDTO.setLastMonthNumber(String.valueOf(lastMonthNumber));
        bpmDTO.setLastMonthComplete(String.valueOf(lastMonthComplete));

        BigDecimal projectAmount = new BigDecimal(0);
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getProjectAmount() == null) {
                projectAmount.add(new BigDecimal(0));
            }
            projectAmount.add(list.get(i).getProjectAmount());
        }
        BigDecimal newPeojectAmount = projectAmount.setScale(2, RoundingMode.HALF_UP);
        bpmDTO.setMoney(String.valueOf(newPeojectAmount));



        List<PlanHeaderInfoToBpmDTO> planHeaderInfoToBpmDTOS = this.initPlanHeaderInfo(list, organizationId);
        bpmDTO.setCgjhxx(planHeaderInfoToBpmDTOS);
        List<PlanHeaderAttachementToBpmDTO> attachementToBpmDTOList = this.initPlanHeaderAttachment(list, organizationId);
        bpmDTO.setAttachments1(attachementToBpmDTOList);
        return bpmDTO;
    }

    private List<PlanHeaderAttachementToBpmDTO> initPlanHeaderAttachment(List<PlanHeaderVO> list, Long organizationId) {
        List<PlanHeaderAttachementToBpmDTO> bpmDTOS = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(list)) {
             bpmDTOS = this.rcwlPlanHeaderRepository.batchSelectAttachmentsInfo(list, organizationId);
        }
        return bpmDTOS;
    }


    private List<PlanHeaderInfoToBpmDTO> initPlanHeaderInfo(List<PlanHeaderVO> list, Long organizationId) {
        return null;
    }


}
