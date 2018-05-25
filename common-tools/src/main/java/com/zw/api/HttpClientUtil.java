package com.zw.api;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


public class HttpClientUtil {
    private static Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);
    
    
    public static String post(String url, String param,List<Header> headerList) {// header方法扩展一下
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader(HTTP.CONTENT_TYPE, "application/json;charset=utf-8");//请求参数为JSON格式
        
        //添加头部信息
        for(int i=0;i<headerList.size();i++){
        	httpPost.addHeader(headerList.get(i));
        }
        
        String content = "";
        try {
            StringEntity entity = new StringEntity(param, "UTF-8");
            httpPost.setEntity(entity);
            CloseableHttpResponse response = httpclient.execute(httpPost);
            try {
                if (response.getStatusLine().getStatusCode() == 200) {
                    HttpEntity respEntity = response.getEntity();
                    if (respEntity != null)
                        content = EntityUtils.toString(respEntity, "UTF-8");
                        System.out.println("#####content:"+content);
                }
            } finally {
                response.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                httpclient.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return content;
    }
}