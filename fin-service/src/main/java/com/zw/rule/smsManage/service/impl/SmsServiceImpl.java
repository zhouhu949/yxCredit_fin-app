package com.zw.rule.smsManage.service.impl;

import com.zw.rule.mapper.smsManage.SmsMapper;
import com.zw.rule.sms.po.SmsRecord;
import com.zw.rule.sms.po.SmsManage;
import com.zw.rule.smsManage.service.SmsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/9/19.
 */
@Service
public class SmsServiceImpl implements SmsService {
    @Resource
    private SmsMapper smsMapper;
    public List getSmsList(Map map){
        return smsMapper.getSmsList(map);
    }
    public int addSms(SmsManage smsManage){
        return smsMapper.addSms(smsManage);
    }

    public int updateSms(SmsManage smsManage){
        return smsMapper.updateSms(smsManage);
    }
    public int deleteSms(String id){
        return smsMapper.deleteSms(id);
    }
    public int updateState(Map map){
        return smsMapper.updateState(map);
    }
    public SmsManage getById(String id){
        return smsMapper.getById(id);
    }
    public List getSmsRecordList(Map map){
        return smsMapper.getSmsRecordList(map);
    }
    public SmsRecord getRecordById(String id){
        return smsMapper.getRecordById(id);
    }
}
