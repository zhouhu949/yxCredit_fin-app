package com.zw.rule.mapper.customer;

import com.zw.rule.customer.po.CustomerJob;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CustomerJobMapper {
    int deleteByPrimaryKey(String id);

    int insert(CustomerJob record);

    int insertSelective(CustomerJob record);

    CustomerJob selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CustomerJob record);

    int updateByPrimaryKey(CustomerJob record);

    //客户工作信息
    List<CustomerJob> getCustomerJob(@Param("customerId")String customerId);
}