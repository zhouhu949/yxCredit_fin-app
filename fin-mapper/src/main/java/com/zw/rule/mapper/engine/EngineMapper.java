package com.zw.rule.mapper.engine;

import com.zw.rule.engine.po.Engine;
import com.zw.rule.mapper.common.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/12.
 */
public interface EngineMapper extends BaseMapper<Engine> {
    List<Engine> getEngineByList(Engine engine);

    int updateEngine(Engine engine);

    int insertEngineAndReturnId(Engine engine);
}

