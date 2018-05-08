package com.zw.rule.mapper.commodityMapper;

import com.zw.rule.commodity.po.Commodity;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/12/16.
 */
public interface CommodityMapper {
    //获取商品集合
    List<Commodity> getMerchantdiseList(Map map);
    //新建商品
    int insertMerchantdise(Commodity commodity);
    //修改商品
    int updateMerchantdise(Commodity commodity);
    //获取商品目录
    List<Commodity> getCommodityMenu(Map map);


    List<Map> getList(Map param);

    int delete(String id);

    int updateState(Map param);

    List<Map> getList1(Map param);

    List<Map> getMerchantListInfo(Map map);
}
