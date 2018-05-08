package com.zw.rule.mapper.customer;

import com.zw.rule.customer.po.CustomerLinkman;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CustomerLinkmanMapper {
    int insert(CustomerLinkman record);

    int insertSelective(CustomerLinkman record);

    //客户联系人详情
    List<CustomerLinkman> getCustomerLinkMan(@Param("customerId")String customerId);
}