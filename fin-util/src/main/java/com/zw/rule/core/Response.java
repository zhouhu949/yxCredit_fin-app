package com.zw.rule.core;


import com.alibaba.fastjson.JSONObject;
import com.zw.constants.CommonConstants;
import com.zw.rule.mybatis.page.Page;
import org.apache.commons.lang.StringUtils;

public class Response implements ResponseCode {

    private Object data;
    
    private Object status;//用于兼容老的服务字段

    private Object result;//用于兼容老的服务字段

    private String res_code;//短信接口响应

    private String res_msg;//短信接口响应

    private Page page;

    //默认为0表示响应正常

    private int code = CommonConstants.SUCCESS;
    private String msg;
    /**
     * 扩展异常方法===============================================================
     * create by 陈清玉
     */
    public static Response error() {
        return error(CommonConstants.FAIL, "系统异常，请联系系统管理员！");
    }

    public static Response error(String msg) {
        return error(CommonConstants.FAIL, msg);
    }

    public static Response error(Integer code, String msg) {
        Response r = new Response();
        r.setCode(code);
        r.setMsg(msg);
        return r;
    }

    public static Response ok(Object data) {
        Response r = new Response();
        r.setData(data);
        return r;
    }

    public static Response ok(String msg ,Object  data) {
        Response r = new Response();
        r.setData(data);
        r.setMsg(msg);
        return r;
    }



    public Response() {
    }

    public Response(Object data) {
        this.data = data;
    }

    public Response(Object data, Page page) {
        this.data = data;
        this.page = page;
    }

    public Response(String msg) {
        this.msg = msg;
    }
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return this.data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public Object getStatus() {
        return status;
    }

    public void setStatus(Object status) {
        this.status = status;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public String getRes_code() {
        return res_code;
    }

    public void setRes_code(String res_code) {
        this.res_code = res_code;
    }

    public String getRes_msg() {
        return res_msg;
    }

    public void setRes_msg(String res_msg) {
        this.res_msg = res_msg;
    }
}
