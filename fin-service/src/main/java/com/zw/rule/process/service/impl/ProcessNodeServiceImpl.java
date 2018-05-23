package com.zw.rule.process.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zw.rule.jeval.tools.StringUtil;
import com.zw.rule.mapper.process.ProcessNodeMapper;
import com.zw.rule.mapper.product.ProCrmProductMapper;
import com.zw.rule.process.ProcessNodeUtil;
import com.zw.rule.process.po.ProcessNode;
import com.zw.rule.process.service.ProcessNodeService;
import com.zw.rule.product.ProCrmProduct;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * <strong>Title : <br>
 * </strong> <strong>Description : </strong>@类注释说明写在此处@<br>
 * <strong>Create on : 2017年06月15日<br>
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
public class ProcessNodeServiceImpl implements ProcessNodeService{

    private static final Logger logger = Logger.getLogger(ProcessServiceImpl.class);

    @Resource
    private ProcessNodeMapper processNodeMapper;
    @Resource
    private ProCrmProductMapper proCrmProductMapper;

    @Override
    public int saveNode(ProcessNode node) {
        return this.processNodeMapper.insertNode(node);
    }

    @Override
    public List<ProcessNode> getNodeListByNodeIds(List nodeIds){
        return this.processNodeMapper.getNodeListByNodeIds(nodeIds);
    }

    public boolean saveProcessNode(ProcessNode eNode) {
        boolean flag = true;
        int count = this.processNodeMapper.insertNodeAndReturnId(eNode);
        if(count<=0){
            flag = false;
        }
        return flag;
    }

    public Integer getMaxNodeType(Long processId){
        return this.processNodeMapper.getMaxNodeType(processId);
    }

    public Integer getMaxNodeOrder(Long processId){
        return this.processNodeMapper.getMaxNodeOrder(processId);
    }

    public int updateProcessNode(ProcessNode eNode){
        return this.processNodeMapper.updateNode(eNode);
    }


    public int renameNode(Map<String, Object> param) {
        return this.processNodeMapper.renameNode(param);
    }

    public int updateNode(ProcessNode processNode){
        return this.processNodeMapper.updateNode(processNode);
    }

    public int delNode(Long nodeId){
        return this.processNodeMapper.delNode(nodeId);
    }

    @Override
    public int addNode(ProcessNode processNode) {
        return this.processNodeMapper.addNode(processNode);
    }

    public void removeNode(Long currentNodeId, Long preNodeId) {
        ProcessNode processNode = (ProcessNode)this.processNodeMapper.selectByPrimaryKey(currentNodeId);
        if(processNode != null) {
            if(preNodeId.longValue() == -1L) {
                int num = this.processNodeMapper.deleteByExample(processNode);
            } else {
                ProcessNode preProcessNode = (ProcessNode)this.processNodeMapper.selectByPrimaryKey(preNodeId);
                if(preProcessNode != null) {
//                    int nodeType = preProcessNode.getNodeType().intValue();
//                    switch(nodeType) {
//                        case 1:
//                            this.handlePreCommonNode(processNode, preProcessNode);
//                            break;
//                        case 2:
//                            this.handlePreCommonNode(processNode, preProcessNode);
//                            break;
//                        case 3:
                    this.handlePreClassifyOrSandBoxNode(processNode, preProcessNode);
//                            break;
//                        case 4:
//                            this.handlePreCommonNode(processNode, preProcessNode);
//                            break;
//                        case 5:
//                            this.handlePreBlackOrWhiteNode(processNode, preProcessNode);
//                            break;
//                        case 6:
//                            this.handlePreBlackOrWhiteNode(processNode, preProcessNode);
//                            break;
//                        case 7:
//                            this.handlePreClassifyOrSandBoxNode(processNode, preProcessNode);
//                            break;
//                        case 8:
//                            this.handlePreCommonNode(processNode, preProcessNode);
//                    }
//                    preProcessNode.setNextNodes("");
//                    this.processNodeMapper.updateByPrimaryKeySelective(preProcessNode);
//                    this.processNodeMapper.deleteByExample(processNode);
                }
            }
        }
    }

    private void handlePreCommonNode(ProcessNode processNode, ProcessNode preProcessNode) {
        preProcessNode.setNextNodes("");
        this.processNodeMapper.updateByPrimaryKeySelective(preProcessNode);
        this.processNodeMapper.deleteByExample(processNode);
    }

    private void handlePreClassifyOrSandBoxNode(ProcessNode processNode, ProcessNode preProcessNode) {
        String nextNodes = preProcessNode.getNextNodes();
        if(StringUtil.isValidStr(nextNodes)) {
            nextNodes = nextNodes.replace(processNode.getNodeCode(), "");
            if(nextNodes.startsWith(",")) {
                nextNodes = nextNodes.substring(1);
            }

            if(nextNodes.endsWith(",")) {
                nextNodes = nextNodes.substring(0, nextNodes.length() - 1);
            }

            preProcessNode.setNextNodes(nextNodes.replace(",,", ","));
        }

        String nodeJson = preProcessNode.getNodeJson();
        if(StringUtil.isValidStr(nodeJson)) {
            nodeJson = nodeJson.replace(processNode.getNodeCode(), "");
            preProcessNode.setNodeJson(nodeJson);
        }

        preProcessNode = ProcessNodeUtil.boxProcessNodeJson(preProcessNode);
        this.processNodeMapper.updateByPrimaryKeySelective(preProcessNode);
        this.processNodeMapper.deleteByExample(processNode);
    }
//    private void handlePreBlackOrWhiteNode(ProcessNode processNode, ProcessNode preProcessNode) {
//        preProcessNode.setNextNodes("");
//        this.processNodeMapper.updateByPrimaryKeySelective(preProcessNode);
//        this.processNodeMapper.deleteByExample(processNode);
//    }



