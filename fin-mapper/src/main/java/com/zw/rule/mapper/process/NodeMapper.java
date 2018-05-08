package com.zw.rule.mapper.process;

import com.zw.rule.mapper.common.BaseMapper;
import com.zw.rule.process.po.Node;
import com.zw.rule.process.po.ProcessNode;

import java.util.List;

/**
 * Created by Administrator on 2017/6/13 0013.
 */
public interface NodeMapper extends BaseMapper<NodeMapper> {
    List<Node> getNodeByList(Node var1);

}
