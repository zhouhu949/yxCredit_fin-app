package com.zw.rule.web.customer.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zw.base.util.DateUtils;
import com.zw.base.util.HttpClientUtil;
import com.zw.rule.core.Response;
import com.zw.rule.customer.po.*;
import com.zw.rule.customer.service.CustomerAuditService;
import com.zw.rule.customer.service.CustomerService;
import com.zw.rule.customer.service.OrderService;
import com.zw.rule.entity.CreditRequest;
import com.zw.rule.entity.CreditRequestParam;
import com.zw.rule.mybatis.ParamFilter;
import com.zw.rule.orderLog.service.OrderLogService;
import com.zw.rule.po.User;
import com.zw.rule.product.service.ICrmProductService;
import com.zw.rule.ruleResult.po.RuleResult;
import com.zw.rule.ruleResult.service.RuleResultService;
import com.zw.rule.service.DictService;
import com.zw.rule.util.CommonUtil;
import com.zw.rule.util.NonceUtil;
import com.zw.rule.util.flow.service.FlowComService;
import com.zw.rule.web.aop.annotaion.WebLogger;
import com.zw.rule.web.util.PageConvert;
import com.zw.rule.web.util.PropertiesUtil;
import com.zw.rule.web.util.UserContextUtil;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/******************************************************
 *Copyrights @ 2017，zhiwang  Co., Ltd.
 *
 *All rights reserved.
 *          小窝
 *Filename：
 *         客户审核controller
 *Description：
 *		  未知
 *Author:
 *		 李开艳
 *Finished：
 *		2017/6/21
 ********************************************************/
@Controller
@RequestMapping("customerAudit")
public class CustomerAuditController {

    @Resource
    private OrderService orderService;
    @Resource
    private CustomerAuditService customerAuditService;
    @Autowired
    private OrderLogService orderLogService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private ICrmProductService crmProductService;
    @Autowired
    private FlowComService flowComService;
    @Resource
    private DictService dictService;

    @Resource
    private RuleResultService ruleResultService;

    /**
     * 订单列表（只是查看）
     * @return
     */
    @GetMapping("orderPage")
    public String orderPage(){
        return "orderManage/orderList";
    }

    @GetMapping("listPage")
    public String list(String leftStatus){
     //   UserContextUtil.setAttribute("leftStatus",leftStatus);
        return "customerManage/customerAudit";
    }

    @GetMapping("details")
    public ModelAndView details(String orderId, String customerId) throws  Exception{
        ModelAndView modelAndView = new ModelAndView("customerManage/headquartersReview");
          Order order = orderService.selectById(orderId);
          modelAndView.addObject("order",order);
        return  modelAndView;
    }

    @PostMapping("list")
    @ResponseBody
    public Response list(@RequestBody ParamFilter queryFilter){
        Map map=queryFilter.getParam();
        //查询已处理的
        if ("2".equals(queryFilter.getParam().get("isLock"))){
            User user = (User) UserContextUtil.getAttribute("currentUser");
            map.put("handlerId",user.getUserId());
        }
        map.put("orgid", UserContextUtil.getOrganId());
        map.put("account", UserContextUtil.getAccount());
        //获取订单访问权限
        map=orderService.getJurisdiction(map);
        int pageNo = PageConvert.convert(queryFilter.getPage().getFirstIndex(),queryFilter.getPage().getPageSize());
        PageHelper.startPage(pageNo, queryFilter.getPage().getPageSize());
        List list = orderService.getOrderListSP(queryFilter.getParam());
        PageInfo pageInfo = new PageInfo(list);
        return new Response(pageInfo);
    }

