package org.srm.purchasecooperation.cux.pr.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

/**
 * @description:预算占用明细数据
 * @author: bin.zhang
 * @createDate: 2021/4/10 10:21
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RCWLItfPrLineDetailDTO {

    @JsonProperty("YSZYJE")
    @ApiModelProperty(value = "预算占用金额")
    private String yszyje;
    @JsonProperty("CPLXCODE")
    @ApiModelProperty(value = "产品类型编码")
    private String cplxcode;
    @JsonProperty("CPLXNAME")
    @ApiModelProperty(value = "产品类型名称")
    private String cplxname;
    @JsonProperty("YWYTCODE")
    @ApiModelProperty(value = "业务用途编码")
    private String ymytcode;
    @JsonProperty("YWYTNAME")
    @ApiModelProperty(value = "业务用途名称")
    private String ymytname;
    @JsonProperty("LINE")
    @ApiModelProperty(value = "行号")
    private String line;

    @ApiModelProperty(value = "预算占用日期")
    @JsonProperty("YSDATE")
    private String ysdate;
    @ApiModelProperty(value = "预留字段")
    private String YLZDA1;
    @ApiModelProperty(value = "预留字段")
    private String YLZDA2;
    @ApiModelProperty(value = "预留字段")
    private String YLZDB1;
    @ApiModelProperty(value = "预留字段")
    private String YLZDB2;


    public String getYsdate() {
        return ysdate;
    }

    public void setYsdate(String ysdate) {
        this.ysdate = ysdate;
    }

    public String getYszyje() {
        return yszyje;
    }

    public void setYszyje(String yszyje) {
        this.yszyje = yszyje;
    }

    public String getCplxcode() {
        return cplxcode;
    }

    public void setCplxcode(String cplxcode) {
        this.cplxcode = cplxcode;
    }

    public String getCplxname() {
        return cplxname;
    }

    public void setCplxname(String cplxname) {
        this.cplxname = cplxname;
    }

    public String getYmytcode() {
        return ymytcode;
    }

    public void setYmytcode(String ymytcode) {
        this.ymytcode = ymytcode;
    }

    public String getYmytname() {
        return ymytname;
    }

    public void setYmytname(String ymytname) {
        this.ymytname = ymytname;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
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
}
