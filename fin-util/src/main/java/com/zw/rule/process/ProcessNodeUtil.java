package com.zw.rule.process;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zw.rule.engine.po.EngineNode;
import com.zw.rule.jeval.tools.JevalUtil;
import com.zw.rule.process.po.ProcessNode;
import com.zw.rule.util.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/12.
 */
public class ProcessNodeUtil {
    public ProcessNodeUtil() {
    }

    public static ProcessNode boxProcessNode(Map<String, Object> param) {
        ProcessNode processNode = boxProcessNode_Common(param);
//        int nodeType = processNode.getNodeType();
//        switch(nodeType) {
//            case 2:
//                boxProcessNode_Policy(param, processNode);
//            case 3:
//            default:
//                break;
//            case 4:
//                boxProcessNode_Scorecard(param, processNode);
//        }

        return processNode;
    }

    private static ProcessNode boxProcessNode_Common(Map<String, Object> param) {
        String parentIdStr = (String)param.get("parentId");
        Long processId = Long.valueOf((String) param.get("processId"));
        Long parentId = parentIdStr == null?null:Long.valueOf(Long.parseLong((String)param.get("parentId")));
        String nodeName = param.get("nodeName").toString();
        String nodeCode = param.get("nodeCode").toString();
        int nodeOrder = Integer.parseInt(param.get("nodeOrder").toString());
        int nodeType = Integer.parseInt(param.get("nodeType").toString());
        double nodeX = Double.parseDouble(param.get("nodeX").toString());
        double nodeY = Double.parseDouble(param.get("nodeY").toString());
        String nextNodes = (String)param.get("nextNodes");
        String nodeJson = String.valueOf(param.get("nodeJson"));
        String params = (String)param.get("params");
        String className = (String)param.get("className");
        int excType = Integer.parseInt(param.get("excType").toString());
        String pageUrl = (String) param.get("pageUrl");
        String handUrl = (String) param.get("handUrl");
        ProcessNode processNode = new ProcessNode();
        processNode.setProcessId(processId);
        processNode.setParentId(parentId);
        processNode.setNodeName(nodeName);
        processNode.setNodeCode(nodeCode);
        processNode.setNodeOrder(nodeOrder);
        processNode.setNodeType(nodeType);
        processNode.setNodeX(nodeX);
        processNode.setNodeY(nodeY);
        processNode.setNextNodes(nextNodes);
        processNode.setNodeJson(nodeJson);
        processNode.setParams(params);
        processNode.setExcType(excType);
        processNode.setPageUrl(pageUrl);
        processNode.setHandUrl(handUrl);
        processNode.setClassName(className);
        convertNodeScript(processNode);
        return processNode;
    }

    public static ProcessNode boxProcessNodeJson(ProcessNode processNode) {
        convertNodeScript(processNode);
        return processNode;
    }

    private static void boxProcessNode_Policy(Map<String, Object> param, ProcessNode processNode) {
        JSONObject jsonObject = new JSONObject();
        String addOrSubRules = param.get("addOrSubRules").toString();
        String deny_rules = param.get("deny_rules").toString();
        JSONObject addOrSubRulesJson = JSONObject.parseObject(addOrSubRules);
        JSONObject deny_rulesJson = JSONObject.parseObject(deny_rules);
        JSONArray array = new JSONArray();
        JSONArray selectRule;
        if(addOrSubRulesJson != null) {
            selectRule = addOrSubRulesJson.getJSONArray("rules");
            if(selectRule != null) {
                array.addAll(selectRule);
            }
        }

        if(deny_rulesJson != null) {
            selectRule = deny_rulesJson.getJSONArray("rules");
            if(selectRule != null) {
                array.addAll(selectRule);
            }
        }

        String var13 = "";
        var13 = array.toString();
        jsonObject.put("selectedRule", var13);
        jsonObject.put("addOrSubRules", addOrSubRules);
        jsonObject.put("deny_rules", deny_rules);
        processNode.setNodeJson(jsonObject.toString());
        int size = array.size();
        List rules = new ArrayList();
        JSONObject object = null;

        for(int i = 0; i < size; i++) {
            object = array.getJSONObject(i);
            rules.add(object.getLong("id"));
        }

        processNode.setRuleList(rules);
    }

    private static void boxProcessNode_Scorecard(Map<String, Object> param, ProcessNode processNode) {
        Long cardId = Long.parseLong(param.get("cardId").toString());
        processNode.setCardId(cardId);
    }

