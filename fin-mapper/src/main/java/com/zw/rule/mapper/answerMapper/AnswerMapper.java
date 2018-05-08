package com.zw.rule.mapper.answerMapper;

import com.zw.rule.answer.po.Answer;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/1/2.
 */
public interface AnswerMapper {
    //添加问题
    int insertAnswerList(List<Answer> answerList);
    List<Answer> getAnswerList(Map map);
    int updateAnswer(Map map);
}
