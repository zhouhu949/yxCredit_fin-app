package com.zw.rule.SendMessage.service.impl;

import com.zw.base.util.DateUtils;
import com.zw.jiguangNew.JiGuangUtils;
import com.zw.rule.SendMessage.service.PhoneMessageService;
import com.zw.rule.SendMessage.service.SendMessageService;
import com.zw.rule.contract.ContractService;
import com.zw.rule.contract.po.MagOrderContract;
import com.zw.rule.customer.po.AppMessage;
import com.zw.rule.customer.po.AppUser;
import com.zw.rule.customer.po.Customer;
import com.zw.rule.mapper.customer.AppMessageMapper;
import com.zw.rule.mapper.customer.AppUserMapper;
import com.zw.rule.mapper.customer.CustomerMapper;
import com.zw.rule.mapper.customer.OrderMapper;
import com.zw.rule.service.AppDictService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Created by Administrator on 2017/9/16.
 */
@Service("sendMessageXJService")
public class SendMessageXJServiceImpl implements SendMessageService {
    private static final Logger log = Logger.getLogger(SendMessageXJServiceImpl.class);
    @Resource
    private AppDictService appDictService;
    @Resource
    private PhoneMessageService phoneMessageService;
    @Resource
    private ContractService contractService;
    @Resource
    private OrderMapper orderMapper;
    @Resource
    private CustomerMapper customerMapper;
    @Resource
    private AppMessageMapper appMessageMapper;
    @Resource
    private AppUserMapper appUserMapper;


    @Override
    public Map<String, Object>  sendMessage(String type, String orderId,  Map<String, Object> param) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Object> userInfo = orderMapper.getUserInfoByorderId(orderId);
        //获取订单信息
        Map<String, Object> contractParam = new HashMap<String, Object>();
        contractParam.put("id", orderId);
        List<MagOrderContract> contractList =  contractService.getContractByOrderId(contractParam);
        String contractNo = "";
        if (null != contractList && contractList.size() > 0)
        {
            contractNo = contractList.get(0).getContract_no();
        }

        if (null == userInfo || null == userInfo.get("registrationId")
                || null == userInfo.get("id") || null == userInfo.get("tel"))
        {
            map.put("code", 1);
            map.put("msg", "订单对应用户信息不存在!");
        }
        String registrationId = userInfo.get("registrationId") + "";
        String userId = userInfo.get("id") + "";
        String tel = userInfo.get("tel") + "";
        String push = "0"; //推送状态
        Customer customer = customerMapper.getCustomerByUserId(userId);
        String bak ="";
        if(customer != null && "1".equals(customer.getIsOpenAccount())){
            bak ="18";
        }

        //将properties文件加载到输入字节流中
        InputStream is = SendMessageXJServiceImpl.class.getClassLoader().getResourceAsStream("properties/host.properties");
        //创建一个Properties容器
        Properties prop = new Properties();
        //从流中加载properties文件信息
        try {
            prop.load(new InputStreamReader(is, "GBK"));
        } catch (IOException e) {
            log.error(e);
            map.put("code", 1);
            map.put("msg", "读取发送信息配置失败");
            return map;
        }

        if ("1".equals(type))
        {//展期
            String title = "展期成功";
            String content = appDictService.getDictInfo("消息内容","zqcg");
            String appKey = prop.getProperty("jpush.appKey");
            String masterSecret = prop.getProperty("jpush.masterSecret");

            param.put("contractNo", contractNo);
            if (null != param && null != param.keySet() && param.keySet().size() > 0)
            {
                Set<String> keys = param.keySet();
                Iterator<String> key = keys.iterator();
                while (key.hasNext())
                {
                    String next = key.next();
                    content = content.replace("$" + next + "$", param.get(next) + "");
                }
            }
            if( JiGuangUtils.alias(appKey, masterSecret, title,content,registrationId))
            {
                push = "0";
            }

            //获取短信签名
            String proName = prop.getProperty("sms.signSP");
            //获取短信模板
            String temp = appDictService.getDictInfo("短信模板","extension");
            temp = proName + temp;

            //发送短信
            phoneMessageService.sendPhoneMessage(tel, temp, param);

            //保存消息
            AppMessage appMessage = new AppMessage();
            String id = UUID.randomUUID().toString();
            appMessage.setId(id);
            appMessage.setOrderId(orderId);
            appMessage.setUserId(userId);
            appMessage.setTitle(title);
            appMessage.setContent(content);
            appMessage.setBak(bak);
            appMessage.setState("0");
            appMessage.setUpdateState("0");
            appMessage.setPushState(push);
            appMessage.setMsgType("1");
            appMessage.setOrderType("1");
            String date = DateUtils.getDateString(new Date());
            appMessage.setCreatTime(date);
            appMessage.setAlterTime(date);
            int i = appMessageMapper.insertSelective(appMessage);
            if (i <= 0)
            {
                map.put("code", 0);
                map.put("msg", "保存消息失败");
            }
        }
        map.put("code", 0);
        map.put("msg", "发送消息成功");
        return map;

    }

    @Override
    public Map<String, Object>  sendMessageByUser(String type, String userId, Map<String, Object> param) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        AppUser userInfo = appUserMapper.selectByPrimaryKey(userId);
        if (null == userInfo || null == userInfo.getRegistrationId()
                || null == userInfo.getId() || null == userInfo.getTel())
        {
            map.put("code", 1);
            map.put("msg", "用户信息不存在!");
            return map;
        }
        String registrationId = userInfo.getRegistrationId();
        String tel = userInfo.getTel();
        String title = "";
        String content = "";
        String push = "0"; //推送状态
        String updateState = "0";
        //将properties文件加载到输入字节流中
        InputStream is = SendMessageSPServiceImpl.class.getClassLoader().getResourceAsStream("properties/host.properties");
        //创建一个Properties容器
        Properties prop = new Properties();
        //从流中加载properties文件信息
        try {
            prop.load(new InputStreamReader(is, "GBK"));
        } catch (IOException e) {
            log.error(e);
            map.put("code", 1);
            map.put("msg", "读取发送信息配置失败");
            return map;
        }

        //保存消息
        AppMessage appMessage = new AppMessage();
        String id = UUID.randomUUID().toString();
        appMessage.setId(id);
        appMessage.setOrderId("");
        appMessage.setUserId(userId);
        appMessage.setTitle(title);
        appMessage.setContent(content);
        appMessage.setBak("");
        appMessage.setState("99");
        appMessage.setUpdateState(updateState);
        appMessage.setPushState(push);
        appMessage.setMsgType("1");
        appMessage.setOrderType("2");
        String date = DateUtils.getDateString(new Date());
        appMessage.setCreatTime(date);
        appMessage.setAlterTime(date);
        int i = appMessageMapper.insertSelective(appMessage);
        if (i <= 0)
        {
            map.put("code", 0);
            map.put("msg", "保存消息失败");
            return map;
        }
        map.put("code", 0);
        map.put("msg", "发送消息成功");
        return map;

    }
}
