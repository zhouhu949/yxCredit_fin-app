package com.zw.rule.timedTask.service;

import com.zw.base.util.TraceLoggerUtil;
import com.zw.rule.collectionRecord.po.MagDerate;
import com.zw.rule.customer.po.Order;
import com.zw.rule.loanmange.po.LoanManage;
import com.zw.rule.mapper.collectionRecord.MagDerateMapper;
import com.zw.rule.mapper.customer.OrderMapper;
import com.zw.rule.mapper.loanmange.LoanManageMapper;
import com.zw.rule.mapper.product.FeeMapper;
import com.zw.rule.mapper.repayment.RepaymentMapper;
import com.zw.rule.product.Fee;
import com.zw.rule.repayment.po.Repayment;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/11/28.
 */
public class TaskInterest {
    @Autowired
    private  RepaymentMapper repaymentMapper;
    @Autowired
    private  OrderMapper orderMapper;
    @Autowired
    private LoanManageMapper loanManageMapper;
    @Autowired
    private FeeMapper feeMapper;

    @Autowired
    private MagDerateMapper magDerateMapper;


    public void timingUpDate(){
        //先计算逾期再进行统计操作
        overdueCalculationUpdate();
        sunUpdate();
        //更新过期减免数据
        derateUpdate();
    }
    //已还 未还 减免 罚息 统计
    public  void sunUpdate(){
        Map<String,Object>map=new HashedMap();
        map.put("state","8");
        List<Order> orders=orderMapper.getList(map);
        double penaltyTotal=0;//罚息总额
        double derateAmount=0;//减免金额
        double returnedAmount=0;//已还款金额
        double unpaidAmount=0;//未还款金额
        String orderId="";
        String repaymentId="";
        DecimalFormat df  = new DecimalFormat("######0.00");
        try {
            for (int i=0;i<orders.size();i++){
                orderId=orders.get(i).getId();
                map.put("productName",orders.get(i).getProductDetailCode());
                penaltyTotal=0;
                derateAmount=0;
                returnedAmount=0;
                unpaidAmount=0;
                map.put("state","8");
                map.put("orderId",orders.get(i).getId());
                List <Repayment> repaymentList=repaymentMapper.getList(map);
                LoanManage loanManage=new LoanManage();
                for(int j=0;j<repaymentList.size();j++){
                    try {
                        Repayment repayment=repaymentList.get(j);
                        repaymentId=repayment.getId();
                        //未还  逾期
                        if ("1".equals(repayment.getState())||"3".equals(repayment.getState())){
                            unpaidAmount=unpaidAmount+Double.valueOf(repayment.getRepaymentAmount());
                        }//已还
                        else if ("2".equals(repayment.getState())){
                            returnedAmount=returnedAmount+Double.valueOf(repayment.getActualAmount());
                            derateAmount=derateAmount+Double.valueOf(repayment.getDerateAmount());
                            penaltyTotal=penaltyTotal+Double.valueOf(repayment.getPenalty());
                        }
                    }catch (Exception e){
                        TraceLoggerUtil.info("还款计划统计，订单编号"+orders.get(i).getId()+"还款计划表编号"+repaymentList.get(j).getId()+"详细信息"+e.toString());
                    }
                }
                //更新放款表
                loanManage.setPenalty(df.format(penaltyTotal));
                loanManage.setReturnedAmount(df.format(returnedAmount));
                loanManage.setUnpaidAmount(df.format(unpaidAmount));
                loanManage.setDerateAmount(df.format(derateAmount));
                //loanManage.setId(loanList.get(i).getId());
                loanManage.setType("1");
                loanManage.setOrderId(orders.get(i).getId());
                loanManageMapper.updateByPrimaryKey(loanManage);
            }
        }catch (Exception e){
            TraceLoggerUtil.info("还款计划统计失败，订单编号"+orderId+"还款计划表编号"+repaymentId+"详细信息"+e.toString());
        }
    }

