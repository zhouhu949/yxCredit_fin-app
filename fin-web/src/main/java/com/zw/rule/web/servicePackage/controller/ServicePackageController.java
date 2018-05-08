package com.zw.rule.web.servicePackage.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zw.base.util.DateUtils;
import com.zw.rule.core.Response;
import com.zw.rule.mybatis.ParamFilter;
import com.zw.rule.service.DictService;
import com.zw.rule.servicePackage.po.ServicePackage;
import com.zw.rule.servicePackage.service.ServicePackageService;
import com.zw.rule.web.aop.annotaion.WebLogger;
import com.zw.rule.web.util.PageConvert;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.UUID;


/**
 * Created by Administrator on 2017/11/28.
 */
@Controller
@RequestMapping("servicePackage")
public class ServicePackageController {
    @Resource
    private ServicePackageService servicePackageService;

    @GetMapping("servicePackage")
    public String magServicePackageList() {
        return "servicePackage/servicePackagePage";
    }


    @PostMapping("servicePackageList")
    @ResponseBody
    @WebLogger("查询服务包列表")
    public Response servicePackageList(@RequestBody ParamFilter queryFilter) {
        int pageNo = PageConvert.convert(queryFilter.getPage().getFirstIndex(), queryFilter.getPage().getPageSize());
        PageHelper.startPage(pageNo, queryFilter.getPage().getPageSize());
        List<ServicePackage> list = servicePackageService.getList(queryFilter.getParam());
        PageInfo pageInfo = new PageInfo(list);
        return new Response(pageInfo);
    }


    @PostMapping("updateServicePackage")
    @ResponseBody
    @WebLogger("修改")
    public Response updateServicePackage(@RequestBody ServicePackage servicePackage,HttpServletRequest request)throws Exception {
        Response response = new Response();
        servicePackage.setAlter_time(DateUtils.getCurrentTime(DateUtils.STYLE_10));

        int num = servicePackageService.updateServicePackage(servicePackage);
        if (num > 0) {
            response.setMsg("修改成功！");
        }else {
            response.setMsg("修改失败！");
        }
        return response;
    }


    @PostMapping("getServicePackage")
    @ResponseBody
    @WebLogger("获取服务包数据")
    public Response getServicePackage(@RequestBody Map map){
        List<ServicePackage> list =servicePackageService.getList(map);
        return new Response(list.get(0));
    }

}