package com.zw.rule.product;

public class BgEfProductDetail {
	
	public static final Integer STATUS_YES = 1;			// 鍚敤
	public static final Integer STATUS_NO = 0;			// 绂佺敤
	
	private String id;					// 鍞竴鏍囪瘑锛堜富閿級
	
	private Double investRate;			// 骞村寲鍒╃巼/鍚堝悓鍒╃巼
	
	private Double periods;				// 鏈熸暟
	
	private String createTime;			// 鍒涘缓鏃堕棿
	
	private String empId;				// 瀵瑰簲鍛樺伐SYS_EMPLOYEE ID
	
	private Integer status;				// 1 鏍囩ず鍚姩 0 鏍囩ず鍋滅敤
	
	private String efProductId;			// BG_EF_PRODUCT 鐨�ID
	
	private Double investManageRate;	// 鎶曡祫鏈熷鎴风鐞嗘湀璐圭巼锛氬崟浣� %/鏈�
	
	private Double activitiesRate;		// 娲诲姩鍒╃巼   骞翠负鍗曚綅
	
	private Integer preparation;	//筹建期
	
	private String description;		//产品描述
	
	private String transferParamId;	//债权转让周期参数BG_TRANSFER_PARAMETER  ID
	
	private Double preManageRate;	//提前还款违约金（投资人）
	
	private Double stepAmount;			// 起投金额
	
	private Integer speRate;			// 利率标识 1 固定利率 2 阶梯利率
	
	private Integer type;				//期数类型 1月 2 周 3日
	
	private Integer rateType;   //利率类型   1 年化利率   2 日利率

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Double getInvestRate() {
		return investRate;
	}

	public void setInvestRate(Double investRate) {
		this.investRate = investRate;
	}

	public Double getPeriods() {
		return periods;
	}

	public void setPeriods(Double periods) {
		this.periods = periods;
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getEfProductId() {
		return efProductId;
	}

	public void setEfProductId(String efProductId) {
		this.efProductId = efProductId;
	}

	public Double getInvestManageRate() {
		return investManageRate;
	}

	public void setInvestManageRate(Double investManageRate) {
		this.investManageRate = investManageRate;
	}

	public Double getActivitiesRate() {
		return activitiesRate;
	}

	public void setActivitiesRate(Double activitiesRate) {
		this.activitiesRate = activitiesRate;
	}

	public Integer getPreparation() {
		return preparation;
	}

	public void setPreparation(Integer preparation) {
		this.preparation = preparation;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTransferParamId() {
		return transferParamId;
	}

	public void setTransferParamId(String transferParamId) {
		this.transferParamId = transferParamId;
	}

	public Double getPreManageRate() {
		return preManageRate;
	}

	public void setPreManageRate(Double preManageRate) {
		this.preManageRate = preManageRate;
	}

	public Double getStepAmount() {
		return stepAmount;
	}

	public void setStepAmount(Double stepAmount) {
		this.stepAmount = stepAmount;
	}

	public Integer getSpeRate() {
		return speRate;
	}

	public void setSpeRate(Integer speRate) {
		this.speRate = speRate;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getRateType() {
		return rateType;
	}

	public void setRateType(Integer rateType) {
		this.rateType = rateType;
	}
	
	
}
