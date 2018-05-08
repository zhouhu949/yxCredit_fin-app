package com.zw.rule.excel;

import com.zw.rule.excel.po.ExcelBean1;
import com.zw.rule.excel.po.ExcelBean2;
import com.zw.rule.excel.po.ExcelBean3;
import com.zw.rule.excel.po.RuleRisk;
import com.zw.rule.loan.po.Loan;
import com.zw.rule.loan.po.MagLoanDetail;
import com.zw.rule.repayment.po.Repayment;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/12.
 */
public interface ExcelService {

    void updateOrderInfo(ExcelBean1 excelBean1);

    void insertRuleRefuse(ExcelBean2 excelBean2);

    void insertRuleRisk(RuleRisk ruleRisk);

    void insertScoreCard(ExcelBean3 excelBean3);

    Map getLoanIdByIdCard(String idCard);

    void deleteLoanDetail(String loanId);

    void updateLoanInfo(Loan loan);

    void insertLoanDetail(List<MagLoanDetail> list1);

    boolean getRuleRefuse(String idCard);

    boolean getScoreCard(String idCard);

    boolean getRuleRisk(String cradNo);

    void insertRepaymentList(List<Repayment> list1);

    void deleteRepaymentByLoanId(String loanId);
}

