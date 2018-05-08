package com.zw.rule.repayment.po;



/**
 * 功能说明：贷后减免管理 实体类
 * 典型用法：该类的典型使用方法和用例
 * 特殊用法：该类在系统中的特殊用法的说明	
 * @author wangmin
 * 修改人: 
 * 修改原因：
 * 修改时间：
 * 修改内容：
 * 创建日期：2017-7-4
 * Copyright zzl
 */
public class DerateManage {

	private String id;
	
	/**
	 * 客户ID
	 */
	private String custInfoId;
	
	/**
	 * 信贷订单表ID
	 */
	private String crmOrderId;
	
	/**
	 * 信贷订单表ID
	 */
	private String paycontrolIds;
	
	/**
	 * 贷后申请ID
	 */
	private String receivableApplyId;
	
	/**
	 * 减免状态 0：结束 1：待命 2：审批 3：开始减免
	 */
	private Integer status;
	
	/**
	 * 审批状态 1：同意  0：不同意
	 */
	private Integer ceoApproveStatus;
	
	/**
	 * 审批人ID sys_employee
	 */
	private String ceoEmployeeId;
	
	/**
	 * 申请时间
	 */
	private String createTime;
	
	/**
	 * 申请时间
	 */
	private String repaymentTime;
	
	/**
	 * 完成时间
	 */
	private String finishTtime;
	
	/**
	 * 初始平台管理费（往期）
	 */
	private Double initialPlManageMoney;
	
	/**
	 * 减免平台管理费（往期）
	 */
	private Double deratePlManageMoney;
	
	/**
	 * 最终平台管理费（往期）
	 */
	private Double finalPlManageMoney;
	
	/**
	 * 初始利息（往期）
	 */
	private Double initialInterest;
	
	/**
	 * 减免利息（往期）
	 */
	private Double derateInterest;
	
	/**
	 * 最终利息（往期）
	 */
	private Double finalInterest;
	
	/**
	 * 初始合同违约金（往期）
	 */
	private Double initialContractPenalty;
	
	/**
	 * 减免合同违约金（往期）
	 */
	private Double derateContractPenalty;
	
	/**
	 * 最终合同违约金（往期）
	 */
	private Double finalContractPenalty;
	
	/**
	 * 初始罚息（往期）
	 */
	private Double initialOverdueInterest;
	
	/**
	 * 减免罚息（往期）
	 */
	private Double derateOverdueInterest;
	
	/**
	 * 最终罚息（往期）
	 */
	private Double finalOverdueInterest;
	
	/**
	 * 初始本金
	 */
	private Double initialCapital;
	
	/**
	 * 减免本金
	 */
	private Double derateCapital;
	
	/**
	 * 最终本金
	 */
	private Double finalCapital;
	
	/**
	 * 初始提前违约金
	 */
	private Double initialAdvancePenalty;
	
	/**
	 * 减免提前违约金
	 */
	private Double derateAdvancePenalty;
	
	/**
	 * 最终提前违约金
	 */
	private Double finalAdvancePenalty;
	
	/**
	 * 最终减免金额
	 */
	private Double finalDerateMoney;
	
	/**
	 * 最终减免金额
	 */
	private Double derateMoney;
	
	/**
	 * 申请减免备注
	 */
	private String applyContent;
	
	/**
	 * 申请类型 1：逾期减免  2：提前结清 3：部分减免
	 */
	private Integer applyType;
	
	/**
	 * 基础金额（部分减免）
	 */
	private Double baseMoney;
	
	/**
	 * 现场金额（部分减免）
	 */
	private Double theSceneMoney;
	
	/**
	 * 银行卡金额（部分减免）
	 */
	private Double bankcardMoney;
	
	
	/**
	 * 初始平台管理费 （本期）
	 */
	private Double initialPlManageMoneyCurrent;
	
	/**
	 * 减免平台管理费 （本期）
	 */
	private Double deratePlManageMoneyCurrent;
	
	/**
	 * 最终平台管理费 （本期）
	 */
	private Double finalPlManageMoneyCurrent;
	
	/**
	 * 初始利息 （本期）
	 */
	private Double initialInterestCurrent;
	
