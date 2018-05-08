package com.zw.rule.mapper.task;

import com.zw.rule.task.po.ProcessTaskNode;

import java.util.List;
import java.util.Map;

public interface ProcessTaskNodeMapper {
    int deleteByPrimaryKey(String id);

    int insert(ProcessTaskNode record);

    int insertSelective(ProcessTaskNode record);

    ProcessTaskNode selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ProcessTaskNode record);

    int updateByPrimaryKey(ProcessTaskNode record);

    String getNextNodeByProcessNode(Long processNode);

    String getNodeJsonByProcessNode(Long processNode);

    int updateProcessTaskNodeStatus(Map param);

    int updateProcessTaskStatus(Map param);

    String getTaskNodeId(String relId);

    int changeTask(Map param);

    int exitTask(String taskNodeId);

    int initProcessTaskNode(Map param);

    List<Map> findIdList(String taskNodeId);

    List<Map> isCommit(String taskNodeId);

    void updateApproveSuggestion(Map map);
}