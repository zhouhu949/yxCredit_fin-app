package com.zw.rule.merchant;

/**
 * Created by zoukaixuan on 2017/12/29.
 * 商户基本信息实体类
 */
public class MerchantBasicInformation {
    private String id;
    private String merName;//商户名
    private String merEmail;//商户邮箱
    private String licenseNumber;//营业执照注册号
    private String mainBusiness;//主营业务
    private String type;
    private String licenseDate;
    private String registeredCapital;
    private String employeesNumber;//员工人数
    private String provincesId;
    private String cityId;
    private String districId;
    private String provinces;
    private String city;
    private String distric;
    private String merTel;
    private String merDetailAddress;//详细地址
    private String applyName;
    private String applyTel;
    private String applyCard;
    private String merDes;//商户描述
    private String state;//状态 0-未激活 1-激活
    private String creatTime;//创建时间
    private String merPrivateAccountId;//商户对私主账户

    //额外数据
    private String relState;


//    par.approve_suggestion AS suggestion,
//    par.node_id AS node,
//    par.id AS processId
    private String suggestion;
    private String nodeId;
    private String processId;

    public String getSuggestion() {
        return suggestion;
    }

    public String getMerEmail() {
        return merEmail;
    }

    public void setMerEmail(String merEmail) {
        this.merEmail = merEmail;
    }

    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public String getRelState() {
        return relState;
    }

    public void setRelState(String relState) {
        this.relState = relState;
    }

    public MerchantBasicInformation() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMerName() {
        return merName;
    }

    public void setMerName(String merName) {
        this.merName = merName;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public String getMainBusiness() {
        return mainBusiness;
    }

    public void setMainBusiness(String mainBusiness) {
        this.mainBusiness = mainBusiness;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLicenseDate() {
        return licenseDate;
    }

    public void setLicenseDate(String licenseDate) {
        this.licenseDate = licenseDate;
    }

    public String getRegisteredCapital() {
        return registeredCapital;
    }

    public void setRegisteredCapital(String registeredCapital) {
        this.registeredCapital = registeredCapital;
    }

    public String getEmployeesNumber() {
        return employeesNumber;
    }

    public void setEmployeesNumber(String employeesNumber) {
        this.employeesNumber = employeesNumber;
    }

    public String getProvincesId() {
        return provincesId;
    }

    public void setProvincesId(String provincesId) {
        this.provincesId = provincesId;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getDistricId() {
        return districId;
    }

    public void setDistricId(String districId) {
        this.districId = districId;
    }

    public String getProvinces() {
        return provinces;
    }

    public void setProvinces(String provinces) {
        this.provinces = provinces;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistric() {
        return distric;
    }

    public void setDistric(String distric) {
        this.distric = distric;
    }

    public String getMerTel() {
        return merTel;
    }

    public void setMerTel(String merTel) {
        this.merTel = merTel;
    }

    public String getMerDetailAddress() {
        return merDetailAddress;
    }

    public void setMerDetailAddress(String merDetailAddress) {
        this.merDetailAddress = merDetailAddress;
    }

    public String getApplyName() {
        return applyName;
    }

    public void setApplyName(String applyName) {
        this.applyName = applyName;
    }

    public String getApplyTel() {
        return applyTel;
    }

    public void setApplyTel(String applyTel) {
        this.applyTel = applyTel;
    }

    public String getApplyCard() {
        return applyCard;
    }

    public void setApplyCard(String applyCard) {
        this.applyCard = applyCard;
    }

    public String getMerDes() {
        return merDes;
    }

    public void setMerDes(String merDes) {
        this.merDes = merDes;
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

    public String getMerPrivateAccountId() {
        return merPrivateAccountId;
    }

    public void setMerPrivateAccountId(String merPrivateAccountId) {
        this.merPrivateAccountId = merPrivateAccountId;
    }
}
