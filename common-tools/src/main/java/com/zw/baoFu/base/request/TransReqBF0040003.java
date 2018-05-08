package com.zw.baoFu.base.request;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 代付交易退款查证
 * 
 * @author Administrator
 * 
 */
@XStreamAlias("trans_reqData")
public class TransReqBF0040003 {

	private String trans_btime;// 宝付批次号
	private String trans_etime;// 商户订单号

	public String getTrans_btime() {
		return trans_btime;
	}

	public void setTrans_btime(String trans_btime) {
		this.trans_btime = trans_btime;
	}

	public String getTrans_etime() {
		return trans_etime;
	}

	public void setTrans_etime(String trans_etime) {
		this.trans_etime = trans_etime;
	}

}
