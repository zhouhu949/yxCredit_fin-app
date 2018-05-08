package com.zw.rule.process.service;


import com.zw.rule.engine.po.Engine;
import com.zw.rule.engine.po.EngineNode;
import com.zw.rule.process.po.Node;
import com.zw.rule.process.po.Process;
import com.zw.rule.task.po.ProcessTask;

import java.util.List;
import java.util.Map;

/**
 * <strong>Title :<br>
 * </strong> <strong>Description : </strong>@类注释说明写在此处@<br>
 * <strong>Create on : 2017年06月12日<br>
 * </strong>
 * <p>
 * <strong>Copyright (C) Vbill Co.,Ltd.<br>
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
public interface ProcessService {
    /**
     * 获取流程列表
     * @param process
     * @return
     */
    List<Process> getProcessByList(Process process);
    /**
     * 通过主键id获取流程
     * @param process
     * @return
     */
    Process getProcessById(Process process);
    /**
     * 通过主键id更新流程
     * @param process
     * @return
     */
    int updateProcess(Process process);
    int insertProcess(Process process);
    int insertProcessAndReturnId(Process process);
    boolean saveProcess(Process process , String url);
    /**
     * 获取节点列表
     * @param node
     * @return
     */
    List<Node> getNodeByList(Node node);
}

