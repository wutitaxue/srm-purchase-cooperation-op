package org.srm.purchasecooperation.cux.pr.infra.mapper;

import java.math.BigDecimal;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author:yuanping.zhang
 * @createTime:2021/5/31 13:52
 * @version:1.0
 */
@Component
public interface RcwlPrToBpmMapper {
    /**
     * 查询业务事项名称
     * @param tenantId
     * @param budgetAccountId
     * @return
     */
    String selectBudetAccount(Long tenantId, Long budgetAccountId);

    /**
     *查询成本中心名称
     * @param tenantId
     * @param costId
     * @return
     */
    String selectCost(Long tenantId, Long costId);

    /**
     * 查询产品类型
     * @param tenantId
     * @param wbsCode
     * @return
     */
    String selectWbs(Long tenantId, String wbsCode);

    /**
     * 获取品类名称
     * @param tenantId
     * @param categoryId
     * @return
     */
    String selectCategoryName(Long tenantId, Long categoryId);
    /**
     * 获取单位名称
     * @param tenantId
     * @param uomId
     * @return
     */
    String selectUomName(Long tenantId, Long uomId);

    /**
     * 获取行不含税金额之和
     * @param tenantId
     * @param prHeaderId
     * @return
     */
    BigDecimal selectLineAmountSum(@Param("tenantId") Long tenantId,@Param("prHeaderId") Long prHeaderId);
}
