package com.zw.rule.web.repayment.controller;//package com.zw.rule.web.LoanController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zw.rule.core.Response;
import com.zw.rule.customer.po.Customer;
import com.zw.rule.customer.service.CustomerService;
import com.zw.rule.mybatis.ParamFilter;
import com.zw.rule.repayment.service.RepayManageService;
import com.zw.rule.repayment.service.RepaymentService;
import com.zw.rule.web.util.PageConvert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("repayManage")
public class RepaymentManageController {

    @Autowired
    private RepayManageService repayManageService;

    @Autowired
    private CustomerService customerService;

    @RequestMapping(value = "index",method = RequestMethod.GET)
    public String list(){
        return "repayPlan/unclearCustomer/unclearList";
    }

    /* *
     * 获取所有有还款订单的客户
     * */
    @RequestMapping(value = "getCust" ,method = RequestMethod.POST)
    @ResponseBody
    public Response getCust(@RequestBody ParamFilter queryFilter){
        Response response = new Response();
        int pageNo = PageConvert.convert(queryFilter.getPage().getFirstIndex(),queryFilter.getPage().getPageSize());
        PageHelper.startPage(pageNo, queryFilter.getPage().getPageSize());
        List list = repayManageService.getCust(queryFilter);
        PageInfo pageInfo = new PageInfo(list);
        response.setData(pageInfo);
        return response;
    }

    /* *
     * 获取当前选中客户下面，所有的需要还款的订单
     * */
    @RequestMapping(value = "getRepayOrders" ,method = RequestMethod.POST)
    @ResponseBody
    public Response getRepayOrders(@RequestBody ParamFilter queryFilter){
        Response response = new Response();
        int pageNo = PageConvert.convert(queryFilter.getPage().getFirstIndex(),queryFilter.getPage().getPageSize());
        PageHelper.startPage(pageNo, queryFilter.getPage().getPageSize());
        List list = repayManageService.getRepayOrders(queryFilter);
        PageInfo pageInfo = new PageInfo(list);
        response.setData(pageInfo);
        return response;
    }

    /**
     * 信贷还款明细表
     * @param queryFilter
     * @return
     */
    @RequestMapping(value = "getCrmPaycontrol" ,method = RequestMethod.POST)
    @ResponseBody
    public Response getCrmPaycontrol(@RequestBody ParamFilter queryFilter){
        Response response = new Response();
        int pageNo = PageConvert.convert(queryFilter.getPage().getFirstIndex(),queryFilter.getPage().getPageSize());
        PageHelper.startPage(pageNo, queryFilter.getPage().getPageSize());
        List list = repayManageService.getCrmPaycontrol(queryFilter);
        PageInfo pageInfo = new PageInfo(list);
        response.setData(pageInfo);
        return response;
    }

    /**
     * 信贷还款计划表
     * @param queryFilter
     * @return
     */
    @RequestMapping(value = "getCrmRepayRecords" ,method = RequestMethod.POST)
    @ResponseBody
    public Response getCrmRepayRecords(@RequestBody ParamFilter queryFilter){
        Response response = new Response();
        int pageNo = PageConvert.convert(queryFilter.getPage().getFirstIndex(),queryFilter.getPage().getPageSize());
        PageHelper.startPage(pageNo, queryFilter.getPage().getPageSize());
        List list = repayManageService.getCrmRepayRecords(queryFilter);
        PageInfo pageInfo = new PageInfo(list);
        response.setData(pageInfo);
        return response;
    }

    /**
     * 功能说明：获取划扣记录
     * @author wangmin 2017年7月3日 15:18:08
     * @param 
     * @return
     * 最后修改时间：
     * date:
     * 修改内容：
     * 修改注意点：
     *
     */
    @RequestMapping("getTransferRecord")
    @ResponseBody
    public Response getTransferRecord(@RequestBody ParamFilter queryFilter) {
        Response response = new Response();
        int pageNo = PageConvert.convert(queryFilter.getPage().getFirstIndex(),queryFilter.getPage().getPageSize());
        PageHelper.startPage(pageNo, queryFilter.getPage().getPageSize());
        List list = repayManageService.getTransferRecord(queryFilter);
        PageInfo pageInfo = new PageInfo(list);
        response.setData(pageInfo);
        return response;
    }

    /**
     * 查看结算户银行卡信息
     * @param
     * @return
     */
    @RequestMapping(value = "getCustBankCardInfo" ,method = RequestMethod.POST)
    @ResponseBody
    public Response getCustBankCardInfo(String customerId){
        Response response = new Response();
        List cardList = repayManageService.getCustBankCardInfo(customerId);
        Map map = new HashMap();
        map.put("cardList",cardList);
        Customer customer = customerService.findOne(customerId);
        map.put("customer",customer);
        response.setData(map);
        return response;
    }

    /**
     * 获得还款数据
     * @param
     * @return
     */
    @RequestMapping(value = "getPayInfo" ,method = RequestMethod.POST)
    @ResponseBody
    public Response getPayInfo(String crmOrderId,int type) throws Exception{
        Response response = new Response();
        List productList = repayManageService.getPayProductInfo(crmOrderId);
        Map map = new HashMap();
        if(!productList.isEmpty()){
            map.put("productList",productList.get(0));
        }
        //进行客户和订单的信息查询
        List customerList = customerService.getCustInfo(crmOrderId);
        if(!customerList.isEmpty()){
            map.put("customerList",customerList.get(0));
        }

        if(type== 0){  //正常还款信息
            //resultJson.putAll(normalPayInfo(crmOrderId,type));
            JSONObject normalPayJson = repayManageService.getNormalPayJson(crmOrderId,"0");
            if("1".equals(normalPayJson.getString("responseCode"))){
                map.put("normalPayJson",normalPayJson);
                response.setData(map);
                return response;
            }else{
                response.setMsg(normalPayJson.getString("info"));
                response.setCode(1);
                return response;
            }
        }
        if(type == 1){ //逾期还款信息
            JSONObject overPayJson = repayManageService.getPayApplyInfoFromType(crmOrderId,"1");
            if("1".equals(overPayJson.getString("has"))){
                map.put("overPayJson",overPayJson);
                response.setData(map);
                return response;
            }else{
                response.setMsg("未发现申请单!");
                return response;
            }
        }
        if(type == 2){ //部分逾期还款信息
            //resultJson.putAll(partOverPayInfo(crmOrderId));
        }
        if(type == 3){ //提前结清信息
            //resultJson.putAll(advanceClearPayInfo(crmOrderId));
        }
        if(type == 4){ //提前收回信息
            //resultJson.putAll(advanceBuyBackPayInfo(crmOrderId));
        }
        if(type == -1){ //提前结清信息获得
            //resultJson.putAll(advanceClearApplyPayInfo(crmOrderId,type));
        }
        response.setData(map);
        return response;
    }

    /**
     * 开始进行真正的还款操作V3
     * @param
     * @return
     */
    @RequestMapping(value = "payMoney" ,method = RequestMethod.POST)
    @ResponseBody
    public Response payMoney(String data) throws Exception{
        Response response = new Response();
        JSONObject repayMap =(JSONObject) JSON.parseObject(data);
        JSONObject resultJson = repayManageService.payMoney(repayMap.toString(),repayMap.get("type").toString());
        if(resultJson == null){
            response.setMsg("操作异常,请联系管理员!");
            response.setCode(1);
            return response;
        }
        response.setData(resultJson);
        return response;
    }
}
