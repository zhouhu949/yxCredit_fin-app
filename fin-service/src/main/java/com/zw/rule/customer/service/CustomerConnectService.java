package com.zw.rule.customer.service;

import com.zw.rule.customer.po.Customer;

import java.util.List;

/**
 * Created by Administrator on 2017/6/29 0029.
 */
public interface CustomerConnectService {
    Boolean change(Customer customer);
    Boolean changeAll(List list);
}
