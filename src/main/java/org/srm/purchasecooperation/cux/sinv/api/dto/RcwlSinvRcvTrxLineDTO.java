package org.srm.purchasecooperation.cux.sinv.api.dto;

import io.swagger.annotations.ApiModelProperty;
import org.srm.purchasecooperation.sinv.api.dto.SinvRcvTrxLineDTO;

import java.math.BigDecimal;

/**
 * @description:
 * @author: bin.zhang
 * @createDate: 2021/4/22 20:20
 */
public class RcwlSinvRcvTrxLineDTO extends SinvRcvTrxLineDTO {
    @ApiModelProperty(value = "质保金")
    private BigDecimal retentionMoney;
    @ApiModelProperty(value = "资产单据号(入库单号)")
    private String assetsbillNum;
    @ApiModelProperty(value = "入库数量")
    private Long warehouseQuantity;
    @ApiModelProperty(value = "收货人id")
    private Long attributeBigint1;
    @ApiModelProperty(value = "收货人名字")
    private String  attributeBigint1Meaning;

    public BigDecimal getRetentionMoney() {
        return retentionMoney;
    }

    public void setRetentionMoney(BigDecimal retentionMoney) {
        this.retentionMoney = retentionMoney;
    }

    public String getAssetsbillNum() {
        return assetsbillNum;
    }

    public void setAssetsbillNum(String assetsbillNum) {
        this.assetsbillNum = assetsbillNum;
    }

    public Long getWarehouseQuantity() {
        return warehouseQuantity;
    }

    public void setWarehouseQuantity(Long warehouseQuantity) {
        this.warehouseQuantity = warehouseQuantity;
    }

    @Override
    public Long getAttributeBigint1() {
        return attributeBigint1;
    }

    @Override
    public void setAttributeBigint1(Long attributeBigint1) {
        this.attributeBigint1 = attributeBigint1;
    }

    public String getAttributeBigint1Meaning() {
        return attributeBigint1Meaning;
    }

    public void setAttributeBigint1Meaning(String attributeBigint1Meaning) {
        this.attributeBigint1Meaning = attributeBigint1Meaning;
    }
}
