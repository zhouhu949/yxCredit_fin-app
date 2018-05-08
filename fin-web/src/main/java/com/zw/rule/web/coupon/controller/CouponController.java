package com.zw.rule.web.coupon.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mysql.jdbc.StringUtils;
import com.zw.base.util.DateUtils;
import com.zw.rule.core.Response;
import com.zw.rule.coupon.po.CouponManage;
import com.zw.rule.coupon.po.Partners;
import com.zw.rule.couponService.service.CouponService;
import com.zw.rule.mybatis.ParamFilter;
import com.zw.rule.service.DictService;
import com.zw.rule.web.aop.annotaion.WebLogger;
import com.zw.rule.upload.service.UploadService;
import com.zw.rule.web.util.PageConvert;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Administrator on 2017/9/19.
 */
@Controller
@RequestMapping("coupon")
public class CouponController {

    @Resource
    private CouponService couponService;
    @Resource
    private DictService dictService;
    @Resource
    private UploadService uploadService;

    @GetMapping("list")//合作平台
    public String list() {
        return "couponManage/coupon";
    }
    @PostMapping("couponList")
    @ResponseBody
    @WebLogger("合作平台列表")
    public Response orderList(@RequestBody ParamFilter queryFilter){
        int pageNo = PageConvert.convert(queryFilter.getPage().getFirstIndex(),queryFilter.getPage().getPageSize());
        PageHelper.startPage(pageNo, queryFilter.getPage().getPageSize());
        List list = couponService.getCouponList(queryFilter.getParam());
        PageInfo pageInfo = new PageInfo(list);
        return new Response(pageInfo);
    }

    @PostMapping("add")
    @ResponseBody
    @WebLogger("添加合作平台")
    public Response addcoupon(@RequestBody CouponManage couponManage){
        Response response = new Response();
        couponManage.setCreate_time(DateUtils.getCurrentTime(DateUtils.STYLE_10));
        couponManage.setId(UUID.randomUUID().toString());
        int num = couponService.addCoupon(couponManage);
        if (num>0){
            response.setMsg("添加成功！");
        }else {
            response.setMsg("添加失败！");
        }
        return response;
    }



    @PostMapping("update")
    @ResponseBody
    @WebLogger("修改合作平台")
    public Response updateContract(@RequestBody CouponManage couponManage){
        Response response=new Response();
        couponManage.setAlter_time(DateUtils.getCurrentTime(DateUtils.STYLE_10));
        int count=couponService.updateCoupon(couponManage);
        if (count>0){
            response.setMsg("修改成功！");
        }else {
            response.setMsg("修改失败！");
        }
        return response;
    }

    @PostMapping("del")
    @ResponseBody
    @WebLogger("删除合作平台")
    public Response delcoupon(@RequestBody Map map){
        Response response=new Response();
        int count=couponService.deleteCoupon(map.get("id").toString());
        if (count>0){
            response.setMsg("删除成功！");
        }else {
            response.setMsg("删除失败！");
        }
        return response;
    }

    @PostMapping("updateState")
    @ResponseBody
    @WebLogger("修改状态")
    public Response updateState(@RequestBody Map map){
        Response response=new Response();
        int count=couponService.updateState(map);
        if (count>0){
            response.setMsg("修改成功！");
        }else {
            response.setMsg("修改失败！");
        }
        return response;
    }

    @PostMapping("getById")
    @ResponseBody
    @WebLogger("根据id获取详情")
    public Response getById(@RequestBody Map  param){
        Response response = new Response();
        CouponManage couponManage=couponService.getById(param.get("id").toString());
        if (couponManage!=null){
            response.setData(couponManage);
        }else {
            return null;
        }
        return response;
    }
    @PostMapping("apendSelect")
    @ResponseBody
    public Response apendSelect(){
        Response response = new Response();
        List list=dictService.getDetailList("合作平台类型");
        if (list!=null&&list.size()>0){
            response.setData(list);
        }else {
            return null;
        }
        return response;
    }
    @PostMapping("apendSelected")
    @ResponseBody
    public Response apendSelected(){
        Response response = new Response();
        List list=dictService.getDetailList("合作平台状态");
        if (list!=null&&list.size()>0){
            response.setData(list);
        }else {
            return null;
        }
        return response;
    }

    @PostMapping("productConfig")
    @ResponseBody
    public Response productConfig(){
        Response response = new Response();
        List list = couponService.productConfig();
        if (list!=null&&list.size()>0){
            response.setData(list);
        }else {
            return null;
        }
        return response;
    }

    @PostMapping("getCustomerInfo")
    @ResponseBody
    public Response getCustomerInfo(@RequestBody ParamFilter queryFilter){
        int pageNo = PageConvert.convert(queryFilter.getPage().getFirstIndex(),queryFilter.getPage().getPageSize());
        PageHelper.startPage(pageNo, queryFilter.getPage().getPageSize());
        List<Map> list = couponService.getCustomerInfo(queryFilter.getParam());
        PageInfo pageInfo = new PageInfo(list);
        return new Response(pageInfo);
    }

    @PostMapping("getCustomer")
    @ResponseBody
    public Response getCustomer(@RequestBody Map param){
        Response response = new Response();
        List<Map> list = couponService.getCustomerChecked(param.get("coupon_id").toString());
        response.setData(list);
        return response;
    }

