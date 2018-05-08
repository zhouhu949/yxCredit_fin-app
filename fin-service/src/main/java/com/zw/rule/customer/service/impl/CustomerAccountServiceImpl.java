package com.zw.rule.customer.service.impl;

import com.zw.rule.customer.po.MagCustomerAccount;
import com.zw.rule.customer.service.CustomerAccountService;
import com.zw.rule.mapper.customer.MagCustomerAccountMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/******************************************************
 *Copyrights @ 2017，zhiwang  Co., Ltd.
 *
 *All rights reserved.
 *
 *Filename：
 *          开户银行信息
 *Finished：
 *		2017/7/3
 ********************************************************/
@Service
public class CustomerAccountServiceImpl implements CustomerAccountService{

    @Resource
    private MagCustomerAccountMapper magCustomerAccountMapper;

    @Override
    public MagCustomerAccount getAccountInfoByOrderId(String orderId) {
        return magCustomerAccountMapper.getAccountInfoByOrderId(orderId);
    }
}
