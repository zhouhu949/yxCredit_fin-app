package com.zw.rule.web.controller;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

/**
 * <strong>Title : <br>
 * </strong> <strong>Description : </strong>@类注释说明写在此处@<br>
 * <strong>Create on : 2017年12月25日<br>
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
public class LanguageController {

    @RequestMapping(value = "/changeLang")
    public String changeLang(HttpServletRequest request, HttpServletResponse response, Model model, @RequestParam(value = "langType", defaultValue = "zh") String langType) {
        if (!model.containsAttribute("contentModel")) {
            if (langType.equals("zh")) {
                Locale locale = new Locale("zh", "CN");
                (new CookieLocaleResolver()).setLocale(request, response, locale);
            } else if (langType.equals("en")) {
                Locale locale = new Locale("en", "US");
                (new CookieLocaleResolver()).setLocale(request, response, locale);
            } else (new CookieLocaleResolver()).setLocale(request, response, LocaleContextHolder.getLocale());
        }
        return "redirect:login";
    }
}
