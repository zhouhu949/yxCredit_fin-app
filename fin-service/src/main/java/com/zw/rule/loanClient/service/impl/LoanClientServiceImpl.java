package com.zw.rule.loanClient.service.impl;

import com.zw.base.util.DateUtils;
import com.zw.rule.customer.po.Order;
import com.zw.rule.loanClient.service.LoanClientService;
import com.zw.rule.mapper.customer.CustomerMapper;
import com.zw.rule.mapper.customer.MagCustomerAccountMapper;
import com.zw.rule.mapper.customer.OrderMapper;
import com.zw.rule.mapper.product.FeeMapper;
import com.zw.rule.mapper.repayment.RepaymentMapper;
import com.zw.rule.product.Fee;
import com.zw.rule.repayment.po.Repayment;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/7/12 0012.
 */
@Service
public class LoanClientServiceImpl implements LoanClientService{
    @Resource
    private CustomerMapper customerMapper;
    @Resource
    private OrderMapper orderMapper;
    @Resource
    private MagCustomerAccountMapper magCustomerAccountMapper;
    @Resource
    private RepaymentMapper repaymentMapper;
    @Resource
    private FeeMapper feeMapper;
    @Override
    public List getCustomerList(Map map) {
        return customerMapper.getCustomerList(map);
    }

    @Override
    public List<Map> getCustOrderList(Map map) {
        return orderMapper.getCustOrderList(map);
    }

    @Override
    public Boolean updateOrderState(Map map) {
        String alertTime = DateUtils.getCurrentTime(DateUtils.STYLE_5);
        Order order = new Order();
        order.setId(map.get("id").toString());
        order.setState(map.get("state").toString());
        order.setAlterTime(alertTime);
        int n = orderMapper.updateByPrimaryKeySelective(order);
        return n>0;
    }

    @Override
    public List getCustBankCardInfo(String customerId) {
        return magCustomerAccountMapper.getCustBankCardInfo(customerId);
    }

    @Override
    public Boolean inserRepayPlan(Repayment repayment) {
        return repaymentMapper.insertRepayment(repayment);
    }

    @Override
    public Fee getFeeByProductName(Map map) {
        return feeMapper.getFeeByProductName(map);
    }
}
