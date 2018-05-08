package com.zw.rule.mapper.repayment;

import com.zw.rule.mapper.common.BaseMapper;
import com.zw.rule.repayment.po.MagRepayment;
import com.zw.rule.repayment.po.Repayment;

import java.util.List;
import java.util.Map;

public interface RepaymentMapper extends BaseMapper{
    int insertRepaymentList(List list);

    List getRepaymentList(Map map);

    List getRepaymentDerateList(Map map);

    List getRepaymentDerateListSP(Map map);

    List getLoandetaillist(String loanId);

    List getLoanRepaymentList(Map map);//通过订单id获取还款计划

    List<Repayment> getList(Map map);

    Repayment getListSP(Map map);

    void updtaeRepayByorderId(Map map);//更新还款状态和备注

    Boolean insertRepayment(Repayment repayment);

    //获取还款详情
    List getAfterLoanDetails (Map paramFilter);

    //获取还款详情
    List getAfterLoanDetailsHJD (Map paramFilter);

    int updtaeRepayment(Repayment repayment);

    List<Map> getPaymentRepaymentLis(Map map);
    //根据id获取还款记录（单条）
    Map selectRepaymentById(Map map);
    //更新还款表状态
    int updateRepaymentState(Map map);
    //更新订单表状态
    int updateOrderState(Map map);
    //查询该订单下所有未还款的记录
    List<Map> getAllNotPayRepayments(Map map);
    //添加还款交易表mag_transaction_details
    int insertTransaction(Map map);
    //更改减免表减免状态为已使用
    int changeDerateState(Map map);

}