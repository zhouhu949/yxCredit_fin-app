package com.zw.rule.commodityMange.service.impl;

import com.zw.rule.commodity.po.Commodity;
import com.zw.rule.commodityMange.service.CommodityService;
import com.zw.rule.mapper.commodityMapper.CommodityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/12/15.
 */
@Service
public class CommodityServiceImpl implements CommodityService {
    @Autowired
    private CommodityMapper commodityMapper;
    //添加商品
    @Override
    public int addCommodity(Commodity commodity){
        return  commodityMapper.insertMerchantdise(commodity);
    }
    //修改商品
    @Override
    public int updateCommodity(Commodity commodity){
        return  commodityMapper.updateMerchantdise(commodity);
    }

    //获取商品目录
    @Override
    public List<Commodity> getCommodityMenu(Map map){

        return  commodityMapper.getCommodityMenu(map);
    }

    @Override
    public List<Commodity> getMerchantdiseList(Map map) {
        return commodityMapper.getMerchantdiseList(map);
    }

    @Override
    public List<Map> getList(Map map) {
        return commodityMapper.getList(map);
    }

    @Override
    public List<Map> getList1(Map map) {
        return commodityMapper.getList1(map);
    }

    @Override
    public List<Map> getMerchantListInfo(Map map){
        return commodityMapper.getMerchantListInfo(map);
    }


    @Override
    public int delete(String id) {
        return commodityMapper.delete(id);
    }

    @Override
    public int updateState(Map map) {
        return commodityMapper.updateState(map);
    }
}
