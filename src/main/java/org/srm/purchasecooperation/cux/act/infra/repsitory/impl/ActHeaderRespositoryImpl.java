package org.srm.purchasecooperation.cux.act.infra.repsitory.impl;

import org.hzero.boot.platform.lov.annotation.ProcessLovValue;
import org.hzero.core.base.BaseConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.srm.purchasecooperation.cux.act.api.dto.ActListHeaderDto;
import org.srm.purchasecooperation.cux.act.domain.repository.ActHeaderRespository;
import org.srm.purchasecooperation.cux.act.infra.mapper.ActHeaderMapper;
import org.srm.purchasecooperation.cux.act.infra.mapper.ActLineMapper;
import org.srm.purchasecooperation.cux.act.infra.utils.rcwlActConstant;
import org.srm.web.annotation.Tenant;

import java.util.logging.Logger;

/**
 * @author lu.cheng01@hand-china.com
 * @description
 * @date 2021/4/6 10:26
 * @version:1.0
 */
@Component
@Tenant(rcwlActConstant.TENANT_NUMBER)
public class ActHeaderRespositoryImpl implements ActHeaderRespository {
    @Autowired
    private ActHeaderMapper actHeaderMapper;

    /**
     * 验收单头信息查询
     * @param acceptListHeaderId 验收单头id
     * @param organizationId 租户id
     * @return ActListHeaderDto
     */
    @Override
    @ProcessLovValue
    public ActListHeaderDto actQuery(Long acceptListHeaderId,Long organizationId) {
        ActListHeaderDto actListHeaderDto = actHeaderMapper.actListHeaderQuery(acceptListHeaderId,organizationId);
//        actListHeaderDto.setActListLinesDtoList(actLineMapper.actListLineQuery(acceptListHeaderId,organizationId));
        return actListHeaderDto;
    }
}
