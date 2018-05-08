package com.zw.rule.web.commodityMange.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zw.base.util.DateUtils;
import com.zw.rule.commodity.po.Commodity;
import com.zw.rule.commodityMange.service.CommodityService;
import com.zw.rule.core.Response;
import com.zw.rule.mybatis.ParamFilter;
import com.zw.rule.web.aop.annotaion.WebLogger;
import com.zw.rule.web.util.PageConvert;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Created by Administrator on 2017/12/15.
 */
@Controller
@RequestMapping("commodity")
public class commodityController {

    @Resource
    private CommodityService commodityService;
    @GetMapping("getPage")
    public  String getPage(){
        return  "commodityMange/commodity";
    }

    @PostMapping("addCommodity")
    @ResponseBody
    @WebLogger("添加商品")
    public Response addCommodity(@RequestBody Commodity commodity){
        Response response = new Response();
        Map map=new HashMap();
        map.put("id",commodity.getParentId());
        String merchanName=commodity.getMerchandiseName();
        List<Map> list =commodityService.getMerchantListInfo(map);
        for(int i=0;i<list.size();i++){
            Map commodityInfo=list.get(i);
            String goodName=commodityInfo.get("merchandiseName").toString();
            if(merchanName.equals(goodName)){
                response.setMsg("很抱歉,同一类型下不能有重复的商品名称");
                response.setCode(1);
                return response;
           }
        }
        commodity.setId(UUID.randomUUID().toString());
        commodity.setCreatTime(DateUtils.getCurrentTime(DateUtils.STYLE_10));
        commodity.setState("1");
        int num = commodityService.addCommodity(commodity);
        if (num > 0) {
            response.setMsg("添加成功！");
            response.setCode(0);
        }else {
            response.setMsg("添加失败！");
            response.setCode(1);
        }
        return response;
    }

    @PostMapping("updateCommodity")
    @ResponseBody
    @WebLogger("修改")
    public Response updateCommodity(@RequestBody Commodity commodity)throws Exception {
        Response response = new Response();
        Map map=new HashMap();
        map.put("id",commodity.getParentId());
        String merchanName=commodity.getMerchandiseName();
        List<Map> list =commodityService.getMerchantListInfo(map);
        for(int i=0;i<list.size();i++){
            Map commodityInfo=list.get(i);
            String goodName=commodityInfo.get("merchandiseName").toString();
            if(merchanName.equals(goodName) ){
                response.setMsg("很抱歉,同一类型下不能有重复的商品名称");
                response.setCode(1);
                return response;
            }
        }
        commodity.setAlterTime(DateUtils.getCurrentTime(DateUtils.STYLE_10));
        int num = commodityService.updateCommodity(commodity);
        if (num > 0) {
            response.setMsg("修改成功！");
            response.setCode(0);
        }else {
            response.setMsg("修改失败！");
            response.setCode(1);
        }
        return response;
    }


    @GetMapping("getCommodityMenu")
    @ResponseBody
    @WebLogger("获取商品集合")
    public Response getCommodityMenu(){
        Map<String,Object> map=new HashedMap();
        List<Commodity> list= commodityService.getCommodityMenu(map);
        Response response=new Response();
        response.setData(list);
        return  response;
    }

    @PostMapping("getCommodityList")
    @ResponseBody
    @WebLogger("获取商品集合")
    public Response getCommodityList(@RequestBody ParamFilter paramFilter){
        int pageNo = PageConvert.convert(paramFilter.getPage().getFirstIndex(), paramFilter.getPage().getPageSize());
        PageHelper.startPage(pageNo, paramFilter.getPage().getPageSize());
        List<Commodity> list= commodityService.getMerchantdiseList(paramFilter.getParam());
        PageInfo pageInfo = new PageInfo(list);
        return  new Response(pageInfo);
    }


    @PostMapping("getCommodity")
    @ResponseBody
    @WebLogger("获取商品列表")
    public Response getCommodity(@RequestBody Map map){
        List<Map> list =commodityService.getList(map);
        return new Response(list.get(0));
    }

    @PostMapping("getCommodity1")
    @ResponseBody
    @WebLogger("获取商品列表")
    public Response getCommodity1(@RequestBody Map map){
        List<Map> list =commodityService.getList1(map);
        return new Response(list.get(0));
    }

    @PostMapping("deleteCommodity")
    @ResponseBody
    @WebLogger("删除")
    public Response delete(@RequestBody Map map){
        Response response=new Response();
        int count=commodityService.delete(map.get("id").toString());
        if (count>0){
            response.setMsg("删除成功！");
        }else {
            response.setMsg("删除失败,您的商品处于启用中！");
        }
        return response;
    }
    @PostMapping("updateCommodityState")
    @ResponseBody
    @WebLogger("更新状态")
    public Response updateCommodityState(@RequestBody Map map) {
        Response response = new Response();
        int num = commodityService.updateState(map);
        if (num > 0) {
            response.setMsg("修改成功！");
        }else {
            response.setMsg("修改失败！");
        }
        return response;
    }


}
