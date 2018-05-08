package com.zw.rule.web.loanRepayment.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mysql.jdbc.StringUtils;
import com.zw.UploadFile;
import com.zw.base.util.Base64Utils;
import com.zw.base.util.DateUtils;
import com.zw.rule.core.Response;
import com.zw.rule.customer.po.*;
import com.zw.rule.customer.service.CustomerService;
import com.zw.rule.customer.service.OrderService;
import com.zw.rule.customer.service.RuleRiskService;
import com.zw.rule.loanRepayment.service.SettleCustomerService;
import com.zw.rule.mybatis.ParamFilter;
import com.zw.rule.po.User;
import com.zw.rule.product.WorkingProductDetail;
import com.zw.rule.product.service.ICrmProductService;
import com.zw.rule.repayment.po.Repayment;
import com.zw.rule.repayment.service.RepaymentService;
import com.zw.rule.score.service.ScoreCardService;
import com.zw.rule.service.DictService;
import com.zw.rule.util.flow.service.FlowComService;
import com.zw.rule.web.aop.annotaion.WebLogger;
import com.zw.rule.web.util.DownloadUtils;
import com.zw.rule.web.util.PageConvert;
import com.zw.rule.web.util.PropertiesUtil;
import com.zw.rule.web.util.UserContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.net.URLDecoder;
import java.util.*;

/**
 * Created by Administrator on 2017/7/11.
 */
@Controller
@RequestMapping("settleCustomer")
public class SettleCustomerController {


    @Resource
    private SettleCustomerService settleCustomerService;

    @Resource
    private CustomerService customerService;
    @Resource
    private DictService dictService;
    @Resource

    private FlowComService flowComService;

    @Resource
    private RepaymentService repaymentService;
    @Resource
    private OrderService orderService;
    @Autowired
    private RuleRiskService ruleRiskService;

    @Autowired
    private ScoreCardService scoreCardService;
    @Autowired
    private ICrmProductService crmProductService;

    /**
     * 订单页面
     * @return
     */
    @GetMapping("orderPage")
    public String orderPage(){
        return "orderManage/orderList";
    }

    /**
     * 订单页面
     * @return
     */
    @GetMapping("overtimeOrderPage")
    public String overtimeOrderPage(){
        return "orderManage/overtimeOrderList";
    }

    /**
     * 订单日志页面
     * @return
     */
    @GetMapping("orderLogView")
    public String orderLogView(){
        return "orderManage/orderLogList";
    }


    @PostMapping("list")
    @ResponseBody
    public Response list(@RequestBody ParamFilter queryFilter){
        int pageNo = PageConvert.convert(queryFilter.getPage().getFirstIndex(),queryFilter.getPage().getPageSize());
        PageHelper.startPage(pageNo, queryFilter.getPage().getPageSize());
        List list = settleCustomerService.getCustomerListByState(queryFilter.getParam());
        PageInfo pageInfo = new PageInfo(list);
        return new Response(pageInfo);
    }

    @PostMapping("orderlist")
    @ResponseBody
    @WebLogger("查询订单信息")
    public Response orderlist(@RequestBody ParamFilter queryFilter){
        int pageNo = PageConvert.convert(queryFilter.getPage().getFirstIndex(),queryFilter.getPage().getPageSize());
        PageHelper.startPage(pageNo, queryFilter.getPage().getPageSize());
        List list = settleCustomerService.getRepayCustomerList(queryFilter);
        PageInfo pageInfo = new PageInfo(list);
        return new Response(pageInfo);
    }

    /* *
    * 获取分页的还款计划
    * */
    @RequestMapping(value = "getList" ,method = RequestMethod.POST)
    @ResponseBody
    public Response repayPlan(@RequestBody ParamFilter queryFilter){
        Map param = queryFilter.getParam();
        Response response = new Response();
        int pageNo = PageConvert.convert(queryFilter.getPage().getFirstIndex(),queryFilter.getPage().getPageSize());
        PageHelper.startPage(pageNo, queryFilter.getPage().getPageSize());
        List list = settleCustomerService.getLoanRepaymentList(param);
        PageInfo pageInfo = new PageInfo(list);
        response.setData(pageInfo);
        return response;
    }

