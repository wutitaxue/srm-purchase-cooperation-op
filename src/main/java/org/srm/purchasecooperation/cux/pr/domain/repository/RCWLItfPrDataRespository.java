package org.srm.purchasecooperation.cux.pr.domain.repository;

import org.srm.purchasecooperation.pr.domain.entity.PrHeader;
import org.srm.purchasecooperation.pr.domain.entity.PrLine;

import java.math.BigDecimal;
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

    BigDecimal selectSumQuantity(Long prLineId, Long tenantId);

    /**
     * 根据行Id查找预算行信息
     * @param prLineId
     * @return
     */
    List<Integer> selectBudgetbudgetDisYear(Long prLineId);

    /**
     * 根据pr_line_id找到pr_action_id最大的数据，找到唯一的一组budget_group为old值和new数据，去重取到所有的年份
     * @param prLineId
     * @return
     */
    List<Integer> selectBudgetChangeActionDisYear(Long prLineId);

    /**
     * 根据line_id查找budget_group为old的budget_dis_amount
     * @param prLineId
     * @param year
     * @return
     */
    BigDecimal selectBudgetDisAmountByBudgetGroup(Long prLineId, Integer year);
}
