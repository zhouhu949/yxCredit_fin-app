package com.zw.rule.approveRecord.po;

import java.math.BigDecimal;

public class OrderOperationRecord {
    private String id;//操作记录ID

    private Integer operationNode;//操作节点【订单申请（申请提交）、自动化审核（通过、拒绝）、人工审核（通过、拒绝、回退）、签约（同意、放弃）、放款审批（放款）、还款】

    private Integer operationResult;//操作结果【 1提交 2通过 3 拒绝 4 回退 5 同意  6放弃 7放款】
    private Integer status;//当前状态:(1有效 0无效）


    private BigDecimal amount;//金额（元）

    private String orderId;//关联订单ID

    private String operationTime;//操作时间

    private String empId;//操作人ID

    private String empName;//操作人姓名

    private String description;//操作描述

    private String amountOperationResultDescription;//金额+操作结果+操作描述

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public Integer getOperationNode() {
        return operationNode;
    }

    public void setOperationNode(Integer operationNode) {
        this.operationNode = operationNode;
    }

    public Integer getOperationResult() {
        return operationResult;
    }

    public void setOperationResult(Integer operationResult) {
        this.operationResult = operationResult;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public String getOperationTime() {
        return operationTime;
    }

    public void setOperationTime(String operationTime) {
        this.operationTime = operationTime == null ? null : operationTime.trim();
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId == null ? null : empId.trim();
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName == null ? null : empName.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getAmountOperationResultDescription() {
        return amountOperationResultDescription;
    }

    public void setAmountOperationResultDescription(String amountOperationResultDescription) {
        this.amountOperationResultDescription = amountOperationResultDescription;
    }
}