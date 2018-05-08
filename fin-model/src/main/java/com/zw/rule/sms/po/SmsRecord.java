package com.zw.rule.sms.po;

/**
 * Created by Administrator on 2017/10/23.
 */
public class SmsRecord {
    private String id;//主键
    private String smsId;//短息模板id
    private String customerId;//客户id
    private String tel;//手机号码
    private String content;//短信内容
    private String createTime;//创建时间

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSmsId() {
        return smsId;
    }

    public void setSmsId(String smsId) {
        this.smsId = smsId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreatTime() {
        return createTime;
    }

    public void setCreatTime(String creatTime) {
        this.createTime = creatTime;
    }
}
