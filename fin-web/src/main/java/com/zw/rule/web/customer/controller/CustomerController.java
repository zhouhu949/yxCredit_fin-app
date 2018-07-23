package com.zw.rule.web.customer.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zw.base.util.DateUtils;
import com.zw.rule.ApiResult.service.ApiResultService;
import com.zw.rule.SendMessage.service.SendMessageFactory;
import com.zw.rule.answer.po.Answer;
import com.zw.rule.core.Response;
import com.zw.rule.customer.po.*;
import com.zw.rule.customer.service.CustomerService;
import com.zw.rule.customer.service.OrderService;
import com.zw.rule.customer.service.RuleRiskService;
import com.zw.rule.excel.ExcelService;
import com.zw.rule.loanClient.service.LoanClientService;
import com.zw.rule.mapper.OfficeClerkManager.OfficeClerkManageMapper;
import com.zw.rule.mybatis.ParamFilter;
import com.zw.rule.officeClerk.service.OfficeClerkManageService;
import com.zw.rule.officeClerkEntity.Branch;
import com.zw.rule.officeClerkEntity.OfficeClerkManager;
import com.zw.rule.orderOperationRecord.service.OrderOperationRecordService;
import com.zw.rule.ruleResult.po.RuleResult;
import com.zw.rule.ruleResult.service.RuleResultService;
import com.zw.rule.rulerisk.po.MagRuleRisk;
import com.zw.rule.score.po.ScoreCard;
import com.zw.rule.score.service.ScoreCardService;
import com.zw.rule.service.DictService;
import com.zw.rule.servicePackage.po.ServicePackageOrder;
import com.zw.rule.upload.service.UploadService;
import com.zw.rule.util.flow.service.FlowComService;
import com.zw.rule.web.aop.annotaion.WebLogger;
import com.zw.rule.web.util.HttpUtil;
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
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("customer")
public class CustomerController {
    @Resource
    private ExcelService excelService;
    @Resource
    private CustomerService customerService;

    @Resource
    private RuleResultService ruleResultService;

    @Resource
    private FlowComService flowComService;

    @Resource
    private DictService dictService;

    @Resource
    private  UploadService uploadService;

    @Resource
    private OfficeClerkManageService officeClerkManageService;

    @Resource
    private OfficeClerkManageMapper officeClerkManageMapper;

    @Resource
    private OrderService orderService;

    @Autowired
    private LoanClientService loanClientService;

    @Autowired
    private SendMessageFactory sendMessageFactory;

    @GetMapping("listPage")
    public String list(){
        return "customerManage/customerList";
    }

    @GetMapping("listPage1")
    public String list1(){
        return "customerManage/intentionalCustomerList";
    }

    @Autowired
    private RuleRiskService ruleRiskService;
    @Autowired
    private ScoreCardService scoreCardService;

    @Autowired
    private ApiResultService apiResultService;

    @Autowired
    private OrderOperationRecordService orderOperationRecordService;




    @PostMapping("quotaList")
    @ResponseBody
    @WebLogger("查询客户提额信息")
    public Response quotaList(@RequestBody ParamFilter queryFilter){
        int pageNo = PageConvert.convert(queryFilter.getPage().getFirstIndex(),queryFilter.getPage().getPageSize());
        PageHelper.startPage(pageNo, queryFilter.getPage().getPageSize());
        List list = customerService.getQuotaList(queryFilter);
        PageInfo pageInfo = new PageInfo(list);
        return new Response(pageInfo);
    }

    @PostMapping("updatePwd")
    @ResponseBody
    @WebLogger("修改客户密码")
    public Response updatePwd(@RequestBody Map param){
        Response response= new Response();
        customerService.updatePwd(param);
        response.setMsg("修改成功！");
        return response;
    }

