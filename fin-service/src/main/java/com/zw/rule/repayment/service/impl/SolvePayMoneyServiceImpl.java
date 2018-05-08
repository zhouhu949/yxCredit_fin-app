package com.zw.rule.repayment.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.zw.base.util.*;
import com.zw.rule.customer.po.Order;
import com.zw.rule.mapper.customer.OrderMapper;
import com.zw.rule.mapper.product.BgEfProductDetailMapper;
import com.zw.rule.mapper.product.ICrmProductMapper;
import com.zw.rule.mapper.repayment.AccDerateManageMapper;
import com.zw.rule.mapper.repayment.CrmPaycontrolMapper;
import com.zw.rule.mapper.repayment.CrmPayrecoderMapper;
import com.zw.rule.mapper.repayment.RepaymentMapper;
import com.zw.rule.repayment.service.RepaymentService;
import com.zw.rule.repayment.service.SolvePayMoneyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 功能说明：专门针对还款的第一个独立Service层
 * @author wangmin
 * 修改人:
 * 修改原因：
 * 修改时间：
 * 修改内容：
 * 创建日期：2017-07-6
 */
@Service
public class SolvePayMoneyServiceImpl implements SolvePayMoneyService {

    @Resource
    private OrderMapper orderMapper;

    @Resource
    private CrmPayrecoderMapper crmPayrecoderMapper;

    @Resource
    private CrmPaycontrolMapper crmPaycontrolMapper;

    @Resource
    private BgEfProductDetailMapper bgEfProductDetailMapper;

    @Resource
    private ICrmProductMapper iCrmProductMapper;

