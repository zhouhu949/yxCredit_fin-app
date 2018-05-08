package com.zw.rule.orderLog.service;

import com.zw.rule.customer.po.Order;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/21 0021.
 */
public interface OrderLogService {

    List selectGetOrderLogList(String orderId);
}
