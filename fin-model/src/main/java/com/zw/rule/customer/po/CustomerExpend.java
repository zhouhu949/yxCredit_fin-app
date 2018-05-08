package com.zw.rule.customer.po;

/**
 * Created by Administrator on 2017/7/12 0012.
 */
public class CustomerExpend {
    private String ID;

    private String CREAT_TIME;

    private String ALTER_TIME;

    private String state;

    private String APEX1;

    private String APEX2;

    private String APEX3;

    private String BAK;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getCREAT_TIME() {
        return CREAT_TIME;
    }

    public void setCREAT_TIME(String CREAT_TIME) {
        this.CREAT_TIME = CREAT_TIME;
    }

    public String getALTER_TIME() {
        return ALTER_TIME;
    }

    public void setALTER_TIME(String ALTER_TIME) {
        this.ALTER_TIME = ALTER_TIME;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getAPEX1() {
        return APEX1;
    }

    public void setAPEX1(String APEX1) {
        this.APEX1 = APEX1;
    }

    public String getAPEX2() {
        return APEX2;
    }

    public void setAPEX2(String APEX2) {
        this.APEX2 = APEX2;
    }

    public String getAPEX3() {
        return APEX3;
    }

    public void setAPEX3(String APEX3) {
        this.APEX3 = APEX3;
    }

    public String getBAK() {
        return BAK;
    }

    public void setBAK(String BAK) {
        this.BAK = BAK;
    }

    public String getMonthAverageexpense() {
        return monthAverageexpense;
    }

    public void setMonthAverageexpense(String monthAverageexpense) {
        this.monthAverageexpense = monthAverageexpense;
    }

    public String getMonthPay() {
        return monthPay;
    }

    public void setMonthPay(String monthPay) {
        this.monthPay = monthPay;
    }

    public String getSupportNum() {
        return supportNum;
    }

    public void setSupportNum(String supportNum) {
        this.supportNum = supportNum;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getMonthIncome() {
        return monthIncome;
    }

    public void setMonthIncome(String monthIncome) {
        this.monthIncome = monthIncome;
    }

    private String monthAverageexpense;

    private String monthPay;

    private String supportNum;

    private String customerId;

    private String monthIncome;


}
