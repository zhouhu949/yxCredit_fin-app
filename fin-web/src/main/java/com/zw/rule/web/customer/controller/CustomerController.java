package com.zw.rule.web.customer.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zw.rule.SendMessage.service.SendMessageFactory;
import com.zw.rule.answer.po.Answer;
import com.zw.rule.core.Response;
import com.zw.rule.customer.po.Customer;
import com.zw.rule.customer.po.CustomerDeviceInfo;
import com.zw.rule.customer.po.Order;
import com.zw.rule.customer.po.RuleRisk;
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

//        public static void main(String args[]) {
//            try {
//                List<String> headList=new ArrayList<>();
//                headList.add("号码");
//                headList.add("号码归属地");
//                headList.add("号码标注");
//                headList.add("需求类别");
//                headList.add("通话次数");
//                headList.add("通话时长");
//                headList.add("呼出次数");
//                headList.add("呼出时间");
//                headList.add("呼入次数");
//                headList.add("呼入时间");
//                headList.add("关系推测");
//                headList.add("最近一周联系次数");
//                headList.add("最近一月联系次数");
//                headList.add("最近三月联系次数");
//                headList.add("三个月以上联系次数");
//                headList.add("凌晨联系次数");
//                headList.add("上午联系次数");
//                headList.add("中午联系次数");
//                headList.add("下午联系次数");
//                headList.add("晚上联系次数");
//                headList.add("是否全天联系");
//                headList.add("周中联系次数");
//                headList.add("周末联系次数");
//                headList.add("节假日联系次数");
//                List<Map<String,Object>> listValue=new ArrayList<>();
//                Map<String,Object> mapRule=new HashedMap();
//
//
//                mapRule.put("phone","18019565938");
//                mapRule.put("name","周军");
//                mapRule.put("idNo","341124198602051430");
//                String jsonPdf= HttpUtil.doGet("http://120.55.51.242:8080/szt/jxl/creditQuery", mapRule);
//                JSONArray  jsonArray=JSON.parseObject(jsonPdf).getJSONObject("reportData").getJSONArray("contact_list");
//                if (jsonArray!=null&&jsonArray.size()>0){
//                    for (int i=0;i<jsonArray.size();i++){
//                        listValue.add((Map<String, Object>) jsonArray.get(i));
//                    }
//                    ContextToPdf.insertPDF(listValue,headList,"C:\\test\\test.pdf");
//                }
//            }catch (Exception e){
//                String SS="";
//            }
//
//        }






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

        //客户联系人信息
        List linkmanList = customerService.getCustomerLinkMan(customerId);

        //获取客户绑定银行卡信息
        Map bankCard = loanClientService.getCustBankCardInfoByCustId(customerId);

        map.put("customer",customer);
        map.put("linkmanList",linkmanList);
        map.put("bankCard",bankCard);
        return new Response(map);


    }


    /***
     * 获取订单详情页面信息
     * @param request
     * @param param
     * @return
     */
    @PostMapping("customerDetailsSP")
    @ResponseBody
    @WebLogger("查询客户详情")
    public Response customerDetailsSP(HttpServletRequest request, @RequestBody Map param){
        String root = request.getSession().getServletContext().getRealPath("/fintecher_file/")+UUID.randomUUID().toString()+".pdf";
        String customerId = param.get("customerId").toString();
       // String type = param.get("type").toString();
        String orderId = (String) param.get("orderId");
        //String salesmanId = (String) param.get("salesmanId");
        Order order=new Order();
        //获取办单员信息
       // OfficeClerkManager salesman=officeClerkManageService.getOneClerkManagerById(salesmanId);
        //String answerScore="0";
        Map map = new HashMap();





        //客户联系人信息
        List linkmanList = customerService.getCustomerLinkMan(customerId);

        //获取客户绑定银行卡信息
        //Map bankCard = loanClientService.getCustBankCardInfoByCustId(customerId);


        map.put("linkmanList",linkmanList);

        return new Response(map);

        /*        Customer customer= customerService.getCustomer(customerId);

        param.put("userId",customer.getUserId());
        //客户联系人信息
        List linkmanList = customerService.getCustomerLinkMan(customerId);
        //客户基本信息
        List personList = customerService.getCustomerPerson(customerId);
        //单位信息
        List occupationList = customerService.getCustomerOccupation(customerId);
        //证件材料
        //List imgList = customerService.getCustomerImage(customerId);
        List imgList = customerService.getCustomerImageOrderId(orderId);*/

  /*      //获取客户答案信息
        List<Answer> answerList=null;
        if("".equals(orderId)||orderId==null){
        }else {
            order=customerService.selectByPrimaryKey(orderId);
            if (order!=null){
                Map<String,Object> mapAnswer=new HashedMap();
                mapAnswer.put("orderId",orderId);
                //信用问卷是否完成
                if ("2".equals(order.getAnswerState())){
                    answerList=customerService.getAnswerList(mapAnswer);
                    //答题分数
                    answerScore=order.getAnswerScore();
                }
            }
        }*/
       /* //服务包信息
        Map<String,Object> servicePackageMap=new HashedMap();
        servicePackageMap.put("orderId",orderId);
        List<ServicePackageOrder>  servicePackageOrderList=customerService.getServicePackageOrderList(servicePackageMap);
        PropertiesUtil prop = new PropertiesUtil("properties/host.properties");
        List<String> riskItemsTD=new ArrayList<>();
        List<Object> riskItemsZXT=new ArrayList<>();
        List<Object> riskItems91ZX=new ArrayList<>();
        String riskItems91ZXStr=customer.getNinetyOneRule();
        List<Object> riskInfo=new ArrayList<>();
        List<Object> YQRisk=new ArrayList<>();
        List<Object> QZRisk=new ArrayList<>();
        List<Object> ZX91Risk=new ArrayList<>();
        String riskScore="0";
        String jsonPdf="";
        String url="";*/
//        if (customer.getCallRecordUrl()==null||"".equals(customer.getCallRecordUrl())){
//            try {
//                List<String> headList=new ArrayList<>();
//                headList.add("号码");
//                headList.add("号码归属地");
//                headList.add("号码标注");
//                headList.add("需求类别");
//                headList.add("通话次数");
//                headList.add("通话时长");
//                headList.add("呼出次数");
//                headList.add("呼出时间");
//                headList.add("呼入次数");
//                headList.add("呼入时间");
//                headList.add("关系推测");
//                headList.add("最近一周联系次数");
//                headList.add("最近一月联系次数");
//                headList.add("最近三月联系次数");
//                headList.add("三个月以上联系次数");
//                headList.add("凌晨联系次数");
//                headList.add("上午联系次数");
//                headList.add("中午联系次数");
//                headList.add("下午联系次数");
//                headList.add("晚上联系次数");
//                headList.add("是否全天联系");
//                headList.add("周中联系次数");
//                headList.add("周末联系次数");
//                headList.add("节假日联系次数");
//                List<Map<String,Object>> listValue=new ArrayList<>();
//                Map<String,Object> mapRule=new HashedMap();
//
//                mapRule.put("phone",customer.getTel());
//                mapRule.put("name",customer.getPersonName());
//                mapRule.put("idNo",customer.getCard());
//                jsonPdf= HttpUtil.doGet(prop.get("ruleUrlSP")+ "/szt/jxl/creditQuery", mapRule);
//                JSONArray  jsonArray=JSON.parseObject(jsonPdf).getJSONObject("reportData").getJSONArray("contact_list");
//                if (jsonArray!=null&&jsonArray.size()>0){
//                    for (int i=0;i<jsonArray.size();i++){
//                        listValue.add((Map<String, Object>) jsonArray.get(i));
//                    }
//                    Collections.sort(listValue,new Comparator<Map<String, Object>>() {
//                        public int compare(Map<String, Object> o1,Map<String, Object> o2)
//                        {
//                            double q1= Double.parseDouble(o1.get("call_len").toString());
//                            double q2=Double.parseDouble(o2.get("call_len").toString());
//                            double p=q2-q1;
//                            if(p>0){
//                                return 1;
//                            }
//                            else if(p==0){
//                                return 0;
//                            }
//                            else
//                                return -1;
//                        }
//                    });
//                    ContextToPdf.insertPDF(listValue,headList,root);
//                    url=uploadService.getOSS(root,UUID.randomUUID()+".pdf");
//                    if(!"".equals(url)){
//                        Customer customer1=new Customer();
//                        customer1.setId(customerId);
//                        customer1.setCallRecordUrl(url);
//                        customer.setCallRecordUrl(url);
//                        int count=customerService.updateByPrimaryKey(customer1);
//                        File file=new File(root);
//                        file.delete();
//                    }
//                }
//            }catch (Exception e){
//            }
//        }


        //获取
        //param.put("engineId","283L");

        //获取风控信息
      /*  List<RuleResult>  ruleResult = ruleResultService.getRuleResultCurrentSP(param);
        if(ruleResult!=null&&ruleResult.size()>0){
            int y=0;
            for(RuleResult rr:ruleResult){
                if("286L".equals(rr.getEngineId()) && "".equals(rr.getRiskScore())){
                    ruleResult = ruleResultService.getRuleResultCurrentSP1(param);
                    y += 1;
                }
            }
            if(y == 0){
                ruleResult = ruleResultService.getRuleResultCurrentSP1(param);
            }
        }else{
            ruleResult = ruleResultService.getRuleResultCurrentSP1(param);
        }
        List<String> ruleJson=new ArrayList<>();
        if (ruleResult!=null&&ruleResult.size()>0){
            for (int ij=0;ij<ruleResult.size();ij++){
                if ((!"异常".equals(ruleResult.get(ij).getState()))&&(!"通过".equals(ruleResult.get(ij).getState()))){
                    try {
                        JSONObject jsonObject=JSONObject.parseObject(ruleResult.get(ij).getRuleJson());
                        JSONArray jsonArray=jsonObject.getJSONArray("data");
                        ruleJson.clear();
                        if (jsonArray!=null){
                            for (int i=0;i<jsonArray.size();i++){
                                JSONArray jsonStr=jsonArray.getJSONObject(i).getJSONArray("resultJson").getJSONObject(0).getJSONArray("refusedRules");
                                if (jsonStr!=null){
                                    for (int j=0;j<jsonStr.size();j++){
                                        ruleJson.add(jsonStr.getJSONObject(j).getString("ruleName"));
                                    }
                                }
                            }
                        }else {
                            //ruleJson=jsonObject.getString("responseMsg");
                            ruleResult.get(ij).setState("异常");
                        }
                        //增信通与同盾规则与91
                        if ("287L".equals(ruleResult.get(ij).getEngineId())&&ruleJson.size()>0){
                            riskItemsZXT.clear();
                            riskItemsTD.clear();
                            riskItems91ZX.clear();
                            for (int j=0;j<ruleJson.size();j++) {
                                //解析同盾命中规则
                                if (ruleResult.get(ij).getTongdunRule() != null && (!"".equals(ruleResult.get(ij).getTongdunRule()))&&"是否命中同盾规则".equals(ruleJson.get(j))) {
                                    JSONObject jsonTongdunRule = JSONObject.parseObject(ruleResult.get(ij).getTongdunRule());
                                    JSONArray risk_items=jsonTongdunRule.getJSONObject("ANTIFRAUD").getJSONArray("risk_items");
                                    for (int i = 0; i < risk_items.size(); i++) {
                                        riskItemsTD.add(risk_items.getJSONObject(i).getString("risk_name"));
                                    }
                                }
                                //解析增信通命中规则
                                if (ruleResult.get(ij).getZenxintongRule() != null && (!"".equals(ruleResult.get(ij).getZenxintongRule()))&&"是否命中增信通黑名单".equals(ruleJson.get(j))) {
                                    JSONArray risk_items=JSONObject.parseArray(ruleResult.get(ij).getZenxintongRule());
                                    for (int i = 0; i < risk_items.size(); i++) {
                                        //逾期明细
                                        if("YQ001".equals(risk_items.getJSONObject(i).getString("label"))||"YQ002".equals(risk_items.getJSONObject(i).getString("label"))||"YQ003".equals(risk_items.getJSONObject(i).getString("label"))||"YQ004".equals(risk_items.getJSONObject(i).getString("label"))||"YQ005".equals(risk_items.getJSONObject(i).getString("label"))||"YQ006".equals(risk_items.getJSONObject(i).getString("label"))||"YQ007".equals(risk_items.getJSONObject(i).getString("label"))){
                                            List<String> YQ=new ArrayList<>();
                                            YQ.add(risk_items.getJSONObject(i).getString("black_type"));
                                            YQ.add(risk_items.getJSONObject(i).getString("item_name"));
                                            YQ.add(risk_items.getJSONObject(i).getString("item_value"));
                                            YQRisk.add(YQ);
                                        }
                                        //欺诈明细
                                        if("QZ001".equals(risk_items.getJSONObject(i).getString("label"))||"QZ002".equals(risk_items.getJSONObject(i).getString("label"))||"QZ003".equals(risk_items.getJSONObject(i).getString("label"))){
                                            List<String> QZ=new ArrayList<>();
                                            QZ.add(risk_items.getJSONObject(i).getString("black_type"));
                                            QZ.add(risk_items.getJSONObject(i).getString("item_name"));
                                            QZ.add(risk_items.getJSONObject(i).getString("item_value"));
                                            QZRisk.add(QZ);
                                        }
                                    }
                                }
                            }
                            //91征信数据
                            if(!"".equals(riskItems91ZXStr)){
                                JSONArray risk_items=JSONObject.parseObject(riskItems91ZXStr).getJSONArray("loanInfo");
                                for(int i=0;i<risk_items.size();i++){
                                    List<String> dataInfo=new ArrayList<>();
                                    JSONObject jsonObj=risk_items.getJSONObject(i);
                                    //0.未知1.个人信贷2.个人抵押3.企业信贷4.企业抵押
                                    if ("0".equals(jsonObj.getString("borrowType"))){
                                        dataInfo.add("未知");
                                    }else if ("1".equals(jsonObj.getString("borrowType"))){
                                        dataInfo.add("个人信贷");
                                    }else if ("2".equals(jsonObj.getString("borrowType"))){
                                        dataInfo.add("个人抵押");
                                    }else if ("3".equals(jsonObj.getString("borrowType"))){
                                        dataInfo.add("企业信贷");
                                    }else if ("4".equals(jsonObj.getString("borrowType"))){
                                        dataInfo.add("企业抵押");
                                    }

                                    //0.未知1.拒贷2.批贷已放款4.借款人放弃申请5.审核中6.待放款
                                    if ("0".equals(jsonObj.getString("borrowState"))){
                                        dataInfo.add("未知");
                                    }else if ("1".equals(jsonObj.getString("borrowState"))){
                                        dataInfo.add("拒贷");
                                    }else if ("2".equals(jsonObj.getString("borrowState"))){
                                        dataInfo.add("批贷已放款");
                                    }else if ("4".equals(jsonObj.getString("borrowState"))){
                                        dataInfo.add("借款人放弃申请");
                                    }else if ("5".equals(jsonObj.getString("borrowState"))){
                                        dataInfo.add("审核中");
                                    }else if ("6".equals(jsonObj.getString("borrowState"))){
                                        dataInfo.add("待放款");
                                    }

                                    //-7.[0,0.1) -6.[0.1,0.2) -5.[0.2,0.3) -4.[0.3,0.4) -3.[0.4,0.6) -2.[0.6,0.8) -1.[0.8,1) 0.未知1.[1,2) 2.[2,4) 3.[4,6) 4.[6,8)…….(单位:万元)
                                    //2万一档依次类推
                                    Integer borrowState=jsonObj.getInteger("borrowState");
                                    if (-7==borrowState){
                                        dataInfo.add("0-1000");
                                    }else if (-6==borrowState){
                                        dataInfo.add("1000-2000");
                                    }else if (-5==borrowState){
                                        dataInfo.add("2000-3000");
                                    }else if (-4==borrowState){
                                        dataInfo.add("3000-4000");
                                    }else if (-3==borrowState){
                                        dataInfo.add("4000-6000");
                                    }else if (-2==borrowState){
                                        dataInfo.add("6000-8000");
                                    }else if (-1==borrowState){
                                        dataInfo.add("8000-10000");
                                    }else if (0==borrowState){
                                        dataInfo.add("未知");
                                    }else if (1==borrowState){
                                        dataInfo.add("8000-10000");
                                    }else {
                                        dataInfo.add((borrowState*2)+"-"+(borrowState*2));
                                    }

                                    long contractDate=jsonObj.getLong("contractDate");
                                    SimpleDateFormat sdf= new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
                                    Date dt = new Date(contractDate);
                                    dataInfo.add(sdf.format(dt));
                                    //批贷期数
                                    dataInfo.add(jsonObj.getString("loanPeriod"));
                                    //还款状态
                                    String repayState=jsonObj.getString("repayState");
                                    if ("0".equals(repayState)){
                                        dataInfo.add("未知");
                                    }else if ("1".equals(repayState)){
                                        dataInfo.add("正常");
                                    }else if ("9".equals(repayState)){
                                        dataInfo.add("已还清");
                                    }else {
                                        dataInfo.add(repayState);
                                    }
                                    //欠款金额
                                    dataInfo.add(jsonObj.getString("arrearsAmount"));
                                    riskItems91ZX.add(riskInfo);
                                }
                                //riskItems91ZX=
                                //ZX91Risk
                            }
                            List ruleList = new ArrayList();
                            riskItemsZXT.add(YQRisk);
                            riskItemsZXT.add(QZRisk);
                            ruleList.add(riskItemsZXT);
                            ruleList.add(riskItemsTD);
                            //ruleList.add(riskItems91ZX);
                            ruleResult.get(ij).setHitRule(ruleList);
                        }
                        //聚力信
                        if ("288L".equals(ruleResult.get(ij).getEngineId())||"281L".equals(ruleResult.get(ij).getEngineId())){
                            ruleResult.get(ij).setHitRule(ruleJson);
                        }

                        //腾讯天域
                        if ("286L".equals(ruleResult.get(ij).getEngineId())){
                            riskInfo.clear();
                            JSONArray risk_items=JSONObject.parseObject(ruleResult.get(ij).getRiskScore()).getJSONObject("data").getJSONArray("riskInfo");
                            if (risk_items!=null){
                                for (int i=0;i<risk_items.size();i++){
                                    JSONObject risk_item=risk_items.getJSONObject(i);
                                    List<String> info=new ArrayList<>();
                                    if ("1".equals(risk_item.getString("riskCode"))){
                                        info.add("信贷中介");
                                        info.add("涉嫌从事包装客户资料，伪造客户资料，冒用客户资料，套取机构风险政策等职业的用户或者机构成员");
                                    }
                                    if ("2".equals(risk_item.getString("riskCode"))){
                                        info.add("不法分子");
                                        info.add("互联网行为涉嫌色情、赌博、毒品等违法行为");
                                    }
                                    if ("3".equals(risk_item.getString("riskCode"))){
                                        info.add("虚假资料");
                                        info.add("输入信息和虚假身份信息提交强相关，或者有恶意申请/操作记录，或者个人信息疑似泄漏、冒用、伪造等");
                                    }
                                    if ("4".equals(risk_item.getString("riskCode"))){
                                        info.add("羊毛党");
                                        info.add("在网贷、电商、O2O 等平台有薅羊毛行为的用户");
                                    }
                                    if ("5".equals(risk_item.getString("riskCode"))){
                                        info.add("身份认证失败");
                                        info.add("身份信息对（身份证、手机号、姓名）涉嫌伪造");
                                    }
                                    if ("6".equals(risk_item.getString("riskCode"))){
                                        info.add("疑似恶意欺诈");
                                        info.add("存在骗贷行为");
                                    }
                                    if ("7".equals(risk_item.getString("riskCode"))){
                                        info.add("失信名单");
                                        info.add("失信名单");
                                    }
                                    if ("8".equals(risk_item.getString("riskCode"))){
                                        info.add("异常支付行为");
                                        info.add("支付行为异常包括支付频次、额度、场景等方面有过异常的");
                                    }
                                    if ("301".equals(risk_item.getString("riskCode"))){
                                        info.add("恶意环境");
                                        info.add("设备和IP命中黑数据库，包括使用虚拟机、代理设备、代理 IP、猫池等");
                                    }
                                    if ("503".equals(risk_item.getString("riskCode"))){
                                        info.add("其他异常行为");
                                        info.add("输入信息和以下高风险可能性关联度较高：被盗风险较高、社交圈子不固定、地理圈子变化较大");
                                    }
                                    if("1".equals(risk_item.getString("riskCodeValue"))){
                                        info.add("低风险");
                                    }else if ("2".equals(risk_item.getString("riskCodeValue"))){
                                        info.add("中风险");
                                    }else if ("3".equals(risk_item.getString("riskCodeValue"))){
                                        info.add("高风险");
                                    }
                                    riskInfo.add(info);
                                }
                            }
                            ruleResult.get(ij).setHitRule(riskInfo);
                            riskScore=JSONObject.parseObject(ruleResult.get(ij).getRiskScore()).getString("riskScore");
                            ruleResult.get(ij).setRiskScore(riskScore);
                        }
                    }catch (Exception e){

                    }
                }else {
                    try {
                        //腾讯天域
                        if ("286L".equals(ruleResult.get(ij).getEngineId()) && ruleResult.get(ij).getRiskScore() != null && (!"".equals(ruleResult.get(ij).getRiskScore()))) {
                            riskScore = JSONObject.parseObject(ruleResult.get(ij).getRiskScore()).getString("riskScore");
                            ruleResult.get(ij).setRiskScore(riskScore);
                            JSONArray risk_items = JSONObject.parseObject(ruleResult.get(ij).getRiskScore()).getJSONObject("data").getJSONArray("riskInfo");
                            for (int i = 0; i < risk_items.size(); i++) {
                                JSONObject risk_item = risk_items.getJSONObject(i);
                                List<String> info = new ArrayList<>();
                                if ("1".equals(risk_item.getString("riskCode"))) {
                                    info.add("信贷中介");
                                    info.add("涉嫌从事包装客户资料，伪造客户资料，冒用客户资料，套取机构风险政策等职业的用户或者机构成员");
                                }
                                if ("2".equals(risk_item.getString("riskCode"))) {
                                    info.add("不法分子");
                                    info.add("互联网行为涉嫌色情、赌博、毒品等违法行为");
                                }
                                if ("3".equals(risk_item.getString("riskCode"))) {
                                    info.add("虚假资料");
                                    info.add("输入信息和虚假身份信息提交强相关，或者有恶意申请/操作记录，或者个人信息疑似泄漏、冒用、伪造等");
                                }
                                if ("4".equals(risk_item.getString("riskCode"))) {
                                    info.add("羊毛党");
                                    info.add("在网贷、电商、O2O 等平台有薅羊毛行为的用户");
                                }
                                if ("5".equals(risk_item.getString("riskCode"))) {
                                    info.add("身份认证失败");
                                    info.add("身份信息对（身份证、手机号、姓名）涉嫌伪造");
                                }
                                if ("6".equals(risk_item.getString("riskCode"))) {
                                    info.add("疑似恶意欺诈");
                                    info.add("存在骗贷行为");
                                }
                                if ("7".equals(risk_item.getString("riskCode"))) {
                                    info.add("失信名单");
                                    info.add("失信名单");
                                }
                                if ("8".equals(risk_item.getString("riskCode"))) {
                                    info.add("异常支付行为");
                                    info.add("支付行为异常包括支付频次、额度、场景等方面有过异常的");
                                }
                                if ("301".equals(risk_item.getString("riskCode"))) {
                                    info.add("恶意环境");
                                    info.add("设备和IP命中黑数据库，包括使用虚拟机、代理设备、代理 IP、猫池等");
                                }
                                if ("503".equals(risk_item.getString("riskCode"))) {
                                    info.add("其他异常行为");
                                    info.add("输入信息和以下高风险可能性关联度较高：被盗风险较高、社交圈子不固定、地理圈子变化较大");
                                }
                                if ("1".equals(risk_item.getString("riskCodeValue"))) {
                                    info.add("低风险");
                                } else if ("2".equals(risk_item.getString("riskCodeValue"))) {
                                    info.add("中风险");
                                } else if ("3".equals(risk_item.getString("riskCodeValue"))) {
                                    info.add("高风险");
                                }
                                riskInfo.add(info);
                            }
                            ruleResult.get(ij).setHitRule(riskInfo);
                            riskScore = JSONObject.parseObject(ruleResult.get(ij).getRiskScore()).getString("riskScore");
                            ruleResult.get(ij).setRiskScore(riskScore);
                        }
                        ruleResult.get(ij).setRuleJson("");
                    }catch (Exception e){
                        //ruleResult.get(ij).setRiskScore("没有解析到数据！");
                    }
                }
            }
        }
        String host = dictService.getDetilNameByCode("ceshi",dictService.getListByCode("host").get(0).getId().toString());
        //设备信息
        if (orderId!=null&&orderId.length()>0){
            CustomerDeviceInfo customerDeviceInfo = customerService.getCustomerDeviceInfo(orderId);
            map.put("customerDeviceInfo",customerDeviceInfo);
        }
        //查询银行卡信息
        List cardList = loanClientService.getCustBankCardInfo(customerId);
        if (null != cardList && cardList.size() > 0)
        {
            map.put("bankCard",cardList.get(0));
        }

        map.put("answerScore",answerScore);
        map.put("answerList",answerList);
        map.put("servicePackageOrderList",servicePackageOrderList);
        map.put("linkmanList",linkmanList);
        map.put("customer",customer);
        map.put("personList",personList);
        map.put("imgList",imgList);
        map.put("occupationList",occupationList);
        map.put("ruleResult",ruleResult);
        map.put("hostUrl",host);
        //办单员实体
        map.put("salesman",salesman);*/

    }
}
