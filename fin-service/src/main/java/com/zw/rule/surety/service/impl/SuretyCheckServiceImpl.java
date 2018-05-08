package com.zw.rule.surety.service.impl;

import com.zw.base.util.DateUtils;
import com.zw.rule.approveRecord.po.ProcessApproveRecord;
import com.zw.rule.customer.po.AppUser;
import com.zw.rule.customer.po.Order;
import com.zw.rule.mapper.customer.AppUserMapper;
import com.zw.rule.mapper.customer.OrderMapper;
import com.zw.rule.mapper.surety.SuretyCheckMapper;
import com.zw.rule.surety.Surety;
import com.zw.rule.surety.service.SuretyCheckService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Administrator on 2018/1/5.
 */
@Service
public class SuretyCheckServiceImpl implements SuretyCheckService{
    @Resource
    private SuretyCheckMapper mapper;
    @Resource
    private OrderMapper orderMapper;
    @Resource
    private AppUserMapper userMapper;

    //获取所有被担保的订单
    public List<Order> getAllSuretyOrders(Map map) {
        return mapper.selectAllSuretyOrder(map);
    }

    /**
     * 操作订单和担保人关联表，更改关联表状态(状态改为2担保成功)
     * @param map
     * @return
     */
    public int changeSuretyOrderState(Map map) {
        return mapper.updateSuretyOrderState(map);
    }

    /**
     * 审核担保人担保的订单通过更改意见
     * @param map
     * @return
     */
    public Boolean addApproveRecord(Map map) {
        ProcessApproveRecord processApproveRecord = new ProcessApproveRecord();
        String uuid = UUID.randomUUID().toString();
        processApproveRecord.setId(uuid);
        processApproveRecord.setCreateTime(DateUtils.getDateString(new Date()));
        processApproveRecord.setCommitTime(DateUtils.getDateString(new Date()));
        processApproveRecord.setOrderId(map.get("id").toString());
        processApproveRecord.setRelId(map.get("customerId").toString());
        processApproveRecord.setType(map.get("type").toString());//1客户id，2商户id
        processApproveRecord.setState(map.get("state").toString());
        processApproveRecord.setResult(map.get("result").toString());//结果 1通过  0拒绝
        processApproveRecord.setApproveSuggestion(map.get("approveSuggestion").toString());//审批意见
        processApproveRecord.setHandlerId(map.get("handlerId").toString());//处理人id
        processApproveRecord.setHandlerName(map.get("handlerName").toString());//处理人姓名
        processApproveRecord.setNodeId("2");//总部审核1；审核担保人担保订单2; 记录在哪里生成的审核意见
        int num = mapper.insertSelective(processApproveRecord);
        if (num > 0 && map.get("result").toString() == "0")
        {
            Order order = orderMapper.selectByPrimaryKey(map.get("id").toString());
            AppUser user = new AppUser();
            user.setId(order.getUserId());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            user.setOrderRefusedTime(sdf.format(new Date()));
            userMapper.updateByPrimaryKeySelective(user);
        }
        else if (num > 0 && map.get("result").toString() == "1")
        {
            Order order = orderMapper.selectByPrimaryKey(map.get("id").toString());
            AppUser user = new AppUser();
            user.setId(order.getUserId());
            user.setOrderRefusedTime("");
            userMapper.updateByPrimaryKeySelective(user);
        }
        return num>0;
    }

    /**
     * 审核担保订单被拒绝
     * @param map
     * @return
     */
    public Boolean addRefusedRecord(Map map){
        ProcessApproveRecord processApproveRecord = new ProcessApproveRecord();
        String uuid = UUID.randomUUID().toString();
        processApproveRecord.setId(uuid);
        processApproveRecord.setCreateTime(DateUtils.getDateString(new Date()));
        processApproveRecord.setCommitTime(DateUtils.getDateString(new Date()));
        processApproveRecord.setOrderId(map.get("id").toString());//订单id
        processApproveRecord.setRelId(map.get("customerId").toString());//客户id
        processApproveRecord.setType(map.get("type").toString());//1客户id，2商户id
        //processApproveRecord.setState(map.get("state").toString());
        processApproveRecord.setResult(map.get("result").toString());//结果 1通过  0拒绝
        processApproveRecord.setApproveSuggestion(map.get("approveSuggestion").toString());//审批意见
        processApproveRecord.setHandlerId(map.get("handlerId").toString());//处理人id
        processApproveRecord.setHandlerName(map.get("handlerName").toString());//处理人姓名
        processApproveRecord.setNodeId("2");//总部审核1；审核担保人担保订单2; 记录在哪里生成的审核意见
        int num = mapper.insertSelective(processApproveRecord);
        if (num > 0 && map.get("result").toString() == "0")
        {
            Order order = orderMapper.selectByPrimaryKey(map.get("id").toString());
            AppUser user = new AppUser();
            user.setId(order.getUserId());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            user.setOrderRefusedTime(sdf.format(new Date()));
            userMapper.updateByPrimaryKeySelective(user);
        }
        else if (num > 0 && map.get("result").toString() == "1")
        {
            Order order = orderMapper.selectByPrimaryKey(map.get("id").toString());
            AppUser user = new AppUser();
            user.setId(order.getUserId());
            user.setOrderRefusedTime("");
            userMapper.updateByPrimaryKeySelective(user);
        }
        return num>0;
    }
    //查询担保后并且审核后的订单
    public  List<Order> getSuretyCheckOrders(Map map){
        return mapper.selectAllSuretyCheckOrderShow(map);
    }

    /**
     * 查询该订单的担保人信息
     * @param relId
     * @return
     */
    public Surety getOrderSurety(String relId) {
        return mapper.getSuretyInformation(relId);
    }

    /**
     * 根据审批意见表id查询审批意见
     * @param id
     * @return
     */
    public ProcessApproveRecord getProById(String id) {
        return mapper.selectProById(id);
    }
}
