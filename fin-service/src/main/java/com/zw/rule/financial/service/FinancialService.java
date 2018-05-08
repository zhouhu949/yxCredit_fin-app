package com.zw.rule.financial.service;

import com.zw.rule.customer.po.Order;
import com.zw.rule.investor.po.Investor;
import com.zw.rule.mybatis.ParamFilter;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/7/12.
 */
public interface FinancialService {

    //获取客户对应的集合
    List<Order> getFinancialOrderList(ParamFilter queryFilter);

    //获取客户列表 待匹配、筹资中
    List getFinancialCustomerList(ParamFilter paramFilter);

    //获取客户对应的集合
    Order getOrder(String id);

    //获取客户对应的集合
    List<Map> getProductList(Map map);

    //修改对应订单信息
    boolean updateOrderStatus(Map map);

    //获取理财客户的集合
    List<Investor> getInvestorList();
}
