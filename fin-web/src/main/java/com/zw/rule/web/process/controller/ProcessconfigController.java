package com.zw.rule.web.process.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zw.rule.core.Response;
import com.zw.rule.mybatis.ParamFilter;
import com.zw.rule.process.po.Process;
import com.zw.rule.process.po.SysCode;
import com.zw.rule.process.service.ProcessConfigService;
import com.zw.rule.process.service.ProcessService;
import com.zw.rule.util.flow.service.FlowComService;
import com.zw.rule.web.aop.annotaion.WebLogger;
import com.zw.rule.web.util.PageConvert;
import com.zw.rule.web.util.UserContextUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/30.
 */
@Controller
@RequestMapping("processConfig")
public class ProcessconfigController {
    @Resource
    private ProcessService processService;
    @Resource
    private ProcessConfigService processConfigService;
    @Resource
    private FlowComService flowComService;

    @RequestMapping({"/toprocessConfigList"})
    public String procedureList() {
        return "/procedureMange/processConfigList";
    }
    @RequestMapping({"/list"})
    @WebLogger("流程配置列表")
    @ResponseBody
    public Response processIndex(@RequestBody ParamFilter queryFilter) {
        Long userId = UserContextUtil.getUserId();
        Long organId = UserContextUtil.getOrganId();
        queryFilter.getParam().put("userId", userId);
        int pageNo = PageConvert.convert(queryFilter.getPage().getFirstIndex(),queryFilter.getPage().getPageSize());
        int pageSize=queryFilter.getPage().getPageSize();
        String searchString=(String)queryFilter.getParam().get("searchString");
        Process process =new Process();
        process.setOrgId(organId);
        process.setSearchString(searchString);
        PageHelper.startPage(pageNo, pageSize);
        List list = this.processService.getProcessByList(process);
        PageInfo pageInfo = new PageInfo(list);
        return new Response(pageInfo);
    }

    @RequestMapping({"/getProductList"})
    @ResponseBody
    public Response getProductList(@RequestBody ParamFilter queryFilter) {
        Map<String, Object> map = queryFilter.getParam();
        String searchKey = String.valueOf(map.get("searchKey"));//查询参数
        int pageNo = PageConvert.convert(queryFilter.getPage().getFirstIndex(),queryFilter.getPage().getPageSize());
        int pageSize=queryFilter.getPage().getPageSize();
        PageHelper.startPage(pageNo, pageSize);
        List<SysCode> list = processConfigService.getDictList(map);
        PageInfo pageInfo = new PageInfo(list);
        return new Response(pageInfo);
    }

    /**
     * 配置产品或商户信息
     * @param map
     * id 流程id
     * rel_type  关联类型
     * rel_id 关联id
     * @return
     */
    @RequestMapping({"/updateProcessRel"})
    @WebLogger("配置产品或商户信息")
    @ResponseBody
    public Response updateProcessRel(@RequestBody Map map) throws Exception{
        Response response = new Response();
        Boolean flag = processConfigService.updateProcessRel(map);
        if(flag == true){
            response.setMsg("配置成功");
            processConfigService.updateStateForSysCodeTable(map.get("relId").toString());
        }else{
            response.setMsg("配置失败");
        }
        return response;
    }
}
