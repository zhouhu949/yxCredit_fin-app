package com.zw.rule.web.red.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zw.base.util.DateUtils;
import com.zw.rule.core.Response;
import com.zw.rule.mybatis.ParamFilter;
import com.zw.rule.product.Fee;
import com.zw.rule.product.Red;
import com.zw.rule.product.RedInfo;
import com.zw.rule.red.service.RedService;
import com.zw.rule.web.aop.annotaion.WebLogger;
import com.zw.rule.web.util.PageConvert;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Administrator on 2017/11/17 0017.
 */
@Controller
@RequestMapping("red")
public class RedController {
    @Resource
    private RedService redService;

    @GetMapping("list")//红包
    public String list() {
        return "redManage/red";
    }

    @GetMapping("listInfo")//红包明细
    public String listInfo() {
        return "redManage/redInfo";
    }

    @PostMapping("redList")
    @ResponseBody
    @WebLogger("获取红包列表")
    public Response list(@RequestBody ParamFilter queryFilter){
        int pageNo = PageConvert.convert(queryFilter.getPage().getFirstIndex(),queryFilter.getPage().getPageSize());
        PageHelper.startPage(pageNo, queryFilter.getPage().getPageSize());
        Red red=new Red();
        List list =redService.getRedList(red);
        PageInfo pageInfo = new PageInfo(list);
        return new Response(pageInfo);
    }

    @PostMapping("redInfoList")
    @ResponseBody
    @WebLogger("获取红包明细列表")
    public Response redInfoList(@RequestBody ParamFilter queryFilter){
        int pageNo = PageConvert.convert(queryFilter.getPage().getFirstIndex(),queryFilter.getPage().getPageSize());
        PageHelper.startPage(pageNo, queryFilter.getPage().getPageSize());
        List<RedInfo> list =redService.getRedInfoList(queryFilter.getParam());
        PageInfo pageInfo = new PageInfo(list);
        return new Response(pageInfo);
    }

    @PostMapping("addRed")
    @ResponseBody
    @WebLogger("添加红包")
    public Response addQuato(@RequestBody Red red){
        Response response=new Response();
        response.setCode(0);
        red.setId(UUID.randomUUID().toString());
        red.setCreate_time(DateUtils.getDateString(new Date()));
        if(redService.addRed(red)>0){
            response.setMsg("添加成功！");
        }else {
            response.setMsg("添加失败！");
            response.setCode(1);
        }
        return response;
    }
    @PostMapping("updateRed")
    @ResponseBody
    @WebLogger("修改红包")
    public Response updateFee(@RequestBody Red red){
        Response response=new Response();
        if("1".equals(red.getStatus())){
            Map<String,Object> map=new HashedMap();
            map.put("status","1");
            List<Red> redList= redService.getList(map);
            if (redList.size()>0){
                response.setMsg("已经存在启用的红包！");
                return response;
            }
        }
        int num = redService.updateRed(red);
        response.setMsg("修改成功！");
        return response;
    }
    @PostMapping("getRed")
    @ResponseBody
    @WebLogger("获取红包")
    public Response getRed(@RequestBody Red red){
        List<Fee> list =redService.getRedList(red);
        return new Response(list.get(0));
    }

}
