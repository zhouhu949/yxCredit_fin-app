package com.zw.rule.mapper.customer;

import com.zw.rule.customer.po.CustomerInvestigation;

import java.util.List;

public interface CustomerInvestigationMapper {
    int deleteByPrimaryKey(String id);

    int insert(CustomerInvestigation record);

    int insertSelective(CustomerInvestigation record);

    CustomerInvestigation selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CustomerInvestigation record);

    int updateByPrimaryKey(CustomerInvestigation record);

    int batchInsert(List list);

    List findByOrderId(String orderId);

    void deleteByOrderId(String orderId);
}