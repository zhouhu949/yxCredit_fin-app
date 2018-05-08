package com.zw.rule.util;

import com.zw.rule.process.po.Process;
import com.zw.rule.process.po.ProcessNode;
import com.zw.rule.process.service.AutoFunctionService;
import com.zw.rule.task.po.ProcessTask;
import com.zw.rule.task.po.ProcessTaskNode;
import com.zw.rule.task.po.TaskOperationRecord;
import com.zw.rule.task.service.ProcessTaskService;
import com.zw.rule.task.service.TaskOperationRecordService;

import javax.annotation.Resource;
import java.util.*;

/**
 * <strong>Title : <br>
 * </strong> <strong>Description : </strong>@类注释说明写在此处@<br>
 * <strong>Create on : 2017年06月22日<br>
 * </strong>
 * <p>
 * <strong>Copyright (C) zw.<br>
 * </strong>
 * <p>
 *
 * @author department:技术开发部 <br>
 *         username:Administrator <br>
 *         email: <br>
 * @version <strong>zw有限公司-运营平台</strong><br>
 *          <br>
 *          <strong>修改历史:</strong><br>
 *          修改人 修改日期 修改描述<br>
 *          -------------------------------------------<br>
 *          <br>
 *          <br>
 */
public class FlowCom {
    @Resource
    private ProcessTaskService processTaskService;

    @Resource
    private TaskOperationRecordService taskOperationRecordService;

    /**
     * 方法说明：根据关联id和关联type查找流程并往任务表和任务节点表(插入当前流程的开始节点连线的下一节点，作为初始任务)插入相应数据
     * @parMap 包含参数：relId   userId  relType
     * @return 返回msg和processId和taskId和当前初始节点processNode
     */
    public Map<String,Object> getProcessByProId(Map parMap){
        Long processId = null;//流程id
        Long userId = Long.decode((String) parMap.get("userId"));
        String relType = (String) parMap.get("relType");
        String relId = (String) parMap.get("relId");
        ProcessTask processTask = new ProcessTask();
        ProcessTaskNode processTaskNode = new ProcessTaskNode();
        TaskOperationRecord taskOperationRecord = new TaskOperationRecord();
        Map<String,Object> param = new HashMap<String,Object>();
        Map<String,Object> paramMap = new HashMap<String,Object>();
        String msg = "";
        String taskId = UUID.randomUUID().toString();//生成process_task表的主键ID
        String taskNodeId = UUID.randomUUID().toString();//生成process_task_node表的主键ID
        Map map = new HashMap();
        map.put("relId",relId);map.put("relType",relType);
        Process process = processTaskService.getProcessByProId(map);//根据产品id获取对应流程
        if(process!=null){
            processId = process.getId();
        }
        if(processId!=null&&processId>0L){
            processTask.setProcessId(processId);
            processTask.setId(taskId);
            processTask.setRelId(relId);
            processTask.setTaskState(0);
            processTaskService.insertTask(processTask);//插入新任务到process_task表
            //记录当前任务信息插入到任务操作记录表
            taskOperationRecord.setId(UUID.randomUUID().toString());
            taskOperationRecord.setTaskId(taskId);
            taskOperationRecord.setMsg("插入任务到process_task表");
            taskOperationRecord.setRelId(relId);
            taskOperationRecord.setRelType("1");
            taskOperationRecord.setStatus("1");
            taskOperationRecord.setType("1");
            taskOperationRecord.setUserId(userId);
            taskOperationRecordService.insertTaskOperationRecord(taskOperationRecord);
            String processNodeCode = processTaskService.getNextNode(processId);//获取开始节点连线的节点node_code
            if(processNodeCode!=null&&processNodeCode.length()>0){
                paramMap.put("processId",processId);
                paramMap.put("nextNodeCode",processNodeCode);
                //根据流程id和nextNodeCode去流程节点表查找下个节点的信息
                ProcessNode processNode = processTaskService.getProcessNode(paramMap);
                processTaskNode.setProcessNode(processNode.getNodeId());
                processTaskNode.setId(taskNodeId);
                processTaskNode.setTaskId(taskId);
                processTaskNode.setOperateUser(userId);
                processTaskNode.setNodeState(0);//初始状态为0：待处理
                processTaskNode.setExcType(processNode.getExcType());//节点类型1：人工节点 0：自动节点
                processTaskNode.setNodeJson(processNode.getNodeJson());
                processTaskNode.setNodeScript(processNode.getNodeScript());
                processTaskNode.setClassName(processNode.getClassName());
                processTaskNode.setPageUrl(processNode.getPageUrl());
                processTaskNode.setRelId(relId);
                processTaskNode.setRelType(relType);
                processTaskService.insertTaskNode(processTaskNode);//插入开始节点下的节点作为初始节点

                taskOperationRecord.setMsg("插入任务节点到process_task_node表");
                taskOperationRecord.setTaskNodeId(taskNodeId);
                taskOperationRecord.setNodeId(processNode.getNodeId());
                taskOperationRecord.setId(UUID.randomUUID().toString());
                taskOperationRecord.setType("2");
                //记录当前任务节点信息插入到任务操作记录表
                taskOperationRecordService.insertTaskOperationRecord(taskOperationRecord);
                msg = "操作完成";
                param.put("msg",msg);
                param.put("processId",processId);
                param.put("processTaskNode",processTaskNode);
                return param;
            }else{
                msg = "该流程开始节点下没有连线";
                param.put("msg",msg);
                param.put("processId",processId);
                param.put("taskId",taskId);
                return param;
            }
        }else{
            msg = "没有找到对应流程";
            param.put("taskId",taskId);
            param.put("msg",msg);
            param.put("processId",processId);
            return param;
        }
    }

