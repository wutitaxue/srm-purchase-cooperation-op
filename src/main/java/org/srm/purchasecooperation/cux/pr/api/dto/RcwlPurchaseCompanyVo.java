package org.srm.purchasecooperation.cux.pr.api.dto;

import org.srm.purchasecooperation.pr.domain.PurchaseCompanyVo;

/**
 * @author lu.cheng01@hand-china.com
 * @description
 * @date 2021/5/20 21:27
 * @version:1.0
 */
public class RcwlPurchaseCompanyVo extends PurchaseCompanyVo {
    private String rcwlUnitName;

    public String getRcwlUnitName() {
        return rcwlUnitName;
    }

    public void setRcwlUnitName(String rcwlUnitName) {
        this.rcwlUnitName = rcwlUnitName;
    }
}
