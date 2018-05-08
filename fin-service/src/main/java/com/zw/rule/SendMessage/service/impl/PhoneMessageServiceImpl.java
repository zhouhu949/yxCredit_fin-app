package com.zw.rule.SendMessage.service.impl;

import com.zw.rule.SendMessage.service.PhoneMessageService;
import com.zw.rule.service.AppDictService;
import com.zw.sms.SendSmsApi;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * Created by Administrator on 2017/9/16.
 */
@Service
public class PhoneMessageServiceImpl implements PhoneMessageService {
    private static final Logger log = Logger.getLogger(PhoneMessageServiceImpl.class);
    @Resource
    private AppDictService appDictService;

    @Override
    public Map<String, Object>  sendPhoneMessage(String phone, String msg, Map<String, Object> tempData) {
        Map<String, Object> map = new HashMap<String, Object>();
        String dxtdType = null;
        try {
            dxtdType = appDictService.getDictInfo("短信通道","DXTD");
        } catch (Exception e) {
            log.error(e);
            map.put("code", 1);
            map.put("msg", "获取短信通道失败");
            return map;
        }
        //将properties文件加载到输入字节流中
        InputStream is = PhoneMessageServiceImpl.class.getClassLoader().getResourceAsStream("properties/host.properties");
        //创建一个Properties容器
        Properties prop = new Properties();
        //从流中加载properties文件信息
        try {
            prop.load(is);
        } catch (IOException e) {
            log.error(e);
            map.put("code", 1);
            map.put("msg", "读取发送短信信息失败");
            return map;
        }

        if("0".equals(dxtdType)){//代表创蓝短信

            String host = prop.getProperty("sms.app_sms_url");//短信地址
            String account = prop.getProperty("sms.app_sms_account");//账号
            String password = prop.getProperty("sms.app_sms_password");//密码
            if (null == host || host.length() == 0
                    || null == account || account.length() == 0
                    || null == password || password.length() == 0 )
            {
                map.put("code", 1);
                map.put("msg", "发送短信信息不存在");
                return map;
            }
            if (null != tempData && null != tempData.keySet() && tempData.keySet().size() > 0)
            {
                Set<String> keys = tempData.keySet();
                Iterator<String> key = keys.iterator();
                while (key.hasNext())
                {
                    String next = key.next();
                    msg= msg.replace("$" + next + "$", tempData.get(next) + "");
                }
            }
            Map<String, Object> inMap = new HashMap<String, Object>();
            inMap.put("account",account);
            inMap.put("password",password);
            inMap.put("tel",phone);
            inMap.put("msg",msg);
            Map map1 = (Map) SendSmsApi.sendSms(host,inMap);
            if (null == map1 || !((Boolean) map1.get("flag")))
            {
                map.put("code", 1);
                map.put("msg", "发送短信失败");
                return map;
            }
        }else if("1".equals(dxtdType)){//代表阿里云短信
            String host = prop.getProperty("aliyunsms.host");//短信地址
            String minute = prop.getProperty("aliyunsms.minute");//短信时长
            String appcode = prop.getProperty("aliyunsms.appcode");//短信code
            String tNum = prop.getProperty("aliyunsms.tNum ");//短信模板
            Map<String, Object> inMap = new HashMap<String, Object>();
            inMap.put("host",host);
            inMap.put("minute",minute);
            inMap.put("appcode",appcode);
            inMap.put("tNum",tNum);
            inMap.put("phone",phone);
            Map map1 = (Map)SendSmsApi.sendAliyun(host, inMap, tempData);
            if (null == map1 || !((Boolean) map1.get("flag")))
            {
                map.put("code", 1);
                map.put("msg", "发送短信失败");
                return map;
            }
        }
        map.put("code", 0);
        map.put("msg", "发送短信成功");
        return map;
    }
}
