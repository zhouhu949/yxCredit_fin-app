package com.zw.rule.product;

/**
 * 
 * 功能说明：信贷类型/产品表
 * 典型用法：
 * 特殊用法：	
 * @author wangmin
 * 修改人: 
 * 修改原因：
 * 修改时间：
 * 修改内容：
 * 创建日期：2017-6-13
 * Copyright zzl-apt
 */
public class CrmProduct {
	
	// 产品状态
	public final static Integer STATUS_YES = 1;				//启用
	public final static Integer STATUS_NO = 0;				//停用

	private String id;			// 唯一标识（主键）
	
	private String name;		// 产品名称
	
	private String number;		// 产品编号（唯一）
	
	private Integer status;		// 0 标示启动 1 标示停用  2待命 3 假删除 
	
	private String createTime;	// 创建时间
	
	private String empId;	// 对应员工SYS_EMPLOYEE ID
	
	private String parentId;	// 0:产品类型  否则:产品名称

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public CrmProduct(String id, String name, String number, Integer status,
                      String createTime, String empId, String parentId) {
		super();
		this.id = id;
		this.name = name;
		this.number = number;
		this.status = status;
		//this.createTime = createTime;
		this.empId = empId;
		this.parentId = parentId;
	}

	public CrmProduct(){
		super();
	}
}
