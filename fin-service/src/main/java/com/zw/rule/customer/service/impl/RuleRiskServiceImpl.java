package com.zw.rule.customer.service.impl;

import com.zw.rule.customer.po.RuleRisk;
import com.zw.rule.customer.service.RuleRiskService;
import com.zw.rule.mapper.customer.RuleRiskMapper;
import com.zw.rule.rulerisk.po.MagRuleRisk;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <strong>Title :<br>
 * </strong> <strong>Description : </strong>@类注释说明写在此处@<br>
 * <strong>Create on : 2017年07月07日<br>
 * </strong>
 * <p>
 * <strong>Copyright (C) Vbill Co.,Ltd.<br>
 * </strong>
 * <p>
 *
 * @author department:技术开发部 <br>
 *         username:hehao <br>
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
public class RuleRiskServiceImpl implements RuleRiskService{
    @Resource
    private RuleRiskMapper ruleRiskMapper;

    /**
     * 获取风险评估结果
     * @param cardNum
     * @return
     */
    @Override
    public RuleRisk getRuleRisk(String cardNum) {
        return ruleRiskMapper.selectByPrimaryKey(cardNum);
    }

    @Override
    public void insertRuleRisk(MagRuleRisk magRuleRisk) {
        //判断是否存在数据存在就更新，不存在就插入数据
        int count = ruleRiskMapper.selectCountByCardNo(magRuleRisk.getCradNo());
        if(count > 0){
            ruleRiskMapper.updateRuleRisk(magRuleRisk);
        }else {
            ruleRiskMapper.insertRuleRisk(magRuleRisk);
        }
    }
}

