package com.zw.rule.repayment.po;

import java.io.Serializable;


/**
 * 
 * 功能说明：信贷订单还款记录
 * wangmin
 * 修改人: 
 * 修改原因：
 * 修改时间：
 * 修改内容：
 * 创建日期：2017-06-29
 */
public class CrmPayrecoder implements Serializable{
	 
	/**
	 */
	private static final long serialVersionUID = -5350437652676144681L;

	private String id;
	
	//对应的客户id
	private String custId;
	
	//bg_cust_info对应的客户id
	private String custInfoId;			// bg_cust_info
	
	//信贷订单id
	private String crmOrderId;
	
	//还款总额
	private Double ShouldMONEY;
	
	//本金
	private Double shouldCAPITAL;
	
	//利息
	private Double ShouldAccrual;
	
	//创建时间
	private String createTime;
	
	//操作人id
	private String empId;
	
	//提前还款退还服务费
	private Double remainFee;
	
	//对应的还款明细id
	private String paycontrolId;
	
	//备注
	private String remark;
	
	//还款类型 0：自动还款，1:手动还款 2:提前结清 3:提前收回 4:部分逾期还款 5:正常手动还款 6：手动全部逾期还款
	private Integer repaymentType;
	
	//管理费
	private Double manageFee;
	
	//逾期利息
	private Double overdueInterest;
	
	//提前还款违约金
	private Double prepaymentViolateMoney;
	
	//逾期违约金
	private Double overdueViolateMoney;
	
	//凭证地址
	private String certificateUrl;
	
	//罚息
	private Double shouldInterest;
	
	//批量操作流水号
	private String batchTransferSerialno;

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

	public String getCrmOrderId() {
		return crmOrderId;
	}

	public void setCrmOrderId(String crmOrderId) {
		this.crmOrderId = crmOrderId;
	}

	public Double getShouldMONEY() {
		return ShouldMONEY;
	}

	public void setShouldMONEY(Double shouldMONEY) {
		ShouldMONEY = shouldMONEY;
	}

	public Double getShouldCAPITAL() {
		return shouldCAPITAL;
	}

	public void setShouldCAPITAL(Double shouldCAPITAL) {
		this.shouldCAPITAL = shouldCAPITAL;
	}

	public Double getShouldAccrual() {
		return ShouldAccrual;
	}

	public void setShouldAccrual(Double shouldAccrual) {
		ShouldAccrual = shouldAccrual;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public Double getRemainFee() {
		return remainFee;
	}

	public void setRemainFee(Double remainFee) {
		this.remainFee = remainFee;
	}

	public String getPaycontrolId() {
		return paycontrolId;
	}

	public void setPaycontrolId(String paycontrolId) {
		this.paycontrolId = paycontrolId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getRepaymentType() {
		return repaymentType;
	}

	public void setRepaymentType(Integer repaymentType) {
		this.repaymentType = repaymentType;
	}

	public Double getManageFee() {
		return manageFee;
	}

	public void setManageFee(Double manageFee) {
		this.manageFee = manageFee;
	}

	public Double getOverdueInterest() {
		return overdueInterest;
	}

	public void setOverdueInterest(Double overdueInterest) {
		this.overdueInterest = overdueInterest;
	}

	public Double getPrepaymentViolateMoney() {
		return prepaymentViolateMoney;
	}

	public void setPrepaymentViolateMoney(Double prepaymentViolateMoney) {
		this.prepaymentViolateMoney = prepaymentViolateMoney;
	}

	public Double getOverdueViolateMoney() {
		return overdueViolateMoney;
	}

	public void setOverdueViolateMoney(Double overdueViolateMoney) {
		this.overdueViolateMoney = overdueViolateMoney;
	}

	public String getCertificateUrl() {
		return certificateUrl;
	}

	public void setCertificateUrl(String certificateUrl) {
		this.certificateUrl = certificateUrl;
	}

	public Double getShouldInterest() {
		return shouldInterest;
	}

	public void setShouldInterest(Double shouldInterest) {
		this.shouldInterest = shouldInterest;
	}

	public String getBatchTransferSerialno() {
		return batchTransferSerialno;
	}

	public void setBatchTransferSerialno(String batchTransferSerialno) {
		this.batchTransferSerialno = batchTransferSerialno;
	}

	@Override
	public String toString() {
		return "CrmPayrecoder [id=" + id + ", custId=" + custId + ", custInfoId=" + custInfoId + ", crmOrderId="
				+ crmOrderId + ", ShouldMONEY=" + ShouldMONEY + ", shouldCAPITAL=" + shouldCAPITAL + ", ShouldAccrual="
				+ ShouldAccrual + ", createTime=" + createTime + ", empId=" + empId + ", remainFee=" + remainFee
				+ ", paycontrolId=" + paycontrolId + ", remark=" + remark + ", repaymentType=" + repaymentType
				+ ", manageFee=" + manageFee + ", overdueInterest=" + overdueInterest + ", prepaymentViolateMoney="
				+ prepaymentViolateMoney + ", overdueViolateMoney=" + overdueViolateMoney + ", certificateUrl="
				+ certificateUrl + ", shouldInterest=" + shouldInterest + ", batchTransferSerialno="
				+ batchTransferSerialno + "]";
	}
	

}
