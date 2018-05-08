package com.zw.rule.loanRepayment.service;

import com.zw.rule.customer.po.CustomerImage;
import com.zw.rule.customer.po.Order;
import com.zw.rule.mybatis.ParamFilter;
import com.zw.rule.orderLog.po.MagOrderLogs;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/7/11.
 */
public interface SettleCustomerService {
    //获取满足放款信息的数据
    List<Order> getRepayCustomerList(ParamFilter queryFilter);
   //获取还款计划
    List getLoanRepaymentList(Map map);
    //
    Boolean credentialUpload(CustomerImage customerImage);

    List getCustomerListByState(Map map);

    List<MagOrderLogs> getOrderLogList(ParamFilter queryFilter);
}
