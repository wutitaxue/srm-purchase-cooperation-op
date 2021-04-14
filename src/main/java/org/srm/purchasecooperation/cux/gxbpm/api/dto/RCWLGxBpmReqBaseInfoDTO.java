package org.srm.purchasecooperation.cux.gxbpm.api.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author lu.cheng01@hand-china.com
 * @description
 * @date 2021/4/12 21:10
 * @version:1.0
 */
@JsonInclude
@Data
@ToString
public class RCWLGxBpmReqBaseInfoDTO {

    @JsonProperty("REQ_SRC_SYS")
    private String reqSrcSys;

    @JsonProperty("REQ_BSN_ID")
    private String reqBsnId;

    @JsonProperty("REQ_REPEAT_CYCLE")
    private String reqRepeatCycle;

    @JsonProperty("REQ_RETRY_TIMES")
    private String reqRetryTimes;

    @JsonProperty("REQ_REPEAT_FLAG")
    private String reqRepeatFlag;

    @JsonProperty("REQ_SEND_TIME")
    private String reqSendTime;

    @JsonProperty("REQ_SYN_FLAG")
    private String reqSynFlag;

    @JsonProperty("COUNT")
    private String count;

    @JsonProperty("REQ_TAR_SYS")
    private String reqTarSys;

    @JsonProperty("REQ_SERVER_NAME")
    private String reqServerName;

    @JsonProperty("REQ_TRACE_ID")
    private String reqTraceId;

    @JsonProperty("BIZTRANSACTIONID")
    private String bizTransactionId;

    public String getReqSrcSys() {
        return reqSrcSys;
    }

    public void setReqSrcSys(String reqSrcSys) {
        this.reqSrcSys = reqSrcSys;
    }

    public String getReqBsnId() {
        return reqBsnId;
    }

    public void setReqBsnId(String reqBsnId) {
        this.reqBsnId = reqBsnId;
    }

    public String getReqRepeatCycle() {
        return reqRepeatCycle;
    }

    public void setReqRepeatCycle(String reqRepeatCycle) {
        this.reqRepeatCycle = reqRepeatCycle;
    }

    public String getReqRetryTimes() {
        return reqRetryTimes;
    }

    public void setReqRetryTimes(String reqRetryTimes) {
        this.reqRetryTimes = reqRetryTimes;
    }

    public String getReqRepeatFlag() {
        return reqRepeatFlag;
    }

    public void setReqRepeatFlag(String reqRepeatFlag) {
        this.reqRepeatFlag = reqRepeatFlag;
    }

    public String getReqSendTime() {
        return reqSendTime;
    }

    public void setReqSendTime(String reqSendTime) {
        this.reqSendTime = reqSendTime;
    }

    public String getReqSynFlag() {
        return reqSynFlag;
    }

    public void setReqSynFlag(String reqSynFlag) {
        this.reqSynFlag = reqSynFlag;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getReqTarSys() {
        return reqTarSys;
    }

    public void setReqTarSys(String reqTarSys) {
        this.reqTarSys = reqTarSys;
    }

    public String getReqServerName() {
        return reqServerName;
    }

    public void setReqServerName(String reqServerName) {
        this.reqServerName = reqServerName;
    }

    public String getReqTraceId() {
        return reqTraceId;
    }

    public void setReqTraceId(String reqTraceId) {
        this.reqTraceId = reqTraceId;
    }

    public String getBizTransactionId() {
        return bizTransactionId;
    }

    public void setBizTransactionId(String bizTransactionId) {
        this.bizTransactionId = bizTransactionId;
    }
}
