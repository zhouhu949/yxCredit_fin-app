package com.zw.rule.web.process.controller;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zw.rule.core.Response;
import com.zw.rule.mybatis.ParamFilter;
import com.zw.rule.process.ProcessNodeUtil;
import com.zw.rule.process.po.NodeTypeEnum;
import com.zw.rule.process.po.Process;
import com.zw.rule.process.po.ProcessNode;
import com.zw.rule.process.service.ProcessNodeService;
import com.zw.rule.jeval.tools.CollectionUtil;
import com.zw.rule.process.po.Node;
import com.zw.rule.product.ProCrmProduct;
import com.zw.rule.util.StringUtil;
import com.zw.rule.web.aop.annotaion.WebLogger;
import com.zw.rule.web.util.PageConvert;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by Administrator on 2017/5/12 0012.
 */
@Controller
@RequestMapping({"/decision_flow"})
public class DecisionFlowController {

    @Resource
    private ProcessNodeService processNodeService;

    @RequestMapping({"/saveNode"})
    @ResponseBody
    public Response saveNode(@RequestBody ProcessNode node){
        Response response=new Response();

        Integer curNodeType = this.processNodeService.getMaxNodeType(node.getProcessId());
        Integer curNodeOrder = this.processNodeService.getMaxNodeOrder(node.getProcessId());
        node.setNodeType(curNodeType);
        node.setNodeOrder(curNodeOrder);
        node.setNodeCode("ND_"+curNodeOrder);
        int result= processNodeService .saveNode(node);
        if(result==1) {
//            String nodeId=processNodeService.getNode(node);
//            response.setData(nodeId);
            response.setMsg("添加成功！");
        }else{
            response.setMsg("添加失败！");
        }
        return response;
    }

    @RequestMapping({"/updateNode"})
    @ResponseBody
    public Response updateNodes(@RequestBody ProcessNode node){
        Response response=new Response();
        int result= processNodeService.updateNode(node);
        if(result==1) {
            response.setMsg("修改成功！");
        }else{
            response.setMsg("修改失败！");
        }
        return response;
    }

    /**
     *删除节点
     * 删除条件：必须该节点没有连线
     * @param map 包含以下参数
     * nodeId 必需 节点Id
     * processId   必需 流程Id
     * 测试通过
     */
    @RequestMapping({"/delNode"})
    @ResponseBody
    public Response delNode(@RequestBody Map map) {
        Response response = new Response();
        Long nodeId  = Long.decode(map.get("nodeId").toString());
        ProcessNode processNode = this.processNodeService.findById(nodeId);
        String nextNodes = processNode.getNextNodes();
        map.put("nodeCode",processNode.getNodeCode());
        List<ProcessNode> list = this.processNodeService.getNextNode(map);
        if((nextNodes==null||"".equals(nextNodes))&&list.size()<=0){//判断节点是否有next_nodes
            int result = this.processNodeService.delNode(nodeId);
            if(result==1){
                response.setMsg("删除成功!");
            }else{
                response.setMsg("删除失败!");
            }
        }else{
            response.setMsg("请先在画布上删除该节点相关连线再删除");
        }
        return response;
    }

    @RequestMapping({"getNodeByProcessIds"})
    @ResponseBody
    public Response getNodeByProcessIds(@RequestBody Map param){
        Response response = new Response();
        Long process_id = Long.decode(param.get("id").toString());
        List<ProcessNode> data = processNodeService.findProcessNodeListByIds(process_id);
        response.setData(data);
        return response;
    }


    @RequestMapping({"/save"})
    @ResponseBody
    public Object saveProcessNode(@RequestBody Map<String, Object> param) {
        Response response = new Response();
        if(!param.containsKey("nextNodes")){
            param.put("nextNodes","");
        }
        Long processId = Long.decode((String) param.get("processId"));
//        Integer nodeType = Integer.decode(param.get("nodeType").toString());
        ProcessNode eNode = ProcessNodeUtil.boxProcessNode(param);
        Integer curNodeType = this.processNodeService.getMaxNodeType(processId);
        Integer curNodeOrder = this.processNodeService.getMaxNodeOrder(processId);
//        if(nodeType==100){
//            eNode.setNodeType(100);
//        }else{
        eNode.setNodeType(curNodeType);
//        }
        eNode.setNodeOrder(curNodeOrder);
        eNode.setNodeCode("ND_"+curNodeOrder);
        this.processNodeService.saveProcessNode(eNode);
        param.put("nodeId",eNode.getNodeId());
        param.put("nodeType",eNode.getNodeType());
        param.put("nodeCode",eNode.getNodeCode());
        param.put("nodeOrder",eNode.getNodeOrder());
        response.setData(param);
        response.setMsg("保存成功！");
        return response;
    }

    /**
     * 方法功能：获取当前最大的节点顺序
     * 方法实现：根据当前id的最大值加一
     * @param param
     * @return
     */
    @RequestMapping({"/getMaxNodeOrder"})
    @ResponseBody
    public Response getMaxNodeOrder(@RequestBody Map param){
        Response response = new Response();
        Long processId = Long.decode((String)param.get("processId"));
        Integer curNodeOrder = this.processNodeService.getMaxNodeOrder(processId);
        response.setData(curNodeOrder);
        return response;
    }


