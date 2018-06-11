package com.zw.rule.api.common;

import com.alibaba.fastjson.JSONObject;
import com.zw.jiguangNew.api.util.CryptoTools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 碧友信请求实体
 * @author luochaofang
 */
public class BYXRequest {
    private static final Logger LOGGER = LoggerFactory.getLogger(BYXRequest.class);

    private Long requestTime;
    private String data;

    public Long getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(Long requestTime) {
        this.requestTime = requestTime;
    }

    public String getData() {

        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    /**
     * 返回json对象参数
     * @param param 返回参数
     * @param byxSettings 碧友信配置
     * @return 请求参数json字符串
     * @throws Exception 加密异常
     */
    public static String getBYXRequest(Object param,BYXSettings byxSettings) throws Exception {
       CryptoTools cryptoTools = new CryptoTools(byxSettings.getDesKey(),byxSettings.getVi());
        BYXRequest byxRequest  = new BYXRequest();
       if(param == null){
           byxRequest.setData("");
       }else{
           //参数转化JSON
           final String paramJson = JSONObject.toJSONString(param);
           System.out.println("请求参数JSON字符串:" + paramJson);
           //碧友信参数加密
           final String encodesStr = cryptoTools.encode(paramJson);
           byxRequest.setData(encodesStr);
       }
        byxRequest.setRequestTime(System.currentTimeMillis());
        return JSONObject.toJSONString(byxRequest);
    }
}
