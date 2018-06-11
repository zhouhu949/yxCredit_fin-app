package com.zw.rule.api.sms.service.impl;

import com.zw.api.HttpClientUtil;
import com.zw.rule.api.common.BYXRequest;
import com.zw.rule.api.common.BYXSettings;
import com.zw.rule.api.sms.service.ISmsApiService;
import com.zw.rule.api.sortmsg.MsgRequest;
import com.zw.rule.api.sortmsg.MsgSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 短信发送实现
 * @author luochaofang
 */
@Service(ISmsApiService.BEAN_KEY)
public class SmsApiServiceImpl implements ISmsApiService {
    @Autowired
    private MsgSettings msgSettings;

    @Autowired
    private BYXSettings byxSettings;

    @Override
    public String sendSms(MsgRequest request) throws Exception {
        Map<String,Object> parameter = new HashMap<>(5);
        parameter.put("phone",request.getPhone());
        parameter.put("type",msgSettings.getType());
        parameter.put("channelUniqId",msgSettings.getChannelUniqId());
        parameter.put("content",request.getContent());
        final String result = HttpClientUtil.post(msgSettings.getRequestUrl(),BYXRequest.getBYXRequest(parameter, byxSettings), byxSettings.getHeadRequest());
        return result;
    }
}
