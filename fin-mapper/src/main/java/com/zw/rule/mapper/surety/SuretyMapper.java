package com.zw.rule.mapper.surety;

import com.zw.rule.customer.po.Order;
import com.zw.rule.surety.Surety;
import com.zw.rule.surety.SuretyRelOrder;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/12/12.
 */
public interface SuretyMapper {
    //查询所有的担保人列表
    public List<Surety> getAllSuretyList(Map map);
    //添加担保人方法
    public int insertOneSurety(Surety surety);
    //查询担保人担保的订单
    public List<Order> selectSuretyOrders(Map map);
    //查询可以被担保人担保过的订单 未被担保过且审核未通过的(state=3)
    public List<Order> selectCanBeSuretyOrders(Map map);
    //担保人担保订单数据层方法
    public int suretyGotoAssureOrder(SuretyRelOrder suretyRelOrder);
}