    /**
     * 风险评估
     *
     * 获取通讯时长（规则引擎接口出入参）
     *入参：
     * priority：优先级
     * pid：唯一标识
     * subPid：子标识
     * userId：用户id
     * name：姓名
     * idNo：身份证号
     * phone：手机号码
     *
     * 返参：
     * bizResponseCode：业务返回编码[0:操作成功,1:查得,2:未查得,3:其他原因未查得]
     * pyIsNameMatchOperato：姓名是否一致[1:是,0:否,-1:无此记录]
     * pyIsIdNoMatchOperator：证件号码是否一致[1:是,0:否,-1:无此记录]
     * pyPhoneOnlineTime：在网时长[1:(0-3],2:(3-6],3:(6-12],4:(12-24],5:(24-],-1:无此记录]
     * pyPhoneStatus：手机状态[1：正常在用 ,2:停机,3:未启用, 4：已销号, 5：其他  6：预销号(该值可能为空),-1:无此记录]
     * responseCode：接口返回码[0：操作成功,1:操作失败,100001:系统错误,100002:数据源访问出错,100003:数据源连接超时,100004:数据源输入参数验证失败,100005:数据源内部错误,100006:验签失败,100007:输入参数错误,100008:短信验证码输入错误或超时,请重新输入,100009:未查询到数据]
     * responseMsg：接口返回信息
     * bizResponseMsg：业务返回信息
     * data：消息体
     * originalData：原始数据
     *
     * */
    @PostMapping("getRisk")
    @ResponseBody
    public Response getRisk(String customerId) throws Exception{
        //获取当前的登录用户
        //String userId = (String) UserContextUtil.getAttribute("userId");
        //根据登录用户获取登录用户的信息
        Map map1 = customerService.getCustomerInfo(customerId);
        Response response = new Response();
        PropertiesUtil prop = new PropertiesUtil("properties/host.properties");
        String url = prop.get("gzyqurl");
        Map map = new HashMap();
        map.put("pid",UUID.randomUUID()+"");
        map.put("subPid","1001");
        map.put("userId",customerId);
        map.put("idNo",map1.get("card"));
        map.put("name",map1.get("name"));
        map.put("phone",map1.get("tel"));
        map.put("priority",1);

        //判断数据库是否存在rule_risk数据如果没有就调用，否则不调用
        if(excelService.getRuleRisk((String)map1.get("card"))){
            //获取通讯时长
            String result1  = HttpUtil.doGet(url+"mobileCheckAndStatusInfo/", map);
            JSONObject jsonObject1 = JSONObject.parseObject(result1);
            //获取风险评估
            map.put("pid",UUID.randomUUID()+"");
            map.put("needOriginalData",false);
            String result2  = HttpUtil.doPost(url+"personRiskCount/", JSONObject.toJSONString(map));
            //{"responseCode":"0","responseMsg":"操作成功","bizResponseMsg":null,"data":null,"originalData":null,"bizResponseCode":"1","alCount":0,"zxCount":0,"sxCount":0,"swCount":0,"cqggCount":0,"wdyqCount":3}
            JSONObject jsonObject2 = JSONObject.parseObject(result2);

            //向rule_risk表中插入数据
            MagRuleRisk magRuleRisk = new MagRuleRisk();
            magRuleRisk.setId(UUID.randomUUID().toString());
            magRuleRisk.setCradNo((String) map1.get("card"));
            magRuleRisk.setPhoneTime(jsonObject1.getString("OnlineTime"));
            magRuleRisk.setPyDistress(jsonObject2.getString("cqggCount"));
            magRuleRisk.setPyTax(jsonObject2.getString("swCount"));
            magRuleRisk.setPyJudCase(jsonObject2.getString("alCount"));
            magRuleRisk.setPyJudDishonesty(jsonObject2.getString("sxCount"));
            magRuleRisk.setPyJudEnfor(jsonObject2.getString("zxCount"));
            magRuleRisk.setPyNetLoanOver(jsonObject2.getString("wdyqCount"));
            ruleRiskService.insertRuleRisk(magRuleRisk);

            Map res = new HashMap();
            res.put("phoneResk",jsonObject1);
            res.put("resk",jsonObject2);
            response.setData(res);
        }
        return response;
    }
    @PostMapping("riskdetails")
    @ResponseBody
    @WebLogger("查询风险评估信息")
    public Response getRiskDetials(String cardNum){
        Response response=new Response();
        RuleRisk ruleRisk=ruleRiskService.getRuleRisk(cardNum);
        ScoreCard scoreCard=scoreCardService.getByIdCard(cardNum);
        Map map=scoreCardService.getRefuse(cardNum);
        Map resMap=new HashMap();
        resMap.put("ruleRisk",ruleRisk);
        resMap.put("scoreCard",scoreCard);
        resMap.put("refuse",map);
        response.setData(resMap);
        return response;
    }

