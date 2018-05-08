package com.zw.rule.task.service;

import com.zw.rule.process.po.Process;
import com.zw.rule.process.po.ProcessNode;
import com.zw.rule.task.po.ProcessTask;
import com.zw.rule.task.po.ProcessTaskNode;
import com.zw.rule.task.po.TaskOperationRecord;

import java.util.Map;

/**
 * Created by Administrator on 2017/6/24.
 */
public interface ProcessTaskService {
    /**
     * 根据产品id去获取对应流程
     * @Map Map
     * @return
     */
    Process getProcessByProId(Map map);

    /**
     * 插入一条任务到任务表
     * @ProcessTask ProcessTask
     * @return
     */
    int insertTask(ProcessTask processTask);

    /**
     * 根据流程ID去查询当前流程下的开始节点连线的节点
     * @param processId
     * @return
     */
    String getNextNode(Long processId);

    /**
     * 插入任务节点表
     * @param processTaskNode
     * @return
     */
    int insertTaskNode(ProcessTaskNode processTaskNode);

    /**
     * 根据当前节点去查找下个节点
     * @param processNode
     * @return
     */
    String getNextNodeByProcessNode(Long processNode);

    /**
     * 根据param去查节点信息
     * @param param 包含流程id和nextNodeCode
     * @return
     */
    ProcessNode getProcessNode(Map param);

    /**
     * 更改任务节点表的节点状态
     * @param param 包含status processNode taskId userId
     * @return
     */
    int updateProcessTaskNodeStatus(Map param);

    /**
     * 更改任务表的节点状态
     * @param param 包含status taskId
     * @return
     */
    int updateProcessTaskStatus(Map param);

    /**
     * 任务转寄
     * @param param 包含taskNodeId和userId
     * @return
     */
    int changeTask(Map param);

    /**
     * 任务退回
     * @param taskNodeId 必需
     * @return
     */
    int exitTask(String taskNodeId);
}