    /**
     * 获取任务getTask
     * 方法说明：根据传过来的已处理通过的节点去zw_process_node表查询
     *  当前节点的下一节点并插入到process_task_node表去，
     * @param param 包含流程id和当前节点id和taskId和userId和relId和relType
     * @return 返回刚插入的那条任务，如果为null说明传过来的节点是当前流程的最后一个节点
     */
    public List<ProcessTaskNode> getTask(Map<String,Object> param){
        ProcessTaskNode processTaskNode = new ProcessTaskNode();
        TaskOperationRecord taskOperationRecord = new TaskOperationRecord();
        List<ProcessTaskNode> processTaskNodeList = new ArrayList<ProcessTaskNode>();
        Long processNode = Long.decode((String)param.get("processNode"));
        String taskId = param.get("taskId").toString();
        Long userId = Long.decode(param.get("userId").toString());
        String relId = null;
        if(param.containsKey("relId")){
            relId = param.get("relId").toString();
        }
        String relType = param.get("relType").toString();
        String nextNodeCode = processTaskService.getNextNodeByProcessNode(processNode);//根据当前节点去查找下个节点
        if(nextNodeCode!=null&&nextNodeCode.length()>0){
            String nextNodesArr[] = nextNodeCode.split(",");
            for(int i = 0;i<nextNodesArr.length;i++){
                if(nextNodesArr[i]!=null&&nextNodesArr[i].length()>0){
                    Map paramMap = new HashMap();
                    paramMap.put("processId",(String)param.get("processId"));
                    paramMap.put("nextNodeCode",nextNodesArr[i]);
                    //根据流程id和nextNodeCode去流程节点表查找下个节点的信息
                    ProcessNode processNode1 = processTaskService.getProcessNode(paramMap);
                    if(processNode1!=null){
                        if(processNode1.getNodeId()!=null&&processNode1.getNodeId()>0L){
                            String taskNodeId = UUID.randomUUID().toString();//生成process_task_node表的主键ID
                            processTaskNode.setProcessNode(processNode1.getNodeId());
                            processTaskNode.setId(taskNodeId);
                            processTaskNode.setTaskId(taskId);
                            processTaskNode.setOperateUser(userId);
                            processTaskNode.setNodeState(0);//初始状态为0：待处理
                            processTaskNode.setExcType(processNode1.getExcType());
                            processTaskNode.setNodeJson(processNode1.getNodeJson());
                            processTaskNode.setNodeScript(processNode1.getNodeScript());
                            processTaskNode.setClassName(processNode1.getClassName());
                            processTaskNode.setPageUrl(processNode1.getPageUrl());
                            processTaskNode.setRelId(relId);
                            processTaskNode.setRelType(relType);
                            processTaskService.insertTaskNode(processTaskNode);
                            //记录当前任务信息插入到任务操作记录表
                            taskOperationRecord.setId(UUID.randomUUID().toString());
                            taskOperationRecord.setTaskId(taskId);
                            taskOperationRecord.setTaskNodeId(taskNodeId);
                            taskOperationRecord.setNodeId(processNode);
                            taskOperationRecord.setExcType(processNode1.getExcType());
                            taskOperationRecord.setMsg("插入任务节点到process_task_node表");
                            taskOperationRecord.setRelType("1");
                            taskOperationRecord.setStatus("1");
                            taskOperationRecord.setType("2");
                            taskOperationRecord.setUserId(userId);
                            taskOperationRecordService.insertTaskOperationRecord(taskOperationRecord);
                            processTaskNodeList.add(processTaskNode);
                        }
                    }
                }
            }
            return processTaskNodeList;
        }else{
            return null;
        }
    }

