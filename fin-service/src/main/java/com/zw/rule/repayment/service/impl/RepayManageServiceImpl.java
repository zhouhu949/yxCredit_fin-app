package com.zw.rule.repayment.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zw.base.util.ArithUtil;
import com.zw.base.util.DateUtils;
import com.zw.rule.customer.po.Order;
import com.zw.rule.jeval.tools.StringUtil;
import com.zw.rule.mapper.customer.OrderMapper;
import com.zw.rule.mapper.customer.PerCustomerMapper;
import com.zw.rule.mapper.product.ProCrmProductMapper;
import com.zw.rule.mapper.repayment.*;
import com.zw.rule.mybatis.ParamFilter;
import com.zw.rule.repayment.service.RepayManageService;
import com.zw.rule.repayment.service.RepaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 功能说明：信贷产品service层
 * @author wangmin
 * 修改人:
 * 修改原因：
 * 修改时间：
 * 修改内容：
 * 创建日期：2017-06-13
 */
@Service
public class RepayManageServiceImpl implements RepayManageService {

    @Resource
    private RepaymentService repaymentService;

    @Resource
    private PerCustomerMapper perCustomerMapper;

    @Resource
    private CrmPaycontrolMapper crmPaycontrolMapper;

    @Resource
    private CrmPayrecoderMapper crmPayrecoderMapper;

    @Resource
    private SysBankCardMapper sysBankCardMapper;

    @Resource
    private OrderMapper orderMapper;

    @Resource
    private ProCrmProductMapper proCrmProductMapper;

    @Resource
    private AccDerateManageMapper accDerateManageMapper;

    @Override
    public List getCust(ParamFilter paramFilter) {
        return perCustomerMapper.getCustomers(paramFilter);
    }

    @Override
    public List getRepayOrders(ParamFilter paramFilter) {
        return orderMapper.getOrders(paramFilter);
    }

    @Override
    public List getCrmPaycontrol(ParamFilter paramFilter) {
        return crmPaycontrolMapper.getCrmPaycontrolList(paramFilter);
    }

    @Override
    public List getCrmRepayRecords(ParamFilter paramFilter) {
        return crmPayrecoderMapper.getCrmRepayRecords(paramFilter);
    }

    @Override
    public List getTransferRecord(ParamFilter paramFilter) {
        return crmPayrecoderMapper.getTransferRecord(paramFilter);
    }

    @Override
    public List getCustBankCardInfo(String customerId) {
        return sysBankCardMapper.getCustBankCardInfo(customerId);
    }

    @Override
    public List getPayProductInfo(String crmOrderId) {
        return proCrmProductMapper.getPayProductInfo(crmOrderId);
    }

    @Override
    public JSONObject getPayApplyInfoFromType(String crmOrderId,String type) {
        JSONObject resultJson = new JSONObject();
        List<Map> list =  accDerateManageMapper.getPayApplyInfoFromType(crmOrderId,type);
        resultJson.put("has", "0");
        if(list.size()>0){
            resultJson.putAll(list.get(0));
            resultJson.put("has", "1");
            resultJson.put("INITIAL_RETURN_SERVICE", chkValue(resultJson, "INITIAL_RETURN_SERVICE"));
            resultJson.put("FINAL_RETURN_SERVICE", chkValue(resultJson, "FINAL_RETURN_SERVICE"));
            resultJson.put("INITIAL_SURPLUS_INTEREST", chkValue(resultJson, "INITIAL_SURPLUS_INTEREST"));
            resultJson.put("FINAL_SURPLUS_INTEREST", chkValue(resultJson, "FINAL_SURPLUS_INTEREST"));
            resultJson.put("INITIAL_ADVANCE_PENALTY", chkValue(resultJson, "INITIAL_ADVANCE_PENALTY"));
            resultJson.put("FINAL_ADVANCE_PENALTY", chkValue(resultJson, "FINAL_ADVANCE_PENALTY"));
            resultJson.put("DERATE_RETURN_SERVICE", chkValue(resultJson, "DERATE_RETURN_SERVICE"));
            resultJson.put("totalMoney", ArithUtil.add(resultJson.getDouble("totalMoney"),resultJson.getDouble("FINAL_SURPLUS_INTEREST")));
            resultJson.put("totalMoney",ArithUtil.sub(resultJson.getDouble("totalMoney"),resultJson.getDouble("FINAL_CONTRACT_PENALTY")));
            resultJson.put("remainOverDueViolateMoney", 0);
            resultJson.put("FINAL_CONTRACT_PENALTY", 0);
        }
        return  resultJson;
    }

