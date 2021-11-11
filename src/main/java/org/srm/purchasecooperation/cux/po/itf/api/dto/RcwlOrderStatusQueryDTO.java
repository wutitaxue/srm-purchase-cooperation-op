package org.srm.purchasecooperation.cux.po.itf.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @Author: longjunquan 21420
 * @Date: 2021/11/9 15:24
 * @Description:
 */
@Data
public class RcwlOrderStatusQueryDTO {
    @ApiModelProperty(value = "渠道码")
    String ent;
    @ApiModelProperty(value = "订单 ID 列表 ")
    List<String> orderIdList;
    @ApiModelProperty(value = "更新时间开始时间")
    Long updateTimeBegin;
    @ApiModelProperty(value = "更新时间结束时间")
    Long updateTimeEnd;
}
