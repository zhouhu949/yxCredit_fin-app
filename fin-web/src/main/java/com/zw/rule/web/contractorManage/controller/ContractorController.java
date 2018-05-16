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
import com.zw.rule.web.aop.annotaion.WebLogger;
import com.zw.rule.web.util.PageConvert;
import com.zw.rule.web.util.UserContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

@Controller
@RequestMapping("contractorManage")
public class ContractorController {

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
        System.out.println(queryFilter);
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
    public Response findUserByMenuUrl(){
        List<UserVo> userVoList = contractorService.findUserByMenuUrl("contractorManage/contractorList");
        return new Response(userVoList);
    }

    @ResponseBody
    @PostMapping("findContractorByRoleId")
    public Response findUserByMenuUrl(@RequestBody String id) throws Exception{
        String roleName = (String) UserContextUtil.getAttribute("roleName");
        String roleNames = (String) UserContextUtil.getAttribute("roleNames");
        List<Contractor> contractorList = contractorService.selectContractorList();
        if("总包商".equals(roleName)) {
            List<Contractor> newContractorList = new ArrayList<Contractor>();
            User  user = (User) UserContextUtil.getAttribute("currentUser");
            for(Contractor oldContractor : contractorList) {
               String userStr = oldContractor.getUserId();
               for(String userId : userStr.split(",")){
                   if(user.getUserId() == Long.parseLong(userId)) {
                       newContractorList.add(oldContractor);
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
        User user=(User) UserContextUtil.getAttribute("currentUser");
        Map<String, Object> Param = queryFilter.getParam();
        Param.put("userId", user.getUserId());
        queryFilter.setParam(Param);
        System.out.println(queryFilter);
        List list = contractorService.findWhiteList(queryFilter);
        PageInfo pageInfo = new PageInfo(list);
        return new Response(pageInfo);
    }

    @PostMapping("contractorOrderListPage")
    @ResponseBody
    public Response contractorOrderList(@RequestBody ParamFilter queryFilter) throws Exception{
        int pageNo = PageConvert.convert(queryFilter.getPage().getFirstIndex(),queryFilter.getPage().getPageSize());
        PageHelper.startPage(pageNo, queryFilter.getPage().getPageSize());
        System.out.println(queryFilter);
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
        int num = contractorService.updateContractor(contractor);
        Response response = new Response();
        if (num > 0){
            response.setMsg("绑定成功");
            return response;
        }
        response.setMsg("绑定失败");
        response.setCode(1);
        return response;
    }

    @ResponseBody
    @PostMapping("addWhiteList")
    @WebLogger("添加白名单")
    public Response addContractor(@RequestBody WhiteList whiteList) throws Exception{
        checkNotNull(whiteList, "白名单不能为空");
       int num = contractorService.addWhiteList(whiteList);
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
    @PostMapping("updateWhiteList")
    @WebLogger("编辑白名单")
    public Response updateContractor(@RequestBody WhiteList whiteList) throws Exception{
        int num = contractorService.updateWhiteList(whiteList);
        Response response = new Response();
        if (num > 0){
            response.setMsg("修改成功");
            return response;
        }
        response.setMsg("修改失败");
        response.setCode(1);
        return response;
    }

    /**
     * 上传总包商附件
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
}
