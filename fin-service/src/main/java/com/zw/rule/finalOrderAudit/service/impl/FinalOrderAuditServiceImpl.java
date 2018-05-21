package com.zw.rule.finalOrderAudit.service.impl;

import com.zw.baoFu.base.TransContent;
import com.zw.baoFu.base.request.TransReqBF0040001;
import com.zw.base.util.DateUtils;
import com.zw.base.util.HttpClientUtil;
import com.zw.base.util.TraceLoggerUtil;
import com.zw.rule.api.BaoFuApi;
import com.zw.rule.api.WithdrawalsApi;
import com.zw.rule.approveRecord.po.OrderOperationRecord;
import com.zw.rule.customer.po.AppUser;
import com.zw.rule.customer.po.CustomerImage;
import com.zw.rule.customer.po.Order;
import com.zw.rule.customer.po.Withdrawals;
import com.zw.rule.finalOrderAudit.service.IFinalOrderAuditService;
import com.zw.rule.mapper.approveRecord.ProcessApproveRecordMapper;
import com.zw.rule.mapper.customer.*;
import com.zw.rule.mapper.loanmange.LoanManageMapper;
import com.zw.rule.mapper.merchant.MerchantListMapper;
import com.zw.rule.mapper.orderLog.OrderLogMapper;
import com.zw.rule.mapper.repayment.RepaymentMapper;
import com.zw.rule.mapper.system.SysDepartmentMapper;
import com.zw.rule.mapper.transaction.TransactionDetailsMapper;
import com.zw.rule.mapper.transaction.TransactionExceptionMapper;
import com.zw.rule.merchant.MerchantAccountRel;
import com.zw.rule.mybatis.ParamFilter;
import com.zw.rule.orderLog.po.MagOrderLogs;
import com.zw.rule.transaction.po.TransactionDetails;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.zw.base.util.DateUtils.getDateString;

/**
 * 功能说明：订单终审service层
 * @author wangmin
 * 修改人:
 * 修改原因：
 * 修改时间：
 * 修改内容：
 * 创建日期：2017-06-23
 */
@Service
public class FinalOrderAuditServiceImpl implements IFinalOrderAuditService {

    @Resource
    private OrderMapper orderMapper;

    @Resource
    private AppUserMapper userMapper;

    @Resource
    private CustomerInvestigationMapper customerInvestigationMapper;

    @Resource
    private CustomerImageMapper customerImageMapper;

    @Resource
    private ProcessApproveRecordMapper processApproveRecordMapper;

    @Resource
    private CustomerRespMapper customerRespMapper;

    @Resource
    private CustomerExamineMapper customerExamineMapper;

    @Resource
    private LoanManageMapper
            loanManageMapper;

    @Resource
    private RepaymentMapper repaymentMapper;

    @Resource
    private TransactionDetailsMapper transactionDetailsMapper;

    @Resource
    private TransactionExceptionMapper transactionExceptionMapper;

    @Resource
    private SysDepartmentMapper sysDepartmentMapper;

    @Resource
    private MerchantListMapper merchantListMapper;

    @Resource
    private OrderLogMapper orderLogMapper;

    private static TransContent<TransReqBF0040001> transContent=null;
    /**
     * 功能说明：得到产品系列列表
     * huangyuanlong  2016-6-27
     * @return
     */
    public List getFinalOrderList(String orderId) throws Exception{
        //返回的json 数据
        List list =new ArrayList();

        return  list;
    }

    @Override
    public List getOrderList(ParamFilter paramFilter) {
        return orderMapper.getOrders(paramFilter);
    }

    @Override
    public boolean updateReceiveId(String orderId,String userId) {
        String date = getDateString(new Date());
        int num = orderMapper.updateFinalReceiveId(orderId,userId,date);
        return num>0;
    }

    @Override
    public int auditOrder(String orderId,String end_credit,String state) {
        Order order = new Order();
        order.setId(orderId);
        order.setState("6");
        //order.setTache(state);
        order.setReceiveId("");
        //order.setEndCredit(end_credit);
        int num = orderMapper.updateState(order);
        return num;
    }

    @Override
    public Map getUserInfoByorderId(String orderId) {
        return orderMapper.getUserInfoByorderId(orderId);
    }

    @Override
    public List decorationList(String orderId) throws Exception {
        return customerRespMapper.findById(orderId);
    }

    @Override
    public List examineList(String orderId) throws Exception {
        return customerExamineMapper.findByOrderId(orderId);
    }

    @Override
    public List investiList(String orderId) throws Exception {
        return customerInvestigationMapper.findByOrderId(orderId);
    }

    @Override
    public List imageList(String orderId) throws Exception {
        return customerImageMapper.findByOrderId(orderId);
    }

    @Override
    public List approveList(String orderId) throws Exception {
        return processApproveRecordMapper.findByOrderId(orderId);
    }
    @Override
    public boolean saveImg(Map map) throws Exception {
        CustomerImage record = new CustomerImage();
        String id = UUID.randomUUID().toString();
        String creatTime = map.get("creat_time").toString();
        String custId= map.get("customerId").toString();
        String orderId = map.get("orderId").toString();
        String type = map.get("type").toString();
        String businessType = map.get("business_type").toString();
        String src = map.get("src").toString();
        record.setId(id);
        record.setAlterTime(creatTime);
        record.setBusinessType(businessType);
        record.setCustomerId(custId);
        record.setOrderId(orderId);
        record.setType(type);
        record.setSrc(src);
        int num = customerImageMapper.insertSelective(record);
        if(num>0){
            return true;
        }
        return false;
    }

    @Override
    public List queryOrders(Map map) throws Exception {
        return orderMapper.findLoanSincereList(map);
    }

