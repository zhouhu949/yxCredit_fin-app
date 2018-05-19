package com.zw.rule.ApiResult.service;


import com.zw.rule.mybatis.ParamFilter;

import java.util.List;

/**
 * 风控Service接口
 * @author 仙海峰
 */
public interface ApiResultService {

    /**
     * 根据订单ID获取风控信息列表
     * @param orderId
     * @return
     */
    List getApiResultByOrderId(String orderId);
}
