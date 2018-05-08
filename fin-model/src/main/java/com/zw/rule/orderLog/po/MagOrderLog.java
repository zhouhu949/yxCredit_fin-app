package com.zw.rule.orderLog.po;
import java.io.Serializable;

public class MagOrderLog implements Serializable {

	/****/
	private String ID;

	/**用户ID**/
	private String USERID;

	/**分期期数**/
	private String periods;

	/**商户ID(类型 0  验证码   1 通知   2 预警)**/
	private String MERCHANTID;

	/**商户名称**/
	private String MERCHANTNAME;

	/****/
	private String merchandiseId;

	/**客户ID**/
	private String CUSTOMERID;

	/**客户名**/
	private String CUSTOMERNAME;

	/**客户联系电话**/
	private String TEL;

	/**证件类型**/
	private String CARDTYPE;

	/**证件号码**/
	private String CARD;

	/**授信额度**/
	private String credit;

	/**金额**/
	private String amount;

	/**归属公司**/
	private String company;

	/**归属部门**/
	private String branch;

	/**归属人**/
	private String manager;

	/**环节**/
	private String tache;

	/**创建时间**/
	private String CREATTIME;

	/**修改时间**/
	private String ALTERTIME;

	/**状态(0 未提交 1 失效 2 借款申请 3 审批通过  4 审批拒绝 5 审批退回 6 开户成功  7 签约成功 8 合同确认 9 放款成功)**/
	private String state;

	/****/
	private String APEX1;

	/****/
	private String APEX2;

	/****/
	private String APEX3;

	/****/
	private String BAK;

	/**审核人**/
	private String APPROVER;

	/**审核信息**/
	private String MESSAGE;

	/**资金渠道**/
	private String channel;

	/**合同ID**/
	private String orderId;

	/**开户行**/
	private String bank;

	/**户名**/
	private String accountName;

	/**账号**/
	private String account;

	/**预授信额度**/
	private String precredit;

	/****/
	private String merchandiseType;

	/****/
	private String merchandiseBrand;

	/****/
	private String merchandiseModel;

	/****/
	private String productType;

	/****/
	private String productTypeName;

	/****/
	private String productName;

	/****/
	private String productNameName;

	/****/
	private String productDetail;

	/****/
	private String productDetailName;

	/**信息变更记录**/
	private String msgChange;

	/**订单交接记录**/
	private String orderChangeMsg;

	/****/
	private String cardTypeId;

	/****/
	private String channelName;

	/****/
	private String repayTypeId;

	/****/
	private String levelId;

	/****/
	private String cusSourceId;

	/****/
	private String merchandiseBrandId;

	/****/
	private String sexName;

	/****/
	private String sex;

	/****/
	private String empNumber;

	/****/
	private String level;

	/****/
	private String orderNo;

	/****/
	private String merchandiseName;

	/****/
	private String empId;

	/****/
	private String repayType;

	/****/
	private String rate;

	/****/
	private String cusSource;

	/****/
	private String merchandiseCode;

	/****/
	private String provincesId;

	/****/
	private String cityId;

	/****/
	private String districId;

	/****/
	private String provinces;

	/****/
	private String city;

	/****/
	private String distric;

	/****/
	private String productDetailCode;

	/****/
	private String crmApplyId;

	/****/
	private String contractAmount;

	/****/
	private String loanAmount;

	/****/
	private String merchandiseTypeId;

	/****/
	private String messageId;

	/****/
	private String merchandiseCodeprovincesId;



	public void setID(String ID){
		this.ID = ID;
	}

	public String getID(){
		return this.ID;
	}

	public void setUSERID(String USERID){
		this.USERID = USERID;
	}

	public String getUSERID(){
		return this.USERID;
	}

	public void setPeriods(String periods){
		this.periods = periods;
	}

	public String getPeriods(){
		return this.periods;
	}

	public void setMERCHANTID(String MERCHANTID){
		this.MERCHANTID = MERCHANTID;
	}

	public String getMERCHANTID(){
		return this.MERCHANTID;
	}

	public void setMERCHANTNAME(String MERCHANTNAME){
		this.MERCHANTNAME = MERCHANTNAME;
	}

	public String getMERCHANTNAME(){
		return this.MERCHANTNAME;
	}

	public void setMerchandiseId(String merchandiseId){
		this.merchandiseId = merchandiseId;
	}

	public String getMerchandiseId(){
		return this.merchandiseId;
	}

	public void setCUSTOMERID(String CUSTOMERID){
		this.CUSTOMERID = CUSTOMERID;
	}

	public String getCUSTOMERID(){
		return this.CUSTOMERID;
	}

	public void setCUSTOMERNAME(String CUSTOMERNAME){
		this.CUSTOMERNAME = CUSTOMERNAME;
	}

	public String getCUSTOMERNAME(){
		return this.CUSTOMERNAME;
	}

