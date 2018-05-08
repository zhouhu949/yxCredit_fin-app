package com.zw.rule.web.loanClient.controller;

import com.zw.rule.core.Response;
import com.zw.rule.customer.po.MagCustomerContact;
import com.zw.rule.customer.po.Order;
import com.zw.rule.customer.service.CustomerService;
import com.zw.rule.customer.service.OrderService;
import com.zw.rule.loanClient.service.LoanClientService;
import com.zw.rule.product.service.ICrmProductService;
import com.zw.rule.util.flow.service.FlowComService;
import com.zw.rule.web.aop.annotaion.WebLogger;
import com.zw.rule.web.util.PropertiesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/7/12.
 */
@Controller
@RequestMapping("loanClient")
public class LoanClientController {
    @Resource
    private LoanClientService loanClinetService;
    @Autowired
    private CustomerService customerService;
    @Resource
    private OrderService orderService;
    @Resource
    private FlowComService flowComService;
    @Autowired
    private ICrmProductService crmProductService;
    

    @PostMapping("customerDetails")
    @ResponseBody
    @WebLogger("查询客户详情")
    public Response customerDetails(@RequestBody Map param) throws  Exception{
        String customerId = param.get("customerId").toString();
        //String type = param.get("type").toString();
        String orderId = param.get("orderId").toString();
        Map map = new HashMap();
        //客户联系人信息
        List linkmanList = customerService.getCustomerLinkMan(customerId);
        //客户基本信息
        List personList = customerService.getCustomerPerson(customerId);
        //客户收入状况信息
        List incomeList = customerService.getCustomerIncome(customerId);
        //客户职业信息
        //List occupationList = customerService.getCustomerOccupation(customerId,type);
        //获取客户通讯信息
        MagCustomerContact customerContact = customerService.getContact(customerId);
//        //居住信息
//        MagCustomerLive customerLive = customerService.getCustomerLive(customerId);
        //订单信息
        Order order = orderService.selectById(orderId);
        //产品详细列表
        List productDetailList = (List)crmProductService.getProductInfo(order.getProductDetail()).get("data");
        //查询客户订单上传素材信息,图片
        List imgList = orderService.findByCustId(customerId);
        PropertiesUtil prop = new PropertiesUtil("properties/host.properties");
        String host =prop.get("imgUrl");
        map.put("host",host);
        map.put("order",order);
        map.put("linkmanList",linkmanList);
        map.put("incomeList",incomeList);
        map.put("personList",personList);
        map.put("imgList",imgList);
        //map.put("occupationList",occupationList);
        map.put("customerContact",customerContact);
        map.put("productDetailList",productDetailList);
        return new Response(map);
    }

}
