package org.srm.purchasecooperation.cux.pr.api.dto;

/**
 * @description:授权返回结果
 * @author: bin.zhang
 * @createDate: 2021/4/12 21:10
 */
public class RCWLTokenGetResponseDTO {
    private Integer code;
    private String msg;
    private String token;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "RCWLTokenGetResponseDTO{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
