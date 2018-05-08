//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//


package com.zw.rule.engine.service.impl;


import com.zw.rule.engine.po.Engine;
import com.zw.rule.engine.po.EngineNode;
import com.zw.rule.engine.po.EngineVersion;
import com.zw.rule.engine.po.NodeTypeEnum;
import com.zw.rule.engine.service.EngineService;
import com.zw.rule.mapper.engine.EngineMapper;
import com.zw.rule.mapper.engine.EngineNodeMapper;
import com.zw.rule.mapper.engine.EngineVersionMapper;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class EngineServiceImpl implements EngineService {
    private static final Logger logger = Logger.getLogger(EngineServiceImpl.class);
    @Resource
    private EngineMapper engineMapper;

    @Resource
    private EngineVersionMapper engineVersionMapper;

    @Resource
    private EngineNodeMapper engineNodeMapper;

    public EngineServiceImpl() {
    }

    public List<Engine> getEngineByList(Engine engineVo) {
        return this.engineMapper.getEngineByList(engineVo);
    }

    public int updateEngine(Engine engineVo) {
        return this.engineMapper.updateEngine(engineVo);
    }

    public boolean saveEngine(Engine engine,String url) {
        boolean flag = true;
        int engineCount = this.engineMapper.insertEngineAndReturnId(engine);
        if(engineCount == 1) {
            Long engineId = engine.getId();
            if(engineId != null && engineId > 0L) {
                EngineVersion engineVersion = new EngineVersion();
                engineVersion.setBootState(0);
                engineVersion.setCreateTime((new Date()).toString());
                engineVersion.setEngineId(engineId);
                engineVersion.setLatestTime((new Date()).toString());
                engineVersion.setLatestUser(engine.getCreator());
                engineVersion.setLayout(0);
                engineVersion.setStatus(1);
                engineVersion.setUserId(engine.getCreator());
                engineVersion.setVersion(0);
                engineVersion.setSubVer(0);
                EngineNode node = new EngineNode();
                node.setProcessId(engineId);
                node.setNodeX(200.0D);
                node.setNodeY(200.0D);
                node.setNodeName("开始");
                node.setNodeOrder(1);
                node.setNodeType(NodeTypeEnum.START.getValue());
                node.setNodeCode("ND_START");
                node.setParams("{\"arr_linkId\":\"\",\"dataId\":\"-1\",\"url\":\""+url+"/resources/images/decision/start.png\",\"type\":\"1\"}");
                int count = this.engineVersionMapper.insertEngineVersionAndReturnId(engineVersion);
                if(count == 1) {
                    this.engineNodeMapper.insertSelective(node);
                }

                ArrayList arrayList = new ArrayList();
                String id_str = "1_$engineID$,11_$engineID$,111_$engineID$,112_$engineID$,1121_$engineID$,12_$engineID$,121_$engineID$,122_$engineID$,1221_$engineID$,123_$engineID$,1231_$engineID$,13_$engineID$,131_$engineID$,132_$engineID$,1321_$engineID$,133_$engineID$,1331_$engineID$,14_$engineID$,141_$engineID$,15_$engineID$,151_$engineID$";
                id_str = id_str.replace("$engineID$", String.valueOf(engineId));
                String[] id_arr = id_str.split(",");
                if(id_arr != null && id_arr.length > 0) {
                    for(int sysUser = 0; sysUser < id_arr.length; sysUser++) {
                        arrayList.add(id_arr[sysUser]);
                    }
                }
            } else {
                flag = false;
            }
        } else {
            flag = false;
        }

        return flag;
    }
}
