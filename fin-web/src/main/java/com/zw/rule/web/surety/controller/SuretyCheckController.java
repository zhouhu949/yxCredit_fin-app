package com.zw.rule.web.surety.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zw.base.util.HttpClientUtil;
import com.zw.rule.approveRecord.po.ProcessApproveRecord;
import com.zw.rule.core.Response;
import com.zw.rule.customer.po.Order;
import com.zw.rule.customer.service.OrderService;
import com.zw.rule.mybatis.ParamFilter;
import com.zw.rule.po.User;
import com.zw.rule.surety.Surety;
import com.zw.rule.surety.service.SuretyCheckService;
import com.zw.rule.surety.service.SuretyService;
import com.zw.rule.web.aop.annotaion.WebLogger;
import com.zw.rule.web.util.PageConvert;
import com.zw.rule.web.util.PropertiesUtil;
import com.zw.rule.web.util.UserContextUtil;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/1/4.
 */
@Controller
@RequestMapping("suretyCheckController")
public class SuretyCheckController {
    @Resource
    private SuretyCheckService suretyCheckService;
    @Resource
    private OrderService orderService;
    @Resource
    private SuretyService suretyService;


    @RequestMapping("suretyCheck")
    public String recommend() {
        return "surety/suretyCheck";
    }
    /**
     * 担保订单列表展示
     * @param queryFilter
     * @return
     */
    @RequestMapping("getSuretyOrderList")
    @ResponseBody
    public Response getRecommendList(@RequestBody ParamFilter queryFilter){
        int pageNo = PageConvert.convert(queryFilter.getPage().getFirstIndex(),queryFilter.getPage().getPageSize());
        PageHelper.startPage(pageNo, queryFilter.getPage().getPageSize());
        List<Order> list = new ArrayList<>(suretyCheckService.getAllSuretyOrders(queryFilter.getParam()));
        PageInfo pageInfo = new PageInfo(list);
        return new Response(pageInfo);
    }


    /**
     * 担保人担保订单访问方法
     * 传入担保人id 订单id集合
     * @param map
     * @return
     */
    @RequestMapping("suretyAssureOrder")
    @ResponseBody
    public Response suretyAssureOrder(@RequestBody Map map){
        Response response=new Response();
        return response;
    }

    /**
     * 返回审核被担保的订单页面
     * @param orderId
     * @param customerId
     * @return
     * @throws Exception
     */
    @GetMapping("details")
    public ModelAndView details(String orderId, String customerId,String relId) throws  Exception{
        ModelAndView modelAndView = new ModelAndView("surety/checkSuretyOrder");
        Order order = orderService.selectById(orderId);//订单实体
        modelAndView.addObject("order",order);
        modelAndView.addObject("relId",relId);
        Surety surety=suretyCheckService.getOrderSurety(relId);//担保人实体
        modelAndView.addObject("surety",surety);//担保人实体
        String s=surety.getCreateTime();
        String createTime= s.substring(0,4)+"-"+s.substring(4,6)+"-"+s.substring(6,8)+"  "+s.substring(8,10)+":"+s.substring(10,12)+":"+s.substring(12,14);//担保该订单的时间
        modelAndView.addObject("createTime",createTime);
        return  modelAndView;
    }

    /**
     * 审核通过的方法
     * @param str
     * @return
     * @throws Exception
     */
    @PostMapping("approved")
    @ResponseBody
    @WebLogger("总部审核通过")
    @Transactional
    public Response approved(@RequestBody String str) throws Exception{
        User user = (User) UserContextUtil.getAttribute("currentUser");
        Map map = JSONObject.parseObject(str);
        map.put("state","5");//订单状态
        //订单状态改为5
        orderService.updateOrderState(map);
        map.put("result","1");//结果 1通过  0拒绝
        map.put("handlerId",user.getUserId());//处理人id
        map.put("handlerName",user.getTrueName());//处理人姓名
        map.put("type","1");//1客户id，2商户id
        //添加审核信息表 审核意见
        suretyCheckService.addApproveRecord(map);
        //更改担保人和订单表状态(已审核 2)
        suretyCheckService.changeSuretyOrderState(map);

        //添加订单审核日志
        Map logsMap=new HashMap();
        logsMap.put("orderId",map.get("id"));
        logsMap.put("handlerId",user.getUserId());
        logsMap.put("handlerName",user.getTrueName());
        logsMap.put("state",map.get("state"));//订单状态
        logsMap.put("tache","担保订单审核");//当前环节
        logsMap.put("changeValue","审核通过");//描述
        suretyService.addOrderAssureLogs(logsMap);
        Response response = new Response();
        response.setMsg("审核通过");
        //调app接口推送
        try {
            PropertiesUtil prop = new PropertiesUtil("properties/host.properties");
            String url =prop.get("appHGUrlSP")+"/orderMessage/orderPushMessage?state=3&orderId="+map.get("id");
            HttpClientUtil.getInstance().sendHttpGet(url);
        }catch (Exception e){}
        return response;
    }



