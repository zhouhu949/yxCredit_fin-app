package com.zw.rule.wechat;

import java.util.List;

/**
 * Created by zl on 2017/8/29.
 */
public class WechatMsg {

    private String accessToken;

    private String openId;

    private String templateName;

    private List paramList;

    private String url;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public List getParamList() {
        return paramList;
    }

    public void setParamList(List paramList) {
        this.paramList = paramList;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "WechatMsg{" +
                "accessToken='" + accessToken + '\'' +
                ", openId='" + openId + '\'' +
                ", templateName='" + templateName + '\'' +
                ", paramList=" + paramList +
                ", url='" + url + '\'' +
                '}';
    }
}
