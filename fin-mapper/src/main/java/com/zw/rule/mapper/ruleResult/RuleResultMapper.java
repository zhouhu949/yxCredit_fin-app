package com.zw.rule.mapper.ruleResult;

import com.zw.rule.ruleResult.po.RuleResult;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/12/5.
 */
public interface RuleResultMapper {
    //获取当前风控返回的记录
    List<RuleResult> getRuleResultCurrent(Map map);

    //获取当前风控返回的记录orderId
    List<RuleResult> getRuleResultCurrentSP(Map map);

    //获取当前风控返回的记录userId
    List<RuleResult> getRuleResultCurrentSP1(Map map);



    //添加风控记录
    int addRuleResult(RuleResult ruleResult);
}
