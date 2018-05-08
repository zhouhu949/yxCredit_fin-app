package com.zw.rule.web.testdemo.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zw.rule.core.Response;
import com.zw.rule.engine.po.Engine;
import com.zw.rule.engine.service.EngineService;
import com.zw.rule.mybatis.ParamFilter;
import com.zw.rule.web.aop.annotaion.WebLogger;
import com.zw.rule.web.util.PageConvert;
import com.zw.rule.web.util.UserContextUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * Created by Administrator on 2017/6/7.
 */
@Controller
@RequestMapping({"engine"})
public class EngineController {
    @Resource
    private EngineService engineService;
    @RequestMapping({"/tolist"})
    public String testInfo() {
        return "/testdemo/testdemo";
    }
    @RequestMapping({"/toprocedureList"})
    public String procedureList() {
        return "/procedureMange/procedureList";
    }
    @RequestMapping({"/list"})
    @WebLogger("流程列表")
    @ResponseBody
    public Response enginIndex(@RequestBody ParamFilter queryFilter) {
        Long userId = UserContextUtil.getUserId();
        Long organId = UserContextUtil.getOrganId();
        queryFilter.getParam().put("userId", userId);
        int pageNo = PageConvert.convert(queryFilter.getPage().getFirstIndex(),queryFilter.getPage().getPageSize());
        int pageSize=queryFilter.getPage().getPageSize();
        String searchString=(String)queryFilter.getParam().get("searchString");
        Engine engine =new Engine();
        engine.setOrgId(organId);
        engine.setSearchString(searchString);
        PageHelper.startPage(pageNo, pageSize);
        List list = this.engineService.getEngineByList(engine);
        PageInfo pageInfo = new PageInfo(list);
        return new Response(pageInfo);
    }
    @RequestMapping({"/toprocedure"})
    public String procedure() {
        return "/procedureMange/procedure";
    }



    @RequestMapping({"/getUUID"})
    @WebLogger("创建流程编码")
    @ResponseBody
    public Response getUUID() {
        HashMap map = new HashMap();
        map.put("uuid", UUID.randomUUID());
        map.put("result", "1");
        return new Response(map);
    }

    @RequestMapping({"update"})
    @WebLogger("保存或修改流程")
    @ResponseBody
    public Response update(@RequestBody Engine engine , HttpServletRequest request) {
        //获取上下文路径
        String url = request.getContextPath();
        Long userId = UserContextUtil.getUserId();
        Long organId = UserContextUtil.getOrganId();
        engine.setOrgId(organId);
        Response response=new Response();
        engine.setCreator(userId);
        engine.setStatus(1);
        engine.setUserId(userId);
        if(engine.getId() == null) {
            if(engineService.saveEngine(engine , url)){
                response.setMsg("添加成功！");
            }else response.setMsg("添加失败！");
            //this.createKnowledgeTree(engine);添加知识树
        } else {
            if(engineService.updateEngine(engine)>0){
                response.setMsg("修改成功！");
            }else response.setMsg("修改失败！");
        }
        return response;
    }
}
