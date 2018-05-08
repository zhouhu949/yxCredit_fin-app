package com.zw.rule.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * http请求工具类
 *
 * @FileName: HttpClientUtils.java
 * @Date: 2016年5月27日 上午9:12:02
 * @Version:V1.00
 */
public final class HttpClientUtils {

    private static final String USER_AGENT = "Mozilla/5.0";

    //Accept:application/json;
    private static final String ACCEPT = "application/json";

    //Content-Type:application/json;charset=utf-8;
    private static final String CONTENT_TYPE = "application/json;charset=utf-8";

    //Content-Length:256;
    private static final String CONTENT_LENGTH = "256";

    private static final CloseableHttpClient CLIENT = HttpClients.createDefault();

    private HttpClientUtils() {
    }

    /**
     * @param parameterMap
     * @return List<NameValuePair>
     * @Name: getParam
     * @Description:
     * @Version: V1.00
     * @Date: 2016年5月27日上午9:12:14
     */
    private static List<NameValuePair> getParam(Map<String, String> parameterMap) {
        List<NameValuePair> nameValuePairs = new ArrayList<>();
        if (parameterMap != null) {
            Set<String> keySet = parameterMap.keySet();
            for (String key : keySet) {
                nameValuePairs.add(new BasicNameValuePair(key, parameterMap.get(key)));
            }
        }
        return nameValuePairs;
    }

    /**
     * @param url
     * @return String
     * @Name: doGet
     * @Description:
     * @Version: V1.00
     * @Date: 2016年5月27日上午9:12:20
     */
    public static String doGet(String url) throws Exception {
        String result = null;
        HttpGet httpGet = new HttpGet(url);
        config(httpGet);
        HttpResponse response = CLIENT.execute(httpGet);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            HttpEntity entity = response.getEntity();
            result = EntityUtils.toString(entity);
        }
        return result;
    }

    /**
     * @param url
     * @param params
     * @return String
     * @Name: doPost
     * @Description:
     * @Version: V1.00
     * @Date: 2016年5月27日上午9:12:27
     */
    public static String doPost(String url, Map<String, String> params) throws Exception {
        String result = null;
        HttpPost httpPost = new HttpPost(url);
        UrlEncodedFormEntity postEntity;
        postEntity = new UrlEncodedFormEntity(getParam(params), "UTF-8");
        httpPost.setEntity(postEntity);
        config(httpPost);
        HttpResponse response = CLIENT.execute(httpPost);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            HttpEntity entity = response.getEntity();
            result = EntityUtils.toString(entity, "UTF-8");
        }
        return result;
    }

    /**
     *
     * @param url 请求地址
     * @param json 请求json串
     * @param authorization 请求验证
     * @return
     * @throws Exception
     */
    public static String doPost(String url, Object json,String authorization) throws Exception {
        String result = null;
        HttpPost httpPost = new HttpPost(url);
        StringEntity stringEntity = new StringEntity(json.toString(),"UTF-8");
        httpPost.setEntity(stringEntity);
        httpsConfig(httpPost,authorization);
        HttpResponse response = CLIENT.execute(httpPost);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            HttpEntity entity = response.getEntity();
            result = EntityUtils.toString(entity, "UTF-8");
        }
        return result;
    }


    /**
     * @param httpRequestBase void
     * @Name: config
     * @Description:
     * @Version: V1.00
     * @Date: 2016年5月27日上午9:12:34
     */
    private static void config(HttpRequestBase httpRequestBase) {
        httpRequestBase.setHeader("User-Agent", USER_AGENT);
        // 配置请求的超时设置
        RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(30000).setConnectTimeout(30000)
                .setSocketTimeout(30000).build();
        httpRequestBase.setConfig(requestConfig);
    }

    /**
     * @param httpRequestBase
     * @param authorization
     */
    private static void httpsConfig(HttpRequestBase httpRequestBase, String authorization) {
        //配置请求头
        httpRequestBase.setHeader("Accept", ACCEPT);
        httpRequestBase.setHeader("Content-Type", CONTENT_TYPE);
        //httpRequestBase.setHeader("Content-Length", CONTENT_LENGTH);
        httpRequestBase.setHeader("Authorization", authorization);
        // 配置请求的超时设置
        RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(30000).setConnectTimeout(30000)
                .setSocketTimeout(30000).build();
        httpRequestBase.setConfig(requestConfig);
    }

}