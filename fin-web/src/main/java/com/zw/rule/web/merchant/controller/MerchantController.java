package com.zw.rule.web.merchant.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zw.base.util.DateUtils;
import com.zw.rule.core.Response;
import com.zw.rule.mybatis.ParamFilter;
import com.zw.rule.po.SysDepartment;
import com.zw.rule.reclaimMerchant.ReclaimMerchant;
import com.zw.rule.reclaimMerchant.service.ReclaimMerchantService;
import com.zw.rule.service.SysDepartmentService;
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
import java.util.UUID;

/**
 * Created by user on 2017/12/21.
 */
@Controller
@RequestMapping("reclaimMerchant")
public class MerchantController {

//    @Resource
//    private  User user;

    @Resource
    private SysDepartmentService departmentService;

    @Resource
    private ReclaimMerchantService Service;
    @RequestMapping("reclaimMerchant")
    public String recommend() {
        return "reclaimMerchant/reclaimMerchant";
    }

    /**
     * 列表展示
     * @param queryFilter
     * @return
     */
    @RequestMapping("getMerchantList")
    @ResponseBody
    public Response getRecommendList(@RequestBody ParamFilter queryFilter){
        int pageNo = PageConvert.convert(queryFilter.getPage().getFirstIndex(),queryFilter.getPage().getPageSize());
        PageHelper.startPage(pageNo, queryFilter.getPage().getPageSize());
        List<ReclaimMerchant> list = Service.getMerchantList(queryFilter.getParam());
        PageInfo pageInfo = new PageInfo(list);
        return new Response(pageInfo);
    }

    @RequestMapping("getRelationList")
    @ResponseBody
    public Response getMagList() {
        Response response=new Response();
        List<SysDepartment>servicePackageList=departmentService.getTypList();
        response.setData(servicePackageList);
        return  response;
    }

    @RequestMapping("getRelationListByDepID")
    @ResponseBody
    public Response getRelationListByDepID() {
        Response response=new Response();
        List<SysDepartment>servicePackageList=departmentService.getTypList();
        response.setData(servicePackageList);
        return  response;
    }

    /**
     * 添加方法
     * @param merchant
     * @return
     */
    @RequestMapping("addMerchant")
    @ResponseBody
    public Response addMerchant(@RequestBody ReclaimMerchant merchant) throws Exception{
        Response response=new Response();
        //  后台验证
        Map map =new HashMap();
        map.put("dep_id",merchant.getDepId());
        List<ReclaimMerchant> list =Service.getMerchantListByDepId(map);
        if (list.isEmpty() && list==null){
            String name=UserContextUtil.getNickName();
            String date=DateUtils.getNowDate();
            merchant.setUpdateTime(date);
            merchant.setUpdateUser(name);
            merchant.setId(UUID.randomUUID().toString());
            int i=Service.addMerchant(merchant);
            if(i>0){
                response.setMsg("添加成功");
            }else{
                response.setCode(1);
                response.setMsg("添加失败");
            }
        }else {
            response.setMsg("该公司已存在");
            response.setCode(1);
        }
        return response;

    }








    @RequestMapping("updateMerchant")
    @ResponseBody
    public Response updateMerchant(@RequestBody ReclaimMerchant merchant){
//       String old = Service.getById(map).getDepId();

        ReclaimMerchant old = Service.getById(merchant.getId());
        String  oldDepId =old.getDepId();
        Response response=new Response();
            if (merchant.getDepId().equals(oldDepId)){
                String name=UserContextUtil.getNickName();
                merchant.setUpdateUser(name);
                String date=DateUtils.getNowDate();
                merchant.setUpdateTime(date);
                int count=Service.updateMerchant(merchant);
                if (count>0){
                    response.setMsg("修改成功！");
                }else {
                    response.setMsg("修改失败！");
                }
            }else {
                Map map =new HashMap();
                map.put("dep_id",merchant.getDepId());
                List<ReclaimMerchant> list =Service.getMerchantListByDepId(map);
                if (list.isEmpty() && list==null){
                    String name=UserContextUtil.getNickName();
                    String date=DateUtils.getNowDate();
                    merchant.setUpdateTime(date);
                    merchant.setUpdateUser(name);
                    merchant.setId(UUID.randomUUID().toString());
                    int i=Service.addMerchant(merchant);
                    if(i>0){
                        response.setMsg("添加成功~");
                    }else{
                        response.setMsg("添加失败~");
                    }
                }else {
                    response.setMsg("该公司已存在~");
                }
            }
        return response;
   }

    @RequestMapping("getById")
    @ResponseBody
    public Response getById(@RequestBody Map param){
        Response response = new Response();
        ReclaimMerchant merchant=Service.getById(param.get("id").toString());
        if (merchant!=null){
            response.setData(merchant);
        }else {
            return null;
        }
        return response;
    }

    @RequestMapping("updateStatus")
    @ResponseBody

    public Response updateStatus(@RequestBody ReclaimMerchant merchant){
        Response response=new Response();

        String name=UserContextUtil.getNickName();
        merchant.setUpdateUser(name);
        String date=DateUtils.getNowDate();
        merchant.setUpdateTime(date);

        int count=Service.updateStatus(merchant);
        if (count>0){
            response.setMsg("修改成功！");
        }else {
            response.setMsg("修改失败！");
        }
        return response;
    }

    @RequestMapping("deleteMerchant")
    @ResponseBody
    public Response deleteMerchant(@RequestBody Map map){
        Response response=new Response();
        int count=Service.deleteMerchant(map.get("id").toString());
        if (count>0){
            response.setMsg("删除成功！");
        }else {
            response.setMsg("删除失败！");
        }
        return response;
    }


}

