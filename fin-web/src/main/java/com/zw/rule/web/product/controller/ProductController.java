package com.zw.rule.web.product.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zw.UploadFile;
import com.zw.base.util.DateUtils;
import com.zw.rule.core.Response;
import com.zw.rule.mybatis.ParamFilter;
import com.zw.rule.product.Fee;
import com.zw.rule.product.ProCrmProduct;
import com.zw.rule.product.WorkingProductDetail;
import com.zw.rule.product.service.ICrmProductService;
import com.zw.rule.service.DictService;
import com.zw.rule.web.aop.annotaion.WebLogger;
import com.zw.rule.web.util.PageConvert;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("product")
public class ProductController {

    private static final Logger logger = Logger.getLogger(ProductController.class);

    @Resource
    private ICrmProductService crmProductService;

    @Resource
    private DictService dictService;

    @GetMapping("index")
    public String list() {
        return "productManage/credictList";
    }

    @GetMapping("feeIndex")
    public String feeList() {
        return "productManage/feeList";
    }
    /**
     * 函数功能说明  得到产品系列
     * wangmin  2017-06-13
     */
    @ResponseBody
    @RequestMapping("getProductSeries")
    public Response getProductSeries() throws Exception {
        Response response = new Response();
        // 处理响应给前台数据的编码
        //response.setContentType("text/html;charset=utf-8");
        // 创建返回结果对象
        JSONObject resultjson = new JSONObject();
//			Client client = new Client(new URL(SysConfig.getInstance().getProperty("v3ServiceXfireWebServicePath") + "crmProductService?wsdl"));

        // 进行查询
//			Object[] object = client.invoke("getProductSeries", new Object[]{});
        //接收调用接口的返回值
        resultjson = crmProductService.getProductSeries();

        //返回json数据到前台页面
        //response.getWriter().print(resultjson);
        response.setData(resultjson);
        return response;
    }

    /**
     * 函数功能说明  得到产品系列对应的类型
     * wangmin  2017-06-14
     */
    @ResponseBody
    @RequestMapping("getProductType")
    public Response getProductType(String id) throws Exception{
        Response response = new Response();
        // 创建返回结果对象
        JSONObject resultjson = new JSONObject();
        //接收调用接口的返回值
        resultjson = crmProductService.getProductType(id);
        List list = (List)resultjson.get("data");
        if(list.size()<1){
            response.setMsg("当前产品系列下无产品类型！");
            response.setData("false");
            //response.setCode(1);
            return response;
        }
        response.setData(resultjson);
        return response;
    }

    /**
     * 函数功能说明  得到产品类型对应的所有产品详情
     * wangmin  2017-06-14
     */
    @ResponseBody
    @RequestMapping("getProductDetail")
    public Response getProductDetail(String crmProductId) throws Exception{
        Response response = new Response();
        // 创建返回结果对象
        JSONObject resultjson = new JSONObject();
        //接收调用接口的返回值，获取产品详情信息
        resultjson = crmProductService.getProductDetail(crmProductId);
        List list = (List)resultjson.get("data");
        if(list.size()<1){
            response.setMsg("当前产品类型下无产品！");
            response.setData("false");
            return response;
        }
        JSONObject productjson = new JSONObject();
        //获取当前的产品类型信息
        productjson = crmProductService.getTypeDetail(crmProductId);
        List list1 = (List)productjson.get("data");
        if(list1.size()<1){
            response.setMsg("当前产品类型无产品！");
            response.setCode(1);
            return response;
        }
        resultjson.put("productType",list1.get(0));
        response.setData(resultjson);
        return response;
    }

