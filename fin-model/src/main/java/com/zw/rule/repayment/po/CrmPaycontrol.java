package com.zw.rule.repayment.po;

import java.io.Serializable;

/**
 * 
 * 功能说明：信贷还款明细表
 * wangmin
 * 修改人: 
 * 修改原因：
 * 修改时间：
 * 修改内容：
 * 创建日期：2017-6-29
 */
public class CrmPaycontrol implements Serializable{
	 
	/**
	 */
	private static final long serialVersionUID = -2012856137667548934L;

	//id
	private String id;				//标识
	
	//信贷订单id
	private String crmOrderId;	
	
	//bg_cust_info对应的客户id
	private String custInfoId;			// bg_cust_info
	
	//bg_customer对应的客户id
	private String custId;				// bg_customer
	
	//应还金额
	private Double shouldMoney;			// 应还金额
	
	//应还本金
	private Double shouldCapiital;		// 应还本金
	
	//应还利息
	private Double shouldAccral;		// 应还利息
	
	//应还管理费
	private Double shouldPlatformManageMoney;// 应还管理费
	
	//应还罚息
	private Double shouldInterest;			// 应还罚息
	
	//应还违约金
	private Double shouldViolateFee;		// 应还逾期违约金
	
	//还款日期
	private String repaymentTime;			// 还款日期
	
	//还款状态  0：待还，1:结清，2:逾期,3:提前结清,4:失效
	private Integer status;					// 还款状态
	
	//逾期天数
	private Integer overdueDay;				// 逾期天数
	
	//逾期罚息
	private Double overdueInterest;			// 逾期罚息
	
	//逾期违约金
	private Double overdueViolateMoney;		// 逾期违约金
	
	//还款期数
	private Integer repaymentDuetime;		// 还款期数
	
	//剩余应还罚息
	private Double remainInterest;			// 剩余应还罚息
	
	//剩余应还本金
	private Double remainCapital;			// 剩余应还本金
	
	//剩余应还利息
	private Double remainAccrual;			// 剩余应还利息
	
	//剩余应还管理费
	private Double remainManageFee;			// 剩余应还管理费
	
	//是否垫付  1.是 0 否
	private Integer replaceStatus;			// 是否垫付
	
	//免息状态  0正常，1免息（默认正常）
	private Integer exemptStatus;			// 免息状态
	
	//异常标识  0异常 1 正常
	private Integer abnormalStatus;			// 异常标识

	//应还GPS流量费（车贷)
	private Double shouldGpsTrafficFee;			// 应还GPS流量费（车贷)
	
	//应还停车费（车贷）
	private Double shouldParkingFee;			// 应还停车费（车贷）
	
	//剩余GPS流量费（车贷)
	private Double remainGpsTrafficFee;			// 剩余GPS流量费（车贷)
	
