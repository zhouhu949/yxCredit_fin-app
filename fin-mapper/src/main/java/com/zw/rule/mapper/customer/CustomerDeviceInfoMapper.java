package com.zw.rule.mapper.customer;

import com.zw.rule.customer.po.CustomerDeviceInfo;

/**
 * Created by Administrator on 2017/9/15.
 */
public interface CustomerDeviceInfoMapper {

    CustomerDeviceInfo selectByOrderId(String orderId);
}
