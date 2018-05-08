package com.zw.rule.goldLogistics.service.impl;

import com.zw.rule.goldLogistics.service.GoldLogisticsService;
import com.zw.rule.mapper.goldLogistics.GoldLogisticsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Administrator on 2017/12/22.
 */
@Service
public class GoldLogisticsServiceImpl implements GoldLogisticsService {

    @Autowired
    private GoldLogisticsMapper goldLogisticsMapper;
    //获取客户发货信息
    @Override
    public List getlogisticsByOrderId(Map map){
        return goldLogisticsMapper.getlogisticsByOrderId(map);
    }

    //添加客户发货信息
    @Override
    public int addLogistics(Map map){
        map.put("delivertime",new Date());
        map.put("updateTime",new Date());
        map.put("id", UUID.randomUUID().toString());
        //更新订单采购信息
        goldLogisticsMapper.updateOrderGold(map);
        //更新订单信息
        goldLogisticsMapper.updateOrder(map);
        //添加物流信息
        return goldLogisticsMapper.addLogistics(map);
    }


    //修改客户发货信息
    @Override
    public int updateLogistics(Map map){
        map.put("delivertime",new Date());
        map.put("updateTime",new Date());
        //更新订单采购信息
        goldLogisticsMapper.updateOrderGold(map);
        //更新订单信息
        goldLogisticsMapper.updateOrder(map);
        //添加物流信息
        return goldLogisticsMapper.updateLogistics(map);
    }

    @Override
    public int updateOrderDelivery(Map map){
        return goldLogisticsMapper.updateOrder(map);
    }
}
