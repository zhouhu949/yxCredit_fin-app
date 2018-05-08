package com.zw.rule.investor.service;

import com.zw.rule.investor.po.Investor;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/7/13.
 */
public interface InvestorService{
    public Boolean addInvestor(Investor investor);
    public Boolean deleteInvestorById(String id);
    public Boolean updtaeInvestorById(Investor investor);
    public Investor selectInvestorById(String id);
    public List<Investor> getInvestorList(Map map);
}
