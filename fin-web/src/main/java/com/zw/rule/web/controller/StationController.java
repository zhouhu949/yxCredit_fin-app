package com.zw.rule.web.controller;

import com.zw.rule.core.Response;
import com.zw.rule.mybatis.ParamFilter;
import com.zw.rule.mybatis.page.Page;
import com.zw.rule.po.*;
import com.zw.rule.service.StationService;
import com.zw.rule.service.SysOrganizationService;
import com.zw.rule.web.aop.annotaion.WebLogger;
import com.zw.rule.web.util.UserContextUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/******************************************************
 *Copyrights @ 2017，zhiwang  Co., Ltd.
 *
 *All rights reserved.
 *          小窝
 *Filename：
 *          岗位管理controller
 *Description：
 *		  未知
 *Author:
 *		 李开艳
 *Finished：
 *		2017年6月12日
 ********************************************************/
@Controller
@RequestMapping("station")
public class StationController {

    @Resource
    public StationService stationService;

    @GetMapping("listPage")
    public String list() {
        return "systemMange/stationList";
    }

    @GetMapping("listPageU")
    public String list1() {
        return "systemMange/stationUser";
    }

    @ResponseBody
    @PostMapping("listU")
    @WebLogger("查询岗位设置列表")
    public Response list1(@RequestBody ParamFilter queryFilter) {
        List<StationUser> stations = stationService.selectAllSU(queryFilter);
        Page page = queryFilter.getPage();
        return new Response(stations,page);
    }

    @ResponseBody
    @PostMapping("list")
    @WebLogger("查询岗位列表")
    public Response list(@RequestBody ParamFilter queryFilter) {
        List<Station> stations = stationService.getAllStation(queryFilter);
        Page page = queryFilter.getPage();
        return new Response(stations,page);
    }

    /*@ResponseBody
    @PostMapping("validList")
    @WebLogger("查询有效的岗位列表")
    public Response validList() {
        List<Station> validList = stationService.getAllValidStation();
        return new Response(validList);
    }*/

    @ResponseBody
    @PostMapping({"add"})
    @WebLogger("添加岗位")
    public Response add(@RequestBody Map map) {
        Response response = new Response();
        User currentUser = (User) UserContextUtil.getAttribute("currentUser");
        List listOnly = stationService.validateStationOnly(map);
        if(listOnly != null && listOnly.size() > 0){
            response.setCode(1);
            response.setMsg("岗位名称已存在！");
        }else{
            if(stationService.createStation(map,currentUser)){
                response.setMsg("创建成功！");
            }else{
                response.setCode(1);
                response.setMsg("创建失败！");
            }
        }
        return  response;
    }

    @ResponseBody
    @PostMapping("detail")
    public Response detail(@RequestBody String id) {
        Station station = stationService.findById(id);
        return new Response(station);
    }

    @ResponseBody
    @PostMapping("edit")
    @WebLogger("编辑岗位")
    public Response edit(@RequestBody Map map) {
        Response response = new Response();
        User currentUser = (User) UserContextUtil.getAttribute("currentUser");
        if(stationService.updateStation(map,currentUser)){
            response.setMsg("修改成功！");
        }else{
            response.setCode(1);
            response.setMsg("修改失败！");
        }
        return  response;
    }

    @ResponseBody
    @PostMapping("updateStatus")
    @WebLogger("启、停用、删除岗位")
    public Response updateStatus(@RequestBody Map param) {
        Response response = new Response();
        if(stationService.updateStatus(param)){
            response.setMsg("操作成功！");
        }else{
            response.setCode(1);
            response.setMsg("操作失败！");
        }
        return response;
    }

    /**
     * 得到所有流程数据
     * @return
     */
    @ResponseBody
    @GetMapping("getProcess")
    public Response getProcess() {
        Response response = stationService.getProcess();
        return response;
    }

    /**
     * 根据流程id得到各自的流程节点
     * @param proId
     * @return
     */
    @ResponseBody
    @GetMapping("getProNodeByProId")
    public Response getProNodeByProId(String proId) {
        long processId = Long.valueOf(Long.parseLong(proId));
        Response response = stationService.getProNodeByProId(processId);
        return response;
    }

    @ResponseBody
    @PostMapping("getProcessByStationId")
    @WebLogger("查询岗位流程列表")
    public Response getProcessByStationId(@RequestBody ParamFilter queryFilter) {
        List stationProList = stationService.getProcessByStationId(queryFilter);
        Page page = queryFilter.getPage();
        return new Response(stationProList,page);
    }

    /**
     * 给某些用户设置岗位
     * @param addParam
     * @return
     */
    @ResponseBody
    @PostMapping("setUpStation")
    @WebLogger("设置岗位")
    public Response setUpStation(@RequestBody Map addParam) {
        Response response = new Response();
        if(stationService.createStationUser(addParam)){
            response.setMsg("设置添加成功！");
        }else{
            response.setCode(1);
            response.setMsg("设置添加失败！");
        }
        return response;
    }

    /**
     * 查找已设置岗位的用户
     * @param stationId
     * @return
     */
    @ResponseBody
    @PostMapping("getSUByStationId")
    public Response getSUByStationId(@RequestBody String stationId) {
        List list = stationService.selectSUByStationId(stationId);
        return new Response(list);
    }

    @ResponseBody
    @PostMapping("getAllStations")
    @WebLogger("查询岗位列表")
    public Response getAllStations() {
        List<Station> list=stationService.getAllStationNoParam();
        Response response=new Response();
        response.setData(list);
        return response;
    }
}