    public Map<String, Object> deleteNodes(List<Long> idList, String array) {
        int num = this.removeNodes(idList, array);
        int count = this.processNodeMapper.deleteNodes(idList);
        this.findById((Long)idList.get(0));
        HashMap map = new HashMap();
        map.put("num", Integer.valueOf(num));
        map.put("count", Integer.valueOf(count));
        return map;
    }

    private int removeNodes(List<Long> idList, String array) {
        int num = 0;
        JSONArray jsonArray = JSONArray.parseArray(array);
        Iterator iterator = jsonArray.iterator();

        while(iterator.hasNext()) {
            Object object = iterator.next();
            JSONObject obj = (JSONObject)object;
            Long subNodeId = obj.getLong("subNodeId");
            Long preNodeId = obj.getLong("preNodeId");
            if(!idList.contains(preNodeId)) {
                this.removeNode(subNodeId, preNodeId);
                num++;
            }
        }
        return num;
    }

    public ProcessNode findById(Long id) {
        return (ProcessNode)this.processNodeMapper.selectByPrimaryKey(id);
    }

    public List<ProcessNode> getNextNode(Map map){
        return this.processNodeMapper.getNextNode(map);
    }

    public ProcessNode findByProcessNode(ProcessNode processNode) {
        return (ProcessNode)this.processNodeMapper.selectByProcessNode(processNode);
    }

    public List<ProcessNode> findProcessNodeListById(Long id) {
        return this.processNodeMapper.selectListByPrimaryKey(id);
    }
    public List<ProcessNode> findProcessNodeListByIds(Long id) {
        return this.processNodeMapper.selectListByPrimaryKeys(id);
    }
    public String getNode(ProcessNode node) {
        return this.processNodeMapper.getNodeId(node);
    }
    public void removeLink(Long currentNodeId, Long preNodeId) {
        ProcessNode processNode = (ProcessNode)this.processNodeMapper.selectByPrimaryKey(currentNodeId);
        ProcessNode preProcessNode = (ProcessNode)this.processNodeMapper.selectByPrimaryKey(preNodeId);
        if(preProcessNode != null) {
//            int nodeType = preProcessNode.getNodeType().intValue();
//            switch(nodeType) {
//                case 3:
            this.handlePreClassifyOrSandBoxLink(processNode, preProcessNode);
//                    break;
//                case 4:
//                case 5:
//                case 6:
//                default:
//                    this.handleCommonLink(processNode, preProcessNode);
//                    break;
//                case 7:
//                    this.handlePreClassifyOrSandBoxLink(processNode, preProcessNode);
//            }
        }

    }

    private void handlePreClassifyOrSandBoxLink(ProcessNode processNode, ProcessNode preProcessNode) {
        String nextNodes = preProcessNode.getNextNodes();
        if(StringUtil.isValidStr(nextNodes)) {
            nextNodes = nextNodes.replace(processNode.getNodeCode(), "");
            if(nextNodes.startsWith(",")) {
                nextNodes = nextNodes.substring(1);
            }

            if(nextNodes.endsWith(",")) {
                nextNodes = nextNodes.substring(0, nextNodes.length() - 1);
            }

            preProcessNode.setNextNodes(nextNodes.replace(",,", ","));
        }

        String nodeJson = preProcessNode.getNodeJson();
        if(StringUtil.isValidStr(nodeJson)) {
            nodeJson = nodeJson.replace(processNode.getNodeCode(), "");
            preProcessNode.setNodeJson(nodeJson);
        }

        preProcessNode = ProcessNodeUtil.boxProcessNodeJson(preProcessNode);
        this.processNodeMapper.updateByPrimaryKeySelective(preProcessNode);
    }

//    private void handleCommonLink(ProcessNode processNode, ProcessNode preProcessNode) {
//        String nextNodes = preProcessNode.getNextNodes();
//        if(StringUtil.isValidStr(nextNodes)) {
//            nextNodes = nextNodes.replace(processNode.getNodeCode(), "");
//            if(nextNodes.startsWith(",")) {
//                nextNodes = nextNodes.substring(1);
//            }
//
//            if(nextNodes.endsWith(",")) {
//                nextNodes = nextNodes.substring(0, nextNodes.length() - 1);
//            }
//
//            preProcessNode.setNextNodes(nextNodes);
//        }
//
//        this.processNodeMapper.updateByPrimaryKeySelective(preProcessNode);
//    }

    public boolean updateNodeForNextOrderAndParams(List<ProcessNode> eList) {
        int count = this.processNodeMapper.updateNodeForNextOrderAndParams(eList);

        ProcessNode var10000;
        ProcessNode processNode;
        for(Iterator iterator = eList.iterator(); iterator.hasNext(); var10000 = (ProcessNode)this.processNodeMapper.selectByPrimaryKey(processNode.getNodeId())) {
            processNode = (ProcessNode)iterator.next();
        }
        return count > 0;
    }

    public boolean updateNodeForMove(ProcessNode processNode) {
        int count = this.processNodeMapper.updateByPrimaryKeySelective(processNode);
        ProcessNode var10000 = (ProcessNode)this.processNodeMapper.selectByPrimaryKey(processNode.getNodeId());
        return count > 0;
    }

    public String getJsonData(Long processId){
        return this.processNodeMapper.getJsonData(processId);
    }

    public List<ProCrmProduct> getProcessByList(String searchKey){
        return this.proCrmProductMapper.getProcessByList(searchKey);
    }

    public int insertNode(ProcessNode processNode){
        return this.processNodeMapper.insertNode(processNode);
    }
}

