package com.zw.rule.web.api.controller;

import com.alibaba.fastjson.JSONObject;
import com.zw.base.util.Consts;
import com.zw.base.util.StringUtils;
import com.zw.rule.api.asset.AssetRequest;
import com.zw.rule.api.repayment.RepaymentRequest;
import com.zw.rule.api.service.IAssetService;
import com.zw.rule.api.service.IRepaymentServer;
import com.zw.rule.core.Response;
import com.zw.rule.repayment.po.Repayment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * 远程调用接口端服务
 * @author 陈淸玉 create on 2018-07-20
 */
@RestController
@RequestMapping("api")
public class InvokeApiController {

    @Autowired
    private IRepaymentServer repaymentServer;

    /**
     * 远程接口端 查询还款账号
     * @author 陈淸玉
     * @param request
     * @return
     */
    @RequestMapping("getLoan")
    public Response syncAssetData(@RequestBody RepaymentRequest request) {
        if (request == null) {
            return Response.error("参数异常");
        }
        try {
            String  result = repaymentServer.getLoan(request);
            if (StringUtils.isNotEmpty(result)) {
                JSONObject resultStr = JSONObject.parseObject(result);
                String resCode = resultStr.get("retCode").toString();
                if ((Consts.API_SUCCESS).equals(resCode)) {
                    return Response.ok("查询还款账号！", null);
                }
            }
            return Response.error("查询还款账号！");
        } catch (IOException e) {
            e.printStackTrace();
            return Response.error(e.getMessage());
        }
    }

}

