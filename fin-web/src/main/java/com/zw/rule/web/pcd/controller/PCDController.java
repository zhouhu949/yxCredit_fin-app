package com.zw.rule.web.pcd.controller;


import com.zw.rule.core.Response;
import com.zw.rule.pcd.service.IPCDLinkedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("pcd")
public class PCDController {
    @Resource
    private IPCDLinkedService pcdLinkedService;

    /**
     * 省市区获取
     * @param map
     * flag
     * parentId
     * @return
     * @throws Exception
     */
    @RequestMapping("getPCD")
    @ResponseBody
    public Response getPCD(@RequestBody Map map) throws Exception {
        List list=pcdLinkedService.getPCD(map);
        return new Response(list);
    }
}

