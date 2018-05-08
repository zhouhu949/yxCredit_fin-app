package com.zw.rule.sms.po;

/**
 * Created by Administrator on 2017/9/19.
 */
public class SmsManage {
    private String id;
    private String sms_name;
    private String sms_content;
    private String sms_rule;
    private String sms_state;
    private String create_time;
    private String alter_time;
    private String platform_type;

    public String getPlatform_type() {
        return platform_type;
    }

    public void setPlatform_type(String platform_type) {
        this.platform_type = platform_type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSms_name() {
        return sms_name;
    }

    public void setSms_name(String sms_name) {
        this.sms_name = sms_name;
    }

    public String getSms_content() {
        return sms_content;
    }

    public void setSms_content(String sms_content) {
        this.sms_content = sms_content;
    }

    public String getSms_rule() {
        return sms_rule;
    }

    public void setSms_rule(String sms_rule) {
        this.sms_rule = sms_rule;
    }

    public String getSms_state() {
        return sms_state;
    }

    public void setSms_state(String sms_state) {
        this.sms_state = sms_state;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getAlter_time() {
        return alter_time;
    }

    public void setAlter_time(String alter_time) {
        this.alter_time = alter_time;
    }
}
