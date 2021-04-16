package org.srm.purchasecooperation.cux.act.app.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import gxbpm.dto.RCWLGxBpmStartDataDTO;
import gxbpm.service.RCWLGxBpmInterfaceService;
import io.choerodon.core.oauth.DetailsHelper;
import javassist.Loader;
import org.hzero.boot.interfaces.sdk.dto.ResponsePayloadDTO;
import org.hzero.boot.platform.lov.annotation.ProcessLovValue;
import org.hzero.boot.platform.profile.ProfileClient;
import org.hzero.core.base.BaseConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.srm.purchasecooperation.cux.act.api.dto.ActListFilesDto;
import org.srm.purchasecooperation.cux.act.api.dto.ActListHeaderDto;
import org.srm.purchasecooperation.cux.act.app.service.ActService;
import org.srm.purchasecooperation.cux.act.domain.repository.ActFilesRespository;
import org.srm.purchasecooperation.cux.act.domain.repository.ActHeaderRespository;
import org.srm.purchasecooperation.cux.act.domain.repository.ActLineRespository;
import org.srm.purchasecooperation.cux.act.infra.repsitory.impl.ActHeaderRespositoryImpl;
import org.srm.purchasecooperation.cux.act.infra.utils.rcwlActConstant;
import org.srm.web.annotation.Tenant;
import sun.security.smartcardio.SunPCSC;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * @author lu.cheng01@hand-china.com
 * @description
 * @date 2021/4/6 10:11
 * @version:1.0
 */
@Service
@Tenant(rcwlActConstant.TENANT_NUMBER)
public class ActServiceImpl implements ActService {
    @Autowired
    private ActHeaderRespository actHeaderRespository;
    @Autowired
    private ActLineRespository actLineRespository;
    @Autowired
    private ActFilesRespository actFilesRespository;
    @Autowired
    private RCWLGxBpmInterfaceService rcwlGxBpmInterfaceService;

    //获取配置参数
    @Autowired
    private ProfileClient profileClient;

    private static final Logger logger = LoggerFactory.getLogger(Loader.class);


    /**
     * 验收单查询，
     *
     * @param acceptListHeaderId 验收单头id
     * @param organizationId     租户id
     * @return ActListHeaderDto
     */
    @Override
    @ProcessLovValue
    public ActListHeaderDto actQuery(Long acceptListHeaderId, Long organizationId) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        ActListHeaderDto actListHeaderDto = actHeaderRespository.actQuery(acceptListHeaderId, organizationId);
        actListHeaderDto.setYSDDH(actLineRespository.actQuery(acceptListHeaderId, organizationId));
        List<ActListFilesDto> actListFilesDtoList = actFilesRespository.actFilesQuery(acceptListHeaderId, organizationId);
        if (actListFilesDtoList != null && actListFilesDtoList.size() > 0) {
            int fileNumber = 1;
            for (ActListFilesDto e : actListFilesDtoList) {
                /*设置文件序号，自动增长，1，2，3，4...*/
                e.setFileNumber(String.valueOf(fileNumber));
                fileNumber++;
            }
        }
        actListHeaderDto.setURL(actListFilesDtoList);
        String reSrcSys = profileClient.getProfileValueByOptions(DetailsHelper.getUserDetails().getTenantId(), DetailsHelper.getUserDetails().getUserId(), DetailsHelper.getUserDetails().getRoleId(), "RCWL_BPM_REQSRCSYS");
        String reqTarSys = profileClient.getProfileValueByOptions(DetailsHelper.getUserDetails().getTenantId(), DetailsHelper.getUserDetails().getUserId(), DetailsHelper.getUserDetails().getRoleId(), "RCWL_BPM_REQTARSYS");

        ResponsePayloadDTO responsePayloadDTO = new ResponsePayloadDTO();

        RCWLGxBpmStartDataDTO rcwlGxBpmStartDataDTO = new RCWLGxBpmStartDataDTO();
        String data = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(actListHeaderDto);
        logger.info("data:" + data);
        String userName = DetailsHelper.getUserDetails().getUsername();

        //设置传输值
        rcwlGxBpmStartDataDTO.setReSrcSys(reSrcSys);
        rcwlGxBpmStartDataDTO.setReqTarSys(reqTarSys);
        rcwlGxBpmStartDataDTO.setUserId(userName);
        rcwlGxBpmStartDataDTO.setBtid(rcwlActConstant.ACCEPT_BPM_TYPE_CODE);
        rcwlGxBpmStartDataDTO.setBoid(actListHeaderDto.getAcceptListNum());
        rcwlGxBpmStartDataDTO.setProcinstId("0");
        rcwlGxBpmStartDataDTO.setData(data);
        logger.info("rcwlGxBpmStartDataDTO：" + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(rcwlGxBpmStartDataDTO));

        responsePayloadDTO = rcwlGxBpmInterfaceService.RcwlGxBpmInterfaceRequestData(rcwlGxBpmStartDataDTO);

        logger.info("返回结果：" + responsePayloadDTO);

        //调用接口
        return actListHeaderDto;
    }
}
