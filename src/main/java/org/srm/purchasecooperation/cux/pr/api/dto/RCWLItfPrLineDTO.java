package org.srm.purchasecooperation.cux.pr.api.dto;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.integration.annotation.Default;
import org.srm.purchasecooperation.cux.pr.domain.repository.RCWLItfPrDataRespository;
import org.srm.purchasecooperation.pr.domain.entity.PrHeader;

import javax.validation.constraints.NotNull;
import java.text.SimpleDateFormat;

/**
 * @description:预算占用/释放接口行
 * @author: bin.zhang
 * @createDate: 2021/4/9 15:52
 */
public class RCWLItfPrLineDTO {

    @Autowired
    private static RCWLItfPrDataRespository rcwlItfPrDataRespository;

    @ApiModelProperty(value = "单据日期")
    @NotNull
    private String BILLDATE;
    @ApiModelProperty(value = "创建人")
    @NotNull
    private String CREATEUSER;
    @ApiModelProperty(value = "组织机构")
    @NotNull
    private String UNITCODE;
    @ApiModelProperty(value = "预算类型")
    @NotNull
    private String YSLX;
    @ApiModelProperty(value = "外部单据标识")
    @NotNull
    private String MEXTERNALSYSID;

    @ApiModelProperty(value = "外部单据编号")
    @NotNull
    private String PAYMENBILLCODE;



    public String getBILLDATE() {
        return BILLDATE;
    }

    public void setBILLDATE(String BILLDATE) {
        this.BILLDATE = BILLDATE;
    }

    public String getCREATEUSER() {
        return CREATEUSER;
    }

    public void setCREATEUSER(String CREATEUSER) {
        this.CREATEUSER = CREATEUSER;
    }

    public String getUNITCODE() {
        return UNITCODE;
    }

    public void setUNITCODE(String UNITCODE) {
        this.UNITCODE = UNITCODE;
    }

    public String getYSLX() {
        return YSLX;
    }

    public void setYSLX(String YSLX) {
        this.YSLX = YSLX;
    }

    public String getMEXTERNALSYSID() {
        return MEXTERNALSYSID;
    }

    public void setMEXTERNALSYSID(String MEXTERNALSYSID) {
        this.MEXTERNALSYSID = MEXTERNALSYSID;
    }

    public String getPAYMENBILLCODE() {
        return PAYMENBILLCODE;
    }

    public void setPAYMENBILLCODE(String PAYMENBILLCODE) {
        this.PAYMENBILLCODE = PAYMENBILLCODE;
    }

    @Override
    public String toString() {
        return "RCWLItfPrLineDTO{" +
                "BILLDATE='" + BILLDATE + '\'' +
                ", CREATEUSER='" + CREATEUSER + '\'' +
                ", UNITCODE='" + UNITCODE + '\'' +
                ", YSLX='" + YSLX + '\'' +
                ", MEXTERNALSYSID='" + MEXTERNALSYSID + '\'' +
                ", PAYMENBILLCODE='" + PAYMENBILLCODE + '\'' +
                '}';
    }

    public static RCWLItfPrLineDTO initOccupy(PrHeader prHeader, Long tenantId) {
        RCWLItfPrLineDTO itfPrLineDTO = new RCWLItfPrLineDTO();
        itfPrLineDTO.setMEXTERNALSYSID("CG");
        itfPrLineDTO.setYSLX("01");
        itfPrLineDTO.setCREATEUSER("jg");
        SimpleDateFormat formatter = new SimpleDateFormat("YYYY-MM-DD");
        String dateString = formatter.format(prHeader.getCreationDate());
        itfPrLineDTO.setBILLDATE(dateString);
        itfPrLineDTO.setPAYMENBILLCODE(prHeader.getPrNum());
        //测试使用
        itfPrLineDTO.setUNITCODE("01");
//        String unitCode = rcwlItfPrDataRespository.selectSapCode(prHeader.getCompanyId(),tenantId);
//        itfPrLineDTO.setUNITCODE(unitCode);
        return itfPrLineDTO;
    }
}
