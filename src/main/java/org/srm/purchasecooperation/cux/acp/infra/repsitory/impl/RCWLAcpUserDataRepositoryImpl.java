package org.srm.purchasecooperation.cux.acp.infra.repsitory.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.srm.purchasecooperation.cux.acp.api.dto.RCWLAcpUserDataDTO;
import org.srm.purchasecooperation.cux.acp.domain.repository.RCWLAcpUserDataRepository;
import org.srm.purchasecooperation.cux.acp.infra.mapper.RCWLAcpUserDataMapper;
import org.srm.purchasecooperation.cux.acp.infra.constant.RCWLAcpConstant;
import org.srm.web.annotation.Tenant;

/**
 * @author lu.cheng01@hand-china.com
 * @description
 * @date 2021/4/8 15:49
 * @version:1.0
 */
@Component
@Tenant(RCWLAcpConstant.TENANT_NUMBER)
public class RCWLAcpUserDataRepositoryImpl implements RCWLAcpUserDataRepository {
    @Autowired
    private RCWLAcpUserDataMapper rcwlAcpUserDataMapper;

    /**
     * 获取发票以及人员数据
     *
     * @param organizationId
     * @param invoiceNum     发票单据号
     * @return
     */
    @Override

    public RCWLAcpUserDataDTO acpGetData(Long organizationId, String invoiceNum) {
        return rcwlAcpUserDataMapper.acpGetData(organizationId, invoiceNum);
    }
}
