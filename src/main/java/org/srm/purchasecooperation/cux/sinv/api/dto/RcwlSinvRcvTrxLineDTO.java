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
    private Long attributeBigint2;
    @ApiModelProperty(value = "收货人名字")
    private String  attributeBigint2Meaning;
    @ApiModelProperty(value = "收货人名字")
    private String  receiverName;

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


    public String getAttributeBigint2Meaning() {
        return attributeBigint2Meaning;
    }

    public void setAttributeBigint2Meaning(String attributeBigint2Meaning) {
        this.attributeBigint2Meaning = attributeBigint2Meaning;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }
}
