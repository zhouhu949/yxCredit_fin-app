package com.zw.rule.web.task.controller;

import com.zw.rule.core.Response;
import com.zw.rule.jeval.tools.StringUtil;
import com.zw.rule.mybatis.ParamFilter;
import com.zw.rule.mybatis.page.Page;
import com.zw.rule.task.service.TaskMsgService;
import com.zw.rule.util.flow.service.FlowComService;
import com.zw.rule.web.aop.annotaion.WebLogger;
import com.zw.rule.web.util.UserContextUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/******************************************************
 *Copyrights @ 2017，zhiwang  Co., Ltd.
 *
 *All rights reserved.
 *          小窝
 *Filename：
 *         流程任务controller
 *Description：
 *		  未知
 *Author:
 *		 李开艳
 *Finished：
 *		2017/6/14
 ********************************************************/
@Controller
@RequestMapping("taskMsg")
public class TaskMsgController {

    @Resource
    private TaskMsgService taskMsgService;

    @Resource
    private FlowComService flowComService;

    @GetMapping("home")
    public String home(){
        return "task/taskHome";
    }
    @GetMapping("pageIn")
    public String pageIn(){
        return "task/taskMsgIn";
    }
    @GetMapping("monitor")
    public String monitor(){
        return "task/monitor";
    }
    @RequestMapping("pageNot")
    public String pageNot(){
        return "task/taskMsgNot";
    }
    @RequestMapping("pagePast")
    public String pagePast(){
        return "task/taskMsgPast";
    }
    @RequestMapping("handlePage")
    public String handle(String processNodeId,String taskNodeId){
        UserContextUtil.setAttribute("processNodeId",processNodeId);
        UserContextUtil.setAttribute("taskNodeId",taskNodeId);
        return "task/taskHandle";
    }
    @GetMapping("lookPage")
    public String look(String processNodeId,String taskNodeId){
        UserContextUtil.setAttribute("processNodeId",processNodeId);
        UserContextUtil.setAttribute("taskNodeId",taskNodeId);
        return "task/taskLook";
    }

    @GetMapping("getCount")
    @ResponseBody
    public Response getCount(){
        Map map = new HashMap();
        Long currentUserId = UserContextUtil.getUserId();
        map.put("currentUserId",currentUserId);
        int notCount = taskMsgService.countNotCommitTask(map);
        map.put("nodeStateList",StringUtil.toLongList("1,2"));
        int inCount = taskMsgService.countCommitTask(map);
        //map.put("nodeStateList",StringUtil.toLongList("3,4"));
        int pastCount = taskMsgService.countMonitor(map);
        Map resMap = new HashMap();
        resMap.put("notCount",notCount);
        resMap.put("inCount",inCount);
        resMap.put("pastCount",pastCount);
        return new Response(resMap);
    }

    @PostMapping("getCommitTask")
    @ResponseBody
    @WebLogger("处理中")
    public Response getCommitTask(@RequestBody ParamFilter paramFilter){
        Long currentUserId = UserContextUtil.getUserId();
        paramFilter.getParam().put("currentUserId",currentUserId);
        List nodeStateList = StringUtil.toLongList(paramFilter.getParam().get("nodeStateList").toString());
        paramFilter.getParam().put("nodeStateList",nodeStateList);
        List list = taskMsgService.getCommitTask(paramFilter);
        Page page = paramFilter.getPage();
        return new Response(list,page);
    }
    @PostMapping("getTaskDetail")
    @ResponseBody
    @WebLogger("查询已处理任务详情")
    public Response getTaskDetail(@RequestBody ParamFilter paramFilter){
        Long currentUserId = UserContextUtil.getUserId();
        paramFilter.getParam().put("currentUserId",currentUserId);
        List nodeStateList = StringUtil.toLongList(paramFilter.getParam().get("nodeStateList").toString());
        paramFilter.getParam().put("nodeStateList",nodeStateList);
        List list = taskMsgService.getDetailTask(paramFilter);
        Page page = paramFilter.getPage();
        return new Response(list,page);
    }

    @PostMapping("getNotCommitTask")
    @ResponseBody
    @WebLogger("待处理")
    public Response getNotCommitTask(@RequestBody ParamFilter paramFilter){
        Long currentUserId = UserContextUtil.getUserId();
        paramFilter.getParam().put("currentUserId",currentUserId);
        List list = taskMsgService.getNotCommitTask(paramFilter);
        Page page = paramFilter.getPage();
        return new Response(list,page);
    }

    @PostMapping("getMonitor")
    @ResponseBody
    @WebLogger("任务监控")
    public Response getMonitor(@RequestBody ParamFilter paramFilter){
        Long currentUserId = UserContextUtil.getUserId();
        paramFilter.getParam().put("currentUserId",currentUserId);
        List list = taskMsgService.getMonitor(paramFilter);
//        paramFilter.getPage().setResultCount(list.size());
        Page page = paramFilter.getPage();
        return new Response(list,page);
    }

    @PostMapping("updateState")
    @ResponseBody
    @WebLogger("处理任务")
    public Response updateState(@RequestBody Map paramMap){
        Long currentUserId = UserContextUtil.getUserId();
        Response response = new Response();
        if(taskMsgService.updateState(paramMap,currentUserId)){
            response.setMsg("操作成功！");
        }else{
            response.setCode(1);
            response.setMsg("操作失败！");
        }
        return response;
    }

    @PostMapping("forwardTask")
    @ResponseBody
    @WebLogger("查询可以被转寄用户")
    public Response forwardTask(@RequestBody ParamFilter paramFilter){
        Long currentUserId = UserContextUtil.getUserId();
        List list = taskMsgService.forwardTask(paramFilter,currentUserId);
        Page page = paramFilter.getPage();
        return new Response(list,page);
    }

    @PostMapping("updateTaskNodeByUser")
    @ResponseBody
    @WebLogger("转寄任务")
    public Response updateTaskNodeByUser(@RequestBody Map paramMap){
        Response response = new Response();
        if(taskMsgService.updateTaskNodeByUser(paramMap)){
            response.setMsg("转寄成功！");
        }else{
            response.setCode(1);
            response.setMsg("转寄失败！");
        }
        return response;
    }

    /**
     * 创建任务
     * @param orderId
     * code 功能码
     * @throws Exception
     */
    @RequestMapping("createTask")
    public void createTask(String orderId,String code) throws Exception {
        Map map = new HashMap();
        if(orderId!=null){
            map = flowComService.getProcessByProId(code,orderId,1L,null);
        }
        System.out.println("----------"+map+"-----------");
    }

    /**
     * 提交任务
     * orderId 订单id
     * @throws Exception
     */
    @RequestMapping("commitTask")
    public void commitTask(String orderId,Integer status,String taskNodeId) throws Exception {
        if(orderId!=null&&taskNodeId!=null){
            flowComService.CommitTask(orderId,1L,status,taskNodeId,"",null);
        }
    }

    /**
     * 查询当前taskNodeId
     * @param orderId 订单id
     * @return
     */
    @RequestMapping("getTaskNodeId")
    public String getTaskNodeId(String orderId){
        return  flowComService.getTaskNodeId(orderId);
    }
}
