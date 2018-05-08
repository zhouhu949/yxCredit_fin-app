package com.zw.rule.web.controller;


import com.junziqian.api.common.Constants;
import com.junziqian.api.common.FileUtils;
import com.junziqian.api.common.org.OrganizationType;
import com.junziqian.api.request.OrganizationAuditStatusRequest;
import com.junziqian.api.request.builder.OrganizationCreateBuilder;
import com.junziqian.api.response.OrganizationAuditStatusResponse;
import com.junziqian.api.response.OrganizationCreateResponse;
import com.junziqian.api.util.LogUtils;
import com.mysql.jdbc.StringUtils;
import com.zw.base.util.DateUtils;
import com.zw.rule.core.Response;
import com.zw.rule.pcd.po.City;
import com.zw.rule.pcd.po.Province;
import com.zw.rule.pcd.service.IPCDLinkedService;
import com.zw.rule.po.Dict;
import com.zw.rule.po.SysDepartment;
import com.zw.rule.service.DictService;
import com.zw.rule.service.SysDepartmentService;
import com.zw.rule.upload.service.UploadService;
import com.zw.rule.web.aop.annotaion.WebLogger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.zw.rule.web.samples.JunziqianClientInit;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * Created by shengkf on 2017/6/13.
 */
@Controller
@RequestMapping("sysDepartment")
public class SysDepartmentController {
    @Resource
    public SysDepartmentService sysDepartmentService;
    @Resource
    private DictService dictService;
    @Resource
    private IPCDLinkedService pcdLinkedService;
    @Resource
    private UploadService uploadService;

    @RequestMapping({"/toindex"})
    public String testInfo() {
        return "/systemMange/magDepartment";
    }