    @Override
    public List queryOrdersGold(Map map) throws Exception {
//        Map<String,Object>map=paramFilter.getParam();
//        if ("admin".equals(account)){
//            map.put("admin",account);
//        }else {
//            SysDepartment sysDepartment=sysDepartmentMapper.findById(orgId);
//            //Pid=0是总公司
//            if (sysDepartment.getPid()==0){
//                map.put("headquarters",sysDepartment.getId());
//            }else {
//                map.put("branch",sysDepartment.getId());
//            }
//        }
        return orderMapper.getWithdrawalsListGold(map);
    }


    @Override
    public List withdrawalList(ParamFilter paramFilter)throws Exception{
        return orderMapper.getOrderWithdrawals(paramFilter.getParam());
    }


    //放款操作
    public String confirmationLoanYibao(Map map)throws Exception{
        if (loanManageMapper.getByPrimaryKey(map).size()>0){
            return "已有提现操作，请勿重复操作！";
        }
        Map<String,Object> mapWithdrawals=new HashedMap();
        //获取提现信息
        mapWithdrawals.put("id",map.get("withdrawalsId").toString());
        mapWithdrawals.put("state","0");
        List<Withdrawals> withdrawalsList=orderMapper.getOrderWithdrawals(mapWithdrawals);
        if (withdrawalsList.size()>0){
            String account=withdrawalsList.get(0).getWithdrawalAmount();
            account="0.01";/******************************************************************************测试写死*/
            map.put("account",account);
            map.put("orderId",withdrawalsList.get(0).getOrderId());
            map.put("withdrawalsType",withdrawalsList.get(0).getType());
            if("2".equals(withdrawalsList.get(0).getState())){
                return "放款申请已提交，请稍后查看！";
            }
        }else {
            return "数据错误，请刷新页面重新操作！";
        }
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
        List<Order> orders=orderMapper.getOrderDel(map.get("orderId").toString());
        if(orders.size()>0){
            double  manageFee=Double.valueOf(orders.get(0).getManageFee());
            double  quickTrialFee=Double.valueOf(orders.get(0).getQuickTrialFee());
            double  amount=manageFee+quickTrialFee;
            map.put("amount",amount);
        }
        String responseMsg="提现提交";
        Map resultMap=new HashMap();
        Map<String,Object> withdrawal=new HashedMap();
        //调放款接口
        Map<String,Object> returnMap=WithdrawalsApi.transferParamResolver(map);
        withdrawal.put("state","2");
        withdrawal.put("id",map.get("withdrawalsId").toString());
        //修改提现表
        orderMapper.updateWithdrawals(withdrawal);
        String retCValue=(String)returnMap.get("retCValue");
        String retCode=(String)returnMap.get("ret_Code");
        String bankStatus=(String)returnMap.get("bank_Status");
        String errorMsg=(String)returnMap.get("error_Msg");
        String batch_no=(String)returnMap.get("batch_No");
        if("1".equals(retCValue)){
            //shibai
            if("0027".equals(retCode)||"0028".equals(retCode) ){
                resultMap.put("flag",true);
                resultMap.put("msg","您的提现申请失败请重新发起！");
                withdrawal.put("valBatchNo",batch_no);
                withdrawal.put("state","3");
                withdrawal.put("id",map.get("withdrawalsId").toString());
                //修改提现表
                orderMapper.updateWithdrawals(withdrawal);
            }else if ("0025".equals(retCode)){
                if ("U".equals(bankStatus)){
                    resultMap.put("flag",true);
                    resultMap.put("msg","您的提现申请失败请重新发起！");
                    withdrawal.put("valBatchNo",batch_no);
                    withdrawal.put("state","3");
                    withdrawal.put("id",map.get("withdrawalsId").toString());
                    //修改提现表
                    orderMapper.updateWithdrawals(withdrawal);
                }else {
                resultMap.put("flag",true);
                resultMap.put("msg","您的提现申请已提交成功,请耐心等待银行处理");
                withdrawal.put("valBatchNo",batch_no);
                withdrawal.put("state","2");
                withdrawal.put("id",map.get("withdrawalsId").toString());
                //修改提现表
                orderMapper.updateWithdrawals(withdrawal);
                }
            }else if ("0030".equals(retCode)){

            }
        }else if ("0044".equals(retCValue)){
            /******************************************************************************测试使用*******************************************************************************/
            resultMap.put("flag",true);
            resultMap.put("msg","您的提现申请已提交成功,请耐心等待银行处理");
            withdrawal.put("valBatchNo",batch_no);
            withdrawal.put("state","2");
            withdrawal.put("id",map.get("withdrawalsId").toString());
            //修改提现表
            orderMapper.updateWithdrawals(withdrawal);
            Map<String,Object> result=new HashedMap();
            result.put("orderId",map.get("orderId").toString());
            result.put("status","S");
            result.put("batch_No",batch_no);
            //模仿提现回调
            ybWithdrawalCallbackInfo(result);
            result.put("status","PAY_SUCCESS");
            result.put("requestno",requestno);
            //模仿扣款回调
            bindCardPayCallback(result);
        }//余额不足
        else if ("0046".equals(retCValue)){
            // TODO: 2017/12/4  余额不足
        }else  if ("999".equals(retCValue)){
            // TODO: 2017/12/4  系统异常
        }
        else{
            resultMap.put("flag",false);
            resultMap.put("msg",errorMsg);
            return errorMsg;
        }
        return responseMsg;
    }
    /***********************************requestno测试使用************************************************************/
    private  String requestno="";
    //放款回调
    @Override
    public void ybWithdrawalCallbackInfo(Map map)throws Exception{
        /********************************************测试注释**************************************************/
        Map result=map;
        //Map result=WithdrawalsApi.ybWithdrawalCallbackInfo(map);
        Map<String,Object> withdrawal=new HashedMap();
        //放款成功
        if ("S".equals(result.get("status").toString())){
            withdrawal.put("batchNo",result.get("batch_No"));
            withdrawal.put("state","1");
            //修改提现表
            orderMapper.updateWithdrawals(withdrawal);
            try {
                Map<String,Object> withdrawalsMap=new HashedMap();
                withdrawalsMap.put("batch_no",result.get("batch_No"));
                List<Withdrawals> withdrawalsList=orderMapper.getOrderWithdrawals(withdrawalsMap);
                //借款进行费用扣款
                if ("1".equals(withdrawalsList.get(0).getType())||"2".equals(withdrawalsList.get(0).getType())){
                    //插入放款表 还款计划表
                    //addLoan(result.get("orderId").toString(),result.get("batch_No").toString());
                    //获取开户信息
                    List<Map> account=orderMapper.getAccount(map);
                    List<Order> orders=orderMapper.getOrderDel(map.get("orderId").toString());
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
                    if (orders.size()>0) {
                        double  quickTrialFee=Double.valueOf(orders.get(0).getQuickTrialFee());
                        double  manageFee=Double.valueOf(orders.get(0).getManageFee());
                        double  total=quickTrialFee+manageFee;
                        DecimalFormat df  = new DecimalFormat("######0.00");
                        map.put("amount", df.format(total));
                    }

                    try {
                        //发起扣款请求
                        /**********************************************************测试注释**************************************************************/
                        //Map returnMap=WithdrawalsApi.bindCardPayQuery(map);
                        Order order=new Order();
                        order.setFeeState("2");
                        order.setState("8");
                        requestno=UUID.randomUUID().toString();
                        order.setRequestno(requestno);
                        //order.setRequestno(returnMap.get("requestno").toString());
                        order.setId(result.get("orderId").toString());
                        orderMapper.updateState(order);
                    }catch (Exception e){
                        Order order=new Order();
                        order.setState("8");
                        order.setId(result.get("orderId").toString());
                        orderMapper.updateState(order);
                    }
                }
            }catch (Exception e){

            }
        }else {
            withdrawal.put("batchNo",result.get("batch_No"));
            withdrawal.put("state","3");
            //修改提现表
            orderMapper.updateWithdrawals(withdrawal);
        }
    }

