package com.zw.rule.mapper.customer;

import com.zw.rule.customer.po.Customer;
import com.zw.rule.mybatis.ParamFilter;

import java.util.List;
import java.util.Map;

public interface PerCustomerMapper {

    List<Map> getCustomers(ParamFilter paramFilter);

}