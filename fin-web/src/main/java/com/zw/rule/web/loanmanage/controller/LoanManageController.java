package com.zw.rule.web.loanmanage.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zw.rule.core.Response;
import com.zw.rule.customer.service.OrderService;
import com.zw.rule.loanManage.service.LoanManageService;
import com.zw.rule.mybatis.ParamFilter;
import com.zw.rule.po.User;
import com.zw.rule.repayment.po.MagRepayment;
import com.zw.rule.repayment.service.MagRepaymentService;
import com.zw.rule.web.util.PageConvert;
import com.zw.rule.web.util.UserContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * <strong>Title : <br>
 * </strong> <strong>Description : </strong>@类注释说明写在此处@<br>
 * <strong>Create on : 2017年11月17日<br>
 * </strong>
 * <p>
 * <strong>Copyright (C) Vbill Co.,Ltd.<br>
 * </strong>
 * <p>
 *
 * @author department:技术开发部 <br>
 *         username:yaoxuetao <br>
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
@RequestMapping("loanManageController")
public class LoanManageController {

    @Autowired
    private LoanManageService loanManageService;
    @Autowired
    private MagRepaymentService magRepaymentService;

    @Autowired
    OrderService orderService;
    /**
     * 跳转页面
     */
    @RequestMapping("loanManagePage")
    public String loanManagePage(){
        return "loanManage/loanManage";
    }


    /**
     * 查询放款表
     * @return
     */

    @RequestMapping("getLoanManage")
    @ResponseBody
    public Response  getLoanManage(@RequestBody ParamFilter queryFilter){
        Map map=queryFilter.getParam();
        map.put("orgid", UserContextUtil.getOrganId());
        map.put("account", UserContextUtil.getAccount());
        //获取订单访问权限
        map=orderService.getJurisdiction(map);
        int pageNo = PageConvert.convert(queryFilter.getPage().getFirstIndex(),queryFilter.getPage().getPageSize());
        PageHelper.startPage(pageNo, queryFilter.getPage().getPageSize());
//        List list = loanManageService.getLoanManage(map);
        List list = loanManageService.getRepaymentList(map);
        PageInfo pageInfo = new PageInfo(list);
        return new Response(pageInfo);
    }

    /**
     * 查询放款计划
     *
     */
    @RequestMapping("getRepayment")
    @ResponseBody
    public Response  getRepayment(@RequestBody ParamFilter queryFilter){
        int pageNo = PageConvert.convert(queryFilter.getPage().getFirstIndex(),queryFilter.getPage().getPageSize());
        PageHelper.startPage(pageNo, queryFilter.getPage().getPageSize());
        List<MagRepayment> list = magRepaymentService.getRepayment(queryFilter.getParam().get("parentId").toString());
        PageInfo pageInfo = new PageInfo(list);
        return new Response(pageInfo);
    }

    /**
     * 获取结清数据
     *
     */
    @RequestMapping("getSettleData")
    @ResponseBody
    public Response  getSettleData(@RequestBody Map map){
        return loanManageService.getSettleData(map);
    }

    /**
     * 添加修改结清数据
     *
     */
    @RequestMapping("addSettleData")
    @ResponseBody
    public Response  addSettleData(@RequestBody Map map){
        //修改
        if ("1".equals(map.get("type"))){
            return loanManageService.updateSettleData(map);
        }//添加
        else {
            User user = (User) UserContextUtil.getAttribute("currentUser");
            map.put("handlerId",user.getUserId());
            map.put("handlerName",user.getTrueName());
            return loanManageService.addSettleData(map);
        }
    }

    /**
     * 获取交易明细记录
     * @param queryFilter
     * @return
     */
    @RequestMapping("getJYDetailList")
    @ResponseBody
    public Response  getJYDetailList(@RequestBody ParamFilter queryFilter){
        Map map=queryFilter.getParam();
        int pageNo = PageConvert.convert(queryFilter.getPage().getFirstIndex(),queryFilter.getPage().getPageSize());
        PageHelper.startPage(pageNo, queryFilter.getPage().getPageSize());
        List list = loanManageService.getJYDetailList(map);
        PageInfo pageInfo = new PageInfo(list);
        return new Response(pageInfo);
    }


}

