package com.zw.rule.web.merchantManagement.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zw.rule.approveRecord.po.ProcessApproveRecord;
import com.zw.rule.core.Response;
import com.zw.rule.merchantManagement.MerchantCheckService;
import com.zw.rule.merchantManagement.MerchantListService;
import com.zw.rule.mybatis.ParamFilter;
import com.zw.rule.mybatis.page.Page;
import com.zw.rule.po.User;
import com.zw.rule.service.UserService;
import com.zw.rule.web.util.PageConvert;
import com.zw.rule.web.util.UserContextUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zoukaixuan on 2018/1/8.
 * 商户审核菜单访问的controller
 */
@Controller
@RequestMapping("merchantCheck")
public class MerchantCheckController {
    @Resource
    private MerchantCheckService MCService;
    @Resource
    private MerchantListService MLService;
    @Resource
    private UserService userService;
    /**
     * 返回商户检查的网页
     * @return
     */
    @RequestMapping("merchantCheck")
    public String getMerchantCheck(){
        return "merchantManagement/merchantCheck";
    }
    /**
     * 展示商户审核列表
     *
     * @param queryFilter
     * @return
     */
    @RequestMapping("checkMerchantsList")
    @ResponseBody
    public Response checkMerchansList(@RequestBody ParamFilter queryFilter) {
        int pageNo = PageConvert.convert(queryFilter.getPage().getFirstIndex(), queryFilter.getPage().getPageSize());
        PageHelper.startPage(pageNo, queryFilter.getPage().getPageSize());
        Map map = queryFilter.getParam();
        PageInfo pageInfo = new PageInfo(MCService.getCheckMerchantList(map));
        return new Response(pageInfo);
    }

    /**
     * 审核商户的方法
     * @param map
     * @return
     */
    @RequestMapping("checkOneMerchant")
    @ResponseBody
    public Response checkOneMerchant(@RequestBody Map map) {
        Response response = new Response();
        User user = (User) UserContextUtil.getAttribute("currentUser");
        if(map.get("checkResult").toString().equals("2")){
            map.put("result","1");//结果 1通过  0拒绝
        }
        if(map.get("checkResult").toString().equals("3")){
            map.put("result","0");//结果 1通过  0拒绝
        }
        map.put("handlerId",user.getUserId());//处理人id
        map.put("handlerName",user.getTrueName());//处理人姓名
        map.put("type","2");//1客户id，2商户id
//        //插入处理建议到建议表
//        MCService.writeSuggestion(map);
//        //更改商户审核状态
//        int i= MCService.changeMerchantCheckState(map);
//        //更改商户等级
//        Map m= new HashMap<String,String>();
//        m.put("merchantId",map.get("merchantId"));
//        m.put("merGrade",map.get("merchantGrade"));
//        int j= MLService.changeMerchantGrade(m);
        boolean b=MCService.auditOneMerchantSer(map);
        if(b){
            response.setMsg("操作成功");
        }else{
            response.setMsg("操作失败");
        }
        return response;
    }
    //
    /**
     * 获取所有的反欺诈专员
     * @param queryFilter
     * @return
     */
    @RequestMapping("getAllAutiFraudMan")
    @ResponseBody
    public Response getAllAutiFraudMan(@RequestBody ParamFilter queryFilter) {
        User user=(User) UserContextUtil.getAttribute("currentUser");
        long orgId=user.getOrgId();
        Map<String, Object> Param = queryFilter.getParam();
        if (UserContextUtil.getAttribute("roleId").equals("1")){
            Param.put("orgId", null);
        }else {
            Param.put("orgId", orgId);
        }
        queryFilter.setParam(Param);
        List userList = userService.getList(queryFilter);
        Page page = queryFilter.getPage();
        PageInfo pageInfo = new PageInfo(userList);
        return new Response(pageInfo);
    }

    /**
     * 商户反欺诈专员分配
     * @param map
     * @return
     */
    @RequestMapping("merchantAutiFraudAllot")
    @ResponseBody
    public Response merchantAutiFraudAllot(@RequestBody Map map) {
        Response response = new Response();
        //反欺诈状态更改为1
        map.put("autiFraudState","1");
        int i=MCService.changeFanQiZhaState(map);
        if(i>0){
            response.setMsg("操作成功");
        }else{
            response.setMsg("操作失败");
        }
        return response;
    }

    /**
     * 获取审核意见(参数 关联id type nodeId)
     * @param paramSug
     * @return
     */
    @RequestMapping("getMerchantSuggestion")
    @ResponseBody
    public Response getMerchantSuggestion(@RequestBody Map paramSug) {
        Response response = new Response();
        ProcessApproveRecord p=MCService.getSuggestion(paramSug);
        response.setData(p);
        return response;
    }

}
