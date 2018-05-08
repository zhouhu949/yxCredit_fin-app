package com.zw.rule.process.po;

import java.util.List;

/**
 * <strong>Title :<br>
 * </strong> <strong>Description : </strong>@类注释说明写在此处@<br>
 * <strong>Create on : 2017年06月13日<br>
 * </strong>
 * <p>
 * <strong>Copyright (C) Vbill Co.,Ltd.<br>
 * </strong>
 * <p>
 *
 * @author department:技术开发部 <br>
 *         username:Administrator <br>
 *         email: <br>
 * @version <strong>zw有限公司-运营平台</strong><br>
 *          <br>
 *          <strong>修改历史:</strong><br>
 *          修改人 修改日期 修改描述<br>
 *          -------------------------------------------<br>
 *          <br>
 *          <br>
 */
public class ProcessNode {
    private static final long serialVersionUID = -1867357850853531748L;

    private Long nodeId;//节点id

    private Long parentId;//父节点id

    private Long processId;//流程ID

    private String engineId;//引擎id

    private Long verId;//版本编号

    private String nodeName;//节点名称

    private  Integer excType;//节点执行类型

    private  String className;//执行类名称

    private  String pageUrl;//页面路径

    private  String nodeUrl;//节点图片路径

    private String nodeCode;//节点代号

    private Integer nodeOrder;//节点顺序

    private Integer nodeType;//节点类型

    private double nodeX;//节点横坐标

    private double nodeY;//节点纵坐标

    private String nodeScript;//节点脚本

    private String nodeJson;//节点信息

    private String params;//节点用到的参数列表

    private String nextNodes;//下个节点(可能是多个)

    private Long cardId;//评分卡Id

    private List<Long> ruleList;

    private Integer lastNextnode;

    private String handUrl;

    public String getHandUrl() {
        return handUrl;
    }

    public void setHandUrl(String handUrl) {
        this.handUrl = handUrl;
    }

    public Integer getLastNextnode() {
        return this.lastNextnode;
    }

    public void setLastNextnode(Integer lastNextnode) {
        this.lastNextnode = lastNextnode;
    }

    public Long getProcessId() {
        return this.processId;
    }

    public void setProcessId(Long processId) {
        this.processId = processId;
    }

    public Long getCardId() {
        return this.cardId;
    }

    public void setCardId(Long cardId) {
        this.cardId = cardId;
    }

    public String getNodeScript() {
        return this.nodeScript;
    }

    public void setNodeScript(String nodeScript) {
        this.nodeScript = nodeScript;
    }

    public String getEngineId() {
        return this.engineId;
    }

    public void setEngineId(String engineId) {
        this.engineId = engineId;
    }

    public String getNodeJson() {
        return this.nodeJson;
    }

    public void setNodeJson(String nodeJson) {
        this.nodeJson = nodeJson;
    }

    public Integer getExcType() {
        return excType;
    }

    public void setExcType(Integer excType) {
        this.excType = excType;
    }

    public String getParams() {
        return this.params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getNextNodes() {
        return this.nextNodes;
    }

    public void setNextNodes(String nextNodes) {
        this.nextNodes = nextNodes;
    }

    public Long getNodeId() {
        return this.nodeId;
    }

    public void setNodeId(Long nodeId) {
        this.nodeId = nodeId;
    }

    public Long getVerId() {
        return verId;
    }

    public void setVerId(Long verId) {
        this.verId = verId;
    }

    public String getNodeName() {
        return this.nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName == null?null:nodeName.trim();
    }

    public String getNodeCode() {
        return this.nodeCode;
    }

    public void setNodeCode(String nodeCode) {
        this.nodeCode = nodeCode == null?null:nodeCode.trim();
    }

    public Integer getNodeOrder() {
        return this.nodeOrder;
    }

    public void setNodeOrder(Integer nodeOrder) {
        this.nodeOrder = nodeOrder;
    }

    public Integer getNodeType() {
        return this.nodeType;
    }

    public void setNodeType(Integer nodeType) {
        this.nodeType = nodeType;
    }

    public double getNodeX() {
        return this.nodeX;
    }

    public void setNodeX(double nodeX) {
        this.nodeX = nodeX;
    }

    public double getNodeY() {
        return this.nodeY;
    }

    public void setNodeY(double nodeY) {
        this.nodeY = nodeY;
    }

    public List<Long> getRuleList() {
        return this.ruleList;
    }

    public void setRuleList(List<Long> ruleList) {
        this.ruleList = ruleList;
    }

    public Long getParentId() {
        return this.parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

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

    public String getNodeUrl() {
        return nodeUrl;
    }

    public void setNodeUrl(String nodeUrl) {
        this.nodeUrl = nodeUrl;
    }
}