    @PostMapping("insertTask")
    @ResponseBody
    public Response insertTask(String relId,String orderId) throws  Exception{
        flowComService.getProcessByProId(relId,orderId,UserContextUtil.getUserId(),null);
        return  new Response();
    }

    @PostMapping("changeTel")
    @ResponseBody
    @WebLogger("修改客户手机号")
    public Response changeTel(@RequestBody Map param){
        Response response= new Response();
        boolean b= customerService.changeCustomerUserTel(param);
        if(b){
            response .setMsg("操作成功");
            response.setCode(0);//表示成功
        }else{
            response.setCode(1);//表示异常
            response .setMsg("该手机号已存在");
        }
        return response;
    }

    @GetMapping("getTelephoneRecord")
    @ResponseBody
    @WebLogger("获取运营商报告")
    public ModelAndView getTelephoneRecord( String customerId,String orderId){
        ModelAndView modelAndView = new ModelAndView("customerManage/telephoneRecord");
        Customer customer= customerService.getCustomer(customerId);
        PropertiesUtil prop = new PropertiesUtil("properties/host.properties");
        Map<String,Object> mapRule=new HashedMap();
        //访问风控的参数
        Order order= orderService.selectById(orderId);
        OfficeClerkManager officeClerkManager=officeClerkManageMapper.selectOneManagerById(order.getEmpId());

//      mapRule.put();//此时需要放入公司id，但是我们获取到的可能是部门id 也可能是公司id
        Branch b=officeClerkManageService.getBranch(officeClerkManager.getBranchId());//部门或者公司实体
        Branch branch=officeClerkManageService.getGongSiName(b);//获取到的公司实体
        if("2".equals(order.getOrderType())){//商品分期是2
            mapRule.put("companyId",branch.getId());//放入公司id
            mapRule.put("companyName",officeClerkManager.getCompany());//公司名
        }else{//现金分期是1
            Map mapXJCompany = customerService.getXJCompany();
            mapRule.put("companyId",mapXJCompany.get("id"));//放入公司id
            mapRule.put("companyName",mapXJCompany.get("dept_name"));//公司名
        }
        mapRule.put("busType",order.getOrderType());
        mapRule.put("phone",customer.getTel());
        mapRule.put("name",customer.getPersonName());
        mapRule.put("idNo",customer.getCard());
        String json="";
        Map<String,Object> phoneBookListMap=new HashedMap();//风控拉取到的话单
        Map<String,Object> phoneMatchingMap=new HashedMap();
        JSONArray phoneBookListArray=JSONObject.parseArray(customer.getPhoneBookList());//customer表里面获取到的联系人信息
        if (phoneBookListArray!=null) {
            for (int i = 0; i < phoneBookListArray.size(); i++) {//将联系人改造成Map<'number','name'>
                phoneBookListMap.put(phoneBookListArray.getJSONObject(i).getString("number"), phoneBookListArray.getJSONObject(i).getString("name"));
            }
        }
        try {
            json = HttpUtil.doGet(prop.get("ruleUrlSP") + "/szt/jxl/creditQuery", mapRule);
//            json =  "";
        } catch (Exception e) {
        }
        if(json!=null && !"".equals(json)){
            modelAndView.addObject("json",json);
            JSONArray  jsonArray=JSON.parseObject(json).getJSONObject("reportData").getJSONArray("contact_list");
            for (int i=0;i<jsonArray.size();i++){
                String phone_num=jsonArray.getJSONObject(i).getString("phone_num");
                String contact_name=phoneBookListMap.get(phone_num)==null?"":phoneBookListMap.get(phone_num).toString();//通讯录名称在联系人map里面获取，不过不存在就是null、
                if (contact_name!=""){
                    jsonArray.getJSONObject(i).put("phoneBookName",contact_name);
                    phoneMatchingMap.put(phone_num,contact_name);
                }
            }
            List<Map<String,Object>> listValue=new ArrayList<>();
            for (int i=0;i<jsonArray.size();i++){
                listValue.add((Map<String, Object>) jsonArray.get(i));
            }
            Collections.sort(listValue,new Comparator<Map<String, Object>>() {
                public int compare(Map<String, Object> o1,Map<String, Object> o2)
                {
                    double q1= Double.parseDouble(o1.get("call_len").toString());
                    double q2=Double.parseDouble(o2.get("call_len").toString());
                    double p=q2-q1;
                    if(p>0){
                        return 1;
                    }
                    else if(p==0){
                        return 0;
                    }
                    else{
                        return -1;
                    }

                }
            });
            //运营商数据分析
            modelAndView.addObject("jsonArray",listValue);
        } else{
            modelAndView.addObject("json",json);
        }
        //运营商数据
        modelAndView.addObject("json",json);
        //通讯录集合条数
        modelAndView.addObject("phoneBookListSize",phoneBookListMap.size());
        //通讯录集合匹配的条数
        modelAndView.addObject("phoneMatchingSize",phoneMatchingMap.size());
        return modelAndView;
    }

