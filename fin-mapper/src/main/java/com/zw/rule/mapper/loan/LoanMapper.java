package com.zw.rule.mapper.loan;

import com.zw.rule.loan.po.Loan;
import com.zw.rule.mapper.common.BaseMapper;

import java.util.List;
import java.util.Map;

public interface LoanMapper extends BaseMapper{
    boolean submitLoan (Map map);
    List getLoanList(Map param);

    int reviewLoan(Map map);

    Loan getLoanById(String loanId);

    Loan getLoanByOrderId(String loanId);

    Map getUserInfoByloanId(String loanId);

    void addLoanDetail(Map map);

    List getAllList(String date);
}