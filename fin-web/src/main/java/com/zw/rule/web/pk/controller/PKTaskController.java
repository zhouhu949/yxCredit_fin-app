package com.zw.rule.web.pk.controller;

import com.alibaba.fastjson.JSON;
import com.zw.rule.api.PKApi;
import com.zw.rule.core.Response;
import com.zw.rule.customer.po.CustomerProperty;
import com.zw.rule.customer.po.Order;
import com.zw.rule.customer.service.CustomerService;
import com.zw.rule.customer.service.OrderService;
import com.zw.rule.mybatis.ParamFilter;
import com.zw.rule.pk.PKTaskEntity;
import com.zw.rule.web.aop.annotaion.WebLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 调用小窝拍客接口，向拍客发送一条任务
 * Created by WeiHong on 2017/6/30.
 */
@Controller
@RequestMapping("pkTask")
public class PKTaskController {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

    @Resource
    private CustomerService customerService;

    @Resource
    private OrderService orderService;

    private static final Logger LOGGER = LoggerFactory.getLogger(PKTaskController.class);

    @RequestMapping("sendTask")
    @ResponseBody
    @WebLogger("向小窝拍客发送任务")
    public Response sendTask(@RequestBody PKTaskEntity entity) {
        Response response = new Response("发送失败，请联系管理员！");
        response.setCode(1);
        //获取订单对应的客户编号
        Map<String, Object> params = new HashMap<>();
        params.put("orderNo", entity.getOrderNO());
        ParamFilter queryFilter = new ParamFilter();
        queryFilter.setParam(params);
        List<Order> orderList = orderService.getOrderList(queryFilter.getParam());
        if (orderList != null && orderList.size() > 0){
            List<CustomerProperty> propertys = customerService.getCustomerProperty(orderList.get(0).getCustomerId());
            if (propertys != null && propertys.size() > 0){
                //现场联系地址
                entity.setAddress(propertys.get(0).getProAddress());
                entity.setOrderId(sdf.format(new Date()));
                entity.setResource("fin-app");
                entity.setAreaCode(440305);
                entity.setHouseTypeId(2);
                entity.setNodeId(1);
                entity.setDescription("");
                entity.setBankCode(1101001);
                entity.setGender("男");
                entity.setNodeId(1);
                entity.setNodeCheckId(Long.parseLong("0"));
                /**
                 * 拍客响应数据：
                 * 成功：{"code":0,"msg":"ok","times":1499141452346}
                 * 失败：状态码 : 1000: 签名错误 1001：参数错误 1002：更新失败 1004:任务不存在 1005:任务不可变更
                 */
                String s = null;
                try {
                    s = PKApi.sendTask(entity);
                } catch (Exception e) {
                    LOGGER.error("订单号：[{}]发起拍客任务失败！错误堆栈：\n{}", entity.getOrderNO(), e);
                }
                if (s != null) {
                    Map<String, Object> result = (Map) JSON.parse(s);
                    if (result != null && result.size() > 0) {
                        if ("0".equals(result.get("code").toString())) {
                            response.setCode(0);
                            response.setMsg("发送成功！");
                        } else {
                            response.setMsg(result.get("msg").toString());
                        }
                    }
                }
            }
        }
        return response;
    }

}
