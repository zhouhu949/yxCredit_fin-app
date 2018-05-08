package com.zw.rule.web.controller;

import com.google.common.base.Strings;
import com.zw.rule.core.Response;
import com.zw.rule.mybatis.ParamFilter;
import com.zw.rule.mybatis.page.Page;
import com.zw.rule.po.Safe;
import com.zw.rule.service.SafeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <strong>Title : <br>
 * </strong> <strong>Description : </strong>@类注释说明写在此处@<br>
 * <strong>Create on : 2017年06月13日<br>
 * </strong>
 * <p>
 * <strong>Copyright (C) Vbill Co.,Ltd.<br>
 * </strong>
 * <p>
 *
 * @author department:技术开发部 <br>
 *         username:Administrator <br>
 *         email: <br>
 * @version <strong>zw有限公司-运营平台</strong><br>
 *          <br>
 *          <strong>修改历史:</strong><br>
 *          修改人 修改日期 修改描述<br>
 *          -------------------------------------------<br>
 *          <br>
 *          <br>
 */
@Controller
@RequestMapping("safe")
public class SafeController {
    @Resource
    private SafeService safeService;

    @GetMapping("list")
    public String list() {
        return "systemMange/safeList";
    }

    /**
     * 添加安全设置
     * @param name
     * @param confName
     * @return
     */
    @PostMapping("add")
    @ResponseBody
    public Response add(String name,String confName){
        Response response = new Response();
        if(Strings.isNullOrEmpty(name) && Strings.isNullOrEmpty(confName)){
            response.setCode(1);
            response.setMsg("操作失败");
        }
        else{
            safeService.insert(name,confName);
            response.setMsg("操作成功");
        }
        return response;
    }

    /**
     * 根据配置名称查询配置的值
     * @return
     */
    @PostMapping("find")
    @ResponseBody
    public Response find(String confName){
        Response response = new Response();
        String data = safeService.selectErrorCount(confName);
        if(data==null){
            response.setCode(1);
            response.setMsg("无此信息");
        }else{
            response.setData(data);
        }
        return response;
    }

    /**
     * 安全配置列表
     * @return
     */
    @PostMapping("getList")
    @ResponseBody
    public Response getSafeList(@RequestBody ParamFilter queryFilter){
        Response response = new Response();
        List<Safe> list = safeService.getSafeList(queryFilter);
        Page page = queryFilter.getPage();
        return new Response(list,page);
    }
}