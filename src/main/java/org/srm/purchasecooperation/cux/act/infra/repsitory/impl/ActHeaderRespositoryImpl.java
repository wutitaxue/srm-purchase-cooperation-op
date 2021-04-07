package org.srm.purchasecooperation.cux.act.infra.repsitory.impl;

import org.hzero.boot.platform.lov.annotation.ProcessLovValue;
import org.hzero.core.base.BaseConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.srm.purchasecooperation.cux.act.api.dto.ActListHeaderDto26422;
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

    @Override
    @ProcessLovValue
    public ActListHeaderDto26422 actQuery(Long acceptListHeaderId,Long organizationId) {
        ActListHeaderDto26422 actListHeaderDto26422 = actHeaderMapper.actListHeaderQuery(acceptListHeaderId,organizationId);
//        actListHeaderDto26422.setActListLinesDto26422List(actLineMapper.actListLineQuery(acceptListHeaderId,organizationId));
        return actListHeaderDto26422;
    }
}
