package com.zw.rule.entity;

public class JxAccountBean {
	private String bankType;
	private String urlType;
	private String custInfoTypeId;
	private String custId;
	private String crmApplayId;
	private String credentialsTypeId;
	private String bankNumber;
	private String smsCode;
	private String srvAuthCode;
	/**
	 * 开户类型: 1富有开户 2江西银行开户
	 * 
	 * @return
	 */
	public String getBankType() {
		return bankType;
	}

	/**
	 * 开户类型: 1富有开户 2江西银行开户
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
	 * 银行卡号
	 * @return
	 */
	public String getBankNumber() {
		return bankNumber;
	}

	/**
	 * 
	 * 银行卡号
	 * @return
	 */
	public void setBankNumber(String bankNumber) {
		this.bankNumber = bankNumber;
	}

	/**
	 *
	 * 江西银行短信验证码
	 * @return
	 */
	public void setSmsCode(String smsCode) {
		this.smsCode = smsCode;
	}

	/**
	 *
	 * 江西银行-前导业务码
	 * @return
	 */
	public void setSrvAuthCode(String srvAuthCode) {
		this.srvAuthCode = srvAuthCode;
	}
	/**
	 *
	 * 江西银行短信验证码
	 * @return
	 */
	public String getSmsCode() {
		return this.smsCode ;
	}

	/**
	 *
	 * 江西银行-前导业务码
	 * @return
	 */
	public String getSrvAuthCode() {
		return this.srvAuthCode ;
	}

}