    public static void convertNodeScript(ProcessNode node) {
//        int nodeType = node.getNodeType();
//        switch(3) {
//            case 3:
        convertClassify(node);
//            case 4:
//            case 5:
//            case 6:
//            default:
//                break;
//            case 7:
//                convertSandBox(node);
//                break;
//            case 8:
//                convertCreditLevel(node);
//                break;
//            case 9:
//                convertDecision(node);
//                break;
//            case 10:
//                convertQuotaCalc(node);
//        }

    }

    private static void convertSandBox(ProcessNode node) {
        String nodeJson = node.getNodeJson();
        if(StringUtil.isValidStr(nodeJson)) {
            JSONArray jsonArray = JSONArray.parseArray(nodeJson);
            if(jsonArray != null && !jsonArray.isEmpty()) {
                int size = jsonArray.size();
                int sum = 0;

                int i;
                for(i = 0; i < size; i++) {
                    sum += jsonArray.getJSONObject(i).getIntValue("proportion");
                }

                for(i = 0; i < size; i++) {
                    jsonArray.getJSONObject(i).put("sum", sum);
                }
            }

            node.setNodeScript(jsonArray.toString());
        }

    }

    private static void convertClassify(ProcessNode node) {
        String nodeJson = node.getNodeJson();
        if(StringUtil.isValidStr(nodeJson)) {
            JSONObject jsonObject = JSONObject.parseObject(nodeJson);
            if(jsonObject != null && !jsonObject.isEmpty()) {
                JSONArray conditions = jsonObject.getJSONArray("conditions");
                if(conditions != null && !conditions.isEmpty()) {
                    JSONArray conditionArray = processClassifyConditions(conditions);
                    JSONObject resultjson = new JSONObject();
                    resultjson.put("fields", jsonObject.getJSONArray("fields"));
                    resultjson.put("conditions", conditionArray);
                    node.setNodeScript(resultjson.toString());
                }
            }
        }

    }

    private static JSONArray processClassifyConditions(JSONArray conditions) {
        JSONArray resultArray = new JSONArray();
        int size = conditions.size();
        JSONObject condition = null;
        JSONObject resultJson = null;
        String nextNode = "";

        for(int i = 0; i < size; i++) {
            condition = conditions.getJSONObject(i);
            nextNode = condition.getString("nextNode");
            if(nextNode != null && !"".equals(nextNode)) {
                JSONArray formulas = condition.getJSONArray("formulas");
                if(formulas != null && !formulas.isEmpty()) {
                    resultJson = new JSONObject();
                    resultJson.put("nextNode", nextNode);
                    resultJson.put("formula", processClassifyFormulas(formulas));
                    resultArray.add(resultJson);
                } else {
                    resultJson = new JSONObject();
                    resultJson.put("nextNode", nextNode);
                    resultJson.put("formula", "");
                    resultArray.add(resultJson);
                }
            }
        }

        return resultArray;
    }

    private static String processClassifyFormulas(JSONArray formulas) {
        int size = formulas.size();
        if(size == 1) {
            return convertFormula(formulas.getJSONObject(0));
        } else {
            StringBuffer sb = new StringBuffer();
            JSONObject formula = null;

            for(int i = 0; i < size; i++) {
                formula = formulas.getJSONObject(i);
                if(i == 0) {
                    sb.append("(");
                    sb.append(convertFormula(formula));
                    sb.append(")");
                } else {
                    sb.append(" ");
                    sb.append(formulas.getJSONObject(i - 1).getString("sign"));
                    sb.append(" ");
                    sb.append("(");
                    sb.append(convertFormula(formula));
                    sb.append(")");
                }
            }

            return sb.toString();
        }
    }

    private static String convertFormula(JSONObject formula) {
        String fieldCode1 = "#{" + formula.getString("field_code1") + "}";
        String operator1 = formula.getString("operator1");
        Object value1 = formula.get("value1");
        String relativeOperator = formula.getString("relative_operator");
        String fieldCode2 = "#{" + formula.getString("field_code2") + "}";
        String operator2 = formula.getString("operator2");
        Object value2 = formula.get("value2");
        StringBuffer sb = new StringBuffer();
        if(StringUtil.isValidStr(relativeOperator)) {
            sb.append("(");
            sb.append(convertOperator(fieldCode1, operator1, value1));
            sb.append(")");
            sb.append(" ");
            sb.append(relativeOperator);
            sb.append(" ");
            sb.append("(");
            sb.append(convertOperator(fieldCode2, operator2, value2));
            sb.append(")");
        } else {
            sb.append("(");
            sb.append(convertOperator(fieldCode1, operator1, value1));
            sb.append(")");
        }

        return sb.toString();
    }