    /**
     * 功能：获取当前流程下的最大节点类型
     * @param param
     * @return
     */
    @RequestMapping({"/getMaxNodeType"})
    @ResponseBody
    public Response getMaxNodeType(@RequestBody Map param){
        Response response = new Response();
        Long processId = Long.decode((String)param.get("processId"));
        Integer curNodeType = this.processNodeService.getMaxNodeType(processId);
        response.setData(curNodeType);
        return response;
    }

    /**
     * 编辑节点名称
     * @param param 包含以下参数
     *              nodeName String 节点名字 必需 （测试数据：开始1）
     *              nodeId   Integer  节点Id  必需 （测试数据：107）
     * @return
     * 测试通过
     */
    @RequestMapping({"/renameNode"})
    @ResponseBody
    public Response renameNode(@RequestBody Map<String, Object> param) {
        int count = processNodeService.renameNode(param);
        Response response = new Response();
        if(count == 1) {
            response.setMsg("修改成功");
        } else {
            response.setCode(1);
            response.setMsg("修改失败");
        }
        return response;
    }
    /**
     *保存或修改节点
     * @EngineNode
     * @return
     * 测试通过
     */
    @RequestMapping({"/update"})
    @ResponseBody
    public Response update(@RequestBody ProcessNode processNode) {
        Response response = new Response();
        processNodeService.updateNode(processNode);
        response.setMsg("修改成功！");
        return response;
    }

    /**
     *
     * @param param 包含以下参数
     * currentNodeId String 节点id 必需
     * preNodeId Long 前一个节点id 必需 (如果是-1,表示没有连线)
     * 测试通过
     */
    @RequestMapping({"/removeNode"})
    @ResponseBody
    public Object delete(@RequestBody Map<String, Object> param) {
        Long currentNodeId = Long.valueOf(param.get("currentNodeId").toString());
        Long preNodeId = Long.valueOf(param.get("preNodeId").toString());
        processNodeService.removeNode(currentNodeId, preNodeId);
        return new Response("删除成功!");
    }

    /**
     *批量删除节点
     * @param paramMap
     * idList 节点id 可选
     * array 包含以下参数:
     * subNodeId int 可选
     * preNodeId int 可选
     * 测试通过
     * @return
     */
    @RequestMapping({"/deleteNodes"})
    @ResponseBody
    public Response deleteNodes(@RequestBody Map<String, Object> paramMap) {
        List<Long> idList = (List)paramMap.get("idList");
        String array = paramMap.get("array").toString();
        Map map = new HashMap();
        Response response = new Response();
        if(CollectionUtil.isNotNullOrEmpty(idList)) {
            Map param = processNodeService.deleteNodes(idList, array);
            int count = Integer.parseInt("" + param.get("count"));
            int num = Integer.parseInt("" + param.get("num"));
            if(count != idList.size() && num <= 0) {
                response.setCode(1);
                response.setMsg("删除选择区域节点失败！");
                return response;
            } else {
                response.setMsg("删除选择区域节点成功！！");
                return response;
            }
        } else {
            response.setCode(1);
            response.setMsg("区域中无有效节点!！！");
            return response;
        }
    }

    /**
     * 删除节点之间的连线
     * @param param 包含以下参数
     *              currentNodeId String 必需
     *              preNodeId   String  必需
     * @return
     *测试成功
     */
    @RequestMapping({"/removeLink"})
    @ResponseBody
    public Response removeLink(@RequestBody Map<String, Object> param) {
        Long currentNodeId = Long.valueOf(param.get("currentNodeId").toString());
        Long preNodeId = Long.valueOf(param.get("preNodeId").toString());
        processNodeService.removeLink(currentNodeId, preNodeId);
        return new Response("删除成功!");
    }


    /**
     *
     * @param param 包含以下参数
     *                    nodeId_1  节点1 String 必需
     *                    nextNodes_1   节点代码 String 必需
     *                    nodeId_2  节点2    String 必需
     *                    nextNodes_2   节点代码2 String 必需
     *
     * @return
     * 测试通过
     */
    @RequestMapping({"/updateProperty"})
    @ResponseBody
    public Response updateProperty(@RequestBody Map<String, Object> param) {
        List<ProcessNode> eList = new ArrayList<ProcessNode>();
        ProcessNode e1 = new ProcessNode();
        ProcessNode e2 = new ProcessNode();
        long nodeId_1 = 0L;
        if(param.containsKey("nodeId_1")) {
            nodeId_1 = Long.parseLong(param.get("nodeId_1").toString());
            e1 = processNodeService.findById(nodeId_1);
        }
        if(e1 != null && e1.getNodeId() == nodeId_1) {
            String flag = e1.getNodeJson();
            if(flag == null) {
                flag = "";
            }
            if(param.containsKey("params_1")) {
                e1.setParams(param.get("params_1").toString());
            }

            if(param.containsKey("nextNodes_1")) {
                e1.setNextNodes(param.get("nextNodes_1").toString());
            }

            if(param.containsKey("node_json_1") && e1.getNodeType() == 7) {
                String temp = String.valueOf(param.get("node_json_1"));
                e1.setNodeJson(temp);
            }

            eList.add(e1);
        }
        if(param.containsKey("params_2")) {
            e2.setParams(param.get("params_2").toString());
        }
        e2.setNextNodes(param.get("nextNodes_2").toString());
        e2.setNodeId(Long.parseLong(param.get("nodeId_2").toString()));
        eList.add(e2);
        processNodeService.updateNodeForNextOrderAndParams(eList);
        return new Response(param);
    }

