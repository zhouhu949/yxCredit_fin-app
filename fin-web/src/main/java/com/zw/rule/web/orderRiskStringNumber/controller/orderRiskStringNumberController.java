package com.zw.rule.web.orderRiskStringNumber.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zw.rule.core.Response;
import com.zw.rule.mybatis.ParamFilter;
import com.zw.rule.riskNumberService.RiskNumberService;
import com.zw.rule.web.util.PageConvert;
import com.zw.rule.web.util.UserContextUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by zoukaixuan on 2018/1/16.
 * 商品串号码管理的controller
 */
@Controller
@RequestMapping("orderRiskStringNumber")
public class orderRiskStringNumberController {
    @Resource
    private RiskNumberService orderService;
    /**
     * 返回商品串号吗管理页面
     * @return
     */
    @RequestMapping("page")
    public String getPage(){
        return "orderManage/orderRiskStringNumber";
    }

    //订单列表
    @PostMapping("getSubmitList")
    @ResponseBody
    public Response getSubmitList(@RequestBody ParamFilter queryFilter){
        int pageNo = PageConvert.convert(queryFilter.getPage().getFirstIndex(),queryFilter.getPage().getPageSize());
        PageHelper.startPage(pageNo, queryFilter.getPage().getPageSize());
        Map map=queryFilter.getParam();
        map.put("orgid", UserContextUtil.getOrganId());
        map.put("account", UserContextUtil.getAccount());
        List list = orderService.getOrderRiskStringNumberList(map);
        PageInfo pageInfo = new PageInfo(list);
        return new Response(pageInfo);
    }
}
