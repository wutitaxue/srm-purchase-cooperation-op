package org.srm.purchasecooperation.cux.act.app.service.impl;

import org.hzero.boot.platform.lov.annotation.ProcessLovValue;
import org.hzero.core.base.BaseConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.srm.purchasecooperation.cux.act.api.dto.ActListHeaderDto26422;
import org.srm.purchasecooperation.cux.act.app.service.ActService26422;
import org.srm.purchasecooperation.cux.act.domain.repository.ActFilesRespository;
import org.srm.purchasecooperation.cux.act.domain.repository.ActHeaderRespository;
import org.srm.purchasecooperation.cux.act.domain.repository.ActLineRespository;
import org.srm.purchasecooperation.cux.act.infra.repsitory.impl.ActHeaderRespositoryImpl;
import org.srm.purchasecooperation.cux.act.infra.utils.rcwlActConstant;
import org.srm.web.annotation.Tenant;

/**
 * @author lu.cheng01@hand-china.com
 * @description
 * @date 2021/4/6 10:11
 * @version:1.0
 */
@Service
@Tenant(rcwlActConstant.TENANT_NUMBER)
public class ActServiceImpl26422 implements ActService26422 {

    @Autowired
    private ActHeaderRespository actHeaderRespository;
    @Autowired
    private ActLineRespository actLineRespository;
    @Autowired
    private ActFilesRespository actFilesRespository;

    /**
     * 验收单查询
     * @param acceptListHeaderId
     * @param organizationId
     * @return
     */
    @Override
    @ProcessLovValue
    public ActListHeaderDto26422 actQuery(Long acceptListHeaderId, Long organizationId) {
        ActListHeaderDto26422 actListHeaderDto26422 = actHeaderRespository.actQuery(acceptListHeaderId, organizationId);
        actListHeaderDto26422.setYSDDH(actLineRespository.actQuery(acceptListHeaderId, organizationId));
        actListHeaderDto26422.setURL(actFilesRespository.actFilesQuery(acceptListHeaderId,organizationId));
        return actListHeaderDto26422;
    }
}
