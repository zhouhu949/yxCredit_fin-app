package com.zw.rule.surety.service;

import com.zw.rule.approveRecord.po.ProcessApproveRecord;
import com.zw.rule.customer.po.Order;
import com.zw.rule.surety.Surety;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/1/5.
 */
public interface SuretyCheckService {
    //获取所有被担保的订单
    public List<Order> getAllSuretyOrders(Map map);
    //操作订单和担保人关联表，更改关联表状态(状态改为2担保成功)
    int changeSuretyOrderState(Map map);
    //审核担保人担保的订单通过 审核意见
    Boolean addApproveRecord(Map map);
    //拒绝
    Boolean addRefusedRecord(Map map);

    //查询担保后并且审核后的订单
    List<Order> getSuretyCheckOrders(Map map);

    //查询该订单的担保人
    Surety getOrderSurety(String relId);

    //查询审批意见根据
    ProcessApproveRecord getProById(String id);
}
