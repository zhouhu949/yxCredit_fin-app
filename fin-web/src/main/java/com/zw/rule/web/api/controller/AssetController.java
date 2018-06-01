package com.zw.rule.web.api.controller;

import com.alibaba.fastjson.JSONObject;
import com.zw.base.util.Consts;
import com.zw.base.util.StringUtils;
import com.zw.rule.api.asset.AssetRequest;
import com.zw.rule.api.service.IAssetService;
import com.zw.rule.core.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @author 仙海峰
 */
@RestController
@RequestMapping("asset")
public class AssetController {
    @Autowired
    private IAssetService assetService;

    /**
     * 资产同步
     * @author 仙海峰
     * @param request
     * @return
     */
    @RequestMapping("syncAssetData")
    public Response syncAssetData(@RequestBody AssetRequest request) {
        if (request == null) {
            return Response.error("参数异常");
        }
        try {
            String  assetDataStr = assetService.syncAssetData(request);
            if (StringUtils.isNotEmpty(assetDataStr)) {
                JSONObject resultStr = JSONObject.parseObject(assetDataStr);
                String resCode = resultStr.get("retCode").toString();
                if ((Consts.API_SUCCESS).equals(resCode)) {
                    return Response.ok("同步成功！", null);
                }
            }
            return Response.error("同步失败！");
        } catch (IOException e) {
            e.printStackTrace();
            return Response.error(e.getMessage());
        }
    }

}

