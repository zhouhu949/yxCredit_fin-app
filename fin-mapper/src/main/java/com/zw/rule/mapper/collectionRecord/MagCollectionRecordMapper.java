package com.zw.rule.mapper.collectionRecord;

import com.zw.rule.collection.po.Collection;
import com.zw.rule.collectionRecord.po.MagCollectionRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 
 * MagCollectionRecordMapper数据库操作接口类
 * 
 **/

public interface MagCollectionRecordMapper{


	/**
	 * 
	 * 查询（根据主键ID查询）
	 * 
	 **/
	MagCollectionRecord selectByPrimaryKey(@Param("id") String id);

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
	int insert(MagCollectionRecord record);

	/**
	 * 
	 * 添加 （匹配有值的字段）
	 * 
	 **/
	int insertSelective(MagCollectionRecord record);

	/**
	 * 
	 * 修改 （匹配有值的字段）
	 * 
	 **/
	int updateByPrimaryKeySelective(MagCollectionRecord record);

	/**
	 * 
	 * 修改（根据主键ID修改）
	 * 
	 **/
	int updateByPrimaryKey(MagCollectionRecord record);

	List getCollectionRecordByLoanId(Map map);

	/**
	 *
	 * 分配电催
	 *
	 **/
	int insertCollection(Collection collection);

	/**
	 *
	 * 分配外访
	 *
	 **/
	int updateCollectionExternal(Collection collection);

	/**
	 *
	 * 分配委外
	 *
	 **/
	int updateEntrusted(Collection collection);

	/**
	 *
	 * 获取催收记录
	 *
	 **/
	List getTelephoneList(Map map);

	/**
	 *
	 * 减免金额审批
	 *
	 **/
	List getApprovalList(Map map);

	/**
	 *
	 * 减免金额审批修改
	 *
	 **/
	int updateApproval(Map map);

	/**
	 *
	 * 减免金额审批修改
	 *
	 **/
	int updateApprovalHJD(Map map);
	/**
	 *
	 * 订单结清修改还款计划表
	 *
	 **/
	int updateRepayment(Map map);

	/**
	 *
	 * 结清修改催收记录表
	 *
	 **/
	int updateCollection(Map map);

	/**
	 *
	 * 添加减免审批
	 *
	 **/
	int addDerate(Map map);

	/**
	 *
	 * 添加减免审批HJD
	 *
	 **/
	int addDerateHJD(Map map);

	/**
	 *
	 * 修改减免审批
	 *
	 **/
	int updateDerate(Map map);
	/**
	 *
	 * 修改委托操作状态
	 *
	 **/
	int updateEntrustedState(Map map);
}