    /**
     * 获取组织架构信息
     *
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("getDepartmentZtree")
    public Response getDepartmentZtree() throws Exception {
        // 响应给前台数据的编码
        Map map = new HashMap();
        map.put("status", "1");//部门状态为启用
        String flag = "true";
        List list = sysDepartmentService.findAllDepts(map);
        if (list.size() == 0) {//判断表中是否有启用的部门公司
            flag = "false";
        }
        List<SysDepartment> departlist = new ArrayList<SysDepartment>(list);
        for (int i = 0; i < departlist.size(); i++) {//根据id查询省市的名称放到地址中
            Map pcdmap = new HashMap();
            String provinceId = departlist.get(i).getProvinceId();//省id
            String cityId = departlist.get(i).getCityId();//市id
            String provincename = "";
            String cityname = "";
            if (!"".equals(provinceId)) {
                Province province = pcdLinkedService.getProvinceById(provinceId);
                provincename = province == null ? "" : province.getProvinceName();
            }
            if (!"".equals(cityId)) {
                City city = pcdLinkedService.getCityById(cityId);
                cityname = city == null ? "" : city.getCityName();
            }
            departlist.get(i).setProAddress(provincename + cityname);
        }

        LinkedList<SysDepartment> finalList = new LinkedList<SysDepartment>();
        for (int i = 0; i < list.size(); i++) {
            Long l_parent_id = departlist.get(i).getPid();
            long l_id = departlist.get(i).getId();
            if (finalList.isEmpty()) {
                finalList.addLast(departlist.get(i));
            } else {
                for (int j = 0; j < finalList.size(); j++) {
                    long s_id = finalList.get(j).getId();
                    Long s_parent_id = finalList.get(j).getPid();
                    if (l_parent_id.equals(s_id)) {
                        finalList.add(j + 1, departlist.get(i));
                        for (int k = 0; k < finalList.size(); k++) {
                            SysDepartment sysDepartment = finalList.get(k);
                            Long k_parent_id = finalList.get(k).getPid();
                            if (l_id == k_parent_id) {
                                finalList.remove(k);
                                finalList.add(j + 2, sysDepartment);
                            }
                        }
                        break;
                    } else if (l_id == s_parent_id) {
                        if (j == 0) {
                            finalList.add(0, departlist.get(i));
                            break;
                        } else {
                            finalList.add(j - 1, departlist.get(i));
                            break;
                        }
                    } else if (finalList.size() - 1 == j) {
                        finalList.addLast(departlist.get(i));
                        break;
                    }
                }
            }
        }
        Response response = new Response();
        response.setData(finalList);
        response.setMsg(flag);
        return response;
    }

    /**
     * 添加按钮回显数据(暂时用不到)
     *
     * @param paramMap pid 父节点id
     *                 number 部门编号
     *                 id  部门id
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("findAllByMap")
    public Response findAllByMap(@RequestBody HashMap<String, Object> paramMap) throws Exception {
        List list = sysDepartmentService.findAllDepts(paramMap);
        return new Response(list);
    }

    /**
     * 添加公司、部门保存
     *
     * @param department type 类型（公司、部门）
     *                   name  部门名称
     *                   pid 父级部门id
     *                   description 备注
     * @return
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping("saveDepartment")
    public Response saveOrUpdateDepartment(@RequestBody SysDepartment department) throws Exception {
        String grade = department.getGrade();//判断是父级还是子级
        if ("0".equals(grade)) {  //添加父级节点
            department.setPid(0);
        }
        //获取返回结果
        boolean flag = sysDepartmentService.saveDepartment(department);
        Response response = new Response();
        if (flag) {
            response.setMsg("保存成功！");
        } else {
            response.setMsg("保存失败！");
        }
        return response;

    }

    /**
     * 修改公司、部门
     *
     * @param department
     * @return
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping("updateDepartment")
    public Response UpdateDepartment(@RequestBody SysDepartment department) throws Exception {
        //获取返回结果
        boolean flag = sysDepartmentService.updateDepartment(department);
        Response response = new Response();
        if (flag) {
            response.setMsg("修改成功！");
        } else {
            response.setMsg("修改失败！");
        }
        return response;

    }

    /**
     * 注册公司、部门
     *
     * @param map
     * @return
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping("register")
    public Response register(@RequestBody Map map, HttpServletRequest request) throws Exception {

        OrganizationCreateBuilder builder = new OrganizationCreateBuilder();
        builder.withEmailOrMobile(map.get("email").toString());//企业注册邮箱
        builder.withName(map.get("companyName").toString());  //企业全称
        builder.withLegalName(map.get("username").toString());//法人姓名或者是被授权人姓名
        builder.withLegalIdentityCard(map.get("card").toString());//法人身份证号或者是被授权人身份证号
        builder.withLegalMobile(map.get("tel").toString());//法人手机号或者是被授权人手机号
        List<String> imgurls = (List<String>) map.get("imgurls");
        if ("0".equals(map.get("gongsiImgType"))) {
            builder.withIdentificationType(Constants.IDENTIFICATION_TYPE_TRADITIONAL);
            builder.withOrganizationType(OrganizationType.ENTERPRISE.getCode());
            builder.withOrganizationCode(map.get("code").toString());//组织结构代码
            String photoUrl1 = imgurls.get(0).toString();
            String photoUrl2 = imgurls.get(1).toString();
            String photoUrl3 = imgurls.get(2).toString();
            String photoUrl4 = imgurls.get(3).toString();
            map.put("photoUrl1", photoUrl1);
            map.put("photoUrl2", photoUrl2);
            map.put("photoUrl3", photoUrl3);
            map.put("photoUrl4", photoUrl4);
            String filePath = request.getSession().getServletContext().getRealPath("");
            String fileName1 = saveUrlAs(photoUrl1, filePath, "GET");
            String fileName2 = saveUrlAs(photoUrl2, filePath, "GET");
            String fileName3 = saveUrlAs(photoUrl3, filePath, "GET");
            String fileName4 = saveUrlAs(photoUrl4, filePath, "GET");
            builder.withOrganizationRegImg(FileUtils.uploadFile(fileName1));
            builder.withOrganizationCodeImg(FileUtils.uploadFile(fileName2));
            builder.withTaxCertificateImg(FileUtils.uploadFile(fileName3));
            builder.withSignApplication(FileUtils.uploadFile(fileName4));
        } else {
            builder.withIdentificationType(Constants.IDENTIFICATION_TYPE_ALLINONE);
            builder.withOrganizationType(OrganizationType.ENTERPRISE.getCode());
            String photoUrl1 = imgurls.get(0).toString();
            String photoUrl2 = imgurls.get(1).toString();
            map.put("photoUrl1", photoUrl1);
            map.put("photoUrl2", photoUrl2);
            String filePath = request.getSession().getServletContext().getRealPath("");
            String fileName1 = saveUrlAs(photoUrl1, filePath, "GET");
            String fileName2 = saveUrlAs(photoUrl2, filePath, "GET");
            builder.withOrganizationRegImg(FileUtils.uploadFile(fileName1));
            builder.withSignApplication(FileUtils.uploadFile(fileName2));
        }
        builder.withOrganizationRegNo(map.get("num").toString());//营业执照号


        /**
         * 企业类型选择传统多证（0）时 则需要传 组织机构代码和组织机构代码复印件；选择多证合一（1）则不需要传组织机构代码和组织机构代码复印件
         */
        //  builder.withOrganizationCode("5801xxxxx");//组织结构代码
        //builder.withOrganizationCodeImg(FileUtils.uploadFile(img));//组织机构复印件
        // builder.withSignApplication(FileUtils.uploadFile(fileName));//君子签企业授权服务书 ，有统一模版
        /**
         * 注意如果不提交君子签企业授权服务书则需要出具承诺函给我们，否则后台不予审核。
         */
        //  builder.withSignImg(FileUtils.uploadFile(img));//企业公章图片，图片规格170*170PX，背景透明。该字段只适用与不提交君子签企业授权服务书。
        //生产环境
       /* String appKey = "5ef9ea24a733c299";
        String appSecrete = "5b7cca805ef9ea24a733c29998a7cd99";
        String services_url = "http://api.junziqian.com/services";*/
        //测试环境
        String appKey = "5e4ff62c16b172f4";
        String appSecrete = "8cd497a65e4ff62c16b172f40184e48b";
        String services_url = "http://sandbox.api.junziqian.com/services";
        OrganizationCreateResponse response1 = JunziqianClientInit.getClient(services_url, appKey, appSecrete).organizationCreate(builder.build());
        Response response = new Response();
        String id = UUID.randomUUID().toString();
        map.put("id", id);
        String create_time = DateUtils.getCurrentTime(DateUtils.STYLE_10);
        map.put("create_time", create_time);
        if (response1.isSuccess() && response1.getEmail() != null) {
            map.put("status", "0"); //审核中
            int num = sysDepartmentService.addCompanyInfo(map);
            if (num > 0) {
                response.setCode(0);
                response.setMsg("用户申请认证审核中！");
            }
        } else {
            map.put("status", "2"); //认证失败
//            int num=sysDepartmentService.addCompanyInfo(map);
            response.setCode(1);
            String message = response1.getError().getMessage();
            response.setMsg(message);
        }
        return response;
    }

    /**
     * 查询企业注册是否通过保全审核
     *
     * @param map
     * @return
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping("findStatus")
    public Response findStatus(@RequestBody Map map) throws Exception {
        String company_name = map.get("company_name").toString();
        Response response = new Response();
        //根据邮箱查询注册的状态是否成功
        //生产环境
       /* String appKey="5ef9ea24a733c299";
        String appSecrete="5b7cca805ef9ea24a733c29998a7cd99";
        String services_url="http://api.junziqian.com/services";*/
        //测试环境
        String appKey = "5e4ff62c16b172f4";
        String appSecrete = "8cd497a65e4ff62c16b172f40184e48b";
        String services_url = "http://sandbox.api.junziqian.com/services";
        Map companyMap = sysDepartmentService.findCompany(company_name);
        response.setData(companyMap);
        if (companyMap == null) {
            response.setCode(1);
            response.setMsg("您目前选择的公司未认证");
        }
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(50);
        for (int i = 0; i < 50; i++) {
            fixedThreadPool.execute(new Runnable() {
                public void run() {
                    Map comMap = new HashMap();
                    OrganizationAuditStatusRequest request = new OrganizationAuditStatusRequest();
                    request.setEmailOrMobile(companyMap.get("email").toString());
                    OrganizationAuditStatusResponse response2 = JunziqianClientInit.getClient(services_url, appKey, appSecrete).organizationStatus(request);
                    if (1 == (response2.getStatus())) { //审核通过
                        comMap.put("id", companyMap.get("id").toString());
                        String update_time = DateUtils.getCurrentTime(DateUtils.STYLE_10);
                        comMap.put("update_time", update_time);
                        comMap.put("status", "1");
                        int num = sysDepartmentService.updateCompanyInfo(comMap);
                        if (num > 0) {
                            response.setCode(1);
                            response.setMsg("企业申请认证成功！");
                        }
                    } else if (0 == (response2.getStatus())) {
                        response.setCode(0);
                        response.setMsg("企业申请认证审核中");
                    } else {
                        comMap.put("id", companyMap.get("id").toString());
                        String update_time = DateUtils.getCurrentTime(DateUtils.STYLE_10);
                        comMap.put("update_time", update_time);
                        comMap.put("status", "2");
                        int num = sysDepartmentService.updateCompanyInfo(comMap);
                        response.setMsg("企业申请认证拒绝");

                    }
                }
            });
        }
        return response;
    }


    public static String saveUrlAs(String url, String filePath, String method) {
        //System.out.println("fileName---->"+filePath);
        //创建不同的文件夹目录
        File file = new File(filePath);
        String fileName = "";
        //判断文件夹是否存在
        if (!file.exists()) {
            //如果文件夹不存在，则创建新的的文件夹
            file.mkdirs();
        }
        FileOutputStream fileOut = null;
        HttpURLConnection conn = null;
        InputStream inputStream = null;
        try {
            // 建立链接
            URL httpUrl = new URL(url);
            conn = (HttpURLConnection) httpUrl.openConnection();
            //以Post方式提交表单，默认get方式
            conn.setRequestMethod(method);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            // post方式不能使用缓存
            conn.setUseCaches(false);
            //连接指定的资源
            conn.connect();
            //获取网络输入流
            inputStream = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(inputStream);
            //判断文件的保存路径后面是否以/结尾
            if (!filePath.endsWith("/")) {

                filePath += "/";

            }
            //写入到文件（注意文件保存路径的后面一定要加上文件的名称）
            fileName = filePath + UUID.randomUUID().toString() + ".png";
            fileOut = new FileOutputStream(fileName);
            BufferedOutputStream bos = new BufferedOutputStream(fileOut);
            byte[] buf = new byte[4096];
            int length = bis.read(buf);
            //保存文件
            while (length != -1) {
                bos.write(buf, 0, length);
                length = bis.read(buf);
            }
            bos.close();
            bis.close();
            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("抛出异常！！");
        }
        //删除本地文件
        File file1=new File(fileName);
        file1.delete();
        return fileName;

    }

    public static void main(String[] args) throws Exception {
        //根据邮箱查询注册的状态是否成功
       /* String appKey = "5ef9ea24a733c299";
        String appSecrete = "5b7cca805ef9ea24a733c29998a7cd99";
        String services_url = "http://api.junziqian.com/services";*/
       String appKey = "5e4ff62c16b172f4";
        String appSecrete = "8cd497a65e4ff62c16b172f40184e48b";
        String services_url = "http://sandbox.api.junziqian.com/services";
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(50);
        for (int i = 0; i < 500; i++) {
            fixedThreadPool.execute(new Runnable() {
                public void run() {
                    OrganizationAuditStatusRequest request = new OrganizationAuditStatusRequest();
                    request.setEmailOrMobile("sichen@chansha.net");
                    OrganizationAuditStatusResponse response2 = JunziqianClientInit.getClient(services_url, appKey, appSecrete).organizationStatus(request);
                    Integer a = response2.getStatus();
                    System.out.println(a);
                }
            });
        }
    }


    @ResponseBody
    @RequestMapping("getDetailList")
    @WebLogger("根据大类名称获取字典")
    public Response getDetailList(@RequestBody Map map) {
        String name = String.valueOf(map.get("name"));
        List<Dict> dict = dictService.getDetailList(name);
        return new Response(dict);
    }

    @ResponseBody
    @PostMapping("findOne")
    public Response findOne(long orgId) {
        SysDepartment sysDepartment = sysDepartmentService.findById(orgId);
        return new Response(sysDepartment);
    }

    @ResponseBody
    @PostMapping("merUpload")
    public Response upload(HttpServletRequest request) throws Exception {
        Response response = new Response();
        String fileName = "";
        Map param = new HashMap();
        //捕获前台传来的图片，并用uuid命名，防止重复
        Map<String, Object> allMap = uploadService.uploadFileOSS(request, "/fintecher_file");
        //当前台有文件时，给图片名称变量赋值
        if (!allMap.isEmpty()) {
            fileName = (String) allMap.get("url");
            response.setMsg("上传成功！");
            param.put("activity_img_id", fileName);
            param.put("activity_img_fileName", fileName);
            response.setData(param);
        }
        if (StringUtils.isNullOrEmpty(fileName)) {
            response.setCode(1);
            response.setMsg("上传失败");
            return response;
        }
        return response;
    }

    /**
     * 获取所有公司 做下拉选使用
     *
     * @param map
     * @return
     */
    @ResponseBody
    @RequestMapping("getAllGongsiName")
    @WebLogger("获取所有公司")
    public Response getAllGongsiName(@RequestBody Map map) {
        List<Map> list = sysDepartmentService.getAllGongsi(map);
        return new Response(list);
    }
