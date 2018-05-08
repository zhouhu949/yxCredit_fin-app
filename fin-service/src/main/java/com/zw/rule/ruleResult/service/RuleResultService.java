package com.zw.rule.ruleResult.service;

import com.zw.rule.ruleResult.po.RuleResult;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/12/5.
 */
public interface RuleResultService {
    //获取当前风控返回的记录
    List<RuleResult> getRuleResultCurrent(Map map);
    //获取当前风控返回的记录orderId
    List<RuleResult> getRuleResultCurrentSP(Map map);
    //获取当前风控返回的记录userId
    List<RuleResult> getRuleResultCurrentSP1(Map map);
    int addRuleResult(RuleResult ruleResult);
}
