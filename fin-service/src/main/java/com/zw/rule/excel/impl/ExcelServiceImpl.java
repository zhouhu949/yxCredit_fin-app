//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//


package com.zw.rule.excel.impl;


import com.zw.rule.excel.ExcelService;
import com.zw.rule.excel.po.ExcelBean1;
import com.zw.rule.excel.po.ExcelBean2;
import com.zw.rule.excel.po.ExcelBean3;
import com.zw.rule.excel.po.RuleRisk;
import com.zw.rule.loan.po.Loan;
import com.zw.rule.loan.po.MagLoanDetail;
import com.zw.rule.mapper.excel.ExcelMapper;
import com.zw.rule.mapper.repayment.RepaymentMapper;
import com.zw.rule.repayment.po.Repayment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class ExcelServiceImpl implements ExcelService {

    @Resource
    private ExcelMapper excelMapper;

    @Autowired
    private RepaymentMapper repaymentMapper;

    @Override
    public void updateOrderInfo(ExcelBean1 excelBean1) {
        excelMapper.updateOrderInfo(excelBean1);
    }

    @Override
    public void insertRuleRefuse(ExcelBean2 excelBean2) {
        excelMapper.insertRuleRefuse(excelBean2);
    }

    @Override
    public void insertRuleRisk(RuleRisk ruleRisk) {
        excelMapper.insertRuleRisk(ruleRisk);
    }

    @Override
    public void insertScoreCard(ExcelBean3 excelBean3) {
        excelMapper.insertScoreCard(excelBean3);
    }

    @Override
    public Map getLoanIdByIdCard(String idCard) {

        return excelMapper.getLoanIdByIdCard(idCard);
    }

    @Override
    public void deleteLoanDetail(String loanId) {
        excelMapper.deleteLoanDetail(loanId);
    }

    @Override
    public void updateLoanInfo(Loan loan) {
        excelMapper.updateLoanInfo(loan);
    }

    @Override
    public void insertLoanDetail(List<MagLoanDetail> list1) {
        excelMapper.insertLoanDetail(list1);
    }

    @Override
    public boolean getRuleRefuse(String idCard) {
        int count = excelMapper.getRuleRefuseCount(idCard);
        if(count > 0){
            return false;
        }
        return true;
    }

    @Override
    public boolean getScoreCard(String idCard) {
        int count = excelMapper.getScoreCardCount(idCard);
        if(count > 0){
            return false;
        }
        return true;
    }

    @Override
    public boolean getRuleRisk(String cradNo) {
        int count = excelMapper.getRuleRiskCount(cradNo);
        if(count > 0){
            return false;
        }
        return true;
    }

    @Override
    public void insertRepaymentList(List<Repayment> list1) {
        //向repayment表中插入数据
        repaymentMapper.insertRepaymentList(list1);
    }

    @Override
    public void deleteRepaymentByLoanId(String loanId) {
        excelMapper.deleteRepaymentByLoanId(loanId);
    }
}
