package com.zw.rule.investor.service.impl;

import com.zw.rule.investor.po.Investor;
import com.zw.rule.investor.service.InvestorService;
import com.zw.rule.mapper.investor.InvestorMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/7/13.
 */
@Service
public class InvestorServiceImpl implements InvestorService{
    @Resource
    private InvestorMapper investorMapper;
    public Boolean addInvestor(Investor investor){
        investorMapper.insertSelective(investor);
        return true;
    }
    public Boolean deleteInvestorById(String id){
        investorMapper.deleteByPrimaryKey(id);
        return true;
    }

    public Boolean updtaeInvestorById(Investor investor){
        investorMapper.updateByPrimaryKeySelective(investor);
        return true;
    }
    public Investor selectInvestorById(String id){
        Investor investor=investorMapper.selectByPrimaryKey(id);
        return investor;
    }

    public List<Investor> getInvestorList(Map map){
        List<Investor> investors = investorMapper.getInvestorList(map);
        return investors;
    }
}