    /**
     *
     * @param param 包含以下参数
     *                    nodeX String 必需  节点横坐标
     *                    nodeY String 必需  节点纵坐标
     *                    nodeId String 必需  节点ID
     * @return
     * 测试通过
     */
    @RequestMapping({"/updatePropertyForMove"})
    @ResponseBody
    public Response updatePropertyForMove(@RequestBody Map<String, Object> param) {
        ProcessNode e = new ProcessNode();
        e.setNodeX(Double.parseDouble(param.get("nodeX").toString()));
        e.setNodeY(Double.parseDouble(param.get("nodeY").toString()));
        e.setNodeId(Long.parseLong(param.get("nodeId").toString()));
        boolean flag = this.processNodeService.updateNodeForMove(e);
        Response response = new Response();
        if(flag) {
            response.setCode(0);
        } else {
            response.setCode(1);
        }

        return response;
    }
    /**
     * 根据id获取节点
     * @ProcessNode nodeId：节点id Long 必需
     * @return
     * 测试通过
     */
    @RequestMapping({"/getNode"})
    @ResponseBody
    public Object getNode(@RequestBody ProcessNode processNode) {
        ProcessNode processNodes = this.processNodeService.findByProcessNode(processNode);
        return new Response(processNodes);
    }

    @RequestMapping({"/validateBranch"})
    @ResponseBody
    public Response validateBranch(@RequestBody Map<String, Object> paramMap) {
        String processNodeIdStr = paramMap.get("engineNodeId").toString();
        Long processNodeId = Long.valueOf(Long.parseLong(processNodeIdStr));
        ProcessNode processNode = processNodeService.findById(processNodeId);
        if(processNode != null) {
            String branch = paramMap.get("branch").toString();
            String jsonStr = processNode.getNodeJson();
            JSONArray object;
            if(StringUtil.isValidStr(jsonStr)) {
                JSONObject conditions = JSONObject.parseObject(jsonStr);
                object = conditions.getJSONArray("conditions");
                if(object != null && !object.isEmpty()) {
                    JSONObject i = null;
                    for(int nextNode = 0; nextNode < object.size();nextNode++) {
                        i = object.getJSONObject(nextNode);
                        if(i.getString("group_name").equals(branch)) {
                            String nextNode1 = i.getString("nextNode");
                            if(StringUtil.isValidStr(nextNode1)) {
                                paramMap.put("result", Integer.valueOf(1));
                                break;
                            }
                        }
                    }
                } else {
                    paramMap.put("result", Integer.valueOf(0));
                }
            } else {
                paramMap.put("result", Integer.valueOf(0));
            }
        } else {
            paramMap.put("result", Integer.valueOf(1));
        }
        return new Response(paramMap);
    }

    /**
     * 获取报文
     * @param  param processId long 必需 流程id
     * @return
     */
    @RequestMapping({"/getJsonData"})
    @ResponseBody
    public Response getJsonData(@RequestBody Map<String,Object> param){
        Long processId = Long.decode(param.get("processId").toString());
        String jsonString = processNodeService.getJsonData(processId);
        param.put("data",jsonString);
        return new Response(param);
    }

    /**
     * 获取产品列表信息
     * @param queryFilter
     * @return
     */
    @RequestMapping({"/getProList"})
    @ResponseBody
    public Response getProList(@RequestBody ParamFilter queryFilter){
        Map<String,Object> param = queryFilter.getParam();
        int pageNo = PageConvert.convert(queryFilter.getPage().getFirstIndex(),queryFilter.getPage().getPageSize());
        int pageSize=queryFilter.getPage().getPageSize();
        String searchKey = null;
        if(param.containsKey("searchKey")){
            searchKey = (String) param.get("searchKey");
        }
        PageHelper.startPage(pageNo, pageSize);
        List<ProCrmProduct> list = this.processNodeService.getProcessByList(searchKey);
        PageInfo pageInfo = new PageInfo(list);
        return new Response(pageInfo);
    }

    /**
     *
     * @param param
     * @return
     */
    @RequestMapping({"/getNodeList"})
    @ResponseBody
    public Response getNodeList(@RequestBody Map<String,Object> param) {
        Long processId = Long.decode(param.get("processId").toString());
        List<ProcessNode> processNodeList = processNodeService.findProcessNodeListById(processId);
        param.put("processNodeList",processNodeList);
        param.put("maxOrder", 1);
        return new Response(param);
    }
}
