package com.zw.rule.mapper.customer;

import com.zw.rule.customer.po.RuleRisk;
import com.zw.rule.rulerisk.po.MagRuleRisk;

public interface RuleRiskMapper {
    int deleteByPrimaryKey(String id);

    int insert(RuleRisk record);

    int insertSelective(RuleRisk record);

    RuleRisk selectByPrimaryKey(String cardNum);

    int updateByPrimaryKeySelective(RuleRisk record);

    int updateByPrimaryKey(RuleRisk record);

    void insertRuleRisk(MagRuleRisk magRuleRisk);

    int selectCountByCardNo(String cradNo);

    void updateRuleRisk(MagRuleRisk magRuleRisk);
}