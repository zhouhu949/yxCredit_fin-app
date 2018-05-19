package com.zw.rule.web.customer.controller;

import com.alibaba.druid.sql.visitor.functions.If;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zw.base.util.DateUtils;
import com.zw.base.util.HttpClientUtil;
import com.zw.rule.approveRecord.po.ProcessApproveRecord;
import com.zw.rule.core.Response;
import com.zw.rule.customer.po.Order;
import com.zw.rule.customer.service.CustomerService;
import com.zw.rule.customer.service.OrderService;
import com.zw.rule.finalOrderAudit.service.IFinalOrderAuditService;
import com.zw.rule.mybatis.ParamFilter;
import com.zw.rule.po.User;
import com.zw.rule.web.aop.annotaion.WebLogger;
import com.zw.rule.web.util.PageConvert;
import com.zw.rule.web.util.PropertiesUtil;
import com.zw.rule.web.util.UserContextUtil;
import org.apache.commons.collections.map.HashedMap;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sound.midi.Soundbank;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/******************************************************
 *Copyrights @ 2017，zhiwang  Co., Ltd.
 *
 *All rights reserved.
 *          小窝
 *Filename：
 *         订单终审controller
 *Description：
 *		  未知
 *Author:
 *		 wangmin
 *Finished：
 *		2017/6/23
 ********************************************************/
@Controller
@RequestMapping("finalAudit")
public class OrderFinalAuditController {

    @Resource
    private IFinalOrderAuditService finalOrderAuditService;

    @Resource
    private OrderService orderService;

    @Resource
    private CustomerService customerService;

    @GetMapping("listPage")
    public String listPage(String leftStatus){
        //   UserContextUtil.setAttribute("leftStatus",leftStatus);
        return "customerManage/customerFinalAudit";
    }

    @PostMapping("orderList")
    @ResponseBody
    @WebLogger("查询确认放款")
    public Response orderList(@RequestBody ParamFilter paramFilter) throws Exception {
        Map map=paramFilter.getParam();
        map.put("orgid", UserContextUtil.getOrganId());
        map.put("account", UserContextUtil.getAccount());
        //获取订单访问权限
        map=orderService.getJurisdiction(map);
        int pageNo = PageConvert.convert(paramFilter.getPage().getFirstIndex(),paramFilter.getPage().getPageSize());
        PageHelper.startPage(pageNo, paramFilter.getPage().getPageSize());
        List list = finalOrderAuditService.queryOrders(map);
        PageInfo pageInfo = new PageInfo(list);
        return new Response(pageInfo);
    }

    @PostMapping("withdrawalList")
    @ResponseBody
    @WebLogger("查询提现信息")
    public Response withdrawalList(@RequestBody ParamFilter paramFilter) throws Exception {
        int pageNo = PageConvert.convert(paramFilter.getPage().getFirstIndex(),paramFilter.getPage().getPageSize());
        PageHelper.startPage(pageNo, paramFilter.getPage().getPageSize());
        List list = finalOrderAuditService.withdrawalList(paramFilter);
        PageInfo pageInfo = new PageInfo(list);
        return new Response(pageInfo);
    }

    //黄金回购放款
    @PostMapping("confirmationLoan")
    @ResponseBody
    @WebLogger("黄金回购确认放款")
    public Response confirmationLoan(String withdrawalsId) throws Exception {
        Response response=new Response();
        Map<String,Object> map= new HashedMap();
        PropertiesUtil prop = new PropertiesUtil("properties/host.properties");
        String pfxPath = ResourceUtils.getFile("classpath:properties/m_pri.pfx").getPath();
        String cerPath = ResourceUtils.getFile("classpath:properties/baofoo_pub.cer").getPath();
        map.put("pfxPath",pfxPath);
        map.put("cerPath",cerPath);
        map.put("memberId",prop.get("baofu.memberId"));
        map.put("terminalId",prop.get("baofu.terminalId"));
        map.put("loanUrl",prop.get("baofu.loanUrl"));
        map.put("debitUrl",prop.get("baofu.debitUrl"));
        map.put("loanQueryUrl",prop.get("baofu.loanQueryUrl"));
        map.put("keyStorePassword",prop.get("baofu.keyStorePassword"));
        map.put("withdrawalsId",withdrawalsId);
        map.put("appHGUrl",prop.get("appHGUrl"));
        String responseMsg=finalOrderAuditService.confirmationLoan(map);
        response.setMsg(responseMsg);
        return  response;
    }

