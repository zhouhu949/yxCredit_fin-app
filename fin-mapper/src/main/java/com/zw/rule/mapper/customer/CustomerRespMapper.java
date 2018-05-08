package com.zw.rule.mapper.customer;

import com.zw.rule.customer.po.CustomerResp;

import java.util.List;

public interface CustomerRespMapper {
    int deleteByPrimaryKey(String id);

    int insert(CustomerResp record);

    int insertSelective(CustomerResp record);

    CustomerResp selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CustomerResp record);

    int updateByPrimaryKey(CustomerResp record);

    CustomerResp selectByName(String name);

    int batchInsert(List<CustomerResp> list);

    List findById(String orderId);

    void deleteByOrderId(String orderId);
}