package com.zw.rule.engine.service;


import com.zw.rule.engine.po.Engine;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/12.
 */
public interface EngineService {
    /**
     * 获取流程列表
     * @param engine
     * @return
     */
    List<Engine> getEngineByList(Engine engine);

    /**
     * 更新流程
     * @param engine
     * @return
     */
    int updateEngine(Engine engine);

    /**
     * 保存流程
     * @param engine
     * @return
     */
    boolean saveEngine(Engine engine , String url);


}

