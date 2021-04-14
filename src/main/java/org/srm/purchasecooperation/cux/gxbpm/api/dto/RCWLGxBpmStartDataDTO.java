package org.srm.purchasecooperation.cux.gxbpm.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

/**
 * @author lu.cheng01@hand-china.com
 * @description
 * @date 2021/4/14 10:06
 * @version:1.0
 */
@JsonInclude
public class RCWLGxBpmStartDataDTO {

    @ApiModelProperty("业务单据ID")
    @NotNull
    private String btid;

    @ApiModelProperty("单据编号")
    @NotNull
    private String boid;

    @ApiModelProperty("默认0，如果是退回修改的流程，则需要将流程ID回传回来")
    @NotNull
    private String procinstId;

    @ApiModelProperty("子账户账号")
    @NotNull
    private String userId;

    @ApiModelProperty("业务数据")
    @NotNull
    private String data;

    public String getBoid() {
        return boid;
    }

    public void setBoid(String boid) {
        this.boid = boid;
    }

    public String getBtid() {
        return btid;
    }

    public void setBtid(String btid) {
        this.btid = btid;
    }

    public String getProcinstId() {
        return procinstId;
    }

    public void setProcinstId(String procinstId) {
        this.procinstId = procinstId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
