package com.zw.rule.customer.po;

import java.math.BigDecimal;

public class Customer {

    /****************************碧友信************************************/
    private String id;//用户编号

    private String userId;//APP用户ID

    private String personName;//用户姓名

    private String sexName;//性别

    private String tel;//手机号码

    private String card;//身份证号

    private String residenceAddress;//工作居住地地址

    private String cardRegisterAddress;//籍贯居住地址

    private String identityComplete;//身份认证完成度（完成显示100，未完成显示0）

    private String linkManComplete;//联系人完整度(100,完成，0未完成)

    private String personInfoComplete;//个人信息完成度(100已完成，0未完成)

    private String authorizationComplete;//授权完成度(100已完成，0未完成)

    private String baseinfoComplete; //基本信息完成度(100已完成，0未完成)

    private String isIdentity;//是否通过实名认证（1，已认证，2未认证）

    private String hometownHouseProperty;//老家住房性质

    private String childrenStatus;//子女状况

    private String maritalStatus;//婚姻状况

    private String creatTime;//创建时间



    /****************************碧友信************************************/

    private String state;//状态

    private String origin;//客户来源

    private String company;//公司

    private String branch;//部门分支

    private String manager;//客户经理

    private String alterTime;//更新时间

    private String saleAdviser;//销售顾问

    private String empId;//业务员ID

    private String empNumber;//业务员编号

    private String personId;

    private String contactId;

    private String cardType;

    private String credentialId;

    private String liveId;

    private String assetId;

    private String professionId;

    private String expendId;

    private String apex1;

    private String apex2;

    private String apex3;

    private String bak;

    private String occupationType;//所属职业类别 1,工薪者；4，在校学生；5，退休人员

    private String bgCustInfoId;//三码认证ID

    private String crmCustInfoId;//三码认证ID

    private String bgCustomerId;//三码认证ID

    private String cardTypeId;

    private String originId;

    private String creditPreAmount;//初始金额

    private String phoneTaskId;

    private String callRecordUrl;

    private String ninetyOneRule;//91结果

    private String phoneBookList;

    private String isOpenAccount;

    /** 剩余合同金额 */

    private BigDecimal surplusContractAmount;

    public BigDecimal getSurplusContractAmount() {
        return surplusContractAmount;
    }

    public void setSurplusContractAmount(BigDecimal surplusContractAmount) {
        this.surplusContractAmount = surplusContractAmount;
    }

    public String getPhoneBookList() {
        return phoneBookList;
    }

    public void setPhoneBookList(String phoneBookList) {
        this.phoneBookList = phoneBookList;
    }

    public String getNinetyOneRule() {
        return ninetyOneRule;
    }

    public void setNinetyOneRule(String ninetyOneRule) {
        this.ninetyOneRule = ninetyOneRule;
    }

    public String getCallRecordUrl() {
        return callRecordUrl;
    }

    public void setCallRecordUrl(String callRecordUrl) {
        this.callRecordUrl = callRecordUrl;
    }

    public String getPhoneTaskId() {
        return phoneTaskId;
    }

