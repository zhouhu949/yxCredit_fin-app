package com.zw.rule.repayment.po;

import com.zw.rule.servicePackage.po.ServicePackageRepayment;

import java.util.List;

public class MagRepayment {
    private String id;

    private String loanId;

    private String payCount;

    private String fee;

    private String amount;

    private String rate;

    private String repaymentAmount;

    private String orderId;

    private String managefee;

    private String quicktrialfee;

    private String loanTime;

    private String payTime;

    private String overdueDays;

    private String penalty;

    private String createTime;

    private String state;

    private String remark;

    private String derateAmount;

    private String settleType;

    private String servicePackageAmount;

    private String totalFine;

    private String defaultInterest;

    public String getDefaultInterest() {
        return defaultInterest;
    }

    public void setDefaultInterest(String defaultInterest) {
        this.defaultInterest = defaultInterest;
    }

    public String getTotalFine() {
        return totalFine;
    }

    public void setTotalFine(String totalFine) {
        this.totalFine = totalFine;
    }

    public String getServicePackageAmount() {
        return servicePackageAmount;
    }

    public void setServicePackageAmount(String servicePackageAmount) {
        this.servicePackageAmount = servicePackageAmount;
    }

    public String getSettleType() {
        return settleType;
    }

    public void setSettleType(String settleType) {
        this.settleType = settleType;
    }

    public String getDerateAmount() {
        return derateAmount;
    }

    public void setDerateAmount(String derateAmount) {
        this.derateAmount = derateAmount;
    }

    private List<ServicePackageRepayment> servicePackageList;

    public List<ServicePackageRepayment> getServicePackageList() {
        return servicePackageList;
    }

    public void setServicePackageList(List<ServicePackageRepayment> servicePackageList) {
        this.servicePackageList = servicePackageList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId == null ? null : loanId.trim();
    }

    public String getPayCount() {
        return payCount;
    }

    public void setPayCount(String payCount) {
        this.payCount = payCount == null ? null : payCount.trim();
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee == null ? null : fee.trim();
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount == null ? null : amount.trim();
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate == null ? null : rate.trim();
    }

    public String getRepaymentAmount() {
        return repaymentAmount;
    }

    public void setRepaymentAmount(String repaymentAmount) {
        this.repaymentAmount = repaymentAmount == null ? null : repaymentAmount.trim();
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
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

    public String getLoanTime() {
        return loanTime;
    }

    public void setLoanTime(String loanTime) {
        this.loanTime = loanTime == null ? null : loanTime.trim();
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime == null ? null : payTime.trim();
    }

    public String getOverdueDays() {
        return overdueDays;
    }

    public void setOverdueDays(String overdueDays) {
        this.overdueDays = overdueDays == null ? null : overdueDays.trim();
    }

    public String getPenalty() {
        return penalty;
    }

    public void setPenalty(String penalty) {
        this.penalty = penalty == null ? null : penalty.trim();
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime == null ? null : createTime.trim();
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}