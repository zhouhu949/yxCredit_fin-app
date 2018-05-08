package com.zw.rule.SendMessage.service;

import java.util.List;
import java.util.Map;

/**
 * 发送信息接口
 */
public interface SendMessageService {
    /**
     * 发送信息
     * @param type 信息类型
     * @param orderId 订单号
     * @return 发送结果
     */
    Map<String, Object>  sendMessage(String type, String orderId, Map<String, Object> param) throws Exception;

    /**
     * 发送信息
     * @param type 信息类型
     * @param userId 用户id
     * @param param 消息内容参数
     * @return 返回
     * @throws Exception
     */
    public Map<String, Object>  sendMessageByUser(String type, String userId, Map<String, Object> param) throws Exception;
}
