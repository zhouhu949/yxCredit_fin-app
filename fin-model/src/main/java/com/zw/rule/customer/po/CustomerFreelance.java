package com.zw.rule.customer.po;

public class CustomerFreelance {
    private String id;

    private String customerId;

    private String sourceIncome;

    private String averageIncomeMonth;

    private String currentWorkYears;

    private String certificateName;

    private String creatTime;

    private String alterTime;

    private String state;

    private String apex1;

    private String apex2;

    private String apex3;

    private String bak;

    private String certificateId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId == null ? null : customerId.trim();
    }

    public String getSourceIncome() {
        return sourceIncome;
    }

    public void setSourceIncome(String sourceIncome) {
        this.sourceIncome = sourceIncome == null ? null : sourceIncome.trim();
    }

    public String getAverageIncomeMonth() {
        return averageIncomeMonth;
    }

    public void setAverageIncomeMonth(String averageIncomeMonth) {
        this.averageIncomeMonth = averageIncomeMonth == null ? null : averageIncomeMonth.trim();
    }

    public String getCurrentWorkYears() {
        return currentWorkYears;
    }

    public void setCurrentWorkYears(String currentWorkYears) {
        this.currentWorkYears = currentWorkYears == null ? null : currentWorkYears.trim();
    }

    public String getCertificateName() {
        return certificateName;
    }

    public void setCertificateName(String certificateName) {
        this.certificateName = certificateName == null ? null : certificateName.trim();
    }

    public String getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(String creatTime) {
        this.creatTime = creatTime == null ? null : creatTime.trim();
    }

    public String getAlterTime() {
        return alterTime;
    }

    public void setAlterTime(String alterTime) {
        this.alterTime = alterTime == null ? null : alterTime.trim();
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    public String getApex1() {
        return apex1;
    }

    public void setApex1(String apex1) {
        this.apex1 = apex1 == null ? null : apex1.trim();
    }

    public String getApex2() {
        return apex2;
    }

    public void setApex2(String apex2) {
        this.apex2 = apex2 == null ? null : apex2.trim();
    }

    public String getApex3() {
        return apex3;
    }

    public void setApex3(String apex3) {
        this.apex3 = apex3 == null ? null : apex3.trim();
    }

    public String getBak() {
        return bak;
    }

    public void setBak(String bak) {
        this.bak = bak == null ? null : bak.trim();
    }

    public String getCertificateId() {
        return certificateId;
    }

    public void setCertificateId(String certificateId) {
        this.certificateId = certificateId == null ? null : certificateId.trim();
    }
}