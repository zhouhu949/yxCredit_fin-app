package com.zw.rule.mapper.engine;

import com.zw.rule.engine.po.EngineNode;
import com.zw.rule.mapper.common.BaseMapper;

/**
 * Created by Administrator on 2017/6/12.
 */
public interface EngineNodeMapper extends BaseMapper<EngineNode> {

    int insertSelective(EngineNode node);
}
