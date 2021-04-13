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

    /**
     * 查找预算科目名称
     * @param budgetAccountNum
     * @param tenantId
     * @return
     */
    String selectBudgetAccountName(@Param("budgetAccountNum")String budgetAccountNum, @Param("tenantId")Long tenantId);

    /**
     * 查找wbs名称
     * @param wbsCode
     * @param tenantId
     * @return
     */
    String selectWbsName(@Param("wbsCode")String wbsCode, @Param("tenantId")Long tenantId);
}
