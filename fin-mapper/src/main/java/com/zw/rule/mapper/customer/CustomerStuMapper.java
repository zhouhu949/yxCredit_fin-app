package com.zw.rule.mapper.customer;

import com.zw.rule.customer.po.CustomerStu;

import java.util.List;

/**
 * Created by Administrator on 2017/7/11 0011.
 */
public interface CustomerStuMapper {
    CustomerStu getCustomerStu(String customerId);
    List<CustomerStu> getCustomerStuList(String customerId);
}
