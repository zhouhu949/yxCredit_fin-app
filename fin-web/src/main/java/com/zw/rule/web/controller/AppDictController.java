package com.zw.rule.web.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Preconditions;
import com.zw.rule.core.Response;
import com.zw.rule.mybatis.ParamFilter;
import com.zw.rule.po.MagDict;
import com.zw.rule.po.MagDictDetail;
import com.zw.rule.service.AppDictService;
import com.zw.rule.web.aop.annotaion.WebLogger;
import com.zw.rule.web.util.PageConvert;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("appDict")
public class AppDictController {
    @Resource
    private AppDictService dictService;

    @GetMapping("list")
    public String list() {
        return "systemMange/appDictList";
    }

    /**
     * 获取字典大类列表
     * @param queryFilter
     * @return
     */
    @ResponseBody
    @PostMapping("listDict")
    @WebLogger("查询字典列表")
    public Response list(@RequestBody ParamFilter queryFilter) {
        Map map = queryFilter.getParam();
        int pageNo = PageConvert.convert(queryFilter.getPage().getFirstIndex(),queryFilter.getPage().getPageSize());
        PageHelper.startPage(pageNo, queryFilter.getPage().getPageSize());
        List<MagDict> list = dictService.getList(map);
        PageInfo pageInfo = new PageInfo(list);
        return new Response(pageInfo);
    }

    /**
     * 获取字典明显列表
     * @param queryFilter
     * @return
     */
    @ResponseBody
    @PostMapping("listDictDetail")
    @WebLogger("查询字典明细列表")
    public Response listDictDetail(@RequestBody ParamFilter queryFilter) {
        Map map = queryFilter.getParam();
        int pageNo = PageConvert.convert(queryFilter.getPage().getFirstIndex(),queryFilter.getPage().getPageSize());
        PageHelper.startPage(pageNo, queryFilter.getPage().getPageSize());
        List<MagDictDetail> list = dictService.getListDictDetail(map);
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
    @PostMapping("addDetail")
    @WebLogger("添加字典明细")
    public Response addDetail(@RequestBody Map map) {
        Response response = new Response();
        Map paramMap = new HashMap();
        paramMap.put("name",(String)map.get("name"));
        paramMap.put("code",(String)map.get("code"));
        paramMap.put("dictId",(String)map.get("dictId"));
        List listName = dictService.findDetailByDictName(paramMap);//判断字段名称是否已经存在
        if(listName.size()>0){
            response.setMsg("字典名称已存在");
            response.setCode(1);
            return response;
        }
        List listCode = dictService.findDetailByDictCode(paramMap);//判断字段Code是否已经存在
        if(listCode.size()>0){
            response.setMsg("字典Code已存在");
            response.setCode(1);
            return response;
        }
        dictService.addDetail(map);
        response.setMsg("添加成功");
        return response;
    }

    @ResponseBody
    @PostMapping("edit")
    @WebLogger("修改字典")
    public Response edit(@RequestBody Map map) {
        Response response = new Response();
        String code = (String)map.get("code");
        List<String> listCode = dictService.findByDictId(map);
        for(int i=0;i<listCode.size();i++){
            String resultCode = (String)listCode.get(i);
            if(code.equals(resultCode)){
                response.setMsg("字典名称已存在");
                response.setCode(1);
                return response;
            }
        }
        dictService.update(map);
        response.setMsg("修改成功");
        return response;
    }

    @ResponseBody
    @PostMapping("editDetail")
    public Response editDetail(@RequestBody Map map) {
        Response response = new Response();
        String code = (String)map.get("code");
        List<String> listCode = dictService.findByDictDetailId(map);
        for(int i=0;i<listCode.size();i++){
            String resultCode = (String)listCode.get(i);
            if(code.equals(resultCode)){
                response.setMsg("字典名称已存在");
                response.setCode(1);
                return response;
            }
        }
        dictService.updateDetail(map);
        response.setMsg("修改成功");
        return response;
    }
    @ResponseBody
    @GetMapping("detail")
    @WebLogger("查询字典详细")
    public Response detail(String id) {
        Preconditions.checkNotNull(id, "不能为空");
        MagDict dict = dictService.getById(id);
        return new Response(dict);
    }

    @ResponseBody
    @GetMapping("details")
    @WebLogger("查询字典明细详细")
    public Response details(String id) {
        Preconditions.checkNotNull(id, "不能为空");
        MagDictDetail dict = dictService.getByDetailId(id);
        return new Response(dict);
    }

    @ResponseBody
    @PostMapping("delete")
    @WebLogger("删除字典大类")
    public Response delete(@RequestBody Map map) {
        String id = (String)map.get("id");
        dictService.delete(id);
        Response response = new Response();
        response.setMsg("删除成功");
        return response;
    }
    @ResponseBody
    @PostMapping("deleteDetail")
    @WebLogger("删除字典明细")
    public Response deleteDetail(@RequestBody Map map) {
        String id = (String)map.get("id");
        dictService.deleteDetail(id);
        Response response = new Response();
        response.setMsg("删除成功");
        return response;
    }

//    @ResponseBody
//    @GetMapping("getTree")
//    @WebLogger("字典树")
//    public Response getTree() {
//        return new Response(dictService.getTree());
//    }
//
//    @ResponseBody
//    @GetMapping("getByParentId")
//    @WebLogger("查询字典列表")
//    public Response getByParentId(String parentId) {
//        List<Dict> dictList = dictService.getListByParentId(parentId);
//        return new Response(dictList);
//    }
//
    @ResponseBody
    @GetMapping("getCatagory")
    public Response getCatagory() {
        List<MagDict> list = dictService.getCatagory();
        Response response = new Response();
        response.setData(list);
        return response;
    }


}