    /**
     * 91征信
     * @param customerId
     * @return
     */
    @GetMapping("get91ZX")
    @ResponseBody
    @WebLogger("根据id获取客戶信息跳转頁面")
    public ModelAndView get91ZX( String customerId){
        ModelAndView modelAndView = new ModelAndView("customerManage/91ZXDetailPage");
        Customer customer= customerService.getCustomer(customerId);
        JSONObject customerJson=new JSONObject();
        customerJson.put("customer",customer);
//        String loanInfo=customer.getNinetyOneRule();
//        List loanInfos= (List)((Map)JSON.parse(loanInfo)).get("loanInfo");
//        for(int i=0;i<loanInfos.size();i++){
//            if(loanInfos.get(i)){
//
//            }
//        }
        modelAndView.addObject("customerJson",customerJson);

        return modelAndView;
    }

    /**
     * 同盾
     * @param customerId
     * @return
     */
    @GetMapping("getTonDunZX")
    @ResponseBody
    @WebLogger("跳转同盾报告页面")
    public ModelAndView getTonDunZX( String customerId,String orderId){
        ModelAndView modelAndView = new ModelAndView("customerManage/tongdunReport");
//        Customer customer= customerService.getCustomer(customerId);
        CustomerDeviceInfo customerDeviceInfo = customerService.getCustomerDeviceInfo(orderId);
        JSONObject customerJson=new JSONObject();
        customerJson.put("customerDeviceInfo",customerDeviceInfo);
        modelAndView.addObject("customerJson",customerJson);

        return modelAndView;
    }

    @PostMapping("sendBindingCardMsg")
    @ResponseBody
    @WebLogger("发送重新绑卡消息")
    public Response sendBindingCardMsg(@RequestBody Map param){
        Response response = new Response();
        Map<String, Object> sendRes = sendMessageFactory.sendMessageByUser(2, "1", param.get("userId").toString(), null);
        if (null == sendRes || 0 != (Integer)sendRes.get("code"))
        {
            response.setMsg("发送消息失败");
        }
        else
        {
            response.setMsg("发送消息成功");
        }
        return response;
    }



    /*********************************************碧友信*******************************************************/


