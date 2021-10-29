package org.srm.purchasecooperation.cux.po.itf.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.choerodon.mybatis.domain.AuditDomain;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hzero.core.base.BaseConstants;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Author: longjunquan 21420
 * @Date: 2021/10/25 15:51
 * @Description:
 */
@Data
public class RcwlSodrHzpoHeaderDTO  extends AuditDomain {

    private Long poHeaderId;
   
    private Long tenantId;

    @ApiModelProperty(value = "订单编号")
   
    private String poNum;

    @ApiModelProperty(value = "订单状态,值集SCUX_RCWL_HZPO_STATUS")
   
    private String statusCode;

    @ApiModelProperty(value = "酒店编号")
   
    private String purchaseOrgNum;

    @ApiModelProperty(value = "酒店名称")
   
    private String purchaseOrgName;

    @ApiModelProperty(value = "订单类型")
   
    private String poType;

    @ApiModelProperty(value = "下单日期")
    @DateTimeFormat(
            pattern = BaseConstants.Pattern.DATETIME
    )
    @JsonFormat(
            pattern = BaseConstants.Pattern.DATETIME,
            timezone = "GMT+8"
    )
    private Date orderDate;

    @ApiModelProperty(value = "供应商确认日期")
    @DateTimeFormat(
            pattern = BaseConstants.Pattern.DATETIME
    )
    @JsonFormat(
            pattern = BaseConstants.Pattern.DATETIME,
            timezone = "GMT+8"
    )
    private Date confirmedDate;

    @ApiModelProperty(value = "预计发货日期")
    @DateTimeFormat(
            pattern = BaseConstants.Pattern.DATETIME
    )
    @JsonFormat(
            pattern = BaseConstants.Pattern.DATETIME,
            timezone = "GMT+8"
    )
    private Date estimatedShippingDate;

    @ApiModelProperty(value = "首次发货日期")
    @DateTimeFormat(
            pattern = BaseConstants.Pattern.DATETIME
    )
    @JsonFormat(
            pattern = BaseConstants.Pattern.DATETIME,
            timezone = "GMT+8"
    )
    private Date firstShippingDate;

    @ApiModelProperty(value = "确认收货日期")
    @DateTimeFormat(
            pattern = BaseConstants.Pattern.DATETIME
    )
    @JsonFormat(
            pattern = BaseConstants.Pattern.DATETIME,
            timezone = "GMT+8"
    )
    private Date confirmReceiptDate;

    @ApiModelProperty(value = "供应商统一社会信用代码")
   
    private String unifiedSocialCode;

    @ApiModelProperty(value = "支付渠道")
    private String paymentChannel;

    @ApiModelProperty(value = "下单人")
   
    private String orderer;

    @ApiModelProperty(value = "订单含税总额")
   
    private BigDecimal taxIncludeAmount;

    @ApiModelProperty(value = "运费")
   
    private BigDecimal freightFee;

    @ApiModelProperty(value = "安装费")
   
    private BigDecimal installationFee;

    @ApiModelProperty(value = "优惠金额")
   
    private BigDecimal discountedPrice;

    @ApiModelProperty(value = "优惠原因")
   
    private String discountedReason;

    @ApiModelProperty(value = "收货地址（省）")
   
    private String shippingAddressProvince;

    @ApiModelProperty(value = "收货地址（市）")
   
    private String shippingAddressCity;

    @ApiModelProperty(value = "酒店品牌")
   
    private String hotelBrand;

    @ApiModelProperty(value = "酒店运营状态")
   
    private String hotelOperatingStatus;

    @ApiModelProperty(value = "采购方类型")
   
    private String buyerType;

    @ApiModelProperty(value = "等级")
   
    private String buyerClass;

    @ApiModelProperty(value = "订单协议价")
   
    private BigDecimal contractPrice;

    @ApiModelProperty(value = "佣金")
   
    private BigDecimal commission;

    @ApiModelProperty(value = "账期模式")
   
    private String paymentTerms;

    @ApiModelProperty(value = "二开订单行")
    private List<RcwlSodrHzpoLineDTO> data;
    
}
