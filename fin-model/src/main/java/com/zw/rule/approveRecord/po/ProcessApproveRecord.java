package com.zw.rule.approveRecord.po;

public class ProcessApproveRecord {
    private String id;

    private String createTime;//创建时间

    private String commitTime;//处理时间

    private String result;//1通过  2拒绝

    private String processId;//流程id

    private String relId;//关联id  客户id等...

    private String type;//a商户 b客户

    private String orderId;//订单id

    private String processName;//流程名称

    private String nodeId;//节点id

    private String nodeName;//节点名称

    private String tache;//当前环节

    private String state;//当前状态

    private String scorecardscore;//评分卡评分

    private String approveSuggestion;//审批意见

    private String handlerId;//处理人id

    private String handlerName;//处理人姓名

    private String input;//入参

    private String credit;//金额

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime == null ? null : createTime.trim();
    }

    public String getCommitTime() {
        return commitTime;
    }

    public void setCommitTime(String commitTime) {
        this.commitTime = commitTime == null ? null : commitTime.trim();
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result == null ? null : result.trim();
    }

    public String getRelId() {
        return relId;
    }

    public void setRelId(String relId) {
        this.relId = relId == null ? null : relId.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName == null ? null : processName.trim();
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName == null ? null : nodeName.trim();
    }

    public String getTache() {
        return tache;
    }

    public void setTache(String tache) {
        this.tache = tache == null ? null : tache.trim();
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    public String getScorecardscore() {
        return scorecardscore;
    }

    public void setScorecardscore(String scorecardscore) {
        this.scorecardscore = scorecardscore == null ? null : scorecardscore.trim();
    }

    public String getApproveSuggestion() {
        return approveSuggestion;
    }

    public void setApproveSuggestion(String approveSuggestion) {
        this.approveSuggestion = approveSuggestion == null ? null : approveSuggestion.trim();
    }

    public String getHandlerId() {
        return handlerId;
    }

    public void setHandlerId(String handlerId) {
        this.handlerId = handlerId == null ? null : handlerId.trim();
    }

    public String getHandlerName() {
        return handlerName;
    }

    public void setHandlerName(String handlerName) {
        this.handlerName = handlerName == null ? null : handlerName.trim();
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input == null ? null : input.trim();
    }

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }
}