package com.zw.rule.mapper.task;

import com.zw.rule.mybatis.ParamFilter;
import com.zw.rule.task.po.ProcessTask;
import com.zw.rule.task.po.ProcessTaskNode;

import java.util.List;
import java.util.Map;

public interface TaskMsgMapper {
    List<Map> getCommitTask(ParamFilter paramFilter);

    List<Map> getCommitDetailTask(ParamFilter paramFilter);

    List<Map> getNotCommitTask(ParamFilter paramFilter);

    List<Map> getMonitor(ParamFilter paramFilter);

    int countCommitTask(Map map);

    int countCommitDetailTask(Map map);

    int countNotCommitTask(Map map);

    int countMonitor(Map map);

    int updateTaskState(ProcessTask processTask);

    int updateNodeState(ProcessTaskNode processTaskNode);

    int updateNodeStates(ProcessTaskNode processTaskNode);

    int createTaskNode(ProcessTaskNode processTaskNode);

    List<Map> getUserByStation(ParamFilter paramFilter);

    int countUserByStation(Map map);

    int updateTaskNodeByUser(Map map);

    List<Map> getOrderId(ProcessTaskNode processTaskNode);
}