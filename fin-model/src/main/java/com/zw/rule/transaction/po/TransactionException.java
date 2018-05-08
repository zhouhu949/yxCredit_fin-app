package com.zw.rule.transaction.po;

/**
 * Created by Administrator on 2017/12/9.
 */
public class TransactionException {
    private  String id;
    private  String batchNo;
    private  String amount;
    private  String repaymentId;
    private  String orderId;
    private  String type;
    private  String state;
    private  String creatTime;
    private  String againBatchNo;
    private  String successTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getRepaymentId() {
        return repaymentId;
    }

    public void setRepaymentId(String repaymentId) {
        this.repaymentId = repaymentId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(String creatTime) {
        this.creatTime = creatTime;
    }

    public String getAgainBatchNo() {
        return againBatchNo;
    }

    public void setAgainBatchNo(String againBatchNo) {
        this.againBatchNo = againBatchNo;
    }

    public String getSuccessTime() {
        return successTime;
    }

    public void setSuccessTime(String successTime) {
        this.successTime = successTime;
    }
}