    //扣款回调
    @Override
    public void bindCardPayCallback(Map map){
        try {
            /*****************************************************测试注释********************************************************/
            Map result=map;
            //Map result=WithdrawalsApi.bindCardPayCallback(map);
            TraceLoggerUtil.info("扣款回调参数--->" + result.toString());
            if (result==null){
            }else {
                if("PAY_SUCCESS".equals(result.get("status"))){
                    Order order=new Order();
                    order.setFeeState("1");
                    order.setValRequestno(result.get("requestno").toString());
                    orderMapper.updateState(order);
                }else{
                    Order order=new Order();
                    order.setFeeState("3");
                    order.setValRequestno(result.get("requestno").toString());
                    orderMapper.updateState(order);
                }
            }
        }catch (Exception e){

        }
    }

    //插入放款表 还款计划表
    private void  addLoan(Map<String,Object> map){
        String returnCode=map.get("returnCode").toString();
        Date date=new Date();
        DateFormat format=new SimpleDateFormat("yyyyMMddHHmmss");
        Map<String,Object> withdrawalsMap=new HashedMap();
        withdrawalsMap.put("id",map.get("withdrawalsId").toString());

        //插入交易明细表transactionDetails
        TransactionDetails transactionDetail=new  TransactionDetails();
        transactionDetail.setId(UUID.randomUUID().toString());
        transactionDetail.setOrderId(map.get("orderId").toString());
        transactionDetail.setType("0");
        transactionDetail.setBaofuCode(returnCode);
        transactionDetail.setBaofuMag(map.get("returnMsg").toString());
        transactionDetail.setBatchNo(map.get("transNo").toString());
        transactionDetail.setAmount(map.get("amount").toString());
        transactionDetail.setCreatTime(format.format(date));

        if ("0000".equals(returnCode)||"0300".equals(returnCode)||"0401".equals(returnCode)||"0999".equals(returnCode)){
            withdrawalsMap.put("state","2");
            withdrawalsMap.put("valBatchNo",map.get("transNo").toString());
            withdrawalsMap.put("bank_card",map.get("bank_card").toString());
            withdrawalsMap.put("account_bank",map.get("account_bank").toString());
            //更新提现表
           orderMapper.updateWithdrawals(withdrawalsMap);
            //插入交易明细表transactionDetails
            transactionDetail.setState("0");
            transactionDetailsMapper.insert(transactionDetail);
            return;
        }else if("200".equals(returnCode)) {

             try{
                 HttpClientUtil.getInstance().sendHttpGet(map.get("appHGUrl") + "orderMessage/orderPushMessage?state=8&orderId="+map.get("orderId"));
             }catch (Exception e){

             }
            //更新提现表
            withdrawalsMap.put("state","1");
            withdrawalsMap.put("alterTime",DateUtils.getDateString(new Date()));
            withdrawalsMap.put("valBatchNo",map.get("transNo").toString());

            withdrawalsMap.put("bank_card",map.get("bank_card").toString());
            withdrawalsMap.put("account_bank",map.get("account_bank").toString());
            orderMapper.updateWithdrawals(withdrawalsMap);
            //插入交易明细表transactionDetails
            transactionDetail.setState("1");
            transactionDetailsMapper.insert(transactionDetail);


            Order order=new Order();
            order.setId( map.get("orderId").toString());
            order.setRebatesState("2");
            //更新订单表
            orderMapper.updateState(order);
        }
        List<Withdrawals> withdrawalsList=orderMapper.getOrderWithdrawals(withdrawalsMap);
        orderMapper.updateWithdrawals(withdrawalsMap);

        List<Order> orders=orderMapper.getOrderDel(map.get("orderId").toString());
        //黄金在签约时已经生成放款与还款计划表
        if ("1".equals(orders.get(0).getOrderType())){
            return;
        }
//        String loanId=UUID.randomUUID().toString();
//        //插入放款表数据
//        LoanManage loanManage=new LoanManage();
//        loanManage.setId(loanId);
//        loanManage.setWithdrawalsId(withdrawalsList.get(0).getId());
//        loanManage.setUserId(orders.get(0).getUserId());
//        loanManage.setAmount(withdrawalsList.get(0).getWithdrawalAmount());
//        loanManage.setState("1");
//        loanManage.setCreatetime(format.format(date));
//        loanManage.setUpdatetime(format.format(date));
//        loanManage.setOrderId(orders.get(0).getId());
//        loanManage.setLoanTime(format.format(date));
//        loanManage.setManagefee(orders.get(0).getManageFee());
//        loanManage.setCustId(orders.get(0).getCustomerId());
//        loanManage.setQuicktrialfee(orders.get(0).getQuickTrialFee());
//        double  fee=Double.valueOf(orders.get(0).getFee());
//        double  money=0;
//        if ("2".equals(withdrawalsList.get(0).getState())){
//            money=Double.valueOf(withdrawalsList.get(0).getPrincipalAmount());
//        }else {
//            money=Double.valueOf(withdrawalsList.get(0).getWithdrawalAmount());
//        }
//        double  total=fee+money;
//        DecimalFormat df  = new DecimalFormat("######0.00");
//        loanManage.setPayableAmount(df.format(total));
//        loanManage.setExpirationDate(orders.get(0).getRepayDate());
//        loanManage.setDerateAmount("0");
//        loanManage.setAgentAmount("0");
//        loanManage.setPenalty("0");
//        loanManage.setType(withdrawalsList.get(0).getType());
//        loanManageMapper.insert(loanManage);
//
//        //插入还款计划表
//        Repayment repayment=new Repayment();
//        repayment.setId(UUID.randomUUID().toString());
//        repayment.setLoanId(loanId);
//        repayment.setPayCount("1");
//        repayment.setFee(df.format(fee));
//        repayment.setAmount(df.format(money));
//        repayment.setRepaymentAmount(df.format(total));
//        repayment.setOrderId(orders.get(0).getId());
//        repayment.setPayTime(orders.get(0).getRepayDate());
//        repayment.setLoanTime(format.format(date));
//        repayment.setOverdueDays("0");
//        repayment.setPenalty("0");
//        repayment.setCreateTime(format.format(date));
//        repayment.setState("1");
//        repaymentMapper.insertRepayment(repayment);
    }
    //黄金回购放款操作
    @Transactional
    @Override
    public String confirmationLoan(Map map) throws Exception {
        if (loanManageMapper.getByPrimaryKey(map).size()>0){
            return "已有提现操作，请勿重复操作！";
        }
        Map<String,Object> mapWithdrawals=new HashedMap();
        //获取提现信息
        mapWithdrawals.put("id",map.get("withdrawalsId").toString());
        mapWithdrawals.put("state1","0");
        List<Withdrawals> withdrawalsList=orderMapper.getOrderWithdrawals(mapWithdrawals);
        if (withdrawalsList.size()>0){
            String amount=withdrawalsList.get(0).getWithdrawalAmount();
            amount="0.01";/******************************************************************************测试写死*/
            map.put("amount",amount);
            map.put("orderId",withdrawalsList.get(0).getOrderId());
            map.put("withdrawalsType",withdrawalsList.get(0).getType());
            if("2".equals(withdrawalsList.get(0).getState())){
                return "放款申请确认中，请稍后查看！";
            }
        }else {
            return "数据错误，请刷新页面重新操作！";
        }
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
        }else {
            return "客户没有开户数据！";
        }
        Random random=new Random();
        List<Order> orders=orderMapper.getOrderDel(map.get("orderId").toString());
//        if(orders.size()>0){
//            double  manageFee=Double.valueOf(orders.get(0).getManageFee());
//            double  quickTrialFee=Double.valueOf(orders.get(0).getQuickTrialFee());
//            double  debitAmount=manageFee+quickTrialFee;
////            map.put("debitAmount",debitAmount);
//            /******************************************************************************测试写死*/
//            map.put("debitAmount","0.01");
//        }
        Map<String,Object> resultMap=new HashMap<>();
        Map<String,Object> debitResultMap=new HashedMap();
        Map<String,Object> queryResultMap=new HashedMap();
        Date date=new Date();
        DateFormat format=new SimpleDateFormat("yyyyMMddHHmmss");
        String transNo="";//放款流水号
        String debitTransNo="";//代扣流水号