    private static String convertOperator(String variable, String operator, Object value) {
        StringBuffer sb = new StringBuffer();
        if(!variable.equalsIgnoreCase("contains") && !variable.equalsIgnoreCase("notContains") && !variable.equalsIgnoreCase("equals") && !variable.equalsIgnoreCase("notEquals")) {
            sb.append(variable).append(operator).append(value);
        } else {
            sb.append(operator).append("(");
            sb.append(variable).append(",").append(value).append(")");
        }

        return sb.toString();
    }

    private static void convertCreditLevel(ProcessNode node) {
        String nodeJson = node.getNodeJson();
        if(StringUtil.isValidStr(nodeJson)) {
            JSONObject var2 = JSONObject.parseObject(nodeJson);
        }

    }

    private static void convertDecision(ProcessNode node) {
        String nodeJson = node.getNodeJson();
        if(StringUtil.isValidStr(nodeJson)) {
            JSONObject jsonObject = JSONObject.parseObject(nodeJson);
            if(jsonObject.containsKey("inputs")) {
                int inputs = jsonObject.getInteger("inputs");
                switch(inputs) {
                    case 1:
                        handleSingleDecisionVariable(node, jsonObject);
                        break;
                    case 2:
                        handleDoubleDecisionVariables(node, jsonObject);
                        break;
                    case 3:
                        handleMoreThanTripleDecisionVariables(node, jsonObject);
                }
            }
        }

    }

    private static void handleSingleDecisionVariable(ProcessNode processNode, JSONObject jsonObject) {
        JSONObject resultJson = new JSONObject();
        JSONArray realConditions = new JSONArray();
        int conditionType = jsonObject.getIntValue("condition_type");
        if(conditionType == 2) {
            processNode.setNodeScript(processNode.getNodeJson());
        } else if(conditionType == 1) {
            JSONArray conditions = jsonObject.getJSONArray("conditions");
            if(conditions != null && !conditions.isEmpty()) {
                int size = conditions.size();
                JSONObject condition = null;
                int i = 0;

                while(true) {
                    if(i >= size) {
                        resultJson.put("inputs", jsonObject.getIntValue("inputs"));
                        resultJson.put("condition_type", jsonObject.getIntValue("condition_type"));
                        resultJson.put("input", jsonObject.getJSONArray("input"));
                        resultJson.put("output", jsonObject.getJSONObject("output"));
                        resultJson.put("conditions", realConditions);
                        break;
                    }

                    condition = conditions.getJSONObject(i);
                    JSONArray subArray = condition.getJSONArray("formula");
                    if(subArray != null && !subArray.isEmpty()) {
                        JSONObject result = new JSONObject();
                        result.put("result", condition.get("result"));
                        result.put("resultKey", condition.get("resultKey"));
                        int subSize = subArray.size();
                        JSONObject subJson = null;
                        StringBuffer expression = new StringBuffer();

                        for(int j = 0; j < subSize; j++) {
                            subJson = subArray.getJSONObject(j);
                            if(j != 0) {
                                expression.append(" ");
                                if("AND".equalsIgnoreCase(subArray.getJSONObject(j).getString("sign"))) {
                                    expression.append("&&");
                                } else if("OR".equalsIgnoreCase(subArray.getJSONObject(j).getString("sign"))) {
                                    expression.append("||");
                                }

                                expression.append(" ");
                            }

                            expression.append("(");
                            String operator = subJson.getString("operator");
                            if(jsonObject.getJSONArray("input").getJSONObject(0).getIntValue("field_type") == 2) {
                                expression.append(operator);
                                expression.append("(");
                                expression.append("#{" + subJson.getString("field_code") + "}");
                                expression.append(",");
                                expression.append(subJson.get("result"));
                                expression.append(")");
                            } else {
                                expression.append("#{" + subJson.getString("field_code") + "}");
                                expression.append(" ");
                                expression.append(operator);
                                expression.append(" ");
                                expression.append(subJson.get("result"));
                                expression.append(")");
                            }
                        }

                        result.put("formula", expression.toString());
                        realConditions.add(result);
                    }

                    i++;
                }
            }

            processNode.setNodeScript(resultJson.toString());
        }

    }

