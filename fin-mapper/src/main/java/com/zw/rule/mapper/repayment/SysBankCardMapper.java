package com.zw.rule.mapper.repayment;


import com.zw.rule.mybatis.ParamFilter;
import com.zw.rule.repayment.po.BankCard;

import java.util.List;
import java.util.Map;

public interface SysBankCardMapper {
    int deleteByPrimaryKey(String id);

    int insert(BankCard record);

    int insertSelective(BankCard record);

    BankCard selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(BankCard record);

    int updateByPrimaryKey(BankCard record);

    List<Map> getCustBankCardInfo(String customerId);
}