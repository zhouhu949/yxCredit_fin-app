package com.zw.rule.loan.service;

import com.zw.rule.customer.po.Order;
import com.zw.rule.mybatis.ParamFilter;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/21 0021.
 */
public interface LoanService {
    boolean submitLoan(Map param);

    //获取放款列表
    List getLoanList(Map param);
    //审核
    int reviewLoan(String loanId,String state,List list,String loanTime,String amount);

    Order getLoanDetailById(String orderId);

    Map getUserInfoByloanId(String loanId);

    List getAllList(String date);
}
