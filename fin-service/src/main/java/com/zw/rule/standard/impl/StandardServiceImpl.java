package com.zw.rule.standard.impl;
import com.zw.rule.customer.po.Order;
import com.zw.rule.mapper.customer.OrderMapper;
import com.zw.rule.mybatis.ParamFilter;
import com.zw.rule.standard.StandardService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2017/7/11.
 */
@Service
public class StandardServiceImpl  implements StandardService {

    @Resource
    private OrderMapper orderMapper;

    @Override
    public List<Order> getOrdersList(ParamFilter paramFilter){
        return orderMapper.getStandardOrders(paramFilter);
    }

    //修改订单状态
    public boolean updateState(ParamFilter queryFilter) {
        boolean fg=false;
        Order order =new Order();
        order.setId(queryFilter.getParam().get("orderId").toString());
        order.setState(queryFilter.getParam().get("state").toString());
        if (orderMapper.updateState(order)>0){
            fg= true;
        }
        return fg;
    }
}
