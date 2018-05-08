package com.zw.rule.goldLogistics.service;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/12/22.
 */
public interface GoldLogisticsService {

    //获取客户发货信息
    List getlogisticsByOrderId(Map map);

    //添加客户发货信息
    int addLogistics(Map map);

    //修改客户发货信息
    int updateLogistics(Map map);

    //修改发货状态
    int updateOrderDelivery(Map map);
}
