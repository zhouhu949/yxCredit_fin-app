package com.zw.rule.web.smsManage.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zw.base.util.DateUtils;
import com.zw.rule.core.Response;
import com.zw.rule.mybatis.ParamFilter;
import com.zw.rule.sms.po.SmsManage;
import com.zw.rule.sms.po.SmsRecord;
import com.zw.rule.smsManage.service.SmsService;
import com.zw.rule.web.aop.annotaion.WebLogger;
import com.zw.rule.web.util.PageConvert;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Administrator on 2017/9/19.
 */
@Controller
@RequestMapping("sms")
public class SMSController {

    @Resource
    private SmsService smsService;

    @GetMapping("list")//合同
    public String list() {
        return "smsManage/sms";
    }

    @PostMapping("smsList")
    @ResponseBody
    @WebLogger("短信列表")
    public Response orderList(@RequestBody ParamFilter queryFilter){
        int pageNo = PageConvert.convert(queryFilter.getPage().getFirstIndex(),queryFilter.getPage().getPageSize());
        PageHelper.startPage(pageNo, queryFilter.getPage().getPageSize());
        List list = smsService.getSmsList(queryFilter.getParam());
        PageInfo pageInfo = new PageInfo(list);
        return new Response(pageInfo);
    }

    @PostMapping("add")
    @ResponseBody
    @WebLogger("添加短信")
    public Response addSms(@RequestBody SmsManage smsManage){
        Response response = new Response();
        smsManage.setCreate_time(DateUtils.getCurrentTime(DateUtils.STYLE_10));
        smsManage.setId(UUID.randomUUID().toString());
        int num = smsService.addSms(smsManage);
        if (num>0){
            response.setMsg("添加成功！");
        }else {
            response.setMsg("添加失败！");
        }
        return response;
    }

    @PostMapping("update")
    @ResponseBody
    @WebLogger("修改短信模板")
    public Response updateContract(@RequestBody SmsManage smsManage){
        Response response=new Response();
        smsManage.setAlter_time(DateUtils.getCurrentTime(DateUtils.STYLE_10));
        int count=smsService.updateSms(smsManage);
        if (count>0){
            response.setMsg("修改成功！");
        }else {
            response.setMsg("修改失败！");
        }
        return response;
    }

    @PostMapping("del")
    @ResponseBody
    @WebLogger("删除短信模板")
    public Response delSms(@RequestBody Map map){
        Response response=new Response();
        int count=smsService.deleteSms(map.get("id").toString());
        if (count>0){
            response.setMsg("删除成功！");
        }else {
            response.setMsg("删除失败！");
        }
        return response;
    }

    @PostMapping("updateState")
    @ResponseBody
    @WebLogger("修改状态")
    public Response updateState(@RequestBody Map map){
        Response response=new Response();
        int count=smsService.updateState(map);
        if (count>0){
            response.setMsg("修改成功！");
        }else {
            response.setMsg("修改失败！");
        }
        return response;
    }

    @PostMapping("getById")
    @ResponseBody
    @WebLogger("根据id获取详情")
    public Response getById(@RequestBody Map  param){
        Response response = new Response();
        SmsManage smsManage=smsService.getById(param.get("id").toString());
        if (smsManage!=null){
            response.setData(smsManage);
        }else {
            return null;
        }
        return response;
    }










    /**短信发送记录
     * gzd
     * 2017-10-23**/


    @GetMapping("listRecord")//短信发送记录
    public String listRecord() {
        return "smsManage/smsRecord";
    }

    @PostMapping("smsRecordList")
    @ResponseBody
    @WebLogger("短信记录列表")
    public Response recordList(@RequestBody ParamFilter queryFilter){
        int pageNo = PageConvert.convert(queryFilter.getPage().getFirstIndex(),queryFilter.getPage().getPageSize());
        PageHelper.startPage(pageNo, queryFilter.getPage().getPageSize());
        List list = smsService.getSmsRecordList(queryFilter.getParam());
        PageInfo pageInfo = new PageInfo(list);
        return new Response(pageInfo);
    }

    @PostMapping("getRecordById")
    @ResponseBody
    @WebLogger("根据id获取详情")
    public Response getRecordById(@RequestBody Map  param){
        Response response = new Response();
        SmsRecord smsRecord=smsService.getRecordById(param.get("id").toString());
        if (smsRecord!=null){
            response.setData(smsRecord);
        }else {
            return null;
        }
        return response;
    }
}
