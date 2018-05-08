package com.zw.rule.SendMessage.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 发送消息
 */
@Service
public class SendMessageFactory{
    private static final Logger log = Logger.getLogger(SendMessageFactory.class);
    @Autowired
    @Qualifier("sendMessageXJService")
    private SendMessageService sendMessageXJService;

    @Autowired
    @Qualifier("sendMessageSPService")
    private SendMessageService sendMessageSPService;

    public static final int TYPE_XJ = 1;//现金
    public static final int TYPE_SP = 2;//商品

    public Map<String, Object> sendMessage(int appType, String messageType, String orderId, Map<String, Object> param) {
        switch (appType) {
            case TYPE_XJ:
                try {
                    return sendMessageXJService.sendMessage(messageType, orderId, param);
                } catch (Exception e) {
                    log.error(e);
                    return null;
                }
            case TYPE_SP:
                try {
                    return sendMessageSPService.sendMessage(messageType, orderId, param);
                } catch (Exception e) {
                    log.error(e);
                    return null;
                }
            default:
                return null;
        }
    }

    public Map<String, Object> sendMessageByUser(int appType, String messageType, String userId, Map<String, Object> param) {
        switch (appType) {
            case TYPE_XJ:
                try {
                    return sendMessageXJService.sendMessageByUser(messageType, userId, param);
                } catch (Exception e) {
                    log.error(e);
                    return null;
                }
            case TYPE_SP:
                try {
                    return sendMessageSPService.sendMessageByUser(messageType, userId, param);
                } catch (Exception e) {
                    log.error(e);
                    return null;
                }
            default:
                return null;
        }
    }
}
