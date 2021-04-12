package org.srm.purchasecooperation.cux.pr.app.service;

import org.srm.purchasecooperation.pr.domain.entity.PrHeader;

public interface RCWLPrItfService {
    void invokeBudget(PrHeader prHeader, Long tenantId);
}
