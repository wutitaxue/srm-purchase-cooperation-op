package org.srm.purchasecooperation.cux.pr.infra.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.srm.purchasecooperation.cux.pr.domain.repository.RCWLItfPrDataRespository;
import org.srm.purchasecooperation.cux.pr.infra.mapper.RCWLItfPrDataMapper;
import org.srm.purchasecooperation.pr.domain.entity.PrHeader;
import org.srm.purchasecooperation.pr.domain.entity.PrLine;

import java.util.List;

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

    @Override
    public String selectBudgetAccountName(Long budgetAccountId) {
        return rcwlItfPrDataMapper.selectBudgetAccountName(budgetAccountId);
    }

    @Override
    public String selectBudgetAccountNum(Long budgetAccountId) {
        return rcwlItfPrDataMapper.selectBudgetAccountNum(budgetAccountId);
    }

    @Override
    public PrHeader selectPrHeaderByPrNum(String prNum, Long tenantId) {
        return rcwlItfPrDataMapper.selectPrHeaderByPrNum(prNum,tenantId);
    }

    @Override
    public List<PrLine> selectPrLineListById(Long prHeaderId, Long tenantId) {
        return rcwlItfPrDataMapper.selectPrLineListById(prHeaderId,tenantId) ;
    }

    @Override
    public List<PrLine> selectPrLineListByIdOld(Long prHeaderId, Long tenantId) {
        return rcwlItfPrDataMapper.selectPrLineListByIdOld(prHeaderId,tenantId);
    }
}