    //订单列表
    @PostMapping("getSubmitList")
    @ResponseBody
    public Response getSubmitList(@RequestBody ParamFilter queryFilter){
        Map map=queryFilter.getParam();
        map.put("orgid", UserContextUtil.getOrganId());
        map.put("account", UserContextUtil.getAccount());
        //获取订单访问权限
        map=orderService.getJurisdiction(map);
        int pageNo = PageConvert.convert(queryFilter.getPage().getFirstIndex(),queryFilter.getPage().getPageSize());
        PageHelper.startPage(pageNo, queryFilter.getPage().getPageSize());
        List list = orderService.getSubmitList(map);
        PageInfo pageInfo = new PageInfo(list);
        return new Response(pageInfo);
    }
    @PostMapping("approved")
    @ResponseBody
    @WebLogger("总部审核通过")
    public Response approved(@RequestBody String str) throws Exception{
        User user = (User) UserContextUtil.getAttribute("currentUser");
        Map map = JSONObject.parseObject(str);
        map.put("state","5");
        orderService.updateOrderState(map);
        map.put("result","1");
        map.put("handlerId",user.getUserId());
        map.put("handlerName",user.getTrueName());
        map.put("type","1");
        map.put("nodeId","5");//5是总部审核
        orderService.addApproveRecord(map);
        Map<String,Object> logsMap=new HashedMap();
        logsMap.put("orderId",map.get("id"));
        logsMap.put("handlerId",user.getUserId());
        logsMap.put("handlerName",user.getTrueName());
        logsMap.put("state","5");
        logsMap.put("tache","总部审核");
        logsMap.put("changeValue","总部审核通过");
        orderService.addOrderLogs(logsMap);
        Response response = new Response();
        response.setMsg("审核通过");
        //调app接口推送
        try {
            PropertiesUtil prop = new PropertiesUtil("properties/host.properties");
            String url =prop.get("appHGUrl")+"/orderMessage/orderPushMessage?state=5&orderId="+map.get("id");
            HttpClientUtil.getInstance().sendHttpGet(url);
        }catch (Exception e){}
        return response;
    }

    @PostMapping("backOrder")
    @ResponseBody
    @WebLogger("退回订单")
    public Response backOrder(@RequestBody Map param) throws Exception{
        User user = (User) UserContextUtil.getAttribute("currentUser");
        Map<String, Object> resMap = orderService.backOrder((String)param.get("orderId"), user);
        Response response = new Response();
        response.setMsg((String)resMap.get("msg"));
        return response;
    }

    @PostMapping("approvedSP")
    @ResponseBody
    @WebLogger("总部审核通过商品")
    public Response approvedSP(@RequestBody String str) throws Exception{
        User user = (User) UserContextUtil.getAttribute("currentUser");
        Map map = JSONObject.parseObject(str);
        map.put("alterTime", DateUtils.formatDate(DateUtils.STYLE_10));
        map.put("state","5");
        orderService.updateOrderState(map);
        map.put("result","1");
        map.put("handlerId",user.getUserId());
        map.put("handlerName",user.getTrueName());
        map.put("type","1");
        map.put("nodeId","5");//5是总部审核
        orderService.addApproveRecord(map);
        Map<String,Object> logsMap=new HashedMap();
        logsMap.put("orderId",map.get("id"));
        logsMap.put("handlerId",user.getUserId());
        logsMap.put("handlerName",user.getTrueName());
        logsMap.put("state","5");
        logsMap.put("tache","总部审核");
        logsMap.put("changeValue","总部审核通过");
        orderService.addOrderLogs(logsMap);
        Response response = new Response();
        response.setMsg("审核通过");
        //调app接口推送
        try {
            PropertiesUtil prop = new PropertiesUtil("properties/host.properties");
            String url =prop.get("appHGUrlSP")+"/orderMessage/orderPushMessage?state=3&orderId="+map.get("id");
            HttpClientUtil.getInstance().sendHttpGet(url);
        }catch (Exception e){}
        return response;
    }
    @PostMapping("approvalRefused")
    @ResponseBody
    @WebLogger("总部审核拒绝")
    public Response approvalRefused(@RequestBody String str)throws Exception{
        User user = (User) UserContextUtil.getAttribute("currentUser");
        Map map = JSONObject.parseObject(str);
        map.put("alterTime", DateUtils.formatDate(DateUtils.STYLE_10));
        map.put("state","6");
        orderService.updateOrderState(map);
        map.put("result","0");
        map.put("handlerId",user.getUserId());
        map.put("handlerName",user.getTrueName());
        map.put("type","1");
        map.put("nodeId","5");//5是总部审核
        orderService.addApproveRecord(map);
        Map<String,Object> logsMap=new HashedMap();
        logsMap.put("orderId",map.get("id"));
        logsMap.put("handlerId",user.getUserId());
        logsMap.put("handlerName",user.getTrueName());
        logsMap.put("state","6");
        logsMap.put("tache","总部审核");
        logsMap.put("changeValue","总部审核拒绝");
        orderService.addOrderLogs(logsMap);
        Response response = new Response();
        response.setMsg("审核拒绝");
        PropertiesUtil prop = new PropertiesUtil("properties/host.properties");
        String url =prop.get("appHGUrl")+"/orderMessage/orderPushMessage?state=6&orderId="+map.get("id");
        HttpClientUtil.getInstance().sendHttpGet(url);
        return response;
    }

