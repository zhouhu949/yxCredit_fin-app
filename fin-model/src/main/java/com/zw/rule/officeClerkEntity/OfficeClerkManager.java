package com.zw.rule.officeClerkEntity;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/11/28.
 * 办单员数据模型实体类
 id
 employee_num
 idcard
 idcard_address
 realname
 date_bath
 sex
 tel
 passwd
 company
 branch
 post
 error_num
 now_address
 creat_time
 alter_time
 state
 apex1
 apex2
 apex3
 activation_state
 sex_name
 */
public class OfficeClerkManager implements Serializable {
    private String id;//办单员id
    private String employeeNum;//办单员工号
    private String idcard;//办单员身份证号
    private String idcardAddress; //身份证地址
    private String realname;//办单员真实姓名
    private String dateBath;//办单员出生年月
    private String sex;//办单员性别
    private String tel;//办单员电话
    private String passwd;//办单员密码
    private String company;//归属公司
    private String branch;//所属部门
    private String post;//岗位
    private String errorNum;//密码输入错误次数
    private String nowAddress;//现住址
    private String creatTime;//注册时间
    private String alterTime;//修改时间
    private String state;//'2失效 0登陆 1未登录'
    private String apex1;//备用字段
    private String apex2;//备用字段
    private String apex3;//备用字段
    private String activationState;//激活状态(0未激活  1激活)
    private String sexName;//性别
    private String branchId;//所属部门id
    private String postId;//岗位id
    private String pname;
    //以下几个属性只为接受参数而用，在其他地方没有任何作用
    private String zhengmian;
    private String fanmian;
    private String shouchi;
    private String qita;
    //办单员和商户的关联状态
    private String relState;
    //办单员和商户关联表mag_merchant_salesman_rel的 id relId
    private String relId;

    public String getRelId() {
        return relId;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public void setRelId(String relId) {
        this.relId = relId;
    }

    public String getRelState() {
        return relState;
    }

    public void setRelState(String relState) {
        this.relState = relState;
    }

    public String getZhengmian() {
        return zhengmian;
    }

    public void setZhengmian(String zhengmian) {
        this.zhengmian = zhengmian;
    }

    public String getFanmian() {
        return fanmian;
    }

    public void setFanmian(String fanmian) {
        this.fanmian = fanmian;
    }

    public String getShouchi() {
        return shouchi;
    }

    public void setShouchi(String shouchi) {
        this.shouchi = shouchi;
    }

    public String getQita() {
        return qita;
    }

    public void setQita(String qita) {
        this.qita = qita;
    }

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    //无参构造器
    public OfficeClerkManager() {
    }
    public OfficeClerkManager(String id, String employeeNum,
                              String idcard, String idcardAddress,
                              String realname, String dateBath,
                              String sex, String tel, String passwd,
                              String company, String branch, String post,
                              String errorNum, String nowAddress, String creatTime,
                              String alterTime, String state, String apex1,
                              String apex2, String apex3, String activationState,
                              String sexName) {
        this.id = id;
        this.employeeNum = employeeNum;
        this.idcard = idcard;
        this.idcardAddress = idcardAddress;
        this.realname = realname;
        this.dateBath = dateBath;
        this.sex = sex;
        this.tel = tel;
        this.passwd = passwd;
        this.company = company;
        this.branch = branch;
        this.post = post;
        this.errorNum = errorNum;
        this.nowAddress = nowAddress;
        this.creatTime = creatTime;
        this.alterTime = alterTime;
        this.state = state;
        this.apex1 = apex1;
        this.apex2 = apex2;
        this.apex3 = apex3;
        this.activationState = activationState;
        this.sexName = sexName;
    }

    public String getId() {
        return id;
    }

    public String getEmployeeNum() {
        return employeeNum;
    }

    public String getIdcard() {
        return idcard;
    }

    public String getIdcardAddress() {
        return idcardAddress;
    }

    public String getRealname() {
        return realname;
    }

    public String getDateBath() {
        return dateBath;
    }

    public String getSex() {
        return sex;
    }

    public String getTel() {
        return tel;
    }

    public String getPasswd() {
        return passwd;
    }

    public String getCompany() {
        return company;
    }

    public String getBranch() {
        return branch;
    }

    public String getPost() {
        return post;
    }

    public String getErrorNum() {
        return errorNum;
    }

    public String getNowAddress() {
        return nowAddress;
    }

    public String getCreatTime() {
        return creatTime;
    }

    public String getAlterTime() {
        return alterTime;
    }

    public String getState() {
        return state;
    }

    public String getApex1() {
        return apex1;
    }

    public String getApex2() {
        return apex2;
    }

    public String getApex3() {
        return apex3;
    }

    public String getActivationState() {
        return activationState;
    }

    public String getSexName() {
        return sexName;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setEmployeeNum(String employeeNum) {
        this.employeeNum = employeeNum;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public void setIdcardAddress(String idcardAddress) {
        this.idcardAddress = idcardAddress;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public void setDateBath(String dateBath) {
        this.dateBath = dateBath;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public void setErrorNum(String errorNum) {
        this.errorNum = errorNum;
    }

    public void setNowAddress(String nowAddress) {
        this.nowAddress = nowAddress;
    }

    public void setCreatTime(String creatTime) {
        this.creatTime = creatTime;
    }

    public void setAlterTime(String alterTime) {
        this.alterTime = alterTime;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setApex1(String apex1) {
        this.apex1 = apex1;
    }

    public void setApex2(String apex2) {
        this.apex2 = apex2;
    }

    public void setApex3(String apex3) {
        this.apex3 = apex3;
    }

    public void setActivationState(String activationState) {
        this.activationState = activationState;
    }

    public void setSexName(String sexName) {
        this.sexName = sexName;
    }

    @Override
    public String toString() {
        return "OfficeClerkManager{" +
                "id='" + id + '\'' +
                ", employeeNum='" + employeeNum + '\'' +
                ", idcard='" + idcard + '\'' +
                ", idcardAddress='" + idcardAddress + '\'' +
                ", realname='" + realname + '\'' +
                ", dateBath='" + dateBath + '\'' +
                ", sex='" + sex + '\'' +
                ", tel='" + tel + '\'' +
                ", passwd='" + passwd + '\'' +
                ", company='" + company + '\'' +
                ", branch='" + branch + '\'' +
                ", post='" + post + '\'' +
                ", errorNum='" + errorNum + '\'' +
                ", nowAddress='" + nowAddress + '\'' +
                ", creatTime='" + creatTime + '\'' +
                ", alterTime='" + alterTime + '\'' +
                ", state='" + state + '\'' +
                ", apex1='" + apex1 + '\'' +
                ", apex2='" + apex2 + '\'' +
                ", apex3='" + apex3 + '\'' +
                ", activationState='" + activationState + '\'' +
                ", sexName='" + sexName + '\'' +
                ", branchId='" + branchId + '\'' +
                ", postId='" + postId + '\'' +
                '}';
    }
}
