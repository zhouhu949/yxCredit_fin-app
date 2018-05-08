package com.zw.rule.mapper.customer;

import com.zw.rule.customer.po.AppMessage;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * 
 * AppMessageMapper数据库操作接口类
 * 
 **/

public interface AppMessageMapper{


	/**
	 * 
	 * 查询（根据主键ID查询）
	 * 
	 **/
	AppMessage selectByPrimaryKey(@Param("id") String id);

	/**
	 * 
	 * 删除（根据主键ID删除）
	 * 
	 **/
	int deleteByPrimaryKey(@Param("id") Long id);

	/**
	 * 
	 * 添加
	 * 
	 **/
	int insert(AppMessage record);

	/**
	 * 
	 * 添加 （匹配有值的字段）
	 * 
	 **/
	int insertSelective(AppMessage record);

	/**
	 * 
	 * 修改 （匹配有值的字段）
	 * 
	 **/
	int updateByPrimaryKeySelective(AppMessage record);

	/**
	 * 
	 * 修改（根据主键ID修改）
	 * 
	 **/
	int updateByPrimaryKey(AppMessage record);

	void updateState(Map map);

	String getUnMessage(String userId);

}