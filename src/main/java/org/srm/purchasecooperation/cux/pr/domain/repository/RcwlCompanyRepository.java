package org.srm.purchasecooperation.cux.pr.domain.repository;

/**
 * @author lu.cheng01@hand-china.com
 * @description
 * @date 2021/5/20 21:47
 * @version:1.0
 */
public interface RcwlCompanyRepository {

    String selectCompanyRcwlUnitName(Long companyId,Long tenantId);
}
