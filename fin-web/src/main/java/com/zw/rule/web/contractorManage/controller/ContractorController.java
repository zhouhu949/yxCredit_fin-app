package com.zw.rule.web.contractorManage.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zw.rule.contractor.po.Contractor;
import com.zw.rule.contractor.po.UserVo;
import com.zw.rule.contractor.po.WhiteList;
import com.zw.rule.contractor.service.ContractorService;
import com.zw.rule.core.Response;
import com.zw.rule.mybatis.ParamFilter;
import com.zw.rule.po.User;
import com.zw.rule.util.StringUtil;
import com.zw.rule.web.aop.annotaion.WebLogger;
import com.zw.rule.web.util.PageConvert;
import com.zw.rule.web.util.UserContextUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

@Controller
@RequestMapping("contractorManage")
public class ContractorController {

    @Value("${byx.img.path}")
    private String imgUrl;

    @Autowired
    private ContractorService contractorService;

    @GetMapping("contractorList")
    public String contractorList(){
        return "contractorManage/contractorList";
    }

   @GetMapping("whiteList")
    public String whiteList(){
        return "contractorManage/whiteList";
    }

    @GetMapping("contractorOrder")
    public String contractorOrderList(){
        return "contractorManage/contractorOrderList";
    }

    @PostMapping("contractorListPage")
    @ResponseBody
    public Response contractorList(@RequestBody ParamFilter queryFilter){
        int pageNo = PageConvert.convert(queryFilter.getPage().getFirstIndex(),queryFilter.getPage().getPageSize());
        PageHelper.startPage(pageNo, queryFilter.getPage().getPageSize());
        User user=(User) UserContextUtil.getAttribute("currentUser");
        Map<String, Object> Param = queryFilter.getParam();
        Param.put("userId", user.getUserId());
        queryFilter.setParam(Param);
        List list = contractorService.findContractorList(queryFilter);
        PageInfo pageInfo = new PageInfo(list);
        return new Response(pageInfo);
    }

    @ResponseBody
    @PostMapping("contractorDetail")
    public Response contractorDetail(@RequestBody String id){
        Contractor contractor = contractorService.getByContractorId(id);
        return new Response(contractor);
    }

    @ResponseBody
    @PostMapping("findUserByMenuUrl")
    public Response findUserByContractorId(@RequestParam String contractorId){
        List<UserVo> userVoList = contractorService.findUserByMenuUrl(contractorId);
        return new Response(userVoList);
    }

    @ResponseBody
    @PostMapping("findContractorByRoleId")
    public Response findUserByMenuUrl(@RequestBody String id) throws Exception{
        String roleNames = (String) UserContextUtil.getAttribute("roleNames");
        List<Contractor> contractorList = contractorService.selectContractorList();
        if(roleNames.contains("总包商")) {
            List<Contractor> newContractorList = new ArrayList<Contractor>();
            User  user = (User) UserContextUtil.getAttribute("currentUser");
            for(Contractor oldContractor : contractorList) {
               String userStr = oldContractor.getUserId();
               for(String userId : userStr.split(",")){
                   if(StringUtils.isNotBlank(userId)) {
                       if(user.getUserId() == Long.parseLong(userId)) {
                           newContractorList.add(oldContractor);
                       }
                   }
               }
            }
            return new Response(newContractorList);
        }
        return new Response(contractorList);
    }

    @ResponseBody
    @PostMapping("whiteListDetail")
    @WebLogger("查询白名单详细")
    public Response whiteListDetail(@RequestBody String id) throws Exception{
        WhiteList whiteList = contractorService.getByWhiteListId(id);
        return new Response(whiteList);
    }

    @PostMapping("whiteListPage")
    @ResponseBody
    public Response whiteList(@RequestBody ParamFilter queryFilter) throws Exception{
        int pageNo = PageConvert.convert(queryFilter.getPage().getFirstIndex(),queryFilter.getPage().getPageSize());
        PageHelper.startPage(pageNo, queryFilter.getPage().getPageSize());
        String roleName = (String) UserContextUtil.getAttribute("roleName");
        Map<String, Object> Param = queryFilter.getParam();
        String userStr = "";
        if(!"超级管理员".equals(roleName)) {
            User user=(User) UserContextUtil.getAttribute("currentUser");
            userStr = ","+user.getUserId()+",";
        }
        Param.put("userId", userStr);
        queryFilter.setParam(Param);
        List list = contractorService.findWhiteList(queryFilter);
        PageInfo pageInfo = new PageInfo(list);
        return new Response(pageInfo);
    }