    /**
     * 函数功能说明  停用产品系列或类型
     * wangmin  2017-06-15
     */
    @ResponseBody
    @RequestMapping("disableProduct")
    public Response disableProductSeries(String id,int flag,int status) throws Exception{
        Response response = new Response();
        //检查当前产品类型下已经发布的产品数量
        if(1==status){
            //接收调用接口的返回值
            int num = crmProductService.updateStatus(id,status);
            if(num>0){
                response.setMsg("操作成功！");
                return response;
            }
            response.setMsg("操作失败！");
            response.setCode(1);
            return response;
        }
        if(flag==1){
            //检查当前产品系列已经启用的产品类型数量
            int countAbleType = crmProductService.countAbleType(id);
            if(countAbleType>0){
                response.setMsg("该类型下有产品处于启用中,请先停用所有产品后再停用该类型!");
                response.setCode(1);
                return response;
            }
            //接收调用接口的返回值
            int num = crmProductService.updateStatus(id,status);
            if(num>0){
                response.setMsg("操作成功！");
                return response;
            }
            response.setMsg("操作失败！");
            response.setCode(1);
            return response;
        }else{
            int countPublisProduct = crmProductService.countPublisProduct(id);
            if(countPublisProduct>0){
                response.setMsg("该产品下有产品处于启用中,请先停用所有产品后再停用该类型!");
                response.setCode(1);
                return response;
            }
            //接收调用接口的返回值
            int num = crmProductService.updateStatus(id,status);
            if(num>0){
                response.setMsg("操作成功！");
                return response;
            }
            response.setMsg("操作失败！");
            response.setCode(1);
            return response;
        }
    }

    /**
     * 函数功能说明  停用或者启用具体产品
     * wangmin  2017-06-15
     */
    @ResponseBody
    @RequestMapping("updateProductStatus")
    public Response updateProductStatus(String id,int status) throws Exception{
        Response response = new Response();
        //检查当前产品类型下已经发布的产品数量
        int countPublisProduct = crmProductService.updateProductStatus(id,status);
        if(countPublisProduct>0){
            response.setMsg("操作成功！");
            return response;
        }
        response.setMsg("操作失败！");
        response.setCode(1);
        return response;
    }

    /**
     * 函数功能说明  增加产品类型
     * wangmin  2017-06-15
     */
    @ResponseBody
    @RequestMapping("addProductType")
    public Response addProductType(HttpServletRequest request) throws Exception{
        ProCrmProduct proCrmProduct = new ProCrmProduct();
        String fileName="";
        String doc="";
        String id = UUID.randomUUID().toString();
        //获取根目录
        String root = request.getSession().getServletContext()
                .getRealPath("/");
        //捕获前台传来的图片，并用uuid命名，防止重复
        Map<String, Object> allMap = UploadFile.getFile(request,root+File.separator + "cms", id);
        List<Map<String, Object>> list = (List<Map<String, Object>>) allMap
                .get("fileList");
        //当前台有文件时，给图片名称变量赋值
        if (!list.isEmpty()) {
            Map<String, Object> fileMap = list.get(0);
            fileName = "/cms/"+(String) fileMap.get("Name");
            doc = (String) fileMap.get("originalName");
        }
        proCrmProduct.setImg_url(fileName);
        Response response = new Response();
        //Map map=(Map) JSON.parseObject(data);;

        String pid = UUID.randomUUID().toString();
        proCrmProduct.setId(pid);
        proCrmProduct.setCreate_time(DateUtils.getDateString(new Date()));
        proCrmProduct.setEmp_id("1");
        String pro_name = allMap.get("pro_name").toString();
        String type = allMap.get("product_type").toString();
        proCrmProduct.setType(type);
        proCrmProduct.setPro_name(pro_name);
        proCrmProduct.setPro_quota(allMap.get("pro_quota")==null?"":allMap.get("pro_quota").toString());
        proCrmProduct.setPro_number(allMap.get("pro_number").toString());
        proCrmProduct.setParent_id(allMap.get("parent_id").toString());
        proCrmProduct.setPro_describe(allMap.get("pro_describe").toString());
        proCrmProduct.setPro_quota_proportion(allMap.get("quotaProportion").toString());
        proCrmProduct.setPro_quota_limit(allMap.get("pro_quota_limit")==null?"":allMap.get("pro_quota_limit").toString());
        if (allMap.containsKey("provinces_id")) {
            proCrmProduct.setProvinces_id( allMap.get("provinces_id")+"");
            proCrmProduct.setCity_id(allMap.get("city_id")+"");
            proCrmProduct.setDistric_id(allMap.get("distric_id")+"");
            proCrmProduct.setProvinces(allMap.get("provinces")+"");
            proCrmProduct.setCity(allMap.get("city")+"");
            proCrmProduct.setDistric(allMap.get("distric")+"");
        }

        proCrmProduct.setStatus("0");
        //检查当前产品类型下已经发布的产品数量
        int countPublisProduct = crmProductService.addProductSeries(proCrmProduct);
        if(countPublisProduct>0){
            response.setMsg("添加成功！");
            response.setData(pid);
            return response;
        }
        response.setMsg("添加失败！");
        response.setCode(1);
        return response;
    }

