package com.zw.rule.web.activity.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zw.base.util.DateUtils;
import com.zw.rule.activity.Activity;
import com.zw.rule.activity.service.ActivityService;
import com.zw.rule.core.Response;
import com.zw.rule.mybatis.ParamFilter;
import com.zw.rule.service.DictService;
import com.zw.rule.web.aop.annotaion.WebLogger;
import com.zw.rule.upload.service.UploadService;
import com.zw.rule.web.util.PageConvert;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Administrator on 2017/9/16.
 */
@Controller
@RequestMapping("activity")
public class ActivityController {
    @Resource
    private ActivityService activityService;


    @Resource
    private DictService dictService;

    @Resource
    private UploadService uploadService;

    @GetMapping("index")
    public String list() {
        return "activity/activityPage";
    }

    @PostMapping("list")
    @ResponseBody
    @WebLogger("查询活动列表")
    public Response activityList(@RequestBody ParamFilter queryFilter) {
        int pageNo = PageConvert.convert(queryFilter.getPage().getFirstIndex(), queryFilter.getPage().getPageSize());
        PageHelper.startPage(pageNo, queryFilter.getPage().getPageSize());
        List<Activity> list = activityService.getList(queryFilter.getParam());
        PageInfo pageInfo = new PageInfo(list);
        return new Response(pageInfo);
    }


    @PostMapping("update")
    @ResponseBody
    @WebLogger("修改活动")
    public Response updateActivity(@RequestBody Activity activity,HttpServletRequest request)throws Exception {
        Response response = new Response();
        activity.setAlter_time(DateUtils.getCurrentTime(DateUtils.STYLE_10));
        int num = activityService.updateActivity(activity);
        if (num > 0) {
            response.setMsg("修改成功！");
        }else {
            response.setMsg("修改失败！");
        }
        return response;
    }

    @PostMapping("add")
    @ResponseBody
    @WebLogger("添加活动")
    public Response addActivity(@RequestBody Activity activity,HttpServletRequest request) throws Exception{
        Response response = new Response();
        activity.setCreate_time(DateUtils.getCurrentTime(DateUtils.STYLE_10));
        int num = activityService.insertActivity(activity);
        if (num > 0) {
            response.setMsg("添加成功！");
        }else {
            response.setMsg("添加失败！");
        }
        return response;
    }

    @PostMapping("del")
    @ResponseBody
    @WebLogger("删除活动")
    public Response delActivity(@RequestBody Map param) {
        Response response = new Response();
        String id = param.get("id").toString();
        int num = activityService.delActivity(id);
        if (num > 0) {
            response.setMsg("删除成功！");
        }else {
            response.setMsg("删除失败！");
        }
        return response;
    }

    @PostMapping("updateState")
    @ResponseBody
    @WebLogger("活动上下架")
    public Response updateState(@RequestBody Map param) {
        Response response = new Response();
        int num = activityService.updateState(param);
        if (num > 0) {
            response.setMsg("修改成功！");
        }else {
            response.setMsg("修改失败！");
        }
        return response;
    }

    @PostMapping("lookActivity")
    @ResponseBody
    @WebLogger("获取活动详细")
    public Response lookActivity(@RequestBody Map param) {
        Response response = new Response();
        String id = param.get("id").toString();
        Activity activity = activityService.lookActivity(id);
        String name = dictService.getDetilNameByCode("ceshi",dictService.getListByCode("host").get(0).getId().toString());
        activity.setActivity_img_url(activity.getActivity_img_url());
        activity.setHost(name);
        response.setData(activity);
        return response;
    }


    @ResponseBody
    @PostMapping("merUpload")
    public Response upload(HttpServletRequest request) throws Exception{
        Response response = new Response();
        Map param = new HashMap();
        String fileName="";
        //String doc="";
        String id = UUID.randomUUID().toString();
        //获取根目录
        String root = request.getSession().getServletContext().getRealPath("/fintecher_file");
        //捕获前台传来的图片，并用uuid命名，防止重复
        Map<String, Object> allMap = uploadService.uploadFileOSS(request,"activity");
        List<Map<String, Object>> list = (List<Map<String, Object>>) allMap.get("fileList");
        //当前台有文件时，给图片名称变量赋值
        fileName = "fintecher_file/activity/"+(String) allMap.get("Name");
        response.setMsg("上传成功！");
        param.put("activity_img_id",id);
        param.put("activity_img_fileName",(String) allMap.get("url"));
        response.setData(param);
        return response;
    }


    //动态获取下拉选
    @PostMapping("imgSelect")
    @ResponseBody
    public Response apendSelect(){
        Response response = new Response();
        List list=dictService.getDetailList("图片位置");
        if (list!=null&&list.size()>0){
            response.setData(list);
        }else {
            return null;
        }
        return response;
    }

    //动态获取下拉选
    @PostMapping("platformType")
    @ResponseBody
    public Response platformType(){
        Response response = new Response();
        List list=dictService.getDetailList("平台类型");
        if (list!=null&&list.size()>0){
            response.setData(list);
        }else {
            return null;
        }
        return response;
    }

}