package org.srm.purchasecooperation.cux.transaction.domain.vo;

import org.hzero.boot.platform.lov.annotation.LovValue;
import org.srm.purchasecooperation.transaction.domain.vo.ReceiveRcvTrxDataVO;

public class RcwlReceiveRcvTrxDataVO extends ReceiveRcvTrxDataVO {

    @LovValue(
            value = "SINV.ASN_HEADERS_STATUS",
            meaningField = "asnStatusMeaning"
    )
    private String asnStatus;
    private String asnStatusMeaning;

    public String getAsnStatus() {
        return asnStatus;
    }

    public void setAsnStatus(String asnStatus) {
        this.asnStatus = asnStatus;
    }

    public String getAsnStatusMeaning() {
        return asnStatusMeaning;
    }

    public void setAsnStatusMeaning(String asnStatusMeaning) {
        this.asnStatusMeaning = asnStatusMeaning;
    }
}
