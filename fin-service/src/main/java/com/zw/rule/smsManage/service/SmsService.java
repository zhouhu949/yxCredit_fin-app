package com.zw.rule.smsManage.service;

import com.zw.rule.sms.po.SmsManage;
import com.zw.rule.sms.po.SmsRecord;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/9/19.
 */
public interface SmsService {
    List getSmsList(Map map);
    int addSms(SmsManage smsManage);
    int updateSms(SmsManage smsManage);
    int deleteSms(String id);
    int updateState(Map map);
    SmsManage getById(String id);
    List getSmsRecordList(Map map);
    SmsRecord getRecordById(String id);
}
