package org.srm.purchasecooperation.cux.pr.app.service;

import org.srm.purchasecooperation.pr.domain.entity.PrHeader;

/**
 * @description:
 * @author: bin.zhang
 * @createDate: 2021/4/25 18:05
 */
public interface RCWLPrHeaderSubmitService {

    PrHeader singletonSubmit(Long tenantId, PrHeader prHeader);
}