        /********************************放款操作**************************************/
        try{
            String randomStr="";
            randomStr+=random.nextInt(10);
            randomStr+=random.nextInt(10);
            randomStr+=random.nextInt(10);
            //唯一流水号
            transNo="TID"+System.currentTimeMillis();
            //transNo=map.get("memberId").toString()+ DateUtils.getDateString(new Date())+randomStr;
            map.put("transNo",transNo);
            resultMap=BaoFuApi.confirmationLoan(map);
        }catch (Exception e){
            TraceLoggerUtil.info(e.getMessage());
            try{
                //调查询接口
                queryResultMap=BaoFuApi.confirmationLoanQuery(map);
                resultMap.put("returnCode",queryResultMap.get("returnCode").toString());
                resultMap.put("returnMsg",queryResultMap.get("returnMsg").toString());
            }catch (Exception ex){
                TraceLoggerUtil.info(ex.getMessage());
                map.put("returnCode","0300");
                map.put("returnMsg","未知");
                addLoan(map);
                return "放款确认中,请稍后查看！";
            }
        }
        String returnCode=resultMap.get("returnCode").toString();//状态码
        String returnMsg=resultMap.get("returnMsg").toString();//消息
        map.put("returnCode",returnCode);
        map.put("returnMsg",returnMsg);
        map.put("transNo",transNo);
        //插入放款表  还款计划表
        addLoan(map);

