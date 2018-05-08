package com.zw.rule.SendMessage.service;

import java.util.Map;

/**
 * 发送短信业务接口
 */
public interface PhoneMessageService {

    /**
     * 发送短信
     * @param phone 手机号
     * @param msg 消息
     * @param temData 模板参数
     * @return 发送结果
     */
    Map<String, Object>  sendPhoneMessage(String phone, String msg, Map<String, Object> temData);
}