    public void setPhoneTaskId(String phoneTaskId) {
        this.phoneTaskId = phoneTaskId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId == null ? null : personId.trim();
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName == null ? null : personName.trim();
    }

    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId == null ? null : contactId.trim();
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel == null ? null : tel.trim();
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType == null ? null : cardType.trim();
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card == null ? null : card.trim();
    }

    public String getCredentialId() {
        return credentialId;
    }

    public void setCredentialId(String credentialId) {
        this.credentialId = credentialId == null ? null : credentialId.trim();
    }

    public String getLiveId() {
        return liveId;
    }

    public void setLiveId(String liveId) {
        this.liveId = liveId == null ? null : liveId.trim();
    }

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId == null ? null : assetId.trim();
    }

    public String getProfessionId() {
        return professionId;
    }

    public void setProfessionId(String professionId) {
        this.professionId = professionId == null ? null : professionId.trim();
    }

    public String getExpendId() {
        return expendId;
    }

    public void setExpendId(String expendId) {
        this.expendId = expendId == null ? null : expendId.trim();
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin == null ? null : origin.trim();
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company == null ? null : company.trim();
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch == null ? null : branch.trim();
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager == null ? null : manager.trim();
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

    public String getSaleAdviser() {
        return saleAdviser;
    }

    public void setSaleAdviser(String saleAdviser) {
        this.saleAdviser = saleAdviser == null ? null : saleAdviser.trim();
    }

    public String getOccupationType() {
        return occupationType;
    }

    public void setOccupationType(String occupationType) {
        this.occupationType = occupationType == null ? null : occupationType.trim();
    }

    public String getBgCustInfoId() {
        return bgCustInfoId;
    }

    public void setBgCustInfoId(String bgCustInfoId) {
        this.bgCustInfoId = bgCustInfoId == null ? null : bgCustInfoId.trim();
    }

    public String getCrmCustInfoId() {
        return crmCustInfoId;
    }

    public void setCrmCustInfoId(String crmCustInfoId) {
        this.crmCustInfoId = crmCustInfoId == null ? null : crmCustInfoId.trim();
    }

    public String getBgCustomerId() {
        return bgCustomerId;
    }

    public void setBgCustomerId(String bgCustomerId) {
        this.bgCustomerId = bgCustomerId == null ? null : bgCustomerId.trim();
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId == null ? null : empId.trim();
    }

    public String getCardTypeId() {
        return cardTypeId;
    }

    public void setCardTypeId(String cardTypeId) {
        this.cardTypeId = cardTypeId == null ? null : cardTypeId.trim();
    }

    public String getEmpNumber() {
        return empNumber;
    }

    public void setEmpNumber(String empNumber) {
        this.empNumber = empNumber == null ? null : empNumber.trim();
    }

    public String getOriginId() {
        return originId;
    }

    public void setOriginId(String originId) {
        this.originId = originId == null ? null : originId.trim();
    }

    public String getCreditPreAmount() {
        return creditPreAmount;
    }

    public void setCreditPreAmount(String creditPreAmount) {
        this.creditPreAmount = creditPreAmount;
    }

    public String getIsOpenAccount() {
        return isOpenAccount;
    }

    public void setIsOpenAccount(String isOpenAccount) {
        this.isOpenAccount = isOpenAccount;
    }

    public String getResidenceAddress() {
        return residenceAddress;
    }

    public void setResidenceAddress(String residenceAddress) {
        this.residenceAddress = residenceAddress;
    }

    public String getCardRegisterAddress() {
        return cardRegisterAddress;
    }

    public void setCardRegisterAddress(String cardRegisterAddress) {
        this.cardRegisterAddress = cardRegisterAddress;
    }

    public String getIdentityComplete() {
        return identityComplete;
    }

    public void setIdentityComplete(String identityComplete) {
        this.identityComplete = identityComplete;
    }

    public String getLinkManComplete() {
        return linkManComplete;
    }

    public void setLinkManComplete(String linkManComplete) {
        this.linkManComplete = linkManComplete;
    }

    public String getPersonInfoComplete() {
        return personInfoComplete;
    }

    public void setPersonInfoComplete(String personInfoComplete) {
        this.personInfoComplete = personInfoComplete;
    }

    public String getAuthorizationComplete() {
        return authorizationComplete;
    }

    public void setAuthorizationComplete(String authorizationComplete) {
        this.authorizationComplete = authorizationComplete;
    }

    public String getBaseinfoComplete() {
        return baseinfoComplete;
    }

    public void setBaseinfoComplete(String baseinfoComplete) {
        this.baseinfoComplete = baseinfoComplete;
    }

    public String getIsIdentity() {
        return isIdentity;
    }

    public void setIsIdentity(String isIdentity) {
        this.isIdentity = isIdentity;
    }

    public String getHometownHouseProperty() {
        return hometownHouseProperty;
    }

    public void setHometownHouseProperty(String hometownHouseProperty) {
        this.hometownHouseProperty = hometownHouseProperty;
    }

    public String getChildrenStatus() {
        return childrenStatus;
    }

    public void setChildrenStatus(String childrenStatus) {
        this.childrenStatus = childrenStatus;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getSexName() {
        return sexName;
    }

    public void setSexName(String sexName) {
        this.sexName = sexName;
    }
}