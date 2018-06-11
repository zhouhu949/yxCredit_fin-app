package com.zw.rule.api.sms.service;

import com.zw.rule.api.sortmsg.MsgRequest;
import com.zw.rule.core.Response;


/**
 * 短信接口
 */
public interface ISmsApiService {

    String BEAN_KEY = "smsApiServiceImpl";
    /**
     * 发送短信
     * @return 结果json字符串
     */
    String sendSms(MsgRequest request) throws Exception;
}
