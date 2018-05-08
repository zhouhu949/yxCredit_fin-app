package com.zw.rule.mapper.customer;

import com.zw.rule.customer.po.CustomerProperty;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CustomerPropertyMapper {
    int deleteByPrimaryKey(String id);

    int insert(CustomerProperty record);

    int insertSelective(CustomerProperty record);

    CustomerProperty selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CustomerProperty record);

    int updateByPrimaryKey(CustomerProperty record);

    //客户房产信息
    List<CustomerProperty> getCustomerProperty(@Param("customerId")String customerId);
}