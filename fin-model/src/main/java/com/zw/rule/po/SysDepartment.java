package com.zw.rule.po;

/**
 * Created by Administrator on 2017/6/13.
 */
public class SysDepartment {
    public static final Integer STATUS_YES = 1;		//启用
    public static final Integer STATUS_NO = 0;		//停用

    public static final Integer TYPE_DEPT = 0;		//部门
    public static final Integer TYPE_COMPANY = 1;	//分公司


    private long id; 	//标识				//标识

    private String number;				//编号

    private String name;				//名称

    private long pid;					//父级id

    private String phone;				//号码

    private String description;			//描述

    private Integer status; 			//状态  0 停用 1 启用

    private String ctime;				//创建时间

    private String updateTime;			//修改时间

    private Integer type;				//是否是公司  0部门  1公司

    private String sequence;			//序号

    private String address;				//公司地址

    private Integer companyType;		//公司类型  1、信贷，2、理财，3、技术，4、后勤，5、总部，0、其他

    private String principal;			//负责人

    private String abbreviation;		//公司简称

    private String cityId;				//城市id

    private String provinceId;			//省份id

    private String districtId;			//区域id

    private Integer pocChannel;			// poc 通道状态

    private Integer bocChannel;			// boc 通道状态

    private String domain;				//域名

    private String grade;  //父级子级类型

    private String pname;  //父级节点名称

    private String proAddress; //省市地址

    private String level;//级别
    private String reqFirm;//第三方名称
    private String totalNum;//调用第三方次数记录

    private String zengxintongTotal;//增信通次数
    private String tongdunTotal;//同盾次数
    private String juxinliTotal;//聚信立次数
    private String tengxunTotal;//腾讯次数

    public String getZengxintongTotal() {
        return zengxintongTotal;
    }

    public void setZengxintongTotal(String zengxintongTotal) {
        this.zengxintongTotal = zengxintongTotal;
    }

    public String getTongdunTotal() {
        return tongdunTotal;
    }

    public void setTongdunTotal(String tongdunTotal) {
        this.tongdunTotal = tongdunTotal;
    }

    public String getJuxinliTotal() {
        return juxinliTotal;
    }

    public void setJuxinliTotal(String juxinliTotal) {
        this.juxinliTotal = juxinliTotal;
    }

    public String getTengxunTotal() {
        return tengxunTotal;
    }

    public void setTengxunTotal(String tengxunTotal) {
        this.tengxunTotal = tengxunTotal;
    }

    public String getLevel() {
        return level;
    }

    public String getReqFirm() {
        return reqFirm;
    }

    public void setReqFirm(String reqFirm) {
        this.reqFirm = reqFirm;
    }

    public String getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(String totalNum) {
        this.totalNum = totalNum;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getProAddress() {
        return proAddress;
    }

    public void setProAddress(String proAddress) {
        this.proAddress = proAddress;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPid() {
        return pid;
    }

    public void setPid(long pid) {
        this.pid = pid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getSequence() {
        return sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getCompanyType() {
        return companyType;
    }

    public void setCompanyType(Integer companyType) {
        this.companyType = companyType;
    }

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public String getDistrictId() {
        return districtId;
    }

    public void setDistrictId(String districtId) {
        this.districtId = districtId;
    }

    public Integer getPocChannel() {
        return pocChannel;
    }

    public void setPocChannel(Integer pocChannel) {
        this.pocChannel = pocChannel;
    }

    public Integer getBocChannel() {
        return bocChannel;
    }

    public void setBocChannel(Integer bocChannel) {
        this.bocChannel = bocChannel;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }
}
