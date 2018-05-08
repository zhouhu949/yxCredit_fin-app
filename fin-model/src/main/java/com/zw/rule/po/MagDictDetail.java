package com.zw.rule.po;


import com.zw.rule.base.BaseEntity;

import java.util.Date;

public class MagDictDetail extends BaseEntity {
	
	private String id;
	
	private String dictId;
	
	private String dictName;
	
	private String code;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDictId() {
		return dictId;
	}

	public void setDictId(String dictId) {
		this.dictId = dictId;
	}

	public String getDictName() {
		return dictName;
	}

	public void setDictName(String dictName) {
		this.dictName = dictName;
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

	@Override
	public Date getCreateTime() {
		return createTime;
	}

	@Override
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getAlterTime() {
		return alterTime;
	}

	public void setAlterTime(Date alterTime) {
		this.alterTime = alterTime;
	}

	private String name;
	
	private String description;
	
	private String state;

	private Date createTime;

	private Date alterTime;


	@Override
	public String toString() {
		return "Dict{" +
				" description='" + description + '\'' +
				", code='" + code + '\'' +
				", name='" + name + '\'' +
				'}';
	}
	
}
