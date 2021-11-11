package org.srm.purchasecooperation.cux.po.itf.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Author: longjunquan 21420
 * @Date: 2021/11/9 15:29
 * @Description:
 */
@Data
public class RcwlOrderStatusDTO {
    @ApiModelProperty(value = "订单ID")
    Long poHeaderId;
    @ApiModelProperty(value = "订单编号")
    String orderId;
    @ApiModelProperty(value = "订单状态")
    String status;
    @ApiModelProperty(value = "供应商确认订单时间")
    String confirmTime;
    @ApiModelProperty(value = "供应商实际发货时间")
    String shipTime;
    @ApiModelProperty(value = "收货时间")
    String signingTime;
}