        /****************************************************放款成功自动发起扣款操作提现类型0 是红包提现不需要生成计划表************************************************************/
//         if("200".equals(returnCode)&&(!"0".equals(withdrawalsList.get(0).getType()))) {
//             try{
//                 HttpClientUtil.getInstance().sendHttpGet(map.get("appHGUrl") + "orderMessage/orderPushMessage?state=8&orderId="+map.get("orderId"));
//             }catch (Exception e){
//
//             }
//            try{
//                String randomStr="";
//                randomStr+=random.nextInt(10);
//                randomStr+=random.nextInt(10);
//                randomStr+=random.nextInt(10);
//                debitTransNo=map.get("memberId").toString()+ DateUtils.getDateString(new Date())+randomStr;
//                map.put("debitTransNo",debitTransNo);
//                debitResultMap = BaoFuApi.ApiPay(map);
//                String debitReturnCode=debitResultMap.get("returnCode").toString();
//                TransactionDetails transactionDetail=new  TransactionDetails();
//                transactionDetail.setId(UUID.randomUUID().toString());
//                transactionDetail.setOrderId(map.get("orderId").toString());
//                transactionDetail.setType("1");
//                transactionDetail.setBaofuCode(debitReturnCode);
//                transactionDetail.setBaofuMag(debitResultMap.get("returnMsg").toString());
//                transactionDetail.setBatchNo(map.get("debitTransNo").toString());
//                transactionDetail.setAmount(map.get("debitAmount").toString());
//                transactionDetail.setCreatTime(format.format(date));
//
//                Order order=new Order();
//                order.setId( map.get("orderId").toString());
//                //0000 交易成功   BF00114 订单已支付成功，请勿重复支付
//                if("0000".equals(debitReturnCode)||"BF00114".equals(debitReturnCode)){
//                    transactionDetail.setState("1");
//                    order.setFeeState("1");
//                }else if("BF00100".equals(debitReturnCode)||"BF00112".equals(debitReturnCode)||"BF00113".equals(debitReturnCode)||"BF00115".equals(debitReturnCode)||"BF00144".equals(debitReturnCode)||"BF00202 ".equals(debitReturnCode)) {
//                    transactionDetail.setState("0");
//                    order.setFeeState("2");
//                }else {
//                    transactionDetail.setState("2");
//                    order.setFeeState("3");
//                }
//                //更新订单表
//                orderMapper.updateState(order);
//                //插入交易明细表
//                transactionDetailsMapper.insert(transactionDetail);
//            }catch (Exception e) {
////                TransactionException ransactionException=new TransactionException();
////                ransactionException.setId(UUID.randomUUID().toString());
////                ransactionException.setOrderId(map.get("orderId").toString());
////                ransactionException.setType("1");
////                ransactionException.setBatchNo(map.get("debitTransNo").toString());
////                ransactionException.setAmount(map.get("debitAmount").toString());
////                ransactionException.setCreatTime(format.format(date));
////                ransactionException.setState("0");
////                // 异常处理  插入异常明细
////                transactionExceptionMapper.insert(ransactionException);
//                TransactionDetails transactionDetail=new  TransactionDetails();
//                transactionDetail.setId(UUID.randomUUID().toString());
//                transactionDetail.setOrderId(map.get("orderId").toString());
//                transactionDetail.setType("1");
//                transactionDetail.setBaofuCode("-1");
//                transactionDetail.setBaofuMag("异常");
//                transactionDetail.setBatchNo(map.get("debitTransNo").toString());
//                transactionDetail.setAmount(map.get("debitAmount").toString());
//                transactionDetail.setCreatTime(format.format(date));
//                transactionDetail.setState("0");
//
//                Order order=new Order();
//                order.setId( map.get("orderId").toString());
//                order.setFeeState("2");
//
//                //更新订单表
//                orderMapper.updateState(order);
//                //插入交易明细表
//                transactionDetailsMapper.insert(transactionDetail);
//            }
//        }
        return returnMsg;
    }


    @Override
    @Transactional
    public Boolean confirmationFinal(Map map) throws Exception {
        OrderOperationRecord orderOperationRecord = new OrderOperationRecord();
        String uuid = UUID.randomUUID().toString();
        orderOperationRecord.setId(uuid);
        orderOperationRecord.setOperationTime(DateUtils.getDateString(new Date()));
        orderOperationRecord.setDescription("已放款");
        orderOperationRecord.setOperationNode(5);//放款审核
        orderOperationRecord.setOperationResult(7);//放款
        orderOperationRecord.setEmpId(map.get("handlerId").toString());
        orderOperationRecord.setEmpName(map.get("handlerName").toString());
        orderOperationRecord.setOrderId(map.get("id").toString());//订单id
        orderOperationRecord.setStatus(1);//1有效，0无效
        int num = processApproveRecordMapper.insertOrderOperRecord(orderOperationRecord);
        if (num > 0)
        {
            Order order = orderMapper.selectByPrimaryKey(map.get("id").toString());
            AppUser user = new AppUser();
            user.setId(order.getUserId());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            user.setOrderRefusedTime(sdf.format(new Date()));
            userMapper.updateByPrimaryKeySelective(user);
        }
        return true;
    }

    //商品贷给商户放款(线下放款)
    @Transactional
    public String confirmationMerchantXianXia(Map map) throws Exception {
        String amount="";
        Order order=orderMapper.getOrderDel(map.get("orderId").toString()).get(0);
        if ("2".equals(order.getLoanState())){
            return "放款处理中，请刷新页面！";
        }else if ("3".equals(order.getLoanState())){
            return "放款已完成，请刷新页面！";
        }else if ((!"1".equals(order.getLoanState()))&&(!"4".equals(order.getLoanState()))){
            return "数据错误，请刷新页面！";
        }
        //线下付款不需要有无开户账号判断
//        MerchantAccountRel merchantAccountRel=merchantListMapper.getLoanAccount(order.getMerchantId());
//        //2是对公账号
//        if (merchantAccountRel==null){
//            return "商户没有开户数据！";
//        }
//        map.put("count_name",merchantAccountRel.getName());
//        map.put("bank_card",merchantAccountRel.getAccount());
//        map.put("account_bank",merchantAccountRel.getBankHead());
//        map.put("account_province",merchantAccountRel.getProvinceName());
//        map.put("account_city",merchantAccountRel.getCityName());
//        map.put("card_num",merchantAccountRel.getIdcard());
//        map.put("tel",merchantAccountRel.getTel());
//        map.put("bankBranch",merchantAccountRel.getBankName());
        DecimalFormat df  = new DecimalFormat("######0.00");
        //减去首付款
        map.put("amount",(order.getApplayMoney()));//放款金额
//        map.put("amount","0.01");
//        Map<String,Object> resultMap=new HashMap<>();
//        Map<String,Object> debitResultMap=new HashedMap();
//        Map<String,Object> queryResultMap=new HashedMap();
        Date date=new Date();
        DateFormat format=new SimpleDateFormat("yyyyMMddHHmmss");
        String transNo="";//放款流水号
        String returnCode="";//状态码
        String returnMsg="";//消息
        int k=0;
        try{//添加订单日志
            MagOrderLogs magOrderLogs=new MagOrderLogs();
            magOrderLogs.setId(UUID.randomUUID().toString());
            magOrderLogs.setOperatorId(map.get("handlerId").toString());
            magOrderLogs.setOperatorName(map.get("handlerName").toString());
            magOrderLogs.setOperatorType("1");
            magOrderLogs.setOrderId(order.getId());
            magOrderLogs.setCreatTimeLog(DateUtils.getDateString(new Date()));
            magOrderLogs.setTache("订单线下放款");
            magOrderLogs.setChangeValue(returnMsg);
            magOrderLogs.setState(order.getState());
            magOrderLogs.setCreatTime(DateUtils.getDateString(new Date()));
            magOrderLogs.setAlterTime(DateUtils.getDateString(new Date()));
            k =orderLogMapper.addOrderLogs(magOrderLogs);
        }
        catch (Exception e){}
        Order orderMod=new Order();
        //插入交易明细表transactionDetails
        TransactionDetails transactionDetail=new  TransactionDetails();
        Random random=new Random();
        String randomStr="";
        randomStr+=random.nextInt(10);
        randomStr+=random.nextInt(10);
        randomStr+=random.nextInt(10);
        //唯一流水号
        transNo="TID"+System.currentTimeMillis(); //唯一流水号
//        transNo="TID"+map.get("memberId").toString()+ DateUtils.getDateString(new Date())+randomStr;
        transNo="TID"+map.get("memberId").toString() +System.currentTimeMillis() + randomStr;
        map.put("transNo",transNo);
        transactionDetail.setId(UUID.randomUUID().toString());
        transactionDetail.setOrderId(order.getId());
        transactionDetail.setBatchNo(transNo);//交易唯一流水号
        transactionDetail.setType("0");//0放款；1扣款；2还款
        transactionDetail.setAmount(map.get("amount").toString());//放款金额
        transactionDetail.setCreatTime(format.format(date));
        transactionDetail.setState("1");//线下放款直接设置交易成功
        transactionDetail.setFangkuanType("0");//此字段只用于判断商品贷放款类型：0-线下放款  1-线上放款
        transactionDetail.setSource("1");//0黄金袋，1商品贷
        transactionDetail.setDescribe("商品贷线下放款");
        int j=transactionDetailsMapper.insert(transactionDetail);

        //更新订单
        orderMod.setId(order.getId());
        orderMod.setFeeState("1");
        orderMod.setLoanState("3");//放款状态 1待放款，2放款中，3已放款，4放款失败
        int i=orderMapper.updateState(orderMod);
        if(i+j+k == 3){
            returnMsg="线下放款操作成功";
        }else{
            returnMsg="线下放款操作异常";
        }
        return returnMsg;
    }

    //商品贷给商户放款(线上放款)
    @Transactional
    @Override
    public String confirmationMerchant(Map map) throws Exception {
        String amount="";
        Order order=orderMapper.getOrderDel(map.get("orderId").toString()).get(0);
        if ("2".equals(order.getLoanState())){
            return "放款处理中，请刷新页面！";
        }else if ("3".equals(order.getLoanState())){
            return "放款已完成，请刷新页面！";
        }else if ((!"1".equals(order.getLoanState()))&&(!"4".equals(order.getLoanState()))){
            return "数据错误，请刷新页面！";
        }
        MerchantAccountRel merchantAccountRel=merchantListMapper.getLoanAccount(order.getMerchantId());
        //2是对公账号
        if (merchantAccountRel==null){
            return "商户没有开户数据！";
        }
        map.put("count_name",merchantAccountRel.getName());
        map.put("bank_card",merchantAccountRel.getAccount());
        map.put("account_bank",merchantAccountRel.getBankHead());
        map.put("account_province",merchantAccountRel.getProvinceName());
        map.put("account_city",merchantAccountRel.getCityName());
        map.put("card_num",merchantAccountRel.getIdcard());
        map.put("tel",merchantAccountRel.getTel());
        map.put("bankBranch",merchantAccountRel.getBankName());
        DecimalFormat df  = new DecimalFormat("######0.00");
        //减去首付款
        map.put("amount", (order.getApplayMoney()));
        map.put("amount","0.01");
        Random random=new Random();
        Map<String,Object> resultMap=new HashMap<>();
        Map<String,Object> debitResultMap=new HashedMap();
        Map<String,Object> queryResultMap=new HashedMap();
        Date date=new Date();
        DateFormat format=new SimpleDateFormat("yyyyMMddHHmmss");
        String transNo="";//放款流水号
        String returnCode="";//状态码
        String returnMsg="";//消息
        TransactionDetails transactionDetail=new  TransactionDetails();

        /********************************放款操作**************************************/
        try{
            String randomStr="";
            randomStr+=random.nextInt(10);
            randomStr+=random.nextInt(10);
            randomStr+=random.nextInt(10);
            //唯一流水号
            transNo="TID"+System.currentTimeMillis();
            transNo=map.get("memberId").toString() + System.currentTimeMillis()+randomStr;
            map.put("transNo",transNo);
            resultMap=BaoFuApi.confirmationLoan(map);
            returnCode=resultMap.get("returnCode").toString();//状态码
            returnMsg=resultMap.get("returnMsg").toString();//消息
        }catch (Exception e){
            TraceLoggerUtil.info(e.getMessage());
            try{
                //调查询接口
                queryResultMap=BaoFuApi.confirmationLoanQuery(map);
                resultMap.put("returnCode",queryResultMap.get("returnCode").toString());
                resultMap.put("returnMsg",queryResultMap.get("returnMsg").toString());
            }catch (Exception ex){
                TraceLoggerUtil.info(ex.getMessage());
                returnCode="0300";
                returnMsg="放款确认中,请稍后查看！";
                transactionDetail.setType("0");
            }
        }

        //添加订单日志
        try{
            MagOrderLogs magOrderLogs=new MagOrderLogs();
            magOrderLogs.setId(UUID.randomUUID().toString());
            magOrderLogs.setOperatorId(map.get("handlerId").toString());
            magOrderLogs.setOperatorName(map.get("handlerName").toString());
            magOrderLogs.setOperatorType("1");
            magOrderLogs.setOrderId(order.getId());
            magOrderLogs.setCreatTimeLog(DateUtils.getDateString(new Date()));
            magOrderLogs.setTache("订单放款");
            magOrderLogs.setChangeValue(returnMsg);
            magOrderLogs.setState(order.getState());
            magOrderLogs.setCreatTime(DateUtils.getDateString(new Date()));
            magOrderLogs.setAlterTime(DateUtils.getDateString(new Date()));
            orderLogMapper.addOrderLogs(magOrderLogs);
        }
        catch (Exception e){

        }
        Order orderMod=new Order();
        //插入交易明细表transactionDetails
        transactionDetail.setId(UUID.randomUUID().toString());
        transactionDetail.setOrderId(order.getId());
        transactionDetail.setBaofuCode(returnCode);
        transactionDetail.setBaofuMag(returnMsg);
        transactionDetail.setBatchNo(transNo);
        transactionDetail.setType("0");
        transactionDetail.setAmount(map.get("amount").toString());
        transactionDetail.setCreatTime(format.format(date));
        transactionDetail.setBankName(merchantAccountRel.getBankHead());
        transactionDetail.setBankCard(merchantAccountRel.getAccount());
        transactionDetail.setSource("1");//0黄金贷，1商品贷
        transactionDetail.setDescribe("商品贷线上放款");
//        transactionDetail.setFangkuanType("1");//此字段只用于判断商品贷放款类型：0-线下放款  1-线上放款 默认为线上
        if ("0000".equals(returnCode)||"0300".equals(returnCode)||"0401".equals(returnCode)||"0999".equals(returnCode)){
            //插入交易明细表transactionDetails
            transactionDetail.setState("0");
            transactionDetailsMapper.insert(transactionDetail);
            orderMod.setLoanState("2");
        }else if("200".equals(returnCode)) {
            try{
                HttpClientUtil.getInstance().sendHttpGet(map.get("appHGUrl") + "orderMessage/orderPushMessage?state=8&orderId="+map.get("orderId"));
            }catch (Exception e){

            }
            //插入交易明细表transactionDetails
            transactionDetail.setState("1");
            transactionDetailsMapper.insert(transactionDetail);
            orderMod.setState("8");
            orderMod.setLoanState("3");
        }else {
            return returnMsg;
        }
        orderMod.setId(order.getId());
        //更新订单表
        orderMapper.updateState(orderMod);
        return returnMsg;
    }


    /*放款回调*/
    @Transactional
    @Override
    public boolean bfWithdrawalCallback(HttpServletRequest req, Map mapParam){
        Map<String,Object>map=new HashedMap();
        try {
            map=BaoFuApi.bfWithdrawalCallback(req,mapParam.get("cerPath").toString());
        }catch (Exception e){
            return  false;
        }
        Date date=new Date();
        DateFormat format=new SimpleDateFormat("yyyyMMddHHmmss");
        String returnCode=map.get("returnCode").toString();
        String returnMsg=map.get("returnMsg").toString();
        String transNo=map.get("transNo").toString();
        String orderType="";
        TransactionDetails transactionDetail=new  TransactionDetails();
        Map<String,Object> withdrawalsMap=new HashedMap();
        //更新提现表
        withdrawalsMap.put("valBatchNo",map.get("transNo").toString());
        transactionDetail=transactionDetailsMapper.getTransactionDetail(map);
        if (transactionDetail==null){
            return true;
        }
        Order order=orderMapper.getOrderDel(transactionDetail.getOrderId()).get(0);
        Order orderMod=new Order();
        orderType=order.getOrderType();
        //放款款明细
        transactionDetail.setBaofuAsynchronousCode(returnCode);
        transactionDetail.setBaofuAsynchronousMag(returnMsg);
        transactionDetail.setSuccessTime(format.format(date));
        transactionDetail.setBatchNo(transNo);
        withdrawalsMap.put("batch_no",map.get("transNo").toString());
        withdrawalsMap.put("batchNo",map.get("transNo").toString());

        //添加订单日志
        try{
            MagOrderLogs magOrderLogs=new MagOrderLogs();
            magOrderLogs.setId(UUID.randomUUID().toString());
            magOrderLogs.setOperatorId("0");
            magOrderLogs.setOperatorName(map.get("系统回调").toString());
            magOrderLogs.setOperatorType("1");
            magOrderLogs.setOrderId(order.getId());
            magOrderLogs.setCreatTimeLog(DateUtils.getDateString(new Date()));
            magOrderLogs.setTache("订单放款");
            magOrderLogs.setChangeValue(returnMsg);
            magOrderLogs.setState(order.getState());
            magOrderLogs.setCreatTime(DateUtils.getDateString(new Date()));
            magOrderLogs.setAlterTime(DateUtils.getDateString(new Date()));
            orderLogMapper.addOrderLogs(magOrderLogs);
        }
        catch (Exception e){

        }
        //1 交易成功
        if ("1".equals(returnCode)){
            transactionDetail.setState("1");
            //黄金贷
            if ("1".equals(orderType)){
                List<Withdrawals> withdrawalsList=orderMapper.getOrderWithdrawals(withdrawalsMap);
                if(withdrawalsList.size()>0){
                    orderMod.setId(order.getId());
                    orderMod.setRebatesState("2");
                    //更新订单表
                    orderMapper.updateState(orderMod);
                    withdrawalsMap.put("state","1");
                    withdrawalsMap.put("alterTime",DateUtils.getDateString(new Date()));
                    orderMapper.updateWithdrawals(withdrawalsMap);
                    try{
                        HttpClientUtil.getInstance().sendHttpGet(mapParam.get("appHGUrl") + "orderMessage/orderPushMessage?state=8&orderId="+order.getId());
                    }catch (Exception e){

                    }
                }
            }//商品贷
            else if ("2".equals(orderType)){
                orderMod.setId(order.getId());
                orderMod.setLoanState("3");
                //更新订单表
                orderMapper.updateState(orderMod);
                try{
                    HttpClientUtil.getInstance().sendHttpGet(mapParam.get("appHGUrl") + "orderMessage/orderPushMessage?state=8&orderId="+order.getId());
                }catch (Exception e){
                }
            }
        }else {
            transactionDetail.setState("2");
            transactionDetail.setBaofuAsynchronousCode(returnCode);
            transactionDetail.setBaofuAsynchronousMag(returnMsg);
            //黄金贷
            if ("1".equals(orderType)) {
                withdrawalsMap.put("state", "3");
                orderMapper.updateWithdrawals(withdrawalsMap);
            }//商品贷
            else if ("2".equals(orderType)){
                orderMod.setId(order.getId());
                orderMod.setLoanState("4");
                //更新订单表
                orderMapper.updateState(orderMod);
            }
        }
        transactionDetailsMapper.updateTransactionDetail(transactionDetail);
        return  true;
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
            if("1".equals(orders.get(0).getFeeState())){
                return "已经扣款成功！请刷新页面！";
            }else if ("2".equals(orders.get(0).getFeeState())){
                return "扣款确认中！请稍后！";
            }
            else if ("0".equals(orders.get(0).getFeeState())||"3".equals(orders.get(0).getFeeState())){
                try{
                    double  manageFee=Double.valueOf(orders.get(0).getManageFee());
                    double  quickTrialFee=Double.valueOf(orders.get(0).getQuickTrialFee());
                    double  debitAmount=manageFee+quickTrialFee;
                    map.put("debitAmount",debitAmount);
                    /******************************************************************************测试写死*/
                    map.put("debitAmount","0.01");
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
                    transaction.setType("1");
                    transaction.setBaofuCode(debitReturnCode);
                    transaction.setBaofuMag(debitResultMap.get("returnMsg").toString());
                    transaction.setBatchNo(map.get("debitTransNo").toString());
                    transaction.setAmount(map.get("debitAmount").toString());
                    transaction.setCreatTime(format.format(date));
                    Order order=new Order();
                    order.setId(map.get("orderId").toString());
                    //0000 交易成功   BF00114 订单已支付成功，请勿重复支付
                    if("0000".equals(debitReturnCode)||"BF00114".equals(debitReturnCode)){
                        transaction.setState("1");

                        order.setFeeState("1");
                        order.setState("8");
                        returnMsg="扣款成功！";
                    }else if("BF00100".equals(debitReturnCode)||"BF00112".equals(debitReturnCode)||"BF00113".equals(debitReturnCode)||"BF00115".equals(debitReturnCode)||"BF00144".equals(debitReturnCode)||"BF00202 ".equals(debitReturnCode)) {
                        transaction.setState("0");

                        order.setFeeState("2");
                        returnMsg="扣款申请已提交，正在确认结果！";
                    }else {
                        transaction.setState("2");

                        order.setFeeState("3");
                    }
                    //更新订单表
                    orderMapper.updateState(order);
                    //插入交易明细表
                    transactionDetailsMapper.insert(transaction);

                }catch (Exception e) {
//                    TransactionException ransactionException=new TransactionException();
//                    ransactionException.setId(UUID.randomUUID().toString());
//                    ransactionException.setOrderId(map.get("orderId").toString());
//                    ransactionException.setType("1");
//                    ransactionException.setBatchNo(map.get("debitTransNo").toString());
//                    ransactionException.setAmount(map.get("debitAmount").toString());
//                    ransactionException.setCreatTime(format.format(date));
//                    ransactionException.setState("0");
//                    // 异常处理  插入异常明细
//                    transactionExceptionMapper.insert(ransactionException);
                    TransactionDetails transaction=new  TransactionDetails();
                    transaction.setId(UUID.randomUUID().toString());
                    transaction.setOrderId(map.get("orderId").toString());
                    transaction.setType("1");
                    transaction.setBaofuCode(debitReturnCode);
                    transaction.setBaofuMag(debitResultMap.get("returnMsg").toString());
                    transaction.setBatchNo(map.get("debitTransNo").toString());
                    transaction.setAmount(map.get("debitAmount").toString());
                    transaction.setCreatTime(format.format(date));
                    transaction.setState("0");
                    transactionDetailsMapper.insert(transaction);
                    Order order=new Order();
                    order.setId(map.get("orderId").toString());
                    order.setFeeState("2");
                    //更新订单表
                    orderMapper.updateState(order);
                    return "扣款申请申请已提交！";
                }
                return  returnMsg;
            }
        }else {
            return "订单不存在！";
        }
        return "";
    }
    @Override
    public List<TransactionDetails> transactionDetailsList(Map map){
        return  transactionDetailsMapper.getTransactionList(map);
    }
}
