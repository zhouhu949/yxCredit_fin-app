package com.zw.rule.mapper.merchantBlackMapper;

import java.util.List;
import java.util.Map;

import com.zw.rule.merchantBlackEntity.MerchantBlack;

/**
 * Created by Administrator on 2017/12/9.
 * 商户黑名单持久层方法映射器
 */
public interface MerchantBlackMapper {
    //获取全部的商户黑名单信息
    public List<MerchantBlack> selectAllMerchantBlack(Map map);
    //添加商户黑名单信息
    public int insertOneMerchantBlack(MerchantBlack black);
    //更改状态(停用启用)
    public int changeState(Map map);
    //根据id查询单个商户黑名单配置信息
    public MerchantBlack selectOneMerchantBlackById(String id);
    //根据id更改单个商户配置信息
    public int changeOneMerchantBlackById(MerchantBlack merchantBlack);
    //根据id删除
    public int deleteOneMerchantBlackById(Map map);


}
