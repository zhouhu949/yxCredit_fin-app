package com.zw.rule.orderOperationRecord.service;

import com.zw.rule.approveRecord.po.OrderOperationRecord;
import com.zw.rule.mybatis.ParamFilter;

import java.util.List;
import java.util.Map;

/**
 * 订单操作记录Service接口
 * @author 仙海峰
 */
public interface OrderOperationRecordService {

    /**
     * 根据订单ID获取审核信息
     * @author 仙海峰
     * @param orderId
     * @return
     */
    Map getOrderOperationRecordByOrderId(String orderId);

    /**
     * 根据订单ID获取放款信息
     * @author 仙海峰
     * @param orderId
     * @return
     */
    Map getLoanRecordByOrderId(String orderId);

    /**
     *获取订单操作流程记录信息
     * @param queryFilter
     * @return
     */
    List<OrderOperationRecord> getOrderOperationRecordByOrderIdList(ParamFilter queryFilter);
}
