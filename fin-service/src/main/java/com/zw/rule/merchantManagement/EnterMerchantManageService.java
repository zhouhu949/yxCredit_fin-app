package com.zw.rule.merchantManagement;

import com.zw.rule.commodity.po.Commodity;
import com.zw.rule.customer.po.Order;
import com.zw.rule.merchant.Merchant;

import java.util.List;
import java.util.Map;

/**
 * Created by zoukaixuan on 2018/1/10.
 * 入驻商户管理
 */
public interface EnterMerchantManageService {
    List<Merchant> getEnterMerchants(Map map);

    //获取订单列表orderList
    List<Order> getMerchantOrdersList(Map map);
    //获取该商户下的商品列表
    List<Commodity> getMerchantMerchantdise(Map map);
    //删除商户下的商品
    int deleteMerGoods(Map map);
    //获取商品图片
    List<String> getMerchantdiseImgs(Map map);
}
