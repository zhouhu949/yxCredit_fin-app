package com.zw.rule.web.goldOrder.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zw.base.util.DateUtils;
import com.zw.base.util.HttpClientUtil;
import com.zw.rule.core.Response;
import com.zw.rule.customer.po.Customer;
import com.zw.rule.customer.po.CustomerLinkman;
import com.zw.rule.customer.po.Order;
import com.zw.rule.customer.service.CustomerService;
import com.zw.rule.customer.service.OrderService;
import com.zw.rule.entity.CreditRequest;
import com.zw.rule.entity.CreditRequestParam;
import com.zw.rule.finalOrderAudit.service.IFinalOrderAuditService;
import com.zw.rule.goldLogistics.service.GoldLogisticsService;
import com.zw.rule.mybatis.ParamFilter;
import com.zw.rule.po.User;
import com.zw.rule.ruleResult.po.RuleResult;
import com.zw.rule.ruleResult.service.RuleResultService;
import com.zw.rule.service.DictService;
import com.zw.rule.util.CommonUtil;
import com.zw.rule.util.NonceUtil;
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
import java.util.*;

/**
 * Created by Administrator on 2017/12/21.
 */
@Controller
@RequestMapping("goldCustomerAudit")
public class GoldOrderController {

    @Resource
    private OrderService orderService;
    @Autowired
    private GoldLogisticsService goldLogisticsService;
    @Autowired
    private CustomerService customerService;
    @Resource
    private DictService dictService;
    @Resource
    private RuleResultService ruleResultService;

    @Resource
    private IFinalOrderAuditService finalOrderAuditService;

    /**
     * 获取黄金订单列表（只是查看）
     * @return
     */
    @GetMapping("orderPage")
    public String orderPage(){
        return "goldOrder/goldOrderList";
    }

    /**
     * 获取黄金提货订单列表
     * @return
     */
    @GetMapping("logisticsPage")
    public String logisticsPage(){
        return "goldOrder/logisticsList";
    }

    /**
     * 获取黄金提货完成订单列表
     * @return
     */
    @GetMapping("logisticsRecordPage")
    public String logisticsRecordPage(){
        return "goldOrder/logisticsRecordList";
    }
    //订单审核页面
    @GetMapping("listPage")
    public String list(String leftStatus){
        //   UserContextUtil.setAttribute("leftStatus",leftStatus);
        return "goldOrder/customerAudit";
    }

    //放款列表
    @GetMapping("loanPage")
    public String loanPage(String leftStatus){
        return "goldOrder/customerFinalAudit";
    }

    //放款记录列表
    @GetMapping("loanRecordPage")
    public String loanRecordPage(String leftStatus){
        return "goldOrder/customerFinalAuditRecord";
    }
    //获取黄金订单列表
    @PostMapping("getSubmitList")
    @ResponseBody
    public Response getSubmitList(@RequestBody ParamFilter queryFilter){
        int pageNo = PageConvert.convert(queryFilter.getPage().getFirstIndex(),queryFilter.getPage().getPageSize());
        PageHelper.startPage(pageNo, queryFilter.getPage().getPageSize());
        List list = orderService.getGoldSubmitList(queryFilter.getParam());
        PageInfo pageInfo = new PageInfo(list);
        return new Response(pageInfo);
    }

    //获取黄金订单发货列表
    @PostMapping("getGoldLogisticsList")
    @ResponseBody
    public Response getGoldLogisticsList(@RequestBody ParamFilter queryFilter){
        int pageNo = PageConvert.convert(queryFilter.getPage().getFirstIndex(),queryFilter.getPage().getPageSize());
        PageHelper.startPage(pageNo, queryFilter.getPage().getPageSize());
        List list = orderService.getGoldLogisticsList(queryFilter.getParam());
        PageInfo pageInfo = new PageInfo(list);
        return new Response(pageInfo);
    }

    @PostMapping("examineList")
    @ResponseBody
    public Response examineList(@RequestBody ParamFilter queryFilter){
        int pageNo = PageConvert.convert(queryFilter.getPage().getFirstIndex(),queryFilter.getPage().getPageSize());
        PageHelper.startPage(pageNo, queryFilter.getPage().getPageSize());
        List list = orderService.getOrderList(queryFilter.getParam());
        PageInfo pageInfo = new PageInfo(list);
        return new Response(pageInfo);
    }
    //订单列表查看
    @GetMapping("examineDetails")
    public ModelAndView examineDetails(String orderId, String customerId) throws  Exception{
        ModelAndView modelAndView = new ModelAndView("goldOrder/customerFinalPage");
        Order order = orderService.selectById(orderId);
        modelAndView.addObject("order",order);
        return  modelAndView;
    }

    @PostMapping("getlogistics")
    @ResponseBody
    @WebLogger("获取发货信息")
    public Response getlogistics(@RequestBody String orderId){
        Response response = new Response();
        Map<String,Object> map=new HashedMap();
        map.put("orderId",orderId);
        List list=goldLogisticsService.getlogisticsByOrderId(map);
        response.setData(list);
        return response;
    }

