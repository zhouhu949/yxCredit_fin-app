package com.zw.rule.approveRecord.po;

import java.math.BigDecimal;

public class OrderOperationRecord {
    private String id;

    private Integer operationNode;

    private Integer operationResult;
    private Integer status;


    private BigDecimal amount;

    private String orderId;

    private String operationTime;

    private String empId;

    private String empName;

    private String description;

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
}