package com.zw.rule.util.flow.service;

import com.zw.rule.process.po.ProcessNode;
import com.zw.rule.task.po.ProcessTaskNode;

import java.util.Map;

/**
 * Created by Administrator on 2017/7/5.
 */
public interface FlowComService {
    Map<String,Object> getProcessByProId(String relId,String orderId,Long userId,Map conditionMap) throws Exception;
    ProcessTaskNode getTask(Map<String,Object> param,Map map) throws Exception;
    ProcessTaskNode CommitTask(String relId,Long userId,Integer status,String taskNodeId,String approve) throws Exception;
    ProcessTaskNode CommitTask(String relId,Long userId,Integer status,String taskNodeId,String approve,Map map) throws Exception;
    int changeTask(Map param);
    int exitTask(Map param);
    void autoFunction(Map param) throws ClassNotFoundException;
    void exitStartTask(String taskNodeId, String approveSuggestion);
    ProcessNode getNextNode(Long nodeId);
    String getTaskNodeId(String orderId);
    ProcessTaskNode exitPreTask(String taskNodeId, String approveSuggestion);
}
