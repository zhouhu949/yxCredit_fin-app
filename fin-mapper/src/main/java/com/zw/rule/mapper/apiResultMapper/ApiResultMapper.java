package com.zw.rule.mapper.apiResultMapper;

import com.zw.rule.message.po.MessageManage;

import java.util.List;
import java.util.Map;

/**
 * 风控Mapper接口
 * @author 仙海峰
 */
public interface ApiResultMapper {
    /**
     * 根据订单ID获取风控信息列表
     * @param orderId
     * @return
     */
    List getApiResultByOrderId(String orderId);

}