	public void setTEL(String TEL){
		this.TEL = TEL;
	}

	public String getTEL(){
		return this.TEL;
	}

	public void setCARDTYPE(String CARDTYPE){
		this.CARDTYPE = CARDTYPE;
	}

	public String getCARDTYPE(){
		return this.CARDTYPE;
	}

	public void setCARD(String CARD){
		this.CARD = CARD;
	}

	public String getCARD(){
		return this.CARD;
	}

	public void setCredit(String credit){
		this.credit = credit;
	}

	public String getCredit(){
		return this.credit;
	}

	public void setAmount(String amount){
		this.amount = amount;
	}

	public String getAmount(){
		return this.amount;
	}

	public void setCompany(String company){
		this.company = company;
	}

	public String getCompany(){
		return this.company;
	}

	public void setBranch(String branch){
		this.branch = branch;
	}

	public String getBranch(){
		return this.branch;
	}

	public void setManager(String manager){
		this.manager = manager;
	}

	public String getManager(){
		return this.manager;
	}

	public void setTache(String tache){
		this.tache = tache;
	}

	public String getTache(){
		return this.tache;
	}

	public void setCREATTIME(String CREATTIME){
		this.CREATTIME = CREATTIME;
	}

	public String getCREATTIME(){
		return this.CREATTIME;
	}

	public void setALTERTIME(String ALTERTIME){
		this.ALTERTIME = ALTERTIME;
	}

	public String getALTERTIME(){
		return this.ALTERTIME;
	}

	public void setState(String state){
		this.state = state;
	}

	public String getState(){
		return this.state;
	}

	public void setAPEX1(String APEX1){
		this.APEX1 = APEX1;
	}

	public String getAPEX1(){
		return this.APEX1;
	}

	public void setAPEX2(String APEX2){
		this.APEX2 = APEX2;
	}

	public String getAPEX2(){
		return this.APEX2;
	}

	public void setAPEX3(String APEX3){
		this.APEX3 = APEX3;
	}

	public String getAPEX3(){
		return this.APEX3;
	}

	public void setBAK(String BAK){
		this.BAK = BAK;
	}

	public String getBAK(){
		return this.BAK;
	}

	public void setAPPROVER(String APPROVER){
		this.APPROVER = APPROVER;
	}

	public String getAPPROVER(){
		return this.APPROVER;
	}

	public void setMESSAGE(String MESSAGE){
		this.MESSAGE = MESSAGE;
	}

	public String getMESSAGE(){
		return this.MESSAGE;
	}

	public void setChannel(String channel){
		this.channel = channel;
	}

	public String getChannel(){
		return this.channel;
	}

	public void setOrderId(String orderId){
		this.orderId = orderId;
	}

	public String getOrderId(){
		return this.orderId;
	}

	public void setBank(String bank){
		this.bank = bank;
	}

	public String getBank(){
		return this.bank;
	}

	public void setAccountName(String accountName){
		this.accountName = accountName;
	}

	public String getAccountName(){
		return this.accountName;
	}

	public void setAccount(String account){
		this.account = account;
	}

	public String getAccount(){
		return this.account;
	}

	public void setPrecredit(String precredit){
		this.precredit = precredit;
	}

	public String getPrecredit(){
		return this.precredit;
	}

	public void setMerchandiseType(String merchandiseType){
		this.merchandiseType = merchandiseType;
	}

	public String getMerchandiseType(){
		return this.merchandiseType;
	}

	public void setMerchandiseBrand(String merchandiseBrand){
		this.merchandiseBrand = merchandiseBrand;
	}

	public String getMerchandiseBrand(){
		return this.merchandiseBrand;
	}

	public void setMerchandiseModel(String merchandiseModel){
		this.merchandiseModel = merchandiseModel;
	}

	public String getMerchandiseModel(){
		return this.merchandiseModel;
	}

	public void setProductType(String productType){
		this.productType = productType;
	}

	public String getProductType(){
		return this.productType;
	}

	public void setProductTypeName(String productTypeName){
		this.productTypeName = productTypeName;
	}

	public String getProductTypeName(){
		return this.productTypeName;
	}

	public void setProductName(String productName){
		this.productName = productName;
	}

	public String getProductName(){
		return this.productName;
	}

	public void setProductNameName(String productNameName){
		this.productNameName = productNameName;
	}

	public String getProductNameName(){
		return this.productNameName;
	}

	public void setProductDetail(String productDetail){
		this.productDetail = productDetail;
	}

	public String getProductDetail(){
		return this.productDetail;
	}

	public void setProductDetailName(String productDetailName){
		this.productDetailName = productDetailName;
	}

	public String getProductDetailName(){
		return this.productDetailName;
	}

	public void setMsgChange(String msgChange){
		this.msgChange = msgChange;
	}

	public String getMsgChange(){
		return this.msgChange;
	}

