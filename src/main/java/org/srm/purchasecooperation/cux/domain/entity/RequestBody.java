package org.srm.purchasecooperation.cux.domain.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RequestBody {
    @JsonProperty("payload")
    private PayLoad payLoad;

    public PayLoad getPayLoad() {
        return payLoad;
    }

    public void setPayLoad(PayLoad payLoad) {
        this.payLoad = payLoad;
    }
}
