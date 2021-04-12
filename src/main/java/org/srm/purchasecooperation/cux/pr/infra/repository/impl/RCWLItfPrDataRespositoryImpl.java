package org.srm.purchasecooperation.cux.pr.infra.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.srm.purchasecooperation.cux.pr.domain.repository.RCWLItfPrDataRespository;
import org.srm.purchasecooperation.cux.pr.infra.mapper.RCWLItfPrDataMapper;

/**
 * @description:常量
 * @author: bin.zhang
 * @createDate: 2021/4/12 15:18
 */
public class RCWLItfPrDataRespositoryImpl implements RCWLItfPrDataRespository {
    @Autowired
    private RCWLItfPrDataMapper rcwlItfPrDataMapper;
    /**
     * 查找sap公司编码
     *
     * @param companyId
     * @param tenantId
     * @return
     */
    @Override
    public String selectSapCode(Long companyId, Long tenantId) {
        return rcwlItfPrDataMapper.selectSapCode(companyId,tenantId) ;
    }
}
