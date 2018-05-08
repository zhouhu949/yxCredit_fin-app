package com.zw.rule.web.merchantManagement.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zw.rule.commodity.po.Commodity;
import com.zw.rule.core.Response;
import com.zw.rule.merchantManagement.EnterMerchantManageService;
import com.zw.rule.merchantManagement.MerchantGrade;
import com.zw.rule.merchantManagement.MerchantListService;
import com.zw.rule.mybatis.ParamFilter;
import com.zw.rule.officeClerk.service.OfficeClerkManageService;
import com.zw.rule.po.User;
import com.zw.rule.service.DictService;
import com.zw.rule.web.util.PageConvert;
import com.zw.rule.web.util.UserContextUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/1/10.
 */
@Controller
@RequestMapping("enterMerchantManage")
public class enterMerchantManageController {
    @Resource
    private EnterMerchantManageService EMMService;

    /**
     * 商户列表的的商户列表信息展示
     * @return
     */
    @RequestMapping("enterMerchantManagePage")
    public String merchantListLsit() {
        return "merchantManagement/enterMerchantManage";
    }

    /**
     * 商户列表的列表展示
     * @param queryFilter
     * @return
     */
    @RequestMapping("enterMerchantManageList")
    @ResponseBody
    public Response getAllMerchantListList(@RequestBody ParamFilter queryFilter) {
        int pageNo = PageConvert.convert(queryFilter.getPage().getFirstIndex(), queryFilter.getPage().getPageSize());
        PageHelper.startPage(pageNo, queryFilter.getPage().getPageSize());
        Map map = queryFilter.getParam();
        PageInfo pageInfo = new PageInfo(EMMService.getEnterMerchants(map));
        return new Response(pageInfo);
    }


    /**
     * 订单列表的列表展示
     * @param queryFilter
     * @return
     */
    @RequestMapping("getMerchantOrdersList")
    @ResponseBody
    public Response getMerchantOrdersList(@RequestBody ParamFilter queryFilter) {
        int pageNo = PageConvert.convert(queryFilter.getPage().getFirstIndex(), queryFilter.getPage().getPageSize());
        PageHelper.startPage(pageNo, queryFilter.getPage().getPageSize());
        Map map = queryFilter.getParam();
        PageInfo pageInfo = new PageInfo(EMMService.getMerchantOrdersList(map));
        return new Response(pageInfo);
    }

    /**
     * 商品管理
     * @param
     * @return
     */
    @RequestMapping("merchantdiseManage")
    @ResponseBody
    public Response dealAntiFraudMerchant(@RequestBody ParamFilter queryFilter) {
        int pageNo = PageConvert.convert(queryFilter.getPage().getFirstIndex(), queryFilter.getPage().getPageSize());
        PageHelper.startPage(pageNo, queryFilter.getPage().getPageSize());
        Map map = queryFilter.getParam();
        PageInfo pageInfo = new PageInfo(EMMService.getMerchantMerchantdise(map));
        return new Response(pageInfo);
    }

    /**
     * 删除该商户下的商品
     * @param map
     * @return
     */
    @RequestMapping("deleteMerchantMerchandise")
    @ResponseBody
    public Response startOrstopState(@RequestBody Map map) {
        Response response = new Response();
        int i=EMMService.deleteMerGoods(map);
        if(i>0){
            response.setMsg("操作成功");
        }else{
            response.setMsg("操作失败");
        }
        return response;
    }
    /**
     * 获取商品图片
     * @param map
     * @return
     */
    @RequestMapping("getMerchantdiseImgs")
    @ResponseBody
    public Response getMerchantdiseImgs(@RequestBody Map map) {
        Response response = new Response();
        List<String> list=EMMService.getMerchantdiseImgs(map);
        response.setData(list);
        return response;
    }
}
