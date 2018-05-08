package com.zw.rule.web.servicePackage.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zw.rule.core.Response;
import com.zw.rule.magServicePackage.Service.MagServicePackageService;
import com.zw.rule.magServicePackage.po.MagServicePackage;
import com.zw.rule.mybatis.ParamFilter;
import com.zw.rule.servicePackage.po.ServicePackage;
import com.zw.rule.servicePackage.service.ServicePackageService;
import com.zw.rule.web.aop.annotaion.WebLogger;
import com.zw.rule.web.util.PageConvert;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("magServicePackage")
public class MagServicePackageController {
    @Resource
    private MagServicePackageService magServicePackageService;
    @Resource
    private ServicePackageService servicePackageService;

    @GetMapping("magServicePackage")
    public String mag() {

        return "servicePackage/magServicePackagePage";
    }


    @PostMapping("magServicePackageList")
    @ResponseBody
    @WebLogger("查询服务包列表")
    public Response magServicePackageList(@RequestBody ParamFilter queryFilter) {
        int pageNo = PageConvert.convert(queryFilter.getPage().getFirstIndex(), queryFilter.getPage().getPageSize());
        PageHelper.startPage(pageNo, queryFilter.getPage().getPageSize());
        List<Map> list = magServicePackageService.getList(queryFilter.getParam());
        PageInfo pageInfo = new PageInfo(list);
        return new Response(pageInfo);
    }

    @PostMapping("magList")
    @ResponseBody
    @WebLogger("查询下拉")
    public Response getMagList() {
        Response response=new Response();
        List<ServicePackage>servicePackageList=servicePackageService.getMagList();
        response.setData(servicePackageList);
        return  response;
    }


    @PostMapping("updateMagServicePackage")
    @ResponseBody
    @WebLogger("修改")
    public Response updateMagServicePackage(@RequestBody MagServicePackage magServicePackage)throws Exception {
        Response response = new Response();
        Map<String,Object> map=new HashedMap();
        map.put("packagNeameKey",magServicePackage.getPackageName());
        List<Map> list = magServicePackageService.getList(map);
        if (list.size()>1){
            response.setCode(1);
            response.setMsg("服务包名称已存在！");
            return response;
        }
        if (list.size()==1){
            if (!((Map)list.get(0)).get("id").toString().equals(magServicePackage.getId())){
                response.setCode(1);
                response.setMsg("服务包名称已存在！");
                return response;
            }
        }
        int num = magServicePackageService.updateMagServicePackage(magServicePackage);
        if (num > 0) {
            response.setCode(0);
            response.setMsg("修改成功！");
        }else {
            response.setCode(1);
            response.setMsg("修改失败！");
        }
        return response;
    }

    @PostMapping("add")
    @ResponseBody
    @WebLogger("添加")
    public Response insertMagServicePackage(@RequestBody MagServicePackage magServicePackage) throws Exception{
        Response response = new Response();
        magServicePackage.setId(UUID.randomUUID().toString());
        Map<String,Object> map=new HashedMap();
        map.put("packagNeameKey",magServicePackage.getPackageName());
        List<Map> list = magServicePackageService.getList(map);
        if (list.size()>0){
            response.setCode(1);
            response.setMsg("服务包名称已存在！");
            return response;
        }
        int num = magServicePackageService.insertMagServicePackage(magServicePackage);

        if (num > 0) {
            response.setMsg("添加成功！");
        }else {
            response.setMsg("添加失败！");
        }
        return response;
    }



    @PostMapping("updatePackageState")
    @ResponseBody
    @WebLogger("更新状态")
    public Response updatePackageState(@RequestBody Map param) {
        Response response = new Response();
        int num = magServicePackageService .updateState(param);
        if (num > 0) {
            response.setMsg("修改成功！");
        }else {
            response.setMsg("修改失败！");
        }
        return response;
    }


    @PostMapping("getMagServicePackage")
    @ResponseBody
    @WebLogger("获取服务包数据")
    public Response getMagServicePackage(@RequestBody Map map){
        List<Map> list =magServicePackageService.getList(map);
        return new Response(list.get(0));
    }

    /*//动态获取下拉选
    @PostMapping("platformType")
    @ResponseBody
    public Response platformType(){
        Response response = new Response();
        List list=servicePackageService.getDetailList("平台类型");
        if (list!=null&&list.size()>0){
            response.setData(list);
        }else {
            return null;
        }
        return response;
    }*/
}
