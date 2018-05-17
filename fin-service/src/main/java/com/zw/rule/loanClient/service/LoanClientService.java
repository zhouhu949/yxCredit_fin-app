package com.zw.rule.loanClient.service;



import com.zw.rule.customer.po.Order;
import com.zw.rule.product.Fee;
import com.zw.rule.repayment.po.Repayment;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/7/12 0012.
 */
public interface LoanClientService {

    //获取客户列表
    List getCustomerList(Map map);
    List<Map> getCustOrderList(Map map);
    Boolean updateOrderState(Map map);
    List getCustBankCardInfo(String customerId);
    Boolean inserRepayPlan(Repayment repayment);
    Fee getFeeByProductName(Map map);//查询费率

    /**
     * 根据客户ID查询绑定银行卡信息
     * @author 仙海峰
     * @param customerId
     * @return
     */
    Map getCustBankCardInfoByCustId(String customerId);



}
