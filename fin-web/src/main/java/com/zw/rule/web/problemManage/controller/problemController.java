package com.zw.rule.web.problemManage.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zw.base.util.DateUtils;
import com.zw.rule.core.Response;
import com.zw.rule.mybatis.ParamFilter;
import com.zw.rule.problem.po.ProblemManage;
import com.zw.rule.problemService.service.ProblemService;
import com.zw.rule.service.AppDictService;
import com.zw.rule.service.DictService;
import com.zw.rule.web.aop.annotaion.WebLogger;
import com.zw.rule.web.util.PageConvert;
import org.apache.commons.collections.map.HashedMap;
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
@RequestMapping("problem")
public class problemController {

    @Resource
    private ProblemService problemService;
    @Resource
    private DictService dictService;

    @Resource
    private AppDictService appDictService;

    @GetMapping("list")//合同
    public String list() {
        return "problemManage/problem";
    }

    @PostMapping("problemList")
    @ResponseBody
    @WebLogger("问题列表")
    public Response orderList(@RequestBody ParamFilter queryFilter){
        int pageNo = PageConvert.convert(queryFilter.getPage().getFirstIndex(),queryFilter.getPage().getPageSize());
        PageHelper.startPage(pageNo, queryFilter.getPage().getPageSize());
        List list = problemService.getProblemList(queryFilter.getParam());
        PageInfo pageInfo = new PageInfo(list);
        return new Response(pageInfo);
    }

    @PostMapping("add")
    @ResponseBody
    @WebLogger("添加问题")
    public Response addproblem(@RequestBody ProblemManage problemManage){
        Response response = new Response();
        problemManage.setCreate_time(DateUtils.getCurrentTime(DateUtils.STYLE_10));
        problemManage.setId(UUID.randomUUID().toString());
        int num = problemService.addProblem(problemManage);
        if (num>0){
            response.setMsg("添加成功！");
        }else {
            response.setMsg("添加失败！");
        }
        return response;
    }

    @PostMapping("update")
    @ResponseBody
    @WebLogger("修改问题模板")
    public Response updateContract(@RequestBody ProblemManage problemManage){
        Response response=new Response();
        problemManage.setAlter_time(DateUtils.getCurrentTime(DateUtils.STYLE_10));
        int count=problemService.updateProblem(problemManage);
        if (count>0){
            response.setMsg("修改成功！");
        }else {
            response.setMsg("修改失败！");
        }
        return response;
    }

    @PostMapping("del")
    @ResponseBody
    @WebLogger("删除问题模板")
    public Response delproblem(@RequestBody Map map){
        Response response=new Response();
        int count=problemService.deleteProblem(map.get("id").toString());
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
        int count=problemService.updateState(map);
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
        ProblemManage problemManage=problemService.getById(param.get("id").toString());
        if (problemManage!=null){
            response.setData(problemManage);
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
        Map <String,Object> map=new HashedMap();
        map.put("dictName","问题类型");
        List list=appDictService.getListDictDetail(map);
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
