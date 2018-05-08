package com.zw.rule.repayment.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.zw.base.util.ArithUtil;
import com.zw.base.util.DateUtils;
import com.zw.base.util.MsgConsts;
import com.zw.rule.api.BaoFuApi;
import com.zw.rule.customer.po.Order;
import com.zw.rule.loan.po.Loan;
import com.zw.rule.mapper.customer.OrderMapper;
import com.zw.rule.mapper.loan.LoanMapper;
import com.zw.rule.mapper.repayment.AccDerateManageMapper;
import com.zw.rule.mapper.repayment.CrmPaycontrolMapper;
import com.zw.rule.mapper.repayment.CrmPayrecoderMapper;
import com.zw.rule.mapper.repayment.RepaymentMapper;
import com.zw.rule.mapper.servicePackage.ServicePackageOrderMapper;
import com.zw.rule.mapper.system.SysDepartmentMapper;
import com.zw.rule.mapper.transaction.TransactionDetailsMapper;
import com.zw.rule.mapper.transaction.TransactionExceptionMapper;
import com.zw.rule.repayment.po.MagRepayment;
import com.zw.rule.repayment.po.Repayment;
import com.zw.rule.repayment.service.RepaymentService;
import com.zw.rule.repayment.service.SolvePayMoneyService;
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
 * 功能说明：信贷产品service层
 * @author wangmin
 * 修改人:
 * 修改原因：
 * 修改时间：
 * 修改内容：
 * 创建日期：2017-06-13
 */
@Service
public class RepaymentServiceImpl implements RepaymentService {

    @Resource
    private SysDepartmentMapper sysDepartmentMapper;
    @Autowired
    private RepaymentMapper repaymentMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private SolvePayMoneyService solvePayMoneyService;

    @Autowired
    private AccDerateManageMapper accDerateManageMapper;

    @Autowired
    private CrmPaycontrolMapper crmPaycontrolMapper;

    @Autowired
    private CrmPayrecoderMapper crmPayrecoderMapper;

    @Autowired
    private ServicePackageOrderMapper servicePackageOrderMapper;

    @Resource
    private TransactionDetailsMapper transactionDetailsMapper;
    @Resource
    private TransactionExceptionMapper transactionExceptionMapper;

    @Resource
    private LoanMapper loanMapper;

    @Override
    public List getRepaymentList(Map map) {
        return repaymentMapper.getRepaymentList(map);
    }

    @Override
    public List getRepaymentListSP(Map map) {
        List<MagRepayment> magRepaymentAllList=repaymentMapper.getRepaymentList(map);
        //获取服务包明细
        for (int i=0;i<magRepaymentAllList.size();i++){
            Map<String,Object>mapPackageRepayment=new HashedMap();
            mapPackageRepayment.put("repaymentId",magRepaymentAllList.get(i).getId());
            magRepaymentAllList.get(i).setServicePackageList(servicePackageOrderMapper.getServicePackageRepayment(mapPackageRepayment));
        }
        return repaymentMapper.getRepaymentList(map);
    }

