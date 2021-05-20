package org.srm.purchasecooperation.cux.pr.infra.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.srm.purchasecooperation.cux.pr.domain.repository.RcwlCompanyRepository;
import org.srm.purchasecooperation.cux.pr.infra.mapper.RcwlCompanyMapper;

/**
 * @author lu.cheng01@hand-china.com
 * @description
 * @date 2021/5/20 21:48
 * @version:1.0
 */
@Component
public class RcwlCompanyRepositoryImpl implements RcwlCompanyRepository {
    @Autowired
    private RcwlCompanyMapper rcwlCompanyMapper;
    @Override
    public String selectCompanyRcwlUnitName(Long companyId,Long tenantId) {
        return rcwlCompanyMapper.selectCompanyRcwlUnitName(companyId,tenantId);
    }
}
