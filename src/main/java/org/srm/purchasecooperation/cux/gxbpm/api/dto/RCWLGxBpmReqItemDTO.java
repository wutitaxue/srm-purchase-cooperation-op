package org.srm.purchasecooperation.cux.gxbpm.api.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author lu.cheng01@hand-china.com
 * @description
 * @date 2021/4/12 20:54
 * @version:1.0
 */
@JsonInclude
@ToString
@Data
public class RCWLGxBpmReqItemDTO {
    @JsonProperty("BTID")
    private String btid;
    @JsonProperty("BSID")
    private String bsid;
    @JsonProperty("PROCINSTID")
    private String procinstid;
    @JsonProperty("BOID")
    private String boid;
    @JsonProperty("USERID")
    private String userId;
    @JsonProperty("BSXML")
    private RCWLGxBpmBSXMLDTO bsxml;

    @JsonProperty("BTID")
    public String getBTID() {
        return btid;
    }

    public void setBTID(String btid) {
        this.btid = btid;
    }

    @JsonProperty("BSID")
    public String getBSID() {
        return bsid;
    }

    public void setBSID(String bsid) {
        this.bsid = bsid;
    }

    @JsonProperty("PROCINSTID")
    public String getProcinstid() {
        return procinstid;
    }

    public void setProcinstid(String procinstid) {
        this.procinstid = procinstid;
    }

    @JsonProperty("BOID")
    public String getBOID() {
        return boid;
    }

    public void setBOID(String boid) {
        this.boid = boid;
    }

    @JsonProperty("USERID")
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @JsonProperty("BSXML")
    public RCWLGxBpmBSXMLDTO getBSXML() {
        return bsxml;
    }

    public void setBSXML(RCWLGxBpmBSXMLDTO bsxml) {
        this.bsxml = bsxml;
    }
}
