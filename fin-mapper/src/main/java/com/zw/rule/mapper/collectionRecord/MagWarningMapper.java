package com.zw.rule.mapper.collectionRecord;

import com.zw.rule.collectionRecord.po.MagWarning;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * 
 * MagWarningMapper数据库操作接口类
 * 
 **/

public interface MagWarningMapper{


	/**
	 * 
	 * 查询（根据主键ID查询）
	 * 
	 **/
	MagWarning selectByPrimaryKey(@Param("id") Long id);

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
	int insert(MagWarning record);

	/**
	 * 
	 * 添加 （匹配有值的字段）
	 * 
	 **/
	int insertSelective(MagWarning record);

	/**
	 * 
	 * 修改 （匹配有值的字段）
	 * 
	 **/
	int updateByPrimaryKeySelective(MagWarning record);

	/**
	 * 
	 * 修改（根据主键ID修改）
	 * 
	 **/
	int updateByPrimaryKey(MagWarning record);

	MagWarning getWarningByLoanId(String loanId);
}