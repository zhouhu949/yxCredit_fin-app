package com.zw.rule.mapper.customer;

import com.zw.rule.customer.po.CustomerRenovation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CustomerRenovationMapper {
    int insert(CustomerRenovation record);

    int insertSelective(CustomerRenovation record);

    //客户装修信息
    List<CustomerRenovation> getCustomerRenovation(@Param("customerId")String customerId);
}