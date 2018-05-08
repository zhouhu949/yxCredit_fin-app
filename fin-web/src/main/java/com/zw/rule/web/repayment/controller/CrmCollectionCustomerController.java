package com.zw.rule.web.repayment.controller;//package com.zw.rule.web.LoanController;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zw.rule.core.Response;
import com.zw.rule.customer.po.Customer;
import com.zw.rule.customer.service.CustomerService;
import com.zw.rule.mapper.repayment.CrmPaycontrolMapper;
import com.zw.rule.mapper.repayment.CrmPayrecoderMapper;
import com.zw.rule.mybatis.ParamFilter;
import com.zw.rule.repayment.po.CrmPaycontrol;
import com.zw.rule.repayment.service.RepayManageService;
import com.zw.rule.web.util.PageConvert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 信贷催收客户 controller
 */
@Controller
@RequestMapping("collection")
public class CrmCollectionCustomerController {

    @Autowired
    private CrmPaycontrolMapper crmPaycontrolMapper;

    /**
     * 更新 信贷客户的 正常还款 申请状态
     * @param
     * @return
     */
    @RequestMapping(value = "findCrmPaycontrolByPayReceivable" ,method = RequestMethod.POST)
    @ResponseBody
    public Response findCrmPaycontrolByPayReceivable(String orderId,boolean repay){
        Response response = new Response();
        //查询 是否有申请中，等待还款的
        List<Map> payLists = crmPaycontrolMapper.getDetailByStatus(orderId,3);
        if(payLists.size()>0){
            response.setCode(1);
            response.setMsg("申请中，请等待还款完成后再操作！");
            return response;
        }
        if(!StringUtils.isEmpty(repay)){
            //找到所有待还
            List<CrmPaycontrol> pay = crmPaycontrolMapper.getPayList(orderId);
            if(!pay.isEmpty()){
                //找到所有待还 的第一条
                pay.get(0).setReplaceStatus(3);
                int num = crmPaycontrolMapper.updateByPrimaryKey(pay.get(0));
                if(num>0){
                    response.setMsg("还款成功！");
                }
            }
        }
        return response;
    }

}
