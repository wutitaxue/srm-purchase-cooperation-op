package org.srm.purchasecooperation.cux.po.itf.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: longjunquan 21420
 * @Date: 2021/11/11 10:22
 * @Description:
 */
@Data
public class RcwlSkuInfoDTO {
    @ApiModelProperty(value = "sku商品编码")
    String skuCode;
    @ApiModelProperty(value = "sku商品名称")
    String skuName;
    @ApiModelProperty(value = "sku商品品类编码")
    String categoryCode;


}
