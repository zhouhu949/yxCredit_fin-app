package com.zw.rule.orderLog.service.impl;

import com.zw.rule.customer.po.Order;
import com.zw.rule.loan.po.Loan;
import com.zw.rule.loan.service.LoanService;
import com.zw.rule.mapper.customer.CustomerMapper;
import com.zw.rule.mapper.customer.OrderMapper;
import com.zw.rule.mapper.loan.LoanMapper;
import com.zw.rule.mapper.orderLog.MagOrderLogMapper;
import com.zw.rule.mapper.repayment.RepaymentMapper;
import com.zw.rule.orderLog.service.OrderLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Administrator on 2017/6/21 0021.
 */
@Service
public class OrderLogServiceImpl implements OrderLogService {

    @Autowired
    private MagOrderLogMapper magOrderLogMapper;

    @Override
    public List selectGetOrderLogList(String orderId) {
        return magOrderLogMapper.selectGetOrderLogList(orderId);
    }
}
