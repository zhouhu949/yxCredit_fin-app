//package com.zw.rule.util.flow.service.impl;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONObject;
//import com.zw.rule.mapper.process.ProcessNodeMapper;
//import com.zw.rule.mapper.task.ProcessTaskMapper;
//import com.zw.rule.mapper.task.ProcessTaskNodeMapper;
//import com.zw.rule.mapper.task.TaskOperationRecordMapper;
//import com.zw.rule.process.po.Process;
//import com.zw.rule.process.po.ProcessNode;
//import com.zw.rule.process.service.AutoFunctionService;
//import com.zw.rule.task.po.ProcessTask;
//import com.zw.rule.task.po.ProcessTaskNode;
//import com.zw.rule.task.po.TaskOperationRecord;
//import com.zw.rule.util.flow.service.FlowComService;
//import org.springframework.stereotype.Service;
//
//import javax.annotation.Resource;
//import java.util.*;
//
///**
// * <strong>Title : <br>
// * </strong> <strong>Description : </strong>@类注释说明写在此处@<br>
// * <strong>Create on : 2017年07月05日<br>
// * </strong>
// * <p>
// * <strong>Copyright (C) zw.<br>
// * </strong>
// * <p>
// *
// * @author department:技术开发部 <br>
// *         username:Administrator <br>
// *         email: <br>
// * @version <strong>zw有限公司-运营平台</strong><br>
// *          <br>
// *          <strong>修改历史:</strong><br>
// *          修改人 修改日期 修改描述<br>
// *          -------------------------------------------<br>
// *          <br>
// *          <br>
// */
//@Service
//public class FlowComServiceImpl implements FlowComService{
//    @Resource
//    private ProcessTaskMapper processTaskMapper;
//
//    @Resource
//    private ProcessNodeMapper processNodeMapper;
//
//    @Resource
//    private ProcessTaskNodeMapper processTaskNodeMapper;
//
//    @Resource
//    private TaskOperationRecordMapper taskOperationRecordMapper;
//
//    /**
//     * 方法说明：根据关联id和关联type查找流程并往任务表和任务节点表(插入当前流程的开始节点连线的下一节点，作为初始任务)插入相应数据
//     * 包含参数：relId   userId   orderId
//     *
//     * 功能码 relId userId
//     * @return 返回msg和processId和taskId和当前初始节点processNode
//     */
//    public Map<String,Object> getProcessByProId(String relId,String orderId,Long userId) throws Exception{
//        Map<String,Object> param = new HashMap<String,Object>();
//        Map<String,Object> paramMap = new HashMap<String,Object>();
//        try{
//            Long processId = null;//流程id
////            Long userId = Long.decode(parMap.get("userId").toString());
////            String relType = (String) parMap.get("relType");
////            String relId = (String) parMap.get("relId");
////            String orderId = (String) parMap.get("orderId");
//            ProcessTask processTask = new ProcessTask();
//            ProcessTaskNode processTaskNode = new ProcessTaskNode();
//            TaskOperationRecord taskOperationRecord = new TaskOperationRecord();
//            String msg = "";
//            String taskId = UUID.randomUUID().toString();//生成process_task表的主键ID
//            String taskNodeId = UUID.randomUUID().toString();//生成process_task_node表的主键ID
//            Map map = new HashMap();
//            map.put("relId",relId);
//            Process process = this.processTaskMapper.getProcessByProId(map);//根据功能码获取对应流程
//            if(process!=null){
//                processId = process.getId();
//            }
//            if(processId!=null&&processId>0L){
//                processTask.setProcessId(processId);
//                processTask.setId(taskId);
//                processTask.setRelId(orderId);//订单id
//                processTask.setTaskState(1);
//                this.processTaskMapper.insertTask(processTask);//插入新任务到process_task表
//                //记录当前任务信息插入到任务操作记录表
//                taskOperationRecord.setId(UUID.randomUUID().toString());
//                taskOperationRecord.setTaskId(taskId);
//                taskOperationRecord.setMsg("插入任务到process_task表");
//                taskOperationRecord.setRelId(orderId);//订单id
////                taskOperationRecord.setRelType(relType);
//                taskOperationRecord.setStatus("1");
//                taskOperationRecord.setType("1");
//                taskOperationRecord.setUserId(userId);
//                this.taskOperationRecordMapper.insertTaskOperationRecord(taskOperationRecord);
//                String processNodeCode = this.processTaskMapper.getNextNode(processId);//获取开始节点连线的节点node_code
//                if(processNodeCode!=null&&processNodeCode.length()>0){
//                    paramMap.put("processId",processId);
//                    paramMap.put("nextNodeCode",processNodeCode);
//                    //根据流程id和nextNodeCode去流程节点表查找下个节点的信息
//                    ProcessNode processNode = this.processNodeMapper.getProcessNode(paramMap);
//                    map.put("nextProcessNode",processNode.getNodeId());
//                    map.put("taskId",taskId);
//                    this.processTaskMapper.updateTask(map);
//                    processTaskNode.setProcessNode(processNode.getNodeId());
//                    processTaskNode.setId(taskNodeId);
//                    processTaskNode.setTaskId(taskId);
//                    processTaskNode.setNodeState(0);//初始状态为0：待处理
//                    processTaskNode.setExcType(processNode.getExcType());//节点类型1：人工节点 0：自动节点
//                    processTaskNode.setNodeJson(processNode.getNodeJson());
//                    processTaskNode.setNodeScript(processNode.getNodeScript());
//                    processTaskNode.setClassName(processNode.getClassName());
//                    processTaskNode.setPageUrl(processNode.getPageUrl());
//                    processTaskNode.setRelId(orderId);//订单id
//                    processTaskNode.setHandUrl(processNode.getHandUrl());
//                    this.processTaskMapper.insertTaskNode(processTaskNode);//插入开始节点下的节点作为初始节点
//
//                    taskOperationRecord.setMsg("插入任务节点到process_task_node表");
//                    taskOperationRecord.setTaskNodeId(taskNodeId);
//                    taskOperationRecord.setNodeId(processNode.getNodeId());
//                    taskOperationRecord.setId(UUID.randomUUID().toString());
//                    taskOperationRecord.setType("2");
//                    //记录当前任务节点信息插入到任务操作记录表
//                    this.taskOperationRecordMapper.insertTaskOperationRecord(taskOperationRecord);
//                    msg = "操作完成";
//                    param.put("msg",msg);
//                    param.put("processId",processId);
//                    param.put("processTaskNode",processTaskNode);
//                    return param;
//                }else{
//                    msg = "该流程开始节点下没有连线";
//                    param.put("msg",msg);
//                    param.put("processId",processId);
//                    param.put("taskId",taskId);
//                    return param;
//                }
//            }else{
//                msg = "没有找到对应流程";
//                param.put("taskId",taskId);
//                param.put("msg",msg);
//                param.put("processId",processId);
//                return param;
//            }
//        }
//        catch (Exception e){
//            e.printStackTrace();
//            return param;
//        }
//    }
//
//    /**
//     * 获取任务getTask
//     * 方法说明：根据传过来的已处理通过的节点去zw_process_node表查询
//     *  当前节点的下一节点并插入到process_task_node表去，
//     * @param param 包含processId和processNode和taskId和userId和orderId
//     * @return 返回刚插入的那条任务，如果为null说明传过来的节点是当前流程的最后一个节点
//     */
//    public List<ProcessTaskNode> getTask(Map<String,Object> param) throws Exception{
//        try{
//            ProcessTaskNode processTaskNode = new ProcessTaskNode();
//            TaskOperationRecord taskOperationRecord = new TaskOperationRecord();
//            List<ProcessTaskNode> processTaskNodeList = new ArrayList<ProcessTaskNode>();
//            Long processNode = Long.decode(param.get("processNode").toString());
//            String taskId = param.get("taskId").toString();
//            Long userId = Long.decode(param.get("userId").toString());
//            String orderId = null;
//            if(param.containsKey("relId")){
//                orderId = param.get("relId").toString();
//            }
//            ProcessNode node = this.processNodeMapper.selectByPrimaryKey(processNode);
//            String nextNodeCode = null;
//            if(node.getExcType()==3){//决策节点
//                String nodeJson = node.getNodeJson().toString();
//                JSONObject json = JSONObject.parseObject(nodeJson);
//                JSONArray jsonArray= (JSONArray)json.get("conditions");
//                for(int i = 0;i<jsonArray.size();i++){
//                    JSONObject jsonCondition= (JSONObject)jsonArray.get(i);
//                    if(jsonCondition.get("group_name").equals(param.get("sta").toString())){
//                        nextNodeCode =  jsonCondition.get("nextNode").toString();
//                    }
//                }
//            }else{
//                nextNodeCode = this.processTaskNodeMapper.getNextNodeByProcessNode(processNode);//根据当前节点去查找下个节点
//            }
//            if(nextNodeCode!=null&&nextNodeCode.length()>0){
//                String nextNodesArr[] = nextNodeCode.split(",");
//                for(int i = 0;i<nextNodesArr.length;i++){
//                    if(nextNodesArr[i]!=null&&nextNodesArr[i].length()>0){
//                        Map<String,Object> paramMap = new HashMap();
//                        paramMap.put("processId",param.get("processId").toString());
//                        paramMap.put("nextNodeCode",nextNodesArr[i]);
//
//                        //根据流程id和nextNodeCode去流程节点表查找下个节点的信息
//                        ProcessNode processNode1 = this.processNodeMapper.getProcessNode(paramMap);
//                        if(processNode1!=null){
//                            if(processNode1.getNodeId()!=null&&processNode1.getNodeId()>0L){
//                                String taskNodeId = UUID.randomUUID().toString();//生成process_task_node表的主键ID
//                                processTaskNode.setProcessNode(processNode1.getNodeId());
//                                processTaskNode.setId(taskNodeId);
//                                processTaskNode.setTaskId(taskId);
//                                processTaskNode.setNodeState(0);//初始状态为0：待处理
//                                processTaskNode.setExcType(processNode1.getExcType());
//                                processTaskNode.setNodeJson(processNode1.getNodeJson());
//                                processTaskNode.setNodeScript(processNode1.getNodeScript());
//                                processTaskNode.setClassName(processNode1.getClassName());
//                                processTaskNode.setPageUrl(processNode1.getPageUrl());
//                                processTaskNode.setHandUrl(processNode1.getHandUrl());
//                                processTaskNode.setRelId(orderId);
//                                this.processTaskMapper.insertTaskNode(processTaskNode);
//                                //记录当前任务信息插入到任务操作记录表
//                                taskOperationRecord.setId(UUID.randomUUID().toString());
//                                taskOperationRecord.setTaskId(taskId);
//                                taskOperationRecord.setTaskNodeId(taskNodeId);
//                                taskOperationRecord.setNodeId(processNode);
//                                taskOperationRecord.setExcType(processNode1.getExcType());
//                                taskOperationRecord.setMsg("插入任务节点到process_task_node表");
//                                taskOperationRecord.setStatus("1");
//                                taskOperationRecord.setType("2");
//                                taskOperationRecord.setUserId(userId);
//                                this.taskOperationRecordMapper.insertTaskOperationRecord(taskOperationRecord);
//                                processTaskNodeList.add(processTaskNode);
//                            }
//                        }
//                    }
//                }
//                return processTaskNodeList;
//            }else{
//                return null;
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    /**
//     * 提交任务
//     *              status 状态 必需
//     *              taskNodeId 当前节点任务ID 必需
//     *              userId 用户ID 必需
//     *              relId
//     *
//     * @return
//     */
//    public void CommitTask(String orderId,Long userId,Integer status,String taskNodeId) throws Exception {
//        Map param = new HashMap();
//        String sta = "";
//        if(status==3){
//            sta="拒绝";
//        }
//        if (status==4){
//            sta="通过";
//        }
//        ProcessTask processTask = this.processTaskMapper.searchByRelId(orderId);
//        ProcessTaskNode processTaskNode = this.processTaskNodeMapper.selectByPrimaryKey(taskNodeId);
//        if(processTask!=null&&taskNodeId!=null){//如果当前订单id已经创建任务
//            String taskId = processTask.getId();
//            Long processNode = processTaskNode.getProcessNode();
//            param.put("processId",processTask.getProcessId());
//            param.put("taskId",taskId);
//            param.put("processNode",processNode);
//            param.put("userId",userId);
//            param.put("taskNodeId",taskNodeId);
//            param.put("relId",orderId);
//            param.put("status",status);
//            if(processTaskNode!=null){
//                String excType = processTaskNode.getExcType().toString();
//                String nextNodeCode = null;
//                if("3".equals(excType)){
//                    String nodeJson = processTaskNode.getNodeJson().toString();
//                    JSONObject json = JSONObject.parseObject(nodeJson);
//                    JSONArray jsonArray= (JSONArray)json.get("conditions");
//                    for(int i = 0;i<jsonArray.size();i++){
//                        JSONObject jsonCondition= (JSONObject)jsonArray.get(i);
//                        if(jsonCondition.get("group_name").equals(sta)){
//                            nextNodeCode =  jsonCondition.get("nextNode").toString();
//                        }
//                    }
//                }else {
//                    nextNodeCode = this.processTaskNodeMapper.getNextNodeByProcessNode(processNode);
//                }
//                if(nextNodeCode.isEmpty()){//说明是最后一个节点
//                    this.updateAllTask(param);
//                }else{//说明不是最后一个节点
//                    if(status==3){//如果status为3-拒绝 更改当前节点状态为3且更改总状态为3
//                        if(excType.equals("3")){//是决策节点
//                            param.put("sta","拒绝");
//                            this.updateNodeTask(param);
//                        }else{
//                            this.updateAllTask(param);
//                        }
//                    }else {
//                        if(excType.equals("3")){
//                            param.put("sta","通过");
//                        }
//                        this.updateNodeTask(param);
//                    }
//                }
//            }
//        }
////        else{
////            this.getProcessByProId(relId,userId);//插入新任务
////        }
//    }
//
//
//
//
//
//    /**
//     * 任务转寄
//     * @param param 包含以下参数
//     * nowUserId   当前操作用户id 必需
//     * userId  用户id 必需
//     * taskNodeId 任务节点表主键 必需
//     */
//    public int changeTask(Map param){
//        TaskOperationRecord taskOperationRecord = new TaskOperationRecord();
//        Long nowUserId = Long.decode(param.get("nowUserId").toString());//取当前操作系统的用户id
//        Long userId = Long.decode(param.get("userId").toString());//取需要转寄任务到那个用户的id
//        String taskNodeId = param.get("taskNodeId").toString();
//        taskOperationRecord.setId(UUID.randomUUID().toString());
//        taskOperationRecord.setTaskNodeId(taskNodeId);
//        taskOperationRecord.setUserId(nowUserId);
//        taskOperationRecord.setStatus("1");
//        taskOperationRecord.setMsg(nowUserId+"的任务转寄到用户id为"+userId+"，更新process_task_node表");
//        taskOperationRecord.setType("2");
//        int num = this.processTaskNodeMapper.changeTask(param);
//        if(num>0){
//            this.taskOperationRecordMapper.insertTaskOperationRecord(taskOperationRecord);
//            return num;
//        }else {
//            return 0;
//        }
//    }
//
//    /**
//     * 任务退回
//     * taskNodeId 任务节点表主键 必需
//     * userId  用户id 必需
//     */
//    public int exitTask(Map param){
//        TaskOperationRecord taskOperationRecord = new TaskOperationRecord();
//        Long userId = Long.decode(param.get("userId").toString());
//        String taskNodeId  = param.get("taskNodeId").toString();
//        taskOperationRecord.setId(UUID.randomUUID().toString());
//        taskOperationRecord.setTaskNodeId(taskNodeId);
//        taskOperationRecord.setUserId(userId);
//        taskOperationRecord.setStatus("1");
//        taskOperationRecord.setMsg("任务退回，更新process_task_node表");
//        taskOperationRecord.setType("2");
//        int num = this.processTaskNodeMapper.exitTask(taskNodeId);
//        if(num>0){
//            this.taskOperationRecordMapper.insertTaskOperationRecord(taskOperationRecord);
//            return num;
//        }else {
//            return 0;
//        }
//    }
//
//    /**
//     * 自动化节点
//     * @param param 包含以下参数
//     * className String 全类名 必需
//     * taskNodeId  String 任务节点表主键id 必需
//     * relId      String 数据id 必需
//     *
//     * @throws ClassNotFoundException
//     */
//    public void autoFunction(Map param) throws ClassNotFoundException {
//        try {
//            String className = param.get("className").toString();
//            String taskNodeId = param.get("taskNodeId").toString();
//            String relId = param.get("relId").toString();
//            AutoFunctionService autoFunctionService = (AutoFunctionService)Class.forName(className).newInstance();
//            autoFunctionService.doWork(taskNodeId,relId);
//        } catch (InstantiationException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public ProcessNode getNextNode(Long nodeId) {
//        return processNodeMapper.selectByPrimaryKey(nodeId);
//    }
//
//    public String getTaskNodeId(String orderId){
//        return processNodeMapper.getTaskNodeId(orderId);
//    }
//
//
//    public void updateAllTask(Map param){
//        TaskOperationRecord taskOperationRecord = new TaskOperationRecord();
//        String taskNodeId = "";
//        if(param.containsKey("taskNodeId")){
//            taskNodeId = param.get("taskNodeId").toString();
//        }
//        String taskId = (String)param.get("taskId");
//        Long userId = Long.decode(param.get("userId").toString());
//        taskOperationRecord.setId(UUID.randomUUID().toString());
//        taskOperationRecord.setTaskId(taskId);
//        taskOperationRecord.setTaskNodeId(taskNodeId);
//        taskOperationRecord.setUserId(userId);
//        taskOperationRecord.setStatus("1");
//        this.processTaskNodeMapper.updateProcessTaskStatus(param);
//        this.processTaskNodeMapper.updateProcessTaskNodeStatus(param);
//        taskOperationRecord.setMsg("提交总任务和节点任务状态，更新process_task表，以及process_task_node表");
//        taskOperationRecord.setType("3");
//        this.taskOperationRecordMapper.insertTaskOperationRecord(taskOperationRecord);
//    }
//
//    public void updateNodeTask(Map param) throws Exception{
//        TaskOperationRecord taskOperationRecord = new TaskOperationRecord();
//        String taskNodeId = "";
//        if(param.containsKey("taskNodeId")){
//            taskNodeId = param.get("taskNodeId").toString();
//        }
//        String taskId = (String)param.get("taskId");
//        Long userId = Long.decode(param.get("userId").toString());
//        taskOperationRecord.setId(UUID.randomUUID().toString());
//        taskOperationRecord.setTaskId(taskId);
//        taskOperationRecord.setTaskNodeId(taskNodeId);
//        taskOperationRecord.setUserId(userId);
//        taskOperationRecord.setStatus("1");
//        this.processTaskNodeMapper.updateProcessTaskNodeStatus(param);
//        taskOperationRecord.setMsg("提交节点任务状态，更新process_task_node表");
//        taskOperationRecord.setType("2");
//        this.taskOperationRecordMapper.insertTaskOperationRecord(taskOperationRecord);
//        List<ProcessTaskNode> list = this.getTask(param);
//        Long processNode =  list.get(0).getProcessNode();
//        param.put("nextProcessNode",processNode);
//        this.processTaskMapper.updateTask(param);
//        if(list.get(0).getExcType()==0){//自动节点
//            param.put("className",list.get(0).getClassName());
//            param.put("processNode",processNode);
//            this.autoFunction(param);
//            this.getTask(param);
//        }
//    }
//
//}
//

