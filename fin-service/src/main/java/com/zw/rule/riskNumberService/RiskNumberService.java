package com.zw.rule.riskNumberService;

import com.zw.rule.customer.po.Order;

import java.util.List;
import java.util.Map;

/**
 * Created by zoukaixuan on 2018/1/16.
 * 商品串号码服务层接口
 */
public interface RiskNumberService {
    List<Order> getOrderRiskStringNumberList(Map map);
}
