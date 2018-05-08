package com.zw.rule.mapper.customer;

import com.zw.rule.customer.po.MagCustomerLive;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * MagCustomerLiveMapper数据库操作接口类
 * 
 **/

public interface MagCustomerLiveMapper{


	/**
	 * 
	 * 查询（根据主键ID查询）
	 * 
	 **/
	MagCustomerLive  selectByPrimaryKey(@Param("id") String id);

	/**
	 *
	 * 查询（根据customerId查询）
	 *
	 **/
	MagCustomerLive  selectByCustomerId(@Param("customerId") String id);



	/**
	 * 
	 * 删除（根据主键ID删除）
	 * 
	 **/
	int deleteByPrimaryKey(@Param("id") String id);

	/**
	 * 
	 * 添加
	 * 
	 **/
	int insert(MagCustomerLive record);

	/**
	 * 
	 * 添加 （匹配有值的字段）
	 * 
	 **/
	int insertSelective(MagCustomerLive record);

	/**
	 * 
	 * 修改 （匹配有值的字段）
	 * 
	 **/
	int updateByPrimaryKeySelective(MagCustomerLive record);

	/**
	 * 
	 * 修改（根据主键ID修改）
	 * 
	 **/
	int updateByPrimaryKey(MagCustomerLive record);

}