    @Override
    public JSONObject payMoneyPreCheck(JSONObject paramsJson, String type) {
        JSONObject resultJson = new JSONObject();
        String crmOrderId = paramsJson.getString("crmOrderId");
        String info = "";
        //分别的金额
        double baseMoney = paramsJson.get("baseMoney")==null?0d:paramsJson.getDouble("baseMoney");
        double cardMoney = paramsJson.get("cardMoney")==null?0d:paramsJson.getDouble("cardMoney");
        double liveMoney = paramsJson.get("liveMoney")==null?0d:paramsJson.getDouble("liveMoney");
        //总金额
        double totalMoney = ArithUtil.add(new Double[]{baseMoney,cardMoney,liveMoney});
        if(totalMoney<=0){
            resultJson.put(MsgConsts.RESPONSE_CODE, "0");
            info="还款金额应大于零元!";
        }
        //检测是否有有效的申请单如果有话,就不可以进行正常还款和部分逾期还款
        List<Map> accApplyList = accDerateManageMapper.getOtherPay(crmOrderId);
        boolean hasApply = false;
        if(!accApplyList.isEmpty()){
            hasApply = true;
        }
        if("normalPay".equals(type)){ //正常还款一定要找到最新一期,并且金额最大值要小于
            List<Map> list  =  crmPaycontrolMapper.getPayMoney(crmOrderId);
            if(hasApply){
                info="该订单有申请单无法进行本操作!";
            }
            if(list.isEmpty()||list==null){
                info="正常还款未发现有效明细!";
            }else{
                double money = Double.parseDouble(list.get(0).get("money").toString());
                if(totalMoney>money){
                    info="还款金额应小与待还明细总金额!";
                }
            }
        }
        if("partOverPay".equals(type)){ //部分逾期还款
            List<Map> list  = crmPaycontrolMapper.getOverMoney(crmOrderId);
            if(hasApply){
                info="该订单有申请单无法进行本操作!";
            }
            if(list.isEmpty()||list==null){
                info="部分逾期还款未发现有效明细!";
            }else{
                double money = Double.parseDouble(list.get(0).get("money").toString());
                if(totalMoney>money){
                    info="还款金额应小与逾期明细总金额!";
                }
            }
        }
        if("overPay".equals(type)){ //逾期还款
            //开始本期数据的赋值
            List<Map> list  =  crmPaycontrolMapper.getOverRepayMoney(crmOrderId);
            List<Map> list2  =  accDerateManageMapper.getPayApplyInfoFromType(crmOrderId,"1");
            if(list.isEmpty()||list==null){
                info="逾期还款未发现有效明细!";
            }
            if(list2.isEmpty()||list==null){
                info="逾期还款未发现申请单!";
            }else{
                //金额
                double money = Double.parseDouble(list2.get(0).get("totalMoney").toString());
                if(totalMoney != money){
                    info="输入的金额应该等于总金额!";
                }
            }
        }
        if("advanceClearPay".equals(type) || "advanceBuyBackPay".equals(type)){ //提前结清
            String applyType = "";
            if("advanceClearPay".equals(type)){ //提前结清 2
                applyType = "2";
            }
            if("advanceBuyBackPay".equals(type)){ //提前收回 3
                applyType = "3";
            }
            List checkUniqueList1 = crmPayrecoderMapper.checkUniqueList(crmOrderId,DateUtils.getCurrentTime(DateUtils.STYLE_2));
            if(checkUniqueList1.isEmpty()||checkUniqueList1==null){
                info= "当日进行过正常还款,无法操作!";
            }
            List checkUniqueList = crmPayrecoderMapper.getPayList(crmOrderId,"-1");
            if(!checkUniqueList.isEmpty()){
                info= "已经进行过提前结清,无法操作!";
            }
            //检测申请单是否存在
            //检测信息是否存在
            List<Map> list = accDerateManageMapper.getPayApplyInfoFromType(crmOrderId,applyType);
            if(list.isEmpty()||list==null){
                info = "未发现申请信息!";
            }else{
                //申请的总金额  申请单的金额+投资人的违约金-退回服务费
                double money = Double.parseDouble(list.get(0).get("totalMoney").toString());
                //开始进行boc的退回服务费的判断
                /*boolean returnServiceFlag = true;
                String flagSql = "SELECT *from  sys_param where code='erpv3.service.repayment.returnservice.boc.enable' ";
                List<Map> flagList  = queryBySqlReturnMapList(flagSql);
                if(ListTool.isNotNullOrEmpty(flagList)){
                    String flagString = flagList.get(0).get("value").toString();
                    if(flagString.equals("1")){ //说明是需要减免的
                        returnServiceFlag = true;
                    }
                    if(flagString.equals("0")){ //说明不需要减免
                        returnServiceFlag = false;
                    }
                }*/
                //获得违约金和退回的服务费 -- >>投资人的暂时不知道怎么处理
                Order co = null;
                co = orderMapper.selectByPrimaryKey(crmOrderId);
                JSONObject advanceInfo = solvePayMoneyService.getAdvanceInfo(paramsJson.getString("crmOrderId"));
                /*if("2".equals(co.getClearingChannel())){ //如果是boc的话,总金额应该是
                    money = ArithUtil.add(money,advanceInfo.getDouble("touAdvanceMoney"));
                    if(returnServiceFlag){ //需要减去退回服务费
                        money = ArithUtil.sub(money,advanceInfo.getDouble("returnServiceMoney"));
                    }
                }*/
                //if("1".equals(co.getClearingChannel())){
                money = ArithUtil.add(money,advanceInfo.getDouble("touAdvanceMoney"));
                money = ArithUtil.sub(money,advanceInfo.getDouble("returnServiceMoney"));
                //}
                //金额
                if(totalMoney != money){
                    info="输入的金额应该等于总金额!";
                }
            }
        }
        if("".equals(info)){
            resultJson.put(MsgConsts.RESPONSE_CODE, "1");
        }else{
            resultJson.put(MsgConsts.RESPONSE_INFO, info);
        }
        return resultJson;
    }
    @Override
    public Boolean updtaeRepayByorderId(Map map){
        repaymentMapper.updtaeRepayByorderId(map);
        return true;
    }

