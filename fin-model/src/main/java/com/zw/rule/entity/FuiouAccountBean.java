package com.zw.rule.entity;

public class FuiouAccountBean {
	private String bankType;
	private String urlType;
	private String custInfoTypeId;
	private String custId;
	private String crmApplayId;
	private String credentialsTypeId;
	private String bankNo;
	private String goldBank;
	private String goldProvince;
	private String goldCity;
	private String password;
	private String lpassword;

	/**
	 * 开户类型: 1富有开户 
	 * 
	 * @return
	 */
	public String getBankType() {
		return bankType;
	}

	/**
	 * 开户类型: 1富有开户 
	 * 
	 * @return
	 */
	public void setBankType(String bankType) {
		this.bankType = bankType;
	}

	/**
	 * 
	 * 客户来源 1理财 2信贷
	 * 
	 * @return
	 */
	public String getUrlType() {
		return urlType;
	}

	/**
	 * 
	 * 客户来源 1理财 2信贷
	 * 
	 * @return
	 */
	public void setUrlType(String urlType) {
		this.urlType = urlType;
	}

	/**
	 * 
	 * 开户类型 0：个人开户 其它：法人开户；
	 * 
	 * @return
	 */
	public String getCustInfoTypeId() {
		return custInfoTypeId;
	}

	/**
	 * 
	 * 开户类型 0：个人开户 其它：法人开户；
	 * 
	 * @return
	 */
	public void setCustInfoTypeId(String custInfoTypeId) {
		this.custInfoTypeId = custInfoTypeId;
	}

	/**
	 * 
	 * 客户ID
	 * 
	 * @return
	 */
	public String getCustId() {
		return custId;
	}

	/**
	 * 
	 * 客户ID
	 * 
	 * @return
	 */
	public void setCustId(String custId) {
		this.custId = custId;
	}

	/**
	 * 申请单编号
	 * 
	 * @return
	 */
	public String getCrmApplayId() {
		return crmApplayId;
	}

	/**
	 * 申请单编号
	 * 
	 * @return
	 */
	public void setCrmApplayId(String crmApplayId) {
		this.crmApplayId = crmApplayId;
	}

	/**
	 * 
	 * 证件类型 0：身份证 7：其他证件
	 * 
	 * @return
	 */
	public String getCredentialsTypeId() {
		return credentialsTypeId;
	}

	/**
	 * 
	 * 证件类型 0：身份证 7：其他证件
	 * 
	 * @return
	 */
	public void setCredentialsTypeId(String credentialsTypeId) {
		this.credentialsTypeId = credentialsTypeId;
	}

	/**
	 * 
	 * 银行卡
	 * 
	 * @return
	 */
	public String getBankNo() {
		return bankNo;
	}

	/**
	 * 
	 * 银行卡
	 * 
	 * @return
	 */
	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}

	/**
	 * 
	 * 开户银行
	 * 
	 * @return
	 */
	public String getGoldBank() {
		return goldBank;
	}

	/**
	 * 
	 * 开户银行
	 * 
	 * @return
	 */
	public void setGoldBank(String goldBank) {
		this.goldBank = goldBank;
	}

	/**
	 * 
	 * 开户省
	 * 
	 * @return
	 */
	public String getGoldProvince() {
		return goldProvince;
	}

	/**
	 * 
	 * 开户省
	 * 
	 * @return
	 */
	public void setGoldProvince(String goldProvince) {
		this.goldProvince = goldProvince;
	}

	/**
	 * 
	 * 开户市
	 * 
	 * @return
	 */
	public String getGoldCity() {
		return goldCity;
	}

	/**
	 * 
	 * 开户市
	 * 
	 * @return
	 */
	public void setGoldCity(String goldCity) {
		this.goldCity = goldCity;
	}

	/**
	 * 
	 * 提现密码
	 * 
	 * @return
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * 
	 * 提现密码
	 * 
	 * @return
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * 
	 * 登录密码
	 * 
	 * @return
	 */
	public String getLpassword() {
		return lpassword;
	}

	/**
	 * 
	 * 登录密码
	 * 
	 * @return
	 */
	public void setLpassword(String lpassword) {
		this.lpassword = lpassword;
	}

}
