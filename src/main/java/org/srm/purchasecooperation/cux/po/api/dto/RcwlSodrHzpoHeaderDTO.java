package org.srm.purchasecooperation.cux.po.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hzero.boot.platform.lov.annotation.LovValue;
import org.hzero.core.base.BaseConstants;
import org.hzero.export.annotation.ExcelColumn;
import org.hzero.export.annotation.ExcelSheet;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author jie.wang05@hand-china.com 2021/10/25 16:51
 * @description 华住订单头返回
 */
@Data
@ExcelSheet(zh = "采购订单头", pageSize = 100000000)
@AllArgsConstructor
@NoArgsConstructor
public class RcwlSodrHzpoHeaderDTO {
    private Long poHeaderId;
    @ApiModelProperty(value = "租户ID")
    private Long tenantId;
    @ApiModelProperty(value = "订单编号")
    @ExcelColumn(title = "订单编号", order = 20)
    private String poNum;
    @ApiModelProperty(value = "订单状态,值集SCUX_RCWL_HZPO_STATUS")
    @LovValue(
            lovCode = "SCUX_RCWL_HZPO_STATUS",
            meaningField = "statusCodeMeaning"
    )
    private String statusCode;
    @ExcelColumn(title = "订单状态", order = 10)
    private String statusCodeMeaning;
    @ApiModelProperty(value = "酒店编号")
    @ExcelColumn(title = "酒店编号", order = 30)
    private String purchaseOrgNum;
    @ApiModelProperty(value = "酒店名称")
    @ExcelColumn(title = "酒店名称", order = 40)
    private String purchaseOrgName;
    @ApiModelProperty(value = "订单类型")
    @ExcelColumn(title = "订单类型", order = 50)
    private String poType;
    @ApiModelProperty(value = "下单日期")
    @ExcelColumn(title = "下单日期", order = 80, pattern = BaseConstants.Pattern.DATETIME)
    @JsonFormat(
            pattern = BaseConstants.Pattern.DATETIME
    )
    @DateTimeFormat(
            pattern = BaseConstants.Pattern.DATETIME
    )
    private LocalDateTime orderDate;
    @ApiModelProperty(value = "供应商确认日期")
    @ExcelColumn(title = "供应商确认日期", order = 90, pattern = BaseConstants.Pattern.DATETIME)
    @JsonFormat(
            pattern = BaseConstants.Pattern.DATETIME
    )
    @DateTimeFormat(
            pattern = BaseConstants.Pattern.DATETIME
    )
    private LocalDateTime confirmedDate;
    @ApiModelProperty(value = "预计发货日期")
    @ExcelColumn(title = "预计发货日期", order = 100, pattern = BaseConstants.Pattern.DATETIME)
    @JsonFormat(
            pattern = BaseConstants.Pattern.DATETIME
    )
    @DateTimeFormat(
            pattern = BaseConstants.Pattern.DATETIME
    )
    private LocalDateTime estimatedShippingDate;
    @ApiModelProperty(value = "首次发货日期")
    @ExcelColumn(title = "首次发货日期", order = 110, pattern = BaseConstants.Pattern.DATETIME)
    @JsonFormat(
            pattern = BaseConstants.Pattern.DATETIME
    )
    @DateTimeFormat(
            pattern = BaseConstants.Pattern.DATETIME
    )
    private LocalDateTime firstShippingDate;
    @ApiModelProperty(value = "确认收货日期")
    @ExcelColumn(title = "确认收货日期", order = 120, pattern = BaseConstants.Pattern.DATETIME)
    @JsonFormat(
            pattern = BaseConstants.Pattern.DATETIME
    )
    @DateTimeFormat(
            pattern = BaseConstants.Pattern.DATETIME
    )
    private LocalDateTime confirmReceiptDate;
    @ApiModelProperty(value = "供应商统一社会信用代码")
    private String unifiedSocialCode;
    @ApiModelProperty(value = "支付渠道")
    @ExcelColumn(title = "支付渠道", order = 130)
    private String paymentChannel;
    @ApiModelProperty(value = "下单人")
    @ExcelColumn(title = "下单人", order = 140)
    private String orderer;
    @ApiModelProperty(value = "订单含税总额")
    @ExcelColumn(title = "订单含税总额", order = 150)
    private BigDecimal taxIncludeAmount;
    @ApiModelProperty(value = "运费")
    @ExcelColumn(title = "运费", order = 160)
    private BigDecimal freightFee;
    @ApiModelProperty(value = "安装费")
    @ExcelColumn(title = "安装费", order = 170)
    private BigDecimal installationFee;
    @ApiModelProperty(value = "优惠金额")
    @ExcelColumn(title = "优惠金额", order = 180)
    private BigDecimal discountedPrice;
    @ApiModelProperty(value = "优惠原因")
    @ExcelColumn(title = "优惠原因", order = 190)
    private String discountedReason;
    @ApiModelProperty(value = "收货地址（省）")
    @ExcelColumn(title = "收货地址（省）", order = 200)
    private String shippingAddressProvince;
    @ApiModelProperty(value = "收货地址（市）")
    @ExcelColumn(title = "收货地址（市）", order = 210)
    private String shippingAddressCity;
    @ApiModelProperty(value = "酒店品牌")
    @ExcelColumn(title = "酒店品牌", order = 220)
    private String hotelBrand;
    @ApiModelProperty(value = "酒店运营状态")
    @ExcelColumn(title = "酒店运营状态", order = 230)
    private String hotelOperatingStatus;
    @ApiModelProperty(value = "采购方类型")
    @ExcelColumn(title = "采购方类型", order = 240)
    private String buyerType;
    @ApiModelProperty(value = "等级")
    @ExcelColumn(title = "等级", order = 250)
    private String buyerClass;
    @ApiModelProperty(value = "订单协议价")
    @ExcelColumn(title = "订单协议价", order = 260)
    private BigDecimal contractPrice;
    @ApiModelProperty(value = "佣金")
    @ExcelColumn(title = "佣金", order = 270)
    private BigDecimal commission;
    @ApiModelProperty(value = "账期模式")
    @ExcelColumn(title = "账期模式", order = 280)
    private String paymentTerms;
    @ApiModelProperty("发票类型")
    @LovValue(
            lovCode = "SCUX_RCWL_INVOICE_TYPE",
            meaningField = "invoiceTypeMeaning"
    )
    private String invoiceType;
    @ExcelColumn(title = "发票类型", order = 300)
    private String invoiceTypeMeaning;

    @ApiModelProperty(value = "供应商编号")
    @ExcelColumn(title = "供应商编号", order = 60)
    private String supplierNum;
    @ApiModelProperty(value = "供应商名称")
    @ExcelColumn(title = "供应商名称", order = 70)
    private String supplierName;
    @ApiModelProperty(value = "商品分类")
    private String categoryName;
    @ApiModelProperty(value = "商品编码")
    private String skuNo;
    @ApiModelProperty(value = "商品名称")
    private String skuName;

    @ApiModelProperty(value = "采购订单行")
    @ExcelColumn(title = "采购订单行", order = 290, child = true)
    private List<RcwlSodrHzpoLineDTO> rcwlSodrHzpoLineDTOS;

    @ApiModelProperty(value = "头ID集合")
    private List<Long> poHeaderIds;

}
