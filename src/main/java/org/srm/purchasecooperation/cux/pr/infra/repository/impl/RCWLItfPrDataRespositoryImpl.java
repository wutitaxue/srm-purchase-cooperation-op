package org.srm.purchasecooperation.cux.pr.infra.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.srm.purchasecooperation.cux.pr.domain.repository.RCWLItfPrDataRespository;
import org.srm.purchasecooperation.cux.pr.infra.mapper.RCWLItfPrDataMapper;

/**
 * @description:常量
 * @author: bin.zhang
 * @createDate: 2021/4/12 15:18
 */
@Component
public class RCWLItfPrDataRespositoryImpl implements RCWLItfPrDataRespository {
    @Autowired
    private RCWLItfPrDataMapper rcwlItfPrDataMapper;
    /**
     * 查找sap公司编码
     *
     * @param companyId
     * @param tenantId
     * @return
     */
    @Override
    public String selectSapCode(Long companyId, Long tenantId) {
        return rcwlItfPrDataMapper.selectSapCode(companyId,tenantId) ;
    }

    /**
     * 查找预算科目名称
     *
     * @param budgetAccountNum
     * @param tenantId
     * @return
     */
    @Override
    public String selectBudgetAccountName(String budgetAccountNum, Long tenantId) {
        return rcwlItfPrDataMapper.selectBudgetAccountName(budgetAccountNum,tenantId);
    }

    /**
     * 查找wbs名称
     *
     * @param wbsCode
     * @param prLineId
     * @return
     */
    @Override
    public String selectWbsName(String wbsCode, Long prLineId) {
        return rcwlItfPrDataMapper.selectWbsName(wbsCode,prLineId);
    }

    /**
     * 查找wbscode
     *
     * @param wbs
     * @param prLineId
     * @return
     */
    @Override
    public String selectWbsCode(String wbs, Long prLineId) {
        return  rcwlItfPrDataMapper.selectWbsCode(wbs,prLineId);
    }
}
