package com.zw.rule.web.customer.controller;

import com.zw.UploadFile;
import com.zw.rule.core.Response;
import com.zw.rule.customer.service.OrderService;
import com.zw.rule.web.aop.annotaion.WebLogger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/******************************************************
 *Copyrights @ 2017，zhiwang  Co., Ltd.
 *
 *All rights reserved.
 *           小窝
 *Filename：
 *          客户反欺诈控制层
 *Description：
 *		  未知
 *Author:
 *		 李开艳
 *Finished：
 *		2017/6/27
 ********************************************************/
@Controller
@RequestMapping("custAntifraud")
public class CustomerAntifraudController {
    @Resource
    private OrderService orderService;

    @GetMapping("listPage")
    public String list(){
        return "customerManage/customerAntifraud";
    }

    @PostMapping("antifraud")
    @ResponseBody
    @WebLogger("反欺诈审核")
    public Response antifraud(@RequestBody Map map){
        Response response = new Response();
        if(orderService.antifraud(map)){
            response.setMsg("保存成功！");
        }else{
            response.setCode(1);
            response.setMsg("保存失败！");
        }
        return response;
    }

    /**
     * 批量上传反欺诈资料
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("batchUploadFile")
    @ResponseBody
    @WebLogger("客户反欺诈上传资料")
    public void batchUploadFile(HttpServletRequest request) throws Exception{
        orderService.batchUploadAntiFraud(request);
    }
}
