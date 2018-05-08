package com.zw.rule.messageService.service.impl;

import com.zw.rule.mapper.messageManage.MessageManageMapper;
import com.zw.rule.messageService.service.MessageService;
import com.zw.rule.message.po.MessageManage;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/9/19.
 */
@Service
public class MessageServiceImpl implements MessageService {
    @Resource
    private MessageManageMapper messageManageMapper;
    public List getMessageList(Map map){
        return messageManageMapper.getMessageList(map);
    }
    public int addMessage(MessageManage messageManage){
        return messageManageMapper.addMessage(messageManage);
    }

    public int updateMessage(MessageManage messageManage){
        return messageManageMapper.updateMessage(messageManage);
    }
    public int deleteMessage(String id){
        return messageManageMapper.deleteMessage(id);
    }
    public int updateState(Map map){
        return messageManageMapper.updateState(map);
    }
    public MessageManage getById(String id){
        return messageManageMapper.getById(id);
    }
}
