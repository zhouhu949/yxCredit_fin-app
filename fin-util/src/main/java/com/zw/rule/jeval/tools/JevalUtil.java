package com.zw.rule.jeval.tools;


import com.zw.rule.jeval.EvaluationException;
import com.zw.rule.jeval.Evaluator;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class JevalUtil {
    public JevalUtil() {
    }

    public static Boolean evaluateBoolean(String expression, Map<String, Object> params) throws EvaluationException {
        Evaluator evaluator = getEvaluator(params);
        return Boolean.valueOf(evaluator.getBooleanResult(expression));
    }

    public static Double evaluateNumric(String expression, Map<String, Object> params) throws EvaluationException {
        Evaluator evaluator = getEvaluator(params);
        return Double.valueOf(evaluator.getNumberResult(expression));
    }

    public static String evaluateString(String expression, Map<String, Object> params) throws EvaluationException {
        Evaluator evaluator = getEvaluator(params);
        return evaluator.evaluate(expression, false, true);
    }

    private static Evaluator getEvaluator(Map<String, Object> params) {
        Evaluator evaluator = new Evaluator();
        if(params != null && !params.isEmpty()) {
            Iterator var3 = params.entrySet().iterator();

            while(var3.hasNext()) {
                Entry entry = (Entry)var3.next();
                evaluator.putVariable((String)entry.getKey(), entry.getValue().toString());
            }
        }

        return evaluator;
    }

    public static String getNumericInterval(String expression, String param) {
        StringBuffer result = new StringBuffer();
        param = "#{" + param + "}";
        if(!expression.startsWith("(") && !expression.startsWith("[") && !expression.endsWith(")") && !expression.endsWith("]")) {
            result.append(param).append(" ").append("==").append(" ").append(expression);
            return result.toString();
        } else {
            String exp = expression.substring(1, expression.length() - 1);
            String[] segments = null;
            if(exp.startsWith(",")) {
                segments = new String[]{exp.substring(1)};
            } else {
                segments = exp.split(",");
            }

            if(segments.length == 1) {
                if(expression.substring(1, expression.length() - 1).startsWith(",")) {
                    if(expression.endsWith(")")) {
                        result.append(param).append(" ").append("<").append(" ").append(segments[0]);
                    } else if(expression.endsWith("]")) {
                        result.append(param).append(" ").append("<=").append(" ").append(segments[0]);
                    }
                } else if(expression.startsWith("(")) {
                    result.append(param).append(" ").append(">").append(" ").append(segments[0]);
                } else if(expression.startsWith("[")) {
                    result.append(param).append(" ").append(">=").append(" ").append(segments[0]);
                }
            } else if(segments.length == 2) {
                if(expression.startsWith("(")) {
                    result.append(param).append(" ").append(">").append(" ").append(segments[0]);
                } else if(expression.startsWith("[")) {
                    result.append(param).append(" ").append(">=").append(" ").append(segments[0]);
                }

                result.append(" ").append("&&").append(" ");
                if(expression.endsWith(")")) {
                    result.append(param).append(" ").append("<").append(" ").append(segments[1]);
                } else if(expression.endsWith("]")) {
                    result.append(param).append(" ").append("<=").append(" ").append(segments[1]);
                }
            }

            return result.toString();
        }
    }

    public static Map<String, Object> convertVariables(Map<String, Integer> fieldsMap, Map<String, Object> variablesMap) {
        if(CollectionUtil.isNotNullOrEmpty(variablesMap)) {
            if(!CollectionUtil.isNotNullOrEmpty(fieldsMap)) {
                return variablesMap;
            }

            String key = "";
            Integer value = null;
            Iterator var5 = variablesMap.entrySet().iterator();

            while(var5.hasNext()) {
                Entry entry = (Entry)var5.next();
                key = (String)entry.getKey();
                value = (Integer)fieldsMap.get(key);
                if(value != null && value.intValue() == 2) {
                    String variableValue = "\'" + variablesMap.get(key).toString() + "\'";
                    variablesMap.put(key, variableValue);
                }
            }
        }

        return variablesMap;
    }
}

