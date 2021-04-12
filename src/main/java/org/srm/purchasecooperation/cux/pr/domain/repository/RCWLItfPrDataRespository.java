package org.srm.purchasecooperation.cux.pr.domain.repository;

public interface RCWLItfPrDataRespository {
    /**
     * 查找sap公司编码
     * @param companyId
     * @param tenantId
     * @return
     */
    String selectSapCode(Long companyId, Long tenantId);
}
