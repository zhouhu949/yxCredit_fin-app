package com.zw.rule.web.areaQuota.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zw.rule.areaQuota.AreaQuota;
import com.zw.rule.areaQuota.service.AreaQuotaService;
import com.zw.rule.core.Response;
import com.zw.rule.mybatis.ParamFilter;
import com.zw.rule.web.util.PageConvert;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by Administrator on 2017/12/6.
 * 区域限额配置的控制层方法
 */
@Controller
@RequestMapping("areaQuota")
public class AreaQuotaController {
    @Resource
    private AreaQuotaService AQService;
    /**
     * 跳转区域限额列表界面方法
     * @return
     */
    @RequestMapping("areaQuotaListPage")
    public String areQuotaList(){
        return("areaQuota/areaQuota");
    }

    /**
     * 展示全部的信息列表
     * @param queryFilter
     * @return
     */
    @RequestMapping("showAreaQuotaList")
    @ResponseBody
    public Response showAreaQuotaList(@RequestBody ParamFilter queryFilter){
        int pageNo = PageConvert.convert(queryFilter.getPage().getFirstIndex(),queryFilter.getPage().getPageSize());
        PageHelper.startPage(pageNo, queryFilter.getPage().getPageSize());
        Map map=queryFilter.getParam();
        //System.out.println(map);
        List list=AQService.getAllQuota(map);
        PageInfo pageInfo = new PageInfo(list);
        return new Response(pageInfo);
    }
    /**
     * 添加区域限额访问方法
     */
    @RequestMapping("addQuota")
    @ResponseBody
    public Response addQuota(@RequestBody AreaQuota areaQuota){
        Response response =new Response();
        String id= UUID.randomUUID().toString();
        areaQuota.setId(id);
        List<AreaQuota> quotaList=AQService.getAllQuota(new HashMap());
        List<String> cityIds=new ArrayList<String>();
        for(int i=0;i<quotaList.size();i++){
            cityIds.add(quotaList.get(i).getCityId());
        }
        //判断该城市之前是否存在过，存在过的话就不再添加
        if(cityIds.contains(areaQuota.getCityId())){
            response.setMsg("该城市已经被添加，请在编辑中进行修改~");
            return response;
        //不存在才进行添加
        }else{
            int i= AQService.addAreaQuota(areaQuota);
            if(i>0){
                response.setMsg("添加成功~");
                return response;
            }else{
                response.setMsg("添加失败~");
                return response;
            }
        }
    }
    /**
     * showOneQuota
     * 按照id来查找区域限制
     */
    @RequestMapping("showOneQuota")
    @ResponseBody
    public Response showOneQuota(@RequestBody Map map){
        Response response =new Response();
        String id=String.valueOf(map.get("id"));
        AreaQuota areaQuota=AQService.getOneAreaQuotaByID(id);
        response.setData(areaQuota);
        return response;
    }

    /**
     * changeOneQuota修改一条区域限额信息
     */
    @RequestMapping("changeOneQuota")
    @ResponseBody
    public Response changeOneQuota(@RequestBody AreaQuota areaQuota){
        Response response=new Response();
        int i= AQService.updateAreaQuotaById(areaQuota);
        if(i>0){
            response.setMsg("修改成功~");
        }else{
            response.setMsg("修改失败~");

        }
        return response;
    }
    /**
     * startOrStopQuota停用或者启用按钮
     */
    @RequestMapping("startOrStopQuota")
    @ResponseBody
    public Response startOrStopQuota(@RequestBody Map map){
        Response response=new Response();
        int i=AQService.changeAreQuotaState(map);
        if(i>0){
            response.setMsg("更改成功~");
        }else{
            response.setMsg("更改失败~");
        }
        return response;
    }
}
