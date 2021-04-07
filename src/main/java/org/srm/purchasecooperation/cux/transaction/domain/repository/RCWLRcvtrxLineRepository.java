package org.srm.purchasecooperation.cux.transaction.domain.repository;

import java.util.List;

import org.srm.purchasecooperation.cux.transaction.api.dto.RCWLReceiveTransactionLineDTO;
import org.srm.purchasecooperation.transaction.api.dto.ReceiveTransactionLineDTO;

public interface RCWLRcvtrxLineRepository {
    List<RCWLReceiveTransactionLineDTO> rcwlQueryReceiveTransactionLineForPurchase(
                    ReceiveTransactionLineDTO queryParam);
}
