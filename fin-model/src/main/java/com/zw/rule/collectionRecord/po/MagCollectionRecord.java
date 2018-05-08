package com.zw.rule.collectionRecord.po;
import java.io.Serializable;

public class MagCollectionRecord implements Serializable {

	/**催收记录Id**/
	private String id;

	/**拨打电话**/
	private String tel;

	/**与客关系**/
	private String relationship;

	/**催收备注**/
	private String remark;

	/**放款Id**/
	private String loanId;

	/**创建时间**/
	private String createTime;

	/**拨打时间**/
	private String callTime;

	/**操作人Id**/
	private String createUserId;

	/**操作人名称**/
	private String createUserName;



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

	public void setRelationship(String relationship){
		this.relationship = relationship;
	}

	public String getRelationship(){
		return this.relationship;
	}

	public void setRemark(String remark){
		this.remark = remark;
	}

	public String getRemark(){
		return this.remark;
	}

	public void setLoanId(String loanId){
		this.loanId = loanId;
	}

	public String getLoanId(){
		return this.loanId;
	}

	public void setCreateTime(String createTime){
		this.createTime = createTime;
	}

	public String getCreateTime(){
		return this.createTime;
	}

	public void setCallTime(String callTime){
		this.callTime = callTime;
	}

	public String getCallTime(){
		return this.callTime;
	}

	public void setCreateUserId(String createUserId){
		this.createUserId = createUserId;
	}

	public String getCreateUserId(){
		return this.createUserId;
	}

	public void setCreateUserName(String createUserName){
		this.createUserName = createUserName;
	}

	public String getCreateUserName(){
		return this.createUserName;
	}

}
