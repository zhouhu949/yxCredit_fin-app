package com.zw.rule.customer.service;

import com.zw.rule.customer.po.*;
import com.zw.rule.mybatis.ParamFilter;

import java.util.List;

public interface PerCustService {

    //准客户的所有列表
    List perCustList(ParamFilter paramFilter);

    //准客户对应的订单列表
    List orderList(ParamFilter paramFilter);

    //查询订单对应的审批流程
    List getAuditList(ParamFilter paramFilter);
}
