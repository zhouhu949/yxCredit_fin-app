package com.zw.rule.mapper.loanmange;


import com.zw.rule.loanmange.po.LoanManage;
import com.zw.rule.loanmange.po.SettleRecord;

import java.util.List;
import java.util.Map;

public interface LoanManageMapper {
    int deleteByPrimaryKey(String id);

    int insert(LoanManage record);

    int insertSelective(LoanManage record);

    LoanManage selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(LoanManage record);

    int updateByPrimaryKey(LoanManage record);

    List <Map>getLoanManage(Map map);

    List<Map> getRepaymentList(Map map);

    List <Map>getLoanManageHJD(Map map);

    List <LoanManage> getByPrimaryKey(Map map);

    SettleRecord getMagSettleRecord(Map map);

    int addSettleRecord(SettleRecord settleRecord);

    int updateSettleRecord(Map map);
    //查询该订单下所有的交易记录
    List<Map>  getJYDetailList(Map map);
}