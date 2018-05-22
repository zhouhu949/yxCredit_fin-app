package com.zw.rule.ApiResult.service;


import com.zw.rule.apiresult.ApiResult;
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



    /**
     * 查询风控结果
     * @param orderId 订单id
     * @param sourceCode 来源码
     * @return 风控结果
     */
    ApiResult getByOrderIdAndSourceCode(String orderId , String sourceCode);

}
