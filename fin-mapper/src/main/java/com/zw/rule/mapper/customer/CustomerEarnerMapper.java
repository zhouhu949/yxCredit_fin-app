package com.zw.rule.mapper.customer;


import com.zw.rule.customer.po.CustomerEarner;

import java.util.List;

public interface CustomerEarnerMapper {
    int deleteByPrimaryKey(String id);

    int insert(CustomerEarner record);

    int insertSelective(CustomerEarner record);

    CustomerEarner selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CustomerEarner record);

    int updateByPrimaryKey(CustomerEarner record);

    CustomerEarner getCustomerEarner(String customerId);

    List<CustomerEarner> getCustomerEarnerList(String customerId);
}