    @PostMapping("addLogistics")
    @ResponseBody
    @WebLogger("添加发货信息")
    public Response addLogistics(@RequestBody Map param){
        Response response = new Response();
        Long currentUserId = UserContextUtil.getUserId();
        param.put("updateUser",currentUserId);
        goldLogisticsService.addLogistics(param);
        response.setMsg("物流状态更新成功！");
        try {
            PropertiesUtil prop = new PropertiesUtil("properties/host.properties");
            String url =prop.get("appHGUrl")+"/orderMessage/orderPushMessage?state=15&orderId="+param.get("orderId");
            HttpClientUtil.getInstance().sendHttpGet(url);
        }catch (Exception e){

        }
        return response;
    }

    @PostMapping("updateOrderDelivery")
    @ResponseBody
    @WebLogger("确认收货")
    public Response updateOrderDelivery(@RequestBody String orderId){
        Map<String,Object> map=new HashedMap();
        map.put("deliveryState","3");
        map.put("orderId",orderId);
        Response response = new Response();
        goldLogisticsService.updateOrderDelivery(map);
        response.setMsg("物流状态更新成功！");
        try {
            PropertiesUtil prop = new PropertiesUtil("properties/host.properties");
            String url =prop.get("appHGUrl")+"/orderMessage/orderPushMessage?state=17&orderId="+orderId;
            HttpClientUtil.getInstance().sendHttpGet(url);
        }catch (Exception e){

        }
        return response;
    }

    @PostMapping("updateLogistics")
    @ResponseBody
    @WebLogger("修改发货信息")
    public Response updateLogistics(@RequestBody Map param){
        Response response = new Response();
        Long currentUserId = UserContextUtil.getUserId();
        param.put("updateUser",currentUserId);
        goldLogisticsService.updateLogistics(param);
        response.setMsg("物流状态更新成功！");
        try {
            PropertiesUtil prop = new PropertiesUtil("properties/host.properties");
            String url =prop.get("appHGUrl")+"/orderMessage/orderPushMessage?state=16&orderId="+param.get("orderId");
            HttpClientUtil.getInstance().sendHttpGet(url);
        }catch (Exception e){

        }
        return response;
    }
    //审核详情
    @GetMapping("details")
    public ModelAndView details(String orderId, String customerId) throws  Exception{
        ModelAndView modelAndView = new ModelAndView("goldOrder/headquartersReview");
        Order order = orderService.selectById(orderId);
        modelAndView.addObject("order",order);
        return  modelAndView;
    }

    @PostMapping("apendSelected")
    @ResponseBody
    @WebLogger("动态加载物流公司下拉框")
    public Response apendSelected(){
        Response response = new Response();
        List list=dictService.getDetailListByCode("LogisticsCompany");
        if (list!=null&&list.size()>0){
            response.setData(list);
        }else {
            return null;
        }
        return response;
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
        //String taskNodeId = flowComService.getTaskNodeId(map.get("id").toString());
        //flowComService.CommitTask((String)map.get("id"),user.getUserId(),4,taskNodeId,"",null);
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

    @PostMapping("approvalRefused")
    @ResponseBody
    @WebLogger("总部审核拒绝")
    public Response approvalRefused(@RequestBody String str)throws Exception{
        User user = (User) UserContextUtil.getAttribute("currentUser");
        Map map = JSONObject.parseObject(str);
        map.put("state","6");
        orderService.updateOrderState(map);
        //orderService.orderMsgDealWith((String) map.get("id"),"0",(String) map.get("userId"),"");
        map.put("result","0");
        map.put("handlerId",user.getUserId());
        map.put("handlerName",user.getTrueName());
        map.put("type","1");
        map.put("nodeId","5");//5是总部审核
        orderService.addApproveRecord(map);
//        String taskNodeId = flowComService.getTaskNodeId(map.get("id").toString());
//        flowComService.CommitTask((String)map.get("id"),user.getUserId(),3,taskNodeId,"",null);
        Response response = new Response();
        response.setMsg("审核拒绝");
        PropertiesUtil prop = new PropertiesUtil("properties/host.properties");
        String url =prop.get("appHGUrl")+"/orderMessage/orderPushMessage?state=6&orderId="+map.get("id");
        HttpClientUtil.getInstance().sendHttpGet(url);
        return response;
    }

    @PostMapping("automation")
    @ResponseBody
    @WebLogger("转自动化审核")
    public Response automation(@RequestBody String orderId) throws Exception{
        PropertiesUtil prop = new PropertiesUtil("properties/host.properties");
        String url =prop.get("ruleUrl")+"/szt/credit/application";
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
        String pid= UUID.randomUUID().toString();
        request.setPid(pid);
        ruleResult.setId(UUID.randomUUID().toString());
        ruleResult.setPid(pid);
        ruleResult.setUserId(user_id);
        ruleResult.setOrderId(order.getId());
        ruleResult.setEngineId("285L");
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
        request.setEngineId(285L);
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
        ///ruleResult.setRuleJson(jsonResult.toJSONString());
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
        List list = finalOrderAuditService.queryOrdersGold(map);
        PageInfo pageInfo = new PageInfo(list);
        return new Response(pageInfo);
    }
}
