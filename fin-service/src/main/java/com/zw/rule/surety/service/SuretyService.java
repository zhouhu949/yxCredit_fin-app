package com.zw.rule.surety.service;

import com.zw.rule.customer.po.Order;
import com.zw.rule.surety.Surety;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/12/12.
 * 担保人服务层接口
 */
public interface SuretyService {
    //获取全部的担保人信息
    public List<Surety> getAllSureList(Map map);
    //添加担保人信息
    public int addOneSurety(Surety surety);
    //查询担保人担保的订单
    public List<Order> getSuretyOrders(Map map);
    //查询可以被担保人担保过的订单 未被担保过且审核未通过的(state=3)
    public List<Order> getCanBeSuretyOrders(Map map);
    //担保人担保订单
    public int suretyGoAssureOrder(Map map);
    //插入订单日志
    int addOrderAssureLogs(Map map);
}
