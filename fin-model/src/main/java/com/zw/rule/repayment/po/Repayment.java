package com.zw.rule.repayment.po;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/6/21 0021.
 */
public class Repayment implements Serializable {
    private static final long serialVersionUID = -1867357850853531748L;
    private String id;
    private String loanId;
    private String loanTime;
    private String amount;
    private String state;
    private String createTime;
    private String payTime;
    private String payCount;
    private String fee;
    private String repaymentAmount;
    private String rate;
    private String orderId;
    private String overdueDays;
    private String penalty;
    private String actualAmount;
    private String actualTime;
    private String derateAmount;
    private String servicePackageAmount;
    private String defaultInterest;
    private String effectiveData;

    public String getEffectiveData() {
        return effectiveData;
    }

    public void setEffectiveData(String effectiveData) {
        this.effectiveData = effectiveData;
    }

    public String getDefaultInterest() {
        return defaultInterest;
    }

    public void setDefaultInterest(String defaultInterest) {
        this.defaultInterest = defaultInterest;
    }

    public String getServicePackageAmount() {
        return servicePackageAmount;
    }

    public void setServicePackageAmount(String servicePackageAmount) {
        this.servicePackageAmount = servicePackageAmount;
    }

    public String getDerateAmount() {
        return derateAmount;
    }

    public void setDerateAmount(String derateAmount) {
        this.derateAmount = derateAmount;
    }

    public String getActualAmount() {
        return actualAmount;
    }

    public void setActualAmount(String actualAmount) {
        this.actualAmount = actualAmount;
    }

    public String getActualTime() {
        return actualTime;
    }

    public void setActualTime(String actualTime) {
        this.actualTime = actualTime;
    }

    public String getOverdueDays() {
        return overdueDays;
    }

    public void setOverdueDays(String overdueDays) {
        this.overdueDays = overdueDays;
    }

    public String getPenalty() {
        return penalty;
    }

    public void setPenalty(String penalty) {
        this.penalty = penalty;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId;
    }

    public String getLoanTime() {
        return loanTime;
    }

    public void setLoanTime(String loanTime) {
        this.loanTime = loanTime;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
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

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public String getPayCount() {
        return payCount;
    }

    public void setPayCount(String payCount) {
        this.payCount = payCount;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getRepaymentAmount() {
        return repaymentAmount;
    }

    public void setRepaymentAmount(String repaymentAmount) {
        this.repaymentAmount = repaymentAmount;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
