package org.srm.purchasecooperation.cux.pr.api.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 * @description:预算占用明细数据
 * @author: bin.zhang
 * @createDate: 2021/4/10 10:21
 */
public class RCWLItfPrLineDetailDTO {
    @ApiModelProperty(value = "预算占用金额")
    private String YSZYJE;
    @ApiModelProperty(value = "产品类型编码")
    private String CPLXCODE;
    @ApiModelProperty(value = "产品类型名称")
    private String CPLXNAME;
    @ApiModelProperty(value = "业务用途编码")
    private String YWYTCODE;
    @ApiModelProperty(value = "业务用途名称")
    private String YWYTNAME;
    @ApiModelProperty(value = "行号")
    private String YLZDA1;
    @ApiModelProperty(value = "预留字段")
    private String YLZDA2;
    @ApiModelProperty(value = "预留字段")
    private String YLZDB1;
    @ApiModelProperty(value = "预留字段")
    private String YLZDB2;

    public String getYSZYJE() {
        return YSZYJE;
    }

    public void setYSZYJE(String YSZYJE) {
        this.YSZYJE = YSZYJE;
    }

    public String getCPLXCODE() {
        return CPLXCODE;
    }

    public void setCPLXCODE(String CPLXCODE) {
        this.CPLXCODE = CPLXCODE;
    }

    public String getCPLXNAME() {
        return CPLXNAME;
    }

    public void setCPLXNAME(String CPLXNAME) {
        this.CPLXNAME = CPLXNAME;
    }

    public String getYWYTCODE() {
        return YWYTCODE;
    }

    public void setYWYTCODE(String YWYTCODE) {
        this.YWYTCODE = YWYTCODE;
    }

    public String getYWYTNAME() {
        return YWYTNAME;
    }

    public void setYWYTNAME(String YWYTNAME) {
        this.YWYTNAME = YWYTNAME;
    }

    public String getYLZDA1() {
        return YLZDA1;
    }

    public void setYLZDA1(String YLZDA1) {
        this.YLZDA1 = YLZDA1;
    }

    public String getYLZDA2() {
        return YLZDA2;
    }

    public void setYLZDA2(String YLZDA2) {
        this.YLZDA2 = YLZDA2;
    }

    public String getYLZDB1() {
        return YLZDB1;
    }

    public void setYLZDB1(String YLZDB1) {
        this.YLZDB1 = YLZDB1;
    }

    public String getYLZDB2() {
        return YLZDB2;
    }

    public void setYLZDB2(String YLZDB2) {
        this.YLZDB2 = YLZDB2;
    }

    @Override
    public String toString() {
        return "RCWLItfPrLineDetailDTO{" +
                "YSZYJE='" + YSZYJE + '\'' +
                ", CPLXCODE='" + CPLXCODE + '\'' +
                ", CPLXNAME='" + CPLXNAME + '\'' +
                ", YWYTCODE='" + YWYTCODE + '\'' +
                ", YWYTNAME='" + YWYTNAME + '\'' +
                ", YLZDA1='" + YLZDA1 + '\'' +
                ", YLZDA2='" + YLZDA2 + '\'' +
                ", YLZDB1='" + YLZDB1 + '\'' +
                ", YLZDB2='" + YLZDB2 + '\'' +
                '}';
    }
}
