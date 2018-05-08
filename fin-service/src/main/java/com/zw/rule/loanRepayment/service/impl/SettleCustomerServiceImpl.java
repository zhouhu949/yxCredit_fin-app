package com.zw.rule.loanRepayment.service.impl;

import com.zw.rule.customer.po.CustomerImage;
import com.zw.rule.customer.po.Order;
import com.zw.rule.loanRepayment.service.SettleCustomerService;
import com.zw.rule.mapper.customer.CustomerImageMapper;
import com.zw.rule.mapper.customer.CustomerMapper;
import com.zw.rule.mapper.customer.OrderMapper;
import com.zw.rule.mapper.orderLog.OrderLogMapper;
import com.zw.rule.mapper.repayment.RepaymentMapper;
import com.zw.rule.mybatis.ParamFilter;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/7/11.
 */
@Service
public class SettleCustomerServiceImpl implements SettleCustomerService{
    @Resource
    private OrderMapper orderMapper;
    @Resource
    private OrderLogMapper orderLogMapper;
    @Resource
    private RepaymentMapper repaymentMapper;
    @Resource
    private CustomerImageMapper customerImageMapper;
    @Resource
    private CustomerMapper customerMapper;
    @Override
    public List<Order> getRepayCustomerList(ParamFilter queryFilter){
        return orderMapper.getRepayCustomerList(queryFilter);
    }
    public  List getLoanRepaymentList(Map map){
        return repaymentMapper.getLoanRepaymentList(map);
    }
    public Boolean credentialUpload(CustomerImage customerImage){
        int num = customerImageMapper.insertSelective(customerImage);
        return num>0;
    }

    public List getCustomerListByState(Map map){
        List list =customerMapper.getCustomerByState(map);
        return list;
    }

    public List getOrderLogList(ParamFilter queryFilter){
        return orderLogMapper.getOrderLogList(queryFilter.getParam());
    }
}
