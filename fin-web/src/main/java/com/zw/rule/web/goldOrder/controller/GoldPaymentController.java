package com.zw.rule.web.goldOrder.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zw.rule.collectionRecord.service.CollectionRecordService;
import com.zw.rule.core.Response;
import com.zw.rule.goldLogistics.service.GoldPaymentService;
import com.zw.rule.loanManage.service.LoanManageService;
import com.zw.rule.mybatis.ParamFilter;
import com.zw.rule.po.User;
import com.zw.rule.web.aop.annotaion.WebLogger;
import com.zw.rule.web.util.PageConvert;
import com.zw.rule.web.util.PropertiesUtil;
import com.zw.rule.web.util.UserContextUtil;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/1/3.
 */
@Controller
@RequestMapping("goldPayment")
public class GoldPaymentController {

    @Autowired
    private GoldPaymentService goldPaymentService;

    @Autowired
    private LoanManageService loanManageService;

    @Resource
    private CollectionRecordService collectionRecordService;

    /**
     * 放款明细
     * @return
     */
    @GetMapping("paymentPage")
    public String orderPage(){
        return "goldOrder/paymentDetails";
    }

    @PostMapping("orderList")
    @ResponseBody
    @WebLogger("查询黄金贷的扣款订单")
    public Response orderList(@RequestBody ParamFilter paramFilter) throws Exception {
        Long orgid = UserContextUtil.getOrganId();
        String account= UserContextUtil.getAccount();
        int pageNo = PageConvert.convert(paramFilter.getPage().getFirstIndex(),paramFilter.getPage().getPageSize());
        PageHelper.startPage(pageNo, paramFilter.getPage().getPageSize());
        List list = goldPaymentService.queryOrders(paramFilter);
        PageInfo pageInfo = new PageInfo(list);
        return new Response(pageInfo);
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
        String responseMsg=goldPaymentService.costDebit(map);
        response.setMsg(responseMsg);
        return  response;
    }

    @PostMapping("transactionDetailsList")
    @ResponseBody
    @WebLogger("查询扣款信息")
    public Response transactionDetailsList(@RequestBody ParamFilter paramFilter) throws Exception {
        int pageNo = PageConvert.convert(paramFilter.getPage().getFirstIndex(),paramFilter.getPage().getPageSize());
        PageHelper.startPage(pageNo, paramFilter.getPage().getPageSize());
        List list = goldPaymentService.transactionDetailsList(paramFilter.getParam());
        PageInfo pageInfo = new PageInfo(list);
        return new Response(pageInfo);
    }

    /**
     *黄金还款查询
     */
    @RequestMapping("goldOverdueQuery")
    public String loanManagePage(){
        return "goldOrder/overdueQuery";
    }


    /**
     * 查询放款表
     * @return
     */

    @RequestMapping("getLoanManage")
    @ResponseBody
    public Response  getLoanManage(@RequestBody ParamFilter queryFilter){
        int pageNo = PageConvert.convert(queryFilter.getPage().getFirstIndex(),queryFilter.getPage().getPageSize());
        PageHelper.startPage(pageNo, queryFilter.getPage().getPageSize());
        Map map=queryFilter.getParam();
        map.put("orgid", UserContextUtil.getOrganId());
        map.put("account", UserContextUtil.getAccount());
        List list = loanManageService.getLoanManageHJD(map);
        PageInfo pageInfo = new PageInfo(list);
        return new Response(pageInfo);
    }

    //减免查询
    @PostMapping("getRepaymentDerateList")
    @ResponseBody
    public Response getRepaymentDerateList(@RequestBody ParamFilter queryFilter){
        Response response = new Response();
        int pageNo = PageConvert.convert(queryFilter.getPage().getFirstIndex(),queryFilter.getPage().getPageSize());
        PageHelper.startPage(pageNo, queryFilter.getPage().getPageSize());
        List list = collectionRecordService.getRepaymentDerateList(queryFilter);
        PageInfo pageInfo = new PageInfo(list);
        response.setData(pageInfo);
        return response;
    }

    //获取还款详情
    @PostMapping("getAfterLoanDetails")
    @ResponseBody
    public Response getAfterLoanDetails(@RequestBody Map param) {
        Response response = new Response();
        List list = collectionRecordService.getAfterLoanDetailsHJD(param);
        if (list.size()>0){
            response.setData(list.get(0));
        }else {
            response.setData(null);
        }
        return response;
    }

    @PostMapping("orderSettle")
    @ResponseBody
    public Response orderSettle(@RequestBody Map param) {
        Response response = new Response();
        //减免金额添加
        if (param.get("derate_id")==null){
            if (param.get("reductionAmount")!=null){
                User user=(User) UserContextUtil.getAttribute("currentUser");
                param.put("derate_personnel",user.getUserId());
                //添加费用减免
                if (collectionRecordService.addDerateHJD(param)>0){
                    response.setMsg("提交成功！");
                }else {
                    response.setMsg("提交失败！");
                }
            }
        }else{
            param.put("derateAmount",param.get("reductionAmount"));
            param.put("state","0");
            param.put("derateId",param.get("derate_id"));
            //修改费用减免
            if (collectionRecordService.updateApprovalHJD(param)>0){
                response.setMsg("提交成功！");
            }else {
                response.setMsg("提交失败！");
            }
        }

//        else {
//            if(collectionRecordService.orderSettle(param)>0){
//                response.setMsg("提交成功！");
//            }else{
//                response.setMsg("提交失败！");
//            }
//            return response;
//        }
        return response;
    }

}
