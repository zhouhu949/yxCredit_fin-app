package com.zw.rule.mapper.task;

import com.zw.rule.task.po.ProcessTask;
import com.zw.rule.task.po.ProcessTaskNode;
import com.zw.rule.process.po.Process;

import java.util.List;
import java.util.Map;

public interface ProcessTaskMapper {
    int deleteByPrimaryKey(String id);

    int insert(ProcessTask record);

    int insertSelective(ProcessTask record);

    ProcessTask selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ProcessTask record);

    int updateByPrimaryKey(ProcessTask record);

    Process getProcessByProId(Map map);

    int insertTask(ProcessTask processTask);

    int updateTask(Map map);

    ProcessTask searchByRelId(String relId);

    void delTaskNode(String taskId);

    String getNextNode(Long processId);

    int insertTaskNode(ProcessTaskNode processTaskNode);

    List<Map> isExist(Map map);

    List<Map> getReturnTask(String relId);

    List<Map> isExistTask(String relId);

    List<Map> getReturnTaskNodeId(String taskId);

    int changeTask(String relId);

    int changeTaskNode(String taskNodeId);
}