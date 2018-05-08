package com.zw.rule.financial.service.impl;

import com.zw.rule.customer.po.Order;
import com.zw.rule.financial.service.FinancialService;
import com.zw.rule.investor.po.Investor;
import com.zw.rule.mapper.investor.InvestorMapper;
import com.zw.rule.mapper.customer.CustomerMapper;
import com.zw.rule.mapper.customer.OrderMapper;
import com.zw.rule.mapper.product.ICrmProductMapper;
import com.zw.rule.mybatis.ParamFilter;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/7/12.
 */
@Service
public class FinancialServiceImpl implements FinancialService {

    @Resource
    private OrderMapper orderMapper;

    @Resource
    private CustomerMapper customerMapper;

    @Resource
    private ICrmProductMapper iCrmProductMapper;

    @Resource
    private InvestorMapper investorMapper;

    //获取客户对应的集合
    @Override
    public List<Order> getFinancialOrderList(ParamFilter queryFilter){
        return orderMapper.getFinancialOrderList(queryFilter);
    }

    //获取客户列表 待匹配、筹资中
    @Override
    public List getFinancialCustomerList(ParamFilter paramFilter) {
        List customers = customerMapper.getFinancialCustomerList(paramFilter);
        return customers;
    }

    //获取客户对应的集合
    @Override
    public Order getOrder(String id) {
        return orderMapper.selectByPrimaryKey(id);
    }

    //获取产品分期信息集合
    @Override
    public List<Map> getProductList(Map map) {
        return iCrmProductMapper.getProductList(map);
    }

    //修改对应订单信息
    @Override
    public boolean updateOrderStatus(Map map){
        if (orderMapper.updateOrderStatus(map)>0) {
            return true;
        }else {
            return  false;
        }
    }

    //获取理财客户的集合
    @Override
    public List<Investor> getInvestorList() {
        return investorMapper.selectInvestorList();
    }
}
