package com.zw.rule.api.sortmsg;

import com.zw.rule.api.common.BaseSettings;

import java.util.Map;

/**
 * 短信配置
 * @author luochaofang
 */
public class MsgSettings extends BaseSettings {
    /**
     * 短信类型,0:验证码通知短信;1:会员营销短信;2:语音验证码短信
     */
    private String  type;

    private String content = "尊敬的用户，您的验证码为:${smsCode}";
    /**
     * 渠道唯一标识
     */
    private String  channelUniqId;
    /**
     * 模板业务编码（模板编号和短信内容不能都为空）
     */
    private String  templateCode;
    /**
     * 短信超时时间
     */
    private Long timeOut;
    /**
     * 短信模板自定义的参数放在名称为”data”的Map中（使用短信模板，有需要替换的参数则必须有，没有参数则不需要）
     */
    private Map<String,Object> data;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getChannelUniqId() {
        return channelUniqId;
    }

    public void setChannelUniqId(String channelUniqId) {
        this.channelUniqId = channelUniqId;
    }

    public String getTemplateCode() {
        return templateCode;
    }

    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(Long timeOut) {
        this.timeOut = timeOut;
    }

    @Override
    public String toString() {
        return "MsgSettings{" +
                ", type='" + type + '\'' +
                ", channelUniqId='" + channelUniqId + '\'' +
                ", templateCode='" + templateCode + '\'' +
                ", data=" + data +
                '}';
    }
}
