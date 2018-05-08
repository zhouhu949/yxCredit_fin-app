package com.zw.rule.merchant;

/**
 * Created by zoukaixuan on 2017/12/16.
 */
public class Merchant {
    private String id;//商户id
    private String type;//商户类型
    private String merCode;//商户代码
    private String memSeq;//商户代码流水号
    private String merName;//商户名称
    private String merEmail;//商户邮箱
    private String provinces;//省
    private String city;//市
    private String distric;//区
    private String des;//商户描述
    private String address;//详细地址
    private String tel;//商户联系电话
    private String personName;//法人姓名
    private String personTel;//法人联系电话
    private String cardType;//证件类型
    private String card;//证件号码
    private String creatTime;//创建时间
    private String alterTime;//修改时间
    private String state;//状态
    private String apex1;//
    private String apex2;//
    private String apex3;//
    private String bak;//
    private String provincesId;//
    private String cityId;//
    private String districId;//
    private String salesmanId;//状态
    private String activationState;//激活状态(0未激活，1激活)
    private String applyName;//申请人姓名
    private String applyTel;//申请人电话
    private String applyIdcard;//申请人身份证号
    private String provinceCityiDstric;//省市区字符串粘结
    private String merGrade;//商户级别name
    private String merGradeId;//商户级别的id
    /************************/
    private String relState;//(这个是多加的字段，商户表里面并没有 是用来存商户和办单元关联状态的)
    private String mainBusiness;//主营业务


    private String checkState; //审核状态 0-待审核(默认) 1-审核中 2-审核通过 3-审核拒绝
    private String fanQIZhaState;//反欺诈状态 0-未发起(默认) 1-反欺诈进行中(反欺诈进行中不能审核操作) 2-反欺诈结束
    private String fanQiZhaManId;//反欺诈专员id
    private String fanQiZhaManName;//反欺诈专员名

    private String gradeName;

    public String getGradeName() {
        return gradeName;
    }

    public String getMerEmail() {
        return merEmail;
    }

    public void setMerEmail(String merEmail) {
        this.merEmail = merEmail;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public String getFanQiZhaManName() {
        return fanQiZhaManName;
    }

    public void setFanQiZhaManName(String fanQiZhaManName) {
        this.fanQiZhaManName = fanQiZhaManName;
    }

    public String getFanQiZhaManId() {
        return fanQiZhaManId;
    }

    public void setFanQiZhaManId(String fanQiZhaManId) {
        this.fanQiZhaManId = fanQiZhaManId;
    }

    public String getMainBusiness() {
        return mainBusiness;
    }

    public void setMainBusiness(String mainBusiness) {
        this.mainBusiness = mainBusiness;
    }

    public String getCheckState() {
        return checkState;
    }

    public void setCheckState(String checkState) {
        this.checkState = checkState;
    }

    public String getFanQIZhaState() {
        return fanQIZhaState;
    }

    public void setFanQIZhaState(String fanQIZhaState) {
        this.fanQIZhaState = fanQIZhaState;
    }

    public String getMerGradeId() {
        return merGradeId;
    }

    public void setMerGradeId(String merGradeId) {
        this.merGradeId = merGradeId;
    }

    public String getMerGrade() {
        return merGrade;
    }

    public void setMerGrade(String merGrade) {
        this.merGrade = merGrade;
    }

    public String getApplyTel() {
        return applyTel;
    }

    public void setApplyTel(String applyTel) {
        this.applyTel = applyTel;
    }

    public String getApplyIdcard() {
        return applyIdcard;
    }

    public void setApplyIdcard(String applyIdcard) {
        this.applyIdcard = applyIdcard;
    }

    public String getProvinceCityiDstric() {
        return provinceCityiDstric;
    }

    public void setProvinceCityiDstric(String provinceCityiDstric) {
        this.provinceCityiDstric = provinceCityiDstric;
    }

    public Merchant() {
    }

    public String getApplyName() {
        return applyName;
    }

    public void setApplyName(String applyName) {
        this.applyName = applyName;
    }

    @Override
    public String toString() {
        return "Merchant{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", merCode='" + merCode + '\'' +
                ", memSeq='" + memSeq + '\'' +
                ", merName='" + merName + '\'' +
                ", provinces='" + provinces + '\'' +
                ", city='" + city + '\'' +
                ", distric='" + distric + '\'' +
                ", des='" + des + '\'' +
                ", address='" + address + '\'' +
                ", tel='" + tel + '\'' +
                ", personName='" + personName + '\'' +
                ", personTel='" + personTel + '\'' +
                ", cardType='" + cardType + '\'' +
                ", card='" + card + '\'' +
                ", creatTime='" + creatTime + '\'' +
                ", alterTime='" + alterTime + '\'' +
                ", state='" + state + '\'' +
                ", apex1='" + apex1 + '\'' +
                ", apex2='" + apex2 + '\'' +
                ", apex3='" + apex3 + '\'' +
                ", bak='" + bak + '\'' +
                ", provincesId='" + provincesId + '\'' +
                ", cityId='" + cityId + '\'' +
                ", districId='" + districId + '\'' +
                ", salesmanId='" + salesmanId + '\'' +
                ", activationState='" + activationState + '\'' +
                ", relState='" + relState + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMerCode() {
        return merCode;
    }

    public void setMerCode(String merCode) {
        this.merCode = merCode;
    }

    public String getMemSeq() {
        return memSeq;
    }

    public void setMemSeq(String memSeq) {
        this.memSeq = memSeq;
    }

    public String getMerName() {
        return merName;
    }

    public void setMerName(String merName) {
        this.merName = merName;
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

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getPersonTel() {
        return personTel;
    }

    public void setPersonTel(String personTel) {
        this.personTel = personTel;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getApex1() {
        return apex1;
    }

    public void setApex1(String apex1) {
        this.apex1 = apex1;
    }

    public String getApex2() {
        return apex2;
    }

    public void setApex2(String apex2) {
        this.apex2 = apex2;
    }

    public String getApex3() {
        return apex3;
    }

    public void setApex3(String apex3) {
        this.apex3 = apex3;
    }

    public String getBak() {
        return bak;
    }

    public void setBak(String bak) {
        this.bak = bak;
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

    public String getSalesmanId() {
        return salesmanId;
    }

    public void setSalesmanId(String salesmanId) {
        this.salesmanId = salesmanId;
    }

    public String getActivationState() {
        return activationState;
    }

    public void setActivationState(String activationState) {
        this.activationState = activationState;
    }

    public String getRelState() {
        return relState;
    }

    public void setRelState(String relState) {
        this.relState = relState;
    }
}
