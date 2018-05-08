package com.zw.rule.loan.po;
import java.io.Serializable;

public class MagLoanDetail implements Serializable {

	/****/
	private String id;

	/**放款Id**/
	private String loanId;

	/**放款金额**/
	private String loanAmount;

	/**放款时间**/
	private String loanTime;

	/**排序**/
	private String sort;



	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return this.id;
	}

	public void setLoanId(String loanId){
		this.loanId = loanId;
	}

	public String getLoanId(){
		return this.loanId;
	}

	public void setLoanAmount(String loanAmount){
		this.loanAmount = loanAmount;
	}

	public String getLoanAmount(){
		return this.loanAmount;
	}

	public void setLoanTime(String loanTime){
		this.loanTime = loanTime;
	}

	public String getLoanTime(){
		return this.loanTime;
	}

	public void setSort(String sort){
		this.sort = sort;
	}

	public String getSort(){
		return this.sort;
	}

}
