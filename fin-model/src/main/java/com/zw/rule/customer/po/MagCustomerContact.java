package com.zw.rule.customer.po;
import java.io.Serializable;


/**
 * 
 * 
 * 
 **/
@SuppressWarnings("serial")
public class MagCustomerContact implements Serializable {

	/****/
	private String ID;

	/****/
	private String customerId;

	/****/
	private String tel;

	/****/
	private String sparetel;

	/****/
	private String email;

	/****/
	private String qq;

	/****/
	private String wechat;

	/****/
	private String CREATTIME;

	/****/
	private String ALTERTIME;

	/****/
	private String state;

	/****/
	private String APEX1;

	/****/
	private String APEX2;

	/****/
	private String APEX3;

	/****/
	private String BAK;

	/**服务密码**/
	private String serverpwd;

	/**电商平台账号**/
	private String shop;

	/**电商平台名称**/
	private String shopName;

	/**电商平台登录账户**/
	private String shopUser;

	/**电商平台登录密码**/
	private String shopPsw;



	public void setID(String ID){
		this.ID = ID;
	}

	public String getID(){
		return this.ID;
	}

	public void setCustomerId(String customerId){
		this.customerId = customerId;
	}

	public String getCustomerId(){
		return this.customerId;
	}

	public void setTel(String tel){
		this.tel = tel;
	}

	public String getTel(){
		return this.tel;
	}

	public void setSparetel(String sparetel){
		this.sparetel = sparetel;
	}

	public String getSparetel(){
		return this.sparetel;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return this.email;
	}

	public void setQq(String qq){
		this.qq = qq;
	}

	public String getQq(){
		return this.qq;
	}

	public void setWechat(String wechat){
		this.wechat = wechat;
	}

	public String getWechat(){
		return this.wechat;
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

	public void setServerpwd(String serverpwd){
		this.serverpwd = serverpwd;
	}

	public String getServerpwd(){
		return this.serverpwd;
	}

	public void setShop(String shop){
		this.shop = shop;
	}

	public String getShop(){
		return this.shop;
	}

	public void setShopName(String shopName){
		this.shopName = shopName;
	}

	public String getShopName(){
		return this.shopName;
	}

	public void setShopUser(String shopUser){
		this.shopUser = shopUser;
	}

	public String getShopUser(){
		return this.shopUser;
	}

	public void setShopPsw(String shopPsw){
		this.shopPsw = shopPsw;
	}

	public String getShopPsw(){
		return this.shopPsw;
	}

}
