package com.zw.rule.mapper.smsManage;

import com.zw.rule.sms.po.SmsManage;
import com.zw.rule.sms.po.SmsRecord;

import java.util.List;
import java.util.Map;

public interface SmsMapper {

    List getSmsList(Map param);
    int addSms(SmsManage smsManage);
    int updateSms(SmsManage smsManage);
    int deleteSms(String id);
    int updateState(Map map);
    SmsManage getById(String id);
    List getSmsRecordList(Map param);
    SmsRecord getRecordById(String id);
}