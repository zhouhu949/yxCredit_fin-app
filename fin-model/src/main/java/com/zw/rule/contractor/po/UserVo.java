package com.zw.rule.contractor.po;


import com.zw.rule.base.BaseEntity;

import java.util.Date;

public class UserVo extends BaseEntity {
	
	private static final long serialVersionUID = 3428451338994126059L;

	private String userId;

	private String account;

	private String tel;

	private String nickName;

	private int status;

	private boolean isBindUser ;//是否绑定总包商

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public boolean getIsBindUser() {
		return isBindUser;
	}

	public void setIsBindUser(boolean isBindUser) {
		this.isBindUser = isBindUser;
	}

	@Override
	public String toString() {
		return "User{" +
				"account='" + account + '\'' +
				", status=" + status +
				'}';
	}
}
