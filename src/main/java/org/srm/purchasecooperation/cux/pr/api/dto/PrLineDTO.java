package org.srm.purchasecooperation.cux.pr.api.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author bin.zhang
 */
public class PrLineDTO {
    @ApiModelProperty(value = "采购申请行id")
    private Long prLineId;
    @ApiModelProperty(value = "采购申请头id")
    private Long prHeaderId;
    @ApiModelProperty(value = "租户id")
    private Long tenantId;
    @ApiModelProperty(value = "采购计划id")
    private Long attributeBigint1;

    public Long getPrLineId() {
        return prLineId;
    }

    public void setPrLineId(Long prLineId) {
        this.prLineId = prLineId;
    }

    public Long getPrHeaderId() {
        return prHeaderId;
    }

    public void setPrHeaderId(Long prHeaderId) {
        this.prHeaderId = prHeaderId;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public Long getAttributeBigint1() {
        return attributeBigint1;
    }

    public void setAttributeBigint1(Long attributeBigint1) {
        this.attributeBigint1 = attributeBigint1;
    }

    @Override
    public String toString() {
        return "PrLineDTO{" +
                "prLineId=" + prLineId +
                ", prHeaderId=" + prHeaderId +
                ", tenantId=" + tenantId +
                ", attributeBigint1=" + attributeBigint1 +
                '}';
    }
}
