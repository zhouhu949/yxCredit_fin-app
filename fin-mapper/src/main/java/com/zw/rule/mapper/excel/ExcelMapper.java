package com.zw.rule.mapper.excel;

import com.zw.rule.excel.po.ExcelBean1;
import com.zw.rule.excel.po.ExcelBean2;
import com.zw.rule.excel.po.ExcelBean3;
import com.zw.rule.excel.po.RuleRisk;
import com.zw.rule.loan.po.Loan;
import com.zw.rule.loan.po.MagLoanDetail;
import com.zw.rule.mapper.common.BaseMapper;

import java.util.List;
import java.util.Map;

public interface ExcelMapper extends BaseMapper{

    void updateOrderInfo(ExcelBean1 excelBean1);

    void insertRuleRefuse(ExcelBean2 excelBean2);

    void insertRuleRisk(RuleRisk ruleRisk);

    void insertScoreCard(ExcelBean3 excelBean3);

    Map getLoanIdByIdCard(String idCard);

    void deleteLoanDetail(String loanId);

    void updateLoanInfo(Loan loan);

    void insertLoanDetail(List<MagLoanDetail> list);

    int getRuleRefuseCount(String idCard);

    int getScoreCardCount(String idCard);

    int getRuleRiskCount(String cradNo);

    void deleteRepaymentByLoanId(String loanId);
}