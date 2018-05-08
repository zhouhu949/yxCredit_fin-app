package com.zw.rule.messageService.service;

import com.zw.rule.message.po.MessageManage;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/9/19.
 */
public interface MessageService {
    List getMessageList(Map map);
    int addMessage(MessageManage MessageManage);
    int updateMessage(MessageManage MessageManage);
    int deleteMessage(String id);
    int updateState(Map map);
    MessageManage getById(String id);
}
