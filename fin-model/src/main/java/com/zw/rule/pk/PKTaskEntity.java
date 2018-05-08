package com.zw.rule.pk;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by WeiHong on 2017/6/30.
 */
public class PKTaskEntity implements Serializable {

    private static final long serialVersionUID = -7402823211666414370L;

    //系统来源（XOS、录单工具）
    private String resource;

    //订单ID
    private String orderId;

    //申请人姓名
    private String name;

    //申请人手机
    private String phone;

    //订单编号
    private String orderNO;

    //区域编码
    private int areaCode;

    //房产地址
    private String address;

    //户型Id
    private int houseTypeId;

    //预约时间 格式 : yyyy-MM-dd HH:mm:ss
    private String promiseCheckTime;

    //节点ID 1 2 3 4
    private int nodeId;

    //备注
    private String description;

    //银行编码
    private int bankCode;

    //性别（男、女）
    private String gender;

    //现场联系人
    private String siteContacts;

    //现场联系人电话
    private String siteContactsPhone;

    //验真ID
    private Long nodeCheckId;

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getOrderNO() {
        return orderNO;
    }

    public void setOrderNO(String orderNO) {
        this.orderNO = orderNO;
    }

    public int getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(int areaCode) {
        this.areaCode = areaCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getHouseTypeId() {
        return houseTypeId;
    }

    public void setHouseTypeId(int houseTypeId) {
        this.houseTypeId = houseTypeId;
    }

    public String getPromiseCheckTime() {
        return promiseCheckTime;
    }

    public void setPromiseCheckTime(String promiseCheckTime) {
        this.promiseCheckTime = promiseCheckTime;
    }

    public int getNodeId() {
        return nodeId;
    }

    public void setNodeId(int nodeId) {
        this.nodeId = nodeId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getBankCode() {
        return bankCode;
    }

    public void setBankCode(int bankCode) {
        this.bankCode = bankCode;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getSiteContacts() {
        return siteContacts;
    }

    public void setSiteContacts(String siteContacts) {
        this.siteContacts = siteContacts;
    }

    public String getSiteContactsPhone() {
        return siteContactsPhone;
    }

    public void setSiteContactsPhone(String siteContactsPhone) {
        this.siteContactsPhone = siteContactsPhone;
    }

    public Long getNodeCheckId() {
        return nodeCheckId;
    }

    public void setNodeCheckId(Long nodeCheckId) {
        this.nodeCheckId = nodeCheckId;
    }

    @Override
    public String toString() {
        return "PKTaskEntity{" +
                "resource='" + resource + '\'' +
                ", orderId='" + orderId + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", orderNO='" + orderNO + '\'' +
                ", areaCode=" + areaCode +
                ", address='" + address + '\'' +
                ", houseTypeId=" + houseTypeId +
                ", promiseCheckTime='" + promiseCheckTime + '\'' +
                ", nodeId=" + nodeId +
                ", description='" + description + '\'' +
                ", bankCode=" + bankCode +
                ", gender='" + gender + '\'' +
                ", siteContacts='" + siteContacts + '\'' +
                ", siteContactsPhone='" + siteContactsPhone + '\'' +
                ", nodeCheckId=" + nodeCheckId +
                '}';
    }
}