    @Override
    public JSONObject getNormalPayJson(String crmOrderId,String type) throws Exception{
        JSONObject resultJson = new JSONObject();
        resultJson.put("responseCode", "0");
        //判断是否有逾期的明细或者存在申请
        List overDetailList = crmPaycontrolMapper.getDetailByStatus(crmOrderId,2);
        if(StringUtils.isEmpty(type)){
            if(overDetailList !=null && overDetailList.size()>0){ //说明存在逾期
                resultJson.put("info", "存在逾期明细无法进行正常还款!");
                return resultJson;
            }
            // 判断是否含有其他的申请如果含有则同样无法进行还款
            if ("1".equals(hasPayApply(crmOrderId).getString("has"))) {
                resultJson.put("info", "存在其他的还款申请无法进行正常还款!");
                return resultJson;
            }
        }
        //开始判断今天是否已经有还款过了,防止重复
        List checkUniqueList = crmPayrecoderMapper.checkUniqueList(crmOrderId, DateUtils.getCurrentTime(DateUtils.STYLE_2));
        if(checkUniqueList.size()>0){
            resultJson.put("info", "当日进行过正常还款,无法操作!");
            return resultJson;
        }
        //开始本期数据的赋值
        List<Map> currentList = crmPaycontrolMapper.getCurrentList(crmOrderId);
        if(currentList.isEmpty()){
            resultJson.put("info", "未发现明细!");
            resultJson.put("totalMoney",0);
            return resultJson;
        }

        resultJson.putAll(currentList.get(0));
        //查找已经还款的金额
        List<Map> paidList = crmPayrecoderMapper.getPaidList(currentList.get(0).get("id").toString());
        resultJson.putAll(paidList.get(0));
        resultJson.put("responseCode", "1");
        resultJson.put("applyType", 0);
        return resultJson;
    }

    @Override
    public JSONObject payMoney(String paramsString, String type) throws Exception {
        //格式化参数
        JSONObject  paramJson = (JSONObject) JSON.parseObject(paramsString);
        //数据检测
        JSONObject  preCheckJson = repaymentService.payMoneyPreCheck(paramJson,type);
        return null;
    }


    private double chkValue(JSONObject parseJson, String arg){
        if (parseJson.containsKey(arg)) {
            return parseJson.getDouble(arg);
        }else{
            return 0;
        }
    }

    /**
     * 判断是否含有其他的还款申请
     * 功能说明：该方法实现的功能
     * @author wangmin
     * @param 方法里面接收的参数及其含义
     * @return 该方法的返回值的类型，含义   
     * @throws  该方法可能抛出的异常，异常的类型、含义。
     * 最后修改时间：最后修改时间
     * date: 2017-7-4
     * 修改内容：
     * 修改注意点：
     */
    private JSONObject hasPayApply(String crmOrderId) throws Exception{
        JSONObject resultJson = new JSONObject();
        List list = accDerateManageMapper.getOtherPay(crmOrderId);
        //String sql  = "SELECT * from acc_derate_manage where CRM_ORDER_ID='"+crmOrderId+"' and STATUS !=0";
        //List list;
        resultJson.put("has", "1");
        //list = queryBySql(sql);
        if(list.isEmpty()){
            resultJson.put("has", "0");
        }
        return resultJson;
    }
}
