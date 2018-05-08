package com.zw.rule.web.investor.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zw.rule.core.Response;
import com.zw.rule.customer.service.OrderService;
import com.zw.rule.investor.po.Investor;
import com.zw.rule.investor.service.InvestorService;
import com.zw.rule.mybatis.ParamFilter;
import com.zw.rule.po.User;
import com.zw.rule.web.aop.annotaion.WebLogger;
import com.zw.rule.web.util.PageConvert;
import com.zw.rule.web.util.ServletUtil;
import com.zw.rule.web.util.UserContextUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Administrator on 2017/7/13.
 */
@Controller
@RequestMapping("investor")
public class InvestorManageController {
    @Resource
    private InvestorService investorService;

    @GetMapping("list")
    public String list(){
        return "investorManage/investor";
    }
    @PostMapping("getList")
    @ResponseBody
    @WebLogger("查询投资人列表")
    public Response list(@RequestBody ParamFilter queryFilter){
        Map param = queryFilter.getParam();
        Response response = new Response();
        int pageNo = PageConvert.convert(queryFilter.getPage().getFirstIndex(),queryFilter.getPage().getPageSize());
        PageHelper.startPage(pageNo, queryFilter.getPage().getPageSize());
        List list = investorService.getInvestorList(queryFilter.getParam());
        PageInfo pageInfo = new PageInfo(list);
        response.setData(pageInfo);
        return response;
    }

    @ResponseBody
    @PostMapping("add")
    @WebLogger("添加投资人")
    public Response add(@RequestBody Investor investor) {
        checkNotNull(investor, "投资人不能为空");
        String id = UUID.randomUUID().toString();
        investor.setId(id);
        Boolean flag = investorService.addInvestor(investor);
        Response response = new Response();
        if(flag){
            response.setMsg("添加成功");
        }else {
            response.setMsg("添加失败");
        }
        return response;
    }

    @ResponseBody
    @PostMapping("detail")
    @WebLogger("查询用户详细")
    public Response detail(@RequestBody Map map) {
        String investorId = String.valueOf(map.get("investorId"));
        Investor investor = investorService.selectInvestorById(investorId);
        return new Response(investor);
    }
    @ResponseBody
    @PostMapping("edit")
    @WebLogger("修改投资人")
    public Response edit(@RequestBody Investor investor) {
        Boolean flag =investorService.updtaeInvestorById(investor);
        Response response = new Response();
        if(flag){
            response.setMsg("修改成功");
        }else {
            response.setMsg("修改失败");
        }
        return response;
    }

    @ResponseBody
    @PostMapping("delete")
    @WebLogger("删除投资人")
    public Response delete(@RequestBody Map map) {
        Boolean flag = investorService.deleteInvestorById(String.valueOf(map.get("id")));
        Response response = new Response();
        if(flag){
            response.setMsg("删除成功");
        }else {
            response.setMsg("删除失败");
        }
        return response;
    }

}
