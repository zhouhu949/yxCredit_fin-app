package com.zw.rule.api.service;

import com.zw.rule.api.repayment.RepaymentRequest;

import java.io.IOException;

/**
 * 放款信息
 * @author 陈淸玉 2018-07-20
 */
public interface IRepaymentServer {

    String BEAN_KEY = "repaymentServerImpl";
    /**
     * 远程接口端 查询还款账号
     * @return  查询结果
     * @exception IOException http 异常
     */
    String getLoan(RepaymentRequest request) throws IOException;
}