    @PostMapping("contractorOrderListPage")
    @ResponseBody
    public Response contractorOrderList(@RequestBody ParamFilter queryFilter) throws Exception{
        int pageNo = PageConvert.convert(queryFilter.getPage().getFirstIndex(),queryFilter.getPage().getPageSize());
        PageHelper.startPage(pageNo, queryFilter.getPage().getPageSize());
        String roleName = (String) UserContextUtil.getAttribute("roleName");
        Map<String, Object> Param = queryFilter.getParam();
        String userStr = "";
        if(!"超级管理员".equals(roleName)) {
            User user=(User) UserContextUtil.getAttribute("currentUser");
            userStr = ","+user.getUserId()+",";
        }
        Param.put("userId", userStr);
        queryFilter.setParam(Param);
        List list = contractorService.findContractorOrderList(queryFilter);
        PageInfo pageInfo = new PageInfo(list);
        return new Response(pageInfo);
    }

    @ResponseBody
    @PostMapping("updateContractor")
    @WebLogger("编辑总包商")
    public Response updateContractor(@RequestBody Contractor contractor) throws Exception {
        int num = contractorService.updateContractor(contractor);
        Response response = new Response();
        if (num > 0){
            response.setMsg("修改成功");
            return response;
        }
        response.setMsg("修改失败");
        response.setCode(1);
        return response;
    }

    @ResponseBody
    @PostMapping("addContractor")
    @WebLogger("添加总包商")
    public Response addContractor(@RequestBody Contractor contractor) throws Exception{
        checkNotNull(contractor, "总包商信息不能为空");
        int num = contractorService.addContractor(contractor);
        Response response = new Response();
        if (num > 0){
            response.setMsg("添加成功");
            return response;
        }
        response.setMsg("添加失败");
        response.setCode(1);
        return response;
    }

    @ResponseBody
    @PostMapping("bindingUser")
    @WebLogger("绑定用户")
    public Response bindingUser(@RequestBody Map map) throws Exception {
        Contractor contractor = new Contractor();
        contractor.setId(map.get("id").toString());
        contractor.setUserId(map.get("userStr").toString());
        if(!StringUtil.isBlank(map.get("userStr").toString())) {
            contractor.setUserId(","+map.get("userStr").toString()+",");
        }
        int num = contractorService.updateContractor(contractor);
        Response response = new Response();
        if (num > 0){
            response.setMsg("操作成功");
            return response;
        }
        response.setMsg("操作失败");
        response.setCode(1);
        return response;
    }

    @ResponseBody
    @PostMapping("addWhiteList")
    @WebLogger("添加白名单")
    public Response addContractor(@RequestBody WhiteList whiteList) throws Exception{
        checkNotNull(whiteList, "白名单不能为空");
        Response response = new Response();
        Map map = new HashMap(1);
        map.put("card",whiteList.getCard());
        List<String> idList = contractorService.vaildateOnly(map);
        if(null != idList && idList.size() > 0){
            response.setMsg("身份证号已存在");
            response.setCode(1);
            return response;
        }
       int num = contractorService.addWhiteList(whiteList);
        if (num > 0){
            response.setMsg("添加成功");
            return response;
        }
        response.setMsg("添加失败");
        response.setCode(1);
        return response;
    }


    @ResponseBody
    @PostMapping("updateWhiteList")
    @WebLogger("编辑白名单")
    public Response updateContractor(@RequestBody WhiteList whiteList) throws Exception{
        Response response = new Response();
        Map map = new HashMap(2);
        map.put("card",whiteList.getCard());
        List<String> idList = contractorService.vaildateOnly(map);
        if(null != idList && idList.size() > 0 && !idList.get(0).equals(whiteList.getId())){
            response.setMsg("身份证号已存在");
            response.setCode(1);
            return response;
        }
        int num = contractorService.updateWhiteList(whiteList);
        if (num > 0){
            response.setMsg("修改成功");
            return response;
        }
        response.setMsg("修改失败");
        response.setCode(1);
        return response;
    }

    /**
     * 上传附件
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("uploadFile")
    @ResponseBody
    public Response uploadFile(HttpServletRequest request) throws Exception{
        Response response = new Response();
        List list = contractorService.uploadContractorImage(request);
        if(!list.isEmpty()){
            response.setMsg("上传成功！");
            response.setData(list.get(0));
        }else{
            response.setCode(1);
            response.setMsg("上传失败！");
        }
        return response;
    }

    @RequestMapping("/byx/imgUrl")
    public void getUploadUrl(@RequestParam String licenceAttachment, HttpServletResponse response) throws IOException {
        try{
            String  imgPath = imgUrl + licenceAttachment;
            FileInputStream hFile = new FileInputStream(imgPath);
            int i=hFile.available(); //得到文件大小
            byte data[]=new byte[i];
            hFile.read(data); //读数据
            hFile.close();
            response.setContentType("image/*"); //设置返回的文件类型
            OutputStream toClient=response.getOutputStream(); //得到向客户端输出二进制数据的对象
            toClient.write(data); //输出数据
            toClient.close();
        }
        catch(IOException e) //错误处理
        {
            e.printStackTrace();
            PrintWriter toClient = response.getWriter(); //得到向客户端输出文本的对象
            response.setContentType("text/html;charset=gb2312");
            toClient.write("无法打开!");
            toClient.close();
        }
    }
}