    //逾期计算
    private void overdueCalculationUpdate() {
        Map<String,Object>map=new HashedMap();
        map.put("state","8");
        //获取当前日期 并设置时分为0
        Date Today=new Date();
        Today.setHours(0);
        Today.setMinutes(0);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<Order> orders=orderMapper.getList(map);
        double penaltyTotal=0;//罚息总额
        int OverdueDays=0;//逾期最大天数
        double penalty=0;//单笔罚息
        double total=0;//应还总额
        String orderId="";
        String repaymentId="";
        for (int i=0;i<orders.size();i++){
            orderId=orders.get(i).getId();
            map.put("productName",orders.get(i).getProductDetailCode());
            Fee fee=feeMapper.getFeeByProductName(map);
            penaltyTotal=0;
            OverdueDays=0;
            map.put("state","8");
            map.put("orderId",orders.get(i).getId());
            List <Repayment> repaymentList=repaymentMapper.getList(map);
            LoanManage loanManage=new LoanManage();
            try {
                for(int j=0;j<repaymentList.size();j++){
                    try {
                        Repayment repayment=repaymentList.get(j);
                        repaymentId=repayment.getId();
                        String payTime=repayment.getPayTime();
                        payTime =payTime.substring(0, 4) + "-" + payTime.substring(4, 6) + "-" + payTime.substring(6, 8);
                        Date strDate=sdf.parse(payTime);
                        int days = (int) ((Today.getTime() - strDate.getTime()) / (1000*3600*24));
                        //逾期计算
                        if (days>0&&(!"2".equals(repayment.getState()))){
                            Repayment mo=new Repayment();
                            total=total+Double.valueOf(repayment.getRepaymentAmount());
                            penalty=Double.valueOf(repayment.getAmount())*days*Double.valueOf(fee.getYuqi_fee());
                            DecimalFormat df  = new DecimalFormat("######0.00");
                            df.format(penalty);
                            penaltyTotal=penaltyTotal+penalty;
                            if (days>OverdueDays){
                                OverdueDays=days;
                            }
                            mo.setId(repayment.getId());
                            mo.setOverdueDays(Integer.toString(days));
                            mo.setPenalty(Double.toString(penalty));
                            //修改成逾期状态
                            mo.setState("3");
                            repaymentMapper.updtaeRepayment(mo);
                        }
                    }catch (Exception e){
                        TraceLoggerUtil.info("还款计划表更新失败，订单编号"+orders.get(i).getId()+"还款计划表编号"+repaymentList.get(j).getId()+"详细信息"+e.toString());
                    }
                }
            }catch (Exception e){
                TraceLoggerUtil.info("还款计划表更新失败，订单编号"+orderId+"还款计划表编号"+repaymentId+"详细信息"+e.toString());
            }
        }
    }
    //减免数据更新
    private void derateUpdate() {
        //获取当前日期 并设置时分为0
        Date Today=new Date();
        Today.setHours(0);
        Today.setMinutes(0);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Map<String,Object> map=new HashedMap();
        List<MagDerate>magDerateList=magDerateMapper.getMagDerateList(map);
        try{
            for (int i=0;i<magDerateList.size();i++){
                MagDerate magDerate=magDerateList.get(i);
                String approvalDate=magDerate.getApprovalDate();
                approvalDate =approvalDate.substring(0, 4) + "-" + approvalDate.substring(4, 6) + "-" + approvalDate.substring(6, 8);
                Date strDate=sdf.parse(approvalDate);
                strDate.setHours(0);
                strDate.setMinutes(0);
                int days = (int) ((Today.getTime() - strDate.getTime()) / (1000*3600*24));
                //审批时间超过一天无效
                if(days>0){
                    magDerate.setState("2");
                    magDerateMapper.updateDerate(magDerate);
                }
            }
        }catch (Exception e){

        }
    }
}
