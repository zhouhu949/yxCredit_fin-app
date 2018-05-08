package com.zw.rule.entity;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/23 0023.
 */
public class CreditRequestParam implements Serializable {

    private static final long serialVersionUID = 3951858761077531880L;

    /**
     * 姓名
     */
    private String name;

    /**
     * 身份证号码
     */
    private String idNum;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 固定电话号码(家庭)
     */
    private String fixphone;

    /**
     * 固定电话号码(工作)
     */
    private String fixphoneWork;

    /**
     * qq号
     */
    private String qq;

    /**
     * 微信号
     */
    private String wechat;

    /**
     * 邮箱号码
     */
    private String email;

    /**
     * 职业
     */
    private String occupation;

    /**
     * 省
     */
    private String provice;

    /**
     * 市
     */
    private String city;

    /**
     * 区
     */
    private String district;

//    /**
//     * 联系人
//     */
//    private List<Contact> contacts;


    /**
     * 申请人申请的省
     */
    private String applyProvice;

    /**
     * 申请人申请的市
     */
    private String applyCity;

    /**
     * 其他参数，扩展
     */
    Map<String, Object> data;

    /**
     * 证件类型
     */
    private String idType;

    /**
     * 查询原因
     */
    private String queryReason;

    /**
     * 申请期数
     */
    private Integer peroids;

//    /**
//     * 联系人1
//     */
//    private Contact contact_frist;
//
//    /**
//     * 联系人2
//     */
//    private Contact contact_second;
//
//    /**
//     * 联系人3
//     */
//    private Contact contact_third;
//
//    /**
//     * 联系人4
//     */
//    private Contact contact_fourth;

//小窝新加字段开始+++++++++++++++++++++++++++++++++++++++++++++++++++++++
    /**
     * 性别
     * 男:0,女:1
     */
    private String sex;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 婚姻状况
     * 离异:0,未婚:1,已婚无子女:2,已婚有子女:3
     */
    private String marriage;

    /**
     * 户籍所在区域
     * 一线城市:0,二线城市:1,三线城市:2,四线城市:3,五线城市:4
     */
    private String area;

    /**
     * 当前居住情况
     * 自有房产:0,租房:1
     */
    private String house;

    /**
     * 现单位工作年限
     */
    private double workYear;

    /**
     * 单位性质
     * 事业单位:0,国企及上市公司:1,一般企业:2
     */
    private String conpanyStyle;

    /**
     * 是否缴纳公积金及社保
     * 公积金:0,社保:1,公积金和社保:2,无公积金和社保:3,
     */
    private String securityAndfund;

    /**
     * 职级
     * 高层管理:0,中层管理:1,基层管理:2,一般员工:3
     */
    private String workLevel;

    /**
     * 工资发放形式
     * 银行代发:0,现金:1
     */
    private String incomestyle;

    /**
     * 购买方式
     * 一次性购买：0，银行按揭：1
     */
    private String buyMode;

    /**
     * 按揭情况
     */
    private Integer mortgage;




//小窝新加字段结束+++++++++++++++++++++++++++++++++++++++++++++++++++++++



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdNum() {
        return idNum;
    }

    public void setIdNum(String idNum) {
        this.idNum = idNum;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getFixphone() {
        return fixphone;
    }

    public void setFixphone(String fixphone) {
        this.fixphone = fixphone;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

//    public List<Contact> getContacts() {
//        return contacts;
//    }
//
//    public void setContacts(List<Contact> contacts) {
//        this.contacts = contacts;
//    }

    public String getFixphoneWork() {
        return fixphoneWork;
    }

    public void setFixphoneWork(String fixphoneWork) {
        this.fixphoneWork = fixphoneWork;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }


    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public String getQueryReason() {
        return queryReason;
    }

    public void setQueryReason(String queryReason) {
        this.queryReason = queryReason;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getProvice() {
        return provice;
    }

    public void setProvice(String provice) {
        this.provice = provice;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public Integer getPeroids() {
        return peroids;
    }

    public void setPeroids(Integer peroids) {
        this.peroids = peroids;
    }
    public String getApplyProvice() {
        return applyProvice;
    }

    public void setApplyProvice(String applyProvice) {
        this.applyProvice = applyProvice;
    }

    public String getApplyCity() {
        return applyCity;
    }

    public void setApplyCity(String applyCity) {
        this.applyCity = applyCity;
    }

    @Override
    public String toString() {
        return "CreditRequestParam [name=" + name + ", idNum=" + idNum + ", mobile=" + mobile + ", fixphone=" + fixphone
                + ", fixphoneWork=" + fixphoneWork + ", qq=" + qq + ", wechat=" + wechat + ", email=" + email
                + ", occupation=" + occupation + ", provice=" + provice + ", city=" + city + ", district=" + district
                + ", applyProvice=" + applyProvice + ", applyCity=" + applyCity + ", data="
                + data + ", idType=" + idType + ", queryReason=" + queryReason + ", peroids=" + peroids + "]";
    }

    public String getKeyString() {
        return "MoxieVerifyRequest [mobile=" + mobile+ ",getIdNo()=" + idNum
                + ", name=" + name + "]";
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getMarriage() {
        return marriage;
    }

    public void setMarriage(String marriage) {
        this.marriage = marriage;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public double getWorkYear() {
        return workYear;
    }

    public void setWorkYear(double workYear) {
        this.workYear = workYear;
    }

    public String getConpanyStyle() {
        return conpanyStyle;
    }

    public void setConpanyStyle(String conpanyStyle) {
        this.conpanyStyle = conpanyStyle;
    }

    public String getSecurityAndfund() {
        return securityAndfund;
    }

    public void setSecurityAndfund(String securityAndfund) {
        this.securityAndfund = securityAndfund;
    }

    public String getWorkLevel() {
        return workLevel;
    }

    public void setWorkLevel(String workLevel) {
        this.workLevel = workLevel;
    }

    public String getIncomestyle() {
        return incomestyle;
    }

    public void setIncomestyle(String incomestyle) {
        this.incomestyle = incomestyle;
    }

    public String getBuyMode() {
        return buyMode;
    }

    public void setBuyMode(String buyMode) {
        this.buyMode = buyMode;
    }

    public Integer getMortgage() {
        return mortgage;
    }

    public void setMortgage(Integer mortgage) {
        this.mortgage = mortgage;
    }
}