    @Override
    public JSONObject getAdvanceInfo(String crmOrderId) {
        JSONObject resultJson = new JSONObject();
        //提前结清的违约金
        resultJson.put("companyA", 0d);
        resultJson.put("companyB", 0d);
        resultJson.put("companyC", 0d);
        resultJson.put("companyD", 0d);
        resultJson.put("touAdvanceMoney", 0d);
        //开始计算投资人的提前还款违约金
        //String isLineSql ="SELECT investment_model,clearing_channel,order_type from crm_order where id='"+crmOrderId+"' ";
        //Map isLineMap=bgEfOrderDao.queryBySqlToLower(isLineSql).get(0);
        //Order order = orderMapper.selectByPrimaryKey(crmOrderId);
        //投资模式
        /*String investment_model = "";
        if(isLineMap.get("investment_model")!=null){
            investment_model = isLineMap.get("investment_model").toString();
        }*/
        //结算渠道默认1
        resultJson.put("clearing_channel", "1");
        /*String orderType="";
        if(isLineMap.get("order_type")!=null){
            orderType = isLineMap.get("order_type").toString();
        }*/
        // TODO: 2017/7/6 没有订单type这个字段，后期根据需求增加，暂时写死
        if("1".equals("1")){ //v1系统数据
            JSONObject realJson  = getAdvanceInfoV1(crmOrderId);
            resultJson.putAll(realJson);
            return resultJson;
        }
        /*if("2".equals(orderType)){ //v2系统数据
            JSONObject realJson  = getAdvanceInfoV2(crmOrderId);
            resultJson.putAll(realJson);
            return resultJson;
        }*/
        /*if("1".equals(investment_model)){
            String touSql =
                    "SELECT  "+
                            "co.contract_money*bedp.pre_manage_rate/100 touAdvanceMoney "+
                            "from "+
                            "crm_order co, "+
                            "bg_ef_product_detail bedp "+
                            "where co.ef_prd_detail_id = bedp.id and co.id='"+crmOrderId+"'";
            resultJson.putAll(bgEfOrderDao.queryBySqlReturnMapList(touSql).get(0));
        }*/
        List<Map> bgList = bgEfProductDetailMapper.getBgData(crmOrderId);
        resultJson.putAll(bgList.get(0));
        //退回的服务费
        resultJson.put("companyAReturn", 0d);
        resultJson.put("companyBReturn", 0d);
        resultJson.put("companyCReturn", 0d);
        resultJson.put("companyDReturn", 0d);
        /*String sql =
                "SELECT  "+
                        "co.contract_money, "+
                        "co.credit_money, "+
                        "IFNULL(co.company_a_server_fund+co.company_b_server_fund+co.company_c_server_fund+co.company_d_server_fund,0) x, "+
                        "pwpd.contract_rate, "+
                        "pwpd.multiple_rate, "+
                        "pwpd.periods, "+
                        "pwpd.staging_services_rate, "+
                        "pwpd.bail_rate, "+
                        "pwpd.duetime_type, "+
                        "pwpr.* "+
                        "from  "+
                        "crm_order co, "+
                        "pro_working_product_detail pwpd, "+
                        "pro_working_prd_company_rate pwpr "+
                        "where "+
                        "co.pro_detail_id = pwpd.id AND "+
                        "co.id='"+crmOrderId+"' AND "+
                        "pwpd.id= pwpr.detail_id " ;*/
        //List<Map> list = bgEfOrderDao.queryBySqlToLower(sql);
        List<Map> list = iCrmProductMapper.getRepayDetail(crmOrderId);
        Map map  = list.get(0);
        // TODO: 2017/7/6  相关视图关联的表没有，后期根据需求获得
        //修改 V3 的合同月利率需要取贝格产品的年华利率/12
        //String bgRateSql  = "SELECT bed.pdInvestRate/12 contract_rate from crm_order co ,view_ef_union_product_info bed where co.ef_prd_detail_id=bed.pdId and (co.order_type is null or co.order_type='3') and co.id='"+crmOrderId+"'";
        //List<Map> bgRateList = bgEfOrderDao.queryBySqlReturnMapList(bgRateSql);
        //map.put("contract_rate", bgRateList.get(0).get("contract_rate"));
        // TODO: 2017/7/6 当前合同利率写死
        map.put("contract_rate", "12.2");

        //String totalPeriodsSql = "SELECT * from crm_paycontrol where crm_order_id='"+crmOrderId+"' ";
        //int totalPeriods = bgEfOrderDao.queryBySqlReturnMapList(totalPeriodsSql).size();//Integer.parseInt(map.get("periods").toString());
        int totalPeriods = crmPaycontrolMapper.selectByOrderId(crmOrderId).size();
        Map formulaKey = new HashMap();
        formulaKey.put("contractRate",map.get("contract_rate")); // 合同月利率
        formulaKey.put("multipleRate",map.get("multiple_rate")); // 综合利率
        formulaKey.put("periods",map.get("periods")); // 借款期限
        formulaKey.put("stagingServicesRate",map.get("staging_services_rate")); // 月分期服务费率
        formulaKey.put("bailRate",map.get("bail_rate")); // 保证金率
        formulaKey.put("D",map.get("credit_money"));//授信额度
        formulaKey.put("format", "yes");
        //JSONObject json  = FormulaUtil.getFormulaValue(formulaKey,map.get("duetime_type")!=null?Integer.parseInt(map.get("duetime_type").toString()):null);
        //前期一次性服务费>> 直接从四家公司之和即可
        //double serviceMoney = Double.parseDouble(map.get("x").toString());
        // TODO: 2017/7/6  服务费写死了，后面根据需要从数据库获取
        double serviceMoney =56.56;
        //获得最后一期的时间
        // TODO: 2017/7/6 暂时无法确定最后一期的放款时间，写死
        //String lastSql = "SELECT loan_time from crm_order where id='"+crmOrderId+"'  ";
        //String lastTime = bgEfOrderDao.queryBySqlReturnMapList(lastSql).get(0).get("loan_time").toString();
        String lastTime ="20170202130556";
        //总期数
        int returnPeriods = 0;
        //查看剩余的待还总期数
        //String countSql = "SELECT count(*) count from crm_paycontrol where crm_order_id='"+crmOrderId+"' and status in (0)";
        List<Map> countList = crmPaycontrolMapper.getPerPayPeriods(crmOrderId);
        returnPeriods = Integer.parseInt(countList.get(0).get("perPayeriods").toString())-1;
        //开始检测当天的状态
        //String checkSql  = "SELECT status from crm_paycontrol where crm_order_id='"+crmOrderId+"' and repayment_time like '"+DateUtils.getCurrentTime(DateUtils.STYLE_2)+"%';";
        List<Map> checkList  =  crmPaycontrolMapper.getCurrentStatus(crmOrderId,DateUtils.getCurrentTime(DateUtils.STYLE_2));
        if(ListTool.isNotNullOrEmpty(checkList)){
            Map checkMap = checkList.get(0);
            if("0".equals(checkMap.get("status").toString())){ //当天的状态是待还
                returnPeriods--;
            }
        }
        if(returnPeriods <= 0){
            returnPeriods=0;
        }
        //相差天数  //当天所在的期数
        long days = 1; //默认第一期
        //String currentDueTimeSql = "SELECT * from crm_paycontrol  where crm_order_id= '"+crmOrderId+"' and repayment_time <='"+DateUtil.getCurrentTime(DateUtil.STYLE_2)+"%' ORDER BY repayment_duetime desc limit 1 ";
        List<Map> currentDueTimeList = crmPaycontrolMapper.getCurrentDueTime(crmOrderId,DateUtils.getCurrentTime(DateUtils.STYLE_2));
        if(ListTool.isNotNullOrEmpty(currentDueTimeList)){ //只有不为空的情况下
            days = Long.parseLong(currentDueTimeList.get(0).get("repayment_duetime").toString());
            //判断是否需要继续加一天
            if(currentDueTimeList.get(0).get("repayment_time").toString().substring(0, 10).compareTo(DateUtils.getCurrentTime(DateUtils.STYLE_2))<0){
                days++;
            }
        }
        if(days>totalPeriods){ //如果大与总期数则需要直接赋值为总期数
            days = totalPeriods;
        }

        if(ListTool.isNotNullOrEmpty(list)){
            for(Map forMap:list){
                //计算退回服务费 (前期一次性服务费*占比*(退回的期数/总期数))
                if("A".equals(forMap.get("identify_company"))){ //A公司的退回服务费和提前结清违约金
                    //服务费
                    resultJson.put("companyAReturn",
                            ArithUtil.mul(ArithUtil.mul(serviceMoney,Double.parseDouble(forMap.get("service_fee_rate").toString())), (returnPeriods*1d/totalPeriods/100d)));
                    //违约金
                    //1.获得真正的利率
                    //2.计算违约金
                    double rate = getAdvanceClearRate(forMap, days);
                    resultJson.put("companyA",NumberTool.format(
                            ArithUtil.mul(Double.parseDouble(map.get("contract_money").toString()), rate))
                    );
                }
                if("B".equals(forMap.get("identify_company"))){//B公司的退回服务费和提前结清违约金
                    resultJson.put("companyBReturn",
                            ArithUtil.mul(ArithUtil.mul(serviceMoney,Double.parseDouble(forMap.get("service_fee_rate").toString())), (returnPeriods*1d/totalPeriods/100d)));
                    //违约金
                    //1.获得真正的利率
                    //2.计算违约金
                    double rate = getAdvanceClearRate(forMap, days);
                    resultJson.put("companyB",NumberTool.format(
                            ArithUtil.mul(Double.parseDouble(map.get("contract_money").toString()), rate)));
                }
                if("C".equals(forMap.get("identify_company"))){//C公司的退回服务费和提前结清违约金
                    resultJson.put("companyCReturn",
                            ArithUtil.mul(ArithUtil.mul(serviceMoney,Double.parseDouble(forMap.get("service_fee_rate").toString())), (returnPeriods*1d/totalPeriods/100d)));
                    //违约金
                    //1.获得真正的利率
                    //2.计算违约金
                    double rate = getAdvanceClearRate(forMap, days);
                    resultJson.put("companyC",NumberTool.format(ArithUtil.mul(Double.parseDouble(map.get("contract_money").toString()), rate)));
                }
                if("D".equals(forMap.get("identify_company"))){//D公司的退回服务费和提前结清违约金
                    resultJson.put("companyDReturn",
                            ArithUtil.mul(ArithUtil.mul(serviceMoney,Double.parseDouble(forMap.get("service_fee_rate").toString())), (returnPeriods*1d/totalPeriods/100d)));
                    //违约金
                    //1.获得真正的利率
                    //2.计算违约金
                    double rate = getAdvanceClearRate(forMap, days);
                    resultJson.put("companyD", NumberTool.format(
                            ArithUtil.mul(Double.parseDouble(map.get("contract_money").toString()), rate)));
                }
            }
        }
        //总的退回服务费
        double companyAReturn = resultJson.getDouble("companyBReturn");
        double companyBReturn = resultJson.getDouble("companyAReturn");
        resultJson.put("companyAReturn", companyAReturn);
        resultJson.put("companyBReturn", companyBReturn);
        JSONObject removeJson =new JSONObject();
        removeJson.put("clearing_channel", "");
        JsonTool.format(resultJson,removeJson);
        resultJson.put("returnServiceMoney", ArithUtil.add(new Double[]{
                resultJson.getDouble("companyAReturn"),
                resultJson.getDouble("companyBReturn"),
                resultJson.getDouble("companyCReturn"),
                resultJson.getDouble("companyDReturn")
        }));
        return resultJson;
    }