	//剩余停车费（车贷）
	private Double remainParkingFee;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}



	public Double getShouldGpsTrafficFee() {
		return shouldGpsTrafficFee;
	}

	public void setShouldGpsTrafficFee(Double shouldGpsTrafficFee) {
		this.shouldGpsTrafficFee = shouldGpsTrafficFee;
	}

	public Double getShouldParkingFee() {
		return shouldParkingFee;
	}

	public void setShouldParkingFee(Double shouldParkingFee) {
		this.shouldParkingFee = shouldParkingFee;
	}


	public Double getRemainGpsTrafficFee() {
		return remainGpsTrafficFee;
	}

	public void setRemainGpsTrafficFee(Double remainGpsTrafficFee) {
		this.remainGpsTrafficFee = remainGpsTrafficFee;
	}

	public Double getRemainParkingFee() {
		return remainParkingFee;
	}

	public void setRemainParkingFee(Double remainParkingFee) {
		this.remainParkingFee = remainParkingFee;
	}

	public String getCrmOrderId() {
		return crmOrderId;
	}

	public void setCrmOrderId(String crmOrderId) {
		this.crmOrderId = crmOrderId;
	}

	public String getCustInfoId() {
		return custInfoId;
	}

	public void setCustInfoId(String custInfoId) {
		this.custInfoId = custInfoId;
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}


	public CrmPaycontrol() {
		super();
	}

	public Double getShouldCapiital() {
		return shouldCapiital;
	}

	public void setShouldCapiital(Double shouldCapiital) {
		this.shouldCapiital = shouldCapiital;
	}

	public Double getShouldAccral() {
		return shouldAccral;
	}

	public void setShouldAccral(Double shouldAccral) {
		this.shouldAccral = shouldAccral;
	}

	public Double getShouldPlatformManageMoney() {
		return shouldPlatformManageMoney;
	}

	public void setShouldPlatformManageMoney(Double shouldPlatformManageMoney) {
		this.shouldPlatformManageMoney = shouldPlatformManageMoney;
	}

	public Double getShouldInterest() {
		return shouldInterest;
	}

	public void setShouldInterest(Double shouldInterest) {
		this.shouldInterest = shouldInterest;
	}

	public Double getShouldViolateFee() {
		return shouldViolateFee;
	}

	public void setShouldViolateFee(Double shouldViolateFee) {
		this.shouldViolateFee = shouldViolateFee;
	}

	public String getRepaymentTime() {
		return repaymentTime;
	}

	public void setRepaymentTime(String repaymentTime) {
		this.repaymentTime = repaymentTime;
	}

	public Integer getOverdueDay() {
		return overdueDay;
	}

	public void setOverdueDay(Integer overdueDay) {
		this.overdueDay = overdueDay;
	}

	public Double getOverdueInterest() {
		return overdueInterest;
	}

	public void setOverdueInterest(Double overdueInterest) {
		this.overdueInterest = overdueInterest;
	}

	public Double getOverdueViolateMoney() {
		return overdueViolateMoney;
	}

	public void setOverdueViolateMoney(Double overdueViolateMoney) {
		this.overdueViolateMoney = overdueViolateMoney;
	}

	public Integer getRepaymentDuetime() {
		return repaymentDuetime;
	}

	public void setRepaymentDuetime(Integer repaymentDuetime) {
		this.repaymentDuetime = repaymentDuetime;
	}

	public Double getRemainInterest() {
		return remainInterest;
	}

	public void setRemainInterest(Double remainInterest) {
		this.remainInterest = remainInterest;
	}

	public Double getRemainCapital() {
		return remainCapital;
	}

	public void setRemainCapital(Double remainCapital) {
		this.remainCapital = remainCapital;
	}

	public Double getRemainAccrual() {
		return remainAccrual;
	}

	public void setRemainAccrual(Double remainAccrual) {
		this.remainAccrual = remainAccrual;
	}

	public Double getRemainManageFee() {
		return remainManageFee;
	}

	public void setRemainManageFee(Double remainManageFee) {
		this.remainManageFee = remainManageFee;
	}

	public Integer getReplaceStatus() {
		return replaceStatus;
	}

	public void setReplaceStatus(Integer replaceStatus) {
		this.replaceStatus = replaceStatus;
	}

	public Integer getExemptStatus() {
		return exemptStatus;
	}

	public void setExemptStatus(Integer exemptStatus) {
		this.exemptStatus = exemptStatus;
	}

	public Integer getAbnormalStatus() {
		return abnormalStatus;
	}

	public void setAbnormalStatus(Integer abnormalStatus) {
		this.abnormalStatus = abnormalStatus;
	}

	public void setShouldMoney(Double shouldMoney) {
		this.shouldMoney = shouldMoney;
	}

	public Double getShouldMoney() {
		return shouldMoney;
	}

	public CrmPaycontrol(String id, String crmOrderId, String custInfoId,
                         String custId, Double shouldMoney, Double shouldCapiital,
                         Double shouldAccral, Double shouldPlatformManageMoney,
                         Double shouldInterest, Double shouldViolateFee,
                         String repaymentTime, Integer status, Integer overdueDay,
                         Double overdueInterest, Double overdueViolateMoney,
                         Integer repaymentDuetime, Double remainInterest,
                         Double remainCapital, Double remainAccrual, Double remainManageFee,
                         Integer replaceStatus, Integer exemptStatus,
                         Integer abnormalStatus, Double shouldGpsTrafficFee,
                         Double shouldParkingFee, Double remainGpsTrafficFee,
                         Double remainParkingFee) {
		super();
		this.id = id;
		this.crmOrderId = crmOrderId;
		this.custInfoId = custInfoId;
		this.custId = custId;
		this.shouldMoney = shouldMoney;
		this.shouldCapiital = shouldCapiital;
		this.shouldAccral = shouldAccral;
		this.shouldPlatformManageMoney = shouldPlatformManageMoney;
		this.shouldInterest = shouldInterest;
		this.shouldViolateFee = shouldViolateFee;
		this.repaymentTime = repaymentTime;
		this.status = status;
		this.overdueDay = overdueDay;
		this.overdueInterest = overdueInterest;
		this.overdueViolateMoney = overdueViolateMoney;
		this.repaymentDuetime = repaymentDuetime;
		this.remainInterest = remainInterest;
		this.remainCapital = remainCapital;
		this.remainAccrual = remainAccrual;
		this.remainManageFee = remainManageFee;
		this.replaceStatus = replaceStatus;
		this.exemptStatus = exemptStatus;
		this.abnormalStatus = abnormalStatus;
		this.shouldGpsTrafficFee = shouldGpsTrafficFee;
		this.shouldParkingFee = shouldParkingFee;
		this.remainGpsTrafficFee = remainGpsTrafficFee;
		this.remainParkingFee = remainParkingFee;
	}
	
	
}
