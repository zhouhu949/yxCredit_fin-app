package com.zw.rule.customer.service;

import com.zw.rule.customer.po.RuleRisk;
import com.zw.rule.rulerisk.po.MagRuleRisk;

import java.util.Map;

/**
 * Created by Administrator on 2017/7/7 0007.
 */
public interface RuleRiskService {
    RuleRisk getRuleRisk(String cardNum);

    void insertRuleRisk(MagRuleRisk magRuleRisk);
}
