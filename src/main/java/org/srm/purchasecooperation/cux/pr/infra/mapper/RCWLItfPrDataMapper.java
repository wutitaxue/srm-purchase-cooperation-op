package org.srm.purchasecooperation.cux.pr.infra.mapper;

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
    String selectSapCode(Long companyId, Long tenantId);
}
