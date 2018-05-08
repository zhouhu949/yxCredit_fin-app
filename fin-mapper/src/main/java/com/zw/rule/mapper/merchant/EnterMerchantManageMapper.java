package com.zw.rule.mapper.merchant;

import com.zw.rule.commodity.po.Commodity;
import com.zw.rule.customer.po.Order;
import com.zw.rule.merchant.Merchant;

import java.util.List;
import java.util.Map;

/**
 * Created by zoukaixuan on 2018/1/10.
 * 入驻商户管理
 */
public interface EnterMerchantManageMapper {
    //入驻商户列表
    List<Merchant> selectAllEnterMerchants(Map map);
    //入驻商户的订单
    List<Order> selectMerchantOrders(Map map);
    //查询商户下面的商品
    List<Commodity>selectMerchantMerchantdise(Map map);
    //删除商户下的图片(更改关联状态)
    int deleteMerchantMerchantdise(Map map);
    //获取商品图片
    List<String> selectMerchantdiseImgs(Map map);
}
