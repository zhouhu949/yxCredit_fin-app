package com.zw.rule.web.suggestion.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zw.rule.core.Response;
import com.zw.rule.mybatis.ParamFilter;
import com.zw.rule.service.DictService;
import com.zw.rule.suggestion.SuggestionService;
import com.zw.rule.suggestion.po.Suggestion;
import com.zw.rule.web.aop.annotaion.WebLogger;
import com.zw.rule.web.util.PageConvert;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <strong>Title : <br>
 * </strong> <strong>Description : </strong>@意见投诉@<br>
 * <strong>Create on : 2017年07月31日<br>
 * </strong>
 * <p>
 * <strong>Copyright (C) Vbill Co.,Ltd.<br>
 * </strong>
 * <p>
 *
 * @author department:技术开发部 <br>
 *         username:gaozhidong <br>
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
@RequestMapping("suggestion")
public class SuggestionController {

    @Resource
    private SuggestionService suggestionService;
    @Resource
    private DictService dictService;

    @GetMapping("listPage")
    @WebLogger("获取意见页面")
    public String list(){
        return "suggestion/suggestion";
    }


    @PostMapping("apendSelect")
    @ResponseBody
    @WebLogger("动态加载问题分类到下拉框")
    public Response apendSelect(){
        Response response = new Response();
        List list=dictService.getDetailList("投诉建议类型");
        if (list!=null&&list.size()>0){
            response.setData(list);
        }else {
            return null;
        }
        return response;
    }

    /*****************************************************碧友信***************************************************/

    /**
     *获取意见反馈列表
     * @author 仙海峰
     * @param queryFilter
     * @return
     */
    @PostMapping("list")
    @ResponseBody
    @WebLogger("获取意见投诉列表")
    public Response list(@RequestBody ParamFilter queryFilter){
        int pageNo = PageConvert.convert(queryFilter.getPage().getFirstIndex(),queryFilter.getPage().getPageSize());
        PageHelper.startPage(pageNo, queryFilter.getPage().getPageSize());
        List<Suggestion> list = suggestionService.getSuggestionList(queryFilter);
        PageInfo pageInfo = new PageInfo(list);
        return new Response(pageInfo);
    }

}
