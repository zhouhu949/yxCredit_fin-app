package com.zw.rule.web.activity.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zw.base.util.DateUtils;
import com.zw.base.util.GeneratePrimaryKeyUtils;
import com.zw.rule.activity.Activity;
import com.zw.rule.activity.service.ActivityService;
import com.zw.rule.contractor.service.ContractorService;
import com.zw.rule.core.Response;
import com.zw.rule.mybatis.ParamFilter;
import com.zw.rule.service.DictService;
import com.zw.rule.web.aop.annotaion.WebLogger;
import com.zw.rule.upload.service.UploadService;
import com.zw.rule.web.util.PageConvert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
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

    @Value("${byx.img.path}")
    private String imgUrl;
    @Resource
    private ActivityService activityService;


    @Resource
    private DictService dictService;

    @Resource
    private UploadService uploadService;

    @Autowired
    private ContractorService contractorService;

    @GetMapping("index")
    public String list() {
        return "activity/activityPage";
    }


    /**
     * 获取Banner列表
     * @author 仙海峰
     * @param queryFilter
     * @return
     */
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


    /**
     * 修改Banner
     * @author 仙海峰
     * @param activity
     * @param request
     * @return
     * @throws Exception
     */
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

    /**
     * 添加Banner
     * @author 仙海峰
     * @param activity
     * @param request
     * @return
     * @throws Exception
     */
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

    /**
     * 删除Banner
     * @author 仙海峰
     * @param param
     * @return
     */
    @PostMapping("del")
    @ResponseBody
    @WebLogger("删除banner")
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

    /**
     * 修改Banner 状态
     * @author 仙海峰
     * @param param
     * @return
     */
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

    /**
     * 查看Banner
     * @author 仙海峰
     * @param param
     * @return
     */
    @PostMapping("lookActivity")
    @ResponseBody
    @WebLogger("获取活动详细")
    public Response lookActivity(@RequestBody Map param) {
        Response response = new Response();
        String id = param.get("id").toString();
        Activity activity = activityService.lookActivity(id);
        response.setData(activity);
        return response;
    }


    /**
     * 上传Banner 图片
     * @author 仙海峰
     * @param request
     * @return
     * @throws Exception
     */
    @ResponseBody
    @PostMapping("merUpload")
    public Response upload(HttpServletRequest request) throws Exception{
        Response response = new Response();
        List list = activityService.uploadaBannerImage(request);
        if(!list.isEmpty()){
            response.setMsg("上传成功！");
            response.setData(list.get(0));
        }else{
            response.setCode(1);
            response.setMsg("上传失败！");
        }
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


    /**
     * 获取图片URL
     * @param bannerImg
     * @param response
     * @throws IOException
     */
    @RequestMapping("/byx/imgUrl")
    public void getUploadUrl(@RequestParam String bannerImg, HttpServletResponse response) throws IOException {
        try{
            String  imgPath = imgUrl + bannerImg;
            FileInputStream hFile = new FileInputStream(imgPath);
            int i=hFile.available(); //得到文件大小
            byte data[]=new byte[i];
            hFile.read(data); //读数据
            hFile.close();
            response.setContentType("image/*"); //设置返回的文件类型
            OutputStream toClient=response.getOutputStream(); //得到向客户端输出二进制数据的对象
            toClient.write(data); //输出数据
            toClient.close();
        }
        catch(IOException e) //错误处理
        {
            e.printStackTrace();
            PrintWriter toClient = response.getWriter(); //得到向客户端输出文本的对象
            response.setContentType("text/html;charset=gb2312");
            toClient.write("无法打开!");
            toClient.close();
        }
    }

}