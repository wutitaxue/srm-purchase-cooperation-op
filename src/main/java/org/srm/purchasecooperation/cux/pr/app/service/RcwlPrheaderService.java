package org.srm.purchasecooperation.cux.pr.app.service;

import org.srm.purchasecooperation.pr.domain.entity.PrHeader;

/**
 * @description:
 * @author:yuanping.zhang
 * @createTime:2021/3/26 16:18
 * @version:1.0
 */
public interface RcwlPrheaderService {
    PrHeader prApprove(PrHeader prHeader,Long tenantId);
}
