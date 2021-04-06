package org.srm.purchasecooperation.cux.transaction.app.service;

import org.srm.purchasecooperation.cux.transaction.api.dto.RCWLReceiveTransactionLineDTO;
import org.srm.purchasecooperation.transaction.api.dto.ReceiveTransactionLineDTO;

import io.choerodon.core.domain.Page;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;

public interface RCWLRcvTrxLineService {
    Page<RCWLReceiveTransactionLineDTO> rcwlQueryReceiveTransactionLineForPurchase(ReceiveTransactionLineDTO queryParam,
                    PageRequest pageRequest);
}
