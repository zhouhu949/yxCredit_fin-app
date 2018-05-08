package com.zw.rule.customer.po;

public class CustomerPerson {
    private String id;

    private String customerId;

    private String realname;

    private String oldname;

    private String sex;

    private String creatTime;

    private String alterTime;

    private String state;

    private String apex1;

    private String apex2;

    private String apex3;

    private String bak;

    private String educational;//教育程度

    private String school;

    private String marry;//婚姻状况

    private String complete;//完成进度

    private String sexName;

    private String educationalName;

    private String marryName;
    private String childNum;

    public String getChildNum() {
        return childNum;
    }

    public void setChildNum(String childNum) {
        this.childNum = childNum;
    }

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

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname == null ? null : realname.trim();
    }

    public String getOldname() {
        return oldname;
    }

    public void setOldname(String oldname) {
        this.oldname = oldname == null ? null : oldname.trim();
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
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

    public String getEducational() {
        return educational;
    }

    public void setEducational(String educational) {
        this.educational = educational == null ? null : educational.trim();
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school == null ? null : school.trim();
    }

    public String getMarry() {
        return marry;
    }

    public void setMarry(String marry) {
        this.marry = marry == null ? null : marry.trim();
    }

    public String getComplete() {
        return complete;
    }

    public void setComplete(String complete) {
        this.complete = complete == null ? null : complete.trim();
    }

    public String getSexName() {
        return sexName;
    }

    public void setSexName(String sexName) {
        this.sexName = sexName == null ? null : sexName.trim();
    }

    public String getEducationalName() {
        return educationalName;
    }

    public void setEducationalName(String educationalName) {
        this.educationalName = educationalName == null ? null : educationalName.trim();
    }

    public String getMarryName() {
        return marryName;
    }

    public void setMarryName(String marryName) {
        this.marryName = marryName == null ? null : marryName.trim();
    }
}

