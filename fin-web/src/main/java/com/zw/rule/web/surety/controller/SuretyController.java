package com.zw.rule.web.surety.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zw.base.util.HttpClientUtil;
import com.zw.rule.core.Response;
import com.zw.rule.coupon.po.Recommend;
import com.zw.rule.couponService.service.CouponRecommendService;
import com.zw.rule.customer.po.Order;
import com.zw.rule.mybatis.ParamFilter;
import com.zw.rule.po.Dict;
import com.zw.rule.po.User;
import com.zw.rule.service.DictService;
import com.zw.rule.surety.Surety;
import com.zw.rule.surety.service.SuretyService;
import com.zw.rule.web.aop.annotaion.WebLogger;
import com.zw.rule.web.util.PageConvert;
import com.zw.rule.web.util.PropertiesUtil;
import com.zw.rule.web.util.UserContextUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Administrator on 2017/12/11.
 */
@Controller
@RequestMapping("surety")
public class SuretyController {
    @Resource
    private DictService dictService;
    @Resource
    private SuretyService Sservice;
    @RequestMapping("surety")
    public String recommend() {
        return "surety/suretyManManage";
    }

    /**
     * 列表展示
     * @param queryFilter
     * @return
     */
    @RequestMapping("getSuretyList")
    @ResponseBody
    public Response getRecommendList(@RequestBody ParamFilter queryFilter){
        int pageNo = PageConvert.convert(queryFilter.getPage().getFirstIndex(),queryFilter.getPage().getPageSize());
        PageHelper.startPage(pageNo, queryFilter.getPage().getPageSize());
        List<Surety> list = Sservice.getAllSureList(queryFilter.getParam());
        PageInfo pageInfo = new PageInfo(list);
        return new Response(pageInfo);
    }
    /**
     * 动态加载担保人关系下拉选访问的controller
     * @param
     * @return
     */
    @RequestMapping("getSuretyRelationList")
    @ResponseBody
    public Response getSuretyRelationList(){
        List<Dict> list=dictService.getDetailList("担保人关系");
        Response response =new Response();
        response.setData(list);
        return response;
    }
    /**
     * 添加方法
     * @param surety
     * @return
     */
    @RequestMapping("addSurety")
    @ResponseBody
    public Response addSurety(@RequestBody Surety surety){
        Response response=new Response();
        surety.setId(UUID.randomUUID().toString());
        int i=Sservice.addOneSurety(surety);
        if(i>0){
            response.setMsg("添加成功~");
        }else{
            response.setMsg("添加失败~");
        }
        return response;

    }
    /**
     * 查询担保人担保的订单
     * @param queryFilter
     * @return
     */
    @RequestMapping("getSuretyOrder")
    @ResponseBody
    public Response getSuretyOrder(@RequestBody ParamFilter queryFilter){
        int pageNo = PageConvert.convert(queryFilter.getPage().getFirstIndex(),queryFilter.getPage().getPageSize());
        PageHelper.startPage(pageNo, queryFilter.getPage().getPageSize());
        List<Order> list = Sservice.getSuretyOrders(queryFilter.getParam());
        PageInfo pageInfo = new PageInfo(list);
        return new Response(pageInfo);

    }

    /**
     * 查询担保人可以去担保的订单(未被担保过的且审核未通过的订单-状态为3的)
     * @param queryFilter
     * @return
     */
    @RequestMapping("getSuretyCanOrder")
    @ResponseBody
    public Response getSuretyCanOrder(@RequestBody ParamFilter queryFilter){
        int pageNo = PageConvert.convert(queryFilter.getPage().getFirstIndex(),queryFilter.getPage().getPageSize());
        PageHelper.startPage(pageNo, queryFilter.getPage().getPageSize());
        List<Order> list = Sservice.getCanBeSuretyOrders(queryFilter.getParam());
        PageInfo pageInfo = new PageInfo(list);
        return new Response(pageInfo);
    }

    /**
     * 担保人担保订单
     * 传入担保人id 订单id集合
     * @param map
     * @return
     */
    @RequestMapping("suretyAssureOrder")
    @ResponseBody
    public Response suretyAssureOrder(@RequestBody Map map){
        User user = (User) UserContextUtil.getAttribute("currentUser");
        Response response=new Response();
        int i=Sservice.suretyGoAssureOrder(map);
        if(i>0){

            //担保成功后插入日志
            Map logsMap=new HashMap();
            logsMap.put("orderId",map.get("orderId"));
            logsMap.put("handlerId",user.getUserId());
            logsMap.put("handlerName",user.getTrueName());
            logsMap.put("state",map.get("state"));//订单状态
            logsMap.put("tache","担保人担保订单");//当前环节
            logsMap.put("changeValue","担保人担保订单");//描述
            Sservice.addOrderAssureLogs(logsMap);
            //调app接口推送(担保成功过后)
            try {
                PropertiesUtil prop = new PropertiesUtil("properties/host.properties");
                String url =prop.get("appHGUrlSP")+"/orderMessage/orderPushMessage?state=4&orderId="+map.get("orderId");
                HttpClientUtil.getInstance().sendHttpGet(url);
            }catch (Exception e){}
            response.setMsg("担保成功~");
        }else{
            response.setMsg("担保失败~");
        }
        return response;
    }
}
