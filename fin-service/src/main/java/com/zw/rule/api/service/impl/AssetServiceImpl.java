package com.zw.rule.api.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.zw.api.HttpClientUtil;
import com.zw.base.util.HttpUtil;
import com.zw.rule.api.asset.AssetRequest;
import com.zw.rule.api.asset.AssetSettings;
import com.zw.rule.api.service.IAssetService;
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


/**
 * 资产数据同步服务实现
 * @author 仙海峰
 */
@Service(IAssetService.BEAN_KEY)
public class AssetServiceImpl implements IAssetService {
    @Autowired
    private AssetSettings  assetSettings;

    @Autowired
    private UserService userService;

    @Override
    public String syncAssetData(AssetRequest request) throws IOException {
        if(request == null){return  null;}
        //设置请求参数
        Map<String,Object> paramMap = new HashMap<>(2);
        paramMap.put("orderId",request.getOrderId());
        paramMap.put("customerId",request.getCustomerId());
        List<Header> headerList  = new ArrayList<>();
        //设置token
        String token = userService.getTokenById(request.getCustomerId());
        headerList.add(new BasicHeader("token",token));
        return HttpUtil.doPost(assetSettings.getRequestUrl(),paramMap,headerList);
    }
}