    /**
     * 根据id获取订单
     * @param map
     * @return
     */
    @PostMapping("getOrderById")
    @ResponseBody
    public Response getOrderById(@RequestBody Map map){
        String orderId = String.valueOf(map.get("orderId"));
        Order order = orderService.selectById(orderId);
        if("9".equals(String.valueOf(map.get("state")))){//未结清
            map.put("state",1);//还款计划表状态为1
        }else{//已结清
            map.put("state",2);//还款计划表状态为2
        }
        List list = settleCustomerService.getLoanRepaymentList(map);
        String  sumLoanRepay ="";
        if(list.size()>0){
            List<Repayment> list1 = list;
            sumLoanRepay =list1.get(0).getRepaymentAmount();
        }
        order.setSumLoanRepay(sumLoanRepay);
        return new Response(order);
    }

    /**
     * 更新订单状态和还款计划表
     * @param
     * @return
     */
    @PostMapping("updateOrderState")
    @ResponseBody
    public Response updateOrderState(@RequestBody Order order) throws Exception {
        String time= DateUtils.getCurrentTime(DateUtils.STYLE_5);//当前时间
        order.setAlterTime(time);//更改时间
        Boolean flag = orderService.updateState(order);//更新订单状态
        String orderId = order.getId();//订单id
        Map map = new HashMap();
        map.put("orderId",orderId);
        map.put("state","2");//还款计划表的已还的状态
        map.put("remark",order.getRemark());//备注
        Boolean flag2 = repaymentService.updtaeRepayByorderId(map);
        Response response = new Response();
        if(flag&&flag2){
            String taskNodeId = flowComService.getTaskNodeId(orderId);
            flowComService.CommitTask(orderId,UserContextUtil.getUserId(),4,taskNodeId,"",null);
            response.setMsg("提交成功");
        }else {
            response.setMsg("提交失败");
        }
        return response;
    }

    @RequestMapping("down")
    @WebLogger("下载合同")
    public ResponseEntity<byte[]> download(HttpServletRequest request, HttpServletResponse response, String src_name, String contract_name) throws Exception {
        String path = request.getSession().getServletContext().getRealPath("/")+src_name;//获取服务上的地址
        contract_name= URLDecoder.decode(contract_name,"UTF-8");//前台传来编码过的名字，后台再次进行解码，解决了乱码的问题
        DownloadUtils.download(response, path,contract_name);//浏览器下载合同数据到本地//将文件名，命名为上传时候的名字
        return null;
    }


    @PostMapping("findOne")
    @ResponseBody
    public Response findOne(@RequestBody String customerId){
        Customer customer = customerService.findOne(customerId);
        return new Response(customer);
    }




    /**
     * 查看客户进件资料
     * @param orderId
     * @param customerId
     * @return
     * @throws Exception
     */
    @GetMapping("details")
    public ModelAndView details(String orderId,String customerId) throws  Exception{
        ModelAndView modelAndView = new ModelAndView("loanRepayment/quartersReview");
        Customer customer = customerService.getCustomer(customerId);
        Order order = orderService.selectById(orderId);
        List linkmanList = customerService.getCustomerLinkMan(customerId);
        List personList = customerService.getCustomerPerson(customerId);
        MagCustomerLive customerLive = customerService.getCustomerLive(customerId);
        MagCustomerContact customerContact = customerService.getContact(customerId);
        List list = (List)crmProductService.getProductInfo(order.getProductName()).get("data");
        List<CustomerImage> imgList = customerService.getCustomerImage(customerId);
        WorkingProductDetail productDetail =new WorkingProductDetail();
        if(list.size()>0){
           productDetail = (WorkingProductDetail)list.get(0);
        }
        customerContact.setServerpwd(Base64Utils.decode(customerContact.getServerpwd()));
        CustomerEarner customerEarne = null;
        CustomerManager customerManager = null;
        CustomerFreelance customerFreelance = null;
        CustomerStu customerStu = null;
        CustomerRetire customerRetire = null;
        switch (customer.getOccupationType()){
            case "1":
                customerEarne = customerService.getJobEarnerInfo(customerId);
                break;
            case "2":
                customerManager = customerService.getJobManagerInfo(customerId);
                break;
            case "3":
                customerFreelance = customerService.getJobFreelanceInfo(customerId);
                break;
            case "4":
                customerStu = customerService.getJobStuInfo(customerId);
                break;
            case "5":
                customerRetire = customerService.getJobRetireInfo(customerId);
                break;
        }
        modelAndView.addObject("customer",customer);
        modelAndView.addObject("linkmanList",linkmanList);
        modelAndView.addObject("person",personList == null ? null:personList.size()>0?personList.get(0):null);
        modelAndView.addObject("customerLive",customerLive);
        modelAndView.addObject("customerEarne",customerEarne);
        modelAndView.addObject("customerManager",customerManager);
        modelAndView.addObject("customerFreelance",customerFreelance);
        modelAndView.addObject("customerStu",customerStu);
        modelAndView.addObject("customerRetire",customerRetire);
        modelAndView.addObject("customerContact",customerContact);
        modelAndView.addObject("productDetail",productDetail);
        modelAndView.addObject("order",order);
        modelAndView.addObject("customerId",customerId);
        modelAndView.addObject("imgList",imgList);
        PropertiesUtil pro = new PropertiesUtil("properties/host.properties");
        modelAndView.addObject("appUrl",pro.get("appUrl"));
        return  modelAndView;
    }

