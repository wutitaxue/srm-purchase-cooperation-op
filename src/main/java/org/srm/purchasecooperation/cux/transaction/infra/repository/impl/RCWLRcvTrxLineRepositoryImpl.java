package org.srm.purchasecooperation.cux.transaction.infra.repository.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.srm.purchasecooperation.cux.transaction.api.dto.RCWLReceiveTransactionLineDTO;
import org.srm.purchasecooperation.cux.transaction.domain.repository.RCWLRcvtrxLineRepository;
import org.srm.purchasecooperation.cux.transaction.infra.constant.RCWLTransactionConstant;
import org.srm.purchasecooperation.cux.transaction.infra.mapper.RCWLRcvTrxLineMapper;
import org.srm.purchasecooperation.transaction.api.dto.ReceiveTransactionLineDTO;
import org.srm.purchasecooperation.transaction.infra.repository.impl.RcvTrxLineRepositoryImpl;
import org.srm.web.annotation.Tenant;

@Component
@Tenant(RCWLTransactionConstant.TENANT_NUMBER)
public class RCWLRcvTrxLineRepositoryImpl extends RcvTrxLineRepositoryImpl implements RCWLRcvtrxLineRepository {
    @Autowired
    private RCWLRcvTrxLineMapper rcvTrxLineMapper;

    @Override
    public List<RCWLReceiveTransactionLineDTO> rcwlQueryReceiveTransactionLineForPurchase(
                    ReceiveTransactionLineDTO queryParam) {
        List<RCWLReceiveTransactionLineDTO> list =
                        this.rcvTrxLineMapper.rcwlSelectReceiveTransactionLineForPurchase(queryParam);
        return list;
    }
}
