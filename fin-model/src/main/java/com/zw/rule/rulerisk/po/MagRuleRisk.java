package com.zw.rule.rulerisk.po;
import java.io.Serializable;
public class MagRuleRisk implements Serializable {

	private String id;

	/**身份证号**/
	private String cradNo;

	/**活体人脸识别**/
	private String htrl = "一致";

	/**四要素验证**/
	private String siys = "一致";

	/**手机号实名验证**/
	private String phoneReal= "一致";

	/**手机号使用时间**/
	private String phoneTime;

	/**手机GPS定位**/
	private String phoneGps;

	/**公积金授权**/
	private String accumulation= "未授权";

	/**社保授权**/
	private String social= "未授权";

	/**支付宝实名验证**/
	private String alipayRealName= "未授权";

	/**芝麻分**/
	private String sesameSeeds= "未授权";

	/**鹏元身份信息**/
	private String pyIfo = "姓名和身份证号码一致";

	/**鹏元教育信息**/
	private String pyEdu = "未授权";

	/**鹏元催欠公告信息条数**/
	private String pyDistress;

	/**鹏元税务行政执法信息条数**/
	private String pyTax;

	/**鹏元司法案例信息条数**/
	private String pyJudCase;

	/**鹏元司法失信信息条数**/
	private String pyJudDishonesty;

	/**鹏元司法执行信息条数**/
	private String pyJudEnfor;

	/**鹏元网贷逾期信息条数**/
	private String pyNetLoanOver;

	/**同盾黑名单命中数量**/
	private String tdBHit = "0";

	/**同盾借款设备代理识别**/
	private String tdLoanProxy = "否";

	/**同盾借款设备作弊工具识别**/
	private String tdLoanTool= "否";

	/**同盾外部贷款平台个数**/
	private String tdExtPlatform= "0";

	/**同盾身份证命中法院犯罪通缉名单**/
	private String tdIdCriminal= "否";

	/**同盾身份证命中高风险关注名单**/
	private String tdIdRisk= "否";

	/**同盾身份证命中欠税名单**/
	private String tdIdTaxes= "否";

	/**同盾身份证命中信贷逾期名单**/
	private String tdIdCredit= "否";

	/**同盾手机号命中高风险关注名单**/
	private String tdPhoneRisk= "否";

	/**同盾手机号命中欠款公司法人代表名单**/
	private String tdPhoneArrears= "否";

	/**同盾手机号命中通讯小号库**/
	private String tdPhoneCommunication= "否";

	/**同盾手机号命中信贷逾期名单**/
	private String tdPhoneCredit= "否";

	/**同盾手机号命中虚假号码库**/
	private String tdPhoneFalseNum= "否";

	/**同盾手机号命中诈骗号码库**/
	private String tdPhoneFraudNum= "否";

	/**同盾联系人手机号命中高风险关注名单**/
	private String tdContPhoneRisk= "否";

	/**同盾联系人手机号命中通讯小号库**/
	private String tdContPhoneCommunication= "否";

	/**同盾联系人手机号命中信贷逾期名单**/
	private String tdContPhoneCredit= "否";

	/**同盾联系人手机号命中虚假号码库**/
	private String tdContPhoneFalseNum= "否";

	/**同盾联系人手机号命中诈骗号码库**/
	private String tdContPhoneFraudNum= "否";

	/**同盾单固命中虚假号码库**/
	private String tdDgFalseNum= "否";

	/**同盾单固命中诈骗号码库**/
	private String tdDgFraudNum= "否";

	/**同盾单固命中中介号码库**/
	private String tdDgAgencyNum= "否";

	/**上海资信查询所有贷款笔数**/
	private String shzxLoan= "0";

	/**上海资信查询未结清贷款数量**/
	private String shzxOutLoan= "0";

	/**上海咨询查询逾期数量**/
	private String shzxOverdue= "0";

	/**上海资信查询共享授信额度总和**/
	private String shzxLineCredit= "0";

