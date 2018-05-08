package com.zw.rule.mapper.customer;

import com.zw.rule.customer.po.MagCustomerContact;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * MagCustomerContactMapper数据库操作接口类
 * 
 **/

public interface MagCustomerContactMapper{


	/**
	 * 
	 * 查询（根据主键ID查询）
	 * 
	 **/
	MagCustomerContact  selectByPrimaryKey(@Param("id") String id);

	/**
	 *
	 * 查询（根据主键ID查询）
	 *
	 **/
	MagCustomerContact  selectByCustomerId(@Param("customerId") String id);

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
	int insert(MagCustomerContact record);

	/**
	 * 
	 * 添加 （匹配有值的字段）
	 * 
	 **/
	int insertSelective(MagCustomerContact record);

	/**
	 * 
	 * 修改 （匹配有值的字段）
	 * 
	 **/
	int updateByPrimaryKeySelective(MagCustomerContact record);

	/**
	 * 
	 * 修改（根据主键ID修改）
	 * 
	 **/
	int updateByPrimaryKey(MagCustomerContact record);

}