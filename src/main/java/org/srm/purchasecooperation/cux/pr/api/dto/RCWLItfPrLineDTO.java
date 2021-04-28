package org.srm.purchasecooperation.cux.pr.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.choerodon.core.exception.CommonException;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.srm.purchasecooperation.pr.domain.entity.PrHeader;
import org.srm.purchasecooperation.cux.pr.domain.repository.RCWLItfPrDataRespository;

import java.text.SimpleDateFormat;

/**
 * @description:预算占用/释放接口行
 * @author: bin.zhang
 * @createDate: 2021/4/9 15:52
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RCWLItfPrLineDTO {

    @Autowired
    private static RCWLItfPrDataRespository rcwlItfPrDataRespository;

    @ApiModelProperty(value = "单据日期")
    @JsonProperty("BILLDATE")
    private String billdate;
    @ApiModelProperty(value = "创建人")
    @JsonProperty("CREATEUSER")
    private String createuser;
    @ApiModelProperty(value = "组织机构")
    @JsonProperty("UNITCODE")
    private String unitcode;
    @ApiModelProperty(value = "预算类型")
    @JsonProperty("YSLX")
    private String yslx;
    @ApiModelProperty(value = "外部单据标识")
    @JsonProperty("MEXTERNALSYSID")
    private String mexternalsysid;

    @ApiModelProperty(value = "外部单据编号")
    @JsonProperty("PAYMENTBILLCODE")
    private String paymentbillcode;

    public static RCWLItfPrDataRespository getRcwlItfPrDataRespository() {
        return rcwlItfPrDataRespository;
    }

    public static void setRcwlItfPrDataRespository(RCWLItfPrDataRespository rcwlItfPrDataRespository) {
        RCWLItfPrLineDTO.rcwlItfPrDataRespository = rcwlItfPrDataRespository;
    }

    public String getBilldate() {
        return billdate;
    }

    public void setBilldate(String billdate) {
        this.billdate = billdate;
    }

    public String getCreateuser() {
        return createuser;
    }

    public void setCreateuser(String createuser) {
        this.createuser = createuser;
    }

    public String getUnitcode() {
        return unitcode;
    }

    public void setUnitcode(String unitcode) {
        this.unitcode = unitcode;
    }

    public String getYslx() {
        return yslx;
    }

    public void setYslx(String yslx) {
        this.yslx = yslx;
    }

    public String getMexternalsysid() {
        return mexternalsysid;
    }

    public void setMexternalsysid(String mexternalsysid) {
        this.mexternalsysid = mexternalsysid;
    }

    public String getPaymentbillcode() {
        return paymentbillcode;
    }

    public void setPaymentbillcode(String paymentbillcode) {
        this.paymentbillcode = paymentbillcode;
    }

//    public static RCWLItfPrLineDTO initOccupy(PrHeader prHeader, Long tenantId, String flag) {
//        RCWLItfPrLineDTO itfPrLineDTO = new RCWLItfPrLineDTO();
//        itfPrLineDTO.setMexternalsysid("CG");
//        //01占用 02释放
//        if("O".equals(flag)){
//            itfPrLineDTO.setYslx("01");
//        }else if("R".equals(flag)){
//            itfPrLineDTO.setYslx("02");
//        }
//
//        itfPrLineDTO.setCreateuser("jg");
//        SimpleDateFormat formatter = new SimpleDateFormat("YYYY-MM-DD");
//        String dateString = formatter.format(prHeader.getCreationDate());
//        itfPrLineDTO.setBilldate(dateString);
//        itfPrLineDTO.setPaymentbillcode(prHeader.getPrNum());
//        //测试使用
//      //  itfPrLineDTO.setUnitcode("01");
//        String unitCode = rcwlItfPrDataRespository.selectSapCode(prHeader.getCompanyId(),tenantId);
//        if(StringUtils.isEmpty(unitCode)){
//            throw new CommonException("组织机构不能为空");
//        }
//        itfPrLineDTO.setUnitcode(unitCode);
//        return itfPrLineDTO;
//    }


}