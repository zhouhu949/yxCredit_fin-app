package com.zw.rule.standard;
import com.zw.rule.customer.po.Order;
import com.zw.rule.mybatis.ParamFilter;
import java.util.List;
/**
 * Created by Administrator on 2017/7/11.
 */

public interface StandardService {

    //获取合规审查列表
    List<Order> getOrdersList(ParamFilter queryFilter) ;

    //修改订单状态
    boolean updateState(ParamFilter queryFilter);
}