    /**
     * 函数功能说明  增加产品系列
     * wangmin  2017-06-15
     */
    @ResponseBody
    @RequestMapping("addProductSeries")
    public Response addProductSeries(String data) throws Exception{
        ProCrmProduct proCrmProduct = new ProCrmProduct();
        Response response = new Response();
        //Map map=(Map) JSON.parseObject(data);;
        JSONObject crmCustInfoMap =(JSONObject) JSON.parseObject(data);
        String pid = UUID.randomUUID().toString();
        proCrmProduct.setId(pid);
        proCrmProduct.setCreate_time(DateUtils.getDateString(new Date()));
        proCrmProduct.setEmp_id("1");
        proCrmProduct.setPro_name(crmCustInfoMap.get("pro_name").toString());
        proCrmProduct.setPro_number(crmCustInfoMap.get("pro_number").toString());
        proCrmProduct.setPro_series_type(crmCustInfoMap.get("pro_series_type").toString());
        proCrmProduct.setParent_id("0");
        proCrmProduct.setStatus("0");
        //检查当前产品类型下已经发布的产品数量
        int countPublisProduct = crmProductService.addProductSeries(proCrmProduct);
        if(countPublisProduct>0){
            response.setMsg("添加成功！");
            response.setData(pid);
            return response;
        }
        response.setMsg("添加失败！");
        response.setCode(1);
        return response;
    }

    /**
     * 获取产品系列配置信息
     * 吕彬  2017-06-15
     */
    @ResponseBody
    @RequestMapping("getPrdTypeInfo")
    public Response getPrdTypeInfo(String prdTypeId) throws Exception{
        Response response = new Response();
        //获取当前的产品类型信息
        JSONObject productjson = crmProductService.getTypeDetail(prdTypeId);
        List list1 = (List)productjson.get("data");
        if(list1.size()<1){
            response.setMsg("当前产品类型无产品！");
            response.setCode(1);
            return response;
        }
        response.setData(list1.get(0));
        return response;
    }

    /**
     * 修改产品系列配置信息
     * 吕彬  2017-06-15
     */
    @ResponseBody
    @RequestMapping("updateSeries")
    public Response updateSeries(@RequestBody String data) throws Exception{
        Response response = new Response();
        Map map=(Map) JSON.parseObject(data);;
        ProCrmProduct proCrmProduct = new ProCrmProduct();
        proCrmProduct.setId(map.get("id").toString());
        proCrmProduct.setPro_name(map.get("pro_name").toString());
        proCrmProduct.setPro_number(map.get("pro_number").toString());
        proCrmProduct.setPro_series_type(map.get("pro_series_type").toString());
/*        proCrmProduct.setProvinces(map.get("provinces")+"");
        proCrmProduct.setCity(map.get("city")+"");
        proCrmProduct.setDistric(map.get("distric")+"");
        proCrmProduct.setProvinces_id(map.get("provinces_id")+"");
        proCrmProduct.setCity_id(map.get("city_id")+"");
        proCrmProduct.setDistric_id(map.get("distric_id")+"");*/
        if(crmProductService.updateProductSeries(proCrmProduct)>0){
            response.setMsg("修改成功！");
        }else {
            response.setMsg("修改失败！");
            response.setCode(1);
        }

        return response;
    }

