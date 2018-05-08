package com.zw.rule.customer.service;

import com.zw.rule.core.Response;
import com.zw.rule.customer.po.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface CustomerAuditService {

    //添加客户通信调查表
    Map addCustomerInvestigation(CustomerInvestigation customerInvestigation);

    //删除客户通信调查表
    Boolean deleteCustomerInvestigation(String id);

    //添加客户电核信息
    Boolean addCustomerExamine(CustomerExamine customerExamine);

    //额度匹配
    List<CustomerMatching> getMatchingPrice(CustomerMatching customerMatching);

    //上传资料,返回图片名称
    List uploadCustomerImage(HttpServletRequest request) throws Exception ;

    //根据id查询装修合理性判断数据
    CustomerResp selectById(String id);

    //客户图片资料
    List<CustomerImage> getOrderImage(String orderId);

    //获取信用问答题目
    Response getAnswer(Map map);

    //添加信用问答题目
    Response addAnswer(List<Map<String,Object>> listMap,String url);

}
