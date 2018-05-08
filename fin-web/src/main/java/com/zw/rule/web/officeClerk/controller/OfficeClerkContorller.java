package com.zw.rule.web.officeClerk.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mysql.jdbc.StringUtils;
import com.zw.base.util.DateUtils;
import com.zw.base.util.MD5Utils;
import com.zw.rule.core.Response;
import com.zw.rule.customer.po.Order;
import com.zw.rule.merchant.Merchant;
import com.zw.rule.mybatis.ParamFilter;
import com.zw.rule.officeClerk.service.OfficeClerkManageService;
import com.zw.rule.officeClerkEntity.Branch;
import com.zw.rule.officeClerkEntity.OfficeClerkManager;
import com.zw.rule.officeClerkEntity.SalesmanImgRel;
import com.zw.rule.upload.service.UploadService;
import com.zw.rule.web.util.PageConvert;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 办单员管理的Controller
 * Created by zoukaixuan on 2017/11/27.
 */
@Controller
@RequestMapping("officeClerk")
public class OfficeClerkContorller {
    @Resource
    private OfficeClerkManageService ocmService;
    @Resource
    private UploadService uploadService;

    @RequestMapping("officeClerkManage")
    public String officeClerkManage() {
        return "officeClerk/officeClerkManage";
    }

    /**
     * 访问此方法返回所有办单员信息
     *
     * @param queryFilter
     * @return
     */
    @RequestMapping("getAllManagers")
    @ResponseBody
    public Response getAllManagers(@RequestBody ParamFilter queryFilter) {
        int pageNo = PageConvert.convert(queryFilter.getPage().getFirstIndex(), queryFilter.getPage().getPageSize());
        PageHelper.startPage(pageNo, queryFilter.getPage().getPageSize());
        List<OfficeClerkManager> list = ocmService.getAllOCManager(queryFilter.getParam());
        PageInfo pageInfo = new PageInfo(list);
        return new Response(pageInfo);
    }

