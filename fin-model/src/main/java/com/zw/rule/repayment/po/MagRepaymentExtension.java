package com.zw.rule.repayment.po;



/**
 * 功能说明：展期 实体类
 * 典型用法：该类的典型使用方法和用例
 * 特殊用法：该类在系统中的特殊用法的说明	
 * @author zh
 */
public class MagRepaymentExtension {

	private String id;
	
	/**
	 * 订单ID
	 */
	private String orderId;
	
	/**
	 * 还款计划id
	 */
	private String repaymentId;
	
	/**
	 * 还款期数
	 */
	private String payCount;
	
	/**
	 * 展期费用
	 */
	private String extensionAmount;
	
	/**
	 * 展期天数
	 */
	private Integer extensionDay;
	
	/**
	 * 操作人
	 */
	private String operator;
	
	/**
	 * 操作时间
	 */
	private String operateTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getRepaymentId() {
		return repaymentId;
	}

	public void setRepaymentId(String repaymentId) {
		this.repaymentId = repaymentId;
	}

	public String getPayCount() {
		return payCount;
	}

	public void setPayCount(String payCount) {
		this.payCount = payCount;
	}

	public String getExtensionAmount() {
		return extensionAmount;
	}

	public void setExtensionAmount(String extensionAmount) {
		this.extensionAmount = extensionAmount;
	}

	public Integer getExtensionDay() {
		return extensionDay;
	}

	public void setExtensionDay(Integer extensionDay) {
		this.extensionDay = extensionDay;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(String operateTime) {
		this.operateTime = operateTime;
	}
}
