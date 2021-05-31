package org.srm.purchasecooperation.cux.pr.infra.mapper;

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
}