package com.zw.rule.util.flow.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zw.base.util.DateUtils;
import com.zw.rule.jeval.EvaluationException;
import com.zw.rule.mapper.process.ProcessNodeMapper;
import com.zw.rule.mapper.task.ProcessTaskMapper;
import com.zw.rule.mapper.task.ProcessTaskNodeMapper;
import com.zw.rule.mapper.task.TaskOperationRecordMapper;
import com.zw.rule.process.po.Process;
import com.zw.rule.process.po.ProcessNode;
import com.zw.rule.process.service.AutoFunctionService;
import com.zw.rule.task.po.ProcessTask;
import com.zw.rule.task.po.ProcessTaskNode;
import com.zw.rule.task.po.TaskOperationRecord;
import com.zw.rule.util.flow.service.FlowComService;
import com.zw.rule.util.flow.service.SpringContextUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.util.*;

/**
 * <strong>Title : <br>
 * </strong> <strong>Description : </strong>@类注释说明写在此处@<br>
 * <strong>Create on : 2017年07月05日<br>
 * </strong>
 * <p>
 * <strong>Copyright (C) zw.<br>
 * </strong>
 * <p>
 *
 * @author department:技术开发部 <br>
 *         username:牛登锋 <br>
 *         email: <br>
 * @version <strong>zw有限公司-运营平台</strong><br>
 *          <br>
 *          <strong>修改历史:</strong><br>
 *          修改人 修改日期 修改描述<br>
 *          -------------------------------------------<br>
 *          <br>
 *          <br>
 */
