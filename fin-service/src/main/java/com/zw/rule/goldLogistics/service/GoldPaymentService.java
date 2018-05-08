package com.zw.rule.goldLogistics.service;

import com.zw.rule.mybatis.ParamFilter;
import com.zw.rule.transaction.po.TransactionDetails;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/1/3.
 */
public interface GoldPaymentService {
    //付款查询
    List queryOrders(ParamFilter paramFilter)throws Exception;
    //扣款操作
    String costDebit(Map map);
    //扣款明细
    List<TransactionDetails> transactionDetailsList(Map map);
}
