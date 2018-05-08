package com.zw.rule.task.po;

import java.util.Date;

public class ProcessTaskNode {
    private String id;

    private String relType;

    private String relId;

    private String taskId;

    private Long processNode;

    private Long operateUser;

    private Integer nodeState;

    private Date createTime;

    private Date commitTime;

    private Integer excType;//节点类型（自动流程节点0，人工节点1）

    private String nodeJson;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getPageUrl() {
        return pageUrl;
    }

    public void setPageUrl(String pageUrl) {
        this.pageUrl = pageUrl;
    }

    private String className;

    private String pageUrl;

    public String getHandUrl() {
        return handUrl;
    }

    public void setHandUrl(String handUrl) {
        this.handUrl = handUrl;
    }

    private String handUrl;

    public Integer getExcType() {
        return excType;
    }

    public void setExcType(Integer excType) {
        this.excType = excType;
    }

    public String getNodeJson() {
        return nodeJson;
    }

    public void setNodeJson(String nodeJson) {
        this.nodeJson = nodeJson;
    }

    public String getNodeScript() {
        return nodeScript;
    }

    public void setNodeScript(String nodeScript) {
        this.nodeScript = nodeScript;
    }

    private String nodeScript;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId == null ? null : taskId.trim();
    }

    public Long getProcessNode() {
        return processNode;
    }

    public void setProcessNode(Long processNode) {
        this.processNode = processNode;
    }

    public Long getOperateUser() {
        return operateUser;
    }

    public void setOperateUser(Long operateUser) {
        this.operateUser = operateUser;
    }

    public Integer getNodeState() {
        return nodeState;
    }

    public void setNodeState(Integer nodeState) {
        this.nodeState = nodeState;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getCommitTime() {
        return commitTime;
    }

    public void setCommitTime(Date commitTime) {
        this.commitTime = commitTime;
    }

    public String getRelId() {
        return relId;
    }

    public void setRelId(String relId) {
        this.relId = relId;
    }

    public String getRelType() {
        return relType;
    }

    public void setRelType(String relType) {
        this.relType = relType;
    }
}