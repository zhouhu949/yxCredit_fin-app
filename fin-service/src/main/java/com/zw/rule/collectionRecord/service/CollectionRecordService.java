package com.zw.rule.collectionRecord.service;

import com.zw.rule.collectionRecord.po.MagCollectionRecord;
import com.zw.rule.collectionRecord.po.MagWarning;
import com.zw.rule.mybatis.ParamFilter;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/15.
 */
public interface CollectionRecordService {
    /**添加催收记录
     * @param magCollectionRecord
     * @return int
     * */
    int addCollectionRecord(MagCollectionRecord magCollectionRecord);
    /**修改催收记录
     * @param magCollectionRecord
     * @return int
     * */
    int updateCollectionRecord(MagCollectionRecord magCollectionRecord);
    /**查询一条催收记录
     * @param id
     * @return MagCollectionRecord
     * */
    MagCollectionRecord getCollectionRecordById(String id);
    /**根据放款Id查询催收记录
     * @param map
     * @return List
     * */
    List getCollectionRecordByLoanId(Map map);
    /**删除催收记录
     * @param id
     * @return int
     * */
    int deleteCollectionRecordById(String id);

    List getAfterLoanList(ParamFilter paramFilter);

    List getRepaymentDerateList(ParamFilter paramFilter);
    //
    List getRepaymentDerateListSP(ParamFilter paramFilter);

    List getLoandetaillist(String loanId);

    MagWarning getWarningByLoanId(String loanId);

    //分配电催
    int setCollection(Map addParam);

    //分配外访
    int updateCollectionExternal(Map addParam);

    //分配委外
    int updateEntrusted(Map addParam);

    //获取电催
    List getTelephoneList(Map param);

    //委外操作状态
    int updateEntrustedState(Map addParam);

    //减免金额审批
    List getApprovalList(Map param);

    //审批完成修改状态
    int updateApproval(Map param);

    //审批完成修改状态HJD
    int updateApprovalHJD(Map param);
    //获取还款详情
    List getAfterLoanDetails (Map param);

    //获取还款详情
    List getAfterLoanDetailsHJD (Map param);

    //订单结清
    int orderSettle(Map addParam);

    //添加费用减免
    int addDerate(Map addParam);

    //添加费用减免
    int addDerateHJD(Map addParam);

    //修改费用减免
    int updateDerate(Map updateParam);

    int payForXianXiaImpl(Map param);

    //根据还款表id来查询还款记录
    Map getRepaymentById(Map map);
    //更改还款表状态
    int changePayState(Map map);
    //查询所有的未还款的还款记录list
    List<Map> getAllNotPayRepayments(Map map);
    //更改订单表状态
    int changeOrderState(Map map);
    //给交易明细表添加记录mag_transaction_details
    int addTransaction(Map map);
    //更改减免表状态
    int changeDerateState(Map map);
    //减免表状态
    Map getMagDerateDetail(Map map);
}
