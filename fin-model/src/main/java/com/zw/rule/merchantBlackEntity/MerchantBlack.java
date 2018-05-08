package com.zw.rule.merchantBlackEntity;

/**
 * Created by Administrator on 2017/12/9.
 * 商户黑名单实体类
 */
public class MerchantBlack {
    private String id;
    private String blackTypeId;
    private String content;
    private String state;
    private String createTime;

    public MerchantBlack() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBlackTypeId() {
        return blackTypeId;
    }

    public void setBlackTypeId(String blackTypeId) {
        this.blackTypeId = blackTypeId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    @Override
    public String toString() {
        return "merchantBlack{" +
                "id='" + id + '\'' +
                ", blackTypeId='" + blackTypeId + '\'' +
                ", content='" + content + '\'' +
                ", state='" + state + '\'' +
                ", createTime='" + createTime + '\'' +
                '}';
    }
}
