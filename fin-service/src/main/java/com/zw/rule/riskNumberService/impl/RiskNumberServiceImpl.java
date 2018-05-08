package com.zw.rule.riskNumberService.impl;

import com.zw.rule.customer.po.Order;
import com.zw.rule.mapper.orderRiskStringNumberMapper.RiskNumberMapper;
import com.zw.rule.riskNumberService.RiskNumberService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by zoukaixuan on 2018/1/16.
 * 商品串号码service层方法
 */
@Service
public class RiskNumberServiceImpl implements RiskNumberService{
    //商品串号码订单查询对象
    @Resource
    private RiskNumberMapper mapper;

    /**
     * 获取商品串号码订单列表信息
     * @param map
     * @return
     */
    public List<Order> getOrderRiskStringNumberList(Map map){
        return mapper.getSubmitList(map);
    }
}
