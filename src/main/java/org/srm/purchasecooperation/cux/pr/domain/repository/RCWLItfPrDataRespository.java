package org.srm.purchasecooperation.cux.pr.domain.repository;

import org.srm.purchasecooperation.pr.domain.entity.PrHeader;
import org.srm.purchasecooperation.pr.domain.entity.PrLine;

import java.util.List;

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
   // String selectBudgetAccountName(String budgetAccountNum, Long tenantId);

    /**
     * 查找wbs名称
     * @param wbsCode
     * @param prLineId
     * @return
     */
    String selectWbsName(String wbsCode, Long prLineId);

    /**
     * 查找wbscode
     * @param wbs
     * @param prLineId
     * @return
     */
    String selectWbsCode(String wbs, Long prLineId);

    String selectBudgetAccountName(Long budgetAccountId);

    String selectBudgetAccountNum(Long budgetAccountId);

    PrHeader selectPrHeaderByPrNum(String prNum, Long tenantId);

    List<PrLine> selectPrLineListById(Long prHeaderId, Long tenantId);

    List<PrLine> selectPrLineListByIdOld(Long prHeaderId, Long tenantId);

    /**
     * 校验是否可以触发接口
     * @param prHeaderId
     * @param tenantId
     * @return
     */
    Integer validateInvokeItf(Long prHeaderId, Long tenantId);
}