	/**上海资信查询月供**/
	private String shzxMonth= "0";



	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return this.id;
	}

	public void setCradNo(String cradNo){
		this.cradNo = cradNo;
	}

	public String getCradNo(){
		return this.cradNo;
	}

	public void setHtrl(String htrl){
		this.htrl = htrl;
	}

	public String getHtrl(){
		return this.htrl;
	}

	public void setSiys(String siys){
		this.siys = siys;
	}

	public String getSiys(){
		return this.siys;
	}

	public void setPhoneReal(String phoneReal){
		this.phoneReal = phoneReal;
	}

	public String getPhoneReal(){
		return this.phoneReal;
	}

	public void setPhoneTime(String phoneTime){
		this.phoneTime = phoneTime;
	}

	public String getPhoneTime(){
		return this.phoneTime;
	}

	public void setPhoneGps(String phoneGps){
		this.phoneGps = phoneGps;
	}

	public String getPhoneGps(){
		return this.phoneGps;
	}

	public void setAccumulation(String accumulation){
		this.accumulation = accumulation;
	}

	public String getAccumulation(){
		return this.accumulation;
	}

	public void setSocial(String social){
		this.social = social;
	}

	public String getSocial(){
		return this.social;
	}

	public void setAlipayRealName(String alipayRealName){
		this.alipayRealName = alipayRealName;
	}

	public String getAlipayRealName(){
		return this.alipayRealName;
	}

	public void setSesameSeeds(String sesameSeeds){
		this.sesameSeeds = sesameSeeds;
	}

	public String getSesameSeeds(){
		return this.sesameSeeds;
	}

	public void setPyIfo(String pyIfo){
		this.pyIfo = pyIfo;
	}

	public String getPyIfo(){
		return this.pyIfo;
	}

	public void setPyEdu(String pyEdu){
		this.pyEdu = pyEdu;
	}

	public String getPyEdu(){
		return this.pyEdu;
	}

	public void setPyDistress(String pyDistress){
		this.pyDistress = pyDistress;
	}

	public String getPyDistress(){
		return this.pyDistress;
	}

	public void setPyTax(String pyTax){
		this.pyTax = pyTax;
	}

	public String getPyTax(){
		return this.pyTax;
	}

	public void setPyJudCase(String pyJudCase){
		this.pyJudCase = pyJudCase;
	}

	public String getPyJudCase(){
		return this.pyJudCase;
	}

	public void setPyJudDishonesty(String pyJudDishonesty){
		this.pyJudDishonesty = pyJudDishonesty;
	}

	public String getPyJudDishonesty(){
		return this.pyJudDishonesty;
	}

	public void setPyJudEnfor(String pyJudEnfor){
		this.pyJudEnfor = pyJudEnfor;
	}

	public String getPyJudEnfor(){
		return this.pyJudEnfor;
	}

	public void setPyNetLoanOver(String pyNetLoanOver){
		this.pyNetLoanOver = pyNetLoanOver;
	}

	public String getPyNetLoanOver(){
		return this.pyNetLoanOver;
	}

	public void setTdBHit(String tdBHit){
		this.tdBHit = tdBHit;
	}

	public String getTdBHit(){
		return this.tdBHit;
	}

	public void setTdLoanProxy(String tdLoanProxy){
		this.tdLoanProxy = tdLoanProxy;
	}

	public String getTdLoanProxy(){
		return this.tdLoanProxy;
	}

	public void setTdLoanTool(String tdLoanTool){
		this.tdLoanTool = tdLoanTool;
	}

	public String getTdLoanTool(){
		return this.tdLoanTool;
	}

	public void setTdExtPlatform(String tdExtPlatform){
		this.tdExtPlatform = tdExtPlatform;
	}

	public String getTdExtPlatform(){
		return this.tdExtPlatform;
	}

	public void setTdIdCriminal(String tdIdCriminal){
		this.tdIdCriminal = tdIdCriminal;
	}

	public String getTdIdCriminal(){
		return this.tdIdCriminal;
	}

	public void setTdIdRisk(String tdIdRisk){
		this.tdIdRisk = tdIdRisk;
	}

	public String getTdIdRisk(){
		return this.tdIdRisk;
	}

	public void setTdIdTaxes(String tdIdTaxes){
		this.tdIdTaxes = tdIdTaxes;
	}

	public String getTdIdTaxes(){
		return this.tdIdTaxes;
	}

	public void setTdIdCredit(String tdIdCredit){
		this.tdIdCredit = tdIdCredit;
	}

	public String getTdIdCredit(){
		return this.tdIdCredit;
	}

	public void setTdPhoneRisk(String tdPhoneRisk){
		this.tdPhoneRisk = tdPhoneRisk;
	}

	public String getTdPhoneRisk(){
		return this.tdPhoneRisk;
	}

	public void setTdPhoneArrears(String tdPhoneArrears){
		this.tdPhoneArrears = tdPhoneArrears;
	}

	public String getTdPhoneArrears(){
		return this.tdPhoneArrears;
	}

	public void setTdPhoneCommunication(String tdPhoneCommunication){
		this.tdPhoneCommunication = tdPhoneCommunication;
	}

	public String getTdPhoneCommunication(){
		return this.tdPhoneCommunication;
	}

	public void setTdPhoneCredit(String tdPhoneCredit){
		this.tdPhoneCredit = tdPhoneCredit;
	}

	public String getTdPhoneCredit(){
		return this.tdPhoneCredit;
	}

	public void setTdPhoneFalseNum(String tdPhoneFalseNum){
		this.tdPhoneFalseNum = tdPhoneFalseNum;
	}

	public String getTdPhoneFalseNum(){
		return this.tdPhoneFalseNum;
	}

	public void setTdPhoneFraudNum(String tdPhoneFraudNum){
		this.tdPhoneFraudNum = tdPhoneFraudNum;
	}

	public String getTdPhoneFraudNum(){
		return this.tdPhoneFraudNum;
	}

	public void setTdContPhoneRisk(String tdContPhoneRisk){
		this.tdContPhoneRisk = tdContPhoneRisk;
	}

	public String getTdContPhoneRisk(){
		return this.tdContPhoneRisk;
	}

	public void setTdContPhoneCommunication(String tdContPhoneCommunication){
		this.tdContPhoneCommunication = tdContPhoneCommunication;
	}

	public String getTdContPhoneCommunication(){
		return this.tdContPhoneCommunication;
	}

	public void setTdContPhoneCredit(String tdContPhoneCredit){
		this.tdContPhoneCredit = tdContPhoneCredit;
	}

	public String getTdContPhoneCredit(){
		return this.tdContPhoneCredit;
	}

	public void setTdContPhoneFalseNum(String tdContPhoneFalseNum){
		this.tdContPhoneFalseNum = tdContPhoneFalseNum;
	}

	public String getTdContPhoneFalseNum(){
		return this.tdContPhoneFalseNum;
	}

	public void setTdContPhoneFraudNum(String tdContPhoneFraudNum){
		this.tdContPhoneFraudNum = tdContPhoneFraudNum;
	}

	public String getTdContPhoneFraudNum(){
		return this.tdContPhoneFraudNum;
	}

	public void setTdDgFalseNum(String tdDgFalseNum){
		this.tdDgFalseNum = tdDgFalseNum;
	}

	public String getTdDgFalseNum(){
		return this.tdDgFalseNum;
	}

	public void setTdDgFraudNum(String tdDgFraudNum){
		this.tdDgFraudNum = tdDgFraudNum;
	}

	public String getTdDgFraudNum(){
		return this.tdDgFraudNum;
	}

	public void setTdDgAgencyNum(String tdDgAgencyNum){
		this.tdDgAgencyNum = tdDgAgencyNum;
	}

	public String getTdDgAgencyNum(){
		return this.tdDgAgencyNum;
	}

	public void setShzxLoan(String shzxLoan){
		this.shzxLoan = shzxLoan;
	}

	public String getShzxLoan(){
		return this.shzxLoan;
	}

	public void setShzxOutLoan(String shzxOutLoan){
		this.shzxOutLoan = shzxOutLoan;
	}

	public String getShzxOutLoan(){
		return this.shzxOutLoan;
	}

	public void setShzxOverdue(String shzxOverdue){
		this.shzxOverdue = shzxOverdue;
	}

	public String getShzxOverdue(){
		return this.shzxOverdue;
	}

	public void setShzxLineCredit(String shzxLineCredit){
		this.shzxLineCredit = shzxLineCredit;
	}

	public String getShzxLineCredit(){
		return this.shzxLineCredit;
	}

	public void setShzxMonth(String shzxMonth){
		this.shzxMonth = shzxMonth;
	}

	public String getShzxMonth(){
		return this.shzxMonth;
	}

}
