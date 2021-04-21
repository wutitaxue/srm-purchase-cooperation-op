package org.srm.purchasecooperation.cux.sinv.domain.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.srm.common.mybatis.domain.ExpandDomain;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SinvRcvTrxToKpiAutoPOLineVO extends ExpandDomain {
    private Long poTypeId;
    private String poNum;
    private Long TenantId;

    public Long getPoTypeId() {
        return poTypeId;
    }

    public void setPoTypeId(Long poTypeId) {
        this.poTypeId = poTypeId;
    }

    public String getPoNum() {
        return poNum;
    }

    public void setPoNum(String poNum) {
        this.poNum = poNum;
    }
}