//       public static void main(String [] args) throws Exception{
//           String img = "C:\\Users\\Administrator\\Desktop\\临时文件\\3dc19202d00165fe9eaa8c977f1c348c.jpg";
//           OrganizationCreateBuilder builder = new OrganizationCreateBuilder();
//           builder.withEmailOrMobile("xhteor29996@chacuo.net");//企业注册邮箱
//           builder.withName("文林果测试企业06");  //企业全称
//           builder.withLegalName("aa");//法人姓名或者是被授权人姓名
//           builder.withLegalIdentityCard("512501197203035172");//法人身份证号或者是被授权人身份证号
//           builder.withLegalMobile("18623559271");//法人手机号或者是被授权人手机号
//
//           //builder.withIdentificationType(Constants.IDENTIFICATION_TYPE_TRADITIONAL);//传统多证（IDENTIFICATION_TYPE_TRADITIONAL=0）；
//           builder.withIdentificationType(Constants.IDENTIFICATION_TYPE_ALLINONE);//多证合一（IDENTIFICATION_TYPE_ALLINONE=1）；
//           builder.withOrganizationType(OrganizationType.ENTERPRISE.getCode());//企业类型(默认选企业)
//           builder.withOrganizationRegNo("91511500000007497T");//营业执照号
//           builder.withOrganizationRegImg(FileUtils.uploadFile(img));//营业执照复印件
//           /**
//            * 企业类型选择传统多证（0）时 则需要传 组织机构代码和组织机构代码复印件；选择多证合一（1）则不需要传组织机构代码和组织机构代码复印件
//            */
//           //  builder.withOrganizationCode("5801xxxxx");//组织结构代码
//           //builder.withOrganizationCodeImg(FileUtils.uploadFile(img));//组织机构复印件
//           builder.withSignApplication(FileUtils.uploadFile(img));//君子签企业授权服务书 ，有统一模版
//           /**
//            * 注意如果不提交君子签企业授权服务书则需要出具承诺函给我们，否则后台不予审核。
//            */
//           //  builder.withSignImg(FileUtils.uploadFile(img));//企业公章图片，图片规格170*170PX，背景透明。该字段只适用与不提交君子签企业授权服务书。
//           String appKey="5e4ff62c16b172f4";
//           String appSecrete="8cd497a65e4ff62c16b172f40184e48b";
//           String services_url="http://sandbox.api.junziqian.com/services";
//           OrganizationCreateResponse response2= JunziqianClientInit.getClient(services_url,appKey,appSecrete).organizationCreate(builder.build());
//
//     //   System.out.println("Run ok!/n<BR>Get URL file " + file);
//    }
}
