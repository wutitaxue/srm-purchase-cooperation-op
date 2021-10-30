package org.srm.purchasecooperation.cux.po.itf.api.dto;

import io.choerodon.mybatis.domain.AuditDomain;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author: longjunquan 21420
 * @Date: 2021/10/25 15:53
 * @Description:
 */
@Data
public class RcwlSodrHzpoLineDTO extends AuditDomain {

    private Long poLineId;

    private Long tenantId;

    private Long poHeaderId;

    @ApiModelProperty(value = "行状态")
    private String lineStatus;

    @ApiModelProperty(value = "行号")
    private String lineNum;

    @ApiModelProperty(value = "商品分类编号")
    private String skuCategoryCode;

    @ApiModelProperty(value = "商品编号")
    private String skuNo;

    @ApiModelProperty(value = "商品名称")
    private String skuName;

    @ApiModelProperty(value = "销售单价")
    private BigDecimal unitPrice;

    @ApiModelProperty(value = "购买数量")
    private BigDecimal quantity;

    @ApiModelProperty(value = "小计")
    private BigDecimal lineAmount;

    @ApiModelProperty(value = "协议价")
    private BigDecimal contractLineAmount;

    @ApiModelProperty(value = "税率")
    private Long taxRate;

}
