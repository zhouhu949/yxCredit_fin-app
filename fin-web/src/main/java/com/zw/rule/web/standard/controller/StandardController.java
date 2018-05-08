package com.zw.rule.web.standard.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zw.base.util.Base64Utils;
import com.zw.rule.core.Response;
import com.zw.rule.customer.po.*;
import com.zw.rule.customer.service.CustomerService;
import com.zw.rule.customer.service.OrderService;
import com.zw.rule.mybatis.ParamFilter;
import com.zw.rule.product.WorkingProductDetail;
import com.zw.rule.product.service.ICrmProductService;
import com.zw.rule.standard.StandardService;
import com.zw.rule.util.flow.service.FlowComService;
import com.zw.rule.web.aop.annotaion.WebLogger;
import com.zw.rule.web.util.PageConvert;
import com.zw.rule.web.util.PropertiesUtil;
import com.zw.rule.web.util.UserContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2017/7/11.
 */
@Controller
@RequestMapping("standard")
public class StandardController {

    @Resource
    private OrderService orderService;
    @Resource
    private StandardService standardService;
    @Autowired
    private CustomerService customerService;

    @Autowired
    private FlowComService flowComService;

    @Autowired
    private ICrmProductService crmProductService;
    @GetMapping("listPage")
    public String list(){
        return "standard/standardList";
    }

    @PostMapping("list")
    @ResponseBody
    @WebLogger("获取合规审查列表")
    public Response list(@RequestBody ParamFilter queryFilter){
        int pageNo = PageConvert.convert(queryFilter.getPage().getFirstIndex(),queryFilter.getPage().getPageSize());
        PageHelper.startPage(pageNo, queryFilter.getPage().getPageSize());
        List<Order> list = standardService.getOrdersList(queryFilter);
        PageInfo pageInfo = new PageInfo(list);
        return new Response(pageInfo);
    }

    @PostMapping("updateState")
    @ResponseBody
    @WebLogger("修改订单状态")
    public Response updateState(@RequestBody ParamFilter queryFilter) throws Exception {
        Response response = new Response();
        if(standardService.updateState(queryFilter)){
            String taskNodeId = flowComService.getTaskNodeId(queryFilter.getParam().get("orderId").toString());
            Integer state = (Integer) queryFilter.getParam().get("state");
            if(state.equals(12)){
                state = 4;
            }else{
                state = 3;
            }
            flowComService.CommitTask(queryFilter.getParam().get("orderId").toString(), UserContextUtil.getUserId(),
                    state,taskNodeId,"",null);
            if(queryFilter.getParam().get("state").toString().equals("4")){
                orderService.orderMsgDealWith(queryFilter.getParam().get("orderId").toString(), "0", queryFilter.getParam().get("userId").toString(), "");
            }
            response.setMsg("操作成功！");
        }else{
            response.setCode(1);
            response.setMsg("操作失败！");
        }
        return response;
    }

    @GetMapping("details")
    public ModelAndView details(String orderId,String customerId) throws  Exception{
        ModelAndView modelAndView = new ModelAndView("standard/headquartersReview");
        Customer customer = customerService.getCustomer(customerId);
        Order order = orderService.selectById(orderId);
        List linkmanList = customerService.getCustomerLinkMan(customerId);
        List personList = customerService.getCustomerPerson(customerId);
        MagCustomerLive customerLive = customerService.getCustomerLive(customerId);
        MagCustomerContact customerContact = customerService.getContact(customerId);
        List<CustomerImage> imgList = customerService.getCustomerImage(customerId);
        List list = (List)crmProductService.getProductInfo(order.getProductDetail()).get("data");
        WorkingProductDetail productDetail = (WorkingProductDetail)list.get(0);
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
        modelAndView.addObject("imgList",imgList);
        PropertiesUtil pro = new PropertiesUtil("properties/host.properties");
        modelAndView.addObject("appUrl",pro.get("appUrl"));
        return  modelAndView;
    }
}
