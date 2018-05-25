package com.zw.rule.api.common;

import java.io.Serializable;

/**
 * 基础settings
 * @author 仙海峰
 */
public class BaseSettings implements Serializable {

    private static final long serialVersionUID = 5030594775406641718L;
    /**
     * 请求url
     */
    private String requestUrl;

    /**
     * 请求类型
     */
    private String requestType;

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }
}
