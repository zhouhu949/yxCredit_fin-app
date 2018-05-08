package com.zw.rule.mapper.transaction;

import com.zw.rule.transaction.po.TransactionDetails;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/12/9.
 */
public interface TransactionDetailsMapper {
    int insert(TransactionDetails transactionDetails);
    int updateTransactionDetail(TransactionDetails transactionDetails);
    TransactionDetails getTransactionDetail(Map map);
    TransactionDetails getTransactionLatest(Map map);
    List<TransactionDetails> getTransactionList(Map map);
}