    private static void handleDoubleDecisionVariables(ProcessNode processNode, JSONObject jsonObject) {
        JSONObject resultJson = new JSONObject();
        JSONArray resultArray = new JSONArray();
        int conditionType = jsonObject.getIntValue("condition_type");
        JSONArray conditions = jsonObject.getJSONArray("conditions");
        JSONArray inputArray = jsonObject.getJSONArray("input");
        HashMap inputFieldTypeMap = new HashMap();
        if(inputArray != null && !inputArray.isEmpty()) {
            for(int i = 0; i < inputArray.size(); i++) {
                inputFieldTypeMap.put(inputArray.getJSONObject(i).getString("field_code"), inputArray.getJSONObject(i).getIntValue("field_type"));
            }
        }

        if(conditions != null && !conditions.isEmpty()) {
            if(conditionType == 1) {
                int size = conditions.size();
                JSONObject condition = null;

                for(int i = 0; i < size; i++) {
                    condition = conditions.getJSONObject(i);
                    Object result = condition.get("result");
                    Object resultKey = condition.get("resultKey");
                    JSONObject formula = condition.getJSONObject("formula");
                    StringBuffer expression = new StringBuffer();
                    expression.append("(");
                    if(((Integer)inputFieldTypeMap.get(formula.getString("field_code1"))) == 1) {
                        expression.append(JevalUtil.getNumericInterval(formula.getString("expression1"), formula.getString("field_code1")));
                    }

                    if(((Integer)inputFieldTypeMap.get(formula.getString("field_code1"))) == 3) {
                        expression.append("#{");
                        expression.append(formula.getString("field_code1"));
                        expression.append("}");
                        expression.append("==");
                        expression.append(formula.getString("expression1"));
                    }

                    if(((Integer)inputFieldTypeMap.get(formula.getString("field_code1"))) == 2) {
                        expression.append("equals");
                        expression.append("(");
                        expression.append("#{");
                        expression.append(formula.getString("field_code1"));
                        expression.append("}");
                        expression.append(",");
                        expression.append(formula.getString("expression1"));
                        expression.append(")");
                    }

                    expression.append(" ");
                    expression.append("&&");
                    expression.append(" ");
                    if(((Integer)inputFieldTypeMap.get(formula.getString("field_code2"))) == 1) {
                        expression.append(JevalUtil.getNumericInterval(formula.getString("expression2"), formula.getString("field_code2")));
                    }

                    if(((Integer)inputFieldTypeMap.get(formula.getString("field_code2"))) == 3) {
                        expression.append("#{");
                        expression.append(formula.getString("field_code2"));
                        expression.append("}");
                        expression.append("==");
                        expression.append(formula.getString("expression2"));
                    }

                    if(((Integer)inputFieldTypeMap.get(formula.getString("field_code2"))) == 2) {
                        expression.append("equals");
                        expression.append("(");
                        expression.append("#{");
                        expression.append(formula.getString("field_code2"));
                        expression.append("}");
                        expression.append(",");
                        expression.append(formula.getString("expression2"));
                        expression.append(")");
                    }

                    expression.append(")");
                    JSONObject resultSubJson = new JSONObject();
                    resultSubJson.put("result", result);
                    resultSubJson.put("resultKey", resultKey);
                    resultSubJson.put("formula", expression.toString());
                    resultArray.add(resultSubJson);
                }

                resultJson.put("inputs", jsonObject.getIntValue("inputs"));
                resultJson.put("condition_type", jsonObject.getIntValue("condition_type"));
                resultJson.put("input", jsonObject.getJSONArray("input"));
                resultJson.put("output", jsonObject.getJSONObject("output"));
                resultJson.put("conditions", resultArray);
                processNode.setNodeScript(resultJson.toString());
            } else {
                processNode.setNodeScript(jsonObject.toString());
            }
        }

    }

    private static void handleMoreThanTripleDecisionVariables(ProcessNode processNode, JSONObject jsonObject) {
        processNode.setNodeScript(jsonObject.toString());
    }

    private static void convertQuotaCalc(ProcessNode node) {
        String nodeJson = node.getNodeJson();
        if(StringUtil.isValidStr(nodeJson)) {
            JSONObject var2 = JSONObject.parseObject(nodeJson);
        }

    }
}
