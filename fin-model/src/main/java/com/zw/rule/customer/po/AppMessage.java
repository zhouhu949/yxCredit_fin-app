package com.zw.rule.customer.po;
import java.io.Serializable;


/**
 * 
 * 
 * 
 **/
@SuppressWarnings("serial")
public class AppMessage implements Serializable {

	/****/
	private String id;

	/**用户的唯一标示**/
	private String userId;

	/**信息标题**/
	private String title;

	/**描述**/
	private String content;

	/**插入时间**/
	private String creatTime;

	/**更改时间**/
	private String alterTime;

	/**状态 '0 未读  1 失效  2 已读';**/
	private String state;

	/**推送是否成功 0 成功 1 失败**/
	private String jpushState;

	/****/
	private String apex1;

	/****/
	private String apex2;

	/****/
	private String apex3;

	/****/
	private String bak;

	/**推送状态//0推送成功,1失败**/
	private String pushState;

	/**跟新状态  0 不可点击 1 可点击**/
	private String updateState;

	/**消息的类型 0 系统 1业务**/
	private String msgType;

	/****/
	private String orderState;

	private String orderType;

	/****/
	private String orderId;



	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return this.id;
	}

	public void setUserId(String userId){
		this.userId = userId;
	}

	public String getUserId(){
		return this.userId;
	}

	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle(){
		return this.title;
	}

	public void setContent(String content){
		this.content = content;
	}

	public String getContent(){
		return this.content;
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

	public void setJpushState(String jpushState){
		this.jpushState = jpushState;
	}

	public String getJpushState(){
		return this.jpushState;
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

	public void setPushState(String pushState){
		this.pushState = pushState;
	}

	public String getPushState(){
		return this.pushState;
	}

	public void setUpdateState(String updateState){
		this.updateState = updateState;
	}

	public String getUpdateState(){
		return this.updateState;
	}

	public void setMsgType(String msgType){
		this.msgType = msgType;
	}

	public String getMsgType(){
		return this.msgType;
	}

	public void setOrderState(String orderState){
		this.orderState = orderState;
	}

	public String getOrderState(){
		return this.orderState;
	}

	public void setOrderId(String orderId){
		this.orderId = orderId;
	}

	public String getOrderId(){
		return this.orderId;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
}
