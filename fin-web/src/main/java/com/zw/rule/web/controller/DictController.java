package com.zw.rule.web.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Preconditions;
import com.zw.rule.core.Response;
import com.zw.rule.mybatis.ParamFilter;
import com.zw.rule.po.Dict;
import com.zw.rule.service.DictService;
import com.zw.rule.web.aop.annotaion.WebLogger;
import com.zw.rule.web.util.PageConvert;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



@Controller
@RequestMapping("dict")
public class DictController {
    @Resource
    private DictService dictService;

    @GetMapping("list")
    public String list() {
        return "systemMange/dictList";
    }

    /**
     * 获取字典列表
     * @param queryFilter
     * @return
     */
    @ResponseBody
    @PostMapping("listDict")
    @WebLogger("查询字典列表")
    public Response list(@RequestBody ParamFilter queryFilter) {
        int pageNo = PageConvert.convert(queryFilter.getPage().getFirstIndex(),queryFilter.getPage().getPageSize());
        PageHelper.startPage(pageNo, queryFilter.getPage().getPageSize());
        List<Dict> list = dictService.getList(queryFilter);
        PageInfo pageInfo = new PageInfo(list);
        return new Response(pageInfo);
    }

    @ResponseBody
    @PostMapping("add")
    @WebLogger("添加字典")
    public Response add(@RequestBody Map map) {
        Response response = new Response();
        Map paramMap = new HashMap();
        paramMap.put("name",(String)map.get("name"));
        paramMap.put("code",(String)map.get("code"));
        paramMap.put("parentId",(String)map.get("parentId"));
        List listName = dictService.findByDictName(paramMap);//判断字段名称是否已经存在
        if(listName.size()>0){
            response.setMsg("字典名称已存在");
            response.setCode(1);
            return response;
        }
        List listCode = dictService.findByDictCode(paramMap);//判断字段Code是否已经存在
        if(listCode.size()>0){
            response.setMsg("字典Code已存在");
            response.setCode(1);
            return response;
        }
        dictService.add(map);
        response.setMsg("添加成功");
        return response;
    }
    @ResponseBody
    @PostMapping("edit")
    public Response edit(@RequestBody Dict dict) {
        dictService.update(dict);
        return Response.ok("修改成功！",null);
    }
    @ResponseBody
    @GetMapping("detail")
    @WebLogger("查询字典详细")
    public Response detail(String id) {
        Preconditions.checkNotNull(id, "不能为空");
        Dict dict = dictService.getById(id);
        return new Response(dict);
    }

    @ResponseBody
    @PostMapping("delete")
    @WebLogger("删除字典")
    public Response delete(@RequestBody Map map) {
        String id = (String)map.get("id");
        dictService.delete(id);
        Response response = new Response();
        response.setMsg("删除成功");
        return response;
    }

    @ResponseBody
    @GetMapping("getTree")
    @WebLogger("字典树")
    public Response getTree() {
        return new Response(dictService.getTree());
    }

    @ResponseBody
    @GetMapping("getByParentId")
    @WebLogger("查询字典列表")
    public Response getByParentId(String parentId) {
        List<Dict> dictList = dictService.getListByParentId(parentId);
        return new Response(dictList);
    }

    @ResponseBody
    @GetMapping("getCatagory")
    public Response getCatagory() {
        List<Dict> list = dictService.getCatagory();
        Response response = new Response();
        response.setData(list);
        return response;
    }
    /**
     * 风控期限配置列表
     */
    @GetMapping("windControlPeriodList")
    public String windControlPeriodList(){
        return  "systemMange/windControlPeriodList";
    }

    /**
     * 风控期限配置列表
     */
    @ResponseBody
    @GetMapping("findWindControlPeriodList")
    public Response findWindControlPeriodList(){
        List<Dict> list = dictService.getDetailListByCode("wcp");
        return Response.ok(list);
    }

}