    /**
     * 提交任务
     * @param param 包含status 状态 必需
     *              taskNodeId 当前节点任务ID 必需
     *              userId 用户ID 必需
     *              excType 节点类型 必需
     *              type 判断是否为最后一个节点 可选（是就传个值否则就不提供该参数）
     *              taskId 总任务id 可选（如果是最后一个节点就必需要有，type有的话或者状态为3拒绝的时候）
     * @return
     */
    public void CommitTask(Map param){
        Integer status = Integer.decode(param.get("status").toString());
        TaskOperationRecord taskOperationRecord = new TaskOperationRecord();
        String taskId = "";
        String taskNodeId = "";
        if(param.containsKey("taskId")){
            taskId = param.get("taskId").toString();
        }
        if(param.containsKey("taskNodeId")){
            taskId = param.get("taskNodeId").toString();
        }
        String excType = (String)param.get("excType");
        Long userId = Long.decode(param.get("userId").toString());
        taskOperationRecord.setId(UUID.randomUUID().toString());
        taskOperationRecord.setTaskId(taskId);
        taskOperationRecord.setTaskNodeId(taskNodeId);
        taskOperationRecord.setUserId(userId);
        taskOperationRecord.setStatus("1");
        if(param.containsKey("type")){//说明是最后一个节点
            processTaskService.updateProcessTaskStatus(param);
            processTaskService.updateProcessTaskNodeStatus(param);
            taskOperationRecord.setMsg("提交总任务和节点任务状态，更新process_task表，以及process_task_node表");
            taskOperationRecord.setType("3");
            taskOperationRecordService.insertTaskOperationRecord(taskOperationRecord);
        }else{//说明不是最后一个节点
            if(status==3){//如果status为3-拒绝 更改当前节点状态为3且更改总状态为3
                if(excType.equals("3")){//是决策节点
                    processTaskService.updateProcessTaskNodeStatus(param);
                    taskOperationRecord.setMsg("提交节点任务状态，更新process_task_node表");
                    taskOperationRecord.setType("2");
                    taskOperationRecordService.insertTaskOperationRecord(taskOperationRecord);
                }else{
                    processTaskService.updateProcessTaskStatus(param);
                    processTaskService.updateProcessTaskNodeStatus(param);
                    taskOperationRecord.setMsg("提交总任务和节点任务状态，更新process_task表，以及process_task_node表");
                    taskOperationRecord.setType("3");
                    taskOperationRecordService.insertTaskOperationRecord(taskOperationRecord);
                }
            }else {
                //更改当前节点状态为status
                processTaskService.updateProcessTaskNodeStatus(param);
                taskOperationRecord.setMsg("提交节点任务状态，更新process_task_node表");
                taskOperationRecord.setType("2");
                taskOperationRecordService.insertTaskOperationRecord(taskOperationRecord);
            }
        }
    }

    /**
     * 任务转寄
     * @param param 包含以下参数
     * nowUserId   当前操作用户id 必需
     * userId  用户id 必需
     * taskNodeId 任务节点表主键 必需
     */
    public int changeTask(Map param){
        TaskOperationRecord taskOperationRecord = new TaskOperationRecord();
        Long nowUserId = Long.decode(param.get("nowUserId").toString());//取当前操作系统的用户id
        Long userId = Long.decode(param.get("userId").toString());//取需要转寄任务到那个用户的id
        String taskNodeId = param.get("taskNodeId").toString();
        taskOperationRecord.setId(UUID.randomUUID().toString());
        taskOperationRecord.setTaskNodeId(taskNodeId);
        taskOperationRecord.setUserId(nowUserId);
        taskOperationRecord.setStatus("1");
        taskOperationRecord.setMsg(nowUserId+"的任务转寄到用户id为"+userId+"，更新process_task_node表");
        taskOperationRecord.setType("2");
        int num = processTaskService.changeTask(param);
        if(num>0){
            taskOperationRecordService.insertTaskOperationRecord(taskOperationRecord);
            return num;
        }else {
            return 0;
        }
    }

    /**
     * 任务退回
     * taskNodeId 任务节点表主键 必需
     * userId  用户id 必需
     */
    public int exitTask(Map param){
        TaskOperationRecord taskOperationRecord = new TaskOperationRecord();
        Long userId = Long.decode(param.get("userId").toString());
        String taskNodeId  = param.get("taskNodeId").toString();
        taskOperationRecord.setId(UUID.randomUUID().toString());
        taskOperationRecord.setTaskNodeId(taskNodeId);
        taskOperationRecord.setUserId(userId);
        taskOperationRecord.setStatus("1");
        taskOperationRecord.setMsg("任务退回，更新process_task_node表");
        taskOperationRecord.setType("2");
        int num = processTaskService.exitTask(taskNodeId);
        if(num>0){
            taskOperationRecordService.insertTaskOperationRecord(taskOperationRecord);
            return num;
        }else {
            return 0;
        }
    }

    /**
     * 自动化节点
     * @param param 包含以下参数
     * className String 全类名 必需
     * taskNodeId  String 任务节点表主键id 必需
     * dataId      String 数据id 必需
     *
     * @throws ClassNotFoundException
     */
    public void autoFunction(Map param) throws ClassNotFoundException {
        try {
            String className = param.get("className").toString();
            String taskNodeId = param.get("taskNodeId").toString();
            String relId = param.get("relId").toString();
            AutoFunctionService autoFunctionService = (AutoFunctionService)Class.forName(className).newInstance();
            autoFunctionService.doWork(taskNodeId,relId);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}