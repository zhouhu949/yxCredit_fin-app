package com.zw.rule.web.coupon.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zw.rule.core.Response;
import com.zw.rule.coupon.po.Recommend;
import com.zw.rule.couponService.service.CouponRecommendService;
import com.zw.rule.mybatis.ParamFilter;
import com.zw.rule.web.aop.annotaion.WebLogger;
import com.zw.rule.web.util.PageConvert;
import com.zw.rule.web.util.PropertiesUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Administrator on 2017/12/11.
 */
@Controller
@RequestMapping("cuponRecommendController")
public class CuponRecommendController {
    @Resource
    private CouponRecommendService CRService;

    @RequestMapping("recommend")
    public String recommend() {
        return "couponManage/recommend";
    }

    /**
     * 列表展示
     * @param queryFilter
     * @return
     */
    @RequestMapping("getRecommendList")
    @ResponseBody
    public Response getRecommendList(@RequestBody ParamFilter queryFilter){
        int pageNo = PageConvert.convert(queryFilter.getPage().getFirstIndex(),queryFilter.getPage().getPageSize());
        PageHelper.startPage(pageNo, queryFilter.getPage().getPageSize());
        List<Recommend> list = CRService.getAllRecommend(queryFilter.getParam());
        PageInfo pageInfo = new PageInfo(list);
        return new Response(pageInfo);
    }

    /**
     * 添加方法
     * @param recommend
     * @return
     */
    @RequestMapping("addRecommend")
    @ResponseBody
    public Response addRecommend(@RequestBody Recommend recommend){
        Response response=new Response();
        recommend.setId(UUID.randomUUID().toString());
        recommend.setState("1");
        List<String>list=CRService.getAllCodes();
        boolean b=list.contains(recommend.getCode());
        if(!b) {
            int i = CRService.addRecommend(recommend);
            if (i > 0) {
                response.setMsg("添加成功~");
            } else {
                response.setMsg("添加失败~");
            }
            return response;
        }else{
            response.setMsg("代码重复无法添加~");
            return response;
        }

    }

    /**
     * 根据id查询单个推广信息
     * @param map
     * @return
     */
    @RequestMapping("showOneRecommend")
    @ResponseBody
    public Response showOneRecommend(@RequestBody Map map){
        Response response=new Response();
        Recommend recommend=CRService.getOneRecommendById(map);
        response.setData(recommend);
        return response;
    }
    /**
     * 根据id编辑单个推广信息
     * @param map
     * @return
     */
    @RequestMapping("editRecommend")
    @ResponseBody
    public Response editRecommend(@RequestBody Map map){
        Response response=new Response();
        int i=CRService.changeRecommendById(map);
        if(i>0){
            response.setMsg("修改成功");
        }else{
            response.setMsg("修改失败");
        }
        return response;
    }

    /**
     * 停用或者启用
     * @param map
     * @return
     */
    @RequestMapping("startOrstopState")
    @ResponseBody
    public Response startOrstopState(@RequestBody Map map){
        Response response=new Response();
        int i=CRService.changeRecommendState(map);
        if(i>0){
            response.setMsg("修改成功");
        }else{
            response.setMsg("修改失败");
        }
        return response;
    }
    /**
     * deleteOneById
     */
    @RequestMapping("deleteOneById")
    @ResponseBody
    public Response deleteOneById(@RequestBody Map map){
        Response response=new Response();
        int i=CRService.deleteOneRecommendById(map);
        if(i>0){
            response.setMsg("删除成功~");
        }else{
            response.setMsg("删除失败~");
        }
        return response;
    }


    @RequestMapping("recommendList")
    public String recommendList() {
        return "couponManage/recommendList";
    }

    @PostMapping("getRecommendListList")
    @ResponseBody
    @WebLogger("获取推广数据列表")
    public Response getRecommendListList(@RequestBody ParamFilter queryFilter) {
        int pageNo = PageConvert.convert(queryFilter.getPage().getFirstIndex(),queryFilter.getPage().getPageSize());
        PageHelper.startPage(pageNo, queryFilter.getPage().getPageSize());
        List<Recommend> list = CRService.getRecommendListList(queryFilter.getParam());
        PageInfo pageInfo = new PageInfo(list);
        return new Response(pageInfo);
    }

    /**
     * 生成推广渠道二维码
     * @param param 推广渠道code
     * @return
     */
    @RequestMapping("/showQRCode")
    @ResponseBody
    public Response showQRCode(@RequestBody Map param) throws Exception{
        Response response=new Response();
        String code = (String)param.get("code");
        if (null == code || code.length() == 0)
        {
            response.setCode(1);
            response.setMsg("推广渠道编码不存在");
            return response;
        }
        //生成二维码
        PropertiesUtil prop = new PropertiesUtil("properties/host.properties");
        String url =prop.get("appHGUrl")+"/sharePromotion?code="+code;
        String img = CRService.createQRCode(url);
        if (null == img || img.length() == 0)
        {
            response.setCode(1);
            response.setMsg("生成二维码错误");
        }
        else
        {
            response.setCode(0);
            response.setData(img);
        }
        return response;
    }
}
