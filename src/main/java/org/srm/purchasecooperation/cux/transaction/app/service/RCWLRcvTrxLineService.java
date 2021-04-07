package org.srm.purchasecooperation.cux.transaction.app.service;

import org.srm.purchasecooperation.cux.transaction.api.dto.RCWLReceiveTransactionLineDTO;
import org.srm.purchasecooperation.transaction.api.dto.ReceiveTransactionLineDTO;

import io.choerodon.core.domain.Page;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;

public interface RCWLRcvTrxLineService {
    /**
     * 采购方查询接收事务
     *
     * @param queryParam 查询条件
     * @param pageRequest 分页排序条件
     * @return 查询结果
     */
    Page<RCWLReceiveTransactionLineDTO> rcwlQueryReceiveTransactionLineForPurchase(ReceiveTransactionLineDTO queryParam,
                    PageRequest pageRequest);
}
