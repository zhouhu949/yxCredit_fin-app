package com.zw.rule.po;


import com.zw.rule.base.BaseEntity;

import java.util.Date;

public class MagDict extends BaseEntity {
	
	private String id;

	private String description;

	private String state;
	
	private Date alterTime;
	
	private Date createTime;
	
	private String code;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Date getAlterTime() {
		return alterTime;
	}

	public void setAlterTime(Date alterTime) {
		this.alterTime = alterTime;
	}

	@Override
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	private String name;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Dict{" +
				" description='" + description + '\'' +
				", code='" + code + '\'' +
				", name='" + name + '\'' +
				'}';
	}
	
}
