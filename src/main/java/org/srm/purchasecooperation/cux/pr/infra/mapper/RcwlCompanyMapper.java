package org.srm.purchasecooperation.cux.pr.infra.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * @author lu.cheng01@hand-china.com
 * @description
 * @date 2021/5/20 21:49
 * @version:1.0
 */
@Component
public interface RcwlCompanyMapper {

    String selectCompanyRcwlUnitName(@Param("companyId") Long companyId,@Param("tenantId") Long tenantId);
}
