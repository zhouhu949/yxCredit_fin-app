package com.zw.rule.goldLogistics.service.impl;

import com.zw.rule.api.BaoFuApi;
import com.zw.rule.customer.po.Order;
import com.zw.rule.goldLogistics.service.GoldPaymentService;
import com.zw.rule.mapper.customer.OrderMapper;
import com.zw.rule.mapper.repayment.RepaymentMapper;
import com.zw.rule.mapper.transaction.TransactionDetailsMapper;
import com.zw.rule.mapper.transaction.TransactionExceptionMapper;
import com.zw.rule.mybatis.ParamFilter;
import com.zw.rule.repayment.po.Repayment;
import com.zw.rule.transaction.po.TransactionDetails;
import com.zw.rule.transaction.po.TransactionException;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrator on 2018/1/3.
 */
@Service
public class GoldPaymentServiceImpl implements GoldPaymentService {

    @Resource
    private OrderMapper orderMapper;
    @Resource
    private TransactionDetailsMapper transactionDetailsMapper;
    @Resource
    private TransactionExceptionMapper transactionExceptionMapper;
    @Autowired
    private RepaymentMapper repaymentMapper;
    @Override
    public List queryOrders(ParamFilter paramFilter) throws Exception {
        List<Order> orderList=orderMapper.getGoldPayment(paramFilter.getParam());
        List<Order> odrList=new ArrayList<>();
        for (int i=0;i<orderList.size();i++){
            Order order=orderList.get(i);
            Map<String,Object> map=new HashedMap();
            map.put("orderId",order.getId());
            TransactionDetails transactionDetails=transactionDetailsMapper.getTransactionLatest(map);
            if (transactionDetails==null){
                //没有扣款数据
                order.setRepaymentState("3");
            }
            else{
                order.setRepaymentState(transactionDetails.getState());
            }
            odrList.add(order);
        }
        return odrList;
    }

