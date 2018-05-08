package com.zw.rule.mapper.customer;

import com.zw.rule.customer.po.CustomerRetire;

import java.util.List;

/**
 * Created by Administrator on 2017/7/11 0011.
 */
public interface CustomerRetireMapper {
    CustomerRetire getCustomerRetire(String customerId);
    List<CustomerRetire> getCustomerRetireList(String customerId);
}
