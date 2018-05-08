package com.zw.rule.mapper.problemManage;

import com.zw.rule.problem.po.ProblemManage;

import java.util.List;
import java.util.Map;

public interface ProblemMapper {

    List getProblemList(Map param);
    int addProblem(ProblemManage ProblemManage);
    int updateProblem(ProblemManage ProblemManage);
    int deleteProblem(String id);
    int updateState(Map map);
    ProblemManage getById(String id);
}