@Service
public class FlowComServiceImpl implements FlowComService {
    @Resource
    private ProcessTaskMapper processTaskMapper;

    @Resource
    private ProcessNodeMapper processNodeMapper;

    @Resource
    private ProcessTaskNodeMapper processTaskNodeMapper;

    @Resource
    private TaskOperationRecordMapper taskOperationRecordMapper;

    /**
     * 方法说明：根据关联id和关联type查找流程并往任务表和任务节点表(插入当前流程的开始节点连线的下一节点，作为初始任务)插入相应数据
     * 包含参数：relId   userId   orderId
     *
     * 功能码 relId userId
     * @return 返回msg和processId和taskId和当前初始节点processNode
     */
    public Map<String,Object> getProcessByProId(String code,String relId,Long userId,Map conditionMap) throws Exception{
        Map<String,Object> param = new HashMap<String,Object>();
        Map<String,Object> paramMap = new HashMap<String,Object>();
        try{
            String num =null;
            String num1 =null;
            Long processId = null;//流程id
            ProcessTask processTask = new ProcessTask();
            ProcessTaskNode processTaskNode = new ProcessTaskNode();
            TaskOperationRecord taskOperationRecord = new TaskOperationRecord();
            String msg = "";
            String taskId = UUID.randomUUID().toString();//生成process_task表的主键ID
            String taskNodeId = UUID.randomUUID().toString();//生成process_task_node表的主键ID
            Map map = new HashMap();
            map.put("code",code);
            Process process = this.processTaskMapper.getProcessByProId(map);//根据功能码获取对应流程
            if(process!=null){
                processId = process.getId();
            }
            if(processId!=null&&processId>0L){
                List plist1=processTaskMapper.getReturnTask(relId);
                List isExist = processTaskMapper.isExistTask(relId);
                if (isExist.size()>0){
                    msg = "该任务已存在";
                    param.put("msg",msg);
                    return param;
                }
                if(plist1.size()>0){
                    num=((Map)plist1.get(0)).get("id").toString();//任务id
                    List plist2 = processTaskMapper.getReturnTaskNodeId(num);
                    num1 = ((Map)plist2.get(0)).get("id").toString();//任务节点id
                    processTaskMapper.changeTask(relId);
                    processTaskMapper.changeTaskNode(num1);
                    taskOperationRecord.setTaskId(num);
                    taskOperationRecord.setMsg("更改退回到业务员的任务状态");
                }else{
                    processTask.setProcessId(processId);
                    processTask.setId(taskId);
                    processTask.setRelId(relId);
                    processTask.setTaskState(1);
                    processTask.setCreateTime(DateUtils.getCurrentTime(DateUtils.STYLE_10));
                    this.processTaskMapper.insertTask(processTask);//插入新任务到process_task表
                    taskOperationRecord.setTaskId(taskId);
                    taskOperationRecord.setMsg("插入任务到process_task表");
                }
                //记录当前任务信息插入到任务操作记录表
                taskOperationRecord.setId(UUID.randomUUID().toString());
                taskOperationRecord.setRelId(relId);
                taskOperationRecord.setStatus("1");
                taskOperationRecord.setType("1");
                taskOperationRecord.setUserId(userId);
                taskOperationRecord.setCreateTime(DateUtils.getCurrentTime());
                this.taskOperationRecordMapper.insertTaskOperationRecord(taskOperationRecord);
                //插入开始节点到轨迹表
                taskOperationRecord.setNodeState("4");
                taskOperationRecord.setExcType(2);
                taskOperationRecord.setMsg("开始节点");
                taskOperationRecord.setId(UUID.randomUUID().toString());
                taskOperationRecord.setCreateTime(DateUtils.getCurrentTime());
                this.taskOperationRecordMapper.insertTaskOperationRecord(taskOperationRecord);
                String processNodeCode = this.processTaskMapper.getNextNode(processId);//获取开始节点连线的节点node_code
                if(processNodeCode!=null&&processNodeCode.length()>0){
                    paramMap.put("processId",processId);
                    paramMap.put("nextNodeCode",processNodeCode);
                    //根据流程id和nextNodeCode去流程节点表查找下个节点的信息
                    ProcessNode processNode = this.processNodeMapper.getProcessNode(paramMap);
                    map.put("nextProcessNode",processNode.getNodeId());
                    String task_id = null;
                    String task_node_id = null;
                    if(plist1.size()>0){
                        task_id = num;
                        task_node_id = num1;
                    }else{
                        task_id = taskId;
                        task_node_id = taskNodeId;
                    }
                    map.put("taskId",task_id);
                    map.put("commitTime", DateUtils.getCurrentTime(DateUtils.STYLE_10));
                    this.processTaskMapper.updateTask(map);
                    processTaskNode.setProcessNode(processNode.getNodeId());
                    processTaskNode.setId(task_node_id);
                    processTaskNode.setTaskId(task_id);
                    processTaskNode.setNodeState(0);//初始状态为0：待处理
                    processTaskNode.setExcType(processNode.getExcType());//节点类型1：人工节点 0：自动节点
                    processTaskNode.setNodeJson(processNode.getNodeJson());
                    processTaskNode.setNodeScript(processNode.getNodeScript());
                    processTaskNode.setClassName(processNode.getClassName());
                    processTaskNode.setPageUrl(processNode.getPageUrl());
                    processTaskNode.setRelId(relId);
                    processTaskNode.setHandUrl(processNode.getHandUrl());
                    processTaskNode.setCreateTime(new Date());
                    if(plist1.size()<=0){
                        if (processTaskNode.getExcType()==3){//起始分支节点
                            processTaskNode.setNodeState(1);
                            this.processTaskMapper.insertTaskNode(processTaskNode);
                            processTaskNode = CommitTask(relId,userId,4,processTaskNode.getId(),"",conditionMap);
                        }else {
                            this.processTaskMapper.insertTaskNode(processTaskNode);//插入开始节点下的节点作为初始节点
                            taskOperationRecord.setNodeState("0");
                            taskOperationRecord.setMsg("插入任务节点到process_task_node表");
                            taskOperationRecord.setTaskNodeId(taskNodeId);
                            taskOperationRecord.setNodeId(processNode.getNodeId());
                            taskOperationRecord.setId(UUID.randomUUID().toString());
                            taskOperationRecord.setType("2");
                            taskOperationRecord.setCreateTime(DateUtils.getCurrentTime());
                            //记录当前任务节点信息插入到任务操作记录表
                            this.taskOperationRecordMapper.insertTaskOperationRecord(taskOperationRecord);
                        }
                    }else {
                        if (processTaskNode.getExcType()==3){//起始分支节点
                            processTaskNode = CommitTask(relId,userId,4,processTaskNode.getId(),"",conditionMap);
                        }
                    }
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
        catch (Exception e){
            e.printStackTrace();
            return param;
        }
    }

    /**
     * 获取任务getTask
     * 方法说明：根据传过来的已处理通过的节点去zw_process_node表查询
     *  当前节点的下一节点并插入到process_task_node表去，
     * @param param 包含processId和processNode和taskId和userId和orderId
     * @return 返回刚插入的那条任务，如果为null说明传过来的节点是当前流程的最后一个节点
     */
    public ProcessTaskNode getTask(Map<String,Object> param, Map map) throws Exception{
        try{
            ProcessTaskNode processTaskNode = new ProcessTaskNode();
            TaskOperationRecord taskOperationRecord = new TaskOperationRecord();
            Long processNode = Long.decode(param.get("processNode").toString());
            String taskId = param.get("taskId").toString();
            Long userId = Long.decode(param.get("userId").toString());
            String orderId = null;
            if(param.containsKey("relId")){
                orderId = param.get("relId").toString();
            }
            ProcessNode node = this.processNodeMapper.selectByPrimaryKey(processNode);
            String nextNodeCode = null;
//            if(node.getExcType()==3){//决策节点
//                String nodeJson = node.getNodeJson().toString();
//                JSONObject json = JSONObject.parseObject(nodeJson);
//                JSONArray jsonArray= (JSONArray)json.get("conditions");
//                for(int i = 0;i<jsonArray.size();i++){
//                    JSONObject jsonCondition= (JSONObject)jsonArray.get(i);
//                    if(jsonCondition.get("group_name").equals(param.get("sta").toString())){
//                        nextNodeCode =  jsonCondition.get("nextNode").toString();
//                    }
//                }
//            }
            if (map!=null&&map.size()>0){
                JSONObject json = JSONObject.parseObject(node.getNodeScript().toString());
                nextNodeCode = handleClassify(json,map);
            }else{
//                nextNodeCode = node.getNextNodes();
                nextNodeCode = this.processTaskNodeMapper.getNextNodeByProcessNode(processNode);//根据当前节点去查找下个节点
            }
            if(nextNodeCode!=null&&nextNodeCode.length()>0){
                Map<String,Object> paramMap = new HashMap();
                paramMap.put("processId",param.get("processId").toString());
                paramMap.put("nextNodeCode",nextNodeCode);
                //根据流程id和nextNodeCode去流程节点表查找下个节点的信息
                ProcessNode processNode1 = this.processNodeMapper.getProcessNode(paramMap);
                if(processNode1!=null){
                    if(processNode1.getNodeId()!=null&&processNode1.getNodeId()>0L){
                        String taskNodeId = UUID.randomUUID().toString();//生成process_task_node表的主键ID
                        processTaskNode.setProcessNode(processNode1.getNodeId());
                        processTaskNode.setId(taskNodeId);
                        processTaskNode.setTaskId(taskId);
                        processTaskNode.setNodeState(0);//初始状态为0：待处理
                        processTaskNode.setExcType(processNode1.getExcType());
                        processTaskNode.setNodeJson(processNode1.getNodeJson());
                        processTaskNode.setNodeScript(processNode1.getNodeScript());
                        processTaskNode.setClassName(processNode1.getClassName());
                        processTaskNode.setPageUrl(processNode1.getPageUrl());
                        processTaskNode.setHandUrl(processNode1.getHandUrl());
                        processTaskNode.setRelId(orderId);
                        processTaskNode.setCreateTime(new Date());
                        //查询该任务节点是否已存在
                        Map imap=new HashMap();
                        imap.put("taskId",taskId);
                        imap.put("processNode",processNode1.getNodeId());
                        List ilist=processTaskMapper.isExist(imap);
                        String num = null;
                        if(ilist.size()<=0){
                            this.processTaskMapper.insertTaskNode(processTaskNode);
                            taskOperationRecord.setMsg("插入任务节点到process_task_node表");
                        }else {
                            num = ((Map)ilist.get(0)).get("id").toString();//taskNodeId
                            processTaskNode.setId(num);
                            imap.put("taskNodeId",num);
                            imap.put("status",0);
                            processTaskNodeMapper.initProcessTaskNode(imap);
                            taskOperationRecord.setMsg("初始化已存在的任务节点");
                        }
                        //记录当前任务信息插入到任务操作记录表
                        taskOperationRecord.setId(UUID.randomUUID().toString());
                        taskOperationRecord.setTaskId(taskId);
                        taskOperationRecord.setTaskNodeId(taskNodeId);
                        taskOperationRecord.setNodeId(processNode);
                        taskOperationRecord.setExcType(processNode1.getExcType());
                        taskOperationRecord.setStatus("1");
                        taskOperationRecord.setType("2");
                        taskOperationRecord.setUserId(userId);
                        taskOperationRecord.setNodeState("0");//初始状态都为0：待处理
                        taskOperationRecord.setCreateTime(DateUtils.getCurrentTime());
                        this.taskOperationRecordMapper.insertTaskOperationRecord(taskOperationRecord);
                    }
                }
                return processTaskNode;
            }else{
                return null;
            }
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ProcessTaskNode CommitTask(String relId, Long userId, Integer status, String taskNodeId, String approveSuggestion) throws Exception {
        return null;
    }

    /**
     * 提交任务
     *              status 状态 必需
     *              taskNodeId 当前节点任务ID 必需
     *              userId 用户ID 必需
     *              relId   关联id
     *
     * @return
     */
    public ProcessTaskNode CommitTask(String relId, Long userId, Integer status, String taskNodeId, String approveSuggestion, Map paramMap) throws Exception {
        Map param = new HashMap();
//        String sta = "";
//        if(status==3){
//            sta="拒绝";
//        }
//        if (status==4){
//            sta="通过";
//        }
        List list = this.processTaskNodeMapper.isCommit(taskNodeId);//防止重复提交
        if (list.size()>0){
            return null;
        }
        ProcessTask processTask = this.processTaskMapper.searchByRelId(relId);
        ProcessTaskNode processTaskNode = this.processTaskNodeMapper.selectByPrimaryKey(taskNodeId);
        if(processTask!=null&&taskNodeId!=null){//如果当前订单id已经创建任务
            String taskId = processTask.getId();
            Long processNode = processTaskNode.getProcessNode();
            param.put("processId",processTask.getProcessId());
            param.put("taskId",taskId);
            param.put("processNode",processNode);
            param.put("userId",userId);
            param.put("taskNodeId",taskNodeId);
            param.put("relId",relId);
            param.put("status",status);
            param.put("approveSuggestion",approveSuggestion);
            if(processTaskNode!=null){
//                String excType = processTaskNode.getExcType().toString();
                String nextNodeCode = null;
//                if("3".equals(excType)){
//                    String nodeJson = processTaskNode.getNodeJson().toString();
//                    JSONObject json = JSONObject.parseObject(nodeJson);
//                    JSONArray jsonArray= (JSONArray)json.get("conditions");
//                    for(int i = 0;i<jsonArray.size();i++){
//                        JSONObject jsonCondition= (JSONObject)jsonArray.get(i);
//                        if(jsonCondition.get("group_name").equals(sta)){
//                            nextNodeCode =  jsonCondition.get("nextNode").toString();
//                        }
//                    }
//                }else {
                nextNodeCode = this.processTaskNodeMapper.getNextNodeByProcessNode(processNode);
//                }
                if(nextNodeCode.isEmpty()){//说明是最后一个节点
                    this.updateAllTask(param);
                    processTaskNode.setId(taskNodeId);
                    processTaskNode.setProcessNode(processNode);
                    processTaskNode.setTaskId(taskId);
                    return processTaskNode;
                }else{//说明不是最后一个节点
                    if(status==3){//如果status为3-拒绝 更改当前节点状态为3且更改总状态为3
//                        if(excType.equals("3")){//是决策节点
//                            param.put("sta","拒绝");
//                            return this.updateNodeTask(param,paramMap);
//                        }else{
                        this.updateAllTask(param);
                        processTaskNode.setId(taskNodeId);
                        processTaskNode.setProcessNode(processNode);
                        processTaskNode.setTaskId(taskId);
                        return processTaskNode;
//                        }
                    }else {
//                        if(excType.equals("3")){
//                            param.put("sta","通过");
//
//                        }
                        return this.updateNodeTask(param,paramMap);
                    }
                }
            }
        }
        return null;
//        else{
//            this.getProcessByProId(relId,userId);//插入新任务
//        }
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
        taskOperationRecord.setCreateTime(DateUtils.getCurrentTime());
        int num = this.processTaskNodeMapper.changeTask(param);
        if(num>0){
            this.taskOperationRecordMapper.insertTaskOperationRecord(taskOperationRecord);
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
        taskOperationRecord.setCreateTime(DateUtils.getCurrentTime());
        int num = this.processTaskNodeMapper.exitTask(taskNodeId);
        if(num>0){
            this.taskOperationRecordMapper.insertTaskOperationRecord(taskOperationRecord);
            return num;
        }else {
            return 0;
        }
    }

    /**
     *回退上级
     * @param taskNodeId
     * approveSuggestion
     */
    @Override
    public ProcessTaskNode exitPreTask(String taskNodeId, String approveSuggestion) {
        try {
            ProcessTaskNode processTaskNode = processTaskNodeMapper.selectByPrimaryKey(taskNodeId);
            TaskOperationRecord taskOperationRecord = new TaskOperationRecord();
            taskOperationRecord.setMsg("任务回退到上一节点");
            taskOperationRecord.setRelId(processTaskNode.getRelId());
            taskOperationRecord.setNodeId(processTaskNode.getProcessNode());
            taskOperationRecord.setTaskNodeId(taskNodeId);
            taskOperationRecord.setId(UUID.randomUUID().toString());
            taskOperationRecord.setExcType(processTaskNode.getExcType());
            taskOperationRecord.setStatus("1");
            taskOperationRecord.setNodeState("5");
            taskOperationRecord.setTaskId(processTaskNode.getTaskId());
            taskOperationRecord.setUserId(processTaskNode.getOperateUser());
            taskOperationRecord.setApproveSuggestion(approveSuggestion);
            taskOperationRecord.setCreateTime(DateUtils.getCurrentTime());
            //上一个节点任务状态改为5
            Map map = new HashMap();
            List<Map> list = processTaskNodeMapper.findIdList(taskNodeId);
            String preTaskNodeId = list.get(0).get("id").toString();
            String nextProcessNode = list.get(0).get("process_node").toString();
            map.put("taskNodeId",preTaskNodeId);
            map.put("status",5);
            processTaskNodeMapper.initProcessTaskNode(map);
            //更改总任务的当前节点为上一节点
            map.put("taskId",processTaskNode.getTaskId());
            map.put("nextProcessNode",nextProcessNode);
            map.put("commitTime", DateUtils.getCurrentTime(DateUtils.STYLE_10));
            processTaskMapper.updateTask(map);
//            //删除当前节点任务
//            processTaskNodeMapper.deleteByPrimaryKey(taskNodeId);
            //更改当前任务状态为6
            Map map1 = new HashMap();
            map1.put("taskNodeId",taskNodeId);
            map1.put("status",6);
            map1.put("approveSuggestion",approveSuggestion);
            map1.put("commitTime", DateUtils.getCurrentTime());
            processTaskNodeMapper.updateProcessTaskNodeStatus(map1);
            //更新记录表
            taskOperationRecordMapper.insertTaskOperationRecord(taskOperationRecord);
            processTaskNode.setId(preTaskNodeId);
            processTaskNode.setProcessNode(Long.decode(nextProcessNode));
            return processTaskNode;
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * 回退到开始
     * @param taskNodeId
     */
    @Override
    public void exitStartTask(String taskNodeId,String approveSuggestion) {
        try{
            //更改当前节点状态为6
            Map map = new HashMap();
            map.put("taskNodeId",taskNodeId);
            map.put("status",6);
            map.put("approveSuggestion",approveSuggestion);
            map.put("commitTime", DateUtils.getCurrentTime());
            processTaskNodeMapper.updateProcessTaskNodeStatus(map);
            processTaskNodeMapper.updateApproveSuggestion(map);
            //更改总任务状态为6
            Map mapp = new HashMap();
            mapp.put("status",6);
            //查找到当前的任务id
            ProcessTaskNode processTaskNode = processTaskNodeMapper.selectByPrimaryKey(taskNodeId);
            String taskId = processTaskNode.getTaskId();
            mapp.put("taskId",taskId);
            mapp.put("commitTime", DateUtils.getCurrentTime(DateUtils.STYLE_10));
            processTaskNodeMapper.updateProcessTaskStatus(mapp);
            //更新记录表
            TaskOperationRecord taskOperationRecord = new TaskOperationRecord();
            taskOperationRecord.setApproveSuggestion(approveSuggestion);
            taskOperationRecord.setMsg("任务退回到开始");
            taskOperationRecord.setRelId(processTaskNode.getRelId());
            taskOperationRecord.setNodeId(processTaskNode.getProcessNode());
            taskOperationRecord.setTaskNodeId(taskNodeId);
            taskOperationRecord.setId(UUID.randomUUID().toString());
            taskOperationRecord.setExcType(processTaskNode.getExcType());
            taskOperationRecord.setStatus("1");
            taskOperationRecord.setNodeState("6");
            taskOperationRecord.setTaskId(processTaskNode.getTaskId());
            taskOperationRecord.setUserId(processTaskNode.getOperateUser());
            taskOperationRecord.setCreateTime(DateUtils.getCurrentTime());
            taskOperationRecordMapper.insertTaskOperationRecord(taskOperationRecord);
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * 自动化节点
     * @param param 包含以下参数
     * className String beanId 必需
     * taskNodeId  String 任务节点表主键id 必需
     * relId      String 数据id 必需
     *
     * @throws ClassNotFoundException
     */
    public void autoFunction(Map param) throws ClassNotFoundException {
        try {
            String className = param.get("className").toString();
            String taskNodeId = param.get("taskNodeId").toString();
            String relId = param.get("relId").toString();
            AutoFunctionService c = SpringContextUtil.getBean(className);
            c.doWork(taskNodeId,relId);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public ProcessNode getNextNode(Long nodeId) {
        return processNodeMapper.selectByPrimaryKey(nodeId);
    }

    public String getTaskNodeId(String relId){
        return  processTaskNodeMapper.getTaskNodeId(relId);
    }


    public void updateAllTask(Map param){
        TaskOperationRecord taskOperationRecord = new TaskOperationRecord();
        String taskNodeId = "";
        if(param.containsKey("taskNodeId")){
            taskNodeId = param.get("taskNodeId").toString();
        }
        String taskId = (String)param.get("taskId");
        Long userId = Long.decode(param.get("userId").toString());
        taskOperationRecord.setId(UUID.randomUUID().toString());
        taskOperationRecord.setTaskId(taskId);
        taskOperationRecord.setTaskNodeId(taskNodeId);
        taskOperationRecord.setUserId(userId);
        taskOperationRecord.setStatus("1");
        taskOperationRecord.setApproveSuggestion(param.get("approveSuggestion").toString());
        taskOperationRecord.setRelId(param.get("relId").toString());
        taskOperationRecord.setNodeId(Long.decode(param.get("processNode").toString()));
        taskOperationRecord.setNodeState(param.get("status").toString());
        taskOperationRecord.setCreateTime(DateUtils.getCurrentTime());
        param.put("commitTime", DateUtils.getCurrentTime(DateUtils.STYLE_10));
        this.processTaskNodeMapper.updateProcessTaskStatus(param);
        param.put("commitTime", DateUtils.getCurrentTime());
        this.processTaskNodeMapper.updateProcessTaskNodeStatus(param);
        taskOperationRecord.setMsg("提交总任务和节点任务状态，更新process_task表，以及process_task_node表");
        taskOperationRecord.setType("3");
        this.taskOperationRecordMapper.insertTaskOperationRecord(taskOperationRecord);
        //插入结束节点到轨迹表
        taskOperationRecord.setNodeState("4");
        taskOperationRecord.setExcType(4);
        taskOperationRecord.setMsg("结束节点");
        taskOperationRecord.setTaskNodeId("");
        taskOperationRecord.setApproveSuggestion("");
        taskOperationRecord.setNodeId(null);
        taskOperationRecord.setId(UUID.randomUUID().toString());
        taskOperationRecord.setCreateTime(DateUtils.getCurrentTime());
        this.taskOperationRecordMapper.insertTaskOperationRecord(taskOperationRecord);
    }

    public ProcessTaskNode updateNodeTask(Map param, Map paramMap) throws Exception{
        TaskOperationRecord taskOperationRecord = new TaskOperationRecord();
        String taskNodeId = "";
        if(param.containsKey("taskNodeId")){
            taskNodeId = param.get("taskNodeId").toString();
        }
        String taskId = (String)param.get("taskId");
        Long userId = Long.decode(param.get("userId").toString());
        taskOperationRecord.setId(UUID.randomUUID().toString());
        taskOperationRecord.setTaskId(taskId);
        taskOperationRecord.setTaskNodeId(taskNodeId);
        taskOperationRecord.setUserId(userId);
        taskOperationRecord.setStatus("1");
        taskOperationRecord.setApproveSuggestion((String) param.get("approveSuggestion"));
        param.put("commitTime", DateUtils.getCurrentTime());
        this.processTaskNodeMapper.updateProcessTaskNodeStatus(param);
        taskOperationRecord.setMsg("提交节点任务状态，更新process_task_node表");
        taskOperationRecord.setType("2");
        taskOperationRecord.setNodeId(Long.decode(param.get("processNode").toString()));
        taskOperationRecord.setRelId((String) param.get("relId"));
        taskOperationRecord.setNodeState(param.get("status").toString());
        taskOperationRecord.setCreateTime(DateUtils.getCurrentTime());
        this.taskOperationRecordMapper.insertTaskOperationRecord(taskOperationRecord);
        ProcessTaskNode processTaskNode = this.getTask(param,paramMap);
        Long processNode =  processTaskNode.getProcessNode();
        param.put("nextProcessNode",processNode);
        param.put("commitTime", DateUtils.getCurrentTime(DateUtils.STYLE_10));
        this.processTaskMapper.updateTask(param);
        if(processTaskNode.getExcType()==0){//自动节点
            param.put("className",processTaskNode.getClassName());
            param.put("processNode",processNode);
            this.autoFunction(param);
            Map map = new HashMap();
            ProcessTaskNode processTaskNode1 = this.getTask(param,map);
            if(processTaskNode1==null){
                param.put("taskNodeId",processTaskNode.getId());
                param.put("status","4");
                param.put("processNode",processTaskNode.getProcessNode());
                this.updateAllTask(param);
                return processTaskNode;
            }else{
                param.put("nextProcessNode",processTaskNode1.getProcessNode());
                param.put("commitTime", DateUtils.getCurrentTime(DateUtils.STYLE_10));
                this.processTaskMapper.updateTask(param);
                param.put("taskNodeId",processTaskNode.getId());
                param.put("status","4");
                param.put("processNode",processTaskNode.getProcessNode());
                Map paramMap1 = new HashMap();
                return this.updateNodeTask(param,paramMap1);
            }
        }
        return processTaskNode;
    }
    /*
            * 处理节点中条件，返回下个节点的node_code
            * */
    private static String handleClassify(JSONObject jsonScript, Map<String, Object> variablesMap) throws Exception, EvaluationException {
        JSONArray conditions = jsonScript.getJSONArray("conditions");
        String nextNode = "";
        if (conditions != null && !conditions.isEmpty()) {
            boolean flag = false;
            JSONObject formula = null;

            for (int i = 0; i < conditions.size(); ++i) {
                formula = conditions.getJSONObject(i);
                if ("".equals(formula.getString("formula"))) {
                    if (nextNode.equals("")) {
                        nextNode = formula.getString("nextNode");
                    }
                } else {
                    String condition = formula.getString("formula");
                    for (String key : variablesMap.keySet()) {
                        Object data = variablesMap.get(key);
                        condition = condition.replace("#{" + key + "}", "'" + data + "'");
                    }
                    flag = evaluateBoolean(condition);
                    if (flag) {
                        nextNode = formula.getString("nextNode");
                        break;
                    }
                }
            }
            return nextNode;
        } else

        {
            return nextNode;
        }

    }


    private static boolean evaluateBoolean(String condition) throws Exception {
        ScriptEngineManager factory = new ScriptEngineManager();
        ScriptEngine engine = factory.getEngineByName("JavaScript");
        String script = "function evaluateBoolean() { if(" + condition + "){ return true;}else{return false;}}";
        engine.eval(script);
        Invocable inv = (Invocable) engine;
        boolean flag = (boolean) inv.invokeFunction("evaluateBoolean");
        return flag;
    }


}


