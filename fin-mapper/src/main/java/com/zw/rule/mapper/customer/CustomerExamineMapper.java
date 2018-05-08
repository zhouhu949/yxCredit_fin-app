package com.zw.rule.mapper.customer;

import com.zw.rule.customer.po.CustomerExamine;

import java.util.List;

public interface CustomerExamineMapper {
    int deleteByPrimaryKey(String id);

    int insert(CustomerExamine record);

    int insertSelective(CustomerExamine record);

    CustomerExamine selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CustomerExamine record);

    int updateByPrimaryKey(CustomerExamine record);

    List findByOrderId(String orderId);

    void updateByOrderId(CustomerExamine customerExamine);
}