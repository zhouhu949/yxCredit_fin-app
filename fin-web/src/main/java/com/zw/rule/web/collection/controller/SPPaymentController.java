package com.zw.rule.web.collection.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zw.rule.core.Response;
import com.zw.rule.customer.service.OrderService;
import com.zw.rule.mybatis.ParamFilter;
import com.zw.rule.repayment.service.RepaymentService;
import com.zw.rule.web.aop.annotaion.WebLogger;
import com.zw.rule.web.util.PageConvert;
import com.zw.rule.web.util.PropertiesUtil;
import com.zw.rule.web.util.UserContextUtil;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/1/3.
 */
@Controller
@RequestMapping("SPPayment")
public class SPPaymentController {

    @Autowired
    RepaymentService repaymentService;

    @Autowired
    OrderService orderService;
    /**
     * 付款管理页面
     * @return
     */
    @GetMapping("paymentPage")
    public String orderPage(){
        return "afterLoan/paymentDetails";
    }

    @PostMapping("paymentList")
    @ResponseBody
    @WebLogger("查询确认付款明细")
    public Response orderList(@RequestBody ParamFilter paramFilter) throws Exception {
        Map map=paramFilter.getParam();
        map.put("orgid", UserContextUtil.getOrganId());
        map.put("account", UserContextUtil.getAccount());
        //获取订单访问权限
        map=orderService.getJurisdiction(map);
        int pageNo = PageConvert.convert(paramFilter.getPage().getFirstIndex(),paramFilter.getPage().getPageSize());
        PageHelper.startPage(pageNo, paramFilter.getPage().getPageSize());
        List list = repaymentService.getPaymentRepaymentLis(map);
        PageInfo pageInfo = new PageInfo(list);
        return new Response(pageInfo);
    }

    @PostMapping("transactionDetailsList")
    @ResponseBody
    @WebLogger("查询扣款信息")
    public Response transactionDetailsList(@RequestBody ParamFilter paramFilter) throws Exception {
        int pageNo = PageConvert.convert(paramFilter.getPage().getFirstIndex(),paramFilter.getPage().getPageSize());
        PageHelper.startPage(pageNo, paramFilter.getPage().getPageSize());
        List list = repaymentService.transactionDetailsList(paramFilter.getParam());
        PageInfo pageInfo = new PageInfo(list);
        return new Response(pageInfo);
    }

    @PostMapping("costDebit")
    @ResponseBody
    @WebLogger("发起扣款")
    public Response costDebit(String repaymentId) throws Exception {
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
        map.put("repaymentId",repaymentId);
        String responseMsg=repaymentService.costDebit(map);
        response.setMsg(responseMsg);
        return  response;
    }

}
