package com.zw.rule.web.quato.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zw.base.util.DateUtils;
import com.zw.rule.core.Response;
import com.zw.rule.mybatis.ParamFilter;
import com.zw.rule.product.ProYield;
import com.zw.rule.quato.service.impl.IQuatoService;
import com.zw.rule.web.aop.annotaion.WebLogger;
import com.zw.rule.web.util.PageConvert;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by Administrator on 2017/11/17 0017.
 */
@Controller
@RequestMapping("quato")
public class QuatoController {
    @Resource
    private IQuatoService iQuatoService;

    @GetMapping("list")//合同
    public String list() {
        return "quatoManage/quato";
    }

    @PostMapping("quatoList")
    @ResponseBody
    @WebLogger("获取额度列表")
    public Response list(@RequestBody ParamFilter queryFilter){
        int pageNo = PageConvert.convert(queryFilter.getPage().getFirstIndex(),queryFilter.getPage().getPageSize());
        PageHelper.startPage(pageNo, queryFilter.getPage().getPageSize());
        ProYield proYield = new ProYield();
        List list =iQuatoService.getQuato(proYield);
        PageInfo pageInfo = new PageInfo(list);
        return new Response(pageInfo);
    }
    @PostMapping("addQuato")
    @ResponseBody
    @WebLogger("添加额度")
    public Response addQuato(@RequestBody ProYield proYield){
        Response response=new Response();
        response.setCode(0);
        proYield.setId(UUID.randomUUID().toString());
        proYield.setCreate_time(DateUtils.getDateString(new Date()));
        if(iQuatoService.addQuato(proYield)>0){
            response.setMsg("添加成功！");
        }else {
            response.setMsg("添加失败！");
            response.setCode(1);
        }
        return response;
    }
    @PostMapping("updateQuato")
    @ResponseBody
    @WebLogger("修改额度")
    public Response updateFee(@RequestBody ProYield proYield){
        Response response=new Response();
//        if ("1".equals(proYield.getStatus())){
//            Map<String,Object> map=new HashedMap();
//            map.put("status","1");
//            List<ProYield>  proYieldList=iQuatoService.getList(map);
//            if (proYieldList.size()>0){
//                response.setMsg("已经存在启用的额度！");
//                return response;
//            }
//        }
        List list = null;
        int num = iQuatoService.updateQuato(proYield);
        response.setMsg("修改成功！");
        return response;
    }
    @PostMapping("getQuato")
    @ResponseBody
    @WebLogger("获取额度")
    public Response getQuato(@RequestBody ProYield proYield){
        List<ProYield> list =iQuatoService.getQuato(proYield);
        return new Response(list.get(0));
    }

    @PostMapping("getQuatoList")
    @ResponseBody
    @WebLogger("获取额度")
    public Response getQuatoList(){
        List<ProYield> list =iQuatoService.getQuatoList();
        return new Response(list.get(0));
    }

}
