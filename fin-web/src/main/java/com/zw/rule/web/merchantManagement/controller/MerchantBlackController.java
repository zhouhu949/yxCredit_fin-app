package com.zw.rule.web.merchantManagement.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zw.base.util.DateUtils;
import com.zw.rule.core.Response;
import com.zw.rule.merchantBlack.service.MerchantBlackService;
import com.zw.rule.merchantBlackEntity.MerchantBlack;
import com.zw.rule.mybatis.ParamFilter;
import com.zw.rule.web.util.PageConvert;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Administrator on 2017/12/8.
 */
@Controller
@RequestMapping("merchantBlack")
public class MerchantBlackController {
    @Resource
    private MerchantBlackService MBService;

    /**
     * 跳转商户黑名单列表界面方法
     *
     * @return
     */
    @RequestMapping("merchantBlackList")
    public String merchantBlack() {
        return "merchantBlack/merchantBlack";
    }

    /**
     * 展示全部的信息列表
     *
     * @param queryFilter
     * @return
     */
    @RequestMapping("showMerchantBlackList")
    @ResponseBody
    public Response showAreaQuotaList(@RequestBody ParamFilter queryFilter) {
        int pageNo = PageConvert.convert(queryFilter.getPage().getFirstIndex(), queryFilter.getPage().getPageSize());
        PageHelper.startPage(pageNo, queryFilter.getPage().getPageSize());
        Map map = queryFilter.getParam();
        PageInfo pageInfo = new PageInfo(MBService.getAllMerchantBlack(map));
        return new Response(pageInfo);
    }

    /**
     * 添加商户黑名单设置访问方法
     */
    @RequestMapping("addMerchantBlack")
    @ResponseBody
    public Response addQuota(@RequestBody MerchantBlack merchantBlack) {
        Response response = new Response();
        String id = UUID.randomUUID().toString();
        merchantBlack.setId(id);
        merchantBlack.setCreateTime(DateUtils.getCurrentTime(DateUtils.STYLE_10));
        int i= MBService.addoneMerchantBlack(merchantBlack);
        if(i>0){
            response.setMsg("添加成功~");
        }else{
            response.setMsg("添加失败~");
        }
        return response;
    }
    /**
     * 停用或启用
     */
    @RequestMapping("startOrstopState")
    @ResponseBody
    public Response startOrstopState(@RequestBody Map map) {
        Response response = new Response();
        int i=MBService.startOrStopState(map);
        if(i>0){
            response.setMsg("更改成功~");
        }else{
            response.setMsg("更改失败~");
        }
        return response;
    }
    /**
     * 根据id查找单个商户黑名单设置信息
     */
    @RequestMapping("getOneMerchantBlackById")
    @ResponseBody
    public Response getOneMerchantBlackById(@RequestBody Map map) {
        Response response = new Response();
        MerchantBlack mb=MBService.getOneBlackById(map.get("id")+"");
        response.setData(mb);
        return response;
    }

    /**
     * 根据id来更改单个商户黑名单信息
     * @param merchantBlack
     * @return
     */
    @RequestMapping("editOneMerchantBlackById")
    @ResponseBody
    public Response editOneMerchantBlackById(@RequestBody MerchantBlack merchantBlack) {
        Response response = new Response();
        int i= MBService.changeBlackById(merchantBlack);
        if(i>0){
            response.setMsg("修改成功~");
        }else{
            response.setMsg("修改失败~");
        }
        return response;
    }

    /**
     * 根据id删除一条数据
     */
    @RequestMapping("deleteOneById")
    @ResponseBody
    public Response deleteOneById(@RequestBody Map map) {
        Response response = new Response();
        int i=MBService.deleteBlackById(map);
        if(i>0){
            response.setMsg("删除成功~");
        }else{
            response.setMsg("删除失败~");
        }
        return response;
    }
}