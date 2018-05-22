package com.zw.rule.mapper.apiResultMapper;

import com.zw.rule.apiresult.ApiResult;
import com.zw.rule.message.po.MessageManage;
import org.apache.ibatis.annotations.Param;

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

    /**
     * 查询风控结果
     * @param orderId 订单id
     * @param sourceCode 来源码
     * @return 风控结果
     */
    ApiResult getByOrderIdAndSourceCode(@Param("orderId") String orderId , @Param("sourceCode") String sourceCode);

}