    @PostMapping("approvalRefusedSP")
    @ResponseBody
    @WebLogger("总部审核拒绝")
    public Response approvalRefusedSP(@RequestBody String str)throws Exception{
        User user = (User) UserContextUtil.getAttribute("currentUser");
        Map map = JSONObject.parseObject(str);
        map.put("state","6");
        orderService.updateOrderState(map);
        map.put("result","0");
        map.put("handlerId",user.getUserId());
        map.put("handlerName",user.getTrueName());
        map.put("type","1");
        map.put("nodeId","5");//5是总部审核
        orderService.addApproveRecord(map);
        Map<String,Object> logsMap=new HashedMap();
        logsMap.put("orderId",map.get("id"));
        logsMap.put("handlerId",user.getUserId());
        logsMap.put("handlerName",user.getTrueName());
        logsMap.put("state","6");
        logsMap.put("tache","总部审核");
        logsMap.put("changeValue","总部审核拒绝");
        orderService.addOrderLogs(logsMap);
        Response response = new Response();
        response.setMsg("审核拒绝");
        PropertiesUtil prop = new PropertiesUtil("properties/host.properties");
        String url =prop.get("appHGUrl")+"/orderMessage/orderPushMessage?state=9&orderId="+map.get("id");
        HttpClientUtil.getInstance().sendHttpGet(url);
        return response;
    }
    @GetMapping("listPagePast")
    public String list1(){
        return "customerManage/customerDetail";
    }
    @PostMapping("alreadyList")
    @ResponseBody
    @WebLogger("查询已处理订单列表")
    public Response alreadyList(@RequestBody ParamFilter queryFilter){
        int pageNo = PageConvert.convert(queryFilter.getPage().getFirstIndex(),queryFilter.getPage().getPageSize());
        PageHelper.startPage(pageNo, queryFilter.getPage().getPageSize());
        List list = orderService.getAlreadyOrders(queryFilter);
        PageInfo pageInfo = new PageInfo(list);
        return new Response(pageInfo);
    }

    @PostMapping("getOrderById")
    @ResponseBody
    @WebLogger("查询一条客户订单")
    public Response getOrderById(@RequestBody String id){
        Order order = orderService.selectById(id);
        return new Response(order);
    }

    @PostMapping("receiveOrder")
    @ResponseBody
    @WebLogger("领取订单")
    public Response receiveOrder(@RequestBody Order order){
        Response response = new Response();
        if(orderService.updateReceiveId(order)){
            Map map = orderService.getTaskId(order.getId());
            orderService.updateTask(map.get("id").toString(),UserContextUtil.getUserId());
            response.setMsg("领取成功！");
        }else{
            response.setCode(1);
            response.setMsg("领取失败！");
        }
        return response;
    }