	/**
	 * 减免利息 （本期）
	 */
	private Double derateInterestCurrent;
	
	/**
	 * 最终利息 （本期）
	 */
	private Double finalInterestCurrent;
	
	/**
	 * 初始合同违约金 （本期）
	 */
	private Double initialContractPenaltyCurrent;
	
	/**
	 * 减免合同违约金 （本期）
	 */
	private Double derateContractPenaltyCurrent;
	
	/**
	 * 最终合同违约金 （本期）
	 */
	private Double finalContractPenaltyCurrent;
	
	/**
	 * 初始罚息 （本期）
	 */
	private Double initialOverdueInterestCurrent;
	
	/**
	 * 减免罚息 （本期）
	 */
	private Double derateOverdueInterestCurrent;
	
	/**
	 * 最终罚息 （本期）
	 */
	private Double finalOverdueInterestCurrent;
	
	/**
	 * 初始 退回服务费
	 */
	private Double initialReturnService;
	
	/**
	 * 减免 退回服务费
	 */
	private Double derateReturnService;
	
	/**
	 * 最终 退回服务费
	 */
	private Double finalReturnService;
	
	/**
	 * 初始 利息小计
	 */
	private Double initialSurplusInterest;
	
	/**
	 * 减免 利息小计
	 */
	private Double derateSurplusInterest;
	
	/**
	 * 最终 利息小计
	 */
	private Double finalSurplusInterest;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCustInfoId() {
		return custInfoId;
	}

	public void setCustInfoId(String custInfoId) {
		this.custInfoId = custInfoId;
	}

	public Double getFinalDerateMoney() {
		return finalDerateMoney;
	}

	public void setFinalDerateMoney(Double finalDerateMoney) {
		this.finalDerateMoney = finalDerateMoney;
	}

	public String getCrmOrderId() {
		return crmOrderId;
	}

	public void setCrmOrderId(String crmOrderId) {
		this.crmOrderId = crmOrderId;
	}

	public String getReceivableApplyId() {
		return receivableApplyId;
	}

