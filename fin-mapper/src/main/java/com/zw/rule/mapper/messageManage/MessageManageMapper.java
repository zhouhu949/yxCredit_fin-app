package com.zw.rule.mapper.messageManage;

import com.zw.rule.message.po.MessageManage;

import java.util.List;
import java.util.Map;

public interface MessageManageMapper {

    List getMessageList(Map param);
    int addMessage(MessageManage MessageManage);
    int updateMessage(MessageManage MessageManage);
    int deleteMessage(String id);
    int updateState(Map map);
    MessageManage getById(String id);
}