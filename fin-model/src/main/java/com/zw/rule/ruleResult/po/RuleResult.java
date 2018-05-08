package com.zw.rule.ruleResult.po;

import java.util.List;

/**
 * Created by Administrator on 2017/12/5.
 */
public class RuleResult {
    private  String id;
    private  String orderId;
    private  String userId;
    private  String pid;
    private  String engineId;
    private  String state;
    private  String createTime;
    private  String ruleJson;
    private  String ruleName;
    private  String riskScore;
    private  String tongdunRule;
    private  String zenxintongRule;//天域分
    private List hitRule;


    public String getTongdunRule() {
        return tongdunRule;
    }

    public void setTongdunRule(String tongdunRule) {
        this.tongdunRule = tongdunRule;
    }

    public String getZenxintongRule() {
        return zenxintongRule;
    }

    public void setZenxintongRule(String zenxintongRule) {
        this.zenxintongRule = zenxintongRule;
    }

    public String getRiskScore() {
        return riskScore;
    }

    public void setRiskScore(String riskScore) {
        this.riskScore = riskScore;
    }

    public List getHitRule() {
        return hitRule;
    }

    public void setHitRule(List hitRule) {
        this.hitRule = hitRule;
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getEngineId() {
        return engineId;
    }

    public void setEngineId(String engineId) {
        this.engineId = engineId;
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

    public String getRuleJson() {
        return ruleJson;
    }

    public void setRuleJson(String ruleJson) {
        this.ruleJson = ruleJson;
    }
}
