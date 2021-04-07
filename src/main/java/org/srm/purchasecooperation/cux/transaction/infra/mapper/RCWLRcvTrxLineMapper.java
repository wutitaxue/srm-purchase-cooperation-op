package org.srm.purchasecooperation.cux.transaction.infra.mapper;

import java.util.List;

import org.srm.purchasecooperation.cux.transaction.api.dto.RCWLReceiveTransactionLineDTO;
import org.srm.purchasecooperation.transaction.api.dto.ReceiveTransactionLineDTO;

public interface RCWLRcvTrxLineMapper {
    List<RCWLReceiveTransactionLineDTO> rcwlSelectReceiveTransactionLineForPurchase(
                    ReceiveTransactionLineDTO queryParam);
}
