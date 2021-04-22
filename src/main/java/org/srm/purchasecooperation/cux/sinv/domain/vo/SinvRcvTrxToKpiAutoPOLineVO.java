package org.srm.purchasecooperation.cux.sinv.domain.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.srm.common.mybatis.domain.ExpandDomain;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SinvRcvTrxToKpiAutoPOLineVO extends ExpandDomain {
    private Long poTypeId;
    private String poNum;
    private Long quantity;
    private Long netReceivedQuantity;
    private Long TenantId;

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Long getNetReceivedQuantity() {
        return netReceivedQuantity;
    }

    public void setNetReceivedQuantity(Long netReceivedQuantity) {
        this.netReceivedQuantity = netReceivedQuantity;
    }

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

    public Long getTenantId() {
        return TenantId;
    }

    public void setTenantId(Long tenantId) {
        TenantId = tenantId;
    }
}
