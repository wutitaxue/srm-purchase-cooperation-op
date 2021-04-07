package org.srm.purchasecooperation.cux.act.infra.repsitory.impl;

import org.hzero.boot.platform.lov.annotation.ProcessLovValue;
import org.hzero.core.base.BaseConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.srm.purchasecooperation.cux.act.api.dto.ActListLinesDto26422;
import org.srm.purchasecooperation.cux.act.domain.repository.ActLineRespository;
import org.srm.purchasecooperation.cux.act.infra.mapper.ActHeaderMapper;
import org.srm.purchasecooperation.cux.act.infra.mapper.ActLineMapper;
import org.srm.purchasecooperation.cux.act.infra.utils.rcwlActConstant;
import org.srm.web.annotation.Tenant;

import java.util.List;

/**
 * @author lu.cheng01@hand-china.com
 * @description
 * @date 2021/4/6 10:26
 * @version:1.0
 */
@Component
@Tenant(rcwlActConstant.TENANT_NUMBER)
public class ActLineRespositoryImpl implements ActLineRespository {
    @Autowired
    private ActLineMapper actLineMapper;

    @Override
    @ProcessLovValue
    public List<ActListLinesDto26422> actQuery(Long acceptListHeaderId, Long organizationId) {
        return actLineMapper.actListLineQuery(acceptListHeaderId,organizationId);
    }
}