    /**
     * 添加办单员
     *
     * @param map
     * @return
     */
    @RequestMapping("addOfficeClerkManager")
    @ResponseBody
    public Response addOfficeClerkManager(@RequestBody Map map) {
        OfficeClerkManager manager=new OfficeClerkManager();
        Response response = new Response();

        List<OfficeClerkManager> salesmans1=ocmService.getSalesmansByTel(map.get("tel").toString());//根据号码查询来的办单员集合
        List<OfficeClerkManager> salesmans2=ocmService.getSalesmansByTelWhereEmployeeNum(map.get("tel").toString());//根据办单员手机号交叉查询
        List<OfficeClerkManager> salesmans3=ocmService.getSalesmansByEmployeeNum(map.get("employeeNum").toString());//根据办单员账号查出来的办单员集合
        List<OfficeClerkManager> salesmans4=ocmService.getSalesmansByEmployeeNumWhereTel(map.get("employeeNum").toString());//根据办单员账号交叉查询
        int countIdcard =ocmService.getIdcardCount(map.get("idcard").toString());//身份证号在表中的条数

        if(salesmans1.size()>0){
            response.setMsg("该手机号已存在");
            response.setCode(1);
        }else if(salesmans2.size()>0){
            response.setMsg("该手机号已存在");
            response.setCode(1);
        }else if(salesmans3.size()>0){
            response.setMsg("该账号已存在");
            response.setCode(1);
        }else if(salesmans4.size()>0){
            response.setMsg("该账号已存在");
            response.setCode(1);
        }else if(countIdcard>0){
            response.setMsg("该身份证号码已存在");
            response.setCode(1);
        }else{
            manager.setId(UUID.randomUUID().toString());
            try {
                manager.setPasswd(MD5Utils.GetMD5Code("888888"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            //办单员工号
            manager.setEmployeeNum(map.get("employeeNum").toString());
            manager.setIdcard(map.get("idcard").toString());
            manager.setIdcardAddress(map.get("idcardAddress").toString());
            manager.setRealname(map.get("realname").toString());
            manager.setDateBath(map.get("dateBath").toString());
            manager.setSex(map.get("sex").toString());
            manager.setTel(map.get("tel").toString());
            manager.setPost(map.get("post").toString());
            manager.setNowAddress(map.get("nowAddress").toString());
            manager.setErrorNum("0");
            manager.setState("0");
            //办单员部门id
            manager.setBranch(map.get("branch").toString());
            //返回公司实体
            Branch branch=ocmService.getGongSiName(ocmService.getBranch(map.get("branch").toString()));
            //公司名
            manager.setCompany(branch.getDeptName());

            if (("1").equals(map.get("sex"))) {
                manager.setSexName("男");
            }
            if (("0").equals(map.get("sex"))) {
                manager.setSexName("女");
            }
            //设置初始激活状态时未激活状态
            manager.setActivationState("0");
            manager.setCreatTime(DateUtils.getCurrentTime());
            if (ocmService.addOfficeClerkManager(manager) == 1) {
                String cardImageZhengMian=map.get("cardImageZhengMian").toString();//身份证正面照片
                String cardImageFanMian=map.get("cardImageFanMian").toString();//身份证反面照片
                String cardImageShouChi=map.get("cardImageShouChi").toString(); //手持身份证照片
                String cardImageQiTa=map.get("cardImageQiTa").toString(); //其他照片
                //身份证正面照片
                SalesmanImgRel salesmanImgRel=new SalesmanImgRel();
                salesmanImgRel.setId(UUID.randomUUID().toString());
                salesmanImgRel.setImgType("0");
                salesmanImgRel.setImgUrl(cardImageZhengMian);
                salesmanImgRel.setSalesmanId(manager.getId());
                salesmanImgRel.setState("0");
                ocmService.addImgToSalesman(salesmanImgRel);
                //身份证反面照片
                SalesmanImgRel salesmanImgRel1=new SalesmanImgRel();
                salesmanImgRel1.setId(UUID.randomUUID().toString());
                salesmanImgRel1.setImgType("1");
                salesmanImgRel1.setImgUrl(cardImageFanMian);
                salesmanImgRel1.setSalesmanId(manager.getId());
                salesmanImgRel1.setState("0");
                ocmService.addImgToSalesman(salesmanImgRel1);
                //手持身份证照片
                SalesmanImgRel salesmanImgRel2=new SalesmanImgRel();
                salesmanImgRel2.setId(UUID.randomUUID().toString());
                salesmanImgRel2.setImgType("2");
                salesmanImgRel2.setImgUrl(cardImageShouChi);
                salesmanImgRel2.setSalesmanId(manager.getId());
                salesmanImgRel2.setState("0");
                ocmService.addImgToSalesman(salesmanImgRel2);
                //其他照片
                SalesmanImgRel salesmanImgRel3=new SalesmanImgRel();
                salesmanImgRel3.setId(UUID.randomUUID().toString());
                salesmanImgRel3.setImgType("3");
                salesmanImgRel3.setImgUrl(cardImageQiTa);
                salesmanImgRel3.setSalesmanId(manager.getId());
                salesmanImgRel3.setState("0");
                ocmService.addImgToSalesman(salesmanImgRel3);
                response.setMsg("添加成功");
            } else {
                response.setMsg("添加失败");
            }
        }
        return response;
    }

    /**
     * 显示单个办单员信息访问的controller
     * 返回单个办单员对象
     *
     * @param param
     * @return
     */
    @RequestMapping("getOneManagerById")
    @ResponseBody
    public Response getOneManagerById(@RequestBody Map param) {
        //System.out.print(param.get("id"));
        Response response = new Response();
        response.setData(ocmService.getOneClerkManagerById((param.get("id")).toString()));
        return response;
    }

    /**
     * 查询办单员的照片 返回的是关联实体组成的集合
     * @param param
     * @return
     */
    @RequestMapping("getSalesmanImgsController")
    @ResponseBody
    public Response getSalesmanImgs(@RequestBody Map param) {
        Response response = new Response();
        List<SalesmanImgRel> list=ocmService.getSalesmanImages(param.get("salesmanId").toString());
        response.setData(list);
        return response;
    }

    /**
     * 编辑单个办单员信息
     * updateOfficeClerkManagerById
     */
    @RequestMapping("updateOfficeClerkManagerById")
    @ResponseBody
    public Response updateOfficeClerkManagerById(@RequestBody OfficeClerkManager officeClerkManager) {
        Response response = new Response();
        List<OfficeClerkManager> salesmans1 = ocmService.getSalesmansByTel(officeClerkManager.getTel());//根据号码查询来的办单员集合
        List<OfficeClerkManager> salesmans2 = ocmService.getSalesmansByTelWhereEmployeeNum(officeClerkManager.getTel());//根据办单员手机号交叉查询
        List<OfficeClerkManager> salesmans3 = ocmService.getSalesmanByIdcard(officeClerkManager.getIdcard());//根据身份证号码查询
        String oldId = officeClerkManager.getId();
        if (salesmans1.size() > 0 && !salesmans1.get(0).getId().equals(officeClerkManager.getId())) {
            response.setCode(1);
            response.setMsg("该手机号已存在");
            return response;
        } else if (salesmans2.size() > 0 && !salesmans2.get(0).getId().equals(officeClerkManager.getId())) {
            response.setCode(1);
            response.setMsg("该手机号已存在");
        } else if (salesmans3.size() > 0 && !salesmans3.get(0).getId().equals(officeClerkManager.getId())) {
            response.setCode(1);
            response.setMsg("该身份证号已存在");
        } else {
            response = changeSalesman(officeClerkManager);
        }
        return response;
    }
    /**
     * 更改办单员的私有方法
     */
    private Response changeSalesman(OfficeClerkManager officeClerkManager) {
        Response response = new Response();
        //设置更改时间
        officeClerkManager.setAlterTime(DateUtils.getCurrentTime());
        if (("1").equals(officeClerkManager.getSex())) {
            officeClerkManager.setSexName("男");
        }
        if (("女").equals(officeClerkManager.getSex())) {
            officeClerkManager.setSexName("女");
        }
        //更改归属公司
        //返回公司实体
        Branch branch = ocmService.getGongSiName(ocmService.getBranch(officeClerkManager.getBranch()));
        //公司名
        String company = branch.getDeptName();
        officeClerkManager.setCompany(company);

        int s = ocmService.editOfficeClerkManager(officeClerkManager);
        if (s == 1) {
            //办单员id
            String salesmanId = officeClerkManager.getId();
            //正面
            String zhengmian = officeClerkManager.getZhengmian();
            //反面
            String fanmian = officeClerkManager.getFanmian();
            //手持
            String shouchi = officeClerkManager.getShouchi();
            //其他
            String qita = officeClerkManager.getQita();
            Map map0 = new HashMap<String, String>();
            map0.put("imgUrl", zhengmian);
            map0.put("salesmanId", salesmanId);
            map0.put("type", "0");
            ocmService.changeSalesmanImg(map0);

            Map map1 = new HashMap<String, String>();
            map1.put("imgUrl", fanmian);
            map1.put("salesmanId", salesmanId);
            map1.put("type", "1");
            ocmService.changeSalesmanImg(map1);

            Map map2 = new HashMap<String, String>();
            map2.put("imgUrl", shouchi);
            map2.put("salesmanId", salesmanId);
            map2.put("type", "2");
            ocmService.changeSalesmanImg(map2);

            Map map3 = new HashMap<String, String>();
            map3.put("imgUrl", qita);
            map3.put("salesmanId", salesmanId);
            map3.put("type", "3");
            ocmService.changeSalesmanImg(map3);
            response.setMsg("更改成功");
        } else {
            response.setMsg("更改失败");
        }
        return response;
    }

    /**
     * 冻结解冻访问controller
     * dongjieById
     */
    @RequestMapping("changeStateById")
    @ResponseBody
    public Response dongjieById(@RequestBody Map map) {
        System.out.println(map);
        Response response = new Response();
        int i = ocmService.changeOfficeClerkState(map);
        if (i > 0) {
            response.setMsg("修改成功");
        } else {
            response.setMsg("修改失败");
        }
        return response;
    }

    /**
     * 重置密码
     *
     * @param param
     * @return
     */
    @RequestMapping("resertPasswdById")
    @ResponseBody
    public Response resertPasswdById(@RequestBody Map param) {
        System.out.println(param);
        Response response = new Response();
        String id = String.valueOf(param.get("id"));
        String passwd = null;
        try {
            passwd = MD5Utils.GetMD5Code("888888");
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map map = new HashMap();
        map.put("id", id);
        map.put("passwd", passwd);
        int i = ocmService.resertPasswd(map);
        if (i > 0) {
            response.setMsg("修改成功");
        } else {
            response.setMsg("修改失败");
        }
        return response;
    }

    /**
     * 访问此方法返回所有办单员关联的商户(分管的门店)
     *
     * @param queryFilter
     * @return
     */
    @RequestMapping("getSalesmanMerchants")
    @ResponseBody
    public Response getSalesmanMerchants(@RequestBody ParamFilter queryFilter) {
        int pageNo = PageConvert.convert(queryFilter.getPage().getFirstIndex(), queryFilter.getPage().getPageSize());
        PageHelper.startPage(pageNo, queryFilter.getPage().getPageSize());
        List<Merchant> list = ocmService.getAllSalesmanMerchant(queryFilter.getParam().get("salesmanId") + "");
        PageInfo pageInfo = new PageInfo(list);
        return new Response(pageInfo);

    }

    /**
     * 获取所有可以被办单员来管理关联的商户
     *
     * @param queryFilter
     * @return
     */
    @RequestMapping("getAllCanBeSalesmanManagerMerchants")
    @ResponseBody
    public Response getAllCanBeSalesmanManagerMerchants(@RequestBody ParamFilter queryFilter) {
        int pageNo = PageConvert.convert(queryFilter.getPage().getFirstIndex(), queryFilter.getPage().getPageSize());
        PageHelper.startPage(pageNo, queryFilter.getPage().getPageSize());
        List<Merchant> list = ocmService.getAllCanBeManagerMerchants(queryFilter.getParam().get("salesmanId") + "");
        PageInfo pageInfo = new PageInfo(list);
        return new Response(pageInfo);
    }

    /**
     * 办单员关联商户(办单员担保商户)
     *
     * @param map
     * @return
     */
    @RequestMapping("salesmanRelationMerchant")
    @ResponseBody
    public Response salesmanRelationMerchant(@RequestBody Map map) {
        System.out.println(map);
        Response response = new Response();
        int i = ocmService.addMerchantToSalesman(map);
        if (i > 0) {
            response.setMsg("绑定成功");
        } else {
            response.setMsg("绑定失败");
        }
        return response;
    }

    /**
     * 解除商户跟办单员的绑定(其实就是将这条数据的关联状态改成1)
     *
     * @param map
     * @return response
     */
    @RequestMapping("removebindingSalesmanMerchant")
    @ResponseBody
    public Response removebindingSalesmanMerchant(@RequestBody Map map) {
        System.out.println(map);
        Response response = new Response();
        int i = ocmService.removeMerchantFromSalesman(map);
        if (i > 0) {
            response.setMsg("解绑成功");
        } else {
            response.setMsg("解绑失败");
        }
        return response;
    }

    /**
     * 办单员再次绑定该商户(绑定之前曾经绑定过但是又解除绑定的商户)
     *
     * @param map
     * @return
     */
    @RequestMapping("salesmanRelationMerchantAgain")
    @ResponseBody
    public Response salesmanRelationMerchantAgain(@RequestBody Map map) {
        System.out.println(map);
        Response response = new Response();
        int i = ocmService.addSameMerchantToSalesmanAgain(map);
        if (i > 0) {
            response.setMsg("绑定成功");
        } else {
            response.setMsg("绑定失败");
        }
        return response;
    }

    /**
     * 自动生成办单员工号的controller 无参数传入
     * 生成工号的规则：先查询出办单员表里面的数据条数总数+1
     * 如：MF000001
     *
     * @param
     * @return
     */
    @RequestMapping("createEmployeeNumController")
    @ResponseBody
    public Response createEmployeeNumController() {
        Response response = new Response();
        String count = String.valueOf(ocmService.getNumFromSalesman()+ 1);
        int len = 6 - count.length();
        for (int i = 0; i < len; i++) {
            count = "0"+count;
        }
        response.setData("MF"+count);
        return response;
    }

    /**
     * 获取办单员手中的订单
     *
     * @param queryFilter
     * @return
     */
    @RequestMapping("getOrdersFromSalesman")
    @ResponseBody
    public Response getOrdersFromSalesman(@RequestBody ParamFilter queryFilter) {
        int pageNo = PageConvert.convert(queryFilter.getPage().getFirstIndex(), queryFilter.getPage().getPageSize());
        PageHelper.startPage(pageNo, queryFilter.getPage().getPageSize());
        List<Order> list = ocmService.getOrdersFromSalesman(queryFilter.getParam());
        PageInfo pageInfo = new PageInfo(list);
        return new Response(pageInfo);
    }

    /**
     * 办单员图片上传图片上传
     * @param request
     * @return
     * @throws Exception
     */
    @ResponseBody
    @PostMapping("merUpload")
    public Response upload(HttpServletRequest request) throws Exception{
        Response response = new Response();
        String fileName="";
        Map param = new HashMap();
        //捕获前台传来的图片，并用uuid命名，防止重复
        Map<String, Object> allMap = uploadService.uploadFileOSS(request,"");
        //当前台有文件时，给图片名称变量赋值
        if (!allMap.isEmpty()) {
            fileName = (String) allMap.get("url");
            response.setMsg("上传成功！");
            //param.put("activity_img_id",fileName);
            param.put("img_url",fileName);
            response.setData(param);
        }
        if(StringUtils.isNullOrEmpty(fileName)){
            response.setCode(1);
            response.setMsg("上传失败");
            return response;
        }
        return response;
    }

    /**
     * 测试办单员访问的地址 返回一个页面
     * @return
     */
    @RequestMapping("testManageAndOrders")
    public String testManageAndOrders() {
        return "officeClerk/officeClerkManageTestOrder";
    }



}