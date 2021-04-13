package org.srm.purchasecooperation.cux.pr.app.service;

import org.srm.purchasecooperation.cux.pr.api.dto.RCWLItfPrHeaderDTO;
import org.srm.purchasecooperation.pr.domain.entity.PrHeader;

public interface RCWLPrItfService {

    /**
     * 预算占用释放接口
     * @param prHeader
     * @param tenantId
     */
    void invokeBudget(PrHeader prHeader, Long tenantId);

    /**
     * 初始化请求头
     * @return
     */
    RCWLItfPrHeaderDTO initOccupyHeader();

    /**
     * 获取接口数据
     * @param prHeader
     * @param tenantId
     * @return
     */
    RCWLItfPrHeaderDTO getBudgetAccountItfData(PrHeader prHeader, Long tenantId);

    /**
     * 获取token
     * @return
     */
    String getToken();
}
