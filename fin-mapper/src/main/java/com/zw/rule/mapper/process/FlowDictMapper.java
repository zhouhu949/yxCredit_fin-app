package com.zw.rule.mapper.process;

import com.zw.rule.po.Dict;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/7/12.
 */
public interface FlowDictMapper {
    List<Dict> getProductByList(Map map);
}
