package com.zw.rule.suggestion.impl;
import com.zw.rule.mapper.suggestion.SuggestionMapper;
import com.zw.rule.mybatis.ParamFilter;
import com.zw.rule.suggestion.SuggestionService;

import com.zw.rule.suggestion.po.Suggestion;
import org.springframework.stereotype.Service;

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
@Service
public class SuggestionServiceImpl implements SuggestionService {

    @Resource
    private SuggestionMapper suggestionMapper;
    //获取合规审查列表
    @Override
    public List<Suggestion> getSuggestionList(ParamFilter queryFilter){
        return suggestionMapper.getSuggestionList(queryFilter);
    }
}
