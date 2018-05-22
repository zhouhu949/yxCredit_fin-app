package com.zw.rule.apiresult;

import java.util.Date;

/**
 * 风控信息结果
 * @author 陈清玉
 */
public class ApiResult {

    private String apiResultId;
    private String code;
    private String message;
    private String sourceChildName;
    private String sourceChildCode;
    private String sourceCode;
    private String sourceName;
    private String realName;
    private String identityCode;
    private String userMobile;
    private String userName;
    private String orderId;
    private Object resultData;
    private Date createdTime;

    public String getApiResultId() {
        return apiResultId;
    }

    public void setApiResultId(String apiResultId) {
        this.apiResultId = apiResultId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSourceChildName() {
        return sourceChildName;
    }

    public void setSourceChildName(String sourceChildName) {
        this.sourceChildName = sourceChildName;
    }

    public String getSourceChildCode() {
        return sourceChildCode;
    }

    public void setSourceChildCode(String sourceChildCode) {
        this.sourceChildCode = sourceChildCode;
    }

    public String getSourceCode() {
        return sourceCode;
    }

    public void setSourceCode(String sourceCode) {
        this.sourceCode = sourceCode;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getIdentityCode() {
        return identityCode;
    }

    public void setIdentityCode(String identityCode) {
        this.identityCode = identityCode;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Object getResultData() {
        return resultData;
    }

    public void setResultData(Object resultData) {
        this.resultData = resultData;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }
}