    @PostMapping("audit")
    @ResponseBody
    @WebLogger("审核客户")
    public Response audit(@RequestBody Map map){
        Response response = new Response();
        if(orderService.firstOrEndAudit(map)){
            orderService.addApproveRecord(map);
            response.setMsg("审核成功！");
        }else{
            response.setCode(1);
            response.setMsg("审核失败！");
        }
        return response;
    }

    @PostMapping("addInvestigation")
    @ResponseBody
    @WebLogger("保存客户通信记录")
    public Response addInvestigation(@RequestBody CustomerInvestigation customerInvestigation){
        Response response = new Response();
        Map map = customerAuditService.addCustomerInvestigation(customerInvestigation);
        if((Boolean) map.get("flag")){
            response.setMsg("保存成功！");
            response.setData(map.get("id"));
        }else{
            response.setCode(1);
            response.setMsg("保存失败！");
        }
        return response;
    }

    @PostMapping("deleteInvestigation")
    @ResponseBody
    @WebLogger("删除客户通信记录")
    public Response deleteInvestigation(@RequestBody String id){
        Response response = new Response();
        if(customerAuditService.deleteCustomerInvestigation(id)){
            response.setMsg("删除成功！");
        }else{
            response.setCode(1);
            response.setMsg("删除失败！");
        }
        return response;
    }



    @PostMapping("addCustomerExamine")
    @ResponseBody
    @WebLogger("添加客户电核信息")
    public Response addCustomerExamine(@RequestBody CustomerExamine customerExamine){
        Response response = new Response();
        String id = UUID.randomUUID().toString();
        customerExamine.setId(id);
        String time = DateUtils.getDateString(new Date());
        customerExamine.setCreatTime(time);
        if(customerAuditService.addCustomerExamine(customerExamine)){
            response.setMsg("保存成功！");
        }else{
            response.setCode(1);
            response.setMsg("保存失败！");
        }
        return response;
    }

    @PostMapping("getMatchingPrice")
    @ResponseBody
    @WebLogger("额度匹配")
    public Response getMatchingPrice(@RequestBody CustomerMatching customerMatching){
        Response response = new Response();
        List list = customerAuditService.getMatchingPrice(customerMatching);
        response.setData(list);
        return response;
    }

