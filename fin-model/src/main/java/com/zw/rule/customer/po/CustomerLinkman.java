package com.zw.rule.customer.po;

public class CustomerLinkman {
    private String id;

    private String customerId;

    private String creatTime;

    private String alterTime;

    private String state;//状态：0，未完成；1，完成

    private String apex1;

    private String apex2;

    private String apex3;

    private String bak;

    private String linkName;//联系人姓名

    private String relationship;//关系 -1：其他（其他联系人）0：其他（家庭联系人），1：父母，2：子女，3：亲兄弟姐妹，4：亲属，5：朋友，6：同事，7：同学

    private String knownLoan;//是否知晓此贷款 1：是；0：否

    private String contact;//联系方式

    private String idcardNum;//身份证号码

    private String workCompany;//工作单位

    private String mainSign;//是否主要联系人，1为是，0为否

    private String crmLinkmanId;

    private String relationshipName;//关系名称

    private String knownLoanName;//是否知晓贷款

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

    public String getLinkName() {
        return linkName;
    }

    public void setLinkName(String linkName) {
        this.linkName = linkName == null ? null : linkName.trim();
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship == null ? null : relationship.trim();
    }

    public String getKnownLoan() {
        return knownLoan;
    }

    public void setKnownLoan(String knownLoan) {
        this.knownLoan = knownLoan == null ? null : knownLoan.trim();
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact == null ? null : contact.trim();
    }

    public String getIdcardNum() {
        return idcardNum;
    }

    public void setIdcardNum(String idcardNum) {
        this.idcardNum = idcardNum == null ? null : idcardNum.trim();
    }

    public String getWorkCompany() {
        return workCompany;
    }

    public void setWorkCompany(String workCompany) {
        this.workCompany = workCompany == null ? null : workCompany.trim();
    }

    public String getMainSign() {
        return mainSign;
    }

    public void setMainSign(String mainSign) {
        this.mainSign = mainSign == null ? null : mainSign.trim();
    }

    public String getCrmLinkmanId() {
        return crmLinkmanId;
    }

    public void setCrmLinkmanId(String crmLinkmanId) {
        this.crmLinkmanId = crmLinkmanId == null ? null : crmLinkmanId.trim();
    }

    public String getRelationshipName() {
        return relationshipName;
    }

    public void setRelationshipName(String relationshipName) {
        this.relationshipName = relationshipName == null ? null : relationshipName.trim();
    }

    public String getKnownLoanName() {
        return knownLoanName;
    }

    public void setKnownLoanName(String knownLoanName) {
        this.knownLoanName = knownLoanName == null ? null : knownLoanName.trim();
    }
}