    /**
     * 上传
     * @param request
     * @return
     * @throws Exception
     */
    @ResponseBody
    @PostMapping("merUpload")
    public Response upload(HttpServletRequest request) throws Exception{
        Response response = new Response();
        String fileName="";
        //String doc="";
        String id = UUID.randomUUID().toString();
        //获取根目录
        String root = request.getSession().getServletContext().getRealPath("fintecher_file");
        //捕获前台传来的图片，并用uuid命名，防止重复
        Map<String, Object> allMap = UploadFile.getFile(request,root+ File.separator + "merchant_img", id);
        List<Map<String, Object>> list = (List<Map<String, Object>>) allMap.get("fileList");
        //当前台有文件时，给图片名称变量赋值
        if (!list.isEmpty()) {
            Map<String, Object> fileMap = list.get(0);
            fileName = "merchant_img/"+(String) fileMap.get("Name");
        }
        if(StringUtils.isNullOrEmpty(fileName)){
            response.setCode(1);
            response.setMsg("上传失败");
            return response;
        }
        CustomerImage customerImage = new CustomerImage();
        customerImage.setId(id);
        customerImage.setType("50");
        customerImage.setSrc(fileName);
        String time = DateUtils.getDateString(new Date());
        customerImage.setCreatTime(time);
        customerImage.setAlterTime(time);
        customerImage.setBusinessType((String)allMap.get("businessType"));
        customerImage.setState("0");
        customerImage.setCustomerId((String)allMap.get("id"));
        Boolean flag = settleCustomerService.credentialUpload(customerImage);
        if(flag){
            response.setMsg("上传成功！");
            response.setData(id);
        }else{
            response.setMsg("上传失败！");
            response.setCode(1);
        }
        return response;
    }

    @ResponseBody
    @GetMapping("getCustomerImage")
    public Response getCustomerImage(String customerId,Model model) throws Exception{
        List<CustomerImage> list = customerService.getCustomerImage(customerId);
        PropertiesUtil pro = new PropertiesUtil("properties/host.properties");
        model.addAttribute(pro.get("appUrl"));
        return new Response(list);
    }

    @PostMapping("orderLogList")
    @ResponseBody
    @WebLogger("查询订单信息")
    public Response orderLogList(@RequestBody ParamFilter queryFilter){
        int pageNo = PageConvert.convert(queryFilter.getPage().getFirstIndex(),queryFilter.getPage().getPageSize());
        PageHelper.startPage(pageNo, queryFilter.getPage().getPageSize());
        List list = settleCustomerService.getOrderLogList(queryFilter);
        PageInfo pageInfo = new PageInfo(list);
        return new Response(pageInfo);
    }

    @PostMapping("overtimeOrderList")
    @ResponseBody
    @WebLogger("订单状态超时列表")
    public Response overtimeOrderList(@RequestBody ParamFilter queryFilter){
        int pageNo = PageConvert.convert(queryFilter.getPage().getFirstIndex(),queryFilter.getPage().getPageSize());
        PageHelper.startPage(pageNo, queryFilter.getPage().getPageSize());
        List list = orderService.getOvertimePayOrder(queryFilter.getParam());
        PageInfo pageInfo = new PageInfo(list);
        return new Response(pageInfo);
    }

    @PostMapping("closeOrder")
    @ResponseBody
    @WebLogger("关闭订单")
    public Response closeOrder(@RequestBody String orderId){
        User user = (User) UserContextUtil.getAttribute("currentUser");
        Map<String, Object> map = orderService.closeOrder(orderId, user);
        return new Response(map);
    }
}
