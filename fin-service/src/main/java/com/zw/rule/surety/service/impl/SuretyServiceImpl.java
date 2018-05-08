package com.zw.rule.surety.service.impl;

import com.zw.base.util.DateUtils;
import com.zw.rule.customer.po.Order;
import com.zw.rule.mapper.orderLog.OrderLogMapper;
import com.zw.rule.mapper.surety.SuretyMapper;
import com.zw.rule.orderLog.po.MagOrderLogs;
import com.zw.rule.po.User;
import com.zw.rule.surety.Surety;
import com.zw.rule.surety.SuretyRelOrder;
import com.zw.rule.surety.service.SuretyService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Administrator on 2017/12/12.
 */
@Service
public class SuretyServiceImpl implements SuretyService {
    @Resource
    private SuretyMapper mapper;

    //担保日志
    @Resource
    private OrderLogMapper orderLogMapper;

    /**
     * 获取全部的担保人返回担保人集合
     * @param map
     * @return
     */
    public List<Surety> getAllSureList(Map map) {
        return mapper.getAllSuretyList(map);
    }

    /**
     * 添加担保人服务层方法
     * @param surety
     * @return
     */
    public int addOneSurety(Surety surety){
        return mapper.insertOneSurety(surety);
    }

    /**
     * 查询担保人担保订单服务层方法
     * @param map
     * @return
     */
    public List<Order> getSuretyOrders(Map map){
        return mapper.selectSuretyOrders(map);
    }

    /**
     * 查询可以被担保人担保过的订单 未被担保过且审核未通过的(state=3)
     * @return
     */
    public List<Order> getCanBeSuretyOrders(Map map){
       return mapper.selectCanBeSuretyOrders(map);
    }

    /**
     * 担保人去担保订单
     * map 里面有 担保人id 和订单id集合
     * @param map
     * @return
     */
    public int suretyGoAssureOrder(Map map) {
        int i = 0;
        if (map.get("suretyId") !=null && map.get("orderId") !=null) {
            String suretyId = map.get("suretyId").toString();
            String orderId = map.get("orderId").toString();
            SuretyRelOrder suretyRelOrder = new SuretyRelOrder();
            suretyRelOrder.setId(UUID.randomUUID().toString());//id
            suretyRelOrder.setSuretyId(suretyId);//担保人id
            suretyRelOrder.setOrderId(orderId);//订单id
            suretyRelOrder.setState("1");//关联状态
            suretyRelOrder.setCreateTime(DateUtils.getCurrentTime(DateUtils.STYLE_10));//创建时间
            i = i + mapper.suretyGotoAssureOrder(suretyRelOrder);
        }
        return i;
    }

    /**
     * 插入订单担保日志
     * @param map
     * @return
     */
    public int addOrderAssureLogs(Map map){
        MagOrderLogs magOrderLogs=new MagOrderLogs();
        magOrderLogs.setId(UUID.randomUUID().toString());
        magOrderLogs.setOperatorId(map.get("handlerId").toString());//当前操作人id
        magOrderLogs.setOperatorName(map.get("handlerName").toString());//当前操作人
        magOrderLogs.setOperatorType("1");//后台操作
        magOrderLogs.setOrderId(map.get("orderId").toString());//订单id
        magOrderLogs.setCreatTimeLog(DateUtils.getDateString(new Date()));//操作时间
        magOrderLogs.setTache(map.get("tache").toString());//环节
        magOrderLogs.setChangeValue(map.get("changeValue").toString());//结果描述
        magOrderLogs.setState(map.get("state").toString());
        magOrderLogs.setCreatTime(DateUtils.getDateString(new Date()));
        magOrderLogs.setAlterTime(DateUtils.getDateString(new Date()));
        orderLogMapper.addOrderLogs(magOrderLogs);
        return 1;
    }
}
