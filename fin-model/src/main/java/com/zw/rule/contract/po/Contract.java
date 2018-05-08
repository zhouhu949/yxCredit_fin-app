package com.zw.rule.contract.po;

import java.io.Serializable;

/**
 * Created by gaozhidong on 2017/6/27.
 */
public class Contract implements  Serializable {

    /**合同id**/
    private String id;

    /**模板名称**/
    private String name;

    /**状态**/
    private String state;

    /**模板类型1：合同，2：协议**/
    private String template_type;

    /**模板内容**/
    private String content;

    /**模板内容(不带html标签)**/
    private String content_no_bq;

    /**订单id**/
    private String orderId;

    /**创建时间**/
    private String createTime;

    /**修改时间**/
    private String alterTime;

    /**平台类型**/
    private String platformType;

    public String getPlatformType() {
        return platformType;
    }

    public void setPlatformType(String platformType) {
        this.platformType = platformType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getAlterTime() {
        return alterTime;
    }

    public void setAlterTime(String alterTime) {
        this.alterTime = alterTime;
    }

    public String getTemplate_type() {
        return template_type;
    }

    public void setTemplate_type(String template_type) {
        this.template_type = template_type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent_no_bq() {
        return content_no_bq;
    }

    public void setContent_no_bq(String content_no_bq) {
        this.content_no_bq = content_no_bq;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