    //获取计划表扣款明细集合
    @Override
    public List<Map> getPaymentRepaymentLis(Map map){
//        if ("admin".equals(map.get("account"))){
//            map.put("admin",map.get("account"));
//        }else {
//            SysDepartment sysDepartment=sysDepartmentMapper.findById((Long) map.get("orgid"));
//            //Pid=0是总公司
//            if (sysDepartment.getPid()==0){
//                map.put("headquarters",sysDepartment.getId());
//            }else {
//                map.put("branch",sysDepartment.getId());
//            }
//        }
        return repaymentMapper.getPaymentRepaymentLis(map);
    }

    @Override
    public List<TransactionDetails> transactionDetailsList(Map map){
        return  transactionDetailsMapper.getTransactionList(map);
    }

    @Override
    public  String costDebit(Map map){
        Repayment repayment=repaymentMapper.getListSP(map);
        List<Order> orders=orderMapper.getOrderDel(repayment.getOrderId());
        String debitTransNo="";
        Map<String,Object> debitResultMap=new HashedMap();
        String debitReturnCode="";
        String returnMsg="";
        Date date=new Date();
        DateFormat format=new SimpleDateFormat("yyyyMMddHHmmss");
        Random random=new Random();

        Map transactionMap=new HashedMap();
        transactionMap.put("repaymentId",repayment.getId());
        if (orders.size()>0){
            TransactionDetails transactionDetails=transactionDetailsMapper.getTransactionLatest(transactionMap);
            if (!"2".equals(transactionDetails.getState())){
                return "数据错误！请刷新页面";
            }
            else {
                try{
                    Loan loan=loanMapper.getLoanByOrderId(orders.get(0).getId());
                    double  repaymentAmount=0.0;
                    double  penalty=0.0;
                    double  amount=0.0;
                    double  servicePackageAmount=0.0;
                    //商品贷
                    if ("1".equals(loan.getSettleState())||"2".equals(loan.getSettleState())){
                        return "已经进行提前结清操作，不能进行扣款！";
                    }
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
                        servicePackageAmount=Double.valueOf(repayment.getServicePackageAmount());
                        amount=Double.valueOf(repayment.getRepaymentAmount())+servicePackageAmount;
                    }else if ("3".equals(repayment.getState())){
                        repaymentAmount=Double.valueOf(repayment.getRepaymentAmount());
                        penalty=Double.valueOf(repayment.getPenalty())+Double.valueOf(repayment.getDefaultInterest());
                        servicePackageAmount=Double.valueOf(repayment.getServicePackageAmount());
                        amount=repaymentAmount+penalty+servicePackageAmount;
                    }
                    map.put("debitAmount",amount);
                    /******************************************************************************测试写死*/
                    map.put("debitAmount","0.01");
                    //获取开户信息
                    Map accountMap=new HashedMap();
                    accountMap.put("orderId",orders.get(0).getId());
                    List<Map> account=orderMapper.getAccount(accountMap);
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
                    }else {
                        return "没有找到开户信息！";
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
                    transaction.setOrderId(repayment.getOrderId());
                    transaction.setType("2");
                    transaction.setRepaymentId(repayment.getId());
                    transaction.setBaofuCode(debitReturnCode);
                    transaction.setBaofuMag(debitResultMap.get("returnMsg").toString());
                    transaction.setBatchNo(map.get("debitTransNo").toString());
                    transaction.setAmount(map.get("debitAmount").toString());
                    transaction.setCreatTime(format.format(date));
                    transaction.setBankName(account.get(0).get("account_bank").toString());
                    transaction.setBankCard(account.get(0).get("bank_card").toString());
                    //0000 交易成功   BF00114 订单已支付成功，请勿重复支付
                    if("0000".equals(debitReturnCode)||"BF00114".equals(debitReturnCode)){
                        transaction.setState("1");
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
                    ransactionException.setOrderId(repayment.getOrderId());
                    ransactionException.setRepaymentId(repayment.getId());
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
}
