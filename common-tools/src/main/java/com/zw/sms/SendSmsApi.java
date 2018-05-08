package com.zw.sms;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * <strong>Title : 嘻哈发送手机短信接口<br>
 * </strong> <strong>Description : </strong>@类注释说明写在此处@<br>
 * <strong>Create on : 2017年10月10日<br>
 * </strong>
 * <p>
 * <strong>Copyright (C) Vbill Co.,Ltd.<br>
 * </strong>
 * <p>
 *
 * @author department:技术开发部 <br>
 *         username:niudengfeng <br>
 * @version <strong>zw有限公司-运营平台</strong><br>
 *          <br>
 *          <strong>修改历史:</strong><br>
 *          修改人 修改日期 修改描述<br>
 *          -------------------------------------------<br>
 *          <br>
 *          <br>
 */
public class SendSmsApi {
    private static final Logger log = Logger.getLogger(SendSmsApi.class);

    /**
     * 手机发送短信接口
     * @param host 接口服务器地址
     * @return
     */
    public static Map sendSms(String host, Map map) {
        Map returnMap = new HashMap();
        String account = map.get("account").toString();//用户账号
        String password = map.get("password").toString();//用户账号密码
        String phone = map.get("tel").toString();//用户手机号码
        String report = "false";//是否需要状态报告（默认false），选填
        String msg = map.get("msg").toString();
        try {
            log.info("发送短信Url--->"+host);
            SmsSendRequest smsSingleRequest = new SmsSendRequest(account, password, msg, phone,report);
            log.info("请求之前数据-->"+smsSingleRequest);
            String requestJson = JSON.toJSONString(smsSingleRequest);
            String response = ChuangLanSmsUtil.sendSmsByPost(host, requestJson);
            log.info("接收到的结果--->"+response);
            SmsSendResponse smsSingleResponse = JSON.parseObject(response, SmsSendResponse.class);
            log.info("接收到的结果--->"+smsSingleResponse);
            if("0".equals(smsSingleResponse.getCode())){
                returnMap.put("flag",true);
            }else{
                returnMap.put("flag",false);
            }
            return returnMap;
        } catch (Exception ex) {
            log.error("发送短信接口--->", ex);
            throw ex;
        }
    }
    public static Map sendAliyun(String host, Map map, Map temData) {
        Map returnMap = new HashMap();
        String minute = map.get("minute").toString();//短信时长
        String code = map.get("varCode").toString();//短信随机码
        String phone = map.get("phone").toString();//用户手机号码
        String tNum = map.get("tNum").toString();//短信模板

        String path = "/sendSms";
        String method = "GET";
        String appcode = map.get("appcode").toString();

        String content = null;
        if (null != temData && null != temData.keySet() && temData.keySet().size() > 0)
        {
            Set<String> keys = temData.keySet();
            StringBuffer sendRequest = new StringBuffer();
            sendRequest.append("{");
            Iterator<String> key = keys.iterator();
            String first = key.next();
            sendRequest.append("\"" + first + "\": \""  + temData.get(first) + "\"");
            while (key.hasNext())
            {
                String next = key.next();
                sendRequest.append(",\"" + next + "\": \""  + temData.get(next) + "\"");
            }
            sendRequest.append("}");
            content = sendRequest.toString();
        }

        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<String, String>();
        if (null != content && content.length() > 0)
        {
            querys.put("content", content);
        }
        querys.put("mobile", phone);
        querys.put("tNum", tNum);
        try {
            String response = HttpUtils.doGet(host, path, method, headers, querys);
            //获取response的body
            log.info("接收到的结果--->"+response);
            JSONObject jsonObject = JSONObject.parseObject(response);
            JSONObject jsonObject1 = (JSONObject) jsonObject.get("showapi_res_body");
            log.info("接收到的结果--->"+jsonObject1);
            if("1".equals(jsonObject1.get("successCounts"))){
                returnMap.put("flag",true);
            }else{
                returnMap.put("flag",false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnMap;
    }

    public static void main(String[] args) throws Exception{
        String msg = "【可以贷】您的验证码为$code$,用于进行网站用户注册功能,$companyName$为了账户安全,请勿泄露,本次验证码有效时间为120秒。全国服务热线:400-0686-600";
        String code = "123455";
        String msg1 = msg.replace("$code$",":"+code+"");
        Map map = new HashMap();
        map.put("account","N3637302");
        map.put("password","oTNSiXHGOg48a3");
        map.put("tel","15656555717");
        map.put("msg",msg1);
        System.out.print("接收短信接口接收data"+SendSmsApi.sendSms("http://smssh1.253.com/msg/send/json",map));
//            String host = "http://ali-sms.showapi.com";
//            String path = "/sendSms";
//            String method = "GET";
//            String appcode = "d097170338524cec942effd48cf22c99";
//            StringBuffer sendRequest = new StringBuffer();
//            sendRequest.append("{");
//            sendRequest.append("\"name\": \"");
//            sendRequest.append("张三疯");
//            sendRequest.append("\",");
//            sendRequest.append("\"code\": \"");
//            sendRequest.append("123456");
//            sendRequest.append("\",");
//            sendRequest.append("\"minute\": \"");
//            sendRequest.append("5");
//            sendRequest.append("\"");
//            sendRequest.append("}");
//            Map<String, String> headers = new HashMap<String, String>();
//            //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
//            headers.put("Authorization", "APPCODE " + appcode);
//            Map<String, String> querys = new HashMap<String, String>();
//            querys.put("content", sendRequest.toString());
//            querys.put("mobile", "17718145026");
//            querys.put("tNum", "T150606060606");
//            try {
//                /**
//                 * 重要提示如下:
//                 * HttpUtils请从
//                 * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
//                 * 下载
//                 *
//                 * 相应的依赖请参照
//                 * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
//                 */
//                String response = HttpUtils.doGet(host, path, method, headers, querys);
//                //获取response的body
//                System.out.println("=============="+response);
//                JSONObject jsonObject = JSONObject.parseObject(response);
//                System.out.println("=============="+jsonObject.get("showapi_res_body"));
//                JSONObject jsonObject1 = (JSONObject) jsonObject.get("showapi_res_body");
//                System.out.println("=============="+jsonObject1.get("successCounts"));
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
    }
}
