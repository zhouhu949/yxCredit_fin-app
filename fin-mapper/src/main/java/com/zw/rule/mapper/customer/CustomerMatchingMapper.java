package com.zw.rule.mapper.customer;

import com.zw.rule.customer.po.CustomerMatching;

import java.util.List;

public interface CustomerMatchingMapper {
    int deleteByPrimaryKey(String id);

    int insert(CustomerMatching record);

    int insertSelective(CustomerMatching record);

    CustomerMatching selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CustomerMatching record);

    int updateByPrimaryKey(CustomerMatching record);

    List<CustomerMatching> getMatchingPrice(CustomerMatching customerMatching);
}