    /**
     * 修改产品系列配置信息
     * 吕彬  2017-06-15
     */
    @ResponseBody
    @RequestMapping("updatePrdTypeInfo")
    public Response updatePrdTypeInfo(HttpServletRequest request) throws Exception{
        ProCrmProduct proCrmProduct = new ProCrmProduct();
        Response response = new Response();
        String fileName="";
        String doc="";
        String id = UUID.randomUUID().toString();
        //获取根目录
        String root = request.getSession().getServletContext()
                .getRealPath("/");
        //捕获前台传来的图片，并用uuid命名，防止重复
        Map<String, Object> allMap = UploadFile.getFile(request,root+File.separator + "cms", id);
        List<Map<String, Object>> list = (List<Map<String, Object>>) allMap
                .get("fileList");
        if (!list.isEmpty()) {
            Map<String, Object> fileMap = list.get(0);
            fileName = "/cms/"+(String) fileMap.get("Name");
            doc = (String) fileMap.get("originalName");
        }
        if(!"".equals(doc)){
            String oldFileName = (String) allMap.get("picName");
            //String[] oldFileNames = oldFileName.split("/");
            //String pic_path ="cms"+File.separator+oldFileNames[oldFileNames.length-1>0?oldFileNames.length-1:0];
            String pic_path =File.separator+oldFileName;
            File file = new File(root+File.separator+pic_path);
            if(file.exists()){
                file.delete();
            }
            proCrmProduct.setImg_url(fileName);
        }

        proCrmProduct.setId(allMap.get("id").toString());
        String pro_name = allMap.get("pro_name").toString();
        String type = allMap.get("product_type2").toString();
        proCrmProduct.setPro_name(pro_name);
        proCrmProduct.setPro_quota(allMap.get("pro_quota").toString());
        proCrmProduct.setPro_quota_limit(allMap.get("pro_quota_limit")==null?"":allMap.get("pro_quota_limit").toString());
        proCrmProduct.setType(type);
        //proCrmProduct.setPro_quota_proportion(allMap.get("pro_quota_proportion").toString());
        proCrmProduct.setPro_describe(allMap.get("pro_describe")==null?"":allMap.get("pro_describe").toString());
        proCrmProduct.setPro_number(allMap.get("pro_number").toString());
        proCrmProduct.setProvinces(allMap.get("provinces")+"");
        proCrmProduct.setCity(allMap.get("city")+"");
        proCrmProduct.setDistric(allMap.get("distric")+"");
        proCrmProduct.setProvinces_id(allMap.get("provinces_id")+"");
        proCrmProduct.setCity_id(allMap.get("city_id")+"");
        proCrmProduct.setDistric_id(allMap.get("distric_id")+"");
        if(crmProductService.updateProductSeries(proCrmProduct)>0){
            response.setMsg("修改成功！");
        }else {
            response.setMsg("修改失败！");
            response.setCode(1);
        }

        return response;
    }

    /**
     * 函数功能说明  获取产品序列号
     * wangmin  2017-06-15
     */
    @ResponseBody
    @RequestMapping("getNumber")
    public Response getNumber() throws Exception{
        Response response = new Response();
        //检查当前产品类型下已经发布的产品数量
        String proNumber = crmProductService.getNumber();

        if(!proNumber.equals("0")){
            proNumber=proNumber.substring(proNumber.length()-4);
            int intNum = Integer.parseInt(proNumber);
            intNum++;
            DecimalFormat df=new DecimalFormat("0000");
            proNumber=df.format(intNum);
            response.setData("BYX"+proNumber);
            return response;
        }
        response.setData("BYX0001");
        response.setCode(1);
        return response;
    }