	public void setOrderChangeMsg(String orderChangeMsg){
		this.orderChangeMsg = orderChangeMsg;
	}

	public String getOrderChangeMsg(){
		return this.orderChangeMsg;
	}

	public void setCardTypeId(String cardTypeId){
		this.cardTypeId = cardTypeId;
	}

	public String getCardTypeId(){
		return this.cardTypeId;
	}

	public void setChannelName(String channelName){
		this.channelName = channelName;
	}

	public String getChannelName(){
		return this.channelName;
	}

	public void setRepayTypeId(String repayTypeId){
		this.repayTypeId = repayTypeId;
	}

	public String getRepayTypeId(){
		return this.repayTypeId;
	}

	public void setLevelId(String levelId){
		this.levelId = levelId;
	}

	public String getLevelId(){
		return this.levelId;
	}

	public void setCusSourceId(String cusSourceId){
		this.cusSourceId = cusSourceId;
	}

	public String getCusSourceId(){
		return this.cusSourceId;
	}

	public void setMerchandiseBrandId(String merchandiseBrandId){
		this.merchandiseBrandId = merchandiseBrandId;
	}

	public String getMerchandiseBrandId(){
		return this.merchandiseBrandId;
	}

	public void setSexName(String sexName){
		this.sexName = sexName;
	}

	public String getSexName(){
		return this.sexName;
	}

	public void setSex(String sex){
		this.sex = sex;
	}

	public String getSex(){
		return this.sex;
	}

	public void setEmpNumber(String empNumber){
		this.empNumber = empNumber;
	}

	public String getEmpNumber(){
		return this.empNumber;
	}

	public void setLevel(String level){
		this.level = level;
	}

	public String getLevel(){
		return this.level;
	}

	public void setOrderNo(String orderNo){
		this.orderNo = orderNo;
	}

	public String getOrderNo(){
		return this.orderNo;
	}

	public void setMerchandiseName(String merchandiseName){
		this.merchandiseName = merchandiseName;
	}

	public String getMerchandiseName(){
		return this.merchandiseName;
	}

	public void setEmpId(String empId){
		this.empId = empId;
	}

	public String getEmpId(){
		return this.empId;
	}

	public void setRepayType(String repayType){
		this.repayType = repayType;
	}

	public String getRepayType(){
		return this.repayType;
	}

	public void setRate(String rate){
		this.rate = rate;
	}

	public String getRate(){
		return this.rate;
	}

	public void setCusSource(String cusSource){
		this.cusSource = cusSource;
	}

	public String getCusSource(){
		return this.cusSource;
	}

	public void setMerchandiseCode(String merchandiseCode){
		this.merchandiseCode = merchandiseCode;
	}

	public String getMerchandiseCode(){
		return this.merchandiseCode;
	}

	public void setProvincesId(String provincesId){
		this.provincesId = provincesId;
	}

	public String getProvincesId(){
		return this.provincesId;
	}

	public void setCityId(String cityId){
		this.cityId = cityId;
	}

	public String getCityId(){
		return this.cityId;
	}

	public void setDistricId(String districId){
		this.districId = districId;
	}

	public String getDistricId(){
		return this.districId;
	}

	public void setProvinces(String provinces){
		this.provinces = provinces;
	}

	public String getProvinces(){
		return this.provinces;
	}

	public void setCity(String city){
		this.city = city;
	}

	public String getCity(){
		return this.city;
	}

	public void setDistric(String distric){
		this.distric = distric;
	}

	public String getDistric(){
		return this.distric;
	}

	public void setProductDetailCode(String productDetailCode){
		this.productDetailCode = productDetailCode;
	}

	public String getProductDetailCode(){
		return this.productDetailCode;
	}

	public void setCrmApplyId(String crmApplyId){
		this.crmApplyId = crmApplyId;
	}

	public String getCrmApplyId(){
		return this.crmApplyId;
	}

	public void setContractAmount(String contractAmount){
		this.contractAmount = contractAmount;
	}

	public String getContractAmount(){
		return this.contractAmount;
	}

	public void setLoanAmount(String loanAmount){
		this.loanAmount = loanAmount;
	}

	public String getLoanAmount(){
		return this.loanAmount;
	}

	public void setMerchandiseTypeId(String merchandiseTypeId){
		this.merchandiseTypeId = merchandiseTypeId;
	}

	public String getMerchandiseTypeId(){
		return this.merchandiseTypeId;
	}

	public void setMessageId(String messageId){
		this.messageId = messageId;
	}

	public String getMessageId(){
		return this.messageId;
	}

	public void setMerchandiseCodeprovincesId(String merchandiseCodeprovincesId){
		this.merchandiseCodeprovincesId = merchandiseCodeprovincesId;
	}

	public String getMerchandiseCodeprovincesId(){
		return this.merchandiseCodeprovincesId;
	}

}
