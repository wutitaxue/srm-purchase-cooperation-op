package org.srm.purchasecooperation.cux.po.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import org.hzero.export.annotation.ExcelColumn;
import org.hzero.export.annotation.ExcelSheet;

import java.math.BigDecimal;
import java.util.List;

/**
 * 华住订单行
 *
 * @author jie.wang05@hand-china.com 2021-10-25 16:36:06
 */
@Data
@ExcelSheet(zh = "采购订单行", pageSize = 100000000)
@Builder
public class RcwlSodrHzpoLineDTO {
    @ApiModelProperty(value = "行ID")
    private Long poLineId;
    @ApiModelProperty(value = "租户ID")
    private Long tenantId;
    @ApiModelProperty(value = "头ID")
    private Long poHeaderId;
    @ApiModelProperty(value = "行状态")
    @ExcelColumn(title = "行状态", order = 10)
    private String lineStatus;
    @ApiModelProperty(value = "行号")
    @ExcelColumn(title = "行号", order = 30)
    private String lineNum;
    @ApiModelProperty(value = "商品分类编号")
    private String skuCategoryCode;
    @ApiModelProperty(value = "商品编号")
    @ExcelColumn(title = "商品编号", order = 70)
    private String skuNo;
    @ApiModelProperty(value = "商品名称")
    @ExcelColumn(title = "商品名称", order = 80)
    private String skuName;
    @ApiModelProperty(value = "销售单价")
    @ExcelColumn(title = "销售单价", order = 90)
    private BigDecimal unitPrice;
    @ApiModelProperty(value = "购买数量")
    @ExcelColumn(title = "数量", order = 100)
    private BigDecimal quantity;
    @ApiModelProperty(value = "小计")
    @ExcelColumn(title = "行金额", order = 110)
    private BigDecimal lineAmount;
    @ApiModelProperty(value = "协议价")
    @ExcelColumn(title = "协议单价", order = 120)
    private BigDecimal contractLineAmount;
    @ApiModelProperty(value = "税率")
    @ExcelColumn(title = "税率", order = 130)
    private Long taxRate;

    @ApiModelProperty(value = "订单编号")
    @ExcelColumn(title = "订单号", order = 20)
    private String poNum;
    @ApiModelProperty(value = "酒店名称")
    @ExcelColumn(title = "酒店名称", order = 40)
    private String purchaseOrgName;
    @ApiModelProperty(value = "供应商名称")
    @ExcelColumn(title = "供应商名称", order = 50)
    private String supplierName;
    @ApiModelProperty(value = "商品分类")
    @ExcelColumn(title = "商品分类", order = 60)
    private String categoryName;

    @ApiModelProperty(value = "头id集合")
    private List<Long> poHeaderIds;
}
