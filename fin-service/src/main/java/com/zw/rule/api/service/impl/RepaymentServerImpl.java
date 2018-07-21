package com.zw.rule.api.service.impl;

import com.zw.base.util.HttpUtil;
import com.zw.rule.api.repayment.RepaymentRequest;
import com.zw.rule.api.repayment.RepaymentSettings;
import com.zw.rule.api.service.IRepaymentServer;
import com.zw.rule.service.UserService;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(IRepaymentServer.BEAN_KEY)
public class RepaymentServerImpl implements IRepaymentServer {

    @Autowired
    private RepaymentSettings repaymentSettings;

    @Autowired
    private UserService userService;

    @Override
    public String getLoan(RepaymentRequest request) throws IOException {
        if(request == null){return  null;}
        //设置请求参数
        Map<String,Object> paramMap = new HashMap<>(2);
        paramMap.put("orderId",request.getOrderId());
        List<Header> headerList  = new ArrayList<>();
        //设置token
        String token = userService.getTokenById(request.getCustomerId());
        headerList.add(new BasicHeader("token",token));
        return HttpUtil.doPost(repaymentSettings.getRequestUrl(),paramMap,headerList);
    }
}