    /**
     * 获取客户列表
     * @author 仙海峰
     * @param queryFilter
     * @return
     */
    @PostMapping("list")
    @ResponseBody
    public Response list(@RequestBody ParamFilter queryFilter){
        int pageNo = PageConvert.convert(queryFilter.getPage().getFirstIndex(),queryFilter.getPage().getPageSize());
        PageHelper.startPage(pageNo, queryFilter.getPage().getPageSize());
        System.out.println(queryFilter);
        List list = customerService.getCustomerList(queryFilter);
        PageInfo pageInfo = new PageInfo(list);
        return new Response(pageInfo);
    }

    /**
     * 获取客户列表（包含用户订单数量）
     * @author 仙海峰
     * @param queryFilter
     * @return
     */
    @PostMapping("listO")
    @ResponseBody
    @WebLogger("查询客户列表")
    public Response list1(@RequestBody ParamFilter queryFilter){
        int pageNo = PageConvert.convert(queryFilter.getPage().getFirstIndex(),queryFilter.getPage().getPageSize());
        PageHelper.startPage(pageNo, queryFilter.getPage().getPageSize());
        List list = customerService.getCustomerAndOrder(queryFilter);
        PageInfo pageInfo = new PageInfo(list);
        return new Response(pageInfo);
    }


    /**
     *获取用户详情信息
     * @author 仙海峰
     * @param request
     * @param param
     * @return
     */
    @PostMapping("customerDetails")
    @ResponseBody
    @WebLogger("查询客户详情")
    public Response customerDetails(HttpServletRequest request, @RequestBody Map param){

        String root = request.getSession().getServletContext().getRealPath("/fintecher_file/")+UUID.randomUUID().toString()+".pdf";
        String customerId = param.get("customerId").toString();
        String type = param.get("type").toString();
        String orderId = (String) param.get("orderId");
        Map map = new HashMap();
        //根据客户ID获取客户信息
        Map customer= customerService.getCustomerById(customerId);

        //获取风控审核信息列表
        List apiResultList = apiResultService.getApiResultByOrderId(customerId);

        //客户联系人信息
        List linkmanList = customerService.getCustomerLinkMan(customerId);

        //获取客户绑定银行卡信息
        Map bankCard = loanClientService.getCustBankCardInfoByCustId(customerId);

        //证件材料
        List imgList = customerService.getCustomerImage(customerId);

        map.put("customer",customer);
        map.put("linkmanList",linkmanList);
        map.put("bankCard",bankCard);
        map.put("apiResultList",apiResultList);
        map.put("imgList",imgList);
        return new Response(map);


    }


    /***
     * 获取订单详情页面信息
     * @author 仙海峰
     * @param request
     * @param param
     * @return
     */
    @PostMapping("customerDetailsSP")
    @ResponseBody
    @WebLogger("查询客户详情")
    public Response customerDetailsSP(HttpServletRequest request, @RequestBody Map param){
        String customerId = param.get("customerId").toString();
        String orderId = (String) param.get("orderId");
        Map map = new HashMap();
        //获取订单信息
        Map order = orderService.getOrderAndBank(orderId);

        if(order.containsKey("orderNo") && null != order.get("orderNo")) {
            BusinessRepayment businessRepayment = new BusinessRepayment();
            businessRepayment.setOrderNo(order.get("orderNo").toString());
            List<BusinessRepayment>  businessRepayList = orderService.findListRepayMentByOrderNo(businessRepayment);
            map.put("repayList",businessRepayList);
        }

        //获取风控审核信息列表
        List apiResultList = apiResultService.getApiResultByOrderId(customerId);

        //客户联系人信息
        List linkmanList = customerService.getCustomerLinkMan(customerId);

        //获取审核信息
        Map orderOperationRecord = orderOperationRecordService.getOrderOperationRecordByOrderId(orderId);
        //获取放款信息
        Map loanRecord = orderOperationRecordService.getLoanRecordByOrderId(orderId);

        //证件材料
        List imgList = customerService.getCustomerImage(customerId);

        map.put("apiResultList",apiResultList);
        map.put("linkmanList",linkmanList);
        map.put("orderOperationRecord",orderOperationRecord);
        map.put("loanRecord",loanRecord);
        map.put("order",order);
        map.put("imgList",imgList);
        return new Response(map);

    }
}
