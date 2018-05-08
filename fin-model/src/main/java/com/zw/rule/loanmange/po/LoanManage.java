package com.zw.rule.loanmange.po;

public class LoanManage {
    private String id;

    private String userId;

    private String amount;

    private String state;

    private String createtime;

    private String updatetime;

    private String des;

    private String orderId;

    private String custId;


    private String loanTime;


    private String managefee;

    private String quicktrialfee;

    private String payableAmount;

    private String expirationDate;

    private String derateAmount;

    private String agentAmount;

    private String penalty;

    private String type;

    private String withdrawalsId;

    private String returnedAmount;

    private String unpaidAmount;

    private String settleState;

    public String getSettleState() {
        return settleState;
    }

    public void setSettleState(String settleState) {
        this.settleState = settleState;
    }

    public String getReturnedAmount() {
        return returnedAmount;
    }

    public void setReturnedAmount(String returnedAmount) {
        this.returnedAmount = returnedAmount;
    }

    public String getUnpaidAmount() {
        return unpaidAmount;
    }

    public void setUnpaidAmount(String unpaidAmount) {
        this.unpaidAmount = unpaidAmount;
    }

    public String getWithdrawalsId() {
        return withdrawalsId;
    }

    public void setWithdrawalsId(String withdrawals_id) {
        this.withdrawalsId = withdrawals_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount == null ? null : amount.trim();
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime == null ? null : createtime.trim();
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime == null ? null : updatetime.trim();
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des == null ? null : des.trim();
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId == null ? null : custId.trim();
    }


    public String getLoanTime() {
        return loanTime;
    }

    public void setLoanTime(String loanTime) {
        this.loanTime = loanTime == null ? null : loanTime.trim();
    }


    public String getManagefee() {
        return managefee;
    }

    public void setManagefee(String managefee) {
        this.managefee = managefee == null ? null : managefee.trim();
    }

    public String getQuicktrialfee() {
        return quicktrialfee;
    }

    public void setQuicktrialfee(String quicktrialfee) {
        this.quicktrialfee = quicktrialfee == null ? null : quicktrialfee.trim();
    }

    public String getPayableAmount() {
        return payableAmount;
    }

    public void setPayableAmount(String payableAmount) {
        this.payableAmount = payableAmount == null ? null : payableAmount.trim();
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate == null ? null : expirationDate.trim();
    }

    public String getDerateAmount() {
        return derateAmount;
    }

    public void setDerateAmount(String derateAmount) {
        this.derateAmount = derateAmount == null ? null : derateAmount.trim();
    }

    public String getAgentAmount() {
        return agentAmount;
    }

    public void setAgentAmount(String agentAmount) {
        this.agentAmount = agentAmount == null ? null : agentAmount.trim();
    }

    public String getPenalty() {
        return penalty;
    }

    public void setPenalty(String penalty) {
        this.penalty = penalty == null ? null : penalty.trim();
    }
}