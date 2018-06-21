package com.zw.rule.web.contractorManage.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zw.base.util.ByxFileUploadUtils;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartFile;

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

    private static final Logger  LOGGER = LoggerFactory.getLogger(ContractorController.class);

    @Value("${byx.img.host}")
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
        //此处需要根据用户id获取总包商列表
        User user=(User) UserContextUtil.getAttribute("currentUser");
        String roleName = (String) UserContextUtil.getAttribute("roleName");
        List<Long> listId = null;
        if("总包商".equals(roleName)) {
            listId.add(user.getUserId());
        } else if(!"超级管理员".equals(roleName)){
            listId = contractorService.findUserPermissByUserId(user.getUserId());
        }
        Map<String, Object> Param = queryFilter.getParam();
        Param.put("idList", listId);
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

    /**
     * 根据当前登录用户获取总包商下拉框
     * @param id
     * @return
     * @throws Exception
     */
    @ResponseBody
    @PostMapping("findContractorByRoleId")
    public Response findUserByMenuUrl(@RequestBody String id) throws Exception{
        String roleNames = (String) UserContextUtil.getAttribute("roleNames");
        List<Contractor> contractorList = contractorService.selectContractorList();
        String roleName = (String) UserContextUtil.getAttribute("roleName");
        User  user = (User) UserContextUtil.getAttribute("currentUser");
        List<Contractor> newContractorList = new ArrayList<Contractor>();
        if("总包商".equals(roleName)) {
            for(Contractor oldContractor : contractorList) {
                String userId = oldContractor.getUserId();
                if(StringUtils.isNotBlank(userId)) {
                    if(user.getUserId() == Long.parseLong(userId)) {
                        newContractorList.add(oldContractor);
                    }
                }
            }
        } else if(!"超级管理员".equals(roleName)){
            List<Long>  listId = contractorService.findUserPermissByUserId(user.getUserId());
            for(Contractor oldContractor : contractorList) {
                String userId = oldContractor.getUserId();
                if(StringUtils.isNotBlank(userId)) {
                    for(Long userIds : listId) {
                        if(userIds.longValue() == Long.parseLong(userId)) {
                            newContractorList.add(oldContractor);
                        }
                    }
                }
            }
        }
        return new Response(newContractorList);
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
        User user=(User) UserContextUtil.getAttribute("currentUser");
        List<Long> listId = null;
        if("总包商".equals(roleName)) {
            listId.add(user.getUserId());
        } else if(!"超级管理员".equals(roleName)){
            listId = contractorService.findUserPermissByUserId(user.getUserId());
        }
        Param.put("idList", listId);
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
        User user=(User) UserContextUtil.getAttribute("currentUser");
        List<Long> listId = null;
        if("总包商".equals(roleName)) {
            listId.add(user.getUserId());
        } else if(!"超级管理员".equals(roleName)){
            listId = contractorService.findUserPermissByUserId(user.getUserId());
        }
        Param.put("idList", listId);
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
        if(StringUtil.isBlank(map.get("userStr").toString())) {
            contractor.setUserId(map.get("userStr").toString());
        }
        int num = contractorService.bindContractorUser(contractor);
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
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile mFile = multipartRequest.getFile("file");
        if(null != mFile) {
            String imgURL = ByxFileUploadUtils.uploadFile(imgUrl, mFile.getInputStream(), mFile.getOriginalFilename());
            if(StringUtils.isBlank(imgURL)) {
                return Response.error("上传失败");
            } else {
                return Response.ok("上传成功" , imgURL);
            }
        }
        return null;
    }

    /**
     * 白名单数据导入 create by 陈淸玉
     * @param file 导入excel
     * @return
     */
    @RequestMapping("/importWhiteList")
    @ResponseBody
    public Response importWhiteList(@RequestParam("filename") MultipartFile file){
        WhiteListImportBusiness whiteListImportBusiness = new WhiteListImportBusiness(file,this.contractorService);
        try {
            List<String> errors = whiteListImportBusiness.importData();
            if(CollectionUtils.isEmpty(errors)){
                return Response.ok(errors);
            }else{
                return Response.error(errors,"导入失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.error();
        }
    }
}
