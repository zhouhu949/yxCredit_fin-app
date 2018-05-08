package com.zw.rule.customer.po;

public class CustomerJob {
    private String id;

    private String customerId;

    private String companyName;//单位名称

    private String companyProperty;//单位性质

    private String companyAddress;//单位地址

    private String companyPhone;//单位电话

    private String hiredate;//现单位入职时间

    private String department;//入职部门

    private String posLevel;//职级

    private String fundSocialsec;//是否缴纳公积金及社保

    private String payType;//工资发放形式

    private String creatTime;

    private String alterTime;

    private String provinceId;

    private String cityId;

    private String districtId;

    private String provinceName;

    private String cityName;

    private String districtName;

    private String address;//详细地址

    private String companyPropertyId;

    private String posLevelId;

    private String fundSocialsecId;

    private String payTypeId;

    private String monthIncome;//月收入

    private String monthPayday;//发薪日

    private String companyCode;

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
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

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName == null ? null : companyName.trim();
    }

    public String getCompanyProperty() {
        return companyProperty;
    }

    public void setCompanyProperty(String companyProperty) {
        this.companyProperty = companyProperty == null ? null : companyProperty.trim();
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress == null ? null : companyAddress.trim();
    }

    public String getCompanyPhone() {
        return companyPhone;
    }

    public void setCompanyPhone(String companyPhone) {
        this.companyPhone = companyPhone == null ? null : companyPhone.trim();
    }

    public String getHiredate() {
        return hiredate;
    }

    public void setHiredate(String hiredate) {
        this.hiredate = hiredate == null ? null : hiredate.trim();
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department == null ? null : department.trim();
    }

    public String getPosLevel() {
        return posLevel;
    }

    public void setPosLevel(String posLevel) {
        this.posLevel = posLevel == null ? null : posLevel.trim();
    }

    public String getFundSocialsec() {
        return fundSocialsec;
    }

    public void setFundSocialsec(String fundSocialsec) {
        this.fundSocialsec = fundSocialsec == null ? null : fundSocialsec.trim();
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType == null ? null : payType.trim();
    }

    public String getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(String creatTime) {
        this.creatTime = creatTime;
    }

    public String getAlterTime() {
        return alterTime;
    }

    public void setAlterTime(String alterTime) {
        this.alterTime = alterTime;
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getDistrictId() {
        return districtId;
    }

    public void setDistrictId(String districtId) {
        this.districtId = districtId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCompanyPropertyId() {
        return companyPropertyId;
    }

    public void setCompanyPropertyId(String companyPropertyId) {
        this.companyPropertyId = companyPropertyId;
    }

    public String getPosLevelId() {
        return posLevelId;
    }

    public void setPosLevelId(String posLevelId) {
        this.posLevelId = posLevelId;
    }

    public String getFundSocialsecId() {
        return fundSocialsecId;
    }

    public void setFundSocialsecId(String fundSocialsecId) {
        this.fundSocialsecId = fundSocialsecId;
    }

    public String getPayTypeId() {
        return payTypeId;
    }

    public void setPayTypeId(String payTypeId) {
        this.payTypeId = payTypeId;
    }

    public String getMonthIncome() {
        return monthIncome;
    }

    public void setMonthIncome(String monthIncome) {
        this.monthIncome = monthIncome;
    }

    public String getMonthPayday() {
        return monthPayday;
    }

    public void setMonthPayday(String monthPayday) {
        this.monthPayday = monthPayday;
    }
}