    //商品贷给商户放款
    @PostMapping("confirmationMerchant")
    @ResponseBody
    @WebLogger("商品贷确认放款")
    public Response confirmationMerchant(@RequestBody Map param) throws Exception {
        String orderId=param.get("orderId").toString();
        String fangkuanStyle=param.get("fangkuanStyle").toString();
        User user = (User) UserContextUtil.getAttribute("currentUser");
        Response response=new Response();
        Map<String,Object> map= new HashedMap();
        map.put("handlerId",user.getUserId());
        map.put("handlerName",user.getTrueName());
        PropertiesUtil prop = new PropertiesUtil("properties/host.properties");
        String pfxPath = ResourceUtils.getFile("classpath:properties/m_pri.pfx").getPath();
        String cerPath = ResourceUtils.getFile("classpath:properties/baofoo_pub.cer").getPath();
        map.put("pfxPath",pfxPath);
        map.put("cerPath",cerPath);
        map.put("memberId",prop.get("baofu.memberId"));
        map.put("terminalId",prop.get("baofu.terminalId"));
        map.put("loanUrl",prop.get("baofu.loanUrl"));
        map.put("debitUrl",prop.get("baofu.debitUrl"));
        map.put("loanQueryUrl",prop.get("baofu.loanQueryUrl"));
        map.put("keyStorePassword",prop.get("baofu.keyStorePassword"));
        map.put("orderId",orderId);
        map.put("appHGUrl",prop.get("appHGUrlSP"));
        String responseMsg;
        if("xianxia".equals(fangkuanStyle)){
            responseMsg=finalOrderAuditService.confirmationMerchantXianXia(map);
        }else if("xianshang".equals(fangkuanStyle)){
            responseMsg=finalOrderAuditService.confirmationMerchant(map);
        }else{
            responseMsg="系统异常";
        }
        response.setMsg(responseMsg);
        return  response;
    }

    @PostMapping("costDebit")
    @ResponseBody
    @WebLogger("发起扣款")
    public Response costDebit(String orderId) throws Exception {
        Response response=new Response();
        Map<String,Object> map= new HashedMap();
        PropertiesUtil prop = new PropertiesUtil("properties/host.properties");
        String pfxPath = ResourceUtils.getFile("classpath:properties/m_pri.pfx").getPath();
        String cerPath = ResourceUtils.getFile("classpath:properties/baofoo_pub.cer").getPath();
        map.put("pfxPath",pfxPath);
        map.put("cerPath",cerPath);
        map.put("memberId",prop.get("baofu.memberId"));
        map.put("terminalId",prop.get("baofu.terminalId"));
        map.put("debitUrl",prop.get("baofu.debitUrl"));
        map.put("keyStorePassword",prop.get("baofu.keyStorePassword"));
        map.put("orderId",orderId);
        String responseMsg=finalOrderAuditService.costDebit(map);
        response.setMsg(responseMsg);
        return  response;
    }



    /**
     * 秒付金服提现回调地址 是在易宝人工配置的
     * @return
     * @throws Exception
     */
    @RequestMapping("/ybWithdrawalCallback")
    @ResponseBody
    public void ybWithdrawalCallback(String responseMsg) throws Exception {
        Map<String,Object> map=new HashedMap();
        PropertiesUtil prop = new PropertiesUtil("properties/host.properties");
        String pfxPath = ResourceUtils.getFile("classpath:zhenshu/10000450379.pfx").getPath();
        map.put("mer_id",prop.get("yibao.mer_id"));
        map.put("hmacKey",prop.get("yibao.hmacKey"));
        map.put("pfxPath",pfxPath);
        map.put("responseMsg",responseMsg);
        map.put("batchcallbackurl",prop.get("yibao.batchcallbackurl"));
        map.put("recordcallbackurl",prop.get("yibao.recordcallbackurl"));
        finalOrderAuditService.ybWithdrawalCallbackInfo(map);
    }

    @RequestMapping("/bfWithdrawalCallback")
    @ResponseBody
    protected void bfWithdrawalCallback(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String,Object> map=new HashedMap();
        PropertiesUtil prop = new PropertiesUtil("properties/host.properties");
        String pfxPath = ResourceUtils.getFile("classpath:properties/m_pri.pfx").getPath();
        String cerPath = ResourceUtils.getFile("classpath:properties/baofoo_pub.cer").getPath();
        map.put("keyStorePassword",prop.get("baofu.keyStorePassword"));
        map.put("pfxPath",pfxPath);
        map.put("cerPath",cerPath);
        map.put("memberId",prop.get("baofu.memberId"));
        map.put("terminalId",prop.get("baofu.terminalId"));
        map.put("loanUrl",prop.get("baofu.loanUrl"));
        map.put("debitUrl",prop.get("baofu.debitUrl"));
        map.put("appHGUrl",prop.get("appHGUrl"));
        if (finalOrderAuditService.bfWithdrawalCallback(req,map)){
            resp.setContentType("text/html;charset=UTF-8");
            PrintWriter out = resp.getWriter();
            out.write("OK");
        }
    }
    /**
     * 秒付金服费用扣款回调地址
     * @return
     * @throws Exception
     */
    @RequestMapping("/bindCardPayCallback")
    @ResponseBody
    public void bindCardPayCallback(String responseMsg) throws Exception {
        Map<String,Object> map=new HashedMap();
        map.put("responseMsg",responseMsg);
        finalOrderAuditService.bindCardPayCallback(map);
    }




