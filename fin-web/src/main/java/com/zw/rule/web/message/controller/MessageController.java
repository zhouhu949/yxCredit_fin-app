package com.zw.rule.web.message.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zw.base.util.DateUtils;
import com.zw.rule.core.Response;
import com.zw.rule.mybatis.ParamFilter;
import com.zw.rule.message.po.MessageManage;
import com.zw.rule.messageService.service.MessageService;
import com.zw.rule.service.DictService;
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
@RequestMapping("message")
public class MessageController {

    @Resource
    private MessageService messageService;
    @Resource
    private DictService dictService;

    @GetMapping("list")//合同
    public String list() {
        return "messageManage/message";
    }

    @PostMapping("messageList")
    @ResponseBody
    @WebLogger("消息列表")
    public Response orderList(@RequestBody ParamFilter queryFilter){
        int pageNo = PageConvert.convert(queryFilter.getPage().getFirstIndex(),queryFilter.getPage().getPageSize());
        PageHelper.startPage(pageNo, queryFilter.getPage().getPageSize());
        List list = messageService.getMessageList(queryFilter.getParam());
        PageInfo pageInfo = new PageInfo(list);
        return new Response(pageInfo);
    }

    @PostMapping("add")
    @ResponseBody
    @WebLogger("添加消息")
    public Response addmessage(@RequestBody MessageManage messageManage){
        Response response = new Response();
        messageManage.setCreate_time(DateUtils.getCurrentTime(DateUtils.STYLE_10));
        messageManage.setId(UUID.randomUUID().toString());
        int num = messageService.addMessage(messageManage);
        if (num>0){
            response.setMsg("添加成功！");
        }else {
            response.setMsg("添加失败！");
        }
        return response;
    }

    @PostMapping("update")
    @ResponseBody
    @WebLogger("修改消息模板")
    public Response updateContract(@RequestBody MessageManage messageManage){
        Response response=new Response();
        messageManage.setAlter_time(DateUtils.getCurrentTime(DateUtils.STYLE_10));
        int count=messageService.updateMessage(messageManage);
        if (count>0){
            response.setMsg("修改成功！");
        }else {
            response.setMsg("修改失败！");
        }
        return response;
    }

    @PostMapping("del")
    @ResponseBody
    @WebLogger("删除消息模板")
    public Response delmessage(@RequestBody Map map){
        Response response=new Response();
        int count=messageService.deleteMessage(map.get("id").toString());
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
        int count=messageService.updateState(map);
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
        MessageManage messageManage=messageService.getById(param.get("id").toString());
        if (messageManage!=null){
            response.setData(messageManage);
        }else {
            return null;
        }
        return response;
    }
    @PostMapping("apendSelect")
    @ResponseBody
    @WebLogger("动态加载问题分类到下拉框")
    public Response apendSelect(){
        Response response = new Response();
        List list=dictService.getDetailList("消息类型");
        if (list!=null&&list.size()>0){
            response.setData(list);
        }else {
            return null;
        }
        return response;
    }

}
