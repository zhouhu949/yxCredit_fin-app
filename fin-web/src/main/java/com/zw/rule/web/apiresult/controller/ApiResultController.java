package com.zw.rule.web.apiresult.controller;

import com.alibaba.fastjson.JSONObject;
import com.zw.rule.ApiResult.service.ApiResultService;
import com.zw.rule.apiresult.ApiResult;
import com.zw.rule.core.Response;
import com.zw.rule.customer.po.Customer;
import com.zw.rule.customer.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;


/**
 * 风控信息控制器
 * @author 陈清玉
 */
@RestController
@RequestMapping("/apiResult")
public class ApiResultController {

    private final Logger LOGGER = LoggerFactory.getLogger(ApiResultController.class);

    @Autowired
    private ApiResultService resultService;

    @Autowired
    private CustomerService customerService;

    /**
     * 获取风控结果数据
     * @return 同盾报告
     */
    @GetMapping("/tongDun/{orderId}/{sourceCode}/{customerId}")
    public Response getByOrderIdAndSourceCode(@PathVariable String orderId
            ,@PathVariable String sourceCode,@PathVariable String customerId ){
        LOGGER.info("获取风控结果数据orderId：{},sourceCode:{}",orderId,sourceCode);
        final ApiResult result = resultService.getByOrderIdAndSourceCode(orderId, sourceCode);
        final Customer customer = customerService.getCustomer(customerId);
        Map<String, Object> resultMap = new HashMap<>(3);
        if(customer != null) {
            resultMap.put("custName", customer.getPersonName());
            resultMap.put("custIc",customer.getCard());
            resultMap.put("custMobile", customer.getTel());
        }
        resultMap.put("tongDunInfo",JSONObject.parseObject(result.getResultData().toString()));
        return   Response.ok(resultMap);
    }


}
