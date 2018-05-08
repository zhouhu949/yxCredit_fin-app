package com.zw.rule.web.collection.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zw.base.util.DateUtils;
import com.zw.rule.SendMessage.service.SendMessageFactory;
import com.zw.rule.collectionRecord.po.MagWarning;
import com.zw.rule.collectionRecord.service.CollectionRecordService;
import com.zw.rule.core.Response;
import com.zw.rule.customer.po.Order;
import com.zw.rule.customer.service.OrderService;
import com.zw.rule.loanManage.service.LoanManageService;
import com.zw.rule.loanmange.po.SettleRecord;
import com.zw.rule.mybatis.ParamFilter;
import com.zw.rule.po.User;
import com.zw.rule.repayment.service.MagRepaymentExtensionService;
import com.zw.rule.repayment.service.RepaymentService;
import com.zw.rule.transaction.po.TransactionDetails;
import com.zw.rule.web.util.PageConvert;
import com.zw.rule.web.util.UserContextUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("afterLoan")
public class AfterLoanController {
    private static final Logger logger = Logger.getLogger(AfterLoanController.class);
    @Resource
    private CollectionRecordService collectionRecordService;

    @Resource
    private MagRepaymentExtensionService magRepaymentExtensionService;

    @Resource
    private OrderService orderService;

    @Autowired
    private SendMessageFactory sendMessageFactory;

    @Autowired
    private RepaymentService repaymentService;

    @Autowired
    private LoanManageService loanManageService;

//
//    @Resource
//    private RepayManageService repayManageService;

    //客户查询
    @GetMapping("listPage")
    public String list(){
        return "afterLoan/overdueQuery";
    }
    //电话催收
    @GetMapping("listPageTelephone")
    public String listTelephone(){
        return "afterLoan/telephoneCollection";
    }

    //减免审批
    @GetMapping("listPageApproval")
    public String listApproval(){
        return "afterLoan/exemptionApproval";
    }

    //外访催收
    @GetMapping("listPageReminders")
    public String listReminders(){
        return "afterLoan/externalReminders";
    }

    //委外催收
    @GetMapping("listPageEntrusted")
    public String listEntrusted(){
        return "afterLoan/entrustedCollection";
    }

    //委外管理
    @GetMapping("listPageManagement")
    public String listManagement(){
        return "afterLoan/entrustedManagement";
    }

    //展期管理
    @GetMapping("listPageExtension")
    public String listExtension(){
        return "afterLoan/extensionManagement";
    }


