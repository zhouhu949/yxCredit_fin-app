package com.zw.rule.mapper.goldLogistics;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/12/22.
 */
public  interface GoldLogisticsMapper {
    //获取客户发货信息
    List getlogisticsByOrderId(Map map);

    //添加客户发货信息
    int addLogistics(Map map);

    //修改客户发货信息
    int updateLogistics(Map map);

    //更新订单采购信息
    int updateOrderGold(Map map);

    //更新订单信息
    int updateOrder(Map map);
}
