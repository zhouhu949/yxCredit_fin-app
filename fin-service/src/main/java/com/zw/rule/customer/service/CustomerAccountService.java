package com.zw.rule.customer.service;

import com.zw.rule.customer.po.MagCustomerAccount;

public interface CustomerAccountService {

    MagCustomerAccount getAccountInfoByOrderId(String orderId);
}
