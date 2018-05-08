package com.zw.rule.mapper.orderLog;


import com.zw.rule.orderLog.po.MagOrderLogs;

import java.util.List;
import java.util.Map;

/**
 * 
 * MagOrderLogMapper数据库操作接口类
 * 
 **/

public interface OrderLogMapper {

	/**
	 * 
	 * 查询（根据主键ID查询）
	 * 
	 **/
	List<MagOrderLogs> getOrderLogList(Map map);

	/**
	 *
	 * 添加订单日志
	 *
	 **/
	int addOrderLogs(MagOrderLogs magOrderLogs);
}