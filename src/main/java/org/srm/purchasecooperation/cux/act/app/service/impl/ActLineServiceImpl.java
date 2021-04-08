package org.srm.purchasecooperation.cux.act.app.service.impl;

import org.hzero.boot.platform.lov.annotation.ProcessLovValue;
import org.hzero.core.base.BaseConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.srm.purchasecooperation.cux.act.api.dto.ActListLinesDto;
import org.srm.purchasecooperation.cux.act.app.service.ActLineService;
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
public class ActLineServiceImpl implements ActLineService {

    @Autowired
    private ActLineRespository actLineRespository;


    /**
     * 验收单行list查询
     * @param acceptListHeaderId 验收单头id
     * @param organizationId 租户id
     * @return List<ActListLinesDto>
     */
    @Override
    public List<ActListLinesDto> actQuery(Long acceptListHeaderId, Long organizationId) {
        return actLineRespository.actQuery(acceptListHeaderId, organizationId);
    }
}
