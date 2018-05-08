package com.zw.rule.process.service.impl;


import com.zw.rule.mapper.process.NodeMapper;
import com.zw.rule.mapper.process.ProcessMapper;
import com.zw.rule.mapper.process.ProcessNodeMapper;
import com.zw.rule.process.po.Node;
import com.zw.rule.process.po.NodeTypeEnum;
import com.zw.rule.process.po.Process;
import com.zw.rule.process.po.ProcessNode;
import com.zw.rule.process.service.ProcessService;
import com.zw.rule.task.po.ProcessTask;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
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
@Service
public class ProcessServiceImpl implements ProcessService{
    private static final Logger logger = Logger.getLogger(ProcessServiceImpl.class);
    @Resource
    private ProcessMapper processMapper;
    @Resource
    private ProcessNodeMapper processNodeMapper;
    @Resource
    private NodeMapper nodeMapper;
    public ProcessServiceImpl() {
    }

    @Override
    public List<Process> getProcessByList(Process process) {
        return this.processMapper.getProcessByList(process);
    }

    @Override
    public Process getProcessById(Process process) {
        return this.processMapper.getProcessById(process);
    }

    @Override
    public int updateProcess(Process process) {
        return this.processMapper.updateProcess(process);
    }

    @Override
    public int insertProcess(Process process) {
        return this.processMapper.insertProcess(process);
    }

    @Override
    public int insertProcessAndReturnId(Process process) {
        return this.processMapper.insertProcessAndReturnId(process);
    }

    @Override
    public boolean saveProcess(Process process, String url) {
        boolean flag = true;
        int processCount = this.processMapper.insertProcessAndReturnId(process);
        if(processCount == 1) {
            Long processId = process.getId();
            if(processId != null && processId > 0L) {
//                ProcessVersion processVersion = new ProcessVersion();
//                processVersion.setBootState(0);
//                processVersion.setCreateTime((new Date()).toString());
//                processVersion.setEngineId(process.getId());
//                processVersion.setLatestTime((new Date()).toString());
//                processVersion.setLatestUser(process.getCreator());
//                processVersion.setLayout(0);
//                processVersion.setStatus(1);
//                processVersion.setUserId(process.getCreator());
//                processVersion.setVersion(0);
//                processVersion.setSubVer(0);
                ProcessNode node = new ProcessNode();
                node.setProcessId(processId);
                node.setNodeX(200.0D);
                node.setNodeY(200.0D);
                node.setNodeName("开始");
                node.setNodeOrder(1);
                node.setNodeType(NodeTypeEnum.START.getValue());
                node.setNodeCode("ND_START");
                node.setParams("{\"arr_linkId\":\"\",\"dataId\":\"-1\",\"url\":\""+url+"/resources/images/decision/start.png\",\"type\":\"1\"}");
                //int count =this.processVersionMapper.insertProcessVersionAndReturnId(processVersion);
                //if(count == 1) {
//                long list_str = processVersion.getVerId();
//                node.setVerId(list_str);
                this.processNodeMapper.insertSelective(node);
                //}

                ArrayList arrayList = new ArrayList();
                String id_str = "1_$engineID$,11_$engineID$,111_$engineID$,112_$engineID$,1121_$engineID$,12_$engineID$,121_$engineID$,122_$engineID$,1221_$engineID$,123_$engineID$,1231_$engineID$,13_$engineID$,131_$engineID$,132_$engineID$,1321_$engineID$,133_$engineID$,1331_$engineID$,14_$engineID$,141_$engineID$,15_$engineID$,151_$engineID$";
                id_str = id_str.replace("$engineID$", String.valueOf(processId));
                String[] id_arr = id_str.split(",");
                if(id_arr != null && id_arr.length > 0) {
                    for(int sysUser = 0; sysUser < id_arr.length; sysUser++) {
                        arrayList.add(id_arr[sysUser]);
                    }
                }
            } else {
                flag = false;
            }
        } else {
            flag = false;
        }

        return flag;
    }

    @Override
    public List<Node> getNodeByList(Node node) {
        return this.nodeMapper.getNodeByList(node);
    }
}

