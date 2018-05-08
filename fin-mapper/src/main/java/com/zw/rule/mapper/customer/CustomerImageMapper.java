package com.zw.rule.mapper.customer;

import com.zw.rule.customer.po.CustomerImage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CustomerImageMapper {
    int deleteByPrimaryKey(String id);

    int deleteByCustomerId(String customerId);

    int insert(CustomerImage record);

    int insertSelective(CustomerImage record);

    CustomerImage selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CustomerImage record);

    int updateByPrimaryKey(CustomerImage record);

    //客户图片资料
    List<CustomerImage> getCustomerImage(@Param("customerId")String customerId);

    //订单图片
    List<CustomerImage> getCustomerImageOrderId(String orderId);


    int batchInsert(List list);

    List findByOrderId(String orderId);

    List<CustomerImage> getOrderImage(String orderId);

    int deleteByOrderIdAndType(@Param("orderId") String orderId,@Param("type")String type);

}