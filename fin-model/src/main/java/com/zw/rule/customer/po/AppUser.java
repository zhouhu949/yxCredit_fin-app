package com.zw.rule.customer.po;
import java.io.Serializable;


/**
 * 
 * 
 * 
 **/
@SuppressWarnings("serial")
public class AppUser implements Serializable {

	/****/
	private String id;

	/**手机号**/
	private String tel;

	/**密码**/
	private String passwd;

	/**密码输入错误次数**/
	private String errorNum;

	/**注册时间**/
	private String creatTime;

	/**修改时间**/
	private String alterTime;

	/**2  失效   0 未登录 1 登录**/
	private String state;

	/****/
	private String registrationId;

	/****/
	private String apex1;

	/****/
	private String apex2;

	/****/
	private String apex3;

	/****/
	private String bak;

	/**头像图片路径**/
	private String headImg;

	/**身份证号码**/
	private String idcard;

	/**真实姓名**/
	private String realname;

	/****/
	private String token;

	/****/
	private String tokenTime;

	/**是否是黑名单用户（1：是，0：否）**/
	private String isBlack;

	/**手机的唯一识别码**/
	private String onlyCode;

	/****/
	private String orderRefusedTime;



	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return this.id;
	}

	public void setTel(String tel){
		this.tel = tel;
	}

	public String getTel(){
		return this.tel;
	}

	public void setPasswd(String passwd){
		this.passwd = passwd;
	}

	public String getPasswd(){
		return this.passwd;
	}

	public void setErrorNum(String errorNum){
		this.errorNum = errorNum;
	}

	public String getErrorNum(){
		return this.errorNum;
	}

	public void setCreatTime(String creatTime){
		this.creatTime = creatTime;
	}

	public String getCreatTime(){
		return this.creatTime;
	}

	public void setAlterTime(String alterTime){
		this.alterTime = alterTime;
	}

	public String getAlterTime(){
		return this.alterTime;
	}

	public void setState(String state){
		this.state = state;
	}

	public String getState(){
		return this.state;
	}

	public void setRegistrationId(String registrationId){
		this.registrationId = registrationId;
	}

	public String getRegistrationId(){
		return this.registrationId;
	}

	public void setApex1(String apex1){
		this.apex1 = apex1;
	}

	public String getApex1(){
		return this.apex1;
	}

	public void setApex2(String apex2){
		this.apex2 = apex2;
	}

	public String getApex2(){
		return this.apex2;
	}

	public void setApex3(String apex3){
		this.apex3 = apex3;
	}

	public String getApex3(){
		return this.apex3;
	}

	public void setBak(String bak){
		this.bak = bak;
	}

	public String getBak(){
		return this.bak;
	}

	public void setHeadImg(String headImg){
		this.headImg = headImg;
	}

	public String getHeadImg(){
		return this.headImg;
	}

	public void setIdcard(String idcard){
		this.idcard = idcard;
	}

	public String getIdcard(){
		return this.idcard;
	}

	public void setRealname(String realname){
		this.realname = realname;
	}

	public String getRealname(){
		return this.realname;
	}

	public void setToken(String token){
		this.token = token;
	}

	public String getToken(){
		return this.token;
	}

	public void setTokenTime(String tokenTime){
		this.tokenTime = tokenTime;
	}

	public String getTokenTime(){
		return this.tokenTime;
	}

	public void setIsBlack(String isBlack){
		this.isBlack = isBlack;
	}

	public String getIsBlack(){
		return this.isBlack;
	}

	public void setOnlyCode(String onlyCode){
		this.onlyCode = onlyCode;
	}

	public String getOnlyCode(){
		return this.onlyCode;
	}

	public void setOrderRefusedTime(String orderRefusedTime){
		this.orderRefusedTime = orderRefusedTime;
	}

	public String getOrderRefusedTime(){
		return this.orderRefusedTime;
	}

}
