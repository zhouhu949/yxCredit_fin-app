package com.zw.rule.contractor.po;


import com.zw.rule.base.BaseEntity;

public class WhiteList extends BaseEntity {
    private String id;

    private String contractorId;

    private String contractorName;

    private String whiteStatus;

    private String realName;

    private String card;

    private String telphone;

    private String contractStatus;

    private String contractStartDate;

    private String contractEndDate;

    private String job;

    private String jobType;

    private String latestPayday;

    private String latestPay;

    private String payType;

    private String payProof;

    private String localMonthlyMinWage;

    public String getWhiteStatus() {
        return whiteStatus;
    }

    public void setWhiteStatus(String whiteStatus) {
        this.whiteStatus = whiteStatus;
    }

    public String getContractorName() {
        return contractorName;
    }

    public void setContractorName(String contractorName) {
        this.contractorName = contractorName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getContractorId() {
        return contractorId;
    }

    public void setContractorId(String contractorId) {
        this.contractorId = contractorId == null ? null : contractorId.trim();
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName == null ? null : realName.trim();
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card == null ? null : card.trim();
    }

    public String getTelphone() {
        return telphone;
    }

    public void setTelphone(String telphone) {
        this.telphone = telphone == null ? null : telphone.trim();
    }

    public String getContractStatus() {
        return contractStatus;
    }

    public void setContractStatus(String contractStatus) {
        this.contractStatus = contractStatus == null ? null : contractStatus.trim();
    }

    public String getContractStartDate() {
        return contractStartDate;
    }

    public void setContractStartDate(String contractStartDate) {
        this.contractStartDate = contractStartDate == null ? null : contractStartDate.trim();
    }

    public String getContractEndDate() {
        return contractEndDate;
    }

    public void setContractEndDate(String contractEndDate) {
        this.contractEndDate = contractEndDate == null ? null : contractEndDate.trim();
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job == null ? null : job.trim();
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType == null ? null : jobType.trim();
    }

    public String getLatestPayday() {
        return latestPayday;
    }

    public void setLatestPayday(String latestPayday) {
        this.latestPayday = latestPayday == null ? null : latestPayday.trim();
    }

    public String getLatestPay() {
        return latestPay;
    }

    public void setLatestPay(String latestPay) {
        this.latestPay = latestPay == null ? null : latestPay.trim();
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType == null ? null : payType.trim();
    }

    public String getPayProof() {
        return payProof;
    }

    public void setPayProof(String payProof) {
        this.payProof = payProof == null ? null : payProof.trim();
    }

    public String getLocalMonthlyMinWage() {
        return localMonthlyMinWage;
    }

    public void setLocalMonthlyMinWage(String localMonthlyMinWage) {
        this.localMonthlyMinWage = localMonthlyMinWage == null ? null : localMonthlyMinWage.trim();
    }
}
