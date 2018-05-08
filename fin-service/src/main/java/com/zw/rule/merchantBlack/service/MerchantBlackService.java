package com.zw.rule.merchantBlack.service;

import com.zw.rule.merchantBlackEntity.MerchantBlack;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/12/9.
 * 商户黑名单设置服务层接口
 */
public interface MerchantBlackService {
    //查询所有
    public List<MerchantBlack> getAllMerchantBlack(Map map);
    //添加一条商户黑名单
    public int addoneMerchantBlack(MerchantBlack merchantBlack);
    //更改状态 停用或者启用
    public int startOrStopState(Map map);
    //根据id单个查询商户黑名单信息
    public MerchantBlack getOneBlackById(String id);
    //根据id更改
    public int changeBlackById(MerchantBlack merchantBlack);
    //根据id删除
    public int deleteBlackById(Map map);
}

