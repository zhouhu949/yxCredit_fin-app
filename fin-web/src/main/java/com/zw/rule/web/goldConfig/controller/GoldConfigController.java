package com.zw.rule.web.goldConfig.controller;

import com.zw.rule.core.Response;
import com.zw.rule.goldconfig.service.IGoldConfigService;
import com.zw.rule.po.User;
import com.zw.rule.web.util.UserContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * <strong>Title : <br>
 * </strong> <strong>Description : </strong>@类注释说明写在此处@<br>
 * <strong>Create on : 2017年12月20日<br>
 * </strong>
 * <p>
 * <strong>Copyright (C) zw.<br>
 * </strong>
 * <p>
 *
 * @author department:技术开发部 <br>
 *         username:zh-pc <br>
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
@RequestMapping("goldConfig")
public class GoldConfigController {

    @Autowired
    private IGoldConfigService goldConfigService;

    @GetMapping("goldConfig")
    public ModelAndView config() throws Exception {
        Map map = goldConfigService.getConfig();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addAllObjects(map);
        modelAndView.setViewName("goldConfig/goldConfig");
        return modelAndView;
    }

    @PostMapping("updateConfig")
    @ResponseBody
    public Response updateConfig(@RequestBody Map<String, String> params) throws Exception {
        Response response = new Response();
        User user = (User) UserContextUtil.getAttribute("currentUser");
        params.put("update_user", user.getAccount());
        goldConfigService.updateConfig(params);
        response.setMsg("修改成功");
        return response;
    }

}
