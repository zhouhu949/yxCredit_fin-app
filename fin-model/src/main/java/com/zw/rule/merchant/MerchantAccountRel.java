package com.zw.rule.merchant;

/**
 * Created by Administrator on 2017/12/28.
 * 商户跟商户的账户的关联表数据实体类
 */
public class MerchantAccountRel {
    private String id;   //
    private String merchantId;//商户id
    private String name;//持卡人姓名
    private String tel;//持卡人电话
    private String idcard;//持卡人身份证号
    private String account;//账号(银行卡号)
    private String provinceId;//省id
    private String cityId;//城市ID
    private String provinceName;//省名
    private String cityName;//城市名
    private String bankName;//开户银行全名 如：建设银行经开区支行
    private String type;//账号类型 0-法人账号   1-委托收款人账号   2-对公账号  (0,1为私人账号)
    private String bankHead;//银行名称:如 建设银行，浦发银行
    private String bankHeadId;//银行名称id

    public String getBankHeadId() {
        return bankHeadId;
    }

    public void setBankHeadId(String bankHeadId) {
        this.bankHeadId = bankHeadId;
    }

    public  String getBankHead() {
        return bankHead;
    }

    public void setBankHead(String bankHead) {
        this.bankHead = bankHead;
    }

    public MerchantAccountRel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
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

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "MerchantAccountRel{" +
                "id='" + id + '\'' +
                ", merchantId='" + merchantId + '\'' +
                ", name='" + name + '\'' +
                ", tel='" + tel + '\'' +
                ", idcard='" + idcard + '\'' +
                ", account='" + account + '\'' +
                ", provinceId='" + provinceId + '\'' +
                ", cityId='" + cityId + '\'' +
                ", provinceName='" + provinceName + '\'' +
                ", cityName='" + cityName + '\'' +
                ", bankName='" + bankName + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
