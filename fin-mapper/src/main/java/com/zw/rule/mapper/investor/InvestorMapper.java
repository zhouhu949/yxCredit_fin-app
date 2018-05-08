package com.zw.rule.mapper.investor;

import com.zw.rule.investor.po.Investor;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/7/13.
 */
public interface InvestorMapper {
    int deleteByPrimaryKey(String id);

    int insert(Investor investor);

    int insertSelective(Investor investor);

    Investor selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Investor investor);


    List<Investor> getInvestorList(Map map);

    //获取理财客户
    List<Investor> selectInvestorList();
}
