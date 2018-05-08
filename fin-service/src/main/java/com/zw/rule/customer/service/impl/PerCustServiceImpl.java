package com.zw.rule.customer.service.impl;

import com.zw.rule.approveRecord.po.ProcessApproveRecord;
import com.zw.rule.approveRecord.service.ProcessApproveRecordService;
import com.zw.rule.customer.service.PerCustService;
import com.zw.rule.mapper.customer.CustomerMapper;
import com.zw.rule.mapper.customer.OrderMapper;
import com.zw.rule.mapper.customer.PerCustomerMapper;
import com.zw.rule.mybatis.ParamFilter;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PerCustServiceImpl implements PerCustService{

    @Resource
    private PerCustomerMapper perCustomerMapper;

    @Resource
    private OrderMapper orderMapper;

    @Resource
    private ProcessApproveRecordService processApproveRecordService;

    @Override
    public List perCustList(ParamFilter paramFilter) {
        return perCustomerMapper.getCustomers(paramFilter);
    }

    @Override
    public List orderList(ParamFilter paramFilter) {
        return orderMapper.getOrders(paramFilter);
    }

    @Override
    public List getAuditList(ParamFilter paramFilter) {
        return processApproveRecordService.processApproveRecodList(paramFilter);
    }
}
