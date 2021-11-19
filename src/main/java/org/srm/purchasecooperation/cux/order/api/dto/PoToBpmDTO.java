package org.srm.purchasecooperation.cux.order.api.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: pengxu.zhi@hand-china.com
 * @Date: 2021-10-31 15:51
 */
public class PoToBpmDTO implements Serializable {

    @ApiModelProperty(value = "SRM订单号")
    @JSONField(name = "DISPLAYPONUM")
    private String displayPoNum;

    @ApiModelProperty(value = "合同编号")
    @JSONField(name = "ERPCONTRACTNUM")
    private String erpContractNum;

    @ApiModelProperty(value = "合同名称")
    @JSONField(name = "ERPCONTRACTNAME")
    private String erpContractName;

    @ApiModelProperty(value = "不含税总金额")
    @JSONField(name = "AMOUNT")
    private String amount;

    @ApiModelProperty(value = "含税总金额")
    @JSONField(name = "TAXINCLUDEAMOUNT")
    private String taxIncludeAmount;

    @ApiModelProperty(value = "币种")
    @JSONField(name = "CURRENCYCODE")
    private String currencyCode;

    @ApiModelProperty(value = "公司")
    @JSONField(name = "COMPANYID")
    private String companyId;

    @ApiModelProperty(value = "供应商")
    @JSONField(name = "TEMPKEY")
    private String tempKey;

    @ApiModelProperty(value = "采购组织")
    @JSONField(name = "ESPURCHASEORGID")
    private String esPurchaseOrgId;

    @ApiModelProperty(value = "订单类型")
    @JSONField(name = "POTYPEID")
    private String poTypeId;

    @ApiModelProperty(value = "原订单号")
    @JSONField(name = "ATTRIBUTEVARCHAR1")
    private String attributeVarchar1;

    @ApiModelProperty(value = "采购员")
    @JSONField(name = "ESAGENTNAME")
    private String esAgentName;

    @ApiModelProperty(value = "创建时间")
    @JSONField(name = "PODATE")
    private String poDate;

    @ApiModelProperty(value = "备注")
    @JSONField(name = "REMARK")
    private String remark;


    //https://pssc.sunacctg.com/app/sodr/send-order/detail/1045?poSourcePlatform=SRM
    @JSONField(name = "URL_MX")
    private String zYunUrl;

    @JSONField(name = "FSubject")
    private String fSubject;

    @JSONField(name = "CGDDH")
    private List<PoToBpmLineDTO> poToBpmLineDTOList;

    public String getfSubject() {
        return fSubject;
    }

    public void setfSubject(String fSubject) {
        this.fSubject = fSubject;
    }

    public String getDisplayPoNum() {
        return displayPoNum;
    }

    public void setDisplayPoNum(String displayPoNum) {
        this.displayPoNum = displayPoNum;
    }

    public String getErpContractNum() {
        return erpContractNum;
    }

    public void setErpContractNum(String erpContractNum) {
        this.erpContractNum = erpContractNum;
    }

    public String getErpContractName() {
        return erpContractName;
    }

    public void setErpContractName(String erpContractName) {
        this.erpContractName = erpContractName;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTaxIncludeAmount() {
        return taxIncludeAmount;
    }

    public void setTaxIncludeAmount(String taxIncludeAmount) {
        this.taxIncludeAmount = taxIncludeAmount;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getTempKey() {
        return tempKey;
    }

    public void setTempKey(String tempKey) {
        this.tempKey = tempKey;
    }

    public String getEsPurchaseOrgId() {
        return esPurchaseOrgId;
    }

    public void setEsPurchaseOrgId(String esPurchaseOrgId) {
        this.esPurchaseOrgId = esPurchaseOrgId;
    }

    public String getPoTypeId() {
        return poTypeId;
    }

    public void setPoTypeId(String poTypeId) {
        this.poTypeId = poTypeId;
    }

    public String getAttributeVarchar1() {
        return attributeVarchar1;
    }

    public void setAttributeVarchar1(String attributeVarchar1) {
        this.attributeVarchar1 = attributeVarchar1;
    }

    public String getEsAgentName() {
        return esAgentName;
    }

    public void setEsAgentName(String esAgentName) {
        this.esAgentName = esAgentName;
    }

    public String getPoDate() {
        return poDate;
    }

    public void setPoDate(String poDate) {
        this.poDate = poDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getzYunUrl() {
        return zYunUrl;
    }

    public void setzYunUrl(String zYunUrl) {
        this.zYunUrl = zYunUrl;
    }

    public List<PoToBpmLineDTO> getPoToBpmLineDTOList() {
        return poToBpmLineDTOList;
    }

    public void setPoToBpmLineDTOList(List<PoToBpmLineDTO> poToBpmLineDTOList) {
        this.poToBpmLineDTOList = poToBpmLineDTOList;
    }
}
