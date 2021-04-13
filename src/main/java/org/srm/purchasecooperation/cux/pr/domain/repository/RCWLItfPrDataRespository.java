package org.srm.purchasecooperation.cux.pr.domain.repository;

public interface RCWLItfPrDataRespository {
    /**
     * 查找sap公司编码
     * @param companyId
     * @param tenantId
     * @return
     */
    String selectSapCode(Long companyId, Long tenantId);

    /**
     * 查找预算科目名称
     * @param budgetAccountNum
     * @param tenantId
     * @return
     */
    String selectBudgetAccountName(String budgetAccountNum, Long tenantId);

    /**
     * 查找wbs名称
     * @param wbsCode
     * @param tenantId
     * @return
     */
    String selectWbsName(String wbsCode, Long tenantId);
}
