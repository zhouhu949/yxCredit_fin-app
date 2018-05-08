package com.zw.rule.web.repayment.controller;//package com.zw.rule.web.LoanController;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zw.rule.core.Response;
import com.zw.rule.mybatis.ParamFilter;
import com.zw.rule.repayment.service.RepaymentService;
import com.zw.rule.web.util.PageConvert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("repayment")
public class RepaymentController {

    @Autowired
    private RepaymentService repaymentService;

    @RequestMapping(value = "list",method = RequestMethod.GET)
    public String list(){
        return "";
    }

    /* *
     * 获取分页的还款计划
     * */
    @RequestMapping(value = "getList" ,method = RequestMethod.POST)
    @ResponseBody
    public Response list(@RequestBody ParamFilter queryFilter){
        Map param = queryFilter.getParam();
        Response response = new Response();
        int pageNo = PageConvert.convert(queryFilter.getPage().getFirstIndex(),queryFilter.getPage().getPageSize());
        PageHelper.startPage(pageNo, queryFilter.getPage().getPageSize());
        List list = repaymentService.getRepaymentList(param);
        PageInfo pageInfo = new PageInfo(list);
        response.setData(pageInfo);
        return response;
    }

    /* *
    * 获取分页的还款计划商品贷
    * */
    @RequestMapping(value = "getListSP" ,method = RequestMethod.POST)
    @ResponseBody
    public Response listSP(@RequestBody ParamFilter queryFilter){
        Map param = queryFilter.getParam();
        Response response = new Response();
        int pageNo = PageConvert.convert(queryFilter.getPage().getFirstIndex(),queryFilter.getPage().getPageSize());
        PageHelper.startPage(pageNo, queryFilter.getPage().getPageSize());
        List list = repaymentService.getRepaymentList(param);
        PageInfo pageInfo = new PageInfo(list);
        response.setData(pageInfo);
        return response;
    }
}
