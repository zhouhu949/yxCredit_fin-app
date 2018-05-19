package com.zw.rule.orderOperationRecord.service.impl;

import com.zw.rule.mapper.orderOperationRecordMapper.OrderOperationRecordMapper;
import com.zw.rule.mybatis.ParamFilter;
import com.zw.rule.orderOperationRecord.service.OrderOperationRecordService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


/**
 * 订单操作记录Service实现类
 * @author 仙海峰
 */
@Service
public class OrderOperationRecordServiceImpl implements OrderOperationRecordService {

    private static final Logger logger = Logger.getLogger(OrderOperationRecordServiceImpl.class);

    @Autowired
    private OrderOperationRecordMapper orderOperationRecordMapper;

    /**
     * 根据订单ID获取审核信息
     * @author 仙海峰
     * @param  orderId
     * @return
     */
    @Override
    public Map getOrderOperationRecordByOrderId(String orderId) {
        return orderOperationRecordMapper.getOrderOperationRecordByOrderId(orderId);
    }


    /**
     * 根据订单ID获取放款信息
     * @author 仙海峰
     * @param orderId
     * @return
     */
    @Override
    public Map getLoanRecordByOrderId(String orderId) {
        return orderOperationRecordMapper.getLoanRecordByOrderId(orderId);
    }


    /**
     *获取订单操作流程记录信息列表
     * @param queryFilter
     * @return
     */
    @Override
    public List<Map> getOrderOperationRecordByOrderIdList(ParamFilter queryFilter) {
        return orderOperationRecordMapper.getOrderOperationRecordByOrderIdList(queryFilter.getParam());
    }
}
