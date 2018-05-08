package com.zw.rule.web.merchantManagement.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zw.rule.core.Response;
import com.zw.rule.merchant.Merchant;
import com.zw.rule.merchantManagement.MerchantAntiFraudService;
import com.zw.rule.merchantManagement.MerchantCheckService;
import com.zw.rule.merchantManagement.MerchantListService;
import com.zw.rule.mybatis.ParamFilter;
import com.zw.rule.po.User;
import com.zw.rule.web.util.PageConvert;
import com.zw.rule.web.util.UserContextUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zoukaixuan on 2018/1/8.
 * 商户审核菜单访问的controller
 */
@Controller
@RequestMapping("merchantAntiFraud")
public class MerchantAntiFraudController {
    @Resource
    private MerchantAntiFraudService MAService;
    @Resource
    private MerchantCheckService MCService;
    /**
     * 返回商户检查的网页
     * @return
     */
    @RequestMapping("merChantAntiFraudMain")
    public String getMerchantCheck(){
        return "merchantManagement/merchantAntiFraudMain";
    }
    /**
     * 展示被发起反欺诈的商户列表
     * @param queryFilter
     * @return
     */
    @RequestMapping("allAntiFraudMerchantsList")
    @ResponseBody
    public Response checkMerchansList(@RequestBody ParamFilter queryFilter) {
        int pageNo = PageConvert.convert(queryFilter.getPage().getFirstIndex(), queryFilter.getPage().getPageSize());
        PageHelper.startPage(pageNo, queryFilter.getPage().getPageSize());
        Map map = queryFilter.getParam();
        PageInfo pageInfo = new PageInfo(MAService.getAntiFraudMerchants(map));
        return new Response(pageInfo);
    }

    /**
     * 处理商户反欺诈商户
     * @param map
     * @return
     */
    @RequestMapping("dealAntiFraudMerchant")
    @ResponseBody
    public Response dealAntiFraudMerchant(@RequestBody Map map) {
        User user = (User) UserContextUtil.getAttribute("currentUser");
        Response response = new Response();
        //添加反欺诈图片
        MAService.addAntiFraudImgsToMerchant(map);
        //反欺诈备注
        Map m=new HashMap<String,String>();
        m.put("type","2");//1客户id，2商户id
        m.put("suggestion",map.get("fanQiZhaBeiiZhu").toString());//备注
        m.put("handlerId",user.getUserId());//处理人id
        m.put("handlerName",user.getTrueName());//处理人姓名
        m.put("merchantId",map.get("merchantId"));
        MAService.writeSuggestion(m);
        //更改商户反欺诈状态
        Map m1=new HashMap<String,String>();
        m1.put("merchantId",map.get("merchantId").toString());
        m1.put("fanQIZhaState","2");//2-反欺诈结束
        MAService.changMerchantFanQiZhaState(m1);
        response.setMsg("操作成功");
        return response;
    }
}
