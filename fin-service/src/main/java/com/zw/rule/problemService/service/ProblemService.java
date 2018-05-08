package com.zw.rule.problemService.service;

import com.zw.rule.problem.po.ProblemManage;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/9/19.
 */
public interface ProblemService {
    List getProblemList(Map map);
    int addProblem(ProblemManage ProblemManage);
    int updateProblem(ProblemManage ProblemManage);
    int deleteProblem(String id);
    int updateState(Map map);
    ProblemManage getById(String id);
}
