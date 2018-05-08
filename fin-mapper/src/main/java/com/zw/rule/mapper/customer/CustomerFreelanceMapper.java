package com.zw.rule.mapper.customer;

import com.zw.rule.customer.po.CustomerFreelance;


import java.util.List;

/**
 * Created by Administrator on 2017/7/11 0011.
 */
public interface CustomerFreelanceMapper {
    CustomerFreelance getCustomerFreelance(String customerId);

    List<CustomerFreelance> getCustomerFreelanceList(String customerId);
}