    @PostMapping("bindCustomer")
    @ResponseBody
    public Response bindCustomer(@RequestBody Map map){
        Response response = new Response();
        Map magCouponInfoMap = new HashMap();
        String ids[] = map.get("ids").toString().split(",");
        String coupon_id = map.get("coupon_id").toString();
        CouponManage couponManage = couponService.getById(coupon_id);
        couponService.deleteMagCouponInfo(coupon_id);
        magCouponInfoMap.put("coupon_id",coupon_id);
        magCouponInfoMap.put("mag_coupon_name",couponManage.getCoupon_name());
        magCouponInfoMap.put("mag_coupon_amount",couponManage.getCoupon_amount());
        magCouponInfoMap.put("mag_coupon_desc",couponManage.getCoupon_desc());
        magCouponInfoMap.put("mag_coupon_type",couponManage.getCoupon_type());
        magCouponInfoMap.put("mag_coupon_effective_time",couponManage.getCoupon_effective_time());
        magCouponInfoMap.put("mag_coupon_state","1");
        magCouponInfoMap.put("crm_product_id",couponManage.getCrm_product_id());
        magCouponInfoMap.put("create_time",DateUtils.getCurrentTime(DateUtils.STYLE_10));
        int num = 0;
        for (int i =0;i<ids.length;i++){
            if(ids[i]!=null&&ids[i].length()>0){
                String id = UUID.randomUUID().toString();
                magCouponInfoMap.put("id",id);
                magCouponInfoMap.put("customer_id",ids[i]);
                num+=couponService.insertMagCouponInfo(magCouponInfoMap);
            }
        }
        if(num>0){
            response.setMsg("选择客户成功");
        }
        return response;
    }

    @PostMapping("partnersList")
    @ResponseBody
    @WebLogger("合作方列表")
    public Response partnersList(@RequestBody ParamFilter queryFilter){
        int pageNo = PageConvert.convert(queryFilter.getPage().getFirstIndex(),queryFilter.getPage().getPageSize());
        PageHelper.startPage(pageNo, queryFilter.getPage().getPageSize());
        List list = couponService.getPartnersList(queryFilter.getParam());
        PageInfo pageInfo = new PageInfo(list);
        return new Response(pageInfo);
    }


    @PostMapping("addPartners")
    @ResponseBody
    @WebLogger("添加合作方")
    public Response addPartners(@RequestBody Partners partners){
        Response response = new Response();
        partners.setId(UUID.randomUUID().toString());
        int num = couponService.addPartners(partners);
        if (num>0){
            response.setMsg("添加成功！");
        }else {
            response.setMsg("添加失败！");
        }
        return response;
    }

    @PostMapping("updatePartners")
    @ResponseBody
    @WebLogger("修改合作方")
    public Response updatePartners(@RequestBody Partners partners){
        Response response=new Response();
        int count=couponService.updatePartners(partners);
        if (count>0){
            response.setMsg("修改成功！");
        }else {
            response.setMsg("修改失败！");
        }
        return response;
    }

    @PostMapping("getPartnersId")
    @ResponseBody
    @WebLogger("根据id获取详情")
    public Response getPartnersId(@RequestBody Map  param){
        Response response = new Response();
        Partners partners=couponService.getPartnersId(param.get("id").toString());
        //String name = dictService.getDetilNameByCode("ceshi",dictService.getListByCode("host").get(0).getId().toString());
        partners.setActivity_img_url(partners.getPath());
        if (partners!=null){
            response.setData(partners);
        }else {
            return null;
        }
        return response;
    }

    @ResponseBody
    @PostMapping("merUpload")
    public Response upload(HttpServletRequest request) throws Exception{
        Response response = new Response();
        String fileName="";
        Map param = new HashMap();
        //捕获前台传来的图片，并用uuid命名，防止重复
        Map<String, Object> allMap = uploadService.uploadFileOSS(request,"/fintecher_file");
        //当前台有文件时，给图片名称变量赋值
        if (!allMap.isEmpty()) {
            fileName = (String) allMap.get("url");
            response.setMsg("上传成功！");
            param.put("activity_img_id",fileName);
            param.put("activity_img_fileName",fileName);
            response.setData(param);
        }
        if(StringUtils.isNullOrEmpty(fileName)){
            response.setCode(1);
            response.setMsg("上传失败");
            return response;
        }
        return response;
    }

    //动态获取下拉选
    @PostMapping("platformType")
    @ResponseBody
    public Response platformType(){
        Response response = new Response();
        List list=dictService.getDetailList("平台类型");
        if (list!=null&&list.size()>0){
            response.setData(list);
        }else {
            return null;
        }
        return response;
    }
//    @ResponseBody
//    @PostMapping("deleteImg")
//    public Response deleteImg(String id, HttpServletRequest request) throws Exception{
//        Response response = new Response();
//        CustomerImage customerImage = merchantUploadService.selectByid(id);
//        if(customerImage==null){
//            response.setCode(1);
//            response.setMsg("删除失败！");
//            return response;
//        }
//        //先删除图片
//        String img = customerImage.getSrc();
//        String root = request.getSession().getServletContext().getRealPath("fintecher_file");
//        String filePath = root+ File.separator + img;
//        File file = new File(filePath);
//        if(file.exists()){
//            file.delete();
//        }
//        //删除数据库数据
//        if(merchantUploadService.deleteById(id)){
//            response.setMsg("删除成功！");
//        }else{
//            response.setCode(1);
//            response.setMsg("删除失败！");
//        }
//        return response;
//    }
}