    @PostMapping("auditOrder")
    @ResponseBody
    public Response auditOrder(@RequestBody Map map) throws Exception {
        Response response = new Response();
        //终审修改数据
        orderService.orderFinalAudit(map);
        //修改订单的状态
        if((Boolean) map.get("flag")){
            int num = finalOrderAuditService.auditOrder((String)map.get("orderId"),(String)map.get("end_credit"),(String)map.get("state"));
            if(num>0){
                Map approveParam = new HashMap();
                //获取订单用户的设备号
                approveParam.put("orderId",map.get("orderId"));
                approveParam.put("customerId",map.get("customerId"));
                approveParam.put("type","1");//1客户id，2商户id
                approveParam.put("state","6");
                approveParam.put("result",map.get("state"));
                approveParam.put("approveSuggestion",map.get("message"));
                approveParam.put("handlerId",String.valueOf(UserContextUtil.getAttribute("userId")));
                approveParam.put("handlerName",String.valueOf(UserContextUtil.getAttribute("nickName")));
                //添加订单的终审记录
                orderService.addApproveRecord(approveParam);
                PropertiesUtil prop = new PropertiesUtil("properties/host.properties");
                String url = "";
                if(((String)map.get("state")).equals("1")){
                    url =prop.get("url")+"/sendmessage?state=6&tache=1&order_id="+map.get("orderId")+"&message=终审审核通过&money="+map.get("end_credit");
                }else if(((String)map.get("state")).equals("0")){
                    url =prop.get("url")+"/sendmessage?state=6&tache=0&order_id="+map.get("orderId")+"&message=终审审核拒绝&money="+map.get("end_credit");
                }
                HttpClientUtil.getInstance().sendHttpGet(url);
                response.setMsg("审批通过！");
            }
        }
        return response;
    }

    @PostMapping("getMessage")
    @ResponseBody
    @WebLogger("获取终审审批意见")
    public Response getMessage(String orderId) throws Exception {
        Response response = new Response();
        //评估结果
        List approveList = finalOrderAuditService.approveList(orderId);
        response.setData(approveList);
        return response;
    }

    @PostMapping("transactionDetailsList")
    @ResponseBody
    @WebLogger("查询放款明细信息")
    public Response transactionDetailsList(@RequestBody ParamFilter paramFilter) throws Exception {
        int pageNo = PageConvert.convert(paramFilter.getPage().getFirstIndex(),paramFilter.getPage().getPageSize());
        PageHelper.startPage(pageNo, paramFilter.getPage().getPageSize());
        List list = finalOrderAuditService.transactionDetailsList(paramFilter.getParam());
        PageInfo pageInfo = new PageInfo(list);
        return new Response(pageInfo);
    }
    /***************************************************碧友信***************************************************/



    /**
     * 获取订单信息和银行信息
     * @author 仙海峰
     * @param orderId
     * @param customerId
     * @return
     * @throws Exception
     */
    @GetMapping("examineDetails")
    public ModelAndView details(String orderId, String customerId) throws  Exception{
        ModelAndView modelAndView = new ModelAndView("customerManage/customerFinalPage");
        Map order = orderService.getOrderAndBank(orderId);
        Map customer = customerService.getCustomerById(customerId);
        List linkmanList = customerService.getCustomerLinkMan(customerId);


        //获取身份证号码

        if(customer !=null){
            String card =customer.get("card").toString();
            //截取出生年月
            card = card.substring(6,14);
            //转化成时间格式
            Date date = DateUtils.strConvertToDate2(DateUtils.STYLE_3, card);
            //算出年龄
            long age = (System.currentTimeMillis()-date.getTime())/(1000*60*60*24*365L);

            customer.put("age",age);
            //获取审核人意见等信息
        }


        modelAndView.addObject("order",order);
        modelAndView.addObject("customer",customer);
        modelAndView.addObject("linkmanList",linkmanList);
        return  modelAndView;
    }



}
