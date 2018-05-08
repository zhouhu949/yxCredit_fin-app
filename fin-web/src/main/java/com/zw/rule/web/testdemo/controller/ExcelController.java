package com.zw.rule.web.testdemo.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zw.base.util.DateUtils;
import com.zw.rule.core.Response;
import com.zw.rule.customer.service.OrderService;
import com.zw.rule.engine.po.Engine;
import com.zw.rule.engine.service.EngineService;
import com.zw.rule.excel.ExcelService;
import com.zw.rule.excel.po.*;
import com.zw.rule.loan.po.Loan;
import com.zw.rule.loan.po.MagLoanDetail;
import com.zw.rule.mybatis.ParamFilter;
import com.zw.rule.repayment.po.Repayment;
import com.zw.rule.util.StringUtil;
import com.zw.rule.web.aop.annotaion.WebLogger;
import com.zw.rule.web.util.ExcelBean;
import com.zw.rule.web.util.ExcelUtil;
import com.zw.rule.web.util.PageConvert;
import com.zw.rule.web.util.UserContextUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrator on 2017/6/7.
 */
@Controller
@RequestMapping("excel")
public class ExcelController {

    @Resource
    private ExcelService excelService;

        @RequestMapping("/insert")
        @ResponseBody
        public Response importExcel(String url,String sheet) {
            Integer count = 0;
            File file = new File(url);
            if(sheet.equals("1")){
                String[] beanProperty = {"idCard","orderTime","zdstate","yamount"};
                List<ExcelBean1> list = ExcelUtil.parserExcel(ExcelBean1.class, file, beanProperty,0);
                  SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
                  SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
                try {
                if(list.size() > 0){
                    for (ExcelBean1 excelBean1:list) {
                        //循环更新订单的数据(订单时间，自动化状态)
                        String time=excelBean1.getOrderTime();
                        String createTime = df.format(df1.parse(time));
                        excelBean1.setOrderTime(createTime);
                        excelService.updateOrderInfo(excelBean1);
                        if(excelBean1.getZdstate().equals("通过")){
                            //根据身份证 号码查询放款的id
                            Map map = excelService.getLoanIdByIdCard(excelBean1.getIdCard());
                            //删除对应的还款计划
                            excelService.deleteRepaymentByLoanId((String)map.get("loanId"));
                            //重新生成还款计划
                            List<Repayment> list1 =  repaymentList((String)map.get("loanId"), excelBean1.getOrderTime(),(Double)map.get("periods"),(Double)map.get("amount"),(Double)map.get("rate"),(String) map.get("orderId"));
                            //向还款计划表中插入还款计划
                            excelService.insertRepaymentList(list1);
                        }
                    }
                }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }else if(sheet.equals("2")){
                String[] beanProperty = {"idCard","firstRule","secondRule","thirdRule","fourthRule","fifthRule","sixthRule","seventhRule"};
                List<ExcelBean2> list = ExcelUtil.parserExcel(ExcelBean2.class, file, beanProperty,1);
                if(list.size() > 0){
                    for (ExcelBean2 excelBean2:list) {
                        //判断一下身份证对应数据是否已存在，存在就不插入数据
                        if(excelService.getRuleRefuse(excelBean2.getIdCard())){
                            //循环插入rule_refuse数据
                            excelService.insertRuleRefuse(excelBean2);
                            count++;
                        }
                    }
                }
            }else if(sheet.equals("3")){
                String[] beanProperty = {"idCard","sex","age","marry","hjszqy","dqjzqk"
                        ,"xdwgznx","dwxz","sfjngjjjsb","rank","gzffxs","gmfs","yaj"
                        ,"pyxl","sjhmsmsfyz","azthrj","shzxwjqdksl","tdwbdkptgs","sqgjj","sqtxl"
                        ,"sqthjl","zmf","dw","sjhsysc","djzc","gzdwmcxg","gzdwdhxg"
                        ,"lxrdhxg","xbxldwcs","jjgz"};
                List<ExcelBean3> list = ExcelUtil.parserExcel(ExcelBean3.class, file, beanProperty,2);
                if(list.size() > 0){
                    for (ExcelBean3 excelBean3:list) {
                        //判断一下身份证对应数据是否已存在，存在就不插入数据
                       if(excelService.getScoreCard(excelBean3.getIdCard())){
                            //循环插入score_card数据
                            excelService.insertScoreCard(excelBean3);
                            count++;
                        }
                    }
                }
            }else if(sheet.equals("4")){
                String[] beanProperty = {"cradNo","htrl","siys","phoneReal","phoneTime","phoneGps","accumulation"
                        ,"social","alipayRealName","sesameSeeds","pyIfo","pyEdu","pyDistress"
                        ,"pyTax","pyJudCase","pyJudDishonesty","pyJudEnfor","pyNetLoanOver","tdBHit"
                        ,"tdLoanProxy","tdLoanTool","tdExtPlatform","tdIdCriminal","tdIdRisk","tdIdTaxes"
                        ,"tdIdCredit","tdPhoneRisk","tdPhoneArrears","tdPhoneCommunication","tdPhoneCredit","tdPhoneFalseNum"
                        ,"tdPhoneFraudNum","tdContPhoneRisk","tdContPhoneCommunication","tdContPhoneCredit","tdContPhoneFalseNum","tdContPhoneFraudNum"
                        ,"tdDgFalseNum","tdDgFraudNum","tdDgAgencyNum","shzxLoan","shzxOutLoan","shzxOverdue"
                        ,"shzxLineCredit","shzxMonth"};
                List<RuleRisk> list = ExcelUtil.parserExcel(RuleRisk.class, file, beanProperty,3);
                if(list.size() > 0){
                    for (RuleRisk ruleRisk:list) {
                        //判断一下身份证对应数据是否已存在，存在就不插入数据
                        if(excelService.getRuleRisk(ruleRisk.getCradNo())){
                            //循环插入rule_risk数据
                            ruleRisk.setId(UUID.randomUUID().toString());
                            excelService.insertRuleRisk(ruleRisk);
                            count++;
                        }
                    }
                }
            }else if(sheet.equals("5")){
                String[] beanProperty = {"idCard","firstTime","firstAmount","secTime","secAmount"
                        ,"theTime","theAmount","foTime","foAmount"};
                List<ExcelBean5> list = ExcelUtil.parserExcel(ExcelBean5.class, file, beanProperty,4);
                if(list.size() > 0){
                    for (ExcelBean5 excelBean5:list) {
                        //根据身份证号码查询放款的id
                        Map map = excelService.getLoanIdByIdCard(excelBean5.getIdCard());
                        if(map != null){
                            //删除对应的放款明细
                            excelService.deleteLoanDetail((String)map.get("loanId"));
                            List<MagLoanDetail> list1 = new ArrayList<>();
                            if(!StringUtil.isBlank(excelBean5.getFirstAmount())){
                                MagLoanDetail magLoanDetail1 = new MagLoanDetail();
                                magLoanDetail1.setId(UUID.randomUUID().toString());
                                magLoanDetail1.setLoanId((String)map.get("loanId"));
                                magLoanDetail1.setLoanTime(excelBean5.getFirstTime());
                                magLoanDetail1.setLoanAmount(excelBean5.getFirstAmount());
                                magLoanDetail1.setSort("1");
                                list1.add(magLoanDetail1);
                            }
                            if(!StringUtil.isBlank(excelBean5.getSecAmount())){
                                MagLoanDetail magLoanDetail1 = new MagLoanDetail();
                                magLoanDetail1.setId(UUID.randomUUID().toString());
                                magLoanDetail1.setLoanId((String)map.get("loanId"));
                                magLoanDetail1.setLoanTime(excelBean5.getSecTime());
                                magLoanDetail1.setLoanAmount(excelBean5.getSecAmount());
                                magLoanDetail1.setSort("2");
                                list1.add(magLoanDetail1);
                            }
                            if(!StringUtil.isBlank(excelBean5.getTheAmount())){
                                MagLoanDetail magLoanDetail1 = new MagLoanDetail();
                                magLoanDetail1.setId(UUID.randomUUID().toString());
                                magLoanDetail1.setLoanId((String)map.get("loanId"));
                                magLoanDetail1.setLoanTime(excelBean5.getTheTime());
                                magLoanDetail1.setLoanAmount(excelBean5.getTheAmount());
                                magLoanDetail1.setSort("3");
                                list1.add(magLoanDetail1);
                            }
                            if(!StringUtil.isBlank(excelBean5.getFoAmount())){
                                MagLoanDetail magLoanDetail1 = new MagLoanDetail();
                                magLoanDetail1.setId(UUID.randomUUID().toString());
                                magLoanDetail1.setLoanId((String)map.get("loanId"));
                                magLoanDetail1.setLoanTime(excelBean5.getFoTime());
                                magLoanDetail1.setLoanAmount(excelBean5.getFoAmount());
                                magLoanDetail1.setSort("4");
                                list1.add(magLoanDetail1);
                            }
                            //循环更新订单的数据
                            excelService.insertLoanDetail(list1);
                            //更新放款的进度
                            Loan loan = new Loan();
                            loan.setId((String)map.get("loanId"));
                            loan.setProgress(String.valueOf(list1.size()));
                            excelService.updateLoanInfo(loan);
                            count++;
                        }
                    }
                }
            }
            Response response = new Response();
            response.setData(count);
            return response;
            }


    /**生成还款计划*/
    public List<Repayment> repaymentList(String loanId, String loanTime, double periods, double amount, double rate, String orderId){
        List<Repayment> list =  new ArrayList<>();
        String createTime = DateUtils.getCurrentTime(DateUtils.STYLE_5);
        DecimalFormat df = new DecimalFormat("######0.00");
        String Fee = df.format(amount*rate/100/12);
        String RepaymentAmount = df.format(amount/periods);
        Date loantime = DateUtils.strConvertToDate2(DateUtils.STYLE_5,loanTime);
        for(int i = 0;i<periods;i++){
            Repayment repayment = new Repayment();
            repayment.setAmount(String.valueOf(amount));
            repayment.setLoanId(loanId);
            repayment.setCreateTime(createTime);
            repayment.setPayCount(String.valueOf(i+1));
            repayment.setLoanTime(loanTime);
            repayment.setState("1");
            repayment.setOrderId(orderId);
            repayment.setFee(Fee);
            repayment.setRate(String.valueOf(rate));
            repayment.setRepaymentAmount(RepaymentAmount);
            repayment.setId(UUID.randomUUID().toString());
            String PayTime = DateUtils.getDateString(DateUtils.getLastMouth(loantime,(i+1)));
            repayment.setPayTime(PayTime);
            list.add(repayment);
        }
        return list;
    }
}
