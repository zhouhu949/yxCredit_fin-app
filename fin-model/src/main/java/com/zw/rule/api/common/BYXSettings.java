package com.zw.rule.api.common;

import com.zw.base.util.MD5Util;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * 碧友信权限配置项
 * @author luochaofang
 */
public class BYXSettings {

    private String  appKey;
    private String desKey;
    private Long ts;
    private String  signa;
    private String vi;
    private String appSecrect;

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getDesKey() {
        return desKey;
    }

    public void setDesKey(String desKey) {
        this.desKey = desKey;
    }

    public Long getTs() {
        return ts;
    }

    public void setTs(Long ts) {
        this.ts = ts;
    }

    public String getSigna() {
        return signa(System.currentTimeMillis());
    }

    public void setSigna(String signa) {
        this.signa = signa;
    }

    public String getAppSecrect() {
        return appSecrect;
    }

    public void setAppSecrect(String appSecrect) {
        this.appSecrect = appSecrect;
    }

    public String getVi() {
        return vi;
    }

    public void setVi(String vi) {
        this.vi = vi;
    }

    public String key(Long ts){
       return MD5Util.MD5Encode(getAppSecrect()+ String.valueOf(ts), "utf-8");
    }

    public String signa(Long ts){
        return MD5Util.MD5Encode (key(ts)+ getAppKey(), "utf-8").toUpperCase();
    }
    public String signaSignContract(Long ts){
        String serverSigna = MD5Util.MD5Encode(appSecrect + ts, "utf-8");
        signa = MD5Util.MD5Encode(serverSigna + appKey, "utf-8").toUpperCase();
        return signa;
    }

    /**‰
     * 获取碧友信请求头
     * @return 请求头map
     */
    public  List<Header> getHeadRequest(){
        final long millis = System.currentTimeMillis();
        List<Header> headers = new ArrayList<>();
        headers.add(new BasicHeader("appKey",getAppKey()));
        headers.add(new BasicHeader("ts",String.valueOf(millis)));
        headers.add(new BasicHeader("signa",signa(millis)));
        System.out.println("appKey:" + getAppKey()+",ts:"+ millis +",signa:" + signa(millis));
        return headers;
    }
    /**
     * 获取碧友信请求头
     * @return 请求头map
     */
    public  List<Header> getSignContractHeadRequest(){
        Calendar c = Calendar.getInstance();
        final long millis = c.getTimeInMillis();
        List<Header> headers = new ArrayList<>();
        headers.add(new BasicHeader("appKey",getAppKey()));
        headers.add(new BasicHeader("appSecret",getAppSecrect()));
        headers.add(new BasicHeader("ts",String.valueOf(millis)));
        headers.add(new BasicHeader("signa",signaSignContract(millis)));
        System.out.println("appKey:" + getAppKey()+",ts:"+ millis +",signa:" + signa(millis));
        return headers;
    }
}
