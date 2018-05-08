package com.zw.rule.mapper.customer;

import com.zw.rule.customer.po.CustomerManager;

import java.util.List;

/**
 * Created by Administrator on 2017/7/11 0011.
 */
public interface CustomerManagerMapper {
    CustomerManager getCustomerManager(String customerId);
    List<CustomerManager> getCustomerManagerList(String customerId);

}
