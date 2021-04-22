package org.srm.purchasecooperation.cux.sinv.domain.entity;

import io.swagger.annotations.ApiModelProperty;
import org.srm.purchasecooperation.sinv.domain.entity.SinvRcvTrxLine;

import java.math.BigDecimal;

/**收货事务行表新加字段
 * @description:
 * @author: bin.zhang
 * @createDate: 2021/4/22 16:14
 */
public class RcwlSinvRcvTrxLine extends SinvRcvTrxLine {
    @ApiModelProperty("质保金金额")
    private BigDecimal retentionMoney;
    @ApiModelProperty("资产单据号")
    private String assetsbillNum;
    @ApiModelProperty("入库数量")
    private BigDecimal warehouseQuantity;
    @ApiModelProperty("收货人")
    private  Long receiverId;

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

    public BigDecimal getWarehouseQuantity() {
        return warehouseQuantity;
    }

    public void setWarehouseQuantity(BigDecimal warehouseQuantity) {
        this.warehouseQuantity = warehouseQuantity;
    }

    public Long getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Long receiverId) {
        this.receiverId = receiverId;
    }
}
