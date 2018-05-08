package com.zw.rule.merchantManagement.impl;

import com.zw.rule.commodity.po.Commodity;
import com.zw.rule.customer.po.Order;
import com.zw.rule.mapper.merchant.EnterMerchantManageMapper;
import com.zw.rule.merchant.Merchant;
import com.zw.rule.merchantManagement.EnterMerchantManageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/1/10.
 * 入驻商户管理
 */
@Service
public class EnterMerchantManageImpl implements EnterMerchantManageService {
    @Resource
    private EnterMerchantManageMapper enterMerchantManageMapper;

    /**
     * 商户列表展示
     * @param map
     * @return
     */
    public List<Merchant> getEnterMerchants(Map map) {
        return enterMerchantManageMapper.selectAllEnterMerchants(map);
    }

    /**
     * 查询商户下的订单列表
     * @param map
     * @return
     */
    public List<Order> getMerchantOrdersList(Map map){
        return enterMerchantManageMapper.selectMerchantOrders(map);
    }

    /**
     * 获取该商户下面的商品列表
     * @param map
     * @return
     */
    public List<Commodity> getMerchantMerchantdise(Map map) {
        return enterMerchantManageMapper.selectMerchantMerchantdise(map);
    }

    /**
     * 删除商户下的图片(更改关联状态)
     * @param map
     * @return
     */
    public int deleteMerGoods(Map map) {
        return enterMerchantManageMapper.deleteMerchantMerchantdise(map);
    }

    /**
     * 获取商品图片
     * @param map
     * @return
     */
    public List<String> getMerchantdiseImgs(Map map){
        return enterMerchantManageMapper.selectMerchantdiseImgs(map);
    }
}
