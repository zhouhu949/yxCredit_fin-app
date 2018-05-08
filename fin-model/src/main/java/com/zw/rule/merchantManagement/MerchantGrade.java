package com.zw.rule.merchantManagement;

/**
 * Created by Administrator on 2017/11/28.
 * gzd
 * 商户分级实体
 */
public class MerchantGrade {
    private  String id;
    private  String grade;//等级
    private  String numberDay;//日进件量限制
    private  String numberWeek;//周进件量限制
    private  String numberMonth;//月进件量限制
    private  String quotaDay;//日限制额
    private  String quotaWeek;//周限制额
    private  String quotaMonth;//月限制额
    private  String singleQuota;//笔限制额
    private  String state;//

    public MerchantGrade(String id, String grade, String numberDay, String numberWeek, String numberMonth, String quotaDay, String quotaWeek, String quotaMonth, String singleQuota, String state) {
        this.id = id;
        this.grade = grade;
        this.numberDay = numberDay;
        this.numberWeek = numberWeek;
        this.numberMonth = numberMonth;
        this.quotaDay = quotaDay;
        this.quotaWeek = quotaWeek;
        this.quotaMonth = quotaMonth;
        this.singleQuota = singleQuota;
        this.state = state;
    }

    public MerchantGrade() {

    }

    public String getId() {
        return id;
    }

    public String getGrade() {
        return grade;
    }

    public String getNumberDay() {
        return numberDay;
    }

    public String getNumberWeek() {
        return numberWeek;
    }

    public String getNumberMonth() {
        return numberMonth;
    }

    public String getQuotaDay() {
        return quotaDay;
    }

    public String getQuotaWeek() {
        return quotaWeek;
    }

    public String getQuotaMonth() {
        return quotaMonth;
    }

    public String getSingleQuota() {
        return singleQuota;
    }

    public String getState() {
        return state;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public void setNumberDay(String numberDay) {
        this.numberDay = numberDay;
    }

    public void setNumberWeek(String numberWeek) {
        this.numberWeek = numberWeek;
    }

    public void setNumberMonth(String numberMonth) {
        this.numberMonth = numberMonth;
    }

    public void setQuotaDay(String quotaDay) {
        this.quotaDay = quotaDay;
    }

    public void setQuotaWeek(String quotaWeek) {
        this.quotaWeek = quotaWeek;
    }

    public void setQuotaMonth(String quotaMonth) {
        this.quotaMonth = quotaMonth;
    }

    public void setSingleQuota(String singleQuota) {
        this.singleQuota = singleQuota;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "MerchantGrade{" +
                "id='" + id + '\'' +
                ", grade='" + grade + '\'' +
                ", numberDay='" + numberDay + '\'' +
                ", numberWeek='" + numberWeek + '\'' +
                ", numberMonth='" + numberMonth + '\'' +
                ", quotaDay='" + quotaDay + '\'' +
                ", quotaWeek='" + quotaWeek + '\'' +
                ", quotaMonth='" + quotaMonth + '\'' +
                ", singleQuota='" + singleQuota + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}
