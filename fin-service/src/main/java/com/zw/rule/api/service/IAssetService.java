package com.zw.rule.api.service;

import com.zw.rule.api.asset.AssetRequest;

import java.io.IOException;

/**
 * 资产同步接口
 */
public interface IAssetService {

    String BEAN_KEY = "assetServiceImpl";
    /**
     * 资产数据推送
     * @return 结果json字符串
     */
    String syncAssetData(AssetRequest request) throws IOException;
    /**
     * 刷新资产状态
     * @return 结果json字符串
     */
    String getByBusinessId(AssetRequest request) throws IOException;

}
