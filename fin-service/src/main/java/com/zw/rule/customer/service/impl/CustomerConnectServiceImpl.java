package com.zw.rule.customer.service.impl;

import com.zw.rule.customer.po.Customer;
import com.zw.rule.customer.service.CustomerConnectService;
import com.zw.rule.mapper.customer.CustomerMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <strong>Title : <br>
 * </strong> <strong>Description : </strong>@类注释说明写在此处@<br>
 * <strong>Create on : 2017年06月29日<br>
 * </strong>
 * <p>
 * <strong>Copyright (C) Vbill Co.,Ltd.<br>
 * </strong>
 * <p>
 *
 * @author department:技术开发部 <br>
 *         username:Administrator <br>
 *         email: <br>
 * @version <strong>zw有限公司-运营平台</strong><br>
 *          <br>
 *          <strong>修改历史:</strong><br>
 *          修改人 修改日期 修改描述<br>
 *          -------------------------------------------<br>
 *          <br>
 *          <br>
 */
@Service
public class CustomerConnectServiceImpl implements CustomerConnectService {
    @Resource
    private CustomerMapper customerMapper;
    @Override
    public Boolean change(Customer customer) {
        int num = customerMapper.change(customer);
        return num>0;
    }

    @Override
    public Boolean changeAll(List list) {
        int num = customerMapper.changeAll(list);
        return num>0;
    }
}