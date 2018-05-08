package com.zw.rule.web.merchantManagement.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zw.rule.core.Response;
import com.zw.rule.merchantManagement.MerchantGrade;
import com.zw.rule.merchantManagement.MerchantListService;
import com.zw.rule.mybatis.ParamFilter;
import com.zw.rule.officeClerk.service.OfficeClerkManageService;
import com.zw.rule.service.DictService;
import com.zw.rule.web.util.PageConvert;
import org.kie.internal.query.QueryFilter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by zoukaixuan on 2017/12/25.
 * 渠道商户管理-->商户列表访问的controller
 */
@Controller
@RequestMapping("merchantListController")
public class MerchantListController {

    @Resource
    private MerchantListService MLService;
    @Resource
    private DictService DService;
    @Resource
    private OfficeClerkManageService salesmanService;
    /**
     * 商户列表的的商户列表信息展示
     * @return
     */
    @RequestMapping("merchantListLsit")
    public String merchantListLsit() {
        return "merchantManagement/merchantList";
    }

    /**
     * 商户列表的列表展示
     * @param queryFilter
     * @return
     */
    @RequestMapping("getAllMerchantListList")
    @ResponseBody
    public Response getAllMerchantListList(@RequestBody ParamFilter queryFilter) {
        int pageNo = PageConvert.convert(queryFilter.getPage().getFirstIndex(), queryFilter.getPage().getPageSize());
        PageHelper.startPage(pageNo, queryFilter.getPage().getPageSize());
        Map map = queryFilter.getParam();
        PageInfo pageInfo = new PageInfo(MLService.getAllMerchants(map));
        return new Response(pageInfo);
    }
    /**
     * getDetailListByCode
     * 动态获取字典表内容 传入字典表大类code
     * 获取企业类型
     */
    @RequestMapping("getMerchantType")
    @ResponseBody
    public Response getMerchantType() {
        Response response = new Response();
        response.setData(DService.getDetailListByCode("merchantType"));
        return response;
    }

    /**
     * 动态加载银行名：如建设银行，浦发银行
     * 动态获取字典表内容 传入字典表大类code
     * 获取企业类型
     */
    @RequestMapping("getBanksHeads")
    @ResponseBody
    public Response getBanksHeads() {
        Response response = new Response();
        response.setData(DService.getDetailListByCode("bankHeads"));
        return response;
    }

    /**
     * 添加商户的方法
     * @param map
     * @return
     */
    @RequestMapping("addMerchant")
    @ResponseBody
    public Response addMerchant(@RequestBody Map map) {
        Response response = new Response();
        int i= MLService.addMerchant(map);
        if(i>0){
            response.setMsg("添加成功");
        }else{
            response.setMsg("添加失败");
        }
        return response;
    }/**
     * 提交审核商户的方法(更改审核状态)
     * @param map
     * @return
     */
    @RequestMapping("submitToCheck")
    @ResponseBody
    public Response submitToCheck(@RequestBody Map map) {
        Response response = new Response();
        int i =MLService.addMerchantToCheck(map);
        if(i>0){
            response.setMsg("操作成功");
        }else{
            response.setMsg("操作失败");
        }
        return response;
    }



    /**
     * 获取单个商户的所有信息
     * 传入id
     * @param map
     * @return
     */
    @RequestMapping("getMerchant")
    @ResponseBody
    public Response getMerchant(@RequestBody Map map) {
        Response response = new Response();
        Map<String,Object>merchantMap= MLService.getMerchantById(map);
        response.setData(merchantMap);
        return response;
    }

    /**
     * 编辑单个商户的方法(编辑的是基本信息和图片信息)
     * @param map
     * @return
     */
    @RequestMapping("editMerchant")
    @ResponseBody
    public Response editMerchant(@RequestBody Map map) {
        Response response = new Response();
        int i=MLService.editMerchantById(map);
        if(i == 2){
            response.setMsg("修改成功");
        }else{
            response.setMsg("修改失败");
        }
        return response;
    }

