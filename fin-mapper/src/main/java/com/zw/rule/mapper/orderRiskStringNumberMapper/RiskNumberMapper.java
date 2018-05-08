package com.zw.rule.mapper.orderRiskStringNumberMapper;

import com.zw.rule.customer.po.Order;

import java.util.List;
import java.util.Map;

/**
 * Created by zoukaixuan on 2018/1/16.
 * 商品串号码持久层接口
 */
public interface RiskNumberMapper {
    List<Order> getSubmitList(Map map);
}
