package org.srm.purchasecooperation.cux.pr.api.dto;

/**
 * @description:授权获取请求
 * @author: bin.zhang
 * @createDate: 2021/4/12 21:07
 */
public class RCWLTokenGetRequestDTO {
    private String openid;
    private String username;

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "RCWLTokenGetDTO{" +
                "openid='" + openid + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
