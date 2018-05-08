package com.zw.rule.problemService.service.impl;

import com.zw.rule.mapper.problemManage.ProblemMapper;
import com.zw.rule.problem.po.ProblemManage;
import com.zw.rule.problemService.service.ProblemService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/9/19.
 */
@Service
public class ProblemServiceImpl implements ProblemService {
    @Resource
    private ProblemMapper problemMapper;
    public List getProblemList(Map map){
        return problemMapper.getProblemList(map);
    }
    public int addProblem(ProblemManage problemManage){
        return problemMapper.addProblem(problemManage);
    }

    public int updateProblem(ProblemManage problemManage){
        return problemMapper.updateProblem(problemManage);
    }
    public int deleteProblem(String id){
        return problemMapper.deleteProblem(id);
    }
    public int updateState(Map map){
        return problemMapper.updateState(map);
    }
    public ProblemManage getById(String id){
        return problemMapper.getById(id);
    }
}
