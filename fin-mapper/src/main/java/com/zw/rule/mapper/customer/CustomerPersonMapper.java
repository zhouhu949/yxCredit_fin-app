package com.zw.rule.mapper.customer;

import com.zw.rule.customer.po.CustomerPerson;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface CustomerPersonMapper {
    int deleteByPrimaryKey(String id);

    int insert(CustomerPerson record);

    int insertSelective(CustomerPerson record);

    CustomerPerson selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CustomerPerson record);

    int updateByPrimaryKey(CustomerPerson record);

    //客户基本信息
    List<Map> getCustomerPerson(@Param("customerId")String customerId);
}