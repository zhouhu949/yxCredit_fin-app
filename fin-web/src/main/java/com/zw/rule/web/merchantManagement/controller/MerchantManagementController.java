package com.zw.rule.web.merchantManagement.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zw.rule.core.Response;
import com.zw.rule.merchantManagement.MerchantGrade;
import com.zw.rule.merchantManagement.MerchantGradeService;
import com.zw.rule.mybatis.ParamFilter;
import com.zw.rule.officeClerkEntity.OfficeClerkManager;
import com.zw.rule.po.User;
import com.zw.rule.service.DictService;
import com.zw.rule.web.util.PageConvert;
import com.zw.rule.web.util.UserContextUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 商户分级设置merchantGrade的web层访问处理方法
 * Created by Administrator on 2017/12/4.
 */
@Controller
@RequestMapping("merchantGrade")
public class MerchantManagementController {
    /**
     * 跳转商户分级设置界面
     * @return
     */
    @Resource
    private MerchantGradeService MGservice;
    @Resource
    private DictService dictService;
    @RequestMapping("listPage")
    public String ListPage(){
        return ("merchantManagement/merchantGrade");
    }
    /**
     * 查询所有商户分级级别MerchantGrade
     */
    @RequestMapping("getAllGrades")
    @ResponseBody
    public Response getAllGrades(@RequestBody ParamFilter queryFilter){
        int pageNo = PageConvert.convert(queryFilter.getPage().getFirstIndex(),queryFilter.getPage().getPageSize());
        PageHelper.startPage(pageNo, queryFilter.getPage().getPageSize());
        List<MerchantGrade> list=MGservice.getAllMerchantGrades(queryFilter.getParam());
        PageInfo pageInfo = new PageInfo(list);
        return new Response(pageInfo);
    }

    /**
     * 动态加载商户界别下拉选
     * @return
     */
    @RequestMapping("apendSelected")
    @ResponseBody
    public Response apendSelected(){
        Response response = new Response();
        List list=dictService.getDetailListByCode("merchantGrade");
        if (list!=null && list.size()>0){
            response.setData(list);
        }else {
            return null;
        }
        return response;
    }

    /**
     * 添加商户等级的方法
     * @param grade
     * @return
     */
    @RequestMapping("addmerchantGrade")
    @ResponseBody
    public Response addmerchantGrade(@RequestBody MerchantGrade grade){
        Response response=new Response();
        System.out.println(grade);
        grade.setId(UUID.randomUUID().toString());
        int i= MGservice.addMerchantGrade(grade);
        if(i>0){
            response.setMsg("添加成功~");
        }else {
            response.setMsg("添加失败~");
        }
        return response;
    }

    /**
     * 停用或启用按钮访问
     * @param
     * @return
     */
    @RequestMapping("startOrStopGrade")
    @ResponseBody
    public Response startOrStopGrade(@RequestBody Map map){
        Response response=new Response();
        System.out.println(map);
        int i=MGservice.sartOrStopGrade(map);
        if(i>0){
            response.setMsg("修改成功");
        }else{
            response.setMsg("修改失败");
        }
        return response;
    }


    /**
     * 单个设置信息查询
     * @param
     * @return
     */
    @RequestMapping("getOneGradebyId")
    @ResponseBody
    public Response getOneGradebyId(@RequestBody Map map){
        Response response=new Response();
        response.setData(MGservice.getOneGradeById(map));
        return response;
    }

    /**
     * 修改商户配置级别信息
     * @param
     * @return
     */
    @RequestMapping("changeOneGradebyId")
    @ResponseBody
    public Response changeOneGradebyId(@RequestBody MerchantGrade merchantGrade){
        Response response=new Response();
        System.out.println(merchantGrade);
        response.setMsg("ok");
        int i=MGservice.changeOneGradeById(merchantGrade);
        if(i>0){
            response.setMsg("修改成功~");
        }else{
            response.setMsg("修改失败~");
        }
        return response;
    }
}
