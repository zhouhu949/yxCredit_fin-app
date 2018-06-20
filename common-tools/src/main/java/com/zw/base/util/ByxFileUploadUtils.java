package com.zw.base.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.nio.charset.Charset;

public class ByxFileUploadUtils {
    public static final String ENCODING="UTF-8";

    public static String uploadFile(String path, File file) {
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.setCharset(Charset.forName(HTTP.UTF_8));//设置请求的编码格式
        builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        HttpEntity entity= builder.addPart("file", new FileBody(file)).build();

        String result = null;
        HttpClient httpClient = HttpClients.createDefault();
        try {
            HttpPost httpRequest = new HttpPost(path);
            if(entity!=null){
                httpRequest.setEntity(entity);
            }
            HttpResponse httpResponse = httpClient.execute(httpRequest);
            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity httpEntity = httpResponse.getEntity();
                if (httpEntity != null) {
                    result = EntityUtils.toString(httpEntity, ENCODING);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            httpClient.getConnectionManager().shutdown();
        }
        return result;
    }

    public static void main(String[] args) {
        String result=ByxFileUploadUtils.uploadFile("http://172.16.10.37:8088/upload", new File("http://localhost:9090/ed12b028-9b32-43b6-a430-e3704cbc2ab0"));
        System.out.println(result);
    }
}
