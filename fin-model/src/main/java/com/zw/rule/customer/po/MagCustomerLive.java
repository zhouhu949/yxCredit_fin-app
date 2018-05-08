package com.zw.rule.customer.po;
import java.io.Serializable;


/**
 * 
 * 
 * 
 **/
@SuppressWarnings("serial")
public class MagCustomerLive implements Serializable {

	/****/
	private String ID;

	/****/
	private String customerId;

	/**现居住地址**/
	private String nowaddress;

	/**此地居住时间**/
	private String addresslivetime;

	/**当前城市居住时间**/
	private String citylivetime;

	/**居住地址类型**/
	private String houseproperty;

	/****/
	private String rent;

	/****/
	private String CREATTIME;

	/****/
	private String ALTERTIME;

	/****/
	private String state;

	/****/
	private String otherCondition;

	/****/
	private String APEX2;

	/****/
	private String APEX3;

	/****/
	private String BAK;

	/****/
	private String electricNumber;

	/****/
	private String electricPassword;

	/****/
	private String remark;

	/**省**/
	private String provinces;

	/**市**/
	private String city;

	/**区**/
	private String distric;

	/****/
	private String provincesId;

	/****/
	private String cityId;

	/****/
	private String districId;

	/**居住地址类型code**/
	private String housepropertyId;

	/**经度**/
	private String longitude;

	/**纬度**/
	private String latitude;

	private String  addressDetail;

	public String getAddressDetail() {
		return addressDetail;
	}

	public void setAddressDetail(String addressDetail) {
		this.addressDetail = addressDetail;
	}

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

	public void setNowaddress(String nowaddress){
		this.nowaddress = nowaddress;
	}

	public String getNowaddress(){
		return this.nowaddress;
	}

	public void setAddresslivetime(String addresslivetime){
		this.addresslivetime = addresslivetime;
	}

	public String getAddresslivetime(){
		return this.addresslivetime;
	}

	public void setCitylivetime(String citylivetime){
		this.citylivetime = citylivetime;
	}

	public String getCitylivetime(){
		return this.citylivetime;
	}

	public void setHouseproperty(String houseproperty){
		this.houseproperty = houseproperty;
	}

	public String getHouseproperty(){
		return this.houseproperty;
	}

	public void setRent(String rent){
		this.rent = rent;
	}

	public String getRent(){
		return this.rent;
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

	public void setOtherCondition(String otherCondition){
		this.otherCondition = otherCondition;
	}

	public String getOtherCondition(){
		return this.otherCondition;
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

	public void setElectricNumber(String electricNumber){
		this.electricNumber = electricNumber;
	}

	public String getElectricNumber(){
		return this.electricNumber;
	}

	public void setElectricPassword(String electricPassword){
		this.electricPassword = electricPassword;
	}

	public String getElectricPassword(){
		return this.electricPassword;
	}

	public void setRemark(String remark){
		this.remark = remark;
	}

	public String getRemark(){
		return this.remark;
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

	public void setHousepropertyId(String housepropertyId){
		this.housepropertyId = housepropertyId;
	}

	public String getHousepropertyId(){
		return this.housepropertyId;
	}

	public void setLongitude(String longitude){
		this.longitude = longitude;
	}

	public String getLongitude(){
		return this.longitude;
	}

	public void setLatitude(String latitude){
		this.latitude = latitude;
	}

	public String getLatitude(){
		return this.latitude;
	}

}
