package org.srm.purchasecooperation.cux.pr.infra.mapper;

import org.apache.ibatis.annotations.Param;

/**
 * @author bin.zhang
 */
public interface RCWLItfPrDataMapper {
    /**
     * 查找sap公司编码
     * @param companyId
     * @param tenantId
     * @return
     */
    String selectSapCode(@Param("companyId") Long companyId,@Param("tenantId") Long tenantId);
}
