package com.zw.rule.web.customer.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zw.rule.core.Response;
import com.zw.rule.customer.po.Customer;
import com.zw.rule.customer.service.CustomerConnectService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <strong>Title : <br>
 * </strong> <strong>Description : </strong>@类注释说明写在此处@<br>
 * <strong>Create on : 2017年06月29日<br>
 * </strong>
 * <p>
 * <strong>Copyright (C) Vbill Co.,Ltd.<br>
 * </strong>
 * <p>
 *
 * @author department:技术开发部 <br>
 *         username:Administrator <br>
 *         email: <br>
 * @version <strong>zw有限公司-运营平台</strong><br>
 *          <br>
 *          <strong>修改历史:</strong><br>
 *          修改人 修改日期 修改描述<br>
 *          -------------------------------------------<br>
 *          <br>
 *          <br>
 */
@Controller
@RequestMapping("cusConnect")
public class CustomerConnectController {
    @Resource
    private CustomerConnectService connectService;
    @GetMapping("index")
    public String getList(){
        return "customerManage/customerConnect";
    }

    @PostMapping("change")
    @ResponseBody
    public Response change(@RequestBody Customer customer){
        Response response = new Response();
        Boolean flag = connectService.change(customer);
        if(flag){
            response.setMsg("交接成功！");
        }else{
            response.setCode(1);
            response.setMsg("交接失败！");
        }
        return response;
    }

    @PostMapping("changeAll")
    @ResponseBody
    public Response changeAll(@RequestBody Map map){
        Response response = new Response();
        JSONObject jsonObject = (JSONObject) JSONObject.toJSON(map);
        JSONArray idArray= jsonObject.getJSONArray("cusIds");
        String empId =(String) map.get("empId");
        List list = new ArrayList();
        if(idArray!=null && idArray.size()>0){
            for(int i=0;i<idArray.size();i++){
                Customer customer = new Customer();
                customer.setId(idArray.get(i).toString());
                customer.setEmpId(empId);
                list.add(customer);
            }
        }else{
            response.setCode(1);
            response.setMsg("交接失败！");
            return response;
        }
        if(list.size()>0){
            Boolean flag = connectService.changeAll(list);
            if(flag){
                response.setMsg("交接成功");
            }else{
                response.setCode(1);
                response.setMsg("交接失败！");
                return response;
            }
        }else{
            response.setCode(1);
            response.setMsg("交接失败！");
            return response;
        }

        return response;
    }
}