package org.srm.purchasecooperation.cux.pr.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.srm.purchasecooperation.cux.pr.app.service.RcwlCompanyService;
import org.srm.purchasecooperation.cux.pr.domain.repository.RcwlCompanyRepository;
import org.srm.web.annotation.Tenant;

/**
 * @author lu.cheng01@hand-china.com
 * @description
 * @date 2021/5/20 21:42
 * @version:1.0
 */
@Service
@Tenant("SRM-RCWL")
public class RcwlComapnyServiceImpl implements RcwlCompanyService {
    @Autowired
    private RcwlCompanyRepository rcwlCompanyRepository;

    @Override
    public String selectCompanyRcwlUnitName(Long companyId,Long tenantId) {
        return rcwlCompanyRepository.selectCompanyRcwlUnitName(companyId,tenantId);
    }
}