    /**
     * 函数功能说明  查看产品详情的所有信息
     * wangmin  2017-06-16
     */
    @ResponseBody
    @RequestMapping("getProductInfo")
    public Response getProductInfo(String id,String parentId) throws Exception{
        Response response = new Response();
        // 创建返回结果对象
        JSONObject resultjson = new JSONObject();
        //接收调用接口的返回值，获取产品详情信息
        resultjson = crmProductService.getProductInfo(id);
        JSONObject productjson = new JSONObject();
        //获取当前的产品类型信息
        productjson = crmProductService.getTypeDetail(parentId);
        List list1 = (List)productjson.get("data");
        if(list1.size()<1){
            response.setMsg("当前产品类型无产品！");
            response.setCode(1);
            return response;
        }
        resultjson.put("productType",list1.get(0));
        response.setData(resultjson);
        return response;
    }

    /**
     *   新增产品详情的所有信息
     * 吕彬  2017-06-16
     */
    @ResponseBody
    @RequestMapping("addOrUpdateProductDetail")
    public Response updateProductInfo( String data,String flag,String type) throws Exception{
        Response response = new Response();
        JSONObject crmCustInfoMap =(JSONObject) JSON.parseObject(data);
        WorkingProductDetail workingProductDetail =new WorkingProductDetail();
        if (crmCustInfoMap != null) {
            workingProductDetail = JSON.toJavaObject(crmCustInfoMap,WorkingProductDetail.class);
        }
        if("add".equals(flag)){
            workingProductDetail.setId(UUID.randomUUID().toString());
            workingProductDetail.setStatus("0");
            workingProductDetail.setType(type);
            if(crmProductService.addOrUpdateProductDetail(workingProductDetail,flag)>0){
                response.setMsg("添加成功！");
            }else {
                response.setMsg("添加失败！");
                response.setCode(1);
            }

        }else if("update".equals(flag)){
            if(crmProductService.addOrUpdateProductDetail(workingProductDetail,flag)>0){
                response.setMsg("修改成功！");
            }else {
                response.setMsg("修改失败！");
                response.setCode(1);
            }

        }

        return response;
    }

    /**
     * 函数功能说明  模糊查询产品
     * wangmin  2017-06-15
     */
    @ResponseBody
    @RequestMapping("selectProduct")
    public Response selectProduct(String proName,String SeriesId) throws Exception{
        Response response = new Response();
        //检查当前产品类型下已经发布的产品数量
        JSONObject idList = crmProductService.selectProduct(proName,SeriesId);
        List list1 = (List)idList.get("data");
        if(list1.size()<1){
            response.setMsg("无此产品！");
            response.setCode(1);
            return response;
        }
        response.setData(list1);
        return response;
    }

    @PostMapping("feeList")
    @ResponseBody
    @WebLogger("获取费率列表")
    public Response list(@RequestBody ParamFilter queryFilter){
        int pageNo = PageConvert.convert(queryFilter.getPage().getFirstIndex(),queryFilter.getPage().getPageSize());
        PageHelper.startPage(pageNo, queryFilter.getPage().getPageSize());
        Fee fee=new Fee();
        List list =crmProductService.getFeeList(fee);
        PageInfo pageInfo = new PageInfo(list);
        return new Response(pageInfo);
    }

    @PostMapping("getProduct")
    @ResponseBody
    @WebLogger("获取产品列表")
    public Response getProduct(){
        Response response = new Response();
        // 创建返回结果对象
        JSONObject resultjson = new JSONObject();
        //接收调用接口的返回值，获取产品详情信息
        resultjson = crmProductService.getProduct();
        List list = (List)resultjson.get("data");
        response.setData(list);
        return response;
    }

    @PostMapping("getPeriods")
    @ResponseBody
    @WebLogger("获取期数下拉")
    public Response getPeriods(){
        Response response = new Response();
        // 创建返回结果对象
        JSONObject resultjson = new JSONObject();
        //接收调用接口的返回值，获取产品详情信息
        resultjson = crmProductService.getProductDetailSelect();
        List list = (List)resultjson.get("data");
        response.setData(list);
        return response;
    }

