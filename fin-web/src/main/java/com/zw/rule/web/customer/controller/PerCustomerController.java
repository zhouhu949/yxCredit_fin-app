package com.zw.rule.web.customer.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zw.base.util.DateUtils;
import com.zw.rule.core.Response;
import com.zw.rule.customer.po.Customer;
import com.zw.rule.customer.po.Order;
import com.zw.rule.customer.service.CustomerService;
import com.zw.rule.customer.service.OrderService;
import com.zw.rule.customer.service.PerCustService;
import com.zw.rule.mybatis.ParamFilter;
import com.zw.rule.service.DictService;
import com.zw.rule.web.aop.annotaion.WebLogger;
import com.zw.rule.web.util.PageConvert;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/******************************************************
 *Copyrights @ 2017，zhiwang  Co., Ltd.
 *
 *All rights reserved.
 *          小窝
 *Filename：
 *         准客户controller
 *Description：
 *		  未知
 *Author:
 *		 wangmin
 *Finished：
 *		2017/6/25
 ********************************************************/
@Controller
@RequestMapping("percust")
public class PerCustomerController {

    @Resource
    private PerCustService perCustService;
    @Resource
    private CustomerService customerService;
    @Resource
    private OrderService orderService;
    @Resource
    private DictService dictService;

    @GetMapping("index")
    public String orderPool(){
        return "customerManage/preCustomer";
    }

    /**
     * 新增产品列表
     * @return
     */
    @GetMapping("productSel")
    public String productSel(String customerId,HttpServletRequest request){
        request.setAttribute("customerId",customerId);
        return "customerManage/productSelect";
    }

    /**
     * 审批流程界面
     * @return
     */
    @GetMapping("approveRecordList")
    public String approveRecordList(String orderId,HttpServletRequest request){
        request.setAttribute("orderId",orderId);
        return "customerManage/approveRecord";
    }

    @PostMapping("list")
    @ResponseBody
    @WebLogger("准客户的所有信息")
    public Response list(@RequestBody ParamFilter paramFilter) throws Exception {
        Response response = new Response();
        int pageNo = PageConvert.convert(paramFilter.getPage().getFirstIndex(),paramFilter.getPage().getPageSize());
        PageHelper.startPage(pageNo, paramFilter.getPage().getPageSize());

        List list = perCustService.perCustList(paramFilter);
        //String userId =String.valueOf(UserContextUtil.getAttribute("userId"));
        PageInfo pageInfo = new PageInfo(list);
        //int pageNo = PageConvert.convert(paramFilter.getPage().getFirstIndex(),paramFilter.getPage().getPageSize());
        //PageHelper.startPage(pageNo, paramFilter.getPage().getPageSize());
        response.setData(pageInfo);
        return response;
    }

    @PostMapping("orderList")
    @ResponseBody
    @WebLogger("根据客户ID查询订单列表")
    public Response orderList(@RequestBody ParamFilter paramFilter) throws Exception {
        Response response = new Response();
        int pageNo = PageConvert.convert(paramFilter.getPage().getFirstIndex(),paramFilter.getPage().getPageSize());
        PageHelper.startPage(pageNo, paramFilter.getPage().getPageSize());

        List list = perCustService.orderList(paramFilter);
        //环节对应的id
        String state;
        //审批状态ID
        String tache;
        for(int i=0;i<list.size();i++){
            JSONObject json = (JSONObject) JSON.toJSON(list.get(i));
            state = json.get("state").toString();
            tache = json.get("tache").toString()==""?"2":json.get("tache").toString();
            //订单对应的环节的名称
            state = dictService.getDetilNameByCode(state,"203");
            //审批状态对应的名称
            tache = dictService.getDetilNameByCode(tache,"214");
            json.put("tacheName",tache);
            json.put("stateName",state);
            list.set(i,json);
        }
        //String userId =String.valueOf(UserContextUtil.getAttribute("userId"));
        PageInfo pageInfo = new PageInfo(list);
        //int pageNo = PageConvert.convert(paramFilter.getPage().getFirstIndex(),paramFilter.getPage().getPageSize());
        //PageHelper.startPage(pageNo, paramFilter.getPage().getPageSize());
        response.setData(pageInfo);
        //String name = dictService.getDetilNameByCode("0","203");
        return response;
    }

    @ResponseBody
    @PostMapping("addProduct")
    public Response addProduct(HttpServletRequest request) throws Exception {
        Response response = new Response();
        Customer customer = customerService.findOne(request.getParameter("customerId"));
        Order order = new Order();
        order.setId(UUID.randomUUID().toString());
        order.setState("0");
        order.setCreatTime(DateUtils.getDateString(new Date()));
        order.setCustomerId(customer.getId());
        order.setPeriods(request.getParameter("productDetail"));
        order.setProductNameName(request.getParameter("productType"));
        order.setTel(customer.getTel());
        //order.setTache("2");
        order.setOrderNo(UUID.randomUUID().toString());
        order.setCustomerName(customer.getPersonName());
        int num = orderService.insertOrder(order);
        if(num>0){
            response.setMsg("产品新增成功！");
            return response;
        }else{
            response.setCode(1);
            response.setData("产品添加失败！");
            return response;
        }
    }

    @PostMapping("auditList")
    @ResponseBody
    @WebLogger("订单的审核信息")
    public Response auditList(@RequestBody ParamFilter paramFilter) throws Exception {
        Response response = new Response();
        int pageNo = PageConvert.convert(paramFilter.getPage().getFirstIndex(),paramFilter.getPage().getPageSize());
        PageHelper.startPage(pageNo, paramFilter.getPage().getPageSize());

        List list = perCustService.getAuditList(paramFilter);
        //环节对应的id
        String state;
        //审批状态ID
        String tache;
        for(int i=0;i<list.size();i++){
            JSONObject json = (JSONObject) JSON.toJSON(list.get(i));
            state = json.get("state").toString();
            tache = json.get("tache").toString();
            //订单对应的环节的名称
            state = dictService.getDetilNameByCode(state,"203");
            //审批状态对应的名称
            tache = dictService.getDetilNameByCode(tache,"214");
            json.put("tacheName",tache);
            json.put("stateName",state);
            list.set(i,json);
        }
        PageInfo pageInfo = new PageInfo(list);
        response.setData(pageInfo);
        return response;
    }
}
