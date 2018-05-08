package com.zw.rule.commodityMange.service;

import com.zw.rule.commodity.po.Commodity;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/12/15.
 */
public interface CommodityService {
    //添加商品
    int addCommodity(Commodity commodity);
    //修改商品
    int updateCommodity(Commodity commodity);
    //获取商品目录
    List<Commodity> getCommodityMenu(Map map);

    List<Commodity> getMerchantdiseList(Map map);

    List<Map> getList(Map map);

    int delete(String id);

    int updateState(Map param);

    List<Map> getList1(Map map);

    List<Map> getMerchantListInfo(Map map);
}