    /**
     * 上传反欺诈or拍客资料
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("uploadFile")
    @ResponseBody
    public Response uploadFile(HttpServletRequest request) throws Exception{
        Response response = new Response();
        List list = customerAuditService.uploadCustomerImage(request);
        if(!list.isEmpty()){
            response.setMsg("上传成功！");
            response.setData(list.get(0));
        }else{
            response.setCode(1);
            response.setMsg("上传失败！");
        }
        return response;
    }


    @PostMapping("submitOrderAudit")
    @ResponseBody
    @WebLogger("提交初审")
    public Response submitOrderAudit(@RequestBody Map map) throws Exception{
        Response response = new Response();
        Boolean flag = orderService.submitOrderAudit(map);
        if(flag){
            //推送消息给app
            String orderId = map.get("orderId").toString();
            Map orderParam = (Map)map.get("orderParam");
            String state = orderParam.get("state").toString();
            String tache = orderParam.get("tache").toString();
            PropertiesUtil prop = new PropertiesUtil("properties/host.properties");
            String url = "";
        /*if(state.equals("5")&&tache.equals("1")){
            url =prop.get("url") + "sendmessage?state="+state+"&tache="+tache+"&order_id="+orderId+"&message=初审通过&money="+(String)orderParam.get("firstCredit");
        }else{
            url =prop.get("url") + "sendmessage?state="+state+"&tache="+tache+"&order_id="+orderId+"&message=初审拒绝&money=";
        }*/
            if(state.equals("5")&&tache.equals("0")){
                url =prop.get("url") + "sendmessage?state="+state+"&tache="+tache+"&order_id="+orderId+"&message=初审拒绝&money=";
                HttpClientUtil.getInstance().sendHttpGet(url);
            }
            response.setMsg("保存成功");
        }else{
            response.setCode(1);
            response.setMsg("保存失败！");
        }


        return response;
    }


    @PostMapping("getOrderLogList")
    @ResponseBody
    public Response getOrderLogList(@RequestBody String orderId){
        List list = orderLogService.selectGetOrderLogList(orderId);
        Order order = orderService.selectById(orderId);
        Map map = new HashMap();
        map.put("list",list);
        map.put("order",order);
        return new Response(map);
    }

    @PostMapping("imgDetails")
    @ResponseBody
    public Response imgDetails(@RequestBody String orderId){
        List list = customerAuditService.getOrderImage(orderId);
        Map map = new HashMap();
        map.put("imageList",list);
        return new Response(map);
    }

    @PostMapping("condition")
    @ResponseBody
    @WebLogger("动态加载订单状态到下拉框")
    public Response apendSelected(){
        Response response = new Response();
        List list=dictService.getDetailList("订单状态");
        if (list!=null&&list.size()>0){
            response.setData(list);
        }else {
            return null;
        }
        return response;
    }

    @PostMapping("automation")
    @ResponseBody
    @WebLogger("转自动化审核")
    public Response automation(@RequestBody String orderId) throws Exception{
        PropertiesUtil prop = new PropertiesUtil("properties/host.properties");
        String url =prop.get("ruleUrlSP")+"/szt/credit/application";
        Order order = orderService.selectById(orderId);
        Customer customer=customerService.findOne(order.getCustomerId());
        RuleResult ruleResult=new RuleResult();

        List<CustomerLinkman> linkmanList = customerService.getCustomerLinkMan(customer.getId());

        String taskId = customer.getPhoneTaskId();
        String user_id = customer.getUserId();
        CreditRequest request = new CreditRequest();
        //动作
        request.setAct("query");
        //时间戳
        request.setTs(System.currentTimeMillis());
        //API调用方生成的随机串：8-10位随机字符串(a-z,0-9)仅包含小写字母和数字
        request.setNonce(NonceUtil.nonce(NonceUtil.LOWERCHARANDNUMBER, 8));
        //流水号：唯一标识，每次调用不一样
        String pid=UUID.randomUUID().toString();
        request.setPid(pid);
        ruleResult.setId(UUID.randomUUID().toString());
        ruleResult.setPid(pid);
        ruleResult.setUserId(user_id);
        ruleResult.setOrderId(order.getId());
        ruleResult.setEngineId("288L");
        // 产品ID
        request.setProductId("miaofu");
        //产品名称
        request.setProductName("miaofu");
        //产品系列名称
        request.setProductSeriesName("miaofu");
        //引擎版本，1:获取当前引擎正在运行版本,2:获取当前引擎中对应的版本，默认值：1
        request.setReqtype(1);
        //授权token
        request.setToken("123456");
        //引擎子版本,reqtype=2的时候才需赋值
        //request.setSubversion();
        //是否同步调用，默认值：true
        request.setSync(true);
        request.setEngineId(288L);
        //用户编号
        request.setUid(user_id);
        //签名：sign签名算法:根据act,ts,nonce,pid,uid,token,按顺序用‘,’连接在一起，做md5签名。
        //即sign = md5(act,ts,nonce,pid,uid,token)
        request.setSign(CommonUtil.generateSign(request, "123456"));
        String link_name1="";
        String relationship1="";
        String contact1="";
        String link_name2="";
        String relationship2="";
        String contact2="";
        if (linkmanList.size()>0){
            link_name1=linkmanList.get(0).getLinkName()==null?"":linkmanList.get(0).getLinkName();
            relationship1=linkmanList.get(0).getRelationship()==null?"":linkmanList.get(0).getRelationship();
            contact1=linkmanList.get(0).getContact()==null?"":linkmanList.get(0).getContact();
        }
        if (linkmanList.size()>1){
            link_name2=linkmanList.get(1).getLinkName()==null?"":linkmanList.get(1).getLinkName();
            relationship2=linkmanList.get(1).getRelationship()==null?"":linkmanList.get(1).getRelationship();
            contact2=linkmanList.get(1).getContact()==null?"":linkmanList.get(1).getContact();
        }

        CreditRequestParam param = new CreditRequestParam();
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("taskId", taskId);
        data.put("contact1_name", link_name1);
        data.put("contact1_relation", relationship1);
        data.put("contact1_mobile", contact1);
        data.put("contact2_name", link_name2);
        data.put("contact2_relation", relationship2);
        data.put("contact2_mobile", contact2);
        param.setData(data);
        //=======================
        String idNo = order.getCard();//身份证号码
        String phone = order.getTel();//手机号码
        String name =order.getCustomerName();//姓名
        param.setMobile(phone);
        param.setName(name);
        param.setIdNum(idNo);
        //==================================
        request.setProductSeriesName("test");
        request.setCreditRequestParam(param);
        JSONObject json = (JSONObject) JSONObject.toJSON(request);
        String result;
        String resultText="";
        JSONObject jsonResult;
        Order ord=new Order();
//        try{
//            result  = HttpUtil.doPost(url, json.toString());
//            jsonResult = JSON.parseObject(result);
//            TraceLoggerUtil.info("=========="+result);
//            if("拒绝".equals(jsonResult.getString("result"))){//风控拒绝
//                ruleResult.setRuleJson(jsonResult.toJSONString());
//                ruleResult.setState("拒绝");
//                resultText="自动化审核结果：拒绝";
//                //updateState
//                ord.setId(order.getId());
//                ord.setState("3");
//            }else if("通过".equals(jsonResult.getString("result"))){//风控通过
//                ruleResult.setRuleJson(jsonResult.toJSONString());
//                ruleResult.setState("通过");
//                resultText="自动化审核结果：通过";
//                ord.setId(order.getId());
//                ord.setState("2");
//            }else{//风控异常
//                ruleResult.setState("异常");
//                ruleResult.setRuleJson("285L风控异常");
//                resultText="风控异常";
//                ord.setId(order.getId());
//                ord.setState("4");
//            }
//        }catch (Exception e) {//风控异常
//            ruleResult.setState("异常");
//            ruleResult.setRuleJson("285L风控异常");
//            resultText="自动化审核异常";
//            ord.setId(order.getId());
//            ord.setState("4");
//            e.printStackTrace();
//        }
        //ruleResult.setRuleJson(jsonResult.toJSONString());
        ruleResult.setState("通过");
        resultText="自动化审核结果：通过";
        ord.setId(order.getId());
        ord.setState("2");
        ruleResult.setRuleName("聚信立");
        ruleResult.setCreateTime(DateUtils.getDateString(new Date()));
        ruleResultService.addRuleResult(ruleResult);
        orderService.updateState(ord);
        Response response1=new Response();
        response1.setMsg(resultText);
        return response1;
    }

    @PostMapping("getAnswer")
    @ResponseBody
    @WebLogger("获取信用问答")
    public Response getAnswer(@RequestBody Map param){
        PropertiesUtil prop = new PropertiesUtil("properties/host.properties");
        String url =prop.get("ruleUrlSP")+"/szt/jxlQuestion/getQuestion";
        param.put("url",url);
        Response response = customerAuditService.getAnswer(param);
        return  response;
    }

    @PostMapping("addAnswer")
    @ResponseBody
    @WebLogger("添加信用问答")
    public Response addAnswer(@RequestBody Object param){
        List<Map<String,Object>> list=(List<Map<String,Object>>)param;
        PropertiesUtil prop = new PropertiesUtil("properties/host.properties");
        String url =prop.get("ruleUrlSP")+"/szt/jxlQuestion/submitAnswer";
        return  customerAuditService.addAnswer(list,url);
    }

}
