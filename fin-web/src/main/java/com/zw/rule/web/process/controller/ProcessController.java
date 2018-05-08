package com.zw.rule.web.process.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zw.rule.core.Response;
import com.zw.rule.engine.po.Engine;
import com.zw.rule.engine.po.EngineNode;
import com.zw.rule.mybatis.ParamFilter;
import com.zw.rule.process.po.Node;
import com.zw.rule.process.po.Process;
import com.zw.rule.process.service.ProcessService;
import com.zw.rule.web.aop.annotaion.WebLogger;
import com.zw.rule.web.util.PageConvert;
import com.zw.rule.web.util.UserContextUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * <strong>Title :<br>
 * </strong> <strong>Description : </strong>@类注释说明写在此处@<br>
 * <strong>Create on : 2017年06月13日<br>
 * </strong>
 * <p>
 * <strong>Copyright (C) Vbill Co.,Ltd.<br>
 * </strong>
 * <p>
 *
 * @author department:技术开发部 <br>
 *         username:何浩 <br>
 *         email: <br>
 * @version <strong>zw有限公司-运营平台</strong><br>
 *          <br>
 *          <strong>修改历史:</strong><br>
 *          修改人 修改日期 修改描述<br>
 *          -------------------------------------------<br>
 *          <br>
 *          <br>
 */
@Controller
@RequestMapping({"process"})
public class ProcessController {
    @Resource
    private ProcessService processService;
    @RequestMapping({"/toprocessList"})
    public String procedureList() {
        return "/procedureMange/procedureList";
    }
    @RequestMapping({"/list"})
    @ResponseBody
    public Response processIndex(@RequestBody ParamFilter queryFilter) {
        Long userId = UserContextUtil.getUserId();
        Long organId = UserContextUtil.getOrganId();
        queryFilter.getParam().put("userId", userId);
        int pageNo = PageConvert.convert(queryFilter.getPage().getFirstIndex(),queryFilter.getPage().getPageSize());
        int pageSize=queryFilter.getPage().getPageSize();
        String searchString=(String)queryFilter.getParam().get("searchString");
        Process process =new Process();
        process.setOrgId(organId);
        process.setSearchString(searchString);
        PageHelper.startPage(pageNo, pageSize);
        List list = this.processService.getProcessByList(process);
        PageInfo pageInfo = new PageInfo(list);
        return new Response(pageInfo);
    }
    /**
     * 显示详情
     * @param id
     * @return
     */
    @RequestMapping({"initupdate"})
    @ResponseBody
    public Response initupdate(String id) {
        Response response = new Response();
        Process processVo = new Process();
        processVo.setId(Long.valueOf(Long.parseLong(id)));
        Long organId = UserContextUtil.getOrganId();
        processVo.setOrgId(organId);
        processVo = processService.getProcessById(processVo);
        if(processVo==null){
            response.setMsg("没有该流程");
        }else{
            response.setData(processVo);
        }
        return response;
    }
    @RequestMapping({"update"})
    @ResponseBody
    public Response update(@RequestBody Process process , HttpServletRequest request) {
        //获取上下文路径
        String url = request.getContextPath();
        Long userId = UserContextUtil.getUserId();
        Long organId = UserContextUtil.getOrganId();
        process.setOrgId(organId);
        Response response=new Response();
        process.setCreator(userId);
        process.setStatus(1);
        process.setUserId(userId);
        if(process.getId() == null) {
            if(processService.saveProcess(process , url)){
                response.setMsg("添加成功！");
            }else response.setMsg("添加失败！");
        } else {
            if(processService.updateProcess(process)>0){
                response.setMsg("修改成功！");
            }else response.setMsg("修改失败！");
        }
        return response;
    }
    @RequestMapping({"/toprocess"})
    public String procedure() {
        return "/procedureMange/procedure";
    }

    @RequestMapping({"/getUUID"})
    @ResponseBody
    public Response getUUID() {
        HashMap map = new HashMap();
        map.put("uuid", UUID.randomUUID());
        map.put("result", "1");
        return new Response(map);
    }
    @RequestMapping({"/getType"})
    @ResponseBody
    public Response getType() {
        HashMap map = new HashMap();
        List typelist=null;//数据字典取
        map.put("typelist", typelist);
        return new Response(map);
    }
}

