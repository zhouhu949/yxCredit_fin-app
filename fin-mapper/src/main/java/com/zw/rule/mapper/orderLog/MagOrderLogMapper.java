package com.zw.rule.mapper.orderLog;

import com.zw.rule.orderLog.po.MagOrderLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 
 * MagOrderLogMapper数据库操作接口类
 * 
 **/

public interface MagOrderLogMapper{


	/**
	 * 
	 * 查询（根据主键ID查询）
	 * 
	 **/
	MagOrderLog selectByPrimaryKey(@Param("id") Long id);

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
	int insert(MagOrderLog record);

	/**
	 * 
	 * 添加 （匹配有值的字段）
	 * 
	 **/
	int insertSelective(MagOrderLog record);

	/**
	 * 
	 * 修改 （匹配有值的字段）
	 * 
	 **/
	int updateByPrimaryKeySelective(MagOrderLog record);

	/**
	 * 
	 * 修改（根据主键ID修改）
	 * 
	 **/
	int updateByPrimaryKey(MagOrderLog record);

	List selectGetOrderLogList(String orderId);
}