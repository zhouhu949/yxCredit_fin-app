package com.zw.rule.process.service;

import com.alibaba.fastjson.JSONObject;
import com.zw.rule.engine.po.EngineNode;
import com.zw.rule.process.po.Node;
import com.zw.rule.process.po.Process;
import com.zw.rule.process.po.ProcessNode;
import com.zw.rule.product.ProCrmProduct;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/15.
 */
public interface ProcessNodeService {
    /**
     * 添加节点
     * @param node
     * @return
     */
    int saveNode(ProcessNode node);

    /**
     * 选择节点
     */
    List<ProcessNode> getNodeListByNodeIds(List nodeIds);

    /**
     * @ProcessNode  包含以下参数
     *              nodeName String 节点名字 必需
     *              nodeId   Integer  节点Id  必需
     * @return
     */
    boolean saveProcessNode(ProcessNode processNode);

    Integer getMaxNodeType(Long processId);

    Integer getMaxNodeOrder(Long processId);

    int updateProcessNode(ProcessNode processNode);

    int renameNode(Map<String, Object> map);

    int updateNode(ProcessNode processNode);

    int delNode(Long nodeId);

    int addNode(ProcessNode processNode);

    void removeNode(Long currentNodeId, Long preNodeId);

    Map<String, Object> deleteNodes(List<Long> list, String array);

    void removeLink(Long currentNodeId, Long preNodeId);

    ProcessNode findById(Long id);

    List<ProcessNode> getNextNode(Map map);

    ProcessNode findByProcessNode(ProcessNode processNode);

    boolean updateNodeForNextOrderAndParams(List<ProcessNode> list);

    boolean updateNodeForMove(ProcessNode processNode);

    String getJsonData(Long processId);

    List<ProcessNode> findProcessNodeListById(Long id);
    List<ProcessNode> findProcessNodeListByIds(Long id);
    /**
     * 添加节点
     * @param node
     * @return
     */
    String getNode(ProcessNode node);

    List<ProCrmProduct> getProcessByList(String searchKey);

    int insertNode(ProcessNode processNode);
}
