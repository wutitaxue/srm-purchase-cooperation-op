package org.srm.purchasecooperation.cux.act.app.service.impl;

import io.choerodon.core.oauth.DetailsHelper;
import javassist.Loader;
import org.hzero.boot.platform.lov.annotation.ProcessLovValue;
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

    /**
     * 验收单查询，
     *
     * @param acceptListHeaderId 验收单头id
     * @param organizationId     租户id
     * @return ActListHeaderDto
     */
    @Override
    @ProcessLovValue
    public ActListHeaderDto actQuery(Long acceptListHeaderId, Long organizationId) {
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
        return actListHeaderDto;
    }
}
