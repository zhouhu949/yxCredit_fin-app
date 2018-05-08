package com.zw.rule.ruleResult.service.impl;

import com.zw.rule.mapper.ruleResult.RuleResultMapper;
import com.zw.rule.ruleResult.po.RuleResult;
import com.zw.rule.ruleResult.service.RuleResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/12/5.
 */
@Service
public class RuleResultServiceImpl implements RuleResultService {
    @Autowired
    private RuleResultMapper ruleResultMapper;
    //获取当前风控返回的记录
    @Override
    public List<RuleResult> getRuleResultCurrent(Map map){
        return ruleResultMapper.getRuleResultCurrent(map);
    }
    @Override
    public List<RuleResult> getRuleResultCurrentSP(Map map){
        return ruleResultMapper.getRuleResultCurrentSP(map);
    }
    public List<RuleResult> getRuleResultCurrentSP1(Map map){
        return ruleResultMapper.getRuleResultCurrentSP1(map);
    }
    @Override
    public int addRuleResult(RuleResult ruleResult){
        return ruleResultMapper.addRuleResult(ruleResult);
    }
}
