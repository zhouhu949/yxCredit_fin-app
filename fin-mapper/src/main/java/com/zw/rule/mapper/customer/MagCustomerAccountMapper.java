package com.zw.rule.mapper.customer;

import com.zw.rule.customer.po.MagCustomerAccount;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 *
 * MagCustomerAccountMapper数据库操作接口类
 *
 **/

public interface MagCustomerAccountMapper{


	/**
	 *
	 * 查询（根据主键ID查询）
	 *
	 **/
	MagCustomerAccount selectByPrimaryKey(@Param("id") Long id);

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
	int insert(MagCustomerAccount record);

	/**
	 *
	 * 添加 （匹配有值的字段）
	 *
	 **/
	int insertSelective(MagCustomerAccount record);

	/**
	 *
	 * 修改 （匹配有值的字段）
	 *
	 **/
	int updateByPrimaryKeySelective(MagCustomerAccount record);

	/**
	 *
	 * 修改（根据主键ID修改）
	 *
	 **/
	int updateByPrimaryKey(MagCustomerAccount record);

	MagCustomerAccount getAccountInfoByOrderId(String orderId);

	List getCustBankCardInfo(String customerId);

	/**
	 * 根据客户ID查询绑定银行卡信息
	 * @param customerId
	 * @return
	 */
	Map getCustBankCardInfoByCustId(String customerId);
}