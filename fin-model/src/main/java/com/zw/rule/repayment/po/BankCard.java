package com.zw.rule.repayment.po;

/**
 * 
 * 功能说明：v3银行卡表
 * wangmin
 * 修改人: 
 * 修改原因：
 * 修改时间：
 * 修改内容：
 * 创建日期：2017-7-3
 */
public class BankCard {
	
	// 来源
	public final static Integer SOURCE_FY = 1;				//富有
	public final static Integer SOURCE_BOC = 2;				//boc
	
	// 绑定状态
	public final static Integer BIND_YES = 1;				//已绑定
	public final static Integer BIND_NO = 0;				//未绑定
	
	private String id;
	
	private String custId;				//关联bg_customer id
	
	private String custInfoId;			//关联bg_customer_info id
	
	private Integer source;				//来源  1 富友  2 boc
	
	private String custName;			//姓名
	
	private String bankNumber;			//银行行别
	
	private String cardNumber;			//卡号
	
	private String bankSubbranch;		//支行名称
	
	private String cityId;				//城市编号   如果是富友那边过来 需要关联 到富友提供的城市编码
	
	private Integer bindStatus;			//绑定状态  0未绑定 1 已绑定
	
	private String createTime;			//创建时间
	
	private String updateTime;			//更新时间
	
	private String bankName;			//银行名称
	private String provNo;			//省份编号
	private String cityNo;			//城市编号

	private String iconUrl;		//银行图标url
	
	
	public String getIconUrl() {
		return iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}
	
	public String getProvNo() {
		return provNo;
	}

	public void setProvNo(String provNo) {
		this.provNo = provNo;
	}

	public String getCityNo() {
		return cityNo;
	}

	public void setCityNo(String cityNo) {
		this.cityNo = cityNo;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getCustInfoId() {
		return custInfoId;
	}

	public void setCustInfoId(String custInfoId) {
		this.custInfoId = custInfoId;
	}

	public Integer getSource() {
		return source;
	}

	public void setSource(Integer source) {
		this.source = source;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getBankNumber() {
		return bankNumber;
	}

	public void setBankNumber(String bankNumber) {
		this.bankNumber = bankNumber;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getBankSubbranch() {
		return bankSubbranch;
	}

	public void setBankSubbranch(String bankSubbranch) {
		this.bankSubbranch = bankSubbranch;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public Integer getBindStatus() {
		return bindStatus;
	}

	public void setBindStatus(Integer bindStatus) {
		this.bindStatus = bindStatus;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public BankCard(String id, String custId, String custInfoId,
                    Integer source, String custName, String bankNumber,
                    String cardNumber, String bankSubbranch, String cityId,
                    Integer bindStatus, String createTime, String updateTime) {
		super();
		this.id = id;
		this.custId = custId;
		this.custInfoId = custInfoId;
		this.source = source;
		this.custName = custName;
		this.bankNumber = bankNumber;
		this.cardNumber = cardNumber;
		this.bankSubbranch = bankSubbranch;
		this.cityId = cityId;
		this.bindStatus = bindStatus;
		this.createTime = createTime;
		this.updateTime = updateTime;
	}

	public BankCard() {
		super();
	}
	
	

}