    @PostMapping("getZbs")
    @ResponseBody
    @WebLogger("获取总包商下拉")
    public Response getZbs(){
        Response response = new Response();
        // 创建返回结果对象
        JSONObject resultjson = new JSONObject();
        //接收调用接口的返回值，获取总包商详情信息
        resultjson = crmProductService.getZbsList();
        List list = (List)resultjson.get("data");
        response.setData(list);
        return response;
    }

    @PostMapping("getPeriodsList")
    @ResponseBody
    @WebLogger("获取产品分期列表")
    public Response getPeriodsList(@RequestBody String crmProductId){
        Response response = new Response();
        // 创建返回结果对象
        JSONObject resultjson = new JSONObject();
        //接收调用接口的返回值，获取产品详情信息
        resultjson = crmProductService.getPeriodsList(crmProductId);
        List list = (List)resultjson.get("data");
        response.setData(list);
        return response;
    }

    @PostMapping("updateFee")
    @ResponseBody
    @WebLogger("修改费率")
    public Response updateFee(@RequestBody Fee fee){
        String feeId = fee.getId();
        Response response=new Response();
        List<Fee> feeList = crmProductService.isExist(fee);
        List list = null;
        String productId = feeList.get(0).getProductId();
        String periods =feeList.get(0).getProductPeriods();
        if(productId.equals(fee.getProductId())&&periods.equals(fee.getProductPeriods())){
            int num = crmProductService.updateFee(fee);
            response.setMsg("修改成功！");
            return response;
        }else {
            fee.setId(null);
            list = crmProductService.getFeeList(fee);
            if (list.size()>0){
                response.setCode(1);
                response.setMsg("该产品配置已经存在，请重新修改！");
                return response;
            }else {
                fee.setId(feeId);
                int num = crmProductService.updateFee(fee);
                response.setMsg("修改成功！");
                return response;
            }
        }
    }

    @PostMapping("addFee")
    @ResponseBody
    @WebLogger("添加费率")
    public Response addFee(@RequestBody Fee fee){
        Response response=new Response();
        response.setCode(0);
        if(crmProductService.getFeeList(fee).size()>0){
            response.setMsg("该产品配置已经存在，请重新输入！");
            response.setCode(1);
            return response;
        }
        fee.setId(UUID.randomUUID().toString());
        if(crmProductService.addFee(fee)>0){
            response.setMsg("添加成功！");
        }else {
            response.setMsg("添加失败！");
            response.setCode(1);
        }
        return response;
    }

    @PostMapping("getFee")
    @ResponseBody
    @WebLogger("获取费率")
    public Response getFee(@RequestBody Fee fee){
        List<Fee> list =crmProductService.getFeeList(fee);
        return new Response(list.get(0));
    }

    @PostMapping("del")
    @ResponseBody
    @WebLogger("删除费率")
    public Response delFee(@RequestBody Fee fee){
        Response response = new Response();
        int num =crmProductService.delFee(fee.getId());
        if (num>0){
            response.setMsg("删除成功！");
        }else {
            response.setMsg("删除失败！");
        }
        return response;
    }

    @PostMapping("apendSelected")
    @ResponseBody
    @WebLogger("动态加载产品类型到下拉框")
    public Response apendSelected(){
        Response response = new Response();
        List list=dictService.getDetailList("产品类型");
        if (list!=null&&list.size()>0){
            response.setData(list);
        }else {
            return null;
        }
        return response;
    }

    @PostMapping("apendSelect")
    @ResponseBody
    @WebLogger("动态加载还款方式到下拉框")
    public Response apendSelect(){
        Response response = new Response();
        List list=dictService.selectAppDictList("还款方式");

        if (list!=null&&list.size()>0){
            response.setData(list);
        }else {
            return null;
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
}
