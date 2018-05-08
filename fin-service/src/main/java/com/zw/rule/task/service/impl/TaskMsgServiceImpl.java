package com.zw.rule.task.service.impl;

import com.zw.rule.customer.po.Order;
import com.zw.rule.mapper.customer.OrderMapper;
import com.zw.rule.mapper.system.StationMapper;
import com.zw.rule.mapper.task.TaskMsgMapper;
import com.zw.rule.mybatis.ParamFilter;
import com.zw.rule.task.po.ProcessTaskNode;
import com.zw.rule.task.service.TaskMsgService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/******************************************************
 *Copyrights @ 2017，zhiwang  Co., Ltd.
 *
 *All rights reserved.
 *              小窝
 *Filename：
 *         流程任务业务逻辑
 *Description：
 *		  未知
 *Author:
 *		 李开艳
 *Finished：
 *		2017/6/14
 ********************************************************/
@Service
public class TaskMsgServiceImpl implements TaskMsgService {
    @Resource
    private TaskMsgMapper taskMsgMapper;
    @Resource
    private StationMapper stationMapper;
    @Resource
    private OrderMapper orderMapper;



    /**
     * 已经开始处理的任务(任务状态 1-处理中 2-挂起 3-拒绝 4-通过)
     * @param paramFilter
     * {
     *     param:{currentUserId 当前登录用户ID,
     *            nodeState 任务节点状态
     *           }
     *     page:{firstIndex pageSize}
     * }
     * @return
     */
    @Override
    public List<Map> getCommitTask(ParamFilter paramFilter) {
        int resultCount = countCommitTask(paramFilter.getParam());
        paramFilter.getPage().setResultCount(resultCount);
        return taskMsgMapper.getCommitTask(paramFilter);
    }

    @Override
    public List<Map> getDetailTask(ParamFilter paramFilter) {
        int resultCount = countCommitDetailTask(paramFilter.getParam());
        paramFilter.getPage().setResultCount(resultCount);
        return taskMsgMapper.getCommitDetailTask(paramFilter);
    }

    /**
     * 待处理的任务(任务状态 0-未处理 )
     * @param paramFilter
     * {
     *     param:{currentUserId 当前登录用户ID}
     *     page:{firstIndex pageSize}
     * }
     * @return
     */
    @Override
    public List<Map> getNotCommitTask(ParamFilter paramFilter) {
        int resultCount = countNotCommitTask(paramFilter.getParam());
        paramFilter.getPage().setResultCount(resultCount);
        return taskMsgMapper.getNotCommitTask(paramFilter);
    }

    @Override
    public List<Map> getMonitor(ParamFilter paramFilter) {
        int resultCount = countMonitor(paramFilter.getParam());
        paramFilter.getPage().setResultCount(resultCount);
        return taskMsgMapper.getMonitor(paramFilter);
    }

    @Override
    public int countCommitTask(Map map) {
        int resultCount = taskMsgMapper.countCommitTask(map);
        return resultCount;
    }
    public int countCommitDetailTask(Map map) {
        int resultCount = taskMsgMapper.countCommitDetailTask(map);
        return resultCount;
    }

    @Override
    public int countNotCommitTask(Map map) {
        int resultCount = taskMsgMapper.countNotCommitTask(map);
        return resultCount;
    }

    @Override
    public int countMonitor(Map map) {
        int resultCount = taskMsgMapper.countMonitor(map);
        return resultCount;
    }

    /**
     * 领取，解挂，挂起任务
     * @param map
     * {
     *     id: String 任务id或任务节点id
     *     state: int 状态
     *     processNodeId: Long 流程节点id
     * }
     * @param currentUserId
     * @return
     */
    @Override
    public Boolean updateState(Map map,Long currentUserId) {
        int num = 0;
        String state = map.get("state").toString();
        String id = map.get("id").toString();
        //领取操作
        if(state.equals("receive")){
            ProcessTaskNode processTaskNode = new ProcessTaskNode();
            processTaskNode.setId(id);
            processTaskNode.setNodeState(1);//新建的任务节点，状态默认为处理中
            processTaskNode.setOperateUser(currentUserId);
            List<Map> orderList=taskMsgMapper.getOrderId(processTaskNode);
                String orderId=orderList.get(0).get("rel_id").toString();
                Order order=new Order();
                order.setId(orderId);
                order.setReceiveId(String.valueOf(currentUserId));
                orderMapper.updateReceiveId(order);
            num += taskMsgMapper.updateNodeState(processTaskNode);
//            num += taskMsgMapper.updateTaskState(processTask);
        }else{
            int stat = 0;
            ProcessTaskNode processTaskNode = new ProcessTaskNode();
            processTaskNode.setId(id);
            if(state.equals("unlock")) stat = 1;//解挂，状态改为处理中
            if(state.equals("lock")) stat = 2;//挂起，状态改为挂起
            processTaskNode.setNodeState(stat);
            num += taskMsgMapper.updateNodeStates(processTaskNode);
        }
        return num>0;
    }

    /**
     * 转寄任务
     * @param paramFilter
     * {
     *     param:{
     *             id: String 任务id或任务节点id
     *             processId: Long 流程id
     *             processNodeId: Long 流程节点id
     *           }
     *     page:{firstIndex pageSize}
     * }
     * @param currentUserId
     * @return
     */
    @Override
    public List<Map> forwardTask(ParamFilter paramFilter, Long currentUserId) {
        long processId = Long.valueOf(Long.parseLong(paramFilter.getParam().get("processId").toString()));
        long processNodeId = Long.valueOf(Long.parseLong(paramFilter.getParam().get("processNodeId").toString()));
        List stationIds = stationMapper.getStationByUPN(currentUserId,processId,processNodeId);
        if(stationIds.size()>0){
            Map mapStation = new HashMap();
            mapStation.put("stationIds",stationIds);
            mapStation.put("currentUserId",currentUserId);
            paramFilter.setParam(mapStation);
            int resultCount = taskMsgMapper.countUserByStation(mapStation);
            paramFilter.getPage().setResultCount(resultCount);
            List users = taskMsgMapper.getUserByStation(paramFilter);
            return users;
        }else{
            paramFilter.getPage().setResultCount(0);
            return null;
        }
    }

    @Override
    public Boolean updateTaskNodeByUser(Map map) {
        int num = taskMsgMapper.updateTaskNodeByUser(map);
        return num>0;
    }
}
