package com.zw.rule.customer.po;

/**
 * 通信信息表
 */
public class CustomerInvestigation {
    private String id;//主键

    private String orderId;
    
    private String customerId;

    private String answerTime;//接听时间

    private String answerTel;//接听电话

    private String answerState;//接听状态（0无人接听 1接通 2其它）

    private String type;//调查类型（1本人电话拨打记录  2网络调查  3114调查）

    private String soundSrc;//录音文件

    private String remark;//备注

    public String getSoundSrc() {
        return soundSrc;
    }

    public void setSoundSrc(String soundSrc) {
        this.soundSrc = soundSrc;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getAnswerTime() {
        return answerTime;
    }

    public void setAnswerTime(String answerTime) {
        this.answerTime = answerTime == null ? null : answerTime.trim();
    }

    public String getAnswerTel() {
        return answerTel;
    }

    public void setAnswerTel(String answerTel) {
        this.answerTel = answerTel == null ? null : answerTel.trim();
    }

    public String getAnswerState() {
        return answerState;
    }

    public void setAnswerState(String answerState) {
        this.answerState = answerState == null ? null : answerState.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}