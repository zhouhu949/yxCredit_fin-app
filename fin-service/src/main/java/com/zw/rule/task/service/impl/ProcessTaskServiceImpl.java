package com.zw.rule.task.service.impl;

import com.zw.rule.mapper.process.ProcessNodeMapper;
import com.zw.rule.mapper.task.ProcessTaskMapper;
import com.zw.rule.mapper.task.ProcessTaskNodeMapper;
import com.zw.rule.mapper.task.TaskMsgMapper;
import com.zw.rule.process.po.Process;
import com.zw.rule.process.po.ProcessNode;
import com.zw.rule.task.po.ProcessTask;
import com.zw.rule.task.po.ProcessTaskNode;
import com.zw.rule.task.po.TaskOperationRecord;
import com.zw.rule.task.service.ProcessTaskService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * <strong>Title : <br>
 * </strong> <strong>Description : </strong>@类注释说明写在此处@<br>
 * <strong>Create on : 2017年06月24日<br>
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
@Service
public class ProcessTaskServiceImpl implements ProcessTaskService{

    @Resource
    private ProcessTaskMapper processTaskMapper;

    @Resource
    private ProcessNodeMapper processNodeMapper;

    @Resource
    private ProcessTaskNodeMapper processTaskNodeMapper;

    public Process getProcessByProId(Map map){
        return this.processTaskMapper.getProcessByProId(map);
    }

    public int insertTask(ProcessTask processTask) {
        return this.processTaskMapper.insertTask(processTask);
    }

    public String getNextNode(Long processId){
        return this.processTaskMapper.getNextNode(processId);
    }

    public int insertTaskNode(ProcessTaskNode processTaskNode){
        return this.processTaskMapper.insertTaskNode(processTaskNode);
    }

    public String getNextNodeByProcessNode(Long processNode){
        return this.processTaskNodeMapper.getNextNodeByProcessNode(processNode);
    }

    public ProcessNode getProcessNode(Map param){
        return this.processNodeMapper.getProcessNode(param);
    }

    public int updateProcessTaskNodeStatus(Map param){
        return this.processTaskNodeMapper.updateProcessTaskNodeStatus(param);
    }

    public int updateProcessTaskStatus(Map param){
        return this.processTaskNodeMapper.updateProcessTaskStatus(param);
    }

    public int changeTask(Map param){
        return this.processTaskNodeMapper.changeTask(param);
    }

    public int exitTask(String taskNodeId){
        return this.processTaskNodeMapper.exitTask(taskNodeId);
    }
}

