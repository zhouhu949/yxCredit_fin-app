package com.zw.rule.product;

/**
 * 
 * 功能说明：V3信贷产品信息
 * 典型用法：
 * 特殊用法：	
 * @author wangmin
 * 修改人: 
 * 修改原因：
 * 修改时间：
 * 修改内容：
 * 创建日期：2017-6-14
 * Copyright zzl-apt
 */
public class WorkingProductDetail {
	
	private String id;			//	唯一标识
	
	private String productId;	//信贷产品id

	private String type;			//产品类型（1单期产品，2多期产品）
	
	private String periods;// 期数
	
	private String contractRate;// 合同利率
	
	private String multipleRate;// 综合利率
	
	private String payment;// 还款方式
	
	private String actualLowerLimit;// 到手额度-下限
	
	private String actualUpperLimit;// 到手额度-上限
	
	private String creditProtectDay;// 征信保护天数
	
	private String overdueProtectDay;// 逾期保护天数
	
	private String interestRate;// 罚息费率
	
	private String interestRemark;// 罚息费率-备注 
	
	private String contractViolateRate;// 合同违约金费率
	
	private String contractViolateRemark;//合同违约金费率-备注
	
	private String bailRate;// 保证金率
	
	private String bailRemark;//保证金率备注
	
	private String bailPayMode;// 保证金缴纳方式
	
	private String stagingServicesRate;// 分期服务费率
	
	private String status; // 产品状态
	
	private String duetimeType; // 产品期数类型

	private String  diyType;

	private String diyDays;

	public String getDiyType() {
		return diyType;
	}

	public void setDiyType(String diyType) {
		this.diyType = diyType;
	}

	public String getDiyDays() {
		return diyDays;
	}

	public void setDiyDays(String diyDays) {
		this.diyDays = diyDays;
	}

	/*//宽贷产品专用
	private Integer overdueDouble;// 逾期第一期限
	
	private Integer interestRateTwo;// 逾期罚息费1
	
	private Integer overdueThird;// 逾期第二期限 下限
	
	private Integer overdueFourth;// 逾期第二期限 上限
	
	private Integer interestRateThird;// 逾期罚息费2
	
	private Integer interestRateFourth;// 逾期罚息费3*/

	private String maxDay;// ***

	private String beforeServiceFeeRate;// ***

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getPeriods() {
		return periods;
	}

	public void setPeriods(String periods) {
		this.periods = periods;
	}

	public String getContractRate() {
		return contractRate;
	}

	public void setContractRate(String contractRate) {
		this.contractRate = contractRate;
	}

	public String getMultipleRate() {
		return multipleRate;
	}

	public void setMultipleRate(String multipleRate) {
		this.multipleRate = multipleRate;
	}

	public String getPayment() {
		return payment;
	}

	public void setPayment(String payment) {
		this.payment = payment;
	}

	public String getActualLowerLimit() {
		return actualLowerLimit;
	}

	public void setActualLowerLimit(String actualLowerLimit) {
		this.actualLowerLimit = actualLowerLimit;
	}

	public String getActualUpperLimit() {
		return actualUpperLimit;
	}

	public void setActualUpperLimit(String actualUpperLimit) {
		this.actualUpperLimit = actualUpperLimit;
	}

	public String getCreditProtectDay() {
		return creditProtectDay;
	}

	public void setCreditProtectDay(String creditProtectDay) {
		this.creditProtectDay = creditProtectDay;
	}

	public String getOverdueProtectDay() {
		return overdueProtectDay;
	}

	public void setOverdueProtectDay(String overdueProtectDay) {
		this.overdueProtectDay = overdueProtectDay;
	}

	public String getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(String interestRate) {
		this.interestRate = interestRate;
	}

	public String getInterestRemark() {
		return interestRemark;
	}

	public void setInterestRemark(String interestRemark) {
		this.interestRemark = interestRemark;
	}

	public String getContractViolateRate() {
		return contractViolateRate;
	}

	public void setContractViolateRate(String contractViolateRate) {
		this.contractViolateRate = contractViolateRate;
	}

	public String getContractViolateRemark() {
		return contractViolateRemark;
	}

	public void setContractViolateRemark(String contractViolateRemark) {
		this.contractViolateRemark = contractViolateRemark;
	}

	public String getBailRate() {
		return bailRate;
	}

	public void setBailRate(String bailRate) {
		this.bailRate = bailRate;
	}

	public String getBailRemark() {
		return bailRemark;
	}

	public void setBailRemark(String bailRemark) {
		this.bailRemark = bailRemark;
	}

	public String getBailPayMode() {
		return bailPayMode;
	}

	public void setBailPayMode(String bailPayMode) {
		this.bailPayMode = bailPayMode;
	}

	public String getStagingServicesRate() {
		return stagingServicesRate;
	}

	public void setStagingServicesRate(String stagingServicesRate) {
		this.stagingServicesRate = stagingServicesRate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDuetimeType() {
		return duetimeType;
	}

	public void setDuetimeType(String duetimeType) {
		this.duetimeType = duetimeType;
	}

	public String getMaxDay() {
		return maxDay;
	}

	public void setMaxDay(String maxDay) {
		this.maxDay = maxDay;
	}

	public String getBeforeServiceFeeRate() {
		return beforeServiceFeeRate;
	}

	public void setBeforeServiceFeeRate(String beforeServiceFeeRate) {
		this.beforeServiceFeeRate = beforeServiceFeeRate;
	}

	public WorkingProductDetail(){
		super();
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