    /**
     * 编辑单个商户的账户(法人 委托人 公共账号)
     * @param map
     * @return
     */
    @RequestMapping("editMerchantAccount")
    @ResponseBody
    public Response editMerchantAccount(@RequestBody Map map) {
        Response response = new Response();
        int i=MLService.editAccountToMerchant(map);
        if(i==4){
            response.setMsg("修改成功");
        }else{
            response.setMsg("修改失败");
        }
        return response;
    }

    /**
     * 激活或者冻结
     * @param map
     * @return
     */
    @RequestMapping("activateOrFreeze")
    @ResponseBody
    public Response activateOrFreeze(@RequestBody Map map) {
        Response response = new Response();
        int i=MLService.setActivateOrFreeze(map);
        if(i==1){
            response.setMsg("操作成功");
        }else{
            response.setMsg("操作失败");
        }
        return response;
    }

    /**
     * 查询可以被关联的办单员
     * @param queryFilter
     * @return
     */
    @RequestMapping("showCanMerSalesman")
    @ResponseBody
    public Response showCanMerSalesman(@RequestBody ParamFilter queryFilter) {
        int pageNo = PageConvert.convert(queryFilter.getPage().getFirstIndex(), queryFilter.getPage().getPageSize());
        PageHelper.startPage(pageNo, queryFilter.getPage().getPageSize());
        Map map = queryFilter.getParam();
        PageInfo pageInfo = new PageInfo(MLService.getAllSalesman(map));
        return new Response(pageInfo);
    }

    /**
     * 查询所有的商户等级
     * @param map
     * @return
     */
    @RequestMapping("showAllMerchantGrade")
    @ResponseBody
    public Response showAllMerchantGrade(@RequestBody Map map) {
        Response response = new Response();
        List<MerchantGrade> list = MLService.getAllMerchantGrade();
        response.setData(list);
        return response;
    }

    /**
     * 更改商户等级
     * @param map
     * @return
     */
    @RequestMapping("changeMerGrade")
    @ResponseBody
    public Response changeMerGrade(@RequestBody Map map) {
        Response response = new Response();
        int i= MLService.changeMerchantGrade(map);
        if(i>0){
            response.setMsg("修改成功");
        }else{
            response.setMsg("修改失败");
        }
        return response;
    }

    /**
     * 商户绑定绑定办单元
     * @param map
     * @return
     */
    @RequestMapping("bidingSalesman")
    @ResponseBody
    public Response bidingSalesman(@RequestBody Map map) {
        Response response = new Response();
        int i= MLService.bidingSalesmanToMerchant(map);
        if(i==1){
            response.setMsg("绑定成功");
        }else{
            response.setMsg("绑定失败");
        }
        return response;
    }
    /**
     *
     */
    /**
     * 查询该商户下的办单员
     * @param queryFilter
     * @return
     */
    @RequestMapping("showSalesmanFromMerchant")
    @ResponseBody
    public Response showSalesmanFromMerchant(@RequestBody ParamFilter queryFilter) {
        int pageNo = PageConvert.convert(queryFilter.getPage().getFirstIndex(), queryFilter.getPage().getPageSize());
        PageHelper.startPage(pageNo, queryFilter.getPage().getPageSize());
        Map map = queryFilter.getParam();
        PageInfo pageInfo = new PageInfo(MLService.getAllMerchantSalesman(map));
        return new Response(pageInfo);
    }
    /**
     * 解除绑定或者再次绑定
     * @param map
     * @return
     */
    @RequestMapping("bidingOrCancelSalesman")
    @ResponseBody
    public Response bidingOrCancelSalesman(@RequestBody Map map) {
        Response response = new Response();
        int i= MLService.bidingOrCalcelSalesman(map);
        if(i==1){
            response.setMsg("操作成功");
        }else{
            response.setMsg("操作失败");
        }
        return response;
    }
}
