package com.zw.rule.repayment.service;


import com.alibaba.fastjson.JSONObject;
import com.zw.rule.transaction.po.TransactionDetails;

import java.util.List;
import java.util.Map;


public interface RepaymentService {

    /**
     *
     * */
    public List getRepaymentList(Map map);

    /**
     *商品贷还款计划
     * */
    public List getRepaymentListSP(Map map);
    /**
     * 还款之前的检测
     * @author wangmin
     * date 2017-7-5
     * @param paramsJson 参数列表
     * @param
     * @return
     */
    public JSONObject payMoneyPreCheck(JSONObject paramsJson,String type);

    public Boolean updtaeRepayByorderId(Map map);//更新还款计划状态

    //获取计划表扣款明细集合
    List<Map> getPaymentRepaymentLis(Map map);

    //扣款明细
    List<TransactionDetails> transactionDetailsList(Map map);

    //扣款操作
    String costDebit(Map map);

}

