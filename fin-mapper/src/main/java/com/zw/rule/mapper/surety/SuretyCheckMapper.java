package com.zw.rule.mapper.surety;

import com.zw.rule.approveRecord.po.ProcessApproveRecord;
import com.zw.rule.customer.po.Order;
import com.zw.rule.surety.Surety;

import java.util.*;

/**
 * Created by Administrator on 2018/1/5.
 */
public interface SuretyCheckMapper {
    //查询所有被担保人担保的订单
    List<Order> selectAllSuretyOrder(Map map);
    //操作订单和担保人关联表，更改关联表状态(状态改为2担保成功)
    int updateSuretyOrderState(Map map);
    //记录更改意见
    int insertSelective(ProcessApproveRecord processApproveRecord);
    //查询所有被担保的订单审核后的订单(包括通过和未通过的)--><!--担保人管理-》担保订单按钮
    List<Order> selectAllSuretyCheckOrderShow(Map map);
    //查询担保人信息
    Surety getSuretyInformation(String relId);
    //查询审批意见实体
    ProcessApproveRecord selectProById(String proId);
}
