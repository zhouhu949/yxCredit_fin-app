package com.zw.rule.mapper.customer;

import com.zw.rule.customer.po.AppUser;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * AppUserMapper数据库操作接口类
 * 
 **/

public interface AppUserMapper{


	/**
	 * 
	 * 查询（根据主键ID查询）
	 * 
	 **/
	AppUser selectByPrimaryKey(@Param("id") String id);

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
	int insert(AppUser record);

	/**
	 * 
	 * 添加 （匹配有值的字段）
	 * 
	 **/
	int insertSelective(AppUser record);

	/**
	 * 
	 * 修改 （匹配有值的字段）
	 * 
	 **/
	int updateByPrimaryKeySelective(AppUser record);

	/**
	 * 
	 * 修改（根据主键ID修改）
	 * 
	 **/
	int updateByPrimaryKey(AppUser record);

}