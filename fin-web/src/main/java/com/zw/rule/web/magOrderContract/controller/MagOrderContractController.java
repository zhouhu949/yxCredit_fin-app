package com.zw.rule.web.magOrderContract.controller;

import com.zw.rule.contract.ContractService;
import com.zw.rule.contract.po.MagOrderContract;
import com.zw.rule.core.Response;
import com.zw.rule.customer.po.Order;
import com.zw.rule.customer.service.OrderService;
import com.zw.rule.product.Fee;
import com.zw.rule.product.service.ICrmProductService;
import com.zw.security.util.AmountToBigUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <strong>Title : <br>
 * </strong> <strong>Description : </strong>@类注释说明写在此处@<br>
 * <strong>Create on : 2017年07月20日<br>
 * </strong>
 * <p>
 * <strong>Copyright (C) zw.<br>
 * </strong>
 * <p>
 *
 * @author department:技术开发部 <br>
 *         username:Administrator <br>
 *         email: <br>
 * @version <strong>zw有限公司-运营平台</strong><br>
 *          <br>
 *          <strong>修改历史:</strong><br>
 *          修改人 修改日期 修改描述<br>
 *          -------------------------------------------<br>
 *          <br>
 *          <br>
 */
@Controller
@RequestMapping("contract")
public class MagOrderContractController {

    @Resource
    private ContractService contractService;
    @Resource
    private OrderService orderService;

    @Resource
    private ICrmProductService crmProductService;

    @RequestMapping("createContract")
    @ResponseBody
    public Response createContract(String orderId) throws Exception {
        try {
            Response response = new Response();
            MagOrderContract magOrderContract = new MagOrderContract();
            Order order = orderService.selectById(orderId);
            if(order==null){
                response.setCode(1);
                response.setMsg("没有该订单！");
                return response;
            }
            List<Map> magCustomerAccountlist = contractService.getMagCustomerAccountByCustomerId(order.getCustomerId());
            if(magCustomerAccountlist.size()!=1){
                response.setCode(1);
                response.setMsg("该客户信息异常");
                return response;
            }
            Map magInfoMap = new HashMap();
            magInfoMap.put("productId",order.getProductName());
            magInfoMap.put("periods",order.getPeriods());
            List<Map> magInfolist = contractService.getInfoByproductId(magInfoMap);
            if(magInfolist.size()!=1){
                response.setCode(1);
                response.setMsg("根据产品id查询利率信息异常");
                return response;
            }
            Map mapParam = new HashMap();
            mapParam.put("productName",order.getProductName());
            mapParam.put("amount",order.getCredit());
            mapParam.put("periods",order.getPeriods());
            Fee fee = crmProductService.getMagFeeList(mapParam);
            if (fee == null ){
                response.setCode(1);
                response.setMsg("根据产品信息查询产品费率表信息异常");
                return response;
            }
//            String productPayment = fee.getProductPayment();
            String productPayment = "";
            Double yearRate = Double.valueOf((String) magInfolist.get(0).get("contract_rate"))*12;
            //获取当前日期，添加期数，获取还款日期
            Date d = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            Calendar ca = Calendar.getInstance();
            int pay = Integer.valueOf(order.getPeriods());
            ca.add(Calendar.DATE, pay);// num添加期数
            d = ca.getTime();
            String payTime = sdf.format(d);
            String payTime1 = payTime.substring(0,4);
            String payTime2 = payTime.substring(4,6);
            String day = payTime.substring(6,8);
            payTime = payTime1+"年"+payTime2+"月"+day+"日";
            String number = sdf.format(d);//生成规则
            String num = number.substring(0, 8);
            String num1 = number.substring(8, 14);
            int num2 = (int) ((Math.random() * 9 + 1) * 10);
            String num3 = num + "-" + num1 + "-" + num2;
            String amount = order.getCredit();
            if(amount==null||"".equals(amount)){
                amount="0";
            }
            magOrderContract.setId(UUID.randomUUID().toString());
            magOrderContract.setMerchant_id(order.getMerchantId());
            magOrderContract.setMerchant_name(order.getMerchantName());
            magOrderContract.setCustomer_name(order.getCustomerName());
            magOrderContract.setCustomerId(order.getCustomerId());
            magOrderContract.setOrder_no(order.getOrderNo());
            magOrderContract.setOrderId(orderId);
            magOrderContract.setContract_amount(amount);//合同总金额即为本金
            magOrderContract.setManager(order.getManager());
            magOrderContract.setContract_no(num3);//合同编号
            magOrderContract.setMonth(order.getPeriods());//期数
            magOrderContract.setAmount(order.getAmount());
            magOrderContract.setUser_id(order.getUserId());
            magOrderContract.setCompany(order.getCompany());
            magOrderContract.setBRANCH(order.getBranch());
            magOrderContract.setOperator(order.getManager());
            magOrderContract.setMerchandise_type(order.getMerchandiseType());
            magOrderContract.setMerchandise_brand(order.getMerchandiseBrand());
            magOrderContract.setMerchandise_model(order.getMerchandiseModel());
            magOrderContract.setEmp_number(order.getEmpNumber());
            magOrderContract.setProduct_type(order.getProductType());
            magOrderContract.setProduct_type_name(order.getProductTypeName());
            magOrderContract.setProduct_name(order.getProductName());
            magOrderContract.setProduct_name_name(order.getProductNameName());
            magOrderContract.setProduct_detail(order.getProductDetail());
            magOrderContract.setProduct_detail_name(order.getProductDetailName());
            magOrderContract.setCustID(order.getCard());
            magOrderContract.setState("1");
            magOrderContract.setInterest(productPayment);//每月还款金额：本金加利息加管理费
            magOrderContract.setBegin_date(payTime);//首次还款的日期
            magOrderContract.setEnd_date(payTime);//结束还款的日期
            magOrderContract.setDay(day);//每月还款日
            magOrderContract.setBank((String) magCustomerAccountlist.get(0).get("account_bank"));//开户银行名称
            magOrderContract.setBank_subbranch((String) magCustomerAccountlist.get(0).get("account_branch_bank"));//支行名称
            magOrderContract.setBank_city((String) magCustomerAccountlist.get(0).get("account_city"));//开户市
            magOrderContract.setAccount((String) magCustomerAccountlist.get(0).get("bank_card"));//银行卡号
            magOrderContract.setAccount_name((String) magCustomerAccountlist.get(0).get("count_name"));//开户人
            magOrderContract.setLoan_amount(order.getCredit());//放款金额
            //magOrderContract.setChannel(order.getChannel());//渠道 chanel
            magOrderContract.setApplay_time(order.getOrderSubmissionTime());//申请日期
            magOrderContract.setProduct_code(order.getProductTypeName());//产品code
            magOrderContract.setPurposeName("购物");//借款途径
            magOrderContract.setMonth_service((String) magInfolist.get(0).get("staging_services_rate"));//月服务费率
            magOrderContract.setInterest_rate((String) magInfolist.get(0).get("interest_rate"));//罚息率
            magOrderContract.setContract_rate((String) magInfolist.get(0).get("contract_rate"));//合同月利率
            magOrderContract.setYearRate(String.valueOf(yearRate));
            magOrderContract.setPayCapialCN(AmountToBigUtil.change(Double.valueOf(amount)));//借款本金大写
            contractService.createContract(magOrderContract);
            response.setData(magOrderContract);
            response.setCode(0);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}

