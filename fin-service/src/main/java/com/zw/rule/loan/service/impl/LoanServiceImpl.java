package com.zw.rule.loan.service.impl;

import com.zw.base.util.DateUtils;
import com.zw.rule.customer.po.Order;
import com.zw.rule.loan.po.Loan;
import com.zw.rule.loan.service.LoanService;
import com.zw.rule.mapper.customer.CustomerMapper;
import com.zw.rule.mapper.customer.OrderMapper;
import com.zw.rule.mapper.loan.LoanMapper;
import com.zw.rule.mapper.repayment.RepaymentMapper;
import com.zw.rule.mybatis.ParamFilter;
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
public class LoanServiceImpl implements LoanService {
    @Autowired
    private LoanMapper loanMapper;
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private RepaymentMapper repaymentMapper;

    @Override
    public boolean submitLoan(Map param) {
        String id = UUID.randomUUID().toString();
        param.put("id",id);
        return loanMapper.submitLoan(param);
    }

    @Override
    public List getLoanList(Map param) {
        return loanMapper.getLoanList(param);
    }

    @Override
    @Transactional
    public int reviewLoan(String loanId, String state,List list,String loanTime,String amount) {
        String updateTime = DateUtils.getCurrentTime(DateUtils.STYLE_5);
        Map map = new HashMap();
        map.put("loanId",loanId);
        map.put("state",state);
        map.put("loanTime",loanTime);
        map.put("updateTime",updateTime);
        int count = loanMapper.reviewLoan(map);

        //向放款明细表插入记录
        map.put("amount",amount);
        map.put("id",UUID.randomUUID().toString());
        loanMapper.addLoanDetail(map);
        //审核通过需要生成还款计划
        if(count > 0){
            //向repayment表中插入数据
            repaymentMapper.insertRepaymentList(list);
            //获取订单id
            Loan loan = loanMapper.getLoanById(loanId);
            Order order = new Order();
            order.setId(loan.getOrderId());
            order.setState("9");
            orderMapper.updateState(order);
        }
        return count;
    }

    @Override
    public Order getLoanDetailById(String orderId) {
        return orderMapper.selectByPrimaryKey(orderId);
    }

    @Override
    public Map getUserInfoByloanId(String loanId) {
        return loanMapper.getUserInfoByloanId(loanId);
    }

    @Override
    public List getAllList(String date) {
        return loanMapper.getAllList(date);
    }
}
