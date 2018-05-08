package com.zw.rule.process.service;

import com.zw.rule.process.po.Node;
import com.zw.rule.process.po.Process;
import com.zw.rule.process.po.SysCode;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/30.
 */
public interface ProcessConfigService {
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

    List<SysCode> getDictList(Map map);//获取数据字典流程配置列表

    Boolean updateProcessRel(Map map);//关联流程产品或者商品

    Boolean updateStateForSysCodeTable(String code);
}
