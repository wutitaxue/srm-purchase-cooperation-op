package org.srm.purchasecooperation.cux.act.app.service.impl;

import org.hzero.boot.platform.lov.annotation.ProcessLovValue;
import org.hzero.core.base.BaseConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.srm.purchasecooperation.cux.act.api.dto.ActListLinesDto26422;
import org.srm.purchasecooperation.cux.act.app.service.ActLineService26422;
import org.srm.purchasecooperation.cux.act.domain.repository.ActLineRespository;
import org.srm.purchasecooperation.cux.act.infra.utils.rcwlActConstant;
import org.srm.web.annotation.Tenant;

import java.util.List;

/**
 * @author lu.cheng01@hand-china.com
 * @description
 * @date 2021/4/6 10:11
 * @version:1.0
 */
@Service
@Tenant(rcwlActConstant.TENANT_NUMBER)
public class ActLineServiceImpl26422 implements ActLineService26422 {

    @Autowired
    private ActLineRespository actLineRespository;


    @Override
    public List<ActListLinesDto26422> actQuery(Long acceptListHeaderId, Long organizationId) {
        return actLineRespository.actQuery(acceptListHeaderId, organizationId);
    }
}