    @Override
    public  String costDebit(Map map){
        List<Order> orders=orderMapper.getOrderDel(map.get("orderId").toString());
        String debitTransNo="";
        Map<String,Object> debitResultMap=new HashedMap();
        String debitReturnCode="";
        String returnMsg="";
        Date date=new Date();
        DateFormat format=new SimpleDateFormat("yyyyMMddHHmmss");
        Random random=new Random();

        if (orders.size()>0){
            TransactionDetails transactionDetails=transactionDetailsMapper.getTransactionLatest(map);
            if (!"2".equals(transactionDetails.getState())){
                return "数据错误！请刷新页面";
            }
            else{
                try{
                    double  repaymentAmount=0.0;
                    double  penalty=0.0;
                    double  amount=0.0;
                    //黄金只有一期
                    Repayment repayment=repaymentMapper.getList(map).get(0);
                    if (repayment==null){
                        return "没有对应的还款计划！";
                    }
                    //1未还，2已还，3逾期 4.还款确认中'
                    if("2".equals(repayment.getState())){
                        return "该笔还款计划已还款！";
                    }else if ("4".equals(repayment.getState())){
                        return "该笔还款计划确认中！";
                    }
                    else if ("1".equals(repayment.getState())){
                        amount=Double.valueOf(repayment.getRepaymentAmount());
                    }else if ("3".equals(repayment.getState())){
                        repaymentAmount=Double.valueOf(repayment.getRepaymentAmount());
                        penalty=Double.valueOf(repayment.getPenalty());
                        amount=repaymentAmount+penalty;
                    }
                    map.put("debitAmount",amount);
//                    /******************************************************************************测试写死*/
//                    map.put("debitAmount","0.01");
                    //获取开户信息
                    List<Map> account=orderMapper.getAccount(map);
                    if (account.size()>0){
                        map.put("user_id",account.get(0).get("user_id"));
                        map.put("count_name",account.get(0).get("count_name"));
                        map.put("bank_card",account.get(0).get("bank_card"));
                        map.put("account_bank",account.get(0).get("account_bank"));
                        map.put("account_branch_bank",account.get(0).get("account_branch_bank"));
                        map.put("account_bank_id",account.get(0).get("account_bank_id"));
                        map.put("account_province",account.get(0).get("account_province"));
                        map.put("account_city",account.get(0).get("account_city"));
                        map.put("card_num",account.get(0).get("card_num"));
                        map.put("tel",account.get(0).get("tel"));
                    }
                    String randomStr="";
                    randomStr+=random.nextInt(10);
                    randomStr+=random.nextInt(10);
                    randomStr+=random.nextInt(10);
                    debitTransNo="TID"+System.currentTimeMillis();
                    //debitTransNo=map.get("memberId").toString()+ DateUtils.getDateString(new Date())+randomStr;
                    map.put("debitTransNo",debitTransNo);
                    map.put("debitTransNo",debitTransNo);
                    debitResultMap = BaoFuApi.ApiPay(map);
                    debitReturnCode=debitResultMap.get("returnCode").toString();
                    returnMsg=debitResultMap.get("returnMsg").toString();
                    TransactionDetails transaction=new  TransactionDetails();
                    transaction.setId(UUID.randomUUID().toString());
                    transaction.setOrderId(map.get("orderId").toString());
                    transaction.setType("2");
                    transaction.setBaofuCode(debitReturnCode);
                    transaction.setBaofuMag(debitResultMap.get("returnMsg").toString());
                    transaction.setBatchNo(map.get("debitTransNo").toString());
                    transaction.setAmount(map.get("debitAmount").toString());
                    transaction.setCreatTime(format.format(date));
                    transaction.setBankName(account.get(0).get("account_bank").toString());
                    transaction.setBankCard(account.get(0).get("bank_card").toString());
                    Order order=new Order();
                    order.setId(map.get("orderId").toString());
                    //0000 交易成功   BF00114 订单已支付成功，请勿重复支付
                    if("0000".equals(debitReturnCode)||"BF00114".equals(debitReturnCode)){
                        transaction.setState("1");
                        order.setState("9");
                        //更新订单表
                        orderMapper.updateState(order);
                        repayment.setState("2");
                        repaymentMapper.updtaeRepayment(repayment);
                        returnMsg="扣款成功！";
                    }else if("BF00100".equals(debitReturnCode)||"BF00112".equals(debitReturnCode)||"BF00113".equals(debitReturnCode)||"BF00115".equals(debitReturnCode)||"BF00144".equals(debitReturnCode)||"BF00202 ".equals(debitReturnCode)) {
                        transaction.setState("0");
                        //order.setFeeState("2");
                        returnMsg="扣款申请已提交，正在确认结果！";
                    }else {
                        transaction.setState("2");

                        //order.setFeeState("3");
                    }
                    //插入交易明细表
                    transactionDetailsMapper.insert(transaction);

                }catch (Exception e) {
                    TransactionException ransactionException=new TransactionException();
                    ransactionException.setId(UUID.randomUUID().toString());
                    ransactionException.setOrderId(map.get("orderId").toString());
                    ransactionException.setType("2");
                    ransactionException.setBatchNo(map.get("debitTransNo").toString());
                    ransactionException.setAmount(map.get("debitAmount").toString());
                    ransactionException.setCreatTime(format.format(date));
                    ransactionException.setState("0");
                    // 异常处理  插入异常明细
                    transactionExceptionMapper.insert(ransactionException);
//                    TransactionDetails transaction=new  TransactionDetails();
//                    transaction.setId(UUID.randomUUID().toString());
//                    transaction.setOrderId(map.get("orderId").toString());
//                    transaction.setType("2");
//                    transaction.setBaofuCode(debitReturnCode);
//                    transaction.setBaofuMag(debitResultMap.get("returnMsg").toString());
//                    transaction.setBatchNo(map.get("debitTransNo").toString());
//                    transaction.setAmount(map.get("debitAmount").toString());
//                    transaction.setCreatTime(format.format(date));
//                    transaction.setState("0");
//                    transactionDetailsMapper.insert(transaction);
                    return "扣款申请申请已提交！";
                }
                return  returnMsg;
            }
        }else {
            return "订单不存在！";
        }
    }

    @Override
    public List<TransactionDetails> transactionDetailsList(Map map){
        return  transactionDetailsMapper.getTransactionList(map);
    }
}