	public void setReceivableApplyId(String receivableApplyId) {
		this.receivableApplyId = receivableApplyId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getCeoApproveStatus() {
		return ceoApproveStatus;
	}

	public void setCeoApproveStatus(Integer ceoApproveStatus) {
		this.ceoApproveStatus = ceoApproveStatus;
	}

	public String getCeoEmployeeId() {
		return ceoEmployeeId;
	}

	public void setCeoEmployeeId(String ceoEmployeeId) {
		this.ceoEmployeeId = ceoEmployeeId;
	}

	public Double getInitialPlManageMoney() {
		return initialPlManageMoney;
	}

	public void setInitialPlManageMoney(Double initialPlManageMoney) {
		this.initialPlManageMoney = initialPlManageMoney;
	}

	public Double getDeratePlManageMoney() {
		return deratePlManageMoney;
	}

	public void setDeratePlManageMoney(Double deratePlManageMoney) {
		this.deratePlManageMoney = deratePlManageMoney;
	}

	public Double getFinalPlManageMoney() {
		return finalPlManageMoney;
	}

	public void setFinalPlManageMoney(Double finalPlManageMoney) {
		this.finalPlManageMoney = finalPlManageMoney;
	}

	public String getFinishTtime() {
		return finishTtime;
	}

	public void setFinishTtime(String finishTtime) {
		this.finishTtime = finishTtime;
	}

	public Double getInitialInterest() {
		return initialInterest;
	}

	public void setInitialInterest(Double initialInterest) {
		this.initialInterest = initialInterest;
	}

	public Double getDerateInterest() {
		return derateInterest;
	}

	public void setDerateInterest(Double derateInterest) {
		this.derateInterest = derateInterest;
	}

	public Double getFinalInterest() {
		return finalInterest;
	}

	public void setFinalInterest(Double finalInterest) {
		this.finalInterest = finalInterest;
	}

	public Double getInitialContractPenalty() {
		return initialContractPenalty;
	}

	public void setInitialContractPenalty(Double initialContractPenalty) {
		this.initialContractPenalty = initialContractPenalty;
	}

	public Double getDerateContractPenalty() {
		return derateContractPenalty;
	}
	
	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public void setDerateContractPenalty(Double derateContractPenalty) {
		this.derateContractPenalty = derateContractPenalty;
	}

	public Double getFinalContractPenalty() {
		return finalContractPenalty;
	}

	public void setFinalContractPenalty(Double finalContractPenalty) {
		this.finalContractPenalty = finalContractPenalty;
	}

	public Double getInitialOverdueInterest() {
		return initialOverdueInterest;
	}

	public void setInitialOverdueInterest(Double initialOverdueInterest) {
		this.initialOverdueInterest = initialOverdueInterest;
	}

	public Double getDerateOverdueInterest() {
		return derateOverdueInterest;
	}

	public void setDerateOverdueInterest(Double derateOverdueInterest) {
		this.derateOverdueInterest = derateOverdueInterest;
	}

	public Double getFinalOverdueInterest() {
		return finalOverdueInterest;
	}

	public void setFinalOverdueInterest(Double finalOverdueInterest) {
		this.finalOverdueInterest = finalOverdueInterest;
	}

	public Double getInitialCapital() {
		return initialCapital;
	}

	public void setInitialCapital(Double initialCapital) {
		this.initialCapital = initialCapital;
	}

	public Double getDerateCapital() {
		return derateCapital;
	}

	public void setDerateCapital(Double derateCapital) {
		this.derateCapital = derateCapital;
	}

	public Double getFinalCapital() {
		return finalCapital;
	}

	public void setFinalCapital(Double finalCapital) {
		this.finalCapital = finalCapital;
	}

	public Double getInitialAdvancePenalty() {
		return initialAdvancePenalty;
	}

	public void setInitialAdvancePenalty(Double initialAdvancePenalty) {
		this.initialAdvancePenalty = initialAdvancePenalty;
	}

	public Double getDerateAdvancePenalty() {
		return derateAdvancePenalty;
	}

	public void setDerateAdvancePenalty(Double derateAdvancePenalty) {
		this.derateAdvancePenalty = derateAdvancePenalty;
	}

	public Double getFinalAdvancePenalty() {
		return finalAdvancePenalty;
	}

	public void setFinalAdvancePenalty(Double finalAdvancePenalty) {
		this.finalAdvancePenalty = finalAdvancePenalty;
	}

	public String getApplyContent() {
		return applyContent;
	}

	public void setApplyContent(String applyContent) {
		this.applyContent = applyContent;
	}

	public Integer getApplyType() {
		return applyType;
	}

	public void setApplyType(Integer applyType) {
		this.applyType = applyType;
	}

	public Double getBaseMoney() {
		return baseMoney;
	}

	public void setBaseMoney(Double baseMoney) {
		this.baseMoney = baseMoney;
	}

	public Double getTheSceneMoney() {
		return theSceneMoney;
	}

	public void setTheSceneMoney(Double theSceneMoney) {
		this.theSceneMoney = theSceneMoney;
	}

	public Double getBankcardMoney() {
		return bankcardMoney;
	}

	public void setBankcardMoney(Double bankcardMoney) {
		this.bankcardMoney = bankcardMoney;
	}

	public Double getInitialPlManageMoneyCurrent() {
		return initialPlManageMoneyCurrent;
	}

	public void setInitialPlManageMoneyCurrent(Double initialPlManageMoneyCurrent) {
		this.initialPlManageMoneyCurrent = initialPlManageMoneyCurrent;
	}

	public Double getDeratePlManageMoneyCurrent() {
		return deratePlManageMoneyCurrent;
	}

	public void setDeratePlManageMoneyCurrent(Double deratePlManageMoneyCurrent) {
		this.deratePlManageMoneyCurrent = deratePlManageMoneyCurrent;
	}

	public Double getFinalPlManageMoneyCurrent() {
		return finalPlManageMoneyCurrent;
	}

	public void setFinalPlManageMoneyCurrent(Double finalPlManageMoneyCurrent) {
		this.finalPlManageMoneyCurrent = finalPlManageMoneyCurrent;
	}

	public Double getInitialInterestCurrent() {
		return initialInterestCurrent;
	}

	public void setInitialInterestCurrent(Double initialInterestCurrent) {
		this.initialInterestCurrent = initialInterestCurrent;
	}

	public Double getDerateInterestCurrent() {
		return derateInterestCurrent;
	}

	public void setDerateInterestCurrent(Double derateInterestCurrent) {
		this.derateInterestCurrent = derateInterestCurrent;
	}

	public Double getFinalInterestCurrent() {
		return finalInterestCurrent;
	}

	public void setFinalInterestCurrent(Double finalInterestCurrent) {
		this.finalInterestCurrent = finalInterestCurrent;
	}

	public Double getInitialContractPenaltyCurrent() {
		return initialContractPenaltyCurrent;
	}

	public void setInitialContractPenaltyCurrent(
			Double initialContractPenaltyCurrent) {
		this.initialContractPenaltyCurrent = initialContractPenaltyCurrent;
	}

	public Double getDerateContractPenaltyCurrent() {
		return derateContractPenaltyCurrent;
	}

	public void setDerateContractPenaltyCurrent(Double derateContractPenaltyCurrent) {
		this.derateContractPenaltyCurrent = derateContractPenaltyCurrent;
	}

	public Double getFinalContractPenaltyCurrent() {
		return finalContractPenaltyCurrent;
	}

	public void setFinalContractPenaltyCurrent(Double finalContractPenaltyCurrent) {
		this.finalContractPenaltyCurrent = finalContractPenaltyCurrent;
	}

	public Double getInitialOverdueInterestCurrent() {
		return initialOverdueInterestCurrent;
	}

	public void setInitialOverdueInterestCurrent(
			Double initialOverdueInterestCurrent) {
		this.initialOverdueInterestCurrent = initialOverdueInterestCurrent;
	}

	public Double getDerateOverdueInterestCurrent() {
		return derateOverdueInterestCurrent;
	}

	public void setDerateOverdueInterestCurrent(Double derateOverdueInterestCurrent) {
		this.derateOverdueInterestCurrent = derateOverdueInterestCurrent;
	}

	public Double getFinalOverdueInterestCurrent() {
		return finalOverdueInterestCurrent;
	}

	public void setFinalOverdueInterestCurrent(Double finalOverdueInterestCurrent) {
		this.finalOverdueInterestCurrent = finalOverdueInterestCurrent;
	}

	public Double getDerateMoney() {
		return derateMoney;
	}

	public void setDerateMoney(Double derateMoney) {
		this.derateMoney = derateMoney;
	}

	public String getPaycontrolIds() {
		return paycontrolIds;
	}

	public void setPaycontrolIds(String paycontrolIds) {
		this.paycontrolIds = paycontrolIds;
	}

	public String getRepaymentTime() {
		return repaymentTime;
	}

	public void setRepaymentTime(String repaymentTime) {
		this.repaymentTime = repaymentTime;
	}

	public Double getInitialReturnService() {
		return initialReturnService;
	}

	public void setInitialReturnService(Double initialReturnService) {
		this.initialReturnService = initialReturnService;
	}

	public Double getDerateReturnService() {
		return derateReturnService;
	}

	public void setDerateReturnService(Double derateReturnService) {
		this.derateReturnService = derateReturnService;
	}

	public Double getFinalReturnService() {
		return finalReturnService;
	}

	public void setFinalReturnService(Double finalReturnService) {
		this.finalReturnService = finalReturnService;
	}

	public Double getInitialSurplusInterest() {
		return initialSurplusInterest;
	}

	public void setInitialSurplusInterest(Double initialSurplusInterest) {
		this.initialSurplusInterest = initialSurplusInterest;
	}

	public Double getDerateSurplusInterest() {
		return derateSurplusInterest;
	}

	public void setDerateSurplusInterest(Double derateSurplusInterest) {
		this.derateSurplusInterest = derateSurplusInterest;
	}

	public Double getFinalSurplusInterest() {
		return finalSurplusInterest;
	}

	public void setFinalSurplusInterest(Double finalSurplusInterest) {
		this.finalSurplusInterest = finalSurplusInterest;
	}
	
	
	
}
