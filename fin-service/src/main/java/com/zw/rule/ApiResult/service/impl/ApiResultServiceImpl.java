package com.zw.rule.ApiResult.service.impl;


import com.zw.rule.ApiResult.service.ApiResultService;

import com.zw.rule.apiresult.ApiResult;
import com.zw.rule.mapper.apiResultMapper.ApiResultMapper;

import com.zw.rule.mapper.customer.OrderMapper;
import com.zw.rule.mybatis.ParamFilter;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 风控Service实现类
 * @author 仙海峰
 */
@Service
public class ApiResultServiceImpl implements ApiResultService {
    private static final Logger logger = Logger.getLogger(ApiResultServiceImpl.class);

    @Autowired
    private ApiResultMapper apiResultMapper;

    @Autowired
    private OrderMapper orderMapper;

    /**
     * 根据订单ID获取风控信息列表
     * @param orderId
     * @return
     */
    @Override
    public List getApiResultByOrderId(String orderId) {
        return apiResultMapper.getApiResultByOrderId(orderMapper.getOrderNoById(orderId));
    }

    @Override
    public ApiResult getByOrderIdAndSourceCode(String orderId, String sourceCode) {
        return apiResultMapper.getByOrderIdAndSourceCode(orderId,sourceCode);
    }

    @Override
    public ApiResult getResultById(String id) {
        return apiResultMapper.getResultById(id);
    }
}

