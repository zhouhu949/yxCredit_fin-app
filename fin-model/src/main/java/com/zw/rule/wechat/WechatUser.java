package com.zw.rule.wechat;

/**
 * @Author WeiHong
 * @Create 2017/08/23 19:24
 **/
public class WechatUser {

    public WechatUser() {
    }

    public WechatUser(String openId) {
        this.openId = openId;
    }

    private String id;

    private String phone;

    private String accessToken;

    private String refreshToken;

    private String openId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }


    @Override
    public String toString() {
        return "WechatUser{" +
                "id='" + id + '\'' +
                ", phone='" + phone + '\'' +
                ", accessToken='" + accessToken + '\'' +
                ", refreshToken='" + refreshToken + '\'' +
                ", openId='" + openId + '\'' +
                '}';
    }
}