    /**
     * 审核拒绝的方法
     * @param str
     * @return
     * @throws Exception
     */
    @PostMapping("refused")
    @ResponseBody
    @WebLogger("总部审核拒绝")
    public Response refused(@RequestBody String str) throws Exception{
        User user = (User) UserContextUtil.getAttribute("currentUser");
        Map map = JSONObject.parseObject(str);
        //map.put("state","5");//订单状态
        //订单状态改为5
        //orderService.updateOrderState(map);
        map.put("result","0");//结果 1通过  0拒绝
        map.put("handlerId",user.getUserId());//处理人id
        map.put("handlerName",user.getTrueName());//处理人姓名
        map.put("type","1");//1客户id，2商户id
        //添加审核信息表 审核意见
        suretyCheckService.addRefusedRecord(map);
        //更改担保人和订单关联表状态(已审核状态 2)
        suretyCheckService.changeSuretyOrderState(map);

        //添加订单审核日志
        Map logsMap=new HashMap();
        logsMap.put("orderId",map.get("id"));
        logsMap.put("handlerId",user.getUserId());
        logsMap.put("handlerName",user.getTrueName());
        logsMap.put("state",map.get("state"));//订单状态
        logsMap.put("tache","担保订单审核");//当前环节
        logsMap.put("changeValue","审核拒绝");//描述
        suretyService.addOrderAssureLogs(logsMap);

        Response response = new Response();
        response.setMsg("审核拒绝");
//        //调app接口推送
//        try {
//            PropertiesUtil prop = new PropertiesUtil("properties/host.properties");
//            String url =prop.get("appHGUrlSP")+"/orderMessage/orderPushMessage?state=9&orderId="+map.get("id");
//            HttpClientUtil.getInstance().sendHttpGet(url);
//        }catch (Exception e){}
        return response;
    }




    /**
     * 担保订单菜单对应的网页
     * @return
     * surety/suretyCheckOrdersShow
     */
    @RequestMapping("suretyCheckOrdersShow")
    public String suretyCheckOrdersShow() {
        return "surety/suretyOrders";
    }
    /**
     * 担保人担保过后并且审核后的订单集合
     * @param queryFilter
     * @return
     */
    @RequestMapping("getSuretyAndCheckOrdersList")
    @ResponseBody
    public Response getSuretyAndCheckOrdersList(@RequestBody ParamFilter queryFilter){
        int pageNo = PageConvert.convert(queryFilter.getPage().getFirstIndex(),queryFilter.getPage().getPageSize());
        PageHelper.startPage(pageNo, queryFilter.getPage().getPageSize());
        List<Order> list = new ArrayList<>(suretyCheckService.getSuretyCheckOrders(queryFilter.getParam()));
        PageInfo pageInfo = new PageInfo(list);
        return new Response(pageInfo);
    }
//    suretyCheckOrderDetailShow

    /**
     * 担保人担保订单(已经被审核过的)访问方法
     * 传入担保人id 订单id集合
     * @param
     * @return modelAndView
     */
    @RequestMapping("suretyCheckOrderDetailShow")
    @ResponseBody
    public ModelAndView suretyCheckOrderDetailShow(String orderId, String customerId,String relId,String checkManName,String checkManId,String checkResult,String checkId){
        ModelAndView modelAndView = new ModelAndView("surety/suretyCheckOrderDetalShow");
        Order order = orderService.selectById(orderId);
        modelAndView.addObject("order",order);//订单实体
        modelAndView.addObject("relId",relId);//担保人担保订单的关联id
        //担保人实体
        Surety surety=suretyCheckService.getOrderSurety(relId);
        String s=surety.getCreateTime();
        //2018 01 13 12 34 07
        String suretyTime= s.substring(0,4)+"-"+s.substring(4,6)+"-"+s.substring(6,8)+"  "+s.substring(8,10)+":"+s.substring(10,12)+":"+s.substring(12,14);//担保该订单的时间
        modelAndView.addObject("suretyTime",suretyTime);//担保时间
        modelAndView.addObject("surety",surety);//担保人实体
        modelAndView.addObject("checkManName",checkManName);//审批人姓名
        modelAndView.addObject("checkManId",checkManId);//审批人id
        modelAndView.addObject("checkResult",checkResult);//审批结果
        modelAndView.addObject("approveSuggestion",suretyCheckService.getProById(checkId).getApproveSuggestion());//审批建议
        modelAndView.addObject("processApproveRecord",suretyCheckService.getProById(checkId));//审批意见表实体
        String ct=suretyCheckService.getProById(checkId).getCommitTime();
        String checkTime=ct.substring(0,4)+"-"+ct.substring(4,6)+"-"+ct.substring(6,8)+"  "+ct.substring(8,10)+":"+ct.substring(10,12)+":"+ct.substring(12,14);//担保时间
        modelAndView.addObject("checkTime",checkTime);//审批时间
        modelAndView.addObject("checkId",checkId);//审批意见表id
        return  modelAndView;
    }
}

