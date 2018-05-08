package com.zw.rule.mapper.customer;

import com.zw.rule.customer.po.CustomerExpend;

import java.util.List;

/**
 * Created by Administrator on 2017/7/12 0012.
 */
public interface CustomerExpendMapper {
    List<CustomerExpend> getCustomerExpend(String customerId);
}
