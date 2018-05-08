package com.zw.rule.collectionRecord.po;
import java.io.Serializable;


/**
 * 
 * 
 * 
 **/
@SuppressWarnings("serial")
public class MagWarning implements Serializable {

	/****/
	private String id;

	/****/
	private String loanId;

	/****/
	private String dtcredit;

	/****/
	private String black;

	/****/
	private String addCredit;

	/****/
	private String liabilities;

	/****/
	private String changeAddress;



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

	public void setDtcredit(String dtcredit){
		this.dtcredit = dtcredit;
	}

	public String getDtcredit(){
		return this.dtcredit;
	}

	public void setBlack(String black){
		this.black = black;
	}

	public String getBlack(){
		return this.black;
	}

	public void setAddCredit(String addCredit){
		this.addCredit = addCredit;
	}

	public String getAddCredit(){
		return this.addCredit;
	}

	public void setLiabilities(String liabilities){
		this.liabilities = liabilities;
	}

	public String getLiabilities(){
		return this.liabilities;
	}

	public void setChangeAddress(String changeAddress){
		this.changeAddress = changeAddress;
	}

	public String getChangeAddress(){
		return this.changeAddress;
	}

}
