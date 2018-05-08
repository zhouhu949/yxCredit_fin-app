package com.zw.rule.entity;
/**
 * 客户日志表
 * mag_customer_log
 */

public class CustomerLogBean {
	private String id;//ID号
	private String user_id;//用户
	private String change_id;//查找的值
	private String type;//类型（0 业务员变更 1 客户自己变更）
	private String classify;// 0 客户资料变更  1 订单变更
	private String value;
	private String create_time;
	private String update_time;
	private String status;
	private String order_id;
	
	
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public void setStatus(String status){
		this.status=status;
	}	
	public String getStatus(){
		return this.status;
	}
	
	public void setUpdateTime(String update_time){
		this.update_time=update_time;
	}	
	public String getUpdateTime(){
		return this.update_time;
	}
	
	public void setCreateTime(String create_time){
		this.create_time=create_time;
	}	
	public String getCreateTime(){
		return this.create_time;
	}
	
	public void setValue(String value){
		this.value=value;
	}	
	public String getValue(){
		return this.value;
	}
	
	public void setClassify(String classify){
		this.classify=classify;
	}	
	public String getClassify(){
		return this.classify;
	}
	
	public void setType(String type){
		this.type=type;
	}	
	public String getType(){
		return this.type;
	}
	
	public void setChangeId(String change_id){
		this.change_id=change_id;
	}	
	public String getChangeId(){
		return this.change_id;
	}
	
	public void setId(String id){
		this.id=id;
	}	
	public String getId(){
		return this.id;
	}
	
	public void setUserId(String user_id){
		this.user_id=user_id;
	}	
	public String getUserId(){
		return this.user_id;
	}
}