    @PostMapping("list")
    @ResponseBody
    public Response list(@RequestBody ParamFilter queryFilter){
        Response response = new Response();
        int pageNo = PageConvert.convert(queryFilter.getPage().getFirstIndex(),queryFilter.getPage().getPageSize());
        PageHelper.startPage(pageNo, queryFilter.getPage().getPageSize());
        List list = collectionRecordService.getAfterLoanList(queryFilter);
        PageInfo pageInfo = new PageInfo(list);
        response.setData(pageInfo);
        return response;
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

    //商品贷减免查询
    @PostMapping("getRepaymentDerateListSP")
    @ResponseBody
    public Response getRepaymentDerateListSP(@RequestBody ParamFilter queryFilter){
        Response response = new Response();
        int pageNo = PageConvert.convert(queryFilter.getPage().getFirstIndex(),queryFilter.getPage().getPageSize());
        PageHelper.startPage(pageNo, queryFilter.getPage().getPageSize());
        List list = collectionRecordService.getRepaymentDerateListSP(queryFilter);
        PageInfo pageInfo = new PageInfo(list);
        response.setData(pageInfo);
        return response;
    }

    //商品贷提前结清还款记录
    @PostMapping("getRepaymentSettleList")
    @ResponseBody
    public Response getRepaymentSettleList(@RequestBody String orderId){
        Response response = new Response();
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("orderId", orderId);
        param.put("state", "1");
        param.put("type", "2");
        param.put("notRepayment", "1");
        List<TransactionDetails> details = repaymentService.transactionDetailsList(param);
        Map<String, Object> settleParam = new HashMap<String, Object>();
        settleParam.put("orderId", orderId);
        settleParam.put("state", "2");
        SettleRecord settle = loanManageService.getSettleList(settleParam);
        Map<String, Object> resMap = new HashMap<String, Object>();
        resMap.put("details", details);
        resMap.put("settle", settle);
        response.setData(resMap);
        return response;
    }

    @PostMapping("loandetaillist")
    @ResponseBody
    public Response loandetaillist(@RequestBody ParamFilter queryFilter){
        Response response = new Response();
        int pageNo = PageConvert.convert(queryFilter.getPage().getFirstIndex(),queryFilter.getPage().getPageSize());
        PageHelper.startPage(pageNo, queryFilter.getPage().getPageSize());
        List list = collectionRecordService.getLoandetaillist((String)queryFilter.getParam().get("loanId"));
        PageInfo pageInfo = new PageInfo(list);
        response.setData(pageInfo);
        return response;
    }

    @PostMapping("warningInfo")
    @ResponseBody
    public Response warningInfo(String loanId){
        Response response = new Response();
        MagWarning magWarning = collectionRecordService.getWarningByLoanId(loanId);
        response.setData(magWarning);
        return response;
    }


    //电催list
    @PostMapping("listTelephone")
    @ResponseBody
    public Response listTelephone(@RequestBody ParamFilter queryFilter){
        Response response = new Response();
        User user=(User) UserContextUtil.getAttribute("currentUser");
        int pageNo = PageConvert.convert(queryFilter.getPage().getFirstIndex(),queryFilter.getPage().getPageSize());
        PageHelper.startPage(pageNo, queryFilter.getPage().getPageSize());
        Map mapParam=queryFilter.getParam();
        mapParam.put("userId_electric",user.getUserId());
        mapParam.put("state","0");
        mapParam.put("examination_state","1");
        List list = collectionRecordService.getTelephoneList(queryFilter.getParam());
        PageInfo pageInfo = new PageInfo(list);
        response.setData(pageInfo);
        return response;
    }

    //外催list
    @PostMapping("listReminders")
    @ResponseBody
    public Response listReminders(@RequestBody ParamFilter queryFilter){
        Response response = new Response();
        User user=(User) UserContextUtil.getAttribute("currentUser");
        int pageNo = PageConvert.convert(queryFilter.getPage().getFirstIndex(),queryFilter.getPage().getPageSize());
        PageHelper.startPage(pageNo, queryFilter.getPage().getPageSize());
        Map mapParam=queryFilter.getParam();
        mapParam.put("userId_external",user.getUserId());
        mapParam.put("state","0");
        mapParam.put("external_auditor_state","1");
        List list = collectionRecordService.getTelephoneList(queryFilter.getParam());
        PageInfo pageInfo = new PageInfo(list);
        response.setData(pageInfo);
        return response;
    }

    //委外list
    @PostMapping("listEntrusted")
    @ResponseBody
    public Response listEntrusted(@RequestBody ParamFilter queryFilter){
        Response response = new Response();
        User user=(User) UserContextUtil.getAttribute("currentUser");
        int pageNo = PageConvert.convert(queryFilter.getPage().getFirstIndex(),queryFilter.getPage().getPageSize());
        PageHelper.startPage(pageNo, queryFilter.getPage().getPageSize());
        Map mapParam=queryFilter.getParam();
        //mapParam.put("userId_external",user.getUserId());
        mapParam.put("state","0");
        mapParam.put("entrust_state","1");
        List list = collectionRecordService.getTelephoneList(queryFilter.getParam());
        PageInfo pageInfo = new PageInfo(list);
        response.setData(pageInfo);
        return response;
    }

    //减免审批
    @PostMapping("listApproval")
    @ResponseBody
    public Response ApprovalList(@RequestBody ParamFilter queryFilter){
        Response response = new Response();
        User user=(User) UserContextUtil.getAttribute("currentUser");
        int pageNo = PageConvert.convert(queryFilter.getPage().getFirstIndex(),queryFilter.getPage().getPageSize());
        PageHelper.startPage(pageNo, queryFilter.getPage().getPageSize());
        Map mapParam=queryFilter.getParam();
        mapParam.put("userId",user.getUserId());
        mapParam.put("state","0");
        mapParam.put("examination_state","1");
        List list = collectionRecordService.getApprovalList(queryFilter.getParam());
        PageInfo pageInfo = new PageInfo(list);
        response.setData(pageInfo);
        return response;
    }

    //减免审批
    @PostMapping("updateApproval")
    @ResponseBody
    public Response updateApproval(@RequestBody Map param){
        Response response = new Response();
        User user=(User) UserContextUtil.getAttribute("currentUser");
        param.put("userId",user.getUserId());
        param.put("state","1");
        param.put("approval_date", DateUtils.getCurrentTime(DateUtils.STYLE_5));
        if(collectionRecordService.updateApproval(param)>0){
            response.setMsg("提交成功！");
        }else{
            response.setMsg("提交失败！");
        }
        return response;
    }

    //展期list
    @PostMapping("listExtension")
    @ResponseBody
    public Response listExtension(@RequestBody ParamFilter queryFilter){
        Response response = new Response();
        int pageNo = PageConvert.convert(queryFilter.getPage().getFirstIndex(),queryFilter.getPage().getPageSize());
        PageHelper.startPage(pageNo, queryFilter.getPage().getPageSize());
        Map mapParam=queryFilter.getParam();
        List<Map<String, Object>> list = magRepaymentExtensionService.getOrderExtensions(mapParam);
        PageInfo pageInfo = new PageInfo(list);
        response.setData(pageInfo);
        return response;
    }

    //增加展期
    @PostMapping("repaymentExtension")
    @ResponseBody
    public Response repaymentExtension(@RequestBody Map param){
        Response response = new Response();
        User user=(User) UserContextUtil.getAttribute("currentUser");
        param.put("operator",String.valueOf(user.getUserId()));
        param.put("operate_time", DateUtils.getCurrentTime(DateUtils.STYLE_10));
        try {
            Map<String, Object> res = magRepaymentExtensionService.saveExtension(param);
            String msg = "展期失败！";
            int code = 1;
            if (null  == res || (Integer)res.get("code") != 0)
            {
                if (null != res.get("msg"))
                {
                    msg = (String)res.get("msg");
                }
            }
            else
            {
                msg = "展期成功！";
                code = 0;
            }
            response.setCode(code);
            response.setMsg(msg);
        } catch (ParseException e) {
            logger.error(e);
            response.setCode(1);
            response.setMsg("展期失败！");
        }
        return response;
    }

    //外访催收分配
    @PostMapping("updateCollection")
    @ResponseBody
    public Response updateCollection(@RequestBody Map addParam) {
        Response response = new Response();
        if(collectionRecordService.updateCollectionExternal(addParam)>0){
            response.setMsg("分配成功！");
        }else{
            response.setMsg("分配失败！");
        }
        return response;
    }
    //电话催收分配
    @PostMapping("setCollection")
    @ResponseBody
    public Response setCollection(@RequestBody Map addParam) {
        Response response = new Response();
        if(collectionRecordService.setCollection(addParam)>0){
            response.setMsg("分配成功！");
        }else{
            response.setMsg("分配失败！");
        }
        return response;
    }
    //委外催收分配
    @PostMapping("updateEntrusted")
    @ResponseBody
    public Response updateEntrusted(@RequestBody Map param) {
        Response response = new Response();
        if(collectionRecordService.updateEntrusted(param)>0){
            response.setMsg("分配成功！");
        }else{
            response.setMsg("分配失败！");
        }
        return response;
    }
    //委外操作状态
    @PostMapping("updateEntrustedState")
    @ResponseBody
    public Response updateEntrustedState(@RequestBody Map param) {
        Response response = new Response();
        if(collectionRecordService.updateEntrustedState(param)>0){
            response.setMsg("保存成功！");
        }else{
            response.setMsg("保存失败！");
        }
        return response;
    }
    @PostMapping("orderSettle")
    @ResponseBody
    public Response orderSettle(@RequestBody Map param) {
        Response response = new Response();
        //减免金额添加
        if (param.get("derate_id")==null||"".equals(param.get("derate_id"))){
            if (param.get("reductionAmount")!=null){
                User user=(User) UserContextUtil.getAttribute("currentUser");
                param.put("derate_personnel",user.getUserId());
                //添加费用减免
                if (collectionRecordService.addDerate(param)>0){
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
            if (collectionRecordService.updateApproval(param)>0){
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

    //获取还款详情
    @PostMapping("getAfterLoanDetails")
    @ResponseBody
    public Response getAfterLoanDetails(@RequestBody Map param) {
        Response response = new Response();
        List list = collectionRecordService.getAfterLoanDetails(param);
        if (list.size()>0){
            response.setData(list.get(0));
        }else {
            response.setData(null);
        }
        return response;
    }

    //获取减免详情
    @PostMapping("getDerateDetails")
    @ResponseBody
    public Response getDerateDetails(@RequestBody Map param) {
        Response response = new Response();
        Map map = collectionRecordService.getMagDerateDetail(param);
        response.setData(map);
        return response;
    }

    /**
     * 单期线下还款
     * @param param
     * @return
     */
    @PostMapping("getXianxiaMoney")
    @ResponseBody
    @Transactional
    public Response getXianxiaMoney(@RequestBody Map param) {
        Response response = new Response();
        int i=collectionRecordService.payForXianXiaImpl(param);
        response.setMsg("线下还款成功");
        //发送消息
        Map msgParam=new HashMap();
        msgParam.put("payCount"  ,param.get("payCount").toString());//payCount 期数
        msgParam.put("realMoney",param.get("sumAmount").toString());//realMoney 实际还款金额
        Map<String, Object> sendRes = sendMessageFactory.sendMessage(2, "4", param.get("orderId").toString(), msgParam);
        return response;
    }
}
