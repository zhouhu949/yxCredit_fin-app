package com.zw.rule.repayment.service;


import com.alibaba.fastjson.JSONObject;
import com.zw.rule.mybatis.ParamFilter;

import java.util.List;
import java.util.Map;


public interface RepayManageService {

    /**
     *根据条件查询客户信息
     * */
    public List getCust(ParamFilter paramFilter);

    /**
     * 根据客户id和订单查询，查询所有待还款的订单
     * @param paramFilter
     * @return
     */
    public List getRepayOrders(ParamFilter paramFilter);

    /**
     * 查询信贷还款明细表
     * @param paramFilter
     * @return
     */
    public List getCrmPaycontrol(ParamFilter paramFilter);

    /**
     * 查询还款记录
     * @param paramFilter
     * @return
     */
    public List getCrmRepayRecords(ParamFilter paramFilter);

    /**
     * 划扣记录
     * @param paramFilter
     * @return
     */
    public List getTransferRecord(ParamFilter paramFilter);

    /**
     * 查看结算户银行卡信息
     * @param
     * @return
     */
    public List getCustBankCardInfo(String customerId);

    /**
     * 产品的参数查找
     * @param
     * @return
     */
    public List getPayProductInfo(String crmOrderId);

    /**
     * 获得申请的金额
     * @param
     * @return
     */
    public JSONObject getPayApplyInfoFromType(String crmOrderId, String type);

    /**
     * 根据订单获得正常还款的数据
     * @param
     * @return
     */
    public JSONObject getNormalPayJson(String crmOrderId,String type) throws Exception;

    /**
     * 真实还款
     * @param
     * @return
     */
    public JSONObject payMoney(String paramsString,String type) throws Exception;
}

