package com.zw.rule.mapper.process;

import com.zw.rule.mapper.common.BaseMapper;
import com.zw.rule.process.po.ProcessNode;
import com.zw.rule.product.ProCrmProduct;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/13 0013.
 */
public interface ProcessNodeMapper extends BaseMapper<ProcessNode> {
    List<ProcessNode> getProcessNodeListByProcessVersionId(@Param("verId") Long var1);
    int renameNode(Map<String, Object> var1);
    int delNode(Long nodeId);
    int updateNode(ProcessNode processNode);
    int addNode(ProcessNode processNode);
    int deleteNodes(List<Long> var1);
    int updateNodeForNextOrderAndParams(List<ProcessNode> var1);
    String getJsonData(Long processId);
    List<ProcessNode> selectListByPrimaryKey(Long id);
    ProcessNode selectByProcessNode(ProcessNode processNode);
    List<ProcessNode> selectListByPrimaryKeys(Long id);
    int insertNodeAndReturnId(ProcessNode var1);
    List<ProcessNode> getNodeListByNodeIds(List nodeIds);
    int insertNode(ProcessNode var1);
    String getNodeId(ProcessNode var1);
    Integer getMaxNodeType(Long processId);
    Integer getMaxNodeOrder(Long processId);
    List<ProCrmProduct> getProcessByList(String searchKey);
    ProcessNode getProcessNode(Map param);
    List<ProcessNode> getNextNode(Map map);
    ProcessNode selectByPrimaryKey(Long nodeId);
    String getTaskNodeId(String orderId);
}