    /**
     * 功能说明：计算提前结清数据
     * @param 
     * @return
     * @throws 
     * 创建人 wangmin
     * 创建日期：2017年7月5日
     * 最后修改时间：
     * 修改人：
     * 修改内容：
     * 修改注意点：
     */
    public JSONObject getAdvanceInfoV1(String crmOrderId){
        JSONObject resultJson = new JSONObject();
        //提前结清的违约金
        resultJson.put("companyAdvanceMoney", 0d);
        resultJson.put("touAdvanceMoney", 0d);
        resultJson.put("companyA",0d);
        resultJson.put("companyB",0d);
        resultJson.put("companyC",0d);
        resultJson.put("companyD",0d);
        resultJson.put("companyAReturn", 0d);
        resultJson.put("companyBReturn", 0d);
        resultJson.put("companyCReturn", 0d);
        resultJson.put("companyDReturn", 0d);
        resultJson.put("returnServiceMoney", 0d);
        List<Map> bgOrder = iCrmProductMapper.bgEfOrder(crmOrderId);
            resultJson.putAll(bgOrder.get(0));
            //根据还款记录里 获取 已还的 本息(本金 + 利息 + 管理费)
            List<Map> list0 = crmPayrecoderMapper.getShouldMoney(crmOrderId);
            List<Map> list1= crmPaycontrolMapper.getShouldMoney(crmOrderId);
            List<Map> list2=crmPaycontrolMapper.countShouldDuetime(crmOrderId);
            List<Map> list3=crmPaycontrolMapper.shouldCapitalManageMoney(crmOrderId);
            double alreadyRepaidMoney=Double.parseDouble(list0.get(0).get("should_money").toString());//已还总额(本金 + 利息 + 管理费)
            double shouldMoney=Double.parseDouble(list1.get(0).get("shouldMoney").toString());//应还总额(本金 + 利息 + 管理费)

            //String s1="select count(1) as daihuanqishu from crm_paycontrol where crm_order_id = '" + crmOrderId + "' and status=0";
            //待还期数
            List<Map> listS1 = crmPaycontrolMapper.getPerPayPeriods(crmOrderId);
            double daihuanqishu=Double.parseDouble(listS1.get(0).get("perPayeriods").toString());
            List<Map> listS2 = crmPaycontrolMapper.getAllPeriods(crmOrderId);
            //String s2="select count(1) as allqishu from crm_paycontrol where crm_order_id = '" + crmOrderId + "' and status in (0,2)";
            //逾期和待还期数
            double allqishu=Double.parseDouble(listS2.get(0).get("AllPeriods").toString());
            if (daihuanqishu>0) {
                allqishu=allqishu-daihuanqishu+1;	//逾期和待还期数-待还期数+本期
                daihuanqishu=daihuanqishu-1;		//待还期数-本期=剩余期数
            }
            //String s3="select should_money from crm_paycontrol where crm_order_id = '" + crmOrderId + "' limit 1";
            //分期等额
            List<Map> listS3 = crmPaycontrolMapper.getOneShouldMoney(crmOrderId);
            double should_money=Double.parseDouble(listS3.get(0).get("should_money").toString());
            //String s4="select sum(a.should_money) remainMoney from crm_paycontrol a where a.crm_order_id = '" + crmOrderId + "' and a.status in (0,2)";
            //应还金额
//			double remainMoney=getDoubleVaule(s4,"remainMoney");
            //剩余本金=合同金额/借款期数*剩余期数
            int periods=resultJson.getInteger("periods");
            double contractMoney=resultJson.getDouble("contractMoney");
            double remainCapital=ArithUtil.mul(ArithUtil.div(contractMoney,periods),daihuanqishu);
            //往期应还剩余金额=分期等额*当期应还+剩余本金
//			resultJson.put("totalCapital", ArithUtil.getTwoNumber(ArithUtil.add(ArithUtil.sub(remainMoney,ArithUtil.mul(should_money,allqishu)),remainCapital)));
            //剩余应支付本金
            double totalCapital=ArithUtil.add(remainCapital,ArithUtil.mul(should_money,allqishu));
            //服务费率scrate
            Double scrate=(Double) resultJson.get("scrate");
            if (!StringUtils.isEmpty(scrate) && scrate==0) {
                //服务费为0，收取提前结清违约金：合同金额*10%
                totalCapital+=ArithUtil.mul(contractMoney,0.10);
            }
            //String s5 = "select should_money from crm_paycontrol where crm_order_id = '" + crmOrderId + "' order by repayment_duetime desc limit 1";
            List<Map> listS5 = crmPaycontrolMapper.getOneShouldMoney(crmOrderId);
            // 减掉最后一期相差的金额
            double last_should_money = Double.parseDouble(listS5.get(0).get("should_money").toString());
            double middle = ArithUtil.sub(should_money, last_should_money);
            totalCapital = ArithUtil.sub(totalCapital, middle);
            resultJson.put("totalCapital", ArithUtil.getTwoNumber(totalCapital));
            //未结清期数 = 已结清明细 统计-明细 统计
            //剩余利息小计 = 总本息 - 已还本息 - (本金+管理费)*未结清期数
			/*double surplusInterest = ArithUtil.sub(ArithUtil.sub(shouldMoney, alreadyRepaidMoney), ArithUtil.mul(shouldCapitalManageMoney, ArithUtil.sub(countDuetime, countShouldDuetime)));
			if (surplusInterest < 0) {
				surplusInterest=0;
				resultJson.put("totalAccrual", "0.00");
			}else{
				resultJson.put("totalAccrual", ArithUtil.getTwoNumber(surplusInterest));
			}*/
            //本息 重新 计算
//			resultJson.put("totalCapital", ArithUtil.getTwoNumber(ArithUtil.sub(ArithUtil.sub(shouldMoney, alreadyRepaidMoney), surplusInterest)));//剩余应支付本金
            resultJson.put("paidCapitalMoney", ArithUtil.getTwoNumber(alreadyRepaidMoney));//已还本金(本金+利息+管理费)
            resultJson.put("shouldCapital", ArithUtil.getTwoNumber(shouldMoney));//借款金额(本金+利息+管理费)
            JSONObject removeJson =new JSONObject();
            removeJson.put("clearing_channel", "");
            JsonTool.format(resultJson,removeJson);
            return resultJson;
    }

    /**
     * 功能说明：获得对应的提前结清违约金的利率
     * @param 
     * @return
     * @throws 
     * 创建人 wangmin
     * 创建日期：2017年7月6日
     * 最后修改时间：
     * 修改人：
     * 修改内容：
     * 修改注意点：
     */
    private double getAdvanceClearRate(Map map,long days){
        //小于或者等于初始天数
        if(days <= Long.parseLong(map.get("violate_initial_day").toString())){
            return Double.parseDouble(map.get("violate_initial_rate").toString())/100d;
        }
        if(days >= Long.parseLong(map.get("violate_transcend_begin_day").toString()) &&
                days <= Long.parseLong(map.get("violate_transcend_end_day").toString())){
            return Double.parseDouble(map.get("violate_transcend_rate").toString())/100d;
        }
        return Double.parseDouble(map.get("violate_by_rate").toString())/100d;
    }
}
