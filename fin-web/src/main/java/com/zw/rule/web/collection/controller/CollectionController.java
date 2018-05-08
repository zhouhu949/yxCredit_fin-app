package com.zw.rule.web.collection.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zw.rule.collectionRecord.service.CollectionRecordService;
import com.zw.rule.collectionRecord.po.MagCollectionRecord;
import com.zw.rule.core.Response;
import com.zw.rule.mybatis.ParamFilter;
import com.zw.rule.mybatis.page.Page;
import com.zw.rule.web.util.PageConvert;
import com.zw.rule.web.util.UserContextUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("collection")
public class CollectionController {

    @Resource
    private CollectionRecordService collectionRecordService;

    @GetMapping("listPage")
    public String list(String leftStatus){
        UserContextUtil.setAttribute("leftStatus",leftStatus);
        return "";
    }

    @PostMapping("list")
    @ResponseBody
    public Response list(@RequestBody ParamFilter queryFilter){
        Response response = new Response();
        int pageNo = PageConvert.convert(queryFilter.getPage().getFirstIndex(),queryFilter.getPage().getPageSize());
        PageHelper.startPage(pageNo, queryFilter.getPage().getPageSize());
        List list = collectionRecordService.getCollectionRecordByLoanId(queryFilter.getParam());
        PageInfo pageInfo = new PageInfo(list);
        response.setData(pageInfo);
        return response;
    }

    @PostMapping("getCollectionRecordById")
    @ResponseBody
    public Response getCollectionRecordById(String id){
        MagCollectionRecord magCollectionRecord = collectionRecordService.getCollectionRecordById(id);
        return new Response(magCollectionRecord);
    }

    @PostMapping("add")
    @ResponseBody
    public Response addCollectionRecord(@RequestBody MagCollectionRecord magCollectionRecord){
        magCollectionRecord.setCreateUserId(String.valueOf(UserContextUtil.getUserId()));
        magCollectionRecord.setCreateUserName(UserContextUtil.getNickName());
        collectionRecordService.addCollectionRecord(magCollectionRecord);
        Response response = new Response();
        response.setMsg("添加成功");
        return response;
    }

    @PostMapping("update")
    @ResponseBody
    public Response updateCollectionRecord(@RequestBody MagCollectionRecord magCollectionRecord){
        collectionRecordService.updateCollectionRecord(magCollectionRecord);
        Response response = new Response();
        response.setMsg("修改成功");
        return response;
    }

    @PostMapping("delete")
    @ResponseBody
    public Response deleteCollectionRecord(String id){
        collectionRecordService.deleteCollectionRecordById(id);
        Response response = new Response();
        response.setMsg("删除成功");
        return response;
    }

}
