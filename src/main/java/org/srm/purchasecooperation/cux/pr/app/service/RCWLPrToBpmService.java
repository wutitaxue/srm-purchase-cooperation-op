package org.srm.purchasecooperation.cux.pr.app.service;

import org.srm.purchasecooperation.pr.domain.entity.PrHeader;


/**
 * @description:
 * @author:yuanping.zhang
 * @createTime:2021/4/19 11:15
 * @version:1.0
 */

public interface RCWLPrToBpmService {

    String prDataToBpm(PrHeader prHeader, String type);
}
