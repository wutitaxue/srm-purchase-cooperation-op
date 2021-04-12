package org.srm.purchasecooperation.cux.acp.infra.repsitory.impl;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.srm.purchasecooperation.cux.acp.domain.entity.RCWLAcpInvoiceHeaderData;
import org.srm.purchasecooperation.cux.acp.domain.repository.RCWLAcpInvoiceHeaderRepository;
import org.srm.purchasecooperation.cux.acp.infra.mapper.RCWLAcpInvoiceHeaderDataMapper;
import org.srm.purchasecooperation.cux.acp.infra.constant.RCWLAcpConstant;
import org.srm.web.annotation.Tenant;

/**
 * @author lu.cheng01@hand-china.com
 * @description
 * @date 2021/4/9 15:01
 * @version:1.0
 */
@Component
@Tenant(RCWLAcpConstant.TENANT_NUMBER)
public class RCWLAcpInvoiceHeaderRepositoryImpl implements RCWLAcpInvoiceHeaderRepository {

    @Autowired
    private RCWLAcpInvoiceHeaderDataMapper rcwlAcpInvoiceHeaderDataMapper;
    /**
     * 获取发票头数据
     * @param organizationId
     * @param InvoiceHeaderId
     * @return
     */
    @Override
    public RCWLAcpInvoiceHeaderData getAcpInvoiceHeaderData(Long organizationId, Long InvoiceHeaderId) {
        return rcwlAcpInvoiceHeaderDataMapper.getAcpInvoiceHeaderData(organizationId,InvoiceHeaderId);
    }
}
